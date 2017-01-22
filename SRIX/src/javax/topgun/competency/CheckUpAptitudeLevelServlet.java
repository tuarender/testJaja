package javax.topgun.competency;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.google.gson.Gson;

public class CheckUpAptitudeLevelServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{       
        Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
        
        int idUser=Util.getInt(request.getSession().getAttribute("SESSION_ID_USER"));
		int idLanguage=38;
        String service=Util.getStr(request.getParameter("service"));
        if(service.equals("view"))
		{
			List<CheckUpHobby> hobies=new CheckUpHobbyManager().getAllForLevel(idUser);
			String hobiesName[]=new String[hobies.size()];
			if(hobies.size()>0)
			{
				for(int i=0; i<hobies.size(); i++)
				{
					com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobies.get(i).getIdHobby(), idLanguage);
					if(hobby!=null)
					{
						hobiesName[i]=hobby.getHobbyName();
					}
					else
					{
						hobiesName[i]=hobies.get(i).getOthHobby();
					}
					
				}
				json.put("hobies",hobies);
				json.put("hobiesName",hobiesName);
			}
			CheckUpUser users=new CheckUpUserManager().getByIdUser(idUser);
			if(users.getBirthDate()!=null)
			{
				String [] bd=Util.getStr(users.getBirthDate()).split("-");
				json.put("bdate",Util.getInt(bd[2]));
				json.put("bmonth",Util.getInt(bd[1]));
				json.put("ymonth",Util.getInt(bd[0]));
			}
			else
			{
				json.put("bdate","");
				json.put("bmonth","");
				json.put("ymonth","");
			}
		}
		else if(service.equals("edit"))
		{
			List<CheckUpHobby> hobies=new CheckUpHobbyManager().getAllForLevel(idUser);
			int count=0;
			
			for(int i=0; i<hobies.size(); i++)
			{
				String param=hobies.get(i).getIdGroup()+"_"+hobies.get(i).getIdHobby();
				int  skill=Util.getInt(request.getParameter(param),0);
				if(skill>0)
				{
					CheckUpHobby hobby=hobies.get(i);
					hobby.setSkill(skill);
					if(new CheckUpHobbyManager().update(hobby)!=1)
					{
						errors.add(propMgr.getMessage("th_TH", "UPDATE_SKILL_FAIL"));
						elements.add("SYSTEM");
					}
					if(hobies.get(i).getIdHobby()==43 || hobies.get(i).getIdHobby()==45 || hobies.get(i).getIdHobby()==56 )
					{
						count++;
					}
				}
				else
				{
					errors.add(propMgr.getMessage("th_TH", "GLOBAL_SPECIFY"));
					elements.add(param);
				}
			}
			
			int bDay=-1;
    		int bMonth=-1;
    		int bYear=-1;
    		Date birthDate=null;
    		bDay=Util.getInt(request.getParameter("bDay"));
			bMonth=Util.getInt(request.getParameter("bMonth"));
			bYear=Util.getInt(request.getParameter("bYear"));
			if(bDay<=0)
			{
				errors.add(propMgr.getMessage("th_TH", "GLOBAL_DAY_REQUIRED"));
				elements.add("bDay");				
			}
			if(bMonth<=0)
			{
				errors.add(propMgr.getMessage("th_TH", "GLOBAL_MONTH_REQUIRED"));
				elements.add("bMonth");				
			}
			if(bYear<=0)
			{
				errors.add(propMgr.getMessage("th_TH", "GLOBAL_YEAR_REQUIRED"));
				elements.add("bYear");				
			}
			birthDate=Util.getSQLDate(bDay, bMonth, bYear);
			if(birthDate!=null)
			{
				CheckUpUser user=new CheckUpUser();
				user.setIdUser(idUser);
				user.setBirthDate(birthDate);
				new CheckUpUserManager().updateBd(user);
			}
			else
			{
				errors.add(propMgr.getMessage("th_TH", "GLOBAL_DAY_REQUIRED"));
				elements.add("bDay");	
			}
			if(errors.size()==0)
			{
				if (count>0) 
				{
					 json.put("pages", "aptitudeExtension");
				}
				else
				{
					 json.put("pages", "aptitudeRank");
				}
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
	}
}
