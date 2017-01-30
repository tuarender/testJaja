<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.topgun.common.ErrorBean"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<c:import url="/config.jsp" charEncoding="UTF-8" />

<%
	List<ErrorBean> errors = (List<ErrorBean>) request.getAttribute("errors");
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}" />
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}" />
	</c:otherwise>
</c:choose>
<c:if test="${errors!=null}">
	<c:if test="${not empty errors}">
		<div class="errorContainer show" align="left">
			<b><fmt:message key="GLOBAL_PLEASE_READ" /></b>
			<ol>
				<c:forEach var="error" items="${errors}">
					<li><c:out value="${error.detail}" escapeXml="false" /></li>
				</c:forEach>
			</ol>
		</div>
	</c:if>
</c:if>
