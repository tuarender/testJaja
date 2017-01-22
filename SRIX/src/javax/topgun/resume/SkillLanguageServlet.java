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
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.SkillComputerManager;
import com.topgun.resume.SkillLanguage;
import com.topgun.resume.SkillLanguageManager;
import com.topgun.resume.SkillLanguageScore;
import com.topgun.resume.SkillLanguageScoreManager;
import com.topgun.shris.masterdata.Language;
import com.topgun.shris.masterdata.LanguageLevel;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.Encoder;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;


public class SkillLanguageServlet extends HttpServlet {
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Gson gson = new Gson();
	        ArrayList<String> errors = new ArrayList<String>();
	        ArrayList<String> elements = new ArrayList<String>();
	        PropertiesManager propMgr=new PropertiesManager();
	        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
	        int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"));
	        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
	        int idResume=Util.getInt(request.getParameter("idResume"));
	        String locale = "";
	        String service=Util.getStr(request.getParameter("service"));
	        Resume resume=new ResumeManager().get(idJsk, idResume);
	        if(resume!=null)
	        {
	        	idLanguage = resume.getIdLanguage();
	        }
	        if(resume==null)
	        {
	        	locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
	        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
				elements.add("SYSTEM");	
	        }
	        else if(service.equals("addSkillLanguage"))
	        {
	        	int idSkillLanguage = Util.getInt(request.getParameter("idLanguage"),1);
	        	int language_listen = Util.getInt(request.getParameter("language_listen"),1);
	        	int language_spaek = Util.getInt(request.getParameter("language_spaek"),1);
	        	int language_read = Util.getInt(request.getParameter("language_read"),1);
	        	int language_write = Util.getInt(request.getParameter("language_write"),1);
	        	SkillLanguage sl = new SkillLanguage(); 
	        	sl.setIdJsk(idJsk);
	        	sl.setIdResume(idResume);
	        	sl.setIdSkillLang(idSkillLanguage);
	        	sl.setListening(language_listen);
	        	sl.setSpeaking(language_spaek);
	        	sl.setReading(language_read);
	        	sl.setWriting(language_write);
	        	if(new SkillLanguageManager().add(sl)!=1)
	        	{
	        		errors.add(propMgr.getMessage(locale, "INSERT_LOCATION_ERROR"));
					elements.add("SYSTEM");	
	        	}
	        }
	        else if(service.equals("getSkillLanguage"))
	        {
	        	List<SkillLanguage> slList = new SkillLanguageManager().getAll(idJsk, idResume);
	        	List<String> languageName = new ArrayList<String>();
	        	List<Integer> idSkillLanguage = new ArrayList<Integer>();
	        	List<String> listen = new ArrayList<String>();
	        	List<String> spaek = new ArrayList<String>();
	        	List<String> read = new ArrayList<String>();
	        	List<String> write = new ArrayList<String>();
	        	
	        	for(int i=0;i<slList.size();i++){
	        		SkillLanguage sl = slList.get(i);
	        		Language lg = MasterDataManager.getLanguage(idLanguage,sl.getIdSkillLang());
	        		languageName.add(lg.getName());
	        		idSkillLanguage.add(lg.getId());
	        		LanguageLevel listenLevel = MasterDataManager.getLanguageLevel(sl.getListening(), idLanguage);
	        		LanguageLevel spaekLevel = MasterDataManager.getLanguageLevel(sl.getSpeaking(), idLanguage);
	        		LanguageLevel readLevel = MasterDataManager.getLanguageLevel(sl.getReading(), idLanguage);
	        		LanguageLevel writeLevel = MasterDataManager.getLanguageLevel(sl.getWriting(), idLanguage);
	        		listen.add(listenLevel.getName());
	        		spaek.add(spaekLevel.getName());
	        		read.add(readLevel.getName());
	        		write.add(writeLevel.getName());
	        	}
	        	json.put("languageName", languageName);
        		json.put("idSkillLanguage", idSkillLanguage);
        		json.put("listen", listen);
        		json.put("spaek", spaek);
        		json.put("read", read);
        		json.put("write", write);
	        	
	        }
	        else if(service.equals("delSkillLanguage"))
	        {
	        	int idSkillLanguage = Util.getInt(request.getParameter("idSkillLanguage"));
	        	SkillLanguage sl = new SkillLanguage();
	        	sl.setIdJsk(idJsk);
	        	sl.setIdResume(idResume);
	        	sl.setIdSkillLang(idSkillLanguage);
	        	if(new SkillLanguageManager().delete(sl)!=1)
	        	{
	        		errors.add(propMgr.getMessage(locale, "INSERT_LOCATION_ERROR"));
					elements.add("SYSTEM");	
	        	}
	        }
	        else if(service.equals("addTest"))
	        {
	        	String typeTest = Util.getStr(request.getParameter("idTest"));
	        	float score_listen = Util.getFloat(request.getParameter("score_listen"));
	        	float score_spaek = Util.getFloat(request.getParameter("score_spaek"));
	        	float score_read = Util.getFloat(request.getParameter("score_read"));
	        	float score_write = Util.getFloat(request.getParameter("score_write"));
	        	float total = score_listen+score_spaek+score_read+score_write;
	        	SkillLanguageScore sls = new SkillLanguageScore();
	        	sls.setIdJsk(idJsk);
	        	sls.setIdResume(idResume);
	        	if(typeTest.equals("other")){
	        		String otherTest = Util.getStr(request.getParameter("otherTest"));
	        		sls.setExamType(otherTest);
	        	}else
	        	{
	        		sls.setExamType(typeTest);
	        	}
	        	sls.setListening(score_listen);
	        	sls.setSpeaking(score_spaek);
	        	sls.setReading(score_read);
	        	sls.setWriting(score_write);
	        	sls.setTotal(total);
	        	if(score_listen!=0||score_spaek!=0||score_read!=0||score_write!=0)
	        	{
		        	if(new SkillLanguageScoreManager().add(sls)!=1)
		        	{
		        		errors.add(propMgr.getMessage(locale, "INSERT_LOCATION_ERROR"));
						elements.add("SYSTEM");	
		        	}
	        	}
	        	
	        }
	        else if(service.equals("getTest"))
	        {
	        	List<SkillLanguageScore> slsList = new SkillLanguageScoreManager().getAll(idJsk, idResume);
	        	List<String> testName = new ArrayList<String>();
	        	List<Float> listen = new ArrayList<Float>();
	        	List<Float> spaek = new ArrayList<Float>();
	        	List<Float> read = new ArrayList<Float>();
	        	List<Float> write = new ArrayList<Float>();
	        	for(int i=0;i<slsList.size();i++)
	        	{
	        		SkillLanguageScore sls = slsList.get(i);
	        		testName.add(sls.getExamType());
	        		listen.add(sls.getListening());
	        		spaek.add(sls.getSpeaking());
	        		read.add(sls.getReading());
	        		write.add(sls.getWriting());
	        		
	        	}
	        	json.put("testName", testName);
	        	json.put("listen", listen);
        		json.put("spaek", spaek);
        		json.put("read", read);
        		json.put("write", write);
	        }
	        else if(service.equals("delTest"))
	        {
	        	String testName = Util.getStr(request.getParameter("testName"));
	        	SkillLanguageScore sls = new SkillLanguageScore();
	        	sls.setIdJsk(idJsk);
	        	sls.setIdResume(idResume);
	        	sls.setExamType(testName);
	        	if(new SkillLanguageScoreManager().delete(sls)!=1)
	        	{
	        		errors.add(propMgr.getMessage(locale, "INSERT_LOCATION_ERROR"));
					elements.add("SYSTEM");	
	        	}
	        }
	        response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			response.setContentType("text/plain");
	        response.setHeader("Cache-control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Expires", "-1");
	        
			if(errors.size()>0)
			{
				json.put("success",0);
				json.put("errors", errors);
				json.put("elements", elements);
			}
			else
			{
				json.put("success",1);			
			}
			out.print(gson.toJson(json));
			out.close();
	}

}
