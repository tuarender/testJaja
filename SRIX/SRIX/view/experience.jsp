<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.JobType"%>
<%@page import="com.topgun.resume.CurrencyManager"%>
<%@page import="com.topgun.resume.ExperienceIndustry"%>
<%@page import="com.topgun.resume.ExperienceIndustryManager"%>
<%@page import="com.topgun.resume.WorkExperience"%>
<%@page import="com.topgun.resume.WorkExperienceManager"%>
<%@page import="com.topgun.resume.ExperienceSummary"%>
<%@page import="com.topgun.shris.masterdata.JobField"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<%@page import="com.topgun.shris.masterdata.State"%>
<%@page import="com.topgun.shris.masterdata.Industry"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.util.Util" %>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ExperienceSummaryManager"%>
<%@page import="com.topgun.shris.masterdata.Currency"%>
<%@page import="java.util.Calendar"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${resume.locale}"/>
<% 
	int sequence=Util.getInt(request.getParameter("sequence"),1);
	Resume resume=(Resume)request.getAttribute("resume");
	if(resume!=null)
	{
		List<JobField> jobFields =MasterDataManager.getAllJobField(resume.getIdLanguage());
		List<Country> countries =MasterDataManager.getAllCountry(resume.getIdLanguage());
		List<State> states =MasterDataManager.getAllState(resume.getIdCountry(),resume.getIdLanguage());
		List<Industry> industries =MasterDataManager.getAllIndustry(resume.getIdLanguage(),false);
		List<JobType> jobTypes=MasterDataManager.getAllJobType(resume.getIdLanguage());
		List<Currency> currencies=MasterDataManager.getAllCurrency();
		WorkExperience lastExp=new WorkExperienceManager().getLatestExperience(resume.getIdJsk(), resume.getIdResume());
		int idWorkLastest=-1;
		if(lastExp!=null)
		{
			idWorkLastest=lastExp.getId();
		}
		request.setAttribute("jobFields",jobFields);
		request.setAttribute("countries",countries);
		request.setAttribute("states",states);	
		request.setAttribute("industries",industries);	
		request.setAttribute("jobTypes",jobTypes);	
		request.setAttribute("currencies",currencies);
		request.setAttribute("sequence",sequence);
		request.setAttribute("lastExp",lastExp);
		request.setAttribute("idWorkLastest",idWorkLastest);
	}
