<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.*"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
		<c:set var="locale" value="${resume.locale}" scope="request"></c:set>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
		<c:set var="locale" value="${sessionScope.SESSION_LOCALE}" scope="request"></c:set>
	</c:otherwise>
</c:choose>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{
		int idLanguage=resume.getIdLanguage();
		List<Resume> resumeList=new ResumeManager().getAllResumeByIdLanguage(idJsk,idLanguage,idResume);
		
		request.setAttribute("resumeList",resumeList);
		request.setAttribute("idResume",idResume);
		request.setAttribute("resume",resume);
	}
	System.out.println();
%>
<c:choose>
	<c:when test='${idResume eq 0}'>
		<c:set var="updatedTxt" value="SYNC_MEMBER_UPDATE" scope="request"></c:set>
		<c:set var="descriptionTxt" value="SYNC_MEMBER_DESCRIPTION" scope="request"></c:set>
		<c:set var="yesTxt" value="SYNC_MEMBER_UPDATE_YES" scope="request"></c:set>
		<c:set var="resumeName" scope="request"><strong style="color:#ad70ad;"><fmt:message key="MENU_ACCOUNT"/></strong></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="updatedTxt" value="SYNC_UPDATE" scope="request"></c:set>
		<c:set var="descriptionTxt" value="SYNC_DESCRIPTION" scope="request"></c:set>
		<c:set var="yesTxt" value="SYNC_UPDATE_YES" scope="request"></c:set>
		<c:set var="resumeName" scope="request"><strong style="color:#ad70ad;"><c:out value="${resume.resumeName}"/></strong></c:set>
	</c:otherwise>
</c:choose>

<div class="modal fade" id="resumeListLayer" role="dialog">
	<form id="syncResumeFrm" name="syncResume" method="POST">
		<div class="modal-dialog modal-xs">
				<div class="modal-content">
			        <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"  style="color:#725fa7">
							<fmt:message key="SYNC_UPDATE"><fmt:param value="${resumeName}"/></fmt:message>
						</h4>
			        </div>
			        <div class="modal-body">
						<span><fmt:message key="${descriptionTxt}"/></span>
						<div class="radio" style="color:#74BEA6">
						  <label><input type="radio" name="targetResume" value="1" checked="checked"><fmt:message key="${yesTxt}"/></label>
						</div>
						<div class="radio" style="color:#D63301;">
						  <label><input type="radio" name="targetResume" value="2"><fmt:message key="SYNC_UPDATE_NO"/></label>
						</div>
						<div class="radio answer">
							 <label><input type="radio" name="targetResume" value="3"><fmt:message key="SYNC_UPDATE_SOME"/></label>
							<div id="targetResumeLayer" style="display:none;">
								<c:forEach var="resume" items="${resumeList}">
									<c:choose>
										<c:when test='${resume.idResume ne 0}'>
											<div class="checkbox">
												<label><input type="checkbox" name="targetIdResume" value="<c:out value='${resume.idResume}'/>"><c:out value='${resume.resumeName}'/></label>
											</div>
										</c:when>
									</c:choose>
								</c:forEach>
								<span style="color:#333;"><fmt:message key="SYNC_CAUTION"/></span>
							</div>
						</div>
			        </div>
			        <div class="modal-footer">
				        <div align="center">
						  <input type="image" src="/images/<c:out value='${locale}'/>/confirm.png" id="submitBtn"/>
						</div>
			        </div>
		    	</div>
		    <input type="hidden" name="idResumeSrc" value="<c:out value="${idResume}"/>">
		  	<input type="hidden" name="section" value="<c:out value="${section}"/>">
		  	<input type="hidden" name="view" value="<c:out value="${view}"/>">
		  	<input type="hidden" name="view" value="<c:out value="${backToView}"/>">
		</div>
	</form>
</div>

<script>
	var section=<c:out value='${section}'/>;
	var backToView='<c:out value='${backToView}'/>';
	var idResume=<c:out value='${idResume}'/>;
	var sequence=<c:out value='${sequence}'/>;
	var view='<c:out value="${view}"/>';
	$(document).ready(function()
	{
		$('input[name=targetResume]').on('change',function(){
			if($(this).val()==3)
			{
				$('#targetResumeLayer').show();
			}
			else
			{
				$('input[name=targetIdResume]').attr("checked",false);
				$('#targetResumeLayer').hide();
			}
		});
		
		$("#syncResumeFrm").submit(function(e) {
		    $.ajax({
				type: "POST",
				url: '/SyncResumeServ',
				data: $("#syncResumeFrm").serialize(), 
				success: function(data)
				{
					if(section==1 || section==2)
					{
						//var targetUrl='/SRIX?view=resumeInfo&idResume='+idResume;
						var targetUrl='/SRIX?view=syncComplete&idResume='+idResume;
						window.location.href = targetUrl;
					}
					else if(section==3)
					{
						//var targetUrl='/SRIX?view=education&idResume='+idResume+'&sequence='+sequence+'&section='+section;
						var targetUrl='/SRIX?view=syncComplete&idResume='+idResume+'&sequence='+sequence+'&section='+section;
						if(backToView!="")
						{
							targetUrl=targetUrl+'&backToView='+backToView;
						}
						window.location.href = targetUrl;
					}
					else if(section==4)
					{
						//var targetUrl='/SRIX?view=resumeInfo&idResume='+idResume;
						var targetUrl='/SRIX?view=syncComplete&idResume='+idResume;
						if(backToView!="")
						{
							targetUrl='/SRIX?view='+backToView+'&idResume='+idResume;
						}
						window.location.href = targetUrl;
					}
					else if(section==5)
					{
						if(view=='strength')
						{
							window.location.href = "?view=strengthOrder&idResume="+idResume+"&sequence="+sequence;
						}
						else if(view=='strengthOrder')
						{
							window.location.href = "?view=strengthDetail&idResume="+idResume+"&sequence="+sequence;	
						}
						else if(view=='strengthDetail')
						{
							//window.location.href = "?view=resumeInfo&idResume="+idResume;
							window.location.href = "?view=syncComplete&idResume="+idResume;
						}
					}
					else if(section==6)
					{
						if(view=='aptitude')
						{
							window.location.href = "?view=aptitudeLevel&idResume="+idResume+"&sequence="+sequence;
						}
						else if(view=='aptitudeLevel')
						{
							window.location.href = "/SRIX?view="+pages+"&idResume="+idResume+"&sequence="+sequence;
						}
						else if(view=='aptitudeExtension')
						{
							window.location.href = "/SRIX?view=aptitudeRank&idResume="+idResume+"&sequence="+sequence;
						}
						else if(view=='aptitudeRank')
						{
							//window.location.href = "?view=resumeInfo&idResume="+idResume;
							window.location.href = "?view=syncComplete&idResume="+idResume;
						}
					}
				}
			});
		    e.preventDefault(); 
		});
	});
</script>
