<%@page import="com.topgun.resume.SocialManager"%>
<%@page import="com.topgun.resume.Social"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<%@page import="com.topgun.shris.masterdata.State"%>
<%@page import="com.topgun.shris.masterdata.City"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Jobseeker jobseeker=new JobseekerManager().get(idJsk);
	if(jobseeker!=null)
	{
		request.setAttribute("jobseeker",jobseeker);
		Resume resume=new ResumeManager().get(idJsk, idResume);
		if(resume!=null)
		{	
			String countryName="";
			String stateName=resume.getOtherState();
			String cityName=resume.getOtherCity();
			
			String lineId="";
	        String tango="";
	        String skype="";
	        String whatsapp="";
	        String facebook="";
			
			if(resume.getIdCountry()>0)
			{
				Country country=MasterDataManager.getCountry(resume.getIdCountry(),resume.getIdLanguage());
				if(country!=null)
				{
					countryName=country.getCountryName();
				}
			}
			
			if(resume.getIdCountry()>0 && resume.getIdState()>0)
			{
				State state=MasterDataManager.getState(resume.getIdCountry(),resume.getIdState(),resume.getIdLanguage());
				if(state!=null)
				{
					stateName=state.getStateName();
				}
			}
	
			if(resume.getIdCountry()>0 && resume.getIdState()>0 && resume.getIdCity()>0)
			{		
				City city=MasterDataManager.getCity(resume.getIdCountry(),resume.getIdState(),resume.getIdCity(),resume.getIdLanguage());
				if(city!=null)
				{
					cityName=city.getCityName();
				}
			}	
			
			Social social=new SocialManager().get(resume.getIdJsk(), resume.getIdResume());
			if(social!=null)
	    	{
				lineId=!social.getLineId().equals("")?social.getLineId():"";
		    	skype=!social.getSkype().equals("")?social.getSkype():"";
		    }
			request.setAttribute("resume",resume);
			request.setAttribute("countryName",countryName);
			request.setAttribute("stateName",stateName);
			request.setAttribute("cityName",cityName);
			request.setAttribute("idResume",resume.getIdResume());
			request.setAttribute("lineId",lineId);
			request.setAttribute("skype",skype);
		}
	}
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<div class="row" style="padding:10pt;">
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-6">
				<label class="caption"><fmt:message key="GLOBAL_FIRST_NAME"/></label>&nbsp;
				<c:choose >
					<c:when test="${resume.salutation eq 'MR'}">
				  		<font class="answer"><fmt:message key="GLOBAL_MR"/></font>
					</c:when>
					<c:when test="${resume.salutation eq 'MISS'}">
				  		<font class="answer"><fmt:message key="GLOBAL_MISS"/></font>
					</c:when>
					<c:when test="${resume.salutation eq 'MRS'}">
				  		<font class="answer"><fmt:message key="GLOBAL_MRS"/></font>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${resume.idLanguage eq 38}">
				   		<font class="answer"><c:out value="${resume.firstNameThai}"/></font>
					</c:when>
					<c:otherwise>
				  		<font class="answer"><c:out value="${resume.firstName}"/></font>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-xs-12 col-sm-6">
				<label class="caption"><fmt:message key="GLOBAL_LAST_NAME"/></label>&nbsp;
				<c:choose>
					<c:when test="${resume.idLanguage eq 38}">
				  		<font class="answer"><c:out value="${resume.lastNameThai}"/></font>
					</c:when>
					<c:otherwise>
				 		<font class="answer"><c:out value="${resume.lastName}"/></font>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:if test="${resume.idCountry eq 216}">
			<div class="row" style="padding-left:10pt;padding-right:10pt;padding-right:10pt;">
				<div class="col-xs-12 col-sm-6">
					<label class="caption"><fmt:message key="GLOBAL_FIRST_NAME_TH"/></label>&nbsp;
					<c:choose>
						<c:when test="${resume.idLanguage eq 38}">
					  		<font class="answer"><c:out value="${resume.firstName}"/></font>
						</c:when>
						<c:otherwise>
					    	<font class="answer"><c:out value="${resume.firstNameThai}"/></font>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-xs-12 col-sm-6">
					<label class="caption"><fmt:message key="GLOBAL_LAST_NAME_TH"/></label>&nbsp;
					<c:choose>
						<c:when test="${resume.idLanguage eq 38}">
					  		<font class="answer"><c:out value="${resume.lastName}"/></font>
						</c:when>
						<c:otherwise>
					 		<font class="answer"><c:out value="${resume.lastNameThai}"/></font>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:if>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_BIRTHDATE"/></label>&nbsp;
				<font class="answer"><fmt:formatDate var="birthDay" value="${resume.birthDate}" pattern="d"/> </font>
				<font class="answer"><fmt:formatDate var="birthMonth" value="${resume.birthDate}" pattern="MMMM"/></font>
				<font class="answer"><fmt:formatDate var="bYear" value="${resume.birthDate}" pattern="yyyy"/></font>
				<c:choose>
					<c:when test="${resume.idLanguage eq 38}">
				   		<font class="answer"><c:out value="${birthDay}"/>&nbsp;<c:out value="${birthMonth}"/>&nbsp;<c:out value="${bYear}"/></font>
					</c:when>
					<c:when test="${resume.idLanguage eq 23}">
		              	<font class="answer"><c:out value="${bYear}"/>年&nbsp;<c:out value="${birthMonth}"/>&nbsp;<c:out value="${birthDay}"/>日</font>
		           	</c:when>
					<c:otherwise>
				  		<font class="answer"><c:out value="${birthMonth}"/> <c:out value="${birthDay}"/>,&nbsp;<c:out value="${bYear}"/></font>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-6">
				<label class="caption"><fmt:message key="GLOBAL_HEIGHT"/></label>&nbsp;
				<font class="answer"><fmt:parseNumber value='${resume.height}' var='parsedAmount'pattern='###,###' type="number"/></font>
		   		<font class="answer"><fmt:formatNumber value='${parsedAmount}' pattern="###,###"/></font> 
		   		<font class="answer"><fmt:message key="GLOBAL_CENTIMETER"/></font>
			</div>
			<div class="col-xs-12 col-sm-6">
				<label class="caption"><fmt:message key="GLOBAL_WEIGHT"/></label>&nbsp;
		   		<font class="answer"><fmt:parseNumber value='${resume.weight}' var='parsedAmount'pattern='###,###' type="number"/></font> 
		   		<font class="answer"><fmt:formatNumber value='${parsedAmount}' pattern="###,###"/></font>
		   		<font class="answer"><fmt:message key="GLOBAL_KILOGRAM"/></font>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_CITIZENSHIP"/></label>&nbsp;
		   		<c:choose>
		 			<c:when test="${resume.citizenship eq 'THAI'}">
		 				<font class="answer"><fmt:message key="GLOBAL_THAI"/></font>
					</c:when>
					<c:otherwise>
						<font class="answer"><c:out value='${resume.citizenship}'/></font> 
					</c:otherwise>					
		 		</c:choose>
		 	</div>
		</div>

		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_HOME_ADDRESS"/></label>&nbsp;
	 			<font class="answer"><c:out value="${resume.homeAddress}"/></font>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_STATE"/></label>&nbsp;
			 	<font class="answer"><c:out value="${stateName}"/></font>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<c:choose>
			  		<c:when test="${addressCountry eq 216}">
			      		<label class="caption"><fmt:message key="GLOBAL_DISTRICT"/></label>
			  		</c:when>
			  		<c:otherwise>
			      		<label class="caption"><fmt:message key="GLOBAL_CITY"/></label>
			  		</c:otherwise>
				</c:choose>
				&nbsp;<font class="answer"><c:out value="${cityName}"/></font>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_POSTAL"/></label>&nbsp;
				<font class="answer"><c:out value="${resume.postal}"/></font>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_COUNTRY"/></label>&nbsp;
				<font class="answer"><c:out value="${countryName}"/></font>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_PRIMARY_PHONE"/></label>&nbsp;
				<font class="answer"><c:out value="${resume.primaryPhone}"/></font>
				<c:choose>
					<c:when test="${resume.primaryPhoneType eq 'HOME'}">
						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_HOME"/>)</font>
					</c:when>
					<c:when test="${resume.primaryPhoneType eq 'WORK'}">
						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_WORK"/>)</font>
					</c:when>
					<c:when test="${resume.primaryPhoneType eq 'MOBILE'}">
						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_MOBILE"/>)</font>
					</c:when>
					<c:when test="${resume.primaryPhoneType eq 'PAGER'}">
						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_PAGER"/>)</font>
					</c:when>
					<c:when test="${resume.primaryPhoneType eq 'FAX'}">
						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_FAX"/>)</font>
					</c:when>
					<c:when test="${resume.primaryPhoneType eq 'DSN'}">
						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_DSN"/>)</font>
					</c:when>
				</c:choose>
				<br>
				<c:if test="${not empty resume.secondaryPhone}">
					<label class="caption"><fmt:message key="GLOBAL_SECONDARY_PHONE"/></label>&nbsp;
					<font class="answer"><c:out value="${resume.secondaryPhone}"/></font>  
					<c:choose>
		  					<c:when test="${resume.secondaryPhoneType eq 'HOME'}">
		  						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_HOME"/>)</font>
		  					</c:when>
		  					<c:when test="${resume.secondaryPhoneType eq 'WORK'}">
		  						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_WORK"/>)</font>
		  					</c:when>
		  					<c:when test="${resume.secondaryPhoneType eq 'MOBILE'}">
		  						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_MOBILE"/>)</font>
		  					</c:when>
		  					<c:when test="${resume.secondaryPhoneType eq 'PAGER'}">
		  						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_PAGER"/>)</font>
		  					</c:when>
		  					<c:when test="${resume.secondaryPhoneType eq 'FAX'}">
		  						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_FAX"/>)</font>
		  					</c:when>
		  					<c:when test="${resume.secondaryPhoneType eq 'DSN'}">
		  						<font class="answer">(<fmt:message key="GLOBAL_PHONE_TYPE_DSN"/>)</font>
		  					</c:when>
		 			</c:choose>
				</c:if>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_EMAIL"/></label>&nbsp;
				<font class="answer"><c:out value="${jobseeker.username}"/></font>
				<c:if test="${not empty resume.secondaryEmail}">
					<br><label class="caption"><fmt:message key="GLOBAL_SECONDARY_EMAIL"/></label>&nbsp;
					<font class="answer"><c:out value="${resume.secondaryEmail}"/></font>
				</c:if>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<c:if test="${not empty lineId}">
					<label class="caption">Line ID</label>&nbsp;
					<font class="answer"><c:out value="${lineId}"/></font>  
				</c:if>
				<c:if test="${not empty skype}"><br>
					<label class="caption">Skype</label>&nbsp;
					<font class="answer"><c:out value="${skype}"/></font>  
				</c:if>
			</div>
		</div>
		<div class="row" style="padding-left:10pt;padding-right:10pt;">
			<div class="col-xs-12 col-sm-12">
				<label class="caption"><fmt:message key="GLOBAL_OWNCAR"/></label>&nbsp;
				<c:choose>
		   			<c:when test="${resume.ownCarStatus eq 'TRUE'}">
		   				<font class="answer"><fmt:message key="GLOBAL_YES"/></font>
					</c:when>
					<c:when test="${resume.ownCarStatus eq 'FALSE'}">
		   				<font class="answer"><fmt:message key="GLOBAL_NO"/></font>
					</c:when>				
		  		</c:choose>
			</div>
		</div>
		
		<div style="text-align:right; padding-right:10pt;padding-right:10pt;">
			<a href="/SRIX?view=personal&sequence=0&idResume=<c:out value="${idResume}"/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
		</div>	
	</div>
</c:if>		
	
	
		
	
		
	
	
	
  		
