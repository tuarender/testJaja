<%@page import="com.topgun.resume.TrackManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.ViewResumeManager"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<%
	int idJsk= Util.getInt(request.getParameter("idJsk"));
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry = Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	EmployerManager em=new EmployerManager();
	PositionManager pm=new PositionManager();
	
	String posCannotApply=Util.getStr(request.getParameter("posCannotApply"));
	String posCanApply=Util.getStr(request.getParameter("posCanApply"));
	
	posCannotApply=Encryption.decode(posCannotApply);
	posCanApply=Encryption.decode(posCanApply);
	
	List<String[]> positions = new ArrayList<String[]>();
	List<String[]> invalidPos = new ArrayList<String[]>();
	if(posCannotApply!="")
	{
		String[] positionSplit=posCannotApply.split("-");
		if(positionSplit!=null)
		{
			for(int i=0; i<positionSplit.length;i++)
			{
				if(positionSplit[i]!=null)
				{
					String[] position=positionSplit[i].split(",");
					String[] rsPos=new String[4];
					Employer emp=em.get(Util.getInt(position[0]));
					if(emp!=null)
					{
						rsPos[0]= Util.getStr(Util.getInt(position[0]));
						rsPos[1]= Util.getStr(emp.getNameEng());
						rsPos[2]= Util.getStr(Util.getInt(position[1]));
						if(idLanguage==38)
						{
							rsPos[1]= Util.getStr(emp.getNameThai());
						}
						Position pos=pm.getPosition(emp.getId(), Util.getInt(position[1]));
						if(pos!=null)
						{
							rsPos[3]= Util.getStr(pos.getPositionName());
						}
						invalidPos.add(rsPos);
					}
				}
			}
		}
	}
	if(posCanApply!="")
	{
		String[] positionSplit=posCanApply.split("-");
		if(positionSplit!=null)
		{
			for(int i=0; i<positionSplit.length;i++)
			{
				if(positionSplit[i]!=null)
				{
					String[] position=positionSplit[i].split(",");
					String[] rsPos=new String[4];
					Employer emp=em.get(Util.getInt(position[0]));
					if(emp!=null)
					{
						rsPos[0]= Util.getStr(Util.getInt(position[0]));
						rsPos[1]= Util.getStr(emp.getNameEng());
						rsPos[2]= Util.getStr(Util.getInt(position[1]));
						if(idLanguage==38)
						{
							rsPos[1]= Util.getStr(emp.getNameThai());
						}
						Position pos=pm.getPosition(emp.getId(), Util.getInt(position[1]));
						if(pos!=null)
						{
							rsPos[3]= Util.getStr(pos.getPositionName());
						}
						positions.add(rsPos);
					}
				}
			}
		}
	}
	request.setAttribute("positions",positions);
	request.setAttribute("invalidPos",invalidPos);

