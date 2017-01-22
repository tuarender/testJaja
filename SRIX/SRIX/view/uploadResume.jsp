<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${resume.locale}"/>
<%
	int sequence=Util.getInt(request.getParameter("sequence"),1);
	int idLanguage=Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.getInt(request.getParameter("idResume"));
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	Resume resume=null;
	if(idJsk>0 && idResume>=0)
	{
		Jobseeker jobseeker=new JobseekerManager().get(idJsk);
		if(jobseeker!=null)
		{
			request.setAttribute("jobseeker",jobseeker);
			resume=new ResumeManager().get(idJsk,idResume);
			if(resume!=null)
			{
				if(resume.getLocale()!=null)
				{
					locale=resume.getLocale();
				}
				request.setAttribute("resume",resume);
				request.setAttribute("idResume",resume.getIdResume());
				request.setAttribute("idLanguage",resume.getIdLanguage());
			}
		}	
	}
	request.setAttribute("locale",locale);	
	request.setAttribute("sequence",sequence);
	String encView=Encryption.getEncoding(0,0,idJsk,0);
	String keyView=Encryption.getKey(0,0,idJsk,0);
	request.setAttribute("encView",encView);
	request.setAttribute("keyView",keyView);
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<div class="seperator"></div>
<div class="visible-xs seperator"></div>
	<div align="right" >
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div>
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent: 0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_EDUCATION"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
	<div class="section_header alignCenter">
	<br>
		<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
	</div>
<div class="alignCenter">
   <h3><fmt:message key="FILE_RESUME_HEADER"/></h3>
</div>

<form id="uploadFile" name="uploadFile" action="/UploadPhotoServ" method="POST" class="form-horizontal" enctype="multipart/form-data">
	<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>" >
	<input type="hidden" name="idFile" id="idFile" value="0" >
	<input type="hidden" name="service" id="service" value="addFile">
	<input type="hidden" name="DocType" id="DocType" value="RESUME">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
		</div>
	</div>
	<div class="row suggestion">
		<div class="col-xs-2">&nbsp;</div>
		<div class="col-xs-8">
			<fmt:message key="DIRECTION_UPLOAD_RESUME"/>
		</div>
		<div class="col-xs-2">&nbsp;</div>
	</div>
	<div class="errorContainer" style="display:none">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="form-group alignCenter">
		<fmt:message key="FILE_SKIP_DETAIL"/>&nbsp;
		<b><a id="uploadSkip" href='javascript:void(0)' onClick="ga('send', 'event', { eventCategory:'Engage-UploadResume', eventAction: 'ClickOn', eventLabel: 'ข้ามไปก่อน'});"><fmt:message key="PHOTO_SKIP"/></a></b>
	</div>
	<div id="resumeListDiv" style="display:none"></div>
	<div>
	</div>
	<div class="form-group">
		<label for="fileTitle"><b class="star">*</b><fmt:message key='RESUME_UPLOAD'/></label>
		<input class="form-control" type="text" id="fileTitle" name="fileTitle" maxlength="100" onClick="ga('send', 'event', { eventCategory:'Engage-UploadResume', eventAction: 'TypeOn', eventLabel: 'ตั้งชื่อเรซูเม่'});">
	</div>
	<div class="form-group">
		<input name="inputUploadFile" id="inputUploadFile" type='file' onClick="ga('send', 'event', { eventCategory:'Engage-UploadResume', eventAction: 'ClickOn', eventLabel: 'choosefile'});">
		<br><fmt:message key="FILE_RESUME_TYPE"/>
	</div>
	<div class="form-group alignCenter">
		<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  id="submitBotton"  onClick="ga('send', 'event', { eventCategory:'Engage-UploadResume', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});">
	</div>
	<!-- modal confirm delete -->
	<div class="modal fade bs-example-modal-sm" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Confirm</h4>
				</div>
				<div class="modal-body">
					<fmt:message key="CONFIRM_DELETE_RESUME"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
					<button type="button" class="btn btn-primary" onclick="deleteFile()"><fmt:message key="GLOBAL_CONFIRM"/></button>
				</div>
			</div>
		</div>
	</div>
	<!-- /end modal confirm delete -->
	<!-- modal skip delete -->
	<div class="modal fade bs-example-modal-sm" id="modalSkip" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Confirm</h4>
				</div>
				<div class="modal-body">
					<fmt:message key="CONFIRM_SKIP_RESUME"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
					<button type="button" class="btn btn-primary" onclick="redirectToFinish()"><fmt:message key="GLOBAL_CONFIRM"/></button>
				</div>
			</div>
		</div>
	</div>
	<!-- /end modal skip delete -->
