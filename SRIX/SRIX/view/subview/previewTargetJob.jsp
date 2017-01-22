<%@page import="com.topgun.util.PropertiesManager"%>
<%@page import="com.topgun.resume.LocationManager"%>
<%@page import="com.topgun.resume.Location"%>
<%@page import="com.topgun.resume.TargetJobFieldManager"%>
<%@page import="com.topgun.resume.TargetJobField"%>
<%@page import="com.topgun.shris.masterdata.JobField"%>
<%@page import="com.topgun.resume.IndustryManager"%>
<%@page import="com.topgun.resume.Industry"%>
<%@page import="com.topgun.resume.JobType"%>
<%@page import="com.topgun.resume.JobTypeManager"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.TargetJob"%>
<%@page import="com.topgun.resume.TargetJobManager"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<%
	PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	List<TargetJobField> jskTargetjob=null;
	List<Industry> jskIndustry=null;
	List<Location> locList=null;
	List<Location>locOutsideList=null;
	List<JobType> jskJobtype=null;
	if(resume!=null)
	{	
		String ln=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String ct=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		String targetcountryName=MasterDataManager.getCountry(resume.getApplyIdCountry(), resume.getIdLanguage()).getCountryName();
		TargetJob tarExt=new TargetJobManager().get(idJsk, idResume);
		jskJobtype=new JobTypeManager().getAll(idJsk, idResume);
		jskIndustry=new IndustryManager().getAll(idJsk, idResume);
		jskTargetjob=new TargetJobFieldManager().getAll(idJsk, idResume);
		locList=new LocationManager().getAllInsideLocationRegionGroup(idJsk, idResume, resume.getIdCountry());
		locOutsideList=new LocationManager().getAllOutsideLocation(idJsk, idResume, resume.getIdCountry());
		String startJobString = "";
		
		if(tarExt!=null)
		{
			if(tarExt.getStartJob()!=null )
			{
			 	if(resume.getIdLanguage() == 23) // japanese
				{
					startJobString=Util.getLocaleDateFormatFull(tarExt.getStartJob(), "FULL", ln,ct);
				}
				else  
				{
					startJobString=Util.getLocaleDateFormatFull(tarExt.getStartJob(),"MEDIUM",ln,ct);
				}
			}
			else 
			{
				if(tarExt.getStartJobNotice() == 1)
				{
					startJobString=propMgr.getMessage(resume.getLocale(), "TARGET_1WEEK");
				}
				else  if(tarExt.getStartJobNotice() == 2)
				{
					startJobString=propMgr.getMessage(resume.getLocale(), "TARGET_2WEEK");
				}
				else  if(tarExt.getStartJobNotice() == 3)
				{
					startJobString=propMgr.getMessage(resume.getLocale(), "TARGET_1MONTH");
				}
				else if(tarExt.getStartJobNotice() == 4)
				{
					startJobString=propMgr.getMessage(resume.getLocale(), "START_IMMEDIATELY");
				}
			}
			if(tarExt.getTravel()>0)
			{
				String desiredTravel=MasterDataManager.desiredTravel(tarExt.getTravel());
				request.setAttribute("desiredTravel",propMgr.getMessage(resume.getLocale(),desiredTravel));
			}
			if(tarExt.getExpectedSalaryPer()>0)
			{
				request.setAttribute("salaryPer",propMgr.getMessage(resume.getLocale(),MasterDataManager.getSalaryPer(tarExt.getExpectedSalaryPer(), resume.getIdLanguage()).getName()));
			}
			if(tarExt.getSalaryCurrency()>0)
			{
				request.setAttribute("salaryCurrency",MasterDataManager.getCurrency(tarExt.getSalaryCurrency()).getName());
			}
		}
		request.setAttribute("resume",resume);
		request.setAttribute("targetcountryName",targetcountryName);
		request.setAttribute("tarExt",tarExt);
		request.setAttribute("locOutsideList",locOutsideList);
		request.setAttribute("startJobString",startJobString);
		request.setAttribute("locOutsideListSize",locOutsideList.size());
		request.setAttribute("idResume",resume.getIdResume());
		request.setAttribute("jskJobtypeList",jskJobtype);
		request.setAttribute("jskTargetjobList",jskTargetjob);
		request.setAttribute("jskIndustryList",jskIndustry);
		request.setAttribute("locList",locList);
			
	}
	request.setAttribute("backToView", Util.getStr(request.getParameter("backToView")));
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<div class="row" style="padding:10pt;">
		<div class="col-xs-12 col-sm-12 caption">
			<label for="่jobType"><fmt:message key="TARGETJOB_TYPE"/></label>
		</div>
		<c:if test="${not empty jskJobtypeList}">
			<div class='col-xs-12 col-sm-12 answer'>
				<c:forEach items="${jskJobtypeList}" var="jskJobtype" varStatus="loop">
					<c:if test="${not empty jskJobtype}">
						<c:set var="jobtype" scope="request" value="${jskJobtype}"/>	
						<%
							JobType jt = (JobType)request.getAttribute("jobtype");
							com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(jt.getJobType(), resume.getIdLanguage());
							request.setAttribute("typeName",aJobbtype!=null?aJobbtype.getTypeName():"");
						%>
						<c:choose>
							<c:when test="${loop.index eq 0}">
								- <c:out value='${typeName}'/>
							</c:when>
							<c:otherwise>
								,<c:out value='${typeName}'/>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</div>
		</c:if>
		
		<div class="col-xs-12 col-sm-12 caption">
			<label for="expectedSalary"><fmt:message key="TARGETJOB_EXPECTED_SALARY"/></label>
		</div>
		<div class='col-xs-12 col-sm-12 answer'>
			<c:choose>
				<c:when test="${tarExt.minExpectedSalary eq -1 && tarExt.maxExpectedSalary eq -1 }" >
					<fmt:message key="TARGET_NEGOTIABLE"/>
				</c:when>
				<c:otherwise>
					<c:if test="${tarExt.minExpectedSalary > 0}">
						<fmt:parseNumber value='${tarExt.minExpectedSalary}' var='parsedAmount'pattern='###,###' type="number"/> 
						<fmt:formatNumber value='${parsedAmount}' pattern="###,###"/> 
					</c:if>
					<c:if test="${tarExt.maxExpectedSalary > 0}">
						<fmt:parseNumber value='${tarExt.maxExpectedSalary}' var='parsedAmount'pattern='###,###' type="number"/> 
						- <fmt:formatNumber value='${parsedAmount}' pattern="###,###"/> 
					</c:if>
					<c:out value='${salaryCurrency}'/>&nbsp;<c:out value='${salaryPer}'/>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="col-xs-12 col-sm-12 caption">
			<label for="targetJobfield"><fmt:message key="PREVIEW_TARGET_JOBFIELD"/><fmt:message key="GLOBAL_RANKED_IN_ORDER"/></label>
		</div>
		<c:if test="${not empty jskTargetjobList}">
			<div class='col-xs-12 col-sm-12 answer'>
				<ol style='padding-left:18;  margin-top:0px;'>
					<c:forEach items="${jskTargetjobList}" var="jskTargetjob" varStatus="loop">
						<c:if test="${not empty jskTargetjob}">
							<c:set var="targetjob" scope="request" value="${jskTargetjob}"/>
							<%
								TargetJobField tj = (TargetJobField)request.getAttribute("targetjob");
								com.topgun.shris.masterdata.JobField aJobField=MasterDataManager.getJobField(tj.getIdJobfield(), resume.getIdLanguage());
								request.setAttribute("fieldName",aJobField!=null?aJobField.getFieldName():"");
								com.topgun.shris.masterdata.SubField aSubField=MasterDataManager.getSubField(tj.getIdJobfield(), tj.getIdSubfield(), resume.getIdLanguage());
								request.setAttribute("subfieldName",aSubField!=null?aSubField.getSubfieldName():"");
							%>
							<li>
								<c:choose>
									<c:when test="${jskTargetjob.idJobfield gt 0}">
										<c:if test="${not empty fieldName}">
											<c:out value='${fieldName}'/>
										</c:if>
										<c:choose>
											<c:when test="${jskTargetjob.idSubfield gt 0}">
												<c:if test="${not empty subfieldName}">
													- <c:out value='${subfieldName}'/>
												</c:if>
											</c:when>
											<c:otherwise>
												- <c:out value='${jskTargetjob.otherSubfield}'/>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:out value='${jskTargetjob.otherJobfield}'/>
										- <c:out value='${jskTargetjob.otherSubfield}'/>
									</c:otherwise>
								</c:choose>
							</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</c:if>
		
		<div class="col-xs-12 col-sm-12 caption">
			<label for="targetIndustry"><fmt:message key="PREVIEW_INDUSTRY_WANTED"/><fmt:message key="GLOBAL_RANKED_IN_ORDER"/></label>
		</div>
		<c:if test="${not empty jskIndustryList}">
			<div class='col-xs-12 col-sm-12 answer'>
				<ol style='padding-left:18;  margin-top:0px;'>
					<c:forEach items="${jskIndustryList}" var="jskIndustry" varStatus="loop">
						<c:if test="${not empty jskIndustry}">
							<c:set var="industry" scope="request" value="${jskIndustry}"/>
							<% 
								Industry indus = (Industry)request.getAttribute("industry");
								com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(indus.getIdIndustry(), resume.getIdLanguage());
								request.setAttribute("indusName",aInsdus!=null?aInsdus.getIndustryName():"");
							%>
							<c:choose>
								<c:when test="${jskIndustry.idIndustry gt 0}">
									<c:if test="${not empty indusName}">
										<li><c:out value='${indusName}'/></li>
									</c:if>
								</c:when>
								<c:otherwise>
									<li><c:out value='${jskIndustry.otherIndustry}'/></li>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</c:if>
		
		<div class="col-xs-12 col-sm-12 caption">
			<label for="locateIn"><fmt:message key="PREVIEW_LOCATIONINSIDE"/></label>
		</div>
		<c:if test="${not empty locList}">
			<div class='col-xs-12 col-sm-12 answer'>
				<ol style='padding-left:18;  margin-top:0px;'>
					<c:forEach items="${locList}" var="loc" varStatus="loop">
						<c:if test="${not empty loc}">
							<li>
								<c:out value="${loc.stateName}"/>
							</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</c:if>
		
		<div class="col-xs-12 col-sm-12 caption">
			<label for="legally"><fmt:message key="TARGETJOB_LEGALLY_ELIGIBLE"><fmt:param value="${targetcountryName}"/></fmt:message></label>
		</div>
		<div class='col-xs-12 col-sm-12 answer'>
			<c:if test="${tarExt.workPermit eq 'TRUE'}">- <fmt:message key="TARGETJOB_ELIGIBLE_YES"/></c:if>
			<c:if test="${tarExt.workPermit eq 'FALSE'}">- <fmt:message key="TARGETJOB_ELIGIBLE_NO"/></c:if>
		</div>
		
		<div class="col-xs-12 col-sm-12 caption">
			<label for="relocate"><fmt:message key="TARGETJOB_RELOCATE"/></label>
		</div>
		<div class='col-xs-12 col-sm-12 answer'>
			<c:if test="${tarExt.relocate eq 'TRUE'}">- <fmt:message key="TARGETJOB_RELOCATE_YES"/></c:if>
			<c:if test="${tarExt.relocate eq 'FALSE'}">- <fmt:message key="TARGETJOB_RELOCATE_NO"/></c:if>
		</div>
		
		<div class="col-xs-12 col-sm-12 caption">
			<label for="desiredTravel"><fmt:message key="TARGETJOB_DISIRED_TRAVEL"/></label>
		</div>
		<div class='col-xs-12 col-sm-12 answer'>
			- <c:out value="${desiredTravel}"/>
		</div>
		
		<c:if test="${locOutsideListSize gt 0}">
			<div class="col-xs-12 col-sm-12 caption">
				<label for="locateOut"><fmt:message key="PREVIEW_LOCATIONOUTSIDE"/></label>
			</div>
			<div class='col-xs-12 col-sm-12 answer'>
				<ol style='padding-left:18;  margin-top:0px;'>
					<c:forEach items="${locOutsideList}" var="loc" varStatus="loop">
						<c:if test="${not empty loc}">
							<c:set var="locate" scope="request" value="${loc}"/>
							<% 
								Location locOut = (Location)request.getAttribute("locate");
								com.topgun.shris.masterdata.Country aCountry=MasterDataManager.getCountry(locOut.getIdCountry(), resume.getIdLanguage());
								request.setAttribute("countryName",aCountry!=null?aCountry.getCountryName():"");
								com.topgun.shris.masterdata.State aState=MasterDataManager.getState(locOut.getIdCountry(), locOut.getIdState(),resume.getIdLanguage());
								request.setAttribute("stateName",aState!=null?aState.getStateName():"");
								com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(locOut.getIdCountry(), locOut.getIdState(), locOut.getIdCity(), resume.getIdLanguage());
								request.setAttribute("cityName",aCity!=null?aCity.getCityName():"");
							%>
							<li>
								<c:choose>
									<c:when test="${not empty countryName}">
										<c:out value="${countryName}" />
									</c:when>
									<c:otherwise>
										<c:out value="${loc.otherState}"/>
									</c:otherwise>
								</c:choose>
								<c:if test="${not empty stateName}">
									,<c:out value="${stateName}" />
								</c:if>
								<c:if test="${empty stateName && not empty loc.otherState}">
									,<c:out value="${loc.otherState}"/>
								</c:if>
								<c:if test="${not empty cityName}">
									,<c:out value="${stateName}" />
								</c:if>
								<c:if test="${empty cityName && not empty loc.otherCity}">
									,<c:out value="${loc.otherCity}"/>
								</c:if>
								<c:if test="${not empty loc.workPermit && loc.workPermit eq 'TRUE'}">
									,<fmt:message key="TARGETJOB_AUTHORIZED"/>
								</c:if>
								<c:if test="${not empty loc.workPermit && loc.workPermit eq 'FALSE'}">
									,<fmt:message key="TARGETJOB_NOT_AUTHORIZED"/>
								</c:if>
							</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</c:if>
		
		<div class="col-xs-12 col-sm-12 col-md-5 col-lg-5 caption">
			<label for="targetJobAvailable"><fmt:message key="PREVIEW_TARGETJOB_AVAILABLE"/></label>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7 answer">
			<c:out value="${startJobString}" />
		</div>
		<div class="row" style="text-align:right;padding:10pt;">
			<div class="col-xs-12 col-sm-12">
				<c:if test="${empty backToView }">
					<a href="/SRIX?view=targetJob&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</c:if>
				<c:if test="${not empty backToView }">
					<a href="/SRIX?view=targetJob&sequence=0&idResume=<c:out value='${idResume}'/>&backToView=<c:out value='${backToView }'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
				</c:if>
			</div>
		</div>
	</div>
</c:if>