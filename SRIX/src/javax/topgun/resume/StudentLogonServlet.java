package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.CompleteStatus;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.LoginLogManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.SRTS;
import com.topgun.resume.SRTSManager;
import com.topgun.resume.WorkExperienceManager;
import com.topgun.shris.masterdata.School;
import com.topgun.util.DBManager;
import com.topgun.util.Encoder;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;


public class StudentLogonServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{  
		ArrayList<String> errors = new ArrayList<String>();
		PropertiesManager propMgr=new PropertiesManager();
		Gson gson = new Gson();
        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
        
        
		String locale=Util.getStr(request.getParameter("locale"));
		if(locale.equals(""))
		{
			locale=Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH");
		}
		String target="/?invalid=1"; //not specify username or password
        String username=Util.getStr(request.getParameter("username"));
		String password=Util.getStr(request.getParameter("password"));
		String idSession=Encoder.getEncode(request.getSession().getId());
		JobseekerManager jskMgr=new JobseekerManager();
		int idJsk=-1;
		String service=Util.getStr(request.getParameter("service"));
		if(service.equals("checkLogin"))
		{
			if(!username.equals("") && !password.equals(""))
			{
				Jobseeker jobseeker=jskMgr.get(username, Encoder.getEncode(password));
				if(jobseeker!=null)
				{
					idJsk=jobseeker.getIdJsk();
					jskMgr.updateLastLogin(idJsk);
					jskMgr.addLogin(idJsk,idSession,"","");
					request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
					SRTS stu=new SRTSManager().getStudentByIdJdk(idJsk);
					if(stu==null)
					{
						target="studentIdForm";
					}
					else
					{
						target= getStatus( idJsk) ;
					}
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "LOGIN_INVALID"));
				}
			}
		}
		else  if(service.equals("setIdJsk"))
        {
			String idStu=Util.getStr(request.getParameter("idStu"));
			idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
			int idSchool=Util.getInt(request.getSession().getAttribute("SESSION_STU_ID_SCHOOL"));
			int idFac=Util.getInt(request.getSession().getAttribute("SESSION_STU_ID_FAC"));
			int idMajor=Util.getInt(request.getSession().getAttribute("SESSION_STU_ID_MAJOR"));
			int degree=Util.getInt(request.getSession().getAttribute("SESSION_STU_ID_DEGREE"));
			String academicYear=Util.getStr(request.getParameter("academicYear"));
			int rs=0;
			if(academicYear.equals(""))
    		{
    			errors.add(propMgr.getMessage(locale, "EDU_ENDDATE_REQUIRED"));
    		}
    		if(idSchool<=0 && idFac<=0 )
        	{
    			String errorMsg="พบข้อผิดพลาด กรุณา <a href='http://student.superresume.com/studentTracking.jsp'>คลิกที่นี่</a> เพื่อกลับไปหน้าแรก";
        		errors.add(propMgr.getMessage(locale, errorMsg));
        	}
			Resume resume=null;
			Jobseeker jsk=new JobseekerManager().get(idJsk);
			if(jsk!=null)
			{
				resume=new ResumeManager().get(idJsk, 0);
			}
			if(resume!=null && idSchool>0 && idFac>0)
			{
				String fname=resume.getFirstName();
				String lname=resume.getLastName();
				if(resume.getIdCountry()==216 && resume.getIdLanguage()==38)
				{
					fname=resume.getFirstNameThai();
					lname=resume.getLastNameThai();
				}
				SRTSManager srtsmng=new SRTSManager();
				SRTS stu=new SRTS();
				stu.setIdStu(idStu);
				stu.setIdJsk(idJsk);
				stu.setEmail(jsk.getUsername());
				stu.setIdUnv(idSchool);
				stu.setIdFac(idFac);
				stu.setIdMajor(idMajor);
				stu.setFirstName(fname);
				stu.setLastName(lname);
				stu.setAcademicYear(academicYear);
				stu.setDegree(degree);
				boolean checkExistStu=srtsmng.checkStudentExist(idSchool, idFac, idMajor, academicYear, idStu);
				if(checkExistStu==false)//insert
				{
					rs=srtsmng.addStudent(stu);
				}
				else//update
				{
					rs=srtsmng.updateStudent(stu);
				}
			}
			if(rs==1)
			{
				target= getStatus( idJsk) ;
			}
			else
			{
				errors.add(propMgr.getMessage(locale, "ID_STUDENT_INVALID"));
			}
        }
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        if(errors.size()>0){
			json.put("success",0);
			json.put("errors", errors);
		}
		else{
			json.put("success",1);
			json.put("target", target);
		}
        
		out.print(gson.toJson(json));
		out.close();
	}
	
	private String  getStatus(int idJsk) 
	{
		ResumeManager rsmMgr=new ResumeManager();
		Resume resume=rsmMgr.get(idJsk, 0);
		String status=null;
		String target=null;
		if(resume!=null)
		{
			status=new ResumeStatusManager().getRegisterStatus(resume);
		}
		else
		{
			status="PERSONALDATA";
		}
		//old jobseeker that complete register, don't have strength or aptitude
		//transfer aptitude and strength from the latest complete resume. and recheck status again
		if(status.equals("STRENGTH") || status.equals("APTITUDE"))
		{
			Resume rs=rsmMgr.getLatestCompleted(idJsk);
			if(rs!=null)
			{
				if(rs.getIdResume()>0)
				{
					rsmMgr.copyStrength(idJsk, rs.getIdResume(), 0, resume.getIdLanguage());
					rsmMgr.copyAptitude(idJsk, rs.getIdResume(), 0, resume.getIdLanguage());
					rsmMgr.updateTimestamp(idJsk, 0);
					status=new ResumeStatusManager().getRegisterStatus(resume);
				}
			}
		}
		if(status.equals("TRUE"))
		{
			target="home";
		}
		else if(status.equals("REGISTERDATA"))
		{
			target="register";
		}
		else if(status.equals("PERSONALDATA"))
		{
			target="personal";
		}
		else if(status.equals("EDUCATION"))
		{
			target="education";
			CompleteStatus edu=new ResumeStatusManager().getEducationCompleteStatus(resume);
			if(edu!=null)
			{
				if(edu.getStatusList()!=null)
				{
					if(edu.getStatusList().size()>0)
					{
						int idEdu=edu.getStatusList().get(0).getId();
						target="education&idEducation="+idEdu;
					}
				}
			}
		}
		else if(status.equals("EXPERIENCE"))
		{
			target="experience";
		}
		else if(status.equals("TARGETJOB"))
		{
			target="targetJob";
		}
		else if(status.equals("STRENGTH"))
		{
			target="strength";
		} 
		else if(status.equals("APTITUDE"))
		{
			target="aptitude";
		}
		rsmMgr.updateStatus(idJsk, 0, status);
		return target;
	}
}

