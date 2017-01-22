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
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.Training;
import com.topgun.resume.TrainingManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class TrainingServlet  extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Training> trainingList = new ArrayList<Training>();
        ArrayList<String[]> trainingListDetail = new ArrayList<String[]>();
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"),216);
		//int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"),38);
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
		//String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
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
			if(service.equals("getAllTraining")){
				trainingList.clear();
				trainingList = (ArrayList<Training>)new TrainingManager().getAll(idJsk, idResume);
			}
			if(service.equals("getTrainingList"))
			{
				ArrayList<Training> trainingData = (ArrayList<Training>)new TrainingManager().getAll(idJsk, idResume);
				for(int i=0;i<trainingData.size();i++){
					String data[] = new String[8];
					data[0] = Util.getStr(trainingData.get(i).getId());
					data[1] = trainingData.get(i).getCourseName();
					data[2] = trainingData.get(i).getInstitute();
					if(trainingData.get(i).getStartDate()!=null)
					{
						data[3] = Util.DateToStr(trainingData.get(i).getStartDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					if(trainingData.get(i).getEndDate()!=null)
					{
						data[4] = Util.DateToStr(trainingData.get(i).getEndDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					data[5] = trainingData.get(i).getCourseDesc();
					if(trainingData.get(i).getStartDate()!=null)
					{
						data[6] = Util.DateToStr(trainingData.get(i).getStartDate(), "yyyy-MM-dd");
					}
					if(trainingData.get(i).getEndDate()!=null)
					{
						data[7] = Util.DateToStr(trainingData.get(i).getEndDate(), "yyyy-MM-dd");
					}
					trainingListDetail.add(data);
				}
			}
			else if(service.equals("getEditTraining")){
				int idTraining = Util.getInt(request.getParameter("idTraining"), 0);
				Training training = new TrainingManager().get(idJsk, idResume, idTraining);
				if(training!=null){
					String data[] = new String[8];
					data[0] = Util.getStr(training.getId());
					data[1] = training.getCourseName();
					data[2] = training.getInstitute();
					if(training.getStartDate()!=null)
					{
						data[3] = Util.DateToStr(training.getStartDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					if(training.getEndDate()!=null)
					{
						data[4] = Util.DateToStr(training.getEndDate(), "d MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					data[5] = training.getCourseDesc();
					if(training.getStartDate()!=null)
					{
						data[6] = Util.DateToStr(training.getStartDate(), "yyyy-MM-dd");
					}
					if(training.getEndDate()!=null)
					{
						data[7] = Util.DateToStr(training.getEndDate(), "yyyy-MM-dd");
					}
					trainingListDetail.add(data);
				}
			}
			else if(service.equals("saveTraining")){
				int idTraining = Util.getInt(request.getParameter("idTraining"), 0);
				String trainingName = Util.getStr(request.getParameter("trainingName"));
				String trainingInstitution = Util.getStr(request.getParameter("trainingInstitution"));
				int startDay = Util.getInt(request.getParameter("startDay"));
				int startMonth = Util.getInt(request.getParameter("startMonth"));
				int startYear = Util.getInt(request.getParameter("startYear"));
				int finishDay = Util.getInt(request.getParameter("finishDay"));
				int finishMonth = Util.getInt(request.getParameter("finishMonth"));
				int finishYear = Util.getInt(request.getParameter("finishYear"));
				
				String trainingDetail = Util.getStr(request.getParameter("trainingDetail"));
				Training training = new Training();
				training.setIdJsk(idJsk);
				training.setIdResume(idResume);
				training.setCourseName(trainingName);
				training.setInstitute(trainingInstitution);
				training.setStartDate(Util.getDate(startDay,startMonth,startYear));
				training.setEndDate(Util.getDate(finishDay,finishMonth,finishYear));
				training.setCourseDesc(trainingDetail);
				if(idTraining>0){
					training.setId(idTraining);
					if(new TrainingManager().update(training)!=1){
						errors.add(propMgr.getMessage(locale, "TRAINING_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					idTraining = new TrainingManager().getNextTrainingId(idJsk, idResume);
					training.setId(idTraining);
					if(new TrainingManager().add(training)!=1){
						errors.add(propMgr.getMessage(locale, "TRAINING_INSERT_ERROR"));
						elements.add("SYSTEM");
					}
				}
			}
			else if(service.equals("deleteTraining")){
				int idTraining = Util.getInt(request.getParameter("idTraining"), 0);
				Training training = new TrainingManager().get(idJsk, idResume, idTraining);
				if(training!=null){
					training.setId(idTraining);
					if(new TrainingManager().delete(training)!=1){
						errors.add(propMgr.getMessage(locale, "TRAINING_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "TRAINING_NOT_FOUND"));
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
			json.put("trainingList", trainingList);
			json.put("trainingListDetail", trainingListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nTrainingServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
		
	}

}
