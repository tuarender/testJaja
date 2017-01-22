<%@page import="com.topgun.shris.masterdata.Major"%>
<%@page import="com.topgun.shris.masterdata.Faculty"%>
<%@page import="com.topgun.shris.masterdata.School"%>
<%@page import="com.topgun.resume.SRTSManager"%>
<%@page import="com.topgun.resume.SRTS"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Degree"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.sql.Date"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<% 
	int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	int idLanguage=11;
	int idCountry=216;
	if(locale.equals("th_TH"))
	{
		idLanguage=38;
	}
	Resume resume=new ResumeManager().get(idJsk, 0);	
	int degree=Util.getInt(session.getAttribute("SESSION_STU_ID_DEGREE"));
	
	int idSchool=Util.getInt(session.getAttribute("SESSION_STU_ID_SCHOOL"));
	School school=new MasterDataManager().getSchool(idCountry, idSchool, idLanguage);
	
	int idFaculty=Util.getInt(session.getAttribute("SESSION_STU_ID_FAC"));
	Faculty faculty=MasterDataManager.getFaculty(idFaculty, idLanguage);
	
	int idMajor=Util.getInt(session.getAttribute("SESSION_STU_ID_MAJOR"));
	Major major=MasterDataManager.getMajor(idFaculty, idMajor, idLanguage);
	
	
	request.setAttribute("idSchool",idSchool);
	request.setAttribute("idFaculty",idFaculty);
	request.setAttribute("idMajor",idMajor);
	request.setAttribute("resume",resume);
	request.setAttribute("school",school);
	request.setAttribute("faculty",faculty);
	request.setAttribute("major",major);
	request.setAttribute("locale",locale);
	request.setAttribute("idLanguage",idLanguage);
	
	
