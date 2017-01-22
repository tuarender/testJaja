<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.JobseekerManager" %>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
	String[] countryList={"TH","ID","US","DE","SG","JP","PH","CN","MM"};
	request.setAttribute("countryList", countryList);
 %>
<c:import url="/config.jsp" charEncoding="UTF-8"/>

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
	    
		
	</head>
 	<body class="backgroundIndex">
 		<div id="hashTag">
			<a href="http://www.jobtopgun.com/" alt="<fmt:message key="HASH_TAG"/>" title="<fmt:message key="HASH_TAG"/>">
				<h5><fmt:message key="HASH_TAG"/></h5>
			</a>
		</div>
 		<div class="container" style="max-width:1024px;padding:0px;">
 			<c:if test="${sessionScope.SESSION_COUNTRY eq 'TH'}">
 				<div class="hidden-xs" style="text-align:right;"><a href="?locale=th_TH">ไทย</a> | <a href="?locale=en_TH">En</a>&nbsp;&nbsp;&nbsp;&nbsp;</div>
		 	</c:if>
		 	<img class="img-responsive center-block hidden-sm hidden-md hidden-lg" src="/images/mobile_BG.jpeg"/>
		 	
			<div>
			 	<div class="transparent">
					<br><br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8" >
							<span><b>Select Language</b></span>
						</div>
						<div class="col-xs-2"></div>
							
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8" style="cursor:pointer;"onclick="window.location.href='/index.jsp?locale=th_TH';">
							<div class="col-xs-3">
								<img src="../images/flag_old/TH_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>Thailand</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8"  style="cursor:pointer;"onclick="window.location.href='/index.jsp?locale=en_ID';">
							<div class="col-xs-3">
								<img src="../images/flag_old/ID_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>Indonesia</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8" >
							<div class="col-xs-3">
								<img src="../images/flag_old/US_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>United State</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8" >
							<div class="col-xs-3">
								<img src="../images/flag_old/DE_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>Germany</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8" >
							<div class="col-xs-3">
								<img src="../images/flag_old/SG_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>Singapore</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8" >
							<div class="col-xs-3">
								<img src="../images/flag_old/JP_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>Japan</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8" >
							<div class="col-xs-3">
								<img src="../images/flag_old/PH_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>Philippines</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8">
							<div class="col-xs-3">
								<img src="../images/flag_old/CN_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>China</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-8">
							<div class="col-xs-3">
								<img src="../images/flag_old/MM_mini.png" />
							</div>
							<div class="col-xs-offset-2 col-xs-7">
								<span><b>Myanmar</b></span>
							</div>
						</div>
						<div class="col-xs-2"></div>
					</div>
					<div class="container visible-xs" style="height:20px;">&nbsp;</div>
				</div>
				<div class="hidden-xs" style="height:7px;">&nbsp;</div>
				
			</div>
			<div class="container hidden-xs">&nbsp;</div>
			<div class="col-xs-12 visible-xs" align="right" style="font-size:11px;">
 	 			&copy;Copyright 2012-2014 SuperResume.com All Right Reserved.
 			</div>
		</div>
		
		
  </body>
</html>