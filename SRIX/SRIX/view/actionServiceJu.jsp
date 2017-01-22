<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.resume.CancelService"%>
<%@page import="com.topgun.resume.CancelServiceManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%
	int idJsk=Util.StrToInt(""+session.getAttribute("SESSION_ID_JSK"));
	String locale = Util.getStr(session.getAttribute("SESSION_LOCALE"));
	String service = "jobupdate";
	int idLanguage = 11;
	int chkCase = 0;
	int howOften = 0;
	String juDay = "";
	Jobseeker jsk=new JobseekerManager().get(idJsk);
	CancelService cs = null;
	if(jsk.getJobUpdateStatus().equals("NO"))
	{
		cs = CancelServiceManager.getCancelByIdJsk(idJsk,"jobupdate");
	}
	if(cs!=null)
	{
		howOften = Util.getInt(cs.getHowOften());
		if(cs.getHowLong()>0)
		{
			chkCase = 2 ;
		}else if(cs.getHowLong()==0)
		{
			if(cs.getReasonType()==2){
				chkCase = 3;
			}else if(cs.getReasonType()==4){
				chkCase = 4;
				juDay = new JobseekerManager().get(idJsk).getJobUpdateStatus();
			}
		}
	}
	else
	{
		chkCase = 1 ;
	}
	boolean jskExist=new JobseekerManager().isExist(idJsk);
	if(!service.equals(service))
	{
		response.sendRedirect("ActionService.jsp");
	}
	else if(!jskExist)
	{
		response.sendRedirect("/LogoutServ");
		return;
	}
	if(locale.equals("th_TH")){
		idLanguage = 38;
	}
	request.setAttribute("service",service);
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
<script>
$(document).ready(function(){
	var chkCase = <%=chkCase%>;
	var service = "<%=service%>";
	if(chkCase==1){
		$("#case1").show();
	}else if(chkCase==2){
		$("#case2").show();
	}else if(chkCase==3){
		$("#case3").show();
	}else if(chkCase==4){
		$("#case4").show();
	}
	if(chkCase==4){
		$("#case4_next").click(function(){
			if($("[name='frequency']:checked").size()==0){
				alert("Please choose frequency for receive Job Match Email");
			}
			else{
				$("#form_case4").submit();
			}
		});
	}
	
       $("#logout").click(function(){
       	if(confirm('<fmt:message key="CANCEL_LOGOUT"/>')){
      			window.location.href = "/LogoutServ";
      		}
       });
       
       $("input[name^='frequency']").click(function (){
       	if($(this).val()==1){
       		$("#juDayDiv_2").slideDown(500);
       	}
       	else{
       		$("#juDayDiv_2").slideUp(500);
       	}
       });
       
       $("#receiveUrl").click(function (){
       	$("#receiveUrl").slideUp(500);
      		$("#juDayDiv_1").slideDown(500);
       });
       
       $("#submitReceive").click(function(){
       		//alert($("#juDayReceive").val());
       		//alert(service);
       		//alert("/CancelServlet?type=receive&juDay="+$("#juDayReceive").val()+"&service="+service);
       		window.location.href = "/CancelServlet?type=receive&juDay="+$("#juDayReceive").val()+"&cancelService="+service;
       });
});

