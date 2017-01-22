<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.sql.Date"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<% 
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idUnv=Util.getInt(session.getAttribute("SESSION_STU_ID_SCHOOL"));
	int idFac=Util.getInt(session.getAttribute("SESSION_STU_ID_FAC"));
	int idMajor=Util.getInt(session.getAttribute("SESSION_STU_ID_MAJOR"));

	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	Resume resume=null;
	if(idJsk>0)
	{
		resume=new ResumeManager().get(idJsk,0);
		if(resume!=null)
		{
			if(resume.getLocale()!=null)
			{
				locale=resume.getLocale();
			}
			Date birthDate=resume.getBirthDate();
			request.setAttribute("resume",resume);
		}
	}
	request.setAttribute("locale",locale);
%>
<fmt:setLocale value="${locale}"/>
<script>
	$(document).ready(function()
	{
		var container = $('div.errorContainer');
		$('#registerFrm').validate(
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
  				idStu:
			 	{
			  		required:true
			  	},
  				username:
  				{
  					required:true,
  					email:true
  				},
			 	password:
			 	{
			  		required:true,
			  		minlength:6
			  	},
			 	confirm:
			 	{
			  		required:true,
			  		minlength:6,
			  		equalTo:'#password'
			  	},
			  	salutation:
			  	{
			  		required:true
			  	},
			  	firstName:
			  	{
			  		required:true
			  	},
			  	lastName:
			  	{
			  		required:true
			  	},
			  	primaryPhone:
			  	{
			  		required:true
			  	},
			  	primaryPhoneType:
			  	{
			  		required:true
			  	},
			  	privacy:
			  	{
			  		required:true
			  	},
			  	agreement:
			  	{
			  		required:true
			  	}		  	
			},			  
			messages: 
			{
				idStu:
				{
					required:'<fmt:message key="ID_STUDENT_REQUIRED"/>'
				},
				username:
				{
					required:'<fmt:message key="USERNAME_REQUIRED"/>',
					email:'<fmt:message key="USERNAME_EMAIL"/>'
				},
				password:
				{
			    	required: '<fmt:message key="PASSWORD_REQUIRED"/>',
			   		minlength: '<fmt:message key="PASSWORD_MINLENGTH"/>'
			  	},
				confirm:
				{
					equalTo:'<fmt:message key="CONFIRM_EQUALTO"/>',
			    	required:'<fmt:message key="CONFIRM_REQUIRED"/>',
			   		minlength:'<fmt:message key="CONFIRM_MINLENGTH"/>'
			  	},
			  	salutation:
			  	{
			  		required:'<fmt:message key="SALUTATION_REQUIRED"/>'
			  	},
			  	firstName:
			  	{
			  		required:'<fmt:message key="FIRSTNAME_REQUIRED"/>'
			  	},
			  	lastName:
			  	{
			  		required:'<fmt:message key="LASTNAME_REQUIRED"/>'
			  	},
			  	primaryPhone:
			  	{
			  		required:'<fmt:message key="PRIMARYPHONE_REQUIRED"/>'
			  	},
			  	primaryPhoneType:
			  	{
			  		required:'<fmt:message key="PRIMARYPHONETYPE_REQUIRED"/>'
			  	},	
			  	privacy:
			  	{
			  		required:'<fmt:message key="PRIVACY_REQUIRED"/>'
			  	},	  			
			  	agreement:
			  	{
			  		required:'<fmt:message key="AGREEMENT_REQUIRED"/>'
			  	}		  			
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
           			url: '/SRTSServ',
           			data: $('#registerFrm').serialize(),
           			async:false,
           			success: function(data)
           			{
           				var obj = jQuery.parseJSON(data);
           				if(obj.success==1)
           				{
           					var request = '{"idResume":"0","sequence":"1"}';
           					$.ajax({
           						type: "POST",
           				  		url: '/MailRegisterServ',
           						data: jQuery.parseJSON(request),
           						async:false
           					});
           					window.location.href = "/SRIX?view=education&idResume=0&sequence=1";
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
         		return false;
			}
		});
		
		//if OTHER salutation is selected, display please specify 
		$('#salutation').change(function()
		{
			if($(this).val()=='OTHER')
			{
				$('#otherSalutation').val('');
				$('#salutationLayer').css({'display':'block'});
				$("#otherSalutation").rules("add", 
				{ 
					required: true,
					messages:
					{
						required:'<fmt:message key="SALUTATION_REQUIRED"/>'
					}
				});
				
				$('#otherSalutation').focus();
			}
			else
			{
				$('#otherSalutation').val('');
				$('#otherSalutation').rules('remove');
				$('#salutationLayer').css({'display':'none'});
			}
		});
	});
</script>
<div class="section_header alignCenter">
   <h3><fmt:message key="GLOBAL_REGISTER"/></h3>
