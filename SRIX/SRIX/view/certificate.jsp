<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.CertFieldLanguage"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.Certificate"%>
<%@page import="com.topgun.resume.CertificateManager"%>
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
	int idCertificate = Util.StrToInt(request.getParameter("idCertificate"));
	Certificate cert = new CertificateManager().get(idJsk,idResume,idCertificate); //use for edit
	ArrayList<CertFieldLanguage> certField = MasterDataManager.getAllCertFieldLanguage(idLanguage);
	request.setAttribute("certField",certField);
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	request.setAttribute("idCertificate",idCertificate);
	request.setAttribute("cert",cert);
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<script>
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";
var idLanguage = '<c:out value="${idLanguage}"/>';
var countCertificate = 0;




$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	getCertificateList();
	getEditData();
	$('#buttonBack').click(function(){
		window.location.href = "?view=resumeInfo&idResume="+idResume;
	});
	//---------------------------- set event ----------------------------
	$('#certificateField').change(function() {
		if($(this).val()==-1){
			$('#certificateSubField option[value!="-1"]').remove();
			$('#certificateName').prop("disabled",false);
			$('.certificateOther').show();
		}
		else{
			$('#certificateSubField option').remove();
			$('#certificateSubField').append("<option value='' selected><fmt:message key='GLOBAL_SELECT'/></option>");
			getCertificateSubField($(this).val());
			$('#certificateSubField').append("<option value='-1'><fmt:message key='GLOBAL_OTHER'/></option>");
			$('#certificateName').prop("disabled",true);
			$('.certificateOther').hide();
		}
	});
	
	$('#certificateSubField').change(function() {
		if($(this).val()==-1){
			$('.certificateOther').show();
			$('#certificateName').prop("disabled",false);
		}
		else{
			$('.certificateOther').hide();
			$('#certificateName').prop("disabled",true);
		}
	});
	//---------------------------- validation ---------------------------
	$('#certificateForm').validate({
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
 			certificateField:{
 				required:true
 			},
 			certificateSubField:{
 				required:true
 			},
 			certificateOther:{
 				required:true
 			},
 			certificateIssue:{
 				required:true
 			},
 			startMonth:{
 				required:true
 			},
 			startYear:{
 				required:true
 			}
		},			  
		messages:{
			certificateField:{
				required:'<fmt:message key="CERT_FIELD_REQUIRED"/>'
			},
			certificateSubField:{
				required:'<fmt:message key="CERT_NAME_REQUIRED"/>'
			},
			certificateOther:{
				required:'<fmt:message key="CERT_NAME_OTHER_REQUIRED"/>'
			},
			certificateIssue:{
				required:'<fmt:message key="CERT_ISSUE_REQUIRE"/>'
			},
			startMonth:{
 				required:'<fmt:message key="CERT_DATE_REQUIRED"/>'
 			},
 			startYear:{
 				required:'<fmt:message key="CERT_DATE_REQUIRED"/>'
 			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/CertificateServ',
       			data: $('#certificateForm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1){
       					window.location.href = "?view=certificate&idResume="+idResume;
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

function getCertificateSubField(idCertificateField){
	container.hide();
	var service = 'getCertificateSubField';
	var request = '{"service":"'+service+'","idCertificateField":"'+idCertificateField+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/CertificateServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				for(var i=0;i<obj.certificateSubField.length;i++){
					$('#certificateSubField').append("<option value='"+obj.certificateSubField[i].idCertSubfield+"'>"+obj.certificateSubField[i].certSubfieldName+"</option>");
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

function getCertificateList(){
	container.hide();
	var service = 'getCertificateList';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/CertificateServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				var html = "<div align='right'></div>";
				html += "<ol>";
				countCertificate = obj.certificateListDetail.length;
				for(var i=0;i<obj.certificateListDetail.length;i++){
					var certName = "";
					if(obj.certificateListDetail[i][1]==null){
						certName = obj.certificateListDetail[i][3];
					}
					else{
						certName = obj.certificateListDetail[i][1];
						if(obj.certificateListDetail[i][2]==null){
							certName+= "/"+obj.certificateListDetail[i][3];
						}
						else{
							certName+= "/"+obj.certificateListDetail[i][2];
						}
					}
					
					html +="<li>"+certName;
					html +="<br>"+obj.certificateListDetail[i][4];
					html +="<br>"+obj.certificateListDetail[i][5];
					html +="<br><a href='javascript:void(0)' onclick='editCertificate("+obj.certificateListDetail[i][0]+")'><fmt:message key='GLOBAL_EDIT'/></a>&nbsp;&nbsp;<a data-toggle='modal' href='javascript:void(0)'onclick='dialogDelCertificate("+obj.certificateListDetail[i][0]+")'> <fmt:message key='GLOBAL_DELETE'/></a>";
					html +="</li>";
				}
				html+="</ol>";
				$("#certificateListDiv").html(html);
				$("#certificateListDiv").show();
				if(countCertificate>0){
					hideCertificateForm();
				}
				else{
					showCertificateForm();
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

function dialogDelCertificate(idCertificate){
$('#md1').data('id',idCertificate).modal('show');
}
function btnDelCertificate(){
	var id = $('#md1').data('id');
	delCertificate(id);
	$('#md1').modal("hide");	
}
function delCertificate(idCertificate){
		container.hide();
		var service = 'deleteCertificate';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idCertificate":"'+idCertificate+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/CertificateServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					//check deleted when edit
					if(idCertificate==$('#idCertificate').val()){
						window.location.href = "?view=certificate&idResume="+idResume;
					}
					else{
						getCertificateList();
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
      	
/*
function deleteCertificate(idCertificate){
	if(confirm("<fmt:message key='CONFIRM_DELETE'/>")){
		container.hide();
		var service = 'deleteCertificate';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idCertificate":"'+idCertificate+'"}';
		$.ajax({
			type: "POST",
	  		url: '/CertificateServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					//check deleted when edit
					if(idCertificate==$('#idCertificate').val()){
						window.location.href = "?view=certificate&idResume="+idResume;
					}
					else{
						getCertificateList();
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
}
*/



function editCertificate(idCertificate){
	window.location.href = "?view=certificate&idResume="+idResume+"&idCertificate="+idCertificate;
}

function showCertificateForm(){
	$('.toggleDisplay').show();
	$('html, body').animate({scrollTop: $('#headToggleDisplay').offset().top}, 'slow');
	$('#buttonToggle').html('<input type="submit" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_NEXT"/>">');
	showForm = true;
}

function hideCertificateForm(){
	$('.toggleDisplay').hide();
	$('#buttonToggle').html('<input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showCertificateForm()">');
}

function clearForm(){
	$("#idCertificate").val(0);
	$("#awardName").val("");
	$("#awardInstitution").val("");
	$("#awardDate").val("");
	$("#awardDetail").val("");
}

function getEditData(){
	if($('#idCertificate').val()>0){
		container.hide();
		var service = 'getEditCertificate';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idCertificate":"'+$('#idCertificate').val()+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/CertificateServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
				
					//$('#certificateSubField option').remove();
					//$('#certificateSubField').append("<option value='' selected><fmt:message key='GLOBAL_SELECT'/></option>");
					getCertificateSubField(parseInt(parseInt(obj.certificateListDetail[0][7])));
					$('#certificateSubField').append("<option value='-1'><fmt:message key='GLOBAL_OTHER'/></option>");
					$('#certificateField option[value="'+parseInt(obj.certificateListDetail[0][7])+'"]').prop('selected', true);
					$('#certificateSubFieldDiv option[value="'+parseInt(obj.certificateListDetail[0][8])+'"]').prop('selected', true);
					
					
					if(parseInt(parseInt(obj.certificateListDetail[0][7]))==-1 || parseInt(parseInt(obj.certificateListDetail[0][8]))==-1)
					{
						$('.certificateOther').show();
						$('#certificateName').prop("disabled",false);
						$('#certificateName').val(obj.certificateListDetail[0][3]);
					}
					$('#certificateDate').val(obj.certificateListDetail[0][9]);
					$('#certificateIssue').val(obj.certificateListDetail[0][5]);
					$('#certificateDetail').val(obj.certificateListDetail[0][6]);
					showCertificateForm();
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
<form id="certificateForm" name="certificateForm" method="post" class="form-horizontal">
	<input name="service" id="service" type="hidden" value="saveCertificate">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" id="idCertificate" name="idCertificate" value="<c:out value="${idCertificate}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
	<div class="section_header"><fmt:message key="CERT_HEADER"/></div>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<br>
	
	<div id="certificateListDiv" style="display:none"></div>
	
	<div class="toggleDisplay" id="headToggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="certificateField"><b class="star">*</b><fmt:message key="CERT_FIELD"/></label></div>
			<select name="certificateField" id="certificateField" class="form-control required">
				<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<c:forEach var="certField" items="${certField}">
						<option value="<c:out value='${certField.idCertField}'/>">
							<c:out value='${certField.certFieldName}'/>
						</option>
					</c:forEach>
				<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
			</select>
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<label for="certificateSubField"><b class="star">*</b><fmt:message key="CERT_NAME"/></label>
			<div id="certificateSubFieldDiv">
				<select name="certificateSubField" id="certificateSubField" class="form-control required" >
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
				</select>
			</div>
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<div class="certificateOther" align="left" style="margin-right:10px;display:none">
				<div class="caption"><label for="certificateName"><b class="star">*</b><fmt:message key="CERT_NAME_OTHER"/></label></div>
				<input disabled class="form-control required" type='text' name="certificateName" id="certificateName" maxlength="200">
			</div>
		</div>
	</div>
	<!--
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="certificateDate"><b class="star">*</b><fmt:message key="CERT_DATE"/></label></div>
			<input  type="month" class="form-control required" name="certificateDate" id="certificateDate" maxlength="200" style="width: 280px">
		</div>
	</div>
	 -->
	 
	 <!-- trainingDate -->
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="certificateDate"><b class="star">*</b><fmt:message key="CERT_DATE"/></label></div></div>
	</div>
	<div class="toggleDisplay" style="display:none"> 
		<fmt:formatDate var="startMonth" value="${cert.cerDate}" pattern="M"/>
		<fmt:formatDate var="startYear" value="${cert.cerDate}" pattern="yyyy"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="form-group">
					<select name="startMonth" id="startMonth" class="form-control required" title="<fmt:message key="GLOBAL_MONTH_REQUIRED"/>">
						<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
						<c:forEach var="i" begin="1" end="12">
							<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
							<c:choose>
								<c:when test="${startMonth eq i}">
									<option value="<c:out value='${i}'/>" selected><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4" style="margin-left:10px;">
				<div class="form-group">
					<select  name="startYear" id="startYear" class="form-control required" title="<fmt:message key="GLOBAL_YEAR_REQUIRED"/>">
						<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
						<c:forEach var="i" begin="0" end="120">
							<c:choose>
								<c:when test="${resume.locale eq 'th_TH'}">
									<option value="<c:out value='${curYear-i}'/>" <c:if test="${startYear eq (curYear-i+543)}">selected</c:if>>
										<c:out value="${curYear-i+543}"/>
									</option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value='${curYear-i}'/>" <c:if test="${startYear eq (curYear-i)}">selected</c:if>>
									<c:out value="${curYear-i}"/>
									</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</div>
	<!-- /trainingDate -->
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<div class="caption"><label for="certificateIssue"><b class="star">*</b><fmt:message key="CERT_ISSUE"/></label></div>
			<input type="text" class="form-control required" name="certificateIssue" id="certificateIssue" maxlength="500">
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="form-group">
			<label for="certificateDetail"><fmt:message key="CERT_DETAIL"/></label>
			<textarea class="form-control" rows='3' name="certificateDetail" id="certificateDetail" maxlength="500"></textarea>
		</div>
	</div>
	
	<div class="form-group">
		<div class="row">
	 		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	 			<input type="button" id="buttonBack" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_CANCEL"/>">
	 		</div>
	 		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
	 			<span id="buttonToggle"><input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showCertificateForm()"></span>
	 		</div>
	 	</div>
 	</div>
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
			        <button type="button" class="btn btn-primary" onclick="btnDelCertificate()"><fmt:message key="GLOBAL_CONFIRM"/></button>
      			</div>
    		</div><!-- /.modal-content -->
  		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
</form>