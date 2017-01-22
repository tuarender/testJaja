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
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class CheckUpExperienceServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<String[]> experienceListDetail = new ArrayList<String[]>();
        PropertiesManager propMgr=new PropertiesManager();
        CheckUpExperienceManager expMgr=new CheckUpExperienceManager();
        String urlErrorRedirect = "";
        
        int idUser=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_USER"));
        if(idUser<=0){
			errors.add(propMgr.getMessage("th_TH", "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			String service=Util.getStr(request.getParameter("service"));
			if(service.equals("addExperience")){
				int idExperience = Util.getInt(request.getParameter("idExperience"));
				int expJobfield = Util.getInt(request.getParameter("expJobfield"),0); 
				int expYear = Util.getInt(request.getParameter("expYear"),0);
				int expMonth = Util.getInt(request.getParameter("expMonth"),0);
				
				if (expJobfield == 0){
					errors.add("โปรดระบุ สายงานที่มีประสบการณ์");
					elements.add("expJobfield");	
				}
				
				if(errors.size()==0){
					boolean isExpExist = true;
					CheckUpExperience chkExp = expMgr.get(idUser, idExperience);
					
					if(chkExp==null){
						chkExp = new CheckUpExperience();
						chkExp.setIdUser(idUser);
						chkExp.setIdExp(idExperience);
						isExpExist = false;
					}
					chkExp.setIdJobfield(expJobfield);
					chkExp.setYear(expYear);
					chkExp.setMonth(expMonth);
					if(isExpExist){
						//------------UPDATE---------------
						if(expMgr.update(chkExp)!=1){
							errors.add("ระบบขัดข้อง ไม่สามารถแก้ไขข้อมูลประสบการ์ณการทำงานได้ในขณะนี้ ขออภัยในความไม่สะดวก");
							elements.add("SYSTEM");
						}
					}
					else{
						//------------INSERT---------------
						if(expMgr.add(chkExp)!=1){
							errors.add("ระบบขัดข้อง ไม่สามารถบันทึกข้อมูลประสบการ์ณการทำงานได้ในขณะนี้ ขออภัยในความไม่สะดวก");
							elements.add("SYSTEM");
						}
					}
				}
			}
			else if(service.equals("getExperienceList")){
				ArrayList<CheckUpExperience> experienceData = (ArrayList<CheckUpExperience>)expMgr.getByIdUser(idUser);
				for(int i=0;i<experienceData.size();i++){
					String jobfieldDetail = "";
					com.topgun.shris.masterdata.JobField jobField=MasterDataManager.getJobField(experienceData.get(i).getIdJobfield(), 38);
					if(jobField!=null)
					{
						jobfieldDetail = "สายงาน "+jobField.getFieldName();
					}
					String data[] = new String[4];
					data[0] = Util.getStr(jobfieldDetail);
					data[1] = Util.getStr(experienceData.get(i).getYear());
					data[2] = Util.getStr(experienceData.get(i).getMonth());
					data[3] = Util.getStr(experienceData.get(i).getIdExp());
					experienceListDetail.add(data);
				}
				
			}
			else if(service.equals("getExperience")){
				experienceListDetail.clear();
				int idExperience = Util.getInt(request.getParameter("idExperience"));
				CheckUpExperience experienceData = expMgr.get(idUser, idExperience);
				if(experienceData!=null){
					String data[] = new String[4];
					data[0] = Util.getStr(experienceData.getIdJobfield());
					data[1] = Util.getStr(experienceData.getYear());
					data[2] = Util.getStr(experienceData.getMonth());
					data[3] = Util.getStr(experienceData.getIdExp());
					experienceListDetail.add(data);
				}
			}
			else if(service.equals("delete")){
				int idExperience = Util.getInt(request.getParameter("idExperience"),-1);
				if(idExperience>0){
					if(expMgr.delete(idUser,idExperience)!=1){
						errors.add(propMgr.getMessage("th_TH", "ระบบขัดข้อง ไม่สามารถลบข้อมูลประสบการ์ณการทำงานได้ในขณะนี้ ขออภัยในความไม่สะดวก"));
						elements.add("SYSTEM");	
					}
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
			json.put("experienceListDetail", experienceListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
	}
}
