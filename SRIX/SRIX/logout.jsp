<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	Cookie[] cookies = null;
   	cookies = request.getCookies();
   	if(cookies != null)
	{
		for (int i = 0; i < cookies.length; i++)
		{
			if(!cookies[i].getName().equals("COOKIE_ID_EMP") && !cookies[i].getName().equals("COOKIE_ID_POSITION"))
			{
				cookies[i].setDomain(".superresume.com");
				cookies[i].setMaxAge(0);
				cookies[i].setValue(null);
				cookies[i].setPath("/");
				response.addCookie(cookies[i]);
			}
		}
	}
	response.sendRedirect("/");
%>