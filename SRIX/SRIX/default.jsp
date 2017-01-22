<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.topgun.util.Util"%>
<%
	int invalid=Util.getInt(request.getParameter("invalid"), -1);
	String strInvalid = "";
	if(invalid>-1){
		strInvalid = "&invalid="+invalid;
	}
	String url = "/index.jsp?jsession="+session.getId()+strInvalid;
	response.sendRedirect(url);
%>