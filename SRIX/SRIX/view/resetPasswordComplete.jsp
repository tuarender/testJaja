<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>

<div align="center">
	<c:choose>
		<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
			<b class="section_header">ตั้งรหัสผ่านใหม่เรียบร้อยแล้ว</b><br />
			<a href="/SRIX?agent=mobile">คลิกที่นี่</a> เพื่อเข้าสู่ www.superresume.com <br>
			<a href="http://www.jobtopgun.com">คลิกที่นี่</a> เพื่อเข้าสู่ www.jobtopgun.com<br /><br />
		</c:when>
		<c:otherwise>
			<b class="section_header">Password Update Successfully</b><br />
			<a href="/SRIX?agent=mobile">Click here</a> to Login www.superresume.com <br>
			<a href="http://www.jobtopgun.com">Click here</a> to Login www.jobtopgun.com<br /><br />
		</c:otherwise>
	</c:choose>
</div>