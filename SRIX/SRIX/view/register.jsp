<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.sql.Date"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<% 
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idEmp=Util.getInt(session.getAttribute("SESSION_ID_EMP"));
	int superMatch=Util.getInt(request.getParameter("superMatch"),0);
	
	request.getSession().setAttribute("SESSION_SUPERMATCH", superMatch);
	
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
	request.setAttribute("idJsk", idJsk);
	
	
	Jobseeker jsk=new JobseekerManager().get(idJsk);
	
	if(jsk!=null)
	{
		request.setAttribute("jobMatch", jsk.getJobMatchStatus());
	 	request.setAttribute("mailOther", jsk.getMailOtherStatus());
	}
	String jobMatchStatus="";
	String mailOtherStatus="";
	
	request.setAttribute("jobMatchStatus", jobMatchStatus);
	request.setAttribute("mailOtherStatus",  mailOtherStatus);
	
	
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
			  	}		  			
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
           			url: '/RegisterServ',
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
<div class="seperator"></div>
<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
<form id="registerFrm" method="post" class="form-horizontal">
	<input type="hidden" name="locale" value="<c:out value="${sessionScope.SESSION_LOCALE}"/>">
	<div class="form-group">
		<div class="errorContainer">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
	</div>
	<div class="form-group">
		<label for="username" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_EMAIL"/></label>
		<c:choose>
			<c:when test="${not empty jobseeker}">
				<input type="hidden" name="username" id="username" value="<c:out value="${jobseeker.username}"/>">
				&nbsp;&nbsp; <c:out value="${jobseeker.username}"/> 
			</c:when>
			<c:otherwise>
				<input type="email" name="username" id="username" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'อีเมล'});" value="">
			</c:otherwise>
		</c:choose>
		<div style="font-style:italic; color:#ff0000"><fmt:message key="USERNAME_REMARK"/></div>
	</div>
	<c:if test="${empty jobseeker}">
		<div class="form-group">
			<label for="username" class="caption"><font color="red">*</font><fmt:message key="SET_PASSWORD"/></label>
			<input type="password" name="password" id="password" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'ตั้งรหัสผ่าน'});" > 
		</div>
		<div class="form-group">
			<label for="username" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_CONFIRM_PASSWORD" /></label>
			<input type="password" name="confirm" id="confirm" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel:'ยืนยันรหัสผ่าน'});" >
		</div>
	</c:if>	
	<div class="form-group">
		<label for="username" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_SALUTATION" /></label>
		<select name="salutation" id="salutation" class="form-control" onclick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Click-dropdown', eventLabel: 'คำนำหน้าชื่อ'});"  >
			<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
			<option value="MR" <c:if test="${resume.salutation eq 'MR'}">selected</c:if>><fmt:message key="GLOBAL_MR"/></option>
			<option value="MRS" <c:if test="${resume.salutation eq 'MRS'}">selected</c:if>><fmt:message key="GLOBAL_MRS"/></option>
			<option value="MISS" <c:if test="${resume.salutation eq 'MISS'}">selected</c:if>><fmt:message key="GLOBAL_MISS"/></option>
			<option value="OTHER" <c:if test="${(not empty resume) and (resume.salutation ne 'MR') and (resume.salutation ne 'MRS') and (resume.salutation ne 'MISS')}">selected</c:if>><fmt:message key="GLOBAL_OTHER"/></option>
		</select>
		<c:choose>
			<c:when test="${(not empty resume) and (resume.salutation ne 'MR') and (resume.salutation ne 'MRS') and (resume.salutation ne 'MISS')}">
				<div id="salutationLayer" style="display:block;">
					<fmt:message key="GLOBAL_SPECIFY"/>: <input type="text" class="form-control" name="otherSalutation" id="otherSalutation" value="<c:out value="${resume.salutation}"/>"  onclick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'คำนำหน้าชื่ออื่น'});" >
				</div>
			</c:when>
			<c:otherwise>
				<div id="salutationLayer" style="display:none;">
					<fmt:message key="GLOBAL_SPECIFY"/>: <input type="text" class="form-control" name="otherSalutation" id="otherSalutation" value="<c:out value="${resume.salutation}"/>"  onclick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'คำนำหน้าชื่ออื่น'});" >
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<c:choose>
		<c:when test="${resume.idLanguage eq 38}">
			<div class="form-group">
				<label for="firstName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_FIRST_NAME"/></label>
				<input type="text" name="firstName" class="form-control" value="<c:out value="${resume.firstNameThai}"/>" onclick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'ชื่อ'});" >
				<br/>
				<label for="lastName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_LAST_NAME"/></label>
				<input type="text" name="lastName" class="form-control" value="<c:out value="${resume.lastNameThai}"/>" onclick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'นามสกุล'});" >
			</div>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<label for="firstName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_FIRST_NAME"/></label>
				<input type="text" name="firstName" class="form-control" value="<c:out value="${resume.firstName}"/>" onclick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'ชื่อ'});">
				<br/>
				<label for="lastName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_LAST_NAME"/></label>
				<input type="text" name="lastName" class="form-control" value="<c:out value="${resume.lastName}"/>" onclick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'นามสกุล'});" >
			</div>
		</c:otherwise>
	</c:choose>
	<div class="form-group">
		<label class="caption"><font color="red">*</font><fmt:message key="GLOBAL_BIRTHDATE" /></label>
		<fmt:formatDate var="bDay" value="${resume.birthDate}" pattern="d" />
		<fmt:formatDate var="bMonth" value="${resume.birthDate}" pattern="M"/>
		<fmt:formatDate var="bYear" value="${resume.birthDate}" pattern="yyyy"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
		<div class="row">
			<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-right:6px;">
				<select name="bDay" id="bDay" class="form-control required"  onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Click-dropdown', eventLabel: 'วันเกิด'});" title="<fmt:message key="GLOBAL_DAY_REQUIRED"/>">
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
				<select name="bMonth" id="bMonth" class="form-control required" title="<fmt:message key="GLOBAL_MONTH_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Click-dropdown', eventLabel: 'เดือนเกิด'});">
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
				<select name="bYear" id="bYear" class="form-control required" title="<fmt:message key="GLOBAL_YEAR_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Click-dropdown', eventLabel: 'ปีเกิด'});">
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
		<label class="caption" for="primaryPhone"><font color="red">*</font><fmt:message key="GLOBAL_PRIMARY_PHONE"/></label>
		<div class="form-inline">
			<div class="form-group"> 
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<input type="tel" name="primaryPhone" id="primaryPhone" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'TypeOn', eventLabel: 'เบอร์โทร'});" value="<c:out value="${resume.primaryPhone}"/>">
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<select name="primaryPhoneType" class="form-control required" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Click-dropdown', eventLabel: 'เลือกอุปกรณ์สื่อสาร'});">
						<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
						<option value="MOBILE" <c:if test="${(resume.primaryPhoneType eq 'MOBILE') or (empty resume)}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_MOBILE"/></option>
						<option value="HOME" <c:if test="${resume.primaryPhoneType eq 'HOME'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_HOME"/></option>
						<option value="WORK" <c:if test="${resume.primaryPhoneType eq 'WORK'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_WORK"/></option>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="caption"><font color="red">*</font><fmt:message key="GLOBAL_PRIVACY_STATUS" /></label>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
				<input type="radio" id="public" name="privacy" value="PUBLIC" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Check', eventLabel: 'บริษัทที่สนใจเข้ามาอ่าน ได้'});" <c:if test="${resume.resumePrivacy eq 'PUBLIC'}">checked</c:if> checked="checked">
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<label class="answer" for="public"><fmt:message key="GLOBAL_PUBLIC"/></label>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
				<input type="radio" id="privacy" name="privacy"  value="PRIVATE" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Check', eventLabel: 'บริษัทที่คุณสมัครเท่านั้น'});" <c:if test="${resume.resumePrivacy eq 'PRIVATE'}">checked</c:if>>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<label class="answer" for="privacy"><fmt:message key="GLOBAL_PRIVATE"/></label>
			</div>
		</div>
	</div>
	
	
	<div class="form-group">
		<label class="caption"><font color="red">*</font><fmt:message key="GLOBAL_MAIL_SETTING" /></label>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
					<input  type="checkbox"  name="jobMatchStatus"  class="choiceRadio" value="TRUE" <c:if test="${jobMatch eq 'TRUE'}">checked</c:if> checked="checked">
				</div>
				<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
					<label class="answer" for="jobMatchStatus"><fmt:message key="GLOBAL_JOBMATCHSTATUS"/></label>
				</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
					<input  type="checkbox" name="mailOtherStatus" class="choiceRadio" value="TRUE" <c:if test="${mailOther  eq 'TRUE'}">checked</c:if> checked="checked"> 
				</div>
				<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
					<label class="answer" for="mailOtherStatus"><fmt:message key="GLOBAL_MAILOTHERSTATUS"/></label>
				</div>
		</div>
		<div class="row">
		<div class="col-xs-12">
		<p class="font_12"><fmt:message key="REGISTER_CANCEL_MAIL"/><a href="http://www.jobtopgun.com/index.jsp">JOBTOPGUN.com</a>,<fmt:message key="REGISTER_JOBTOPGUN"/></p>
		</div>
		</div>
	</div>
	<br/>
	<div class="form-group alignCenter">
		<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" id="submit"  onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"/>
	</div>
	<div class="form-group" align="center">
		<label for="agreement">
			<input type="hidden" name="agreement" id="agreement" value="1" >
			<c:set var="term">
				<b><a href="/SRIX?view=term" target="_blank" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Click', eventLabel: 'ข้อตกลงการใช้บริการ'});" ><fmt:message key="GLOBAL_TERM"/></a></b>
			</c:set>
			<c:set var="privacy">
				<b><a href="/SRIX?view=privacy" target="_blank" onClick="ga('send', 'event', { eventCategory: 'Engage-RegisterLocale', eventAction: 'Click', eventLabel: 'นโยบายการเก็บรักษาข้อมูลส่วนบุคคล'});" ><fmt:message key="GLOBAL_PRIVACY"/></a></b>
			</c:set> &nbsp;&nbsp;
			<fmt:message key="TERM_AND_PRIVACY">
				<fmt:param value="${term}"/>
				<fmt:param value="${privacy}"/>
			</fmt:message>
		</label>
		<br/>
	</div>
</form>