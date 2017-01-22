<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.Award"%>
<%@page import="com.topgun.resume.AwardManager"%>
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
	int idAward = Util.StrToInt(request.getParameter("idAward"));
	Award award = new AwardManager().get(idJsk,idResume,idAward);
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	request.setAttribute("idAward",idAward);
	request.setAttribute("award",award);
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<script>
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";
var idLanguage = '<c:out value="${idLanguage}"/>';
var countAward = 0;

function getAwardList(){
	container.hide();
	var service = 'getAwardList';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/AwardServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				var html = "<div align='right'></div>";
				html += "<ol>";
				countAward = obj.awardListDetail.length;
				for(var i=0;i<obj.awardListDetail.length;i++){
					html +="<li>"+obj.awardListDetail[i][1]+"&nbsp;("+obj.awardListDetail[i][2]+")";
					html +="<br>"+obj.awardListDetail[i][3];
					html +="<br>"+obj.awardListDetail[i][4];
					html +="&nbsp;<br><a href='javascript:void(0)' onclick='editAward("+obj.awardListDetail[i][0]+")'><fmt:message key='GLOBAL_EDIT'/></a>&nbsp;&nbsp;<a data-toggle='modal' href='#' onclick='dialogDelAward("+obj.awardListDetail[i][0]+")'><fmt:message key='GLOBAL_DELETE'/></a>";
					html +="</li>";
				}
				html+="</ol>";
				$("#awardListDiv").html(html);
				$("#awardListDiv").show();
				if(countAward>0){
					hideAwardForm();
				}
				else{
					showAwardForm();
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

function dialogDelAward(idAward){
	$('#md1').data('id',idAward).modal('show');
}
function btnDelAward(){
	var id = $('#md1').data('id');
	deleteAward(id);
	$('#md1').modal("hide");	
}

$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	getAwardList();
	getEditData();
	$('#buttonBack').click(function(){
		window.location.href = "?view=resumeInfo&idResume="+idResume;
	});
	//---------------------------- validation ---------------------------
	$('#awardForm').validate({
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
 			awardName:{
 				required:true
 			},
 			awardInstitution:{
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
			awardName:{
				required:'<fmt:message key="AWARD_NAME_REQUIRED"/>'
			},
			awardInstitution:{
				required:'<fmt:message key="AWARD_INSTITUTION_REQUIRED"/>'
			},
			startMonth:{
				required:'<fmt:message key="AWARD_DATE_REQUIRED"/>'
			},
			startYear:{
				required:'<fmt:message key="AWARD_DATE_REQUIRED"/>'
			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/AwardServ',
       			data: $('#awardForm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1){
       					window.location.href = "?view=awards&idResume="+idResume;
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

   	
function deleteAward(idAward){

		container.hide();
		var service = 'deleteAward';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idAward":"'+idAward+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/AwardServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					//check deleted when edit
					if(idAward==$('#idAward').val()){
						window.location.href = "?view=awards&idResume="+idResume;
					}
					else{
						getAwardList();
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

function editAward(idAward){
	window.location.href = "?view=awards&idResume="+idResume+"&idAward="+idAward;
}

function showAwardForm(){
	$('.toggleDisplay').show();
	$('html, body').animate({scrollTop: $('#headToggleDisplay').offset().top}, 'slow');
	$('#buttonToggle').html('<input type="submit" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_NEXT"/>">');
	showForm = true;
}

function hideAwardForm(){
	$('.toggleDisplay').hide();
	$('#buttonToggle').html('<input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showAwardForm()">');
}

function clearForm(){
	$("#idAward").val(0);
	$("#awardName").val("");
	$("#awardInstitution").val("");
	$("#awardDate").val("");
	$("#awardDetail").val("");
}

function getEditData(){
	if($('#idAward').val()>0){
		container.hide();
		var service = 'getEditAward';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idAward":"'+$('#idAward').val()+'"}';
		$.ajax({
			type: "POST",
	  		url: '/AwardServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					$("#awardName").val(obj.awardListDetail[0][1]);
					$("#awardInstitution").val(obj.awardListDetail[0][2]);
					//$("#awardDate").val(obj.awardListDetail[0][5]);
					$("#awardDetail").val(obj.awardListDetail[0][4]);
					showAwardForm();
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
<link type="text/css" href="/css/jquery.autocomplete.css" rel="stylesheet" />

<form id="awardForm" name="awardForm" method="post" class="form-horizontal">
	<input name="service" id="service" type="hidden" value="saveAward">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" id="idAward" name="idAward" value="<c:out value="${idAward}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
	
	<div class="section_header"><fmt:message key="AWARD_HEADER"/></div>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<br>
	
	<div id="awardListDiv" style="display:none"></div>
	
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
				        <button type="button" class="btn btn-primary" onclick="btnDelAward()"><fmt:message key="GLOBAL_CONFIRM"/></button>
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	
	<div class="toggleDisplay" id="headToggleDisplay" style="display:none">
		<div class="row"><label for="awardName"><b class="star">*</b><fmt:message key="AWARD_NAME"/></label></div>
		<div class="form-group">
			<input type="text" class="form-control required" name="awardName" id="awardName" maxlength="200" >
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="awardInstitution"><b class="star">*</b><fmt:message key="AWARD_INSTITUTION"/></label></div></div>
		<div class="form-group">
			<input type="text" class="form-control required" name="awardInstitution" id="awardInstitution" maxlength="200" >
		</div>
	</div>

	<!-- awardDate -->
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="awardDate"><b class="star">*</b><fmt:message key="AWARD_DATE"/></label></div></div>
	</div>
	<div class="toggleDisplay" style="display:none"> 
		<fmt:formatDate var="startMonth" value="${award.awardDate}" pattern="M"/>
		<fmt:formatDate var="startYear" value="${award.awardDate}" pattern="yyyy"/>
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
	<!-- /awardDate -->
	
	<div class="toggleDisplay" style="display:none">
		<div class="caption"><label for="awardDetail"><fmt:message key="AWARD_DETAIL"/></label></div>
		<div class="form-group">
			<input type="text" name="awardDetail" id="awardDetail" class="form-control" maxlength="500" >
		</div>
	</div>
	
	<div class="form-group">
		<div class="row">
	 		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	 			<input type="button" id="buttonBack" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_CANCEL"/>">
	 		</div>
	 		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
	 			<span id="buttonToggle"><input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showAwardForm()"></span>
	 		</div>
	 	</div>
 	</div>
</form>



