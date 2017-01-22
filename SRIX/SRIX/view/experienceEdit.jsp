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
<% 
	Resume resume=(Resume)request.getAttribute("resume");
	List<JobField> jobFields =MasterDataManager.getAllJobField(resume.getIdLanguage());
	List<Country> countries =MasterDataManager.getAllCountry(resume.getIdLanguage());
	List<State> states =MasterDataManager.getAllState(resume.getIdCountry(),resume.getIdLanguage());
	List<Industry> industries =MasterDataManager.getAllIndustry(resume.getIdLanguage(),false);
	List<JobType> jobTypes=MasterDataManager.getAllJobType(resume.getIdLanguage());
	List<Currency> currencies=MasterDataManager.getAllCurrency();
	int sequence=Util.getInt(request.getParameter("sequence"));
	int idWork=Util.getInt(request.getParameter("idWork"),0);
	if(idWork>0)
	{
		WorkExperience workExp=new WorkExperienceManager().get(resume.getIdJsk(), resume.getIdResume(), idWork);
		if(workExp!=null)
		{
			request.setAttribute("workExp",workExp);
		}
	}
	request.setAttribute("idWork",idWork);
	request.setAttribute("jobFields",jobFields);
	request.setAttribute("countries",countries);
	request.setAttribute("states",states);	
	request.setAttribute("industries",industries);	
	request.setAttribute("jobTypes",jobTypes);	
	request.setAttribute("currencies",currencies);
	request.setAttribute("sequence",sequence);
	request.setAttribute("backToView", Util.getStr(request.getParameter("backToView")));
%>
<fmt:setLocale value="${resume.locale}"/>
<script>
	var idResume=<c:out value="${resume.idResume}"/>;
	var idLanguage=<c:out value="${resume.idLanguage}"/>;
	var idCountry=<c:out value="${resume.idCountry}"/>;
	var applyIdCountry=<c:out value="${resume.applyIdCountry}"/>;
	var expStatus='<c:out value="${resume.expStatus}"/>';
	var expCompany='<c:out value="${resume.expCompany}"/>';
	var sequence=<c:out value="${sequence}"/>;
	var globalAdd='<fmt:message key="GLOBAL_ADD"/>';
	var globalOther='<fmt:message key="GLOBAL_OTHER"/>';
	var globalRequire='<fmt:message key="GLOBAL_REQUIRE"/>';
	var globalSelect='<fmt:message key="GLOBAL_SELECT"/>';
	var globalJobField='<fmt:message key="GLOBAL_JOBFIELD"/>';
	var globalSubField='<fmt:message key="GLOBAL_SUBFIELD"/>';
	var globalDelete='<fmt:message key="GLOBAL_DELETE"/>';
	var globalCancel='<fmt:message key="GLOBAL_CANCEL"/>';
	var globalAll='<fmt:message key="GLOBAL_ALL"/>';
	var globalYear='<fmt:message key="GLOBAL_YEAR"/>';
	var globalMonth='<fmt:message key="GLOBAL_MONTH"/>';
	var globalTotalExp='<fmt:message key="WORKEXP_TOTALNUMBER"/>';
	var globalSalNum='<fmt:message key="SALARY_REQUIRED_NUMBER"/>';
	var globalSalRequired='<fmt:message key="SALARY_REQUIRED"/>';
	var globalEquivMarketPositionRequired='<fmt:message key="EQUIVALENT_MARKET_POSITION_REQUIRED"/>';
	var idWork=<c:out value="${idWork}"/>;
	var backToView='<c:out value="${backToView}"/>';
	var globalIndustryReq = '<fmt:message key="INDUSTRY_REQUIRED"/>';
</script>
<script src="/js/bootstrap3-typeahead.min.js"></script>
<script src="/js/experienceEdit.js?version=1"></script>
<div class="section_header alignCenter">
	<h3><fmt:message key="GLOBAL_EXPERIENCE"/></h3>
</div>
<h5 class="section_header"><fmt:message key="EXP_DETAIL"/></h5>
<p/>
<div class="errorContainer">
	<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
	<ol></ol>
</div>
<br/>

