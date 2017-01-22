package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Additional;
import com.topgun.resume.AdditionalManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class AdditionalServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        Additional additionalData = null;
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"),216);
		int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"),38);
		String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String urlErrorRedirect = "";
		
		String service=Util.getStr(request.getParameter("service"));
		System.out.println(service);
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			if(service.equals("getEditAdditional")){
				additionalData = new AdditionalManager().get(idJsk, idResume);
			}
			else if(service.equals("saveAdditional")){
				String idCard = Util.getStr(request.getParameter("idCard"));
				String canDrive = Util.getStr(request.getParameter("canDrive"));
				String canDriveId = Util.getStr(request.getParameter("canDriveId"));
				String handicap = Util.getStr(request.getParameter("handicap"));
				String handicapReason = Util.getStr(request.getParameter("handicapReason"));
				String illness = Util.getStr(request.getParameter("illness"));
				String illnessReason = Util.getStr(request.getParameter("illnessReason"));
				String fire = Util.getStr(request.getParameter("fire"));
				String fireReason = Util.getStr(request.getParameter("fireReason"));
				String criminal = Util.getStr(request.getParameter("criminal"));
				String criminalReason = Util.getStr(request.getParameter("criminalReason"));
				String military = Util.getStr(request.getParameter("military"));
				Additional additional = new AdditionalManager().get(idJsk, idResume);
				if(additional!=null){
					additional.setIdCard(idCard);
					additional.setDriveStatus(canDrive);
					additional.setIdDrive(canDriveId);
					additional.setHandicapStatus(handicap);
					additional.setHandicapReason(handicapReason);
					additional.setIllnessStatus(illness);
					additional.setIllnessReason(illnessReason);
					additional.setFireStatus(fire);
					additional.setFireReason(fireReason);
					additional.setCriminalStatus(criminal);
					additional.setCriminalReason(criminalReason);
					additional.setMilitaryStatus(military);
					if(new AdditionalManager().update(additional)!=1){
						errors.add(propMgr.getMessage(locale, "ADDITIONAL_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					additional = new Additional();
					additional.setIdJsk(idJsk);
					additional.setIdResume(idResume);
					additional.setIdCard(idCard);
					additional.setDriveStatus(canDrive);
					additional.setIdDrive(canDriveId);
					additional.setHandicapStatus(handicap);
					additional.setHandicapReason(handicapReason);
					additional.setIllnessStatus(illness);
					additional.setIllnessReason(illnessReason);
					additional.setFireStatus(fire);
					additional.setFireReason(fireReason);
					additional.setCriminalStatus(criminal);
					additional.setCriminalReason(criminalReason);
					additional.setMilitaryStatus(military);
					if(new AdditionalManager().add(additional)!=1){
						errors.add(propMgr.getMessage(locale, "ADDITIONAL_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
			}
			else if(service.equals("deleteAdditional")){
				Additional additional = new AdditionalManager().get(idJsk, idResume);
				if(additional!=null){
					if(new AdditionalManager().delete(additional)!=1){
						errors.add(propMgr.getMessage(locale, "ADDITIONAL_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "ADDITIONAL_NOT_FOUND"));
					elements.add("SYSTEM");
				}
			}
			else{
				errors.add(propMgr.getMessage(locale, "STRENGTH_SERVICE_REQUIRE"));
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
			json.put("additional", additionalData);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nAdditionalServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
	}
}
