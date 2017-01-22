<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.DecryptionLink"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.ComputerGroup"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Util"%>

<%
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	String enc=Util.getStr(request.getParameter("enc"));
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.getInt(request.getParameter("idResume"));
	Resume resume = new ResumeManager().get(idJsk, idResume);
	int applyAll = Util.getInt(request.getParameter("applyAll"),0);
	if(resume!=null)
	{
		idLanguage = resume.getIdLanguage();
	}
	List<ComputerGroup> computerGroup = MasterDataManager.getAllComputerGroup(idLanguage);
	String path = "/ApplyServ";
	if(applyAll==1)
	{
		path = "/ApplyAllServ";
	}
	request.setAttribute("resume",resume);
	request.setAttribute("idResume", idResume);
	request.setAttribute("computerGroup", computerGroup);
	request.setAttribute("enc", enc);
	request.setAttribute("path", path);
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<div class="seperator">&nbsp;</div>
<div class="row">
	<div class="col-xs-12 text-center">
		<span class="font_16 color715fa8"><fmt:message key="FULLFILL_SKILL_COM_HEADER"/></span>
	</div>
</div>
<div class="seperator">&nbsp;</div>
<div class="seperator">&nbsp;</div>
<form id="skillComputerFrm">
	<input type="hidden" name="idResume" value="<%=idResume%>">
	<input type="hidden" name="service" value="addSkillComputer" />
	<input type="hidden" name="fromApply" value="1" />
	<div class="errorContainer row" id="computerErrorContainar">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<div id="skillComputerList"></div>
		</div>
	</div>
	<div class="form-group">
		<div class="row">
			<div class="col-xs-7 text-left">
				<label class="color333333" for="idGroupComputer"><fmt:message key="SKILLS_COMPUTER_CATEGORY"/></label>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<select name="idGroupComputer" id="idGroupComputer" class="form-control required">
					<option value="" ></option>
					<c:forEach items="${computerGroup}" var="computer">
						<option value="<c:out value="${computer.idGroup}"/>"><c:out value="${computer.groupName}"/></option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<br>
	<div class="form-group">
		<div class="row">
			<div class="col-xs-7 text-left"  >
				<label class="color333333" for="achieve"><fmt:message key="SKILLS"/></label>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<select name="idSkillComputer" id="idSkillComputer" class="form-control required" disabled>
					
				</select>
			</div>
		</div>
	</div>
	<br>
	<div class="form-group" style="display:none;"  id="otherLayer">
		<div class="row">
			<div class="col-xs-7 text-left"  >
				<label for="otherCom"><fmt:message key="GLOBAL_OTHER_SPECIFY"/></label>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<input type="text" name="otherCom" id="otherCom" class="form-control" />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				&nbsp;
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="row">
			<div class="col-xs-7 text-left"  >
				<label class="color333333" for="skillLevel"><fmt:message key="SKILLS_PROFICIENCY"/></label>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<select id="level_skillComputer" name="level_skillComputer" class="form-control required" disabled>
						<option value=""></option>
						<option value="1"><fmt:message key="SKILL_COM_LEARNING"/></option>
						<option value="2"><fmt:message key="SKILL_COM_BEGINNER"/></option>
						<option value="3"><fmt:message key="SKILL_COM_INTERMEDIATE"/></option>
						<option value="4"><fmt:message key="SKILL_COM_ADVANCED"/></option>
						<option value="5"><fmt:message key="SKILL_COM_EXPERT"/></option>
				</select>
			</div>
		</div>
	</div>
	<div class="row" align="right">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<input type="image" src="/images/<c:out value='${resume.locale}'/>/save.png"  name="btn_submit" id="btn_submit" />
		</div>
	</div>
</form>
<br><br>
<div class="row" align="center">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<a href="<c:out value="${path}"/>?enc=<c:out value="${enc}"/>&chkFillResume=1&skipExp=1&skipSkillCom=1&submitSkillCom=1"><img  src="/images/<c:out value='${resume.locale}'/>/next.png"  /></a>
	</div>
</div>
<br>
<div class="row" align="center">
	<a href="<c:out value="${path}"/>?enc=<c:out value="${enc}"/>&chkFillResume=1&skipExp=1&skipSkillCom=1&clickSkipSkillCom=1"><fmt:message key="FULLFILL_EXP_SKIP"/></a>
</div>