<label class="caption_bold"><fmt:message key="WORKEXP_TELLCURRENT_EDIT"/></label>
<form id="experienceFrm" name="experienceFrm" class="form-horizontal">
	<input type="hidden" name="idWork" id="idWork" value="<c:out value="${param.idWork}"/>">
	<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>">
	<input type="hidden" name="service" value="setExperience">
	
	<div class="form-group">
		<label class="caption" for="company"><p class="caption"><b><fmt:message key="WORKEXP_COMNAME"/></b></p></label>
		<input type="text" id="company" name="company" value="" class="required form-control" title='<fmt:message key="COMPANY_REQUIRED"/>'>
	</div>
	
	<div class="form-group">
		<label class="caption"><b><fmt:message key="GLOBAL_COUNTRY"/></b></label>
		<select name="idCountry" id="idCountry"   class="required form-control" title='<fmt:message key="COUNTRY_REQUIRED"/>'>
			<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
			<c:forEach var="country" items="${countries}">
				<option value="<c:out value='${country.idCountry}'/>"><c:out value='${country.countryName}'/></option>
			</c:forEach>
		</select>
	</div>
	
	<div class="form-group">
		<label class="caption"><b><fmt:message key="GLOBAL_STATE"/></b></label>	
		<div id="stateLayer" style="display:block;">
			<select name="idState" id="idState" title="<fmt:message key="STATE_REQUIRED"/>" class="required form-control">
				<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
			</select>
		</div>
		
	</div>
	
	<div class="form-group">
		<div class="caption" id="otherStateLayer" style="display:none;">
			<input type="text" name="otherState" id="otherState" value="" title="<fmt:message key="STATE_REQUIRED"/>" class="form-control" >
		</div>
	</div>

	<br/>
	<div class="form-group">
		<input type="hidden" name="partIndustry" id="partIndustry" value="" >
		<label class="caption"><fmt:message key="WORKEXP_INDUSTRY"/>&nbsp;<fmt:message key="WORKEXP_INDUSTRY_MAX"/></label>
		<div id="industryListLayer">
			<ol id="industryList"></ol>
		</div>
	</div>
		
	<div id="addIndustryLayer">
		<div class="form-group">
			<select name="idIndustry" id="idIndustry" class="form-control" title="<fmt:message key="INDUSTRY_REQUIRED"/>">
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
	
	
		<br/><br/>
	<div class="form-group">
		<p class="caption" for="comBusiness"><fmt:message key="WORKEXP_COM_DESC"/></p>
		<textarea rows="5" cols="30" class="form-control" maxlength='1000' name="comBusiness" id="comBusiness" onkeyup="callMaxlengthEvent('comBusiness')"></textarea>
		<span id='countChar_comBusiness' class="caption">0</span>&nbsp;<label class="caption"><fmt:message key='STRENGTH_MAX_CHAR'/></label>
	</div>
	
	<br/><br/>
	
	<div class="form-group">
		<p class="caption"><fmt:message key="WORKEXP_EMP"/></p>
		<select name="comSize" id="comSize" class="form-control" >
			<option value=""><fmt:message key="GLOBAL_SELECT"/></OPTION>
			<option value="1">1-15 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="2">15-30 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="3">30-50 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="4">50-100 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="5">100-150 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="6">150-300 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="7">300-500 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="8">500-1000 <fmt:message key="PREVIEW_WORKING_EMPLOYEES"/></option>
			<option value="9">1000 <fmt:message key="PREVIEW_WORKING_UP_EMPLOYEES"/></option>
		</select>
	</div>
	
	<div class="form-group">
		<label class="caption" for="jobType"><b><fmt:message key="WORKEXP_TYPE"/></b></label>
		<select name="jobType" id="jobType" class="required form-control"  title='<fmt:message key="JOBTYPE_REQUIRED"/>'>
			<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
			<c:forEach var="jobType" items="${jobTypes}">
				<option value="<c:out value='${jobType.idType}'/>"><c:out value='${jobType.typeName}'/> </option>
			</c:forEach>
		</select>
	</div>
	
	<div class="form-group">
		<label class="caption" for="workJobField"><b><fmt:message key="GLOBAL_JOBFIELD"/></b></label>
		<select name="workJobField" id="workJobField"  class="required form-control" title='<fmt:message key="JOBFIELD_REQUIRED"/>'>
			<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
			<c:forEach var="jobfield" items="${jobFields}">
				<option value="<c:out value="${jobfield.idField}"/>"><c:out value="${jobfield.fieldName}"/></option>
			</c:forEach>
			<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
		</select>
		<div id="otherJobFieldLayer" style="display: none;">
	   		<br>
			<label for="workJobFieldOther" for="workJobFieldOther"><fmt:message key="GLOBAL_SPECIFY"/></label>
			<input type="text" id="workJobFieldOther" name="workJobFieldOther" class="form-control" value="<c:out value="${workExp.workJobFieldOth}"/>" title='<fmt:message key="JOBFIELD_REQUIRED"/>'>
		</div>
	</div>

	<div class="form-group">
		<label class="caption"><fmt:message key="WORKEXP_EQUIVALENT"/></label>
		<input type="hidden" name="workSubField" id="workSubField">
		<input type="text" name="workSubFieldName" id="workSubFieldName"  disabled="true"  class="required form-control" title="<fmt:message key="POSITION_REQUIRED"/>" >

	</div>
	
	<div class="form-group">
		<div id="relateSubfieldPosition" style="display: none">
			<div id="relateSubfieldPositionHead">
				<p class="caption"><b><fmt:message key="EQUIVALENT_MARKET_POSITION"/></b></p>
			</div>
			<div id="relateSubfieldPositionBody">
		
			</div>
		</div>
	</div>

	<div class="form-group">
		<p class="caption" for="subordinate"><fmt:message key="WORKEXP_SUBORDINATE"/></p>
		<div class="form-inline">
			<input type="text" id="subordinate" name="subordinate" class="form-control" >&nbsp;&nbsp;<label for="subordinate" class="caption"><fmt:message key="WORKEXP_PERSON"/></label>
		</div>
	</div>
	
	<div class="form-group">
		<p class="caption"><b><fmt:message key="WORKEXP_LASTEST_SAL"/></b></p>
		<div class="row">
			<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding-right:6px;">
				<div class="input-group-element">
					<input type="text" value="" class="required form-control" id="salaryLast" name="salaryLast" title="<fmt:message key="SALARY_REQUIRED"/>"  >
				</div>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-right:0;">
				<div class="input-group-element">
					<select name="currency" id="currency"  class="required form-control" title="<fmt:message key="SALARY_CURRENCY_REQUIRED"/>" >
						<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
						<c:forEach var="currency" items="${currencies}">
							<option value="<c:out value='${currency.id }'/>"><c:out value='${currency.code }'/></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" style="padding-left:6px;">
				<div class="input-group-element">	
					<select name="salaryPer" id="salaryPer"  class="required form-control" title="<fmt:message key="SALARY_PER_REQUIRED"/>" >
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
	
	<br/><br/>
	
	<div class="form-group">
		<label class="caption" for="startMonth"><fmt:message key="WORKEXP_START_DATE"/></label>
		<fmt:formatDate var="startMonth" value="${workExp.workStart}" pattern="M"/>
		<fmt:formatDate var="startYear" value="${workExp.workStart}" pattern="yyyy"/>
		<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
		<div class="row"> 
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
				<select name="startMonth" id="startMonth" class="required form-control" title="<fmt:message key="GLOBAL_START_MONTH_REQUIRED"/>">
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
				<select  name="startYear" id="startYear" class="required form-control" title="<fmt:message key="GLOBAL_START_YEAR_REQUIRED"/>">
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
	
	<br/>
	<div class="form-group">
		<label class="caption" for="present"><fmt:message key="WORKEXP_END_DATE"/></label>
		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<input type="radio" <c:if test="${(not empty workExp) and (workExp.workingStatus ne 'TRUE')}">checked</c:if> name="present" value="FALSE" class="required radio" title='<fmt:message key="END_WORK_REQUIRED"/>'>&nbsp;&nbsp;
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<fmt:formatDate var="endMonth" value="${workExp.workEnd}" pattern="M"/>
				<fmt:formatDate var="endYear" value="${workExp.workEnd}" pattern="yyyy"/>
				<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
				<select class="workEnd form-control input-sm" name="endMonth" id="endMonth" title="<fmt:message key="GLOBAL_END_MONTH_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
					<c:forEach var="i" begin="1" end="12">
						<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
						<c:choose>
							<c:when test="${(not empty workExp) and (endMonth eq i) and (workExp.workingStatus ne 'TRUE')}">
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
				<select class="workEnd form-control input-sm" name="endYear" id="endYear" title="<fmt:message key="GLOBAL_END_YEAR_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
					<c:forEach var="i" begin="0" end="120">
						<c:choose>
							<c:when test="${resume.locale eq 'th_TH'}">
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${(not empty workExp) and (endYear eq (curYear-i+543)) and (workExp.workingStatus ne 'TRUE')}">selected</c:if>>
									<c:out value="${curYear-i+543}"/>
								</option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${(not empty workExp) and (endYear eq (curYear-i)) and (workExp.workingStatus ne 'TRUE')}">selected</c:if>>
								<c:out value="${curYear-i}"/>
								</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<input type="radio" <c:if test="${(not empty workExp) and (workExp.workingStatus eq 'TRUE')}">checked</c:if>  id="presentTrue" name="present" value="TRUE" class="required radio" title='<fmt:message key="END_WORK_REQUIRED"/>'>&nbsp;&nbsp;
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<label for="presentTrue" class="answer"><fmt:message key="WORKEXP_PRESENT"/></label>
			</div>
		</div>
	</div>
	
	<br>
	
	<div class="form-group">
		<p class="caption" for="jobDesc"><fmt:message key="WORKEXP_JOB_DESC"/></p>
		<textarea rows="5" cols="30" maxlength='1000' name="jobDesc" id="jobDesc" onkeyup="callMaxlengthEvent('jobDesc')"  class="form-control"></textarea>
		<span id='countChar_jobDesc'>0</span>&nbsp;<fmt:message key='STRENGTH_MAX_CHAR'/>
	</div>

	<br>
	
	<div class="form-group">
		<p class="caption" for="achieve"><fmt:message key="WORKEXP_JOB_ACHIEVE"/></p>
		<textarea rows="5" cols="30"  maxlength='1000' name="achieve" id="achieve" onkeyup="callMaxlengthEvent('achieve')" class="form-control"></textarea>
		<span id='countChar_achieve' class="caption">0</span>&nbsp;<label class="caption"><fmt:message key='STRENGTH_MAX_CHAR'/></label>
	</div>
	
	
	<br/><br/>
	
	<div class="form-group">
		<label class="caption"><fmt:message key="WORKEXP_START_POS"/></label>
	</div>
	
	<div class="form-group">
		<p class="caption"><fmt:message key="GLOBAL_JOBFIELD"/></p>
		<select name="workJobFieldStart" id="workJobFieldStart" class="form-control" title="">
			<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
			<c:forEach var="jobfield" items="${jobFields}">
				<option value="<c:out value="${jobfield.idField}"/>"><c:out value="${jobfield.fieldName}"/></option>
			</c:forEach>
			<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
		</select>
		<div id="otherJobFieldStartLayer" style="display: none;">
			<br>
			<p class="caption"><fmt:message key="GLOBAL_SPECIFY"/> :</p>
			<input type="text" id="workJobFieldStartOther" name="workJobFieldStartOther" class="form-control">
		</div>
	</div>
	
	<div class="form-group">
		<p class="caption"><fmt:message key="WORKEXP_EQUIVALENT"/></p>
		<input type="hidden" name="workSubFieldStart" id="workSubFieldStart">
		<input class="form-control" type="text" name="workSubFieldStartName" id="workSubFieldStartName"  disabled="true" title="<fmt:message key="POSITION_REQUIRED"/>">
		<div id="relateSubfieldStartPosition" style="display: none">
			<div id="relateSubfieldStartPositionHead">
				<p class="caption"><fmt:message key="EQUIVALENT_MARKET_POSITION"/></p>
			</div>
			<div id="relateSubfieldStartPositionBody">
		
			</div>
		</div>
	</div>
	
	<div class="form-group">
		<p class="caption"><fmt:message key="WORKEXP_START_SAL"/></p>
		<div class="row">
			<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5" style="padding-right:6px;">
				<input type="text" value="" class="form-control col-md-6" id="salaryStart" name="salaryStart" >
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding-right:0px;">
				<select name="currencyStart" id="currencyStart"  class="required form-control col-md-3" title="<fmt:message key="SALARY_CURRENCY_REQUIRED"/>" >
					<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
					<c:forEach var="currency" items="${currencies}">
						<option value="<c:out value='${currency.id }'/>"><c:out value='${currency.code }'/></option>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" style="padding-left:6px;">
				<select name="salaryPerStart" id="salaryPerStart"  class="required form-control col-md-3" title="<fmt:message key="SALARY_PER_REQUIRED"/>" >
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
	
	<div class="form-group">
		<p class="caption" for="reasonQuit"><fmt:message key="WORKEXP_REASON_LEAVE"/></p>
		<textarea rows="5" cols="30" maxlength='1000' name="reasonQuit" id="reasonQuit" onkeyup="callMaxlengthEvent('reasonQuit')" class="form-control"></textarea>
		<span id='countChar_reasonQuit' class="caption">0</span>&nbsp;<label class="caption"><fmt:message key='STRENGTH_MAX_CHAR'/></label>
	</div>
	
	<br/>
	<div class="row alignCenter"><button type="submit" class="btn btn-lg btn-success"><fmt:message key="GLOBAL_NEXT"/></button></div><br/><br/>
</form>
<br/><br/><br/>
<c:set var="section" value="4" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>