<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.JobseekerManager" %>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Degree"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	request.getSession().removeAttribute("SESSION_ID_JSK");
	request.getSession().removeAttribute("SESSION_STU_ID_DEGREE");
	request.getSession().removeAttribute("SESSION_STU_ID_SCHOOL");
	request.getSession().removeAttribute("SESSION_STU_ID_FAC");
	request.getSession().removeAttribute("SESSION_STU_ID_MAJOR");
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	int idLanguage=11;
	if(locale.equals("th_TH"))
	{
		idLanguage=38;
	}
	ArrayList<Degree> degrees = (ArrayList<Degree>)MasterDataManager.getAllDegree(idLanguage);
	request.setAttribute("locale",locale);
	request.setAttribute("degrees",degrees);
	request.setAttribute("idLanguage",idLanguage);
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
	    
<script>
	$(document).ready(function()
	{
		getSchool();
		getFaculty();
		getMajor();
		var container = $('div.errorContainer');
		$('#form1').validate(
		{
			ignore: [],
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
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
  				degree:
  				{
  					required:true 
  				},
			 	idSchool:
			 	{
			  		required:true 
			  	},
			 	idFac:
			 	{
			  		required:true 
			  	},
			  	idMajor:
			  	{
			  		required:true
			  	}
			},			  
			messages: 
			{
				degree:
				{
					required:'<fmt:message key="EDU_DEGREE_REQUIRED"/>',
				},
				idSchool:
				{
			    	required: '<fmt:message key="EDU_UNIVERSITY_REQUIRED"/>'
			  	},
			  	idFac:
				{
			  		required: '<fmt:message key="EDU_FACULTY_REQUIRED"/>'
			  	},
			  	idMajor:
			  	{
			  		required:'<fmt:message key="EDU_MAJOR_REQUIRED"/>'
			  	} 
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
           			url: '/SRTSServ',
           			data: $('#form1').serialize(),
           			async:false,
           			success: function(data)
           			{
           				var obj = jQuery.parseJSON(data);
           				if(obj.success==1)
           				{
           					window.location.href = "/SRIX?view=studentLogin&idResume=0&sequence=1";
           				}
               			else
               			{
               				$('div.container li').remove();
               				for(var i=0; i<obj.errors.length; i++)
               				{
               					container.find("ol").append('<li>'+obj.errors[i]+'</li>');
               				}
               				container.css({'display':'block'});
               				container.find("ol").css({'display':'block'});
               				$('html, body').animate({scrollTop: '0px'}, 300); 
               			}
           			}
         		});
         		return false;
			}
		});
		$('#degree').change(function() 
		{
			if($("#degree").val()>0) 
			{
				getSchool();
			}
			else
			{
				$('#idSchool option[value!=""]').remove();
				$('#idFac option[value!=""]').remove();
				$('#idMajor option[value!=""]').remove();
			}
		});
		$('#idSchool').change(function() 
		{
			if($("#degree").val()>0 && $("#idSchool").val()>0) 
			{
				getFaculty();
			}
			else
			{
				$('#idFac option[value!=""]').remove();
				$('#idMajor option[value!=""]').remove();
			}
		});		
		$('#idFac').change(function() 
		{
			if($("#degree").val()>0 && $("#idSchool").val()>0 && $("#idFac").val()>0) 
			{
				getMajor();
			}
			else
			{
				$('#idMajor option[value!=""]').remove();
			}
		});
	});
	function getSchool(idDegree)
	{
		var degree=$("#degree").val();
		var idLanguage='<c:out value="${idLanguage}"/>';
		$.ajax({
	  		url: '/SRTSServ',
			data: {'service':'getSchool','degree':degree,'idLanguage':idLanguage},
			async :true,
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj!=null)
				{
					var schoolList=obj.school;
					if(schoolList!=null)
					{
						$('#idSchool option[value!=""]').remove();
						for (var i=0; i<schoolList.length; i++) 
						{
							$('#idSchool').append("<option value='"+schoolList[i].idSchool+"'>"+schoolList[i].schoolName+"</option>");
					    }
					}
				}
			},
			error:function(xdr, status,error)
			{
				alert("Error"+error);
			}
		});
	}
	function getFaculty()
	{
		var idLanguage='<c:out value="${idLanguage}"/>';
		$.ajax({
	  		url: '/SRTSServ',
			data: {'service':'getFaculty','degree':$("#degree").val(),'idSchool':$("#idSchool").val(),'idLanguage':idLanguage},
			async :true,
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj!=null)
				{
					var facultyList=obj.faculty;
					if(facultyList!=null)
					{
						$('#idFac option[value!=""]').remove();
						for (var i=0; i<facultyList.length; i++) 
						{
							$('#idFac').append("<option value='"+facultyList[i].idFaculty+"'>"+facultyList[i].facultyName+"</option>");
					    }
					}
				}
			},
			error:function(xdr, status,error)
			{
				alert("Error"+error);
			}
		});
	}
	function getMajor()
	{
		var idLanguage='<c:out value="${idLanguage}"/>';
		$.ajax({
	  		url: '/SRTSServ',
			data: {'service':'getMajor','degree':$("#degree").val(),'idSchool':$("#idSchool").val(),'idFac':$("#idFac").val(),'idLanguage':idLanguage},
			async :true,
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj!=null)
				{
					var majorList=obj.major;
					if(majorList!=null)
					{
						$('#idMajor option[value!=""]').remove();
						for (var i=0; i<majorList.length; i++) 
						{
							$('#idMajor').append("<option value='"+majorList[i].idMajor+"'>"+majorList[i].majorName+"</option>");
					    }
					}
				}
			},
			error:function(xdr, status,error)
			{
				alert("Error"+error);
			}
		});
	}
