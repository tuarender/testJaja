<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.DecryptionLink"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.resume.WorkExperienceManager"%>
<%@page import="com.topgun.resume.WorkExperience"%>
<%@page import="com.topgun.util.Util"%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<%
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	String enc=Util.getStr(request.getParameter("enc"));
	int idWork = Util.getInt(request.getParameter("idWork"));
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.getInt(request.getParameter("idResume"),-1);
	int applyAll = Util.getInt(request.getParameter("applyAll"),0);
	String positionName = "";
	WorkExperience exp = null ;
	if(idResume>=0 && idWork>0)
	{
		exp = new WorkExperienceManager().get(idJsk, idResume, idWork);
		request.setAttribute("companyName",exp.getCompanyName());
		request.setAttribute("positionName",exp.getPositionLast());
		request.setAttribute("jobDesc",exp.getJobDesc());
		request.setAttribute("achieve", exp.getAchieve());
	}
	String path = "/ApplyServ";
	if(applyAll==1)
	{
		path = "/ApplyAllServ";
	}
	
	request.setAttribute("enc", enc);
	request.setAttribute("path", path);
%>
<div class="seperator">&nbsp;</div>
<div class="row">
	<div class="col-xs-12 text-center">
		<span class="font_16 color715fa8"><fmt:message key="FULLFILL_EXP_HEADER"/></span>
	</div>
</div>
<div class="seperator">&nbsp;</div>
<div class="seperator">&nbsp;</div>
<div class="row">
	<div class="col-xs-12">
		<b><u><span class="color333333"><fmt:message key="FULLFILL_EXP_LAST_POSITION"/></span></u></b>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<span class="color58595b"><fmt:message key="FULLFILL_EXP_COMPANY_NAME"/> : <c:out value="${companyName}" escapeXml="false"/></span>
	</div>
	<div class="col-xs-12">
		<span class="color58595b"><fmt:message key="FULLFILL_EXP_POSITION_NAME"/> : <c:out value="${positionName}" escapeXml="false"/></span>
	</div>
</div>
<div class="seperator">&nbsp;</div>
<div class="seperator">&nbsp;</div>
<form id="fillExpFrm">
	<input type="hidden" name="service" value="fillExperience">
	<input type="hidden" name="idResume" value="<%=idResume%>">
	<input type="hidden" name="idWork" value="<%=idWork%>">
	<div class="form-group">
		<div class="row">
			<div class="col-xs-7 text-left">
				<label class="color333333" for="jobDesc"><fmt:message key="FULLFILL_EXP_RESPONSIBILITY"/></label>
			</div>
			<div class="col-xs-5 text-right" > 
				<a href='#modalJobDesc' data-toggle='modal'><fmt:message key="FULLFILL_EXP_EXAMPLE"/></a>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<textarea  rows="5" cols="30" maxlength='1000' name="jobDesc" id="jobDesc" onkeyup="callMaxlengthEvent('jobDesc')"  class="form-control color333333"><c:out value="${jobDesc}"/></textarea>
			</div>
		</div>
		<span id='countChar_jobDesc'>0</span>&nbsp;<fmt:message key='STRENGTH_MAX_CHAR'/>
	</div>

	<br>

	<div class="form-group">
		<div class="row">
			<div class="col-xs-7 text-left"  >
				<label class="color333333" for="achieve"><fmt:message key="PREVIEW_WORKING_ACHIEVEMENT"/><b>(<fmt:message key="GLOBAL_OPTIONAL"/>)</b></label>
			</div>
			<div class="col-xs-5 text-right" > 
				<a href='#modalAchievement' data-toggle='modal'><fmt:message key="FULLFILL_EXP_EXAMPLE"/></a>
			</div>
		</div>
		<textarea rows="5" cols="30"  maxlength='1000' name="achieve" id="achieve" onkeyup="callMaxlengthEvent('achieve')" class="form-control color333333"><c:out value="${achieve}"/></textarea>
		<span id='countChar_achieve'>0</span>&nbsp;<fmt:message key='STRENGTH_MAX_CHAR'/>
	</div>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<input type="image" src="/images/<c:out value='${resume.locale}'/>/save.png"  name="btn_submit" id="btn_submit" />
		</div>
	</div>
	<div class="row" align="center">
		<a href="<c:out value="${path}"/>?enc=<c:out value="${enc}"/>&chkFillResume=1&skipExp=1&clickSkipExp=1"><fmt:message key="FULLFILL_EXP_SKIP"/></a>
	</div>
</form>

<div class="modal fade" id="modalJobDesc" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:680px !important;">
		<div class="modal-content">
			<div class="modal-body">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<br>
				<center><img src="/images/<c:out value='${resume.locale}'/>/responsibility_example.png" /></center>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="modalAchievement" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:680px !important;">
		<div class="modal-content">
			<div class="modal-body">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<br>
				<center><img src="/images/<c:out value='${resume.locale}'/>/achievement_example.png" /></center>
			</div>
		</div>
	</div>
</div>
<script>
	var applyAll = <%=applyAll%>;
	function callMaxlengthEvent(id){
		$('#'+id).keyup(function(){  
	        var limit = parseInt($(this).attr('maxlength'));  
	        var text = $(this).val(); 
	        $('#countChar_'+id).html(text.length);
	    });
	}
	$(document).ready(function(){
		$('#fillExpFrm').submit(function(){
			$.ajax(
			{
				type: "POST",
	   			url: '/ExperienceEditServ',
	   			data: $('#fillExpFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				if(applyAll==1)
	   				{
	   					window.location.href="/ApplyAllServ?&enc=<%=enc%>&chkFillResume=1&skipExp=1&submitExp=1";
	   				}
	   				else
	   				{
	   					window.location.href="/ApplyServ?&enc=<%=enc%>&chkFillResume=1&skipExp=1&submitExp=1";
	   				}
	   			}
	 		});
	 		return false ;
		})
    });
</script>
