<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Encoder"%>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume= Util.getInt(request.getParameter("idResume"));
	Resume resume= new ResumeManager().get(idJsk, 0);
	String status= "FALSE";
	int sequence = Util.getInt(request.getParameter("sequence"),1);
	if(resume != null)
	{
		status= Util.getStr(new ResumeStatusManager().getRegisterStatus(resume));
	}
	String idSession=Encoder.getEncode(session.getId());
	request.setAttribute("status", status); 
	request.setAttribute("idSession", idSession); 
	request.setAttribute("sequence", sequence);
	request.setAttribute("idResume", idResume);
	//request.setAttribute("locale",resume.getLocale());
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<script> 
	$(document).ready(function(){
		$("#panel").hide();
	    $("#flip").click(function(){
	    	$("#panel").slideUp('fast');
	    	$("img[class='resumeArrow']").prop("src","../images/arrow_down_green.png");
	    	if(!$("#panel").is(":visible"))
	    	{
	 			$("#panel").slideDown('fast');
	 			$('img[class="resumeArrow"]').prop('src','../images/arrow_up_green.png');
	 		}
	        //$("#panel").slideToggle("slow");
	    });
	});
	
</script>
<c:if test="${idResume > 0 and (param.view eq 'registerFinish')}"> 
	<div align="right">
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div>
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent: 0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_EDUCATION"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
 </c:if> 
<c:if test="${(param.view eq 'experience') or (param.view eq 'targetJob') or (param.view eq 'personal') or (param.view eq 'education')  or (param.view eq 'register') or (param.view eq 'registerIOS') }">
	<div align="right">
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div>
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent: 0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_EDUCATION"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
</c:if>
<c:if test="${(param.view eq 'strength') or (param.view eq 'strengthOrder')}">
	<div align="right">
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div>
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent: 0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_STRENGTHS"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
	<br>
</c:if> 
<c:if test="${ (param.view eq 'aptitude') or (param.view eq 'aptitudeLevel') or (param.view eq 'aptitudeExtension') or (param.view eq 'aptitudeRank')}">
	<div align="right">
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div>
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent: 0.5cm;" class="color59595c font_14"><fmt:message key="APTITUDE_CONTENT"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
	<br>
