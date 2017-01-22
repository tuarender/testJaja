<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.TargetJob"%>
<%@page import="com.topgun.resume.TargetJobManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.JobTypeManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<%@page import="com.topgun.shris.masterdata.JobField"%>
<%@page import="com.topgun.shris.masterdata.SubField"%>
<%@page import="com.topgun.shris.masterdata.State"%>
<%@page import="com.topgun.shris.masterdata.Industry"%>
<%@page import="com.topgun.shris.masterdata.Currency"%>
<%@page import="com.topgun.shris.masterdata.JobType"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	List<Country> countries=MasterDataManager.getAllCountry(resume.getIdLanguage());
	List<State> states=MasterDataManager.getAllState(resume.getIdCountry(), resume.getIdLanguage());
	List<JobField> jobFields = MasterDataManager.getAllJobField(resume.getIdLanguage());
	List<Industry> industries = MasterDataManager.getAllIndustry(resume.getIdLanguage());
	List<JobType> jobTypes=MasterDataManager.getAllJobType(resume.getIdLanguage());
	List<Currency> currencies=MasterDataManager.getAllCurrency();
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	String countryName=MasterDataManager.getCountry(resume.getApplyIdCountry(),resume.getIdLanguage()).getCountryName();
	TargetJob targetJob=new TargetJobManager().get(resume.getIdJsk(),resume.getIdResume());
	
	if(targetJob!=null)
	{
		request.setAttribute("targetJob",targetJob);
	}
	request.setAttribute("resume",resume);
	request.setAttribute("countryName",countryName);
	request.setAttribute("countries",countries);
	request.setAttribute("jobTypes",jobTypes);	
	request.setAttribute("states",states);	
	request.setAttribute("jobFields",jobFields);
	request.setAttribute("industries",industries);
	request.setAttribute("currencies",currencies);
	request.setAttribute("sequence",sequence);
	String backToView = Util.getStr(request.getParameter("backToView")).trim();
 %>
<fmt:setLocale value="${resume.locale}"/>
<script>
	var idResume=<c:out value="${resume.idResume}"/>;
	var idLanguage=<c:out value="${resume.idLanguage}"/>;
	var idCountry=<c:out value="${resume.applyIdCountry}"/>;
	var sequence=<c:out value="${sequence}"/>;
	var globalAdd='<fmt:message key="GLOBAL_ADD"/>';
	var globalOther='<fmt:message key="GLOBAL_OTHER"/>';
	var globalRequire='<font color="red"><fmt:message key="GLOBAL_REQUIRE"/></font>';
	var globalSelect='<fmt:message key="GLOBAL_SELECT"/>';
	var globalJobField='<fmt:message key="GLOBAL_JOBFIELD"/>';
	var globalSubField='<fmt:message key="GLOBAL_SUBFIELD"/>';
	var globalDelete='<fmt:message key="GLOBAL_DELETE"/>';
	var globalCancel='<fmt:message key="GLOBAL_CANCEL"/>';
	var globalAll='<fmt:message key="GLOBAL_ALL"/>';
	var globalJobtype='<fmt:message key="GLOBAL_REQUIRE"/><fmt:message key="TARGETJOB_TYPE"/>';
	var globalSalaryMin='<fmt:message key="SALARY_REQUIRED_MIN"/>';
	var globalSalaryNumMin='<fmt:message key="SALARY_REQUIRED_NUMBER_MIN"/>';
	var globalSalaryMax='<fmt:message key="SALARY_REQUIRED_MAX"/>';
	var globalSalaryNumMax='<fmt:message key="SALARY_REQUIRED_NUMBER_MAX"/>';
	var backToView = '<%=backToView%>';
	var provinceStr = '<fmt:message key="GLOBAL_PROVINCE" />';
	var industrialStr = '<fmt:message key="TARGET_INDUSTRIAL" />';
</script>
<script src="/js/targetJob.js"></script>
<div class="seperator"></div>
<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
<div class="section_header alignCenter">
   <h5 class="suggestion"><fmt:message key="DIRECTION_TARGETJOB"/></h5>
