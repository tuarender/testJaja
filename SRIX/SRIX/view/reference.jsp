<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.CertFieldLanguage"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>

<%
	Resume resume=(Resume)request.getAttribute("resume");
	int idLanguage=resume.getIdLanguage();
	int idCountry=resume.getIdCountry();
	//int idLanguage=Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	//int idCountry=Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.StrToInt(request.getParameter("idResume"));
	int idReference = Util.StrToInt(request.getParameter("idReference"));
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	request.setAttribute("idReference",idReference);
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<script>
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";
var idLanguage = '<c:out value="${idLanguage}"/>';
var countReference = 0;
$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	getReferenceList();
	getEditData();
	$('#buttonBack').click(function(){
		window.location.href = "?view=resumeInfo&idResume="+idResume;
	});
	//---------------------------- set event ----------------------------
	$('#referenceType').change(function() {
		if($(this).val()==4){
			$('#referenceTypeOther').prop("disabled",false);
			$('.referenceOther').show();
		}
		else{
			$('#referenceTypeOther').prop("disabled",true);
			$('.referenceOther').hide();
		}
	});
	//---------------------------- validation ---------------------------
	$('#referenceForm').validate({
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		invalidHandler: function(form, validator) {
			$('html, body').animate({scrollTop:"0px" }, 300);      
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
 			referenceType:{
 				required:true
 			},
 			referenceTypeOther:{
 				required:true
 			},
 			referenceName:{
 				required:true
 			},
 			referenceTelephone:{
 				required:true
 			}
 			
		},			  
		messages:{
			referenceType:{
				required:'<fmt:message key="REFERENCES_RELATIONSHIP_REQUIRED"/>'
			},
			referenceTypeOther:{
				required:'<fmt:message key="REFERENCES_RELATIONSHIP_OTHER_REQUIRED"/>'
			},
			referenceName:{
				required:'<fmt:message key="REFERENCES_NAME_REQUIRED"/>'
			},
			referenceTelephone:{
				required:'<fmt:message key="REFERENCES_TELEPHONE_REQUIRED"/>'
			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/ReferenceServ',
       			data: $('#referenceForm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1){
       					window.location.href = "?view=reference&idResume="+idResume;
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

function getReferenceSubField(idReferenceField){
	container.hide();
	var service = 'getReferenceSubField';
	var request = '{"service":"'+service+'","idReferenceField":"'+idReferenceField+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/ReferenceServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				for(var i=0;i<obj.referenceSubField.length;i++){
					$('#referenceSubField').append("<option value='"+obj.referenceSubField[i].idCertSubfield+"'>"+obj.referenceSubField[i].certSubfieldName+"</option>");
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

function getReferenceList(){
	container.hide();
	var service = 'getReferenceList';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/ReferenceServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				var html = "<div align='right'></div>";
				html += "<ol>";
				countReference = obj.referenceListDetail.length;
				for(var i=0;i<obj.referenceListDetail.length;i++){
					html +="<li>"+obj.referenceListDetail[i][7];
					html +="<br>"+obj.referenceListDetail[i][1]; 
					if(obj.referenceListDetail[i][2]!=""){
						html +="<br>"+obj.referenceListDetail[i][2];
					}
					if(obj.referenceListDetail[i][3]!=""){
						html +="<br>"+obj.referenceListDetail[i][3];
					}
					html +="<br>"+obj.referenceListDetail[i][4];
					html +="<br><a href='/SRIX?view=reference&idResume="+idResume+"&idReference="+obj.referenceListDetail[i][0]+"' ><fmt:message key='GLOBAL_EDIT'/></a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='dialogDelReference("+obj.referenceListDetail[i][0]+")'><fmt:message key='GLOBAL_DELETE'/></a>";
					html +="</li>";
				}
				html+="</ol>";
				$("#referenceListDiv").html(html);
				$("#referenceListDiv").show();
				if(countReference>0){
					hideReferenceForm();
					if(countReference==2&&$('#idReference').val()<=0){
						$('#buttonToggle').hide();
					}
					else{
						$('#buttonToggle').show();
					}
				}
				else{
					showReferenceForm();
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

function dialogDelReference(idReference){
$('#md1').data('id',idReference).modal('show');
}
function btnDelReference(){
	var id = $('#md1').data('id');
	deleteReference(id);
	$('#md1').modal("hide");	
}

function deleteReference(idReference){
	container.hide();
	var service = 'deleteReference';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","idReference":"'+idReference+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/ReferenceServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				//check deleted when edit
				if(idReference==$('#idReference').val()){
					window.location.href = "?view=reference&idResume="+idResume;
				}
				else{
					getReferenceList();
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


function editReference(idReference){
	window.location = "/SRIX?view=reference&idResume="+idResume+"&idReference="+idReference;
}

function showReferenceForm(){
	$('.toggleDisplay').show();
	$('html, body').animate({scrollTop: $('#headToggleDisplay').offset().top}, 'slow');
	$('#buttonToggle').html('<input type="submit" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_NEXT"/>">');
	showForm = true;
}

function hideReferenceForm(){
	$('.toggleDisplay').hide();
	$('#buttonToggle').html('<input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showReferenceForm()">');
}

function getEditData(){
	if($('#idReference').val()>0){
		container.hide();
		var service = 'getEditReference';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idReference":"'+$('#idReference').val()+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/ReferenceServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					$('#referenceType option[value="'+parseInt(obj.referenceListDetail[0][4])+'"]').prop('selected', true);
					if(parseInt(obj.referenceListDetail[0][4])==4){
						$('#referenceTypeOther').prop("disabled",false);
						$('.referenceOther').show();
					}
					$('#referenceTypeOther').val(obj.referenceListDetail[0][5]);
					$('#referenceName').val(obj.referenceListDetail[0][6]);
					$('#referenceCompany').val(obj.referenceListDetail[0][1]);
					$('#referenceTitle').val(obj.referenceListDetail[0][2]);
					$('#referenceTelephone').val(obj.referenceListDetail[0][3]);
					showReferenceForm();
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
}
</script>
<form id="referenceForm" name="referenceForm" method="post" class="form-horizontal">
	<input name="service" id="service" type="hidden" value="saveReference">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" id="idReference" name="idReference" value="<c:out value="${idReference}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
	
	<div class="section_header"><fmt:message key="REFERENCES"/></div>
	<div class="suggestion"><fmt:message key="DIRECTION_REFERENCES"/></div>
	<br><br><br>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	
	<div id="referenceListDiv" style="display:none"></div>
	
	<div class="toggleDisplay" id="headToggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="referenceType"><b class="star">*</b><fmt:message key="REFERENCES_RELATIONSHIP"/></label></div>
			<select class="form-control required" name="referenceType" id="referenceType" style="width: 280px">
				<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
				<option value="2"><fmt:message key="REFERENCES_RELATIONSHIP_CURRENT"/></option>
				<option value="1"><fmt:message key="REFERENCES_RELATIONSHIP_PREVIOUS"/></option>
				<option value="3"><fmt:message key="REFERENCES_RELATIONSHIP_PROFESSOR"/></option>
				<option value="5"><fmt:message key="REFERENCES_RELATIONSHIP_BUSINESS"/></option>
				<option value="6"><fmt:message key="REFERENCES_RELATIONSHIP_BUSINESS_COLLEAGUE"/></option>
				<option value="4"><fmt:message key="GLOBAL_OTHER"/></option>
			</select>
		</div>
		<div class="form-group">
			<div class="referenceOther" align="left" style="margin-right:10px;display:none">
				<div class="caption"><label for="referenceTypeOther"><b class="star">*</b><fmt:message key="GLOBAL_SPECIFY"/></label></div>
				<input disabled type='text' class="form-control required" name="referenceTypeOther" id="referenceTypeOther" maxlength="200" style="width: 280px">
			</div>
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="referenceName"><b class="star">*</b><fmt:message key="REFERENCES_NAME"/></label></div>
			<input type="text" class="form-control required" name="referenceName" id="referenceName" maxlength="250" style="width: 280px">
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="referenceCompany"><fmt:message key="REFERENCES_COMPANY"/></label></div>
			<input type="text" class="form-control" name="referenceCompany" id="referenceCompany" maxlength="250" style="width: 280px">
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="referenceTitle"><fmt:message key="REFERENCES_TITLE"/></label></div>
			<input type="text" class="form-control" name="referenceTitle" id="referenceTitle" maxlength="250" style="width: 280px">
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="referenceTelephone"><b class="star">*</b><fmt:message key="REFERENCES_TELEPHONE"/></label></div>
			<input type="text" class="form-control required" name="referenceTelephone" id="referenceTelephone" maxlength="60" style="width: 280px">
		</div>
	</div>
	
	<div class="form-group">
		<div class="row">
	 		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	 			<input type="button" id="buttonBack" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_CANCEL"/>">
	 		</div>
	 		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
	 			<div id="buttonToggle"><input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showReferenceForm()"></div>
	 		</div>
	 	</div>
 	</div>
 	
 	<!-- modal skip delete -->
	<div class="modal fade" id="md1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  		<div class="modal-dialog modal-sm">
    		<div class="modal-content">
      			<div class="modal-header">
       				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        			<h4 class="modal-title">Message</h4>
      			</div>
      			<div class="modal-body">
       		 		<p><fmt:message key="CONFIRM_DELETE"/></p>
      			</div>
      			<div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
			        <button type="button" class="btn btn-primary" onclick="btnDelReference()"><fmt:message key="GLOBAL_CONFIRM"/></button>
      			</div>
    		</div><!-- /.modal-content -->
  		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- /end modal skip delete -->
</form>
