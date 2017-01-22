<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.ViewResumeManager"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.resume.PopupManager"%>
<%@page import="com.topgun.resume.Popup"%>
<c:import url="/config.jsp" charEncoding="UTF-8" />
<style>

.button_link_create
{
  -webkit-border-radius: 14;
  -moz-border-radius: 14;
  border-radius: 5px;
  font-family: Arial;
  color: #ffffff;
  font-size: 16px;
  background: #4ABEA6;
  padding: 10px 20px 10px 20px;
  text-decoration: none;
  border: solid #9aa4ab 1px;
  cursor:pointer;
}

@media (min-width: 752px){
.modal-dialog {
    width: 600px !important;
}
}
</style>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}" />
<%
	int idJsk = Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry = Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	String idSession = Encoder.getEncode(session.getId());
	int remember = Util.getInt(request.getParameter("remember"));
	//update all superresume status(idResume>0)
	
	List<Resume> rs = new ResumeManager().getAll(idJsk, false, false);
	
	
	ResumeManager rsmMng = new ResumeManager();
	if (rs != null) {
		for (int i = 0; i < rs.size(); i++) {
			new ResumeManager().updateStatus(idJsk, rs.get(i).getIdResume(),
					new ResumeStatusManager().getResumeStatus(rs.get(i)));
		}
	}
	List<Resume> resumes = new ArrayList<Resume>();
	Resume saf = new ResumeManager().get(idJsk, 0);
	if (saf != null) {
		if (Util.getStr(new ResumeStatusManager().getRegisterStatus(saf)).equals("TRUE")) {
			resumes = new ResumeManager().getAll(idJsk, false, false, true);
			int resumeCount = resumes != null ? resumes.size() : 0;
			request.setAttribute("resumes", resumes);
			request.setAttribute("resumeCount", resumeCount > 10 ? 10 : resumeCount);
		}
		request.setAttribute("saf", saf);
	}
	int i = 0;
	String encView = Encryption.getEncoding(0, 0, idJsk, 0);
	String keyView = Encryption.getKey(0, 0, idJsk, 0);
	request.setAttribute("encView", encView);
	request.setAttribute("keyView", keyView);
	request.setAttribute("idSession", idSession);
	//Show layer popup
	PopupManager popMng = new PopupManager();
	int checkPopup = popMng.checkPopup(idJsk, 0);
	if(checkPopup==1){
		List<Popup> resumeName = popMng.getResumePopup(idJsk,idLanguage);
		request.setAttribute("resumeName", resumeName);
		//updateFlg
		popMng.updateFlg(idJsk);
	}
	request.setAttribute("checkPopup", checkPopup);
	int idResumeMatch = new ResumeManager().getSuperMatchIdResume(idJsk);
	request.setAttribute("idResumeMatch", idResumeMatch);
	request.setAttribute("idJsk", idJsk);
	List<Resume> showList = new ArrayList<Resume>();
	List<Resume> totalResumeList = new ArrayList<Resume>();
	int idResumeLastUpdate = new ResumeManager().getIdResumeLastUpdate(idJsk);
	int idResumeLastApply = new ResumeManager().getIdResumeLastApply(idJsk);
	Resume resumeLastApply = new Resume();
	Resume resumeMatch = new Resume();
	Resume resumeLastUpdate = new Resume();
	resumeLastApply = null;
	resumeMatch = null;
	resumeLastUpdate = null;
	for(int l=0 ;l<rs.size();l++)
	{
		//System.out.println("rs > "+rs.get(l).getIdResume()+" idResumeMatch > "+idResumeMatch+" idResumeLastUpdate > "+idResumeLastUpdate+" idResumeLastApply > "+idResumeLastApply);
		if(rs.get(l).getIdResume()==idResumeMatch || rs.get(l).getIdResume()==idResumeLastUpdate || rs.get(l).getIdResume()==idResumeLastApply)
		{
			if(rs.get(l).getIdResume()==idResumeMatch){
				resumeMatch=rs.get(l);
			}
			if(rs.get(l).getIdResume()==idResumeLastUpdate){
				resumeLastUpdate=rs.get(l);
			}
			if(rs.get(l).getIdResume()==idResumeLastApply){
				resumeLastApply=rs.get(l);
			}
		}else{
			totalResumeList.add(rs.get(l));
		}
	}
	if(resumeLastApply!=null){
		showList.add(resumeLastApply);
	}
	if(resumeLastUpdate!=null)
	{
		if(!showList.contains(resumeLastUpdate)){
			showList.add(resumeLastUpdate);
		}
	}
	if(resumeMatch!=null)
	{
		if(!showList.contains(resumeMatch)){
			showList.add(resumeMatch);
		}
	}
	int size = showList.size()+1;
	request.setAttribute("totalResumeList", totalResumeList);
	request.setAttribute("showList", showList);
	request.setAttribute("idResumeLastUpdate", idResumeLastUpdate);
	request.setAttribute("idResumeLastApply", idResumeLastApply);
	request.setAttribute("size", size);
	
%>
<c:import url="/view/remindUpdate.jsp" charEncoding="UTF-8" />
<div class="errorContainer" style="padding: 5px;">
	<b><fmt:message key="GLOBAL_PLEASE_READ" /></b>
	<ol></ol>
</div>

