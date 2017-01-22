<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.TrackManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Track"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Encoder"%>
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
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	String idSession=Encoder.getEncode(session.getId());
	List<Track>  tracks=new TrackManager().getAll(idJsk);
	request.setAttribute("idSession", idSession);
	request.setAttribute("tracks",tracks);
	request.setAttribute("totalTracks",tracks.size());
	String applyDate="";
	int i=0;

%>
<style>
<!--
.field_set {
    border: 1px solid #f5b419;
    border-radius: 10px;
    -moz-border-radius:10px;
	-webkit-border-radius:10px;
}
-->
</style>
<div>
	<h3 align="center" class="section_header"><fmt:message key="SECTION_APPLICATION"/></h3>
	<div align="center">
		<div class="suggestion">
			<fmt:message key="DIRECTION_APPLICATION_RECORD" />
			<fmt:message key="APPLY_RECORD_TOTAL_COMPANY"/><c:out value="${totalTracks }"/>
		
			<c:choose>
				<c:when test="${totalTracks > 1 }"><fmt:message key="GLOBAL_COMPANIES_UNIT"/></c:when>
				<c:otherwise><fmt:message key="GLOBAL_COMPANY_UNIT"/></c:otherwise>
			</c:choose>
		</div>
		<br />
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" align="center">
			<c:forEach var="track" items="${tracks}" varStatus="idx">
				<c:set var="applyDate" scope="request"><fmt:formatDate value='${track.sendDate}'  pattern="yyyyMMdd" /></c:set>
				<%
					applyDate=(String)request.getAttribute("applyDate");
					int adate = Integer.parseInt(applyDate);
					String applyDateFormat=Util.DateToStr(tracks.get(i).getSendDate(), "yyyyMMdd");
					int totalResume=new TrackManager().countTrackByDatePosition(applyDateFormat, tracks.get(i).getIdEmp(), tracks.get(i).getIdPosition());
					request.setAttribute("totalResume",totalResume);
					request.setAttribute("adate",adate);
				 %>
				 <fieldset class="field_set">
				 	<div align="left" style="padding-left:1em;padding-top:0.2em;padding-bottom:0.5em;">
				 		<b class='caption_bold'><c:out value="${idx.index+1}"/>.<fmt:message key="APPLY_RECORD_DATE"/>:</b>
						<span class='caption'><fmt:formatDate value='${track.sendDate}'  pattern="dd/MM/yyyy" /></span><br>
						<b class='caption_bold'><fmt:message key="APPLY_RECORD_COMPANY"/>:</b>
						<span class='caption'><c:out value="${track.empOther}"/></span><br>
						<c:choose>
	      					<c:when test='${track.idEmp ne "-1" && track.idPosition ne "-1" }'>
	      						<b class='caption_bold'><fmt:message key="APPLY_RECORD_POSITION"/>:</b>
				   				<span class='caption'><a href="http://www.jobtopgun.com/search/jobpost.jsp?idEmp=<c:out value='${track.idEmp}'/>&idPosition=<c:out value='${track.idPosition}'/>&hr=1" target="_blank"><c:out value="${track.positionOther}" escapeXml="flase"/></a></span>
	        				</c:when>
				            <c:otherwise>
				           		<b class='caption_bold'><fmt:message key="APPLY_RECORD_POSITION"/>:</b>
				                <span class='caption'><c:out value="${track.positionOther}"/></span>
				            </c:otherwise>
		  				</c:choose>
				 	</div>
				 	<hr style="border-top: 1px solid #f5b419;width:95%;margin-top:0.5px;margin-bottom:0.5em">
					<div align="left" style="padding-left:1em;padding-top:0.2em;">
						<b class='answer'><fmt:message key="SEND_RESUME_NO_CANDIDATES"/></b>
						<c:choose>
						<c:when test='${track.idEmp ne "-1" && track.idPosition ne "-1"}'>
							<span class='suggestion' style="text-align=right"><fmt:formatNumber value='${totalResume}' pattern="###,###"/></span>
						</c:when>
						<c:otherwise>
							<span class='suggestion' style="text-align=right"><c:out value="N/A"/></span>
						</c:otherwise>
		        		</c:choose>
					</div>
			        <hr style="border-top: 1px solid #f5b419;width:95%;margin-top:0.5em; margin-bottom:0.5em ">
					<%
						int countView=0;
						if(adate>=20110726)
						{
							countView=new TrackManager().chkViewResume(tracks.get(i).getIdEmp(), tracks.get(i).getIdPosition(), tracks.get(i).getIdJsk(), tracks.get(i).getIdResume());
							i++;
						}
						request.setAttribute("countView",countView);
					%>
						<div align="left" style="padding-left:1em;padding-top:0.2em;">
							<b class='answer'><fmt:message key="SEND_RESUME_TIME_VIEWING"/></b>
							<c:choose>
								<c:when test='${adate >= 20110726 }'>
									<c:choose>
										<c:when test="${countView >0}">
											<span class='suggestion' style="text-align=right"><fmt:message key="RECORD_VIEW" /></span>
										</c:when>
										<c:otherwise>
											<span class='suggestion' style="text-align=right"><fmt:message key="RECORD_RECIEVE" /></span>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<span class='suggestion' style="text-align=right"><c:out value="N/A"/></span>
								</c:otherwise>
							</c:choose>
		  				</div>
		  				<hr style="border-top: 1px solid #f5b419;width:95%;margin-top:0.5em; margin-bottom:0.5em ">
		  				<div align="left" style="padding-left:1em;padding-top:0.2em;padding-bottom:1em;">
		  					<c:set var="idResume" scope="request"><c:out value="${track.idResume}"/></c:set>
		  					<%
		  						
		  						 String idResume=(String)request.getAttribute("idResume");
		  						 Resume res= new ResumeManager().getIncludeDeleted(idJsk, Util.getInt(idResume));
		  						 request.setAttribute("ResName", Util.getStr(res.getResumeName()));
		  					%>
							<b class='answer'><fmt:message key="RECORD_SEND_RESUME"/> :</b>
							<a href="/view/viewResume.jsp?idResume=<c:out value="${track.idResume}"/>&jSession=<c:out value="${idSession}"/>"><c:out value="${ResName}" /></a>
		  				</div>
				</fieldset>
				<br>
			</c:forEach>
	</div>
</div>
</div>