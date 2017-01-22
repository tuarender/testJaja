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
	int idFamily = Util.StrToInt(request.getParameter("idFamily"));
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	request.setAttribute("idFamily",idFamily);
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<script>
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";
var idLanguage = '<c:out value="${idLanguage}"/>';
var countFamily = 0;
$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	getFamilyList();
	getEditData();
	$('#buttonBack').click(function(){
		window.location.href = "?view=resumeInfo&idResume="+idResume;
	});
	//---------------------------- set event ----------------------------
	$('#familyType').change(function() {
		$('input[name="aliveStatus"]').prop("disabled",true);
		$('.aliveStatus').hide();
		$('input[name="citizenship"]').prop("disabled",true);
		$('.citizenship').hide();
		$('input[name="workStatus"]').prop("disabled",true);
		$('.workStatus').hide();
		$('input[name="age"]').prop("disabled",true);
		$('.age').hide();
		$('#relationship').prop("disabled",true);
		$('.relationship').hide();
		$('input[name="eduStatus"]').prop("disabled",true);
		$('.eduStatus').hide();
		$('.familyDetail').hide();
		$('#familyName').prop("disabled",true);
		$('#familyLastName').prop("disabled",true);
		$('#familyTelephone').prop("disabled",true);
		$('input[name="spouseStatus"]').prop("disabled",true);
		$('.spouseStatus').hide();
		$('#citizenshipOther').prop("disabled",true);
		$('.citizenshipOther').hide();
		$('#position').prop("disabled",true);
		$('#company').prop("disabled",true);
		$('.workStatusDetail').hide();
		$('#eduStatusDetail').prop("disabled",true);
		$('.eduStatusDetail').hide();
		clearFamilySelected();
		if($(this).val()!=''){
			showFormByType($(this).val());
		}
	});
	
	$('input[name="spouseStatus"]').change(function() {
		if($(this).val()=='MARRIED'){
			$('.familyDetail').show();
			$('#familyName').prop("disabled",false);
			$('#familyLastName').prop("disabled",false);
			$('#familyTelephone').prop("disabled",false);
			$('input[name="citizenship"]').prop("disabled",false);
			$('.citizenship').show();
			$('input[name="workStatus"]').prop("disabled",false);
			$('.workStatus').show();
		}
		else{
			$('.familyDetail').hide();
			$('#familyName').prop("disabled",true);
			$('#familyLastName').prop("disabled",true);
			$('#familyTelephone').prop("disabled",true);
			$('input[name="citizenship"]').prop("disabled",true);
			$('.citizenship').hide();
			$('input[name="workStatus"]').prop("disabled",true);
			$('.workStatus').hide();
		}
	});
	
	$("input[name='citizenship']").change(function() {
		if($(this).val()=='THAI'){
			$('#citizenshipOther').prop("disabled",true);
			$('.citizenshipOther').hide();
		}
		else{
			$('#citizenshipOther').prop("disabled",false);
			$('.citizenshipOther').show();
		}
	});
	
	$("input[name='workStatus']").change(function() {
		if($(this).val()=='WORKING'||$(this).val()=='RETIRED'){
			$('#position').prop("disabled",false);
			$('.workStatusDetail').show();
			$('#company').prop("disabled",false);
			$('.workStatusDetail').show();
		}
		else{
			$('#position').prop("disabled",true);
			$('.workStatusDetail').hide();
			$('#company').prop("disabled",true);
			$('.workStatusDetail').hide();
		}
	});
	
	$("input[name='eduStatus']").change(function() {
		if($(this).val()=='WORKING'){
			$('#position').prop("disabled",false);
			$('.workStatusDetail').show();
			$('#company').prop("disabled",false);
			$('.workStatusDetail').show();
			$('#eduStatusDetail').prop("disabled",true);
			$('.eduStatusDetail').hide();
		}
		else if($(this).val()=='LEARNING'){
			$('#position').prop("disabled",true);
			$('.workStatusDetail').hide();
			$('#company').prop("disabled",true);
			$('.workStatusDetail').hide();
			$('#eduStatusDetail').prop("disabled",false);
			$('.eduStatusDetail').show();
		}
	});
	
	//---------------------------- validation ---------------------------
	$('#familyForm').validate({
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
 			familyType:{
 				required:true
 			},
 			familyName:{
 				required:true
 			},
 			familyLastName:{
 				required:true
 			},
 			familyTelephone:{
 				required:true
 			},
 			spouseStatus:{
 				required:true
 			},
 			aliveStatus:{
 				required:true
 			},
 			citizenship:{
 				required:true
 			},
 			citizenshipOther:{
 				required:true
 			},
 			age:{
 				required:true,
 				number:true,
 				min:0,
 				max:100
 			},
 			relationship:{
 				required:true
 			},
 			workStatus:{
 				required:true
 			},
 			company:{
 				required:true
 			},
 			position:{
 				required:true
 			},
 			eduStatus:{
 				required:true
 			},
 			eduStatusDetail:{
 				required:true
 			}
		},			  
		messages:{
			familyType:{
				required:'<fmt:message key="FAMILY_TYPE_REQUIRE"/>'
			},
			familyName:{
				required:'<fmt:message key="FAMILY_NAME_REQUIRED"/>'
			},
			familyLastName:{
				required:'<fmt:message key="FAMILY_LAST_NAME_REQUIRED"/>'
			},
			familyTelephone:{
				required:'<fmt:message key="FAMILY_TELEPHONE_REQUIRED"/>'
			},
 			spouseStatus:{
 				required:'<fmt:message key="FAMILY_MARRIED_STATUS_REQUIRED"/>'
 			},
 			aliveStatus:{
 				required:'<fmt:message key="FAMILY_ALLIVE_REQUIRED"/>'
 			},
 			citizenship:{
 				required:'<fmt:message key="FAMILY_CITIZENSHIP_REQUIRED"/>'
 			},
 			citizenshipOther:{
 				required:'<fmt:message key="FAMILY_CITIZENSHIP_OTHER_REQUIRED"/>'
 			},
 			age:{
 				required:'<fmt:message key="AGE_REQUIRED"/>',
 				number:'<fmt:message key="AGE_NUMBER_REQUIRED"/>',
 				min:'<fmt:message key="AGE_MIN_REQUIRED"/>',
 				max:'<fmt:message key="AGE_MAX_REQUIRED"/>'
 			},
 			relationship:{
 				required:'<fmt:message key="SIBLING_RELATIONSHIP_REQUIRED"/>'
 			},
 			workStatus:{
 				required:'<fmt:message key="WORK_STATUS_REQUIRED"/>'
 			},
 			company:{
 				required:'<fmt:message key="COMPANY_REQUIRED"/>'
 			},
 			position:{
 				required:'<fmt:message key="POSITION_REQUIRED"/>'
 			},
 			eduStatus:{
 				required:'<fmt:message key="WORK_STATUS_REQUIRED"/>'
 			},
 			eduStatusDetail:{
 				required:'<fmt:message key="EDUCATION_REQUIRED"/>'
 			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/FamilyServ',
       			data: $('#familyForm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1){
       					window.location.href = "?view=family&idResume="+idResume;
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

function clearFamilySelected(){
	$('#familyName').val('');
	$('#familyLastName').val('');
	$('#citizenshipOther').val('');
	$('#familyTelephone').val('');
	$('#company').val('');
	$('#position').val('');
	$('#eduStatusDetail').val('');
	$('#age').val('');
	$('input[name="workStatus"]').prop("checked",false);
	$('input[name="aliveStatus"]').prop("checked",false);
	$('input[name="citizenship"]').prop("checked",false);
	$('input[name="eduStatus"]').prop("checked",false);
	$('input[name="spouseStatus"]').prop("checked",false);
	$('#relationship').val('');
}

function getFamilySubField(idFamilyField){
	container.hide();
	var service = 'getFamilySubField';
	var request = '{"service":"'+service+'","idFamilyField":"'+idFamilyField+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/FamilyServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				for(var i=0;i<obj.familySubField.length;i++){
					$('#familySubField').append("<option value='"+obj.familySubField[i].idCertSubfield+"'>"+obj.familySubField[i].certSubfieldName+"</option>");
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

function getFamilyList(){
	container.hide();
	var service = 'getFamilyList';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/FamilyServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				var html = "<div align='right'></div>";
				html += "<ol>";
				countFamily = obj.familyListDetail.length;
				for(var i=0;i<obj.familyListDetail.length;i++){
					html += "<li><b>"+obj.familyListDetail[i][13]+"</b>";
					if(obj.familyListDetail[i][1]=="SPOUSE"){
						html += "&nbsp;:&nbsp;"+obj.familyListDetail[i][14];
						if(obj.familyListDetail[i][10]=="MARRIED"){
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><fmt:message key='FAMILY_CITIZENSHIP'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][5];
							html += "<br><fmt:message key='FAMILY_SIBLING_STATUS'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][15];
							if(obj.familyListDetail[i][6]=="WORKING"){
								html += "<br><fmt:message key='FAMILY_WORK_STATUS'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
								html += "<br><fmt:message key='FAMILY_WORK_POSITION'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
							}
							else if(obj.familyListDetail[i][6]=="RETIRED"){
								html += "<br><fmt:message key='FAMILY_WORK_LAST_COMPANY'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
								html += "<br><fmt:message key='FAMILY_WORK_POSITION'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
							}
							else if(obj.familyListDetail[i][6]=="LEARNING"){
								html += "<br><fmt:message key='FAMILY_EDUCATION'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
							}
							html +="<br><fmt:message key='FAMILY_TELEPHONE'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][9];
						}
					}
					else{
						//if parrent show alive status and citizenship
						if(obj.familyListDetail[i][1]=="FATHER"||obj.familyListDetail[i][1]=="MOTHER"){
							html += "&nbsp;<b>(</b>"+obj.familyListDetail[i][4]+"<b>)</b>";
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><fmt:message key='FAMILY_CITIZENSHIP'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][5];
						}
						else if(obj.familyListDetail[i][1]=="SIBLING"){
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><fmt:message key='FAMILY_RELATIONSHIP'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][16];
							html += "<br><fmt:message key='FAMILY_AGE'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][12];
						}
						else if(obj.familyListDetail[i][1]=="CHILD"){
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><fmt:message key='FAMILY_AGE'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][12];
						}
						else{
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
						}
						
						html += "<br><fmt:message key='FAMILY_SIBLING_STATUS'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][15];
						//show status work by type
						if(obj.familyListDetail[i][6]=="WORKING"){
							html += "<br><fmt:message key='FAMILY_WORK_STATUS'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
							html += "<br><fmt:message key='FAMILY_WORK_POSITION'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
						}
						else if(obj.familyListDetail[i][6]=="RETIRED"){
							html += "<br><fmt:message key='FAMILY_WORK_LAST_COMPANY'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
							html += "<br><fmt:message key='FAMILY_WORK_POSITION'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
						}
						else if(obj.familyListDetail[i][6]=="LEARNING"){
							html += "<br><fmt:message key='FAMILY_EDUCATION'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
						}
						html +="<br><fmt:message key='FAMILY_TELEPHONE'/>&nbsp;:&nbsp;"+obj.familyListDetail[i][9];
					}
					html +="<br><a href='javascript:void(0)' onclick='editFamily("+obj.familyListDetail[i][0]+")'><fmt:message key='GLOBAL_EDIT'/></a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='dialogDelFamily("+obj.familyListDetail[i][0]+")'><fmt:message key='GLOBAL_DELETE'/></a>";
					html +="</li>";
				}
				html+="</ol>";
				$("#familyListDiv").html(html);
				$("#familyListDiv").show();
				if(countFamily>0){
					hideFamilyForm();
				}
				else{
					showFamilyForm();
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

function dialogDelFamily(idFamily){
$('#md1').data('id',idFamily).modal('show');
}
function btnDelFamily(){
	var id = $('#md1').data('id');
	deleteFamily(id);
	$('#md1').modal("hide");	
}

function deleteFamily(idFamily){

		container.hide();
		var service = 'deleteFamily';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idFamily":"'+idFamily+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/FamilyServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					if(idFamily==$('#idFamily').val()){
						window.location.href = "?view=family&idResume="+idResume;
					}
					else{
						getFamilyList();
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


function editFamily(idFamily){
	window.location.href = "?view=family&idResume="+idResume+"&idFamily="+idFamily;
}

function showFamilyForm(){
	$('.toggleDisplay').show();
	$('html, body').animate({scrollTop: $('#headToggleDisplay').offset().top}, 'slow');
	$('#buttonToggle').html('<input type="submit" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_NEXT"/>">');
	showForm = true;
}

function hideFamilyForm(){
	$('.toggleDisplay').hide();
	$('#buttonToggle').html('<input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showFamilyForm()">');
}

function getEditData(){
	if($('#idFamily').val()>0){
		container.hide();
		var service = 'getEditFamily';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idFamily":"'+$('#idFamily').val()+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/FamilyServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					$('#familyType option[value="'+obj.familyListDetail[0][1]+'"]').prop('selected', true);
					$('input[name="spouseStatus"]').filter('[value="'+obj.familyListDetail[0][10]+'"]').prop('checked', true);
					if(obj.familyListDetail[0][10]=='MARRIED'){
						$('.familyDetail').show();
						$('#familyName').prop("disabled",false);
						$('#familyLastName').prop("disabled",false);
						$('#familyTelephone').prop("disabled",false);
						$('input[name="citizenship"]').prop("disabled",false);
						$('.citizenship').show();
						$('input[name="workStatus"]').prop("disabled",false);
						$('.workStatus').show();	
					}
					$('input[name="aliveStatus"]').filter('[value='+obj.familyListDetail[0][17]+']').prop('checked', true);
					if(obj.familyListDetail[0][5]!=null&&obj.familyListDetail[0][5]!=""){
						if(obj.familyListDetail[0][18]=='THAI'){
						$('input[name="citizenship"]').filter('[value="THAI"]').prop('checked', true);
						}
						else{
							$('input[name="citizenship"]').filter('[value="OTHER"]').prop('checked', true);
							$('#citizenshipOther').val(obj.familyListDetail[0][18]);
							$('#citizenshipOther').prop("disabled",false);
							$('.citizenshipOther').show();
						}
					}
					if(obj.familyListDetail[0][1]=='SIBLING'||obj.familyListDetail[0][1]=='CHILD'){
						$('input[name="eduStatus"]').filter('[value="'+obj.familyListDetail[0][6]+'"]').prop('checked', true);
					}
					else{
						$('input[name="workStatus"]').filter('[value="'+obj.familyListDetail[0][6]+'"]').prop('checked', true);
					}
					if(obj.familyListDetail[0][6]=='WORKING'||obj.familyListDetail[0][6]=="RETIRED"){
						$('#position').prop("disabled",false);
						$('.workStatusDetail').show();
						$('#company').prop("disabled",false);
						$('.workStatusDetail').show();
						$('#company').val(obj.familyListDetail[0][7]);
						$('#position').val(obj.familyListDetail[0][8]);
					}
					else if(obj.familyListDetail[0][6]=='LEARNING'){
						$('#position').prop("disabled",true);
						$('.workStatusDetail').hide();
						$('#company').prop("disabled",true);
						$('.workStatusDetail').hide();
						$('#eduStatusDetail').prop("disabled",false);
						$('.eduStatusDetail').show();
						$('#eduStatusDetail').val(obj.familyListDetail[0][7]);
					}
					$('#relationship option[value="'+obj.familyListDetail[0][11]+'"]').prop('selected', true);
					$('#age').val(obj.familyListDetail[0][12]);
					$('#familyName').val(obj.familyListDetail[0][2]);
					$('#familyLastName').val(obj.familyListDetail[0][3]);
					$('#familyTelephone').val(obj.familyListDetail[0][9]);
					showFormByType(obj.familyListDetail[0][1]);
					showFamilyForm();
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

function showFormByType(type){
	if(type!='SPOUSE'){
		$('.familyDetail').show();
		$('#familyName').prop("disabled",false);
		$('#familyLastName').prop("disabled",false);
		$('#familyTelephone').prop("disabled",false);
		//------------------is father or mother----------------
		if(type=='FATHER'||type=='MOTHER'){
			$('input[name="aliveStatus"]').prop("disabled",false);
			$('.aliveStatus').show();
			$('input[name="citizenship"]').prop("disabled",false);
			$('.citizenship').show();
			$('input[name="workStatus"]').prop("disabled",false);
			$('.workStatus').show();
		}
		//------------------is sibling----------------
		else if(type=='SIBLING'||type=='CHILD'){
			$('input[name="age"]').prop("disabled",false);
			$('.age').show();
			$('input[name="eduStatus"]').prop("disabled",false);
			$('.eduStatus').show();
			if(type=='SIBLING'){
				$('#relationship').prop("disabled",false);
				$('.relationship').show();
			}
		}
	}
	else{
		$('input[name="spouseStatus"]').prop("disabled",false);
		$('.spouseStatus').show();
	}
}
</script>

<form id="familyForm" name="familyForm" method="post" class="form-horizontal">
	<input name="service" id="service" type="hidden" value="saveFamily">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" id="idFamily" name="idFamily" value="<c:out value="${idFamily}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
	
	<div class="section_header"><fmt:message key="FAMILY"/></div>
	<div class="suggestion"><fmt:message key="DIRECTION_FAMILY"/></div>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<br>
	
	<div id="familyListDiv" style="display:none"></div>
	
	<div class="toggleDisplay" id="headToggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="familyType"><b class="star">*</b><fmt:message key="FAMILY_RELATIONSHIP"/></label></div></div>
		<div class="form-group">
			<select name="familyType" id="familyType" class="form-control required">
				<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
				<option value="FATHER"><fmt:message key="FAMILY_FATHER"/></option>
				<option value="MOTHER"><fmt:message key="FAMILY_MOTHER"/></option>
				<option value="SIBLING"><fmt:message key="FAMILY_SIBLING"/></option>
				<option value="SPOUSE"><fmt:message key="FAMILY_SPOUSE"/></option>
				<option value="CHILD"><fmt:message key="FAMILY_CHILD"/></option>
			</select>
		</div>
	</div>
	
	<div class="spouseStatus" style="display:none">
		<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="FAMILY_MARRIED_STATUS"/></label></div></div>
		<div class="form-group">
			<div class="radio">
	            <label>
	                <input disabled type="radio" name="spouseStatus" value="SINGLE" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_MARRIED_STATUS_SINGLE"/>
	            </label>
	        </div>
	        <div class="radio">
	            <label>
	                <input disabled type="radio" name="spouseStatus" value="MARRIED" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_MARRIED_STATUS_MARRIED"/>
	            </label>
	        </div>
	        <div class="radio">
	            <label>
	                <input disabled type="radio" name="spouseStatus" value="DIVORCED" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_MARRIED_STATUS_DIVORCED"/>
	            </label>
	        </div>
		</div>
	</div>
	
	<div class="familyDetail" style="display:none">
		<div class="row"><div class="caption"><label for="familyName"><b class="star">*</b><fmt:message key="FAMILY_NAME"/></label></div></div>
		<div class="form-group">
			<input disabled type="text" name="familyName" id="familyName" class="form-control required" maxlength="250">
		</div>
		<div class="row"><div class="caption"><label for="familyLastName"><b class="star">*</b><fmt:message key="FAMILY_LASTNAME"/></label></div></div>
		<div class="form-group">
			<input disabled type="text" name="familyLastName" id="familyLastName" class="form-control required" maxlength="250">
		</div>
	</div>
	
	<div class="aliveStatus" style="display:none">
		<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="FAMILY_STATUS"/></label></div></div>
		<div class="form-group">
			<div class="row">
		 		<div class="radio col-lg-6 col-md-6 col-sm-6 col-xs-6">
		 			<label><input disabled type="radio" name="aliveStatus" value="ALIVE" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_STATUS_ALIVE"/></label>
		 		</div>
		 		<div class="radio col-lg-6 col-md-6 col-sm-6 col-xs-6">
		 			<label><input disabled type="radio" name="aliveStatus" value="DIED" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_STATUS_DIED"/></label>
		 		</div>
	 		</div>
	 	</div>
	</div>
		
	<div class="citizenship" style="display:none">
		<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="FAMILY_CITIZENSHIP"/></label></div></div>
		<div class="form-group">
			<div class="row">
		 		<div class="radio col-lg-6 col-md-6 col-sm-6 col-xs-6">
		 			<label><input disabled type="radio" name="citizenship" value="THAI" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_CITIZENSHIP_THAI"/></label>
		 		</div>
		 		<div class="radio col-lg-6 col-md-6 col-sm-6 col-xs-6">
		 			<label><input disabled type="radio" name="citizenship" value="OTHER" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_CITIZENSHIP_OTHER"/></label>
		 		</div>
	 		</div>
		</div>
	</div>
	
	<div class="citizenshipOther" style="display:none">
		<div class="row"><div class="caption"><label for="citizenshipOther"><b class="star">*</b><fmt:message key="GLOBAL_SPECIFY"/></label></div></div>
		<div class="form-group">
			<input disabled type='text' name="citizenshipOther" id="citizenshipOther" class="form-control required" maxlength="200">
		</div>
	</div>
	
	<div class="age" style="display:none">
		<div class="row"><div class="caption"><label for="age"><b class="star">*</b><fmt:message key="FAMILY_AGE"/></label></div></div>
		<div class="form-group">
			<input disabled type='text' name="age" id="age" maxlength="3" class="form-control required" >
		</div>
	</div>
	
	<div class="relationship" style="display:none">
		<div class="row"><div class="caption"><label for="relationship"><b class="star">*</b><fmt:message key="FAMILY_SIBLING_RELATIONSHIP"/></label></div></div>
		<div class="form-group">
			<select class="form-control required" disabled name="relationship" id="relationship">
				<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
				<option value="OLDER_SISTER"><fmt:message key="FAMILY_SIBLING_OLDER_SISTER"/></option>
				<option value="YOUNGER_SISTER"><fmt:message key="FAMILY_SIBLING_YOUNGER_SISTER"/></option>
				<option value="OLDER_BROTHER"><fmt:message key="FAMILY_SIBLING_OLDER_BROTHER"/></option>
				<option value="YOUNGER_BROTHER"><fmt:message key="FAMILY_SIBLING_YOUNGER_BROTHER"/></option>
			</select>
		</div>
	</div>
	
	<div class="workStatus" style="display:none">
		<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="FAMILY_WORK_STATUS"/></label></div></div>
		<div class="form-group">
			<div class="row">
		 		<div class="radio">
		 			<label><input disabled type="radio" name="workStatus" value="WORKING" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_WORK_STATUS_WORK"/></label>
		 		</div>
		 		<div class="radio">
		 			<label><input disabled type="radio" name="workStatus" value="RETIRED" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_WORK_STATUS_RETIRE"/></label>
		 		</div>
		 		<div class="radio">
		 			<label><input disabled type="radio" name="workStatus" value="NONWORKING" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_WORK_STATUS_NOT"/></label>
		 		</div>
		 		<div class="radio">
		 			<label><input disabled type="radio" name="workStatus" value="HOUSEWIFE" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_WORK_STATUS_FATHER_HOME"/>/<fmt:message key="FAMILY_WORK_STATUS_MOTHER_HOME"/></label>
		 		</div>
		 	</div>
		</div>
	</div>
		
	<div class="eduStatus" style="display:none">
		<div class="row"><div class="caption"><label class="control-label"><b class="star">*</b><fmt:message key="FAMILY_WORK_STATUS"/></label></div></div>
		<div class="form-group">
			<div class="row">
		 		<div class="radio">
		 			<label><input disabled type="radio" name="eduStatus" value="LEARNING" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_SIBLING_STATUS_STUDYING"/></label>
		 		</div>
		 		<div class="radio">
		 			<label><input disabled type="radio" name="eduStatus" value="WORKING" style="margin-left:5px;margin-right:5px"><fmt:message key="FAMILY_STATUS_WORKING"/></label>
		 		</div>
		 	</div>
		</div>
	</div>
		
	<div class="eduStatusDetail" style="display:none">
		<div class="row"><div class="caption"><label for="eduStatusDetail"><b class="star">*</b><fmt:message key="FAMILY_EDUCATION"/></label></div></div>
		<div class="form-group">
			<input disabled type='text' name="eduStatusDetail" id="eduStatusDetail" class="form-control required" maxlength="200">
		</div>
	</div>
	
	<div class="workStatusDetail" style="display:none;">
		<div class="row"><div class="caption"><label for="company"><b class="star">*</b><fmt:message key="FAMILY_WORK_COMPANY"/></label></div></div>
		<div class="form-group">
			<input disabled type='text' name="company" id="company" maxlength="200" class="form-control required">
		</div>
		<div class="row"><div class="caption"><label for="position"><b class="star">*</b><fmt:message key="FAMILY_WORK_POSITION"/></label></div></div>
		<div class="form-group">
			<input disabled type='text' name="position" id="position" maxlength="200" class="form-control required">
		</div>
	</div>
		
	<div class="familyDetail" style="display:none">
		<div class="row"><div class="caption"><label for="familyTelephone"><b class="star">*</b><fmt:message key="FAMILY_TELEPHONE"/></label></div></div>
		<div class="form-group">
			<input disabled type="text" name="familyTelephone" id="familyTelephone" class="form-control required" maxlength="60">
		</div>
	</div>
		
	<div class="form-group">
		<div class="row">
	 		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	 			<input type="button" id="buttonBack" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_CANCEL"/>">
	 		</div>
	 		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
	 			<span id="buttonToggle"><input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showFamilyForm()"></span>
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
			        <button type="button" class="btn btn-primary" onclick="btnDelFamily()"><fmt:message key="GLOBAL_CONFIRM"/></button>
      			</div>
    		</div><!-- /.modal-content -->
  		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- /end modal skip delete -->
	
</form>







