<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<%@page import="com.topgun.shris.masterdata.Degree"%>
<%@page import="com.topgun.shris.masterdata.Faculty"%>
<%@page import="com.topgun.shris.masterdata.Major"%>
<%@page import="com.topgun.shris.masterdata.School"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Error"%>
<%@page import="com.topgun.resume.Education"%>
<%@page import="com.topgun.resume.EducationManager"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Resume"%>
<%
	int idLanguage=Util.getInt(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry=Util.getInt(session.getAttribute("SESSION_ID_COUNTRY"));
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	int idEducation = Util.StrToInt(request.getParameter("idEducation")!=null?request.getParameter("idEducation"):"-1");
	int idSchoolExclusive = Util.getInt(session.getAttribute("SESSION_ID_SCHOOL_EXCLUSIVE"));
	String error = Util.getStr(request.getParameter("error"),"");
	Resume resume=(Resume)request.getAttribute("resume");
	ArrayList<Country> countries = (ArrayList<Country>)MasterDataManager.getAllCountry(resume.getIdLanguage());
	ArrayList<Degree> degrees = (ArrayList<Degree>)MasterDataManager.getAllDegree(resume.getIdLanguage());
	Education edu = new EducationManager().get(resume.getIdJsk(),resume.getIdResume(),idEducation);
	School school = null;
	
	if (edu!=null){	
		idCountry = edu.getIdCountry();
		if(edu.getIdState()>0){
			school = MasterDataManager.getSchool(edu.getIdCountry(),edu.getIdState(),edu.getIdSchool(),idLanguage);
		}
		else{
			school = MasterDataManager.getSchool(edu.getIdCountry(),0,edu.getIdSchool(),idLanguage);
		}
	}else if(idSchoolExclusive>0){
		school = MasterDataManager.getSchool(idCountry,0,idSchoolExclusive,idLanguage);
	}
	request.setAttribute("error",error);
	
	request.setAttribute("idCountry",idCountry);
	request.setAttribute("idLanguage",idLanguage);
	
	request.setAttribute("idResume",resume.getIdResume());
	request.setAttribute("idEducation",idEducation);
	request.setAttribute("education",edu);
	request.setAttribute("resume",resume);
	request.setAttribute("idSchoolExclusive",idSchoolExclusive);
	request.setAttribute("countries",countries);
	request.setAttribute("degrees",degrees);
	request.setAttribute("sequence",sequence);
	request.setAttribute("school",school);
	
%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<div class="section_header alignCenter">
   <h3><fmt:message key="SECTION_EDUCATION"/></h3>
</div>
<div class="errorContainer" style="display:none">
	<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
</div>
<div id="educationListDiv"></div>
<div class="row text-right">
	<a id="eduToggle" href='javascript:void(0)'><span id="eduToggleText"><fmt:message key='GLOBAL_ADD'/></span></a>
</div>
<form id="educationForm" name="educationForm" class="form-horizontal">
<div id="eduFormDiv" style="display:none">
	<input type="hidden" name="service" value="addEducation">
	<input type="hidden" id="idResume" name="idResume" value="<c:out value="${idResume}"/>">
	<input name="idEducation" id="idEducation" type="hidden" value="<c:out value='${idEducation}'/>">
	<input name="idLanguage" id="idLanguage" type="hidden" value="<c:out value='${idLanguage}'/>">
	<div id="divHightestEdu" class="row">
		<b><fmt:message key='HIGHTEST_EDUCATION'/></b>
	</div>
	<div class="form-group">
		<font color="red">*</font><label for="country"><fmt:message key='EDU_COUNTRY'/></label>
		<select class="form-control" name="country" id="country" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'ประเทศ่'});">
			<c:forEach var="country" items="${countries}">
				<option value="<c:out value='${country.idCountry}'/>"
				<c:choose>
					<c:when test='${country.idCountry==idCountry}'>
						selected
					</c:when>
				</c:choose>
				><c:out value='${country.countryName}'/></option>
			</c:forEach>
		</select>
	</div>
	<div id="stateGroup" class="form-group" style="display:none">
		<font color="red">*</font><label for=""><fmt:message key='EDU_STATE'/></label>
		<select name="state" id="state" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'รัฐ'});">
			<option value=""><fmt:message key='EDU_STATE'/></option>
		</select>
	</div>
	<div class="form-group">
		<font color="red">*</font><label for="school"><fmt:message key='EDU_UNIVERSITY'/></label>
		<input type="text" name="school" id="school" class="form-control typeahead" autocomplete="off" placeholder="<fmt:message key='EDU_UNIVERSITY'/>" value="<c:choose><c:when test='${education.idSchool == -1}'><c:out value="${education.otherSchool}"/></c:when><c:otherwise><c:out value="${school.schoolName}"/></c:otherwise></c:choose>" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'TypeOn', eventLabel: 'สถาบัน'});"/>
		<input type="text" name="idSchool" id="idSchool" class="form-control" value="<c:choose><c:when test='${education.idSchool != -1&&education.idSchool!=0 && idSchoolExclusive ==-1}'><c:out value="${education.idSchool}"/></c:when><c:when test='${idSchoolExclusive>0}'><c:out value="${idSchoolExclusive}"/></c:when><c:otherwise>-1</c:otherwise></c:choose>">
	</div>
	<div class="form-group">
		<font color="red">*</font><label for="degree"><fmt:message key='EDU_DEGREE'/></label>
		<select class="form-control" name="degree" id="degree" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'ระดับการศึกษา'});">
			<option value=""><fmt:message key="EDU_DEGREE"/></option>
			<c:forEach var="degree" items="${degrees}">
				<option value="<c:out value='${degree.idDegree}'/>"
					<c:choose>
						<c:when test='${degree.idDegree == education.idDegree}'>
							selected
						</c:when>
					</c:choose>
					><c:out value='${degree.degreeName}'/>
				</option>
			</c:forEach>
    	</select>
	</div>
	<div class="form-group" style="margin-bottom:5px">
		<font color="red">*</font><label for="idFaculty"><fmt:message key='EDU_FACULTY'/></label>
		<select class="form-control" id="idFaculty" name="idFaculty" disabled onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'คณะ'});">
			<option value=""><fmt:message key="EDU_FACULTY"/></option>
			<option value="-1"><fmt:message key="EDU_OTHER"/></option>
		</select>
	</div>
	<div class="form-group">
		<input class="form-control" id="otherFaculty" name="faculty" type="text" maxLength="300" disabled style="display:none" placeholder="<fmt:message key='EDU_OTHER_FACULTY'/>">
	</div>
	<div class="form-group" style="margin-bottom:5px">
		<font color="red">*</font><label for="idMajor"><fmt:message key='EDU_MAJOR'/></label>
		<select class="form-control" id="idMajor" name="idMajor" disabled onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'สาขา'});">
			<option value=""><fmt:message key="EDU_MAJOR"/></option>
			<option value="-1"><fmt:message key="EDU_OTHER"/></option>
		</select>
	</div>
	<div class="form-group">
		<input class="form-control" id="otherMajor" name="major" type="text" maxLength="300" disabled style="display:none" placeholder="<fmt:message key='EDU_OTHER_MAJOR'/>">
	</div>
	<!-- start educate -->
	<div class="row">
		<fmt:formatDate var="startDate" value="${education.startDate}" pattern="d"/>
		<fmt:formatDate var="startMonth" value="${education.startDate}" pattern="M"/>
		<fmt:formatDate var="startYear" value="${education.startDate}" pattern="yyyy"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()+6%></c:set>
		<div class="col-sm-6">
			<div class="form-group">
				<label for="startMonth"><fmt:message key="EDU_TIME_EDUCATE"/></label>
				<select class="form-control" name="startMonth" id="startMonth" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'เดือนที่เริ่มศึกษา'});">
					<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
					<fmt:parseDate value='6' var='parsedDateOften' pattern='M' />
					<option value="6"><fmt:formatDate value="${parsedDateOften}" pattern="MMMM" /></option>
					<option disabled>----------</option>
					<c:forEach var="i" begin="1" end="12">
						<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
						<c:choose>
							<c:when test="${startMonth eq i}">
								<c:choose>
									<c:when test="${startDate eq 1}">
										<option value="<c:out value='${i}'/>" selected><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
									</c:when>					
									<c:otherwise>
										<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
									</c:otherwise>				
								</c:choose>
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-sm-1"></div>
		<div class="col-sm-5">
			<div class="form-group">
				<label for="startYear" class="hidden-xs">&nbsp;</label>
				<select class="form-control" name="startYear" id="startYear" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'ปีที่เริ่มศึกษา'});">
					<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
					<c:forEach var="i" begin="10" end="14">
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
					<option disabled>----------</option>
					<c:forEach var="i" begin="0" end="120">
						<c:choose>
							<c:when test="${resume.locale eq 'th_TH'}">
								<c:choose>
									<c:when test="${startDate eq 1 or startDate eq 2}">
										<option value="<c:out value='${curYear-i}'/>" <c:if test="${startYear eq (curYear-i+543)}">selected</c:if>>
											<c:out value="${curYear-i+543}"/>
										</option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value='${curYear-i}'/>" >
											<c:out value="${curYear-i+543}"/>
										</option>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${startDate eq 1 or startDate eq 2}">
										<option value="<c:out value='${curYear-i}'/>" <c:if test="${startYear eq (curYear-i)}">selected</c:if>>
										<c:out value="${curYear-i}"/>
										</option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value='${curYear-i}'/>" >
										<c:out value="${curYear-i}"/>
										</option>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="row"><fmt:message key="EDUCATION_REMARK_GRADE_REGISTER_REMEMBER"/></div>
	<!-- finish educate -->
	<div class="row">
		<fmt:formatDate var="finishMonth" value="${education.finishDate}" pattern="M"/>
		<fmt:formatDate var="finishYear" value="${education.finishDate}" pattern="yyyy"/>
		<fmt:formatDate var="finishDate" value="${education.finishDate}" pattern="d"/>
		<div class="col-sm-6">
			<div class="form-group">
				<label for="finishMonth"><fmt:message key="EDU_TO"/></label>
				<select class="form-control" name="finishMonth" id="finishMonth" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'ศึกษาถึงเดือน'});">
					<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
					<fmt:parseDate value='3' var='parsedDateOften' pattern='M' />
					<option value="3"><fmt:formatDate value="${parsedDateOften}" pattern="MMMM" /></option>
					<option disabled>----------</option>
					<c:forEach var="i" begin="1" end="12">
						<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
						<c:choose>
							<c:when test="${finishMonth eq i}">
								<c:choose>
									<c:when test="${finishDate eq 1}">
										<option value="<c:out value='${i}'/>" selected><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
									</c:otherwise>
								</c:choose>
								
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-sm-1">
			<label for="finishMonth" class="hidden-xs">&nbsp;</label>
			<span class="hidden-xs hidden-sm " style="margin-left:10px;"><font color="red">*</font></span>
		</div>
		<div class="col-sm-5">
			<div class="form-group">
				 <label  class="hidden-xs hidden-sm">&nbsp;</label>
				 <label  class="hidden-lg hidden-md"><font color="red">*</font></label>
				 <select class="form-control" name="finishYear" id="finishYear" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'ศึกษาถึงปี'});">
					<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
					<c:forEach var="i" begin="6" end="10">
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
					<option disabled>----------</option>
					<c:forEach var="i" begin="0" end="120">
						<c:choose>
							<c:when test="${resume.locale eq 'th_TH'}">
								<!-- 
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${finishYear eq (curYear-i+543)}">selected</c:if>>
									<c:out value="${curYear-i+543}"/>
								</option>
								-->
								<c:choose>
									<c:when test="${finishDate eq 1 or finishDate eq 2}">
										<option value="<c:out value='${curYear-i}'/>" <c:if test="${finishYear eq (curYear-i+543)}">selected</c:if>>
											<c:out value="${curYear-i+543}"/>
										</option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value='${curYear-i}'/>" >
											<c:out value="${curYear-i+543}"/>
										</option>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<!--  
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${finishYear eq (curYear-i)}">selected</c:if>>
									<c:out value="${curYear-i}"/>
								</option> 
								-->
								
								<c:choose>
									<c:when test="${finishDate eq 1 or finishDate eq 2}">
										<option value="<c:out value='${curYear-i}'/>" <c:if test="${finishYear eq (curYear-i)}">selected</c:if>>
										<c:out value="${curYear-i}"/>
										</option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value='${curYear-i}'/>" >
										<c:out value="${curYear-i}"/>
										</option>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="row form-group"><fmt:message key="EDUCATION_CAUTION_END_DATE_REGISTER"/></div>
	<!-- End finish date -->
	
	<div class="row">
		<div class="col-xs-12 col-sm-7">
			<div class="form-group">
				<font color="red">*</font><label style="padding-right:1pt;"><fmt:message key='EDU_GRADE'/></label>
				<input class="form-control" type="text" id="grade" name="grade" style="padding:0px 0px 0px 5px;max-width:60pt;margin-right:3pt;display:inline !important"  placeholder="<fmt:message key='EDU_GRADE'/>" value="<c:if test='${education.gpa != 0.0}'><c:out value='${education.gpa}'/></c:if>" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'TypeOn', eventLabel: 'คะแนนเฉลี่ย'});">
			</div>
		</div>
	   	<div class="col-xs-12 col-sm-5">
			<div class="form-group">
				<font style="padding-right:1pt;"><fmt:message key='EDU_UNIT'/></font>
				<select class="form-control" name="unit" id="unit" style="max-width:50pt;display:inline !important" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'คะแนนเต็ม'});">
	   				<option value="/4" <c:if test='${education.unit eq "/4"||education.unit == ""}'>selected</c:if>>4</option>
	   				<option value="/5" <c:if test='${education.unit eq "/5"}'>selected</c:if>>5</option>
	   				<option value="/7" <c:if test='${education.unit eq "/7"}'>selected</c:if>>7</option>
	   				<option value="%" <c:if test='${education.unit eq "%"}'>selected</c:if>>%</option>
	   				<option value="-1" <c:if test='${education.unit eq "-1"}'>selected</c:if>><fmt:message key="EDU_OTHER"/></option>
	   			</select>
			</div>
		</div>
	</div>
	<div class="form-group" id="unitOtherGroup" style="display:none">
		<div class="row">
			<div class="col-sm-6" style="text-align:right">
				<label for="unitOther"><fmt:message key="GLOBAL_REQUIRE"/></label>
			</div>
			<div class="col-sm-6">
				<input disabled type="text" name="unitOther" id="unitOther" maxlength="300" value="" class="form-control" placeholder="<fmt:message key="EDU_UNIT"/>">
			</div>
		</div>
	</div>
	<div class="row form-group"><fmt:message key="EDUCATION_REMARK_GRADE_REGISTER_REMEMBER_NEXT"/></div>
