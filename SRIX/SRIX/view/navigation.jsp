<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Encoder"%>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	Resume resume= new ResumeManager().get(idJsk, 0);
	String status= "FALSE";
	if(resume != null)
	{
		status= Util.getStr(new ResumeStatusManager().getRegisterStatus(resume));
	}
	String idSession=Encoder.getEncode(session.getId());
	request.setAttribute("status", status); 
	request.setAttribute("idSession", idSession); 
%>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	<div class="navbar navbar-default navbar-fixed-top visible-xs" role="navigation">
		<div class="container">
       		<div align="center" class="navbar-header" style="padding-left:10px;padding-top:2px;">
	       		<c:choose>
	       			<c:when test="${(param.view eq 'home') or (param.view eq 'apply') or (param.view eq 'applyRecord') or (param.view eq 'applyResult') or (param.view eq 'resumeInfo') and (status eq 'TRUE')}">
	       				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		           			<span class="sr-only">Toggle navigation</span>
		           			<span class="icon-bar"></span>
		           			<span class="icon-bar"></span>
		           			<span class="icon-bar"></span>
	         			</button>
	         			<img class="img-responsive" src="/images/logo_super-16.png" style="max-height:60px;margin-left:60px;"/>
	       			</c:when>
	       			<c:otherwise>
	       				<img class="img-responsive" src="/images/logo_super-16.png" style="max-height:60px"/>
	       			</c:otherwise>
	       		</c:choose>
       		</div>
       		<div class="collapse navbar-collapse">
      			<ul class="nav navbar-nav">
					<li><a href="/SRIX?view=home"><fmt:message key="MENU_HOME"/></a></li>
					<li><a href="/SRIX?view=apply"><fmt:message key="MENU_APPLY"/></a></li>
					<li><a href="/SRIX?view=resumeInfo&idResume=0"><fmt:message key="MENU_ACCOUNT"/></a></li>
					<li><a href="/SRIX?view=applyRecord"><fmt:message key="MENU_APPLICATION"/></a></li>
					<li><a href="http://www.jobtopgun.com?idJsk=<c:out value="${idJsk}"/>&locale=<c:out value="${sessionScope.SESSION_LOCALE}"/>&jSession=<c:out value="${idSession}"/>" target="_blank"><fmt:message key="REGFINISH_CONTENT4"/></a></li>
					<li><a href="/LogoutServ"><fmt:message key="MENU_LOGOUT"/></a></li>
	         	</ul>
       		</div>
     	</div>
   	</div>	
	<div class="container visible-xs" style="padding-top:60px;"></div>	