%>
<fmt:setLocale value="${locale}"/>
<script>
	$(document).ready(function()
	{
		var idSchool="<c:out value='${idSchool}'/>";
		var idFaculty="<c:out value='${idFaculty}'/>";
		var idMajor="<c:out value='${idMajor}'/>";
		if(idSchool<=0 || idFaculty<=0 || idMajor<=0)
		{
			window.location.href = "http://student.superresume.com/studentTracking.jsp";
			return false;
		}
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
  				idStu:
			  	{
  					required:true
			  	}
			},			  
			messages: 
			{
				idStu:
			  	{
					required:'<fmt:message key="ID_STUDENT_REQUIRED"/>'
			  	} 
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
           			url: '/StudentLogonServ',
           			data: $('#form1').serialize(),
           			async:false,
           			success: function(data)
           			{
           				var obj = jQuery.parseJSON(data);
           				if(obj.success==1)
           				{
           					var pages=obj.target;
           					if(pages!="")
           					{
           						window.location.href = "/SRIX?view="+pages+"&idResume=0&sequence=1";
               					return false;
           					}
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
	});
</script>
<c:choose>
	<c:when test="${idSchool>0 &&idFaculty>0}">
		<div class="section_header alignCenter">
  			<h3><fmt:message key="TRACKING_BEFORE_UPDATE"/></h3>
		</div>
		<h5 class="text-center suggestion"><fmt:message key="TRACKING_FILL_ID_STU"/></h5>
		<br/>
		<form id="form1" method="post" class="form-horizontal">
			<input type="hidden" name="locale" value="<c:out value="${sessionScope.SESSION_LOCALE}"/>">
			<input type="hidden" name="idSchool" value="<c:out value="${sessionScope.SESSION_STU_ID_SCHOOL}"/>">
			<input type="hidden" name="idFac" value="<c:out value="${sessionScope.SESSION_STU_ID_FAC}"/>">
			<input type="hidden" name="idMajor" value="<c:out value="${sessionScope.SESSION_STU_ID_MAJOR}"/>">
			<input type="hidden" name="service" value="setIdJsk">
			<div class="form-group">
				<div class="errorContainer">
					<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
					<ol></ol>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-0 col-sm-0 col-lg-2"></div>
				<div class="col-xs-12 col-sm-8 col-lg">
					<label><fmt:message key='EDU_UNIVERSITY'/></label>
					<div class="answer">&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${school.schoolName}"/></div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-0 col-sm-0 col-lg-2"></div>
				<div class="col-xs-12 col-sm-8 col-lg">
					<label><fmt:message key='EDU_FACULTY'/></label>
					<div class="answer">&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${faculty.facultyName}"/></div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-0 col-sm-0 col-lg-2"></div>
				<div class="col-xs-12 col-sm-8 col-lg">
					<label><fmt:message key='EDU_MAJOR'/></label>
					<div class="answer">&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${major.majorName}"/></div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-0 col-sm-0 col-lg-2"></div>
				<div class="col-xs-12 col-sm-8 col-lg">
					<label><fmt:message key='GLOBAL_FIRST_NAME'/>-<fmt:message key="GLOBAL_LAST_NAME"/></label> 
					<div class="answer">
					<c:choose>
						<c:when test="${idLanguage==11}">
							&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${resume.firstName}"/>&nbsp;&nbsp;&nbsp;<c:out value="${resume.lastName}"/>
						</c:when>
						<c:otherwise>
							&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${resume.firstNameThai}"/>&nbsp;&nbsp;&nbsp;<c:out value="${resume.lastNameThai}"/>
						</c:otherwise>
					</c:choose>
					</div>
				</div>
			</div>
			<div class="row">
				<c:set var="acurYear" scope="request"><%=Util.getCurrentYear()-8%></c:set>
				<c:set var="curYear" scope="request"><%=Util.getCurrentYear()%></c:set>
				<div class="col-xs-0 col-sm-0 col-lg-2"></div>
				<div class="col-xs-12 col-sm-8 col-lg">
					<label for="academicYear" class="caption"><font color="red">*</font><fmt:message key="EDUCATION_FINISH_YEAR"/></label>
					<select  name="academicYear" id="academicYear" class="form-control required">
						<c:forEach var="i" begin="0" end="10">
							<c:choose>
								<c:when test="${locale eq 'th_TH'}">
									<option value="<c:out value='${acurYear+i}'/>" <c:if test="${(acurYear+i) == (curYear)}">selected</c:if> >
										<c:out value="${acurYear+i+543}"/>
									</option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value='${acurYear+i}'/>" <c:if test="${(acurYear+i) == curYear}">selected</c:if>>
									<c:out value="${acurYear+i}"/>
									</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					</div>
			</div>
			<div class="row">
				<div class="col-xs-0 col-sm-0 col-lg-2"></div>
				<div class="col-xs-12 col-sm-8 col-lg">
					<label for="idStu" class="caption"><font color="red">*</font><fmt:message key="ID_STUDENT"/></label>
					<input type="text" name="idStu" id="idStu" class="form-control" value="">
				</div>
			</div>
			<br/>
			<div class="row alignCenter"><input type="image" src="/images/<c:out value="${sessionScope.SESSION_LANGUAGE}"/>/next.png"><br/><br/></div>
			<div class="row">
				<div class="col-xs-0 col-sm-0 col-lg-2"></div>
				<div class="col-xs-12 col-sm-8 col-lg">
					<a href="http://student.superresume.com/studentTracking.jsp">คลิกที่นี่</a> เพื่อกลับไปเลือก มหาวิทยาลัย คณะและสาขาใหม่
				</div>
			</div>
		</form>
	</c:when>
	<c:otherwise>
		<div class="section_header alignCenter">
  			<h3>พบข้อผิดพลาด กรุณา <a href='http://student.superresume.com/studentTracking.jsp'>คลิกที่นี่</a> เพื่อกลับไปหน้าแรก</h3>
		</div>
	</c:otherwise>
</c:choose>
