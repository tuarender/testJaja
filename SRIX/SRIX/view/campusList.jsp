<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	String locale=Util.getStr(session.getAttribute("SESSION_LOCALE"),"th_TH");
	int idLanguage = Util.getInt(session.getAttribute("SESSION_ID_LANGUAGE"),11);
	int idSchoolExclusive = Util.getInt(request.getParameter("idSchoolExclusive"),0);
	List<com.topgun.shris.masterdata.School> schoolList = new ArrayList<com.topgun.shris.masterdata.School>() ;
	if(idSchoolExclusive!=0){
		List<Integer> id = new ArrayList<Integer>();
		if(idSchoolExclusive==11){
			id.add(11);
			id.add(46);
			id.add(47);
			id.add(48);
		}
		if(id.size()>0){
			for(int i=0;i<id.size();i++){
				com.topgun.shris.masterdata.School school = new com.topgun.shris.masterdata.School();
				school.setIdSchool(id.get(i));
				school.setSchoolName(MasterDataManager.getSchool(216,id.get(i),idLanguage).getSchoolName());
				schoolList.add(school);
			}
		}
	}
	request.setAttribute("schoolList", schoolList);
	request.setAttribute("locale",locale);
	
%>
<fmt:setLocale value="${locale}"/>
<script>
	$(document).ready(function()
	{
		var container = $('div.errorContainer');
		$('#campusFrm').validate(
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
  				
			  	campus:
			  	{
			  		required:true
			  	}		  	
			},			  
			messages: 
			{
				
			  	campus:
			  	{
			  		required:'<fmt:message key="AGREEMENT_REQUIRED"/>'
			  	}		  			
			}
		});
		
		
	});
</script>
<form id="campusFrm" method="post" class="form-horizontal" action="/SRIX?view=register">
<div class="section_header alignCenter">
   <h3><fmt:message key="GLOBAL_REGISTER"/></h3>
</div>
<div class="form-group">
		<div class="errorContainer">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
</div>
<c:forEach items="${schoolList}" var="schoolList" varStatus="idx">
	<div class="form-group caption checkbox">
		<input type="radio" name="campus" id="campus_<c:out value="${schoolList.idSchool}" />" value="<c:out value="${schoolList.idSchool}" />">
		<label for="campus_<c:out value="${schoolList.idSchool}" />"><c:out value="${schoolList.schoolName}" /></label>
	</div>
</c:forEach>
<br/>
	<div class="row alignCenter"><button type="submit" class="btn btn-large btn-success"><fmt:message key="GLOBAL_NEXT"/></button><br/><br/></div>
</form>