<c:if test="${checkPopup eq 1 }">
	<div id="popup" class="alert alert-info" style="margin-top: 30px;background-color: #c5edff;border-radius:10px;line-height:1.7">
	<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		</br>
		<p style="color: #444444;">
			<fmt:message key="POPUP_DESCRIPTION1" />
			<fmt:message key="POPUP_DESCRIPTION2" />
			<fmt:message key="POPUP_DESCRIPTION3" />
		</p>
		<h5 id="swap" style="color: #a94aa5;cursor:pointer">
			<fmt:message key="POPUP_RESUME_UPDATE" />
			<img id="arrow" style="width: 15px;" src="/images/arrow-04.png" />
		</h5>
		<div id="popupName" >
			<c:forEach var="res" items="${resumeName}" varStatus="loop">
				<label style="color: #7755ab;"><c:out value='${loop.count}' />.&nbsp;<c:out value='${res.resumeName}' /></label>
				<ul style="color: #444444;">
					<c:forEach var="job" items="${res.popupList}">
						<li> <fmt:message key="POPUP_RESUME_FROM" /> <strong style="color:#006BB6">"<c:out value='${job.fieldNameOld}' /> : <c:out value='${job.subFieldNameOld}' />"</strong>  
							<fmt:message key="POPUP_RESUME_TO" /> <strong style="color:#006BB6">"<c:out value='${job.fieldNameNew}' /> : <c:out value='${job.subFieldNameNew}' />"</strong>
						</li>
					</c:forEach>
				</ul>
			</c:forEach>
		</div>
	</div>
</c:if>

<div class="row"
	style="border: 0px; margin-top: 30px; display: inline-block; text-align: right">
	<div class="col-xs-3">
		<div id="imageDiv" class="imgLiquidNoFill imgLiquid"
			style="width: 81px; height: 111px; display: absolute">
			<a
				href="/SRIX?view=uploadPhoto&idResume=0&sequence=0&jSession=<c:out value="${idSession}"/>"><img
				id="photo" src="/images/photoUpload.png" /></a>
		</div>
		<span><a
			href="/SRIX?view=uploadPhoto&idResume=0&sequence=0&jSession=<c:out value="${idSession}"/>"><fmt:message
					key="GLOBAL_EDIT" /></a></span>
	</div>
	<div class="col-xs-9 h4">
		<c:choose>
			<c:when
				test="${sessionScope.SESSION_LANGUAGE eq 'th' and saf.idLanguage eq 38}">
				<c:out value="${saf.firstNameThai}" />
			</c:when>
			<c:otherwise>
				<c:out value="${saf.firstName}" />
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div>
	<h4 style="color: #ad70ad;">
		Super Resume
	</h4>
</div>
<div>
	<span class="answer"> <c:set var="one" value="1" /> <fmt:message
			key='RESUME_COUNT'>
			<c:choose>
				<c:when test="${resumeCount>0}">
					<fmt:param value="${resumeCount}" />
				</c:when>
				<c:otherwise>
					<fmt:param value="${one}" />
				</c:otherwise>
			</c:choose>
		</fmt:message>
	</span>