</script>
	</head>
 	<body class="backgroundIndex">
 		<div class="container" style="max-width:1024px;padding:0px;">
 			<div class="hidden-xs" style="text-align:right;"><a href="?locale=th_TH">ไทย</a> | <a href="?locale=en_TH">En</a>&nbsp;&nbsp;&nbsp;&nbsp;</div>
			
	 		
			<div class="hidden-xs" style="height:7px;">&nbsp;</div>
			<div>
			 	<div class="col-sm-offset-4 transparent">
					<div class="row" style="margin-top:0px; margin-bottom:0px; padding:0px;">
						<div style="text-align:center;">
							<img class="img-responsive hidden-xs center-block" src="/images/superresume.png" style="max-height:140px;"/>
						</div>
					</div>
					<br/>
					<form id="form1" method="post" class="form-horizontal">
					<input type="hidden" name="service" id="service" value="setStudent"/>
					<div class="row">
						<div class="col-xs-0 col-sm-2 col-lg-2"></div>
						<div class="col-xs-0 col-sm-9 col-lg-9" style="text-align:center;">
							<label class="redFont"><fmt:message key="TRACKING_WELCOME"/></label><br>
							<label class="redFont"><fmt:message key="TRACKING_CHOSSE"/></label><br>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-1 col-sm-3 col-lg-3"></div>
						<div class="col-xs-10 col-sm-7 col-lg-6">
							<div class="form-group">
								<div class="errorContainer" >
									<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
									<ol></ol>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-1 col-sm-3 col-lg-3"></div>
						<div class="col-xs-10 col-sm-7 col-lg-6" style="height:40px;">
							<div class="form-group">
								<font color="red">*</font><label for="degree" class="caption"><fmt:message key='EDU_DEGREE'/></label>
								<select class="form-control" name="degree" id="degree" >
									<option value=""><fmt:message key="EDU_DEGREE"/></option>
									<c:forEach var="degree" items="${degrees}">
										<option value="<c:out value='${degree.idDegree}'/>"
											<c:choose>
												<c:when test='${degree.idDegree == education.idDegree}'>
													selected
												</c:when>
											</c:choose>
											><c:out value='${degree.degreeName}'/>
										</option>
									</c:forEach>
						    	</select>
							</div>
						</div>
					</div>
					<br><br>
					<div class="row">
					<div class="col-xs-1 col-sm-3 col-lg-3"></div>
						<div class="col-xs-10 col-sm-7 col-lg-6" style="height:40px;">
							<div class="form-group">
								<font color="red">*</font><label for="school" class="caption"><fmt:message key='EDU_UNIVERSITY'/></label>
								<select name="idSchool" id="idSchool" class="form-control required" >
									<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
								</select>
							</div>
						</div>
					</div>
					<br><br>
					<div class="row">
					<div class="col-xs-1 col-sm-3 col-lg-3"></div>
						<div class="col-xs-10 col-sm-7 col-lg-6" style="height:40px;">
							<div class="form-group">
								<font color="red">*</font><label for="idFac" class="caption"><fmt:message key='EDU_FACULTY'/></label>
								<select name="idFac" id="idFac" class="form-control required" >
									<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
								</select>
							</div>
						</div>
					</div>
					<br><br>
					<div class="row">
					<div class="col-xs-1 col-sm-3 col-lg-3"></div>
						<div class="col-xs-10 col-sm-7 col-lg-6" style="height:40px;">
							<div class="form-group">
								<font color="red">*</font><label for="idMajor" class="caption"><fmt:message key='EDU_MAJOR'/></label>
								<select name="idMajor" id="idMajor" class="form-control required">
									<option value=""><fmt:message key="GLOBAL_SELECT"/></option>
								</select>
							</div>
						</div>
					</div>
					<br><br>
					<div class="row alignCenter"><input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/next.png"><br/><br/></div>
					<br><br>
					</form>
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