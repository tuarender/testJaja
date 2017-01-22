<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.util.Util"%>
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
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	Resume resume=null;
	if(idJsk>0)
	{
		Jobseeker jobseeker=new JobseekerManager().get(idJsk);
		if(jobseeker!=null)
		{
			request.setAttribute("jobseeker",jobseeker);
			resume=new ResumeManager().get(idJsk,0);
			if(resume!=null)
			{
				if(resume.getLocale()!=null)
				{
					locale=resume.getLocale();
				}
				request.setAttribute("resume",resume);
			}
		}	
	}
	request.setAttribute("locale",locale);
%>
<script>
	$(document).ready(function()
	{
		$('#buttonNext').click(function()
		{
			window.location.href = "/SRIX?view=education&idResume=0&sequence=1";
		});
	});
</script>
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

<script type="text/javascript">
var fb_param = {};
fb_param.pixel_id = '6009469648017';
fb_param.value = '0.00';
fb_param.currency = 'USD';
(function(){
  var fpw = document.createElement('script');
  fpw.async = true;
  fpw.src = '//connect.facebook.net/en_US/fp.js';
  var ref = document.getElementsByTagName('script')[0];
  ref.parentNode.insertBefore(fpw, ref);
})();
</script>
<noscript><img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/offsite_event.php?id=6009469648017&amp;value=0&amp;currency=USD" /></noscript>
<!-- End google conversion page & facebook conversion page -->

<div class="row" align="center">
	<div class="section_header alignCenter">
	   	<h3>
			<fmt:message key="PERSONAL_WELCOME">
				<c:if test="${not empty resume}">
					<c:choose>
						<c:when test="${resume.idLanguage eq 38}">
							<fmt:param value="${resume.firstNameThai}"></fmt:param>
						</c:when>
						<c:otherwise>
							<fmt:param value="${resume.firstName}"></fmt:param>
						</c:otherwise>
					</c:choose>
				</c:if>
			</fmt:message>
	   	</h3>
	</div>
</div>
<br>
<br>
<div class="row">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<fmt:message key="PERSONAL_MESSAGE5"/>
	</div>
</div>
<div class="row">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<fmt:message key="PERSONAL_MESSAGE4"/>
	</div>
</div>
<br>
<div class="row" align="center">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<a href="/SRIX?view=education&idResume=0&sequence=1"><input type="button"  id="buttonNext" class="btn btn-lg btn-success" onClick="ga('send', 'event', { eventCategory: 'Engage-WelcomePage', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"  value="<fmt:message key="GLOBAL_NEXT"/>"></a>
	</div>
</div>
<br>

<div class="row">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<b><fmt:message key="GLOBAL_REMARK"/></b>
		<br>
		<c:set var="username"><span class="answer"><c:out value="${jobseeker.username}"/></span></c:set>
		<fmt:message key="PERSONAL_MESSAGE1">
			<c:if test="${not empty jobseeker}">
				<fmt:param value="${username}"></fmt:param>
			</c:if>
		</fmt:message>
	</div>
</div>
<div class="row">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<fmt:message key="PERSONAL_MESSAGE2"/>
	</div>
</div>
<br>
<br>