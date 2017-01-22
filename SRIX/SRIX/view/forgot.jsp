<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.sql.Timestamp"%>
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
<%
	int idError= Util.getInt(request.getAttribute("errorId"));
	request.setAttribute("errorID", idError);
	
	java.util.Date date= new java.util.Date();
	request.setAttribute("time", new Timestamp(date.getTime()));
	 
%>
<script>
	$(document).ready(function()
	{
		var container = $('div.errorContainer');
		$('#forgotFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
  			invalidHandler: function(form, validator) 
  			{
  				$('html, body').animate({scrollTop: '0px'}, 300);      
  			},
  			highlight: function(element) 
  			{
            	$(element).closest('.form-group').addClass('has-error');
        	},
        	unhighlight: function(element) 
        	{
            	$(element).closest('.form-group').removeClass('has-error');
        	},
  			rules:
  			{
			  	verification:
			  	{
			  		required:true
			  	}		  	
			},			  
			messages: 
			{
			  	verification:
			  	{
			  		required:'<fmt:message key="GLOBAL_SPECIFY"/>'
			  	}		  			
			},
		});	
	});
</script>
<form id="forgotFrm" method="post" action="/ForgotPasswordServ">
<div class="contener">
	<h3 align="center" class="section_header"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/></h3>
	<div class="errorContainer" align="left">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="redFont" align="center">
		<c:choose>
			<c:when test="${errorID eq 1}">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						กรุณากรอกอีเมล์ หรือ เบอร์โทรศัพท์
					</c:when>
					<c:otherwise>
						Please fill E-mail or mobile number
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${errorID eq 2}">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						ไม่พบเบอร์นี้ในระบบ
					</c:when>
					<c:otherwise>
						There isn't this mobile phone in system.
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${errorID eq 3}">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						ไม่พบอีเมล์นี้ในระบบ
					</c:when>
					<c:otherwise>
						There isn't this e-mail in system.
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${errorID eq 6}">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						กรุณากรอกรหัสยืนยันให้ถูกต้อง
					</c:when>
					<c:otherwise>
						Wrong confirm password.
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
	</div>
	<div class="caption">
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				กรุณากรอกอีเมลที่คุณเคยลงทะเบียนไว้ <b style="color:red;">หรือ</b> เบอร์โทรศัพท์มือถือของคุณ
			</c:when>
			<c:otherwise>
				Please enter your e-mail registered with us <b style="color:red;">or</b> mobile number.
			</c:otherwise>
		</c:choose>
	</div>
	<div class="form-group">
	   	<font style="color:red;">*</font><font class="caption"><fmt:message key="GLOBAL_EMAIL"/></font>
	   	<input type="email" class="form-control" id="username" placeholder="Enter email" name="username">
 	</div>
	<div align="center">
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				<b style="color:red;">หรือ</b>
			</c:when>
			<c:otherwise>
				<b style="color:red;">OR</b>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="form-group">
	   	<font style="color:red;">*</font><font class="caption"><fmt:message key="GLOBAL_PHONE_TYPE_MOBILE_NUMBER"/></font>
	   	<input type="text" class="form-control" id="mobile" placeholder="Enter mobile phone" name="mobile">
 	</div>
 	<div class="form-group">
 		<font class="caption"><fmt:message key="GLOBAL_VERIFICATION_CODE" /></font><br />
 		<img src="http://www.jobtopgun.com/captcha/Captcha?sid=<c:out value="${pageContext.session.id}"/>&time=<c:out value="${time}" />">
 	</div>
 	<div class="form-group">
   		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				<font class="star">*</font><font class="caption">กรุณากรอกรหัสยืนยัน</font>
			</c:when>
			<c:otherwise>
				<font class="star">*</font><font class="caption">Please input the Verification Code</font>
			</c:otherwise>
		</c:choose>
		<div class="form-group">
	   		<input type="text" class="form-control" id="verification" placeholder="Password" name="verification">
	   	</div>
 	</div>
 	<div align="center"><button type="submit" class="btn btn-lg btn-success"><fmt:message key="CANCEL_JM_SUBMIT" /></button></div>
</div>
</form>
