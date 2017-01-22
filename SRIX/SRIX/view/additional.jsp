<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="java.util.ArrayList"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${resume.locale}"/>
<%
	int idLanguage=Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry=Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.StrToInt(request.getParameter("idResume"));
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<script>
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";

$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	getEditData();
	$('#buttonBack').click(function(){
		window.location.href = "?view=resumeInfo&idResume="+idResume;
	});
	//---------------------------- set event ----------------------------
	$("input[name='canDrive']").change(function() {
		if($(this).val()=='TRUE'){
			$('#canDriveIdDiv').show();
			$('#canDriveId').prop('disabled',false);
		}
		else{
			$('#canDriveIdDiv').hide();
			$('#canDriveId').prop('disabled',true);
		}
	});
	$("input[name='handicap']").change(function() {
		if($(this).val()=='TRUE'){
			$('#handicapReasonDiv').show();
			$('#handicapReason').prop('disabled',false);
		}
		else{
			$('#handicapReasonDiv').hide();
			$('#handicapReason').prop('disabled',true);
		}
	});
	$("input[name='illness']").change(function() {
		if($(this).val()=='TRUE'){
			$('#illnessReasonDiv').show();
			$('#illnessReason').prop('disabled',false);
		}
		else{
			$('#illnessReasonDiv').hide();
			$('#illnessReason').prop('disabled',true);
		}
	});
		$("input[name='fire']").change(function() {
		if($(this).val()=='TRUE'){
			$('#fireReasonDiv').show();
			$('#fireReason').prop('disabled',false);
		}
		else{
			$('#fireReasonDiv').hide();
			$('#fireReason').prop('disabled',true);
		}
	});
	$("input[name='criminal']").change(function() {
		if($(this).val()=='TRUE'){
			$('#criminalReasonDiv').show();
			$('#criminalReason').prop('disabled',false);
		}
		else{
			$('#criminalReasonDiv').hide();
			$('#criminalReason').prop('disabled',true);
		}
	});
	//---------------------------- validation ---------------------------
	$('#additionalForm').validate({
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		invalidHandler: function(form, validator) {
			$('html, body').animate({scrollTop:"0px"}, 300);      
		},
		highlight: function(element) 
 		{
           	$(element).closest('.form-group').addClass('has-error');
       	},
       	unhighlight: function(element) 
       	{
           	$(element).closest('.form-group').removeClass('has-error');
       	},
		rules:{
 			idCard:{
 				required : true,
 				maxlength : 13,
 				minlength : 13
 			},
 			canDrive:{
 				required:true
 			},
 			canDriveId:{
 				required:true
 			},
 			handicap:{
 				required:true
 			},
 			handicapReason:{
 				required:true
 			},
			illness:{
				required:true
			},
			illnessReason:{
				required:true
			},
			fire:{
				required:true
			},
			fireReason:{
				required:true
			},
			criminal:{
				required:true
			},
			criminalReason:{
				required:true
			},
			military:{
				required:true
			}
		},			  
		messages:{
			idCard:{
				required:'<fmt:message key="ADDITIONAL_ID_CARD_REQUIRED"/>'
			},
			canDrive:{
				required:'<fmt:message key="ADDITIONAL_DRIVE_REQUIRED"/>'
			},
			canDriveId:{
				required:'<fmt:message key="ADDITIONAL_DRIVE_ID_REQUIRED"/>'
			},
			handicap:{
				required:'<fmt:message key="ADDITIONAL_HANDICAP_REQUIRED"/>'
			},
			handicapReason:{
				required:'<fmt:message key="ADDITIONAL_HANDICAP_REASON_REQUIRED"/>'
			},
			illness:{
				required:'<fmt:message key="ADDITIONAL_ILLNESS_REQUIRED"/>'
			},
			illnessReason:{
				required:'<fmt:message key="ADDITIONAL_ILLNESS_REASON_REQUIRED"/>'
			},
			fire:{
				required:'<fmt:message key="ADDITIONAL_FIRE_REQUIRED"/>'
			},
			fireReason:{
				required:'<fmt:message key="ADDITIONAL_FIRE_REASON_REQUIRED"/>'
			},
			criminal:{
				required:'<fmt:message key="ADDITIONAL_CRIMINAL_REQUIRED"/>'
			},
			criminalReason:{
				required:'<fmt:message key="ADDITIONAL_CRIMINAL_REASON_REQUIRED"/>'
			},
			military:{
				required:'<fmt:message key="ADDITIONAL_MILITARY_REQUIRED"/>'
			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/AdditionalServ',
       			data: $('#additionalForm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1){
       					window.location.href = "?view=resumeInfo&idResume="+idResume;
       				}
           			else{
           				$('div.container li').remove();
           				if(obj.urlError!=""){
           					window.location.href = obj.urlError;
           				}
           				else{
           					for(var i=0; i<obj.errors.length; i++){
	           					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
	           				}
	           				container.css({'display':'block'});
	           				$('html, body').animate({scrollTop: '0px'}, 300); 
           				}
           			}
       			}
       		});
       		return false;
		}
	});
});

