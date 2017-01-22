<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.JobType"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	int sequence=Util.getInt(request.getParameter("sequence"),1);
	int idLanguage=resume.getIdLanguage();
	int idCountry=resume.getIdCountry();
	int resumeCountry = resume.getTemplateIdCountry();
	String view = Util.getStr(request.getParameter("view"));
	request.setAttribute("view", view);
	request.setAttribute("idLanguage", idLanguage);
	request.setAttribute("idCountry", resumeCountry);
	request.setAttribute("idJsk", resume.getIdJsk());
	request.setAttribute("idResume", resume.getIdResume());
	request.setAttribute("sequence",sequence);
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
	var pages;
	$(document).ready(function()
	{
		getAptitude();
		var container = $('div.errorContainer');
		$('#aptitudeFrm').validate(
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
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
		   			url: '/AptitudeLevelServ',
		   			data: $('#aptitudeFrm').serialize(),
		   			success: function(data)
		   			{
		   				var obj = jQuery.parseJSON(data);
		   				if(obj.success==1)
           				{
           					pages=obj.pages;
           					if(sequence==0)
							{
								$('#resumeListLayer').modal({
       								show: 'true'
    							});
   							}
   							else
   							{
   								window.location.href = "/SRIX?view="+pages+"&idResume=<c:out value='${idResume}'/>&sequence="+sequence;
   							} 
           				}
               			else
               			{
               				$('#error_'+form.id+' li').remove();
               				for(var i=0; i<obj.errors.length; i++)
               				{
               					$('#error_'+form.id+' ol').append('<li><label class="error">'+obj.errors[i]+'</label></li>');
               				}
               				container.css({'display':'block'});
               				$('html, body').animate({scrollTop: '0px'}, 300); 
               			}
		   			}
		 		});
		 		return false ;
			}		
		});
	});
	function getAptitude()
	{
		$.ajax(
		{
			type: "POST",
			url: '/AptitudeLevelServ',
			data: {'service':'view','idResume':<c:out value='${idResume}'/>},
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj.success==1)
				{
					var html ='';
					for(var i=0; i<obj.hobies.length; i++)
					{	
						html+="<div class='row'>";
						html+="<div class='col-lg-4 col-md-4 col-sm-4 col-xs-5'>";
						html+=(i+1)+".";
						html+="  "+obj.hobiesName[i]+" ";
						html+="</div>";
						html+="<div class='col-lg-8 col-md-8 col-sm-8 col-xs-7'>";
						html+="<div class='form-group'>";
						html+="<select  name='"+obj.hobies[i].idGroup+"_"+obj.hobies[i].idHobby+"' id='"+obj.hobies[i].idGroup+"_"+obj.hobies[i].idHobby+"'  class='required form-control' title='<fmt:message key="GLOBAL_SPECIFY"/> "+obj.hobiesName[i]+"' onClick=\"ga('send', 'event', { eventCategory: 'Engage-AptitudeLevel', eventAction: 'Click-dropdown', eventLabel: 'AptitudeLevel'});\">";
						html+="<option value=''><fmt:message key="GLOBAL_SPECIFY"/></option>";
						html+="<option value='5'><fmt:message key="SKILL_COM_EXPERT"/></option>";
						html+="<option value='4'><fmt:message key="SKILL_COM_ADVANCED"/></option>";
						html+="<option value='3'><fmt:message key="SKILL_COM_INTERMEDIATE"/></option>";
						html+="<option value='2'><fmt:message key="SKILL_COM_BEGINNER"/></option>";
						html+="<option value='1'><fmt:message key="SKILL_COM_LEARNING"/></option></select>";
						html+="</div>";
						html+="</div>";
						html+="</div>";
					}
					$("#aptitudeList").html(html);
					for(var i=0; i<obj.hobies.length; i++)
					{
						$("#"+obj.hobies[i].idGroup+"_"+obj.hobies[i].idHobby).val("");
						if(obj.hobies[i].skill>0)
						{
							$("#"+obj.hobies[i].idGroup+"_"+obj.hobies[i].idHobby).val(obj.hobies[i].skill);
						}
					}
					$('#aButton').show();
				}
				else
				{
					window.location.href = "/SRIX?view=aptitudeRank&idResume=<c:out value='${idResume}'/>&sequence="+sequence;
				}
			}
		});
	}
</script>

<form id="aptitudeFrm" method="post" class="form-horizontal">
<div class="seperator"></div>
	<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
	<div class="suggestion">
		<h5><fmt:message key="DIRECTION_APTITUDE_LEVEL"/></h5>
	</div>
	
	<div class="row errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol> </ol>
	</div>
	<div class="row">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<input type="hidden" name="idResume" value="<c:out value="${idResume}"/>">
			<input type="hidden" name="service" value="edit">
			<div id="aptitudeList">
			
			</div>
		</div>
	</div>
	<br>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  onClick="ga('send', 'event', { eventCategory: 'Engage-AptitudeLevel', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"/>
		</div>
	</div>
	<br>
</form>
<c:set var="section" value="6" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>