<script>
	var idResume = '<c:out value="${idResume}"/>';
	$(document).ready(function(){
		getSkillComputerList();
		var container = $('div#computerErrorContainar');
		
		$('#skillComputerFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
			invalidHandler: function(form, validator) 
			{
				$('div#computerErrorContainar li').remove();
				$('html, body').animate({scrollTop:"0px" },300);      
			},
			messages :
			{
				idGroupComputer:
				{
					required:"<fmt:message key='SKILL_COMPUTER_GROUP_REQUIRE'/>"
				},
				idSkillComputer:
				{
					required:"<fmt:message key='SKILL_COMPUTER_SKILL_REQUIRE'/>"
				},
				level_skillComputer:
				{
					required:"<fmt:message key='SKILL_COMPUTER_LEVEL_REQUIRE'/>"
				}
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
			submitHandler:function(form)
			{
				container.hide();
				$.ajax(
				{
					type: "POST",
	        		url: '/SkillComputerServ',
       				data: $('#skillComputerFrm').serialize(),
	       			async:false,
	       			success: function(data)
	       			{
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					$('#idGroupComputer').val("");
	       					$('#idSkillComputer').val("");
	       					$('#idSkillComputer').empty();
	       					$('#level_skillComputer').val("");
	       					$('#idSkillComputer').prop('disabled',true);
							$('#level_skillComputer').prop('disabled',true);
							$('#otherCom').val("");
							$('#otherLayer').hide();
	       					getSkillComputerList();
	       				}
	           			else
	           			{
	           				$('div#computerErrorContainar li').remove();
           					for(var i=0; i<obj.errors.length; i++)
           					{
	           					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
	           				}
	           				container.css({'display':'block'});
	           				$('html, body').animate({scrollTop: '0px'}, 300); 
	           			}
	       			}
       			});
			}
		});
		
		$('#idGroupComputer').change(function(){
			if($(this).val()!="")
			{
				$.ajax(
				{
					type: "POST",
		   			url: '/SkillComputerServ',
		   			data: {"service":"skillComputer","idResume":idResume,"idGroupComputer":$(this).val()},
		   			async:false,
		   			success: function(data)
		   			{
		   				var obj = jQuery.parseJSON(data);
		   				if(obj.success==1)
		   				{
		   					var comList = obj.comList ; 
		   					if(comList!=null)
		   					{
		   						$('#idSkillComputer').empty();
		   						$('#idSkillComputer').append("<option value=''></option>");
		   						comList.forEach(function(skillCom)
		   						{
		   							$('#idSkillComputer').append("<option value='"+skillCom.idComputer+"'>"+skillCom.computerName+"</option>");
		   						});
		   						$('#idSkillComputer').append("<option value='-1'><fmt:message key="EDU_OTHER"/></option>");
		   						$('#idSkillComputer').prop('disabled',false);
		   						$('#level_skillComputer').prop('disabled',false);
		   					}
		   				}
		   			}
		 		});
			}
			else
			{
				$('#idSkillComputer').empty();
				$('#idSkillComputer').prop('disabled',true);
				$('#level_skillComputer').prop('disabled',true);
			}
		});
		
		$('#idSkillComputer').change(function()
		{
			var idSkillComputer = $(this).val();
			if(idSkillComputer==-1){
				$('#otherLayer').show();
				$('#otherCom').rules("add", {
					required:true,
				    messages: {
				       required:"<fmt:message key='SKILL_COM_PLS_FILL_OTHER'/>"
				    }
				});
			}else{
				$('#otherLayer').hide();
				$('#otherCom').rules('remove');
				$('#otherCom').removeClass('error');
			}
		});
    });
    
    function getSkillComputerList(){
 		$.ajax(
			{
				type: "POST",
        		url: '/SkillComputerServ',
       			data: {'service':'getSkillComputer','idResume':idResume},
       			async:false,
       			success: function(data)
       			{
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					$('#skillComputerList').empty();
       					if(obj.idGroupComputer.length>0){
       						$('#skillComputerListLayer').show();
	       					$('#skillComputerList').append('<table style="border-collapse:collapse;">');
	       					for(var i=0;i<obj.idGroupComputer.length;i++)
	       					{
	       						$('#skillComputerList').append('<tr>');
	       						$('#skillComputerList').append('<td>'+(i+1)+'. '+obj.groupName[i]+'</td>');
	       						$('#skillComputerList').append('<td>&nbsp;</td>');
	       						$('#skillComputerList').append('<td><a href="javascript:void(0);" onclick="delSkillComputer('+obj.idSkillComputer[i]+');" ><fmt:message key="GLOBAL_DELETE"/></a></td>');
	       						$('#skillComputerList').append('</tr>');
	       						$('#skillComputerList').append('<tr>');
	       						$('#skillComputerList').append('<td colspan="3" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+obj.skillName[i]+' / '+obj.levelType[i]+'</td>');
	       						$('#skillComputerList').append('</tr>');
	       					}
	       					$('#skillComputerList').append('</table>');
       					}else{
       						$('#skillComputerListLayer').hide();
       					}
       				}
           			else
           			{
           				$('div.container li').remove();
           				if(obj.urlError!=""){
           					window.location.href = obj.urlError;
           				}
           				else
           				{
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
      		return false ;
 		}
 		function delSkillComputer(idSkillComputer){
        $.ajax(
			{
				type: "POST",
        		url: '/SkillComputerServ',
       			data: {'service':'delSkillComputer','idResume':'<c:out value="${resume.idResume}"/>','idSkillComputer':idSkillComputer},
       			async:false,
       			success: function(data)
       			{
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					getSkillComputerList();
       				}
           			else
           			{
           				$('div.container li').remove();
           				if(obj.urlError!=""){
           					window.location.href = obj.urlError;
           				}
           				else
           				{
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
      	}
</script>
