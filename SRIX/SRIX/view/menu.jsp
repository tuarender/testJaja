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
<c:if test="${((param.view eq 'home') or (param.view eq 'apply') or (param.view eq 'applyRecord') or (param.view eq 'applyResult') or (param.view eq 'resumeInfo') or (param.view eq 'applyAllResult') or (param.view eq 'syncComplete') or (param.view eq 'registerFinish')) and status eq 'TRUE'}">
	<!-- Menu Bar for Desktop -->
	<div class="menuBar">
		<ul>
			<c:choose>
				<c:when test="${param.view eq 'home'}">
					<li class="main_menu_selected"><fmt:message key="MENU_HOME"/></li>
				</c:when>
				<c:otherwise>
					<li><a class="main_menu" href="/SRIX?view=home&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'หน้าหลัก'});"><fmt:message key="MENU_HOME"/></a></li>
				</c:otherwise>
			</c:choose>
	
			<c:choose>
				<c:when test="${param.view eq 'apply'}">
					<li class="main_menu_selected"><fmt:message key="MENU_APPLY"/></li>
				</c:when>
				<c:otherwise>
					<li><a class="main_menu" href="/SRIX?view=apply&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'หน้าส่งเรซูเม่'});"><fmt:message key="MENU_APPLY"/></a></li>
				</c:otherwise>
			</c:choose>
	
			<c:choose>
				<c:when test="${(param.view eq 'resumeInfo') and (param.idResume eq 0)}">
					<li class="main_menu_selected"><fmt:message key="MENU_ACCOUNT"/></li>
				</c:when>
				<c:otherwise>
					<li><a class="main_menu" href="/SRIX?view=resumeInfo&idResume=0&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'ข้อมูลสมาชิก'});"><fmt:message key="MENU_ACCOUNT"/></a></li>
				</c:otherwise>
			</c:choose>
	
			<c:choose>
				<c:when test="${(param.view eq 'applyRecord')}">
					<li class="main_menu_selected"><fmt:message key="MENU_APPLICATION"/></li>
				</c:when>
				<c:otherwise>
					<li><a class="main_menu" href="/SRIX?view=applyRecord&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'บันทึกการสมัครงาน'});"><fmt:message key="MENU_APPLICATION"/></a></li>
				</c:otherwise>
			</c:choose>
			<li><a class="main_menu" onClick="ga('send', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'ค้นหางานและสมัครงาน'});" href="http://www.jobtopgun.com?&idJsk=<c:out value="${idJsk}"/>&jSession=<c:out value="${idSession}"/>&locale=<c:out value="${sessionScope.SESSION_LOCALE}"/>" target="_blank"><fmt:message key="REGFINISH_CONTENT4"/></a></li>
			<li><a class="main_menu" href="/LogoutServ" onClick="ga('send', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'ออกจากระบบ'});"><fmt:message key="MENU_LOGOUT"/></a></li>
			<c:if test="${(param.view eq 'register') or (param.view eq 'home') or (param.view eq 'apply') or (param.view eq 'resumeInfo') or (param.view eq 'applyRecord') or (param.view eq 'syncComplete')}">
				<li style="float:right;">
					<a class="main_menu" onClick="ga('send', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'ไทย'});" href="/SRIX?idResume=<c:out value="${param.idResume}"/>&view=<c:out value="${param.view}"/>&locale=th_TH">ไทย |</a><a class="main_menu" onClick="ga('send resume', 'event', { eventCategory: 'send resume – tab menu', eventAction: 'Click', eventLabel: 'en'});" href="/SRIX?idResume=<c:out value="${param.idResume}"/>&view=<c:out value="${param.view}"/>&locale=en_TH"> En</a>
				</li>
			</c:if>	
		</ul>
	</div>
</c:if>