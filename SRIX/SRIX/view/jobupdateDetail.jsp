<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.services.*"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="java.util.List"%>
<%
	//edit how to list page and data by ton
	int idJsk=Util.StrToInt(""+session.getAttribute("SESSION_ID_JSK"));
	int pageSize = 20;
	if(idJsk<=0){
		String encode = Util.getStr(request.getParameter("encode"));
		String key = Util.getStr(request.getParameter("key"));
		Encryption.getDecoding(encode, key);
		idJsk = Encryption.idJsk;
		if(idJsk<=0){
			response.sendRedirect("/LogoutServ");
			return;
		}
	}	
	int idResume=JobManager.getIdResumeComplete(idJsk);
	List<Job> allJob = JobManager.getJobUpdateJob(idJsk,idResume);
	int jobSize=allJob.size();
    
    int pageJob = Util.StrToInt(request.getParameter("pageJob")!=null?request.getParameter("pageJob"):"1");
    int countPage = (jobSize/20)+1;
    
	int beginJob = ((pageJob-1)*pageSize);
	int endJob = pageJob==countPage?(jobSize-1):(pageJob*pageSize)-1;

	Jobseeker jsk = new JobseekerManager().get(idJsk);
	request.setAttribute("idJsk",idJsk);
	request.setAttribute("jsk",jsk);
	request.setAttribute("allJob",allJob);
	request.setAttribute("jobSize",jobSize);
	request.setAttribute("beginJob",beginJob);
	request.setAttribute("endJob",endJob);
	request.setAttribute("pageJob",pageJob);
	request.setAttribute("idResume",idResume);
