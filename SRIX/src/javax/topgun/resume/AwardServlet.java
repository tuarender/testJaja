package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Award;
import com.topgun.resume.AwardManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class AwardServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Award> awardList = new ArrayList<Award>();
        ArrayList<String[]> awardListDetail = new ArrayList<String[]>();
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"),216);
		//int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"),38);
		//String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		int idLanguage=Util.getInt(request.getParameter("idLanguage"));
		String locale;
		if(idLanguage==11)
		{
			locale="en_TH";
		}
		else
		{
			locale="th_TH";
		}
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String urlErrorRedirect = "";
		String localeArray[] = locale.split("_");
		
		String service=Util.getStr(request.getParameter("service"));
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			if(service.equals("getAllAward")){
				awardList.clear();
				awardList = (ArrayList<Award>)new AwardManager().getAll(idJsk, idResume);
			}
			if(service.equals("getAwardList")){
				ArrayList<Award> awardsData = (ArrayList<Award>)new AwardManager().getAll(idJsk, idResume);
				for(int i=0;i<awardsData.size();i++){
					String data[] = new String[6];
					data[0] = Util.getStr(awardsData.get(i).getId());
					data[1] = awardsData.get(i).getAwardName();
					data[2] = awardsData.get(i).getInstitute();
					data[3] = Util.DateToStr(awardsData.get(i).getAwardDate(), "MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					data[4] = awardsData.get(i).getDetail();
					data[5] = Util.DateToStr(awardsData.get(i).getAwardDate(), "yyyy-MM");
					awardListDetail.add(data);
				}
			}
			else if(service.equals("getEditAward")){
				//System.out.println("service="+service);
				int idAward = Util.getInt(request.getParameter("idAward"), 0);
				Award awards = new AwardManager().get(idJsk, idResume, idAward);
				if(awards!=null){
					String data[] = new String[6];
					data[0] = Util.getStr(awards.getId());
					data[1] = awards.getAwardName();
					data[2] = awards.getInstitute();
					if(awards.getAwardDate()!=null)
					{
						data[3] = Util.DateToStr(awards.getAwardDate(), "MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					//data[3] = Util.DateToStr(awards.getAwardDate(), "MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					data[4] = awards.getDetail();
					if(awards.getAwardDate()!=null)
					{
						data[5] = Util.DateToStr(awards.getAwardDate(), "yyyy-MM");
					}
					//data[5] = Util.DateToStr(awards.getAwardDate(), "yyyy-MM");
					//System.out.println("date="+data[5]);
					awardListDetail.add(data);
				}
			}
			else if(service.equals("saveAward")){
				int idAward = Util.getInt(request.getParameter("idAward"), 0);
				String awardName = Util.getStr(request.getParameter("awardName"));
				String awardInstitution = Util.getStr(request.getParameter("awardInstitution"));
				//String awardDate = Util.getStr(request.getParameter("awardDate"));
				String awardDetail = Util.getStr(request.getParameter("awardDetail"));
				int startDay=1;
				int startMonth=Util.getInt(request.getParameter("startMonth"));
				int startYear=Util.getInt(request.getParameter("startYear"));      	
				Date dateStart=Util.getSQLDate(startDay, startMonth, startYear);
				
				if(awardName.equals("")){
					errors.add(propMgr.getMessage(locale, "AWARD_NAME_REQUIRED"));
					elements.add("awardName");
				}
				else if(awardInstitution.equals("")){
					errors.add(propMgr.getMessage(locale, "AWARD_INSTITUTION_REQUIRED"));
					elements.add("awardInstitution");
				}
				else if(dateStart.equals("")){
					errors.add(propMgr.getMessage(locale, "AWARD_DATE_REQUIRED"));
					elements.add("awardDate");
				}
				/*
				else if(awardDate.equals("")){
					errors.add(propMgr.getMessage(locale, "AWARD_DATE_REQUIRED"));
					elements.add("awardDate");
				}
				*/
				if(errors.size()==0){
					Award awards = new Award();
					awards.setIdJsk(idJsk);
					awards.setIdResume(idResume);
					awards.setAwardName(awardName);
					awards.setInstitute(awardInstitution);
					//awards.setAwardDate(Util.getSQLDate(awardDate, "yyyy-MM"));
					if(dateStart!=null)
		        	{
						awards.setAwardDate(dateStart);
		        	} 
					awards.setDetail(awardDetail);
					if(idAward>0){
						awards.setId(idAward);
						if(new AwardManager().update(awards)!=1){
							errors.add(propMgr.getMessage(locale, "AWARD_INSERT_ERROR"));
							elements.add("SYSTEM");
						}
					}
					else{
						idAward = new AwardManager().getNextId(idJsk, idResume);
						awards.setId(idAward);
						if(new AwardManager().add(awards)!=1){
							errors.add(propMgr.getMessage(locale, "AWARD_INSERT_ERROR"));
							elements.add("SYSTEM");
						}
					}
				}
			}
			else if(service.equals("deleteAward")){
				int idAward = Util.getInt(request.getParameter("idAward"), 0);
				Award awards = new AwardManager().get(idJsk, idResume, idAward);
				if(awards!=null){
					if(new AwardManager().delete(awards)!=1){
						errors.add(propMgr.getMessage(locale, "AWARD_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "AWARD_NOT_FOUND"));
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
			json.put("awardList", awardList);
			json.put("awardListDetail", awardListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nAwardsServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
		
	}
}
