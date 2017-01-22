<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.Language"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.Encoder"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<%
	int sequence=Util.getInt(request.getParameter("sequence"));
	String cmd=Util.getStr(request.getParameter("cmd"));
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry = Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	String idSession=Encoder.getEncode(session.getId());
	int idResumeDel=Util.getInt(request.getParameter("idResumeDel"));
	
	Resume resume=(Resume)request.getAttribute("resume");
	
	if(resume!=null)
	{
		List<Country> countries=MasterDataManager.getAllCountry(resume.getIdLanguage());
		List<Language> languages=MasterDataManager.getAvailableLanguage(resume.getIdLanguage());
		request.setAttribute("countries",countries);
		request.setAttribute("languages",languages);
		request.setAttribute("resume",resume);
		if(resume.getIdLanguage()!=0)
		{
			 String language = MasterDataManager.getLanguageName(resume.getIdLanguage(),resume.getIdLanguage());
			 if(language!=null)
			 {
			 	request.setAttribute("currentLanguage", language);
			 }
		}
	}
	else
	{
		List<Country> countries=MasterDataManager.getAllCountry(idLanguage);
		List<Language> languages=MasterDataManager.getAvailableLanguage(idLanguage);
		request.setAttribute("countries",countries);
		request.setAttribute("languages",languages);
	}
	
	request.setAttribute("idCountry", idCountry);
	request.setAttribute("sequence",sequence);
	request.setAttribute("cmd",cmd);
	request.setAttribute("idSession", idSession); 
	request.setAttribute("idResumeDel",idResumeDel);
 %>
  <script>
 	var sequence = '<c:out value="${sequence}"/>';	
 	$(document).ready(function()
 	{
 		container = $('div.errorContainer');
 			//---------------------------- validation ---------------------------
		$('#namingFrm').validate({
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
			submitHandler:function(form){
			container.hide();
			$.ajax({
				type: "POST",
        		url: '/NamingServ',
       			data: $('#namingFrm').serialize(),
       			async:false,
       			success: function(data){
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					var cmd='<c:out value="${cmd }"/>';
       					if(cmd=="CREATE" && obj.idResumeDst>=0 )
       					{
       						window.location.href = "/SRIX?view=strength&idResume="+obj.idResumeDst+"&sequence="+sequence;
       					}
       					else if(sequence==0 && cmd=="EDIT")
       					{
       						window.location.href = "/SRIX?view=resumeInfo&idResume="+obj.idResume+"&sequence="+sequence;
       					}
       					else if(sequence==0 && cmd=="COPY")
       					{
       						window.location.href = "/SRIX?view=home&jSession=<c:out value='${idSession}'/>";
       					}
       				}
           			else
           			{
           				$('div.errorContainer li').remove();
           				if(obj.errors!=""){
           					for(var i=0; i<obj.errors.length; i++)
           					{
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
 	
 </script>
<form id="namingFrm" method="post" class="form-horizontal">
	<input type="hidden" name="sequence" id="sequence" value='<c:out value="${sequence }"/>'>
	<input type="hidden" name="service" id="service" value='<c:out value="${cmd }"/>'>
	<input type="hidden" name="idResume" id="idResume" value='<c:out value="${resume.idResume}"/>'>
	<input type="hidden" name="idCountry" value="<c:out value="${SESSION_ID_COUNTRY}"/>">
	<input type="hidden" name="idResumeDel" value='<c:out value="${idResumeDel}"/>'>
	<div class="row" align="center">
		<div class="section_header alignCenter">
		<c:choose>
				<c:when test="${cmd ne 'COPY'}">
					<h3><fmt:message key="RESUME_SETTING"/></h3>
				</c:when>
				<c:otherwise>
					<h3><fmt:message key="NAMING_NAMERESUME"/></h3>
				</c:otherwise>
			</c:choose>
   			
		</div>
	</div>
	<div class="row errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol> </ol>
	</div>
	
	<!--Text Field  -->
	<div class="row" >
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<div class="form-group">
				<label for="resumeName"><font color="red">*</font><fmt:message key="NAMING_NAMERESUME"/></label>
				<c:set var="resumeName" value="${resume.resumeName}" />
				<c:if test="${cmd eq 'CREATE'}">
					<c:set var="resumeName" value="My Super Resume" />
				</c:if>
				<c:choose>
					<c:when test="${cmd eq 'GENERATEPARENT' || cmd eq 'EVOLUTION'}">
						<input type="text" name="resumeName" id="resumeName" value="Edit of <c:out value='${resumeName}'/>"  class="required form-control answer" title="<fmt:message key="NAMING_REQUIRE"/>">
					</c:when>
					<c:when test="${cmd eq 'COPY'}">
						<input type="text" name="resumeName" id="resumeName" value="Copy of <c:out value='${resumeName}'/>"  class="required form-control answer" title="<fmt:message key="NAMING_REQUIRE"/>">
					</c:when>
					<c:otherwise>
						<input type="text" name="resumeName" id="resumeName" value="<c:out value='${resumeName}'/>"  class="required form-control answer" title="<fmt:message key="NAMING_REQUIRE"/>">
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="row" >
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<div class="form-group">
				<label for="idLanguage"><font color="red">*</font><fmt:message key="NAMING_LANGUAGE"/></label>
					<c:choose>
							<c:when test="${cmd ne 'EDIT' && cmd ne 'GENERATEPARENT' && cmd ne 'EVOLUTION'}" >
								<c:choose>
									<c:when test="${idCountry eq 216}">
										<select name="idLanguage" id="idLanguage"  class="required form-control answer" title="<fmt:message key="NAMING_LANG_REQUIRE"/>">
											<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
											<c:forEach var="language" items="${languages}">
												<c:if test="${language.id eq 38 or language.id eq 11 or language.id eq 16 or language.id eq 36 or language.id eq 23 or language.id eq 5 or language.id eq 6}">
													<c:choose>
														<c:when test="${language.id eq resume.idLanguage }">
															<option selected value="<c:out value='${language.id}'/>"><c:out value='${language.name}'/></option>
														</c:when>
														<c:otherwise>
															<option value="<c:out value='${language.id}'/>"><c:out value='${language.name}'/></option>
														</c:otherwise>
													</c:choose>
												</c:if>
											</c:forEach>
					  					</select>
									</c:when>
									<c:otherwise>
										<select name="idLanguage" id="idLanguage"  class="form-control answer" title="<fmt:message key="NAMING_LANG_REQUIRE"/>" disabled>
											<option value="11" selected>English</option>
					  					</select>
					  					<input type="hidden" name="idLanguage" value="11">
									</c:otherwise>
								</c:choose>
			  				</c:when>
			  				<c:otherwise>
			  						<span><c:out value='${currentLanguage}'/></span>
			  				</c:otherwise>
			  		</c:choose>
				
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-offset-1 col-xs-11" style="padding:0px;">
			<label  for="resumeName"><fmt:message key="NAMING_PRIVACY"/></label>
			<div class="form-group"> 
				<div class="col-xs-offset-1 radio">
					<label style="font-weight:normal !important;" class="answer" for="public">
						<c:choose>
							<c:when test="${resume.resumePrivacy eq 'PUBLIC'}">
	   							<input  type="radio" name="privacy" id="public" value="PUBLIC" checked  class="required" title="<fmt:message key="NAMING_PUBLIC_REQUIRE"/>" >
	   						</c:when>
	   						<c:otherwise>
	   							<input  type="radio" name="privacy" id="public" value="PUBLIC" class="required" title="<fmt:message key="NAMING_PUBLIC_REQUIRE"/>">
	   						</c:otherwise>
		    			</c:choose>
	    				<fmt:message key="NAMING_PUBLIC"/>
	    			</label>
	   			</div>
				<div class="col-xs-offset-1 radio">
					<label style="font-weight:normal !important;" class="answer" for="private">
						<c:choose>
							<c:when test="${resume.resumePrivacy eq 'PRIVATE'}">
		   						<input type="radio" name="privacy" id="private" value="PRIVATE" checked  class="required" title="<fmt:message key="NAMING_PUBLIC_REQUIRE"/>"  >
		   					</c:when>
		   					<c:otherwise>
		   						<input type="radio" name="privacy" id="private" value="PRIVATE"  class="required" title="<fmt:message key="NAMING_PUBLIC_REQUIRE"/>" >
		   					</c:otherwise>
	    				</c:choose>
		    			<fmt:message key="NAMING_PRIVATE"/>
		    		</label>
	    		</div>
			</div>
			
			<c:choose>
				<c:when test="${cmd ne 'COPY'}">
					<label  for="resumeName"><fmt:message key="NAMING_RESUME_CREATE_BY"/></label>
					<div class="form-group">
						<div class="col-xs-offset-1 radio">
							<label><input type="radio" checked name="source" value="1"><fmt:message key="NAMING_RESUME_COPY_LATEST"/></label>
						</div>
		
						<div class="col-xs-offset-1 radio">
							<label><input type="radio" name="source" value="2"><fmt:message key="NAMING_RESUME_COPY_MEMBER"/></label>
						</div>
					</div>	
				</c:when>
			</c:choose>
					
		</div>
	</div>
	
	
	
	<div class="row" align="center">
		<div class="col-xs-12">
			<c:choose>
				<c:when test="${not empty resume}">
					<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  name="btn_submit" id="btn_submit" />
				</c:when>
				<c:otherwise>
					<input type="image" src="/images/<c:out value="${sessionScope.SESSION_LOCALE}"/>/next.png"  name="btn_submit" id="btn_submit" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</form>
							