<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.topgun.util.*"%>
<% 
	int idJsk = Util.getInt(request.getParameter("idJsk"));
	int idResume = Util.getInt(request.getParameter("idResume"));
	String encode = Encryption.getEncoding(0, 0, idJsk, idResume);
	out.print(encode);
%>