function getEditData(){
	container.hide();
	var service = 'getEditAdditional';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'"}';
	$.ajax({
		type: "POST",
  		url: '/AdditionalServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				if(obj.additional!=null){
					$("#idCard").val(obj.additional.idCard);
					$("input[name='canDrive'][value='"+obj.additional.driveStatus+"']").prop('checked',true);
					if(obj.additional.driveStatus=='TRUE'){
						$('#canDriveId').val(obj.additional.idDrive);
						$('#canDriveIdDiv').show();
						$('#canDriveId').prop('disabled',false);
					}
					$("input[name='handicap'][value='"+obj.additional.handicapStatus+"']").prop('checked',true);
					if(obj.additional.handicapStatus=='TRUE'){
						$('#handicapReason').val(obj.additional.handicapReason);
						$('#handicapReasonDiv').show();
						$('#handicapReason').prop('disabled',false);
					}
					$("input[name='illness'][value='"+obj.additional.illnessStatus+"']").prop('checked',true);
					if(obj.additional.illnessStatus=='TRUE'){
						$('#illnessReason').val(obj.additional.illnessReason);
						$('#illnessReasonDiv').show();
						$('#illnessReason').prop('disabled',false);
					}
					$("input[name='fire'][value='"+obj.additional.fireStatus+"']").prop('checked',true);
					if(obj.additional.fireStatus=='TRUE'){
						$('#fireReason').val(obj.additional.fireReason);
						$('#fireReasonDiv').show();
						$('#fireReason').prop('disabled',false);
					}
					$("input[name='criminal'][value='"+obj.additional.criminalStatus+"']").prop('checked',true);
					if(obj.additional.criminalStatus=='TRUE'){
						$('#criminalReason').val(obj.additional.criminalReason);
						$('#criminalReasonDiv').show();
						$('#criminalReason').prop('disabled',false);
					}
					$("input[name='military'][value='"+obj.additional.militaryStatus+"']").prop('checked',true);
				}
			}
   			else{
   				$('div.container li').remove();
  				for(var i=0; i<obj.errors.length; i++){
   					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
   				}
   				container.css({'display':'block'});
   				$('html, body').animate({scrollTop: '0px'}, 300); 
   			}
		}
	});
}
</script>
<form id="additionalForm" name="additionalForm" method="POST" class="form-horizontal">
	<input name="service" id="service" type="hidden" value="saveAdditional">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
			
	<div class="section_header"><fmt:message key="ADDITIONAL_HEADER"/></div>
	<div class="suggestion"><fmt:message key="DIRECTION_ADDITIONAL"/></div>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<br>
	<div class="row"><div class="caption"><label for="idCard"><b class="star">*</b><fmt:message key="ADDITIONAL_ID_CARD"/></label></div></div>
	<div class="form-group">
   		<input type="text" class="form-control required" name="idCard" id="idCard" maxlength="13">
	</div>
	
	<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="ADDITIONAL_DRIVE"/></label></div></div>
	<div class="form-group">
		<div class="row">
	 		<div class="radio col-lg-4 col-md-4 col-sm-4 col-xs-4">
	 			<label><input type="radio" name="canDrive" value="FALSE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_CAN_NOT"/></label>
	 		</div>
	 		<div class="radio col-lg-8 col-md-8 col-sm-8 col-xs-8">
	 			<label><input type="radio" name="canDrive" value="TRUE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_CAN_DRIVE"/></label>
	 		</div>
	 	</div>
	</div>
	
	<div class="form-group">
		<div id="canDriveIdDiv" style="display:none;margin-top:5px">
			<input disabled type="text" id="canDriveId" class="form-control required" name="canDriveId" maxlength="20">
		</div>
	</div>
	
	<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="ADDITIONAL_HANDICAP"/></label></div></div>
	<div class="form-group">
		<div class="row">
	 		<div class="radio col-lg-4 col-md-4 col-sm-4 col-xs-4">
	 			<label><input type="radio" name="handicap" value="FALSE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_HAVE_NOT"/></label>
	 		</div>
	 		<div class="radio col-lg-8 col-md-8 col-sm-8 col-xs-8">
	 			<label><input type="radio" name="handicap" value="TRUE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_YES_IS"/></label>
	 		</div>
	 	</div>
	</div>
	
	<div class="form-group">
		<div id="handicapReasonDiv" style="display:none;margin-top:5px">
			<input disabled type="text" id="handicapReason" name="handicapReason" maxlength="50" class="form-control required">
		</div>
	</div>
	
	<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="ADDITIONAL_ILLNESS"/></label></div></div>
	<div class="form-group">
		<div class="row">
	 		<div class="radio col-lg-4 col-md-4 col-sm-4 col-xs-4">
	 			<label><input type="radio" name="illness" value="FALSE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_HAVE_NOT"/></label>
	 		</div>
	 		<div class="radio col-lg-8 col-md-8 col-sm-8 col-xs-8">
	 			<label><input type="radio" name="illness" value="TRUE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_YES_IS"/></label>
	 		</div>
	 	</div>
	</div>
	
	<div class="form-group">
		<div id="illnessReasonDiv" style="display:none;margin-top:5px">
			<input disabled type="text" id="illnessReason" name="illnessReason" maxlength="50" class="form-control required">
		</div>
	</div>
	
	<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="ADDITIONAL_FIRE"/></label></div></div>
	<div class="form-group">
		<div class="row">
	 		<div class="radio col-lg-4 col-md-4 col-sm-4 col-xs-4">
	 			<label><input type="radio" name="fire" value="FALSE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_NO"/></label>
	 		</div>
	 		<div class="radio col-lg-8 col-md-8 col-sm-8 col-xs-8">
	 			<label><input type="radio" name="fire" value="TRUE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_YES_BECAUSE"/></label>
	 		</div>
	 	</div>
	</div>
	
	<div class="form-group">
		<div id="fireReasonDiv" style="display:none;margin-top:5px">
			<input disabled type="text" id="fireReason" name="fireReason" maxlength="50" class="form-control required" >
		</div>
	</div> 
	
	<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="ADDITIONAL_CRIMINAL"/></label></div></div>
	<div class="form-group">
		<div class="row">
	 		<div class="radio col-lg-4 col-md-4 col-sm-4 col-xs-4">
	 			<label><input type="radio" name="criminal" value="FALSE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_NO"/></label>
	 		</div>
	 		<div class="radio col-lg-8 col-md-8 col-sm-8 col-xs-8">
	 			<label><input type="radio" name="criminal" value="TRUE" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_YES_BECAUSE"/></label>
	 		</div>
	 	</div>
	</div>
	
	<div class="form-group">
		<div id="criminalReasonDiv" style="display:none;margin-top:5px">
			<input disabled type="text" id="criminalReason" name="criminalReason" maxlength="50" class="form-control required">
		</div>
	</div>
	
	<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="ADDITIONAL_MILITARY"/></label></div></div>
	<div class="form-group">
	    <div class="row">
	        <div class="radio">
	            <label><input type="radio" name="military" value="0" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_MILITARY_PASS"/></label>
	        </div>
	        <div class="radio">
	            <label><input type="radio" name="military" value="1" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_MILITARY_NOT_PASS"/></label>
	        </div>
	        <div class="radio">
	            <label><input type="radio" name="military" value="2" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_MILITARY_EXCEPTION"/></label>
	        </div>
	        <div class="radio">
	            <label><input type="radio" name="military" value="3" style="margin-right:5px;margin-left:5px"><fmt:message key="ADDITIONAL_MILITARY_RESERVE"/></label>
	        </div>
	     </div>
	</div>
	
	<div class="form-group">
		<div class="row">
	 		<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
	 			<input type="button" id="buttonBack" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_CANCEL"/>">
	 		</div>
	 		<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
	 			<input type="submit" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" >
	 		</div>
	 	</div>
 	</div>
	
</form>