%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<table style="border-collapse:collapse; width:100%;">
	<tr>
		<td class="content" align="center">
			<table align="center" style="border-collapse:collapse;" border="0" >
				<tr style="height:6px;"><td></td></tr>
				<tr><td align="center"><img src="/images/ju_logo.png" border="0"/></td></tr>
				<tr>
					<td align="left">
						<div style="margin:10px 0px 10px;padding:5px;background-color:#EEEEEE">
							<b>Job Update</b> : <b><c:out value='${beginJob+1}' /> - <c:out value='${endJob+1}' /></b> Form <b><c:out value='${jobSize}' /></b> jobs
						</div>
						<c:if test="${jobSize>0}">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<c:forEach var="job" items="${allJob}" begin="${beginJob}" end="${endJob}" varStatus="idx" >    
            						<c:set var="iEmpJob" scope="request"><c:out value='${job.idEmp}'/></c:set>
										<% int jobMod = 0 ; String section="";%>
										<c:choose>
											<c:when test="${job.jobSection eq '1'}">
												 <% if ( jobMod%2 == 0){out.print("<tr valign=\"middle\" bgcolor=\"#E3DABE\">");} else {out.print("<tr valign=\"middle\" bgcolor=\"#eee9d8\">");}%>
							                     <% section ="<a href=\"http://www.jobtopgun.com/profile/searchprofile?id_emp="+(String)request.getAttribute("iEmpJob")+"\"> "+
												 			 " View </a>"; %> 
							                </c:when>
							                <c:when test="${job.jobSection eq '2'}">
												 <% if ( jobMod%2 == 0){out.print("<tr valign=\"middle\" bgcolor=\"#ffffff\">");} else {out.print("<tr valign=\"middle\" bgcolor=\"#ffffff\">");}%>
							                     <% section="-";%>
							                </c:when>
							                <c:when test="${job.jobSection eq '3'}">
												 <% if ( jobMod%2 == 0){out.print("<tr valign=\"middle\" bgcolor=\"#E3DABE\">");} else {out.print("<tr valign=\"middle\" bgcolor=\"#eee9d8\">");}%>
							                     <% section="SME";%>
							                </c:when>
							               	<c:otherwise>
												<% if ( jobMod%2 == 0){out.print("<tr valign=\"middle\" bgcolor=\"#ffffff\">");} else {out.print("<tr valign=\"middle\" bgcolor=\"#ffffff\">");}%>
							                    <% section="-";%>
							                </c:otherwise> 
										</c:choose>
											<td valign="top" width="120" style="padding:3px 3px 0px 3px">
												<a href="http://www.jobtopgun.com/search/searchjobbycompany?id_emp=<c:out value='${job.idEmp}'/>"><img class="logo" src="http://www.jobtopgun.com/content/filejobtopgun/logo/lg<c:out value='${job.idEmp}'/>.gif" border="0" width="116px" height="41px"></a>
											</td>
											<td valign="top" style="padding:3px 0px 3px">
												<span style="font-size:14px"><a href="http://www.jobtopgun.com/search/popup/jobpost.jsp?idEmp=<c:out value='${job.idEmp}'/>&idPosition=<c:out value='${job.idPosition}'/>" target="_blank"><c:out value="${job.positionName}" escapeXml="false"/></a></span>
												<div style="margin-top:5px;font-size:12px"><b>Exp:</b>
													<c:set var="eless" scope="request"> <c:out value='${job.expLess}'/></c:set> 
                        							<c:set var="emost" scope="request"> <c:out value='${job.expMost}'/></c:set> 
                        							<% 
                        								String expJob = JobManager.getEXP((String)request.getAttribute("eless"),(String)request.getAttribute("emost"));
                        								out.print(expJob);
                        							%> 
												</div>
											</td>
											<td width="14" align="left">
												<a href="http://www.jobtopgun.com/search/popup/jobpost.jsp?idEmp=<c:out value='${job.idEmp}'/>&idPosition=<c:out value='${job.idPosition}'/>" target="_blank"><img src="/images/arrow_right.png" width="9" height="20" border="0"/></a>
											</td>
										<tr style="height:2px;background-color:#000000"><td colspan="3"></td></tr>
								</c:forEach>
										<tr>
								        	<td align="right" colspan="2">
	                  						<% 
	                  							String pageResult="",pageTemp="";
	                  							for(int i=1;i<=countPage;i++){
	                  								if(pageJob==(i)){
		                  								pageTemp = "<b style=\"font-size:15px\">"+(i)+"</b>";
		                  							}else{
		                  								pageTemp = "<a href=\"/SRIX?view=jobupdateDetail&pageJob="+(i)+"&encode="+Encryption.getEncoding(0,0,idJsk,0)+"&key="+Encryption.getKey(0,0,idJsk,0)+"\">"+i+"</a>" ;
		                  							}
		                  							
		                  							if(!pageResult.equals("")){
		                  								pageResult += " , ";
		                  							}
		                  							pageResult+=pageTemp;
	                  							}
	                  							
		                  						out.print("<b>Page : </b>"+pageResult);
	                  						%>
		                  					</td>
		                  				</tr>
							</table>
							<table cellSpacing="0" cellPadding="0" border="0" width="100%" style="margin:15px 0px 10px">
								<tr>
									<td style="font-size:18px;background-color:#eeeeee" colspan="3"><b>Your Job Update Criteria</b></td>
								</tr>
								<tr style="height:6px;"><td></td></tr>
								<tr>
									<td align="right" valign="top"><b>Jobtype</b></td>
									<td valign="top"><b>&nbsp;:&nbsp;</b></td>
									<td> <% out.print(JobupdateManager.getJtNameJobupdate(idJsk,idResume));%>	</td>
								</tr>
								<tr>
									<td align="right"  valign="top"><b>Jobfield</b></td>
									<td valign="top"><b>&nbsp;:&nbsp;</b></td>
									<td> <%out.print(JobupdateManager.getJfSfNameJobupdate(idJsk,idResume)); %></td>
								</tr>
								<tr>
									<td align="right"  valign="top"><b>Industry</b></td>
									<td valign="top"><b>&nbsp;:&nbsp;</b></td>
									<td> <%out.print(JobupdateManager.getIndustryNameJobupdate(idJsk,idResume)); %></td>
								</tr>
								<tr>
									<td align="right" valign="top"><b>Location</b></td>
									<td valign="top"><b>&nbsp;:&nbsp;</b></td>
									<td> <%out.print(JobupdateManager.getLocationNameJobupdate(idJsk,idResume)); %></td>
								</tr>
								<tr>
									<td align="right" valign="top"><b>Salary</b></td>
									<td valign="top"><b>&nbsp;:&nbsp;</b></td>
									<td> <%out.print(JobupdateManager.getSalaryNameJobupdate(idJsk,idResume)); %></td>
								</tr>
				                <c:if test="${jsk.jobUpdateStatus eq 'NO'}">  
				                <tr>
									<td align="right" valign="top"><b>Jobupdate Status</b></td>
									<td valign="top"><b>&nbsp;:&nbsp;</b></td>
				                    <td align="left">&nbsp;No</td>
				                </tr>
				                </c:if>
				                <tr style="height:6px;"><td></td></tr>
		                        <c:if test="${jsk.jobUpdateStatus ne 'NO'}">  
		                          	<tr>
										<td align="right" valign="top"><b>Jobupdate Status</b></td>
										<td valign="top"><b>&nbsp;:&nbsp;</b></td>
			                            <td> 
			                            	<div style="margin:0px 0px 5px">Yes</div>
											<form name="juStatusForm" action="JobUpdateServlet" >	  
											<select name="JOBUPDATEDAY">		
												<option value="ALL" <c:if test="${jsk.jobUpdateStatus eq 'ALL'}"><c:out value="selected" /></c:if>>Everyday</option>
												<option value="MON" <c:if test="${jsk.jobUpdateStatus eq 'MON'}"><c:out value="selected" /></c:if>>Every Monday</option>
												<option value="TUE" <c:if test="${jsk.jobUpdateStatus eq 'TUE'}"><c:out value="selected" /></c:if>>Every Tuesday</option>
												<option value="WED" <c:if test="${jsk.jobUpdateStatus eq 'WED'}"><c:out value="selected" /></c:if>>Every Wednesday</option>
												<option value="THU" <c:if test="${jsk.jobUpdateStatus eq 'THU'}"><c:out value="selected" /></c:if>>Every Thursday</option>
												<option value="FRI" <c:if test="${jsk.jobUpdateStatus eq 'FRI'}"><c:out value="selected" /></c:if>>Every Friday</option>
												<option value="SAT" <c:if test="${jsk.jobUpdateStatus eq 'SAT'}"><c:out value="selected" /></c:if>>Every Saturday</option>
												<option value="SUN" <c:if test="${jsk.jobUpdateStatus eq 'SUN'}"><c:out value="selected" /></c:if>>Every Sunday</option>
											</select>  
											<input type="submit" name="submitJuStatus" value="Save">
											<input type="hidden" name="updateStatus" value="1">
				                         	</form>
		                            	</td>
									</tr>   
		                        </c:if>
		                        <tr style="height:6px;"><td></td></tr>
	                         	<tr>
	                         		<td  align="right" style="color:red" colspan="3">
	                           			<a href="SRIX?view=cancelService&locale=<%=request.getSession().getAttribute("SESSION_LOCALE")%>&cancelService=jobupdate&encode=<%=Encryption.getEncoding(0,0,idJsk,idResume)%>&key=<%=Encryption.getKey(0,0,idJsk,idResume)%>" target="_blank"> Receive / Stop receiving e-mail</a>
	                            	</td>
	                            </tr>
	                            <tr style="height:6px;"><td></td></tr>
								<tr>
									<td  align="right" style="color:red;font-size:12px" colspan="3">
										If you want to change your jobupdate criteria,please <b><a href="SRIX?view=targetJob&idResume=<c:out value='${idResume}'/>&sequence=0">click here</a></b>
									</td>
               					</tr>
               				</table>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
		<td class="faq">
		</td>
	</tr>
</table>
