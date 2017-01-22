<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Encryption"%>

<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>

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
		String Key=Encryption.getKey(0,0,resume.getIdJsk(),resume.getIdResume());
		int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
		int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
		String idSession=Encoder.getEncode(session.getId());
		Cookie[] cookies = null;
	   	cookies = request.getCookies();
	   	int idEmp = Util.getInt(request.getParameter("idEmp"));
		int idPosition = Util.getInt(request.getParameter("idPosition")); 
		int superMatch=Util.getInt(session.getAttribute("SESSION_SUPERMATCH"),0);
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
		request.setAttribute("resume",resume);
		request.setAttribute("superMatch",superMatch);
		request.setAttribute("idSession", idSession); 
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("Enc",Enc);
		request.setAttribute("Key",Key);
%>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=381512415208715";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<br>
<div class="seperator"></div>
<div align="center">
<c:choose>
<c:when test="${superMatch>0}">
<p align="center" class="font_16 colorb37bb6" ><fmt:message key="SM_EMAIL_REGISTER_WELCOME"/>
						<c:if test="${not empty resume}">
							<c:choose>
								<c:when test="${resume.idLanguage eq 38}">
									<c:out value="${resume.firstNameThai}"/>
								</c:when>
								<c:otherwise>
									<c:out value="${resume.firstName}"/>
								</c:otherwise>
							</c:choose>
						</c:if>
						</p>
		<div align="center" class="font_16 colorb37bb6" ><fmt:message key="GLOBAL_SUPERMATCH_CM"/></div>
		<div class="seperator"></div>
		<div align="center">
			<a href="http://www.jobtopgun.com?view=superMatch&jSession=<c:out value="${idSession}"/>&idJsk=<c:out value="${idJsk}"/>"><button type="submit" class="btn" style="background-color:#4abfa7;"><font style="color:white;" class="font_16"><fmt:message key="GLOBAL_SUPERMATCH_BTN"/></font></button></a>
		</div>
		<div><fmt:message key="GLOBAL_SUPERMATCH_BL"/></div>
		<div class="seperator"></div>
		<div class="seperator"></div>
		<div class="seperator"></div>
		<div align="right">
				<u><a href="?view=applyLater"><fmt:message key="APPLY_LATER_LINK" /></a></u>&nbsp;>>
		</div>
