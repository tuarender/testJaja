<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.Strength"%>
<%@page import="com.topgun.resume.StrengthManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
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
		List<Strength> strengthList =MasterDataManager.getAllStrength(idLanguage);
		List<com.topgun.resume.Strength> strengths=new StrengthManager().getAll(idJsk, idResume);
		
		String view = Util.getStr(request.getParameter("view"));
		request.setAttribute("view", view);
		if(strengths !=null)
		{
			for(int i=0; i<strengths.size(); i++)
			{
				if(strengths.get(i).getIdStrength()<0)
				{
					other= strengths.get(i).getOthStrength();
				}
			}
		}
		
		request.setAttribute("idResume",idResume);
		request.setAttribute("idJsk", idJsk);
		request.setAttribute("sequence",sequence);
		request.setAttribute("idLanguage",idLanguage);
		request.setAttribute("strengthList",strengthList);
		request.setAttribute("strengths",strengths);
		request.setAttribute("sequence",sequence);
		request.setAttribute("other", other);
	}
%>
<script>
	var idResume = '<c:out value="${idResume}"/>';
	var idJsk = '<c:out value="${idJsk}"/>';
	var sequence= '<c:out value="${sequence}"/>';
	var maximun=12;
	var minimum=5;
	$(document).ready(function()
	{
		$('input:checkbox').change(function() {
			if($('input:checkbox:checked').length>maximun)
			{
				$(this).prop('checked', false); 
				alert("<fmt:message key='STRENGTH_TITLE1' />");
				return false;
			}
		});
		<c:if test="${not empty strengths}">
			<c:forEach var="strength" items="${strengths}">
				$('input[type=checkbox][value=<c:out value="${strength.idStrength}"/>]').prop('checked', true);				
			</c:forEach>
		</c:if>
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
				if(checkedValues.length >= minimum && checkedValues.length <= maximun) //more than or equa to 5 can add
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
									if(sequence==0)
									{
										$('#resumeListLayer').modal({
	       									show: 'true'
	    								});
    								}
    								else
    								{
    									window.location.href = "?view=strengthOrder&idResume="+idResume+"&sequence="+sequence;
    								}
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
		 		else if(checkedValues.length > 12)
				{
					alert("<fmt:message key='STRENGTH_TITLE1' />");
					return false;
				}
				else //in case select strength less than 5 
				{
					alert("<fmt:message key='STRENGTH_TITLE2' />");
					return false;
				}
			}
		});
		$('#otherTick').click(function(event) 
		{
			if($(this).is(':checked')==true)
	    	{
	    		$("#otherStrength").focus();
	    		$("#otherStrength").addClass("required");
	    	}
	    	else
	    	{
	    		$("#otherStrength").val('');
	    		$("#otherStrength").removeClass("required");
	    	}
		});
		$('#otherStrength').keyup(function(event) 
		{
			if($(this).val()!="")
	    	{
	    		$("#otherTick").prop('checked', true);		
	    	}
	    	else
	    	{
	    		$("#otherTick").prop('checked', false);		
	    	}
		});
	});
	
</script>
	<form id="strengthFrm" method="post" class="form-horizontal">
	<input type="hidden" name="idResume" value="<c:out value='${idResume}'/>" >
	<input type="hidden" name="service" value="addStrength" >
	<div class="seperator"></div>
		<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
		<div class="suggestion">
			<h5><fmt:message key="DIRECTION_STRENGTH"/></h5>
		</div>
		<div class="row errorContainer" style="padding:5px">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol><br>
		</div>
		<div class="form-group" style="padding-left:10pt;">
			<div class="row">
				<div class="col-sm-10 col-xs-10" style="padding-left:20pt;">
					<div>
						<input type="hidden" name="idResume" value="<c:out value='${idResume}'/>" >
							<c:forEach var="strengthList" items="${strengthList}">
								<div class="checkbox caption">
							    	<label>
							    		<input type="checkbox" name="strength" value="<c:out value='${strengthList.idStrength}'/>"><c:out value='${strengthList.strengthName}'/>
							    	</label>
							  	</div>
							</c:forEach>
							<div class="checkbox caption">
								<label><input type="checkbox" name="strength" value="-1" id="otherTick"><font class="caption"><fmt:message key="EDU_OTHER" /></font></label>
								&nbsp;
								<input type="text" class="form-control" id="otherStrength" name="otherStrength" value="<c:out value="${other}"/>" title="<fmt:message key="GLOBAL_REQUIRE"/><fmt:message key="GLOBAL_OTHER"/>">	
							</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div align="center">
					<br>
					<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" onClick="ga('send', 'event', { eventCategory: 'Engage-Strength', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});" />
				</div>
			</div>
		</div>
		<div><font>&nbsp;</font></div>
	</form>
	<c:set var="section" value="5" scope="request"/>
	<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>