</div>
<div class="form-group">
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
</div>
<!-- START TARGET JOBTYPE -->
<font color="red">*</font><label class="caption"><fmt:message key="TARGETJOB_TYPE"/></label>
<form  method="post" id="jobTypePcFrm" name="jobTypePcFrm" class="form-horizontal">
	<input type="hidden" name="service" value="add">
	<input type="hidden" name="idResume" value="<c:out value="${param.idResume}"/>">
	<c:forEach var="jobType" items="${jobTypes}">
		<div class="row"> 
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="checkbox" name="idJobTypePc" id="<c:out value="${jobType.idType}"/>"  value="<c:out value="${jobType.idType}"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Check', eventLabel: 'ประเภทงานที่ต้องการ'});" > &nbsp;&nbsp;</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10"><label class="caption" for="<c:out value="${jobType.idType}"/>"><font class="answer"><c:out value="${jobType.typeName}"/></font></label></div>
		</div>
	</c:forEach>
</form>
<!-- START TARGET JOBFIELD -->
<p/>
<label  class="caption" ><font color="red">*</font><fmt:message key="TARGETJOB_TARGET_JOBFIELD"/>&nbsp;<fmt:message key="TARGETJOB_TARGET_JOBFIELD_REMARK"/></label><p/>
<div id="jobFieldList" class="form-group"></div>
<div id="addJobFieldLayer">
	<a  data-toggle="modal" href="#jf" id="jfLink" class="btn btn-default btn-sm" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'ClickOn', eventLabel: 'เพิ่มสายงานที่ต้องการ'});"  ><span class="glyphicon glyphicon-plus"></span><font class="answer">&nbsp;&nbsp;<fmt:message key="GLOBAL_ADD"/><fmt:message key="TARGETJOB_TARGET_JOBFIELD"/></font></a>
</div>
<!-- jobFieldDlg -->
<div class="modal fade" id="jf" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog  modal-sm">
		<div class="modal-content">
			<div class="modal-header"  style="background-color:#f6b454;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" style="color:#ffffff;"><fmt:message key="TARGETJOB_TARGET_JOBFIELD"/></h4>
			</div>
			<div class="modal-body">
				<form  method="post" id="jobFieldFrm" name="jobFieldFrm">
					<input type="hidden" name="service" value="add">
					<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>">
					<label  class="caption"><fmt:message key="GLOBAL_JOBFIELD"/></label>
					<p/>
					<div class="form-group" id="idJobFieldLayer">
						<select class="form-control" id="idJobField" name="idJobField">						
							<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
							<c:forEach var="jobField" items="${jobFields}">
								<option value="<c:out value='${jobField.idField}'/>">
								<c:out value='${jobField.fieldName}'/></option>
							</c:forEach>
							<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
						</select>
					</div>
					<div id="otherJobFieldLayer" style="display: none;">
						<span><fmt:message key="GLOBAL_SPECIFY"/></span>
						<input type="text" id="otherJobField" name="otherJobField" maxlength="100" class="form-control" value="" />
					</div>
					<div id="subFieldLayer" style="display:none;">
						<label  class="caption"><fmt:message key="GLOBAL_SUBFIELD"/></label>
						<p/>
						<div class="form-group">
							<select class="form-control"  id="idSubField" name="idSubField"  >						
								<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
							</select>
						</div>
						<div id="otherSubFieldLayer" style="display: none;">
							<fmt:message key="GLOBAL_SPECIFY"/> :
							<input type="text" id="otherSubField" maxlength="100" name="otherSubField" class="form-control" value="" />
						</div>
					</div>
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
<font color="red">*</font><label  class="caption_bold"><fmt:message key="TARGETJOB_INDUSTRY_WANTED"/></label>
<label  class="caption_bold"><fmt:message key="TARGETJOB_INDUSTRY_WANTED_REMARK"/></label><br><p/><p/>
<div id="industryList" class="form-group"></div>
<div class="row">
	<div class="col-xs-12 col-sm-6">
		<div id="addIndustryLayer">
			<a  data-toggle="modal" href="#Indus" id="indLink" class="btn btn-default btn-sm"  onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'ClickOn', eventLabel: 'เพิ่มประเภทธุรกิจที่ต้องการ'});"  ><span class="glyphicon glyphicon-plus"></span><font class="answer">&nbsp;&nbsp;<fmt:message key="GLOBAL_ADD"/><fmt:message key="TARGETJOB_INDUSTRY_WANTED"/></font></a>
		</div>
	</div>
