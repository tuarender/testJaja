<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@ page import="com.topgun.util.*" %>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<%
	String number= Util.getStr(request.getAttribute("mobile"));
	int wrong= Util.getInt(request.getAttribute("wrong"));
  	request.setAttribute("wrong", wrong);
%>
<script>	
	$(document).ready(function()
	{
		var container = $('div.errorContainer');
		var wrong = <%=wrong%>;
		if( wrong == 1){
			$('div.errorContainer ol').empty();
			$('div.errorContainer ol').append("<li><fmt:message key="CONFIRMOTP_ENTER_VERIFICATION_CODE_IS_INCORRECT" /></li>");
			container.show();
		} else {
			container.hide();
		}
		
		$('#forgotFrm').validate(
		{
			ignore: [],
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
	      	errorPlacement: function(error, element) 
	      	{
	          	if(element.parent('.form-group').length) 
	          	{
	              	error.insertAfter(element.parent());
	          	} 
	          	else 
	          	{
	              	error.insertAfter(element);
	          	}
	      	},
  			rules:
  			{
			  	otp:
			  	{
			  		required:true
			  	}		  	
			},			  
			messages: 
			{
			  	otp:
			  	{
			  		required:'<fmt:message key="GLOBAL_CONFIRM_CODE"/>'
			  	}		  			
			},
			submitHandler:function(form)
			{
				form.submit();
			}
		});	
	});
</script>
<html>
<body>
	<form id="forgotFrm" method="post" action="/ForgotPasswordNewServ">
		<input type="hidden" name="locale" value="<c:out value="${sessionScope.SESSION_LOCALE}"/>">
		<div align="center">
			<h3 class="section_header"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/></h3>
			<br>
			<div class="errorContainer" align="left">
				<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
				<ol></ol>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="row col-lg-3 col-md-3 col-sm-3 col-xs-3"></div>
					<div align="left" class="row col-lg-12 col-md-12 col-sm-12 col-xs-12 caption">
						<c:choose>
							<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
								เบอร์โทรศัพท์ของคุณคือ <%=number%><br>
								ระบบจะส่ง "รหัสยืนยัน" ให้คุณทางโทรศัพท์มือถือที่ระบุไว้ข้างต้นภายใน 5 นาที<br>
								หลังจากนั้น ให้คุณกรอกรหัสที่คุณได้รับในด้านล่าง และกดปุ่มยืนยันเพื่อไปตั้งรหัสผ่านใหม่ค่ะ<br>
							</c:when>
							<c:otherwise>
								Your mobile number is <%=number%><br>
								Verification code will be sent to mobile number above in 5 minutes.<br>
								Insert the code in the box below and click "submit" button to go to reset password page.
							</c:otherwise>
						</c:choose>
						<div align="center" class="form-group">
						<br>
						<div class="form-group" align="left">
							<font style="color:red;">*</font><font class="caption"><fmt:message key="GLOBAL_CONFIRM_CODE"/></font><br />
							<br><input type="text" class="form-control" name="otp" id="otp">
						</div>
						<input type="hidden" name="service" value="chooseMethodSendEmailAndOTP">
						<input type="hidden" name="step" value="2">
						<input type="hidden" name="telephoneNo" value=<%=number%>><br />
						<button type="submit" name="submit" id="submit" class="btn btn-lg btn-success"><fmt:message key="CANCEL_JM_SUBMIT" /></button>	
					</div>
				</div>
				</div>
			</div>
		<br/>
	</form>
</body>
</html>