<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.CertSubFieldLanguage"%>
<%@page import="com.topgun.shris.masterdata.CertFieldLanguage"%>
<%@page import="com.topgun.resume.CertificateManager"%>
<%@page import="com.topgun.resume.Certificate"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<% 
	//PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{
		String ln=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String ct=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Certificate> certificateList =  new CertificateManager().getAll(resume.getIdJsk(),resume.getIdResume());
		request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idResume",idResume);
		request.setAttribute("ln",ln);
		request.setAttribute("ct",ct);
		request.setAttribute("certificateList",certificateList);
	}
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<c:choose>
		<c:when test="${not empty certificateList}">
			<div class="row" style="padding:10pt;">
				<c:forEach items="${certificateList}" var="certificate" varStatus="loop">
					<c:if test="${not empty certificate}">
						<c:set var="certificate" scope="request" value="${certificate}"/>	
						<% 
							Certificate certificate = (Certificate)request.getAttribute("certificate");
							CertFieldLanguage cf = MasterDataManager.getCertFieldLanguage(certificate.getIdCertField(), resume.getIdLanguage());
							request.setAttribute("certFieldName",cf!=null?cf.getCertFieldName():"");
							CertSubFieldLanguage sf = MasterDataManager.getCertSubFieldLanguage(certificate.getIdCertField(), resume.getIdLanguage(), certificate.getIdCertSubfield());
							request.setAttribute("subNameField",sf!=null?sf.getCertSubfieldName():"");
						%>
						<!-- cername -->
						<div class="col-xs-12 col-sm-12 answer">
							<c:if test="${empty certFieldName && empty subNameField}"> <!-- idCertField=-1 && idCertSubfield =-1 -->
									<label for="cerName1">
										<c:out value='${loop.index+1}'/>. <c:out value='${certificate.cerName}'/>
									</label>
							</c:if>
							<c:if test="${not empty certFieldName && not empty subNameField}"> <!-- idCertField!=-1 && idCertSubfield !=-1 -->
									<label for="cerName2">
										<c:out value='${loop.index+1}'/>. <c:out value='${certFieldName}'/> / <c:out value='${subNameField}'/>
									</label>
							</c:if>
							<c:if test="${not empty certFieldName && empty subNameField}"> <!-- idCertField!=-1 && idCertSubfield =-1 -->
									<label for="cerName3">
										<c:out value='${loop.index+1}'/>. <c:out value='${certFieldName}'/> / <c:out value='${certificate.cerName}'/>
									</label>
							</c:if>
							<c:if test="${not empty certificate.institution}">
								<label for="institution">
									, <c:out value='${certificate.institution}'/>
								</label>
							</c:if>
						</div>
						<!-- //cername -->
						<div class="col-xs-12 col-sm-12 answer">
							<fmt:formatDate value="${certificate.cerDate}" dateStyle="full"/>
						</div>
						<c:if test="${not empty certificate.detail}">
							<div class="col-xs-12 col-sm-12 answer">
								<c:out value='${certificate.detail}'/>
							</div>
						</c:if>
					</c:if>
				</c:forEach>
				<div class="col-xs-12 col-sm-12" style="text-align:right;">
					<a href="/SRIX?view=certificate&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row" style="text-align:right;padding:10pt;">
				<div class="col-xs-12 col-sm-12">
					<a href="/SRIX?view=certificate&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>
