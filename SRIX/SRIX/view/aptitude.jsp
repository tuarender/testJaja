<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.HobbyManager"%>
<%@page import="com.topgun.shris.masterdata.Hobby"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.HobbyGroup"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	int idLanguage=resume.getIdLanguage();
	int idCountry=resume.getIdCountry();
	int sequence=Util.getInt(request.getParameter("sequence"),1);
	int resumeCountry = resume.getTemplateIdCountry();
	List<HobbyGroup> hobbyGroups = null;
	String view = Util.getStr(request.getParameter("view"));
	request.setAttribute("view", view);
	if(idLanguage==11 || idLanguage == 38)
	{
		hobbyGroups = MasterDataManager.getAllHobbyGroup(idLanguage);
	}
	else{
		hobbyGroups = MasterDataManager.getAllHobbyGroup(11);
	}
	request.setAttribute("hobbyGroups", hobbyGroups);
	request.setAttribute("idLanguage", idLanguage);
	request.setAttribute("idCountry",idCountry);
	request.setAttribute("idJsk",resume.getIdJsk());
	request.setAttribute("idResume",resume.getIdResume());
	request.setAttribute("sequence", sequence);
	
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
	var sequence=<c:out value='${sequence}'/>;
	var idResume=<c:out value="${idResume}"/>;
	$(document).ready(function()
	{ 
		var container = $('div.errorContainer');
		
		$('#aptitudeFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
  			invalidHandler: function(form, validator) 
  			{
  				$('html, body').animate({scrollTop: '0px'}, 300);      
  			},
  			highlight: function(element) 
  			{
            	$(element).closest('.form-group').addClass('has-error');
        	},
        	unhighlight: function(element) 
        	{
            	$(element).closest('.form-group').removeClass('has-error');
        	},
        	errorPlacement: function(error, element) 
        	{
            	if(element.parent('.form-group').length) 
            	{
                	error.insertAfter(element.parent());
            	} 
            	else 
            	{
                	error.insertAfter(element);
            	}
        	},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
		   			url: '/AptitudeServ',
		   			data: $('#aptitudeFrm').serialize(),
		   			success: function(data)
		   			{
		   				var obj = jQuery.parseJSON(data);
						if(obj.success==1)
						{
							if(obj.success==true)
							{
								if(sequence==0)
								{
									$('#resumeListLayer').modal({
	       								show: 'true'
	    							});
    							}
    							else
    							{
    								window.location.href = "/SRIX?view=aptitudeLevel&idResume=<c:out value='${idResume}'/>&sequence="+sequence;
    							} 
								
							}
							else
							{
								alert('<fmt:message key="APTITIDE_ATLEAST_1"/>');
								return false;
							}
						}
		   			}
		 		});
		 		return false ;
			}
		});
		$('input[name="aptitude"]').click(function(event) 
		{
			var id=$(this).attr('rel');
		    if($(this).val()==-1 || $(this).val()==-2)
		    {
		    	if($(this).is(':checked')==true)
		    	{
		    		$("#othHobby_"+id).focus();
		    		$("#othHobby_"+id).addClass("required");
		    	}
		    	else
		    	{
		    		$("#othHobby_"+id).val('');
		    		$("#othHobby_"+id).removeClass("required");
		    	}
		    }
		});
	});
