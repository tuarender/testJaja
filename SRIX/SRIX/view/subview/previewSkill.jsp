<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.SkillLanguageScore"%>
<%@page import="com.topgun.resume.SkillOther"%>
<%@page import="com.topgun.resume.SkillLanguage"%>
<%@page import="com.topgun.resume.SkillComputer"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.SkillLanguageScoreManager"%>
<%@page import="com.topgun.resume.SkillOtherManager"%>
<%@page import="com.topgun.resume.SkillLanguageManager"%>
<%@page import="com.topgun.resume.SkillComputerManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.PropertiesManager"%>

<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	PropertiesManager propMgr=new PropertiesManager();
	Resume resume=new ResumeManager().get(idJsk, idResume);
	int nextidEdu=0;
	String ln;
	String ct;
	List<SkillComputer> coms= null;
	List<SkillLanguage> langs=null;
	List<SkillOther> others=null;
	List<SkillLanguageScore> langugeScore= null;
	float toeflReading=0;
	float toeflListening=0;
	float toeflWriting=0;
	float toeflSpeaking=0;
	float ieltsListening=0;
	float ieltsWriting=0;
	float ieltsSpeaking=0;
	float ieltsReading=0;
	float toeicListening=0;
	float toeicWriting=0;
	float toeicSpeaking=0;
	float toeicReading=0;
	int templateCountry=0;
	SkillLanguageScore toefl=null;
	String languageName="";
	SkillLanguageScore ielts=null;
	SkillLanguageScore toeic=null;
	if(resume!=null)
	{
		ln=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		ct=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		coms=new SkillComputerManager().getAll(resume.getIdJsk(),resume.getIdResume());
		langs=new SkillLanguageManager().getAll(resume.getIdJsk(),resume.getIdResume());
		others=new SkillOtherManager().getAll(resume.getIdJsk(),resume.getIdResume());
		langugeScore= new SkillLanguageScoreManager().getAll(idJsk, idResume);
		toefl=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"TOEFL");
		if(toefl!=null)
		{
			toeflReading=toefl.getReading();
			toeflListening= toefl.getListening();
			toeflWriting= toefl.getWriting();
			toeflSpeaking= toefl.getSpeaking();
		}
		ielts=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"IELTS");
		if(ielts!=null)
		{
			ieltsReading=ielts.getReading();
			ieltsListening= ielts.getListening();
			ieltsWriting= ielts.getWriting();
			ieltsSpeaking= ielts.getSpeaking();
		}
		toeic=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"TOEIC");
		if(toeic!=null)
		{
			toeicReading=toeic.getReading();
			toeicListening= toeic.getListening();
			toeicWriting= toeic.getWriting();
			toeicSpeaking= toeic.getSpeaking();
		}
		templateCountry= resume.getTemplateIdCountry();
		languageName= propMgr.getMessage(resume.getLocale(), "GLOBAL_LANGUAGE");
	}
	
	request.setAttribute("resume", resume);
	request.setAttribute("langSize", langs.size());
	request.setAttribute("langs", langs);
	request.setAttribute("toeflReading", toeflReading);
	request.setAttribute("toeflListening", toeflListening);
	request.setAttribute("toeflWriting", toeflWriting);
	request.setAttribute("toeflSpeaking", toeflSpeaking);
	request.setAttribute("ieltsReading", ieltsReading);
	request.setAttribute("ieltsListening", ieltsListening);
	request.setAttribute("ieltsWriting", ieltsWriting);
	request.setAttribute("ieltsSpeaking", ieltsSpeaking);
	request.setAttribute("toeicReading", toeicReading);
	request.setAttribute("toeicListening", toeicListening);
	request.setAttribute("toeicWriting", toeicWriting);
	request.setAttribute("toeicSpeaking", toeicSpeaking);
	request.setAttribute("templateCountry", templateCountry);
	request.setAttribute("languageName", languageName);
	request.setAttribute("reading", propMgr.getMessage(resume.getLocale(), "GLOBAL_LANGUAGE_READING"));
	request.setAttribute("listening", propMgr.getMessage(resume.getLocale(), "GLOBAL_LANGUAGE_LISTENING"));
	request.setAttribute("writing", propMgr.getMessage(resume.getLocale(), "GLOBAL_LANGUAGE_WRITING"));
	request.setAttribute("speaking", propMgr.getMessage(resume.getLocale(), "GLOBAL_LANGUAGE_SPEAKING"));
	request.setAttribute("computer", propMgr.getMessage(resume.getLocale(), "GLOBAL_COMPUTER"));
	request.setAttribute("other", propMgr.getMessage(resume.getLocale(), "SKILLS_OTHER"));
	request.setAttribute("langugeScore", langugeScore);
	request.setAttribute("idResume", idResume);
	request.setAttribute("comSize", coms.size());
	request.setAttribute("otherSize", others.size());
	request.setAttribute("coms", coms);
	request.setAttribute("others", others);
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<div class="row" style="padding:10pt;">
	<div class="col-xs-12 col-sm-12">
		<c:if test="${not empty resume}">
			<!-- Language -->
			<c:choose>
				<c:when test="${ langSize>0 || (toeflReading>0 || toeflListening>0 || toeflWriting>0 || toeflSpeaking>0) || (ieltsReading>0 || ieltsListening>0 || ieltsWriting>0 || ieltsSpeaking>0) || (toeicReading>0 || toeicListening>0 || toeicWriting>0 || toeicSpeaking>0)}">
					<c:choose>
						<c:when test="${templateCountry == 216 || templateCountry==151 }"><!-- Thailand & Myanma -->
							<c:if test="${ langSize>0 }">
								<b class="caption_bold"><c:out value="${languageName}" /></b><br>
								<%int i=0; %>
								<c:forEach var="language" items="${langs}" varStatus="loop">
									<c:choose>
										<c:when test="${language.idSkillLang >0}">
											<b class="caption_bold"><%=MasterDataManager.getLanguageName(langs.get(i).getIdSkillLang(), resume.getIdLanguage())%></b>
										</c:when>
										<c:otherwise>
											<b class="caption_bold"><c:out value="${language.othLang}"></c:out></b>
										</c:otherwise>
									</c:choose>
									<div class='row'>
										<div class='col-sm-4 col-xs-4 caption'>
											&nbsp;&nbsp;<c:out value="${reading}" />
										</div>
										<c:if test="${language.reading !=0}">
											<div class='col-sm-8 col-xs-8 caption' align="left">
												:&nbsp;<font class='answer'><%=propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyLanguage(langs.get(i).getReading()))%></font>
											</div>
										</c:if>
									</div>
									<div class='row'>
										<div class='col-sm-4 col-xs-4 caption'>
											&nbsp;&nbsp;<c:out value="${listening}" />
										</div>
										<c:if test="${language.reading !=0}">
											<div class='col-sm-8 col-xs-8 caption' align="left">
												:&nbsp;<font class='answer'><%=propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyLanguage(langs.get(i).getListening()))%></font>
											</div>
										</c:if>
									</div>
									<div class='row'>
										<div class='col-sm-4 col-xs-4 caption'>
											&nbsp;&nbsp;<c:out value="${writing}" />
										</div>
										<c:if test="${language.reading !=0}">
											<div class='col-sm-8 col-xs-8 caption' align="left">
												:&nbsp;<font class='answer'><%=propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyLanguage(langs.get(i).getWriting()))%></font>
											</div>
										</c:if>
									</div>
									<div class='row'>
										<div class='col-sm-4 col-xs-4 caption'>
											&nbsp;&nbsp;<c:out value="${speaking}" />
										</div>
										<c:if test="${language.reading !=0}">
											<div class='col-sm-8 col-xs-8 caption' align="left">
												:&nbsp;<font class='answer'><%=propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyLanguage(langs.get(i).getSpeaking()))%></font>
											</div>
										</c:if>
									</div>
								<%i++;%>
								</c:forEach>
							</c:if>
						</c:when>
						<c:otherwise> <!-- other country -->
							<c:if test="${ langSize>0 }">
								<%int i=0; %>
								<c:forEach var="language" items="${langs}" varStatus="loop">
									<c:choose>
										<c:when test="${language.idSkillLang >0}">
											<b class="caption_bold"><%=MasterDataManager.getLanguageName(langs.get(i).getIdSkillLang(), resume.getIdLanguage())%></b>
										</c:when>
										<c:otherwise>
											<b class="caption_bold"><c:out value="${language.othLang}"></c:out></b>
										</c:otherwise>
									</c:choose>
									<c:if test="${language.levelSkill }">
										<%=propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyLanguage(langs.get(i).getLevelSkill()))%>
									</c:if>
								<%i++;%>
								</c:forEach>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:if test="${toeflReading!=0 || toeflListening!=0 || toeflWriting!=0 || toeflSpeaking!=0}">
						<div class="row">
							<div class='col-sm-4 col-xs-4' ><b class="caption_bold"><%=toefl.getExamType()%></b></div>
							<div class='col-sm-8 col-xs-8 answer'></div>
						</div>
						<c:if test="${toeflReading > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${reading}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeflReading}" /></div>
							</div>
						</c:if>
						<c:if test="${toeflListening > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${listening}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeflListening}" /></div>
							</div>
						</c:if>
						<c:if test="${toeflWriting > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${writing}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeflWriting}" /></div>
							</div>
						</c:if>
						<c:if test="${toeflSpeaking > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${speaking}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeflSpeaking}" /></div>
							</div>
						</c:if>
					</c:if>
					<c:if test="${ieltsReading!=0 || ieltsListening!=0 || ieltsWriting!=0 || ieltsSpeaking!=0}">
						<div class="row">
							<div class='col-sm-4 col-xs-4' ><b class="caption_bold"><%=ielts.getExamType()%></b></div>
							<div class='col-sm-8 col-xs-8 answer'></div>
						</div>
						<c:if test="${ieltsReading > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${reading}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${ieltsReading}" /></div>
							</div>
						</c:if>
						<c:if test="${ieltsListening > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${listening}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${ieltsListening}" /></div>
							</div>
						</c:if>
						<c:if test="${ieltsWriting  > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${writing}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${ieltsWriting}" /></div>
							</div>
						</c:if>
						<c:if test="${ieltsSpeaking > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${speaking}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${ieltsSpeaking}" /></div>
							</div>
						</c:if>
					</c:if>
					<c:if test="${toeicReading!=0 || toeicListening!=0 || toeicWriting!=0 || toeicSpeaking!=0}">
						<div class="row">
							<div class='col-sm-4 col-xs-4' ><b class="caption_bold"><%=toeic.getExamType()%></b></div>
							<div class='col-sm-8 col-xs-8 answer'></div>
						</div>
						<c:if test="${toeicReading > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${reading}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeicReading}" /></div>
							</div>
						</c:if>
						<c:if test="${toeicListening > 0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${listening}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeicListening}" /></div>
							</div>
						</c:if>
						<c:if test="${toeicWriting >0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${writing}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeicWriting}" /></div>
							</div>
						</c:if>
						<c:if test="${toeicSpeaking >0}">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${speaking}" /></div>
								<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><c:out value="${toeicSpeaking}" /></div>
							</div>
						</c:if>
					</c:if>
					<c:if test="${not empty langugeScore}">
						<%int i=0; %>
						<c:forEach var="score" items="${langugeScore}" varStatus="loop">
							<c:if test="${score.examType ne 'TOEFL' && score.examType ne 'IELTS' && score.examType ne 'TOEIC' }">
								<div class="row">
								<div class='col-sm-4 col-xs-4' ><b class="caption_bold"><%=langugeScore.get(i).getExamType()%></b></div>
								<div class='col-sm-8 col-xs-8 answer'></div>
							</div>
							<c:if test="${score.reading > 0}">
								<div class="row">
									<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${reading}" /></div>
									<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><%=langugeScore.get(i).getReading()%></div>
								</div>
							</c:if>
							<c:if test="${score.listening > 0}">
								<div class="row">
									<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${listening}" /></div>
									<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><%=langugeScore.get(i).getListening()%></div>
								</div>
							</c:if>
							<c:if test="${score.writing > 0}">
								<div class="row">
									<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${writing}" /></div>
									<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><%=langugeScore.get(i).getWriting()%></div>
								</div>
							</c:if>
							<c:if test="${score.speaking > 0}">
								<div class="row">
									<div class='col-sm-4 col-xs-4 caption'>&nbsp;&nbsp;<c:out value="${speaking}" /></div>
									<div class='col-sm-4 col-xs-4 answer'><font class="caption">:</font><%=langugeScore.get(i).getSpeaking()%></div>
								</div>
							</c:if>
							</c:if>
							<%i++;%>
						</c:forEach>
					</c:if>
					<div style="text-align:right;">
						<a href="/SRIX?view=skillLanguage&sequence=0&idResume=<c:out value="${idResume}"/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
					</div>	
				</c:when>
				<c:otherwise>
					<div class='caption_bold' align="center"><%=propMgr.getMessage(resume.getLocale(), "LANGUAGE_YOUR_SKILL")%><br>
					<a href="/SRIX?view=skillLanguage&sequence=0&idResume=<c:out value="${idResume}"/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a></div>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${comSize>0 || otherSize>0}">
					<c:if test="${comSize>0}">
						<b class="caption_bold"><c:out value="${computer}" /></b><br>
						<%int i=0;%>
						<c:forEach var="com" items="${coms}" varStatus="loop">
							<%
								com.topgun.shris.masterdata.Computer aCom=MasterDataManager.getComputer(coms.get(i).getIdGroup(), coms.get(i).getIdComputer()); 
								request.setAttribute("aCom", aCom);
							%>
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>
								<c:choose>
								<c:when test="${not empty aCom}">
									<%=aCom.getComputerName()%>
								</c:when>
								<c:otherwise>
									<%=coms.get(i).getOthComputer()%>
								</c:otherwise>
								</c:choose>
								</div>
								<div class='col-sm-8 col-xs-8 answer'><font class="caption">:</font>
									<%
										if(coms.get(i).getLevelSkill() != 0)
										{
											out.print(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(coms.get(i).getLevelSkill())));
										} 
									%>
								</div>
							</div>
							<%i++;%>
						</c:forEach>
					</c:if>
					<c:if test="${otherSize>0}">
						<b class="caption_bold"><c:out value="${other}" /></b>
						<%int i=0;%>
						<c:forEach var="otherLoop" items="${others}" varStatus="loop">
							<div class="row">
								<div class='col-sm-4 col-xs-4 caption'>
									<%=others.get(i).getSkillName()%>
								</div>
								<div class='col-sm-8 col-xs-8 answer'><font class="caption">:</font>
									<%=propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(others.get(i).getLevelSkill()))%>
								</div>
							</div>
							<%i++;%>
						</c:forEach>
					</c:if>
					<div style="text-align:right;">
						<a href="/SRIX?view=skillComputer&sequence=0&idResume=<c:out value="${idResume}"/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
					</div>	
				</c:when>
				<c:otherwise>
					<div class='caption_bold' align="center"><%=propMgr.getMessage(resume.getLocale(), "SKILLS_COMPUTER")%><br>
					<a href="/SRIX?view=skillComputer&sequence=0&idResume=<c:out value="${idResume}"/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a></div>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</div>