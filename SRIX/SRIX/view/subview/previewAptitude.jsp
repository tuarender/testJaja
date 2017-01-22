<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page import="com.topgun.resume.TravelManager"%>
<%@page import="com.topgun.resume.PetManager"%>
<%@page import="com.topgun.resume.Travel"%>
<%@page import="com.topgun.resume.Pet"%>
<%@page import="com.topgun.resume.ReadingManager"%>
<%@page import="com.topgun.resume.Reading"%>
<%@page import="com.topgun.resume.HobbyManager"%>
<%@page import="com.topgun.resume.Hobby"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.*"%>

<%
	PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{
		List<Hobby> hobbyRanked=new HobbyManager().getAllRank(resume.getIdJsk(),resume.getIdResume());
		List<Hobby> hobbyListening=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 1);
		List<Hobby> hobbyThaiIns= null;
	    if(resume.getTemplateIdCountry() == 110) // Japanese
	    {
	    	hobbyThaiIns=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 11);
	    }
	    else 
	    {
	    	hobbyThaiIns=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 2);
	    }
	    List<Hobby> hobbyStrings=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 3);
	    List<Hobby> hobbyPercussion=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 4);
	    List<Hobby> hobbyWoodwinds=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 5);
	    List<Hobby> hobbyBrass=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 6);
	    List<Hobby> hobbyKeyboards=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 7);
	    List<Hobby> hobbySports=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 8);
	    List<Hobby> hobbyOutdoor=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 9);
	    List<Hobby> hobbyOther=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 10);
	    List<Reading> readBook=new ReadingManager().getAll(resume.getIdJsk(),resume.getIdResume());
	    List<Pet> jskPet=new PetManager().getAll(resume.getIdJsk(),resume.getIdResume());
	    List<Travel> jskTravel=TravelManager.getAll(resume.getIdJsk(),resume.getIdResume());
	    request.setAttribute("resume",resume);
		request.setAttribute("idJsk",idJsk);
		request.setAttribute("idResume",idResume);
		request.setAttribute("hobbyRanked",hobbyRanked);
		request.setAttribute("hobbyListening",hobbyListening);
		request.setAttribute("hobbyThaiIns",hobbyThaiIns);
		request.setAttribute("hobbyStrings",hobbyStrings);
		request.setAttribute("hobbyPercussion",hobbyPercussion);
		request.setAttribute("hobbyWoodwinds",hobbyWoodwinds);
		request.setAttribute("hobbyBrass",hobbyBrass);
		request.setAttribute("hobbyKeyboards",hobbyKeyboards);
		request.setAttribute("hobbySports",hobbySports);
		request.setAttribute("hobbyOutdoor",hobbyOutdoor);
		request.setAttribute("hobbyOther",hobbyOther);
		request.setAttribute("readBook",readBook);
		request.setAttribute("jskPet",jskPet);
		request.setAttribute("jskTravel",jskTravel);
		request.setAttribute("hobbySports",hobbySports);
		request.setAttribute("hobbyOutdoor",hobbyOutdoor);
		request.setAttribute("hobbyOther",hobbyOther);
		request.setAttribute("readBook",readBook);
		request.setAttribute("jskPet",jskPet);
		request.setAttribute("jskTravel",jskTravel);
		request.setAttribute("hobbyRankedSize",hobbyRanked.size());
		request.setAttribute("hobbyListeningSize",hobbyListening.size());
		request.setAttribute("hobbyThaiInsSize",hobbyThaiIns.size());
		request.setAttribute("hobbyStringsSize",hobbyStrings.size());
		request.setAttribute("hobbyPercussionSize",hobbyPercussion.size());
		request.setAttribute("hobbyWoodwindsSize",hobbyWoodwinds.size());
		request.setAttribute("hobbyBrassSize",hobbyBrass.size());
		request.setAttribute("hobbyKeyboardsSize",hobbyKeyboards.size());
		request.setAttribute("hobbySportsSize",hobbySports.size());
		request.setAttribute("hobbyOutdoorSize",hobbyOutdoor.size());
		request.setAttribute("hobbyOtherSize",hobbyOther.size());
		request.setAttribute("readBookSize",readBook.size());
		request.setAttribute("jskPetSize",jskPet.size());
		request.setAttribute("jskTravelSize",jskTravel.size());
	}
