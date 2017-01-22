<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.Activity"%>
<%@page import="com.topgun.resume.ActivityManager"%>
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
	int idActivity = Util.StrToInt(request.getParameter("idActivity"));
	Activity activity = new ActivityManager().get(idJsk,idResume,idActivity);
	
	request.setAttribute("idResume",idResume);
	request.setAttribute("idLanguage",idLanguage);
	request.setAttribute("idActivity",idActivity);
	request.setAttribute("activity",activity);
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<script>
var container = null;
var idResume = '<c:out value="${idResume}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";
var idLanguage = '<c:out value="${idLanguage}"/>';
var countActivity = 0;
$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	getActivityList();
	getEditData();
	$('#buttonBack').click(function(){
		window.location.href = "?view=resumeInfo&idResume="+idResume;
	});
	//---------------------------- validation ---------------------------
	$('#activityForm').validate({
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
 			activityClub:{
 				required:true
 			},
 			activityPosition:{
 				required:true
 			},
 			activityStart:{
 				required:true
 			},
 			activityFinish:{
 				required:true
 			}
		},			  
		messages:{
			activityClub:{
				required:'<fmt:message key="ACTIVITY_CLUB_REQUIRED"/>'
			},
			activityPosition:{
				required:'<fmt:message key="ACTIVITY_POSITION_REQUIRED"/>'
			},
			startDay:{
				required:'<fmt:message key="ACTIVITY_START_REQUIRED"/>'
			},
			startMonth:{
				required:'<fmt:message key="ACTIVITY_START_REQUIRED"/>'
			},
			startYear:{
				required:'<fmt:message key="ACTIVITY_START_REQUIRED"/>'
			},
			finishDay:{
				required:'<fmt:message key="ACTIVITY_FINISH_REQUIRED"/>'
			},
			finishMonth:{
				required:'<fmt:message key="ACTIVITY_FINISH_REQUIRED"/>'
			},
			finishYear:{
				required:'<fmt:message key="ACTIVITY_FINISH_REQUIRED"/>'
			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/ActivityServ',
       			data: $('#activityForm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1){
       					window.location.href = "?view=activities&idResume="+idResume;
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

function getActivityList(){
	container.hide();
	var service = 'getActivityList';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","idLanguage":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/ActivityServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				var html = "<div align='right' style='width:280px;'></div>";
				html += "<ol>";
				countActivity = obj.activityListDetail.length;
				for(var i=0;i<obj.activityListDetail.length;i++){
					html +="<li>"+obj.activityListDetail[i][1]+"&nbsp;("+obj.activityListDetail[i][2]+")";
					html +="<br>"+obj.activityListDetail[i][3]+"&nbsp;-&nbsp;"+obj.activityListDetail[i][4];
					if(obj.activityListDetail[i][5]!=""){
						html +="<br>"+obj.activityListDetail[i][5];
					}
					html +="<br><a href='javascript:void(0)' onclick='editActivity("+obj.activityListDetail[i][0]+")'><fmt:message key='GLOBAL_EDIT'/></a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='dialogDelActivity("+obj.activityListDetail[i][0]+")'><fmt:message key='GLOBAL_DELETE'/></a>";   
					html +="</li>";
				}
				html+="</ol>";
				$("#activityListDiv").html(html);
				$("#activityListDiv").show();
				if(countActivity>0){
					hideActivityForm();
				}
				else{
					showActivityForm();
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

function dialogDelActivity(idActivity){
$('#md1').data('id',idActivity).modal('show');
}
function btnDelActivity(){
	var id = $('#md1').data('id');
	deleteActivity(id);
	$('#md1').modal("hide");	
}

function deleteActivity(idActivity){
		container.hide();
		var service = 'deleteActivity';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idActivity":"'+idActivity+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/ActivityServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					//check deleted when edit
					if(idActivity==$('#idActivity').val()){
						window.location.href = "?view=activities&idResume="+idResume;
					}
					else{
						getActivityList();
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


function editActivity(idActivity){
	window.location.href = "?view=activities&idResume="+idResume+"&idActivity="+idActivity;
}

function showActivityForm(){
	$('.toggleDisplay').show();
	$('html, body').animate({scrollTop: $('#headToggleDisplay').offset().top}, 'slow');
	$('#buttonToggle').html('<input type="submit" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_NEXT"/>">');
	showForm = true;
}

function hideActivityForm(){
	$('.toggleDisplay').hide();
	$('#buttonToggle').html('<input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showActivityForm()">');
}

function clearForm(){
	$("#idActivity").val(0);
	$("#activityClub").val("");
	$("#activityPosition").val("");
	$("#activityStart").val("");
	$("#activityFinish").val("");
	$("#activityDetail").val("");
}

function getEditData(){
	if($('#idActivity').val()>0){
		container.hide();
		var service = 'getEditActivity';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","idActivity":"'+$('#idActivity').val()+'","idLanguage":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/ActivityServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					$("#activityClub").val(obj.activityListDetail[0][1]);
					$("#activityPosition").val(obj.activityListDetail[0][2]);
					$("#activityStart").val(obj.activityListDetail[0][6]);
					$("#activityFinish").val(obj.activityListDetail[0][7]);
					$("#activityDetail").val(obj.activityListDetail[0][5]);
					showActivityForm();
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
<form id="activityForm" name="activityForm" method="post" class="form-horizontal">

	<input name="service" id="service" type="hidden" value="saveActivity">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input type="hidden" id="idActivity" name="idActivity" value="<c:out value="${idActivity}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">

	<div class="section_header"><fmt:message key="ACTIVITY_HEADER"/></div>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	
	<div><fmt:message key="ACTIVITY_HEADER_DETAIL"/></div>
	<br>
	<div id="activityListDiv" style="display:none"></div>
	
	<div class="toggleDisplay" id="headToggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label class="control-label" for="activityClub"><b class="star">*</b><fmt:message key="ACTIVITY_CLUB"/></label></div></div>
		<div class="form-group">
			<input type="text" class="form-control required" name="activityClub" id="activityClub" maxlength="200">
		</div>
	</div>
	
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label class="control-label" for="activityPosition"><b class="star">*</b><fmt:message key="ACTIVITY_POSITION"/></label></div></div>
		<div class="form-group">
			<input type="text" class="form-control required" name="activityPosition" id="activityPosition" maxlength="200" >
		</div>
	</div>
	
	<!-- startDate -->
	<div class="toggleDisplay" style="display:none">
		<div class="row"><div class="caption"><label class="control-label" for="activityStart"><b class="star">*</b><fmt:message key="ACTIVITY_START"/></label></div></div>
	</div>
	<div class="toggleDisplay" style="display:none">
		<fmt:formatDate var="startDay" value="${activity.startDate}" pattern="d"/>
		<fmt:formatDate var="startMonth" value="${activity.startDate}" pattern="M"/>
		<fmt:formatDate var="startYear" value="${activity.startDate}" pattern="yyyy"/>
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
		<div class="row"><div class="caption"><label class="control-label" for="activityFinish"><b class="star">*</b><fmt:message key="ACTIVITY_FINISH"/></label></div></div>
	</div>
	<div class="toggleDisplay" style="display:none">
		<fmt:formatDate var="finishDay" value="${activity.endDate}" pattern="d"/>
		<fmt:formatDate var="finishMonth" value="${activity.endDate}" pattern="M"/>
		<fmt:formatDate var="finishYear" value="${activity.endDate}" pattern="yyyy"/>
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
		<div class="row"><div class="caption"><label class="control-label" for="activityDetail"><fmt:message key="ACTIVITY_DETAIL"/></label></div></div>
		<div class="form-group">
			<input type="text" class="form-control" name="activityDetail" id="activityDetail" maxlength="500" >
		</div>
	</div>
	
	<div class="form-group">
		<div class="row">
	 		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	 			<input type="button" id="buttonBack" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_CANCEL"/>">
	 		</div>
	 		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
	 			<span id="buttonToggle"><input type="button" id="buttonNext" class="btn btn-lg btn-success" value="<fmt:message key="GLOBAL_ADD"/>" onClick="showActivityForm()"></span>
	 		</div>
	 	</div>
 	</div>
</form>
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
			        <button type="button" class="btn btn-primary" onclick="btnDelActivity()"><fmt:message key="GLOBAL_CONFIRM"/></button>
      			</div>
    		</div><!-- /.modal-content -->
  		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<!-- /end modal skip delete -->