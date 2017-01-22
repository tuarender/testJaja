package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.MailThread;
import com.topgun.util.Util;

public class MailRegisterServlet extends HttpServlet 
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
		LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		int sequence = Util.getInt(request.getParameter("sequence"),0);
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"));
		String referer=Util.getStr(request.getHeader("referer"));
		String jobMatchStatus = Util.getStr(request.getParameter("jobMatchStatus"));
		String mailOtherStatus = Util.getStr(request.getParameter("mailOtherStatus"));
		if(idJsk>0&&idResume==0&&sequence==1)
		{
			if(referer.indexOf("view=register")>-1)
			{
				new MailThread(idJsk,1).start();
			}
			else if(referer.indexOf("view=experience")>-1)
			{
				if(idCountry==216)
				{
					new MailThread(idJsk,2).start();
				}
			}
			else if(referer.indexOf("view=targetJob")>-1)
			{
				if(idCountry==216)
				{
					new MailThread(idJsk,3).start();
					new MailThread(idJsk, 0, 4).start();
				}
			}
		}
		
		Jobseeker jsk=new JobseekerManager().get(idJsk);
		jsk.setJobMatchStatus(jobMatchStatus);
		jsk.setMailOtherStatus(mailOtherStatus);
			
		new JobseekerManager().update(jsk);
		
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