%>

<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
	<c:if test="${hobbyListeningSize gt 0 || hobbyThaiInsSize gt 0 || hobbyStringsSize gt 0 || hobbyPercussionSize gt 0 || hobbyWoodwindsSize gt 0 || hobbyBrassSize gt 0 || hobbyKeyboardsSize gt 0}">
		<div class="row" style="padding:10pt;">
			<div class="col-xs-12 col-sm-12 caption">
				<label for="aptitudeMusic"><fmt:message key="APTITUDE_MUSIC"/></label>
			</div>
		<!-- Listening -->
			<c:if test="${hobbyListeningSize gt 0}">
					<div class="col-xs-2 col-sm-2">
						<fmt:message key="APTITUDE_LISTENING"/>:
					</div>
					<div class="col-xs-10 col-sm-10 answer">
						<c:forEach items="${hobbyListening}" var="hobbyListening" varStatus="loop">
							<c:if test="${not empty hobbyListening}">
								<c:set var="listening" scope="request" value="${hobbyListening}"/>
								<% 
									Hobby listen = (Hobby)request.getAttribute("listening");
									com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(listen.getIdHobby(), resume.getIdLanguage());
									request.setAttribute("listenName",hobby!=null?hobby.getHobbyName():"");
								%>
									<c:choose>
										<c:when test="${not empty listenName}">
											<c:choose>
												<c:when test="${loop.index eq 0}">
													<c:out value="${listenName}" />
												</c:when>
												<c:otherwise>
													,<c:out value="${listenName}" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${loop.index eq 0}">
													<c:out value="${hobbyListening.othHobby}" />
												</c:when>
												<c:otherwise>
													,<c:out value="${hobbyListening.othHobby}" />
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
							</c:if>
						</c:forEach>
					</div>
			</c:if>
		<!-- Listening -->
		
		<!-- PLAYING -->
			<c:set var="flag" scope="request" value="0"/>	
			<c:if test="${hobbyThaiInsSize gt 0 || hobbyStringsSize gt 0 || hobbyPercussionSize gt 0 || hobbyWoodwindsSize gt 0 || hobbyBrassSize gt 0 || hobbyKeyboardsSize gt 0}">
			 	<div class="col-xs-2 col-sm-2">
					<fmt:message key="APTITUDE_PLAYING"/>:
				</div>
			<!-- ThaiIns -->
				<c:if test="${hobbyThaiInsSize gt 0}">
					<c:set var="flag" scope="request" value="1"/>	
					<c:forEach items="${hobbyThaiIns}" var="hobbyThaiIns" varStatus="loop">
						<c:if test="${not empty hobbyThaiIns}">
							<c:set var="thaiIns" scope="request" value="${hobbyThaiIns}"/>	
							<% 
								Hobby inst = (Hobby)request.getAttribute("thaiIns");
								com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(inst.getIdHobby(), resume.getIdLanguage());
								request.setAttribute("insName",hobby!=null?hobby.getHobbyName():"");
								com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(inst.getSkill(), resume.getIdLanguage());
								if(skill!=null){
									skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
								}
								request.setAttribute("skill",skill!=null?skill.getName():"");
								//String skill=MasterDataManager.proficiencyComAndSkill(inst.getSkill());
								//request.setAttribute("skill",skill);
							%>
							<c:if test="${loop.index eq 0}">
								<div class="col-xs-10 col-sm-10">
									<c:choose>
										<c:when test="${resume.templateIdCountry eq 110}">
											<u><fmt:message key="APTITUDE_JAPAN_INSTUMENTS"/></u>
										</c:when>
										<c:otherwise>
											<u><fmt:message key="APTITUDE_THAI_INSTRUMENTS"/></u>
										</c:otherwise>
									</c:choose>
								</div>
							</c:if>
							
							<c:choose>
								<c:when test="${not empty insName}">
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${insName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${hobbyThaiIns.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>	
				</c:if>
			<!-- //ThaiIns -->
			
			<!-- Strings -->
				<c:if test="${hobbyStringsSize gt 0}">
					<c:forEach items="${hobbyStrings}" var="hobbyStrings" varStatus="loop">
						<c:if test="${not empty hobbyStrings}">
							<c:set var="strings" scope="request" value="${hobbyStrings}"/>	
							<% 
								Hobby str = (Hobby)request.getAttribute("strings");
								com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(str.getIdHobby(), resume.getIdLanguage());
								request.setAttribute("stringsName",hobby!=null?hobby.getHobbyName():"");
								com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(str.getSkill(), resume.getIdLanguage());
								if(skill!=null){
									skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
								}
								request.setAttribute("skill",skill!=null?skill.getName():"");
							%>
							<c:if test="${loop.index eq 0}">
								<c:choose>
									<c:when test="${flag eq 0}">
										<c:set var="flag" scope="request" value="1"/>	
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_STRINGS"/></u>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-xs-2 col-sm-2"></div>
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_STRINGS"/></u>
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:choose>
								<c:when test="${not empty stringsName}">
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${stringsName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${hobbyStrings.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:if>
			<!-- //Strings -->
				
			<!-- Percussion -->
				<c:if test="${hobbyPercussionSize gt 0}">
					<c:forEach items="${hobbyPercussion}" var="hobbyPercussion" varStatus="loop">
						<c:if test="${not empty hobbyPercussion}">
							<c:set var="percussion" scope="request" value="${hobbyPercussion}"/>	
							<% 
								Hobby perc = (Hobby)request.getAttribute("percussion");
								com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(perc.getIdHobby(), resume.getIdLanguage());
								request.setAttribute("percussionName",hobby!=null?hobby.getHobbyName():"");
								com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(perc.getSkill(), resume.getIdLanguage());
								if(skill!=null){
									skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
								}
								request.setAttribute("skill",skill!=null?skill.getName():"");
							%>
							<c:if test="${loop.index eq 0}">
								<c:choose>
									<c:when test="${flag eq 0}">
										<c:set var="flag" scope="request" value="1"/>	
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_PERCUSSION"/></u>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-xs-2 col-sm-2"></div>
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_PERCUSSION"/></u>
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:choose>
								<c:when test="${not empty percussionName}">
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${percussionName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${hobbyPercussion.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:if>
			<!-- //Percussion -->
			
			<!-- Woodwinds -->
				<c:if test="${hobbyWoodwindsSize gt 0}">
					<c:forEach items="${hobbyWoodwinds}" var="hobbyWoodwinds" varStatus="loop">
						<c:if test="${not empty hobbyWoodwinds}">
							<c:set var="woodwinds" scope="request" value="${hobbyWoodwinds}"/>	
							<% 
								Hobby wood = (Hobby)request.getAttribute("woodwinds");
								com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(wood.getIdHobby(), resume.getIdLanguage());
								request.setAttribute("woodwindsName",hobby!=null?hobby.getHobbyName():"");
								com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(wood.getSkill(), resume.getIdLanguage());
								if(skill!=null){
									skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
								}
								request.setAttribute("skill",skill!=null?skill.getName():"");
							%>
							<c:if test="${loop.index eq 0}">
								<c:choose>
									<c:when test="${flag eq 0}">
										<c:set var="flag" scope="request" value="1"/>	
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_WOODWINDS"/></u>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-xs-2 col-sm-2"></div>
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_WOODWINDS"/></u>
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:choose>
								<c:when test="${not empty woodwindsName}">
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${woodwindsName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${hobbyWoodwinds.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:if>
			<!-- //Woodwinds -->
			
			<!-- Brass -->
				<c:if test="${hobbyBrassSize gt 0}">
					<c:forEach items="${hobbyBrass}" var="hobbyBrass" varStatus="loop">
						<c:if test="${not empty hobbyBrass}">
							<c:set var="brass" scope="request" value="${hobbyBrass}"/>	
							<% 
								Hobby br = (Hobby)request.getAttribute("brass");
								com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(br.getIdHobby(), resume.getIdLanguage());
								request.setAttribute("brassName",hobby!=null?hobby.getHobbyName():"");
								com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(br.getSkill(), resume.getIdLanguage());
								if(skill!=null){
									skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
								}
								request.setAttribute("skill",skill!=null?skill.getName():"");
							%>
							<c:if test="${loop.index eq 0}">
								<c:choose>
									<c:when test="${flag eq 0}">
										<c:set var="flag" scope="request" value="1"/>	
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_BRASS"/></u>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-xs-2 col-sm-2"></div>
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_BRASS"/></u>
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:choose>
								<c:when test="${not empty brassName}">
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${brassName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${hobbyBrass.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:if>
			<!-- //Brass -->
			
			<!-- Keyboards -->
				<c:if test="${hobbyKeyboardsSize gt 0}">
					<c:forEach items="${hobbyKeyboards}" var="hobbyKeyboards" varStatus="loop">
						<c:if test="${not empty hobbyBrass}">
							<c:set var="keyboards" scope="request" value="${hobbyKeyboards}"/>	
							<% 
								Hobby kb = (Hobby)request.getAttribute("keyboards");
								com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(kb.getIdHobby(), resume.getIdLanguage());
								request.setAttribute("keyboardsName",hobby!=null?hobby.getHobbyName():"");
								com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(kb.getSkill(), resume.getIdLanguage());
								if(skill!=null){
									skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
								}
								request.setAttribute("skill",skill!=null?skill.getName():"");
							%>
							<c:if test="${loop.index eq 0}">
								<c:choose>
									<c:when test="${flag eq 0}">
										<c:set var="flag" scope="request" value="1"/>	
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_KEYBOARDS"/></u>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-xs-2 col-sm-2"></div>
										<div class="col-xs-10 col-sm-10">
											<u><fmt:message key="APTITUDE_KEYBOARDS"/></u>
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:choose>
								<c:when test="${not empty keyboardsName}">
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${keyboardsName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer"><c:out value='${hobbyKeyboards.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:if>
			<!-- //Keyboards -->
			</c:if>
		<!-- PLAYING -->
		</div>
	</c:if>
	
	<!-- SPORTS -->
	<c:if test="${hobbySportsSize gt 0}">
		<div class="row" style="padding:10pt;">	
			<div class="col-xs-2 col-sm-2 caption">
				<label for="aptitudeSport"><fmt:message key="APTITUDE_SPORTS"/>:</label>
			</div>
			<c:forEach items="${hobbySports}" var="hobbySports" varStatus="loop">
				<c:if test="${not empty hobbySports}">
					<c:set var="sport" scope="request" value="${hobbySports}"/>	
					<% 
						Hobby sp = (Hobby)request.getAttribute("sport");
						com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(sp.getIdHobby(), resume.getIdLanguage());
						request.setAttribute("sportsName",hobby!=null?hobby.getHobbyName():"");
						com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(sp.getSkill(), resume.getIdLanguage());
						if(skill!=null){
							skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
						}
						request.setAttribute("skill",skill!=null?skill.getName():"");
					%>
					<c:choose>
						<c:when test="${loop.index eq 0}">
							<c:choose>
								<c:when test="${not empty sportsName}">
									<div class="col-xs-10 col-sm-10 answer">
										<c:out value='${sportsName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-10 col-sm-10 answer">
										<c:out value='${hobbySports.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${not empty sportsName}">
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer">
										<c:out value='${sportsName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-2 col-sm-2"></div>
									<div class="col-xs-10 col-sm-10 answer">
										<c:out value='${hobbySports.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
		</div>
	</c:if>
	<!-- //SPORTS -->
	
	<!-- OUTDOOR -->
	<c:if test="${hobbyOutdoorSize gt 0}">
		<div class="row" style="padding:10pt;">	
			<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3 caption">
				<label for="aptitudeOutdoor"><fmt:message key="APTITUDE_OUTDOOR_ADVENTURE"/>:</label>
			</div>
			<c:forEach items="${hobbyOutdoor}" var="hobbyOutdoor" varStatus="loop">
				<c:if test="${not empty hobbyOutdoor}">
					<c:set var="outdoor" scope="request" value="${hobbyOutdoor}"/>	
					<% 
						Hobby outd = (Hobby)request.getAttribute("outdoor");
						com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(outd.getIdHobby(), resume.getIdLanguage());
						request.setAttribute("outdoorName",hobby!=null?hobby.getHobbyName():"");
						com.topgun.shris.masterdata.SkillLevel skill=MasterDataManager.getSkillLevel(outd.getSkill(), resume.getIdLanguage());
						if(skill!=null){
							skill.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skill.getId())));
						}
						request.setAttribute("skill",skill!=null?skill.getName():"");
					%>
					<c:choose>
						<c:when test="${loop.index eq 0}">
							<c:choose>
								<c:when test="${not empty outdoorName}">
									<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
										<c:out value='${outdoorName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
										<c:out value='${hobbyOutdoor.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${not empty sportsName}">
									<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3"></div>
									<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
										<c:out value='${outdoorName}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-xs-4 col-sm-4 col-md-3 col-lg-3"></div>
									<div class="col-xs-8 col-sm-8 col-md-9 col-lg-9 answer">
										<c:out value='${hobbyOutdoor.othHobby}'/>
										<c:if test="${not empty skill}">
											- <c:out value='${skill}'/>
										</c:if>
									</div>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
		</div>
	</c:if>
	<!-- //OUTDOOR -->
	
	<!-- OTHER_HOBBIES -->
	<c:if test="${hobbyOtherSize gt 0}">
		<div class="row" style="padding:10pt;">	
			<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3 caption">
				<label for="aptitudeOtherHobbies"><fmt:message key="APTITUDE_OTHER_HOBBIES"/>:</label>
			</div>
			
			<c:forEach items="${hobbyOther}" var="hobbyOther" varStatus="loop"><!-- for ใหญ่ -->
				<c:if test="${not empty hobbyOther}">
					<c:set var="hobbyOther" scope="request" value="${hobbyOther}"/>	
					<%
						/*--oth hobboes--*/
						Hobby oth = (Hobby)request.getAttribute("hobbyOther");
						com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(oth.getIdHobby(), resume.getIdLanguage());
						request.setAttribute("othName",hobby!=null?hobby.getHobbyName():"");
						com.topgun.shris.masterdata.SkillLevel skillOth=MasterDataManager.getSkillLevel(oth.getSkill(), resume.getIdLanguage());
						if(skillOth!=null){
							skillOth.setName(propMgr.getMessage(resume.getLocale(),MasterDataManager.proficiencyComAndSkill(skillOth.getId())));
						}
						request.setAttribute("skillOth",skillOth!=null?skillOth.getName():"");
					%>
					<c:choose>
						<c:when test="${hobbyOther.idHobby eq 45}"><!-- Peading -->
							<c:choose>
								<c:when test="${loop.index eq 0}">
									<c:if test="${not empty othName}">
										<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
											<c:out value="${othName}"/>
											<c:if test="${readBookSize gt 0}">
												<c:forEach items="${readBook}" var="readBook" varStatus="loopread">
													<c:if test="${not empty readBook}">
														<c:set var="reading" scope="request" value="${readBook}"/>	
														<%
															/*--reading--*/
															Reading read = (Reading)request.getAttribute("reading");
															com.topgun.shris.masterdata.ReadingBook aBook=MasterDataManager.getReadingBook(read.getIdBook(),  resume.getIdLanguage());
															request.setAttribute("bookName",aBook!=null?aBook.getBookName():"");
														%>
														<c:choose>
															<c:when test="${loopread.index eq 0}">
																<c:choose>
																	<c:when test="${not empty bookName}">
																		- <c:out value="${bookName}"/>
																	</c:when>
																	<c:otherwise>
																		- <c:out value="${readBook.otherBook}"/>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${not empty bookName}">
																		,<c:out value="${bookName}"/>
																	</c:when>
																	<c:otherwise>
																		,<c:out value="${readBook.otherBook}"/>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
											</c:if>
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty othName}">
										<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3"></div>
										<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
											<c:out value="${othName}"/>
											<c:if test="${readBookSize gt 0}">
												<c:forEach items="${readBook}" var="readBook" varStatus="loopread">
													<c:if test="${not empty readBook}">
														<c:set var="reading" scope="request" value="${readBook}"/>	
														<%
															/*--reading--*/
															Reading read = (Reading)request.getAttribute("reading");
															com.topgun.shris.masterdata.ReadingBook aBook=MasterDataManager.getReadingBook(read.getIdBook(),  resume.getIdLanguage());
															request.setAttribute("bookName",aBook!=null?aBook.getBookName():"");
														%>
														<c:choose>
															<c:when test="${loopread.index eq 0}">
																<c:choose>
																	<c:when test="${not empty bookName}">
																		- <c:out value="${bookName}"/>
																	</c:when>
																	<c:otherwise>
																		- <c:out value="${readBook.otherBook}"/>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${not empty bookName}">
																		,<c:out value="${bookName}"/>
																	</c:when>
																	<c:otherwise>
																		,<c:out value="${readBook.otherBook}"/>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
											</c:if>
										</div>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${hobbyOther.idHobby eq 56}"><!-- Pet -->
							<c:choose>
								<c:when test="${loop.index eq 0}">
									<c:if test="${not empty othName}">
										<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
											<c:out value="${othName}"/>
											<c:if test="${jskPetSize gt 0}">
												<c:forEach items="${jskPet}" var="jskPet" varStatus="looppet">
													<c:if test="${not empty jskPet}">
														<c:set var="pet" scope="request" value="${jskPet}"/>	
														<%
															/*--pet--*/
															Pet pet = (Pet)request.getAttribute("pet");
															com.topgun.shris.masterdata.Pet aPet=MasterDataManager.getPet(pet.getIdPet(),  resume.getIdLanguage());
															request.setAttribute("petName",aPet!=null?aPet.getPetName():"");
														%>
														<c:choose>
															<c:when test="${looppet.index eq 0}">
																<c:choose>
																	<c:when test="${not empty petName}">
																		- <c:out value="${petName}"/>
																	</c:when>
																	<c:otherwise>
																		- <c:out value="${jskPet.otherPet}"/>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${not empty petName}">
																		,<c:out value="${petName}"/>
																	</c:when>
																	<c:otherwise>
																		,<c:out value="${jskPet.otherPet}"/>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
											</c:if>
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty othName}">
										<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3"></div>
										<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
											<c:out value="${othName}"/>
											<c:if test="${jskPetSize gt 0}">
												<c:forEach items="${jskPet}" var="jskPet" varStatus="looppet">
													<c:if test="${not empty jskPet}">
														<c:set var="pet" scope="request" value="${jskPet}"/>	
														<%
															/*--pet--*/
															Pet pet = (Pet)request.getAttribute("pet");
															com.topgun.shris.masterdata.Pet aPet=MasterDataManager.getPet(pet.getIdPet(),  resume.getIdLanguage());
															request.setAttribute("petName",aPet!=null?aPet.getPetName():"");
														%>
														<c:choose>
															<c:when test="${looppet.index eq 0}">
																<c:choose>
																	<c:when test="${not empty petName}">
																		- <c:out value="${petName}"/>
																	</c:when>
																	<c:otherwise>
																		- <c:out value="${jskPet.otherPet}"/>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${not empty petName}">
																		,<c:out value="${petName}"/>
																	</c:when>
																	<c:otherwise>
																		,<c:out value="${jskPet.otherPet}"/>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
											</c:if>
										</div>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${hobbyOther.idHobby eq 43}"><!-- Travel -->
							<c:choose>
								<c:when test="${loop.index eq 0}">
									<c:if test="${not empty othName}">
										<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
											<c:out value="${othName}"/>
											<c:if test="${jskTravelSize gt 0}">
												<c:forEach items="${jskTravel}" var="jskTravel" varStatus="looptravel">
													<c:if test="${not empty jskTravel}">
														<c:set var="travel" scope="request" value="${jskTravel}"/>	
														<%
															Travel travel = (Travel)request.getAttribute("travel");
															com.topgun.shris.masterdata.Travel aTavel=MasterDataManager.getTravel(travel.getIdTravel(), resume.getIdLanguage());
															request.setAttribute("travelName",aTavel!=null?aTavel.getTravelName():"");
    			  		 									com.topgun.shris.masterdata.TravelFreq aFreq=MasterDataManager.getTravelFreq(travel.getIdFrequency(), resume.getIdLanguage());
															request.setAttribute("freqName",aFreq!=null?aFreq.getFrequencyName():"");
														%>
														<c:if test="${not empty travelName && not empty freqName}">
															- <c:out value="${travelName}"/> - <c:out value="${freqName}"/><br>
														</c:if>
													</c:if>
												</c:forEach>
											</c:if>
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty othName}">
										<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3"></div>
										<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
											<c:out value="${othName}"/>
											<c:if test="${jskTravelSize gt 0}">
												<c:forEach items="${jskTravel}" var="jskTravel" varStatus="looptravel">
													<c:if test="${not empty jskTravel}">
														<c:set var="travel" scope="request" value="${jskTravel}"/>	
														<%
															Travel travel = (Travel)request.getAttribute("travel");
															com.topgun.shris.masterdata.Travel aTavel=MasterDataManager.getTravel(travel.getIdTravel(), resume.getIdLanguage());
															request.setAttribute("travelName",aTavel!=null?aTavel.getTravelName():"");
    			  		 									com.topgun.shris.masterdata.TravelFreq aFreq=MasterDataManager.getTravelFreq(travel.getIdFrequency(), resume.getIdLanguage());
															request.setAttribute("freqName",aFreq!=null?aFreq.getFrequencyName():"");
														%>
														<c:if test="${not empty travelName && not empty freqName}">
															<c:out value="${travelName}"/> <c:out value="${freqName}"/><br>
														</c:if>
													</c:if>
												</c:forEach>
											</c:if>
										</div>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${loop.index eq 0}">
									<c:choose>
										<c:when test="${not empty othName}">
											<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
												<c:out value='${othName}'/>
												<c:if test="${not empty skillOth}">
													- <c:out value='${skillOth}'/>
												</c:if>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
												<c:out value='${hobbyOther.othHobby}'/>
												<c:if test="${not empty skillOth}">
													- <c:out value='${skillOth}'/>
												</c:if>
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
										<c:choose>
											<c:when test="${not empty othName}">
												<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3"></div>
												<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 answer">
													<c:out value='${othName}'/>
													<c:if test="${not empty skillOth}">
														- <c:out value='${skillOth}'/>
													</c:if>
												</div>
											</c:when>
											<c:otherwise>
												<div class="col-xs-4 col-sm-4 col-md-3 col-lg-3"></div>
												<div class="col-xs-8 col-sm-8 col-md-9 col-lg-9 answer">
													<c:out value='${hobbyOther.othHobby}'/>
													<c:if test="${not empty skillOth}">
														- <c:out value='${skillOth}'/>
													</c:if>
												</div>
											</c:otherwise>
										</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach><!-- for ใหญ่ -->
		</div>
	</c:if>
	<!-- //OTHER_HOBBIES -->
	
	<!-- APTITUDE_RANKED -->
	<c:if test="${hobbyRankedSize gt 0}">
		<div class="row" style="padding:10pt;">	
			<div class="col-xs-12 col-sm-12 caption">
				<label for="aptitudeRanked"><fmt:message key="PREVIEW_APTITUDE_RANKED_TOP5"/>:</label>
			</div>
			<div class="col-xs-12 col-sm-12 answer">
				<c:forEach items="${hobbyRanked}" var="hobbyRanked" varStatus="loopRank">
					<c:if test="${not empty hobbyRanked}">
						<c:set var="ranked" scope="request" value="${hobbyRanked}"/>
							<% 
								Hobby ranked = (Hobby)request.getAttribute("ranked");
								com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(ranked.getIdHobby(), resume.getIdLanguage());
								request.setAttribute("hobbyName",hobby!=null?hobby.getHobbyName():"");
							%>
							<c:choose>
								<c:when test="${not empty hobbyName}">
									<c:out value='${loopRank.index+1}'/>.<c:out value='${hobbyName}'/>&nbsp;&nbsp;&nbsp;&nbsp;
								</c:when>
								<c:otherwise>
									<c:out value='${loopRank.index+1}'/>.<c:out value='${hobbyRanked.othHobby}'/>&nbsp;&nbsp;&nbsp;&nbsp;
								</c:otherwise>
							</c:choose>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</c:if>
	<!-- APTITUDE_RANKED -->
	
	<div class="row" style="text-align:right;padding:10pt;">
		<div class="col-xs-12 col-sm-12">
			<a href="/SRIX?view=aptitude&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_EDIT"/></a>
		</div>
	</div>
</c:if>