</c:when>
</c:choose>
<c:choose>
	<c:when test="${idEmp > 0 && idPosition > 0}">
	<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/> 
			<div class="normal" align="center">
			 <p align="center" class="font_16 color8081be" ><fmt:message key="GLOBAL_SUPERMATCH_CM"/></p>
					<b class="font_16" style="color:#8081be;"><fmt:message key="ACTIVITY_POSITION"/></b>&nbsp;<label class="answer"><c:out value="${positionName}" escapeXml="false"/></label><br>
					<b class="font_16" style="color:#8081be;"><fmt:message key="FAMILY_WORK_LAST_COMPANY"/></b>&nbsp;<label class="answer"><c:out value="${companyName}"/></label>
			</div>
			<div class="seperator"></div>
			<div align="center" class="font_16 colorb37bb6" ><fmt:message key="GLOBAL_APPLY_NOW"/></div>
			<div class="seperator"></div>
			<div align="center">
				<a href="/SRIX?view=apply&idEmp=<c:out value="${idEmp}"/>&idPosition=<c:out value="${idPosition}"/>&jSession=<c:out value="${idSession}"/>'"><button type="button" class="btn" style="background-color:#4abfa7;"><font style="color:white;" class="font_16"><fmt:message key="GLOBAL_BACK_TO_RESUME"/></font></button></a>
			</div>
			<br>
			<div class="seperator"></div>
			<div class="seperator"></div>
			<div class="seperator"></div>
			<div align="right">
				<u><a href="?view=applyLater"><fmt:message key="APPLY_LATER_LINK" /></a></u>&nbsp;>>
			</div>
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${superMatch == 0 and idEmp == -1  && idPosition == -1}">
	<div>
	<p align="center" class="font_16 color8081be" ><fmt:message key="SM_EMAIL_REGISTER_WELCOME"/>
		<c:if test="${not empty resume}">
			<c:choose>
				<c:when test="${resume.idLanguage eq 38}">
					<c:out value="${resume.firstNameThai}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${resume.firstName}"/>
				</c:otherwise>
			</c:choose>
			<p class="color8081be"><fmt:message key="GLOBAL_SUPERMATCH_CM"/></p>
		</c:if>
	</p>
	</div>	
	<div class="seperator"></div>	
	<div>
		<label class="colorb37bb6" ><fmt:message key="GLOBAL_SUPERMATCH_SG"/></label>
		<p><fmt:message key="APPLY_RESULT_CONTENT"/></p>
	</div>
		<div class="seperator"></div>
		<div align="center">
			<div class="row">
				<a href='https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8'><img src="../images/app_store.png" class="img-responsive" width="40%"></a>
			</div>
			<div class="seperator"></div>
			<div class="row">
				<a href='https://play.google.com/store/apps/details?id=com.topgun.jobtopgun'><img src="../images/google_play.png" class="img-responsive" width="40%"></a>
			</div>
		</div>
			<div class="seperator"></div>
			<div class="row">
				<div align="center">
					<p><fmt:message key="APPLY_RESULT_HOW_TO"/>
						<a href="" data-toggle="modal" data-target="#myModal"><u><fmt:message key="SEND_CLICK_HERE"/></u></a>
					</p>
				</div>
			</div>
			  <!-- Modal -->
			  <div class="modal fade" id="myModal" role="dialog">
			    <div class="modal-dialog">
			      <!-- Modal content-->
			      <div class="modal-content">
			       <button type="button" class="close" data-dismiss="modal" style="padding:10px;">&times;</button>
			       	 <br>
			        <div class="modal-body text-left">
			          <p class="color7461a7 font_16"><b><fmt:message key="APPLY_RESULT_APP"/></b></p>
			          <li class="colorb37bb6">iOS</li>
			          <div style="height:10px;"></div>
			          <p>1. <fmt:message key="APPLY_RESULT_APP_STEP1"/> <img src="../images/app_store.png" class="img-responsive" width="20%" style="display:inline;"></p>
			          <p>2. <fmt:message key="APPLY_RESULT_APP_STEP2"/></p>
			          <p>3. <fmt:message key="APPLY_RESULT_APP_STEP3"/></p>
			          <p>4. <fmt:message key="APPLY_RESULT_APP_STEP4"/></p>
			          <p>5. <fmt:message key="APPLY_RESULT_APP_STEP5"/></p>
			          <div class="seperator"></div>
			            <li class="colorb37bb6">Android</li>
			          <div style="height:10px;"></div>
			          <p>1.<fmt:message key="APPLY_RESULT_APP_STEP1_ANDROID"/> <img src="../images/google_play.png" class="img-responsive" width="20%" style="display:inline;"></p>
			          <p>2.<fmt:message key="APPLY_RESULT_APP_STEP2"/></p>
			          <p>3.<fmt:message key="APPLY_RESULT_APP_STEP3"/></p>
			          <p>4.<fmt:message key="APPLY_RESULT_APP_STEP4"/></p>
			          <p>5.<fmt:message key="APPLY_RESULT_APP_STEP5"/></p>
			          
			        </div>
			      </div>
			    </div>
			  </div>
		<div class="row">
			<div align="center">
				<p><fmt:message key="APPLY_RESULT_DONE_DOWNLOAD"/><a href="http://www.jobtopgun.com?view=index&idJsk=<c:out value="${idJsk }"/>&locale=<c:out value="${sessionScope.SESSION_LOCALE }"/>&jSession=<c:out value="${idSession}"/>"><u><fmt:message key="APPLY_RESULT_SEEKING_FOR_JOB"/></u></a></p>
			</div>
			<div align="center">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'en_TH'}">
						View your Super Resume <a href="?view=home"><fmt:message key="CLICK_HERE"/></a>
					</c:when>
					<c:otherwise>
						ดู Super Resume ของคุณ <a href="?view=home"><u><fmt:message key="CLICK_HERE"/></u></a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:when>
</c:choose>
</div>