%>
<div>
	<div align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" align="center">
		<c:if test="${not empty positions}">
			<div class="row row-centered" >
					<div class="col-xs-0 col-sm-0 col-md-2 col-lg-2"></div>
					<div class="col-xs-0 col-sm-0 col-md-12 col-lg-12 " style="padding:1px; text-align:center;">
						<br><br><p style="color:#af1e39;"><b><fmt:message key="APPLY_ALL_RESULT"/></b></p><br>
					</div>
			</div>
			<div class="row">
			<div class="form-group">
				<div class="row">
					<div class="col-xs-1 col-sm-1 col-md-2 col-lg-2 " style="padding:1px; text-align:center;">
						<fmt:message key="APPLY_ALL_NO"/>
					</div>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" style="padding:1px; text-align:center;">
						<fmt:message key="APPLY_ALL_NAME_OF_COMPANY"/>
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 " style="padding:1px; text-align:center;">
						<fmt:message key="APPLY_ALL_POSITION"/>
					</div>
					<div class="col-xs-4 col-sm-4 col-md-3 col-lg-3 " style="padding:1px; text-align:center;">
						<fmt:message key="SEND_RESUME_NO_CANDIDATES"/>
					</div>
				</div>
			</div>
			
			<c:set var="i" scope="session" value="1"/>
			<c:forEach items="${positions}" var="position">
				<c:set var="idEmp" scope="request"><c:out value="${position[0]}"/></c:set>
				<c:set var="idPos" scope="request"><c:out value="${position[2]}"/></c:set>
			<%
				int idEmp= Util.getInt(request.getAttribute("idEmp"));
				int idPos= Util.getInt(request.getAttribute("idPos"));
				String  applyDate = Util.nowDateFormat("yyyyMMdd");
				int totalResume=new TrackManager().countTrackByDatePosition(applyDate, idEmp, idPos);
				request.setAttribute("totalResume",totalResume);
			%>
				<div class="row answer" style="border-bottom-style:dotted;border-bottom-width:0.2px;">
					<div class="col-xs-1 col-sm-1 col-md-2 col-lg-2 " style="padding:1px; text-align:center;">
						<c:out value="${i}"/>.
					</div>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" style="padding-right:12px;">
						<p style="text-align:left;"><c:out value="${position[1]}"  escapeXml="false"/> </p>
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 " style="padding:2px;">
						<p style="text-align:left;"><c:out value="${position[3]}"  escapeXml="false"/></p>
					</div>
					<div class="col-xs-4 col-sm-4 col-md-3 col-lg-3 " style="padding:1px; text-align:center;">
						<c:choose>
						<c:when test='${position[0] ne "-1" && position[2] ne "-1"}'>
							<span class='suggestion' style="text-align=right"><fmt:formatNumber value='${totalResume}' pattern="###,###"/></span>
						</c:when>
						<c:otherwise>
							<span class='suggestion' style="text-align=right"><c:out value="N/A"/></span>
						</c:otherwise>
		        		</c:choose>
					</div>
				</div>
				<c:set var="i" scope="session" value="${i+1 }"/>
			</c:forEach>
			</div>
		</c:if>
		</div>
	</div>
	<div align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" align="center">
		<c:if test="${not empty invalidPos}">
			<div class="row row-centered" >
					<div class="col-xs-0 col-sm-0 col-md-2 col-lg-2"></div>
					<div class="col-xs-0 col-sm-0 col-md-12 col-lg-12 " style="padding:1px; text-align:center;">
						<br><br><p style="color:#af1e39;"><b><fmt:message key="APPLY_ALL_NOTE"/></b></p>
					</div>
			</div>
			<div class="row">
			<div class="form-group">
				<div class="row">
					<div class="col-xs-0 col-sm-0 col-md-2 col-lg-2 " style="padding:1px; text-align:center;">
						<fmt:message key="APPLY_ALL_NO"/>
					</div>
					<div class="col-xs-0 col-sm-0 col-md-5 col-lg-5" style="padding:1px; text-align:center;">
						<fmt:message key="APPLY_ALL_NAME_OF_COMPANY"/>
					</div>
					<div class="col-xs-0 col-sm-0 col-md-3 col-lg-3 " style="padding:1px; text-align:center;">
						<fmt:message key="APPLY_ALL_POSITION"/>
					</div>
				</div>
			</div>
			
			<c:set var="i" scope="session" value="1"/>
			<c:forEach items="${invalidPos}" var="invalidPos">
				<div class="row answer" style="border-bottom-style:dotted;border-bottom-width:0.2px;">
					<div class="col-xs-0 col-sm-0 col-md-2 col-lg-2 " style="padding:1px; text-align:center;">
						<c:out value="${i}"/>.
					</div>
					<div class="col-xs-0 col-sm-0 col-md-5 col-lg-5" style="padding-right:12px;">
						<p style="text-align:left;"><c:out value="${invalidPos[1]}" escapeXml="false"/> </p>
					</div>
					<div class="col-xs-0 col-sm-0 col-md-3 col-lg-3 " style="padding:2px;">
						<p style="text-align:left;"><c:out value="${invalidPos[3]}" escapeXml="false"/></p>
					</div>
				</div>
				<c:set var="i" scope="session" value="${i+1 }"/>
			</c:forEach>
			</div>
		</c:if>
		</div>
	</div>
<div>
	<label class="colorb37bb6"><font color="red">*</font><fmt:message key="SAF_FINISH_HEADER"/></label>
	<p><fmt:message key="APPLY_RESULT_CONTENT"/></p>
</div>
	<div class="seperator"></div>
<div align="center">
	<div class="row">
			<c:choose>
				<c:when test="${sessionScope.SESSION_LOCALE eq 'en_TH'}">
					<img src="../images/sr_apps_en.png" class="img-responsive">
				</c:when>
				<c:otherwise>
					<img src="../images/sr_apps_th.png" class="img-responsive">
				</c:otherwise>
			</c:choose>
	</div>
	<div class="seperator"></div>
	<div class="seperator"></div>
	<div class="row">
		<div class="visible-xs">
			<div class="col-xs-12 col-sm-6">
				<a href='https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8'><img src="../images/app_store.png" width="60%"></a>
			</div>
			<div class="visible-xs col-xs-12">
				&nbsp;	
			</div>
			<div class="col-xs-12 col-sm-6">
				<a href='https://play.google.com/store/apps/details?id=com.topgun.jobtopgun'><img src="../images/google_play.png" width="60%" ></a>
			</div>
		</div>
		<div class="hidden-xs">
			<div class="col-xs-12 col-sm-6 text-right">	
				<a href='https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8'><img src="../images/app_store.png" width="70%"></a>
			</div>
			<div class="col-xs-12 col-sm-6 text-left">
				<a href='https://play.google.com/store/apps/details?id=com.topgun.jobtopgun'><img src="../images/google_play.png" width="70%" ></a>
			</div>
		</div>
	</div>
</div>
<div class="seperator"></div>
	<div class="row">
	<div align="center">
		<p><fmt:message key="APPLY_RESULT_HOW_TO"/>
			<a href="" data-toggle="modal" data-target="#myModal"><u><fmt:message key="SEND_CLICK_HERE"/></u></a>
		</p>
	</div>
	  <!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog">
	      <!-- Modal content-->
	      <div class="modal-content">
	       <button type="button" class="close" data-dismiss="modal" style="padding:10px;">&times;</button>
	       	 <br>
	        <div class="modal-body">
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
		<div align="center">
			<p><fmt:message key="APPLY_RESULT_DONE_DOWNLOAD"/><a href="http://www.jobtopgun.com/"><u><fmt:message key="APPLY_RESULT_SEEKING_FOR_JOB"/></u></a></p>
		</div>
</div>
</div>
 