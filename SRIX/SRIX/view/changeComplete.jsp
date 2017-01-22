<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<div>
	<c:choose>
	<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
		<h3 align="center" class="section_header">อัพเดทข้อมูลใหม่เรียบร้อยแล้ว</h3>
		<center class="caption">
			<a href="http://www.superresume.com?locale=th_TH">คลิกที่นี่</a> เพื่อเข้าสู่เว็บไซต์ <a href="http://www.superresume.com?locale=th_TH">www.superresume.com</a><br>
			<a href="http://www.jobtopgun.com?locale=th_TH">คลิกที่นี่</a> เพื่อเข้าสู่เว็บไซต์ <a href="http://www.jobtopgun.com?locale=th_TH">www.jobtopgun.com</a>	
		</center>
	</c:when>
	<c:otherwise>
		<h3><font class="section_header">Updated Successfully</font></h3><br>
		<center class="caption">
			<a href="http://www.superresume.com?locale=en_TH">Click Here</a> to Login <a href="http://www.superresume.com?locale=en_TH">www.superresume.com</a><br>	
			<a href="http://www.jobtopgun.com?locale=en_TH">Click Here</a> to Login <a href="http://www.jobtopgun.com?locale=en_TH">www.jobtopgun.com</a>
		</center>	
	</c:otherwise>
	</c:choose>
	<br />
</div>