</div>
<div class="form-group alignCenter">
	<button type="submit" id="submit" class="btn btn-lg btn-success" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"><fmt:message key="GLOBAL_NEXT"/></button>
</div>
</form>
<script src="/js/bootstrap3-typeahead.min.js"></script>
<script src="/js/jsonData/faculty/facultyJson_<c:out value="${resume.idLanguage}"/>.js"></script>
<script src="/js/jsonData/major/majorJson_<c:out value="${resume.idLanguage}"/>.js"></script>
<script>
var idResume = '<c:out value="${idResume}"/>';
var sequence = '<c:out value="${sequence}"/>';
var idDegree = '<c:out value="${education.idDegree}"/>';
var idFaculty = '<c:out value="${education.idFacMajor}"/>';
var idMajor = '<c:out value="${education.idMajor}"/>';
var idState = '<c:out value="${education.idState}"/>';
var unit = '<c:out value="${education.unit}"/>';
var otherFaculty = '<c:out value="${education.otherFaculty}"/>';
var otherMajor = '<c:out value="${education.otherMajor}"/>';
var otherUnit = '<c:out value="${education.otherUnit}"/>';
var eduOther = "<fmt:message key='EDU_OTHER'/>";
var eduAdd = "<fmt:message key='GLOBAL_ADD'/>";
var eduClose = "<fmt:message key='GLOBAL_CANCEL'/>";
var confirmDelete = "<fmt:message key='EDU_CONFIRM_DELETE'/>";
var degreeMaster = "<fmt:message key='EDU_DEGREE_MASTER_REQUIRED'/>";
var degreeBechelor = "<fmt:message key='EDU_DEGREE_BECHELOR_REQUIRED'/>";
var error = '<c:out value="${error}"/>';
var isEduOpen = false;
var haveNoEducation = 0;
var maxGrade = 0;
var maxMessage = "";
var container = null;

