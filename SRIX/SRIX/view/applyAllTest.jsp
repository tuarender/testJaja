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
<%@page import="com.topgun.util.Encoder"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="com.topgun.shris.masterdata.State"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<%
	int idJsk= Util.getInt(request.getParameter("idJsk"));
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
	String[] positionList= {"5,83","5,85","5,176","5,64","5,65","5,177","5,103","5,136","5,137","5,138","5,139","5,140","5,128","5,141","5,142","5,134","5,135","5,107","5,80","5,82","5,133","5,66","5,67","5,68","5,78","5,129","5,69","5,73","5,127","5,179","5,131","5,178","5,106","5,71","5,72","5,74","5,76","5,77","5,60","5,62","5,63","5,171","5,172","5,173","5,174","5,175","5,155","5,156","5,157","5,158","5,159","5,160","5,161","5,162","5,163","5,164","5,166","5,167","5,168","5,121","5,86","5,88","5,169","5,170","5,122","5,118","5,184","5,119","5,100","5,101","5,102","5,181","5,182","5,120","5,89","5,90","5,113","5,111","5,112","5,189","5,190","5,123","5,110","5,183","5,108","5,98","5,186","5,187","5,124","5,125","5,143","5,144","5,145","5,146","5,147","5,148","5,149","5,150","5,151","5,152","5,185","5,153","5,154","5,126","5,93","5,95","5,96","5,97","5,194","5,197","5,195","5,188","5,191","5,198","5,196","5,199","5,193","5,192","5,104","5,116","5,130","5,132","5,84","5,202","5,165","5,200","5,201","5,206","5,205","5,207","5,211","5,214","5,210","5,209","5,215","5,208","5,212","5,220","5,219","5,222","5,216","5,217","5,221","5,227","5,226","5,223","5,225","5,224","5,228","5,218","5,229","5,235","5,236","5,237","5,238","5,239","5,231","5,242","5,117","5,246","5,258","5,259","5,270","5,244","5,254","5,248","5,252","5,204","5,271","5,274","5,288","5,291","5,302","5,306","5,282","5,286","5,290","5,321","5,303","5,296","5,305","5,309","5,285","5,281","5,293","5,294","5,308","5,278","5,301","5,280","5,284","5,283","5,298","5,300","5,276","5,297","5,279","5,320","5,287","5,310","5,312","5,319","5,292","5,317","5,318","5,322","5,326","5,328","5,307","5,289","5,304","5,327","5,299","5,311","5,295","5,315","5,247","5,251","5,250","5,261","5,262","5,263","5,264","5,266","5,243","5,256","5,253","5,245","5,260","5,265","5,257","5,249","5,275","5,334","5,331","5,345","5,337","5,340","5,343","5,341","5,342","5,332","5,333","5,335","5,336","5,339","5,346","5,330","5,347","5,344"};
	/* String posJTG=Util.getStr(request.getParameter("positionList"));
	if(posJTG!=null)
	{
		positionList=posJTG.split("_");
	} */
	List<String[]> positions = new ArrayList<String[]>();
	List<String[]> invalidPos = new ArrayList<String[]>();
	EmployerManager em=new EmployerManager();
	PositionManager pm=new PositionManager();
	if(positionList!=null)
	{
		for(int i=0; i<positionList.length;i++)
		{
			String[] position=positionList[i].split(",");
			String[] rsPos=new String[4];
			Employer emp=em.get(Util.getInt(position[0]));
			if(emp!=null)
			{
				rsPos[0]= Util.getStr(Util.getInt(position[0]));
				rsPos[1]= Util.getStr(emp.getNameEng());
				rsPos[2]= Util.getStr(Util.getInt(position[1]));
				if(idLanguage==38)
				{
					rsPos[1]= Util.getStr(emp.getNameThai());
				}
				Position pos=pm.getPosition(emp.getId(), Util.getInt(position[1]));
				if(pos!=null)
				{
					rsPos[3]= Util.getStr(pos.getPositionName());
				}
				positions.add(rsPos);
			}
		}
		request.setAttribute("totalPos",positionList.length);
		request.setAttribute("positions",positions);
	}
	
	Resume saf=new ResumeManager().get(idJsk, 0);
	if(saf!=null)
	{
		if(Util.getStr(new ResumeStatusManager().getRegisterStatus(saf)).equals("TRUE"))
		{
			List<Resume> resumes=new ResumeManager().getAll(idJsk,false,true);
			
			List<Attachment> ownResumes = new AttachmentManager().getAllByFileType(idJsk,"RESUME");
			List<Attachment> docs = new AttachmentManager().getAllByFileType(idJsk,"DOCUMENT");
			request.setAttribute("resumes",resumes);
			request.setAttribute("docs",docs);
			request.setAttribute("ownResumes",ownResumes);
			request.setAttribute("saf",saf);
		}
		
	}
	request.setAttribute("errorId",errorId);
	String idSession=Encoder.getEncode(session.getId());
	request.setAttribute("idSession", idSession); 
	request.setAttribute("idJsk",idJsk);
	
	boolean isNationWideJob=false;
	int idEmp=-1;
	int idPosition=-1;
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
		
		$('#totalResumeDiv').text(<c:out value="${totalPos}"/>+0);
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
			submitHandler:function(form)
			{
				$('#applyLayer').hide();
				$.blockUI(
				{ 
					message:'     ',
					css: 
					{	
						top:'40%', 
				        left:'45%', 
						color:'#ffffff', 
						width:'70px',
						height:'70px',
						border: 'none', 
						padding: '0px', 
						'-webkit-border-radius': '8px', 
						'-moz-border-radius': '8px', 
						opacity: 1,
						background:'#ffffff url("/images/spinner.gif") no-repeat center center'
					}
				});
				var error=0;
				if( $('input[name="position"]:checked').length<=0)
				{
					error++;
					alert('<fmt:message key="JM_OVER_SELECTED"/>');
					return false;
				}
				else
				{
					$('input[name="position"]:checked').each(function(){
						var state=$(this).attr('rel');
						if($('#'+state).val()=="")
						{
							error++;
							alert('<fmt:message key="APPLY_TARGET_JOB"/>');
							$('#'+state).focus();
							return false;
						}
					});
				}
				if(error==0)
				{
					form.submit();
				}
				else
				{
					$('#applyLayer').show();
					$.unblockUI();
					return false;
				}
				
			}
		});
		 $( "input[name='position']" ).click(function() {
		 	$('#totalResumeDiv').text($( "input[name='position']:checked" ).length+0);
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
		<c:when test="${errorId==0 }">
			<c:choose>
				<c:when test="${not empty saf}">
					<form id="applyFrm" method="post" action="/ApplyAllServTest">
						<input type="hidden" name="idJsk" id="idJsk" value="<c:out value="${idJsk}"/>">
						<div class="section_header alignCenter">
							<h3><fmt:message key="SECTION_SENDING_PAGE"/></h3>
							<p class="suggestion"><fmt:message key="DIRECTION_APPLYALL"></fmt:message></font>
						</div>
						<div class="errorContainer">
							<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
							<ol></ol>
						</div>
						<div class="row row-centered" >
							<div class="col-xs-0 col-sm-0 col-md-2 col-lg-2"></div>
		 					<div class="col-xs-12 col-sm-12 col-md-10 col-lg-10">
								<div class="form-group">
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
								<c:choose>
									<c:when test="${not empty resumes}">
										<c:forEach items="${resumes}" var="resume" varStatus="loop">
											<div class="row">
												<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"></div>
												<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding-left:0px;">
													 <label>
														<input type="radio" name="idResume" value="<c:out value="${resume.idResume}"/>" class="required" title="<fmt:message key="APPLY_REQUIRED_SUPERRESUME"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_choose_p<c:out value="${loop.index+1}"/>'});">
														&nbsp;&nbsp;<c:out value="${resume.resumeName}"/>
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
														</c:choose>&nbsp;
														<a href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_ปรับปรุง/แก้ไข_<c:out value="${loop.index+1}"/>'});"><u><fmt:message key="APPLY_REVISE"/></u></a>
													)
												</div>
											</div>
										</c:forEach>
									</c:when>
									<c:otherwise>
								<div class="row">
										<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1" style="padding-left:0px;"></div>
										<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding-left:0px;">
											<label>
												<input type="radio" name="idResume" value="<c:out value="${saf.idResume}"/>" checked onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_choose_p'});">&nbsp;&nbsp;
												My Super Resume(<fmt:message key="SHORT_RESUME"/>)
											</label>
											</div>
											<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-left:0px;">
												<u><a target="_blank"  href="/view/viewResume.jsp?idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_ตรวจดู'});"><fmt:message key="GLOBAL_VIEW"/></a></u> 
										</div>
									</div>
									<div class="row">
											<div class="col-xs-0 col-sm-0 col-md-1 col-lg-1" style="padding-left:0px;"></div>
											<div class="col-xs-12 col-sm-12 col-md-11 col-lg-11" style="padding-left:0px;font-size:12px;font-weight:bold;">
												(	
													<fmt:formatDate var="timestamps" value="${saf.timeStamp}" pattern="d/M/yyyy"/>
													<fmt:message key="APPLY_INFO_DATE"><fmt:param value="${timestamps}"/></fmt:message>
													<c:choose>
														<c:when test="${saf.yearPass>0 || saf.monthPass>0 || saf.dayPass>0}">
															<fmt:message key="APPLY_AGO"/>
															<c:if test="${saf.yearPass>0}">&nbsp;<c:out value="${saf.yearPass}"/>&nbsp;<fmt:message key="APPLY_YEAR"/></c:if>
															<c:if test="${saf.monthPass>0}">&nbsp;<c:out value="${saf.monthPass}"/>&nbsp;<fmt:message key="APPLY_MONTH"/></c:if>
															<c:if test="${saf.dayPass>0}">&nbsp;<c:out value="${saf.dayPass}"/>&nbsp;<fmt:message key="APPLY_DAY"/></c:if>
															<fmt:message key="APPLY_AGO2"/>
														</c:when>
													</c:choose><br>&nbsp;&nbsp;&nbsp;&nbsp;
													<a href="/SRIX?view=resumeInfo&idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel: 'srm_ปรับปรุง/แก้ไข'});"><u><fmt:message key="APPLY_REVISE"/></u></a>
												)
											</div>
										</div>
								</c:otherwise>
								</c:choose>	
								</div>
								<div class="form-group">
									<c:if test="${not empty ownResumes}">
										<div class="row">
											<div class="col-xs-1 section_header_blueBar">2.</div>
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
												<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding-left:0px;">
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
								<div class="form-group">
									<c:if test="${not empty docs}">
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
													<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding-left:0px;">
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
							</div>
							<div class="form-group">
								<div align="center" id="applyLayer">
									<input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/send.gif" onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click', eventLabel:'CTA_ส่งเรซูเม่'});"/>
								</div>
							</div>
						</div>
						<c:if test="${not empty positions}">
							<div class="row row-centered" >
			 					<div class="col-xs-0 col-sm-0 col-md-2 col-lg-2"></div>
			 					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 suggestion">
										<b>
											<fmt:message key="APPLY_ALL_DETAIL3">
												<fmt:param><label id='totalResumeDiv'>0</label></fmt:param>
											</fmt:message>
										</b>
								</div>
							</div> 
							<div class="row">
								<div class="col-xs-2 col-sm-3 col-md-2 col-lg-2 " style="text-align:center;">
									<b><fmt:message key="APPLY_ALL_NO"/></b>
								</div>
								<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10" style="text-align:center;">
									<b><fmt:message key="APPLY_ALL_NAME_OF_COMPANY"/> / <fmt:message key="APPLY_ALL_POSITION"/></b>
								</div>
							</div>
							<c:set var="i" scope="session" value="1"/>
							<c:forEach items="${positions}" var="position">
							<c:set var="idEmp" scope="request" value='${position[0]}'/>
							<c:set var="idPosition" scope="request" value='${position[2]}'/>
								<div class="row answer" style="border-bottom-style:dotted;border-bottom-width:0.2px;">
									<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 " style="text-align:center;">
										<c:out value="${i}"/>.&nbsp;
										<input type='checkbox' value='<c:out value="${position[0]}"/>,<c:out value="${position[2]}"/>' name='position'  rel="<c:out value="${position[0]}"/>_<c:out value="${position[2]}"/>"  onClick="ga('send', 'event', { eventCategory: 'send resume – choose resume', eventAction: 'Click',eventLabel:'Check_เลือกบริษัท'});" checked/>
									</div>
									<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
										<font class="caption"><fmt:message key="APPLY_COMPANY_NAME"/></font>&nbsp;&nbsp;:&nbsp;<c:out value="${position[1]}" escapeXml="false"/>  <br/>
										<font class="caption"><fmt:message key="APPLY_POSITION"/></font>&nbsp;&nbsp;:&nbsp;<c:out value="${position[3]}" escapeXml="false"/>
									<%
										idEmp=Util.getInt(request.getAttribute("idEmp"));
										idPosition=Util.getInt(request.getAttribute("idPosition"));
										isNationWideJob=new PositionManager().isNationWideJob(idEmp, idPosition);
										if(isNationWideJob)
										{
											List<State> states=MasterDataManager.getAllState(idCountry, idLanguage);
											request.setAttribute("states",states);
										}
										request.setAttribute("isNationWideJob",isNationWideJob);
										idPosition++;
									
									 %>
									 <br>
									<c:if test="${isNationWideJob eq true }">
									<b class="star">*</b><font class="caption"><fmt:message key="APPLY_TARGETJOB_LOCATION"/></font>&nbsp;&nbsp;:&nbsp;
										<select name="<c:out value="${position[0]}"/>_<c:out value="${position[2]}"/>"  id="<c:out value="${position[0]}"/>_<c:out value="${position[2]}"/>" title="<fmt:message key="APPLY_TARGET_JOB"/>">
											<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
												<c:forEach var="state" items="${states}">
													<option value="<c:out value='${state.idState}'/>"><c:out value='${state.stateName}'/></option>
												</c:forEach>
										</select>
									</c:if>
									</div>
								</div>
								<c:set var="i" scope="session" value="${i+1 }"/>
							</c:forEach>
						</c:if>
					</form>
				</c:when>
				<c:otherwise>
					<br/><br/>
					<div><fmt:message key="RESUME_NOT_FOUND"/>&nbsp;<a href="/SRIX?view=register&jSession=<c:out value="${idSession}"/>"><fmt:message key="CLICK_HERE"/></a></div>
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
					<label class="error"><fmt:message key="ERROR_ID_145"/></label>
				</c:if>
		</c:otherwise>
	</c:choose>
 