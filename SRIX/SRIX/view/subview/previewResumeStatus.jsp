<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<%@page import="com.topgun.shris.masterdata.Language"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{
		Country country=MasterDataManager.getCountry(resume.getApplyIdCountry(),resume.getIdLanguage());
		String language=MasterDataManager.getLanguageName(resume.getIdLanguage(),resume.getIdLanguage());
		request.setAttribute("resume",resume);
		request.setAttribute("country",country);
		request.setAttribute("language",language);
		request.setAttribute("idResume",resume.getIdResume());
	}
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<div class="row" style="padding:10pt;">
		<div class="col-xs-12 col-sm-6 caption_bold">
			<fmt:message key="PREVIEW_RESUME_NAME"/> :
		</div>
		<div class="col-xs-12 col-sm-6 answer">
			<c:out value="${resume.resumeName}"/>
		</div>

		<div class="col-xs-12 col-sm-6 caption_bold">
			<fmt:message key="PREVIEW_APPLY_TO_COUNTRY"/> :
		</div>
		<div class="col-xs-12 col-sm-6 answer">
			<c:out value="${country.countryName}"/>
		</div>
	
		<div class="col-xs-12 col-sm-6 caption_bold">
			<fmt:message key="GLOBAL_LANGUAGE"/> :
		</div>
		<div class="col-xs-12 col-sm-6 answer">
			<c:out value="${language}"/>
		</div>
	
		<div class="col-xs-12 col-sm-6 caption_bold">
			<fmt:message key="NAMING_PRIVACY"/> :
		</div>
		<div class="col-xs-12 col-sm-6 answer">
			<c:choose>
				<c:when test="${resume.resumePrivacy eq 'PUBLIC'}">
			  		<fmt:message key="PREVIEW_PUBLIC"/>
				</c:when>
				<c:when test="${resume.resumePrivacy eq 'PRIVATE'}">
			   		<fmt:message key="PREVIEW_PRIVATE"/>
				</c:when>
			</c:choose>
		</div>
		
		<div style="text-align:right; padding-right:10pt;">
			<a href="/SRIX?view=naming&cmd=EDIT&sequence=0&idResume=<c:out value="${idResume}"/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
		</div>
	</div>
</c:if>