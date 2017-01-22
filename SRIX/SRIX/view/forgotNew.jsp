<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.sql.Timestamp"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	int error = Util.getInt(request.getAttribute("errorId"),0);
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

  var error = <%=error%>;
  $(document).ready(function(){
		var container = $('div.errorContainer');
		if(error == 4){
			$('div.errorContainer ol').empty();
			$('div.errorContainer ol').append("<li><fmt:message key="FORGOTNEW_PLEASE_ENTER_VERIFICATION_CODE_CORRECTLY" /></li>");
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
					username:
					{
						required:true
					},
				 	mobile:
				 	{
				  		required:true
				  	},
				  	verification : 
					{
						required:true
					}
				},			  
			messages: 
				{
					username:
					{
						required: '<fmt:message key="FORGOTNEW_PLEASE_ENTER_YOUR_EMAIL"/>',
					},
					mobile:
					{
				    	required: '<fmt:message key="FORGOTNEW_PLEASE_ENTER_YOUR_MOBILE_NUMBER"/>',
				  	},
					verification :
					{
						required: '<fmt:message key="FORGOTNEW_PLEASE_ENTER_VERIFICATION_CODE"/>'	
					}
				},
			submitHandler:function(form)
				{
					form.submit();
				}
			});
		
		$('#username').change(function() {
			var username = $(this).val();
			$('div.container li').remove();						
			if(username != "") {
				$("#mobile").rules("remove");					
			} else {
				$("#mobile").rules("add", {						
						required: true,
					  	messages: {
							required: '<fmt:message key="FORGOTNEW_PLEASE_ENTER_YOUR_MOBILE_NUMBER"/>'
					}
				});
			}
		});
		
		$('#mobile').change(function(){
			var username = $(this).val();
			$('div.container li').remove();						 
			if(username != "") {
				$("#username").rules( "remove" );				
			} else {
				$("#username").rules( "add", {					
					required: true,
					messages: {
						required: '<fmt:message key="FORGOTNEW_PLEASE_ENTER_YOUR_EMAIL"/>'
					}
				});
			}
		});		
				
		//check three fields
		$('#submit').click(function(){ 
			var username = $("#username").val();
			var primaryPhone = $("#mobile").val();
			var captcha = $("#verification").val();
			var container = $('div.errorContainer');
			if(username != "" && captcha != ""){
				$("#mobile").rules( "remove" );
				$('div.errorContainer ol').remove();	
			} else if (primaryPhone != "" && captcha != "") {
				$("#username").rules( "remove" );
				$('div.errorContainer ol').remove();
			} else if (username != "" && primaryPhone != "" && captcha != "" ) {
				$('div.errorContainer ol').remove();
				container.hide();
			}
		});
});
</script>

<%
	//Check Validation with ErrorID Type
	int idError= Util.getInt(request.getAttribute("errorId"));
	request.setAttribute("errorID", idError);
	
	//Send time to Random Captcha
	java.util.Date date= new java.util.Date();
	request.setAttribute("time", new Timestamp(date.getTime())); 
%>

<form id="forgotFrm" method="post" action="/ForgotPasswordNewServ">
	<input type="hidden" name="service" value="inputData" />
	<div class="contener"><h3 align="center" class="section_header"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/></h3>
	<div class="errorContainer" align="left">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b><ol></ol>
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
	   	<input type="email" class="form-control" id="username" name="username" placeholder="Enter email">
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
	   	<input type="text" class="form-control" id="mobile" name="mobile" placeholder="Enter mobile phone">
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
	   		<input type="text" class="form-control" id="verification" name="verification" placeholder="Password">
	   	</div>
 	</div>
 	
	<div align="center"><button type="submit" name="submit" id="submit" class="btn btn-lg btn-success"><fmt:message key="CANCEL_JM_SUBMIT" /></button></div>
</div>
</form>