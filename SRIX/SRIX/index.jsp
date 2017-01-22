<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.JobseekerManager" %>
<%@page import="com.topgun.resume.AccessLogManager" %>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1); 
	request.getSession().removeAttribute("SESSION_ID_JSK");
	int invalid=Util.getInt(request.getParameter("invalid"), -1);
	String locale = Util.getStr(session.getAttribute("SESSION_LOCALE"));
	String[] countryList={"TH","ID","US","DE","SG","JP","PH","CN","MM"};
	request.setAttribute("invalid",invalid);
	request.setAttribute("countryList",countryList);
	request.setAttribute("locale",locale);
	if(request.getHeader("Host").indexOf("student.superresume.com")!=-1)
	{
		response.sendRedirect("/studentTracking.jsp");
	}
	
	int group = 2;
	if(locale.equals("en_TH"))		
	{
		group = 9;
	}
	
	request.setAttribute("group",group); 
	System.out.println("group " +group);
	
	//System.out.println("locale " +Util.getStr(session.getAttribute("SESSION_LOCALE")));
	System.out.println("locale "+Util.getStr(session.getAttribute("SESSION_LOCALE")));
	
	
	//read cookie
	Cookie[] cookies = null;
   	cookies = request.getCookies();
   	if(cookies != null)
	{
		int idJsk=-1;
		String idSession="";
		for (int i = 0; i < cookies.length; i++)
		{
			Cookie cookie = cookies[i];
			if(cookies[i].getName().equals("COOKIE_ID_JSK"))
			{
				idJsk=Util.getInt(cookies[i].getValue());
			} 
			else if(cookies[i].getName().equals("COOKIE_ID_SESSION"))
			{
				idSession=cookies[i].getValue();
			}
		}
		
		if(idJsk>0 && !idSession.equals(""))
		{
			if(new JobseekerManager().isLogin(idJsk,idSession))
			{
				session.setAttribute("SESSION_ID_JSK", idJsk);
				session.setAttribute("SET_COOKIE","TRUE");
				response.sendRedirect("/SRIX?view=home");
				return;
			}
		}
	}
%>

