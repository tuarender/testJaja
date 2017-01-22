<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%
	int idJsk=Util.StrToInt(""+session.getAttribute("SESSION_ID_JSK"));
	int errorId = Util.getInt(request.getParameter("ErrorId"));
	String locale = Util.getStr(session.getAttribute("SESSION_LOCALE"));
	String service = Util.getStr(request.getParameter("cancelService"),"jobmatch");
	int idLanguage = 11;
	boolean jskExist=new JobseekerManager().isExist(idJsk);
	if(!jskExist)
	{
		response.sendRedirect("/LogoutServ");
		return;
	}
	if(locale.equals("th_TH")){
		idLanguage = 38;
	}
	request.setAttribute("locale",locale);
	request.setAttribute("service",service);
	String username = new JobseekerManager().get(idJsk).getUsername();
%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<style type="text/css">
fieldset { border:none; }
legend { font-size:18px; margin:0px; padding:10px 0px; color:#b0232a; font-weight:bold;}
label { display:block; margin:15px 0 5px;}
input[type=text], input[type=password] { width:200px; padding:5px; border:solid 1px #000;}
.prev, .next { background-color:#b0232a; padding:5px 10px; color:#fff; text-decoration:none;}
.prev:hover, .next:hover { background-color:#000; text-decoration:none;}
#steps { list-style:none; width:100%; overflow:hidden; margin:0px; padding:0px;}
#steps li {font-size:24px; float:left; padding:10px; color:#b0b1b3;}
#steps li span {font-size:11px; display:block;}
#steps li.current { color:#000;}
#makeWizard { background-color:#b0232a; color:#fff; padding:5px 10px; text-decoration:none; font-size:18px;}
#makeWizard:hover { background-color:#000;}
</style>
<link href="/css/cancelStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var errorId = <%=errorId%>;
	var service = "<%=service%>";
	var selectOften = '<center><fmt:message key="CANCEL_SELECT_OFTEN"/></center>';
	if(service=="jobupdate"){
		selectOften = '<center><fmt:message key="CANCEL_JU_SELECT_OFTEN"/></center>';
	}
	
    $(document).ready(function(){
    	if(errorId>0){
    		$("#reasonType").val("5");
    		$("#reason1").hide();
    		$("#reason5_detail").show();
    		$("#changeMail").attr('checked','checked');
    		$("#reason5_changeEmail").slideDown(400);
    		$("#changeMailError").html("<center><% //new ErrorLang(errorId,idLanguage).getErrorMessage()%></center>");
    	}
    	
        $("#next").click(function(){
        	var urlSubmit = "";
        	if($("#reasonType").val()>0){
        		if($("#reasonType").val()==1||$("#reasonType").val()==3||$("#reasonType").val()==6){
        			var reasonType = $("#reasonType").val();
        			var howLong = $("input:checked[name=howLong"+reasonType+"]").val();
    			    if(howLong==null){
       					$("#reason"+$("#reasonType").val()+"Error").html(selectOften);
       				}
       				else{
       					urlSubmit = "reasonType="+reasonType+"&howLong="+howLong;
       					if($("#reasonType").val()==6){
       						$("#reason6ErrorEmail").html("");
       						if($("#otherReason").val()!=""){
       							urlSubmit+="&otherReason="+encodeURI($("#otherReason").val());
       						}
       						if($("#tel").val()!=""){
       							urlSubmit+="&tel="+encodeURI($("#tel").val());
       						}
       						if($("#otherEmail").val()!=""){
       							if(!validateEmail($("#otherEmail").val())){
	       							$("#reason6ErrorEmail").html('<center><fmt:message key="CANCEL_INVALID_EMAIL"/></center>');
	       							urlSubmit = "";
	       						}	
	       						else{
	       							urlSubmit+="&email="+encodeURI($("#otherEmail").val());
	       						}
       						}
       					}
       					$("#reason"+$("#reasonType").val()+"Error").html("");
       				}
        		}
        		else if($("#reasonType").val()==2){
        			var reasonType = $("#reasonType").val();
        			var notMatch = $("input:checked[name=notMatch]").val();
        			var howLong = $("input:checked[name=howLong"+reasonType+"]").val();
        			if(notMatch==null){
       					$("#reason2Error").html('<center><fmt:message key="CANCEL_PLEASE_SELECT"/></center>');
       				}
       				else{
       					if(notMatch==2){
       						if(howLong==null){
		       					$("#reason2_2Error").html(selectOften);
		       				}
		       				else{
		       					urlSubmit = "reasonType="+reasonType+"&notMatch="+notMatch+"&howLong="+howLong;
		       					$("#reason2_2Error").html("");
		       				}
       					}
       					else{
       						urlSubmit = "reasonType="+reasonType+"&notMatch="+notMatch;
       					}
       				}
        		}
        		else if($("#reasonType").val()==4){
        			var reasonType = $("#reasonType").val();
        			var howOften = $("input:checked[name=howOften]").val();
        			var howLong = $("input:checked[name=howLong"+reasonType+"]").val();
        			if(howOften==null){
        				$("#reason4Error").html('<center><fmt:message key="CANCEL_PLEASE_SELECT"/></center>');
        			}
        			else{
        				if(howOften!=4){
		       				urlSubmit = "reasonType="+reasonType+"&howOften="+howOften;
		       				if(howOften==1&&$('#service').val()=="jobupdate"){
		       					urlSubmit+= "&juDay="+$('#juDay').val();
		       				}
        				}
        				else{
	        				if(howLong==null){
		       					$("#reason4_2Error").html(selectOften);
		       				}
		       				else{
		       					$("#reason4_2Error").html("");
		       					urlSubmit = "reasonType="+reasonType+"&howOften="+howOften+"&howLong="+howLong;
		       				}
        				}
        			}
        		}
        		else if($("#reasonType").val()==5){
        			var reasonType = $("#reasonType").val();
        			var changeMail = $("input:checked[name=changeMail]").val();
        			var howLong = $("input:checked[name=howLong"+reasonType+"]").val();
        			if(changeMail==1){
        				if($('#newUsername').val()==""||$('#confirmUsername').val()==""||$('#password').val()==""){
        					$("#changeMailError").html('<center><fmt:message key="CANCEL_REQUIRED_FIELD"/></center>');
        				}
        				else{
        					if(!validateEmail($('#newUsername').val())||!validateEmail($('#confirmUsername').val())){
        						$("#changeMailError").html('<center><fmt:message key="CANCEL_INVALID_EMAIL"/></center>');
        					}
        					else if($('#newUsername').val()!=$('#confirmUsername').val()){
        						$("#changeMailError").html('<center><fmt:message key="CANCEL_INVALID_EMAIL_NOT_SAME"/></center>');
        					}
        					else{
        						$("#changeMailError").html("");
        						$("#changeMailForm").submit();
        					}
        				}
        			}
        			else if(changeMail==2){
        				if(howLong==null){
        					$("#reason5_2Error").html(selectOften);
        				}
        				else{
        					$("#reason5_2Error").html("");
        					urlSubmit = "reasonType="+reasonType+"&changeMail="+changeMail+"&howLong="+howLong;
        				}
        			}
        			else{
        				$("#reason5Error").html("<center>Please select data.</center>");
        			}
        		}
        	}
        	else{
	        	if($("input:checked[name=reason]").val()!=null){
	        		$("#cancel").fadeIn(300);
	        		$("#reasonType").val($("input:checked[name=reason]").val());
	        		$('#reason1').slideUp(400);
	        		$('#reason'+$("#reasonType").val()+'_detail').delay(400).slideDown(400);
	        		$("#reasonError").html("");
	        	}
	        	else{
	        		$("#reasonError").html('<center><fmt:message key="CANCEL_SELECT_REASON"/></center>');
	        	}
        	}
        	
        	if(urlSubmit!=""){
        		var service = $("#service").val();
        		urlSubmit+="&cancelService="+service;
        		window.location.href = "/CancelServlet?"+urlSubmit;
        	}
        });
        $("#cancel").click(function(){
        	if($("#reasonType").val()>0){
        		$("#cancel").fadeOut(300);
        		$('#reason'+$("#reasonType").val()+'_detail').slideUp(400);
        		$('#reason1').delay(400).slideDown(400);
        		$("#reasonType").val(0);
        	}
        	else{
        		if(confirm('<fmt:message key="CANCEL_LOGOUT"/>')){
        			window.location.href = "http://www.superresume.com";
        		}
        	}
        });
        
        $("input[name^='howOften']").click(function (){
        	if($(this).val()==1){
        		$("#juDayDiv").slideDown(500);
        	}
        	else{
        		$("#juDayDiv").slideUp(500);
        	}
        });
        
        $("#logout").click(function(){
        	if(confirm('<fmt:message key="CANCEL_LOGOUT"/>')){
       			window.location.href = "/LogoutServ";
       		}
        });
        
        $("textarea[maxlength]").bind('input propertychange', function() {  
	        var maxLength = $(this).attr('maxlength');  
	        if ($(this).val().length > maxLength) {  
	            $(this).val($(this).val().substring(0, maxLength));  
	        }  
	    });
    });
    
    function validateEmail(email){
		var atpos=email.indexOf("@");
		var dotpos =email.lastIndexOf(".");
		if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
			return false;
		}
		else{
			return true; 
		}
	}
    
    function checkConfirm(confirm){
    	if(confirm){
    		if($("#reasonType").val()==5){
    			$('#reason'+$("#reasonType").val()+'_changeEmail').slideUp(400);
    			$("#reason5Error").html("");
    		}
    		$('#reason'+$("#reasonType").val()+'_confirmCancel').slideDown(400);
    		$("#reason"+$("#reasonType").val()+"Error").html("");
    	}
    	else{
    		if($("#reasonType").val()==5){
    			$('#reason'+$("#reasonType").val()+'_changeEmail').slideDown(400);
    			$("#reason5Error").html("");
    		}
    		$('#reason'+$("#reasonType").val()+'_confirmCancel').slideUp(400);
    		$("#reason"+$("#reasonType").val()+"Error").html("");
    	}
    }
    
</script>
<div class="contentCancel">
	<div align="right" style="font-size:14px"><a id="logout" href="javascript:void(0)"><fmt:message key="SECTION_LOGOUT"/></a></div>
	<input type="hidden" id="reasonType" name="reasonType" value="0">
	<input type="hidden" id="service" name="service" value="<c:out value="${service}"/>">
	<div id="contentForm">
		<div id="reason1" style="margin-top:15px">
			<span style="font-size:16px;font-weight:bold">
			<c:choose>
				<c:when test="${(service eq 'jobmatch') or (service eq 'jmfreq') or (service eq 'jmcrit')}">
					<fmt:message key="CANCEL_JM_REASON"/>
				</c:when>
				<c:when test="${service eq 'jobupdate'}">
					<fmt:message key="CANCEL_JU_REASON"/>
				</c:when>
			</c:choose>
			</span>
			<table width="100%" cellpadding="0" cellspacing="5" style="margin-top:15px">
				<tr>
					<td valign="top" width="24"><input name='reason' type='radio' value="1" ></td>
					<td><fmt:message key="CANCEL_JM_REASON_1"/></td>
				</tr>
				<c:if test="${service eq 'jobmatch'}">
				<tr>
					<td valign="top" width="24"><input name='reason' type='radio' value="2"></td>
					<td><fmt:message key="CANCEL_JM_REASON_2"/></td>
				</tr>
				</c:if>
				<tr>
					<td valign="top" width="24"><input name='reason' type='radio' value="3"></td>
					<td><fmt:message key="CANCEL_JM_REASON_3"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='reason' type='radio' value="4"></td>
					<td><fmt:message key="CANCEL_JM_REASON_4"/></td>
				</tr>
				<c:if test="${service eq 'jobmatch'}">
				<tr>
					<td valign="top" width="24"><input name='reason' type='radio' value="5"></td>
					<td><fmt:message key="CANCEL_JM_REASON_5"/></td>
				</tr>
				</c:if>
				<tr>
					<td valign="top" width="24"><input name='reason' type='radio' value="6"></td>
					<td><fmt:message key="CANCEL_OTHERS"/></td>
				</tr>
			</table>
			<div id="reasonError" class='error'></div>
			<br>
		</div>
		<div id="reason1_detail" style="display:none;margin-top:15px">
			<span style="font-size:16px;font-weight:bold;"><fmt:message key="CANCEL_JM_REASON_STATE2"/>&nbsp;<fmt:message key="CANCEL_JM_REASON_1"/></span>
			<div style="margin:15px 0px 15px;">
			<c:choose>
				<c:when test="${service eq 'jobmatch'}">
					<fmt:message key="CANCEL_JM_REASON_1_DETAIL"/>
				</c:when>
				<c:when test="${service eq 'jobupdate'}">
					<fmt:message key="CANCEL_JU_REASON_1_DETAIL"/>
				</c:when>
			</c:choose>
			</div>
			<table width="100%" cellpadding="0" cellspacing="5" style="margin-top:15px">
				<tr>
					<td valign="top" width="24"><input name='howLong1' type='radio' value="1"></td>
					<td><fmt:message key="CANCEL_JM_3_MONTHS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong1' type='radio' value="2"></td>
					<td><fmt:message key="CANCEL_JM_6_MONTHS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong1' type='radio' value="3"></td>
					<td><fmt:message key="CANCEL_JM_1_YEARS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong1' type='radio' value="4"></td>
					<td><fmt:message key="CANCEL_JM_2_YEARS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong1' type='radio' value="5"></td>
					<td><fmt:message key="CANCEL_JM_NOT_SPECIFIED"/></td>
				</tr>
			</table>
			<div id="reason1Error" class='error'></div>
			<br>
		</div>
		<div id="reason2_detail" style="display:none;margin-top:15px">
			<span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_REASON_STATE2"/>&nbsp;<fmt:message key="CANCEL_JM_REASON_2"/></span>
			<div style="margin:15px 0px 15px;">
			<fmt:message key="CANCEL_JM_REASON_2_DETAIL"/>
			</div>
			<table width="100%" cellpadding="0" cellspacing="5" style="margin-top:15px">
				<tr>
					<td valign="top" width="24"><input name='notMatch' type='radio' value="1" onclick="checkConfirm(false)"></td>
					<td><fmt:message key="CANCEL_JM_REASON_2_CHOICE_1"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='notMatch' type='radio' value="2" onclick="checkConfirm(true)"></td>
					<td><fmt:message key="CANCEL_JM_CONFIRM_CANCEL"/></td>
				</tr>
			</table>
			
			<div id="reason2_confirmCancel" style="display:none;width:90%;margin:auto;padding:10px;background-color:#FDFCEA;border:2px solid;border-radius:10px;">
				<div style="margin:0px 0px 15px"><fmt:message key="CANCEL_JM_REASON_1_DETAIL"/></div>
				<input name='howLong2' type='radio' value="1">&nbsp;<fmt:message key="CANCEL_JM_3_MONTHS"/>
				<br><input name='howLong2' type='radio' value="2">&nbsp;<fmt:message key="CANCEL_JM_6_MONTHS"/>
				<br><input name='howLong2' type='radio' value="3">&nbsp;<fmt:message key="CANCEL_JM_1_YEARS"/>
				<br><input name='howLong2' type='radio' value="4">&nbsp;<fmt:message key="CANCEL_JM_2_YEARS"/>
				<br><input name='howLong2' type='radio' value="5">&nbsp;<fmt:message key="CANCEL_JM_NOT_SPECIFIED"/>
				<br><br>
				<div id="reason2_2Error" class='error'></div>
			</div>
			<br>
			<div id="reason2Error" class='error'></div>
			<br>
		</div>
		<div id="reason3_detail" style="display:none;margin-top:15px">
			<span style="font-size:16px;font-weight:bold">
			<fmt:message key="CANCEL_JM_REASON_STATE2"/>&nbsp;<fmt:message key="CANCEL_JM_REASON_3"/>
			</span>
			<div style="margin:15px 0px 15px;">
			<fmt:message key="CANCEL_JM_REASON_3_DETAIL"/>&nbsp;
			<c:choose>
				<c:when test="${service eq 'jobmatch'}">
					<fmt:message key="CANCEL_JM_REASON_1_DETAIL"/>
				</c:when>
				<c:when test="${service eq 'jobupdate'}">
					<fmt:message key="CANCEL_JU_REASON_1_DETAIL"/>
				</c:when>
			</c:choose>
			</div>
			<table width="100%" cellpadding="0" cellspacing="5" style="margin-top:15px">
				<tr>
					<td valign="top" width="24"><input name='howLong3' type='radio' value="1"></td>
					<td><fmt:message key="CANCEL_JM_3_MONTHS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong3' type='radio' value="2"></td>
					<td><fmt:message key="CANCEL_JM_6_MONTHS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong3' type='radio' value="3"></td>
					<td><fmt:message key="CANCEL_JM_1_YEARS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong3' type='radio' value="4"></td>
					<td><fmt:message key="CANCEL_JM_2_YEARS"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howLong3' type='radio' value="5"></td>
					<td><fmt:message key="CANCEL_JM_NOT_SPECIFIED"/></td>
				</tr>
			</table>
			<div id="reason3Error" class='error'></div>
			<br>
		</div>
		<div id="reason4_detail" style="display:none;margin-top:15px">
			<span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_REASON_STATE2"/>&nbsp;<fmt:message key="CANCEL_JM_REASON_4"/></span>
			<div style="margin:15px 0px 15px;">
				<fmt:message key="CANCEL_JM_REASON_4_DETAIL"/>
			</div>
			<table width="100%" cellpadding="0" cellspacing="5" style="margin-top:15px">
				<tr>
					<td valign="top" width="24"><input name='howOften' type='radio' value="1" onclick="checkConfirm(false)"></td>
					<td>
						<fmt:message key="CANCEL_JM_REASON_4_CHOICE_1"/>
						<div>
							<c:if test="${service eq 'jobupdate'}">
								<div id="juDayDiv" style="display:none;padding:0px;margin:8px 0px 0px">
									<select id="juDay" name="juDay">
										<option value="MON" ><fmt:message key="TARGETJOB_MONDAY"/></option>
										<option value="TUE" ><fmt:message key="TARGETJOB_TUESDAY"/></option>
										<option value="WED" ><fmt:message key="TARGETJOB_WEDNESDAY"/></option>
										<option value="THU" ><fmt:message key="TARGETJOB_THURSDAY"/></option>
										<option value="FRI" ><fmt:message key="TARGETJOB_FRIDAY"/></option>
										<option value="SAT" ><fmt:message key="TARGETJOB_SATURDAY"/></option>
										<option value="SUN" ><fmt:message key="TARGETJOB_SUNDAY"/></option>
									</select>
								</div>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howOften' type='radio' value="2" onclick="checkConfirm(false)"></td>
					<td><fmt:message key="CANCEL_JM_REASON_4_CHOICE_2"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howOften' type='radio' value="3" onclick="checkConfirm(false)"></td>
					<td><fmt:message key="CANCEL_JM_REASON_4_CHOICE_3"/></td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='howOften' type='radio' value="4" onclick="checkConfirm(true)"></td>
					<td><fmt:message key="CANCEL_JM_CONFIRM_CANCEL"/></td>
				</tr>
			</table>
			<div id="reason4_confirmCancel" style="display:none;width:90%;margin:auto;padding:10px;background-color:#FDFCEA;border:2px solid;border-radius:10px;">
				<div style="margin:0px 0px 15px">
				<c:choose>
					<c:when test="${service eq 'jobmatch'}">
						<fmt:message key="CANCEL_JM_REASON_1_DETAIL"/>
					</c:when>
					<c:when test="${service eq 'jobupdate'}">
						<fmt:message key="CANCEL_JU_REASON_1_DETAIL"/>
					</c:when>
				</c:choose>
				</div>
				<input name='howLong4' type='radio' value="1">&nbsp;<fmt:message key="CANCEL_JM_3_MONTHS"/>
				<br><input name='howLong4' type='radio' value="2">&nbsp;<fmt:message key="CANCEL_JM_6_MONTHS"/>
				<br><input name='howLong4' type='radio' value="3">&nbsp;<fmt:message key="CANCEL_JM_1_YEARS"/>
				<br><input name='howLong4' type='radio' value="4">&nbsp;<fmt:message key="CANCEL_JM_2_YEARS"/>
				<br><input name='howLong4' type='radio' value="5">&nbsp;<fmt:message key="CANCEL_JM_NOT_SPECIFIED"/>
				<br><br>
				<div id="reason4_2Error" class='error'></div>
			</div>
			<div id="reason4Error" class='error'></div>
			<br>
		</div>
		<div id="reason5_detail" style="display:none;margin-top:15px">
			<span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_REASON_STATE2"/>&nbsp;<fmt:message key="CANCEL_JM_REASON_5"/></span>
			<div style="margin:15px 0px 15px;">
			<fmt:message key="CANCEL_JM_REASON_5_DETAIL"/>
			</div>
			<table width="100%" cellpadding="0" cellspacing="5" style="margin-top:15px">
				<tr>
					<td valign="top" width="24"><input name='changeMail' id="changeMail" type='radio' value="1" onclick="checkConfirm(false)"></td>
					<td>
						<fmt:message key="CANCEL_JM_CHANGE_EMAIL"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="reason5_changeEmail" style="display:none;width:90%;margin:auto;margin-top:15px;padding:10px;background-color:#FDFCEA;border:2px solid;border-radius:10px;">
							<font style="color:red"><fmt:message key="CANCEL_JM_REASON_5_DETAIL_2"/></font>
							<br>
							<form id="changeMailForm" action="/CancelServ" method="post">
							<table border="0" width="100%" style="font-size:14px;margin:auto;margin-top:15px">
								<tr>
									<td align="left"><b><fmt:message key="CANCEL_JM_CURRENT_EMAIL"/></b></td>
								</tr>
								<tr>
									<td align="left"><%=username%></td>
								</tr>
								<tr>
									<td align="left"><b><font style="color:red">*</font><fmt:message key="CANCEL_JM_NEW_EMAIL"/></b></td>
								</tr>
								<tr>
									<td align="left"><input id="newUsername" name="newUsername" type="text" style="width:200"></td>
								</tr>
								<tr>
									<td align="left"><b><font style="color:red">*</font><fmt:message key="CANCEL_JM_NEW_EMAIL_CONFIRM"/></b></td>
								</tr>
								<tr>
									<td align="left"><input id="confirmUsername" name="confirmUsername" type="text" style="width:200"></td>
								</tr>
								<tr>
									<td align="left"><b><font style="color:red">*</font><fmt:message key="CANCEL_JM_NEW_EMAIL_PASSWORD"/></b></td>
								</tr>
								<tr>
									<td align="left"><input id="password" name="password" type="password"  style="width:200"></td>
								</tr>
							</table>
							<br>
								<div id="changeMailError" class='error'></div>
							</form>
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top" width="24"><input name='changeMail' type='radio' value="2" onclick="checkConfirm(true)"></td>
					<td>
						<fmt:message key="CANCEL_JM_CONFIRM_CANCEL"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="reason5_confirmCancel" style="display:none;width:90%;margin:auto;padding:10px;background-color:#FDFCEA;border:2px solid;border-radius:10px;">
							<fmt:message key="CANCEL_JM_REASON_1_DETAIL"/>
							<br><br>
							<input name='howLong5' type='radio' value="1">&nbsp;<fmt:message key="CANCEL_JM_3_MONTHS"/>
							<br><input name='howLong5' type='radio' value="2">&nbsp;<fmt:message key="CANCEL_JM_6_MONTHS"/>
							<br><input name='howLong5' type='radio' value="3">&nbsp;<fmt:message key="CANCEL_JM_1_YEARS"/>
							<br><input name='howLong5' type='radio' value="4">&nbsp;<fmt:message key="CANCEL_JM_2_YEARS"/>
							<br><input name='howLong5' type='radio' value="5">&nbsp;<fmt:message key="CANCEL_JM_NOT_SPECIFIED"/>
							<br><br>
							<div id="reason5_2Error" class='error'></div>
						</div>
					</td>
				</tr>
			</table>	
			<div id="reason5Error" class='error'></div>
			<br>
		</div>
		<div id="reason6_detail" style="display:none;margin-top:15px">
			<span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_REASON_STATE2"/>&nbsp;<fmt:message key="CANCEL_OTHERS"/></span>
			<br><br>
			<table border="0" width="100%">
				<tr>
					<td align="left" valign="top"><fmt:message key="CANCEL_JM_OTHER_SPECIFY"/></td>
				</tr>
				<tr>
					<td align="left"><textarea id="otherReason" maxlength='1000' rows="4" style="width:100%;resize:vertical"></textarea></td>
				</tr>
				<tr>
					<td align="left"><fmt:message key="CANCEL_JM_OTHER_TEL"/></td>
				</tr>
				<tr>
					<td align="left"><input type="text" id="tel" name="tel" maxlength="10" ></td>
				</tr>
				<tr>
					<td align="left"><fmt:message key="CANCEL_JM_OTHER_EMAIL"/></td>
				</tr>
				<tr>
					<td align="left"><input type="text" id="otherEmail" name="otherEmail" maxlength="30" ></td>
				</tr>
			</table>
			<br>
			<div id="reason6ErrorEmail" class='error'></div>
			<br>
			<div id="reason6_confirmCancel" style="display:block;width:90%;margin:auto;padding:10px;background-color:#FDFCEA;border:2px solid;border-radius:10px;">
				<c:choose>
					<c:when test="${service eq 'jobmatch'}">
						<fmt:message key="CANCEL_JM_REASON_1_DETAIL"/>
					</c:when>
					<c:when test="${service eq 'jobupdate'}">
						<fmt:message key="CANCEL_JU_REASON_1_DETAIL"/>
					</c:when>
				</c:choose>
				<br><br>
				<input name='howLong6' type='radio' value="1">&nbsp;<fmt:message key="CANCEL_JM_3_MONTHS"/>
				<br><input name='howLong6' type='radio' value="2">&nbsp;<fmt:message key="CANCEL_JM_6_MONTHS"/>
				<br><input name='howLong6' type='radio' value="3">&nbsp;<fmt:message key="CANCEL_JM_1_YEARS"/>
				<br><input name='howLong6' type='radio' value="4">&nbsp;<fmt:message key="CANCEL_JM_2_YEARS"/>
				<br><input name='howLong6' type='radio' value="5">&nbsp;<fmt:message key="CANCEL_JM_NOT_SPECIFIED"/>
				<br><br>
				<div id="reason6Error" class='error'></div>
			</div>
			<br>
		</div>
		<div id="detailReason"></div>
		<div style="text-align:center">
			<a id="cancel" href="javascript:void(0)" class="next" style="display:none" ><fmt:message key="CANCEL_BACK"/></a>&nbsp;&nbsp;<a id="next" href="javascript:void(0)" class="next"><fmt:message key="CANCEL_NEXT"/></a>
		</div>
	</div>
</div>