<%@page import="com.topgun.util.Encoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page import="com.topgun.resume.CompleteStatus"%>
<%@page import="com.topgun.resume.Status"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>

<c:if test="${not empty resume}">
	<c:choose>
		<c:when test="${not empty resume}">
			<fmt:setLocale value="${resume.locale}"/>
		</c:when>
		<c:otherwise>
			<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
		</c:otherwise>
	</c:choose>
	<%
		Resume resume=(Resume)request.getAttribute("resume");
		request.setAttribute("idResume",resume.getIdResume());
		String locale=Util.getStr(request.getParameter("locale"));
		int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
		Jobseeker jobseeker=new JobseekerManager().get(idJsk);
		if(jobseeker!=null)
		{
			request.setAttribute("jobseeker",jobseeker);
		}
		if(locale.equals(""))
		{
			locale = Util.getStr(session.getAttribute("SESSION_LOCALE"));
		}
		if(locale.equals(""))
		{
			locale="th_TH";
		}
		
		CompleteStatus educationStatus=new ResumeStatusManager().getEducationCompleteStatus(resume);
		CompleteStatus expStatus=new ResumeStatusManager().getWorkingExperienceStatus(resume);
		Status targetJobStatus=new ResumeStatusManager().getTargetJobStatus(resume) ;
		
		PropertiesManager propMgr=new PropertiesManager();
		
		if(educationStatus!=null)
		{
			request.setAttribute("educationStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		if(expStatus!=null)
		{
			request.setAttribute("expStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		if(targetJobStatus!=null)
		{
			request.setAttribute("targetJobStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		request.setAttribute("backToView", Util.getStr(request.getParameter("view")));	
	 %>
	<script>
	
		$(document).ready(function()
		{ 
			$( "a" ).click(function() {
				var pages="";
				var rel=$(this).attr('rel');
				if(rel==4)
				{
					pages="previewEducation";
				}
				else if(rel==6)
				{
					pages="previewWorking";
				}
				else if(rel==3)
				{
					pages="previewTargetJob";
				}
				if($.trim($('#layer_'+rel).html())==""&&pages!="")
				{ 
					var d =new Date();
					$.get('/view/subview/'+pages+'.jsp?idResume=<c:out value="${resume.idResume}"/>&t='+d.getTime()+'&backToView=<c:out value="${backToView}" />',function(data) 
					{
						$('#layer_'+rel).html(data);
					});
				}
				$('#layer_'+rel).collapse('show');
			});
			
			$('#backToJobtopgun').click(function(event){
				event.preventDefault();
				window.location.href = 'http://www.jobtopgun.com/?idJsk=<%=idJsk %>&key=<%=Encoder.getEncode(request.getSession().getId()) %>';	
			});
			
			$('#backToSuperresume').click(function(event){
				event.preventDefault();
				window.location.href = 'http://www.superresume.com/SRIX?view=home&jSession=<%=Encoder.getEncode(request.getSession().getId()) %>';
			});
		});
	</script>
	<style>
		.incomplete
		{
			float:right !important;
			color:#ff0000 !important;
			font-weight:normal !important;
			font-style:italic !important;
		}
		.topic
		{
			color: #1C94C4;
    		font-weight: bold;
		}
		.section_header, .section_footer
		{
			padding-top: 20px;
			padding-bottom: 20px;
		}
		.jobMatchDesc-body
		{
			font-size: 1.1em;
		}
	</style>
	<div class="section_header alignCenter">
		<span class="section_header"><fmt:message key="EDIT_JM_CRITERIA"/></span>
	</div>
	<div class="jobMatchDesc-body">
		<p>
			<fmt:message key="JOBMATCH_EDIT_DESC"></fmt:message>
		</p>
		<ol>
			<li><fmt:message key="JOBMATCH_EDIT_DESC_1"></fmt:message></li>
			<li><fmt:message key="JOBMATCH_EDIT_DESC_2"></fmt:message></li>
			<li><fmt:message key="JOBMATCH_EDIT_DESC_3"></fmt:message></li>
		</ol>
	</div>
	<div class="panel-group" id="accordion">
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_4" rel="4" class="topic" > <fmt:message key="SECTION_EDUCATION"/><p class="incomplete"><c:out value="${educationStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_4"></div>
		</div>
		<br>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_6" rel="6" class="topic" > <fmt:message key="WORKEXP_TITLE"/><p  class="incomplete"><c:out value="${expStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_6"></div>
		</div>
		<br>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_3"  rel="3" class="topic" > <fmt:message key="TARGETJOB"/><p class="incomplete"><c:out value="${targetJobStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_3"></div>
		</div>
	</div>
	<div class="section_footer alignCenter">
		 <div class="row">
			<div class="col-xs-5">
				<a id="backToJobtopgun">
				 	<button type="button" class="btn btn-lg btn-info">
				 		<fmt:message key="BACK_TO_JOBTOPGUN"></fmt:message>
				 	</button>
				</a>
			</div>
			<div class="col-xs-1">
				&nbsp;
			</div>
			<div class="col-xs-5">
				<a id="backToSuperresume">
				 	<button type="button" class="btn btn-lg btn-info">
				 		<fmt:message key="BACK_TO_SUPERRESUME"></fmt:message>
				 	</button>
				 </a>
			</div>
		</div>
	</div>
</c:if>
<br/><br/>
