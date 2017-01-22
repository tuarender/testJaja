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
<!-- Google conversion page & facebook conversion page -->
<!-- Google Code for Total Register Conversion Page (Jobtopgun) -->
<script type="text/javascript">
	/* <![CDATA[ */
	var google_conversion_id = 1013159874;
	var google_conversion_language = "en";
	var google_conversion_format = "3";
	var google_conversion_color = "ffffff";
	var google_conversion_label = "0tAPCL7j4QIQwq-O4wM";
	var google_conversion_value = 0;
	if (1) {
	  google_conversion_value = 1;
	}
	/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js"></script>
<noscript>
	<div style="display:inline;">
	<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/1013159874/?value=1&amp;label=0tAPCL7j4QIQwq-O4wM&amp;guid=ON&amp;script=0"/>
	</div>
</noscript>

<!-- Google Code for Register Conversion Page (Superresume) -->
<script type="text/javascript">
	/* <![CDATA[ */
	var google_conversion_id = 996733952;
	var google_conversion_language = "en";
	var google_conversion_format = "3";
	var google_conversion_color = "ffffff";
	var google_conversion_label = "71WeCJixgQQQgOij2wM";
	var google_conversion_value = 0;
	/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/996733952/?value=0&amp;label=71WeCJixgQQQgOij2wM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>
<script>
	$(document).ready(function()
	{
		var container = $('div.errorContainer');
		$('#experienceFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
			invalidHandler: function(form, validator) 
			{
				$('html, body').animate({scrollTop: '0px'}, 300);      
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
	       			url: '/ExperienceServ',
	       			data: $('#experienceFrm').serialize(),
	       			async:false,
	       			success: function(data)
	       			{
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					var caseExp=obj.caseExp;
	       					var workType=obj.workType;
	       					if(caseExp==5)
	       					{
	       						window.location.href='/SRIX?view=education&idResume=0&sequence=1';
	       					}
	       					else
	       					{
	       						window.location.href='/SRIX?view=experience&idResume=0&sequence=1&caseExp='+caseExp+'&workType='+workType;
	       					}
	       				} 
	       			}
	     		});
	     		return false;
			}
		});
	});
</script>


<div class="row" align="center">
	<div class="section_header alignCenter">
	   	<h3>
			<fmt:message key="EXPERIENCE_WELCOME">
				<c:if test="${not empty resume}">
					<c:choose>
						<c:when test="${resume.idLanguage eq 38}">
							<fmt:param value="${resume.firstNameThai}"></fmt:param>
						</c:when>
						<c:otherwise>
							<fmt:param value="${resume.firstName}"></fmt:param>
						</c:otherwise>
					</c:choose>
				</c:if>
			</fmt:message><br>
			<fmt:message key="EXPERIENCE_BEGIN"/>
	   	</h3>
	</div>	
</div>
<div>
	&nbsp;
</div>
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<form id="experienceFrm" name="experienceFrm" class="form-horizontal">
		<input type="hidden" name="idResume" id="idResume" value="0"/>
		<input type="hidden" name="service" id="service" value="expQuestion"/>
		<div class="row" > 
			<div class="col-lg-1 col-md-0 col-sm-0 col-xs-0"></div>
			<div class="col-lg-11 col-md-12 col-sm-12 col-xs-12">
				<ul style="padding-left:0px;">
					<li>
						<fmt:message key="EXPERIENCE_ASK_FULL_TIME"/>
						<div class="row">
							<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
								<input type="radio" id="quest11" name="fullTime" value="1" class="required"  onClick="ga('send', 'event', { eventCategory: 'Engage-experienceQuestion', eventAction: 'ClickOn', eventLabel: 'fullTime_yes'});" title='<fmt:message key="EXPERIENCE_FULL_TIME"/>' >&nbsp;&nbsp;
								<label for="quest11" class="answer"><fmt:message key="EXPERIENCE_YES"/></label>
							</div>
							<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
								<input type="radio" id="quest12" name="fullTime" value="0" class="required"  onClick="ga('send', 'event', { eventCategory: 'Engage-experienceQuestion', eventAction: 'ClickOn', eventLabel: 'fullTime_no'});" title='<fmt:message key="EXPERIENCE_FULL_TIME"/>' >&nbsp;&nbsp;		
								<label for="quest12" class="answer"><fmt:message key="EXPERIENCE_NO"/></label>
							</div>
						</div>
					</li>
					<li>
						<fmt:message key="EXPERIENCE_ASK_PART_TIME"/>
						<div class="row">
							<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
								<input type="radio" id="quest21" name="parttime" value="1" class="required"  onClick="ga('send', 'event', { eventCategory: 'Engage-experienceQuestion', eventAction: 'ClickOn', eventLabel: 'parttime_yes'});" title='<fmt:message key="EXPERIENCE_PART_TIME"/>' >&nbsp;&nbsp;
								<label for="quest21" class="answer"><fmt:message key="EXPERIENCE_YES"/></label>
							</div>
							<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
								<input type="radio" id="quest22" name="parttime" value="0" class="required"  onClick="ga('send', 'event', { eventCategory: 'Engage-experienceQuestion', eventAction: 'ClickOn', eventLabel: 'parttime_no'});" title='<fmt:message key="EXPERIENCE_PART_TIME"/>' >&nbsp;&nbsp;		
								<label for="quest22" class="answer"><fmt:message key="EXPERIENCE_NO"/></label>
							</div>
						</div>
					</li>
					<li>
						<fmt:message key="EXPERIENCE_ASK_INTERN"/>
						<div class="row">
							<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
								<input type="radio" id="quest31" name="intern" value="1" class="required"  onClick="ga('send', 'event', { eventCategory: 'Engage-experienceQuestion', eventAction: 'ClickOn', eventLabel: 'intern_yes'});" title='<fmt:message key="EXPERIENCE_INTERN_TIME"/>' >&nbsp;&nbsp;
								<label for="quest31" class="answer"><fmt:message key="EXPERIENCE_YES"/></label>
							</div>
							<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
								<input type="radio" id="quest32" name="intern" value="0" class="required"  onClick="ga('send', 'event', { eventCategory: 'Engage-experienceQuestion', eventAction: 'ClickOn', eventLabel: 'intern_no'});" title='<fmt:message key="EXPERIENCE_INTERN_TIME"/>' >&nbsp;&nbsp;		
								<label for="quest32" class="answer"><fmt:message key="EXPERIENCE_NO"/></label>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<br>
		<div class="row" align="center">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			 	<input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/next.png" onClick="ga('send', 'event', { eventCategory: 'Engage-experienceQuestion', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"/>
			</div>
		</div>
		<br>
	</form>