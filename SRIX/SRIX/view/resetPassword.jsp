<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ page import="com.topgun.util.*" %>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% 
	int idError= Util.getInt(request.getAttribute("ErrorId"));
	request.setAttribute("idError", idError);
	int idJsk= Util.getInt(request.getAttribute("idJskOTP"),0);
	request.setAttribute("idJsk",idJsk);
%>

<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>

<script>
	$(document).ready(function()
	{
		var container = $('div.errorContainer');
		container.hide();
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
  				password:
			  	{
			  		required:true,
			  		minlength:6
			  	},
			  	confirm:
			  	{
			  		required:true,
			  		minlength:6,
			  		equalTo: "#password"
			  	}	
			},			  
			messages: 
			{
				password:
			  	{
			  		required:'<fmt:message key="GLOBAL_NEW_PASSWORD"/>',
			  		minlength:'<fmt:message key="PASSWORD_MINLENGTH"/>'
			  	},
			  	confirm:
			  	{
			  		required:'<fmt:message key="GLOBAL_CONFIRM_PASSWORD"/>',
			  		equalTo :'<fmt:message key="CONFIRM_EQUALTO"/>' ,
			  		minlength:'<fmt:message key="PASSWORD_MINLENGTH"/>'
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
	<div id="container">
		<form id="forgotFrm" method="post" action="/ForgotPasswordNewServ">	
			<input type="hidden" name="cmd" value="reset">	
			<input type="hidden" name="email" value="<c:out value='${param.email}'/>">
			<input type="hidden" name="idJsk" value="<c:out value='${idJskOTP}'/>">
			<input type="hidden" name="key" value="<c:out value='${param.key}'/>">
			<input type="hidden" name="locale" value="<c:out value="${sessionScope.SESSION_LOCALE}"/>">
			<div class="errorContainer">
				<br><b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
				<ol></ol>
			</div>
			<div>
				<h3 class="section_header" align="center">
					<c:choose>
						<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">ตั้งรหัสผ่านใหม่</c:when>
						<c:otherwise>Reset your password</c:otherwise>
					</c:choose>
				</h3>
			</div>
			<br>
			<div class="caption">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						<b class="star">*</b>รหัสผ่านใหม่
					</c:when>
					<c:otherwise>
						<b class="star">*</b>New password
					</c:otherwise>
				</c:choose>
			</div>
			<div class="form-group">
				<br><input type="password" name="password" id="password" class="form-control">
			</div>
			<br>
			<div class="caption">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						<b class="star">*</b>ยืนยันรหัสผ่านใหม่
					</c:when>
					<c:otherwise>
						<b class="star">*</b>Confirm New password
					</c:otherwise>
				</c:choose>
			</div>
			<div class="form-group">
				<br><input type="password" name="confirm" id="confirm" class="form-control">
			</div>
			<br>
			<div align="center"><button type="submit" name="submit" id="submit" class="btn btn-lg btn-success"><fmt:message key="CANCEL_JM_SUBMIT" /></button></div>
		</form>
	</div>
</body>
</html>