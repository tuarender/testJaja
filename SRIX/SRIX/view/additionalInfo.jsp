<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
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
	//---------------------------- validation ---------------------------
	$('#additionalInfoForm').validate({
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
 			additionalInfo:{
 				required:true
 			}
		},			  
		messages:{
			additionalInfo:{
				required:'<fmt:message key="ADDITIONAL_INFO_REQUIRED"/>'
			}
		},
		submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/AdditionalInfoServ',
       			data: $('#additionalInfoForm').serialize(),
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
  		url: '/AdditionalInfoServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				$("#additionalInfo").val(obj.additionalInfo);
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

<form id="additionalInfoForm" name="additionalInfoForm" method="POST" class="form-horizontal">
	<input name="service" id="service" type="hidden" value="saveAdditionalInfo">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
	
	<div class="section_header"><fmt:message key="ADDITIONAL_INFO_HEADER"/></div>
	
	<div class="errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<br>
		
	<div class="caption"><label class="control-label" for="additionalInfo"><b class="star">*</b><fmt:message key="ADDITIONAL_INFO_DETAIL"/></label></div>
	<div class="form-group">
   		<textarea class="form-control required" name="additionalInfo" id="additionalInfo" rows="10" maxlength="2000"></textarea>
 	</div>
 	<br/>
 	
 	<div class="form-group">
	 	<div class="row">
	 		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	 			<button type="reset" id="buttonBack" class="btn btn-lg btn-success"><fmt:message key="GLOBAL_CANCEL"/></button>
	 		</div>
	 		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
	 			<button type="submit" id="buttonNext" class="btn btn-lg btn-success"><fmt:message key="GLOBAL_ADD"/></button>
	 		</div>
	 	</div>
 	</div>
 	
  
</form>