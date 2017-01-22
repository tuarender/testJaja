<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.EducationManager"%>
<%@page import="com.topgun.resume.Education"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.*"%>
<%@page import="com.topgun.util.*"%>
<%@page import="java.util.List"%>
<%
	PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	List<Education>eduList=null;
	String ln="";
	String ct="";
	int nextidEdu;
	if(resume!=null)
	{	
		ln=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		ct=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		eduList = new EducationManager().getAll(idJsk, idResume);
		if(eduList != null)
		{
			request.setAttribute("eduList",eduList);
		}
		request.setAttribute("ln",ln);
		request.setAttribute("ct",ct);
		request.setAttribute("resume",resume);
	}
	String backToView = Util.getStr(request.getParameter("backToView"));
	request.setAttribute("backToView", backToView);	
%>
<fmt:setLocale value="${resume.locale}"/>
<script>
//------------------------- set event bcuz got some error when im trying to hide modal but blackdrop doesn't close------------------
//----------------------------------------------------------------------------------------------------------------------------------
//-------------------disadventage:eduList will alway reload when close modal,No metter eduList got change or not--------------------
function confirmDeleteEducation(idEducation,idResume){
	$('#idEducationDelete').val(idEducation);
	$('#idResumeDelete').val(idResume);
	$('#modalDeleteEducation').modal('show');
	$('#modalDeleteEducation').on('hidden.bs.modal', function (e) {
		$.ajax({
			type: "GET",
			url: '/view/subview/previewEducation.jsp?idResume='+idResume+'&backToView=<%=backToView%>',
			async:false,
			success: function(data){
				$('#layer_4').html(data);
			}
		});
	});
}

