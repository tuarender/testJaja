<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.DecryptionLink"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<%
	 int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	 String enc=Util.getStr(request.getParameter("enc"));
	 int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	 int idResume = Util.getInt(request.getParameter("idResume"));
	 int applyAll = Util.getInt(request.getParameter("applyAll"),0);
	 Resume resume = null ;
	 if(idJsk>0 && idResume >=0)
	 {
	 	resume = new ResumeManager().get(idJsk, idResume);
	 }
	 String path = "/ApplyServ";
	 if(applyAll==1)
	 {
	 	path = "/ApplyAllServ";
	 }
	 request.setAttribute("resume", resume);
	 request.setAttribute("idResume", idResume);
	 request.setAttribute("enc", enc);
	 request.setAttribute("path", path);
%>
<div class="seperator">&nbsp;</div>
<form id="applyFrm" method="post" action="<c:out value="${path}"/>">
	<input type="hidden" name="enc" value="<c:out value="${enc}"/>">
	<div class="row">
		<div class="col-xs-12 text-center">
			<span class="font_16 color715fa8"><fmt:message key="FULLFILL_EXP_UPDATE_SUCCESS"/></span>
		</div>
		<div class="col-xs-12 seperator">&nbsp;</div>
		<c:if test="${not empty resume}">
			<div class="row">
				<div class="col-xs-12 col-sm-8">
					<span class="color333333">
						<c:out value="${resume.resumeName}"/>&nbsp;
						<fmt:formatDate var="timestamps" value="${resume.timeStamp}" pattern="d/M/yyyy"/>
						(<fmt:message key="GLOBAL_UPDATE"/> <c:out value="${timestamps}"/>)
					</span>
				</div>
				<div class="col-xs-6 col-sm-2">
					<a target="_blank"  href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_ตรวจดู_1'});"><fmt:message key="GLOBAL_VIEW"/></a>
				</div>
			</div>
		</c:if>
		<div class="col-xs-12 seperator">&nbsp;</div>
		<div class="col-xs-12 seperator">&nbsp;</div>
		<div class="col-xs-12 text-center">
			<input type="image" src="/images/<c:out value="${resume.locale}"/>/send.png" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel:'cta_ส่ง super resume'});">
		</div>
	</div>
</form>
