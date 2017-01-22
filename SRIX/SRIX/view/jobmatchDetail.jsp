<%@page import="com.topgun.supermatch.Position"%>
<%@page import="com.topgun.supermatch.SuperMatch"%>
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
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<%
	//edit how to list page and data by ton
	int idJsk=Util.getInt(""+session.getAttribute("SESSION_ID_JSK"),Util.getInt(request.getParameter("idJsk")));
	int idLanguage=Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int pageSize = 20;
	if(idJsk<=0)
	{
		String encode = Util.getStr(request.getParameter("encode"));
		String key = Util.getStr(request.getParameter("key"));
		Encryption.getDecoding(encode, key);
		idJsk = Encryption.idJsk;
		if(idJsk<=0){
			response.sendRedirect("/LogoutServ");
			return;
		}
	}
	//System.out.println("ok 1");
	int idResume = Util.getInt(request.getParameter("idResume"),0);	
	SuperMatch superMatch=new SuperMatch(idJsk,idResume);
	List<Position> allJob=superMatch.getSuperMatch();
	int jobSize=allJob.size();
    //System.out.println("ok 1"+jobSize);
    int pageJob = Util.StrToInt(request.getParameter("pageJob")!=null?request.getParameter("pageJob"):"1");
    int countPage = (jobSize/20)+1;
    
	int beginJob = ((pageJob-1)*pageSize);
	int endJob = pageJob==countPage?(jobSize-1):(pageJob*pageSize)-1;
	
	Resume resume = new ResumeManager().get(idJsk,idResume);
	int expYear = resume.getExpYear();
	Jobseeker jsk = new JobseekerManager().get(idJsk);
	String jmStatus =  jsk.getJobMatchStatus();
	
	request.setAttribute("expYear",""+expYear);
	request.setAttribute("jmStatus",jmStatus);
	request.setAttribute("idJsk",idJsk);
	request.setAttribute("jobLists",allJob);
	request.setAttribute("jobSize",jobSize);
	request.setAttribute("beginJob",beginJob);
	request.setAttribute("endJob",endJob);
	request.setAttribute("pageJob",pageJob);
	request.setAttribute("idResume",idResume);
%>
<table width="100%" style="border-collapse:collapse;" border="0">
	<tr>
		<td>
			<table align="center" style="border-collapse:collapse;" border="0" >
				<tr style="height:6px;"><td></td></tr>
				<tr><td align="center"><img src="/images/jm_logo.png" border="0"/></td></tr>
				<tr>
					<td align="left">
						<div style="margin:10px 0px 10px;padding:5px;background-color:#EEEEEE">
							<b>Super Match</b> : <b><c:out value='${beginJob+1}' /> - <c:out value='${endJob+1}' /></b> Form <b><c:out value='${jobSize}' /></b> jobs
						</div>
						<c:forEach items="${jobLists}" var="job" varStatus="idx">
							<div style='margin:auto; background-color:"+color+"'>
						 	<table border='0' width='100%'   cellpadding="2">  
						 	<tr> 
								<td valign='top' class='subCol' style='width:118px;'  rowspan='2' >
						 			<img valign='top' src='http://mail.jobinthailand.com/logo/lg<c:out value="${job.idEmp }"/>.gif' style='padding-left:5px;display: block;vertical-align: text-top; border:1px;width:116px;background-color:#fff;'/> 
								</td> 
								<td valign='top' class='mainCol color1d2354 font18'colspan='2' style='color:#1d2354;'> 
									<c:out value="${idx.index+1}"/>.&nbsp;&nbsp;<a href='http://www.jobtopgun.com/search/jobpost.jsp?idEmp=<c:out value="${job.idEmp }"/>&idPosition=<c:out value="${job.idPosition}"/>' class='contents color1d2354 font20' style='color:#1d2354;'><c:out value="${job.positionName}" escapeXml="false"/></a>
								</td> 
							</tr> 
							<tr>
							<td valign='top'>
								<table cellpadding="2">
									<tr valign='top'>
										<td class='mainCol color0f4a90 font18 unLine'  style='color:#0f4a90;'>
											<span style='padding-left:15px'>&nbsp;</span><a href='http://www.jobtopgun.com/search/hotcompanyList.jsp?degree=BACHELOR&idEmp=<c:out value="${job.idEmp}"/>' class='contents color0f4a90 font20' style='color:#0f4a90;'><c:out value="${job.nameTha}"/></a> 
										</td> 
									</tr> 	
									<tr valign='top'>
						 				<td class='mainCol color696969 font16'  style='color:#696969;'>  
											<span style='padding-left:15px'>&nbsp;</span><c:out value="${job.expLess}"/>-<c:out value="${job.expMost }"/>
										</td>  
									</tr> 	
									<tr valign='top'>
						  				<td class='mainCol color696969 font16'  style='color:#696969;'>  
											<span style='padding-left:15px'>&nbsp;</span><c:out value="${job.locationTha}"/>
										</td> 
									</tr> 	
								</table>
							</td>
							</tr>
							<tr>
								<td align='center' colspan="2">
									<a href='http://register.superresume.com/SRIX?view=apply&idEmp=<c:out value="${job.idEmp }"/>&idPosition=<c:out value="${job.idPosition}"/>'><h3><img class="logo" src="http://mail.jobinthailand.com/content/superMatch/images/TH/btn_apply.png" border="0"></a> 
								</td>
							</tr>
						</table> 
						</div>
						<br/>
						</c:forEach>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>