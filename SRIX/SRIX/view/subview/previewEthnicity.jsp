<%@page import="com.topgun.resume.EthnicityManager"%>
<%@page import="com.topgun.resume.Ethnicity"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%
	//PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{
		Ethnicity et=new EthnicityManager().get(resume.getIdJsk(),resume.getIdResume());
		request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idResume",idResume);
		request.setAttribute("et",et);
		if(resume.getTemplateIdCountry() != 216)
		{
%>
			<fmt:setLocale value="${resume.locale}"/>
			<div class="row" style="padding:10pt;">
				<div class="col-xs-12 col-sm-12 answer">
					<c:choose>
						<c:when test="${et.ethnicity eq 'WHITE'}">
							<fmt:message key="ETHNICITY_WHITE"/>
						</c:when>
						<c:when test="${et.ethnicity eq 'BLACK'}">
							<fmt:message key="ETHNICITY_BLACK"/>
						</c:when>
						<c:when test="${et.ethnicity eq 'HISPANIC'}">
							<fmt:message key="ETHNICITY_HISPANIC"/>
						</c:when>
						<c:when test="${et.ethnicity eq 'ASIAN'}">
							<fmt:message key="ETHNICITY_ASIAN"/>
						</c:when>
						<c:when test="${et.ethnicity eq 'AMERICAN'}">
							<fmt:message key="ETHNICITY_AMERICAN"/>
						</c:when>
					</c:choose>
				</div>
			</div>
<%	
		}
	}
%>