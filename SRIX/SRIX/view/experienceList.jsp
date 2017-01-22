<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="java.util.List"%>
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
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	Resume resume=null;
	if(idJsk>0 && idResume>=0)
	{
		resume=new ResumeManager().get(idJsk,idResume);
		if(resume!=null)
		{
			if(resume.getLocale()!=null)
			{
				locale=resume.getLocale();
			}
			request.setAttribute("resume",resume);
		}	
	}
	request.setAttribute("locale",locale);
%>
<fmt:setLocale value="${locale}"/>
<script>
	$(document).ready(function()
	{ 
		var d =new Date();
		$.get('/view/subview/experienceList.jsp?idResume=<c:out value="${param.idResume}"/>&sequence=<c:out value="${param.sequence}"/>&t='+d.getTime(),function(data)
		{
			$('#expList').html(data);
		});
	});

	function confirmDeleteWorkExp(idWork,idResume){
		$('#idWorkDelete').val(idWork);
		$('#idResumeDelete').val(idResume);
		$('#modalDeleteWorkExp').modal('show');
		$('#modalDeleteWorkExp').on('hidden.bs.modal', function (e) {
			$.ajax({
				type: "GET",
				url: '/view/experienceList.jsp?idResume='+idResume,
				async:false,
				success: function(data){
					$.get('/SRIX?view=experienceList&idResume='+idResume,function(data) 
					{
						$('#expList').html(data);
					});
				}
			});
		});
	}

	function deleteWorkExp(){
		var idWork = $('#idWorkDelete').val();
		var idResume = $('#idResumeDelete').val();
		var service = 'delete';
		var request = '{"service":"'+service+'","idWork":"'+idWork+'","idResume":"'+idResume+'"}';
		$.ajax({
			type: "POST",
			url: '/ExperienceEditServ',
			data: JSON.parse(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					$("#modalDeleteWorkExp").modal('hide');
				}
			}
		});
	}
</script>
<div class="seperator"></div>
<div align="right">
  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
</div>
<div>
  <div id="demo" class="collapse">
	  <div style="height:10px;"></div>
		  <p style="text-align:justify !important; text-indent:0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_EDUCATION"/></p>
  </div>
</div>
<div >
  <div id="hotline" class="collapse">
	  <div style="height:10px;"></div>
	 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
  </div>
</div>
<br>

<div class="form-group">
	<div class="section_header alignCenter">
		<h3><fmt:message key="GLOBAL_EXPERIENCE"/></h3>
		<div id="expList"></div>
	</div>
</div>
<!-- modal skip delete -->
<div class="modal fade bs-example-modal-sm" id="modalDeleteWorkExp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<input type="hidden" id="idWorkDelete" value="">
				<input type="hidden" id="idResumeDelete" value="">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalDeleteWorkExpLabel">Confirm</h4>
			</div>
			<div class="modal-body">
				<fmt:message key="CONFIRM_DELETE"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
				<button type="button" class="btn btn-primary" onclick="deleteWorkExp()"><fmt:message key="GLOBAL_CONFIRM"/></button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal skip delete -->