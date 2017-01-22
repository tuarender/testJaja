<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<%
	int idEmp = Util.getInt(request.getParameter("idEmp"));
	int idPos = Util.getInt(request.getParameter("idPosition"));
	request.setAttribute("idEmp",idEmp);
	request.setAttribute("idPos",idPos);
%>
<div class="row" align="center">
	<img class="img-responsive" style="max-width:314px;min-width:120px;max-height:120px; padding-top:10px;padding-bottom:10px;" src="http://www.jobtopgun.com/content/filejobtopgun/logo_com_job/j<c:out value="${param.idEmp}" escapeXml="false" />.gif"/>
</div>
<div class="row" align="center">
	<label class="please" ><fmt:message key="GLOBAL_PLEASE_READ"/></label>
</div>
<div class="row" align="center">
	<label class="please" ><fmt:message key="APPLY_REGISTER_CONTENT"/></label>
</div>
<div class="row">
	<label class="normal"><fmt:message key="STEP_REGISTER"/></label>
</div>
<div class="row">
	<ol>
		<li>
			<span class="normal"><fmt:message key="STEP_REGISTER_1"/></span>
		</li>
		<li>
			<span class="normal"><fmt:message key="STEP_REGISTER_2"/></span>
		</li>
		<li>
			<span class="normal"><fmt:message key="STEP_REGISTER_3"/></span>
		</li>
		<li>
			<c:set var="applyJob">"<span class="suggestion"><b><fmt:message key="APPLY_NOW"/>"</b></span></c:set>
			<span class="normal">
				<fmt:message key="STEP_REGISTER_4">
					<fmt:param value="${applyJob}"></fmt:param>
				</fmt:message>
			</span>
		</li>
		<li>
			<c:set var="sendResume">"<span class="suggestion"><b><fmt:message key="APPLY_SEND_RESUME"/>"</b></span></c:set>
			<span class="normal">
				<fmt:message key="STEP_REGISTER_5">
					<fmt:param value="${sendResume}"></fmt:param>
				</fmt:message>
			</span>
		</li>
	</ol>
</div>
<br>
<div class="row" align="center">
	<a class="linkButton" href="SRIX?view=register"><fmt:message key="CLICK_REGISTER"/></a>
</div>
<div class="row" align="center">
	<c:set var="clickHere"><span class="suggestion"><b><a href="/SRIX?view=applyLogin&idEmp=<c:out value="${param.idEmp}" escapeXml="false" />&idPosition=<c:out value="${param.idPosition}" escapeXml="false" />"><fmt:message key="CLICK_HERE"/></a></b></span></c:set>
	<span class="normal_bold">
		<fmt:message key="ALREADY_MEMBER_LOGIN">
			<fmt:param value="${clickHere}"></fmt:param>
		</fmt:message>
	</span>
</div>
