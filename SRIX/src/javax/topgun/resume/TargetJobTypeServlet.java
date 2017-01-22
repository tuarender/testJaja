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

import com.topgun.resume.ExperienceSummary;
import com.topgun.resume.JobType;
import com.topgun.resume.JobTypeManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.google.gson.Gson;

public class TargetJobTypeServlet extends HttpServlet 
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
			String[] idJobTypes=request.getParameterValues("idJobType");
			if(idJobTypes==null)
			{
				idJobTypes=request.getParameterValues("idJobTypePc");
			}
			if(idJobTypes!=null)
			{
				
				new JobTypeManager().deleteAll(idJsk,idResume);
				for(int i=0; i<idJobTypes.length; i++)
				{
					int id=Util.getInt(idJobTypes[i]);
					if(id>0)
					{
						JobType jt=new JobType();
						jt.setIdJsk(idJsk);
						jt.setIdResume(idResume);
						jt.setJobType(id);
						if(new JobTypeManager().add(jt)!=1)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_JOBTYPE_FAIL"));
							elements.add("SYSTEM");					
						}
					}
				}
			}
		}
		else if(service.equals("view"))
		{
			List<JobType>jobTypes=new JobTypeManager().getAll(idJsk, idResume);
			String names[]=null;
			if(jobTypes!=null)
			{
				names=new String[jobTypes.size()];
				for(int i=0; i<jobTypes.size(); i++)
				{
					com.topgun.shris.masterdata.JobType jt=MasterDataManager.getJobType(jobTypes.get(i).getJobType(), resume.getIdLanguage());
					if(jt!=null)
					{
						names[i]=(jt.getTypeName());
					}
				}
			}
			json.put("jobTypes",jobTypes);
			json.put("names",names);
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
