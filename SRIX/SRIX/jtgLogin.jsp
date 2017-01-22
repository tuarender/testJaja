<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.JobseekerManager" %>
<%
	int idJsk=Util.getInt(request.getParameter("idJsk"));
	String idSession=Util.getStr(request.getParameter("idSession"));
	if(idJsk>0 && !idSession.equals(""))
	{
		if(new JobseekerManager().isLogin(idJsk,idSession))
		{
			session.setAttribute("SESSION_ID_JSK", idJsk);	
			response.sendRedirect("http://www.jobtopgun.com/main.jsp");
		}
		else
		{
			response.sendRedirect("/");
		}
	}
%>