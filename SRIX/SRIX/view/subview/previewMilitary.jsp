<%@page import="com.topgun.resume.AdditionalManager"%>
<%@page import="com.topgun.resume.Additional"%>
<%@page import="com.topgun.resume.AdditionalInfoManager"%>
<%@page import="com.topgun.resume.AdditionalInfo"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%
	PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	String idCard="";
	Resume resume=new ResumeManager().get(idJsk, idResume);
	Additional additional= null;
	int idCountry=0;
	
	if(resume!=null)
	{
		additional=new AdditionalManager().get(resume.getIdJsk(),resume.getIdResume());
		if(additional!=null )
		{
			idCountry=resume.getTemplateIdCountry();
			idCard= Util.getStr(additional.getIdCard());
		}
	}
	request.setAttribute("idJsk", idJsk);
	request.setAttribute("idResume", idResume);
	request.setAttribute("resume", resume);
	request.setAttribute("additional", additional);
	request.setAttribute("idCountry", idCountry);
	request.setAttribute("idCard", idCard);
	request.setAttribute("additionalCard", propMgr.getMessage(resume.getLocale(),"ADDITION_IDCARD"));
	request.setAttribute("ADDITION_DRIVE_QUESTION", propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_QUESTION"));
	request.setAttribute("ADDITION_DRIVE_STATUS_NO", propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_STATUS_NO"));
	request.setAttribute("ADDITION_DRIVE_STATUS_YES", propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_STATUS_YES"));
	request.setAttribute("ADDITION_DRIVE_ID", propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_ID"));
	request.setAttribute("ADDITION_HADICAP_STATUS_NO", propMgr.getMessage(resume.getLocale(),"ADDITION_HADICAP_STATUS_NO"));
	request.setAttribute("ADDITION_HADICAP_QUESTION", propMgr.getMessage(resume.getLocale(),"ADDITION_HADICAP_QUESTION"));
	request.setAttribute("ADDITION_HADICAP_STATUS_YES", propMgr.getMessage(resume.getLocale(),"ADDITION_HADICAP_STATUS_YES"));
	request.setAttribute("ADDITION_ILLNESS_QUESTION", propMgr.getMessage(resume.getLocale(),"ADDITION_ILLNESS_QUESTION"));
	request.setAttribute("ADDITION_ILLNESS_STATUS_NO", propMgr.getMessage(resume.getLocale(),"ADDITION_ILLNESS_STATUS_NO"));
	request.setAttribute("ADDITION_ILLNESS_STATUS_YES", propMgr.getMessage(resume.getLocale(),"ADDITION_ILLNESS_STATUS_YES"));
	request.setAttribute("ADDITION_FIRE_QUESTION", propMgr.getMessage(resume.getLocale(),"ADDITION_FIRE_QUESTION"));
	request.setAttribute("ADDITION_FIRE_STATUS_NO", propMgr.getMessage(resume.getLocale(),"ADDITION_FIRE_STATUS_NO"));
	request.setAttribute("ADDITION_FIRE_STATUS_YES", propMgr.getMessage(resume.getLocale(),"ADDITION_FIRE_STATUS_YES"));
	request.setAttribute("ADDITION_CRIMINAL_QUESTION", propMgr.getMessage(resume.getLocale(),"ADDITION_CRIMINAL_QUESTION"));
	request.setAttribute("ADDITION_CRIMINAL_STATUS_NO", propMgr.getMessage(resume.getLocale(),"ADDITION_CRIMINAL_STATUS_NO"));
	request.setAttribute("ADDITION_CRIMINAL_STATUS_YES", propMgr.getMessage(resume.getLocale(),"ADDITION_CRIMINAL_STATUS_YES"));
 %>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<script>
/*
function deleteMilitary(idResume){
	var service = 'deleteAdditional';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'"}';
	if(confirm("Are you sure to delete additional data?")){
		$.ajax({
			type: "POST",
	  		url: '/AdditionalServ',
			data: JSON.parse(request),
			async:false,
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj.success==1)
				{
					$.get('/view/mobile/subview/previewMilitary.jsp?idResume='+idResume,function(data) 
					{
						$('#layer_17').html(data);
					});
				}
			}
		});
	}
}
*/
function confirmDeleteMilitary(idResume)
{
	$('#idResumeDelete').val(idResume);
	$('#modalDeleteMilitary').modal('show');
	$('#modalDeleteMilitary').on('hidden.bs.modal', function (e) {
		$.ajax({
			type: "GET",
			url: '/view/subview/previewMilitary.jsp?idResume='+idResume,
			async:false,
			success: function(data)
			{
				$('#layer_17').html(data);
			}
		});
	});
}

function deleteMilitary(){
	var idResume = $('#idResumeDelete').val();
	var service = 'deleteAdditional';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'"}';
	$.ajax({
		type: "POST",
  		url: '/AdditionalServ',
		data: JSON.parse(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				$("#modalDeleteMilitary").modal('hide');
			}
		}
	});
}
</script>
<div class="row answer" style="padding-left:20pt;padding-top:10pt;padding-right:20pt;padding-bottom:10pt;">
	<c:if test="${not empty resume}">
		<c:choose>
			<c:when test="${not empty additional}">
				<c:choose>
					<c:when test="${idCountry == 216}">
						<c:if test="${idCard != ''}">
							<b class="caption"><c:out value="${additionalCard}" />:</b>&nbsp;
							<c:out value="${additional.idCard}" /><br>
							<b class='caption'><c:out value="${ADDITION_DRIVE_QUESTION}" />:</b>&nbsp;
							<c:choose>
								<c:when test="${additional.driveStatus == 'FALSE'}">
									<c:out value="${ADDITION_DRIVE_STATUS_NO}" /><br>
								</c:when>
								<c:when test="${additional.driveStatus == 'TRUE'}">
									<c:out value="${ADDITION_DRIVE_STATUS_YES}" />
									<c:if test="${additional.idDrive != ''}">
										,&nbsp;
										<c:out value="${ADDITION_DRIVE_ID}" />
										<c:out value="${additional.idDrive}" /><br>
									</c:if>
								</c:when>
							</c:choose>
							<b class="caption"><c:out value="${ADDITION_HADICAP_QUESTION}" />:</b>&nbsp;
							<c:choose>
								<c:when test="${additional.handicapStatus == 'FALSE'}">
									<c:out value="${ADDITION_HADICAP_STATUS_NO}" /><br>
								</c:when>
								<c:when test="${additional.handicapStatus == 'TRUE'}">
									<c:out value="${ADDITION_HADICAP_STATUS_YES}" />
									<c:if test="${additional.handicapReason != ''}">
										,&nbsp;<c:out value="${additional.handicapReason}" />
									</c:if>
									<br>
								</c:when>
							</c:choose>
							<b class="caption"><c:out value="${ADDITION_ILLNESS_QUESTION}" />:</b>&nbsp;
							<c:choose>
								<c:when test="${additional.illnessStatus == 'FALSE'}">
									<c:out value="${ADDITION_ILLNESS_STATUS_NO}" /><br>
								</c:when>
								<c:when test="${additional.illnessStatus == 'TRUE'}">
									<c:out value="${ADDITION_ILLNESS_STATUS_YES}" />
									<c:if test="${additional.illnessReason != ''}">
										,&nbsp;<c:out value="${additional.illnessReason}" />
									</c:if>
									<br>
								</c:when>
							</c:choose>
							<b class="caption"><c:out value="${ADDITION_FIRE_QUESTION}" />:</b>&nbsp;
							<c:choose>
								<c:when test="${additional.fireStatus == 'FALSE'}">
									<c:out value="${ADDITION_FIRE_STATUS_NO}" /><br>
								</c:when>
								<c:when test="${additional.fireStatus == 'TRUE'}">
									<c:out value="${ADDITION_FIRE_STATUS_YES}" />
									<c:if test="${additional.fireReason != ''}">
										,&nbsp;<c:out value="${additional.fireReason}" />
									</c:if>
									<br>
								</c:when>
							</c:choose>
							<b class="caption"><c:out value="${ADDITION_CRIMINAL_QUESTION}" />:</b>&nbsp;
							<c:choose>
								<c:when test="${additional.criminalStatus =='FALSE'}">
									<c:out value="${ADDITION_CRIMINAL_STATUS_NO}" /><br>
								</c:when>
								<c:when test="${additional.criminalStatus =='TRUE'}">
									<c:out value="${ADDITION_CRIMINAL_STATUS_YES}" />
									<c:if test="${additional.criminalReason != ''}">
										<c:out value="${additional.criminalReason}" />
									</c:if>
									<br>
								</c:when>
							</c:choose>
						</c:if>							
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${additional.militaryStatus == 1}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ACTIVE")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 2}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_RESERV")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 3}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_NATIONAL")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 4}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_RETIRED")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 5}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ANG")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 6}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_IR")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 7}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ING")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 8}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_VETERAN")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 9}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_DISABLED")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 10}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SERVICE")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 11}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ROTC")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 12}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SSF")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 13}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SSR")%><br>
							</c:when>
							<c:when test="${additional.militaryStatus == 14}">
								<%=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SOV")%><br>
							</c:when>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<div align='left'>
					<a href="/SRIX?view=additional&sequence=0&idResume=<c:out value="${idResume}"/>"><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_EDIT")%></a>
					&nbsp;|&nbsp;<a href="#" class="deleteMilitary" onClick='confirmDeleteMilitary(<c:out value="${resume.idResume}"/>)'><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_DELETE")%></a>
				</div>
			</c:when>
			<c:otherwise>
				<div align='right'>
					<a href="/SRIX?view=additional&sequence=0&idResume=<c:out value="${idResume}" />" class='button_link'><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_ADD")%></a>
				</div>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>
<!-- modal skip delete -->
<div class="modal fade bs-example-modal-sm" id="modalDeleteMilitary" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<input type="hidden" id="idResumeDelete" value="">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalDeleteMilitaryLabel">Confirm</h4>
			</div>
			<div class="modal-body">
				<fmt:message key="CONFIRM_DELETE"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
				<button type="button" class="btn btn-primary" onclick="deleteMilitary()"><fmt:message key="GLOBAL_CONFIRM"/></button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal skip delete -->