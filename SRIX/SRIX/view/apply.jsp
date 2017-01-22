<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.ViewResumeManager"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.shris.masterdata.State"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="java.util.Date"%>
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
	int idEmp=Util.getInt(request.getParameter("idEmp"));
	int idPosition=Util.getInt(request.getParameter("idPosition"));
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry = Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	int errorId=Util.getInt(request.getParameter("errorId"),0);
	//update all superresume status(idResume>0)
	List<Resume> rs=new ResumeManager().getAll(idJsk, false, false);
	if(rs!=null)
	{
		for(int i=0; i<rs.size(); i++)
		{
			new ResumeManager().updateStatus(idJsk, rs.get(i).getIdResume(), new ResumeStatusManager().getResumeStatus(rs.get(i)));
		}
	}
			
	
	String companyName="";
	String positionName="";
	if(idEmp!=-1 && idPosition!=-1 )
	{
		Employer emp=new EmployerManager().get(idEmp);
		if(emp!=null)
		{
			companyName=emp.getNameEng();
			if(idLanguage==38)
			{
				companyName=emp.getNameThai();
			}
			Position pos=new PositionManager().getPosition(emp.getId(), idPosition);
			if(pos!=null)
			{
				positionName=pos.getPositionName();
			}
		}
		if(companyName!="" && positionName!="")
		{
			request.setAttribute("companyName",companyName);
			request.setAttribute("positionName",positionName);
		}
	}
	
	Resume saf=new ResumeManager().get(idJsk, 0);
	List<Resume> resumes = null ;
	if(saf!=null)
	{
		if(Util.getStr(new ResumeStatusManager().getRegisterStatus(saf)).equals("TRUE"))
		{
			resumes=new ResumeManager().getAllCanApply(idJsk, true);
			
			List<Attachment> ownResumes = new AttachmentManager().getAllByFileType(idJsk,"RESUME");
			List<Attachment> docs = new AttachmentManager().getAllByFileType(idJsk,"DOCUMENT");
			request.setAttribute("resumes",resumes);
			request.setAttribute("docs",docs);
			request.setAttribute("ownResumes",ownResumes);
			request.setAttribute("saf",saf);
		}
		
	}
	request.setAttribute("idEmp",idEmp);
	request.setAttribute("idPosition",idPosition);
	
	//int inquiry=MasterDataManager.getIdQuestionInquiry(idEmp);
	int inquiry=0;
	request.setAttribute("inquiry",inquiry);
	request.setAttribute("errorId",errorId);
	String idSession=Encoder.getEncode(session.getId());
	request.setAttribute("idSession", idSession); 
	boolean isNationWideJob=new PositionManager().isNationWideJob(idEmp, idPosition);
	request.setAttribute("isNationWideJob",isNationWideJob);
	if(isNationWideJob)
	{
		List<State> states=MasterDataManager.getAllState(idCountry, idLanguage);
		request.setAttribute("states",states);
	}
	String encView=Encryption.getEncoding(0,0,idJsk,0);
	String keyView=Encryption.getKey(0,0,idJsk,0);
	request.setAttribute("encView",encView);
	request.setAttribute("keyView",keyView);
	boolean isOnline=true;
	if(idEmp>0 && idPosition>0)
	{
		isOnline=new PositionManager().isOnlinePosition(idEmp,idPosition);
	}
	request.setAttribute("isOnline",isOnline);
	int z = 0 ;
