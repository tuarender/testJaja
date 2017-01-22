<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.CertFieldLanguage"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${resume.locale}"/>
<%
	int sequence=Util.getInt(request.getParameter("sequence"),1);
	int idLanguage=Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry=Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.StrToInt(request.getParameter("idResume"));
	String agent = Util.getStr(request.getParameter("agent"));
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	request.setAttribute("agent",agent);
	request.setAttribute("sequence",sequence);
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
   <h3><fmt:message key="SECTION_PHOTO"/></h3>
</div> 
<form id="uploadPhoto" name="uploadPhoto" action="/UploadPhotoServ" method="POST" class="form-horizontal" enctype="multipart/form-data">
	<input name="service" id="service" type="hidden" value="savePhoto">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" id="idFile" name="idFile" value="0">
	<input type="hidden" id="DocType" name="DocType" value="PHOTOGRAPH">
	
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
		</div>
	</div>
	<div class="errorContainer" style="display:none">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="form-group alignCenter" style="margin:10px">
		<fmt:message key="PHOTO_SKIP_DETAIL"/>&nbsp;
		<b><a id="uploadSkip" href='javascript:void(0)' onClick="ga('send', 'event', { eventCategory:'Engage-UploadPhoto', eventAction: 'ClickOn', eventLabel: 'ข้ามไปก่อน'});"><fmt:message key="PHOTO_SKIP"/></a></b>
	</div>
	<div class="row alignCenter">
		<div style="border: 0px;margin:10px;display:inline-block;text-align:right">
			<span id="spanDeletePhoto" style="display:none"><a id="urlDeletePhoto" href="javascript:void(0)"><fmt:message key="GLOBAL_DELETE"/></a></span>
			<div id="imageDiv" class="imgLiquidNoFill imgLiquid" style="width:162px; height:222px;display:block">
			    <img id="photo" class="img-responsive" src="/images/photoUpload.png" />
			</div>
			<div id="uploadPhotoDiv" class="uploadPhoto" style="text-align:center">
		    	<a id="urlUploadPhoto" href="javascript:void(0)" class="urlFillContent" style="color:#FFFFFF" onClick="ga('send', 'event', { eventCategory:'Engage-UploadPhoto', eventAction: 'ClickOn', eventLabel: 'เพ่ิม'});">
		    		<b>+&nbsp;<fmt:message key="PHOTO_ADD"/>&nbsp;</b>
		    	</a>
			</div>
			<div id="progress" style="display:none">
		        <div id="bar" style="float:left"></div>
		        <div id="percent">0%</div >
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3"></div>
		<div class="col-xs-9"><input name="inputUploadFile" id="inputUploadFile"  type='file' style="display:none;margin:6px auto;">
		</div>
	</div>
	<div class="row alignCenter">
		<fmt:message key="FILE_PHOTO_TYPE"/>
	</div>
	<div class="form-group alignCenter" style="margin:10px">
		<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  id="submitBotton" onClick="ga('send', 'event', { eventCategory:'Engage-UploadPhoto', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});">
	</div>
	<!-- modal confirm delete -->
	<div class="modal fade" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Confirm</h4>
				</div>
				<div class="modal-body">
					<fmt:message key="CONFIRM_DELETE_PHOTO"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
					<button type="button" class="btn btn-primary" onclick="deletePhoto()"><fmt:message key="GLOBAL_CONFIRM"/></button>
				</div>
			</div>
		</div>
	</div>
	<!-- /end modal confirm delete -->
	<!-- modal skip delete -->
	<div class="modal fade" id="modalSkip" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Confirm</h4>
				</div>
				<div class="modal-body">
					<fmt:message key="CONFIRM_SKIP_PHOTO"/>
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
<script src="/js/imgLiquid-min.js"></script>
<script src="/js/jquery.form.js"></script>
<script>
var ie = isIE();
var sequence=<c:out value='${sequence}'/>;
var errorContainer = null;
var idResume = '<c:out value="${idResume}"/>';
var agent = '<c:out value="${agent}"/>';
var sequence = '<c:out value="${sequence}"/>';
var globalSave = "<fmt:message key='GLOBAL_SAVE'/>";
var globalNext = "<fmt:message key='GLOBAL_NEXT'/>";
var fileSizeError = "<fmt:message key='PHOTO_SIZE_ERROR'/>";
var linkUpload="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/upload.png";
var linkNext="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png";
$(document).ready(function(){
	errorContainer = $('div.errorContainer');
	
	$('#uploadSkip').click(function(){
		$('#modalSkip').modal('show');
	});
	
	$('#urlUploadPhoto').click(function() {
		$('input[id="submitBotton"]').attr('src', linkUpload);
    	$('#inputUploadFile').show();
    	$('#uploadPhotoDiv').css('background-color', '#CDCDCD');
	});
	
	$('#urlDeletePhoto').click(function() {
		$('#modalDelete').modal('show');
	});
	
	$('#submitBotton').click(function() {
		errorContainer.hide();
		if($('#idFile').val()==0){
			if($('#inputUploadFile').val()!=""){
				$('input[id="submitBotton"]').attr('src', linkNext);
				$('#uploadPhoto').ajaxForm(options);
			}
			else{
				/*$('div.container li').remove();
				errorContainer.find("ol").append('<li><label class="error"><fmt:message key="PHOTO_REQUIRE"/></label></li>');
				errorContainer.css({'display':'block'});
		        $('html, body').animate({scrollTop: '0px'}, 300); */
		        $('#modalSkip').modal('show');
			}
		}
		else{
			if($('#inputUploadFile').val()!=""){
				//$('#uploadPhoto').submit();
				$('input[id="submitBotton"]').attr('src', linkNext);
				$('#uploadPhoto').ajaxForm(options);
			}
			else{
				if(sequence==0){
					window.location.href = "/SRIX?view=home";
				}
				else{
					window.location.href = "?view=uploadResume&idResume="+idResume+"&sequence="+sequence;
				}
			}
		}
	});
	
	//--------------------ajax upload-----------------
	var options = {
		timeout:0,
		beforeSend: function() {
	        $("#progress").fadeIn(100);
	        $("#bar").width('0%');
	        $("#message").html("");
	        $("#percent").html("0%");
	    },
	    uploadProgress: function(event, position, total, percentComplete) {
	        $("#bar").width(percentComplete+'%');
	        $("#percent").html(percentComplete+'%');
	    },
	    success: function() {
	        $("#bar").width('100%');
	        $("#percent").html('100%');
	    },
	    complete: function(response) {
	    	errorContainer.hide();
	    	var strJson = ""+response.responseText;
	    	strJson = strJson.replace("<PRE>","");
	    	strJson = strJson.replace("</PRE>","");
	    	var obj = jQuery.parseJSON(strJson);
	    	if(obj.success==1){
	    		if(obj.urlPhoto!=""){
		    		$("#photo").attr("src",obj.urlPhoto);
		    		setImageSize();
		    		$('#imageDiv').css('background-color', '#EDEDED');
		    		$('#imageDiv').css('border', '2px solid #CCCCCC');
		    		$('#urlUploadPhoto').html("<b>+&nbsp;<fmt:message key='PHOTO_EDIT'/>&nbsp;</b>");
		    		$('#idFile').val(obj.idFile);
		    		$('#nameFileDiv').html("");
					$('#submitBotton').val(globalNext);
					$('#inputUploadFile').val("");
					$('#inputUploadFile').hide();
					$('#inputUploadFile').replaceWith($('#inputUploadFile').clone( true ) );
					$('#uploadPhotoDiv').css('background-color', '#CD85CD');
					$('#spanDeletePhoto').show();
	    		}
	    	}
	    	else{
	    		$('div.errorContainer li').remove();
    			if(obj.urlError!=""){
     				window.location.href = obj.urlError;
     			}
     			else{
     				for(var i=0; i<obj.errors.length; i++){
      					errorContainer.find("ol").append('<li><label class="error">'+obj.errors[i]+'</label></li>');
	      			}
	      			errorContainer.css({'display':'block'});
      				$('html, body').animate({scrollTop: '0px'}, 300); 
     			}
	    	}
			$("#progress").fadeOut(500);
	    },
	    error: function(){
	        $("#progress").fadeOut(500);
	    }
	}; 
	$('#uploadPhoto').ajaxForm(options);
	
	//--------------------------get photo----------------------
	getPhoto();
	setImageSize();
	
	$('#inputUploadFile').change(function() 
	{
		var chkSize=1024*1024;
		if(this.files[0].size>chkSize)
		{
			alert(fileSizeError);
			$('#uploadPhoto')[0].reset();
		}
	});
});

