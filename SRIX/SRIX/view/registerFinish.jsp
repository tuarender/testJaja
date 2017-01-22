<%@page import="monfox.toolkit.snmp.agent.modules.SnmpV2Mib.SysOREntry"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>
<style>
	li{
		line-height: 2.1;
	}
	a:hover
{
	text-decoration:none;
	color:#ffffff;
}
</style>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
	<%
		Resume resume=(Resume)request.getAttribute("resume");
		String Enc=Encryption.getEncoding(0,0,resume.getIdJsk(),resume.getIdResume());
		String idSession=Encoder.getEncode(session.getId());
		String Key=Encryption.getKey(0,0,resume.getIdJsk(),resume.getIdResume());
		int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
		int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
		int idEmp = Util.getInt(request.getAttribute("idEmp"));
		int idPosition = Util.getInt(request.getParameter("idPosition"));  
		
		int superMatch=Util.getInt(session.getAttribute("SESSION_SUPERMATCH"),0);
		
		Cookie[] cookies = null;
	   	cookies = request.getCookies();
	   	String companyName="";
		String positionName="";
	   	if(cookies!=null)
	   	{
	   		for (int i = 0; i < cookies.length; i++)
			{
				Cookie cookie = cookies[i];
				if(cookies[i].getName().equals("COOKIE_ID_EMP"))
				{
					idEmp=Util.getInt(cookies[i].getValue());
				} 
				else if(cookies[i].getName().equals("COOKIE_ID_POSITION"))
				{
					idPosition=Util.getInt(cookies[i].getValue());
				}
			}
	   	}
	   
	   	if(idEmp>0 && idPosition>0)
	   	{
	   		Employer emp=new EmployerManager().get(idEmp);
		if(emp!=null)
		{
			companyName=emp.getNameEng();
			if(idLanguage==38)
			{
				companyName=emp.getNameThai();
			}
			Position pos=new PositionManager().getPosition(emp.getId(), idPosition);
			if(pos!=null)
			{
				positionName=pos.getPositionName();
			}
		}
		if(companyName!="" && positionName!="")
		{
			request.setAttribute("companyName",companyName);
			request.setAttribute("positionName",positionName);
		}
	   	}
		request.getSession().setAttribute("SESSION_LOCALE",resume.getLocale());
		request.setAttribute("idEmp",idEmp);
		request.setAttribute("idPosition",idPosition);
		request.setAttribute("superMatch",superMatch);
		request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idSession", idSession); 
		request.setAttribute("Enc",Enc);
		request.setAttribute("Key",Key);
		
		
		
		
	%>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=381512415208715";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
<br>
<!-- <c:import url="register_suggestion.jsp" charEncoding="UTF-8"/> -->
<form id="loginFrm" action="LogonServ" name="loginFrm">
	<input type="hidden" name="idEmp" value="<c:out value="${param.idEmp}"/>">
	<input type="hidden" name="idPosition" value="<c:out value="${param.idPosition}"/>">
<div class="seperator"></div>
<div>
 <c:choose>
	<c:when test="${superMatch == 0 and idEmp == -1 && idPosition == -1}">
		<!-- <div align="center">
			<p class="font_16 color8081be"><fmt:message key="GLOBAL_RESUME_ARE_READY"/></p>
		</div> -->
	</c:when>
	<c:when test="${idEmp  > 0 && idPosition > 0}">
		<div class="normal" align="center">
		 <p align="center" class="font_16 color8081be" ><fmt:message key="GLOBAL_RESUME_READY"/></p>
				<b class="font_16" style="color:#8081be;"><fmt:message key="ACTIVITY_POSITION"/></b>&nbsp;<label class="answer"><c:out value="${positionName}" escapeXml="false"/></label><br>
				<b class="font_16" style="color:#8081be;"><fmt:message key="FAMILY_WORK_LAST_COMPANY"/></b>&nbsp;<label class="answer"><c:out value="${companyName}"/></label>
		</div>
		<br>
		<div align="center" class="font_16 colorb37bb6" ><fmt:message key="GLOBAL_APPLY_NOW"/></div>
		<br>
		<div align="center">
			<a href="/SRIX?view=apply&idEmp=<c:out value="${idEmp}"/>&idPosition=<c:out value="${idPosition}"/>&jSession=<c:out value="${idSession}"/>'"><button type="button" class="btn" style="background-color:#4abfa7;"><b style="color:white;"><fmt:message key="GLOBAL_BACK_TO_RESUME"/></b></button></a>
		</div>
	</c:when>
	
 <c:otherwise>
	<c:if test="${superMatch>0}">
		<div align="center"  class="font_16 color8081be"><fmt:message key="GLOBAL_SUPERMATCH"/></div>
		<div style="height:10px"></div>
		<div align="center">
			<a href="http://www.jobtopgun.com?view=superMatch&jSession=<c:out value="${idSession}"/>&idJsk=<c:out value="${idJsk}"/>"><button type="button" class="btn" style="background-color:#4abfa7"><b style="color:white;"><fmt:message key="GLOBAL_SUPERMATCH_BTN"/></b></button></a>
			<div style="height:10px"></div>
			<div><fmt:message key="GLOBAL_SUPERMATCH_BL"/></div>
		</div>
	</c:if>
</c:otherwise>
</c:choose>
</div> 
<div style="height:10px;"></div>
<div>
	<!-- <p><fmt:message key="GLOBAL_RESUME_MMS"/><a href="/SRIX?view=naming&cmd=CREATE&sequence=1&jSession=<c:out value="${idSession}"/>"><u><fmt:message key="CLICK_HERE"/></u></a> </p>
	<a href="" data-toggle="modal" data-target="#myModal"><u><fmt:message key="REGISTER_UPGRADE"/></u></a> -->
	<p style="color: #4ABEA6;"><fmt:message key="REGISTER_FINISH_CONTENT1"/></p>
	
	<ul style="padding-left: 0px;">
	  <li><fmt:message key="REGISTER_FINISH_CONTENT2"/></li>
	  <a href="/view/viewResume.jsp?idResume=0&jSession=<c:out value="${idSession}"/>" target="_blank" class="button_link">ตรวจดู</a>
	  <li><fmt:message key="REGISTER_FINISH_CONTENT3"/></li>
	  <a href="/NamingServ?service=REGISTERFINISH&sequence=1&jSession=<c:out value="${idSession}"/>" class="button_link_long">อัพเกรดเลยตอนนี้ คลิก</a>&nbsp;&nbsp;หรือ&nbsp;
	  <a href="http://www.jobtopgun.com" class="button_link_long">ต้องการหางานก่อน คลิก</a>
	</ul>
	  <!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog">
	      <!-- Modal content-->
	      <div class="modal-content">
	       <button type="button" class="close" data-dismiss="modal" style="padding:10px;">&times;</button>
	       	 <br>
	        <div class="modal-body">
	          <p><fmt:message key="REGISTER_FINISH_MODAL"/></p>
	        </div>
	      </div>
	      
	    </div>
	  </div>
</div>
<div class="seperator"></div>
<div class="seperator"></div>
<div class="seperator"></div>
<div class="seperator"></div>
<!-- <div align="right">
		<u><a href="?view=applyLater"><fmt:message key="APPLY_LATER_LINK" /></a></u>&nbsp;>>
</div> -->
</form>

<br><br>

