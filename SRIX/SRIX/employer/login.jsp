<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.topgun.util.*"%>
<%
	int idPosition=Util.getInt(request.getParameter("PositionId"));
	int idEmp=Util.getInt(request.getParameter("EmployerId"));
	String locale=Util.getStr(request.getParameter("locale"));
	response.sendRedirect("http://www.superresume.com/SRIX?view=apply&idEmp="+idEmp+"&idPosition="+idPosition+"&locale="+locale);
	return;
%>