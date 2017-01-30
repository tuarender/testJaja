<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}" />
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}" />
	</c:otherwise>
</c:choose>

<form method="post" action="/ResetPasswordServ?action=retrieveUser">
	<div class="contener">
		<h3 align="center" class="section_header">
			<fmt:message key="GLOBAL_FORGOT_PASSWORD" />
		</h3>
		<jsp:include page="common/errors.jsp" />
		<div class="form-group">
			<fmt:message key="FORGOT_PASSWORD_DETAIL"/>
		</div>
		<div class="form-group">
			<font class="caption requiredMark"><fmt:message
					key="GLOBAL_PHONE_TYPE_MOBILE_NUMBER" /></font> <input type="text"
				class="form-control" id="searchIdentity" name="searchIdentity"
				placeholder="<fmt:message key="FORGOT_PASSWORD_INPUT_PLACEHOLDER" />">
		</div>
		<div align="center">
			<button type="submit" name="submit" id="submit"
				class="btn btn-lg btn-success">
				<fmt:message key="CANCEL_JM_SUBMIT" />
			</button>
		</div>
	</div>
</form>