</form>

<script src="/js/jquery.form.js"></script>
<script>
var date = new Date();
var sequence=<c:out value='${sequence}'/>;
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var globalFileRequire = '<fmt:message key="FILE_REQUIRE"/>';
var globalTitleRequire = '<fmt:message key="FILE_TITLE_REQUIRE"/>';
var countFileResume = 0;
var url="";
var urlFinish="";
var idFileTemp = "";
$(document).ready(function(){
	container = $('div.errorContainer');
	urlFinish = "?view=uploadFinish&idResume="+idResume;
	url="?view=registerFinish&idResume="+idResume;
	
	$('#uploadSkip').click(function(){
		$('#modalSkip').modal('show');
	});
	
	$('#submitBotton').click(function() {
		container.hide();
		var submitUpload = true;
		$('div.errorContainer li').remove();
		if($("#inputUploadFile").val()==""&&$('#service').val()=="addFile"){
			submitUpload = false;
   			container.find("ol").append('<li><label class="has-error">'+globalFileRequire+'</label></li>');
		}
		if($("#fileTitle").val()==""){
			submitUpload = false;
   			container.find("ol").append('<li><label class="has-error">'+globalTitleRequire+'</label></li>');
		}
		if(submitUpload){
			$('#uploadFile').submit();
		}
		else if(countFileResume>0&&$("#inputUploadFile").val()==""&&$('#service').val()=="addFile"&&$("#fileTitle").val()==""){
			$('#modalSkip').modal('show');
		}else if(countFileResume==0&&$("#inputUploadFile").val()==""&&$('#service').val()=="addFile"){
			$('#modalSkip').modal('show');
		}else{
			container.css({'display':'block'});
			$('html, body').animate({scrollTop: '0px'}, 300); 
		}
	});
    
	//--------------------ajax upload-----------------
	var options = { 
		timeout:0,
	    complete: function(response) {
	    	container.hide();
	    	var strJson = ""+response.responseText;
	    	strJson = strJson.replace("<PRE>","");
	    	strJson = strJson.replace("</PRE>","");
	    	var obj = jQuery.parseJSON(strJson);
	    	if(obj.success==1){
	    		if(obj.idFile>0){
			 		if($('#service').val()=="addFile"){
			 			window.location.href = urlFinish;
			 		}
			 		else{
			 			getAttachmentList($('#DocType').val());
			 			$('#addFile').val("addFile");
	   			 		$('#fileTitle').val("");
				 		$('#idFile').val(0);
				 		$('#inputUploadFile').show();
				 		$('#inputUploadFile').prop('disabled', false);
			 		}
	    		}
	    		$('#submitBotton').val('<fmt:message key="GLOBAL_NEXT"/>');
	    	}
	    	else{
	    		$('div.errorContainer li').remove();
    			if(obj.urlError!=""){
     				window.location.href = obj.urlError;
     			}
     			else{
     				for(var i=0; i<obj.errors.length; i++){
      					container.find("ol").append('<li><label class="has-error">'+obj.errors[i]+'</label></li>');
	      			}
	      			container.css({'display':'block'});
      				$('html, body').animate({scrollTop: '0px'}, 300); 
     			}
	    	}
	    }
	}; 
	
	$('#uploadFile').ajaxForm(options);
	
	getAttachmentList("RESUME");
});

function redirectToFinish(){
	window.location.href = url;
}