$(document).ready(function(){
	
	container = $('div.errorContainer');
	getEducationList();	
	//-------------------------------------Event---------------------------------
	setEvent();
	setTypeAhead();
	setValidation();
	
	//-------------------preload data-------------------
	if($('#idEducation').val()>-1){
		toggleEducationForm(true,true);
		$('#eduToggleText').html("");
		getState($('#country').val(),idState);
		//degree recheck
		if(idDegree!=1){
			getFaculty(idDegree,idFaculty);
			getMajor(idFaculty,idMajor);
			/*if(idMajor!=-1){
				getMajor(idFaculty,idMajor);
			}
			getMajor(idFaculty,idMajor);
			else{
				$('#idMajor').prop('disabled', false);
				$('#idMajor').css("background", "#ffffff");
				$('#idMajor').val(-1);
				$('#otherMajor').fadeIn(500);
				$('#otherMajor').prop('disabled', false);
				$('#otherMajor').val(otherMajor);
			}*/
		}
	}
	
	if(unit=="-1"){
		$('#unitOtherGroup').fadeIn(500);
		$('#unitOther').prop('disabled', false);
		$('#unitOther').val(otherUnit);
		setRulesUnit(false);
	}
	else{
		setRulesUnit(true);
	}
	
	if(error!=""){
		$('div.errorContainer li').remove();
		if(error=="degreeMaster"){
			container.find("ol").append('<li>'+degreeMaster+'</li>');
		}
		else if(error=="degreeBechelor"){
			container.find("ol").append('<li>'+degreeBechelor+'</li>');
		}
		container.css({'display':'block'});
		container.find("ol").css({'display':'block'});
		$('html, body').animate({scrollTop: '0px'}, 300);
		toggleEducationForm(true,false);
	}
	if($('#startMonth').val()!=""&&$('#startYear').val()!=""){
		$('#startYear').rules("add", 
			{
				required:true
				,
		        messages: {
		        	required:'<fmt:message key="EDU_START_YEAR_REQUIRED"/>'
			    }
	    	});
	}
	
	if($('#finishMonth').val()!=""&&$('#finishYear').val()!=""){
		$('#finishYear').rules("add", 
			{
				required:true
				,
		        messages: {
		        	required:'<fmt:message key="EDU_END_YEAR_REQUIRED"/>'
			    }
	    	});
	}
	
	
});