function deleteEducation(){
	var idEducation = $('#idEducationDelete').val();
	var idResume = $('#idResumeDelete').val();
	var service = 'delete';
	var request = '{"service":"'+service+'","idEducation":"'+idEducation+'","idResume":"'+idResume+'"}';
	$.ajax({
		type: "POST",
  		url: '/EducationServ',
		data: JSON.parse(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				$("#modalDeleteEducation").modal('hide');
			}
		}
	});
}
</script>
<c:if test="${not empty resume}">
	<c:if test="${not empty eduList}">
		<div style='padding:10px' class="answer">
			<c:forEach var="edu" items="${eduList}">
				<c:if test="${not empty edu}">
					<c:set var="edu" scope="request" value="${edu}"/>	
					<%
						//degree
						Education edu = (Education)request.getAttribute("edu");
						com.topgun.shris.masterdata.Degree aDegree=MasterDataManager.getDegree(edu.getIdDegree(), resume.getIdLanguage());
						request.setAttribute("degreeName",aDegree!=null?aDegree.getDegreeName():"");
						//faculty & major
						String facultyName = "";
						String majorName = "";
						if(edu.getIdFacMajor()!=0){
							//faculty
							facultyName  = "<font class='caption'>"+propMgr.getMessage(resume.getLocale(), "PREVIEW_EDUCATION_FACULTY")+"</font>&nbsp;";
							com.topgun.shris.masterdata.Faculty aFaculty = null;
							aFaculty=MasterDataManager.getFacultyExclusive(edu.getIdSchool(), edu.getIdDegree(), edu.getIdFacMajor(), resume.getIdLanguage());
							if(aFaculty==null)
							{
								aFaculty = MasterDataManager.getFaculty(edu.getIdFacMajor(), resume.getIdLanguage());
							}
							if(aFaculty!=null){
								facultyName += aFaculty.getFacultyName();
							}
							else{
								facultyName += edu.getOtherFaculty();
							}
							//major
							if(edu.getIdMajor()!=0){
								majorName = "<font class='caption'>"+propMgr.getMessage(resume.getLocale(), "EDUCATION_MAJOR")+"</font>&nbsp;";
								com.topgun.shris.masterdata.Major aMajor = null;
								aMajor=MasterDataManager.getMajorExclusive(edu.getIdSchool(), edu.getIdDegree(), edu.getIdFacMajor(), edu.getIdMajor(), resume.getIdLanguage());
								if(aMajor==null){
									aMajor = MasterDataManager.getMajor(edu.getIdFacMajor(), edu.getIdMajor(), resume.getIdLanguage());
								}
								if(aMajor!=null)
								{
									majorName += aMajor.getMajorName();
								}
								else 
								{
									majorName += edu.getOtherMajor();
								}
							}
						}
						request.setAttribute("facultyName",facultyName);
						request.setAttribute("majorName",majorName);

						//school
						com.topgun.shris.masterdata.School aSchool = MasterDataManager.getSchool(resume.getIdCountry(), edu.getIdState(), edu.getIdSchool(), resume.getIdLanguage());
						request.setAttribute("schoolName",aSchool!=null?aSchool.getSchoolName():edu.getOtherSchool());
						
						//student Id
						String studentId = "";
						if(edu.getIdStudent()!=null)
						{
							studentId = propMgr.getMessage(resume.getLocale(),"STUDENT_ID")+" "+edu.getIdStudent()+"<br>";
						}
						request.setAttribute("studentId",studentId);

						//country 
						com.topgun.shris.masterdata.Country aCountry = MasterDataManager.getCountry(edu.getIdCountry(), resume.getIdLanguage());
						request.setAttribute("countryName",aCountry!=null?aCountry.getCountryName():"");

						//gpa
						String gpa = "<font class='caption'>"+ propMgr.getMessage(resume.getLocale(), "EDUCATION_GPA")+"</font>&nbsp;"+edu.getGpa() + edu.getUnit();
						request.setAttribute("gpa",gpa);

						//award
						request.setAttribute("award",!edu.getAward().equals("")?edu.getAward().replaceAll("\n","<br>"):"");
					%>
					<li>
						<label class='caption_bold'><c:out value="${degreeName}"/></label>&nbsp;&nbsp;
						<c:out value="${facultyName}" escapeXml="false"/>,<span><c:out value="${majorName}" escapeXml="false"/></span><br/>
						<c:out value="${studentId}" escapeXml="false"/>
						<c:if test='${not empty edu.finishDate}'>
							<fmt:formatDate var="finishDate" value="${edu.finishDate}" pattern="d"/>
							<fmt:formatDate var="eduFinishMonth" value="${edu.finishDate}" pattern="MMMM"/> 
							<fmt:formatDate var="eduFinishYear" value="${edu.finishDate}" pattern="yyyy"/> 
							<c:choose> 
								<c:when test="${resume.idLanguage == 23}"><c:out value="${eduFinishYear}"/>年</c:when> 
								<c:otherwise>
									<c:choose>
										<c:when test="${(finishDate eq 1 ) or (finishDate eq 2)}">
											<c:out value="${eduFinishYear}"/> , &nbsp;
										</c:when>
									</c:choose>
								</c:otherwise> 
							</c:choose>
						</c:if>
						<c:out value="${schoolName}" escapeXml="false"/><br/>
						<c:out value="${countryName}" escapeXml="false"/><br/>
						<c:out value="${gpa}" escapeXml="false"/><br/>
						<span style="word-wrap:break-word;"><c:out value="${award}" escapeXml="false"/></span><br/>
						<div>
							<c:if test="${empty backToView }">
								<a name='edit' href="javascript:void(0)" onClick='document.location.href="/SRIX?view=education&sequence=0&idEducation=<c:out value="${edu.id}"/>&idResume=<c:out value="${resume.idResume}"/>"' >
								<fmt:message key="GLOBAL_EDIT"/>
							</c:if>
							<c:if test="${not empty backToView }">
								<a name='edit' href="javascript:void(0)" onClick='document.location.href="/SRIX?view=education&sequence=0&idEducation=<c:out value="${edu.id}"/>&idResume=<c:out value="${resume.idResume}"/>&backToView=<c:out value="${backToView }"/>"' >
								<fmt:message key="GLOBAL_EDIT"/>
							</c:if>
							
							</a>
							&nbsp;|&nbsp;
							<a name='remove' href="javascript:void(0)" class="deleteEducation" onClick='confirmDeleteEducation(<c:out value="${edu.id}"/>,<c:out value="${resume.idResume}"/>)' >
								<fmt:message key="GLOBAL_DELETE"/>
							</a>
						</div>
					</li>
				</c:if>
			</c:forEach>
		</div>
	</c:if>
</c:if>
<div style="text-align:right; padding:20px">
		<c:if test="${empty backToView }">
			<a href='/SRIX?view=education&sequence=0&idResume=<c:out value="${resume.idResume}"/>' class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
		</c:if>
		<c:if test="${not empty backToView }">
			<a href='/SRIX?view=education&sequence=0&idResume=<c:out value="${resume.idResume}"/>&backToView=<c:out value="${backToView }"/>' class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
		</c:if>
	
</div>
<!-- modal skip delete -->
<div class="modal fade bs-example-modal-sm" id="modalDeleteEducation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<input type="hidden" id="idEducationDelete" value="">
				<input type="hidden" id="idResumeDelete" value="">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalDeleteEducationLabel">Confirm</h4>
			</div>
			<div class="modal-body">
				<fmt:message key="CONFIRM_DELETE"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
				<button type="button" class="btn btn-primary" onclick="deleteEducation()"><fmt:message key="GLOBAL_CONFIRM"/></button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal skip delete -->