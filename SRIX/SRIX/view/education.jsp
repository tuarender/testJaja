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
<%@page import="com.topgun.resume.WorkExperience"%>
<%@page import="com.topgun.resume.EducationManager"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.resume.WorkExperienceManager"%>
<%@page import="com.topgun.resume.CompleteStatus"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.Resume"%>
<%
	int idLanguage=Util.getInt(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idLanguageResume = 11; 
	int idCountry=Util.getInt(session.getAttribute("SESSION_ID_COUNTRY"));
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	int idEducation = Util.StrToInt(request.getParameter("idEducation")!=null?request.getParameter("idEducation"):"-1");
	int idSchoolExclusive = Util.getInt(session.getAttribute("SESSION_ID_SCHOOL_EXCLUSIVE"));
	String backToView = Util.getStr(request.getParameter("backToView")).trim();
	String error = Util.getStr(request.getParameter("error"),"");
	Resume resume=(Resume)request.getAttribute("resume");
	ArrayList<Country> countries = (ArrayList<Country>)MasterDataManager.getAllCountry(resume.getIdLanguage());
	ArrayList<Degree> degrees = (ArrayList<Degree>)MasterDataManager.getAllDegree(resume.getIdLanguage());
	Education edu = new EducationManager().get(resume.getIdJsk(),resume.getIdResume(),idEducation);
	
	System.out.print("idJsk"+idJsk);
	School school = null;
	if(resume!=null)
	{
	 	idLanguageResume = resume.getIdLanguage();
	}
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
	
	List<WorkExperience> fullTimeExp = new WorkExperienceManager().getAllFulltimes(idJsk, resume.getIdResume());
	int expCount = fullTimeExp.size();
	
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
	request.setAttribute("idLanguageResume", idLanguageResume);
	request.setAttribute("expCount", expCount);	
	request.setAttribute("backToView", backToView);	
	
%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<!-- Google conversion page & facebook conversion page -->
<!-- Google Code for Total Register Conversion Page (Jobtopgun) -->
<script type="text/javascript">
	/* <![CDATA[ */
	var google_conversion_id = 1013159874;
	var google_conversion_language = "en";
	var google_conversion_format = "3";
	var google_conversion_color = "ffffff";
	var google_conversion_label = "0tAPCL7j4QIQwq-O4wM";
	var google_conversion_value = 0;
	if (1) {
	  google_conversion_value = 1;
	}
	/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js"></script>
<noscript>
	<div style="display:inline;">
	<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/1013159874/?value=1&amp;label=0tAPCL7j4QIQwq-O4wM&amp;guid=ON&amp;script=0"/>
	</div>
</noscript>

<!-- Google Code for Register Conversion Page (Superresume) -->
<script type="text/javascript">
	/* <![CDATA[ */
	var google_conversion_id = 996733952;
	var google_conversion_language = "en";
	var google_conversion_format = "3";
	var google_conversion_color = "ffffff";
	var google_conversion_label = "71WeCJixgQQQgOij2wM";
	var google_conversion_value = 0;
	/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/996733952/?value=0&amp;label=71WeCJixgQQQgOij2wM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
<div class="row form-group alignCenter"><span class="suggestion"><fmt:message key="DIRECTION_EDUCATION"/></span></div>

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
	<div class="form-group">
		<div class="row col-xs-12 col-sm-12">
			<font color="red">*</font><label for="country"><fmt:message key='EDU_COUNTRY'/></label>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				<select style="width:100%;" class="form-control" name="country" id="country" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'ประเทศ่'});">
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
		</div>
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
		<input type="hidden" name="idSchool" id="idSchool" class="form-control" value="<c:choose><c:when test='${education.idSchool != -1&&education.idSchool!=0 && idSchoolExclusive ==-1}'><c:out value="${education.idSchool}"/></c:when><c:when test='${idSchoolExclusive>0}'><c:out value="${idSchoolExclusive}"/></c:when><c:otherwise>-1</c:otherwise></c:choose>">
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
	<!-- finish educate -->
	<div class="form-group">
		<fmt:formatDate var="finishMonth" value="${education.finishDate}" pattern="M"/>
		<fmt:formatDate var="finishYear" value="${education.finishDate}" pattern="yyyy"/>
		<fmt:formatDate var="finishDate" value="${education.finishDate}" pattern="d"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()+6%></c:set>
		<div class="row col-xs-12 col-sm-12">
			<font id="remarkYear" color="red">*</font><label for="finishYear"><fmt:message key="EDUCATION_FINISH_YEAR"/></label>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				<select style="width:100%;" class="form-control" name="finishYear" id="finishYear" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'Click-dropdown', eventLabel: 'ศึกษาถึงปี'});">
					<option value=""><fmt:message key="EDUCATION_YEAR"/></option>
					<c:forEach var="i" begin="2" end="10">
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
	<div class="row form-group"><span class="suggestion"><fmt:message key="EDUCATION_CAUTION_END_DATE_REGISTER"/></span></div>
	<div class="form-group" id="idStudentLayer">
		<font color="red">*</font><label style="padding-right:1pt;"><fmt:message key="STUDENT_ID"/></label>
		<input class="form-control" id="idStudent" name="idStudent" type="text" maxLength="300"  value="<c:if test='${not empty education.idStudent}'><c:out value='${education.idStudent}'/></c:if>" >
	</div>
	<div class="form-group" >
		<font color="red">*</font><label style="padding-right:1pt;"><fmt:message key='EDU_GRADE'/></label>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group">
				<input class="form-control" type="text" id="grade" name="grade" style="padding:0px 0px 0px 5px;max-width:150pt;margin-right:3pt;display:inline !important"  placeholder="<fmt:message key='EDU_GRADE'/>" value="<c:if test='${education.gpa != 0.0}'><c:out value='${education.gpa}'/></c:if>" onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'TypeOn', eventLabel: 'คะแนนเฉลี่ย'});">
			</div>
		</div>
	   	<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="form-group">
				<font style="padding-right:1pt;padding-left:1pt;"><fmt:message key='EDU_FROM'/></font>
				&nbsp;<font style="padding-right:1pt;"><fmt:message key='EDU_UNIT'/></font>
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
	<div class="form-group">
		<label for="award" class="caption"><fmt:message key="EDU_AWARD"/></label> 
		<textarea rows="5" cols="30" class="form-control" name="award" id="award" ><c:out value="${education.award}"/></textarea>
	</div>
	<div class="row form-group"><span class="suggestion"><fmt:message key="EDUCATION_AWARD_DETAIL"/></span></div>