function setValidation(){
	$('#educationForm').validate({
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		invalidHandler: function(form, validator) 
		{
			$('html, body').animate({scrollTop: '0px'}, 300);      
		},
		highlight: function(element) 
		{
           	$(element).closest('.form-group').addClass('has-error');
       	},
       	unhighlight: function(element) 
       	{
           	$(element).closest('.form-group').removeClass('has-error');
       	},
       	errorPlacement: function(error, element) 
       	{
           	if(element.parent('.form-group').length) 
           	{
               	error.insertAfter(element.parent());
           	} 
           	else 
           	{
               	error.insertAfter(element);
           	}
       	},
		rules:{
 			country:{
 				required:true
 			},
 			state:{
 				required:true
 			},
 			school:{
 				required:true
 			},
 			degree:{
 				required:true
 			},
 			idFaculty:{
 				required:true
 			},
 			idMajor:{
 				required:true
 			},
 			faculty:{
 				required:true
 			},
 			major:{
 				required:true
 			},
 			grade:{
 				required:true,
 				number:true
 			},
 			unit:{
 				required:true
 			},
 			unitOther:{
 				required:true
 			},
 			unitOther:{
 				required:true
 			},
 			finishYear:{
 				required:true
 			}
 			
		},			  
		messages:{
			country:{
				required:'<fmt:message key="EDU_COUNTRY_REQUIRED"/>'
			},
			state:{
				required:'<fmt:message key="EDU_STATE_REQUIRED"/>'
			},
			school:{
				required:'<fmt:message key="EDU_UNIVERSITY_REQUIRED"/>'
			},
			degree:{
				required:'<fmt:message key="EDU_DEGREE_REQUIRED"/>'
			},
			idFaculty:{
				required:'<fmt:message key="EDU_FACULTY_REQUIRED"/>'
			},
			idMajor:{
				required:'<fmt:message key="EDU_MAJOR_REQUIRED"/>'
			},
 			faculty:{
 				required:'<fmt:message key="EDU_OTHER_FACULTY_REQUIRED"/>'
 			},
 			major:{
 				required:'<fmt:message key="EDU_OTHER_MAJOR_REQUIRED"/>'
 			},
 			grade:{
 				required:'<fmt:message key="EDU_GRADE_REQUIRED"/>',
 				number:'<fmt:message key="EDU_GRADE_REQUIRED_NUMBER"/>'
 			},
 			unit:{
 				required:'<fmt:message key="EDU_UNIT_REQUIRED"/>'
 			},
 			unitOther:{
 				required:'<fmt:message key="EDU_UNIT_REQUIRED"/>'
 			},
 			finishYear:{
 				required:'<fmt:message key="EDU_END_YEAR_REQUIRED"/>'
 			}
		},
		submitHandler:function(form){
			var container = $('div.errorContainer');
			if(isEduOpen){//check form open if isn't open,Check can next?
				$.ajax({
					type: "POST",
	        		url: '/EducationServ',
	       			data: $('#educationForm').serialize(),
	       			async:false,
	       			success: function(data){
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					if(sequence==0)
	       					{
	       						window.location.href = "/SRIX?view=education&idResume="+idResume+"&sequence="+sequence;
	       					}
	       					else
	       					{
	       						if(idResume==0){
	       							window.location.href = "/SRIX?view=experience&idResume="+idResume+"&sequence="+sequence;
	       						}
	       						else{
	       							window.location.href = "/SRIX?view=education&idResume="+idResume+"&sequence="+sequence;
	       						}
	       					}
	       				}
	           			else{
	           				$('div.errorContainer li').remove();
	           				if(obj.urlError!=""){
	           					window.location.href = obj.urlError;
	           				}
	           				else{
	           					if(obj.degreeError!=""){
	           						window.location.href = "/SRIX?view=education&idResume="+idResume+"&sequence="+sequence+"&error="+obj.degreeError;
	           					}
	           					else{
	           						for(var i=0; i<obj.errors.length; i++){
			           					container.find("ol").append('<li>'+obj.errors[i]+'</li>');
			           				}
			           				container.css({'display':'block'});
			           				container.find("ol").css({'display':'block'});
			           				$('html, body').animate({scrollTop: '0px'}, 300); 
	           					}
	           				}
	           			}
	       			}
	       		});
				return false;
			}
			else{
				if(idResume==0){//check if register flow
					var container = $('div.errorContainer');
					var service = 'checkHightestEducation';
					var request = '{"service":"'+service+'","idResume":"'+idResume+'"}';
					$.ajax({
						type: "POST",
				  		url: '/EducationServ',
						data: jQuery.parseJSON(request),
						async:false,
						success: function(data){
							var obj = jQuery.parseJSON(data);
							if(obj.success==1){
								if(sequence==0){
		       						window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
		       					}
		       					else{
		       						window.location.href = "/SRIX?view=experience&idResume="+idResume+"&sequence="+sequence;
		       					}
							}
				   			else{
				   				$('div.errorContainer li').remove();
				   				if(obj.urlError!=""){
				   					window.location.href = "/SRIX?view=education&idResume="+idResume+"&sequence="+sequence+"&error="+obj.degreeError;
				   				}
				   				else{
				   					for(var i=0; i<obj.errors.length; i++){
				    					container.find("ol").append('<li>'+obj.errors[i]+'</li>');
				    				}
				    				container.css({'display':'block'});
				    				container.find("ol").css({'display':'block'});
				    				$('html, body').animate({scrollTop: '0px'}, 300); 
				   				}
				   			}
						}
					});
				}
				else{
					if(sequence==0){
   						window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
   					}
   					else{
  						window.location.href = "/SRIX?view=experienceList&idResume="+idResume+"&sequence="+sequence;
   					}
				}
				return false;
			}
		}
	});
}

