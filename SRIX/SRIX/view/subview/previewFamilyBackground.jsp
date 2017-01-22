<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.CertFieldLanguage"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.FamilyManager"%>
<%@page import="com.topgun.resume.Family"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${resume.locale}"/>
<%
	int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.StrToInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume != null)
	{
		int idLanguage=resume.getIdLanguage();
		int idCountry=resume.getIdCountry();
		int idFamily = Util.StrToInt(request.getParameter("idFamily"));
		int have=0;
		Family f_family=null;
		Family m_family=null;
		List<Family> siblingList=null;
		List<Family>childList=null;
		int idSpouse= 0;
		if(resume != null)
		{
			f_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 1); // dad
			m_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 2); // mom
			siblingList=new FamilyManager().getFamilySibling(resume.getIdJsk(),resume.getIdResume());// sibling
			idSpouse =new FamilyManager().getIdFamilyMarried(resume.getIdJsk(),resume.getIdResume());// id spouse 
			childList =new FamilyManager().getFamilyChild(resume.getIdJsk(),resume.getIdResume()); // child  
			if(f_family!=null || m_family!=null ||siblingList.size()>0 || idSpouse>0 || childList.size()>0)
			{
				have=1;
			}
		}
		PropertiesManager propMgr=new PropertiesManager();
		request.setAttribute("idResume",idResume);
		request.setAttribute("idLanguage",idLanguage);
		request.setAttribute("idFamily",idFamily);
		request.setAttribute("have",have);
		request.setAttribute("f_family", f_family);
		request.setAttribute("m_family", m_family);
		request.setAttribute("siblingList", siblingList);
		request.setAttribute("idSpouse", idSpouse);
		request.setAttribute("childList", childList);
		request.setAttribute("propMgr", propMgr);
		request.setAttribute("siblingSize", siblingList.size());
		request.setAttribute("childSize", childList.size());
		request.setAttribute("resume", resume);
	}
	
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
var have = '<c:out value="${have}"/>';
var confirmTxt = "<fmt:message key='CANCEL_BACK'/>?";
var idLanguage= '<c:out value="${idLanguage}"/>';
var countFamily = 0;
$(document).ready(function(){
	container = $('div.errorContainer');
	//---------------------------- load data ----------------------------
	if(have==1)
	{
		getFamilyList();
	}
	
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
	var request = '{"service":"'+service+'","idFamilyField":"'+idFamilyField+'"}';
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
					html += "<b class='caption_bold'>"+obj.familyListDetail[i][13]+"</b>";
					if(obj.familyListDetail[i][1]=="SPOUSE")
					{
						html += "<font class='answer'>";
						html += "&nbsp;:&nbsp;"+obj.familyListDetail[i][14];
						if(obj.familyListDetail[i][10]=="MARRIED")
						{
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><b><fmt:message key='FAMILY_CITIZENSHIP'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][5];
							html += "<br><b><fmt:message key='FAMILY_SIBLING_STATUS'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][15];
							if(obj.familyListDetail[i][6]=="WORKING")
							{
								html += "<br><b><fmt:message key='FAMILY_WORK_STATUS'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
								html += "<br><b><fmt:message key='FAMILY_WORK_POSITION'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
							}
							else if(obj.familyListDetail[i][6]=="RETIRED")
							{
								html += "<br><b><fmt:message key='FAMILY_WORK_LAST_COMPANY'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
								html += "<br><b><fmt:message key='FAMILY_WORK_POSITION'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
							}
							else if(obj.familyListDetail[i][6]=="LEARNING")
							{
								html += "<br><b><fmt:message key='FAMILY_EDUCATION'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
							}
							html +="<br><b><fmt:message key='FAMILY_TELEPHONE'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][9];
						}
						html += "</font>";
					}
					else
					{
						html += "<font class='answer'>";
						//if parrent show alive status and citizenship
						if(obj.familyListDetail[i][1]=="FATHER"||obj.familyListDetail[i][1]=="MOTHER"){
							html += "&nbsp;<b>(</b>"+obj.familyListDetail[i][4]+"<b>)</b>";
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><b><fmt:message key='FAMILY_CITIZENSHIP'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][5];
						}
						else if(obj.familyListDetail[i][1]=="SIBLING"){
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><b><fmt:message key='FAMILY_RELATIONSHIP'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][16];
							html += "<br><b><fmt:message key='FAMILY_AGE'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][12];
						}
						else if(obj.familyListDetail[i][1]=="CHILD"){
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
							html += "<br><b><fmt:message key='FAMILY_AGE'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][12];
						}
						else{
							html += "<br>"+obj.familyListDetail[i][2]+" "+obj.familyListDetail[i][3];
						}
						
						html += "<br><b><fmt:message key='FAMILY_SIBLING_STATUS'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][15];
						//show status work by type
						if(obj.familyListDetail[i][6]=="WORKING"){
							html += "<br><b><fmt:message key='FAMILY_WORK_STATUS'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
							html += "<br><b><fmt:message key='FAMILY_WORK_POSITION'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
						}
						else if(obj.familyListDetail[i][6]=="RETIRED"){
							html += "<br><b><fmt:message key='FAMILY_WORK_LAST_COMPANY'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
							html += "<br><b><fmt:message key='FAMILY_WORK_POSITION'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][8];
						}
						else if(obj.familyListDetail[i][6]=="LEARNING"){
							html += "<br><b><fmt:message key='FAMILY_EDUCATION'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][7];
						}
						html +="<br><b><fmt:message key='FAMILY_TELEPHONE'/></b>&nbsp;:&nbsp;"+obj.familyListDetail[i][9];
						html += "</font>";
					}
					html +="<br /><br />";
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
<br />
<c:if test="${not empty resume}">
	<c:choose>
		<c:when test="${(not empty f_family) || (not empty m_family) || (siblingSize>0) || (idSpouse>0) || (childSize>0)}">
			<div class="row">
				<div id="familyListDiv" style="display:none"></div>
				<div style="text-align:right;padding-right:20pt;padding-bottom:10pt;">
					<a href="/SRIX?view=family&sequence=0&idResume=<c:out value="${idResume}" />" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</div>	
			</div>
		</c:when>
		<c:otherwise>
			<div style="text-align:right;padding:10pt;">
					<a href="/SRIX?view=family&sequence=0&idResume=<c:out value="${idResume}" />" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
				</div>	
		</c:otherwise>
	</c:choose>
</c:if>
		
