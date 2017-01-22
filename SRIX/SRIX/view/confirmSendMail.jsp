<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
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
	
<html>
<body>
	<h3 align="center"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/></h3><br>
	<div align="center">
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				รหัสผ่านของคุณถูกส่งไปที่อีเมล กรุณาเช็คอีเมลของท่าน <br>
				กลับเข้าสู่หน้าหลัก <a href="/SRIX?agent=mobile">คลิกที่นี่</a>
			</c:when>
			<c:otherwise>
				Your password has been sent to your email address. Please check in your mailbox.<br>
				<a href="/SRIX?agent=mobile">Click here</a> to go www.superresume.com
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>