</script>
<table style="border-collapse:collapse; width:100%;">
	<tr>
		<td align="center">
			<table align="center" style="border-collapse:collapse;" border="0" >
				<tr style="height:6px;"><td></td></tr>
				<tr valign="top">
					<td colspan="2">
						<div class="contentCancel">
							<div align="right" style="font-size:14px"><a id="logout" href="javascript:void(0)"><fmt:message key="SECTION_LOGOUT"/></a></div>
							<div id="contentForm" align="center" >
								<div id="case1" style="display:none;">
									<table>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td valign="top"><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JU_STATUS"/>&nbsp;:&nbsp;&nbsp;&nbsp;</span></td>
											<td><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JU_STATUS_1"/></span></td>
										</tr>
										<tr style="height:10px;"><td></td></tr>
									</table>
								</div>
								<div id="case2" style="display:none;">
									<table>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td valign="top"><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JU_STATUS"/>&nbsp;:&nbsp;&nbsp;&nbsp;</span></td>
											<td><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JU_STATUS_2"/></span></td>
										</tr>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td colspan="2" align="center"><a id="receiveUrl" href="javascript:void(0)"><fmt:message key="CANCEL_JU_STATUS_CONFIRM"/></a>
												<div id="juDayDiv_1" style="display:none;padding:0px;margin:0px">
													<table>
														<tr>
															<td valign="middle">
																<select id="juDayReceive" name="juDayReceive" style="vertical-align: middle;">
																	<option value="ALL" ><fmt:message key="TARGETJOB_EVERYDAY"/></option>
																	<option value="MON" ><fmt:message key="TARGETJOB_MONDAY"/></option>
																	<option value="TUE" ><fmt:message key="TARGETJOB_TUESDAY"/></option>
																	<option value="WED" ><fmt:message key="TARGETJOB_WEDNESDAY"/></option>
																	<option value="THU" ><fmt:message key="TARGETJOB_THURSDAY"/></option>
																	<option value="FRI" ><fmt:message key="TARGETJOB_FRIDAY"/></option>
																	<option value="SAT" ><fmt:message key="TARGETJOB_SATURDAY"/></option>
																	<option value="SUN" ><fmt:message key="TARGETJOB_SUNDAY"/></option>
																</select>
																&nbsp;&nbsp;
															</td>
															<td valign="middle" style="vertical-align: middle">
																<span><a id="submitReceive" href="javascript:void(0)" class="next"><fmt:message key="CANCEL_JM_SUBMIT"/></a></span>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</div>
								<div id="case4" style="display:none;">
								<form id="form_case4" name="form_case4" id="form_case4" method="get" action="/CancelServlet">
								<input type="hidden" name="cancelService" value="<c:out value="${service}"/>"/>
								<input type="hidden" name="type" value="change"/>
									<table>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td valign="top"><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JU_STATUS"/>&nbsp;:&nbsp;&nbsp;&nbsp;</span></td>
											<td><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JU_STATUS_4"/></span></td>
										</tr>
										<tr>
											<td colspan="2">
												<table cellspacing="5" style="margin:0px 0px 15px">
													<tr>
														<td valign="top"><input type="radio" name="frequency" value="1" <%=howOften==1?"checked":""%>></td>
														<td>
															<fmt:message key="CANCEL_JM_REASON_4_CHOICE_1"/>
															<div>
																<div id="juDayDiv_2" style="display:<%=howOften==1?"block":"none"%>;padding:0px;margin:0px;">
																	<select id="juDay" name="juDay" style="width:150px;margin:8px 0px 0px">
																		<option value="MON" <%=juDay.equals("MON")?"selected":""%>><fmt:message key="TARGETJOB_MONDAY"/></option>
																		<option value="TUE" <%=juDay.equals("TUE")?"selected":""%>><fmt:message key="TARGETJOB_TUESDAY"/></option>
																		<option value="WED" <%=juDay.equals("WED")?"selected":""%>><fmt:message key="TARGETJOB_WEDNESDAY"/></option>
																		<option value="THU" <%=juDay.equals("THU")?"selected":""%>><fmt:message key="TARGETJOB_THURSDAY"/></option>
																		<option value="FRI" <%=juDay.equals("FRI")?"selected":""%>><fmt:message key="TARGETJOB_FRIDAY"/></option>
																		<option value="SAT" <%=juDay.equals("SAT")?"selected":""%>><fmt:message key="TARGETJOB_SATURDAY"/></option>
																		<option value="SUN" <%=juDay.equals("SUN")?"selected":""%>><fmt:message key="TARGETJOB_SUNDAY"/></option>
																	</select>
																</div>
															</div>
														</td>
													</tr>
													<tr>
														<td valign="top"><input type="radio" name="frequency" value="2" <%=howOften==2?"checked":""%>></td>
														<td><fmt:message key="CANCEL_JM_REASON_4_CHOICE_2"/></td>
													</tr>
													<tr>
														<td valign="top"><input type="radio" name="frequency" value="3" <%=howOften==3?"checked":""%>></td>
														<td><fmt:message key="CANCEL_JM_REASON_4_CHOICE_3"/></td>
													</tr>
													<tr>
														<td valign="top"><input type="radio" name="frequency" value="4" ></td>
														<td><fmt:message key="CANCEL_JU_STATUS_CONFIRM_STANDARD"/></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td colspan = "2" align="center"><a id="case4_next" href="javascript:void(0)" class="next"><fmt:message key="CANCEL_JM_SUBMIT"/></a></td>
										</tr>
										<tr style="height:10px;"><td></td></tr>
									</table>
								</form>
								</div>
								<div id="cancel" style="margin:15px 0px 0px">
									<table>
										<tr>
											<td><a href="/SRIX?view=cancelService&cancelService=<c:out value="${service}"/>&valid=1"><fmt:message key="CANCEL_JU_STATUS_CANCEL"/></a></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		<br><br><br>
		</td>
		<td class="faq">
		</td>
	</tr>
</table>