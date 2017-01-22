package javax.topgun.competency;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.topgun.util.Util;

public class CheckUpServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String agent=Util.getStr(request.getParameter("agent"));
		String view=Util.getStr(request.getParameter("view"));
		if(agent.equals(""))
		{
			agent = Util.getStr(request.getSession().getAttribute("SESSION_DEVICE"));
		}
		if(agent.equals(""))
		{
			agent = Util.getStr(request.getHeader("User-Agent"));
		}
		String device="desktop";
		if(agent.toUpperCase().indexOf("MOBILE")!=-1 && agent.toUpperCase().indexOf("IPAD")==-1)
		{
			device="mobile";
		}
		
		int idUsr=Util.getInt(request.getSession().getAttribute("SESSION_ID_USER"));
		
		request.getSession().setAttribute("SESSION_DEVICE", device);
		
		if(idUsr>0)
		{
			if(view.equals("")){
				request.getRequestDispatcher("/checkUp/"+device+"/index.jsp").forward(request, response);
			}
			else{
				request.getRequestDispatcher("/checkUp/index.jsp").forward(request, response);
			}
		}
		else
		{
			if(view.equals("register")||view.equals("forgot")){
				request.getRequestDispatcher("/checkUp/index.jsp").forward(request, response);
			}
			else{
				request.getRequestDispatcher("/checkUp/"+device+"/index.jsp").forward(request, response);
			}
		}
	}
}