</div>
<!-- IndusDlg -->
<div class="modal fade" id="Indus" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog  modal-sm">
		<div class="modal-content">
			<div class="modal-header"  style="background-color:#f6b454;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" style="color:#ffffff;"><fmt:message key="TARGETJOB_INDUSTRY_WANTED"/></h4>
			</div>
			<div class="modal-body">
				<form  method="post" id="industryFrm" name="industryFrm" class="form-horizontal">
					<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>" >
					<input type="hidden" name="service" value="add"/>
					<select name="idIndustry" id="idIndustry" class="form-control">
						<option value=""><fmt:message key='GLOBAL_SELECT'/></option>
						<c:forEach var="industry" items="${industries}">
							<option value="<c:out value='${industry.idIndustry}'/>"><c:out value='${industry.industryName}'/></option>
						</c:forEach>
						<option value="-1"><fmt:message key="GLOBAL_OTHER"/></option>
					</select>
					<div id="otherIndustryLayer" style="display: none;">
						<fmt:message key="GLOBAL_SPECIFY"/> :
						<input type="text" id="otherIndustry" maxlength="100" name="otherIndustry"  class="form-control" value="" />
					</div>
					<div class="modal-footer">
	          			<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
	          			<button type="submit" class="btn btn-primary"><fmt:message key="GLOBAL_ADD"/></button>
	       			</div>
				</form>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.IndusDlg -->
