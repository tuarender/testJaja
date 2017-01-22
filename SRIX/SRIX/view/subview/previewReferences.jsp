<%@page import="com.topgun.resume.Reference"%>
<%@page import="com.topgun.resume.ReferenceManager"%>
<%@page import="java.util.List"%>
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
		List<Reference> reference=new ReferenceManager().getAll(resume.getIdJsk(), resume.getIdResume());
		request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idResume",idResume);
		request.setAttribute("referenceList",reference);
		request.setAttribute("referenceSize",reference.size());
	}
%>

<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<c:choose>
		<c:when test="${referenceSize gt 0}">
			<div class="row" style="padding:10pt;">
				<c:forEach items="${referenceList}" var="reference" varStatus="loop">
					<c:if test="${not empty reference.refName}">
						<div class="col-xs-12 col-sm-12 answer">
							<label for="ref">
								<c:out value='${loop.index+1}'/>. <c:out value='${reference.refName}'/>
								<c:choose>
									<c:when test="${reference.refType eq 1}">
										(<fmt:message key="REFERENCES_RELATIONSHIP_PREVIOUS"/>)
									</c:when>
									<c:when test="${reference.refType eq 2}">
										(<fmt:message key="REFERENCES_RELATIONSHIP_CURRENT"/>)
									</c:when>
									<c:when test="${reference.refType eq 3}">
										(<fmt:message key="REFERENCES_RELATIONSHIP_PROFESSOR"/>)
									</c:when>
									<c:when test="${reference.refType eq 4 && not empty reference.othRefType}">
										(<c:out value='${reference.othRefType}'/>)
									</c:when>
									<c:when test="${reference.refType eq 5}">
										(<fmt:message key="REFERENCES_RELATIONSHIP_BUSINESS"/>)
									</c:when>
									<c:otherwise>
										(<fmt:message key="REFERENCES_RELATIONSHIP_BUSINESS_COLLEAGUE"/>)
									</c:otherwise>
								</c:choose>
							</label>
						</div>
						<div class="col-xs-12 col-sm-12 answer">
							<c:if test="${not empty reference.refTitle}">
								<c:out value='${reference.refTitle}'/>
							</c:if>
							<c:if test="${not empty reference.refCompany}">
								<c:choose>
									<c:when test="${not empty reference.refTitle}">
										, <c:out value='${reference.refCompany}'/>
									</c:when>
									<c:otherwise>
										<c:out value='${reference.refCompany}'/>
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
						<div class="col-xs-12 col-sm-12 answer">
							<c:if test="${not empty reference.refTel}">
								<fmt:message key="PREVIEW_TEL"/> <c:out value='${reference.refTel}'/>
							</c:if>
						</div>
					</c:if>
				</c:forEach>
				<div class="col-xs-12 col-sm-12" style="text-align:right;">
					<a href="/SRIX?view=reference&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row" style="text-align:right;padding:10pt;">
				<div class="col-xs-12 col-sm-12">
					<a href="/SRIX?view=reference&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>