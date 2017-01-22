<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Training"%>
<%@page import="com.topgun.resume.TrainingManager"%>
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
	int idTraining = Util.StrToInt(request.getParameter("idTraining"));
	Training training = new TrainingManager().get(idJsk,idResume,idTraining);
	request.setAttribute("resume",resume);
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	request.setAttribute("idTraining",idTraining);
	request.setAttribute("training",training);
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<script>
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";
var idLanguage = '<c:out value="${idLanguage}"/>';
var countTraining = 0;
$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	getTrainingList();
	getEditData();
	$('#buttonBack').click(function(){
		window.location.href = "?view=resumeInfo&idResume="+idResume;
	});
	//---------------------------- validation ---------------------------
	$('#trainingForm').validate({
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
 			trainingName:{
 				required:true
 			},
 			trainingInstitution:{
 				required:true
 			},
 			trainingStart:{
 				required:true
 			},
 			trainingFinish:{
 				required:true
 			}
		},			  
		messages:{
			trainingName:{
				required:'<fmt:message key="TRAINING_NAME_REQUIRED"/>'
			},
			trainingInstitution:{
				required:'<fmt:message key="TRAINING_INSTITUTION_REQUIRED"/>'
			},
			startDay:{
				required:'<fmt:message key="TRAINING_START_DAY_REQUIRED"/>'
			},
			startMonth:{
				required:'<fmt:message key="TRAINING_START_MONTH_REQUIRED"/>'
			},
			startYear:{
				required:'<fmt:message key="TRAINING_START_YEAR_REQUIRED"/>'
			},
			finishDay:{
				required:'<fmt:message key="TRAINING_END_DAY_REQUIRED"/>'
			},
			finishMonth:{
				required:'<fmt:message key="TRAINING_END_MONTH_REQUIRED"/>'
			},
			finishYear:{
				required:'<fmt:message key="TRAINING_END_YEAR_REQUIRED"/>'
			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/TrainingServ',
       			data: $('#trainingForm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1){
       					window.location.href = "?view=training&idResume="+idResume;
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

function getTrainingList(){
	container.hide();
	var service = 'getTrainingList';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/TrainingServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				var html = "<div align='right'></div>";
				html += "<ol>";
				countTraining = obj.trainingListDetail.length;
				for(var i=0;i<obj.trainingListDetail.length;i++){
					html +="<li>"+obj.trainingListDetail[i][1]+"&nbsp;("+obj.trainingListDetail[i][2]+")";
					html +="<br><fmt:formatDate value="${training.startDate}" dateStyle="full"/>&nbsp;-&nbsp;"+obj.trainingListDetail[i][4];
					html +="<br>"+obj.trainingListDetail[i][5];
					html +="&nbsp;<br><a href='javascript:void(0)' onclick='editTraining("+obj.trainingListDetail[i][0]+")'><fmt:message key='GLOBAL_EDIT'/></a>&nbsp;&nbsp;<a data-toggle='modal' href='javascript:void(0)'onclick='dialogDelTraining("+obj.trainingListDetail[i][0]+")'> <fmt:message key='GLOBAL_DELETE'/></a>";
					html +="</li>";
				}
				html+="</ol>";
				$("#trainingListDiv").html(html);
				$("#trainingListDiv").show();
				if(countTraining>0){
					hideTrainingForm();
				}
				else{
					showTrainingForm();
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

function dialogDelTraining(idTraining){
$('#md1').data('id',idTraining).modal('show');
}
function btnDelTraining(){
	var id = $('#md1').data('id');
	deleteTraining(id);
	$('#md1').modal("hide");	
}

function deleteTraining(idTraining){
		container.hide();
		var service = 'deleteTraining';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idTraining":"'+idTraining+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/TrainingServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					//check deleted when edit
					if(idTraining==$('#idTraining').val()){
						window.location.href = "?view=training&idResume="+idResume;
					}
					else{
						getTrainingList();
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

function editTraining(idTraining){
	window.location.href = "?view=training&idResume="+idResume+"&idTraining="+idTraining;
}

function showTrainingForm(){
	$('.toggleDisplay').show();
	$('html, body').animate({scrollTop: $('#headToggleDisplay').offset().top}, 'slow');
	$('#buttonToggle').html('<input type="submit" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_NEXT"/>">');
	showForm = true;
}

function hideTrainingForm(){
	$('.toggleDisplay').hide();
	$('#buttonToggle').html('<input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showTrainingForm()">');
}

function clearForm(){
	$("#idTraining").val(0);
	$("#trainingName").val("");
	$("#trainingInstitution").val("");
	$("#trainingStart").val("");
	$("#trainingFinish").val("");
	$("#trainingDetail").val("");
}

function getEditData(){
	if($('#idTraining').val()>0){
		container.hide();
		var service = 'getEditTraining';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idTraining":"'+$('#idTraining').val()+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/TrainingServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					$("#trainingName").val(obj.trainingListDetail[0][1]);
					$("#trainingInstitution").val(obj.trainingListDetail[0][2]);
					$("#trainingDetail").val(obj.trainingListDetail[0][5]);
					showTrainingForm();
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

<form id="trainingForm" name="trainingForm" method="post" class="form-horizontal">
	<input name="service" id="service" type="hidden" value="saveTraining">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" id="idTraining" name="idTraining" value="<c:out value="${idTraining}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
			
	<div class="section_header"><fmt:message key="TRAINING_HEADER"/></div>
	<div class="suggestion"><fmt:message key="DIRECTION_TRAINING"/></div>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<br>
	
	<div id="trainingListDiv" style="display:none"></div>
	
	<div class="toggleDisplay" id="headToggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="trainingName"><b class="star">*</b><fmt:message key="TRAINING_NAME"/></label></div></div>
		<div class="form-group">
	   		<input type="text" class="form-control required" name="trainingName" id="trainingName" maxlength="200">
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="trainingInstitution"><b class="star">*</b><fmt:message key="TRAINING_INSTITUTION"/></label></div></div>
		<div class="form-group">
	   		<input type="text" class="form-control required" name="trainingInstitution" id="trainingInstitution" maxlength="200">
		</div>
	</div>
	
	<!-- startDate -->
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="strartTraining"><b class="star">*</b><fmt:message key="TRAINING_START"/></label></div></div>
	</div>
	<div class="toggleDisplay" style="display:none">
		<fmt:formatDate var="startDay" value="${training.startDate}" pattern="d"/>
		<fmt:formatDate var="startMonth" value="${training.startDate}" pattern="M"/>
		<fmt:formatDate var="startYear" value="${training.startDate}" pattern="yyyy"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="form-group">
					<select  name="startDay" id="startDay" class="form-control required" title="<fmt:message key="GLOBAL_DAY_REQUIRED"/>">
						<option value=""><fmt:message key="GLOBAL_DAY"/></option>
						<c:forEach var="i" begin="1" end="31">
							<c:choose>
								<c:when test="${startDay eq i}">
									<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
			
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
			
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
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
	<!-- /startDate -->
	
	<!-- finishDate -->
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="finishTraining"><b class="star">*</b><fmt:message key="TRAINING_FINISH"/></label></div></div>
	</div>
	<div class="toggleDisplay" style="display:none">
		<fmt:formatDate var="finishDay" value="${training.endDate}" pattern="d"/>
		<fmt:formatDate var="finishMonth" value="${training.endDate}" pattern="M"/>
		<fmt:formatDate var="finishYear" value="${training.endDate}" pattern="yyyy"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="form-group">
					<select  name="finishDay" id="finishDay" class="form-control required" title="<fmt:message key="GLOBAL_DAY_REQUIRED"/>">
						<option value=""><fmt:message key="GLOBAL_DAY"/></option>
						<c:forEach var="i" begin="1" end="31">
							<c:choose>
								<c:when test="${finishDay eq i}">
									<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="form-group">
					<select name="finishMonth" id="finishMonth" class="form-control required" title="<fmt:message key="GLOBAL_MONTH_REQUIRED"/>">
						<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
						<c:forEach var="i" begin="1" end="12">
							<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
							<c:choose>
								<c:when test="${finishMonth eq i}">
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
			
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="form-group">
					<select  name="finishYear" id="finishYear" class="form-control required" title="<fmt:message key="GLOBAL_YEAR_REQUIRED"/>">
						<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
						<c:forEach var="i" begin="0" end="120">
							<c:choose>
								<c:when test="${resume.locale eq 'th_TH'}">
									<option value="<c:out value='${curYear-i}'/>" <c:if test="${finishYear eq (curYear-i+543)}">selected</c:if>>
										<c:out value="${curYear-i+543}"/>
									</option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value='${curYear-i}'/>" <c:if test="${finishYear eq (curYear-i)}">selected</c:if>>
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
	<!-- /finishDate -->
	
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label for="trainingDetail"><fmt:message key="TRAINING_DETAIL"/></label></div></div>
		<div class="form-group">
	   		<input type="text" class="form-control" name="trainingDetail" id="trainingDetail" maxlength="200">
		</div>
	</div>
	
	<div class="form-group">
		<div class="row">
	 		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	 			<input type="button" id="buttonBack" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_CANCEL"/>">
	 		</div>
	 		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
	 			<span id="buttonToggle"><input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showTrainingForm()"></span>
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
			        <button type="button" class="btn btn-primary" onclick="btnDelTraining()"><fmt:message key="GLOBAL_CONFIRM"/></button>
      			</div>
    		</div><!-- /.modal-content -->
  		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
 </form>
 
