<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%
	int idResume = Util.StrToInt(request.getParameter("idResume"));
	request.setAttribute("idResume",idResume);
%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<div class="row alignCenter" style="margin:25px 0px;">
	<fmt:message key="FILE_UPLOAD_FINISH"/>
</div>
<div class="row alignCenter"  style="margin-bottom:50px">
	<input type="button" id="submit" value="<fmt:message key="GLOBAL_NEXT"/>" class="btn btn-lg btn-success">
</div>
<script>
var idResume = '<c:out value="${idResume}"/>';
$(document).ready(function(){
	$('#submit').click(function() {
		window.location.href = "?view=registerFinish&idResume="+idResume;
	});
});
</script>