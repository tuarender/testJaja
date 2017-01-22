package javax.topgun.resume;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.util.Util;

public class LogoutCancelServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String locale = Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"));
		String service = Util.getStr(request.getSession().getAttribute("SESSION_SERVICE"));
		String method = Util.getStr(request.getSession().getAttribute("SESSION_METHOD"));
		request.getSession().removeAttribute("SESSION_ID_JSK");
		request.getSession().removeAttribute("SESSION_LOCALE");
		request.getSession().removeAttribute("SESSION_USERNAME");
		request.getSession().removeAttribute("SESSION_SERVICE");
		request.getSession().removeAttribute("SESSION_CANCEL_SERVICE");
		request.getSession().removeAttribute("SESSION_METHOD");
		request.getSession().invalidate();
		String urlRedirect = "/TH/Services/CancelServiceLogon.jsp?locale="+locale+"&Service="+service;
		if(!method.trim().equals("")){
			urlRedirect+="&method="+method;
		}
		response.sendRedirect(urlRedirect);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
