<%@page import="com.topgun.resume.Jobseeker"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.resume.CancelService"%>
<%@page import="com.topgun.resume.CancelServiceManager"%>
<%
	int idJsk=Util.StrToInt(""+session.getAttribute("SESSION_ID_JSK"));
	String locale = Util.getStr(session.getAttribute("SESSION_LOCALE"));
	String service = "jobmatch";
	int idLanguage = 11;
	int chkCase = 0;
	int howOften = 0;
	
	Jobseeker jsk=new JobseekerManager().get(idJsk);
	CancelService cs = null;
	if(jsk.getJobMatchStatus().equals("FALSE"))
	{
		cs = CancelServiceManager.getCancelByIdJsk(idJsk,service);
		if(cs==null)
		{
			cs = CancelServiceManager.getCancelByIdJsk(idJsk,"jobmatch");
		}
	}
	
	if(cs!=null)
	{
		howOften = Util.getInt(cs.getHowOften());
		if(cs.getHowLong()>0){
			chkCase = 2 ;
		}else if(cs.getHowLong()==0){
			if(cs.getReasonType()==2){
				chkCase = 3;
			}else if(cs.getReasonType()==4){
				chkCase = 4;
			}
		}
		
		boolean jskExist=new JobseekerManager().isExist(idJsk);
		if(!cs.getService().equals(service)){
			response.sendRedirect("ActionServiceJu.jsp");
		}
		else if(!jskExist){
			response.sendRedirect("/LogoutServ");
			return;
		}
	}else{
		chkCase = 1 ;
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
});

</script>
<table style="border-collapse:collapse; width:100%;">
	<tr>
		<td class="content" align="center">
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
											<td valign="top"><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS"/>&nbsp;:&nbsp;&nbsp;&nbsp;</span></td>
											<td><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS_1"/></span></td>
										</tr>
										<tr style="height:10px;"><td></td></tr>
									</table>
								</div>
								<div id="case2" style="display:none;">
									<table>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td valign="top"><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS"/>&nbsp;:&nbsp;&nbsp;&nbsp;</span></td>
											<td><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS_2"/></span></td>
										</tr>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td colspan="2" align="center"><a href="/CancelServlet?type=receive&cancelService=<c:out value="${service}"/>"><fmt:message key="CANCEL_JM_STATUS_CONFIRM"/> </a></td>
										</tr>
									</table>
								</div>
								<div id="case3" style="display:none;">
									<table>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td valign="top"><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS"/>&nbsp;:&nbsp;&nbsp;&nbsp;</span></td>
											<td><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS_3"/></span></td>
										</tr>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td colspan="2" align="center"><a href="/CancelServlet?type=receive&cancelService=<c:out value="${service}"/>"><fmt:message key="CANCEL_JM_STATUS_CONFIRM_STANDARD"/></a></td>
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
											<td valign="top"><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS"/>&nbsp;:&nbsp;&nbsp;&nbsp;</span></td>
											<td><span style="font-size:16px;font-weight:bold"><fmt:message key="CANCEL_JM_STATUS_4"/></span></td>
										</tr>
										<tr style="height:10px;"><td></td></tr>
										<tr>
											<td colspan="2">
												<table cellspacing="5" style="margin:0px 0px 15px">
													<tr>
														<td valign="top"><input type="radio" name="frequency" value="1" <%=howOften==1?"checked":""%>></td>
														<td><fmt:message key="CANCEL_JM_REASON_4_CHOICE_1"/></td>
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
														<td><fmt:message key="CANCEL_JM_STATUS_CONFIRM_STANDARD"/></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td colspan = "2" align="center"><a id="case4_next" href="javascript:void(0)" class="next"><fmt:message key="CANCEL_JM_SUBMIT"/></a></td>
										</tr>
									</table>
								</form>
								</div>
								<div id="cancel" style="margin:15px 0px 0px">
									<table>
										<tr>
											<td><a href="/SRIX?view=cancelService&cancelService=<c:out value="${service}"/>&valid=1"><fmt:message key="CANCEL_JM_STATUS_CANCEL"/></a></td>
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