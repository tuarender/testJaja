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
		var container = $('div.errorsContainer');
		$('#loginFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ul", container),
			wrapper: 'li',
			focusInvalid: false,
  			invalidHandler: function(form, validator) 
  			{
  				$('html, body').animate({scrollTop: '0px'}, 300);      
  			},
  			highlight: function(element) 
  			{
            	$(element).closest('.input-group').addClass('has-error');
        	},
        	unhighlight: function(element) 
        	{
            	$(element).closest('.input-group').removeClass('has-error');
        	},
        	errorPlacement: function(error, element) 
        	{
            	if(element.parent('.input-group').length) 
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
			    	required: '<fmt:message key="PASSWORD_REQUIRED"/>',
			   		minlength: '<fmt:message key="PASSWORD_MINLENGTH"/>'
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
<div class="errorsContainer col-sm-offset-2 col-sm-8" style="color: #D63301;background-color: #FFccBA;display:none;font-style:italic;border: 1px solid #f00;margin-top: 20px;margin-bottom:20px;padding-top:12px;">
	<ul></ul>
</div>
<br>
<form id=loginFrm action="/LogonServ" method="post">
	<div class="row">
		<div class="form-group col-sm-offset-2 col-sm-8">
	   		<label class="control-label" for="username"><fmt:message key="GLOBAL_USERNAME"/>/<fmt:message key="GLOBAL_EMAIL"/></label>
			<div class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
		    	<input type="email" class="form-control" id="username" name="username" placeholder="Username" />
			</div>
		</div>   	
		<div class="form-group col-sm-offset-2 col-sm-8">
			<label class="control-label" for="password"><fmt:message key="GLOBAL_PASSWORD"/></label>
			<div class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
				<input type="password" class="form-control" id="password" name="password" placeholder="Password" />
			</div>
		</div>	
		<div class="form-group col-sm-offset-2 col-sm-8">
			<input type="submit" class="btn btn-primary" value="<fmt:message key="GLOBAL_LOGON"/>">
		</div>
		<div class="form-group col-sm-offset-2 col-sm-8">
				<table class="table table-condensed">
				<tr>
					<td>
						<a href="http://www.superresume.com/contents/TH/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/sr/index.htm"  target="_blank" style="color:#42bfa7; text-decoration:none; font-weight:bold;">
							<fmt:message key="WHAT_IS_SUPERRESUME"/>
						</a>
					</td>
					<td style="width:5px;">
						<a href="http://www.superresume.com/contents/TH/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/sr/index.htm" target="_blank">
							<img src="/images/arrow_green_right.png" class="img-responsive" height="20"  border="0">
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="http://www.superresume.com/landingpage/adword/resume_<c:out value="${sessionScope.SESSION_LANGUAGE}"/>.html"  target="_blank" style="color:#42bfa7; text-decoration:none; font-weight:bold;">
							<fmt:message key="MEMBER_PRIVILEGES"/>
						</a>										
					</td>
					<td width="30">
						<a href="http://www.superresume.com/landingpage/adword/resume_<c:out value="${sessionScope.SESSION_LANGUAGE}"/>.html" target="_blank">
							<img src="/images/arrow_green_right.png" class="img-responsive" height="20"  border="0">
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="http://www.youtube.com/watch?v=Y6Z5hHD-UIU&list=PL_iAr5G3PB_I-0ECktHbYtgKgJ4m1w_OU&feature=c4-overview-vl"  target="_blank" style="color:#42bfa7; text-decoration:none; font-weight:bold;"><fmt:message key="VDO_TIP"/></a>
					</td>
					<td>
						<a href="http://www.youtube.com/watch?v=Y6Z5hHD-UIU&list=PL_iAr5G3PB_I-0ECktHbYtgKgJ4m1w_OU&feature=c4-overview-vl"  target="_blank">
							<img src="/images/arrow_green_right.png" class="img-responsive" height="20"  border="0">
						</a>
					</td>
				</tr>
				</table>	
			</div>			
	</div>
</form>
							