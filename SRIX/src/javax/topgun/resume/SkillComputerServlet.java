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
import com.topgun.resume.CareerObjectiveManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.SkillComputer;
import com.topgun.resume.SkillComputerManager;
import com.topgun.resume.SkillOther;
import com.topgun.resume.SkillOtherManager;
import com.topgun.shris.masterdata.Computer;
import com.topgun.shris.masterdata.ComputerGroup;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;


public class SkillComputerServlet extends HttpServlet {
	
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
        String locale=Util.getStr(request.getParameter("locale"));
		if(locale.equals("")){
			locale=Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"));
		}
		if(locale.equals("")){
			locale="th_TH";
		}
        String service=Util.getStr(request.getParameter("service"));
        Resume resume=new ResumeManager().get(idJsk, idResume);
        if(resume!=null)
        {
        	idLanguage = resume.getIdLanguage();
        	locale = resume.getLocale();
        }
        if(resume==null)
        {
        	locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale,"GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
        else if(service.equals("skillComputer"))
        {
        	int idGroupComputer = Util.getInt(request.getParameter("idGroupComputer"));
        	List<Computer> comList = MasterDataManager.getAllComputer(idGroupComputer);
        	if(comList.size()!=0)
        	{
        		json.put("comList",comList);
        	}
        	else
        	{
        		errors.add(propMgr.getMessage(locale, "INSERT_LOCATION_ERROR"));
				elements.add("SYSTEM");	
        	}
        }
        else if(service.equals("addSkillComputer"))
        {
        	int idGroupComputer = Util.getInt(request.getParameter("idGroupComputer"),1);
        	int idSkillComputer = Util.getInt(request.getParameter("idSkillComputer"),1);
        	int level_skillComputer = Util.getInt(request.getParameter("level_skillComputer"),1);
        	String otherCom = Util.getStr(request.getParameter("otherCom"),"");
        	SkillComputer sc = new SkillComputer();
        	sc.setIdJsk(idJsk);
        	sc.setIdResume(idResume);
        	sc.setIdGroup(idGroupComputer);
        	if(idGroupComputer==-1){
        		sc.setOthComputer(otherCom);
        	}
        	if(idSkillComputer==-1){
        		sc.setOthComputer(otherCom);
        		int min = new SkillComputerManager().getMinIdSkillComputer(idJsk, idResume);
        		if(min<0){
        			min = min-1;
        		}else{
        			min = -1;
        		}
        		idSkillComputer = min;
        	}
        	sc.setIdComputer(idSkillComputer);
        	sc.setLevelSkill(level_skillComputer);
        	if(new SkillComputerManager().chkSkillComputer(idJsk, idResume, idGroupComputer, idSkillComputer)==1)
        	{
        		errors.add(propMgr.getMessage(locale,"SKILL_COMPUTER_EXIST"));
				elements.add("SYSTEM");	
        	}
        	else 
        	{
        		int fromApply = Util.getInt(request.getParameter("fromApply"));
            	if(fromApply==1)
            	{
            		List<SkillComputer> scList = new SkillComputerManager().getAll(idJsk, idResume);
            		if(scList.size()==0)
            		{
            			new SkillComputerManager().addSkillComLog(idJsk, idResume);
            		}
            	}
        		if(new SkillComputerManager().add(sc)!=1){
	        		errors.add(propMgr.getMessage(locale,"SKILL_COMPUTER_EXIST"));
					elements.add("SYSTEM");
        		}
        	}
        }
        else if(service.equals("getSkillComputer"))
        {
        	List<SkillComputer> scList = new SkillComputerManager().getAll(idJsk, idResume);
        	List<Integer> idGroupComputer = new ArrayList<Integer>();
        	List<Integer> idSkillComputer = new ArrayList<Integer>();
        	List<String> groupName = new ArrayList<String>();
        	List<String> skillName = new ArrayList<String>();
        	List<String> levelType = new ArrayList<String>();
        	for(int i=0;i<scList.size();i++)
        	{
        		SkillComputer sc = scList.get(i);
        		String gName = "";
        		String cName = "";
        		String sName = "";
        		com.topgun.shris.masterdata.SkillLevel skillLevel = MasterDataManager.getSkillLevel(sc.getLevelSkill(), idLanguage);
        		if(sc.getLevelSkill()==1)
        		{
        			sName = propMgr.getMessage(locale,"SKILL_COM_LEARNING");
        		}else if(sc.getLevelSkill()==2){
        			sName = propMgr.getMessage(locale,"SKILL_COM_BEGINNER");
        		}else if(sc.getLevelSkill()==3){
        			sName = propMgr.getMessage(locale,"SKILL_COM_INTERMEDIATE");
        		}else if(sc.getLevelSkill()==4){
        			sName = propMgr.getMessage(locale,"SKILL_COM_ADVANCED");
        		}else if(sc.getLevelSkill()==5){
        			sName = propMgr.getMessage(locale,"SKILL_COM_EXPERT");
        		}else{
        			sName = "None";
        		}
        		if(sc.getIdComputer()>0){
	        		Computer computer = MasterDataManager.getComputer(sc.getIdGroup(), sc.getIdComputer());
	        		ComputerGroup computerGroup = MasterDataManager.getComputerGroup(sc.getIdGroup(), idLanguage);
	        		gName = Util.getStr(computerGroup.getGroupName());
	        		cName = Util.getStr(computer.getComputerName());
        		}else{
	        		ComputerGroup computerGroup = MasterDataManager.getComputerGroup(sc.getIdGroup(), idLanguage);
	        		gName = Util.getStr(computerGroup.getGroupName());
	        		cName = Util.getStr(sc.getOthComputer());
        		}
        		idGroupComputer.add(sc.getIdGroup());
        		idSkillComputer.add(sc.getIdComputer());
        		groupName.add(gName);
        		skillName.add(cName);
        		levelType.add(sName);
        	}
        	json.put("idGroupComputer", idGroupComputer);
        	json.put("idSkillComputer", idSkillComputer);
        	json.put("groupName", groupName);
        	json.put("skillName", skillName);
        	json.put("levelType", levelType);
        }
        else if(service.equals("delSkillComputer"))
        {
        	int idSkillComputer = Util.getInt(request.getParameter("idSkillComputer"));
        	SkillComputer sc = new SkillComputer();
        	sc.setIdJsk(idJsk);
        	sc.setIdResume(idResume);
        	sc.setIdComputer(idSkillComputer);
        	if(new SkillComputerManager().delete(sc)!=1)
        	{
        		errors.add(propMgr.getMessage(locale, "INSERT_LOCATION_ERROR"));
				elements.add("SYSTEM");	
        	}
        }
        else if(service.equals("addSkillOther"))
        {
        	String skillOther = Util.getStr(request.getParameter("skillOther"));
        	int level_skillOther = Util.getInt(request.getParameter("level_skillOther"));
        	SkillOther so = new SkillOther();
        	int nextId = new SkillOtherManager().getNextId(idJsk, idResume);
        	so.setIdJsk(idJsk);
        	so.setIdResume(idResume);
        	so.setId(nextId);
        	so.setLevelSkill(level_skillOther);
        	so.setSkillName(skillOther);
        	if(new SkillOtherManager().add(so)!=1)
        	{
        		errors.add(propMgr.getMessage(locale, "INSERT_LOCATION_ERROR"));
				elements.add("SYSTEM");	
        	}
        }
        else if(service.equals("getSkillOther"))
        {
        	List<SkillOther> soList = new SkillOtherManager().getAll(idJsk, idResume);
        	List<String> skillOther = new ArrayList<String>();
        	List<String> levelType = new ArrayList<String>();
        	List<Integer> idSkillOther = new ArrayList<Integer>();
        	for(int i =0;i<soList.size();i++){
        		SkillOther so = soList.get(i);
        		skillOther.add(so.getSkillName());
        		idSkillOther.add(so.getId());
        		String skillName = "" ;
        		if(so.getLevelSkill()==1)
        		{
        			skillName = propMgr.getMessage(locale,"SKILL_COM_LEARNING");
        		}else if(so.getLevelSkill()==2){
        			skillName = propMgr.getMessage(locale,"SKILL_COM_BEGINNER");
        		}else if(so.getLevelSkill()==3){
        			skillName = propMgr.getMessage(locale,"SKILL_COM_INTERMEDIATE");
        		}else if(so.getLevelSkill()==4){
        			skillName = propMgr.getMessage(locale,"SKILL_COM_ADVANCED");
        		}else if(so.getLevelSkill()==5){
        			skillName = propMgr.getMessage(locale,"SKILL_COM_EXPERT");
        		}else{
        			skillName = "None";
        		}
        		levelType.add(skillName);
        	}
        	json.put("skillOther", skillOther);
        	json.put("levelType", levelType);
        	json.put("idSkillOther", idSkillOther);
        }
        else if(service.equals("delSkillOther"))
        {
        	int idSkillOther = Util.getInt(request.getParameter("idSkillOther"));
        	SkillOther so = new SkillOther();
        	so.setIdJsk(idJsk);
        	so.setIdResume(idResume);
        	so.setId(idSkillOther);
        	if(new SkillOtherManager().delete(so)!=1){
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
