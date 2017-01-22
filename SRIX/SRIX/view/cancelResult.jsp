<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%
	int success = Util.getInt(request.getParameter("success"));
	String locale = Util.getStr(session.getAttribute("SESSION_LOCALE"));
	String service = Util.getStr(request.getParameter("cancelService"),"jobmatch");
	request.setAttribute("locale",locale);
	request.setAttribute("success",success);
	request.setAttribute("service",service);
	
%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<style type="text/css">
fieldset { border:none; }
legend { font-size:18px; margin:0px; padding:10px 0px; color:#b0232a; font-weight:bold;}
label { display:block; margin:15px 0 5px;}
input[type=text], input[type=password] { width:300px; padding:5px; border:solid 1px #000;}
.prev, .next { background-color:#b0232a; padding:5px 10px; color:#fff; text-decoration:none;}
.prev:hover, .next:hover { background-color:#000; text-decoration:none;}
#steps { list-style:none; width:100%; overflow:hidden; margin:0px; padding:0px;}
#steps li {font-size:24px; float:left; padding:10px; color:#b0b1b3;}
#steps li span {font-size:11px; display:block;}
#steps li.current { color:#000;}
#makeWizard { background-color:#b0232a; color:#fff; padding:5px 10px; text-decoration:none; font-size:18px;}
#makeWizard:hover { background-color:#000;}
</style>
<link href="/css/cancelStyle.css" rel="stylesheet" type="text/css" />
<div class="contentCancel" style="font-size:18px;">
	<c:if test="${success eq 1}">
		<center><font style="color:#000000;"><fmt:message key='CANCEL_SUCCESS'/></font></center>
	</c:if>
	<c:if test="${success ne 1}">
		<center><font style="color:#000000;"><fmt:message key='CANCEL_ERROR'/></font></center>
	</c:if>
	<div style="margin:15px 0px;text-align:center"><a href="/LogoutServ" class="next"><fmt:message key="GLOBAL_CLOSE"/></a></div>
</div>