<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<!DOCTYPE html>
   <html>
 	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1 user-scalable=no">
		<title>เรซูเม่ | ตัวอย่างเรซูเม่ | เรซูเม่ อังกฤษ | Super Resume</title>
		<meta name="description" content="Super Resume คือ เรซูเม่อิเล็กทรอนิกส์แบบใหม่ซึ่งมีรูปแบบเดียวกันทั่วโลก แสดงตัวอย่างเรซูเม่ เรซูเม่ ภาษาอังกฤษ ซึ่งทำให้เห็นถึงความสามารถของผู้สมัครได้เป็นอย่างดี โดยเฉพาะอย่างยิ่งทางด้านคุณสมบัติเด่น บุคลิกลักษณะ และประสบการณ์การทำงาน">
		<meta name="keywords" content="เรซูเม่, ตัวอย่างเรซูเม่, เรซูเม่ อังกฤษ">
	    <!-- Bootstrap -->
	    <link href="/css/bootstrap.min.css" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet">
		
    	<script src="/js/jquery.min.js"></script>
    	<script src="/js/bootstrap.min.js"></script>	
    	<script src="/js/jquery.form.js"></script>	
    	<script src="/js/jquery.validate.min.js"></script>	
    	<script src="/js/jquery.blockUI.js"></script>
    	
		<!--[if lt IE 9]>
	      <script src="/js/html5shiv.js"></script>
	      <script src="/js/respond.min.js"></script>
	    <![endif]-->
	    
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
	    
		<script>
			$(document).ready(function()
			{
				$('#submitBtn').click(function(){
					$('#loginFrm').submit();
				});
				
				
				var container = $('div.errorsContainer');
				$('#loginFrm').validate(
				{
					errorContainer: container,
					errorLabelContainer: $("ol", container),
					wrapper: 'li',
					focusInvalid: false,
					onfocusout: false,
     				onkeyup: false,
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
		  			rules:
		  			{
		  				username:
		  				{
		  					required:true,
		  					email:true
		  				},
					 	password:
					 	{
					  		required:true
					  	}	  	
					},			  
					messages: 
					{
						username:
						{
							required:'<fmt:message key="USERNAME_REQUIRED"/>',
							email:'<fmt:message key="USERNAME_EMAIL"/>'
						},
						password:
						{
					    	required: '<fmt:message key="PASSWORD_REQUIRED"/>',
					   		minlength: '<fmt:message key="PASSWORD_MINLENGTH"/>'
					  	}	  			
					},
					submitHandler:function(form)
					{
						form.submit();
		         		return true;
					}
				});	
				
				var invalid=<c:out value="${invalid }"/>;
				if(invalid>0)
				{
					$('div.errorsContainer li').remove();
   					$('div.errorsContainer ol').append('<li><fmt:message key="LOGIN_INVALID"/></li>');
    				$('div.errorsContainer').css({'display':'block'});
    				$('html, body').animate({scrollTop: '0px'}, 300); 
   				}
			});
		</script>
	</head>
 	<body class="backgroundIndex">
 		<div id="hashTag">
			<a href="http://www.jobtopgun.com/" alt="<fmt:message key="HASH_TAG"/>" title="<fmt:message key="HASH_TAG"/>">
				<h5><fmt:message key="HASH_TAG"/></h5>
			</a>
		</div>
 		<div class="container" style="max-width:1024px;padding:0px;">
 			<c:if test="${sessionScope.SESSION_COUNTRY eq 'TH'}">
 				<div class="hidden-xs" style="text-align:right;">
 					<a href="?locale=th_TH">ไทย</a> | 
 					<a href="?locale=en_TH">English</a> |
 					<a href="?locale=de_TH">Deutsch</a> |
 					<a href="?locale=es_TH">español</a> |
 					<a href="?locale=ja_TH">Japan</a> |
 					<a href="?locale=zh_TH">Chinese(SC)</a> |
 					<a href="?locale=zt_TH">Chinese(TC)</a> &nbsp;&nbsp;&nbsp;
 				</div>
		 	</c:if>
		 	<img class="img-responsive center-block hidden-sm hidden-md hidden-lg" src="/images/mobile_BG.jpeg"/>
		 	<div class="visible-xs">
		 		<br>
				<table style="border-collapse:collapse; border-spacing:0;margin: 0 auto !important; ">
					<tr>
						<td valign="middle" rowspan="3" width="40">
							<a href="/selectCountry.jsp">
								<img src="/images/flag_old/<c:out value='${sessionScope.SESSION_COUNTRY}'/>_mini.png"/>
							</a>
						</td>
						<td valign="middle" rowspan="3" style="font-size:10pt; font-weight:bold; color:#000000;">
							&nbsp;
							<c:choose>
								<c:when test="${sessionScope.SESSION_COUNTRY eq 'TH'}">
									Thailand
								</c:when>
								<c:when test="${sessionScope.SESSION_COUNTRY eq 'ID'}">
									Indonesia
								</c:when>
							</c:choose>
						</td>
						<td valign="middle" rowspan="3" width="40" align="center"><c:if test="${sessionScope.SESSION_COUNTRY eq 'TH'}"><img src="/images/arrow_right.png" height="24"></c:if></td>
						<td><c:if test="${sessionScope.SESSION_COUNTRY eq 'TH'}"><a href="?locale=th_TH" style="font-size:10pt; font-weight:bold; color:#000000; text-decoration:none;">Th</a></c:if></td>
					</tr>
					<tr style="height:5px;"><td></td></tr>
					<tr>
						<td><c:if test="${sessionScope.SESSION_COUNTRY eq 'TH'}"><a href="?locale=en_TH" style="font-size:10pt; font-weight:bold; color:#000000; text-decoration:none;">En</a></c:if></td>
					</tr>
					<tr style="height:5px;"><td></td></tr>
					<tr>
						<td colspan="4" align="center">
							<a href="/selectCountry.jsp" style="text-decoration: underline;">Change Country</a>
						</td>
					</tr>
				</table>
			</div>
			
	 		<!--  <img class="img-responsive col-sm-offset-4  hidden-xs" src="/images/flag.png"/>-->
	 		
			<div class="row hidden-xs">
				<div class="col-sm-4">&nbsp;</div>
				<div class="col-sm-1">
					<img src="/images/flag/<c:out value="${sessionScope.SESSION_COUNTRY}"/>.png"/>
				</div>
				<div class="col-sm-7">
					<c:forEach var="country" items="${countryList}">
						<c:if test="${country ne sessionScope.SESSION_COUNTRY}">
							<div style="width:12.5%;float:left;">
							<c:choose>
								<c:when test="${country eq 'TH'}">
									<a href="/index.jsp?locale=th_TH">
										<img style="margin-top:20px;" class="img-responsive center-block" src="/images/flag/<c:out value='${country}'/>_mini.png"/>
									</a>
								</c:when>
								<c:when test="${country eq 'ID'}">
									<a href="/index.jsp?locale=en_ID">
										<img style="margin-top:20px;" class="img-responsive center-block" src="/images/flag/<c:out value='${country}'/>_mini.png"/>
									</a>
								</c:when>
								<c:otherwise>
									<img style="margin-top:20px;" class="img-responsive center-block" src="/images/flag/<c:out value='${country}'/>_mini.png"/>
								</c:otherwise>
							</c:choose>
							</div>
						</c:if>
					</c:forEach>
					
				</div>
			</div>
			<div class="hidden-xs" style="height:7px;">&nbsp;</div>
			<div>
			 	<div class="col-sm-offset-4 transparent">
					<div class="row" style="margin-top:0px; margin-bottom:0px; padding:0px;">
						<div style="text-align:center;">
							<img class="img-responsive hidden-xs center-block" src="/images/superresume.png" style="max-height:140px;"/>
						</div>
					</div>
			 		
					
					
					<!------------ Error Message Layer------->
					<div class="errorsContainer col-sm-offset-3 col-sm-7" style="color: #D63301;background-color: #FFccBA;display:none;font-style:italic;border: 1px solid #f00;margin-top: 4px;margin-bottom:4px;padding-top:4px; padding-bottom:0px;">
						<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
						<ol></ol>
					</div>
					<br>
					
					<!------------ Begin Login Form------->
					<form class="form-horizontal" id=loginFrm action="/LogonServ" method="post">
						<div style="margin-top:0px; margin-bottom:0px; padding:0px;">
							<div class="col-xs-offset-1 col-sm-offset-3">
								<label class="redFont"><fmt:message key="HEAD_NONMEMBER"/></label>
							</div>
						</div>
						<div class="form-group" style="margin-top:0px; margin-bottom:0px; padding:0px;">
							<div class="col-sm-offset-3 col-sm-7 col-lg-6" style="text-align:center;">
								<a href="/SRIX?view=register&locale=<c:out value="${param.locale}"/>&jSession=<c:out value="${pageContext.session.id}"/>"  onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'ClickOn', eventLabel: 'สมัครสมาชิก'});">
									<img class="img-responsive center-block" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/register_button.png"/>
								</a>
							</div>
						</div>
						<div class="hidden-xs" style="height:15px;">&nbsp;</div>
						<div style="margin-top:0px; margin-bottom:0px; padding:0px;">
							<div class="col-xs-offset-1 col-sm-offset-3">
								<label class="redFont"><fmt:message key="HEAD_MEMBER"/></label>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-1 col-sm-3 col-lg-3"></div>
							<div class="col-xs-10 col-sm-7 col-lg-6" style="height:40px;">
								<div class="form-group" style="background-color:#FFFFFF;border-radius:5px;">
									<div class=" col-xs-3 col-sm-3" >
										<label class="control-label rememberFont" for="username"><fmt:message key="GLOBAL_EMAIL"/></label>
									</div>
									<div class="col-xs-9 col-sm-9">
										<input type="text" class="form-control" id="username" name="username" placeholder="<fmt:message key="EX_EMAIL"/>" style="border:0px solid #000000;background-color:transparent;" onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'TypeOn', eventLabel: 'อีเมล'});"/>
									</div>
								</div>
							</div>
							<div class="col-xs-1 col-sm-2 col-sm-3"></div>
						</div>
						<div class="row">
							<div class="col-xs-1 col-sm-3"></div>
							<div class="col-xs-10 col-sm-7 col-lg-6" style="height:40px;">
								<div class="form-group" style="background-color:#FFFFFF;border-radius:5px;">
									<div class="col-xs-3 col-sm-3">
										<label class="control-label rememberFont" for="password"><fmt:message key="GLOBAL_PASSWORD"/></label>
									</div>
									<div class="col-xs-9 col-sm-9">
										<input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="EX_PASSWORD"/>" style="border:0px solid #000000;background-color:transparent;" onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'TypeOn', eventLabel: 'รหัสผ่าน'});"/>
									</div>
								</div>
							</div>
							<div class="col-xs-1 col-sm-2 col-sm-3"></div>
						</div>
						
						<div class="visible-xs" style="height:15px;"></div>
						<div class="form-group" style="margin-top:0px; margin-bottom:0px; padding:0px;">
							<div class="col-xs-offset-1 col-sm-offset-3 col-xs-10 col-sm-6 ">
								<input style="padding:0px; margin:0px;" type="checkbox" id="remember" name="remember" value="1" checked onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'Check', eventLabel: 'จำ username'});"/>
								<label for="remember" class="rememberFont"><fmt:message key="GLOBAL_REMEMBER"/></label>
							</div>
						</div>
						<div class="form-group" style="margin-top:0px; margin-bottom:0px; padding:0px;">
							<div class="col-sm-offset-3 col-sm-7 col-lg-6" style="text-align:center;">
								<input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/login_button.png" onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'ClickOn', eventLabel: 'เข้าสู่ระบบ'});"/>
							</div>
						</div>
						<div class="row" align="right">
							<div class="col-lg-offset-3 col-lg-6 col-xs-11">
								<label><u><a href="/SRIX?view=forgotNew" onClick="ga('send', 'event', { eventCategory: 'Engage-Homepage', eventAction: 'ClickOn', eventLabel: 'ลืมรหัสผ่าน'});"><fmt:message key="GLOBAL_FORGOT_PASSWORD"/></a></u></label>
							</div>
							<div class="col-lg-3 col-xs-1"></div>
						</div>
						
					</form>
					<div class="hidden-xs" style="height:15px;"></div>
					<c:if test="${sessionScope.SESSION_COUNTRY eq 'TH'}">
						<div class="container visible-xs">&nbsp;</div>
						<div class="container visible-xs">&nbsp;</div>
						<div class="hidden-xs" style="height:7px;background-color:inherit;">&nbsp;</div>
						<div style="padding:2px; margin-top:0px; text-align:center;">		
							<a href="http://www.jobtopgun.com" target="_blank"><img class="img-responsive center-block" src="/images/sr_banner.png"/></a>
						</div>
					</c:if>
				</div>
				<div class="hidden-xs" style="height:7px;">&nbsp;</div>
				<!--  
				<div>
					<div class="col-sm-offset-4" style=" margin-top:0px;">		
						<a href="http://www.jobtopgun.com" target="_blank"><img class="img-responsive " src="/images/sr_banner.png"/></a>
					</div>
				</div>-->
			</div>
			<div class="container hidden-xs">&nbsp;</div>
			<div class="col-xs-12 visible-xs" align="right" style="font-size:11px;">
 	 			&copy;Copyright 2012-2014 SuperResume.com All Right Reserved.
 			</div>
		</div>
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