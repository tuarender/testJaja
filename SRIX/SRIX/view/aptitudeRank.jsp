<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.JobType"%>
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
	$(document).ready(function()
	{
		getAptitude();
		var container = $('div.errorContainer');
		$('#aptitudeRankFrm').validate(
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
				var q=0;
				$('select').each(function()
				{
					var val=$(this).val();
					var id=$(this).prop('id');
					if(val!="")
					{
						$('select').each(function()
						{
							if($(this).prop('id')!=id)
							{
								if(val==$(this).val())
								{
									$(this).addClass("error");
									$("#"+id).addClass("error");
									$('div.errorContainer li').remove();
					  				container.find("ol").append("<li><fmt:message key="SAMETOPGUN_APTITUDE"/></li>");
					   				container.css({'display':'block'});
					   				container.find("ol").css({'display':'block'});
					   				$('html, body').animate({scrollTop: '0px'}, 300); 
					   				q=1 ;
								}
							}
						});
					}
				});
				if(q==0)
				{
					$.ajax(
					{
						type: "POST",
			   			url: '/AptitudeRankServ',
			   			data: $('#aptitudeRankFrm').serialize(),
			   			success: function(data)
			   			{
			   				if(sequence==0)
	      					{
	      						$('#resumeListLayer').modal({
									show: 'true'
								});
	      						//window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
	      					}
	      					else if(idResume>0)
	      					{
	      						window.location.href = "/SRIX?view=education&idResume=<c:out value='${idResume}'/>&sequence="+sequence;
	      					}
	      					else
	      					{
	      						window.location.href = "/SRIX?view=uploadPhoto&idResume=<c:out value='${idResume}'/>&sequence="+sequence;
	      					}
			   			}
			 		});
			 	}
		 		return false ;
			}		
		});
	});
	function getAptitude()
	{
		$.ajax(
		{
			type: "POST",
			url: '/AptitudeRankServ',
			data: {'service':'view','idResume':<c:out value='${idResume}'/>},
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj.success==1)
				{
					var html="";
					for(var i=0; i<obj.hobies.length; i++)
					{
						html+="<option value='"+obj.hobies[i].idHobby+":"+obj.hobies[i].idGroup+"'>"+obj.hobiesName[i]+"</option>";
					}
					var maxRankDiv=5;
					if(obj.hobies.length<5)
					{
						maxRankDiv=obj.hobies.length;
					}
					for(var j=0; j<=maxRankDiv; j++)
					{
						$('#rank'+j).append(html);
						$('#rankLayer'+j).show();
					}
					var temp = [];
					$.each(obj.hobies, function(key, value) 
					{
						if(value.hobbyOrder>0)
						{
							temp.push({v:value, k: key});
						}
					});
					temp.sort(function(a,b)
					{
						if(a.v.hobbyOrder > b.v.hobbyOrder){ return 1;}
						if(a.v.hobbyOrder < b.v.hobbyOrder){ return -1;}
						return 0;
					});
					$.each(temp, function(key, obj) 
					{
						var aKey=key+1;
						var aVal=obj.v.idHobby+":"+obj.v.idGroup;
						$("#rank"+aKey).val(aVal);
					});

					
				}
			}
		});
	}
</script>
<form id="aptitudeRankFrm" method="post" class="form-horizontal">
	<br>
	<input type="hidden" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" name="service" value="edit">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<div class="suggestion">
   				 <h5><fmt:message key="DIRECTION_APTITUDE_RANK"/></h5>
 			</div>
		</div>
	</div>
	<div class="errorContainer row" style="display:none">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b> 
		<ol> </ol>
	</div>
	<br>
	<div id="rankLayer1" class="row" style="display:none">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><span class="caption_bold">1.</span></div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 form-group"> 
			<select name="rank1" id="rank1"  class='required form-control answer' title="<fmt:message key="TOPGUN_APTITUDE_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-AptitudeRank', eventAction: 'Click-dropdown', eventLabel: 'AptitudeRank'});">
				<option value=""><fmt:message key="GLOBAL_SPECIFY"/></option>
			</select>
		</div>
		<br>
	</div>
	
	<div id="rankLayer2" class="row" style="display:none">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><span class="caption_bold">2.</span></div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 form-group">
			<select name="rank2" id="rank2"  class='required form-control answer' title="<fmt:message key="TOPGUN_APTITUDE_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-AptitudeRank', eventAction: 'Click-dropdown', eventLabel: 'AptitudeRank'});">
				<option value=""><fmt:message key="GLOBAL_SPECIFY"/></option>
			</select>
		</div>
		<br>
	</div>
	
	<div id="rankLayer3" class="row" style="display:none"> 
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><span class="caption_bold">3.</span></div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 form-group">
			<select name="rank3" id="rank3"  class='required form-control answer' title="<fmt:message key="TOPGUN_APTITUDE_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-AptitudeRank', eventAction: 'Click-dropdown', eventLabel: 'AptitudeRank'});">
				<option value=""><fmt:message key="GLOBAL_SPECIFY"/></option>
			</select>
		</div>
		<br>
	</div>
	
	<div id="rankLayer4" class="row" style="display:none">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><span class="caption_bold">4.</span></div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 form-group">
			<select name="rank4" id="rank4"  class='required form-control answer' title="<fmt:message key="TOPGUN_APTITUDE_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-AptitudeRank', eventAction: 'Click-dropdown', eventLabel: 'AptitudeRank'});">
				<option value=""><fmt:message key="GLOBAL_SPECIFY"/></option>
			</select>
		</div>
		<br>
	</div>
	
	<div id="rankLayer5" class="row" style="display:none">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><span class="caption_bold">5.</span></div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 form-group">
			<select name="rank5" id="rank5"  class='required form-control answer' title="<fmt:message key="TOPGUN_APTITUDE_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-AptitudeRank', eventAction: 'Click-dropdown', eventLabel: 'AptitudeRank'});">
				<option value=""><fmt:message key="GLOBAL_SPECIFY"/></option>
			</select>
		</div>
		<br>
	</div>
	
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" onClick="ga('send', 'event', { eventCategory: 'Engage-Aptitude', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});">
		</div>
	</div>
	<br>
</form>
<c:set var="section" value="6" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>