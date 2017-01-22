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

import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.Industry;
import com.topgun.resume.IndustryManager;
import com.topgun.resume.TargetJobField;
import com.topgun.resume.TargetJobFieldManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.google.gson.Gson;

public class TargetIndustryServlet extends HttpServlet 
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
        
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
        String service=Util.getStr(request.getParameter("service"));
        Resume resume=new ResumeManager().get(idJsk, idResume);
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
        else if(service.equals("add"))
		{
        	if(new IndustryManager().count(idJsk, idResume)<3) //maximum 3 jobfields per jobseeker
        	{
				int idIndustry=Util.getInt(request.getParameter("idIndustry"),-1);
				String otherIndustry=Util.getStr(request.getParameter("otherIndustry"));
				if(idIndustry>0 || !otherIndustry.equals(""))
				{
					if(idIndustry<0)
					{
						idIndustry=new IndustryManager().getNextOtherIndustryId(idJsk, idResume);
					}
					Industry industry=new IndustryManager().get(idJsk, idResume, idIndustry);
					if(industry==null)
					{
						industry=new Industry();
						industry.setIdJsk(idJsk);
						industry.setIdResume(idResume);
						industry.setIdIndustry(idIndustry);
						industry.setOtherIndustry(otherIndustry);
						industry.setIndustryOrder(new IndustryManager().getMaxIndustryOrder(idJsk, idResume)+1);
						if(new IndustryManager().add(industry)!=1)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_INDUSTRY_FAIL"));
							elements.add("SYSTEM");	
						}
					}
				}
        	}
		}
		else if(service.equals("view"))
		{
			List<Industry> industries = new IndustryManager().getAll(idJsk, idResume);
			if(industries!=null)
			{
				String names[]=new String[industries.size()];
				for(int i=0; i<industries.size(); i++)
				{
					com.topgun.shris.masterdata.Industry industry=MasterDataManager.getIndustry(industries.get(i).getIdIndustry(), resume.getIdLanguage());
					if(industry!=null)
					{
						names[i]=Util.getStr(industry.getIndustryName());
					}
					else
					{
						names[i]=Util.getStr(industries.get(i).getOtherIndustry());
					}
				}
				
				json.put("industries",industries);
				json.put("names",names);
			}
		}
		else if(service.equals("delete"))
		{
			int idIndustry = Util.getInt(request.getParameter("idIndustry"));
			
			Industry ind = new IndustryManager().get(idJsk, idResume, idIndustry);
			if(ind!=null)
			{
				if(new IndustryManager().delete(ind)!=1)
				{
					errors.add(propMgr.getMessage(resume.getLocale(), "DELETE_INDUSTRY_FAIL"));
					elements.add("SYSTEM");	
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
		out.close();
	}
}