function setEvent(){
	$('#country').change(function() {
		$("#school").val("");
		$("#idSchool").val(-1);
		getState($(this).val(),0);
	});
	
	$('#state').change(function() {
		if($(this).val()>0){
			$('#school').prop('disabled', false);
		}
		else{
			$('#school').prop('disabled', true);
		}
	});
	
	$('#degree').change(function() {
		if($("#degree").val()>1){
			//disable other faculty
			$('#otherFaculty').prop('disabled', true);
			$('#otherFaculty').hide();
			//disabled major select box
			$('#idMajor').prop('disabled', true);
			$('#idMajor').val('');
			//disable other major
			$('#otherMajor').prop('disabled', true);
			$('#otherMajor').fadeOut(500);
			getFaculty($("#degree").val(),0);
		}
		else{
			//disable faculty select box
			$('#idFaculty').prop('disabled', true);
			$('#idFaculty').css("background", "#eeeeee");
			$('#idFaculty').val('');
			//disabled major select box
			$('#idMajor').prop('disabled', true);
			$('#idMajor').css("background", "#eeeeee");
			$('#idMajor').val('');
			//disable other faculty
			$('#otherFaculty').prop('disabled', true);
			$('#otherFaculty').fadeOut(500);
		  	//disable other major
			$('#otherMajor').prop('disabled', true);
			$('#otherMajor').fadeOut(500);
		}
	});
	
	$('#idFaculty').change(function() {
		if($('#idFaculty').val()==-1){
			$('#otherFaculty').prop('disabled', false);
			$('#otherFaculty').fadeIn(500);
			$('#otherFaculty').focus();
			$('#idMajor').val(-1);//set to other
		    $('#idMajor option[value!=""][value!=-1]').remove();//clear list
		    $('#idMajor').prop('disabled', false);
		    $('#idMajor').css("background", "#ffffff");
		    $('#otherMajor').prop('disabled', false);
		    $('#otherMajor').fadeIn(500);
		}
		else{
			$('#otherFaculty').prop('disabled', true);
			$('#otherFaculty').fadeOut(500);
			$('#otherMajor').prop('disabled', true);
			$('#otherMajor').fadeOut(500);
			if($('#idFaculty').val()!=''){//if faculty have value
				getMajor($('#idFaculty').val(),0);
			}
			else{
				$('#idMajor').prop('disabled', true);
				$('#idMajor').css("background", "#eeeeee");
			}
		}
	});
	
	$('#idMajor').change(function() {
		if($('#idMajor').val()==-1){
			$('#otherMajor').prop('disabled', false);
			$('#otherMajor').fadeIn(500);
		}
		else{
			$('#otherMajor').prop('disabled', true);
			$('#otherMajor').fadeOut(500);
		}
	});
	
	$('#unit').change(function(){
		if($(this).val()==-1){
			setRulesUnit(false);
			$('#unitOther').prop('disabled', false);
			$('#unitOtherGroup').fadeIn(500);
		}
		else{
			setRulesUnit(true);
		    $('#unitOtherGroup').fadeOut(500);
			$('#unitOther').prop('disabled', true);
		}
	});
	
	$('#eduToggle').click(function(){
		if(haveNoEducation){
			$("#dialogFormAlert").dialog({
			      resizable: false,
			      height:200,
			      modal: true,
			      title:"Message",
			      buttons: {
			        "<fmt:message key='GLOBAL_CANCEL'/>": function() {
			          $( this ).dialog( "close" );
			        }
			      }
			});
		}
		else{
			if(isEduOpen){
				toggleEducationForm(false,true);
			}
			else{
				toggleEducationForm(true,true);
			}
		}
	});
	
	$('#school').change(function (){
		if($(this).val()==""){
			$("#idSchool").val(-1);
		}
		
	});
	$('#startMonth').change(function (){
		if($('#startMonth').val()!=""){
			$('#startYear').rules("add", 
			{
				required:true
				,
		        messages: {
		        	required:'<fmt:message key="EDU_START_YEAR_REQUIRED"/>'
			    }
	    	});
		}
		else{
			$('#startYear').rules( "remove", "required" );
		}
	
	});
	
	$('#finishMonth').change(function (){
		if($('#finishMonth').val()!=""){
			$('#finishYear').rules("add", 
			{
				required:true
				,
		        messages: {
		        	required:'<fmt:message key="EDU_END_YEAR_REQUIRED"/>'
			    }
	    	});
		}
		else{
			$('#finishYear').rules( "remove", "required" );
		}
	
	});
}

