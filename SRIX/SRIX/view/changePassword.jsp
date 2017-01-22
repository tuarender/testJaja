<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
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
  				cur_password:
			  	{
			  		required:true
			  	},
			  	new_password:
		  		{
			  		required:true,
			  		minlength:6
		  		},
		  		confirm_password:
		  		{
		  			required:true,
		  			equalTo:'#new_password',
		  			minlength:6,
		  		}
			  	
			},			  
			messages: 
			{
				cur_password:
			  	{
			  		required:'<fmt:message key="GLOBAL_SPECIFY"/>'
			  	},
			  	new_password:
			  	{
			  		required:'<fmt:message key="GLOBAL_SPECIFY"/>'
			  	},
			  	confirm_password:
			  	{
			  		required:'<fmt:message key="GLOBAL_SPECIFY"/>'
			  	}
			},
		});	
	});
</script>

<form id="forgotFrm" method="post" action="/AccountServ?Service=changePassword">
<div>
	<h3 align="center" class="section_header"><fmt:message key="CHANGE_PASSWORD" /></h3>
</div>
<div>
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<c:if test="${error eq '255'}">
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				<font color="red">คุณกรอกรหัสผ่านปัจจุบันไม่ถูกต้อง</font>
			</c:when>
			<c:otherwise>
				<font color="red">Your current password is not correct</font>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>
<div >
	<b class="star">*</b><fmt:message key="CHANGE_PASSWORD_CURRENT" />
	<br />
	<div class="form-group">
		<input type="password" class="form-control" name="cur_password" id="cur_password" >
	</div>
	<b class="star">*</b><fmt:message key="GLOBAL_NEW_PASSWORD" />
	<div class="form-group">
		<input type="password" class="form-control" name="new_password" id="new_password">
	</div>
	<font class="star">*</font><fmt:message key="GLOBAL_NEW_PASSWORD_CONFIRM" />
	<div class="form-group">
		<input type="password" class="form-control" name="confirm_password" id="confirm_password">
	</div>
	<div align="center"><input type="submit" value="Update" class="btn btn-lg btn-success"></div>
	<br />
</div>
</form>