</div>
<form id="registerFrm" method="post" class="form-horizontal">
	<input type="hidden" name="locale" value="<c:out value="${sessionScope.SESSION_LOCALE}"/>">
	<input type="hidden" name="idSchool" value="<c:out value="${sessionScope.SESSION_STU_ID_SCHOOL}"/>">
	<input type="hidden" name="idFac" value="<c:out value="${sessionScope.SESSION_STU_ID_FAC}"/>">
	<input type="hidden" name="idMajor" value="<c:out value="${sessionScope.SESSION_STU_ID_MAJOR}"/>">
	<input type="hidden" name="degree" value="<c:out value="${sessionScope.SESSION_STU_ID_DEGREE}"/>">
	<input type="hidden" name="service" value="register">
	 <!-- <label class="form-group suggestion"><font color="#993330"><fmt:message key="DIRECTION_REGISTER"/></font></label> -->
	<br>
	<div class="form-group">
		<div class="errorContainer">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
	</div>
	<div class="form-group">
		<label for="academicYear" class="caption"><font color="red">*</font><fmt:message key="EDUCATION_FINISH_YEAR"/></label>
		<c:set var="acurYear" scope="request"><%=Util.getCurrentYear()-8%></c:set>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				<select  name="academicYear" id="academicYear" class="form-control required">
					<c:forEach var="i" begin="0" end="10">
						<c:choose>
							<c:when test="${locale eq 'th_TH'}">
								<option value="<c:out value='${acurYear+i}'/>" <c:if test="${(acurYear+i) == (curYear)}">selected</c:if> >
									<c:out value="${acurYear+i+543}"/>
								</option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${acurYear+i}'/>" <c:if test="${(acurYear+i) == curYear}">selected</c:if>>
								<c:out value="${acurYear+i}"/>
								</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="idStu" class="caption"><font color="red">*</font><fmt:message key="ID_STUDENT"/></label>
		<input type="text" name="idStu" id="idStu" class="form-control" value="">
	</div>
	<div class="form-group">
		<label for="username" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_EMAIL"/></label>
		<c:choose>
			<c:when test="${not empty jobseeker}">
				<input type="hidden" name="username" id="username" value="<c:out value="${jobseeker.username}"/>">
				&nbsp;&nbsp; <c:out value="${jobseeker.username}"/> 
			</c:when>
			<c:otherwise>
				<input type="email" name="username" id="username" class="form-control" value="">
			</c:otherwise>
		</c:choose>
		<div style="font-style:italic; color:#ff0000"><fmt:message key="USERNAME_REMARK"/></div>
	</div>
	<c:if test="${empty jobseeker}">
		<div class="form-group">
			<label for="username" class="caption "><font color="red">*</font><fmt:message key="SET_PASSWORD"/></label>
			<div class="row">
				<div class="col-xs-12 col-sm-6">
					<input type="password" name="password" id="password" class="form-control" >
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="username" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_CONFIRM_PASSWORD" /></label>
			<div class="row">
				<div class="col-xs-12 col-sm-6">
					<input type="password" name="confirm" id="confirm" class="form-control" >
				</div>
			</div>	
		</div>
	</c:if>	
	<div class="form-group">
		<label for="username" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_SALUTATION" /></label>
		<select name="salutation" id="salutation" class="form-control">
			<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
			<option value="MR" <c:if test="${resume.salutation eq 'MR'}">selected</c:if>><fmt:message key="GLOBAL_MR"/></option>
			<option value="MRS" <c:if test="${resume.salutation eq 'MRS'}">selected</c:if>><fmt:message key="GLOBAL_MRS"/></option>
			<option value="MISS" <c:if test="${resume.salutation eq 'MISS'}">selected</c:if>><fmt:message key="GLOBAL_MISS"/></option>
			<option value="OTHER" <c:if test="${(not empty resume) and (resume.salutation ne 'MR') and (resume.salutation ne 'MRS') and (resume.salutation ne 'MISS')}">selected</c:if>><fmt:message key="GLOBAL_OTHER"/></option>
		</select>
		<c:choose>
			<c:when test="${(not empty resume) and (resume.salutation ne 'MR') and (resume.salutation ne 'MRS') and (resume.salutation ne 'MISS')}">
				<div id="salutationLayer" style="display:block;">
					<fmt:message key="GLOBAL_SPECIFY"/>: <input type="text" class="form-control" name="otherSalutation" id="otherSalutation" value="<c:out value="${resume.salutation}"/>">
				</div>
			</c:when>
			<c:otherwise>
				<div id="salutationLayer" style="display:none;">
					<fmt:message key="GLOBAL_SPECIFY"/>: <input type="text" class="form-control" name="otherSalutation" id="otherSalutation" value="<c:out value="${resume.salutation}"/>">
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<c:choose>
		<c:when test="${resume.idLanguage eq 38}">
			<div class="form-group">
				<label for="firstName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_FIRST_NAME"/></label>
				<input type="text" name="firstName" class="form-control" value="<c:out value="${resume.firstNameThai}"/>">
				<br/>
				<label for="lastName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_LAST_NAME"/></label>
				<input type="text" name="lastName" class="form-control" value="<c:out value="${resume.lastNameThai}"/>">
			</div>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<label for="firstName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_FIRST_NAME"/></label>
				<input type="text" name="firstName" class="form-control" value="<c:out value="${resume.firstName}"/>">
				<br/>
				<label for="lastName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_LAST_NAME"/></label>
				<input type="text" name="lastName" class="form-control" value="<c:out value="${resume.lastName}"/>">
			</div>
		</c:otherwise>
	</c:choose>
	<div class="form-group">
		<label class="caption"><font color="red">*</font><fmt:message key="GLOBAL_BIRTHDATE" /></label>
		<fmt:formatDate var="bDay" value="${resume.birthDate}" pattern="d"/>
		<fmt:formatDate var="bMonth" value="${resume.birthDate}" pattern="M"/>
		<fmt:formatDate var="bYear" value="${resume.birthDate}" pattern="yyyy"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
		<div class="row">
			<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-right:6px;">
				<select name="bDay" id="bDay" class="form-control required" title="<fmt:message key="GLOBAL_DAY_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_DAY"/></option>
					<c:forEach var="i" begin="1" end="31">
						<c:choose>
							<c:when test="${bDay eq i}">
								<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding:0px;">
				<select name="bMonth" id="bMonth" class="form-control required" title="<fmt:message key="GLOBAL_MONTH_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
					<c:forEach var="i" begin="1" end="12">
						<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
						<c:choose>
							<c:when test="${bMonth eq i}">
								<option value="<c:out value='${i}'/>" selected><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" style="padding-left:6px;">
				<select name="bYear" id="bYear" class="form-control required" title="<fmt:message key="GLOBAL_YEAR_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
					<c:forEach var="i" begin="0" end="120">
						<c:choose>
							<c:when test="${locale eq 'th_TH'}">
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${bYear eq (curYear-i+543)}">selected</c:if>>
									<c:out value="${curYear-i+543}"/>
								</option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${bYear eq (curYear-i)}">selected</c:if>>
								<c:out value="${curYear-i}"/>
								</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="caption" for="primaryPhone"><font color="red">*</font><fmt:message key="GLOBAL_PRIMARY_PHONE"/>&nbsp;</label>
		<div class="row">
			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
				<input type="tel" name="primaryPhone" id="primaryPhone" class="form-control" value="<c:out value="${resume.primaryPhone}"/>">
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<select name="primaryPhoneType" class="form-control required">
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<option value="MOBILE" <c:if test="${(resume.primaryPhoneType eq 'MOBILE') or (empty resume)}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_MOBILE"/></option>
					<option value="HOME" <c:if test="${resume.primaryPhoneType eq 'HOME'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_HOME"/></option>
					<option value="WORK" <c:if test="${resume.primaryPhoneType eq 'WORK'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_WORK"/></option>
				</select>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="caption"><font color="red">*</font><fmt:message key="GLOBAL_PRIVACY_STATUS" /></label>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
				<input type="radio" id="public" name="privacy"  value="PUBLIC" <c:if test="${resume.resumePrivacy eq 'PUBLIC'}">checked</c:if> checked>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<label class="answer" for="public"><fmt:message key="GLOBAL_PUBLIC"/></label>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
				<input type="radio" id="privacy" name="privacy" value="PRIVATE" <c:if test="${resume.resumePrivacy eq 'PRIVATE'}">checked</c:if>>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<label class="answer" for="privacy"><fmt:message key="GLOBAL_PRIVATE"/></label>
			</div>
		</div>
	</div>
	<div class="form-group caption checkbox">
		<label for="agreement">
			<input type="checkbox" name="agreement" id="agreement" value="1" >
			<c:set var="term">
				<b><a href="/SRIX?view=term" target="_blank"><fmt:message key="GLOBAL_TERM"/></a></b>
			</c:set>
			<c:set var="privacy">
				<b><a href="/SRIX?view=privacy" target="_blank"><fmt:message key="GLOBAL_PRIVACY"/></a></b>
			</c:set> &nbsp;&nbsp;
			<fmt:message key="TERM_AND_PRIVACY">
				<fmt:param value="${term}"/>
				<fmt:param value="${privacy}"/>
			</fmt:message>
		</label>
		<br/>
	</div>
	<br/>
	<div class="row alignCenter"><input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/register.png" ><br/><br/></div>
</form>