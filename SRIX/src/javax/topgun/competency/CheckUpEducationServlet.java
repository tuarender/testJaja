package javax.topgun.competency;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Reference;
import com.topgun.resume.ReferenceManager;
import com.topgun.resume.ResumeManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class CheckUpEducationServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<String[]> educationListDetail = new ArrayList<String[]>();
        PropertiesManager propMgr=new PropertiesManager();
        CheckUpEducationManager eduMgr=new CheckUpEducationManager();
        String urlErrorRedirect = "";
        
        int idUser=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_USER"));
        if(idUser<=0){
			errors.add(propMgr.getMessage("th_TH", "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			String service=Util.getStr(request.getParameter("service"));
			if(service.equals("addEducation")){
				int idEducation = Util.getInt(request.getParameter("idEducation"));
				int degree = Util.getInt(request.getParameter("degree")); 
				int faculty = Util.getInt(request.getParameter("idFaculty"),0);
				int major = Util.getInt(request.getParameter("idMajor"),0);
				if(degree==1){
					faculty = 0;
					major = 0;
				}
				
				if (degree == 0){
					errors.add(propMgr.getMessage("th_TH", "EDU_DEGREE_REQUIRED"));
					elements.add("degree");	
				}
				else if (((faculty == -1)||faculty==0) && degree != 1){
					errors.add(propMgr.getMessage("th_TH", "EDU_FACULTY_REQUIRED"));
					elements.add("faculty");	
				}
				else if (((major == -1)||major==0) && degree!= 1 ){
					errors.add(propMgr.getMessage("th_TH", "EDU_MAJOR_REQUIRED"));
					elements.add("major");	
				}
				
				if(errors.size()==0){
					boolean isEduExist = true;
					CheckUpEducation chkEdu = eduMgr.get(idUser, idEducation);
					
					if(chkEdu==null){
						chkEdu = new CheckUpEducation();
						chkEdu.setIdUser(idUser);
						chkEdu.setIdEducation(idEducation);
						isEduExist = false;
					}
					chkEdu.setIdDegree(degree);
					chkEdu.setIdFaculty(faculty);
					chkEdu.setIdMajor(major);
					if(isEduExist){
						//------------UPDATE---------------
						if(eduMgr.updateEdu(chkEdu)!=1){
							errors.add(propMgr.getMessage("th_TH", "EDU_UPDATE_ERROR"));
							elements.add("SYSTEM");
						}
					}
					else{
						//------------INSERT---------------
						if(eduMgr.addEdu(chkEdu)!=1){
							errors.add(propMgr.getMessage("th_TH", "EDU_INSERT_ERROR"));
							elements.add("SYSTEM");
						}
					}
				}
			}
			else if(service.equals("getEducationList")){
				educationListDetail.clear();
				ArrayList<CheckUpEducation> educationData = (ArrayList<CheckUpEducation>)eduMgr.getByIdUser(idUser);
				for(int i=0;i<educationData.size();i++){
					String facultyDetail = "";
					String majorDetail = "";
					com.topgun.shris.masterdata.Faculty faculty=MasterDataManager.getFaculty(educationData.get(i).getIdFaculty(), 38);
					com.topgun.shris.masterdata.Major major=MasterDataManager.getMajor(educationData.get(i).getIdFaculty(), educationData.get(i).getIdMajor(), 38);
					if(faculty!=null)
					{
						facultyDetail = propMgr.getMessage("th_TH", "EDU_FACULTY")+" "+faculty.getFacultyName();
					}
					if(major!=null)
					{
						majorDetail +=", "+propMgr.getMessage("th_TH", "EDU_MAJOR")+" "+major.getMajorName();
					}
					String data[] = new String[4];
					data[0] = Util.getStr(MasterDataManager.getDegree(educationData.get(i).getIdDegree(), 38).getDegreeName());
					data[1] = Util.getStr(facultyDetail);
					data[2] = Util.getStr(majorDetail);
					data[3] = Util.getStr(educationData.get(i).getIdEducation());
					educationListDetail.add(data);
				}
			}
			else if(service.equals("getEducation")){
				educationListDetail.clear();
				int idEducation = Util.getInt(request.getParameter("idEducation"));
				CheckUpEducation educationData = eduMgr.get(idUser, idEducation);
				if(educationData!=null){
					String data[] = new String[4];
					data[0] = Util.getStr(educationData.getIdDegree());
					data[1] = Util.getStr(educationData.getIdFaculty());
					data[2] = Util.getStr(educationData.getIdMajor());
					data[3] = Util.getStr(educationData.getIdEducation());
					educationListDetail.add(data);
				}
			}
			else if(service.equals("delete")){
				int idEducation = Util.getInt(request.getParameter("idEducation"),1);
				if(eduMgr.delete(idUser,idEducation)!=1){
					errors.add(propMgr.getMessage("th_TH", "EDU_UPDATE_ERROR"));
					elements.add("SYSTEM");	
				}
			}
			else{
				errors.add("ไม่พบหน้าที่คุณต้องการ");
				elements.add("SYSTEM");
			}
		}
        
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
		LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
		if(errors.size()>0){
			json.put("success",0);
			json.put("errors", errors);
			json.put("elements", elements);
			json.put("urlError", urlErrorRedirect);
		}
		else{
			json.put("success",1);
			json.put("educationListDetail", educationListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
	}
}
