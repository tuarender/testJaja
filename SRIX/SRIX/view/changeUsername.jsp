<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@ page import="com.topgun.util.*" %>
<%@ page import="com.topgun.resume.ForgotManager" %>
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
	int idJsk= Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
	int idError= Util.getInt(request.getAttribute("ErrorId"));
	request.setAttribute("error", idError);
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
  				new_username:
			  	{
			  		required:true
			  	},
			  	confirm_username:
			  	{
			  		required:true,
			  		equalTo:'#new_username',
			  	},
			  	password:
		  		{
			  		required:true
		  		}
			},			  
			messages: 
			{
				new_username:
			  	{
			  		required:'<fmt:message key="GLOBAL_SPECIFY"/>'
			  	},
			  	confirm_username:
			  	{
			  		required:'<fmt:message key="GLOBAL_SPECIFY"/>'
			  	},
			  	password:
			  	{
			  		required:'<fmt:message key="GLOBAL_SPECIFY"/>'
			  	}
			},
		});	
	});
</script>
<form id="forgotFrm" method="post" action="/AccountServ?Service=changeUsername">
<div align="left">
	<h3 class="section_header"><fmt:message key="GLOBAL_FORGOT_CHANGE_USERNAME" /></h3>
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="row col-lg-2 col-md-2 col-sm-2 col-xs-2"></div>
		<div align="left" class="form-group row col-lg-10 col-md-10 col-sm-10 col-xs-10">
			<br />
			<c:choose>
				<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
					<c:if test="${error eq '254'}"><font color="red">Username/Email นี้มีคนใช้งานแล้วค่ะ</font></c:if>
					<c:if test="${error eq '255'}"><font color="red">รหัสผ่านไม่ถูกต้อง</font></c:if>
					<c:if test="${error eq '209'}"><font color="red">ไม่สามารถอัพเดทข้อมูลได้</font></c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${error eq '254'}"><font color="red">Username/Email already in my data</font></c:if>
					<c:if test="${error eq '255'}"><font color="red">Incorrect password</font></c:if>
					<c:if test="${error eq '209'}"><font color="red">Can't update your data.</font></c:if>
				</c:otherwise>
			</c:choose>
			<font class="caption"><fmt:message key="GLOBAL_FORGOT_CURRENT_EMAIL" /></font>
			<br/>
			<div align="center" class="suggestion"><%=Util.getStr(Util.getStr(ForgotManager.getUsernameByIdJsk(idJsk)))%></div>
			<br />
			<font class="caption"><b class="star">*</b><fmt:message key="GLOBAL_FORGOT_NEW_USERNAME" /></font>
			<br />
			<input type="email" name="new_username" class="form-control" id="new_username">
			<br />
			<font class="caption"><b class="star">*</b><fmt:message key="GLOBAL_FORGOT_CONFIRM_NEW_PASSWORD" /></font>
			<br />
			<input type="email" name="confirm_username" class="form-control" id="confirm_username">
			<br />
			<font class="caption"><font class="star">*</font><fmt:message key="GLOBAL_PASSWORD" /></font>
			<br />
			<input type="password" class="form-control" name="password" id="password">
			<br />
			<div align="center"><input type="submit" value="Update" class="btn btn-lg btn-success"></div>
			<br />
		</div>
	</div>
</div>
</form>
