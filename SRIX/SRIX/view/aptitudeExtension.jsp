<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.shris.masterdata.Pet"%>
<%@page import="com.topgun.shris.masterdata.TravelFreq"%>
<%@page import="com.topgun.shris.masterdata.Travel"%>
<%@page import="com.topgun.shris.masterdata.ReadingBook"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.TravelManager"%>
<%@page import="com.topgun.resume.ReadingManager"%>
<%@page import="com.topgun.resume.PetManager"%>
<%@page import="com.topgun.resume.HobbyManager"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	int sequence=Util.getInt(request.getParameter("sequence"),1);
	int idLanguage=resume.getIdLanguage();
	int idCountry=resume.getIdCountry();
	int resumeCountry = resume.getTemplateIdCountry();
	List<com.topgun.resume.Hobby> hobies=new HobbyManager().getAll(resume.getIdJsk(), resume.getIdResume());
	List<com.topgun.shris.masterdata.Travel> travels=MasterDataManager.getAllTravel(idLanguage);
	List<TravelFreq> travelFeqs=MasterDataManager.getAllTravelFreq(idLanguage);
	List<ReadingBook> readingBooks=MasterDataManager.getAllReadingBook(idLanguage);
	List<Pet> pets=MasterDataManager.getAllPet(idLanguage);
	List<com.topgun.resume.Travel> jskTravels = TravelManager.getAll(resume.getIdJsk(), resume.getIdResume());
	List<com.topgun.resume.Reading> jskReading =  new ReadingManager().getAll(resume.getIdJsk(), resume.getIdResume());
	List<com.topgun.resume.Pet> jskPet=new PetManager().getAll(resume.getIdJsk(), resume.getIdResume());
	String view = Util.getStr(request.getParameter("view"));
	request.setAttribute("view", view);
	request.setAttribute("idJsk", resume.getIdJsk());
	request.setAttribute("idResume", resume.getIdResume());
	request.setAttribute("idCountry", resumeCountry);
	request.setAttribute("hobies", hobies);
	request.setAttribute("travels", travels);
	request.setAttribute("travelFeqs", travelFeqs);
	request.setAttribute("readingBooks", readingBooks);
	request.setAttribute("jskTravels", jskTravels);
	request.setAttribute("jskReading", jskReading);
	request.setAttribute("jskPet", jskPet);
	request.setAttribute("pets", pets);
	request.setAttribute("idLanguage", idLanguage);
	request.setAttribute("idCountry", idCountry);
	request.setAttribute("sequence",sequence);
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
	var idResume=<c:out value="${resume.idResume}"/>;
	$(document).ready(function(){
		getAptitudeExtension();
		$('input[name="travelPlace"]').click(function(){
			if($(this).is(':checked'))
			{
			    $("select[name='travelFreq"+$(this).val()+"']").addClass( "required" );
			    $("#travelFreqLayer"+$(this).val()).show();
			}
			else
			{
			   	$("select[name='travelFreq"+$(this).val()+"']").removeClass( "required" ); 
			    $("#travelFreqLayer"+$(this).val()).hide();
			}
		});
		$('input[name="readingBook"]').click(function()
		{
			if($(this).is(':checked')==true&&($(this).val()==-1))
			{
				$("#otherReading").addClass( "required" );
			}
			else
			{
				$("#otherReading").removeClass( "required" );
			}
		});
		$('input[name="pet"]').click(function()
		{
			if($(this).is(':checked')==true&&($(this).val()==-1))
			{
				$("#otherPet").addClass( "required" );
			}
			else
			{
				$("#otherPet").removeClass( "required" );
			}
		});
		
		var container = $('div.errorContainer');
		$('#ExtensionFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
			invalidHandler: function(form, validator) 
			{
				$('html, body').animate({scrollTop: '0px'}, 300);      
			},
			submitHandler:function(form)
			{
				var chkTravel = true ;
				var chkReading = true ;
				var chkPet = true ;
				
				if($('#travelFlag').val()==1){
					$('#service').val("editTravel");
					$.ajax(
					{
						type: "POST",
			   			url: '/AptitudeExtensionServ',
			   			data: $('#ExtensionFrm').serialize(),
			   			async:false,
			   			success: function(data)
			   			{
			   				var obj = jQuery.parseJSON(data);
			   				if(obj.success==0)
	               			{
	               				chkTravel = false ;
	               				$('div.errorContainer').remove();
			       				for(var i=0; i<obj.errors.length; i++)
			       				{
			       					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
			       				}
			       				container.css({'display':'block'});
			       				$('html, body').animate({scrollTop: '0px'}, 300); 
	               			}
		   				}
		 			});
				}
				if($('#readingFlag').val()==1){
					$('#service').val("editReading");
					$.ajax(
					{
						type: "POST",
			   			url: '/AptitudeExtensionServ',
			   			data: $('#ExtensionFrm').serialize(),
			   			async:false,
			   			success: function(data)
			   			{
			   				var obj = jQuery.parseJSON(data);
			   				if(obj.success==0)
	               			{
	               				chkReading = false ;
	               				$('div.errorContainer').remove();
			       				for(var i=0; i<obj.errors.length; i++)
			       				{
			       					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
			       				}
			       				container.css({'display':'block'});
			       				$('html, body').animate({scrollTop: '0px'}, 300); 
	               			}
		   				}
		 			});
				}
				if($('#petFlag').val()==1){
					$('#service').val("editPet");
					$.ajax(
					{
						type: "POST",
			   			url: '/AptitudeExtensionServ',
			   			data: $('#ExtensionFrm').serialize(),
			   			async:false,
			   			success: function(data)
			   			{
			   				var obj = jQuery.parseJSON(data);
			   				if(obj.success==0)
	               			{
	               				chkPet = false ;
	               				$('div.errorContainer').remove();
			       				for(var i=0; i<obj.errors.length; i++)
			       				{
			       					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
			       				}
			       				container.css({'display':'block'});
			       				$('html, body').animate({scrollTop: '0px'}, 300); 
	               			}
		   				}
		 			});
				}
				
				if(chkTravel&&chkReading&&chkPet){
					$.ajax(
					{
						type: "POST",
						url: '/AptitudeExtensionServ',
						data: {'service':'checkExtension','idResume':idResume},
						async:false,
						success: function(data)
						{
							var obj = jQuery.parseJSON(data);
							if(obj.success==1)
							{
								if(obj.canNext==true)
								{
									if(sequence==0)
									{
										$('#resumeListLayer').modal({
		       								show: 'true'
		    							});
		   							}
		   							else
		   							{
		   								window.location.href = "/SRIX?view=aptitudeRank&idResume=<c:out value='${idResume}'/>&sequence="+sequence;
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
				}
			}		
		});
	});	
	function getAptitudeExtension(){
		<c:forEach var="hobies" items="${hobies}">
			<c:if test="${hobies.idHobby eq 43}">
				$('#travelLayer').show();
				$('#travelFlag').val("1");
			</c:if>
			<c:if test="${hobies.idHobby eq 45}">
				$('#readingLayer').show();
				$('#readingFlag').val("1");
			</c:if>
			<c:if test="${hobies.idHobby eq 56}">
				$('#petLayer').show();
				$('#petFlag').val("1");
			</c:if>
		</c:forEach>
		<c:if test="${not empty jskTravels}">
			<c:forEach var="jskTravels" items="${jskTravels}">
				$('input[type=checkbox][id=travelPlace<c:out value="${jskTravels.idTravel}"/>]').prop('checked', true);	
				$('#travelFreqLayer<c:out value="${jskTravels.idTravel}"/>').show();
				$('select[id=travelFreq<c:out value="${jskTravels.idTravel}"/>]').val('<c:out value="${jskTravels.idFrequency}"/>');
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty jskReading}">
			<c:forEach var="jskReading" items="${jskReading}">
				$('input[type=checkbox][id=readingBook<c:out value="${jskReading.idBook}"/>]').prop('checked', true);
				<c:if test="${jskReading.idBook eq -1}">
					$("#otherReading").val('<c:out value="${jskReading.otherBook}"/>');
				</c:if>
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty jskPet}">
			<c:forEach var="jskPet" items="${jskPet}">
				$('input[type=checkbox][id=pet<c:out value="${jskPet.idPet}"/>]').prop('checked', true);
				<c:if test="${jskPet.idPet eq -1}">
					$("#otherPet").val('<c:out value="${jskPet.otherPet}"/>');
				</c:if>	
			</c:forEach>
		</c:if>
	}
</script>
<form id="ExtensionFrm" method="post" class="form-horizontal">
	<input type="hidden" name="service" id="service" value="">
	<input type="hidden" name="idResume" value="<c:out value='${idResume}' />" >
	<div class="seperator"></div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
		</div>
	</div>
	<div class="suggestion alignCenter">
		<h5><fmt:message key="DIRECTION_APTITUDE_EXTENSION"/></h5>
	</div>
	<div class="row errorContainer" style="padding:5px">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol> </ol>
	</div>
	
	<div id="travelLayer" class="row" style="display:none;">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<input type="hidden" name="travelFlag" id="travelFlag" value="0">
			<div class="row">
				<span class="caption_bold"><fmt:message key="SECTION_TRAVELING"/></span>
			</div>
			<c:forEach var="travel" items="${travels}" varStatus="idx">
				<div class="row">
					<input type="checkbox" title="<fmt:message key="YOU_HAVE_TRAVAL_TO"/>" class="required"  value="<c:out value="${travel.idTravel}"/>" name="travelPlace" id="travelPlace<c:out value="${travel.idTravel}"/>">
					<label for="travelPlace<c:out value="${travel.idTravel}"/>" class="answer" style="font-weight:normal !important;"><c:out value="${travel.travelName}"/></label>
				</div>	
				<div id="travelFreqLayer<c:out value="${travel.idTravel}"/>" class="row" style="display:none;">
					<select name="travelFreq<c:out value="${travel.idTravel}"/>" id="travelFreq<c:out value="${travel.idTravel}"/>" title="<fmt:message key='FREQUENCY_TRAVAL'/>" class="form-control">
						<option value=""><fmt:message key="GLOBAL_SPECIFY"/></option>
						<c:forEach var="travelFeq" items="${travelFeqs}">	
							<option value='<c:out value="${travelFeq.idFequency }"/>'><c:out value="${travelFeq.frequencyName}"/></option>	
						</c:forEach>
					</select>
				</div>
			</c:forEach>
		</div>
		<br>
	</div>
	
	<div id="readingLayer" class="row" style="display:none;">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<input type="hidden" name="readingFlag" id="readingFlag" value="0">
			<div class="row">
				<span class="caption_bold"><fmt:message key="SECTION_READING"/></span>
			</div>
			<c:forEach var="readingBook" items="${readingBooks}">
				<div class="row">
					<input type="checkbox" value="<c:out value="${readingBook.idBook}"/>" id="readingBook<c:out value="${readingBook.idBook}"/>"  name="readingBook" title="<fmt:message key="BOOK_REQUIRED"/>" class="required" >
					<label for="readingBook<c:out value="${readingBook.idBook}"/>" class="answer" style="font-weight:normal !important;"><c:out value="${readingBook.bookName}"/></label>
				</div>
			</c:forEach>
			<div class="row">
				<input type="checkbox" value="-1" name="readingBook" id="readingBook-1" >
				<label for="readingBook-1" class="answer" style="font-weight:normal !important;"><fmt:message key="GLOBAL_OTHER"/></label>
				<input type="text" name="otherReading" id="otherReading" title="<fmt:message key='OTHER_BOOK_REQUIRED'/>">
			</div>
		</div>
		<br>
	</div>
	
	<div id="petLayer"  class="row" style="display:none;">
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
			&nbsp;
		</div>
		<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			<input type="hidden" name="petFlag" id="petFlag" value="0">
			<div class="row">
				<span class="caption_bold"><fmt:message key="SECTION_PET"/></span>
			</div>
			<c:forEach var="pet" items="${pets}">
				<div class="row">
					<input type="checkbox" value="<c:out value="${pet.idPet}"/>" name="pet" id="pet<c:out value="${pet.idPet}"/>" title="<fmt:message key="PET_REQUIRED"/>" class="required">
					<label for="pet<c:out value="${pet.idPet}"/>" class="answer" style="font-weight:normal !important;"><c:out value="${pet.petName}"/></label>
				</div>
			</c:forEach>
			<div class="row">
				<input type="checkbox" value="-1" name="pet" id="pet-1">
				<label for="pet-1" class="answer" style="font-weight:normal !important;"><fmt:message key="GLOBAL_OTHER"/></label>
				<input type="text" id="otherPet" name="otherPet" title="<fmt:message key='OTHER_PET_REQUIRED'/>">
			</div>
		</div>
		<br>
	</div>
	<br>
	<div class="row" align="center">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" onClick="ga('send', 'event', { eventCategory: 'Engage-AptitudeExtension', eventAction: 'ClickOn', eventLabel: 'ถัดไป'});"/>
		</div>
	</div>
	<br>
</form>
<c:set var="section" value="6" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>