function getAttachmentList(docType){
	container.hide();
	var service = 'getAllResume';
	var request = '{"service":"'+service+'"}';
	$.ajax({
		type : "POST",
  		url : '/UploadPhotoServ?timestamp='+date.getTime(),
		data: jQuery.parseJSON(request),
		cache: false,
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				countFileResume = obj.attachmentList.length;
				if(countFileResume>0){
					var html = '<table style="border-collapse:collapse; width:100%;">';
					for(var i=0;i<obj.attachmentList.length;i++){
						html += '<tr>'+
									'<td class="digit">'+(i+1)+'</td>'+
									'<td valign="top">'+
										'&nbsp;&nbsp;<a class="ownResumeLink" id="'+obj.attachmentList[i].idFile+'" title="'+obj.attachmentList[i].fileTitle+'">'+obj.attachmentList[i].fileTitle+'</a>'+
										'<img id="arrow_file_'+obj.attachmentList[i].idFile+'" class="resumeArrow" src="/images/arrow_down_green.png">'+
									'</td>'+
								'</tr>'+
								'<tr>'+
									'<td></td>'+
									'<td>'+
										'<div class="ownResumeLayer" id="ownResume_'+obj.attachmentList[i].idFile+'" style="display:none;">'+
											'<a href="/DownloadServ?idFile='+obj.attachmentList[i].idFile+'&Enc=<c:out value="${encView}"/>&Key=<c:out value="${keyView}"/>"><fmt:message key="GLOBAL_VIEW"/></a> | '+
											'<a href="javascript:void(0)" class="editDocument" id="'+docType+'_'+obj.attachmentList[i].idFile+'"><fmt:message key="GLOBAL_EDIT"/></a> | '+
											'<a href="javascript:void(0)" class="deleteFile" id="'+docType+'_'+obj.attachmentList[i].idFile+'"><fmt:message key="GLOBAL_DELETE"/></a>'+
											'<br><br>'+
										'</div>'+
									'</td>'+
								'</tr>'+
								'<tr style="height:10px;">'+
									'<td colspan="2"></td>'+
								'</tr>';
					}
					html += '</html>';
					$('#resumeListDiv').html(html);
					$('#resumeListDiv').show();
					setEventDocumentClick();
				}
			}
   			else{
   				countFileResume = 0;
   				$('div.errorContainer li').remove();
  				for(var i=0; i<obj.errors.length; i++){
   					container.find("ol").append('<li><label class="has-error">'+obj.errors[i]+'</label></li>');
   				}
   				container.css({'display':'block'});
   				$('html, body').animate({scrollTop: '0px'}, 300); 
   			}
		}
	});
}

function setEventDocumentClick(){
 	$('a[class=ownResumeLink]').click(function(){
 		$('div[class="ownResumeLayer"][id!="'+this.id+'"]').slideUp('fast');
 		$('img[class="resumeArrow"][id!="arrow_'+this.id+'"]').prop('src','/images/arrow_down_green.png');
 		if(!$('#ownResume_'+this.id).is(":visible")){
 			$('#ownResume_'+this.id).slideDown('fast');
 			$('img[class="resumeArrow"][id="arrow_'+this.id+'"]').prop('src','/images/arrow_up_green.png');
 		}
 	});
 
  	$('a[class=editDocument]').click(function() {
  		var param = this.id.split("_");
  		$('#fileTitle').val($("#"+param[1]).attr("title"));
  		$('#idFile').val(param[1]);
  		$('#DocType').val(param[0]);
  		$('#service').val("updateTitle");
  		$('#inputUploadFile').hide();
  		$('#inputUploadFile').prop('disabled', true);
  		$('#submitBotton').val('<fmt:message key="GLOBAL_SAVE"/>');
 	});
 	
  	$('a[class=deleteFile]').click(function() {
  		$('#addFile').val("addFile");
		$('#fileTitle').val("");
 		$('#idFile').val(0);
 		$('#inputUploadFile').show();
 		$('#inputUploadFile').prop('disabled', false);
 		$('#submitBotton').val('<fmt:message key="GLOBAL_NEXT"/>');
  		idFileTemp = this.id;
  		$("#modalDelete").modal('show');
 	});
}

function deleteFile(){
	if(idFileTemp!=""){
		container.hide();
		var param = idFileTemp.split("_");
		var service = 'deleteFile';
		var request = '{"service":"'+service+'","idFile":"'+param[1]+'"}';
		$.ajax({
			type : "POST",
	  		url : '/UploadPhotoServ?timestamp='+date.getTime(),
			data: jQuery.parseJSON(request),
			cache: false,
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					getAttachmentList(param[0]);
				}
	   			else{
	   				$('div.errorContainer li').remove();
	  				for(var i=0; i<obj.errors.length; i++){
	   					container.find("ol").append('<li><label class="has-error">'+obj.errors[i]+'</label></li>');
	   				}
	   				container.css({'display':'block'});
	   				$('html, body').animate({scrollTop: '0px'}, 300); 
	   			}
	   			$("#modalDelete").modal('hide');
			}
		});
	}
}
</script>