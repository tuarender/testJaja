package javax.topgun.resume;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.JobseekerManager;
import com.topgun.util.Encoder;
import com.topgun.util.Util;

public class LogoutServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException   
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
	   	String idSession=Encoder.getEncode(request.getSession().getId());
		new JobseekerManager().clearLogin(idJsk, idSession);

		request.getSession().removeAttribute("SESSION_ID_JSK");
		request.getSession().removeAttribute("SET_COOKIE");
		request.getSession().invalidate();
		
		Cookie[] cookies = null;
	   	cookies = request.getCookies();
	   	if(cookies != null)
		{
			for (int i = 0; i < cookies.length; i++)
			{
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
		
		
		response.sendRedirect("/");
	}
}