%>
<script>
	var errorContainer = null;
	var errorContainer2 = null;
	var globalFileRequire = '<fmt:message key="FILE_REQUIRE"/>';
	var globalTitleRequire = '<fmt:message key="FILE_TITLE_REQUIRE"/>';
	var globalattachResume = '<fmt:message key="HOME_YOUR_OWN_RESUME"/>';
	var globalattachDocument = '<fmt:message key="HOME_OTHER_DOCUMENT"/>';
	var globalTypeResume = '<fmt:message key="FILE_RESUME_TYPE"/>';
	var globalTypeDocument = '<fmt:message key="FILE_DOCUMENT_TYPE"/>';

	$(document).ready(function()
	{ 
		errorContainer = $('div.errorContainer');
		errorContainer2 = $('div.errorContainer2');
		
		var inquiry=parseInt(<c:out value="${inquiry}"/>)+0;
		if(inquiry>0)
		{
			var idEmp=parseInt(<c:out value="${idEmp}"/>)+0;
			var idPosition=parseInt(<c:out value="${idPosition}"/>)+0;
			if(idEmp>0 && idPosition>0)
			{
				window.location.href = "/SRIX?view=inquiry&idEmp="+idEmp+"&idPos="+idPosition;
			}
		}
		$('#cancleOwnResume').click(function(){
 			$("input[name='saf']").removeAttr("checked");
 		});
		var container = $('div.errorContainer');
		$('#applyFrm').validate(
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
  			rules:
  			{
  				email:
  				{
  					required:true 
  				},
  				idState:
  				{
  					required:true 
  				}
			},			  
			messages: 
			{
				email:
				{
					required:"<fmt:message key="APPLY_REQUIRED_EMAIL_TO"/>"
				},
				idState:
				{
					required:"<fmt:message key="APPLY_TARGET_JOB"/>"
				}
			},
			submitHandler:function(form)
			{
				form.submit();
			}
		});
		
		$('#submitAttachment').click(function(){
			errorContainer2.hide();
			var submitUpload = true;
			$('div.errorContainer2 li').remove();
			if($("#inputUploadFile").val()==""&&$('#service').val()=="addFile"){
				submitUpload = false;
	   			errorContainer2.find("ol").append('<li><label class="has-error">'+globalFileRequire+'</label></li>');
			}
			if($("#fileTitle").val()==""){
				submitUpload = false;
	   			errorContainer2.find("ol").append('<li><label class="has-error">'+globalTitleRequire+'</label></li>');
			}
			if(submitUpload){
				$('#uploadFile').submit();
			}
			else{
				errorContainer2.css({'display':'block'});
			}
		});
		
		$('.addAttachment').click(function(){
	 		errorContainer2.hide();
	 		$('div.errorContainer2 li').remove();
	 		if($(this).attr('id')=="document"){
	 			$('#myModalLabelAttachment').html(globalattachDocument);
	 			$('#attachmentType').html(globalTypeDocument);
	 			$('#DocType').val("DOCUMENT");
	 		}
	 		else if($(this).attr('id')=="ownResume"){
	 			$('#myModalLabelAttachment').html(globalattachResume);
	 			$('#attachmentType').html(globalTypeResume);
	 			$('#DocType').val("RESUME");
	 		}
	 		$('#fileTitle').val("");
	 		$('#idFile').val(0);
	 		$('#inputUploadFile').show();
			$('#modalAttachment').modal('show');
		});
		
		//--------------------ajax upload-----------------
		var options = { 
		    complete: function(response) {
		    	errorContainer2.hide();
		    	var strJson = ""+response.responseText;
		    	strJson = strJson.replace("<PRE>","");
		    	strJson = strJson.replace("</PRE>","");
		    	var obj = jQuery.parseJSON(strJson);
		    	if(obj.success==1){
		    		if(obj.idFile>0){
		    			$('#modalAttachment').modal('hide');
		    			location.reload();
		    		}
		    	}
		    	else{
		    		$('div.container li').remove();
	    			if(obj.urlError!=""){
	     				window.location.href = obj.urlError;
	     			}
	     			else{
	     				for(var i=0; i<obj.errors.length; i++){
	      					errorContainer2.find("ol").append('<li><label class="has-error">'+obj.errors[i]+'</label></li>');
		      			}
		      			errorContainer2.css({'display':'block'});
	      				$('html, body').animate({scrollTop: '0px'}, 300); 
	     			}
		    	}
		    }
		};
		
		$('#uploadFile').ajaxForm(options);
	});
