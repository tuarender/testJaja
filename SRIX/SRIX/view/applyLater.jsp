<%@page import="monfox.toolkit.snmp.agent.modules.SnmpV2Mib.SysOREntry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.TrackManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.Encoder"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	String idSession=Encoder.getEncode(session.getId());
	int idJsk = Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	request.setAttribute("idSession", idSession); 
	request.setAttribute("idJsk", idJsk);
%>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<div class="seperator"></div>
<div align="center">
	<div class="row">
			<c:choose>
				<c:when test="${sessionScope.SESSION_LOCALE eq 'en_TH'}">
					<img src="../images/sr_apps_en.png" width="100%">
				</c:when>
				<c:otherwise>
					<img src="../images/sr_apps_th.png" width="100%">
				</c:otherwise>
			</c:choose>
	</div>
	<div class="seperator"></div>
	<div class="seperator"></div>
	<div class="row">
		<div class="visible-xs">
			<div class="col-xs-12 col-sm-6">
				<a href='https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8'><img src="../images/app_store.png" width="60%"></a>
			</div>
			<div class="visible-xs col-xs-12">
				&nbsp;	
			</div>
			<div class="col-xs-12 col-sm-6">
				<a href='https://play.google.com/store/apps/details?id=com.topgun.jobtopgun'><img src="../images/google_play.png" width="60%" ></a>
			</div>
		</div>
		<div class="hidden-xs">
			<div class="col-xs-12 col-sm-6">
				<a href='https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8'><img src="../images/app_store.png" width="100%"></a>
			</div>
			<div class="col-xs-12 col-sm-6">
				<a href='https://play.google.com/store/apps/details?id=com.topgun.jobtopgun'><img src="../images/google_play.png" width="100%" ></a>
			</div>
		</div>
	</div>
</div>
	<div class="seperator"></div>
	<div class="row">
	<div align="center">
		<p><fmt:message key="APPLY_RESULT_HOW_TO"/>
			<a href="" data-toggle="modal" data-target="#myModal"><u><fmt:message key="SEND_CLICK_HERE"/></u></a>
		</p>
	</div>
	  <!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog">
	      <!-- Modal content-->
	      <div class="modal-content">
	       <button type="button" class="close" data-dismiss="modal" style="padding:10px;">&times;</button>
	       	 <br>
	        <div class="modal-body">
	          <p class="color7461a7 font_16"><b><fmt:message key="APPLY_RESULT_APP"/></b></p>
	         	 <li class="colorb37bb6"><b>iOS</b></li>
	          <div style="height:10px;"></div>
	          <p>1. <fmt:message key="APPLY_RESULT_APP_STEP1"/> <img src="../images/app_store.png" class="img-responsive" width="20%" style="display:inline;"></p>
	          <p>2. <fmt:message key="APPLY_RESULT_APP_STEP2"/></p>
	          <p>3. <fmt:message key="APPLY_RESULT_APP_STEP3"/></p>
	          <p>4. <fmt:message key="APPLY_RESULT_APP_STEP4"/></p>
	          <p>5. <fmt:message key="APPLY_RESULT_APP_STEP5"/></p>
	          <div class="seperator"></div>
	            <li class="colorb37bb6"><b>Android</b></li>
	          <div style="height:10px;"></div>
	          <p>1.<fmt:message key="APPLY_RESULT_APP_STEP1_ANDROID"/> <img src="../images/google_play.png" class="img-responsive" width="20%" style="display:inline;"></p>
	          <p>2.<fmt:message key="APPLY_RESULT_APP_STEP2"/></p>
	          <p>3.<fmt:message key="APPLY_RESULT_APP_STEP3"/></p>
	          <p>4.<fmt:message key="APPLY_RESULT_APP_STEP4"/></p>
	          <p>5.<fmt:message key="APPLY_RESULT_APP_STEP5"/></p>
	          
	        </div>
	      </div>
	    </div>
	  </div>
		<div align="center">
			<p><fmt:message key="APPLY_RESULT_DONE_DOWNLOAD"/><a href="http://www.jobtopgun.com/?idJsk=<c:out value="${idJsk }"/>&locale=<c:out value="${sessionScope.SESSION_LOCALE }"/>&jSession=<c:out value="${idSession}"/>"><u><fmt:message key="APPLY_RESULT_SEEKING_FOR_JOB"/></u></a></p>
		</div>
		<div align="center">
			<c:choose>
				<c:when test="${sessionScope.SESSION_LOCALE eq 'en_TH'}">
					View your Super Resume <a href="?view=home"><fmt:message key="CLICK_HERE"/></a>
				</c:when>
				<c:otherwise>
					ดู Super Resume ของคุณ <a href="?view=home"><u><fmt:message key="CLICK_HERE"/></u></a>
				</c:otherwise>
			</c:choose>
		</div>
</div>
