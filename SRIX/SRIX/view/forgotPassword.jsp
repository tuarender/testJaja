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
		<fmt:message key="FORGOT_PASSWORD_DETAIL"/>
		<div class="form-group">
			<font style="color: red;">*</font><font class="caption"><fmt:message
					key="GLOBAL_PHONE_TYPE_MOBILE_NUMBER" /></font> <input type="text"
				class="form-control" id="mobile" name="mobile"
				placeholder="Enter mobile phone">
		</div>
		<div align="center">
			<button type="submit" name="submit" id="submit"
				class="btn btn-lg btn-success">
				<fmt:message key="CANCEL_JM_SUBMIT" />
			</button>
		</div>
	</div>
</form>