</script>
<!-- modal upload attachment -->
<div class="modal fade" id="modalAttachment" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabelAttachment">Confirm</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-1 col-sm-3"></div>
					<div class="col-xs-10 col-sm-6">
						<form id="uploadFile" name="uploadFile" action="/UploadPhotoServ" method="POST" class="form-horizontal" enctype="multipart/form-data">
						<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>" >
						<input type="hidden" name="idFile" id="idFile" value="0" >
						<input type="hidden" name="service" id="service" value="addFile">
						<input type="hidden" name="DocType" id="DocType" value="">
						<div class="form-group">
							<label for="fileTitle"><fmt:message key='FILE_TITLE'/></label>
							<input class="form-control" type="text" id="fileTitle" name="fileTitle" maxlength="100">
						</div>
						<div class="form-group">
							<input name="inputUploadFile" id="inputUploadFile" type='file'>
						</div>
						<div class="form-group" id="attachmentType">
							<fmt:message key="FILE_RESUME_TYPE"/>
						</div>
						<div class="errorContainer2" style="padding:5px;display:none">
							<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
							<ol></ol>
						</div>
						</form>
					</div>
					<div class="col-xs-1 col-sm-3"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
				<button type="button" class="btn btn-primary" id="submitAttachment"><fmt:message key="GLOBAL_CONFIRM"/></button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal upload attachment -->
