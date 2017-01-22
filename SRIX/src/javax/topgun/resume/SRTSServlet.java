package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.SRTS;
import com.topgun.resume.SRTSManager;
import com.topgun.shris.masterdata.Faculty;
import com.topgun.shris.masterdata.Major;
import com.topgun.shris.masterdata.School;
import com.topgun.util.Encoder;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class SRTSServlet extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
        
        String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
        int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"));
        int idLanguage=Util.getInt(request.getParameter("idLanguage")!=""?request.getParameter("idLanguage"):38);
        int degree=Util.getInt(request.getParameter("degree")!=""?request.getParameter("degree"):2);
        int idSchool=Util.getInt(request.getParameter("idSchool")!=""?request.getParameter("idSchool"):0);
        int idFac=Util.getInt(request.getParameter("idFac")!=""?request.getParameter("idFac"):0);
        int idMajor=Util.getInt(request.getParameter("idMajor")!=""?request.getParameter("idMajor"):0);
        String service=Util.getStr(request.getParameter("service"));
        if(service.equals("getSchool"))
        {
        	List<School> school=new SRTSManager().getAllSchool(idLanguage, degree);
        	if(school.size()>0)
        	{
        		json.put("school", school);
        	}
        }
        else if(service.equals("getFaculty"))
        {
        	List<Faculty> faculty=new SRTSManager().getAllFaculty(idLanguage, idSchool, degree);
        	if(faculty.size()>0)
        	{
        		json.put("faculty", faculty);
        	}
        }
        else if(service.equals("getMajor"))
        {
        	List<Major> major=new SRTSManager().getAllMajor(idLanguage, idSchool, idFac, degree);
        	if(major.size()>0)
        	{
        		json.put("major", major);
        	}
        }
        else if(service.equals("setStudent"))
        {
        	if(idSchool>0 && idFac>0 )
        	{
        		request.getSession().setAttribute("SESSION_STU_ID_DEGREE", degree);
        		request.getSession().setAttribute("SESSION_STU_ID_SCHOOL", idSchool);
        		request.getSession().setAttribute("SESSION_STU_ID_FAC", idFac);
        		request.getSession().setAttribute("SESSION_STU_ID_MAJOR", idMajor);
        	}
        	else
        	{
        		errors.add(propMgr.getMessage(locale, "SRTS_ERROR"));
        	}
        }
        else if(service.equals("register"))
        {
        	int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
            String device = Util.getStr(request.getSession().getAttribute("SESSION_DEVICE"),"desktop");
            String agent = Util.getStr(request.getHeader("User-Agent"));
            String idSession=Encoder.getEncode(request.getSession().getId());
            JobseekerManager jskMgr=new JobseekerManager();
            ResumeManager rsmMgr=new ResumeManager();
            Jobseeker jsk=null;
            
            if(idJsk>0)
            {
            	jsk=jskMgr.get(idJsk);
            }
    		int idResume=Util.getInt(request.getParameter("idResume"));
    		if(idResume<0) idResume=0;
    		
    		if(idCountry<=0) idCountry=216;
    		if(idLanguage<=0) idLanguage=38;
    		if(idResume<=0) idResume=0;
    		
    		String idStu="";
    		String academicYear="";
    		String salutation="";
    		String firstNameThai="";
    		String lastNameThai="";
    		String primaryPhone="";
    		String primaryPhoneType="";
    		String privacy="";
    		String username="";
    		String password="";
    		String confirm="";
    		int bDay=-1;
    		int bMonth=-1;
    		int bYear=-1;
    		Date birthDate=null;
    		//username
    		if(idJsk<0)
    		{
    			username=Util.getStr(request.getParameter("username"));
    			if(username.equals(""))
    			{
    				errors.add(propMgr.getMessage(locale, "USERNAME_REQUIRED"));
    				elements.add("username");
    			}
    			else if(!Util.isEmail(username))
    			{
    				errors.add(propMgr.getMessage(locale, "USERNAME_EMAIL"));
    				elements.add("username");
    			}
    			else if(jskMgr.isExist(username))
    			{
    				errors.add(propMgr.getMessage(locale, "USERNAME_EXIST").replace("{0}",username));//pending
    				elements.add("username");
    			}
    			
    			//password
    			password=Util.getStr(request.getParameter("password"));
    			if(password.equals(""))
    			{
    				errors.add(propMgr.getMessage(locale, "PASSWORD_REQUIRED"));
    				elements.add("password");
    			}
    	
    			//confirm password
    			confirm=Util.getStr(request.getParameter("confirm"));
    			if(!confirm.equals(password))
    			{
    				errors.add(propMgr.getMessage(locale, "CONFIRM_EQUALTO"));
    				elements.add("confirm");
    			}
    		}
    		//salutation
    		if(idCountry==216)
    		{
    			salutation=Util.getStr(request.getParameter("salutation"));
    			if(salutation.equals("OTHER"))
    			{
    				salutation=Util.getStr(request.getParameter("otherSalutation"));
    			}
    			if(salutation.equals(""))
    			{
    				errors.add(propMgr.getMessage(locale, "SALUTATION_REQUIRED"));
    				elements.add("salutation");
    			}
    		}
    		if(idSchool<=0 && idFac<=0 )
        	{
        		errors.add(propMgr.getMessage(locale, "SRTS_ERROR"));
        	}
			idStu=Util.getStr(request.getParameter("idStu"));
			if(idStu.equals(""))
			{
				errors.add(propMgr.getMessage(locale, "ID_STUDENT_REQUIRED"));
				elements.add("username");
			}
			academicYear=Util.getStr(request.getParameter("academicYear"));
			if(academicYear.equals(""))
    		{
    			errors.add(propMgr.getMessage(locale, "EDU_ENDDATE_REQUIRED"));
    			elements.add("academicYear");
    		}
    		//firstName
    		String firstName=Util.getStr(request.getParameter("firstName"));
    		if(firstName.equals(""))
    		{
    			errors.add(propMgr.getMessage(locale, "FIRSTNAME_REQUIRED"));
    			elements.add("firstName");
    		}
    		
    		//lastName
    		String lastName=Util.getStr(request.getParameter("lastName"));
    		if(lastName.equals(""))
    		{
    			errors.add(propMgr.getMessage(locale, "LASTNAME_REQUIRED"));
    			elements.add("lastName");
    		}
    		String fname=firstName;
			String lname=lastName;
    		if(idCountry==216)
    		{
    			bDay=Util.getInt(request.getParameter("bDay"));
    			bMonth=Util.getInt(request.getParameter("bMonth"));
    			bYear=Util.getInt(request.getParameter("bYear"));
    		
    			if(bDay<=0)
    			{
    				errors.add(propMgr.getMessage(locale, "GLOBAL_DAY_REQUIRED"));
    				elements.add("bDay");				
    			}

    			if(bMonth<=0)
    			{
    				errors.add(propMgr.getMessage(locale, "GLOBAL_MONTH_REQUIRED"));
    				elements.add("bMonth");				
    			}

    			if(bYear<=0)
    			{
    				errors.add(propMgr.getMessage(locale, "GLOBAL_YEAR_REQUIRED"));
    				elements.add("bYear");				
    			}
    			
    			if(idLanguage==38)
    			{
    				firstNameThai=firstName;
    				lastNameThai=lastName;
    				firstName="";
    				lastName="";
    			}
    		}
    		birthDate=Util.getSQLDate(bDay, bMonth, bYear);
    		primaryPhone=Util.getStr(request.getParameter("primaryPhone"));
    		if(primaryPhone.equals(""))
    		{
    			errors.add(propMgr.getMessage(locale, "PRIMARYPHONE_REQUIRED"));
    			elements.add("primaryPhone");
    		}
    		
    		primaryPhoneType=Util.getStr(request.getParameter("primaryPhoneType"));
    		if(primaryPhoneType.equals(""))
    		{
    			errors.add(propMgr.getMessage(locale, "PRIMARYPHONETYPE_REQUIRED"));
    			elements.add("primaryPhoneType");
    		}
    		
    		privacy=Util.getStr(request.getParameter("privacy"));
    		if(privacy.equals(""))
    		{
    			errors.add(propMgr.getMessage(locale, "PRIVACY_REQUIRED"));
    			elements.add("privacy");
    		}
    		if(errors.size()==0)
    		{
    			if(idJsk<0) //create account
    			{
    				idJsk=jskMgr.getMaxIdJsk()+1;
    				jsk=new Jobseeker();			
    				jsk.setIdJsk(idJsk);
    				jsk.setUsername(username);
    				jsk.setPassword(password);
    				jsk.setAuthen(Encoder.getEncode(password));
    				jsk.setIdCountryRegister(idCountry);
    		
    				if(jskMgr.add(jsk,password)==1)  
    				{
    					Resume resume=new Resume();
    					resume.setIdJsk(idJsk);
    					resume.setIdResume(0);
    					resume.setFirstName(firstName);
    					resume.setFirstNameThai(firstNameThai);
    					resume.setLastName(lastName);
    					resume.setLastNameThai(lastNameThai);
    					if(birthDate!=null)
    					{
    						resume.setBirthDate(birthDate);
    					}
    					resume.setSalutation(salutation);
    					resume.setCompleteStatus("PERSONALDATA");
    					resume.setIdLanguage(idLanguage);
    					resume.setIdCountry(idCountry);
    					resume.setApplyIdCountry(idCountry);
    					resume.setLocale(locale);
    					resume.setPrimaryPhone(primaryPhone);
    					resume.setPrimaryPhoneType(primaryPhoneType);
    					resume.setResumeName("REGISTERDATA");
    					resume.setResumePrivacy(privacy);
    					resume.setTemplateIdCountry(idCountry);
    					if(rsmMgr.add(resume)!=1)
    					{
    						errors.add(propMgr.getMessage(locale, "RESUME_INSERT_ERROR"));
    						elements.add("SYSTEM");	
    					}
    					else
    					{
    						request.getSession().setAttribute("SESSION_ID_JSK", jsk.getIdJsk());
    						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
    						if(!jskMgr.isLogin(idJsk,idSession))
    						{
    							jskMgr.addLogin(idJsk,idSession,device,agent);
    						}
    					}
    				}
    				else
    				{
    					errors.add(propMgr.getMessage(locale, "JOBSEEKER_INSERT_ERROR"));
    					elements.add("SYSTEM");	
    				}
    				
    			}
    			else //update account
    			{
    				int exist=0;
    				Resume resume=rsmMgr.get(idJsk, 0);
    				if(resume!=null)
    				{
    					exist=1;
    				}
    				else
    				{
    					resume=new Resume();
    				}
    				resume.setIdJsk(idJsk);
    				resume.setIdResume(0);
    				resume.setFirstName(firstName);
    				resume.setFirstNameThai(firstNameThai);
    				resume.setLastName(lastName);
    				resume.setLastNameThai(lastNameThai);
    				if(birthDate!=null)
    				{
    					resume.setBirthDate(birthDate);
    				}
    				resume.setSalutation(salutation);
    				resume.setCompleteStatus("PERSONALDATA");
    				resume.setIdLanguage(idLanguage);
    				resume.setIdCountry(idCountry);
    				resume.setApplyIdCountry(idCountry);
    				resume.setLocale(locale);
    				resume.setPrimaryPhone(primaryPhone);
    				resume.setPrimaryPhoneType(primaryPhoneType);
    				resume.setResumeName("REGISTERDATA");
    				resume.setResumePrivacy(privacy);
    				resume.setTemplateIdCountry(idCountry);
    				if(exist==1)
    				{
    					if(rsmMgr.update(resume)!=1)
    					{
    						errors.add(propMgr.getMessage(locale, "RESUME_UPDATE_ERROR"));
    						elements.add("SYSTEM");
    					}
    					else
    					{
    						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
    					}
    				}
    				else 
    				{
    					if(rsmMgr.add(resume)!=1)
    					{
    						errors.add(propMgr.getMessage(locale, "RESUME_INSERT_ERROR"));
    						elements.add("SYSTEM");
    					}
    					else
    					{
    						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
    					}
    				}
    				if(!jskMgr.isLogin(idJsk,idSession))
    				{
    					jskMgr.addLogin(idJsk,idSession,device,agent);
    				}
    			}
    			SRTSManager srtsmng=new SRTSManager();
				SRTS stu=new SRTS();
				stu.setIdStu(idStu);
				stu.setIdJsk(idJsk);
				stu.setEmail(jsk.getUsername());
				stu.setIdUnv(idSchool);
				stu.setIdFac(idFac);
				stu.setIdMajor(idMajor);
				stu.setAcademicYear(academicYear);
				stu.setFirstName(fname);
				stu.setLastName(lname);
				stu.setDegree(degree);
				boolean checkExistStu=srtsmng.checkStudentExist(idSchool, idFac, idMajor, academicYear, idStu);
				if(checkExistStu==false)//insert
				{
					if(srtsmng.addStudent(stu)==0)
					{
						errors.add(propMgr.getMessage(locale, "SRTS_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else//update
				{
					if(srtsmng.updateStudent(stu)==0)
					{
						errors.add(propMgr.getMessage(locale, "SRTS_ERROR"));
						elements.add("SYSTEM");
					}
				}
    		}
        }
        else if(service.equals("checkLogin"))
        {
			String username=Util.getStr(request.getParameter("username"));
			String password=Util.getStr(request.getParameter("password"));
			int idJsk=-1;
			if(!username.equals("") && !password.equals(""))
			{
				Jobseeker jobseeker=new JobseekerManager().get(username, Encoder.getEncode(password));
				if(jobseeker!=null)
				{
					idJsk=jobseeker.getIdJsk();
					request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "LOGIN_INVALID"));
					elements.add("SYSTEM");
				}
				
			}
			else
			{
				errors.add(propMgr.getMessage(locale, "LOGIN_INVALID"));
				elements.add("SYSTEM");
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
			json.put("elements", elements);
		}
		else{
			json.put("success",1);
		}
		out.print(gson.toJson(json));
		out.close();
	}

}
