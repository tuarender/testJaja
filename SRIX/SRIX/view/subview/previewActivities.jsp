<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ActivityManager"%>
<%@page import="com.topgun.resume.Activity"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
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
		List <Activity> activityList=new ActivityManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String ln=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String ct=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idResume",idResume);
		request.setAttribute("ln",ln);
		request.setAttribute("ct",ct);
		request.setAttribute("activityList",activityList);
		request.setAttribute("activitySize",activityList.size());
	}
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<c:choose>
		<c:when test="${activitySize gt 0}">
			<div class="row" style="padding:10pt;">
				<c:forEach items="${activityList}" var="activity" varStatus="loop">
					<div class="col-xs-12 col-sm-12 answer">
						<label for="club">
							<c:out value='${loop.index+1}'/>. <c:out value='${activity.club}'/>
						</label>
						<c:if test="${not empty activity.position}">
							(<c:out value="${activity.position}"/>)
						</c:if>
					</div>
					<div class="col-xs-12 col-sm-12 answer">
						<fmt:formatDate value="${activity.startDate}" dateStyle="full"/> -
						<fmt:formatDate value="${activity.endDate}" dateStyle="full"/>
					</div>
					<div class="col-xs-12 col-sm-12 answer">
						<c:if test="${not empty activity.description}">
							<c:out value="${activity.description}"/>
						</c:if>
					</div>
				</c:forEach>
				<div class="col-xs-12 col-sm-12" style="text-align:right;">
					<a href="/SRIX?view=activities&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row" style="text-align:right;padding:10pt;">
				<div class="col-xs-12 col-sm-12">
					<a href="/SRIX?view=activities&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>	
	
	
	
	
	
	
