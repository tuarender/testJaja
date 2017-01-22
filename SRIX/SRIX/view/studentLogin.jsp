<%@page import="com.topgun.shris.masterdata.Major"%>
<%@page import="com.topgun.shris.masterdata.Faculty"%>
<%@page import="com.topgun.shris.masterdata.School"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Degree"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.sql.Date"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<% 
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	int idCountry=216;
	int idLanguage=11;
	if(locale.equals("th_TH"))
	{
		idLanguage=38;
	}
	ArrayList<Degree> degrees = (ArrayList<Degree>)MasterDataManager.getAllDegree(idLanguage);
	request.setAttribute("locale",locale);
	request.setAttribute("degrees",degrees);
	request.setAttribute("idLanguage",idLanguage);
	
	
	int idSchool=Util.getInt(session.getAttribute("SESSION_STU_ID_SCHOOL"));
	School school=new MasterDataManager().getSchool(idCountry, idSchool, idLanguage);
	
	int idFaculty=Util.getInt(session.getAttribute("SESSION_STU_ID_FAC"));
	Faculty faculty=MasterDataManager.getFaculty(idFaculty, idLanguage);
	
	int idMajor=Util.getInt(session.getAttribute("SESSION_STU_ID_MAJOR"));
	Major major=MasterDataManager.getMajor(idFaculty, idMajor, idLanguage);
	
	request.setAttribute("idSchool",idSchool);
	request.setAttribute("idFaculty",idFaculty);
	request.setAttribute("idMajor",idMajor);
	
%>
<fmt:setLocale value="${locale}"/>
<script>
	$(document).ready(function()
	{
		var idSchool="<c:out value='${idSchool}'/>";
		var idFaculty="<c:out value='${idFaculty}'/>";
		var idMajor="<c:out value='${idMajor}'/>";
		if(idSchool<=0 || idFaculty<=0 || idMajor<=0)
		{
			window.location.href = "http://student.superresume.com/studentTracking.jsp";
			return false;
		}
		var container = $('div.errorsContainer');
		$('#loginFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
			onfocusout: false,
				onkeyup: false,
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
			    	required: '<fmt:message key="PASSWORD_REQUIRED"/>',
			   		minlength: '<fmt:message key="PASSWORD_MINLENGTH"/>'
			  	}	  			
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
           			url: '/StudentLogonServ',
           			data: $('#loginFrm').serialize(),
           			async:false,
           			success: function(data)
           			{
           				var obj = jQuery.parseJSON(data);
           				if(obj.success==1)
           				{
           					var pages=obj.target;
           					if(pages!="")
           					{
           						window.location.href = "/SRIX?view="+pages+"&idResume=0&sequence=1";
               					return false;
           					}
           				}
               			else
               			{
               				$('div.container li').remove();
               				for(var i=0; i<obj.errors.length; i++)
               				{
               					container.find("ol").append('<li>'+obj.errors[i]+'</li>');
               				}
               				container.css({'display':'block'});
               				container.find("ol").css({'display':'block'});
               				$('html, body').animate({scrollTop: '0px'}, 300); 
               			}
           			}
         		});
         		
			}
		});
	});
</script>
<form class="form-horizontal" id=loginFrm  method="post">
<input type="hidden" name="service" id="service" value="checkLogin"/>
<br/>
<div class="row">
	<div class="col-xs-0 col-sm-1 col-lg-1"></div>
	<div class="col-xs-12 col-sm-10 col-lg-10">
		<label class="redFont text-center" ><fmt:message key="TRACKING_NORESUME"/></label>
	</div>
</div>
<div class="form-group">
	<a href="/SRIX?view=registerTracking&locale=<c:out value="${param.locale}"/>&jSession=<c:out value="${pageContext.session.id}"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-StudentLogin', eventAction: 'ClickOn', eventLabel: 'สมัครสมาชิก'});">
		<img class="img-responsive center-block" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/register_button.png"/>
	</a>
</div>
<div class="row">
	<div class="col-xs-0 col-sm-1 col-lg-1"></div>
	<div class="col-xs-12 col-sm-10 col-lg-10">
		<label class="text-justify" style="color: #725fa7;font-size: 16px;font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="TRACKING_HAVE_RESUME"/></label>
	</div>
</div>
<div class="form-group">
	<div class="errorsContainer col-sm-offset-3 col-sm-7" style="color: #D63301;background-color: #FFccBA;display:none;font-style:italic;border: 1px solid #f00;margin-top: 4px;margin-bottom:4px;padding-top:4px; padding-bottom:0px;">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-0 col-sm-0 col-lg-2"></div>
	<div class="col-xs-12 col-sm-8 col-lg-8">
		<div class="form-group">
			<font color="red">*</font><label class="control-label" for="password"><fmt:message key="GLOBAL_EMAIL"/></label>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-0 col-sm-0 col-lg-2"></div>
	<div class="col-xs-12 col-sm-8 col-lg-8">
		<div class="form-group">
			<input type="text" class="form-control" id="username" name="username" placeholder="<fmt:message key="EX_EMAIL"/>"/>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-0 col-sm-0 col-lg-2"></div>
	<div class="col-xs-12 col-sm-8 col-lg-8">
		<div class="form-group">
			<font color="red">*</font><label class="control-label" for="password"><fmt:message key="GLOBAL_PASSWORD"/></label>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-0 col-sm-0 col-lg-2"></div>
	<div class="col-xs-12 col-sm-8 col-lg-8">
		<div class="form-group">
			<input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="EX_PASSWORD"/>"/>
		</div>
	</div>
</div>
<div class="visible-xs" style="height:15px;"></div>
<div class="form-group text-center">
		<input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/login_button.png"/>
	</div>
<div class="form-group text-right">
	<div class="col-xs-0 col-sm-0 col-lg-11">
		<label><u><a href="/SRIX?view=forgot"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/></a></u></label>
	</div>
</div>
	
</form>