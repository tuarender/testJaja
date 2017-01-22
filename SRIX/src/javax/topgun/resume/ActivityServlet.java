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
import com.topgun.resume.Activity;
import com.topgun.resume.ActivityManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class ActivityServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Activity> activityList = new ArrayList<Activity>();
        ArrayList<String[]> activityListDetail = new ArrayList<String[]>();
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
		System.out.println(service);
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			if(service.equals("getAllActivity")){
				activityList.clear();
				activityList = (ArrayList<Activity>)new ActivityManager().getAll(idJsk, idResume);
			}
			if(service.equals("getActivityList")){
				ArrayList<Activity> activityData = (ArrayList<Activity>)new ActivityManager().getAll(idJsk, idResume);
				for(int i=0;i<activityData.size();i++){
					String data[] = new String[8];
					data[0] = Util.getStr(activityData.get(i).getId());
					data[1] = activityData.get(i).getClub();
					data[2] = activityData.get(i).getPosition();
					data[3] = Util.DateToStr(activityData.get(i).getStartDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					data[4] = Util.DateToStr(activityData.get(i).getEndDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					data[5] = Util.getStr(activityData.get(i).getDescription());
					data[6] = Util.DateToStr(activityData.get(i).getStartDate(), "yyyy-MM-dd");
					data[7] = Util.DateToStr(activityData.get(i).getEndDate(), "yyyy-MM-dd");
					activityListDetail.add(data);
				}
			}
			else if(service.equals("getEditActivity")){
				int idActivity = Util.getInt(request.getParameter("idActivity"), 0);
				Activity activity = new ActivityManager().get(idJsk, idResume, idActivity);
				if(activity!=null){
					String data[] = new String[8];
					data[0] = Util.getStr(activity.getId());
					data[1] = activity.getClub();
					data[2] = activity.getPosition();
					if(activity.getStartDate()!=null)
					{
						data[3] = Util.DateToStr(activity.getStartDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					if(activity.getEndDate()!=null)
					{
						data[4] = Util.DateToStr(activity.getEndDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					//data[3] = Util.DateToStr(activity.getStartDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					//data[4] = Util.DateToStr(activity.getEndDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					data[5] = Util.getStr(activity.getDescription());
					if(activity.getStartDate()!=null)
					{
						data[6] = Util.DateToStr(activity.getStartDate(), "yyyy-MM-dd");
					}
					if(activity.getEndDate()!=null)
					{
						data[7] = Util.DateToStr(activity.getEndDate(), "yyyy-MM-dd");
					}
					//data[6] = Util.DateToStr(activity.getStartDate(), "yyyy-MM-dd");
					//data[7] = Util.DateToStr(activity.getEndDate(), "yyyy-MM-dd");
					activityListDetail.add(data);
				}
			}
			else if(service.equals("saveActivity")){
				int idActivity = Util.getInt(request.getParameter("idActivity"), 0);
				String activityClub = Util.getStr(request.getParameter("activityClub"));
				String activityPosition = Util.getStr(request.getParameter("activityPosition"));
				//String activityStart = Util.getStr(request.getParameter("activityStart"));
				//String activityFinish = Util.getStr(request.getParameter("activityFinish"));
				int startDay=Util.getInt(request.getParameter("startDay"));
				int startMonth=Util.getInt(request.getParameter("startMonth"));
				int startYear=Util.getInt(request.getParameter("startYear"));      	
				Date dateStart=Util.getSQLDate(startDay, startMonth, startYear);
				
				int finishDay=Util.getInt(request.getParameter("finishDay"));
				int finishMonth=Util.getInt(request.getParameter("finishMonth"));
				int finishYear=Util.getInt(request.getParameter("finishYear"));      	
				Date dateFinish=Util.getSQLDate(finishDay, finishMonth, finishYear);
				
				String activityDetail = Util.getStr(request.getParameter("activityDetail"));
				Activity activity = new Activity();
				activity.setIdJsk(idJsk);
				activity.setIdResume(idResume);
				activity.setClub(activityClub);
				activity.setPosition(activityPosition);
				activity.setStartDate(dateStart);
				activity.setEndDate(dateFinish);
				//activity.setStartDate(Util.getSQLDate(activityStart, "yyyy-MM-dd"));
				//activity.setEndDate(Util.getSQLDate(activityFinish, "yyyy-MM-dd"));
				activity.setDescription(activityDetail);
				if(idActivity>0){
					activity.setId(idActivity);
					if(new ActivityManager().update(activity)!=1){
						errors.add(propMgr.getMessage(locale, "ACTIVITY_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					idActivity = new ActivityManager().getNextActivityId(idJsk, idResume);
					activity.setId(idActivity);
					if(new ActivityManager().add(activity)!=1){
						errors.add(propMgr.getMessage(locale, "ACTIVITY_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
			}
			else if(service.equals("deleteActivity")){
				int idActivity = Util.getInt(request.getParameter("idActivity"), 0);
				Activity activity = new ActivityManager().get(idJsk, idResume, idActivity);
				if(activity!=null){
					activity.setId(idActivity);
					if(new ActivityManager().delete(activity)!=1){
						errors.add(propMgr.getMessage(locale, "ACTIVITY_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "ACTIVITY_NOT_FOUND"));
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
			json.put("activityList", activityList);
			json.put("activityListDetail", activityListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nActivityServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
	}
}
