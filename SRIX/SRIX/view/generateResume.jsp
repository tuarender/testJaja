<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.getInt(request.getParameter("idResume"));
	int delete = Util.getInt(request.getParameter("delete"));
	String idSession=Encoder.getEncode(request.getSession().getId());
	Resume resume = new ResumeManager().get(idJsk, idResume);
	List<Resume> resumeList = new ResumeManager().getAll(idJsk,false,false,true);
	int resumeListCount = resumeList.size();
	request.setAttribute("resumeList",resumeList);
	request.setAttribute("resumeListCount",resumeListCount);
	request.setAttribute("delete", delete);
	request.setAttribute("currentIdResume",idResume);
%>
<script>
	$(document).ready(function(){
		var resumeListCount  = <%=resumeListCount%>
		
		$('#confirmBtn').click(function(){
			if(resumeListCount<10)
			{
				window.location.href="/SRIX?view=naming&cmd=GENERATEPARENT&sequence=0&idResume=<%=idResume%>&jSeesion=<%=idSession%>";
			}
			else{
				window.location.href="/SRIX?view=generateResume&idResume=<%=idResume%>&jSeesion=<%=idSession%>&delete=1";	
				return true; 
			}
		});
		
		$('#cancelBtn').click(function(){
			window.location.href="/SRIX?view=home&jSeesion=<%=idSession%>";
		});
		
		$('#deleteBtn').click(function(){
			$('#chooseResumeFrm').submit();
		});
		var container = $('div#errorContainer');
		$('#chooseResumeFrm').validate(
			{
				errorContainer: container,
				errorLabelContainer: $("ol", container),
				wrapper: 'li',
				focusInvalid: false,
				invalidHandler: function(form, validator) 
				{
				$('html, body').animate({scrollTop:"600px" }, 300);      
				},
				rules :
				{
					idResumeDelete:
					{
						required:true
					}
				},
				messages :
				{
					idResumeDelete:
					{
						required:"please select resume for delete"
					}
				},
				submitHandler:function(form)
				{
					var idResumeDel = $('input[name=idResumeDelete]:checked', '#chooseResumeFrm').val();
					window.location.href="/SRIX?view=naming&generateResume=1&cmd=GENERATEPARENT&sequence=0&idResume=<%=idResume%>&idResumeDel="+idResumeDel+"&jSeesion=<%=idSession%>";
				}
		});
	}); 
</script>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
	<br>
<div>
	<c:choose>
	<c:when test="${delete eq 1}">
		<div>
			<form name="chooseResumeFrm" id="chooseResumeFrm">
				<div class="errorContainer" id="errorContainer">
					<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
					<ol></ol>
				</div>
				<div class='col-xs-12 col-sm-12' style='height:20px;'>&nbsp;</div>
				<div class="col-sm-12 col-xs-12" style="text-align:center;font-size:16px;">
					<span class="section_header"><fmt:message key="FULLFILL_DELETE_EDIT_CONFIRM"/></span>
				</div>
				<div class='col-xs-12 col-sm-12' style='height:20px;'>&nbsp;</div>
					<c:forEach begin="0" end="9" var="resume" items="${resumeList}" varStatus="idx">
						<div class='col-xs-12 col-sm-12' style='height:20px;'>&nbsp;</div>
						<div class='col-xs-12 col-sm-12'>
							<div class="hidden-xs col-sm-1">&nbsp;</div>
							<div class="col-xs-1 col-sm-1">
								<c:choose>
									<c:when test="${idx.index eq 0}">
										<c:if test="${resume.idResume ne currentIdResume}">
											<input type="radio" name="idResumeDelete" id="idResumeDelete" value="<c:out value="${resume.idResume}"/>" checked>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${resume.idResume ne currentIdResume}">
											<input type="radio" name="idResumeDelete" id="idResumeDelete" value="<c:out value="${resume.idResume}"/>">
										</c:if>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="col-xs-11 col-sm-7">
								<b class="section_header"><c:out value="${resume.resumeName}"/></b>
							</div>
							<div class="col-xs-12 col-sm-2">
								<a target="_blank"  href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message key="GLOBAL_VIEW"/></a>
							</div>
						</div>
						<div class='col-xs-12 col-sm-12'>
							<div class="hidden-xs col-sm-2">
								&nbsp;
							</div>
							<div class="col-xs-12 col-sm-10">
								<font class="caption">
								(	
									<fmt:formatDate var="timestamps" value="${resume.timeStamp}" pattern="d/M/yyyy"/>
									<fmt:message key="APPLY_INFO_DATE"><fmt:param value="${timestamps}"/></fmt:message>
									<c:choose>
										<c:when test="${resume.yearPass>0 || resume.monthPass>0 || resume.dayPass>0}">
											<fmt:message key="APPLY_AGO"/>
											<c:if test="${resume.yearPass>0}">&nbsp;<c:out value="${resume.yearPass}"/>&nbsp;<fmt:message key="APPLY_YEAR"/></c:if>
											<c:if test="${resume.monthPass>0}">&nbsp;<c:out value="${resume.monthPass}"/>&nbsp;<fmt:message key="APPLY_MONTH"/></c:if>
											<c:if test="${resume.dayPass>0}">&nbsp;<c:out value="${resume.dayPass}"/>&nbsp;<fmt:message key="APPLY_DAY"/></c:if>
											<fmt:message key="APPLY_AGO2"/>
										</c:when>
									</c:choose>
								)
								</font>
							</div>
						</div>
					</c:forEach>
				<div class='col-xs-12 col-sm-12' style='height:50px;'>&nbsp;</div>
				<div class="col-sm-12 col-xs-12" style="text-align:center;">
					<div class="col-xs-12 col-md-6" style="text-align:center;">
						<a id="cancelBtn" class="btn btn-default" ><fmt:message key="GLOBAL_NOT_DELETE_RESUME"/></a>
					</div>
					<div class="col-sm-12 col-xs-12 visible-xs visible-sm" style="height:30px;">&nbsp;</div>
					<div class="col-xs-12 col-md-6" style="text-align:center;">
						<a id="deleteBtn" class="btn btn-primary" ><fmt:message key="GLOBAL_DELETE_RESUME"/></a>
					</div>
				</div>
			</form>
		</div>
	</c:when>
	<c:otherwise>
		<div class="col-sm-12 col-xs-12" style="text-align:center;font-size:16px;">
			<span class="section_header"><fmt:message key="GENERATE_RESUME_HEADER"/></span>
		</div>
		<div class="col-sm-12 col-xs-12" style="height:70px;">&nbsp;</div>
		<div class="col-sm-12 col-xs-12" style="text-align:center;">
			<div class='hidden-xs col-sm-2'>&nbsp;</div>
			<div class="col-xs-12 col-md-4" style="text-align:center;">
				<a id="cancelBtn" class="btn btn-default" ><fmt:message key="GLOBAL_CANCEL"/></a>
			</div>
			<div class="col-sm-12 col-xs-12 visible-xs visible-sm" style="height:30px;">&nbsp;</div>
			<div class="col-xs-12 col-md-4" style="text-align:center;">
				<a id="confirmBtn" class="btn btn-primary" ><fmt:message key="GLOBAL_NEXT"/></a>
			</div>
			<div class='hidden-xs col-sm-2'>&nbsp;</div>
		</div>
	</c:otherwise>
	</c:choose>
	<div class='col-xs-12 col-sm-12' style='height:20px;'>&nbsp;</div>
</div>