function setRulesUnit(isAddRule){
	if(isAddRule){
		maxGrade = getSelectedUnitMaxGrade($("#unit").val());
		maxMessage = getGradeMessage(maxGrade);
		$('#grade').rules("add", {
			min:0,
	        max: parseInt(maxGrade),
	        messages: {
	        	max: maxMessage,
		        min: "<fmt:message key='EDU_GRADE_REQUIRED_0'/>"
		    }
	    });
	}
	else{
		$('#grade').rules( "remove", "min max" );
	}
}

function getEducationList(){
	$.ajax({
		type: "POST",
   		url: '/view/subview/educationList.jsp',
   		data: {'idResume':idResume,'sequence':sequence},
   		async:false,
   		success: function(data){
   			if(data.trim()!=""){
   				$('#educationListDiv').html(data);
   				$('#educationListDiv').fadeIn(500);
   				$('#divHightestEdu').hide();
   			}
   			else{
   				haveNoEducation = true;
   				toggleEducationForm(true,true);
   				$('#eduToggleText').html("");
   				$('#educationListDiv').html("");
   				$('#educationListDiv').fadeOut(500);
   				$('#divHightestEdu').show();
   			}
   		}
   	});
}

function getSelectedUnitMaxGrade(unit){
	var result = 0;
	if(unit=="/4"){
		result = 4;
	}
	else if(unit=="/5"){
		result = 5;
	}
	else if(unit=="/7"){
		result = 7;
	}
	else if(unit=="%"){
		result = 100;
	}
	return result;
}

function getGradeMessage(maxGrade){
	var result = "";
	if(maxGrade==4){
		result = '<fmt:message key="EDU_GRADE_REQUIRED_4"/>';
	}
	else if(maxGrade==5){
		result = '<fmt:message key="EDU_GRADE_REQUIRED_5"/>';
	}
	else if(maxGrade==7){
		result = '<fmt:message key="EDU_GRADE_REQUIRED_7"/>';
	}
	else if(maxGrade==100){
		result = '<fmt:message key="EDU_GRADE_REQUIRED_100"/>';
	}
	return result;
}


function toggleEducationForm(setOpen,setAnimate){
	if(setOpen){
		$('#country').prop('disabled',false);
		getState($('#country').val(),0);
		$('#degree').prop('disabled',false);
		$('#startMonth').prop('disabled',false);
		$('#startYear').prop('disabled',false);
		$('#finishMonth').prop('disabled',false);
		$('#finishYear').prop('disabled',false);
		$('#grade').prop('disabled',false);
		$('#unit').prop('disabled',false);
		if($('#unit').val()==-1){
			$('#unitOther').prop('disabled', false);
		}
		$('#submit').prop('disabled',false);
		$('#eduToggleText').html(eduClose);
		$('#eduFormDiv').fadeIn(500);
		isEduOpen = true;
		if(setAnimate){
			$('html, body').animate({scrollTop: $('#eduFormDiv').position().top}, 500);
		}
	}
	else{
		$('#eduFormDiv :input').prop('disabled', true);
		$('#eduToggleText').html(eduAdd);
		$('#eduFormDiv').fadeOut(500);
		isEduOpen = false;
		$('html, body').animate({scrollTop: '0px'}, 300);
	}
}

