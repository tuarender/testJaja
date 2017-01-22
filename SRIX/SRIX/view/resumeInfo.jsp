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
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<c:if test="${not empty resume}">
	<%
		int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
		int idResume = Util.getInt(request.getParameter("idResume"));
		Resume resume=new ResumeManager().get(idJsk, idResume);
		request.setAttribute("idResume",resume.getIdResume());
		Jobseeker jobseeker=new JobseekerManager().get(idJsk);
		String locale = resume.getLocale();
		if(jobseeker!=null)
		{
			request.setAttribute("jobseeker",jobseeker);
		}
		Status personalStatus=new ResumeStatusManager().getPersonalStatus(resume);
		Status careerStatus=new ResumeStatusManager().getCareerObjectiveStatus(resume);
		Status targetJobStatus=new ResumeStatusManager().getTargetJobStatus(resume) ;
		CompleteStatus educationStatus=new ResumeStatusManager().getEducationCompleteStatus(resume);
		CompleteStatus expStatus=new ResumeStatusManager().getWorkingExperienceStatus(resume);
		CompleteStatus skillLangStatus=new ResumeStatusManager().getSkillLanguageStatus(resume);
		CompleteStatus skillComStatus=new ResumeStatusManager().getSkillComputerStatus(resume);
		CompleteStatus strengthStatus=new ResumeStatusManager().getStrengthStatus(resume);
		CompleteStatus aptitudeStatus=new ResumeStatusManager().getAptitudeStatus(resume);
		PropertiesManager propMgr=new PropertiesManager();
		if(personalStatus!=null)
		{
			request.setAttribute("personalStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		if(careerStatus!=null)
		{
			request.setAttribute("careerStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		if(targetJobStatus!=null)
		{
			request.setAttribute("targetJobStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		if(educationStatus!=null)
		{
			request.setAttribute("educationStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		if(expStatus!=null)
		{
			request.setAttribute("expStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
		if(skillLangStatus!=null || skillComStatus!=null)
		{
			if(idResume>0)
			{
				request.setAttribute("skillStatus",propMgr.getMessage(locale, "SECTION_SKILL_AND_LANGUAGE")+" "+propMgr.getMessage(locale, "PART_INCOMPLETE"));
				if(skillLangStatus!=null&&skillComStatus==null)
				{
					request.setAttribute("skillStatus",propMgr.getMessage(locale, "GLOBAL_LANGUAGE")+" "+propMgr.getMessage(locale, "PART_INCOMPLETE"));
				}
				if(skillComStatus!=null&&skillLangStatus==null)
				{
					request.setAttribute("skillStatus",propMgr.getMessage(locale, "SKILLS")+" "+propMgr.getMessage(locale, "PART_INCOMPLETE"));
				}
			}
		}
		if(strengthStatus!=null)
		{
			if(Util.getStr(strengthStatus.getSection()).equals("STRENGTH_RANK_INCOMPLEASE")) 
			{
				request.setAttribute("strengthStatus",propMgr.getMessage(locale, "PART_INCOMPLETE")+" / "+propMgr.getMessage(locale, "PLEASE_RANK"));
			}
			else
			{
				request.setAttribute("strengthStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
			}
		}
		if(aptitudeStatus!=null)
		{
			request.setAttribute("aptitudeStatus",propMgr.getMessage(locale, "PART_INCOMPLETE"));
		}
	 %>
	<script>
	
		var previews=[	'previewResumeStatus','previewPersonal','previewCareerObjective','previewTargetJob','previewEducation',
						'previewParttime','previewWorking','previewSkill','previewStrength','previewAptitude',
						'','previewTraining','previewAward','previewCertificate','previewActivities',
						'previewReferences','previewAdditionalInfo','previewMilitary','previewFamilyBackground','previewEthnicity'];
						
		$(document).ready(function()
		{ 
			$( "a" ).click(function() {
				var idx=$(this).attr('rel');
				if($.trim($('#layer_'+idx).html())==""&&previews[idx]!="")
				{ 
					var d =new Date();
					$.get('/view/subview/'+previews[idx]+'.jsp?idResume=<c:out value="${resume.idResume}"/>&t='+d.getTime(),function(data) 
					{
						$('#layer_'+idx).html(data);
					});
				}
				$('#layer_'+idx).collapse('show');
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
	</style>
	<div class="section_header alignCenter">
	<c:choose>
		<c:when test="${idResume eq 0}">
			<h3><fmt:message key="SYNC_UPDATING"/> : <fmt:message key="SECTION_ACCOUNT"/></h3><br/>
		</c:when>
		<c:otherwise>
			<h3><fmt:message key="SYNC_UPDATING"/> : <c:out value="${resume.resumeName}"></c:out></h3><br/>
		</c:otherwise>
	</c:choose>
		
	</div>
	<div class="panel-group" id="accordion">
		<b><font style="color:#ad70ad;"><fmt:message key="SECTION_PREVIEW_DETAIL_PAGE"/></font></b>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_20" rel="20" class="topic" > <fmt:message key="SECTION_ACCOUNT_LOGIN"/> </a>
			</div>
			<div id="layer_20" class="collapse">
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-3">
							<fmt:message key="SECTION_ACCOUNT_LOGIN"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3">
							<label><fmt:message key="GLOBAL_EMAIL_USERNAME"/></label>
						</div>
						<div class="col-sm-9">
							<c:out value="${jobseeker.username}"/>&nbsp;<br>
							(<a href="/SRIX?view=changeUsername"><font color="blue"><fmt:message key="PHOTO_EDIT"/></font></a>)
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3">
							<label><fmt:message key="GLOBAL_PASSWORD"/></label>
						</div>
						<div class="col-sm-9">
							<label class="answer">*********</label>&nbsp;<a href="/SRIX?view=changePassword"><font color="blue"><fmt:message key="CHANGE_PASSWORD"/></font></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_0" rel="0" class="topic" > <fmt:message key="SECTION_RESUME_STATUS"/> </a>
			</div>
			<div id="layer_0" class="panel-collapse collapse"></div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_1" rel="1" class="topic" > <fmt:message key="SECTION_PERSONAL_DATA"/><p  class="incomplete"><c:out value="${personalStatus}"/></p></a>
			</div>
			<div id="layer_1" class="panel-collapse collapse"></div>
		</div>
		 <c:if test="${idResume !=0 }">
			 <div class="panel panel-default">
				<div class="panel-heading">
					<a data-toggle="collapse" data-parent="#accordion" href="layer_2" rel="2" class="topic" > <fmt:message key="CAREEROBJECTIVE"/><p  class="incomplete"><c:out value="${careerStatus}"/></p></a>
				</div>
				<div id="layer_2" class="panel-collapse collapse"></div>
			</div>
		</c:if>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_3"  rel="3" class="topic" > <fmt:message key="TARGETJOB"/><p class="incomplete"><c:out value="${targetJobStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_3"></div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_4" rel="4" class="topic" > <fmt:message key="SECTION_EDUCATION"/><p class="incomplete"><c:out value="${educationStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_4"></div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_5" rel="5" class="topic" > <fmt:message key="SECTION_PARTTIME"/></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_5"></div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_6" rel="6" class="topic" > <fmt:message key="WORKEXP_TITLE"/><p  class="incomplete"><c:out value="${expStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_6"></div>
		</div>
		<!--  
		<c:if test="${idResume !=0 }">
			<div class="panel panel-default">
				<div class="panel-heading">
					<a data-toggle="collapse" data-parent="#accordion" href="layer_7" rel="7" class="topic" > <fmt:message key="SECTION_SKILL_AND_LANGUAGE"/><p  class="incomplete"><c:out value="${skillStatus}"/></p></a>
				</div>
				<div  class="panel-collapse collapse" id="layer_7"></div>
			</div>
		</c:if>
		-->
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_7" rel="7" class="topic" > <fmt:message key="SECTION_SKILL_AND_LANGUAGE"/><p  class="incomplete"><c:out value="${skillStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_7"></div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_8" rel="8" class="topic" > <fmt:message key="SECTION_STRENGTH"/><p  class="incomplete"><c:out value="${strengthStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_8"></div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a data-toggle="collapse" data-parent="#accordion" href="layer_9" rel="9" class="topic" > <fmt:message key="SECTION_APTITUDE"/><p  class="incomplete"><c:out value="${aptitudeStatus}"/></p></a>
			</div>
			<div  class="panel-collapse collapse" id="layer_9"></div>
		</div>
		<c:if test="${idResume !=0 }">
			<div class="panel panel-default">
				<div class="panel-heading">
					<a data-toggle="collapse" data-parent="#accordion" href="layer_10" rel="10" class="topic" ><fmt:message key="PREVIEW_OPTIONAL"/></a>
				</div>
				<div class="panel-collapse collapse in" id="layer_10">
					<div class="panel-group" id="accordion2" style="width:90%;margin:auto;">
					<br/>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_11" rel="11" class="topic" >&nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="SECTION_TRAINING"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_11"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_12" rel="12" class="topic" > &nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="AWARD_HEADER"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_12"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_13" rel="13" class="topic" >&nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="CERT_HEADER"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_13"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_14" rel="14" class="topic" >&nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="SECTION_ACTIVITY"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_14"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_15" rel="15" class="topic" >&nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="REFERENCES"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_15"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_16" rel="16" class="topic" > &nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="SECTION_ADDITIONAL_INFO"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_16"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_17" rel="17" class="topic" > &nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="ADDITION"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_17"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<a data-toggle="collapse" data-parent="#accordion2" href="layer_18" rel="18" class="topic" >&nbsp;&nbsp;-&nbsp;&nbsp;<fmt:message key="SECTION_FAMILY_BACKGROUND"/></a>
							</div>
							<div  class="panel-collapse collapse" id="layer_18"></div>
						</div>
						<br/>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</c:if>
<br/><br/>
