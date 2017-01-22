<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.Random"%>
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
	Random rand = new Random();
	int mascotId = rand.nextInt(9) + 1;
	request.setAttribute("mascotId",mascotId);
	int hasFaq=0;
	request.setAttribute("hasFaq", hasFaq);
%>
			
<c:if test="${(param.view eq 'experience') or (param.view eq 'aptitude') or (param.view eq 'education') or (param.view eq 'register') or (param.view eq 'registerTracking') or (param.view eq 'additionalInfo')}">
	<c:set var="hasFaq" value="1" scope="request"/>
	<div style="background-color:#eee;padding:20px;">
		<div style="font-size:16pt; color:#8080bc"><fmt:message key="GLOBAL_HELP"/></div>
		<div style="font-size:12pt; color:#ad70ad">&nbsp;&nbsp;FAQ</div>
		<div><img class="img-responsive" src="/images/faq.jpg"/></div>
		<div style="color:#ad70ad; font-size:14px;">FAQ</div>
		<div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:choose>
				<c:when test="${param.view eq 'experience'}">
					<fmt:message key="FAQ_EXPERIENCE"></fmt:message>
				</c:when>
				<c:when test="${param.view eq 'register'}">
					<fmt:message key="FAQ_REGISTER"></fmt:message>
				</c:when>
				<c:when test="${param.view eq 'registerTracking'}">
					<fmt:message key="FAQ_REGISTER"></fmt:message>
				</c:when>
				<c:when test="${param.view eq 'education'}">
					<fmt:message key="FAQ_EDUCATION"></fmt:message>
				</c:when>
				<c:when test="${param.view eq 'additionalInfo'}">
					<fmt:message key="FAQ_ADDITIONAL_INFO"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="FAQ_APTITUDE"></fmt:message>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</c:if>
				
<c:if test="${	(param.view eq 'naming') or (param.view eq 'home') or (param.view eq 'apply') or 
				(param.view eq 'awards') or (param.view eq 'activity') or (param.view eq 'career') or 
				(param.view eq 'certificate') or (param.view eq 'personal')  or (param.view eq 'reference') or 
				(param.view eq 'resumeInfo') or (param.view eq 'skillComputer') or (param.view eq 'skillLanguage') or 
				(param.view eq 'strength') or (param.view eq 'strengthDetail') or (param.view eq 'targetJob') or 
				(param.view eq 'training')  or (param.view eq 'uploadPhoto')  or (param.view eq 'resetPassword') or 
				(param.view eq 'resetPasswordComplete') or (param.view eq 'syncComplete')}">
	<c:set var="hasFaq" value="1" scope="request"/>			
	<div style="background-color:#eee;margin:0px;">
		<c:if test="${param.view ne 'experience'}">					
			<div style="font-size:16pt; color:#8080bc"><fmt:message key="GLOBAL_HELP"/></div>
		</c:if>
		<div style="font-size:12pt; color:#ad70ad">&nbsp;&nbsp;Tips</div>
		<table style="border-collapse:collapse; width:266px;margin: 0 auto !important; float: none !important;">
		<tr style="height:18px;">
			<td colspan="3" style="background:url('/images/tips_box/top.png'); no-repeat; width:266px;"></td>
		</tr>
		<tr>
			<td style="background:url('/images/tips_box/left.png'); width:14px;"></td>
			<td>
				<table>
					<tr>
						<td class="suggestion">Tips</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
								<c:when test="${param.view eq 'naming'}">
									<fmt:message key="TIPS_NAMING"/>
								</c:when>
								<c:when test="${param.view eq 'home'}">
									<fmt:message key="TIPS_HOME"/>
								</c:when>
								<c:when test="${param.view eq 'apply'}">
									<fmt:message key="TIPS_APPLY"/>
								</c:when>
								<c:when test="${param.view eq 'awards'}">
									<fmt:message key="TIPS_AWARD"/>
								</c:when>
								<c:when test="${param.view eq 'activity'}">
									<fmt:message key="TIPS_ACTIVITY"/>
								</c:when>
								<c:when test="${param.view eq 'career'}">
									<fmt:message key="TIPS_CAREER_OBJECTIVE"/>
								</c:when>
								<c:when test="${param.view eq 'certificate'}">
									<fmt:message key="TIPS_CERTIFICATE"/>
								</c:when>
								<c:when test="${param.view eq 'personal'}">
									<fmt:message key="TIPS_PERSONAL"/>
								</c:when>
								<c:when test="${param.view eq 'reference'}">
									<fmt:message key="TIPS_REFERENCES"/>
								</c:when>
								<c:when test="${param.view eq 'resumeInfo'}">
									<fmt:message key="TIPS_RESUME_INFO"/>
								</c:when>
								<c:when test="${param.view eq 'skillComputer'}">
									<fmt:message key="TIPS_SKILL_COMPUTER"/>
								</c:when>
								<c:when test="${param.view eq 'skillLanguage'}">
									<fmt:message key="TIPS_SKILL_LANGUAGE"/>
								</c:when>
								<c:when test="${param.view eq 'strength'}">
									<fmt:message key="TIPS_STRENGTH"/>
								</c:when>
								<c:when test="${param.view eq 'strengthDetail'}">
									<fmt:message key="TIPS_STRENGTH_DETAIL"/>
								</c:when>
								<c:when test="${param.view eq 'targetJob'}">
									<fmt:message key="TIPS_TARGETJOB"/>
								</c:when>
								<c:when test="${param.view eq 'training'}">
									<fmt:message key="TIPS_TRAINING"/>
								</c:when>
								<c:when test="${param.view eq 'uploadPhoto'}">
									<fmt:message key="TIPS_UPLOAD_PHOTO"/>
								</c:when>
								<c:when test="${param.view eq 'resetPassword'}">
									<fmt:message key="TIPS_RESET_PASSWORD"/>
								</c:when>
								<c:when test="${param.view eq 'resetPasswordComplete'}">
									<fmt:message key="TIPS_RESET_PASSWORD_COMPLETE"/>
								</c:when>
								<c:when test="${param.view eq 'syncComplete'}">
									<fmt:message key="TIPS_RESUME_INFO"/>
								</c:when>
								<c:otherwise>
									<fmt:message key="TIPS_EXPERIENCE"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</td>
			<td style="background:url('/images/tips_box/right.png'); width:14px;"></td>
		</tr>
		<tr style="height:31px;">
			<td colspan="3" style="background:url('/images/tips_box/bottom.png'); no-repeat; width:266px;"></td>
		</tr>
		<tr>
			<td colspan="3">
				<img class="img-responsive pull-right" style="padding-right:10px;" src="/images/sr<c:out value="${mascotId}"/>.png">
			</td>
		</tr>
		</table>
	</div>
</c:if>

<c:if test="${hasFaq eq 0}">
	<div class="hidden-xs" style="min-height:500px;"></div>
</c:if>
