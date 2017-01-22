<%@page import="com.topgun.resume.Social"%>
<%@page import="com.topgun.resume.SocialManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<% 
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	if(idJsk>0 && idResume>=0)
	{
		Resume resume=new ResumeManager().get(idJsk,idResume);
		if(resume!=null)
		{
			int sequence=Util.getInt(request.getParameter("sequence"),1);
			List<Country> countries=MasterDataManager.getAllCountry(resume.getIdLanguage());
			request.setAttribute("resume",resume);
			request.setAttribute("countries",countries);	
			request.setAttribute("sequence",sequence);
		}
		Social social = new SocialManager().get(idJsk, idResume);
		if(social != null)
		{
			request.setAttribute("social", social);
		}
	}
	//Status job update
	Jobseeker jskBean= new JobseekerManager().get(idJsk);
	if(jskBean != null)
	{
		request.setAttribute("juStatus", jskBean.getJobUpdateStatus());
	}
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
	var sequence=<c:out value='${sequence}'/>;
	var idResume=<c:out value="${resume.idResume}"/>;
	var idLanguage=<c:out value="${resume.idLanguage}"/>;
	
	function getCountry()
	{
		if(<c:out value="${resume.idCountry}"/>>0)
		{
			$('#idCountry option[value="<c:out value="${resume.idCountry}"/>"]').prop("selected",true);
		}
		else
		{
			$('#idCountry option[value="<c:out value="${resume.applyIdCountry}"/>"]').prop("selected",true);
		}
	}
	
	function getStates(idCountry)
	{		
		if(idCountry>0)
		{
			$.ajax(
			{
				url:'/AjaxServ?service=State&idCountry='+idCountry+'&idLanguage='+idLanguage,
				success:function(result)
				{
					var states = jQuery.parseJSON(result);
					if(states.length>0)
					{
						$("#otherState").removeClass('required');
						$('#idState option[value!=""]').remove();
						for (var i=0; i<states.length; i++) 
						{
							if(idCountry==<c:out value="${resume.idCountry}"/> && states[i].idState==<c:out value="${resume.idState}"/>)
							{
								$('#idState').append("<option value='"+states[i].idState+"' selected>"+states[i].stateName+"</option>");
								getCities(idCountry,states[i].idState);
							}
							else
							{
								$('#idState').append("<option value='"+states[i].idState+"'>"+states[i].stateName+"</option>");
							}
		   		 		}	
						$('#otherState').val('');
						$('#otherStateLayer').hide();
						
					}
					else
					{
						$('#idState option[value!=""]').remove();
						$('#idState').append("<option value='-1' selected><fmt:message key="GLOBAL_OTHER"/></option>");
						$("#otherState").addClass('required');
						$('#otherStateLayer').show();
						getCities(-1,-1);
					}
				}		
			});
		}
		else
		{
			$('#idState option[value!=""]').remove();
			$('#idState').append("<option value='-1' selected><fmt:message key="GLOBAL_OTHER"/></option>");
			$("#otherState").addClass('required');
			$('#otherStateLayer').show();
			getCities(-1,-1);
		}		
	}
	
	function getCities(idCountry, idState)
	{
		if(idCountry>0 && idState>0)
		{
			$.ajax(
			{
				url:'/AjaxServ?service=City&idCountry='+idCountry+'&idState='+idState+'&idLanguage='+idLanguage,
				success:function(result)
				{
					var cities = jQuery.parseJSON(result);
					if(cities.length>0)
					{
						$("div[name='cityLayer']").show();
						$("#otherCity").removeClass('required');
						$('#idCity option[value!=""]').remove();
						for (var i=0; i<cities.length; i++) 
						{
							if(idCountry==<c:out value="${resume.idCountry}"/> && idState==<c:out value="${resume.idState}"/> && cities[i].idCity==<c:out value="${resume.idCity}"/>)
							{
								$('#idCity').append("<option value='"+cities[i].idCity+"' selected>"+cities[i].cityName+"</option>");
							}
							else
							{
								$('#idCity').append("<option value='"+cities[i].idCity+"'>"+cities[i].cityName+"</option>");
							}
		   		 		}	
						$('#otherCity').val('');
						$('#otherCityLayer').hide();
					}
					else
					{
						$("div[name='cityLayer']").show();
						$('#idCity option[value!=""]').remove();
						$('#idCity').append("<option value='-1' selected><fmt:message key="GLOBAL_OTHER"/></option>");
						$("#otherCity").addClass('required');
						$('#otherCityLayer').show();
						$('#otherCity').focus();
					}
				}		
			});
		}
		else
		{
			/*$("div[name='cityLayer']").hide();
			$('#idCity option[value!=""]').remove();*/
			$('#idCity option[value!=""]').remove();
			$("div[name='cityLayer']").hide();
			$('#idCity').append("<option value='-1' selected><fmt:message key="GLOBAL_OTHER"/></option>");
			$("#otherCity").addClass('required');
			$('#otherCityLayer').show();
		}
	}
	
	$(document).ready(function()
	{	
		//set Salutation
		<c:if test="${not empty resume.salutation}">
			var salute='<c:out value="${resume.salutation}"/>';
			if(salute=='MR' || salute=='MRS' || salute=='MISS')
			{
				$('#salutation option[value="'+salute+'"]').prop('selected',true);
			}
			else
			{
				$('#salutation option[value="OTHER"]').prop('selected',true);
				$('#salutationLayer').show();
			}
		</c:if>
		//set citizenship
		<c:if test="${resume.citizenship eq 'THAI' || resume.idCountry==216}">
			$('#citizenship').val('THAI');
		</c:if>
		<c:if test="${(resume.citizenship ne '') and (resume.citizenship ne 'THAI')}">
			$('#citizenship').val('OTHER');
		</c:if>
		
		getCountry();
		getStates(<c:out value="${resume.idCountry}"/>);
		getCities(<c:out value="${resume.idCountry}"/>,<c:out value="${resume.idState}"/>);
		
		//set Own car
		<c:if test="${not empty resume.ownCarStatus}">
			$('input[name="ownCar"][value="<c:out value="${resume.ownCarStatus}"/>"]').prop('checked',true);
		</c:if>		
		$('#idCountry').change(function()
		{
			getStates($(this).val());
		});
		
		$('#idState').change(function()
		{
			getCities($('#idCountry').val(),$(this).val());
		});
										
		var container = $('div.errorContainer');
		$('#personalFrm').validate(
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
			  	salutation:
			  	{
			  		required:true
			  	},
			  	<c:choose>
			  		<c:when test="${resume.locale eq 'th_TH'}">
					  	firstNameThai:
					  	{
					  		required:true
					  	},
					  	lastNameThai:
					  	{
					  		required:true
					  	},
					  </c:when>
					  <c:otherwise>
					  	firstName:
					  	{
					  		required:true
					  	},
					  	lastName:
					  	{
					  		required:true
					  	},
					  </c:otherwise>
			  	</c:choose>
			  	height:
			  	{
			  		required:true
			  	},	
			  	weight:
			  	{
			  		required:true
			  	},				  			  	
			  	citizenship:
			  	{
			  		required:true
			  	},
			  	idCountry:
			  	{
			  		required:true
			  	},
			  	idState:
			  	{
			  		required:true
			  	},
			  	idCity:
			  	{
			  		required:true
			  	},			  				  	
			  	homeAddress:
			  	{
			  		required:true
			  	},	
			  	postal:
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
			  	owncar:
			  	{
			  		required:true
			  	}
			},			  
			messages: 
			{
			  	salutation:
			  	{
			  		required:'<fmt:message key="SALUTATION_REQUIRED"/>'
			  	},
			  	<c:choose>
			  		<c:when test="${resume.locale eq 'th_TH'}">
					  	firstNameThai:
			  			{
			  				required:'<fmt:message key="FIRSTNAME_REQUIRED"/>'
			  			},
			  			lastNameThai:
			  			{
			  				required:'<fmt:message key="LASTNAME_REQUIRED"/>'
			  			},
					  </c:when>
					  <c:otherwise>
					  	firstName:
			  			{
			  				required:'<fmt:message key="FIRSTNAME_REQUIRED"/>'
			  			},
			  			lastName:
			  			{
			  				required:'<fmt:message key="LASTNAME_REQUIRED"/>'
			  			},
					  </c:otherwise>
			  	</c:choose>	
			  	height:
			  	{
			  		required:'<fmt:message key="HEIGHT_REQUIRED"/>'
			  	},			  	
			  	weight:
			  	{
			  		required:'<fmt:message key="WEIGHT_REQUIRED"/>'
			  	},			  	
				citizenship:
			  	{
			  		required:'<fmt:message key="CITIZENSHIP_REQUIRED"/>'
			  	},
			  	idCountry:
			  	{
			  		required:'<fmt:message key="COUNTRY_REQUIRED"/>'
			  	},
			  	idState:
			  	{
			  		required:'<fmt:message key="STATE_REQUIRED"/>'
			  	},
			  	idCity:
			  	{
			  		required:'<fmt:message key="CITY_REQUIRED"/>'
			  	},			  				  	
			  	
			  	homeAddress:
			  	{
			  		required:'<fmt:message key="HOMEADDRESS_REQUIRED"/>'
			  	},	
			  	postal:
			  	{
			  		required:'<fmt:message key="POSTAL_REQUIRED"/>'
			  	},				  	
			  	primaryPhone:
			  	{
			  		required:'<fmt:message key="PRIMARYPHONE_REQUIRED"/>'
			  	},
			  	primaryPhoneType:
			  	{
			  		required:'<fmt:message key="PRIMARYPHONETYPE_REQUIRED"/>'
			  	},	
			  	owncar:
			  	{
			  		required:'<fmt:message key="OWNCAR_REQUIRED"/>'
			  	}
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
           			url: '/PersonalDataServ',
           			data: $('#personalFrm').serialize(),
           			async:false,
           			success: function(data)
           			{
           				var obj = jQuery.parseJSON(data);
           				if(obj.success==1)
           				{
           					if(sequence==0)
       						{
       							$('#resumeListLayer').modal({
       								show: 'true'
    							}); 
       							//window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
       						}
       						else
       						{
       							if(idResume==0)
       							{
       								window.location.href = "/SRIX?view=strength&idResume=<c:out value="${resume.idResume}"/>&sequence="+sequence;
       							}
       							else
       							{
       								window.location.href = "/SRIX?view=resumeFinish&idResume=<c:out value="${resume.idResume}"/>&sequence="+sequence;
       							}
       						}
           				}
               			else
               			{
               				$('div.errorContainer li').remove();
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
		$('#salutation').change(function(){
			if($(this).val()=='OTHER')
			{
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
				$('#otherSalutation').rules('remove');
				$('#salutationLayer').css({'display':'none'});
			}
		});
		
		//if OTHER citizenship is selected, display please specify 
		$('#citizenship').change(function(){
			if($(this).val()=='OTHER')
			{
				$('#citizenshipLayer').show();
				$("#otherCitizenship").rules("add", 
				{ 
					required: true,
					messages:
					{
						required:'<fmt:message key="CITIZENSHIP_REQUIRED"/>'
					}
				});
				
				$('#otherCitizenship').focus();
			}
			else
			{
				$('#otherCitizenship').rules('remove');
				$('#citizenshipLayer').hide();
			}
		});		
		
		$('#secondaryPhone').blur(function()
		{
			if($(this).val().trim()!='')
			{
				$("#secondaryPhoneType").rules('add', 
				{ 
					required: true,
					messages:
					{
						required:'<fmt:message key="SECONDARYPHONETYPE_REQUIRED"/>'
					}
				});
			}
			else
			{
				$("#secondaryPhoneType").rules('remove');
			}
		});
		
		$('#lineId').blur(function(){
			var lineId = $(this).val();
			for( i = 0 ; i < lineId.length ; i++ )
			{
				if(lineId[i] != '@')
				{
					$(this).val(lineId.substring(i, lineId.length));
					break;
				}
				else if(i == lineId.length - 1)
				{
					$(this).val('');
				}
			}
		});
	});
</script>
<%-- <c:if test="${sequence == 1 && juStatus ne 'NO'}">
	<div class="alignCenter" style="padding-top:10pt;">
		<font style="color:#58595b;font-size:22px;"><fmt:message key="TARGET_JOB_JOB_UPDATE" /></font>
	</div>
</c:if> --%>
<div class="seperator"></div>
<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
<form id="personalFrm" method="post" action="/PersonalDataServ" class="form-horizontal">
	<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>">
	<div class="form-group">
		<div class="errorContainer">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
	</div>
	<br/>
	<div class="form-group">
		<label for="salutation" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_SALUTATION"/></label>
		<div class="row">
			<div class="col-xs-12 col-sm-4">
				<select name="salutation" id="salutation"  class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'คำน้ำหน้าชื่อ'});">
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<option value="MR"><fmt:message key="GLOBAL_MR"/></option>
					<option value="MRS"><fmt:message key="GLOBAL_MRS"/></option>
					<option value="MISS"><fmt:message key="GLOBAL_MISS"/></option>
					<option value="OTHER"><fmt:message key="GLOBAL_OTHER"/></option>
				</select>
			</div>
		</div>
	</div>
	<div id="salutationLayer" style="display:none;" class="form-group">
		<label for="otherSalutation" class="caption"><fmt:message key="GLOBAL_SPECIFY"/></label>: <input type="text" class="form-control" name="otherSalutation" id="otherSalutation" value="<c:out value="${resume.salutation}"/>">
	</div>
	<c:choose>
		<c:when test="${resume.idLanguage eq 38}">
			<div class="form-group">
				<label for="firstNameThai" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_FIRST_NAME"/></label>
				<input type="text" name="firstNameThai" id="firstNameThai" class="form-control" value="<c:out value="${resume.firstNameThai}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'ชื่อ'});"> 
			</div>
			<div class="form-group">
				<label for="lastNameThai" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_LAST_NAME"/></label>
				<input type="text" name="lastNameThai" id="lastNameThai" class="form-control" value="<c:out value="${resume.lastNameThai}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'นามสกุล'});">
			</div>
			
				<c:if test="${resume.idCountry eq 216}">
					<div class="form-group">
						<label for="firstName" class="caption"><fmt:message key="GLOBAL_FIRST_NAME_TH"/></label>
						<input type="text" name="firstName" id="firstName" class="form-control" value="<c:out value="${resume.firstName}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'ชื่อภาษาอังกฤษ'});"> 
					</div>
					<div class="form-group">
						<label for="lastName" class="caption"><fmt:message key="GLOBAL_LAST_NAME_TH"/></label>
						<input type="text" name="lastName" id="lastName" class="form-control " value="<c:out value="${resume.lastName}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'นามสกุลภาษาอังกฤษ'});">
					</div>
				</c:if>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<label for="firstName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_FIRST_NAME"/></label>
				<input type="text" name="firstName" id="firstName" class="form-control" value="<c:out value="${resume.firstName}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'ชื่อภาษาอังกฤษ'});"> 
			</div>
			<div class="form-group">
				<label for="lastName" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_LAST_NAME"/></label>
				<input type="text" name="lastName" id="lastName" class="form-control" value="<c:out value="${resume.lastName}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'นามสกุลภาษาอังกฤษ'});">
			</div>
				<c:if test="${resume.idCountry eq 216}">
					<div class="form-group">
						<label for="firstNameThai" class="caption"><fmt:message key="GLOBAL_FIRST_NAME_TH"/></label>
						<input type="text" name="firstNameThai" id="firstNameThai" class="form-control" value="<c:out value="${resume.firstNameThai}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'ชื่อ'});"> 
					</div>
					<div class="form-group">
						<label for="lastNameThai" class="caption"><fmt:message key="GLOBAL_LAST_NAME_TH"/></label>
						<input type="text" name="lastNameThai" id="lastNameThai" class="form-control" value="<c:out value="${resume.lastNameThai}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'นามสกุล'});">
					</div>
				</c:if>
		</c:otherwise>
	</c:choose>
	
	<label class="form-group caption"><font color="red">*</font><fmt:message key="GLOBAL_BIRTHDATE" /></label>
	<fmt:formatDate var="bDay" value="${resume.birthDate}" pattern="d"/>
	<fmt:formatDate var="bMonth" value="${resume.birthDate}" pattern="M"/>
	<fmt:formatDate var="bYear" value="${resume.birthDate}" pattern="yyyy"/>
	<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
	<div class="row">
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
			<div class="form-group">
				<select  name="bDay" id="bDay" class="form-control required" title="<fmt:message key="GLOBAL_DAY_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'วันเกิด'});">
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
		</div>
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
			<div class="form-group">
				<select name="bMonth" id="bMonth" class="form-control required" title="<fmt:message key="GLOBAL_MONTH_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'เดือนเกิด'});">
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
		</div>
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
			<div class="form-group">
				<select  name="bYear" id="bYear" class="form-control required" title="<fmt:message key="GLOBAL_YEAR_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'ปีเกิด'});">
					<option value=""><fmt:message key="EDUCATION_YEAR"/></option>
					<c:forEach var="i" begin="0" end="120">
						<c:choose>
							<c:when test="${resume.locale eq 'th_TH'}">
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
		<label for="height" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_HEIGHT"/></label>
		<div class="row" >
			<div class="col-sm-5 col-xs-12">
				<c:choose>
					<c:when test="${resume.height > 0}">
						<input type="text" name="height" id="height" step="any" class="form-control"  value="<c:out value="${resume.height}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'ส่วนสูง'});"> 
					</c:when>
					<c:otherwise>
						<input type="text" name="height" id="height" step="any" class="form-control"  value="" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'ส่วนสูง'});"> 
					</c:otherwise>
				</c:choose> 
			</div>
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><fmt:message key="GLOBAL_CENTIMETER"/> </div>
		</div>
	</div>
	<p/>
	<div class="form-group">
		<label for="weight" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_WEIGHT"/></label>
		<div class="row">
			<div class="col-sm-5 col-xs-12">
				<c:choose>
					<c:when test="${resume.weight > 0}">
						<input type="text" name="weight" id="weight" step="any" class="form-control"  value="<c:out value="${resume.weight}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'น้ำหนัก'});"> 
					</c:when>
					<c:otherwise>
						<input type="text" name="weight" id="weight" step="any" class="form-control"  value="" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'น้ำหนัก'});"> 
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><fmt:message key="GLOBAL_KILOGRAM"/> </div>
		</div>
	</div>
	<p/>
	<c:choose>
		<c:when test="${resume.idCountry eq 216}">
			<div class="form-group">
				<label for="citizenship" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_CITIZENSHIP" /></label>
				<div class="row">
					<div class="col-xs-12 col-sm-8">
						<select name="citizenship" id="citizenship"class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'สัญชาติ'});">
							<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
							<option value="THAI"><fmt:message key="GLOBAL_THAI"/></option>
							<option value="OTHER"><fmt:message key="GLOBAL_OTHER"/></option>
						</select>
					</div>
				</div>
			</div>
			<p/>
			<c:choose>
				<c:when test="${(resume.citizenship ne '') and (resume.citizenship ne 'THAI')}">
					<div id="citizenshipLayer" style="display:block;" class="form-group">
						<fmt:message key="GLOBAL_SPECIFY"/>: <input type="text" class="required" title="<fmt:message key="CITIZENSHIP_REQUIRED"/>"  class="form-control"   name="otherCitizenship" id="otherCitizenship" value="<c:out value="${resume.citizenship}"/>">
					</div>
				</c:when>
				<c:otherwise>
					<div id="citizenshipLayer" style="display:none;" class="form-group">
						<fmt:message key="GLOBAL_SPECIFY"/>: <input type="text" class="form-control"  name="otherCitizenship" id="otherCitizenship">
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<label for="citizenship" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_CITIZENSHIP" /></label>
				<!-- <select name="citizenship" id="citizenship" class="form-control">
					<option value="OTHER" selected="selected"><fmt:message key="GLOBAL_OTHER"/></option>
				</select>
				 -->
				 <input type="hidden" name="citizenship" id="citizenship" value="OTHER">
				<input type="text" class="required form-control" title="<fmt:message key="CITIZENSHIP_REQUIRED"/>"  name="otherCitizenship" id="otherCitizenship" value="<c:out value="${resume.citizenship}"/>">
			</div>
		</c:otherwise>
	</c:choose>
	<p/>
	
	
	<div class="form-group">
		<label for="idCountry" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_COUNTRY_RESIDENCE"/></label>
		<div class="row">
			<div class="col-xs-12 col-sm-8">
				<select name="idCountry" id="idCountry" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'ประเทศ'});">
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<c:forEach var="country" items="${countries}">
						<option value="<c:out value='${country.idCountry}'/>"><c:out value='${country.countryName}'/></option>
					</c:forEach>
				</select>	
			</div>
		</div>
	</div>
	<p/>
	<div class="form-group">
		<label for="idState" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_STATE"/></label>
		<div class="row">
			<div class="col-xs-12 col-sm-8">
				<select name="idState" id="idState" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'จังหวัด'});">
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
				</select>
			</div>
		</div>
	</div>
	<div id="otherStateLayer" style="display:none;" class="form-group">
		<fmt:message key="GLOBAL_SPECIFY"/>:
		<div class="row">
			<div class="col-xs-12 col-sm-8">
				<input type="text" name="otherState" id="otherState" value="<c:out value="${resume.otherState}"/>" class="form-control" title="<fmt:message key="STATE_REQUIRED"/>">
			</div>
		</div>
	</div>
	<p/>
	<div name="cityLayer" style="display:none;" >
		<div class="form-group">
			<font color="red">*</font><b><fmt:message key="GLOBAL_DISTRICT"/></b>
		</div>
	</div>
	<div name="cityLayer" style="display:none;">
		<div class="form-group">
			<div class="row">
				<div class="col-xs-12 col-sm-8">
					<select name="idCity" id="idCity"  class="form-control" >
						<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					</select>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div id="otherCityLayer" style="display:none;">
				<fmt:message key="GLOBAL_SPECIFY"/>:
				<div class="row">
					<div class="col-xs-12 col-sm-8">
						<input type="text" name="otherCity" id="otherCity" value="<c:out value="${resume.otherCity}"/>"  class="form-control"  title="<fmt:message key="CITY_REQUIRED"/>">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="homeAddress" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_HOME_ADDRESS"/></label> 
		<textarea rows="5" cols="30" class="form-control required"   name="homeAddress" id="homeAddress" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'ที่อยู่'});"><c:out value="${resume.homeAddress}" /></textarea>
	</div>
	<p/>
	<div class="form-group">
		<label for="postal" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_POSTAL"/></label>
		<div class="row">
			<div class="col-xs-12 col-sm-8">
				<input type="tel" class="form-control" name="postal" id="postal" value="<c:out value="${resume.postal}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'รหัสไปรษณีย์'});">
			</div>
		</div>
	</div>
	<p/>
	<div class="form-group">
		<label for="primaryPhone" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_PRIMARY_PHONE"/>&nbsp;</label>
		<div class="row">
			<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
				<input type="tel" name="primaryPhone" id="primaryPhone" class="form-control" value="<c:out value="${resume.primaryPhone}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'เบอร์โทร'});">
			</div>
			<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
				<select name="primaryPhoneType" id="primaryPhoneType" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'เลือกอุปกรณ์สื่อสาร'});">
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<option value="MOBILE" <c:if test="${resume.primaryPhoneType eq 'MOBILE'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_MOBILE"/></option>
					<option value="HOME" <c:if test="${resume.primaryPhoneType eq 'HOME'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_HOME"/></option>
					<option value="WORK" <c:if test="${resume.primaryPhoneType eq 'WORK'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_WORK"/></option>
				</select>
			</div>
		</div>
	</div>
	<p/>
	<div class="form-group">
		<label for="secondaryPhone"  class="caption" ><fmt:message key="GLOBAL_SECONDARY_PHONE"/>&nbsp;</label>
		<div class="row">
			<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
				<input type="tel" name="secondaryPhone" id="secondaryPhone" class="form-control" value="<c:out value="${resume.secondaryPhone}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'เบอร์โทรสำรอง'});">
			</div>
			<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
				<select name="secondaryPhoneType" id="secondaryPhoneType" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Click-dropdown', eventLabel: 'เลือกอุปกรณ์ของเบอร์สำรอง'});">
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<option value="MOBILE" <c:if test="${resume.secondaryPhoneType eq 'MOBILE'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_MOBILE"/></option>
					<option value="HOME" <c:if test="${resume.secondaryPhoneType eq 'HOME'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_HOME"/></option>
					<option value="WORK" <c:if test="${resume.secondaryPhoneType eq 'WORK'}">selected</c:if>><fmt:message key="GLOBAL_PHONE_TYPE_WORK"/></option>
				</select>
			</div>
		</div>
	</div>
	<p/>
	<div class="form-group">
		<label for="email" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_EMAIL"/></label>
		<input type="email" class="form-control" value="<c:out value="${jobseeker.username}"/>" readonly="readonly" disabled="disabled" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'อีเมล'});">
		<div style="font-style:italic;" class="suggestion"><fmt:message key="USERNAME_REMARK"/></div>
	</div>
	<p/>
	<div class="form-group">
		<label for="secondaryEmail" class="caption"><fmt:message key="GLOBAL_SECONDARY_EMAIL"/></label>
		<input type="email" name="secondaryEmail" id="secondaryEmail" class="form-control"  value="<c:out value="${resume.secondaryEmail}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'TypeOn', eventLabel: 'อีเมลสำรอง'});">
	</div>
	<p/>
	<div class="form-group">
		<label for="test" class="caption"><fmt:message key="ADDITION_CONTACT_INFO"/></label>
	</div>
	<p/>
	<div class="form-group">
		<label for="lineId" class="caption">Line ID</label>
		<div class="input-group">
			<div class="input-group-addon">@</div>
			<input type="text" name="lineId" id="lineId" class="form-control" maxlength="20" value="<c:out value="${social.lineId}"/>">
		</div>
	</div>
	<p/>
	<div class="form-group">
		<label for="skype" class="caption">Skype</label>
		<input type="text" name="skype" id="skype" class="form-control" maxlength="50"  value="<c:out value="${social.skype}"/>">
	</div>
	<p/>
	<div class="form-group">
		<label for="ownCar" class="caption"><font color="red">*</font><fmt:message key="GLOBAL_OWNCAR"/></label>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="ownCar" id="carTrue" value="TRUE"  class=" required caption" title="<fmt:message key="GLOBAL_OWNCAR"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Check', eventLabel: 'มีรถ'});"></div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10"><label for="carTrue" class="caption"><fmt:message key="GLOBAL_YES"/></label></div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="ownCar"  id="carfalse" value="FALSE"  class=" required caption" title="<fmt:message key="GLOBAL_OWNCAR"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'Check', eventLabel: 'ไม่มีรถ'});"></div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10"><label for="carfalse" class="caption"><fmt:message key="GLOBAL_NO"/></label></div>
		</div>
	</div>
	<p/>
	<div class="row alignCenter"><input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" onClick="ga('send', 'event', { eventCategory: 'Engage-Personal', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"></div>
	<br/><br/>
</form>
<c:set var="section" value="1" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>