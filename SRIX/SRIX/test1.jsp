<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Cookie cookJsk = new Cookie("COOKIE_TEST","11111");
	
	cookJsk.setMaxAge(365*24*60*60); 
	
	cookJsk.setDomain(".superresume.com");
	
	response.addCookie(cookJsk);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="test2.jsp">Go to test2</a>
</body>
</html>