</c:if> 
<c:choose>
<c:when test="${((param.view eq 'register') or (param.view eq 'registerIOS') or (param.view eq 'experienceQuestion') or (param.view eq 'education') or (param.view eq 'experience') or (param.view eq 'targetJob') or (param.view eq 'personal') or (param.view eq 'strength') or (param.view eq 'strengthOrder') or (param.view eq 'aptitude') or (param.view eq 'aptitudeLevel') or (param.view eq 'aptitudeExtension') or (param.view eq 'aptitudeRank') or (param.view eq 'uploadPhoto') or (param.view eq 'uploadResume') or (param.view eq 'registerFinish') or (param.view eq 'resumeFinish')) and (sequence eq 1 ) }">
	<c:if test="${param.view eq 'personal' and param.emailJobRegister eq 1 }">
		<div style="margin-top: 10px">
			<div><fmt:message key="TARGETJOB_EMAIL_NOTICE_1"/></div>
			<div>
				<ol style="margin: 0px">
					<li><fmt:message key="TARGETJOB_EMAIL_NOTICE_2"/></li>
					<li><fmt:message key="TARGETJOB_EMAIL_NOTICE_3"/></li>
				</ol>
			</div>
			<div>
				<fmt:message key="TARGETJOB_EMAIL_NOTICE_4"/>
			</div>
		</div>
	</c:if>
	<div class="section_header alignCenter">
		<c:choose>
			<c:when test="${(param.view eq 'register') or (param.view eq 'registerIOS')}">
			<br>
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p><br>
			</c:when>
			<c:when test="${param.view eq 'education'}">
			<br>
			<c:if test="${idResume==0}">
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
			</c:if>
				<h3><fmt:message key="SECTION_EDUCATION"/></h3>
			</c:when>
			<c:when test="${param.view eq 'experience'}">
			<br>
			<c:if test="${idResume==0}">
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
			</c:if>
				<h3><fmt:message key="GLOBAL_EXPERIENCE"/></h3>
				<h5 class="section_header"><fmt:message key="EXP_DETAIL"/></h5>
				<h5 class="suggestion"><fmt:message key="DIRECTION_EXPERIENCE"/></h5>
			</c:when>
			<c:when test="${param.view eq 'targetJob'}">
			<br>
			<c:if test="${idResume==0}">
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
			</c:if>
				<h3><fmt:message key="TARGETJOB"/></h3>
			</c:when>
			<c:when test="${param.view eq 'personal'}">
			<br>
			<c:if test="${idResume==0}">
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
			</c:if>
				 <h3><fmt:message key="SECTION_PERSONAL_DATA"/></h3>
				 <div class="suggestion alignCenter">
				    <h5><fmt:message key="DIRECTION_PERSONAL"/></h5>
				 </div>
			</c:when>
			<c:when test="${idResume==0 and (param.view eq 'strength' or param.view eq 'strengthOrder')}">
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
				 <h3>TOPGUN 'S Strengths©</h3>
			</c:when>
			<c:when test="${idResume > 0  and (param.view eq 'strength' or param.view eq 'strengthOrder')}">
				 <h3>TOPGUN 'S Strengths©</h3>
			</c:when>
			<c:when test="${param.view eq 'uploadPhoto'}">
			</c:when>
			<c:when test="${param.view eq 'uploadResume'}">
			</c:when>
			<c:when test="${param.view eq 'registerFinish'}">
			</c:when>
			<c:when test="${param.view eq 'resumeFinish'}">
			</c:when>
			<c:when test="${idResume==0 and param.view eq 'aptitude'}">
			<br>
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
				<h3><fmt:message key="APTITUDE_TOPIC"/></h3>
			</c:when>
			<c:when test="${idResume==0 and param.view eq 'aptitudeLevel'}">
			<br>
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
				<h3><fmt:message key="APTITUDE_TOPIC"/></h3>
			</c:when>
			<c:when test="${idResume==0 and param.view eq 'aptitudeExtension'}">
			<br>
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
				<h3><fmt:message key="APTITUDE_TOPIC"/></h3>
			</c:when>
			<c:when test="${idResume==0 and param.view eq 'aptitudeRank'}">
			<br>
				<p class="suggestion"><fmt:message key="GLOBAL_REGISTERNEW"/></p>
				<h3><fmt:message key="APTITUDE_TOPIC"/></h3>
			</c:when>
			<c:otherwise>
			<c:if test="${idResume > 0 }">
				<h3><fmt:message key="APTITUDE_TOPIC"/></h3>
			</c:if>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="form-group">
				<c:choose>
					<c:when test="${idResume == 0 or (param.view eq 'register') or (param.view eq 'registerIOS')}">
				 <font class="caption_bold"><fmt:message key="GLOBAL_STEP_REGISTER" /></font> 
					</c:when>
				</c:choose>
				 <div class="caption_green">
				<c:choose>
					<c:when test="${(param.view eq 'register') or (param.view eq 'registerIOS')}">
						1. <fmt:message key="GLOBAL_STEP_1"/>
						<img alt="งาน หางาน" src="../images/bar/bar_0.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and param.view eq 'education'}">
						2. <fmt:message key="GLOBAL_STEP_2"/>
						<img alt="งาน หางาน" src="../images/bar/bar-01.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and param.view eq 'experience'}">
						3. <fmt:message key="GLOBAL_STEP_3"/>
						<img alt="งาน หางาน" src="../images/bar/bar-02.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and param.view eq 'targetJob'}">
						4. <fmt:message key="GLOBAL_STEP_4" />
						<img alt="งาน หางาน" src="../images/bar/bar-03.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and param.view eq 'personal'}">
						5. <fmt:message key="GLOBAL_STEP_5" />
						<img alt="งาน หางาน" src="../images/bar/bar-04.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and (param.view eq 'strength')}">
						6. <fmt:message key="GLOBAL_STEP_6" />
						<img alt="งาน หางาน" src="../images/bar/bar-05.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and (param.view eq 'strengthOrder')}">
						6. <fmt:message key="GLOBAL_STEP_6" />
						<img alt="งาน หางาน" src="../images/bar/bar-06.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and (param.view eq 'aptitude')}">
						7. <fmt:message key="GLOBAL_STEP_6" />
						<img alt="งาน หางาน" src="../images/bar/bar-07.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and (param.view eq 'aptitudeLevel')}">
						7. <fmt:message key="GLOBAL_STEP_7" />
						<img alt="งาน หางาน" src="../images/bar/bar-08.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and (param.view eq 'aptitudeExtension')}">
						7. <fmt:message key="GLOBAL_STEP_7" />
						<img alt="งาน หางาน" src="../images/bar/bar-09.png" style="width:100%;">
					</c:when>
					<c:when test="${idResume == 0 and (param.view eq 'aptitudeRank')}">
						7. <fmt:message key="GLOBAL_STEP_7" />
						<img alt="งาน หางาน" src="../images/bar/bar-10.png" style="width:100%;">
					</c:when>
					<c:when test="${(param.view eq 'uploadPhoto')}">
						<img alt="งาน หางาน" src="../images/bar/bar-11.png" style="width:100%;">
					</c:when>
					<c:when test="${(param.view eq 'uploadResume')}">
						<img alt="งาน หางาน" src="../images/bar/bar-12.png" style="width:100%;">
					</c:when>
					<c:when test="${(param.view eq 'registerFinish')}">
						<img alt="งาน หางาน" src="../images/bar/bar-13.png" style="width:100%;">
					</c:when>
					<%-- <c:otherwise>
						7. <fmt:message key="GLOBAL_STEP_7" />
						<img alt="งาน หางาน" src="../images/bar/bar-100.png" style="width:100%;">
					</c:otherwise> --%>
				</c:choose>
		</div>
		<c:choose>
			<c:when test="${idResume == 0 or (param.view eq 'register') or (param.view eq 'registerIOS')}">
			<div id="flip" align="right" class="suggestion">
				<fmt:message key="GLOBAL_ALL_STEP_REGISTER" />
				<img src="../images/arrow_down_green.png" class="resumeArrow" />
			</div>
		</c:when>
		</c:choose>
		<div class="caption" id="panel">
			<c:choose>
				<c:when test="${(param.view eq 'register') or (param.view eq 'registerIOS')}">
					<font class="caption_green">1. <fmt:message key="GLOBAL_STEP_1"/><br></font>
				</c:when>
				<c:otherwise>
					1. <fmt:message key="GLOBAL_STEP_1"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${param.view eq 'education'}">
					<font class="caption_green">2. <fmt:message key="GLOBAL_STEP_2"/><br></font>
				</c:when>
				<c:otherwise>
					2. <fmt:message key="GLOBAL_STEP_2"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${param.view eq 'experience'}">
					<font class="caption_green">3. <fmt:message key="GLOBAL_STEP_3"/></font><br>
				</c:when>
				<c:otherwise>
					3. <fmt:message key="GLOBAL_STEP_3"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${param.view eq 'targetJob'}">
					<font class="caption_green">4. <fmt:message key="GLOBAL_STEP_4"/></font><br>
				</c:when>
				<c:otherwise>
					4. <fmt:message key="GLOBAL_STEP_4"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${param.view eq 'personal'}">
					<font class="caption_green">5. <fmt:message key="GLOBAL_STEP_5"/></font><br>
				</c:when>
				<c:otherwise>
					5. <fmt:message key="GLOBAL_STEP_5"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(param.view eq 'strength')  or (param.view eq 'strengthOrder')}">
					<font class="caption_green">6. <fmt:message key="GLOBAL_STEP_6"/></font><br>
				</c:when>
				<c:otherwise>
					6. <fmt:message key="GLOBAL_STEP_6"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(param.view eq 'aptitude') or (param.view eq 'aptitudeLevel') or (param.view eq 'aptitudeExtension') or (param.view eq 'aptitudeRank')}">
					<font class="caption_green">7. <fmt:message key="GLOBAL_STEP_7"/></font><br>
				</c:when>
				<c:otherwise>
					7. <fmt:message key="GLOBAL_STEP_7"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(param.view eq 'uploadPhoto') or (param.view eq 'aptitudeLevel')}">
					
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</c:when>
<c:otherwise>
	<div class="section_header">
		<c:choose>
			<c:when test="${param.view eq 'register'}">
				<h3><fmt:message key="GLOBAL_REGISTER"/></h3><br>
			</c:when>
			<c:when test="${param.view eq 'education'}">
				<h3><fmt:message key="SECTION_EDUCATION"/></h3> 
			</c:when>
			<c:when test="${param.view eq 'experience'}">
				<h3><fmt:message key="GLOBAL_EXPERIENCE"/></h3>
				<h5 class="section_header"><fmt:message key="EXP_DETAIL"/></h5>
				<h5 class="suggestion"><fmt:message key="DIRECTION_EXPERIENCE"/></h5>
			</c:when>
			<c:when test="${param.view eq 'targetJob'}">
				<h3><fmt:message key="TARGETJOB"/></h3>
			</c:when>
			<c:when test="${param.view eq 'personal'}">
				 <h3><fmt:message key="SECTION_PERSONAL_DATA"/></h3>
				 <div class="suggestion alignCenter">
				    <h5><fmt:message key="DIRECTION_PERSONAL"/></h5>
				 </div>
			</c:when>
			<c:when test="${(param.view eq 'strength') or (param.view eq 'strengthOrder')}">
				 <h3>TOPGUN 'S Strengths©</h3>
			</c:when>
			<c:when test="${param.view eq 'uploadPhoto'}">
				 <div class="suggestion alignCenter">
				   <fmt:message key="DIRECTION_UPLOAD_PHOTO"/>
				 </div>
			</c:when>
			<c:otherwise>
				<h3><fmt:message key="APTITUDE_TOPIC"/></h3>
			</c:otherwise>
		</c:choose>
	</div>
</c:otherwise>
</c:choose>