function setTypeAhead(){
	$('#school').typeahead({
		minLength:2,
	  	source: function (query, process) {
	  		var attachParam = "?service=university&idCountry="+$('#country').val();
	  		var idState = 0;
	    	if($('#country').val() == 231 ||$('#country').val() == 110 ||$('#country').val() == 213 ||$('#country').val() == 81){
	        	attachParam+= "&idState="+$('#state').val();
	        	idState =$('#state').val();
	        }
	        else{
	        	attachParam+= "&idState=0";
	        	idState = 0;
	        }
	        $.support.cors = true;
	        return $.ajax({
		        	url: '/JSONServ',
					data: {'action':'genUniversityByKeyword','test':1,'idCountry':$('#country').val(),'idState':idState,'keyword':query},
		        	global: false,
		        	async :true,
		        	success: function( data, textStatus, jqXHR) {
	                    objects = [];
		       	 		map = {};
		       	 		if(data!=""){
			       	 		$.each(jQuery.parseJSON(data).data, function(i, object) {
					            map[object.SCHOOL_NAME] = object;
					            objects.push(object.SCHOOL_NAME);
					        });
				        }
				        $('#idSchool').val(-1);
			            return process(objects);
	                },
					error:function(xdr, status,error)
					{
						alert(error);
					}
	        	});
	        /*
	        $.ajax({
		        	url: 'http://202.142.222.244/JTGServices/iServBom.php',
					data: {'action':'genUniversityByKeyword','test':1,'idCountry':$('#country').val(),'idState':idState,'keyword':query},
		        	global: false,
		        	async :true,
		        	success: function( data, textStatus, jqXHR) {
	                    objects = [];
		       	 		map = {};
		       	 		if(data!=""){
			       	 		$.each(jQuery.parseJSON(data), function(i, object) {
					            map[object.SCHOOL_NAME] = object;
					            objects.push(object.SCHOOL_NAME);
					        });
				        }
				        $('#idSchool').val(-1);
			            return process(objects);
	                },
					error:function(xdr, status,error)
					{
						alert(error);
					}
	        	});
	        	// old code ,get university from textFile
	        	*/
	        	/* 
	        	$.ajax({
		        	url: "/AutoCompleteServ"+attachParam,
		        	global: false,
		        	async :true,
		        	success: function( data, textStatus, jqXHR) {
	                    objects = [];
		       	 		map = {};
		       	 		if(data!=""){
			       	 		$.each(jQuery.parseJSON(data), function(i, object) {
					            map[object.label] = object;
					            objects.push(object.label);
					        });
				        }
				        $('#idSchool').val(-1);
			            return process(objects);
	                }
	        	});
	        	*/
	        	
    	},
	    updater: function(item) {
	        $('#idSchool').val(map[item].ID_SCHOOL);
	        $("#school").prop('maxLength', item.length);
	        $('#school').unbind('change');
	        $('#degree').val("");
	        $('#idFaculty').val("");
	        $('#idMajor').val("");
	        $('#idFaculty').prop('disabled',true);
	        $('#idMajor').prop('disabled',true);
	        $('#idFaculty').css("background", "#eeeeee");
	        $('#idMajor').css("background", "#eeeeee");
	        return item;
	    }
	});
}

