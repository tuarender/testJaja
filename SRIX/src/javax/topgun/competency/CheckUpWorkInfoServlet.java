package javax.topgun.competency;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Training;
import com.topgun.resume.TrainingManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class CheckUpWorkInfoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        CheckUpStrengthManager strMgr=new CheckUpStrengthManager();
        CheckUpTargetJobsManager targetJobsMgr=new CheckUpTargetJobsManager();
        ArrayList<String[]> strengthListDetail = new ArrayList<String[]>();
        ArrayList<String[]> targetJobsListDetail = new ArrayList<String[]>();
        String urlErrorRedirect = "";
        
        int idUser=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_USER"));
        if(idUser<=0){
			errors.add(propMgr.getMessage("th_TH", "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			String service=Util.getStr(request.getParameter("service"));
			if(service.equals("addWorkInfo")){
				int strength_1 = Util.getInt(request.getParameter("strength_1"));
				int strength_2 = Util.getInt(request.getParameter("strength_2"));
				int strength_3 = Util.getInt(request.getParameter("strength_3"));
				int strength_4 = Util.getInt(request.getParameter("strength_4"));
				int strength_5 = Util.getInt(request.getParameter("strength_5"));
				int jobfield_1 = Util.getInt(request.getParameter("jobfield_1"));
				int jobfield_2 = Util.getInt(request.getParameter("jobfield_2"));
				int jobfield_3 = Util.getInt(request.getParameter("jobfield_3"));
				
				if (strength_1<=0||strength_2<=0||strength_3<=0||strength_4<=0||strength_5<=0){
					errors.add("โปรดระบุจุดแข็งให้ครบถ้วน");
					elements.add("strength_1");	
				}
				else if(jobfield_1<=0){
					errors.add("โปรดระบุสายงานที่คุณต้องการอย่างน้อย 1 สายงาน");
					elements.add("jobfield_1");	
				}
				
				if(errors.size()==0){
					//-----------------STRENGTH--------------------
					
					int idStrength[] = new int[5];
					idStrength[0] = strength_1;
					idStrength[1] = strength_2;
					idStrength[2] = strength_3;
					idStrength[3] = strength_4;
					idStrength[4] = strength_5;
					
					strMgr.delete(idUser);
					for(int i=0;i<idStrength.length;i++){
						CheckUpStrength chkStr = new CheckUpStrength();
						chkStr.setIdUser(idUser);
						chkStr.setIdStrength(idStrength[i]);
						chkStr.setIdStrengthOrder(i+1);
						if(strMgr.add(chkStr)!=1){
							errors.add("ระบบขัดข้อง ไม่สามารถบันทึกข้อมูลจุดแข็ง ("+(i+1)+") ได้ในขณะนี้ ขออภัยในความไม่สะดวก");
							elements.add("SYSTEM");
						}
					}
					
					//-----------------TARGET JOB--------------------
					
					int idJobField[] = new int[3];
					idJobField[0] = jobfield_1;
					idJobField[1] = jobfield_2;
					idJobField[2] = jobfield_3;
					
					targetJobsMgr.delete(idUser);
					
					for(int i=0;i<idJobField.length;i++){
						CheckUpTargetJobs chkTargetJob = new CheckUpTargetJobs();
						chkTargetJob.setIdUser(idUser);
						chkTargetJob.setIdJobfield(idJobField[i]);
						chkTargetJob.setIdJobfieldOrder(i+1);
						if(targetJobsMgr.add(chkTargetJob)!=1){
							errors.add("ระบบขัดข้อง ไม่สามารถบันทึกข้อมูลสายงานที่คุณต้องการ ("+(i+1)+") ได้ในขณะนี้ ขออภัยในความไม่สะดวก");
							elements.add("SYSTEM");
						}
					}
				}
			}
			else if(service.equals("getStrengthList")){
				ArrayList<CheckUpStrength> chkStrengthJob = (ArrayList<CheckUpStrength>)strMgr.getByIdUser(idUser);
				for(int i=0;i<chkStrengthJob.size();i++){
					String data[] = new String[3];
					data[0] = Util.getStr(chkStrengthJob.get(i).getIdUser());
					data[1] =  Util.getStr(chkStrengthJob.get(i).getIdStrength());
					data[2] =  Util.getStr(chkStrengthJob.get(i).getIdStrengthOrder());
					strengthListDetail.add(data);
				}
			}
			else if(service.equals("getTargetJobsList")){
				ArrayList<CheckUpTargetJobs> chkTargetJob = (ArrayList<CheckUpTargetJobs>)targetJobsMgr.getByIdUser(idUser);
				for(int i=0;i<chkTargetJob.size();i++){
					String data[] = new String[3];
					data[0] = Util.getStr(chkTargetJob.get(i).getIdUser());
					data[1] =  Util.getStr(chkTargetJob.get(i).getIdJobfield());
					data[2] =  Util.getStr(chkTargetJob.get(i).getIdJobfieldOrder());
					targetJobsListDetail.add(data);
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
			json.put("strengthListDetail",strengthListDetail);
			json.put("targetJobsListDetail",targetJobsListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
	}
}