</div>
<div>
	<c:choose>
		<c:when test="${not empty saf}">
			<c:choose>
				<c:when test="${empty resumes}">
					<fmt:formatDate var="timeStamp" value="${saf.timeStamp}"
						pattern="d/MM/yyyy" />
					<table style="border-collapse: collapse; width: 100%;">
					<tr>
						<td class="digit">1</td>
						<td valign="top">&nbsp;&nbsp;<b>My Super Resume(<fmt:message
									key="SHORT_RESUME" />)
						</b>&nbsp;&nbsp;(<fmt:message key='LAST_UPDATE' />&nbsp;&nbsp;<c:out
								value="${timeStamp}" />)
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<div class="resumeLayer"
								id="resume_<c:out value="${saf.idResume}"/>"
								style="display: block;">
								<%
									String Enc = Encryption.getEncoding(0, 0, saf.getIdJsk(), saf.getIdResume());
											String Key = Encryption.getKey(0, 0, saf.getIdJsk(), saf.getIdResume());
											request.getSession().setAttribute("SESSION_LOCALE", saf.getLocale());
											request.setAttribute("enc", Enc);
											request.setAttribute("key", Key);
								%>
								<c:choose>
									<c:when test="${saf.idResume==0}">
										<c:set var="upgrade">
											<span class="answer"><fmt:message key="UPGRADE" /></span>
										</c:set>
										<c:if test="${resumeCount == 0}">
											<fmt:message key='UPGRADE_SUGGESTION'>
												<fmt:param value="${upgrade}"></fmt:param>
											</fmt:message>
											<br />
											<a
												href="/SRIX?view=naming&cmd=CREATE&sequence=1&jSession=<c:out value="${idSession}"/>"><fmt:message
													key="UPGRADE" /></a> |
											</c:if>
										<a target="_blank"
											href="/view/viewResume.jsp?idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
												key="GLOBAL_VIEW" /></a> |
											<a
											href="/SRIX?view=resumeInfo&idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
												key="GLOBAL_EDIT" /></a> |
											<a target="_blank"
											href="http://www.topgunthailand.com/jtg/research/prediction.php?Enc=<c:out value="${enc}"/>&Key=<c:out value="${key}"/>&jSession=<c:out value="${idSession}"/>"
											style="cursor: pointer;"><fmt:message key="ANALYZE_BUTTON" /></a>|
											<a
											href="/SRIX?view=jobmatchDetail&idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
												key="HOME_JOBMATCH" /></a>
									</c:when>
									<c:otherwise>
										<a
											href="/view/mobile/viewResume.jsp?idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>"
											target="_blank"><fmt:message key="GLOBAL_VIEW" /></a> | 
											<a
											href="/SRIX?view=resumeInfo&idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
												key="GLOBAL_EDIT" /></a> | 
											<a
											href="/SRIX?view=naming&cmd=COPY&sequence=0&idResume=<c:out value="${saf.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
												key="GLOBAL_COPY" /></a>
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</table>
				</c:when>
			</c:choose>
			<br>
			<c:choose>
				<c:when test="${not empty showList}">
					<table style="border-collapse: collapse; width: 100%;">
						<c:set var="newIndex" scope="page" value="1" />
						<c:forEach begin="0" end="9" var="resume" items="${showList}"
							varStatus="idx">
							<c:if test="${resume.idParent == 0 or resume.idParent eq null}">
								<c:set var="idResume" scope="request" value="${resume.idResume}" />
								<%
									String resumeStatus = new ResumeStatusManager().getResumeStatus(resumes.get(i));
															String enc = Encryption.getEncoding(0, 0, resumes.get(i).getIdJsk(),
																	resumes.get(i).getIdResume());
															String key = Encryption.getKey(0, 0, resumes.get(i).getIdJsk(),
																	resumes.get(i).getIdResume());
															String resumeLocale = new ResumeManager().getResumeLanguage(
																	resumes.get(i).getIdLanguage(), idLanguage);

															request.setAttribute("resumeStatus", resumeStatus);
															request.setAttribute("resumeLocale", resumeLocale);
															request.setAttribute("enc", enc);
															request.setAttribute("key", key);
															i++;
								%>
								<tr>
									<td class="digit" valign="top"><c:out value="${newIndex}" /></td>
									<td valign="top" class="answer"><fmt:formatDate
											var="timeStamp" value="${resume.timeStamp}"
											pattern="d/MM/yyyy" /> &nbsp;&nbsp;<b><c:out
												value="${resume.resumeName}" /></b> <c:out
											value="${resumeLocale}" />&nbsp;&nbsp;(<fmt:message
											key='LAST_UPDATE' />&nbsp;&nbsp;<c:out value="${timeStamp}" />)
									</td>
								</tr>
								<c:if test="${idResumeLastUpdate == resume.idResume}">
								<tr>
									<td></td>
									<td valign="top" class="answer">
										(เป็นฉบับที่คุณอัพเดตล่าสุด) 
									</td>
								</tr>
								</c:if>
								<c:if test="${idResumeMatch == resume.idResume}">
								<tr>
									<td></td>
									<td valign="top" class="answer">
										(เป็นฉบับที่เราส่ง Super Match ให้คุณ) 
										<a href='#' class="button_link superMatch" style="text-decoration: none;color: #ffffff;">
										เปลี่ยน</a>
									</td>
								</tr>
								</c:if>
								<c:if test="${idResumeLastApply == resume.idResume}">
								<tr>
									<td></td>
									<td valign="top" class="answer">
										(เป็นฉบับที่คุณส่งสมัครงานล่าสุด) 
									</td>
								</tr>
								</c:if>
								<tr>
									<td></td>
									<td>
										<div class="resumeLayer"
											id="resume_<c:out value="${resume.idResume}"/>"
											style="display: block;">
											<c:choose>
												<c:when test="${resume.idResume==0}">
													<c:set var="upgrade">
														<span class="answer"><fmt:message key="UPGRADE" /></span>
													</c:set>
													<fmt:message key='UPGRADE_SUGGESTION'>
														<fmt:param value="${upgrade}"></fmt:param>
													</fmt:message>
													<br />
													<a href="/SRIX?view=naming&cmd=CREATE&sequence=1"><fmt:message
															key="UPGRADE" /></a>
													<a target="_blank"
														href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
															key="GLOBAL_VIEW" /></a>
													<a
														href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
															key="GLOBAL_EDIT" /></a>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${resumeStatus=='TRUE'}">
															<a target="_blank"
																href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_VIEW" /></a> |
																<a
																href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_EDIT" /></a> |
																<c:if test="${resumeCount<10}">
																<a
																	href="/SRIX?view=naming&cmd=COPY&sequence=0&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																		key="GLOBAL_COPY" /></a> |
																</c:if>
															<a class="deleteResume" style="cursor: pointer;"
																id="<c:out value="${resume.idResume}"/>"><fmt:message
																	key="GLOBAL_DELETE" /></a> |
																<a target="_blank"
																href="http://www.topgunthailand.com/jtg/research/prediction.php?Enc=<c:out value="${enc}"/>&Key=<c:out value="${key}"/>&jSession=<c:out value="${idSession}"/>"
																style="cursor: pointer;"><fmt:message
																	key="ANALYZE_BUTTON" /></a> |  
																<a
																href="/SRIX?view=jobmatchDetail&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="HOME_JOBMATCH" /></a>
														</c:when>
														<c:otherwise>
															<a target="_blank"
																href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_VIEW" /></a> | 
																<a
																href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_EDIT" /></a> |
																<a class="deleteResume" style="cursor: pointer;"
																id="<c:out value="${resume.idResume}"/>"><fmt:message
																	key="GLOBAL_DELETE" /></a> |
																<c:choose>
																<c:when test="${resumeStatus=='PERSONALDATA'}">
																	<a
																		href="/SRIX?view=personal&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='EDUCATION'}">
																	<a
																		href="/SRIX?view=education&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='EXPERIENCE'}">
																	<a
																		href="/SRIX?view=experience&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='TARGETJOB'}">
																	<a
																		href="/SRIX?view=targetJob&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='STRENGTH'}">
																	<a
																		href="/SRIX?view=strength&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='APTITUDE'}">
																	<a
																		href="/SRIX?view=aptitude&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='CAREER_OBJECTIVE'}">
																	<a
																		href="/SRIX?view=career&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='SKILL_COMPUTER'}">
																	<a
																		href="/SRIX?view=skillComputer&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='SKILL_LANGUAGE'}">
																	<a
																		href="/SRIX?view=skillLanguage&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
															</c:choose>
																 | <a
																href="/SRIX?view=jobmatchDetail&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="HOME_JOBMATCH" /></a>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</div>
									</td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="2"></td>
								</tr>
								<!--<c:set var="newIndex" scope="page" value="${newIndex + 1}" />-->
							</c:if>
						</c:forEach>
					</table>
				</c:when>
			</c:choose>
		</c:when>
	</c:choose>
</div>
<c:choose>
	<c:when test="${not empty totalResumeList}">
		<a style="text-align:right" href="#" class="show swap">ดู Super Resume ฉบับอื่นๆ คลิก <img width="20" src="/images/arrow_down_green.png"></a>
	</c:when>
</c:choose>
<div id="resumeLayer">
	<c:choose>
				<c:when test="${not empty totalResumeList}">
					<table style="border-collapse: collapse; width: 100%;">
						<c:set var="newIndex" scope="page" value="${size}" />
						<c:forEach begin="0" end="9" var="resume" items="${totalResumeList}" varStatus="idx">
							<c:if test="${resume.idParent == 0 or resume.idParent eq null}">
								<c:set var="idResume" scope="request" value="${resume.idResume}" />
								<%
									String resumeStatus = new ResumeStatusManager().getResumeStatus(resumes.get(i));
															String enc = Encryption.getEncoding(0, 0, resumes.get(i).getIdJsk(),
																	resumes.get(i).getIdResume());
															String key = Encryption.getKey(0, 0, resumes.get(i).getIdJsk(),
																	resumes.get(i).getIdResume());
															String resumeLocale = new ResumeManager().getResumeLanguage(
																	resumes.get(i).getIdLanguage(), idLanguage);

															request.setAttribute("resumeStatus", resumeStatus);
															request.setAttribute("resumeLocale", resumeLocale);
															request.setAttribute("enc", enc);
															request.setAttribute("key", key);
															i++;
								%>
								<tr>
									<td class="digit" valign="top"><c:out value="${newIndex}" /></td>
									<td valign="top" class="answer"><fmt:formatDate
											var="timeStamp" value="${resume.timeStamp}"
											pattern="d/MM/yyyy" /> &nbsp;&nbsp;<b><c:out
												value="${resume.resumeName}" /></b> <c:out
											value="${resumeLocale}" />&nbsp;&nbsp;(<fmt:message
											key='LAST_UPDATE' />&nbsp;&nbsp;<c:out value="${timeStamp}" />)
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<div class="resumeLayer"
											id="resume_<c:out value="${resume.idResume}"/>"
											style="display: block;">
											<c:choose>
												<c:when test="${resume.idResume==0}">
													<c:set var="upgrade">
														<span class="answer"><fmt:message key="UPGRADE" /></span>
													</c:set>
													<fmt:message key='UPGRADE_SUGGESTION'>
														<fmt:param value="${upgrade}"></fmt:param>
													</fmt:message>
													<br />
													<a href="/SRIX?view=naming&cmd=CREATE&sequence=1"><fmt:message
															key="UPGRADE" /></a>
													<a target="_blank"
														href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
															key="GLOBAL_VIEW" /></a>
													<a
														href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
															key="GLOBAL_EDIT" /></a>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${resumeStatus=='TRUE'}">
															<a target="_blank"
																href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_VIEW" /></a> |
																<a
																href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_EDIT" /></a> |
																<c:if test="${resumeCount<10}">
																<a
																	href="/SRIX?view=naming&cmd=COPY&sequence=0&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																		key="GLOBAL_COPY" /></a> |
																</c:if>
															<a class="deleteResume" style="cursor: pointer;"
																id="<c:out value="${resume.idResume}"/>"><fmt:message
																	key="GLOBAL_DELETE" /></a> |
																<a target="_blank"
																href="http://www.topgunthailand.com/jtg/research/prediction.php?Enc=<c:out value="${enc}"/>&Key=<c:out value="${key}"/>&jSession=<c:out value="${idSession}"/>"
																style="cursor: pointer;"><fmt:message
																	key="ANALYZE_BUTTON" /></a> |  
																<a
																href="/SRIX?view=jobmatchDetail&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="HOME_JOBMATCH" /></a>
														</c:when>
														<c:otherwise>
															<a target="_blank"
																href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_VIEW" /></a> | 
																<a
																href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="GLOBAL_EDIT" /></a> |
																<a class="deleteResume" style="cursor: pointer;"
																id="<c:out value="${resume.idResume}"/>"><fmt:message
																	key="GLOBAL_DELETE" /></a> |
																<c:choose>
																<c:when test="${resumeStatus=='PERSONALDATA'}">
																	<a
																		href="/SRIX?view=personal&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='EDUCATION'}">
																	<a
																		href="/SRIX?view=education&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='EXPERIENCE'}">
																	<a
																		href="/SRIX?view=experience&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='TARGETJOB'}">
																	<a
																		href="/SRIX?view=targetJob&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='STRENGTH'}">
																	<a
																		href="/SRIX?view=strength&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='APTITUDE'}">
																	<a
																		href="/SRIX?view=aptitude&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='CAREER_OBJECTIVE'}">
																	<a
																		href="/SRIX?view=career&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='SKILL_COMPUTER'}">
																	<a
																		href="/SRIX?view=skillComputer&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
																<c:when test="${resumeStatus=='SKILL_LANGUAGE'}">
																	<a
																		href="/SRIX?view=skillLanguage&idResume=<c:out value="${resume.idResume}"/>&sequence=0"><fmt:message
																			key="UPGRADE_COMPLETE" /></a>
																</c:when>
															</c:choose>
																 | <a
																href="/SRIX?view=jobmatchDetail&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
																	key="HOME_JOBMATCH" /></a>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</div>
									</td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="2"></td>
								</tr>
								<!--<c:set var="newIndex" scope="page" value="${newIndex + 1}" />-->
							</c:if>
						</c:forEach>
					</table>
				</c:when>
			</c:choose>
</div>
<!-- ATTACHMENT -->
<c:if test="${resumeCount<10 && resumeCount!=0}">
	</br>
	<a href='/SRIX?view=naming&cmd=CREATE&sequence=1&jSession=<c:out value="${idSession}"/>' class="button_link_create" style="text-decoration: none;color: #ffffff;">
	สร้าง Super Resume ฉบับใหม่</a>
	</br></br>
</c:if>
<!-- OWN RESUME -->
<div>
	<table style="border-collapse: collapse; width: 100%; border: 0px">
		<tr style="height: 5px;">
			<td></td>
		</tr>
		<tr>
			<td><span class="suggestion"><fmt:message
						key='HOME_YOUR_OWN_RESUME' /></span>&nbsp;<span id="totalSizeOwnResume"></span>&nbsp;<a
				class="addAttachment" id="ownResume" href='javascript:void(0)'><img
					border="0" src="/images/add.png" height="16" width="16"
					style="vertical-align: middle"></a></td>
		</tr>
		<tr style="height: 5px;">
			<td></td>
		</tr>
		<tr>
			<td>
				<div id="resumeListDiv" style="display: none"></div>

			</td>
		</tr>
	</table>
</div>
<!-- Document -->
<div style="margin-bottom: 20px">
	<table style="border-collapse: collapse; width: 100%; border: 0px">
		<tr style="height: 5px;">
			<td></td>
		</tr>
		<tr>
			<td><span class="suggestion"><fmt:message
						key='HOME_OTHER_DOCUMENT' /></span>&nbsp;<span id="totalSizeDocument"></span>&nbsp;<a
				class="addAttachment" id="document" href='javascript:void(0)'><img
					border="0" src="/images/add.png" height="16" width="16"
					style="vertical-align: middle"></a></td>
		</tr>
		<tr style="height: 5px;">
			<td></td>
		</tr>
		<tr>
			<td>
				<div id="documentListDiv" style="display: none"></div>
			</td>
		</tr>
	</table>
</div>
<!-- modal confirm delete -->
<div class="modal fade" id="modalDeleteResume" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Confirm</h4>
			</div>
			<div class="modal-body">
				<fmt:message key="CONFIRM_DELETE_RESUME" />
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="GLOBAL_CANCEL" />
				</button>
				<button type="button" class="btn btn-primary"
					onclick="deleteResume()">
					<fmt:message key="GLOBAL_CONFIRM" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal confirm delete -->
<!-- modal confirm delete file-->
<div class="modal fade" id="modalDelete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Confirm</h4>
			</div>
			<div class="modal-body">
				<fmt:message key="CONFIRM_DELETE_RESUME" />
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="GLOBAL_CANCEL" />
				</button>
				<button type="button" class="btn btn-primary" onclick="deleteFile()">
					<fmt:message key="GLOBAL_CONFIRM" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal confirm delete file-->
<!-- modal upload attachment -->
<div class="modal fade" id="modalAttachment" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabelAttachment">Confirm</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-1 col-sm-2"></div>
					<div class="col-xs-10 col-sm-8">
						<form id="uploadFile" name="uploadFile" action="/UploadPhotoServ"
							method="POST" class="form-horizontal"
							enctype="multipart/form-data">
							<input type="hidden" name="idResume"
								value="<c:out value="${resume.idResume}"/>"> <input
								type="hidden" name="idFile" id="idFile" value="0"> <input
								type="hidden" name="service" id="service" value="addFile">
							<input type="hidden" name="DocType" id="DocType" value="">
							<div class="form-group">
								<label for="fileTitle"><fmt:message key='FILE_TITLE' /></label>
								<input class="form-control" type="text" id="fileTitle"
									name="fileTitle" maxlength="100">
							</div>
							<div class="form-group">
								<input name="inputUploadFile" id="inputUploadFile" type='file'>
							</div>
							<div class="form-group" id="attachmentType">
								<fmt:message key="FILE_RESUME_TYPE" />
							</div>
							<div class="errorContainer2" style="padding: 5px; display: none">
								<b><fmt:message key="GLOBAL_PLEASE_READ" /></b>
								<ol></ol>
							</div>
						</form>
					</div>
					<div class="col-xs-1 col-sm-2"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="GLOBAL_CANCEL" />
				</button>
				<button type="button" class="btn btn-primary" id="submitAttachment">
					<fmt:message key="GLOBAL_CONFIRM" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal upload attachment -->
<!-- modal super match -->
<div class="modal fade" id="resumeSuperMatch" role="dialog">
	<form id="superMatchFrm" name="superMatch" method="POST">
		<div class="modal-dialog">
				<div class="modal-content">
			        <div class="modal-body">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
						<div class="radio answer">
							<div id="superMatchLayer" >
								<h4 class="modal-title"  style="color:#725FA7;text-align:center;">
									เลือก Super Resume ฉบับที่คุณต้องการให้ส่ง Super Match
								</h4></br>
								<p style="text-align:center;">(อีเมลแจ้งตำแหน่งงานที่คุณต้องการและ HR ก็ต้องการคุณ)</p>
								<c:forEach var="resume" items="${resumes}">
									<div class="checkbox">
										<div class="col-xs-1 col-sm-2"></div>
										<div class="col-xs-11 col-sm-10">
											<label>
												<input type="radio" name="matchIdResume" id="sup<c:out value='${resume.idResume}'/>" 
												<c:if test="${idResumeMatch == resume.idResume}">
												<c:out value="checked='checked'"/>
												</c:if>
												
												value="<c:out value='${resume.idResume}'/>"><c:out value='${resume.resumeName}'/>
											</label>
											&nbsp;&nbsp;<c:out value="${resumeLocale}" /></br>
											<p style=" line-height: 1.5em; padding-left: 20px; ">
												(<fmt:message key='LAST_UPDATE' />&nbsp;&nbsp;<c:out value="${timeStamp}" />)
												<a target="_blank"
													href="/view/viewResume.jsp?idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
														key="GLOBAL_VIEW" /></a>
												&nbsp;&nbsp;|&nbsp;&nbsp;
												<a
													href="/SRIX?view=resumeInfo&idResume=<c:out value="${resume.idResume}"/>&jSession=<c:out value="${idSession}"/>"><fmt:message
														key="GLOBAL_EDIT" /></a>
											</p>
										</div>
									</div>
								</c:forEach>
								<input type="hidden" name="idJsk" value="<c:out value="${idJsk}"/>">
							</div>
						</div>
						<div align="center">
						  <input type="image" src="/images/<c:out value='${locale}'/>/confirm.png" id="submitBtn"/>
						</div>
			        </div>
		    	</div>
		</div>
	</form>
</div>
<!-- /end modal super match -->

<div id="attachmentDialog" title="Upload" style="display: none"></div>
<!-- end modal upload attachment -->
<script src="/js/imgLiquid-min.js"></script>
<script src="/js/jquery.form.js"></script>
<script>
	var errorContainer = null;
	var errorContainer2 = null;
	var globalFileRequire = '<fmt:message key="FILE_REQUIRE"/>';
	var globalTitleRequire = '<fmt:message key="FILE_TITLE_REQUIRE"/>';
	var globalattachResume = '<fmt:message key="HOME_YOUR_OWN_RESUME"/>';
	var globalattachDocument = '<fmt:message key="HOME_OTHER_DOCUMENT"/>';
	var globalTypeResume = '<fmt:message key="FILE_RESUME_TYPE"/>';
	var globalTypeDocument = '<fmt:message key="FILE_DOCUMENT_TYPE"/>';
	var idFileTemp = "";
	var idResumeTemp = "";
	var idChildEdit = 0;
	var totalResume =
<%=resumes.size()%>
	;

	$(document).ready(
			function() {
			
				$('#resumeLayer').hide();
				errorContainer = $('div.errorContainer');
				errorContainer2 = $('div.errorContainer2');
				getPhoto();
				setImageSize();

				//----------------------------- set event --------------------------------
				$('.deleteResume').click(function() {
					idResumeTemp = this.id;
					$('#modalDeleteResume').modal('show');
				});
				
				$('.superMatch').click(function() {
					$('#resumeSuperMatch').modal('show');
				});
				
				$('.show').click(function() {
					$('#resumeLayer').slideToggle( "slow" );
					if ( $('.show img').attr('src') == '/images/arrow_down_green.png' ) {
				        $('.show img').attr('src', '/images/arrow_up_green.png');
				    } else {
				        $('.show img').attr('src', '/images/arrow_down_green.png');
				    }
				});
				
				$('#submitBtn').click(function() {
					$.ajax({
						type : "POST",
						url : '/AccountServ',
						data : $('#superMatchFrm').serialize(),
						async : false,
						success : function(data) {
							var obj = jQuery.parseJSON(data);
		       				if(obj.success==1)
		       				{
								$("#resumeSuperMatch").modal('hide');
							}
						}
					});
					
				});
				

				$('.addAttachment').click(
						function() {
							errorContainer2.hide();
							$('div.errorContainer2 li').remove();
							if ($(this).attr('id') == "document") {
								$('#myModalLabelAttachment').html(
										globalattachDocument);
								$('#attachmentType').html(globalTypeDocument);
								$('#DocType').val("DOCUMENT");
							} else if ($(this).attr('id') == "ownResume") {
								$('#myModalLabelAttachment').html(
										globalattachResume);
								$('#attachmentType').html(globalTypeResume);
								$('#DocType').val("RESUME");
							}
							$('#fileTitle').val("");
							$('#idFile').val(0);
							$('#inputUploadFile').show();
							$('#modalAttachment').modal('show');
						});

				$('#submitAttachment').click(
						function() {
							errorContainer2.hide();
							var submitUpload = true;
							$('div.errorContainer2 li').remove();
							if ($("#inputUploadFile").val() == ""
									&& $('#service').val() == "addFile") {
								submitUpload = false;
								errorContainer2.find("ol").append(
										'<li><label class="has-error">'
												+ globalFileRequire
												+ '</label></li>');
							}
							if ($("#fileTitle").val().trim() == "") {
								submitUpload = false;
								errorContainer2.find("ol").append(
										'<li><label class="has-error">'
												+ globalTitleRequire
												+ '</label></li>');
							}
							if (submitUpload) {
								$('#uploadFile').submit();
							} else {
								errorContainer2.css({
									'display' : 'block'
								});
							}
						});

				//--------------------ajax upload-----------------
				var options = {
					complete : function(response) {
						errorContainer2.hide();
						var strJson = "" + response.responseText;
						strJson = strJson.replace("<PRE>", "");
						strJson = strJson.replace("</PRE>", "");
						var obj = jQuery.parseJSON(strJson);
						if (obj.success == 1) {
							if (obj.idFile > 0) {
								getAttachmentList($('#DocType').val());
								$('#modalAttachment').modal('hide');
							}
						} else {
							$('div.container li').remove();
							if (obj.urlError != "") {
								window.location.href = obj.urlError;
							} else {
								for (var i = 0; i < obj.errors.length; i++) {
									errorContainer2.find("ol").append(
											'<li><label class="has-error">'
													+ obj.errors[i]
													+ '</label></li>');
								}
								errorContainer2.css({
									'display' : 'block'
								});
								$('html, body').animate({
									scrollTop : '0px'
								}, 300);
							}
						}
					}
				};

				$('#uploadFile').ajaxForm(options);
				getAttachmentList("DOCUMENT");
				getAttachmentList("RESUME");
				//----------------------------show popup------------------------
				
				$('#swap').click(function() {
		
					$('#popupName').toggle();
					
					if($('#popupName').is(":visible"))
					{
						$('#arrow').attr('src','/images/arrow-03.png');
					}
					else
					{
						$('#arrow').attr('src','/images/arrow-04.png');
					}
					
				});
				
				$('#close').click(function(){
					$('#popup').hide();
				});
				$('#ok').click(function(){
					$('#popup').hide();
				});
				$('#popupName').hide();
				
			});
			

	function setEventDocumentClick() {
		$('a[class=documentLink]')
				.click(
						function() {
							$(
									'div[class="documentLayer"][id!="document_'
											+ this.id + '"]').slideUp('fast');
							$('img.documentLink').prop('src',
									'/images/arrow_down_green.png');
							if (!$('#document_' + this.id).is(":visible")) {
								$('#document_' + this.id).slideDown('fast');
							}
							$('#arrow_file_' + this.id)
									.prop(
											'src',
											$('#document_' + this.id).is(
													":visible") ? '/images/arrow_up_green.png'
													: '/images/arrow_down_green.png');
						});

		$('img.documentLink')
				.click(
						function() {
							var id = $(this).data('idfile');
							$(
									'div[class="documentLayer"][id!="document_'
											+ id + '"]').slideUp('fast');
							$('img.documentLink').prop('src',
									'/images/arrow_down_green.png');
							if (!$('#document_' + id).is(":visible")) {
								$('#document_' + id).slideDown('fast');
							}
							$('#arrow_file_' + id)
									.prop(
											'src',
											$('#document_' + id).is(":visible") ? '/images/arrow_up_green.png'
													: '/images/arrow_down_green.png');
						});

		$('a[class=ownResumeLink]')
				.click(
						function() {
							$(
									'div[class="ownResumeLayer"][id!="ownResume_'
											+ this.id + '"]').slideUp('fast');
							$('img.ownResumeLink').prop('src',
									'/images/arrow_down_green.png');
							if (!$('#ownResume_' + this.id).is(":visible")) {
								$('#ownResume_' + this.id).slideDown('fast');
							}
							$('#arrow_file_' + this.id)
									.prop(
											'src',
											$('#ownResume_' + this.id).is(
													":visible") ? '/images/arrow_up_green.png'
													: '/images/arrow_down_green.png');
						});

		$('img.ownResumeLink')
				.click(
						function() {
							var id = $(this).data('idfile');
							$(
									'div[class="ownResumeLayer"][id!="ownResume_'
											+ id + '"]').slideUp('fast');
							$('img.ownResumeLink').prop('src',
									'/images/arrow_down_green.png');
							if (!$('#ownResume_' + id).is(":visible")) {
								$('#ownResume_' + id).slideDown('fast');
							}
							$('#arrow_file_' + id)
									.prop(
											'src',
											$('#ownResume_' + id)
													.is(":visible") ? '/images/arrow_up_green.png'
													: '/images/arrow_down_green.png');
						});

		$('a[class=editDocument]').click(function() {
			var param = this.id.split("_");
			$('#fileTitle').val($("#" + param[1]).attr("title"));
			$('#idFile').val(param[1]);
			$('#DocType').val(param[0]);
			$('#service').val("updateTitle");
			$('#inputUploadFile').hide();
			$('#modalAttachment').modal('show');
		});

		$('a[class=deleteFile]').click(function() {
			idFileTemp = this.id;
			$('#modalDelete').modal('show');
		});
	}

	function deleteFile() {
		if (idFileTemp != "") {
			errorContainer.hide();
			var param = idFileTemp.split("_");
			var service = 'deleteFile';
			var request = '{"service":"' + service + '","idFile":"' + param[1]
					+ '"}';
			$.ajax({
				type : "POST",
				url : '/UploadPhotoServ',
				data : jQuery.parseJSON(request),
				async : false,
				success : function(data) {
					var obj = jQuery.parseJSON(data);
					if (obj.success == 1) {
						getAttachmentList(param[0]);
					} else {
						$('div.errorContainer li').remove();
						for (var i = 0; i < obj.errors.length; i++) {
							errorContainer.find("ol").append(
									'<li><label class="has-error">'
											+ obj.errors[i] + '</label></li>');
						}
						errorContainer.css({
							'display' : 'block'
						});
						$('html, body').animate({
							scrollTop : '0px'
						}, 300);
					}
					idFileTemp = "";
					$("#modalDelete").modal('hide');
				}
			});
		}
	}

	function deleteResume() {
		if (idResumeTemp != "") {
			window.location.href = "/NamingServ?service=DELETE&sequence=0&idResume="
					+ idResumeTemp;
		}
	}

	function getAttachmentList(docType) {
		errorContainer.hide();
		var service = "";
		var divContainer = "";
		var classLink = "";
		var classLayer = "";
		var prefixId = "";
		var totalSizeSpan = "";
		if (docType == "DOCUMENT") {
			service = 'getAllDocument';
			divContainer = 'documentListDiv';
			classLink = 'documentLink';
			classLayer = 'documentLayer';
			prefixId = 'document';
			totalSizeSpan = 'totalSizeDocument';
		} else if (docType == "RESUME") {
			service = 'getAllResume';
			divContainer = 'resumeListDiv';
			classLink = 'ownResumeLink';
			classLayer = 'ownResumeLayer';
			prefixId = 'ownResume';
			totalSizeSpan = 'totalSizeOwnResume';
		}
		var request = '{"service":"' + service + '"}';
		$
				.ajax({
					type : "POST",
					url : '/UploadPhotoServ',
					data : jQuery.parseJSON(request),
					async : false,
					success : function(data) {
						var obj = jQuery.parseJSON(data);
						if (obj.success == 1) {
							if (obj.attachmentList.length > 0) {
								var html = '<table style="border-collapse:collapse; width:100%;">';
								for (var i = 0; i < obj.attachmentList.length; i++) {
									html += '<tr>' + '<td class="digit">'
											+ (i + 1)
											+ '</td>'
											+ '<td valign="top">'
											+ '&nbsp;&nbsp;<a class="'+classLink+'" id="'+obj.attachmentList[i].idFile+'" title="'+obj.attachmentList[i].fileTitle+'">'
											+ obj.attachmentList[i].fileTitle
											+ '</a>'
											+ '<img data-idfile='+obj.attachmentList[i].idFile+' border="0"id="arrow_file_'+obj.attachmentList[i].idFile+'" class="'+classLink+' resumeArrow" src="/images/arrow_down_green.png">'
											+ '</td>'
											+ '</tr>'
											+ '<tr>'
											+ '<td></td>'
											+ '<td>'
											+ '<div class="'+classLayer+'" id="'+prefixId+'_'+obj.attachmentList[i].idFile+'" style="display:none;">'
											+ '<a href="/DownloadServ?idFile='
											+ obj.attachmentList[i].idFile
											+ '&Enc=<c:out value="${encView}"/>&Key=<c:out value="${keyView}"/>"><fmt:message key="GLOBAL_VIEW"/></a> | '
											+ '<a class="editDocument" href="javascript:void(0)" id="'
											+ docType
											+ '_'
											+ obj.attachmentList[i].idFile
											+ '"><fmt:message key="GLOBAL_EDIT"/></a> | '
											+ '<a class="deleteFile" href="javascript:void(0)" id="'
											+ docType
											+ '_'
											+ obj.attachmentList[i].idFile
											+ '"><fmt:message key="GLOBAL_DELETE"/></a>'
											+ '<br><br>' + '</div>' + '</td>'
											+ '</tr>'
											+ '<tr style="height:10px;">'
											+ '<td colspan="2"></td>' + '</tr>';
								}
								html += '</html>';
								$('#' + divContainer).html(html);
								$('#' + divContainer).show();
								setEventDocumentClick();
							} else {
								$('#' + divContainer).html("");
								$('#' + divContainer).hide();
							}
							var usedFile = obj.totalFileSize / (1024 * 1024);
							$('#' + totalSizeSpan).html(
									"<b>" + usedFile.toFixed(3)
											+ " of 5Mb.</b>");
						} else {
							$('div.errorContainer li').remove();
							for (var i = 0; i < obj.errors.length; i++) {
								errorContainer.find("ol").append(
										'<li><label class="has-error">'
												+ obj.errors[i]
												+ '</label></li>');
							}
							errorContainer.css({
								'display' : 'block'
							});
							$('html, body').animate({
								scrollTop : '0px'
							}, 300);
						}
					}
				});
	}

	function getPhoto() {
		errorContainer.hide();
		var service = 'getPhoto';
		var request = '{"service":"' + service + '"}';
		$
				.ajax({
					type : "POST",
					url : '/UploadPhotoServ',
					data : jQuery.parseJSON(request),
					async : false,
					success : function(data) {
						var obj = jQuery.parseJSON(data);
						if (obj.success == 1) {
							if (obj.urlPhoto != "") {
								$("#photo").attr("src", obj.urlPhoto);
								setImageSize();
								$('#imageDiv').css('background-color',
										'#EDEDED');
								$('#imageDiv').css('border',
										'2px solid #CCCCCC');
								$('#idFilePhoto').val(obj.idFile);
								$('#urlUploadPhoto')
										.html(
												"<b>+&nbsp;<fmt:message key='PHOTO_EDIT'/>&nbsp;</b>");
								$('.uploadPhotoHomeDelete').show();
								$(".uploadPhotoHome").css({
									top : '70px'
								});
							} else {
								$("#photo").attr("src",
										"/images/photoUpload.png");
								$('#idFilePhoto').val(0);
								$('#urlUploadPhoto')
										.html(
												"<b>+&nbsp;<fmt:message key='PHOTO_ADD'/>&nbsp;</b>");
								$(".uploadPhotoHome").css({
									top : '85px'
								});
								$('.uploadPhotoHomeDelete').hide();
							}
						} else {
							$('div.errorContainer li').remove();
							for (var i = 0; i < obj.errors.length; i++) {
								errorContainer.find("ol").append(
										'<li><label class="has-error">'
												+ obj.errors[i]
												+ '</label></li>');
							}
							errorContainer.css({
								'display' : 'block'
							});
							$('html, body').animate({
								scrollTop : '0px'
							}, 300);
						}
					}
				});
	}

	function setImageSize() {
		$(".imgLiquidNoFill").imgLiquid({
			fill : false,
			horizontalAlign : "center",
			verticalAlign : "50%"
		});
	}
</script>