</script>
<form id="aptitudeFrm" method="post" class="form-horizontal">
<input type="hidden" name="idResume" value="<c:out value='${idResume}'/>" >
	<br>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="suggestion">
   				 <h5><fmt:message key="DIRECTION_APTITUDE"/></h5>
 			</div>
		</div>
	</div>
	<div class="row errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol><br>
	</div>
	<div class="row">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<c:if test="${not empty  hobbyGroups}">
			<c:set var="j" value="1"/>
				<%
					request.setAttribute("j", 1);
					for (int i = 1; i <= hobbyGroups.size(); i++) {
							request.setAttribute("i", "" + i);
				%>
					<c:if test='${(i eq 8) or (i eq 9) or (i eq 10)}'>
					<h5>
						<c:choose>
							<c:when test="${i eq 8}">
								<div class="form-group">
									<b><c:out value="${j}"/>.<fmt:message key="APTITUDE_SPORTS" /></b>
								</div>
								<c:set var="j" value="${j+1}"/>
							</c:when>
							<c:when test="${i eq 9}">
								<div class="form-group">
									<b><c:out value="${j}"/>.<fmt:message key="APTITUDE_OUTDOOR_ADVENTURE" /></b>
								</div>
								<c:set var="j" value="${j+1}"/>
							</c:when>
							<c:when test="${i eq 10}">
								<div class="form-group">
									<b><c:out value="${j}"/>.<fmt:message key="APTITUDE_OTHER_HOBBIES" /></b>
								</div>
								<c:set var="j" value="${j+1}"/>
							</c:when>
						</c:choose>
					</h5>
					</c:if>
					<%
						if(i == 8 || i == 9 || i == 10){
						List<Hobby> hobbys = MasterDataManager.getAllHobbyByCountry(idCountry, i, idLanguage);
						List<com.topgun.resume.Hobby> hobbiesSelect = new HobbyManager().getAll(resume.getIdJsk(), resume.getIdResume());
						request.setAttribute("hobbys", hobbys);
						request.setAttribute("hbAllSize", hobbys.size());
						request.setAttribute("hobbiesSelect", hobbiesSelect);
						request.setAttribute("hbSize", hobbiesSelect.size());
					%>
					<c:set var="checkedOther1" value="0" />
					<c:set var="checkedOther2" value="0" />
					<c:set var="hobbyOther1" value="" />
					<c:set var="hobbyOther2" value="" />
					<c:forEach items="${hobbiesSelect}" var="hbselect">
						<c:if test="${(hbselect.idHobby eq -1) && (hbselect.idGroup eq hobbyGroups[i-1].id)}">
							<c:set var="checkedOther1" value="1" />
							<c:set var="hobbyOther1" value="${hbselect.othHobby }"/>
						</c:if>
						<c:if test="${(hbselect.idHobby eq -2) && (hbselect.idGroup eq hobbyGroups[i-1].id)}">
							<c:set var="checkedOther2" value="1" />
							<c:set var="hobbyOther2" value="${hbselect.othHobby }"/>
						</c:if>
					</c:forEach>
					<c:forEach items="${hobbys}" var="hobby" varStatus="idx">
						<c:forEach items="${hobbiesSelect}" var="hbselect">
								<c:if test="${hbselect.idHobby eq hobby.idHobby}">
									<c:set var="checked" value="1" />
								</c:if>
						</c:forEach>
						<c:if test="${idx.index%2==0 }">
							<div class="row">
						</c:if>	
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
								<c:choose>
									<c:when test="${checked eq 1}">
										<input id ="hobby_<c:out value="${hobby.idHobby}" />" type="checkbox" name="aptitude" class="required" title="<fmt:message key="APTITIDE_ATLEAST_1"/>" value='<c:out value="${hobby.idHobby}" />' checked>
										<label for="hobby_<c:out value="${hobby.idHobby}"/>" style="font-weight:normal !important;"><c:out value="${hobby.hobbyName}" /></label>
									</c:when>
									<c:otherwise>
										<input id ="hobby_<c:out value="${hobby.idHobby}" />" type="checkbox" name="aptitude" class="required" title="<fmt:message key="APTITIDE_ATLEAST_1"/>" value='<c:out value="${hobby.idHobby}" />'>
										<label for="hobby_<c:out value="${hobby.idHobby}" />" style="font-weight:normal !important;"><c:out value="${hobby.hobbyName}" /></label>
									</c:otherwise>
								</c:choose>
								<c:set var="checked" value="0" />
							</div>
						<c:if test="${idx.index%2==1 }">
							</div>
						</c:if>	
					</c:forEach>
					<c:if test="${hbAllSize%2==1}">
						</div>
					</c:if>
					<c:choose>
						<c:when test="${resume.idCountry ne 216 and i==2}">
						</c:when>
						<c:otherwise>
							<div class="row">
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 " >
									<input type="checkbox" id="othHobbyChk_1<c:out value='${i}'/>" name="aptitude" class="required" title="<fmt:message key="APTITIDE_ATLEAST_1"/>" rel="1<c:out value='${i}'/>" <c:if test="${checkedOther1 eq 1}">checked </c:if> value='-1'>
									<label for="othHobbyChk_1<c:out value='${i}'/>" style="font-weight:normal !important;"><fmt:message key="GLOBAL_OTHER"/>&nbsp;</label>
									<input type="text" style="display:inline-block !important;" class="form-control" name="othHobby_1<c:out value='${i}'/>" id="othHobby_1<c:out value='${i}'/>" style="width:100px;"  maxlength="50" title="<fmt:message key="GLOBAL_REQUIRE"/><fmt:message key="GLOBAL_OTHER"/>" value="<c:out value="${hobbyOther1}"/>">
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<%
						}
					}//for each
					%>
				</c:if>
				<c:if test="${not empty  hobbyGroups}">
				<c:set var="j" value="4"/>
				<%
					request.setAttribute("j",4);
					for (int i = 1; i <= hobbyGroups.size(); i++) {
							request.setAttribute("i", "" + i);
				%>
					
					<c:if test='${(i < 8)}'>
					<h5>
						<c:choose>
							<c:when test="${i eq 1}">
								<div class="form-group">
									<b><c:out value="${j}"/>.<fmt:message key="APTITUDE_MUSIC" /><fmt:message key="APTITUDE_AT" /><fmt:message key="APTITUDE_LISTENING" /></b>
								</div>
								<c:set var="j" value="${j+1}"/>
							</c:when>
							<c:when test="${i eq 2}">
								<div class="form-group">
								<b><c:out value="${j}"/>.<fmt:message key="APTITUDE_MUSIC" /><fmt:message key="APTITUDE_AT" /><fmt:message key="APTITUDE_PLAYING" /></b>
								</div>
								<c:set var="j" value="${j+1}"/>
							</c:when>
						</c:choose>
					</h5>
					</c:if>
					<c:if test='${i > 1 && i < 8}'>
						<c:choose>
							<c:when test="${resume.idCountry eq 216}">
								<div class="form-group">
								<h5><b>
								<c:choose>
										<c:when test='${i eq 2}'>&nbsp;5.1&nbsp;<fmt:message key="APTITUDE_THAI_INSTRUMENTS" /></c:when>
										<c:when test='${i eq 3}'>&nbsp;5.2&nbsp;<fmt:message key="APTITUDE_STRINGS" /></c:when>
										<c:when test='${i eq 4}'>&nbsp;5.3&nbsp;<fmt:message key="APTITUDE_PERCUSSION" /></c:when>
										<c:when test='${i eq 5}'>&nbsp;5.4&nbsp;<fmt:message key="APTITUDE_WOODWINDS" /></c:when>
										<c:when test='${i eq 6}'>&nbsp;5.5&nbsp;<fmt:message key="APTITUDE_BRASS" /></c:when>
										<c:when test='${i eq 7}'>&nbsp;5.6&nbsp;<fmt:message key="APTITUDE_KEYBOARDS" /></c:when>
								</c:choose>
								</b></h5>
								</div>
							</c:when>
							<c:otherwise>
								<div style="margin-left:-10px;">
									<b>
									<c:choose>
										<c:when test='${i eq 3}'>&nbsp;5.1&nbsp;<fmt:message key="APTITUDE_STRINGS" /></c:when>
										<c:when test='${i eq 4}'>&nbsp;5.2&nbsp;<fmt:message key="APTITUDE_PERCUSSION" /></c:when>
										<c:when test='${i eq 5}'>&nbsp;5.3&nbsp;<fmt:message key="APTITUDE_WOODWINDS" /></c:when>
										<c:when test='${i eq 6}'>&nbsp;5.4&nbsp;<fmt:message key="APTITUDE_BRASS" /></c:when>
										<c:when test='${i eq 7}'>&nbsp;5.5&nbsp;<fmt:message key="APTITUDE_KEYBOARDS" /></c:when>
									</c:choose>
									</b>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<%
						if(i < 8){
						List<Hobby> hobbys = MasterDataManager.getAllHobbyByCountry(idCountry, i, idLanguage);
						List<com.topgun.resume.Hobby> hobbiesSelect = new HobbyManager().getAll(resume.getIdJsk(), resume.getIdResume());
						request.setAttribute("hobbys", hobbys);
						request.setAttribute("hbAllSize", hobbys.size());
						request.setAttribute("hobbiesSelect", hobbiesSelect);
						request.setAttribute("hbSize", hobbiesSelect.size());
					%>
					<c:set var="checkedOther1" value="0" />
					<c:set var="checkedOther2" value="0" />
					<c:set var="hobbyOther1" value="" />
					<c:set var="hobbyOther2" value="" />
					<c:forEach items="${hobbiesSelect}" var="hbselect">
						<c:if test="${(hbselect.idHobby eq -1) && (hbselect.idGroup eq hobbyGroups[i-1].id)}">
							<c:set var="checkedOther1" value="1" />
							<c:set var="hobbyOther1" value="${hbselect.othHobby }"/>
						</c:if>
						<c:if test="${(hbselect.idHobby eq -2) && (hbselect.idGroup eq hobbyGroups[i-1].id)}">
							<c:set var="checkedOther2" value="1" />
							<c:set var="hobbyOther2" value="${hbselect.othHobby }"/>
						</c:if>
					</c:forEach>
					<c:forEach items="${hobbys}" var="hobby" varStatus="idx">
						<c:forEach items="${hobbiesSelect}" var="hbselect">
								<c:if test="${hbselect.idHobby eq hobby.idHobby}">
									<c:set var="checked" value="1" />
								</c:if>
						</c:forEach>
						<c:if test="${idx.index%2==0 }">
							<div class="row">
						</c:if>	
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
								<c:choose>
									<c:when test="${checked eq 1}">
										<input id ="hobby_<c:out value="${hobby.idHobby}" />" type="checkbox" name="aptitude" class="required" title="<fmt:message key="APTITIDE_ATLEAST_1"/>" value='<c:out value="${hobby.idHobby}" />' checked>
										<label for="hobby_<c:out value="${hobby.idHobby}"/>" style="font-weight:normal !important;"><c:out value="${hobby.hobbyName}" /></label>
									</c:when>
									<c:otherwise>
										<input id ="hobby_<c:out value="${hobby.idHobby}" />" type="checkbox" name="aptitude" class="required" title="<fmt:message key="APTITIDE_ATLEAST_1"/>" value='<c:out value="${hobby.idHobby}" />'>
										<label for="hobby_<c:out value="${hobby.idHobby}" />" style="font-weight:normal !important;"><c:out value="${hobby.hobbyName}" /></label>
									</c:otherwise>
								</c:choose>
								<c:set var="checked" value="0" />
							</div>
						<c:if test="${idx.index%2==1 }">
							</div>
						</c:if>	
					</c:forEach>
					<c:if test="${hbAllSize%2==1}">
						</div>
					</c:if>
					<c:choose>
						<c:when test="${resume.idCountry ne 216 and i==2}">
						</c:when>
						<c:otherwise>
							<div class="row">
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 " >
									<input type="checkbox" id="othHobbyChk_1<c:out value='${i}'/>" name="aptitude" class="required" title="<fmt:message key="APTITIDE_ATLEAST_1"/>" rel="1<c:out value='${i}'/>" <c:if test="${checkedOther1 eq 1}">checked </c:if> value='-1'>
									<label for="othHobbyChk_1<c:out value='${i}'/>" style="font-weight:normal !important;"><fmt:message key="GLOBAL_OTHER"/>&nbsp;</label>
									<input type="text" style="display:inline-block !important;" class="form-control" name="othHobby_1<c:out value='${i}'/>" id="othHobby_1<c:out value='${i}'/>" style="width:100px;"  maxlength="50" title="<fmt:message key="GLOBAL_REQUIRE"/><fmt:message key="GLOBAL_OTHER"/>" value="<c:out value="${hobbyOther1}"/>">
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<%
						}
					}//for each
					%>
				</c:if>
			</div>
		</div>
		<br>
		<div class="row" align="center">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" onClick="ga('send', 'event', { eventCategory: 'Engage-Aptitude', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"/>
			</div>
		</div>
		<br>
</form>
<c:set var="section" value="6" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>