%>
<c:if test="${not empty resume }">
	<script>
		var idWorkLastest=<c:out value="${idWorkLastest}"/>;
		var idResume=<c:out value="${resume.idResume}"/>;
		var idLanguage=<c:out value="${resume.idLanguage}"/>;
		var idCountry=<c:out value="${resume.idCountry}"/>;
		var applyIdCountry=<c:out value="${resume.applyIdCountry}"/>;
		var expStatus='<c:out value="${resume.expStatus}"/>';
		var expCompany='<c:out value="${resume.expCompany}"/>';
		var globalAdd='<fmt:message key="GLOBAL_ADD"/>';
		var globalOther='<fmt:message key="GLOBAL_OTHER"/>';
		var globalRequire='<font color="red"><fmt:message key="GLOBAL_REQUIRE"/></font>';
		var globalSelect='<fmt:message key="GLOBAL_SELECT"/>';
		var globalJobField='<fmt:message key="GLOBAL_JOBFIELD"/>';
		var globalSubField='<fmt:message key="GLOBAL_SUBFIELD"/>';
		var globalDelete='<fmt:message key="GLOBAL_DELETE"/>';
		var globalCancel='<fmt:message key="GLOBAL_CANCEL"/>';
		var globalAll='<fmt:message key="GLOBAL_ALL"/>';
		var globalYear='<fmt:message key="GLOBAL_YEAR"/>';
		var globalMonth='<fmt:message key="GLOBAL_MONTH"/>';
		var globalTotalExp='<fmt:message key="WORKEXP_TOTALNUMBER"/>';
		var globalSalary='<fmt:message key="SALARY_REQUIRED"/>';
		var globalSalaryNum='<fmt:message key="SALARY_REQUIRED_NUMBER"/>';
		var sequence=<c:out value="${sequence}"/>
		var globalSalRequired='<fmt:message key="SALARY_REQUIRED"/>';
		var globalSalNum='<fmt:message key="SALARY_REQUIRED_NUMBER"/>';
		var globalSalaryLast='<fmt:message key="WORKEXP_LASTEST_SAL"/>';
		var globalEquivMarketPositionRequired='<fmt:message key="EQUIVALENT_MARKET_POSITION_REQUIRED"/>';
	</script>
	<script src="/js/bootstrap3-typeahead.min.js"></script>
	<script src="/js/experience.js?version=1"></script>
	<c:choose>
		<c:when test="${not empty resume}">
			<fmt:setLocale value="${resume.locale}"/>
		</c:when>
		<c:otherwise>
			<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
		</c:otherwise>
	</c:choose>
	<div class="seperator"></div>
	<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
	<p/>
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<p/>
	<form id="expStatusFrm" name="expStatusFrm">
		<input type="hidden" name="idResume" id="idResume" value="<c:out value="${resume.idResume}"/>" >
		<input type="hidden" name="service" id="service" value="setExpStatus"/> 
		<div class="form-group">
		    <p class="control-label caption_bold"><fmt:message key="WORKEXP_ASKEXP"/></p>
		    <div class="form-inline"> 
		        <div class="col-md-6 radio">
		       		<input type="radio" class="radio" name="expStatus" id="expStatusTrue" value="TRUE" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Check', eventLabel: 'มีประสบการณ์'});" />&nbsp;&nbsp;<label for="expStatusTrue" class="answer"><fmt:message key="GLOBAL_YES"/></label> 
		        </div>
		        <div class="col-md-6 radio">
		          	<input type="radio" class="radio" name="expStatus" id="expStatusFalse" value="FALSE" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Check', eventLabel: 'ไม่มีประสบการณ์'});" />&nbsp;&nbsp;<label for="expStatusFalse" class="answer"><fmt:message key="GLOBAL_NO"/></label>
		        </div>
		    </div>
		    <br/><br/>
	        <div id="noExpButtonLayer" style="display:block; margin:auto;">
				<div class="row alignCenter">
					<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" id="noExpButton"  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'ClickOn', eventLabel: 'ถัดไปไม่มีประสพการณ์'});"/>
				</div><br/><br/>
			</div>
		</div>
	</form>
	<div id="experienceLayer" style="display:none;">
		<form  id="expCompanyFrm" name="expCompanyFrm" class="form-horizontal">
			<input type="hidden" name="idResume" id="idResume" value="<c:out value="${resume.idResume}"/>" >
			<input type="hidden" name="service" id="service" value="setExpCompany"/>
			<div class="form-group">
			    <label class="caption_bold"><fmt:message key="WORKEXP_TELLTOTALEXP"/></label>
				<p class="caption"><font color="red">*</font><fmt:message key="WORKEXP_NUMBER_COM"/></p>
				<div class="row" id="expCompanyLayer">
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
						<select name="expCompany" id="expCompany" class="required form-control" title='<fmt:message key="GLOBAL_REQUIRE"/>&nbsp;<fmt:message key="WORKEXP_NUMBER_COM"/>'  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'จำนวนบริษัทที่เคยร่วมงาน'});">
							<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
							<c:forEach var="idx" begin="1" end="50">
								<option value="<c:out value='${idx}'/>"><c:out value="${idx}" /></option>
							</c:forEach>
						</select>&nbsp;&nbsp;
					</div>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
						<label for="expCompany" class="answer"><fmt:message key="WORKEXP_COMPANY"/></label>
					</div>
				</div>
			</div>
		</form>
		<div class="form-group">
			<label class="control-label caption_bold"><font color="red">*</font><fmt:message key="WORKEXP_INFIELD"/></label>
			<div id="jobFieldList"></div>
			<div id="addJobFieldLayer">
				<a data-toggle="modal" href="#jf" class="btn btn-default btn-sm" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'ClickOn', eventLabel: 'เพิ่มสายงาน'});" ><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="GLOBAL_ADD"/><fmt:message key="GLOBAL_JOBFIELD"/></a>
			</div>
		</div>
	  <!-- jobFieldDlg -->
		  <div class="modal fade" id="jf" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		    <div class="modal-dialog  modal-sm">
		      <div class="modal-content">
		        <div class="modal-header"  style="background-color:#f6b454;">
		          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		          <h4 class="modal-title" style="color:#ffffff;"><fmt:message key="GLOBAL_ADD"/><fmt:message key="GLOBAL_JOBFIELD"/></h4>
		        </div>
		        <div class="modal-body">
					<form  method="post" id="jobFieldFrm" name="jobFieldFrm">
						<input type="hidden" name="service" value="addJobField">
						<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>">
						<label class="control-label caption"><fmt:message key="GLOBAL_JOBFIELD"/></label>
						<select class="required form-control" id="idJobField" name="idJobField">	
						<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
							<c:forEach var="jobField" items="${jobFields}">
								<option value="<c:out value='${jobField.idField}'/>"><c:out value='${jobField.fieldName}'/></option>
							</c:forEach>
						</select>
						<br/>
						<label class="control-label caption"><fmt:message key="GLOBAL_YEAR"/></label>
						<select class="required form-control" id="expYear" name="expYear">	
						<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
						<c:forEach var="idx" begin="0" end="99">  
							<option value='<c:out value="${idx}" />'><c:out value="${idx}" /></option>
						</c:forEach>
						</select>
						<br/>
						<label class="control-label caption"><fmt:message key="GLOBAL_MONTH"/>&nbsp;&nbsp;</label>
						<select class="required form-control" id="expMonth" name="expMonth">	
						<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
						<c:forEach var="idx" begin="0" end="11">  
							<option value='<c:out value="${idx}" />'><c:out value="${idx}" /></option>
						</c:forEach>
						</select>
						<div class="modal-footer">
		          			<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
		          			<button type="submit" class="btn btn-primary"><fmt:message key="GLOBAL_ADD"/></button>
		       			</div>
					</form>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.jobFieldDlg -->
	
		<br/> 
		<form id="experienceFrm" name="experienceFrm" class="form-horizontal">
			<label class="control-label caption_bold"><fmt:message key="WORKEXP_TELLCURRENT"/></label><br/><br/>
			<input type="hidden" name="idWork" id="idWork" value='<c:out value="${idWorkLastest}"/>'>
			<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>">
			<input type="hidden" name="service" value="setExperience">
			<input type="hidden" name="partExpStatus" id="partExpStatus" value="" class="required" title="<fmt:message key="WORKEXP_ASKEXP"/>">
			<input type="hidden" name="partExpCompany" id="partExpCompany" value="" class="required" title="<fmt:message key="GLOBAL_REQUIRE"/>&nbsp;<fmt:message key="WORKEXP_NUMBER_COM"/>">
			<input type="hidden" name="partJobField" id="partJobField" value="" class="required" title="<fmt:message key="EXP_JOBFIELD_REQUIRED"/>">
			<div class="form-group">
				<label for="company" class="caption"><font color="red">*</font><fmt:message key="WORKEXP_COMNAME"/></label>
				<input type="text" id="company" name="company" class="form-control  required" title="<fmt:message key="COMPANY_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'TypeOn', eventLabel: 'ชื่อบริษัท'});">
			</div>
			<p/><p/>
			<div class="form-group" id="countryLayer">
				<label  class="caption" for="country"><font color="red">*</font><fmt:message key="GLOBAL_COUNTRY"/></label>
				<select name="idCountry" id="idCountry" class="require form-control" title="<fmt:message key="COUNTRY_REQUIRED"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'ประเทศ'});" >
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<c:forEach var="country" items="${countries}">
						<option value="<c:out value='${country.idCountry}'/>"><c:out value='${country.countryName}'/></option>
					</c:forEach>
				</select>
			</div>
			<p/><p/>
			<div class="form-group">
				<label  class="caption" for="idState"><font color="red">*</font><fmt:message key="GLOBAL_STATE"/></label>
				<div id="stateLayer" style="display:block;">
					<select name="idState" id="idState"  title="<fmt:message key="STATE_REQUIRED"/>" class="required form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'จังหวัด'});" >
						<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					</select>
				</div>
				<div id="otherStateLayer" style="display:none;">
					<input type="text" name="otherState" id="otherState" value=""  title="<fmt:message key="STATE_REQUIRED"/>">
				</div>
			</div>
			<p/><p/>
			<div class="form-group">
				<label class="caption_bold"><font color="red">*</font><fmt:message key="WORKEXP_INDUSTRY"/></label>&nbsp;<fmt:message key="WORKEXP_INDUSTRY_MAX"/>
				<div id="industryListLayer">
					<ol id="industryList"></ol>
				</div>
				<input type="hidden" name="partIndustry" id="partIndustry" value="">
			</div>
			<div id="addIndustryLayer">
				<div class="form-group">
					<select name="idIndustry" id="idIndustry" class="form-control" title="<fmt:message key="INDUSTRY_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'ประเภทธุรกิจประเภทงาน'});">
						<option value=""><fmt:message key='GLOBAL_SELECT'/></option>
						<c:forEach var="industry" items="${industries}">
							<option value="<c:out value='${industry.idIndustry}'/>"><c:out value='${industry.industryName}'/></option>
						</c:forEach>
						<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
					</select>
				</div>
				<div id="otherIndustryLayer" style="display: none;">
							<div><fmt:message key="GLOBAL_SPECIFY"/></div>
							<input type="text" id="otherIndustry" maxlength="100" name="otherIndustry"  style="width:280px;" value="" />
						</div>
				<br/>
				<div id="indLinkLayer" style="display: none;"><button type="button" id='indLink' class="btn btn-default"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="GLOBAL_ADD"/></button></div>
			</div>
			<p/><p/>
			<div class="form-group">
				<label class="caption_bold" for="jobType"><font color="red">*</font><fmt:message key="WORKEXP_TYPE"/></label>
				<select name="jobType" id="jobType" class="required form-control" title='<fmt:message key="JOBTYPE_REQUIRED"/>'   onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'ประเทภของงาน'});">
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<c:forEach var="jobType" items="${jobTypes}">
						<option value="<c:out value='${jobType.idType}'/>"><c:out value='${jobType.typeName}'/> </option>
					</c:forEach>
			 	</select>
		 	</div>
		 	<p/><p/>
		 	<div class="form-group">
			 	<label class="caption" for="workJobField"><font color="red">*</font><fmt:message key="GLOBAL_JOBFIELD"/></label>
			 	<select name="workJobField" id="workJobField"  class="required form-control" title='<fmt:message key="JOBFIELD_REQUIRED"/>'  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'สายงาน'});">
			 		<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<c:forEach var="jobfield" items="${jobFields}">
						<option value="<c:out value="${jobfield.idField}"/>"><c:out value="${jobfield.fieldName}"/></option>
					</c:forEach>
					<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
			   	</select>
			   	<div id="otherJobFieldLayer" style="display: none;">
					<label for="workJobFieldOther"><fmt:message key="GLOBAL_SPECIFY"/> </label>
					<input type="text" id="workJobFieldOther" name="workJobFieldOther" class="form-control" title='<fmt:message key="JOBFIELD_REQUIRED"/>'>
				</div>
		   	</div>
		   	
			<p/><p/>
			<div class="form-group">
				<label class="caption" for="workSubFieldName"><font color="red">*</font><fmt:message key="WORKEXP_EQUIVALENT"/></label>
				<input type="hidden" name="workSubField" id="workSubField">
				<input type="text" autocomplete = "off"  maxlength="255" name="workSubFieldName" id="workSubFieldName" class="required form-control typeahead " title="<fmt:message key="POSITION_REQUIRED"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'TypeOn', eventLabel: 'ตำแหน่ง'});" >
			</div>
			<p/><p/>
			<div class="form-group" id="relateSubfieldPosition" style="display: none">
				<div id="relateSubfieldPositionHead">
					<p class="caption"><b><fmt:message key="EQUIVALENT_MARKET_POSITION"/></b></p>
				</div>
				<div id="relateSubfieldPositionBody">
			
				</div>
			</div>
			<div class="form-group">
				<label class="caption" for="salaryLast"><font color="red">*</font><fmt:message key="WORKEXP_LASTEST_SAL"/></label>
				<div class="row">
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding-right:6px;">
						<div class="input-group-element">
							<input type="text" value="" class="required form-control" id="salaryLast" name="salaryLast" title="<fmt:message key="SALARY_REQUIRED"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'เงินเดือนล่าสุด'});">
						</div>
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-right:0;">
						<div class="input-group-element">
							<select name="currency" id="currency"  class="required form-control" title="<fmt:message key="SALARY_CURRENCY_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'หน่วยเงิน'});">
								<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
								<c:forEach var="currency" items="${currencies}">
									<option value="<c:out value='${currency.id }'/>"><c:out value='${currency.code }'/></option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" style="padding-left:6px;">
						<div class="input-group-element">
							<select name="salaryPer" id="salaryPer"  class="required form-control" title="<fmt:message key="SALARY_PER_REQUIRED"/>"   onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'หน่วยเวลาต่อเงินที่ได้'});">
								<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
								<option value="1"><fmt:message key="GLOBAL_PER_YEAR"/></option>
								<option value="2"><fmt:message key="GLOBAL_PER_MONTH"/></option>
								<option value="3"><fmt:message key="GLOBAL_PER_WEEK"/></option>
								<option value="4"><fmt:message key="GLOBAL_PER_BI_WEEK"/></option>
								<option value="5"><fmt:message key="GLOBAL_PER_DAY"/></option>
								<option value="6"><fmt:message key="GLOBAL_PER_HOUR"/></option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<p/><p/>
			<div class="form-group">
				<label class="caption" for="startMonth"><font color="red">*</font><fmt:message key="WORKEXP_START_DATE"/></label>
				<fmt:formatDate var="startMonth" value="${lastExp.workStart}" pattern="M"/>
				<fmt:formatDate var="startYear" value="${lastExp.workStart}" pattern="yyyy"/>
				<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
				<div class="row"> 
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
						<select name="startMonth" id="startMonth" class="required form-control" title="<fmt:message key="GLOBAL_START_MONTH_REQUIRED"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'เดือนที่เริ่มงาน'});">
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
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
						<select  name="startYear" id="startYear" class="required form-control" title="<fmt:message key="GLOBAL_START_YEAR_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'ปีที่เริ่มงาน'});">
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
			<p/><p/>
			<div class="form-group">
				<label class="caption" for="present"><font color="red">*</font><fmt:message key="WORKEXP_END_DATE"/></label>
				<div class="row">
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
						<input type="radio" <c:if test="${(not empty lastExp) and (lastExp.workingStatus ne 'TRUE')}">checked</c:if> name="present" value="FALSE" class="required" title='<fmt:message key="END_WORK_REQUIRED"/>'  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Check', eventLabel: 'เลือกวันสิ้นสุดการทำงานเอง'});">&nbsp;&nbsp;
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
						<fmt:formatDate var="endMonth" value="${lastExp.workEnd}" pattern="M"/>
						<fmt:formatDate var="endYear" value="${lastExp.workEnd}" pattern="yyyy"/>
						<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
						<select class="workEnd form-control input-sm" name="endMonth" id="endMonth" title="<fmt:message key="GLOBAL_END_MONTH_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'เดือนที่สิ้นสุดการทำงาน'});">
							<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
							<c:forEach var="i" begin="1" end="12">
								<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
								<c:choose>
									<c:when test="${(not empty lastExp) and (endMonth eq i) and (lastExp.workingStatus ne 'TRUE')}">
										<option value="<c:out value='${i}'/>" selected><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
						<select class="workEnd form-control input-sm" name="endYear" id="endYear" title="<fmt:message key="GLOBAL_END_YEAR_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Click-dropdown', eventLabel: 'ปีที่สิ้นสุดการทำงาน'});">
							<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
							<c:forEach var="i" begin="0" end="120">
								<c:choose>
									<c:when test="${resume.locale eq 'th_TH'}">
										<option value="<c:out value='${curYear-i}'/>" <c:if test="${(not empty lastExp) and (endYear eq (curYear-i+543)) and (lastExp.workingStatus ne 'TRUE')}">selected</c:if>>
											<c:out value="${curYear-i+543}"/>
										</option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value='${curYear-i}'/>" <c:if test="${(not empty lastExp) and (endYear eq (curYear-i)) and (lastExp.workingStatus ne 'TRUE')}">selected</c:if>>
										<c:out value="${curYear-i}"/>
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
						<input type="radio" <c:if test="${(not empty lastExp) and (lastExp.workingStatus eq 'TRUE')}">checked</c:if>  name="present"  id="present" value="TRUE" class="required" title='<fmt:message key="END_WORK_REQUIRED"/>' onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'Check', eventLabel: 'เลือกปัจจุบัน'});">&nbsp;&nbsp;
					</div>
					<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
						<label class="answer" for="present"><fmt:message key="WORKEXP_PRESENT"/></label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<p class="caption" for="jobDesc"><fmt:message key="WORKEXP_JOB_DESC"/></p>
				<textarea rows="5" cols="30" maxlength='1000' name="jobDesc" id="jobDesc" onkeyup="callMaxlengthEvent('jobDesc')"  class="form-control"></textarea>
				<span id='countChar_jobDesc'>0</span>&nbsp;<fmt:message key='STRENGTH_MAX_CHAR'/>
			</div>
		
			<br>
			
			<div class="form-group">
				<p class="caption" for="achieve"><fmt:message key="PREVIEW_WORKING_ACHIEVEMENT"/><b>(<fmt:message key="GLOBAL_OPTIONAL"/>)</b></p>
				<textarea rows="5" cols="30"  maxlength='1000' name="achieve" id="achieve" onkeyup="callMaxlengthEvent('achieve')" class="form-control"></textarea>
				<span id='countChar_achieve'>0</span>&nbsp;<fmt:message key='STRENGTH_MAX_CHAR'/>
			</div>
			<br><br>
	    		<div class="row alignCenter">
	    			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" id="submit"  onClick="ga('send', 'event', { eventCategory: 'Engage-experience', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"/>
	    		</div><br/><br/>
	    	<br><br>
		</form>
	</div>
</c:if>