<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%
	 int idError= Util.getInt(request.getAttribute("errorId"));
	 request.setAttribute("errorID", idError);
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
<form id="forgotFrm" method="post" action="/ForgotPasswordNewServ">
	<input type="hidden" name="cmd" value="reset">	
	<input type="hidden" name="email" value="<c:out value='${param.email}'/>">
	<input type="hidden" name="key" value="<c:out value='${param.key}'/>">
	<input type="hidden" name="locale" value="<c:out value="${sessionScope.SESSION_LOCALE}"/>">
	<%-- <div align="center" class="section_header"><br><fmt:message key="GLOBAL_FORGOT_PASSWORD"/><br></div> --%>
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="caption" align="center">
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				ตั้งรหัสผ่านใหม่
			</c:when>
			<c:otherwise>
				Reset your password.
			</c:otherwise>
		</c:choose>
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
	<div>
		<input type="password" name="password" id="password" class="form-control">
	</div>
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
	<div>
		<input type="password" name="confirm" id="confirm" class="form-control">
	</div>
	<div align="center">
		<br>
		<input type="submit" value="Submit" class="btn btn-lg btn-success">
	</div>
	<div>
		<br>
	</div>
</form>