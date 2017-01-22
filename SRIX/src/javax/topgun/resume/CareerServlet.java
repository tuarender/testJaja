package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.google.gson.Gson;
import com.topgun.resume.CareerObjective;
import com.topgun.resume.CareerObjectiveManager;
import com.topgun.resume.HobbyManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.util.Util;

public class CareerServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
        Gson gson = new Gson();
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
        String [] careerObj=request.getParameterValues("idCareerObjective");
        String othCareer1 =  Util.getStr(request.getParameter("othCareer1"));
        String othCareer2 =  Util.getStr(request.getParameter("othCareer2"));
		if(idJsk>0 && idResume>=0 && careerObj!=null)
		{
			new CareerObjectiveManager().deleteAll(idJsk, idResume);
			for (int i = 0; i < careerObj.length ; i++)
			{
				int idCareerObj=Util.getInt(careerObj[i],0);
				if(idCareerObj!=0)
				{
					CareerObjective careerobj=new CareerObjective();
					careerobj.setIdJsk(idJsk);
					careerobj.setIdResume(idResume);
					careerobj.setIdCareerObjective(idCareerObj);
					String oth="";
					if(idCareerObj==-1)
					{
						oth=othCareer1;
					}
					if(idCareerObj==-2)
					{
						oth=othCareer2;
					}
					careerobj.setOtherObjective(oth);
					new CareerObjectiveManager().add(careerobj);
				}
			}
			
			LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
			json.put("success",1);
			out.print(gson.toJson(json));
		}
		else //error
		{
			LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
			json.put("success",0);
			out.print(gson.toJson(json));
		}		
	}

}
