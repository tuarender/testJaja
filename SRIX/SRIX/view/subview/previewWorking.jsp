<%@page import="java.text.DecimalFormat"%>
<%@page import="com.topgun.resume.ResumeStatusManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.WorkExperience"%>
<%@page import="com.topgun.resume.WorkExperienceManager"%>
<%@page import="com.topgun.resume.ExperienceIndustry"%>
<%@page import="com.topgun.resume.ExperienceIndustryManager"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.State"%>
<%@page import="com.topgun.shris.masterdata.City"%>
<%@page import="com.topgun.shris.masterdata.Country"%>
<%@page import="com.topgun.shris.masterdata.JobType"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%
	DecimalFormat aFormat = new DecimalFormat("#,###.##");
	String locale=Util.getStr(request.getParameter("locale"));
	if(locale.equals(""))
	{
		locale=Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"));
	}
	
	if(locale.equals(""))
	{
		locale="th_TH";
	}
	PropertiesManager proMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	List<WorkExperience> workList = null;
	if(resume!=null)
	{	
		workList=new WorkExperienceManager().getAllFulltimes(resume.getIdJsk(), resume.getIdResume());
		int totalY=0;
		int totalM=0;
		int month=0;
		int totalCom=0;
		if(workList!=null)
		{
			totalCom=workList.size();
			for(int i=0; i<workList.size(); i++)
			{
				month+=workList.get(i).getExpY()*12+workList.get(i).getExpM();
			}
			totalY = month / 12;
      		totalM = month % 12;
		}
		int idWorkLastest=new WorkExperienceManager().getNextId(idJsk, idResume);
		
		request.setAttribute("resume",resume);
		request.setAttribute("totalM",totalM);
		request.setAttribute("totalY",totalY);
		request.setAttribute("totalCom",totalCom);
		request.setAttribute("workList",workList);
		request.setAttribute("idResume",idResume);
		request.setAttribute("idWorkLastest",idWorkLastest);
	}
	String backToView = Util.getStr(request.getParameter("backToView"));
	request.setAttribute("backToView", Util.getStr(request.getParameter("backToView")));
%>
<fmt:setLocale value="${resume.locale}"/>
<style>
	.subincomplete
	{
		float:left !important;
		color:#ff0000 !important;
		font-weight:normal !important;
		font-style:italic !important;
		font-size:8pt !important;
	}
</style>
<script>
//------------------------- set event bcuz got some error when im trying to hide modal but blackdrop doesn't close------------------
//----------------------------------------------------------------------------------------------------------------------------------
//-------------------disadventage:eduList will alway reload when close modal,No metter eduList got change or not--------------------
function confirmDeleteWorkexp(idWork,idResume){
	var d =new Date();
	$('#idWorkexpDelete').val(idWork);
	$('#idResumeDelete').val(idResume);
	$('#modalDeleteWorkexp').modal('show');
	$('#modalDeleteWorkexp').on('hidden.bs.modal', function (e) {
		$.ajax({
			type: "GET",
			url: '/view/subview/previewWorking.jsp?idResume='+idResume+'&t='+d.getTime()+'&backToView=<%=backToView%>',
			async:false,
			success: function(data){
				$('#layer_6').html(data);
			}
		});
	});
}

function deleteWorkexp(){
	var idWork = $('#idWorkexpDelete').val();
	var idResume = $('#idResumeDelete').val();
	var service = 'delete';
	var request = '{"service":"'+service+'","idWork":"'+idWork+'","idResume":"'+idResume+'"}';
	$.ajax({
		type: "POST",
  		url: '/ExperienceEditServ',
		data: JSON.parse(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1){
				$("#modalDeleteWorkexp").modal('hide');
			}
		}
	});
}