<c:choose>
	<c:when test="${isOnline==false}">
	<br/><br/><br/><br/>
		<div class="star">ขออภัยค่ะ ตำแหน่งงานที่คุณกำลังสมัครปิดรับสมัครเรียบร้อยแล้วค่ะ คุณสามารถดูตำแหน่งงานอื่นเพิ่มเติมได้ที่  <a href="http://www.jobtopgun.com/index.jsp?locale=th_TH">www.jobtopgun.com</a></div><br/>
		<div class="star">We’re sorry. This position has already expired. Please find more jobs on  <a href="http://www.jobtopgun.com/index.jsp?locale=en_TH">www.jobtopgun.com</a></div>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${errorId==0 }">
				<c:import url="/view/remindUpdate.jsp" charEncoding="UTF-8"/>
				<c:choose>
					<c:when test="${not empty saf}">
						<form id="applyFrm" method="post" action="/ApplyServ">
							<input type="hidden" name="idEmp" value="<c:out value="${idEmp}"/>">
							<input type="hidden" name="idPos" value="<c:out value="${idPosition}"/>">
							<input type="hidden" name="chkFillResume" value="1"/>
							<div class="section_header alignCenter">
								<h3><fmt:message key="SECTION_SENDING_PAGE"/></h3>
								<p class="suggestion"><fmt:message key="DIRECTION_APPLY"></fmt:message></font>
							</div>
							<div class="errorContainer">
								<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
								<ol></ol>
							</div>
							<div align="left" style="padding-top:1em;">
								<b class="star">*</b><font class="caption"><fmt:message key="APPLY_COMPANY_NAME"/></font><br />
								<c:choose>
									<c:when test='${not empty companyName && idEmp!=-1 && idPosition!=-1}'>
										<div class="form-group"><span class="answer">&nbsp;&nbsp;<c:out value="${companyName }"/></span></div>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<input type="text" value="" maxlength="120"  name="company" class="required form-control" title="<fmt:message key="APPLY_REQUIRED_COMNAME"/>" >
										</div>
									</c:otherwise>
								</c:choose>
								<b class="star">*</b><font class="caption"><fmt:message key="APPLY_POSITION"/></font><br/>
								<c:choose>
									<c:when test='${not empty positionName && idEmp!=-1 && idPosition!=-1}'>
										<div class="form-group"><span class="answer">&nbsp;&nbsp;<c:out value="${positionName}" escapeXml="false"/></span></div>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<input type="text" value="" maxlength="100" name="position" class="required form-control" title="<fmt:message key="APPLY_REQUIRED_POSITION"/>"> 
										</div>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${idEmp==-1 && idPosition ==-1}">
										<b class="star">*</b><font class="caption"><fmt:message key="APPLY_EMAIL_TO"/></font><br />
										<div class="form-group">
											<input type="text" value="" maxlength="100" name="email" class="required form-control" title="<fmt:message key="APPLY_REQUIRED_EMAIL_TO"/>">
										</div>
									</c:when>
								</c:choose>
							</div> 
							<div class="row" style="height:20px;" align="center"><p  class="alignCenter" style="height:2px;width:65%;border-width:2px;color:#BBBBBB;background-color:#BBBBBB"></p></div>
							<div> 
								
								<p class="section_header_blueBar"><fmt:message key="SECTION_SENT_DOCUMENT"/></p>
								<div class="row">
									<div class="col-md-1 section_header_blueBar">1.</div>
									<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11"  style="padding-left:0px;">
										<b><font color="#6d2776">Super Resume</font><br/><fmt:message key="APPLY_SUPER_RESUME"/></b>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"></div>
									<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11 suggestion" style="padding-left:0px;">
										 <b><fmt:message key="APPLY_CHOOSE_SUPER_RESUME"/></b>
									</div>
								</div>
								<c:if test="${not empty saf}">
									<c:if test="${not empty resumes}">
										<c:forEach items="${resumes}" var="resume" varStatus="loop">
											<c:if test="${resume.idParent eq 0}">
												<%
													String language=MasterDataManager.getLanguageName(resumes.get(z).getIdLanguage(),idLanguage);
													request.setAttribute("language",language);
												 %>
												<div class="row">
													<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"></div>
													<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8" style="padding-left:0px;">
														 <label>
															<input type="radio" name="idResume" value="<c:out value="${resume.idResume}"/>" class="required" title="<fmt:message key="APPLY_REQUIRED_SUPERRESUME"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_choose_p<c:out value="${loop.index+1}"/>'});">
															&nbsp;&nbsp;
															<c:choose>
																<c:when test="${resume.idResume eq 0}">
																	My Super Resume(<fmt:message key="SHORT_RESUME"/>)
																</c:when>
																<c:otherwise>
																	<c:out value="${resume.resumeName}"/>
																</c:otherwise>
															</c:choose>
															&nbsp;-&nbsp;<span class="normal"><c:out value="${language}"/></span>
														</label>
													</div>
													<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-left:0px;">
														<u><a target="_blank"  href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_ตรวจดู_<c:out value="${loop.index+1}"/>'});"><fmt:message key="GLOBAL_VIEW"/></a></u> 
													</div>
												</div>
												<div class="row">
													<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"></div>
													<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11" style="font-size:12px;font-weight:bold;">&nbsp;&nbsp;
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
															</c:choose><br>&nbsp;&nbsp;&nbsp;&nbsp;
															<a href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_ปรับปรุง/แก้ไข_<c:out value="${loop.index+1}"/>'});"><u><fmt:message key="APPLY_REVISE"/></u></a>
														)
													</div>
												</div>
											</c:if>
											<% z++; %>
										</c:forEach>
									</c:if>
								</c:if>
							</div>
							<br/>
							<div>
								<c:if test="${not empty ownResumes}">
									<div class="row">
										<div class="col-md-1 section_header_blueBar">2.</div>
										<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11 suggestion"  style="padding-left:0px;">
											<b><fmt:message key="APPLY_CHOOSE_MORE_DOCUMENT"/></b>
										</div>
									</div>
									<div class="row" style="height:6px;"></div>
									<div class="row">
										<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"> </div>
										<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11 suggestion_title"  style="padding-left:0px;">
											<b><fmt:message key="YOUR_OWN_RESUME"/></b>
										</div>
									</div>
									<c:forEach items="${ownResumes}" var="saf" varStatus="loop" >
										<div class="row">
											<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"> </div>
											<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8" style="padding-left:0px;">
												<label>
													<input type="radio" name="saf" value="<c:out value="${saf.idFile}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'resume_choose_p<c:out value="${loop.index+1}"/>'});">&nbsp;&nbsp;<c:out value="${saf.fileTitle}"/>
												</label>
											</div>
											<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-left:0px;">
												<a target="_blank"  href="/DownloadServ?idFile=<c:out value="${saf.idFile}"/>&Enc=<c:out value="${encView}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'resume_ตรวจดู_p<c:out value="${loop.index+1}"/>'});"><u><fmt:message key="GLOBAL_VIEW"/></u> </a>
											</div>
										</div>
									</c:forEach>
								</c:if>
								</div>
								<div>
								<c:if test="${not empty docs}">
									<div class="row" style="height:6px;"></div>
									<div class="row" style="height:6px;"></div>
									<div class="row" style="height:6px;"></div>
									<div class="row">
										<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"> </div>
										<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11 suggestion_title"  style="padding-left:0px;">
											<b><fmt:message key="HOME_OTHER_DOCUMENT"/></b>
										</div>
									</div>
									<div class="form-group">
										<c:forEach items="${docs}" var="doc" varStatus="loop">
											<div class="row">
												<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"> </div>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8" style="padding-left:0px;">
													<label>
														<input type="checkbox" name="docs" value="<c:out value="${doc.idFile}"/>"  onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'otf_choose_p<c:out value="${loop.index+1}"/>'});">&nbsp;&nbsp;<c:out value="${doc.fileTitle}"/>
													</label>
												</div>
												<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-left:0px;">
													<a target="_blank"  href="/DownloadServ?idFile=<c:out value="${doc.idFile}"/>&Enc=<c:out value="${encView}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel:'otf_ตรวจดู_p<c:out value="${loop.index+1}"/>'});"><u><fmt:message key="GLOBAL_VIEW"/></u> </a>
												</div>
											</div>
										</c:forEach>
									</div>
									
								</c:if>
								<div class="row">
									<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"> </div>
									<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11"  style="padding-left:0px;">
										<fmt:message key="APPLY_CANCLE_CHOOSE_RESUME"/>&nbsp;<a class="addAttachment" id="document" href='javascript:void(0)'><u><fmt:message key="CLICK_HERE"/></u></a>
									</div>
								</div>
							</div>
							<c:if test="${isNationWideJob eq true }">
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"> </div>
								<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11"  style="padding-left:0px;">
									<b class="star">*</b>&nbsp;&nbsp;<b><fmt:message key="APPLY_TARGET_JOB"/></b>
								</div>
							</div> 
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"></div>
								<div class="col-xs-7 col-sm-7 col-md-7 col-lg-7"  style="padding-left:0px;">
									<select name="idState" id="idState" class="form-control"  title="<fmt:message key="APPLY_TARGET_JOB"/>">
										<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
											<c:forEach var="state" items="${states}">
												<option value="<c:out value='${state.idState}'/>">
													<c:out value='${state.stateName}'/></option>
											</c:forEach>
									</select>
								</div>
							</div>
							</c:if>
							<div align="center"><br/><input type="image" src="/images/<c:out value="${sessionScope.SESSION_LOCALE}"/>/send.png" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel:'cta_ส่ง super resume'});"></div>
						</form>
					</c:when>
					<c:otherwise>
						<br><br><div class="section_header suggestion row"><fmt:message key="RESUME_NOT_FOUND"/><br><fmt:message  key="RESUME_NOT_FOUND2"/>&nbsp;<a href="SRIX?view=register&idResume=0&jSession=<c:out value="${idSession}"/>"><fmt:message key="PERSONAL_MESSAGE4"/></a></div>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
					<c:if test="${errorId==149 }">
						<label class="error"><fmt:message key="ERROR_ID_149"/></label>
					</c:if>
					<c:if test="${errorId==136}">
						<label class="error"><fmt:message key="ERROR_ID_136"/></label>
					</c:if>
					<c:if test="${errorId==137}">
						<label class="error"><fmt:message key="ERROR_ID_137"/></label>
					</c:if>
					<c:if test="${errorId==138}">
						<label class="error"><fmt:message key="ERROR_ID_138"/></label>
					</c:if>
					<c:if test="${errorId==139}">
						<label class="error"><fmt:message key="ERROR_ID_139"/></label>
					</c:if>
					<c:if test="${errorId==143}">
						<label class="error"><fmt:message key="ERROR_ID_143"/></label>
					</c:if>
					<c:if test="${errorId==145}">
						<label style="padding-top:20px; font-weight:normal !important;">
							<fmt:message key="ERROR_ID_145">
								<fmt:param><c:out value="${idSession}"/></fmt:param>
							</fmt:message>
						</label>
					</c:if>
					<c:if test="${errorId==150}">
						<label class="error">เกิดข้อผิดพลาดในการแปลงภาษาเรซูเม่ กรุณาลองใหม่อีกครั้ง</label>
					</c:if>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

