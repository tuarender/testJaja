<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.shris.masterdata.CareerObjective"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.CareerObjectiveManager"%>
<%@page import="com.topgun.resume.Resume"%>
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
<%
	Resume resume=(Resume)request.getAttribute("resume");
	if(resume!=null)
	{
		int idLanguage=resume.getIdLanguage();
		int idCountry=resume.getIdCountry();
		int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
		int idResume = Util.StrToInt(request.getParameter("idResume"));
		int sequence=Util.getInt(request.getParameter("sequence"),1);
		
		
		List<CareerObjective> careerObjectiveList=MasterDataManager.getAllCareerObjective(idLanguage);
		
		List<com.topgun.resume.CareerObjective> careerObjectives=new CareerObjectiveManager().getAll(idJsk, idResume);
		request.setAttribute("idResume",idResume);
		request.setAttribute("sequence",sequence);
		request.setAttribute("CareerObjectiveList",careerObjectiveList);
		request.setAttribute("careerObjectives",careerObjectives); 
	}
	
%>
<script>
	$(document).ready(function()
	{
		var container = $('div.errorContainer');
		$('#careerFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
			invalidHandler: function(form, validator) 
			{
				$('html, body').animate({scrollTop: '0px'}, 300);      
			},
			submitHandler:function(form)
			{
				var checkBoxLength=$( "input:checked" ).length;
				if(checkBoxLength<0)
				{
					$('div.container li').remove();
	  				container.append('<li><label class="error"><fmt:message key="CAREEROBJTIVE_COMMAND1"/></label></li>');
	   				container.css({'display':'block'});
	   				$('html, body').animate({scrollTop: '0px'}, 300); 
				}
				else if(checkBoxLength>2)
				{
					$('div.container li').remove();
	  				container.append('<li><label class="error"><fmt:message key="CAREEROBJTIVE_COMMAND2"/></label></li>');
	   				container.css({'display':'block'});
	   				$('html, body').animate({scrollTop: '0px'}, 300); 
				}
				else
				{
					$.ajax(
					{
						type: 'POST',
	           			url: '/CareerServ',
	           			data: $("#careerFrm").serialize(),
	           			success: function(data)
	           			{
	           				var obj = jQuery.parseJSON(data);
	           				if(obj.success==1)
	           				{
	           					if(<c:out value="${sequence}"/>==0)
	           					{
	           						window.location.href='/SRIX?view=resumeInfo&idResume=<c:out value="${idResume}"/>';
	           					}
	           					else
	           					{
	           						window.location.href='/SRIX?view=personal&idResume=<c:out value="${idResume}"/>&sequence=<c:out value="${sequence}"/>';
	           					}
	           				}
	           			}
			 		});
				}
				return false ;
			}		
		});

		$('#idOther1').click(function()
		{
			if($(this).is(':checked'))
			{
				$('#othCareer_1').rules('add', 
				{ 
					required: true
				});
				
				$('#othCareer_1').focus();
			}
			else
			{
				$('#othCareer_1').rules('remove');
				$('#othCareer_1').val('');
			}
		});
		
		
		$('#idOther2').click(function()
		{
			if($(this).is(':checked'))
			{
				$('#othCareer_2').rules('add', 
				{ 
					required: true
				});
				
				$('#othCareer_2').focus();
			}
			else
			{
				$('#othCareer_2').rules('remove');
				$('#othCareer_2').val('');
			}
		});
		
		$('#othCareer_2').click(function()
		{
			$('#idOther2').prop('checked',true);
		});
		
		$('#othCareer_1').click(function()
		{
			$('#idOther1').prop('checked',true);
		});
		
	});
</script>
<div class="seperator"></div>
	<div align="right" >
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div >
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent: 0.5cm;" class="color59595c font_14"><fmt:message  key="COLLAPSE_EDUCATION"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
	<br>
<form action="" method="post" id="careerFrm">
	<input type="hidden" name="idResume" value="<c:out value="${idResume}"/>" />
	<h3 align="center" class="section_header"><fmt:message key="CAREEROBJECTIVE"/></h3>
	<div class="caption">
		<b>*<fmt:message key="CAREEROBJTIVE_COMMAND1"/> <fmt:message key="CAREEROBJTIVE_COMMAND2"/></b>
	</div>
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="form-group answer">
		<c:forEach var="careerobj" items="${CareerObjectiveList}" varStatus="idx">
			<c:set var='checked' value=""/>
			<c:forEach var="jsk_Career" items="${careerObjectives}">
				<c:if test="${jsk_Career.idCareerObjective eq careerobj.idCareerobj}">
					<c:set var='checked' value="checked"/>
				</c:if>
			</c:forEach>
			<label>
			<input type='checkbox' class='required' <c:out value="${checked}"/> title='<fmt:message key="CAREEROBJTIVE_COMMAND1"/>' name="idCareerObjective" id="<c:out value="${careerobj.idCareerobj}"/>" value="<c:out value="${careerobj.idCareerobj}"/>">&nbsp;&nbsp;
			<c:out value="${careerobj.careerobjName}"/>
			</label>
			<br />
		</c:forEach>
		<c:set var='checked' value=""/>
		<c:set var='other' value=""/>
		<c:forEach var="jsk_Career" items="${careerObjectives}">
			<c:if test="${jsk_Career.idCareerObjective eq -1}">
				<c:set var='checked' value="checked"/>
				<c:set var='other' value="${jsk_Career.otherObjective}"/>
			</c:if>
		</c:forEach>
		<input type='checkbox' class='required' <c:out value="${checked}"/> title='<fmt:message key="CAREEROBJTIVE_COMMAND1"/>' name="idCareerObjective" id="idOther1" value="-1">&nbsp;&nbsp;
		<fmt:message key="GLOBAL_OTHER_SPECIFY"/>&nbsp;&nbsp;
		<input type="text" name="othCareer1" class="form-control" id="othCareer_1" maxlength="50" value="<c:out value="${other}"/>" title='<fmt:message key="GLOBAL_SPECIFY"/><fmt:message key="CAREEROBJECTIVE"/>'>
		<c:set var='checked' value=""/><br />
		<c:set var='other' value=""/>
		<c:forEach var="jsk_Career" items="${careerObjectives}">
			<c:if test="${jsk_Career.idCareerObjective eq -2}">
				<c:set var='checked' value="checked"/>
				<c:set var='other' value="${jsk_Career.otherObjective}"/>
			</c:if>
		</c:forEach>
		<input type='checkbox' class='required' class="form-control" <c:out value="${checked}"/> title='<fmt:message key="CAREEROBJTIVE_COMMAND1"/>'  name="idCareerObjective" id="idOther2" value="-2">&nbsp;&nbsp;
		<fmt:message key="GLOBAL_OTHER_SPECIFY"/>&nbsp;&nbsp;
		<input type="text" name="othCareer2" id="othCareer_2" class="form-control" maxlength="50" value="<c:out value="${other}"/>" title='<fmt:message key="GLOBAL_SPECIFY"/><fmt:message key="CAREEROBJECTIVE"/>'>
		<div align="center"><br/>
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  name="btn_submit" id="btn_submit" />
		</div>	
	</div>
</form>
