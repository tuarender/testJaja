<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
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
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	Resume resume=null;
	if(idJsk>0)
	{
		Jobseeker jobseeker=new JobseekerManager().get(idJsk);
		if(jobseeker!=null)
		{
			request.setAttribute("jobseeker",jobseeker);
			resume=new ResumeManager().get(idJsk,0);
			if(resume!=null)
			{
				if(resume.getLocale()!=null)
				{
					locale=resume.getLocale();
				}
				request.setAttribute("resume",resume);
			}
		}	
	}
	request.setAttribute("locale",locale);
%>
<script>
	$(document).ready(function()
	{
		$('#buttonNext').click(function()
		{
			window.location.href = '/SRIX?view=personal&idResume=<c:out value="${resume.idResume}"/>&sequence=1';
		});
	});
</script>
<br>
<div class="row" >
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<label class="caption"><fmt:message key="MESSAGE_JOBUPDATE_3"/></label>
	</div>
</div>
<br>
<div class="row" align="center">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<input type="button"  class="btn btn-lg btn-success" id="buttonNext" value="<fmt:message key="GLOBAL_NEXT"/>" onClick="ga('send', 'event', { eventCategory:'Engage-JobUpdate', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});">
	</div>
</div>
<br>
<div class="row">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<b><fmt:message key='GLOBAL_REMARK'/></b>
		<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:message key='MESSAGE_JOBUPDATE'>
			<c:if test="${not empty jobseeker}">
				<c:set var="email"><span class="answer"><c:out value="${jobseeker.username}"/></span></c:set>
				<fmt:param value="${email}"></fmt:param>
			</c:if>
		</fmt:message>
		<br><br>
		<fmt:message key='MESSAGE_JOBUPDATE_2'/>		
	</div>
	
</div>
