<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.ComputerGroup"%>
<%@page import="com.topgun.shris.masterdata.Computer"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.*"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	int idLanguage=resume.getIdLanguage();
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	List<ComputerGroup> computerGroup = MasterDataManager.getAllComputerGroup(idLanguage);
	request.setAttribute("resume",resume);
	request.setAttribute("computerGroup", computerGroup);
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
 	<script type="text/javascript">
		var sequence=<c:out value="${sequence}"/>;
		var idResume=<c:out value="${resume.idResume}"/>;
		
		function getSkillComputerList(){
 		$.ajax(
			{
				type: "POST",
        		url: '/SkillComputerServ',
       			data: {'service':'getSkillComputer','idResume':'<c:out value="${resume.idResume}"/>'},
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
	       						$('#skillComputerList').append('<td><a data-toggle="modal" href="#" onclick="delSkillComputer('+obj.idSkillComputer[i]+');" ><fmt:message key="GLOBAL_DELETE"/></a></td>');
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
 		function dialogDelComputer(idSkillLanguage){
		$('#md1').data('id',idSkillLanguage).modal('show');
		}
		function btnDelComputer(){
			var id = $('#md1').data('id');
			delSkillComputer(id);
			$('#md1').modal("hide");	
		}
		function getSkillOtherList(){
 		$.ajax(
			{
				type: "POST",
        		url: '/SkillComputerServ',
       			data: {'service':'getSkillOther','idResume':'<c:out value="${resume.idResume}"/>'},
       			async:false,
       			success: function(data)
       			{
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					$('#skillOtherList').empty();
       					if(obj.skillOther.length>0){
	       					$('#skillOtherList').append('<table style="border-collapse:collapse;">');
	       					for(var i=0;i<obj.skillOther.length;i++)
	       					{
	       						$('#skillOtherList').append('<tr>');
	       						$('#skillOtherList').append('<td>'+(i+1)+'. '+obj.skillOther[i]+'</td>');
	       						$('#skillOtherList').append('<td>&nbsp;</td>');
	       						$('#skillOtherList').append('<td>&nbsp;&nbsp;&nbsp;<a data-toggle="modal" href="#" onclick="delSkillOther('+obj.idSkillOther[i]+')" ><fmt:message key="GLOBAL_DELETE"/></a></td>');
	       						$('#skillOtherList').append('</tr>');
	       						$('#skillOtherList').append('<tr>');
	       						$('#skillOtherList').append('<td colspan="3" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+obj.levelType[i]+'</td>');
	       						$('#skillOtherList').append('</tr>');
	       					}
	       					$('#skillOtherList').append('</table>');
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
 		function dialogDelOther(idSkill){
			$('#md2').data('id',idSkill).modal('show');
		}
		function btnDelOther(){
			var id = $('#md2').data('id');
			delSkillOther(id);
			$('#md2').modal("hide");	
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
      	function delSkillOther(idSkillOther){
	       $.ajax(
			{
				type: "POST",
        		url: '/SkillComputerServ',
       			data: {'service':'delSkillOther','idResume':'<c:out value="${resume.idResume}"/>','idSkillOther':idSkillOther},
       			async:false,
       			success: function(data)
       			{
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					getSkillOtherList();
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
 		getSkillComputerList();
		getSkillOtherList();
		$('#otherLayer').hide();
		$('#idSkillComputer').prop('disabled','disabled');
		$('#level_skillComputer').prop('disabled','disabled');
		$('#level_skillOther').prop('disabled','disabled');
		
		$('#btn_submit').click(function(){
			if($('#skillComputerList').html()!="" || $('#skillOtherList').html()!=""){
				if(sequence==0){
					window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
				}else{
					window.location.href = "/SRIX?view=targetJob&idResume="+idResume+"&sequence="+sequence;
				}
			}else{
				$('div#computerErrorContainar li').remove();
				$('div#computerErrorContainar ol').show();
				$('div#computerErrorContainar ol').append('<li><label class="error"><fmt:message key='COM_OR_OTHER_LEAST_1'/></label></li>');
				container.css({'display':'block'});
             	$('html, body').animate({scrollTop: '0px'}, 300); 
             	return false ;
			}
		});
		var container = $('div#computerErrorContainar');
		var container2 = $('div#otherErrorContainar');
		
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
	       					$('#level_skillComputer').val("");
	       					$('#idSkillComputer').prop('disabled','disabled');
							$('#level_skillComputer').prop('disabled','disabled');
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
		
		$('#skillOtherFrm').validate(
		{
			errorContainer: container2,
			errorLabelContainer: $("ol", container2),
			wrapper: 'li',
			focusInvalid: false,
			invalidHandler: function(form, validator) 
			{
				$('html, body').animate({scrollTop:"300px" }, 300);
			},
			messages :
			{
				skillOther:
				{
					required:"<fmt:message key='SKILL_OTHER_REQUIRE'/>"
				},
				level_skillOther:
				{
					required:"<fmt:message key='SKILL_OTHER_LEVEL_REQUIRE'/>"
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
				$.ajax(
				{
					type: "POST",
	        		url: '/SkillComputerServ',
	       			data: $('#skillOtherFrm').serialize(),
	       			async:false,
	       			success: function(data)
	       			{
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					getSkillOtherList();
	       					$('#skillOther').val("");
	       					$('#level_skillOther').val("");
							$('#level_skillOther').prop("disabled","disabled");
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
		});
			
			$('#idGroupComputer').change(function()
			{
				if($(this).val()!="")
				{
					var idGroupComputer = $(this).val();
					getSkillComputer(idGroupComputer);
					$('#idSkillComputer').removeAttr('disabled');
					$('#level_skillComputer').removeAttr('disabled');
					$('#idSkillComputer').val("");
					$('#level_skillComputer').val("");
				}
				else
				{
					$('#idSkillComputer').empty();
			       	$('#idSkillComputer').append('<option value=""></option>');
			       	$('#idSkillComputer').val("");
			       	$('#idSkillComputer').prop('disabled','disabled');
			       	$('#level_skillComputer').val("");
			      	$('#level_skillComputer').prop('disabled','disabled');
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
					       required:"Other Skill Computer is required."
					    }
					});
				}else{
					$('#otherLayer').hide();
					$('#otherCom').rules('remove');
					$('#otherCom').removeClass('error');
				}
			});
			
			function getSkillComputer(idGroupComputer)
			{
				if(idGroupComputer>0)
				{
					$.ajax(
						{
							type: "POST",
			        		url: '/SkillComputerServ',
			       			data: {'service':'skillComputer','idGroupComputer':idGroupComputer,'idResume':'<c:out value="${resume.idResume}"/>'},
			       			global: false,
							async :true,
							timeout : 10000,
			       			success: function(data)
			       			{
			       				var obj = jQuery.parseJSON(data);
			       				if(obj.success==1)
			       				{
			       					$('#idSkillComputer').empty();
			       					$('#idSkillComputer').append('<option value=""></option>');
			       					for(var i=0;i<obj.comList.length;i++)
			       					{
										$('#idSkillComputer').append('<option value="'+obj.comList[i].idComputer+'">'+obj.comList[i].computerName+'</option>');
									}
									$('#idSkillComputer').append('<option value="-1"><fmt:message key="EDU_OTHER"/></option>');
									
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
				return false;
			}
			
			$('#skillOther').keyup(function(){
				var text = $(this).val();
				if(text!=""){
					$('#level_skillOther').removeAttr("disabled");
				}else{
					$('#level_skillOther').val("");
					$('#level_skillOther').prop("disabled","disabled");
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
			  <p style="text-align:justify !important;text-indent: 0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_EDUCATION"/></p>
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
   				 <h3><fmt:message key="SKILLS_COMPUTER"/></h3>
 			</div>
		</div>
	</div>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="suggestion">
   				 <h5><fmt:message key="DIRECTION_SKILL_COMPUTER"/></h5>
 			</div>
		</div>
	</div>
	<form name="skillComputerFrm" id="skillComputerFrm" class="form-horizontal">
		<input type="hidden" name="idResume" value='<c:out value="${resume.idResume}"/>'/>
		<input type="hidden" name="service" value="addSkillComputer" />
		<br>
		<div class="errorContainer row" id="computerErrorContainar">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
		<div class="row" id="skillComputerListLayer" style="display:none;"> 
			<div class="col-lg-2 col-md-2 col-sm-1 col-xs-1">
				&nbsp;
			</div>
			<div class="col-lg-10 col-md-10 col-sm-11 col-xs-11">
				<div id="skillComputerList"></div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label for="idGroupComputer"><fmt:message key="SKILLS_COMPUTER_CATEGORY"/></label>
					<select id="idGroupComputer" name="idGroupComputer" class="required form-control">
						<option value="" ></option>
						<c:forEach var="computerGroup" items="${computerGroup}">
							<option value="<c:out value="${computerGroup.idGroup}"/>"><c:out value="${computerGroup.groupName}"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
			
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label for="idSkillComputer"><fmt:message key="SKILLS"/></label>
					<select id="idSkillComputer" name="idSkillComputer" class="form-control required" >
						<option value=""></option>
					</select>
				</div>
			</div>
		</div>
		<div class="row" id="otherLayer">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label for="otherCom"><fmt:message key="GLOBAL_OTHER_SPECIFY"/></label>
					<input type="text" name="otherCom" id="otherCom" class="form-control" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label for="level_skillComputer"><fmt:message key="SKILLS_PROFICIENCY"/></label>
					<select id="level_skillComputer" name="level_skillComputer"  class="required form-control">
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
		<div align="right">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/save.png" id="addMoreSkillComputer" />
		</div>
	</form>
	<form name="skillOtherFrm" id="skillOtherFrm" class="form-horizontal">
		<input type="hidden" name="idResume" value='<c:out value="${resume.idResume}"/>'/>
		<input type="hidden" name="service" value="addSkillOther" />
		<div class="errorContainer row" id="otherErrorContainar">
			<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
			<ol></ol>
		</div>
		<div class="row"> 
			<div class="col-lg-2 col-md-2 col-sm-1 col-xs-1">
				&nbsp;
			</div>
			<div class="col-lg-10 col-md-10 col-sm-11 col-xs-11">
				<div id="skillOtherList"></div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label for="skillOther"><fmt:message key="EDU_OTHER"/></label>
					<input type="text" id="skillOther" name="skillOther" class="form-control required"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-1 col-md-1 col-sm-1">
				&nbsp;
			</div>
			<div class="col-lg-11 col-md-11 col-sm-11 col-xs-12">
				<div class="form-group">
					<label  for="level_skillOther"><fmt:message key="SKILLS_PROFICIENCY"/></label>
					<select id="level_skillOther" name="level_skillOther" class="form-control required">
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
		<div align="right">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/save.png" id="addMoreSkillOther" />
		</div>
	</form>
	<div class="row" align="center">
		&nbsp;
	</div>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png"  name="btn_submit" id="btn_submit" />
		</div>
	</div>
	<br>