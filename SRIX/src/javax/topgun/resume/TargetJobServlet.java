package javax.topgun.resume;

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

import com.topgun.resume.Industry;
import com.topgun.resume.IndustryManager;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Location;
import com.topgun.resume.LocationManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.TargetJob;
import com.topgun.resume.TargetJobField;
import com.topgun.resume.TargetJobFieldManager;
import com.topgun.resume.JobType;
import com.topgun.resume.JobTypeManager;
import com.topgun.resume.TargetJobManager;
import com.topgun.shris.masterdata.JobField;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.shris.masterdata.SubField;
import com.topgun.util.Encoder;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;


import com.google.gson.Gson;

public class TargetJobServlet extends HttpServlet 
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
        else if(service.equals("view"))
        {
        	TargetJob targetJobs=new TargetJobManager().get(idJsk, idResume);
        	json.put("targetJobs",targetJobs);
        }
        else if(service.equals("setWorkPermit"))
        {
        	String workPermit=Util.getStr(request.getParameter("workPermit"));
        	TargetJob tj=new TargetJobManager().get(idJsk, idResume);
        	if(tj==null)
        	{
        		tj=new TargetJob();
        		tj.setIdJsk(idJsk);
        		tj.setIdResume(idResume);
        		tj.setWorkPermit(workPermit);
        		new TargetJobManager().add(tj);
        	}
        	else
        	{
        		tj.setWorkPermit(workPermit);
        		new TargetJobManager().update(tj);
        	}
			ResumeManager rsmMgr=new ResumeManager();
			ResumeStatusManager rstMgr=new ResumeStatusManager();
			if(idResume==0)
			{
				rsmMgr.updateStatus(idJsk,0, rstMgr.getRegisterStatus(rsmMgr.get(idJsk,0)));
			}
			else
			{
				rsmMgr.updateStatus(idJsk,idResume, rstMgr.getResumeStatus(rsmMgr.get(idJsk,idResume)));
			}
			rsmMgr.updateTimestamp(idJsk,idResume);
        }
        else if(service.equals("salary"))
        {
        	int salaryChoice=Util.getInt(request.getParameter("salaryChoice"),0);
        	TargetJob tj=new TargetJobManager().get(idJsk, idResume);
    		if(tj==null)
    		{
    			tj=new TargetJob(); 
    			tj.setIdJsk(idJsk);
    			tj.setIdResume(idResume);
    			tj.setWorkPermit("");
    			tj.setRelocate("");
    		}
        	if(salaryChoice==0)
        	{
    			tj.setExpectedSalaryPer(-1);
    			tj.setMaxExpectedSalary(-1);
    			tj.setMinExpectedSalary(-1);
    			tj.setSalaryCurrency(-1);
        	}
        	else
        	{
        		int minSalary=Util.getInt(request.getParameter("minSalary"),-1);
        		int maxSalary=Util.getInt(request.getParameter("maxSalary"),-1);
        		int currency=Util.getInt(request.getParameter("currency"),-1);
        		int salaryPer=Util.getInt(request.getParameter("salaryPer"),-1);
        		if(minSalary>maxSalary)
        		{
        			int temp=minSalary;
        			minSalary=maxSalary;
        			maxSalary=temp;
        		}
    			tj.setExpectedSalaryPer(salaryPer);
    			tj.setMaxExpectedSalary(maxSalary);
    			tj.setMinExpectedSalary(minSalary);
    			tj.setSalaryCurrency(currency);        		
        	}
        	
        	if(new TargetJobManager().get(idJsk, idResume)!=null)
        	{
        		new TargetJobManager().update(tj);
        	}
        	else
        	{
        		new TargetJobManager().add(tj);
        	}
			ResumeManager rsmMgr=new ResumeManager();
			ResumeStatusManager rstMgr=new ResumeStatusManager();
			if(idResume==0)
			{
				rsmMgr.updateStatus(idJsk,0, rstMgr.getRegisterStatus(rsmMgr.get(idJsk,0)));
			}
			else
			{
				rsmMgr.updateStatus(idJsk,idResume, rstMgr.getResumeStatus(rsmMgr.get(idJsk,idResume)));
			}
			rsmMgr.updateTimestamp(idJsk,idResume);
        }
        else if(service.equals("setRelocate"))
        {
        	String relocate=Util.getStr(request.getParameter("relocate"));
        	TargetJob tj=new TargetJobManager().get(idJsk, idResume);
        	if(tj==null)
        	{
        		tj=new TargetJob();
        		tj.setIdJsk(idJsk);
        		tj.setIdResume(idResume);
        		tj.setRelocate(relocate);
        		new TargetJobManager().add(tj);
        	}
        	else
        	{
        		tj.setRelocate(relocate);
        		new TargetJobManager().update(tj);
        	}
			ResumeManager rsmMgr=new ResumeManager();
			ResumeStatusManager rstMgr=new ResumeStatusManager();
			if(idResume==0)
			{
				rsmMgr.updateStatus(idJsk,0, rstMgr.getRegisterStatus(rsmMgr.get(idJsk,0)));
			}
			else
			{
				rsmMgr.updateStatus(idJsk,idResume, rstMgr.getResumeStatus(rsmMgr.get(idJsk,idResume)));
			}
			rsmMgr.updateTimestamp(idJsk,idResume);
        }
        
        else if(service.equals("setTravel"))
        {
        	int travel=Util.getInt(request.getParameter("travel"),0);
        	TargetJob tj=new TargetJobManager().get(idJsk, idResume);
        	if(tj==null)
        	{
        		tj=new TargetJob();
        		tj.setIdJsk(idJsk);
        		tj.setIdResume(idResume);
        		tj.setTravel(travel);
        		new TargetJobManager().add(tj);
        	}
        	else
        	{
        		tj.setTravel(travel);
        		new TargetJobManager().update(tj);
        	}
			ResumeManager rsmMgr=new ResumeManager();
			ResumeStatusManager rstMgr=new ResumeStatusManager();
			if(idResume==0)
			{
				rsmMgr.updateStatus(idJsk,0, rstMgr.getRegisterStatus(rsmMgr.get(idJsk,0)));
			}
			else
			{
				rsmMgr.updateStatus(idJsk,idResume, rstMgr.getResumeStatus(rsmMgr.get(idJsk,idResume)));
			}
			rsmMgr.updateTimestamp(idJsk,idResume);
        }        
        else if(service.equals("setNotice"))
        {
        	int noticeStatus=Util.getInt(request.getParameter("noticeStatus"));
        	int notice=Util.getInt(request.getParameter("notice"));
        	
        	//startDate        	
        	int startDay=Util.getInt(request.getParameter("startDay"));
			int startMonth=Util.getInt(request.getParameter("startMonth"));
			int startYear=Util.getInt(request.getParameter("startYear"));      	
			Date startDate=Util.getSQLDate(startDay, startMonth, startYear);
        	if(noticeStatus==1)
        	{
        		notice=-1;
        	}
        	else
        	{
        		startDate=null;
        	}
        	TargetJob tj=new TargetJobManager().get(resume.getIdJsk(), resume.getIdResume());
        	if(tj==null)
        	{
        		tj=new TargetJob();
        		tj.setIdJsk(idJsk);
        		tj.setIdResume(idResume);
        		tj.setStartJob(startDate);
        		
        		tj.setStartJobNotice(notice);
        		new TargetJobManager().add(tj);
        	}
        	else
        	{
        		tj.setStartJobNotice(notice);
           		tj.setStartJob(startDate);
        		new TargetJobManager().update(tj);
        	}
			ResumeManager rsmMgr=new ResumeManager();
			ResumeStatusManager rstMgr=new ResumeStatusManager();
			if(idResume==0)
			{
				rsmMgr.updateStatus(idJsk,0, rstMgr.getRegisterStatus(rsmMgr.get(idJsk,0)));
			}
			else
			{
				rsmMgr.updateStatus(idJsk,idResume, rstMgr.getResumeStatus(rsmMgr.get(idJsk,idResume)));
			}
			rsmMgr.updateTimestamp(idJsk,idResume);
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
