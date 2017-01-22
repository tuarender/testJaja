<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Encoder"%>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	Resume resume= new ResumeManager().get(idJsk, 0);
	String status= "FALSE";
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	String locale="th_TH";
	if(resume != null)
	{
		status= Util.getStr(new ResumeStatusManager().getRegisterStatus(resume));
		if(Util.getStr(resume.getLocale()).equals(""))
		{
			locale=Util.getStr(resume.getLocale());
		}
	}
	String idSession=Encoder.getEncode(session.getId());
	request.setAttribute("status", status); 
	request.setAttribute("idSession", idSession); 
	request.setAttribute("sequence", sequence);
	request.setAttribute("locale",locale);
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<c:if test="${((param.view eq 'experienceQuestion') or (param.view eq 'education') or (param.view eq 'experience') or (param.view eq 'targetJob') or (param.view eq 'personal') or (param.view eq 'strength') or (param.view eq 'strengthOrder') or (param.view eq 'aptitude') or (param.view eq 'aptitudeLevel') or (param.view eq 'aptitudeExtension') or (param.view eq 'aptitudeRank') or (param.view eq 'uploadPhoto') or (param.view eq 'uploadResume') or (param.view eq 'registerFinish') ) and (sequence eq 1) }">
	<!-- Menu Bar for Desktop -->
	<div class="progressBar">
			<c:choose>
				<c:when test="${param.view eq 'education'}">
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive2"><fmt:message key="EDU_HEADER"/></div>
				</c:when>
				<c:otherwise>
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;border-right:2px solid white;" class="main_menu barResponsive2"><fmt:message key="EDU_HEADER"/></div>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(param.view eq 'experience') or (param.view eq 'experienceQuestion')}">
					<c:choose>
						<c:when test="${locale eq 'th_TH'}">
							<div align="center" style="width:16%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive"><fmt:message key="WORKEXP_TITLE"/></div>
						</c:when>
						<c:otherwise>
							<div align="center" style="width:16%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive2"><fmt:message key="WORKEXP_TITLE"/></div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${locale eq 'th_TH'}">
							<div align="center" style="width:16%;height:40px;float:left;font-size:13px;border-right:2px solid white;" class="main_menu barResponsive"><fmt:message key="WORKEXP_TITLE"/></div>
						</c:when>
						<c:otherwise>
							<div align="center" style="width:16%;height:40px;float:left;font-size:13px;border-right:2px solid white;" class="main_menu barResponsive2"><fmt:message key="WORKEXP_TITLE"/></div>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${param.view eq 'targetJob'}">
					<c:choose>
						<c:when test="${locale eq 'th_TH'}">
							<div align="center" style="width:14%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive"><fmt:message key="SECTION_TARGET_JOB"/></div>
						</c:when>
						<c:otherwise>
							<div align="center" style="width:14%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive2"><fmt:message key="SECTION_TARGET_JOB"/></div>
						</c:otherwise>
					</c:choose>
					
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${locale eq 'th_TH'}">
							<div align="center" style="width:14%;height:40px;float:left;font-size:13px;border-right: 2px solid white;" class="main_menu barResponsive"><fmt:message key="SECTION_TARGET_JOB"/></div>
						</c:when>
						<c:otherwise>
							<div align="center" style="width:14%;height:40px;float:left;font-size:13px;border-right: 2px solid white;" class="main_menu barResponsive2"><fmt:message key="SECTION_TARGET_JOB"/></div>
						</c:otherwise>
					</c:choose>
					
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${param.view eq 'personal'}">
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive2"><fmt:message key="PREVIEW_RESUME_PERSONAL_DATA"/></div>
				</c:when>
				<c:otherwise>
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;border-right: 2px solid white;" class="main_menu barResponsive2"><fmt:message key="PREVIEW_RESUME_PERSONAL_DATA"/></div>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(param.view eq 'strength') or (param.view eq 'strengthOrder')}">
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive"><fmt:message key="MENU_STRENGTH"/></div>
				</c:when>
				<c:otherwise>
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;border-right: 2px solid white;" class="main_menu barResponsive"><fmt:message key="MENU_STRENGTH"/></div>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(param.view eq 'aptitude') or (param.view eq 'aptitudeLevel') or (param.view eq 'aptitudeExtension') or (param.view eq 'aptitudeRank')}">
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;background-color:#0f3759;border-right: 2px solid white;" class="main_menu_selected barResponsive"><fmt:message key="MENU_APTITUDE"/></div>
				</c:when>
				<c:otherwise>
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;border-right: 2px solid white;" class="main_menu barResponsive"><fmt:message key="MENU_APTITUDE"/></div>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(param.view eq 'uploadPhoto') or (param.view eq 'uploadResume') or (param.view eq 'registerFinish')}">
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;background-color:#0f3759;" class="main_menu_selected barResponsive2"><fmt:message key="PREVIEW_OPTIONAL"/></div>
				</c:when>
				<c:otherwise>
					<div align="center" style="width:14%;height:40px;float:left;font-size:13px;" class="main_menu barResponsive2"><fmt:message key="PREVIEW_OPTIONAL"/></div>
				</c:otherwise>
			</c:choose>
	</div>
</c:if>