</div>



<div class="form-group alignCenter">
	<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" id="submit"  onClick="ga('send', 'event', { eventCategory: 'Engage-Education', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"/>
</div>
</form>
<c:set var="section" value="3" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>
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
var idLanguageResume = '<c:out value="${idLanguageResume}"/>';
var expCount = '<c:out value="${expCount}"/>';

$(document).ready(function(){
	container = $('div.errorContainer');
	getEducationList();	
	//-------------------------------------Event---------------------------------
	setEvent();
	setTypeAhead();
	setValidation();
	//setExpValidation();
	
	//-------------------preload data-------------------
	if($('#idEducation').val()>-1){
		toggleEducationForm(true,true);
		$('#eduToggleText').html("");
		getState($('#country').val(),idState);
		showIdStudent($('#finishYear').val());
		//degree recheck
		if(idDegree>1){
			getFaculty(idDegree,idFaculty);
			if(idFaculty!=-1){
				getMajor(idFaculty,idMajor);
			}else{
				$('#idMajor').prop('disabled', false);
				$('#idMajor').css("background", "#ffffff");
				$('#idMajor').val(-1);
				$('#otherMajor').fadeIn(500);
				$('#otherMajor').prop('disabled', false);
				$('#otherMajor').val(otherMajor);
			}
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
	       						$('#resumeListLayer').modal({
       								show: 'true'
    							}); 
	       						//window.location.href = "/SRIX?view=education&idResume="+idResume+"&sequence="+sequence+'<%=backToView.length() > 0 ? "&backToView="+backToView : ""%>';
	       					}
	       					else
	       					{
	       						if(idResume==0){
	       							window.location.href = "/SRIX?view=experience&idResume="+idResume+"&sequence="+sequence;
	       						}
	       						else{
	       							window.location.href = "/SRIX?view=education&idResume="+idResume+"&sequence="+sequence+'<%=backToView.length() > 0 ? "&backToView="+backToView : ""%>';
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
		       						var request = '{"idResume":"'+idResume+'","sequence":"'+sequence+'"}';
		       						if(idResume==0){
		       							window.location.href = "/SRIX?view=experience&idResume="+idResume+"&sequence="+sequence+'<%=backToView.length() > 0 ? "&backToView="+backToView : ""%>';
	               					}else{
	               						window.location.href = "/SRIX?view=experience&idResume="+idResume+"&sequence="+sequence+'<%=backToView.length() > 0 ? "&backToView="+backToView : ""%>';
	               					}
		       					}
							}
				   			else{
				   				$('div.errorContainer li').remove();
				   				if(obj.urlError!=""){
				   					window.location.href = "/SRIX?view=education&idResume="+idResume+"&sequence="+sequence+"&error="+obj.degreeError+'<%=backToView.length() > 0 ? "&backToView="+backToView : ""%>';
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
					<%if(backToView.length() == 0){ %>
					if(sequence==0){
   						window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
   					}
   					else{
  						window.location.href = "/SRIX?view=experienceList&idResume="+idResume+"&sequence="+sequence;
   					}
					<% }else{ %>
						window.location.href = "/SRIX?view=<%=backToView%>&idResume="+idResume;
					<% } %>
				}
				return false;
			}
		}
	});
}
/*function setExpValidation(){
	if(expCount==0)
	{
		$('#finishYear').rules("add", 
			{
				required:true
				,
		        messages: {
		        	required:'<fmt:message key="EDU_END_YEAR_REQUIRED"/>'
			    }
	    	});
	    $('#remarkYear').show();
	}
}*/
function setEvent(){
	$('#country').change(function() {
		$("#school").val("");
		$("#idSchool").val(-1);
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
		if($("#degree").val()>1 && $('#idSchool').val() !=0 && $('#country').val()>0) {
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
	
	$('#finishYear').change(function(){
		
		if($('#finishYear').val()>=2014)
		{
			$('#idStudent').rules("add", 
				{
					required:true,
					maxlength:15,
					
			        messages: {
			        	required:'<fmt:message key="STUDENT_ID_REQUIRED"/>',
			        	maxlength:'<fmt:message key="STUDENT_ID_LENGHT"/>'
			        	
				    }
		    });
			$('#idStudentLayer').fadeIn(500);
		}else{
			$('#idStudent').val("");
			$('#idStudent').rules('remove');
			$('#idStudent').removeClass('error');
			$('#idStudentLayer').fadeOut();					
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
			$("#idSchool").val(0);
			$('#idFaculty').prop('disabled', true);
			$('#idFaculty').css("background", "#eeeeee");
			$('#idFaculty').val('');
			//disabled major select box
			$('#idMajor').prop('disabled', true);
			$('#idMajor').css("background", "#eeeeee");
			$('#idMajor').val('');
		}
		if($('#degree').val()>1 && $('#idSchool').val()!=0 && $('#country').val()>0)
	        {       	
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
	        }else{
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
   		data: {'idResume':idResume,'sequence':sequence<%=backToView.trim().length() > 0 ? ",'backToView':'"+backToView+"'" : ""%>},
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
		$('#idStudentLayer').fadeOut();
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
		hint: true,
  		highlight: true,
	  	source: function (query, process) {	  
	  		var idState = 0;
	    	if($('#country').val() == 231 ||$('#country').val() == 110 ||$('#country').val() == 213 ||$('#country').val() == 81){
	        	idState =$('#state').val();
	        }
	        else{	        	
	        	idState = 0;
	        }
	        $.support.cors = true;		
	        return $.ajax({
		        	url: '/JSONServ',
					data: {'action':'genUniversityByKeyword','idCountry':$('#country').val(),'idState':idState,'idLanguage':idLanguageResume,'keyword':query},
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
    	},
	    updater: function(item) {
	        $('#idSchool').val(map[item].ID_SCHOOL);
	        //$("#school").prop('maxLength', item.length);
	        $('#school').unbind('change');
	        if($('#degree').val()>1 && $('#idSchool').val()!=0 && $('#country').val()>0)
	        {
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
	        }else{
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
	        
	        /*$('#idFaculty').val("");
	        $('#idMajor').val("");
	        $('#idFaculty').prop('disabled',true);
	        $('#idMajor').prop('disabled',true);
	        $('#idFaculty').css("background", "#eeeeee");
	        $('#idMajor').css("background", "#eeeeee");*/
	        return item;
	    }
	});
}
function showIdStudent(year)
{
	if(year>=2014)
	{
		$('#idStudent').rules("add", 
			{
				required:true,
					maxlength:15,
					
			        messages: {
			        	required:'<fmt:message key="STUDENT_ID_REQUIRED"/>',
			        	maxlength:'<fmt:message key="STUDENT_ID_LENGHT"/>',
			        	
				    }
		});
		$('#idStudentLayer').fadeIn(500);
		
	}else{
		$('#idStudent').rules('remove');
		$('#idStudent').removeClass('error');
		$('#idStudent').val("");
		$('#idStudentLayer').fadeOut();
		
	}
}
function getState(idCountry,idState){
	if(idCountry == 231 || idCountry == 110 || idCountry == 213 || idCountry == 81 ){
		var urlGet = "/AjaxServ?service=State&idCountry="+idCountry+"&idLanguage="+idLanguageResume;
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
	}else{
		$('#stateGroup').fadeOut();
		$('#school').prop('disabled', false);
		$('#state').prop('disabled', true);
	}
}
function getFaculty(idDegree,idSelected){
	// new code //
	var idSchool = $('#idSchool').val();
	var checkedStatus = false ;
	$('#idFaculty option[value!=""]').remove();
	$.ajax({
  		url: '/JSONServ',
		data: {'action':'getFacultyExclusive','idSchool':idSchool,'idLanguage':idLanguageResume,'idDegree':idDegree},
		global: false,
		async :false,
		success: function(data){
			var obj = jQuery.parseJSON(data).data;
			for (var i=0; i<obj.length; i++) {
				if(obj[i].ID_FACULTY!=0 && obj[i].ID_FACULTY!=-1){
					if(obj[i].ID_FACULTY==idSelected){
						$('#idFaculty').append("<option value='"+obj[i].ID_FACULTY+"' selected>"+obj[i].NAME+"</option>");
						checkedStatus = true ;
					}
					else{
						$('#idFaculty').append("<option value='"+obj[i].ID_FACULTY+"'>"+obj[i].NAME+"</option>");
					}
				}
		    }
		    if(obj.length>0)
		    {
		   		$('#idFaculty').append("<option value='--' disabled >------------------------------------------------------</option>");		    
		    }
		},
		error:function(xdr, status,error)
		{
			alert("Error"+error);
		},
		complete:function(){
			$.ajax({
		  		url: '/JSONServ',
				data: {'action':'getFacultySR','idLanguage':idLanguageResume,'idDegree':idDegree},
				global: false,
				async :false,
				success: function(data){
					var obj = jQuery.parseJSON(data).data;
					for (var i=0; i<obj.length; i++) {
						if(obj[i].ID_FACULTY!=33 && obj[i].ID_FACULTY!=0 && obj[i].ID_FACULTY!=-1){
							if(obj[i].ID_FACULTY==idSelected  && checkedStatus==false){
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
					alert("Error"+error);
				}
			});
		}
	});
}
function getMajor(idFaculty,idSelected){
	//new code //
	var idSchool = $('#idSchool').val();
	var checkedStatus = false ;
	$('#idMajor option[value!=""]').remove();
	$.ajax({
  		url: '/JSONServ',
		data: {'action':'getMajorExclusive','idSchool':idSchool,'idLanguage':idLanguageResume,'idDegree':$("#degree").val(),'idFaculty':idFaculty},
		global: false,
		async :false,
		success: function(data){
			var obj = jQuery.parseJSON(data).data;
			for(var i=0;i<obj.length;i++){
				if(obj[i].ID_MAJOR==idSelected){
					$('#idMajor').append("<option value='"+obj[i].ID_MAJOR+"' selected>"+obj[i].NAME+"</option>");
					checkedStatus = true ;
				}
				else{
					$('#idMajor').append("<option value='"+obj[i].ID_MAJOR+"'>"+obj[i].NAME+"</option>");
				}
			}
			if(obj.length>0)
		    {
		   		$('#idMajor').append("<option value='--' disabled >------------------------------------------------------</option>");		    
		    }
		},
		error:function(xdr, status,error)
		{
			alert(error);
		},
		complete:function(){
			$.ajax({
		  		url: '/JSONServ',
				data: {'action':'getMajorSR','idLanguage':idLanguageResume,'idFaculty':idFaculty},
				global: false,
				async :false,
				success: function(data){
					var obj = jQuery.parseJSON(data).data;
					for(var i=0;i<obj.length;i++){
						if(obj[i].ID_MAJOR==idSelected && checkedStatus == false){
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
		}
	});
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
<!-- Facebook Conversion Code for JOBTOPGUN-facebook -->
<script>(function() {
	var _fbq = window._fbq || (window._fbq = []);
	if (!_fbq.loaded) {
	var fbds = document.createElement('script');
	fbds.async = true;
	fbds.src = '//connect.facebook.net/en_US/fbds.js';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(fbds, s);
	_fbq.loaded = true;
	}
	})();
	window._fbq = window._fbq || [];
	window._fbq.push(['track', '6032216793106', {'value':'0.01','currency':'THB'}]);
</script>
<noscript><img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/tr?ev=6032216793106&amp;cd[value]=0.01&amp;cd[currency]=THB&amp;noscript=1" /></noscript>