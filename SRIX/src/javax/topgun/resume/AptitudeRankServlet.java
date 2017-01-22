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

import com.topgun.resume.Hobby;
import com.topgun.resume.HobbyManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.google.gson.Gson;

public class AptitudeRankServlet extends HttpServlet 
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
        ResumeManager rsmMgr=new ResumeManager();
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
		int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"));
        
        String service=Util.getStr(request.getParameter("service"));
        Resume resume=rsmMgr.get(idJsk, idResume);
        if(resume!=null){
       	 idLanguage=Util.getInt(resume.getIdLanguage());
       }
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
		else if(service.equals("view"))
		{
			List<Hobby> hobies=new HobbyManager().getAll(idJsk, idResume);
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
			else
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_NOT_FOUND"));
				elements.add("SYSTEM");	
			}
		}
		else if(service.equals("edit"))
		{
			new HobbyManager().updateOrderAllHobby(idJsk,idResume);
			for(int i=0; i<5;i++)
			{
				int j=i+1;
				String param="rank"+j;
				String rank=Util.getStr(request.getParameter(param));
				if(rank!="")
				{
					String [] arank=(rank).split(":");
					Hobby hobby=new HobbyManager().get(idJsk, idResume, Util.getInt(arank[0]));
					if(hobby!=null)
					{
						hobby.setHobbyOrder(j);
						if(new HobbyManager().update(hobby)==0)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_NOT_FOUND"));
							elements.add("SYSTEM");	
						}
					}
					
				}
			}
			if(resume.getIdResume()==0)
			{
				rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
			}
			else
			{
				rsmMgr.updateStatus(idJsk,resume.getIdResume(), new ResumeStatusManager().getResumeStatus(rsmMgr.get(idJsk,resume.getIdResume())));
			}
			rsmMgr.updateTimestamp(resume.getIdJsk(), resume.getIdResume());
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