function redirectToFinish(){
	if(sequence==0)
	{
		window.location.href = "/SRIX?view=home";
	}
	else
	{
		window.location.href = "?view=uploadResume&idResume="+idResume+"&sequence="+sequence;
	}
}

function deletePhoto(){
	var service = 'deletePhoto';
	var request = '{"service":"'+service+'"}';
	$.ajax({
		type : "POST",
  		url : '/UploadPhotoServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				$('#imageDiv').css('background-color', '#FFFFFF');
		    	$('#imageDiv').css('border', '0px solid #CCCCCC');
				$("#photo").attr("src","/images/photoUpload.png");
				setImageSize();
				$('#idFilePhoto').val(0);
				$('#urlUploadPhoto').html("<b>+&nbsp;<fmt:message key='PHOTO_ADD'/>&nbsp;</b>");
				$('.uploadPhotoDelete').hide();
				$(".uploadPhoto").css({ top: '85px' });
				$('#spanDeletePhoto').hide();
				$('#inputUploadFile').replaceWith($('#inputUploadFile').clone( true ) );
				$('#idFile').val(0);
				$('#modalDelete').modal('hide');
			}
   			else{
   				$('div.errorContainer li').remove();
  				for(var i=0; i<obj.errors.length; i++){
   					errorContainer.find("ol").append('<li><label class="error">'+obj.errors[i]+'</label></li>');
   				}
   				errorContainer.css({'display':'block'});
   				$('html, body').animate({scrollTop: '0px'}, 300); 
   			}
		}
	});
} 

