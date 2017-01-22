<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%
	int idEmp = Util.getInt(request.getParameter("idEmp"));
	int idPos = Util.getInt(request.getParameter("idPosition"));
	request.setAttribute("idEmp",idEmp);
	request.setAttribute("idPos",idPos);
%>

<form id="loginFrm" action="LogonServ" name="loginFrm">
	<input type="hidden" name="idEmp" value="<c:out value="${param.idEmp}"/>">
	<input type="hidden" name="idPosition" value="<c:out value="${param.idPosition}"/>">
	<input type="hidden" name="view" value="apply">
	<div class="row" align="center">
		<img class="img-responsive" style="max-width:314px;min-width:120px;max-height:120px; padding-top:10px;padding-bottom:10px;" src="http://www.jobtopgun.com/content/filejobtopgun/logo_com_job/j<c:out value="${param.idEmp}" escapeXml="false" />.gif"/>
	</div>
	<div class="row errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="row" align="center">
		<label class="please"><fmt:message key="GLOBAL_PLEASE_READ"/></label>
	</div>
	<div class="row" align="center">
		<label class="please"><fmt:message key="APPLY_CONTENT_1"/></label>
	</div>
	<div class="form-group">
		<label class="control-label" for="username"><fmt:message key="GLOBAL_EMAIL"/>/<fmt:message key="GLOBAL_USERNAME"/></label>
		<input type="text" title="<fmt:message key="GLOBAL_SPECIFY"/>" name="username" class="form-control required" id="username" size="30"/>
	</div>
	<div class="form-group">
		<label class="control-label" for="password"><fmt:message key="GLOBAL_PASSWORD"/></label>
		<input type="password" name="password" id="password" class="form-control required">
	</div>
	<div class="form-group" style="margin-top:0px; margin-bottom:0px; padding:0px;">
			<input type="checkbox"  id="remember" name="remember" />
			<label for="remember" class="rememberFont">จำ Username/Password เวลาฉันเข้ามา</label>
	</div>
	<br>
	<div class="row" align="center">
		<input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/login_button.png"/>
	</div>
	<br>
	<div class="row" align="center">
		<a target="_blank" href="/SRIX?view=forgot"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/>&nbsp;<fmt:message key="CLICK_HERE"/></a><br />
		<a href="/SRIX?view=stepRegister&idEmp=<c:out value="${param.idEmp}" escapeXml="false" />&idPosition=<c:out value="${param.idPosition}" escapeXml="false" />"><fmt:message key="NEED_REGISTER"/></a><br />
	</div>
	<br>
	<br>
</form>
<script>
	$(document).ready(function(){
		var container = $('div.errorContainer');
		var invalid=parseInt(<c:out value="${invalid }"/>)+0;
		if(invalid>0)
		{
			$('div.errorContainer li').remove();
 				if(invalid==1||invalid==2)
 				{
	 				container.append('<li><label class="error"><fmt:message key="LOGIN_INVALID"/></label></li>');
	  				container.css({'display':'block'});
	  				$('html, body').animate({scrollTop: '0px'}, 300); 
 				}
		}
		$('#loginFrm').validate(
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
	 				username:
	 				{
	 					required:true,
	 					email:true
	 				},
				 	password:
				 	{
				  		required:true
				  	}	  	
				},			  
			messages: 
			{
				username:
				{
					required:'<fmt:message key="USERNAME_REQUIRED"/>',
					email:'<fmt:message key="USERNAME_EMAIL"/>'
				},
				password:
				{
			    	required: '<fmt:message key="PASSWORD_REQUIRED"/>'
			  	}	  			
			},
			submitHandler:function(form)
			{
				form.submit();
	        	return true;
			}
		});
	});
	
</script>