<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Language"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.*"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	int idLanguage=resume.getIdLanguage();
	List<Language> language = MasterDataManager.getAllLanguage(idLanguage);
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	request.setAttribute("resume",resume);
	request.setAttribute("language", language);
	request.setAttribute("sequence",sequence);
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<script>
	var sequence=<c:out value="${sequence}"/>;
	var idResume=<c:out value="${resume.idResume}"/>;
		function getSkillLanguage()
		{
			$.ajax(
				{
					type: "POST",
	        		url: '/SkillLanguageServ',
	       			data: {'service':'getSkillLanguage','idResume':'<c:out value="${resume.idResume}"/>'},
	       			async:false,
	       			success: function(data)
	       			{
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					$('#skillLanguageList').empty();
	       					if(obj.idSkillLanguage.length>0){
	       						$('#skillLanguageListLayer').show();
		       					$('#skillLanguageList').append('<table style="border-collapse:collapse;">');
		       					for(var i=0;i<obj.idSkillLanguage.length;i++)
		       					{
		       						var idSkillLanguage = obj.idSkillLanguage[i];
		       						$('#idLanguage option[value="'+idSkillLanguage+'"]').attr('disabled','disabled');
		       						$('#skillLanguageList').append('<tr>');
		       						$('#skillLanguageList').append('<td>'+(i+1)+'. '+obj.languageName[i]+'</td>');
		       						$('#skillLanguageList').append('<td><a data-toggle="modal" href="#" onclick="delSkillLanguage('+idSkillLanguage+')"   > <fmt:message key="GLOBAL_DELETE"/></a></td>');
		       						$('#skillLanguageList').append('</tr>');
		       						$('#skillLanguageList').append('<tr>');
		       						$('#skillLanguageList').append('<td colspan="2">&nbsp;&nbsp;&nbsp;<fmt:message key="LANGUAGE_LISTEN"/> - '+obj.listen[i]+'  /  <fmt:message key="LANGUAGE_SPAEK"/> - '+obj.spaek[i]+'</td>');
		       						$('#skillLanguageList').append('</tr>');
		       						$('#skillLanguageList').append('<tr>');
		       						$('#skillLanguageList').append('<td colspan="2">&nbsp;&nbsp;&nbsp;<fmt:message key="LANGUAGE_READ"/> - '+obj.read[i]+'  /  <fmt:message key="LANGUAGE_WRITE"/> - '+obj.write[i]+'</td>');
		       						$('#skillLanguageList').append('</tr>');
		       					}
		       					$('#skillLanguageList').append('</table>');
	       					}else{
	       						$('#skillLanguageListLayer').hide();
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
       			return false;
		}
		
		function dialogDelLanguage(idSkillLanguage){
			$('#md1').data('id',idSkillLanguage).modal('show');
		}
		function btnDelLanguage(){
			var id = $('#md1').data('id');
			delSkillLanguage(id);
			$('#md1').modal("hide");	
		}
		function getTest()
		{
			$.ajax(
				{
					type: "POST",
	        		url: '/SkillLanguageServ',
	       			data: {'service':'getTest','idResume':'<c:out value="${resume.idResume}"/>'},
	       			async:false,
	       			success: function(data)
	       			{
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					
	       					$('#scoreList').empty();
	       					$('#scoreList').append('<table style="border-collapse:collapse; width:280px;">');
	       					for(var i=0;i<obj.testName.length;i++)
	       					{
	       						var testName = obj.testName[i];
	       						
	       						if(testName=="TOEFL"||testName=="TOEIC"||testName=="IELTS")
	       						{
	       							$('#idTest option[value="'+testName+'"]').attr('disabled','disabled');
	       						}
	       						$('#scoreList').append('<tr>');
	       						$('#scoreList').append('<td>'+(i+1)+'. '+obj.testName[i]+'    :   </td>');
	       						$('#scoreList').append('<td>&nbsp;&nbsp;&nbsp;<fmt:message key="LANGUAGE_LISTEN"/> - '+obj.listen[i]+'  /  <fmt:message key="LANGUAGE_SPAEK"/> - '+obj.spaek[i]+'</td>');
	       						$('#scoreList').append('<td><a data-toggle="modal" href="#" onclick="delTest(\''+testName+'\');" >&nbsp;<fmt:message key="GLOBAL_DELETE"/></a></td>');
	       						$('#scoreList').append('</tr>');
	       						$('#scoreList').append('<tr>');
	       						$('#scoreList').append('<td>&nbsp;</td>');
	       						$('#scoreList').append('<td>&nbsp;&nbsp;&nbsp;<fmt:message key="LANGUAGE_READ"/> - '+obj.read[i]+'  /  <fmt:message key="LANGUAGE_WRITE"/> - '+obj.write[i]+'</td>');
	       						$('#scoreList').append('<td>&nbsp;</td>');
	       						$('#scoreList').append('</tr>');
	       						
	       					}
	       					$('#scoreList').append('</table>');
	       				}
	           			else
	           			{
	           				$('div.container li').remove();
	           				if(obj.urlError!=""){
	           					window.location.href = obj.urlError;
	           				}
	           				else{
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
		function dialogDelTest(testname){
			$('#md2').data('testName',testname).modal('show');
		}
		function btnDelTest(){
			var testname = $('#md2').data('testName');
			delTest(testname);
			$('#md2').modal("hide");	
		}
		function delSkillLanguage(idSkillLanguage)
		{

		        $.ajax(
				{
					type: "POST",
	        		url: '/SkillLanguageServ',
	       			data: {'service':'delSkillLanguage','idResume':'<c:out value="${resume.idResume}"/>','idSkillLanguage':idSkillLanguage},
	       			async:false,
	       			success: function(data)
	       			{
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					$('#idLanguage option[value="'+idSkillLanguage+'"]').removeAttr('disabled');
	       					getSkillLanguage();
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
		function delTest(testName)
		{
	        $.ajax(
			{
				type: "POST",
        		url: '/SkillLanguageServ',
       			data: {'service':'delTest','idResume':'<c:out value="${resume.idResume}"/>','testName':testName},
       			async:false,
       			success: function(data)
       			{
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					$('#idTest option[value="'+testName+'"]').removeAttr('disabled');
       					getTest();
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
			$(document).ready(function(){
				getSkillLanguage();
				getTest();
				$('#language_listen').prop('disabled',true);
				$('#language_spaek').prop('disabled',true);
				$('#language_read').prop('disabled',true);
				$('#language_write').prop('disabled',true);
				$('#score_listen').prop('disabled',true);
				$('#score_spaek').prop('disabled',true);
				$('#score_read').prop('disabled',true);
				$('#score_write').prop('disabled',true);
				var container = $('div#languageErrorContainar');
				var container2 = $('div#testErrorContainar');
				//-Submit Page-//
				$('#btn_submit').click(function(){
					if($('#skillLanguageList').html()!=""){
						if(sequence==0){
							window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
						}else{
							window.location.href = "/SRIX?view=skillComputer&idResume="+idResume+"&sequence="+sequence;
						}
					}else{
						$('div#languageErrorContainar li').remove();
						$('div#languageErrorContainar ol').show();
						$('div#languageErrorContainar ol').append('<li><label class="error"><fmt:message key='LANGUAGE_LEAST_1'/></label></li>');
						container.css({'display':'block'});
               			$('html, body').animate({scrollTop: '0px'}, 300); 
               			return false ;
					}
				});
				
				//---- button for submit ----//
				
				
				$('#idLanguage').change(function()
				{
					if($(this).val()!="")
					{
						$('#language_listen').removeAttr('disabled');
						$('#language_spaek').removeAttr('disabled');
						$('#language_read').removeAttr('disabled');
						$('#language_write').removeAttr('disabled');
						$('#language_listen').val("");
						$('#language_spaek').val("");
						$('#language_read').val("");
						$('#language_write').val("");
						$('#language_listen').rules("add", {
							required:true,
						    messages: {
						       required:"<fmt:message key='SKILL_LANGUAGE_LISTEN'/>"
						    }
						});
						$('#language_spaek').rules("add", {
							required:true,
						    messages: {
						       required:"<fmt:message key='SKILL_LANGUAGE_SPEAK'/>"
						    }
						});
						$('#language_read').rules("add", {
							required:true,
						    messages: {
						       required:"<fmt:message key='SKILL_LANGUAGE_READ'/>"
						    }
						});
						$('#language_write').rules("add", {
							required:true,
						    messages: {
						       required:"<fmt:message key='SKILL_LANGUAGE_WRITE'/>"
						    }
						});
					}
					else
					{
						$('#language_listen').val("").prop('disabled',true);
						$('#language_spaek').val("").prop('disabled',true);
						$('#language_read').val("").prop('disabled',true);
						$('#language_write').val("").prop('disabled',true);
						$('#language_listen').rules('remove');
						$('#language_spaek').rules('remove');
						$('#language_read').rules('remove');
						$('#language_write').rules('remove');
						$('#language_listen').removeClass('error');
						$('#language_spaek').removeClass('error');
						$('#language_read').removeClass('error');
						$('#language_write').removeClass('error');
					}
				});
				
				$('#idTest').change(function()
				{
					if($(this).val()!="")
					{
						$('#score_listen').removeAttr('disabled');
						$('#score_spaek').removeAttr('disabled');
						$('#score_read').removeAttr('disabled');
						$('#score_write').removeAttr('disabled');
						if($(this).val()=="other")
						{
							$('#otherLayer').show();
							$('#otherTest').focus();
							$('#otherTest').rules("add", {
							required:true,
						    messages: {
						       required:"<fmt:message key='TEST_OTHER_VALIDATION'/>"
						    }
						});
						}else
						{
							$('#otherLayer').hide();
							$('#score_listen').focus();
							$('#otherTest').rules('remove');
							$('#otherTest').removeClass('error');
						}
					}
					else
					{
						$('#score_listen').val("").prop('disabled',true);
						$('#score_spaek').val("").prop('disabled',true);
						$('#score_read').val("").prop('disabled',true);
						$('#score_write').val("").prop('disabled',true);
						
					}
				});
				
				
				$('#skillLanguageFrm').validate(
					{
						errorContainer: container,
						errorLabelContainer: $("ol", container),
						wrapper: 'li',
						focusInvalid: false,
						invalidHandler: function(form, validator) 
						{
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
			        	messages :
						{
							idLanguage:
							{
								required:"<fmt:message key='SKILL_LANGUAGE_VALIDATION'/>"
							}
						},
						submitHandler:function(form)
						{
							container.hide();
							$.ajax(
							{
								type: "POST",
				        		url: '/SkillLanguageServ',
				       			data: $('#skillLanguageFrm').serialize(),
				       			async:false,
				       			success: function(data)
				       			{
				       				var obj = jQuery.parseJSON(data);
				       				if(obj.success==1)
				       				{
				       					getSkillLanguage();
				       					$('#idLanguage').val("");
				       					$('#language_listen').val("");
										$('#language_spaek').val("");
										$('#language_read').val("");
										$('#language_write').val("");
				       					$('#language_listen').prop('disabled',true);
										$('#language_spaek').prop('disabled',true);
										$('#language_read').prop('disabled',true);
										$('#language_write').prop('disabled',true);
				       					
				       				}
				           			else
				           			{
				           				$('div#languageErrorContainar li').remove();
				           				if(obj.urlError!=""){
				           					window.location.href = obj.urlError;
				           				}
				           				else{
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
					
					$('#testFrm').validate(
					{
						errorContainer: container2,
						errorLabelContainer: $("ol", container2),
						wrapper: 'li',
						focusInvalid: false,
						invalidHandler: function(form, validator) 
						{
						$('html, body').animate({scrollTop:"600px" }, 300);      
						},
						rules :
						{
							idTest:
							{
								required:true
							},
							score_listen:
							{
								number:true,
								maxlength: 4
							},
							score_spaek:
							{
								number:true,
								maxlength: 4
							},score_read:
							{
								number:true,
								maxlength: 4
							},score_write:
							{
								number:true,
								maxlength: 4
							}
							
						},
						messages :
						{
							idTest:
							{
								required:"<fmt:message key='TEST_VALIDATION'/>"
							},
							score_listen:
							{
								number:"<fmt:message key='TEST_NUMBER_ONLY'/>",
								maxlength:"<fmt:message key='TEST_SCORE_LENGTH'/>"
							},
							score_spaek:
							{
								number:"<fmt:message key='TEST_NUMBER_ONLY'/>",
								maxlength: "<fmt:message key='TEST_SCORE_LENGTH'/>"
							},score_read:
							{
								number:"<fmt:message key='TEST_NUMBER_ONLY'/>",
								maxlength: "<fmt:message key='TEST_SCORE_LENGTH'/>"
							},score_write:
							{
								number:"<fmt:message key='TEST_NUMBER_ONLY'/>",
								maxlength: "<fmt:message key='TEST_SCORE_LENGTH'/>"
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
							var chk =0;
							if($('#score_listen').val()!="" || $('#score_spaek').val()!="" || $('#score_read').val()!="" || $('#score_write').val()!=""){
								chk = 1; 
							}else{
								$(this).addClass("error");
								$('div#testErrorContainar li').remove();
								container2.find("ol").append("<li><fmt:message key='TEST_SCORE_LEAST_1'/></li>");
				   				container2.css({'display':'block'});
				   				container2.find("ol").css({'display':'block'});
				   				$('html, body').animate({scrollTop: '600px'}, 300); 
							}
							if(chk==1){
								$.ajax(
								{
									type: "POST",
					        		url: '/SkillLanguageServ',
					       			data: $('#testFrm').serialize(),
					       			async:false,
					       			success: function(data)
					       			{
					       				var obj = jQuery.parseJSON(data);
					       				if(obj.success==1)
					       				{
					       					getTest();
					       					$('#idTest').val("");
					       					$('#otherLayer').hide();
					       					$('#otherTest').val("");
					       					$('#score_listen').val("").prop('disabled',true);
											$('#score_spaek').val("").prop('disabled',true);
											$('#score_read').val("").prop('disabled',true);
											$('#score_write').val("").prop('disabled',true);
					       					
					       				}
					           			else
					           			{
					           				$('div#testErrorContainar li').remove();
					           				if(obj.urlError!=""){
					           					window.location.href = obj.urlError;
					           				}
					           				else{
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
			       			return false;
						}
					});
			});
</script>
<div class="seperator"></div>
	<div align="right">
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div>
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent:0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_EDUCATION"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
	<br>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="section_header alignCenter">
   				 <h3><fmt:message key="LANGUAGE_SKILL"/></h3>
 			</div>
		</div>
	</div>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="suggestion">
   				 <h5><fmt:message key="DIRECTION_SKILL_LANGUAGE"/></h5>
 			</div>
		</div>
	</div>
	<form id="skillLanguageFrm" name="skillLanguageFrm" class="form-horizontal">
		<input type="hidden" name="idResume" value='<c:out value="${resume.idResume}"/>'/>
		<input type="hidden" name="service" value="addSkillLanguage" />
		<div class="errorContainer row" id="languageErrorContainar">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<label class="control-label"><fmt:message key="LANGUAGE_YOUR_SKILL"/></label>
			</div>
		</div>
		<div class="row" id="skillLanguageListLayer" style="display:none;">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
				<div id="skillLanguageList"></div>
			</div>	
		</div>
		<div class="modal fade" id="md1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  		<div class="modal-dialog modal-sm">
	    		<div class="modal-content">
	      			<div class="modal-header">
	       				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        			<h4 class="modal-title">Message</h4>
	      			</div>
	      			<div class="modal-body">
	       		 		<p><fmt:message key="CONFIRM_DELETE_LANGUAGE"/></p>
	      			</div>
	      			<div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
				        <button type="button" class="btn btn-primary" onclick="btnDelLanguage()"><fmt:message key="GLOBAL_CONFIRM"/></button>
				        
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<br>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
				<fmt:message key="LANGUAGE_INFORMATION"/>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label for="idLanguage"><fmt:message key="LANGUAGE_TYPE"/></label>
					<select id="idLanguage" name="idLanguage" class="required form-control" >
						<option value=""></option>
						<c:forEach var="language" items="${language}">
							<option value="<c:out value="${language.id}"/>"><c:out value="${language.name}"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<label class="control-label"><fmt:message key="LANGUAGE_LEVEL"/></label>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label for="idLanguage"><fmt:message key="LANGUAGE_LISTEN"/></label>
					<select id="language_listen" name="language_listen" class="required form-control">
						<option value=""></option>
						<option value="1"><fmt:message key="SKILL_LANGUAGE_LEARNING"/></option>
						<option value="2"><fmt:message key="SKILL_LANGUAGE_BEGINNER"/></option>
						<option value="3"><fmt:message key="SKILL_LANGUAGE_INTERMEDIATE"/></option>
						<option value="4"><fmt:message key="SKILL_LANGUAGE_ADVANCED"/></option>
						<option value="5"><fmt:message key="SKILL_LANGUAGE_FLUENT"/></option>
					</select>
				</div>
				<div class="form-group">
					<label  for=language_spaek><fmt:message key="LANGUAGE_SPAEK"/></label>
					<select id="language_spaek" name="language_spaek"  class="required form-control">
						<option value=""></option>
						<option value="1"><fmt:message key="SKILL_LANGUAGE_LEARNING"/></option>
						<option value="2"><fmt:message key="SKILL_LANGUAGE_BEGINNER"/></option>
						<option value="3"><fmt:message key="SKILL_LANGUAGE_INTERMEDIATE"/></option>
						<option value="4"><fmt:message key="SKILL_LANGUAGE_ADVANCED"/></option>
						<option value="5"><fmt:message key="SKILL_LANGUAGE_FLUENT"/></option>
					</select>
				</div>
				<div class="form-group">
					<label  for="language_read"><fmt:message key="LANGUAGE_READ"/></label>
					<select id="language_read" name="language_read"  class="required form-control">
						<option value=""></option>
						<option value="1"><fmt:message key="SKILL_LANGUAGE_LEARNING"/></option>
						<option value="2"><fmt:message key="SKILL_LANGUAGE_BEGINNER"/></option>
						<option value="3"><fmt:message key="SKILL_LANGUAGE_INTERMEDIATE"/></option>
						<option value="4"><fmt:message key="SKILL_LANGUAGE_ADVANCED"/></option>
						<option value="5"><fmt:message key="SKILL_LANGUAGE_FLUENT"/></option>
					</select>
				</div>
				<div class="form-group">
					<label  for="language_write"><fmt:message key="LANGUAGE_WRITE"/></label>
					<select id="language_write" name="language_write"  class="required form-control">
						<option value=""></option>
						<option value="1"><fmt:message key="SKILL_LANGUAGE_LEARNING"/></option>
						<option value="2"><fmt:message key="SKILL_LANGUAGE_BEGINNER"/></option>
						<option value="3"><fmt:message key="SKILL_LANGUAGE_INTERMEDIATE"/></option>
						<option value="4"><fmt:message key="SKILL_LANGUAGE_ADVANCED"/></option>
						<option value="5"><fmt:message key="SKILL_LANGUAGE_FLUENT"/></option>
					</select>
				</div>
			</div>
		</div>
		
		<div align="right">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/save.png"  id="addMoreLanguage" />
		</div>
	</form>
	<form name="testFrm" id="testFrm" class="form-horizontal">
		<input type="hidden" name="idResume" value='<c:out value="${resume.idResume}"/>'/>
		<input type="hidden" name="service" value="addTest" />
		<div class="errorContainer row" id="testErrorContainar">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
		
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
				<div id="scoreList"></div>	
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label  for="idTest"><fmt:message key="LANGUAGE_TEST"/></label>
					<select id="idTest" name="idTest" class="required form-control">
						<option value=""></option>
						<option value="TOEFL">TOEFL</option>
						<option value="TOEIC">TOEIC</option>
						<option value="IELTS">IELTS</option>
						<option value="other"><fmt:message key="EDU_OTHER"/></option>
					</select>
					
				</div>
				
			</div>
		</div>
		<div class="row" id="otherLayer" style="display:none;">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-10 col-md-10 col-sm-11 col-xs-12">
				<div class="form-group">
					<label  for="otherTest"><fmt:message key="GLOBAL_OTHER_SPECIFY"/></label>
					<input type="text" name="otherTest" id="otherTest" class="form-control"/>
				</div>
				
			</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label  for="idLanguage"><fmt:message key="LANGUAGE_LISTEN"/></label>
					<input type="text" name="score_listen" class="form-control " id="score_listen" />
				</div>
				<div class="form-group">
					<label  for=language_spaek><fmt:message key="LANGUAGE_SPAEK"/></label>
					<input type="text" name="score_spaek" class="form-control " id="score_spaek"  />
				</div>
				<div class="form-group">
					<label  for="language_read"><fmt:message key="LANGUAGE_READ"/></label>
					<input type="text" name="score_read" class="form-control " id="score_read" />
				</div>
				<div class="form-group">
					<label  for="language_write"><fmt:message key="LANGUAGE_WRITE"/></label>
					<input type="text" name="score_write" class="form-control " id="score_write" />
				</div>
			</div>
		</div>
		<div align="right">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/save.png"  id="addMoreTest" />
		</div>
	</form>
	<br>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  name="btn_submit" id="btn_submit" />
		</div>
	</div>
	<br>
	<br>
	