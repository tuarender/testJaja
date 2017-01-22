package javax.topgun.resume;

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
import com.topgun.resume.AdditionalInfo;
import com.topgun.resume.AdditionalInfoManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class AdditionalInfoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        String additionalData = "";
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"),216);
		int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"),38);
		String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String urlErrorRedirect = "";
		
		String service=Util.getStr(request.getParameter("service"));
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			if(service.equals("getEditAdditional")){
				AdditionalInfo additionalInfo = new AdditionalInfoManager().get(idJsk, idResume);
				if(additionalInfo!=null){
					additionalData = Util.getStr(additionalInfo.getAdditional1());
				}
			}
			else if(service.equals("saveAdditionalInfo")){
				String additionalInfoData = Util.getStr(request.getParameter("additionalInfo"));
				AdditionalInfo additionalInfo = new AdditionalInfoManager().get(idJsk, idResume);
				if(additionalInfo!=null){
					additionalInfo.setAdditional1(additionalInfoData);
					if(new AdditionalInfoManager().update(additionalInfo)!=1){
						errors.add(propMgr.getMessage(locale, "ADDITIONAL_INFO_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					additionalInfo = new AdditionalInfo();
					additionalInfo.setIdJsk(idJsk);
					additionalInfo.setIdResume(idResume);
					additionalInfo.setAdditional1(additionalInfoData);
					if(new AdditionalInfoManager().add(additionalInfo)!=1){
						errors.add(propMgr.getMessage(locale, "ADDITIONAL_INFO_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
			}
			else if(service.equals("deleteAdditional")){
				AdditionalInfo additionalInfo = new AdditionalInfoManager().get(idJsk, idResume);
				if(additionalInfo!=null){
					if(new AdditionalInfoManager().delete(additionalInfo)!=1){
						errors.add(propMgr.getMessage(locale, "ADDITIONAL_INFO_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "ADDITIONAL_INFO_NOT_FOUND"));
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
			json.put("additionalInfo", additionalData);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nAdditionalServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
	}

}