</script>
<c:if test="${not empty resume}">
	<div class="row" style="padding:10pt;">
		<div class="col-xs-12 col-sm-12 caption_bold">
			<label for="totalExp"><fmt:message key="WORKEXP_TOTAL_WORKING"/></label>
		</div>
		<div class="col-xs-12 col-sm-12 answer">
			<c:out value="${totalY}"/>
			<c:choose>
				<c:when test="${totalY > 1}">
					<fmt:message key="GLOBAL_YEARS_UNIT"/>
				</c:when>
				<c:otherwise>
					<fmt:message key="GLOBAL_YEAR_UNIT"/>
				</c:otherwise>
			</c:choose>
			
			<c:out value="${totalM}"/>
			<c:choose>
				<c:when test="${totalM > 1}">
					<fmt:message key="GLOBAL_MONTHS_UNIT"/>
				</c:when>
					<c:otherwise><fmt:message key="GLOBAL_MONTH_UNIT"/>
				</c:otherwise>
			</c:choose>,
			
			<c:out value="${totalCom}"/>
			<c:choose>
				<c:when test="${totalCom > 1}"><fmt:message key="GLOBAL_COMPANIES_UNIT"/></c:when>
				<c:otherwise><fmt:message key="GLOBAL_COMPANY_UNIT"/></c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12" style="height:6px;"></div>
	
	<c:if test="${not empty workList}">
		<%	int j=0;%>
		<c:forEach var="workExp" items="${workList}">
			<%
				List<String> chkExp=new ResumeStatusManager().checkCompleteWorkingExperience(workList.get(j), resume);
				j++;
				String incomplete="";
				if(chkExp.size()>0)
				{
					incomplete="( "+proMgr.getMessage(locale, "PART_INCOMPLETE")+" )";
				}
				request.setAttribute("incomplete",incomplete);
			%>
		<c:set var="idWork" value="${workExp.id}"/>
		<div class="row" style="padding:10pt;">
			<div class="col-xs-12 col-sm-12">
				<c:choose>
					<c:when test="${jskWorkList.workingStatus eq 'TRUE'}">
						<fmt:formatDate value="${workExp.workStart}" pattern="MMMM yyyy"/> - <fmt:message key="WORKEXP_PRESENT"/>
					</c:when>
					<c:otherwise>
						<fmt:formatDate value="${workExp.workStart}" pattern="MMMM yyyy"/> - <fmt:formatDate value="${workExp.workEnd}" pattern="MMMM yyyy"/>
					</c:otherwise>
				</c:choose>
				<div class="subincomplete"><c:out value="${incomplete}"/></div>
			</div>
			<div class="col-xs-12 col-sm-12 caption_bold">
				<c:out value="${workExp.companyName}"/>
			</div>
			<div class="col-xs-12 col-sm-12 answer">
				<c:set var="workExp" value="${workExp}" scope="request"/>
				<%
					WorkExperience workExp=(WorkExperience) request.getAttribute("workExp");
					if(workExp!=null)
					{
						if(workExp.getIdCountry()>0 && workExp.getIdState()>0)
						{
							State state=MasterDataManager.getState(workExp.getIdCountry(), workExp.getIdState(),resume.getIdLanguage());
							if(state!=null)
							{
								out.print(state.getStateName());
							}
						}
						else if(!Util.getStr(workExp.getOtherState()).equals(""))
						{
							out.print(","+workExp.getOtherState());
						}
						
						if(workExp.getIdCountry()>0)
						{
							Country country=MasterDataManager.getCountry(workExp.getIdCountry(),resume.getIdLanguage());
							if(country!=null)
							{
								out.print(","+country.getCountryName());
							}
						}
						out.print("<br>");
						//jobtype
						if(Util.getInt(workExp.getWorkJobType())>0)
						{
							JobType jobType=MasterDataManager.getJobType(workExp.getWorkJobType(),resume.getIdLanguage());
							if(jobType!=null)
							{
								if(!Util.getStr(jobType.getTypeName()).equals(""))
								{
									out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_TYPE")+" : </span><br>"+Util.getStr(jobType.getTypeName())+"<br>");
								}
							}
						}
						
						//industry
						List<ExperienceIndustry> industries=new ExperienceIndustryManager().get(workExp.getIdJsk(), workExp.getIdResume(), workExp.getId());
						if(industries!=null)
						{
							if(industries.size()>0)
							{
								out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_INDUSTRY")+" : </span><br>");
								int idx=1;
								for(int i=0; i<industries.size(); i++)
								{
									if(industries.get(i).getIdIndustry()>0)
									{
										com.topgun.shris.masterdata.Industry industry=MasterDataManager.getIndustry(industries.get(i).getIdIndustry(),resume.getIdLanguage());
										if(industry!=null)
										{
											out.print(idx+"."+industry.getIndustryName()+"<br>");
											idx++;
										}
									}
									else if(!Util.getStr(industries.get(i).getOtherIndustry()).equals(""))
									{
										out.print(idx+"."+Util.getStr(industries.get(i).getOtherIndustry())+"<br>");
										idx++;
									}
								}
							}
						}
						//company business
						if(!Util.getStr(workExp.getComBusiness()).equals(""))
						{
							out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_COMPANY_BUSINESS")+" : </span><br>");
							out.print(Util.getStr(workExp.getComBusiness())+"<br>");
						}
						
						//company size
						if(Util.getInt(workExp.getComSize())>0)
						{
							out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+" : </span><br>");
							if(workExp.getComSize()==1)
							{
								out.print("1-15 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==2)
							{
								out.print("15-30 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==3)
							{
								out.print("30-50 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==4)
							{
								out.print("50-100 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==5)
							{
								out.print("100-150 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==6)
							{
								out.print("150-300 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==7)
							{
								out.print("300-500 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==8)
							{
								out.print("500-1000 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
							}
							else if(workExp.getComSize()==9)
							{
								out.print("1000 "+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_UP_EMPLOYEES"));
							}
							out.print("<br>");
						}
						
						//currencyCode and salaryPer use for  latest position and start position both
						String currencyCode="";
						if(workExp.getIdCurrency()>0)
						{
							com.topgun.shris.masterdata.Currency currency=MasterDataManager.getCurrency(workExp.getIdCurrency());
							if(currency!=null)
							{
								currencyCode=currency.getCode();
							}
						}
						String salaryPer="";
						com.topgun.shris.masterdata.SalaryPer salPer=MasterDataManager.getSalaryPer(Util.getInt(workExp.getSalaryPer()), resume.getIdLanguage());
						if(salPer!=null)
						{
							salaryPer=proMgr.getMessage(resume.getLocale(),salPer.getName());
						}
						
						
						//latest position
						if(!Util.getStr(workExp.getPositionLast()).equals(""))
						{
							out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_POS")+" : </span><br>");
							out.print("<span class='answer'>"+workExp.getPositionLast()+"</span><br>");
							//Job Field
							if(Util.getInt(workExp.getWorkJobField())>0)
							{
								com.topgun.shris.masterdata.JobField jobField=MasterDataManager.getJobField(workExp.getWorkJobField(),resume.getIdLanguage());
								if(jobField!=null)
								{
									if(Util.getInt(workExp.getWorkJobField())>0 && Util.getInt(workExp.getWorkSubField())>0)
									{
										out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+" : </span><br>");
									}
									else
									{
										out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+" : </span><br>");
									}
									out.print(Util.getStr(jobField.getFieldName())+" : ");
								}
								
							}
							else if(!Util.getStr(workExp.getWorkJobFieldOth()).equals(""))
							{
								out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+" : </span><br>");
								out.print(Util.getStr(workExp.getWorkJobFieldOth())+" : ");
							}
							//subField
							if(Util.getInt(workExp.getWorkJobField())>0 && Util.getInt(workExp.getWorkSubField())>0)
							{
								com.topgun.shris.masterdata.SubField subField=MasterDataManager.getSubField(workExp.getWorkJobField(),workExp.getWorkSubField(),resume.getIdLanguage());
								if(subField!=null)
								{
									out.print(Util.getStr(subField.getSubfieldName())+"<br>");
								}
								
							}
							else if(!Util.getStr(workExp.getWorkSubFieldOth()).equals(""))
							{
								out.print(Util.getStr(workExp.getWorkSubFieldOth())+"<br>");
							}
							if(Util.getInt(workExp.getSubordinate())>0)
							{
								out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_SUBORDINATE")+" : </span><br>");
								out.print("<span class='answer'>"+workExp.getSubordinate()+"</span><br>");
							}
							//System.out.println(Util.getInt(workExp.getWorkJobField()));
							//System.out.println(Util.getInt(workExp.getWorkSubField()));
							
							//lastest salary
							if(workExp.getSalaryLast()>0)
							{
								out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+" : </span><br>");												
								out.print(aFormat.format(workExp.getSalaryLast())+" "+currencyCode+" : "+salaryPer+"<br>");
							}
							out.print("<br>");
						}
						
						//position start
						if(!Util.getStr(workExp.getPositionStart()).equals(""))
						{
							out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_START_POS")+" : </span><br>");
							out.print("<span class='answer'>"+Util.getStr(workExp.getPositionStart())+"</span><br>");
							
							//Job Field
							if(Util.getInt(workExp.getWorkJobFieldStart())>0)
							{
								com.topgun.shris.masterdata.JobField jobField=MasterDataManager.getJobField(workExp.getWorkJobFieldStart(),resume.getIdLanguage());
								if(jobField!=null)
								{
									if(Util.getInt(workExp.getWorkJobFieldStart())>0 && Util.getInt(workExp.getWorkSubFieldStart())>0)
									{
										out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+" : </span><br>");
									}
									else
									{
										out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+" : </span><br>");
									}
									out.print(Util.getStr(jobField.getFieldName())+" : ");
								}
								
							}
							else if(!Util.getStr(workExp.getWorkJobFieldOthStart()).equals(""))
							{
								out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+" : </span><br>");
								out.print(Util.getStr(workExp.getWorkJobFieldOth())+" : ");
							}

							//subField
							if(Util.getInt(workExp.getWorkJobFieldStart())>0 && Util.getInt(workExp.getWorkSubFieldStart())>0)
							{
								com.topgun.shris.masterdata.SubField subField=MasterDataManager.getSubField(workExp.getWorkJobFieldStart(),workExp.getWorkSubFieldStart(),resume.getIdLanguage());
								if(subField!=null)
								{
									out.print(Util.getStr(subField.getSubfieldName())+"<br>");
								}
								
							}
							else if(!Util.getStr(workExp.getWorkSubFieldOthStart()).equals(""))
							{
								out.print(Util.getStr(workExp.getWorkSubFieldOthStart())+"<br>");
							}
							
							salPer=MasterDataManager.getSalaryPer(Util.getInt(workExp.getSalaryPerStart()), resume.getIdLanguage());
							if(salPer!=null)
							{
								salaryPer=proMgr.getMessage(resume.getLocale(),salPer.getName());
							}
							
							if(workExp.getIdCurrencyStart()>0)
							{
								com.topgun.shris.masterdata.Currency currency=MasterDataManager.getCurrency(workExp.getIdCurrencyStart());
								if(currency!=null)
								{
									currencyCode=currency.getCode();
								}
							}
							
							//salary start
							if(workExp.getSalaryStart()>0)
							{
								out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+" : </span><br>");												
								out.print(aFormat.format(workExp.getSalaryStart())+" "+currencyCode+" : "+salaryPer+"<br>");
							}
							out.print("<br>");
							
						}
						
						
						if(!Util.getStr(workExp.getJobDesc()).equals(""))
						{
							out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_RESPONSIBILITIES")+" : </span><br>");												
							out.print("<span style='display:inline-block; width:200px;'>"+Util.getStr(workExp.getJobDesc())+"</span><br>");
						}
					
						if(!Util.getStr(workExp.getAchieve()).equals(""))
						{
							out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_ACHIEVEMENT")+" : </span><br>");												
							out.print("<span style='display:inline-block; width:200px;'>"+Util.getStr(workExp.getAchieve())+"</span><br>");
						}

						if(!Util.getStr(workExp.getReasonQuit()).equals(""))
						{
							out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_REASON_FOR_LEAVING")+" : </span><br>");												
							out.print("<span style='display:inline-block; width:200px;'>"+Util.getStr(workExp.getReasonQuit())+"</span><br>");
						}
					}
				%>
			</div>
			<div class="col-xs-12 col-sm-12">
				<c:if test="${empty backToView }">
					<a name='edit' href="javascript:void(0)" onClick='document.location.href="/SRIX?view=experienceEdit&sequence=0&idWork=<c:out value='${idWork}'/>&idResume=<c:out value='${idResume}'/>"' >
						<fmt:message key="GLOBAL_EDIT"/>
					</a>
				</c:if>
				<c:if test="${not empty backToView }">
					<a name='edit' href="javascript:void(0)" onClick='document.location.href="/SRIX?view=experienceEdit&sequence=0&idWork=<c:out value='${idWork}'/>&idResume=<c:out value='${idResume}'/>&backToView=<c:out value="${backToView }"/>"' >
						<fmt:message key="GLOBAL_EDIT"/>
					</a>
				</c:if>
				&nbsp;|&nbsp;
				<a name='remove' href="javascript:void(0)" class="deleteWorkexp" onClick='confirmDeleteWorkexp(<c:out value="${idWork}"/>,<c:out value="${idResume}"/>)' >
					<fmt:message key="GLOBAL_DELETE"/>
				</a>
			</div>
		</div>
		</c:forEach>
	</c:if>		
</c:if>
<div class="row"  style="text-align:right;padding:10pt;">
	<c:if test="${empty backToView }">
		<div class="col-xs-12 col-sm-12">
			<a href="/SRIX?view=experienceEdit&idWork=<c:out value='${idWorkLastest}'/>&sequence=0&idResume=<c:out value='${idResume}'/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
		</div>
	</c:if>
	<c:if test="${not empty backToView }">
		<div class="col-xs-12 col-sm-12">
			<a href="/SRIX?view=experienceEdit&idWork=<c:out value='${idWorkLastest}'/>&sequence=0&idResume=<c:out value='${idResume}'/>&backToView=<c:out value="${backToView }"/>" class="button_link"><fmt:message key="GLOBAL_ADD"/></a>
		</div>
	</c:if>
</div>
<!-- modal skip delete -->
<div class="modal fade bs-example-modal-sm" id="modalDeleteWorkexp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<input type="hidden" id="idWorkexpDelete" value="">
				<input type="hidden" id="idResumeDelete" value="">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalDeleteWorkexpLabel">Confirm</h4>
			</div>
			<div class="modal-body">
				<fmt:message key="CONFIRM_DELETE"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
				<button type="button" class="btn btn-primary" onclick="deleteWorkexp()"><fmt:message key="GLOBAL_CONFIRM"/></button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal skip delete -->