<p/><p/>
<label  class="caption"><font color="red">*</font><fmt:message key="TARGETJOB_EXPECTED_SALARY"/></label>
<form id="salaryFrm" name="salaryFrm" method="post">
	<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>" >
	<input type="hidden" name="service" value="salary"/>
	<div class="row">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="salaryChoice" value="1"  onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Check', eventLabel: 'เลือกเงินเดือนที่ต้องการ'});" ></div>
		<div class="col-lg-5 col-md-5 col-sm-10 col-xs-10">
			<div>
				<label for="minSalary" class="answer"><fmt:message key="SALARY_MIN"/></label>
				<input type="text" name="minSalary" id="minSalary" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'TypeOn', eventLabel: 'เงินเดือนต่ำที่สุด'});" > 
			</div>
			<div>
				<label for="maxSalary" class="answer"><fmt:message key="SALARY_MAX"/></label>
				<input type="text" name="maxSalary" id="maxSalary"  class="form-control"  onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'TypeOn', eventLabel: 'เงินเดือนสูงที่สุด'});" >
			</div>
			<div>
				<label for="currency" class="answer"><fmt:message key="SALARY_CURRENCY"/></label>
				<select name="currency" id="currency" class="form-control"  onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Click-dropdown', eventLabel: 'หน่วยเงิน'});">
				<c:forEach var="currency" items="${currencies}">
					<c:choose>
						<c:when test="${currency.id eq 140 and resume.idCountry eq 216}">
			   				<option value="<c:out value='${currency.id}'/>" selected><c:out value="${currency.code}" /></option>
			   			</c:when>
			   			<c:when test="${currency.id eq 62 and resume.idCountry eq 102}">
			   				<option value="<c:out value='${currency.id}'/>" selected><c:out value="${currency.code}" /></option>
			   			</c:when>
			   			<c:otherwise>
			   				<option value="<c:out value='${currency.id}'/>"><c:out value="${currency.code}" /></option>
			   			</c:otherwise>
			   		</c:choose>
				</c:forEach>
				</select>
			</div>
			<div>
				<label for="salaryPer" class="answer"><fmt:message key="SALARY_PERIOD"/></label>
				<select name="salaryPer" id="salaryPer" class="form-control"   onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Click-dropdown', eventLabel: 'period'});">
					<option value="1"><fmt:message key="GLOBAL_PER_YEAR"/></option>
					<option value="2" selected><fmt:message key="GLOBAL_PER_MONTH"/></option>
					<option value="3"><fmt:message key="GLOBAL_PER_WEEK"/></option>
					<option value="4"><fmt:message key="GLOBAL_PER_BI_WEEK"/></option>
					<option value="5"><fmt:message key="GLOBAL_PER_DAY"/></option>
					<option value="6"><fmt:message key="GLOBAL_PER_HOUR"/></option>
				</select>
			</div>
		</div>
	</div>
	<p/>
	<div class="row">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="salaryChoice" id="asalaryChoice" value="0"  onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Check', eventLabel: 'ต่อรองได้'});"></div>
		<div class="col-lg-5 col-md-5 col-sm-10 col-xs-10"><label for="asalaryChoice" class="answer"><fmt:message key="TARGET_NEGOTIABLE"/></label></div>
	</div>
	</form>
	<c:if test="${resume.idResume > 0}">
		<p/><p/>
		<div class="form-group">
			<label class="caption"><fmt:message key="TARGETJOB_RELOCATE"/></label>
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="relocate" id="rTrue"  value="TRUE"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="rTrue" class="answer"><fmt:message key="TARGETJOB_RELOCATE_YES"/></label></div>
			</div>
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="relocate"id="rfalse"  value="FALSE"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="rfalse" class="answer"><fmt:message key="TARGETJOB_RELOCATE_NO"/></label></div>
			</div>
		</div>
		<p/><p/>
		<div class="form-group">
			<label class="caption"><fmt:message key="TARGETJOB_DISIRED_TRAVEL"/></label>
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="travel" id="travel1" value="1"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="travel1" class="answer"><fmt:message key="TARGETJOB_NEGLIGIBLE"/></label></div>
			</div>
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="travel" id="travel2" value="2"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="travel2" class="answer"><fmt:message key="TARGETJOB_UPTO25"/></label></div>
			</div><div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="travel" id="travel3" value="3"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="travel3" class="answer"><fmt:message key="TARGETJOB_UPTO50"/></label></div>
			</div><div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="travel" id="travel4" value="4"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="travel4" class="answer"><fmt:message key="TARGETJOB_UPTO75"/></label></div>
			</div><div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="travel" id="travel5" value="5"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="travel5" class="answer"><fmt:message key="TARGETJOB_UPTO100"/></label></div>
			</div>
		</div>
		<p/><p/>
		<div class="form-group">
			<label class="caption"><fmt:message key="TARGETJOB_LEGALLY_ELIGIBLE"><fmt:param value="${countryName}"/></fmt:message></label>					
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="workPermit" id="workPermitTRUE" value="TRUE"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="workPermitTRUE" class="answer"><fmt:message key="TARGETJOB_ELIGIBLE_YES"/></label></div>
			</div>
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="workPermit" id="workPermitFALSE" value="FALSE"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="workPermitFALSE" class="answer"><fmt:message key="TARGETJOB_ELIGIBLE_NO"/></label></div>
			</div>
		</div>
	</c:if>	
		<p/>	<p/>
	<label class="caption"><font color="red">*</font><fmt:message key="TARGETJOB_LOCATION"><fmt:param value="${countryName}"/></fmt:message><p/><fmt:message key="TARGETJOB_UPTO20"/></label>
	<div id="locationList" style="width:100%; display:block;"></div>
	<div id="addLocationLayer">
		<a  data-toggle="modal" href="#loc" id="locLink" class="btn btn-default btn-sm"  onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'ClickOn', eventLabel: 'เพิ่มสถานที่ทำงาน'});" ><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<font class="answer"><fmt:message key="TARGETJOB_ADD_LOCATION"/></font></a>
	</div>
	<!-- locationDlg -->
	<div class="modal fade" id="loc" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog  modal-sm">
			<div class="modal-content">
				<div class="modal-header"  style="background-color:#f6b454;">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" style="color:#ffffff;"><fmt:message key="TARGETJOB_ADD_LOCATION"/></h4>
				</div>
				<div class="modal-body">
					<form  method="post" id="locationFrm" name="locationFrm" class="form-horizontal">
						<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>" >
						<input type="hidden" name="service" value="add"/>
						<div class="idRegionLayer">
							<select name="idRegion" class="form-control" id="idRegion">
								
							</select>
							<br>
						</div>
						<div class="idStateLayer" style="display:none;">
							<span class="caption" id="stateTopic"></span>
							<select name="idState" id="idState" class="form-control" >
								
							</select>
							<br>
						</div>
						<div id="cityLayer" style="display:none;">
							<span class="caption"><fmt:message key="GLOBAL_DISTRICT"/></span>
							<select name="idCity" id="idCity" class="form-control" >
								<option value=""><fmt:message key='GLOBAL_SELECT'/></option>
							</select>
							<br>
						</div>
						<div id="otherCityLayer" style="display:none;">
							<label class="caption"><fmt:message key="GLOBAL_DISTRICT"/></label>
							<input type="text" id="otherCity" name="otherCity" class="form-control" placeholder="<fmt:message key="GLOBAL_ALL"/>" maxlength="50" onfocus="clearAllCity()">
						</div>
						<div class="modal-footer">
    						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="clearLocationPopup();"><fmt:message key="GLOBAL_CANCEL"/></button>
    						<button type="submit" class="btn btn-primary"><fmt:message key="GLOBAL_ADD"/></button>
 						</div>
					</form>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.locationDlg -->
	<c:if test="${resume.idResume > 0}">
		<p/><p/>
		<div id="outsideLayer" style="display:block;">
			<label class="caption"><fmt:message key="TARGETJOB_ARE_YOU_WILLTO_WORK_OUTSIDE"><fmt:param value="${countryName}"/></fmt:message></label>
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="outside" id="out1"value="1"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="out1" class="answer"><fmt:message key="TARGETJOB_YES"/></label></div>
			</div>
			<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><input type="radio" name="outside" id="out2" value="0"></div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"><label for="out2" class="answer"><fmt:message key="TARGETJOB_NO"/></label></div>
			</div>
		</div>	<p/><p/>
		<div id="outsideLocationLayer" style="display:none; width:100%;">
			<label class="caption"><fmt:message key="TARGETJOB_LOCATION_OUTSIDE"><fmt:param value="${countryName}"/></fmt:message></label>
			<label class="caption"><fmt:message key="TARGETJOB_UPTO20"/></label>
			<div id="outsideLocationList" style="width:100%; display:block;"></div>	
			<div id="addOutsideLocationLayer">
				<a  data-toggle="modal" id="locOutButton" href="#locOut" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="TARGETJOB_ADD_LOCATION"/></a>
			</div>
			<!-- locOutDlg -->
			<div class="modal fade" id="locOut" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog  modal-sm">
					<div class="modal-content">
						<div class="modal-header"  style="background-color:#f6b454;">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" style="color:#ffffff;"><fmt:message key="TARGETJOB_ADD_LOCATION"/></h4>
						</div>
						<div class="modal-body">
							<form  method="post" id="outsideLocationFrm" name="outsideLocationFrm" class="form-horizontal">
								<input type="hidden" name="idResume" value="<c:out value="${resume.idResume}"/>" >
								<input type="hidden" name="service" value="add"/>
								<label class="caption"><fmt:message key="GLOBAL_COUNTRY"/></label>
								<p/>
								<select name="idCountry" id="idCountry" class="form-control">
 									<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
									<c:forEach var="country" items="${countries}">
										<c:if test="${resume.idCountry ne country.idCountry}">
											<option value="<c:out value='${country.idCountry}'/>">
												<c:out value='${country.countryName}'/>
											</option>
										</c:if>
									</c:forEach>
		    					</select>
								<label class="caption"><fmt:message key="GLOBAL_STATE"/></label>
								<div id="stateOutsideLayer">
									<select name="idStateOutside" id="idStateOutside" class="form-control">
  										<option value=""><fmt:message key='GLOBAL_SELECT'/></option>
  									</select>
  								</div>
    							<div id="otherStateOutsideLayer" style="display:none;">
  									<input type="text" id="otherStateOutside" name="otherStateOutside" class="form-control" maxlength="50">
  								</div>
 									<label class="caption"><fmt:message key="TARGETJOB_LEGALLY_ELIGIBLE_COUNTRY"/></label>
								<label for="outsideWorkPermit" class="error" style="display:none"></label>
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><input type="radio" name="outsideWorkPermit" id="oswp1" value="TRUE"></div>
									<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10"><label for="oswp1" class="answer"><fmt:message key="TARGETJOB_YES"/></label></div>
								</div>
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><input type="radio" name="outsideWorkPermit" id="oswp2"value="FALSE"></div>
									<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10"><label for="oswp2" class="answer"><fmt:message key="TARGETJOB_NO"/></label></div>
								</div>
  								<div class="modal-footer">
          							<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
          							<button type="submit" class="btn btn-primary"><fmt:message key="GLOBAL_ADD"/></button>
       							</div>
							</form>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			</div><!-- /.locOutDlg -->
		</div>
	</c:if>
	<label  class="caption"><font color="red">*</font><fmt:message key="TARGETJOB_AVAILABLE"/></label>
	<form id="noticeFrm" name="noticeFrm" method="post">
		<input type="hidden" name="idResume" id="idResume" value="<c:out value="${resume.idResume}"/>" >
		<input type="hidden" name="service" id="service" value="setNotice"/>
		<c:if test="${targetJob.startJobNotice eq -1}">
			<fmt:formatDate var="startDay" value="${targetJob.startJob}" pattern="d"/>
			<fmt:formatDate var="startMonth" value="${targetJob.startJob}" pattern="M"/>
			<fmt:formatDate var="startYear" value="${targetJob.startJob}" pattern="yyyy"/>
		</c:if>
		<c:set var="curYear" scope="request"><%=(Util.getCurrentYear()+5)%></c:set>
		<div class="row">
			<div class="col-sm-1 col-xs-2"><input type="radio" name="noticeStatus" id="noticeStatus0" value="0" class="radio" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Check', eventLabel: 'เลือกตัวเลือกอื่น'});">&nbsp;</div>
			<div class="row">
				<div class="col-xs-9 col-sm-6" style="padding:2px;" >
					<select name="notice" id="notice" class="form-control" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Click-dropdown', eventLabel: 'เลือกตัวเลือก'});" >
	     				 <option value="3"><fmt:message key="TARGET_1MONTH"/></option>
	     				 <option value="1"><fmt:message key="TARGET_1WEEK"/></option>
	     				 <option value="2"><fmt:message key="TARGET_2WEEK"/></option>
	     				 <option value="4"><fmt:message key="START_IMMEDIATELY"/></option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="inline">
				<div class="col-sm-1 col-xs-2"><input type="radio" name="noticeStatus" id="noticeStatus1" value="1" class="radio" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Check', eventLabel: 'เลือกใส่วันเริ่มงานเอง'});"></div>
				<div class="col-sm-2 col-xs-3" style="padding:1px;">
					<select name="startDay" id="startDay"  class="form-control startNotice required" title="<fmt:message key="GLOBAL_START_DAY_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Click-dropdown', eventLabel: 'วันที่เริ่มงานได้'});">
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
				<div class="col-sm-4 col-xs-4"  style="padding:1px;">
					<select  name="startMonth" id="startMonth" class="form-control startNotice required" title="<fmt:message key="GLOBAL_START_MONTH_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Click-dropdown', eventLabel: 'เดือนที่เริ่มงานได้้'});">
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
				<div class="col-sm-3 col-xs-3" style="padding:1px;">
					<select  name="startYear" id="startYear"  class="form-control startNotice required" title="<fmt:message key="GLOBAL_START_YEAR_REQUIRED"/>" onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'Click-dropdown', eventLabel: 'ปีที่เริ่มงานได้'});">
						<option value=""><fmt:message key="EDUCATION_YEAR"/></option>
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
		<p/>
		</form>
		<form id="mainFrm" name="mainFrm" method="post">
			<input type="hidden" name="partJobType" id="partJobType" class="required" title="<fmt:message key="JOBTYPE_REQUIRED"/>">
			<input type="hidden" name="partJobField" id="partJobField" class="required" title="<fmt:message key="JOBFIELD_REQUIRED"/>">
			<input type="hidden" name="partIndustry" id="partIndustry" class="required" title="<fmt:message key="INDUSTRY_REQUIRED"/>">
			<input type="hidden" name="partSalary" id="partSalary" class="required" title="<fmt:message key="SALARY_REQUIRED"/>">
			<c:if test="${resume.idResume > 0}">
				<input type="hidden" name="partRelocate" id="partRelocate" class="required" title="<fmt:message key="RELOCATE_REQUIRED"/>">
				<input type="hidden" name="partTravel" id="partTravel" class="required" title="<fmt:message key="TRAVEL_REQUIRED"/>">
				<input type="hidden" name="partWorkPermit" id="partWorkPermit" class="required" title="<fmt:message key="WORKPERMIT_REQUIRED"/>">
			</c:if>
			<input type="hidden" name="partInsideLocation" id="partInsideLocation" class="required" title="<fmt:message key="INSIDELOCATION_REQUIRED"/>">
			<c:if test="${resume.idResume > 0}">
				<input type="hidden" name="partWillWorkOutside" id="partWillWorkOutside" class="required" title="<fmt:message key="WILL_WORK_OUTSIDE_REQUIRED"/>">
				<input type="hidden" name="partOutsideLocation" id="partOutsideLocation" title="<fmt:message key="OUTSIDE_REQUIRED"/>">
			</c:if>
			<br/>	<br/>
			<input type="hidden" name="partNotice" id="partNotice" class="required" title="<fmt:message key="NOTICE_REQUIRED"/>">
			<div class="row alignCenter"><input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  onClick="ga('send', 'event', { eventCategory: 'Engage-TargetJob', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});" ></div>
			<br/><br/>
		</form>
		<c:set var="section" value="2" scope="request"/>
		<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>