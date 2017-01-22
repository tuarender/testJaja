<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.Date"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="com.topgun.resume.JobseekerManager" %>
<%@page import="com.topgun.resume.AccessLogManager" %>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1); 
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	String refer= Util.getStr(session.getAttribute("SESSION_REFER"));
	request.setAttribute("idJsk", idJsk);
	String locale = Util.getStr(session.getAttribute("SESSION_LOCALE"));
	String idSession=Encoder.getEncode(session.getId());
	String isSetCookie=Util.getStr(session.getAttribute("SET_COOKIE"));
	int remember=Util.getInt(request.getParameter("remember"));
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	request.setAttribute("locale",locale);
	request.setAttribute("sequence", sequence);
	request.setAttribute("refer", refer);
	
	int group = 2;
	if(locale.equals("en_TH"))		
	{
		group = 9;
	}
	
	request.setAttribute("group",group); 
	
	
	
	
	
	
	if(!isSetCookie.equals("TRUE"))
	{
		if(new JobseekerManager().isLogin(idJsk,idSession))
		{
			if(remember==1)
			{
				Cookie cookJsk = new Cookie("COOKIE_ID_JSK",Util.getStr(idJsk));
		   		Cookie cookSession = new Cookie("COOKIE_ID_SESSION",idSession);
		   		// Set Domain
		   		cookJsk.setDomain(".superresume.com");
				cookSession.setDomain(".superresume.com"); 
				
		   		// Set expiry date after 24 Hrs for both the cookies.
		   		cookJsk.setMaxAge(365*24*60*60); 
		   		cookSession.setMaxAge(365*24*60*60); 
				
		   		// Add both the cookies in the response header.
		   		response.addCookie(cookJsk);
		   		response.addCookie(cookSession);	
		   		session.setAttribute("SET_COOKIE","TRUE");
			}
			else //delete old cookie
			{
				Cookie cookie = null;
				Cookie[] cookies = null;
				cookies = request.getCookies();
				if( cookies != null )
				{
				      for (int i = 0; i < cookies.length; i++)
				      {
				         cookie = cookies[i];
				         if(cookie.getName().equals("COOKIE_ID_JSK") || cookie.getName().equals("COOKIE_ID_SESSION"))
				         {
				            cookie.setMaxAge(0);
				            response.addCookie(cookie);
				         }
				      }
				}
			}
			
		}	
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
<!DOCTYPE html>
<html>
 	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1 user-scalable=no">
		<title>SUPERRESUME.COM</title>

	    <!-- Bootstrap -->
	    <link href="/css/bootstrap.min.css" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet">
		<link href="/css/font.css" rel="stylesheet">

		<script src="/js/detectmobilebrowser.js"></script>
    	<script src="/js/jquery.min.js"></script>
    	<script src="/js/bootstrap.min.js"></script>	
    	<script src="/js/jquery.form.js"></script>	
    	<script src="/js/jquery.validate.min.js"></script>	
    	<script src="/js/jquery.blockUI.js"></script>
    	<script src="/js/script.js"></script>
    	<script src="/js/date.js"></script>  	
    	
    	<script type="text/javascript">
			window.__lc = window.__lc || {};
			window.__lc.license = 1882452;
			window.__lc.group=<c:out value ="${group}"/>;
			(function() {
			  var lc = document.createElement('script'); lc.type = 'text/javascript'; lc.async = true;
			  lc.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'cdn.livechatinc.com/tracking.js';
			  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(lc, s);
			})();
		</script>	
    	
		<!--[if lt IE 9]>
	      <script src="/js/html5shiv.js"></script>
	      <script src="/js/respond.min.js"></script>
	    <![endif]-->  	    
	</head>
 	<body>
 		<!-- navigation.jsp is menu for mobile -->
 		<c:import url="/view/navigation.jsp" charEncoding="UTF-8"/>
 		<%-- <c:if test="${locale ne 'en_ID'}">
			<div class="col-xs-12 hidden-xs" style="color:#00f; text-align:right;background-color:#fff;">
				<br/>
				<b>Hotline Tel: 081-989-9779</b><br>
				(จันทร์ - ศุกร์ 8:30 - 17:00 น.)<br>
				<a href='http://www.jobtopgun.com/?view=comment' target='_blank'>แจ้งปัญหาในการใช้งาน คลิกที่นี่</a><br><br>
				<c:if test="${(param.view eq 'register') or (param.view eq 'registerTracking') or (param.view eq 'studentLogin') or (param.view eq 'studentTracking') or (param.view eq 'home') or (param.view eq 'welcome') or (param.view eq 'jobMatch') or (param.view eq 'jobUpdate') or (param.view eq 'apply') or (param.view eq 'resumeInfo') or (param.view eq 'applyRecord')}">
					<a href="/SRIX?idResume=<c:out value="${param.idResume}"/>&view=<c:out value="${param.view}"/>&locale=th_TH">ไทย</a> | <a href="/SRIX?idResume=<c:out value="${param.idResume}"/>&view=<c:out value="${param.view}"/>&locale=en_TH">En</a>
				</c:if>					
			</div>
		</c:if> --%>
 		<div class="container">
 			<div class="row hidden-xs">
 				<div class="col-sm-6 col-md-6">
					<c:choose>
 						<c:when test="${(param.view eq 'applyLogin') or (param.view eq 'applyLoginNew') or (param.view eq 'stepRegister')}">
 							<img class="img-responsive" src="/images/logoJTG-06.png"/>
 						</c:when>
 						<c:otherwise>
 							<img class="img-responsive" src="/images/logo.png"/>
 						</c:otherwise>
 					</c:choose>
 				</div>
 				<div class="col-sm-6 col-md-6" align="right">
 				<br/>
 					<c:choose>
 						<c:when test="${(param.view eq 'applyLogin') or (param.view eq 'applyLoginNew') or (param.view eq 'stepRegister')}">
 							<img class="img-responsive" src="/images/SR-04.png"/>
 						</c:when>
 						<c:otherwise>
 							<img class="img-responsive" src="/images/TG-04.png" />
 						</c:otherwise>
 					</c:choose>
	 			</div>
	 		</div>
	 		<div class="row blueBar hidden-xs">
	 			<c:import url="/view/menu.jsp"  charEncoding="UTF-8"/>
	 		</div>
	 		<c:if test="${((param.view eq 'experienceQuestion') or (param.view eq 'education') or (param.view eq 'experience') or (param.view eq 'targetJob') or (param.view eq 'personal') or (param.view eq 'strength') or (param.view eq 'strengthOrder') or (param.view eq 'aptitude') or (param.view eq 'aptitudeLevel') or (param.view eq 'aptitudeExtension') or (param.view eq 'aptitudeRank') or (param.view eq 'uploadPhoto') or (param.view eq 'uploadResume') ) and (sequence eq 1) }">
		 		<div class="row grayBar hidden-xs">
		 			<c:import url="/view/progressBar.jsp"  charEncoding="UTF-8"/>
		 		</div>
	 		</c:if>
 			<div class="row" style="background-color:#fff;">
 				<c:choose>
	 				<c:when test="${param.view eq 'cancel'|| param.view eq 'cancelService' || param.view eq 'actionService' || param.view eq 'actionServiceJu' || param.view eq 'cancelResult' || param.view eq 'privacy' || param.view eq 'term'}">
	 					<div class="col-xs-12">
	 	 					<div class="row">
								<div class="col-lg-3 col-sm-2 col-xs-1"></div>
		 						<div class="col-lg-6 col-sm-8 col-xs-10">
		 							<c:if test="${not empty param.view}">
				 						<c:import url="/view/${param.view}.jsp" charEncoding="UTF-8"/>
				 					</c:if>
								</div>
								<div class="col-lg-3 col-sm-2 col-xs-1"></div>
							</div>
		 				</div>
	 				</c:when>
	 				<c:otherwise>
	 					 <div class="col-sm-7 col-md-8">
		 	 					<div class="row">
		 	 						<c:choose>
		 	 							<c:when test="${param.view eq 'resumeInfo' || param.view eq 'applyAll'|| param.view eq 'applyAllResult' || param.view eq 'resumeFullFillEdit' || param.view eq 'resumeFullFill' || param.view eq 'generateResume'}">
					 						<div class="col-lg-1 col-sm-1 col-xs-1"></div>
					 						<div class="col-lg-10 col-sm-10 col-xs-10">
					 							<c:if test="${not empty param.view}">
							 						<c:import url="/view/${param.view}.jsp" charEncoding="UTF-8"/>
							 					</c:if>
											</div>
											<div class="col-lg-1 col-sm-1 col-xs-1"></div>
										</c:when>
										<c:when test="${param.view eq 'home'}">
					 						<div class="col-lg-1 col-sm-1 col-xs-1"></div>
					 						<div class="col-lg-10 col-sm-10 col-xs-10">
					 							<c:if test="${not empty param.view}">
							 						<c:import url="/view/${param.view}.jsp" charEncoding="UTF-8"/>
							 					</c:if>
											</div>
											<div class="col-lg-1 col-sm-1 col-xs-1"></div>
										</c:when>
										<c:otherwise>
											<div class="col-lg-3 col-sm-2 col-xs-1"></div>
					 						<div class="col-lg-6 col-sm-8 col-xs-10" style="padding:0px;">
					 							<c:if test="${not empty param.view}">
							 						<c:import url="/view/${param.view}.jsp" charEncoding="UTF-8"/>
							 					</c:if>
											</div>
											<div class="col-lg-3 col-sm-2 col-xs-1"></div>
										</c:otherwise>
									</c:choose>
								</div>
								<br/><br/><br/>
		 				</div>
		 				<div class="col-sm-5 col-md-4" style="background-color:#eee;">
		 					<c:if test="${sessionScope.SESSION_COUNTRY eq 'TH'}">
			 					<br>
			 					<div class="hidden-xs" style="color:#00f; text-align:right;">
				 					<b>Hotline Tel: 081-989-9779</b><br>
				 					(จันทร์ - ศุกร์ 8:30 - 17:00 น.)<br>
				 					<a href='http://www.jobtopgun.com/?view=comment' target='_blank' onClick="ga('send', 'event', { eventCategory: 'hotline', eventAction: 'Click', eventLabel:'แจ้งปัญหา'});" >แจ้งปัญหาในการใช้งาน คลิกที่นี่</a><br><br>
			 					</div>
		 					</c:if>
							<c:import url="/view/faq.jsp" charEncoding="UTF-8"></c:import>
		 				</div>
	 				</c:otherwise>
 				</c:choose>
 			</div>
 			<div class="col-sm-9 col-lg-9 footer">
 	 			&copy;Copyright 2012-2014 SuperResume.com All Right Reserved.
 			</div>
 		</div>
 		<c:if test="${ (sequence eq '1') and  (idJsk ne '-1') and (refer eq 'Facebook')}">
 			<!-- Facebook Conversion Code for registration -->
 			<!--
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
				window._fbq.push(['track', '6020895879715', {'value':'0.00','currency':'THB'}]);
			</script>
			<noscript><img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/tr?ev=6020895879715&amp;cd[value]=0.00&amp;cd[currency]=THB&amp;noscript=1" /></noscript>
			-->
 		</c:if>
		<script> 
			 (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){ 
			 (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o), 
			 m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m) 
			 })(window,document,'script','//www.google-analytics.com/analytics.js','ga'); 
			 ga('create', 'UA-10045262-2', 'Superresume.com');  
			 ga('send', 'pageview'); 
		</script> 
		<%AccessLogManager.addLog(request); %>
  </body>
</html>