<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.JobseekerManager" %>
<%@page import="com.topgun.resume.Employer" %>
<%@page import="com.topgun.resume.EmployerManager" %>
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
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1); 
	request.getSession().removeAttribute("SESSION_ID_JSK");
	int invalid=Util.getInt(request.getParameter("invalid"), -1);
	request.setAttribute("invalid",invalid);
	
	Employer emp = new EmployerManager().get(idEmp);
	request.setAttribute("emp",emp);
	
	
   	
%>

<form id="loginFrm" action="LogonServ" name="loginFrm">
	<input type="hidden" name="idEmp" value="<c:out value="${param.idEmp}"/>">
	<input type="hidden" name="idPosition" value="<c:out value="${param.idPosition}"/>">
	<input type="hidden" name="view" value="apply">
	<div style="height:25px;"></div>
	<div class="row" align="center">
		<img class="img-responsive" style="max-width:314px;min-width:120px;max-height:120px; padding-top:10px;padding-bottom:10px;" src="http://www.jobtopgun.com/content/filejobtopgun/logo_com_job/j<c:out value="${param.idEmp}" escapeXml="false" />.gif"/>
	</div>
	<div class="row errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="col-xs-12 col-sm-12" align="left">
		<b class="font_14 color7461a7"><fmt:message key="REGISTER_MEMBER"/></b>
	</div>
	<div style="height:25px;"></div>
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-lg-12" > 
			<div class="form-group" >
				<div class=" col-xs-12 col-sm-4" >
					<label class="control-label color59595c" for="username" style="padding-top:7px;"><fmt:message key="GLOBAL_EMAIL"/></label>
				</div>
				<div class="col-xs-12 col-sm-8">
					<input type="text" class="form-control" id="username" name="username" placeholder="<fmt:message key="EX_EMAIL"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'TypeOn', eventLabel: 'อีเมล'});"/>
				</div>
			</div>
		</div> 
	</div>
	<div class="visible-xs" style="height:10px;"></div>
	<div class="hidden-xs" style="height:10px;"></div>
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-lg-12" >
			<div class="form-group">
				<div class="col-xs-12 col-sm-4">
					<label class="control-label color59595c" for="password"  style="padding-top:7px;"><fmt:message key="GLOBAL_PASSWORD"/></label>
				</div>
				<div class="col-xs-12 col-sm-8">
					<input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="APPLY_LOGIN_PASSWORD"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'TypeOn', eventLabel: 'รหัสผ่าน'});"/>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="hidden-xs" style="height:5px;"></div>
	<div class="form-group" style="margin-top:0px; margin-bottom:0px; padding:0px;">
		<div class=" col-sm-12">
			<input type="checkbox"  id="remember" name="remember" value="1" checked/>
			<font class="color59595c font_12" ><fmt:message key="GLOBAL_REMEMBER"/></font>
		</div>
	</div>
		<div class="col-sm-12 col-xs-12">
		<div style="height:10px;"></div>
				<button type="submit" class="btn  col-sm-12 col-xs-12 bg765576" ><font class="colorwhite"><fmt:message key="REGISTER_LOGIN"/></font></button>
		</div>
		<div class="col-lg-12 col-sm-12 col-xs-12"> 
		<div style="height:10px;"></div>
			<div align="right"> 
				<u><a href="/SRIX?view=forgot"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/></a></u>
			 </div> 
		</div>
	<div class="hidden-xs" style="height:5px;"></div>
	 <div class="row">
		<div class="col-lg-12 col-sm-12" >
				<div class="col-sm-12 col-xs-12">
					<b class="font_14 color7461a7"><fmt:message key="HEAD_NONMEMBER"/> </b>
					<div style="height:10px;"></div>
					<div class="color59595c"><fmt:message key="GLOBAL_REQUIRE_JOBPOST_TOKNOW"/></div>
				<c:choose>
					<c:when test="${resume.idLanguage eq 38}"> 
						<p class="color59595c"><c:out value="${emp.nameThai}"></c:out><fmt:message key="GLOBAL_REQUIRE_JOBPOST"/></p>
					</c:when> 
					<c:otherwise> 
						<p class="color59595c"><c:out value="${emp.nameEng}"></c:out><fmt:message key="GLOBAL_REQUIRE_JOBPOST"/></p>
					</c:otherwise> 
				</c:choose>
				</div>
				<div class="col-sm-12 col-xs-12" align="center">
					<div style="height:10px;"></div>
					<a href="/SRIX?view=register&idEmp=<c:out value="${param.idEmp}" escapeXml="false" />&idPosition=<c:out value="${param.idPosition}" escapeXml="false" />">
						<button type="button" class="btn-lg col-sm-12 col-xs-12 gradient btn-g bgdddddd" ><b class="color009898 font_16"><fmt:message key="REGISTER_SP"/></b><br><font class="color009898 font_14"><fmt:message key="REGISTER_CREATE_RESUME"/></font></button>
					</a>
				</div>
		</div>
	</div> 
	<div class="hidden-xs" style="height:20px;"></div>
	
	
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