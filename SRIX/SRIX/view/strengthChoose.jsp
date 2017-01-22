<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.Strength"%>
<%@page import="com.topgun.resume.StrengthManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	if(resume!=null)
	{
		int idLanguage=resume.getIdLanguage();
		int idCountry=resume.getIdCountry();
		int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
		int idResume = Util.getInt(request.getParameter("idResume"));
		int totalSelect=0;
		int sequence = Util.getInt(request.getParameter("sequence"),1);
		String other="";
		List<Strength> strengthList=new ArrayList<Strength>();
		List<com.topgun.resume.Strength> strengths=new StrengthManager().getAll(idJsk, idResume);
		if(strengths !=null)
		{
			for(int i=0; i<strengths.size(); i++)
			{
				Strength strength=new Strength();
				strength.setIdLanguage(idLanguage);
				strength.setIdStrength(strengths.get(i).getIdStrength());
				if(strengths.get(i).getIdStrength()<0)
				{
					strength.setStrengthName(strengths.get(i).getOthStrength());
				}
				else
				{
					strength.setStrengthName(MasterDataManager.getStrength(strengths.get(i).getIdStrength(), idLanguage).getStrengthName());
				}
				strengthList.add(strength);
			}
		}
		request.setAttribute("idResume",idResume);
		request.setAttribute("idJsk", idJsk);
		request.setAttribute("sequence",sequence);
		request.setAttribute("idLanguage",idLanguage);
		request.setAttribute("strengthList",strengthList);
		request.setAttribute("strengthListSize",strengthList.size());
		request.setAttribute("strengths",strengths);
		request.setAttribute("sequence",sequence);
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
	$(document).ready(function()
	{
		var idResume = '<c:out value="${idResume}"/>';
		var sequence= '<c:out value="${sequence}"/>';
		var container = $('div.errorContainer');
		$('#strengthFrm').validate(
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
				var checkedValues = $('input:checkbox:checked').map(function() 
				{
				    return this.value;
				}).get();
				if(checkedValues.length == 5) //more than or equa to 5 can add
				{
					$.ajax(
					{
						type: "POST",
			   			url: '/StrengthServ',
			   			data: $('#strengthFrm').serialize(),
			   			success: function(data)
			   			{
			   				var obj = jQuery.parseJSON(data);
							if(obj.success==1)
							{
								if(obj.success==true)
								{
									window.location.href = "?view=strengthOrder&idResume="+idResume+"&sequence="+sequence;
									return false;
								}
								else
								{
									alert("<fmt:message key='STRENGTH_TITLE2' />");
									return false;
								}
							}
			   			}
			 		});
		 		}
				else  
				{
					alert("<fmt:message key='STRENGTH_TITLE2' />");
					return false;
				}
			}
		});
	});
</script>
<div style="margin: 50px 0px;">
	<form id="strengthFrm" method="post" class="form-horizontal">
	<input type="hidden" name="idResume" value="<c:out value='${idResume}'/>" >
	<input type="hidden" name="service" value="updateChoose" >
		<div class="section_header alignCenter">
		   <h3>TOPGUN 'S Strengths©</h3>
		</div>
		<div class="suggestion alignCenter">
		<div class="row errorContainer" style="padding:5px">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol><br>
		</div>
	    <h5>
	    	<fmt:message key="DIRECTION_STRENGTH_CHOOSE">
				<fmt:param value="${strengthListSize}"></fmt:param>
			</fmt:message>
	   </h5>
		</div>
		<div class="form-group" style="padding-left:10pt;">
			<div class="row">
				<div class="col-sm-10 col-xs-10" style="padding-left:20pt;">
					<div>
						<c:forEach var="strengthList" items="${strengthList}">
							<div class="checkbox caption">
						    	<label>
						    		<input type="checkbox"  name="strength" value="<c:out value='${strengthList.idStrength}'/>" onClick="ga('send', 'event', { eventCategory: 'Engage-StrengthChoose', eventAction: 'Check', eventLabel: 'StrengthChoose'});"><c:out value='${strengthList.strengthName}'/>
						    	</label>
						  	</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<br>
			<div class="row" align="center">
				<input type="image" src="/images/<c:out value="${resume.locale}" />/next.png" onClick="ga('send', 'event', { eventCategory: 'Engage-StrengthChoose', eventAction: 'Click', eventLabel: 'ถัดไป'});"/>
			</div>
		</div>
		<div><font>&nbsp;</font></div>
	</form>
</div>