function getState(idCountry,idState){
	if(idCountry == 231 || idCountry == 110 || idCountry == 213 || idCountry == 81 ){
		var urlGet = "/AjaxServ?service=State&idCountry="+idCountry+"&idLanguage="+$('#idLanguage').val();
		$.ajax({url:urlGet,success:function(result){
			var state = jQuery.parseJSON(result);
			$('#state option[value!=""]').remove();
			for (var i=0, len=state.length; i < len; i++) {
				if(state[i].idState==idState){
					$('#state').append("<option value='"+state[i].idState+"' selected>"+state[i].stateName+"</option>");
				}
				else{
					$('#state').append("<option value='"+state[i].idState+"'>"+state[i].stateName+"</option>");
				}
		    }
		    if(idState>0){
		    	$('#school').prop('disabled', false);
		    }
		    else{
		    	$('#school').prop('disabled', true);
		    }
		    $('#stateGroup').fadeIn(500);
		    $('#state').prop('disabled', false);
		    $('#state').focus();
		}});
	}
	else{
		$('#stateGroup').fadeOut();
		$('#school').prop('disabled', false);
		$('#state').prop('disabled', true);
	}
}
function getFaculty(idDegree,idSelected){
	// new code //
	
	var idSchool = $('#idSchool').val();
	var chkSchoolType = 0;
	if(idSchool == 11 || idSchool ==46 || idSchool ==47 || idSchool==48)
	{
		if(idDegree!=2 && idDegree!=3 && idDegree!=7 )
		{
			idDegree = -1;
		}
		chkSchoolType = 1
	}else{
		if(idDegree==4||idDegree==5||idDegree==14||idDegree==15){
			idDegree = 1;
		}
		else{
			idDegree = 0;
		}	
		chkSchoolType = 0
	}
	$.ajax({
		type: "GET",
  		url: '/JSONServ',
		data: {'action':'getFaculty','test':1,'idSchool':idSchool,'idLanguage':$('#idLanguage').val(),'idDegree':idDegree},
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data).data;
			$('#idFaculty option[value!=""]').remove();
			for (var i=0; i<obj.length; i++) {
				if(chkSchoolType==0){
					if(obj[i].ID_FACULTY!=33){
						if(obj[i].ID_FACULTY==idSelected){
							$('#idFaculty').append("<option value='"+obj[i].ID_FACULTY+"' selected>"+obj[i].NAME+"</option>");
						}
						else{
							$('#idFaculty').append("<option value='"+obj[i].ID_FACULTY+"'>"+obj[i].NAME+"</option>");
						}
					}
				}else if(chkSchoolType==1){
					if(obj[i].ID_FACULTY==idSelected){
						$('#idFaculty').append("<option value='"+obj[i].ID_FACULTY+"' selected>"+obj[i].NAME+"</option>");
					}
					else{
						
						$('#idFaculty').append("<option value='"+obj[i].ID_FACULTY+"'>"+obj[i].NAME+"</option>");
					}
				}
				
		    }
		    $('#idFaculty').append("<option value='-1'>"+eduOther+"</option>");
			if(idSelected==0){
				$('#idFaculty').val('');
			}
			else if(idSelected==-1){
				$('#idFaculty').val(-1);
				$('#otherFaculty').prop('disabled', false);
			    $('#otherFaculty').show();
				$('#otherFaculty').val(otherFaculty);
			}
			$('#idFaculty').prop('disabled', false);
			$('#idFaculty').css("background", "#ffffff");
			
		},
		error:function(xdr, status,error)
		{
			alert(error);
		}
	});
	
	// old code //
	/*
	var dataFaculty = jQuery.parseJSON(facultyJson);
	var data = null;
	if(idDegree==4||idDegree==5||idDegree==14||idDegree==15){
		data = dataFaculty.facultyLevel1;
	}
	else{
		data = dataFaculty.facultyLevel0;
	}
	$('#idFaculty option[value!=""]').remove();
	for (var i=0; i<data.length; i++) {
		if(data[i].id!=33){
			if(data[i].id==idSelected){
				$('#idFaculty').append("<option value='"+data[i].id+"' selected>"+data[i].name+"</option>");
			}
			else{
				$('#idFaculty').append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
		}
    }
	$('#idFaculty').append("<option value='-1'>"+eduOther+"</option>");
	if(idSelected==0){
		$('#idFaculty').val('');
	}
	else if(idSelected==-1){
		$('#idFaculty').val(-1);
		$('#otherFaculty').prop('disabled', false);
	    $('#otherFaculty').show();
		$('#otherFaculty').val(otherFaculty);
	}
	$('#idFaculty').prop('disabled', false);
	$('#idFaculty').css("background", "#ffffff");
	*/
}
function getMajor(idFaculty,idSelected){
	//new code //
	var idSchool = $('#idSchool').val();
	if(idSchool == 11 || idSchool ==46 || idSchool ==47 || idSchool==48)
	{
		if(idDegree!=2 && idDegree!=3 && idDegree!=7 )
		{
			idDegree = -1;
		}
		chkSchoolType = 1
	}
	$.ajax({
		type: "GET",
  		url: '/JSONServ',
		data: {'action':'getMajor','test':1,'idSchool':idSchool,'idLanguage':$('#idLanguage').val(),'idDegree':$("#degree").val(),'idFaculty':idFaculty},
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data).data;
			$('#idMajor option[value!=""]').remove();
			for(var i=0;i<obj.length;i++){
				if(obj[i].ID_MAJOR==idSelected){
					$('#idMajor').append("<option value='"+obj[i].ID_MAJOR+"' selected>"+obj[i].NAME+"</option>");
				}
				else{
					$('#idMajor').append("<option value='"+obj[i].ID_MAJOR+"'>"+obj[i].NAME+"</option>");
				}
			}
			$('#idMajor').append("<option value='-1'>"+eduOther+"</option>");
			if(idSelected==-1){
				$('#idMajor').prop('disabled', false);
				$('#idMajor').css("background", "#ffffff");
				$('#idMajor').val(-1);
				$('#otherMajor').fadeIn(500);
				$('#otherMajor').prop('disabled', false);
				$('#otherMajor').val(otherMajor);
			}
			$('#idMajor').prop('disabled', false);
			$('#idMajor').css("background", "#ffffff");
		},
		error:function(xdr, status,error)
		{
			alert(error);
		}
	});
	
	// old code //
	/*
	var dataMajor = jQuery.parseJSON(majorJson);
	var foundFaculty = false;
	for(var i=0;i<dataMajor.facMajor.length&&!foundFaculty;i++){
		if(idFaculty==dataMajor.facMajor[i].idFaculty){
			$('#idMajor option[value!=""]').remove();
			for(var j=0;j<dataMajor.facMajor[i].major.length;j++){
				if(dataMajor.facMajor[i].major[j].idMajor==idSelected){
					$('#idMajor').append("<option value='"+dataMajor.facMajor[i].major[j].idMajor+"' selected>"+dataMajor.facMajor[i].major[j].name+"</option>");
				}
				else{
					$('#idMajor').append("<option value='"+dataMajor.facMajor[i].major[j].idMajor+"'>"+dataMajor.facMajor[i].major[j].name+"</option>");
				}
			}
			$('#idMajor').append("<option value='-1'>"+eduOther+"</option>");
			if(idSelected<=0){
				$('#idMajor').val('');
			}
			$('#idMajor').prop('disabled', false);
			$('#idMajor').css("background", "#ffffff");
			foundFaculty = true;
		}
	}
	*/
	
}



function deleteEducation(idEducation){
	var container = $('div.errorContainer');
	var service = 'delete';
	var request = '{"service":"'+service+'","idEducation":"'+idEducation+'","idResume":"'+idResume+'"}';
	if(confirm(confirmDelete)){
		$.ajax({
			type: "POST",
	  		url: '/EducationServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					 getEducationList();
				}
	   			else{
	   				$('div.errorContainer li').remove();
	   				if(obj.urlError!=""){
	   					window.location.href = obj.urlError;
	   				}
	   				else{
	   					for(var i=0; i<obj.errors.length; i++){
	    					container.find("ol").append('<li><label class="error">'+obj.errors[i]+'</label></li>');
	    				}
	    				container.css({'display':'block'});
	    				container.find("ol").css({'display':'block'});
	    				$('html, body').animate({scrollTop: '0px'}, 300); 
	   				}
	   			}
			}
		});
	}
}
</script>