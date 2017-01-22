<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>

<%
	int idResume=Util.getInt(request.getParameter("idResume"));
	int section=Util.getInt(request.getParameter("section"));
	int sequence=Util.getInt(request.getParameter("sequence"));
	String locale=Util.getStr(request.getParameter("locale"));
	String idSession=Encoder.getEncode(session.getId());
	request.setAttribute("idResume",idResume);
	request.setAttribute("section",section);
	request.setAttribute("sequence",sequence);
	request.setAttribute("locale",locale);
	request.setAttribute("idSession",idSession);
%>
<c:choose>
	<c:when test="${locale ne ''}">
		<fmt:setLocale value="${locale}"/>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${not empty resume}">
				<fmt:setLocale value="${resume.locale}"/>
			</c:when>
			<c:otherwise>
				<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<c:import url="/config.jsp" charEncoding="UTF-8"/>

<form>
	<div class="alignCenter" style="color: #58595b">
		<br/>
		<h4><fmt:message key="SYNC_COMPLETE_SUCCESS"/>
			<u><a target="_blank"  href="/view/viewResume.jsp?idResume=<c:out value="${idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message key="SYNC_COMPLETE_VIEW"/></a></u>
		</h4>
		<p><h4><fmt:message key="SYNC_COMPLETE_EDIT"/></h4></p>
		</br>
		<c:choose>
			<c:when test="${section ne 3}">
				<c:set var="urlYes" scope="request">SRIX?view=resumeInfo&idResume=<c:out value="${idResume}"/></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="urlYes" scope="request">SRIX?view=education&idResume=<c:out value="${idResume}"/>&sequence=<c:out value="${sequence}"/></c:set>
			</c:otherwise>
		</c:choose>
		<div class="row">
			<a href="<c:out value="${urlYes}"/>" class="button_link_big"><fmt:message key="SYNC_COMPLETE_BTN_YES"/></a>
			<a href="/SRIX?view=home&jSession=<c:out value="${idSession}"/>" class="button_link_big col-xs-offset-1"><fmt:message key="SYNC_COMPLETE_BTN_NO"/></a>
		</div>
		<div class="row  col-xs-offset-6 col-xs-6">
			<p class="font_14" style="padding-left: 0px;padding-right: 0px;"><fmt:message key="SYNC_COMPLETE_BACK_TO_HOMEPAGE"/></p>
		</div>
		
	</div>
</form>