function getPhoto(){
	//container.hide();
	var service = 'getPhoto';
	var request = '{"service":"'+service+'"}';
	$.ajax({
		type : "POST",
  		url : '/UploadPhotoServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				var urlPhoto = obj.urlPhoto;
				if(obj.urlPhoto!=null){
					$("#photo").attr("src",obj.urlPhoto);
		    		setImageSize();
		    		$('#imageDiv').css('background-color', '#EDEDED');
		    		$('#imageDiv').css('border', '2px solid #CCCCCC');
		    		$('#idFile').val(obj.idFile);
		    		$('#urlUploadPhoto').html("<b>+&nbsp;<fmt:message key='PHOTO_EDIT'/>&nbsp;</b>");
		    		$('#spanDeletePhoto').show();
				}
				else{
					$('#spanDeletePhoto').hide();
				}
			}
   			else{
   				$('div.errorContainer li').remove();
  				for(var i=0; i<obj.errors.length; i++){
   					errorContainer.find("ol").append('<li><label class="error">'+obj.errors[i]+'</label></li>');
   				}
   				errorContainer.css({'display':'block'});
   				$('html, body').animate({scrollTop: '0px'}, 300); 
   			}
		}
	});
}

function setImageSize(){
	$(".imgLiquidNoFill").imgLiquid({
	    fill: false,
	    horizontalAlign: "center",
	    verticalAlign: "50%"
	});
}
</script>