
<%@page import="monfox.toolkit.snmp.agent.modules.SnmpV2Mib.SysOREntry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.TrackManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.resume.EmployerManager"%>
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
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idEmp=Util.getInt(request.getParameter("idEmp"));
	int idPos=Util.getInt(request.getParameter("idPos"));
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry = Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	int totalResume=0; 
	String applyDate=Util.getCurrentDate("yyyyMMdd", "en");
	
	 if(idEmp != -1  && idPos != -1)
	{ 
		totalResume=new TrackManager().countTrackByDatePosition(applyDate, idEmp, idPos );
	} 
	request.setAttribute("idEmp",idEmp);
	request.setAttribute("idPos",idPos);
	request.setAttribute("totalResume",totalResume);
	
	Employer emp = new EmployerManager().get(idEmp);
	request.setAttribute("emp",emp);
%>
<div class="form-group" align="center">
	<div><br /><br /></div>
	<div>
	<c:choose>
		<c:when test="${resume.idLanguage eq 38}"> 
			<p class="color8081be font_16"><fmt:message key="APPLY_RESULT_RESUME_SENT"/><c:out value="${emp.nameThai}"></c:out><fmt:message key="APPLY_RESULT_RESUME_DONE"/></p>
		</c:when> 
		<c:otherwise> 
			<p class="color8081be font_16"><fmt:message key="APPLY_RESULT_RESUME_SENT"/><c:out value="${emp.nameEng}"></c:out><fmt:message key="APPLY_RESULT_RESUME_DONE"/></p>
		</c:otherwise> 
	</c:choose>
		<c:if test="${idEmp ne '-1' && idPos ne '-1' && totalResume ne '0' }"> 
	   		<font class="color59595c font_16"><fmt:message key="SEND_RESUME_CANDIDATES"><fmt:param value="<font color='#0000FF'>${totalResume}</font>"/></fmt:message></font>
		 </c:if>  
	   	<div class="seperator"></div>
		<font class="caption"><fmt:message key="APPLY_RESULT_HISTORY"/>&nbsp;&nbsp;<a href="/SRIX?view=applyRecord"><u><fmt:message key="SEND_CLICK_HERE"/></u></a></font><br /> 
	</div>
</div>
<div>
	<label class="colorb37bb6"><font color="red">*</font><fmt:message key="SAF_FINISH_HEADER"/></label>
	<p><fmt:message key="APPLY_RESULT_CONTENT"/></p>
</div>
<div class="seperator"></div>
<div align="center">
	<div class="row">
			<c:choose>
				<c:when test="${sessionScope.SESSION_LOCALE eq 'en_TH'}">
					<img src="../images/sr_apps_en.png" class="img-responsive">
				</c:when>
				<c:otherwise>
					<img src="../images/sr_apps_th.png" class="img-responsive">
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
			<div class="col-xs-12 col-sm-6 text-right">	
				<a href='https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8'><img src="../images/app_store.png" width="100%"></a>
			</div>
			<div class="col-xs-12 col-sm-6 text-left">
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
	          <li class="colorb37bb6">iOS</li>
	          <div style="height:10px;"></div>
	          <p>1. <fmt:message key="APPLY_RESULT_APP_STEP1"/> <img src="../images/app_store.png" class="img-responsive" width="20%" style="display:inline;"></p>
	          <p>2. <fmt:message key="APPLY_RESULT_APP_STEP2"/></p>
	          <p>3. <fmt:message key="APPLY_RESULT_APP_STEP3"/></p>
	          <p>4. <fmt:message key="APPLY_RESULT_APP_STEP4"/></p>
	          <p>5. <fmt:message key="APPLY_RESULT_APP_STEP5"/></p>
	          <div class="seperator"></div>
	            <li class="colorb37bb6">Android</li>
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
			<p><fmt:message key="APPLY_RESULT_DONE_DOWNLOAD"/><a href="http://www.jobtopgun.com/"><u><fmt:message key="APPLY_RESULT_SEEKING_FOR_JOB"/></u></a></p>
		</div>
</div>
