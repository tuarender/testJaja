<%@page import="com.topgun.resume.AdditionalInfo"%>
<%@page import="com.topgun.resume.AdditionalInfoManager"%>
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
		AdditionalInfo addInfo=new AdditionalInfoManager().get(idJsk, idResume);
		request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idResume",idResume);
		request.setAttribute("addInfo",addInfo);
	}
%>

<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<c:choose>
		<c:when test="${not empty addInfo}">
			<div class="row" style="padding:10pt;">
				<c:if test="${not empty addInfo.additional1}">
					<div class="col-xs-12 col-sm-12 answer">
						<label for="additional1">
							<c:out value='${addInfo.additional1}'/>
						</label>
					</div>
				</c:if>
				<c:if test="${not empty addInfo.additional2}">
					<div class="col-xs-12 col-sm-12 answer">
						<label for="additional2">
							<c:out value='${addInfo.additional2}'/>
						</label>
					</div>
				</c:if>
				<c:if test="${not empty addInfo.additional3}">
					<div class="col-xs-12 col-sm-12 answer">
						<label for="additional3">
							<c:out value='${addInfo.additional3}'/>
						</label>
					</div>
				</c:if>
				<c:if test="${not empty addInfo.additional4}">
					<div class="col-xs-12 col-sm-12 answer">
						<label for="additional4">
							<c:out value='${addInfo.additional4}'/>
						</label>
					</div>
				</c:if>
				<div class="col-xs-12 col-sm-12" style="text-align:right;">
					<a href="/SRIX?view=additionalInfo&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row" style="text-align:right;padding:10pt;">
				<div class="col-xs-12 col-sm-12">
					<a href="/SRIX?view=additionalInfo&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>
