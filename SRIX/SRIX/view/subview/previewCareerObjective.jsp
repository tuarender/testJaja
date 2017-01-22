<%@page import="com.topgun.util.PropertiesManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.CareerObjective"%>
<%@page import="com.topgun.resume.CareerObjectiveManager"%>
<%@page import="java.util.List"%>
 <%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	PropertiesManager propMgr=new PropertiesManager();
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{
		List<CareerObjective> careers=new CareerObjectiveManager().getAll(idJsk,idResume);
		request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idResume",idResume);
		request.setAttribute("careers",careers);
		request.setAttribute("careersSize",careers.size());
	}
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<c:choose>
		<c:when test="${careersSize gt 0}">
			<div class="row" style="padding:10pt;">
				<c:forEach items="${careers}" var="careers" varStatus="loop">
					<c:if test="${not empty careers}">
						<c:set var="careers" scope="request" value="${careers}"/>	
						<c:choose>
							<c:when test="${careers.idCareerObjective gt 0}">
								<%
									CareerObjective cr = (CareerObjective)request.getAttribute("careers");
									com.topgun.shris.masterdata.CareerObjective career=MasterDataManager.getCareerObjective(cr.getIdCareerObjective(),resume.getIdLanguage());
									request.setAttribute("careerObjName",career!=null?career.getCareerobjName():"");
								%>
								<c:if test="${not empty careerObjName}">
									<div class="col-xs-12 col-sm-12 answer">
										<label for="careerObjName">
											<c:out value='${loop.index+1}'/>. <c:out value='${careerObjName}'/>
										</label>
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								<div class="col-xs-12 col-sm-12 answer">
									<label for="otherObjective">
										<c:out value='${loop.index+1}'/>. <c:out value='${careers.otherObjective}'/>
									</label>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<div class="col-xs-12 col-sm-12" style="text-align:right;">
					<a href="/SRIX?view=career&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row" style="text-align:right;padding:10pt;">
				<div class="col-xs-12 col-sm-12">
					<a href="/SRIX?view=career&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>