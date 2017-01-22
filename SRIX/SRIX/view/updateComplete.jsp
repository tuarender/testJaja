<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.getInt(request.getParameter("idResume"));
	String idSession=Encoder.getEncode(request.getSession().getId());
	Resume resume = new ResumeManager().get(idJsk, idResume);
%>
<script>
	$(document).ready(function(){
		
		$('#confirmBtn').click(function(){
			window.location.href="/SRIX?view=naming&cmd=CREATE&sequence=1&jSeesion=<%=idSession%>";
		});
		
		$('#cancelBtn').click(function(){
			window.location.href="/SRIX?view=home&jSeesion=<%=idSession%>";
		});
		
	}); 
</script>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	<br>
<div>
	<div class="col-sm-12 col-xs-12" style="text-align:center;font-size:16px;">
		<span class="section_header"><fmt:message key="UPGRADE_COMPLETE_RESUME1"/></span>
	</div>
	<div class="col-sm-12 col-xs-12" style="height:20px;">&nbsp;</div>
	<div class="col-sm-12 col-xs-12" style="text-align:center;font-size:16px;">
		<span class="section_header"><fmt:message key="UPGRADE_COMPLETE_RESUME2"/></span>
	</div>
	<div class="col-sm-12 col-xs-12" style="height:70px;">&nbsp;</div>
	<div class="col-sm-12 col-xs-12" style="text-align:center;">
		<div class='hidden-xs col-sm-2'>&nbsp;</div>
		<div class="col-xs-12 col-md-4" style="text-align:center;">
			<a id="cancelBtn" class="btn btn-default" ><fmt:message key="GLOBAL_CANCEL"/></a>
		</div>
		<div class="col-sm-12 col-xs-12 visible-xs visible-sm" style="height:30px;">&nbsp;</div>
		<div class="col-xs-12 col-md-4" style="text-align:center;">
			<a id="confirmBtn" class="btn btn-primary" ><fmt:message key="GLOBAL_NEXT"/></a>
		</div>
		<div class='hidden-xs col-sm-2'>&nbsp;</div>
	</div>
</div>
