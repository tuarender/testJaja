<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.*"%>
<%@page import="com.topgun.util.*"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.resume.WorkExperience"%>
<%@page import="com.topgun.resume.WorkExperienceManager"%>
<%@page import="com.topgun.resume.ExperienceIndustry"%>
<%@page import="com.topgun.resume.ExperienceIndustryManager"%>
<%@page import="java.text.DecimalFormat"%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<script>
function confirmDeleteExperience(idWork,idResume)
{
	$('#idExperienceDelete').val(idWork);
	$('#idResumeDelete').val(idResume);
	$('#modalDeleteExperience').modal('show');
	$('#modalDeleteExperience').on('hidden.bs.modal', function (e) {
		$.ajax({
			type: "GET",
			url: '/view/subview/previewParttime.jsp?idResume='+idResume,
			async:false,
			success: function(data){
				$('#layer_5').html(data);
			}
		});
	});
}


function deleteExperience()
{
	var idWork = $('#idExperienceDelete').val();
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
			if(obj.success==1)
			{
				$("#modalDeleteExperience").modal('hide');
			}
		}
	});
}
</script>
<%
	PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	String ln="";
	String ct="";
	int idWorkLastest=0;
	int totalCom = 0;
	int totalM =0;
	int totalY = 0;
	int resumeLang= resume.getIdLanguage();
	List<WorkExperience> workList=null;
	if(resume != null)
	{
		ln=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		ct=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		idWorkLastest=new WorkExperienceManager().getNextId(idJsk, idResume);
		workList=new WorkExperienceManager().getAllParttimes(resume.getIdJsk(),resume.getIdResume());
		if(workList != null)
		{
			if(workList.size()>0)
			{
				totalCom = workList.size();
				for (int co=0;co<workList.size();co++) 
				{
					totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
				}
				totalY = totalM / 12;
				totalM = totalM % 12;
				request.setAttribute("propMgr", propMgr);
				request.setAttribute("idJsk", idJsk);
				request.setAttribute("idResume", idResume);
				request.setAttribute("resume", resume);
				request.setAttribute("ln", ln);
				request.setAttribute("ct", ct);
				request.setAttribute("idWorkLastest", idWorkLastest);
				request.setAttribute("totalCom", totalCom);
				request.setAttribute("totalM", totalM);
				request.setAttribute("totalY", totalY);
				request.setAttribute("workList", workList);
				request.setAttribute("resumeLang", resumeLang);
				request.setAttribute("workListSize", totalCom);
				request.setAttribute("yearsUnit", propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT"));
				request.setAttribute("yearUnit", propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT"));
				request.setAttribute("partimeEx", propMgr.getMessage(resume.getLocale(),"PREVIEW_TOTAL_PART_TIME_EXPERIENCE"));
				request.setAttribute("monthsUnit", propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT"));
				request.setAttribute("monthUnit", propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT"));
				request.setAttribute("comanyUnit", propMgr.getMessage(resume.getLocale(),"GLOBAL_COMPANY_UNIT"));
				request.setAttribute("comaniesUnit", propMgr.getMessage(resume.getLocale(),"GLOBAL_COMPANIES_UNIT"));
				request.setAttribute("workPresent", propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT"));
				request.setAttribute("workExp", propMgr.getMessage(resume.getLocale(),"WORKEXP_INDUSTRY"));
				request.setAttribute("workCom", propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_COMPANY_BUSINESS"));
				request.setAttribute("workCo", propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE"));
				request.setAttribute("workEm", propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES"));
				request.setAttribute("lastPosition", propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_POS"));
				request.setAttribute("workEquivalentPosition", propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION"));
				request.setAttribute("workJobfield", propMgr.getMessage(resume.getLocale(),"WORKEXP_JOBFIELD"));
				request.setAttribute("workSub", propMgr.getMessage(resume.getLocale(),"WORKEXP_SUBORDINATE"));
				request.setAttribute("workType", propMgr.getMessage(resume.getLocale(),"WORKEXP_TYPE"));
				request.setAttribute("workLateSalary", propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL"));
				request.setAttribute("latePosition", propMgr.getMessage(resume.getLocale(),"WORKEXP_START_POS"));
				request.setAttribute("expJob", propMgr.getMessage(resume.getLocale(),"WORKEXP_JOBFIELD"));
				request.setAttribute("startSalary", propMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL"));
				request.setAttribute("responsd", propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_RESPONSIBILITIES"));
				request.setAttribute("achive", propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_ACHIEVEMENT"));
				request.setAttribute("reason", propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_REASON_FOR_LEAVING"));
				request.setAttribute("position", propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT"));
			}
		}
	}
%>
<div class="row answer" style="padding:10pt;">
	<div class="col-xs-12 col-sm-12">
		<c:choose>
			<c:when test="${not empty resume}">
				<c:choose>
					<c:when test="${workListSize>0}">
						<label class='caption'><c:out value="${partimeEx}" />:</label><br>
						<c:out value="${totalY}" />
						<c:choose>
							<c:when test="${totalY>1}">
								<c:out value="${yearsUnit}" />
							</c:when>
							<c:otherwise>
								<c:out value="${yearUnit}" />
							</c:otherwise>
						</c:choose>
						<c:if test="${totalM ne 0}">
							&nbsp;<c:out value="${totalM}" />
							<c:choose>
								<c:when test="${totalM>1}">
									<c:out value="${monthsUnit}" />
								</c:when>
								<c:otherwise>
									<c:out value="${monthUnit}" />
								</c:otherwise>
							</c:choose>
						</c:if>
						&nbsp;,<c:out value="${totalCom}" />
						<c:choose>
							<c:when test="${totalCom >1}">
								<c:out value="${comaniesUnit}" />
							</c:when>
							<c:otherwise>
								<c:out value="${comanyUnit}" />
							</c:otherwise>
						</c:choose>
						<br>
						<c:if test="${not empty workList}">
							<c:forEach var="work" items="${workList}" varStatus="loop">
							<c:set var="test" value="${loop.count}"/> 
							<%
								int c= ((Integer) pageContext.getAttribute("test")) -1;
								String startWork="";
								List<ExperienceIndustry>expIndustry= null;
								com.topgun.shris.masterdata.JobField jobfield;
								com.topgun.shris.masterdata.SubField aSubfield;
								com.topgun.shris.masterdata.JobType aJobbtype;
								
								DecimalFormat aDecimal = new DecimalFormat( "###,###" );
								if(workList != null)
								{
									expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
									startWork= Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",ln,ct)+" - ";
									com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
									com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
									
									jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
									aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
									
									aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
									
									
									request.setAttribute("aState", aState);
									request.setAttribute("countrySelect", countrySelect);
									request.setAttribute("expIndustry", expIndustry); 
									request.setAttribute("startWork", startWork);
									request.setAttribute("jobfield", jobfield);
									request.setAttribute("aSubfield", aSubfield);
									request.setAttribute("aJobbtype", aJobbtype);
									request.setAttribute("aDecimal", aDecimal);
									request.setAttribute("jobfield", jobfield);
									request.setAttribute("LateSalary", aDecimal.format(workList.get(c).getSalaryLast()).toString());
									request.setAttribute("LateSalaryAmount",workList.get(c).getSalaryLast());
									request.setAttribute("StartSalary", aDecimal.format(workList.get(c).getSalaryStart()).toString());
									request.setAttribute("StartSalartAmount", workList.get(c).getSalaryStart());
									request.setAttribute("per", propMgr.getMessage(resume.getLocale(),MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage()).getName()));
									if(Util.getInt(workList.get(c).getSalaryPerStart())>0)
									{
										request.setAttribute("perStart", propMgr.getMessage(resume.getLocale(),MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage()).getName()));
									}
									else
									{
										request.setAttribute("perStart","");
									}
								
									if(MasterDataManager.getCurrency(workList.get(c).getIdCurrency()) != null)
									{
										request.setAttribute("currency", MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode());
									}
									else
									{
										request.setAttribute("currency","");
									}
									if(MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()) != null)
									{
										request.setAttribute("currencyStart", MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode());
									}
									else
									{
										request.setAttribute("currencyStart","");
									}
								}
							%>
							<c:choose>
							<c:when test="${resumeLang == 38}">
								<!-- Thai language -->
								<c:if test="${work.workStart != 'null'}">
									<b class="caption_bold"><%=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",ln,ct)%>&nbsp;-&nbsp;</b>
								</c:if>
								<c:if test="${work.workEnd != 'null'}">
									<c:choose>
										<c:when test="${work.workingStatus == 'TRUE'}">
											<b class="caption_bold"><c:out value="${workPresent}" />&nbsp;<%=Util.getYear(workList.get(c).getWorkEnd())+543%></b><br>
										</c:when>
										<c:otherwise>
											<b class="caption_bold"><%=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",ln,ct)%></b><br>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:when>
							<c:otherwise>
								<!-- English and other language -->
								<c:if test="${work.workStart != 'null'}">
									<b class="caption_bold"><c:out value="${startWork}" /></b>
								</c:if>
								<c:if test="${work.workEnd != 'null'}">
									<c:choose>
										<c:when test="${work.workingStatus == 'TRUE'}">
											<b class="caption_bold"><c:out value="${workPresent}" />&nbsp;<%=Util.getYear(workList.get(c).getWorkEnd())%></b><br>
										</c:when>
										<c:otherwise>
											<b class="caption_bold"><%=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",ln,ct)%></b><br>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:otherwise>
							</c:choose>
							(
							<c:if test="${work.expY != 0}">
								<c:out value="${work.expY}" />
								<c:choose>
									<c:when test="${work.expY >1}">
										<c:out value="${yearsUnit}" />
									</c:when>
									<c:otherwise>
										<c:out value="${yearUnit}" />
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${work.expM !=0}">
								<c:out value="${work.expM}" />
								<c:choose>
									<c:when test="${work.expM >1}">
										<c:out value="${monthsUnit}" />
									</c:when>
									<c:otherwise>
										<c:out value="${monthUnit}" />
									</c:otherwise>
								</c:choose>
							</c:if>
							)<br>
							<c:out value="${work.companyName}" />
							<c:choose>
								<c:when test="${not empty aState}">
									,<c:out value="${aState.stateName}" />
								</c:when>
								<c:when test="${empty aState}">
									,<c:out value="${work.otherState}" />
								</c:when>
							</c:choose>
							<c:if test="${not empty countrySelect}">
								,<c:out value="${countrySelect.countryName}" />
							</c:if>
							<br>
							<c:if test="${not empty expIndustry}">
								<b class="caption"><c:out value="${workExp}" /></b>
								<ol>
								<c:forEach var="expIn" items="${expIndustry}" varStatus="loop">
									<c:set var="test1" value="${loop.count}"/> 
									<%
										int k= ((Integer) pageContext.getAttribute("test1")) -1;
										com.topgun.shris.masterdata.Industry aInsdus=null;
										if(expIndustry != null)
										{
											aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
											request.setAttribute("aInsdus", aInsdus);
										}
									%>
									<c:choose>
										<c:when test="${not empty aInsdus}">
											<li><c:out value="${aInsdus.industryName}" /></li>
										</c:when>
										<c:otherwise>
											<li><c:out value="${expIn.otherIndustry}" /></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								</ol>
							</c:if>
							<c:if test="${work.comBusiness != ''}">
								<b class="caption"><c:out value="${workCom}" />:</b><br>
								<c:out value="${work.comBusiness}" />
								<br>
							</c:if>
							<c:if test="${work.comSize > 0}">
								<b class="caption"><c:out value="${workCo}" />&nbsp;:</b>
								<c:if test="${work.comSize == 1}">
									1-15&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 2}">
									15-30&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 3}">
									30-50&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 4}">
									50-100&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 5}">
									100-150&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 6}">
									150-300&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 7}">
									300-500&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 8}">
									500-1000&nbsp;<c:out value="${workEm}" />
								</c:if>
								<c:if test="${work.comSize == 9}">
									1000&nbsp;<%=propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_UP_EMPLOYEES")%>
								</c:if>
								<br>
							</c:if>
							<c:if test="${not empty aJobbtype}">
								<b class="caption"><c:out value="${workType}" />:</b><br>
								<c:out value="${aJobbtype.typeName}" />
								<br>
							</c:if>
							<c:if test="${work.positionLast != ''}">
								<b class="caption"><c:out value="${lastPosition}" />:</b><br>
								<c:out value="${work.positionLast}" />
								<br>
							</c:if>
							<c:if test="${work.positionLast != ''}">
								<c:choose>
									<c:when test="${not empty jobfield}">
										<b class='caption'>
											<c:choose>
												<c:when test="${not empty jobfield && not empty aSubfield}">
													<c:out value="${workEquivalentPosition}" />:
												</c:when>
												<c:when test="${true}">
													<c:out value="${position}" />:
												</c:when>
											</c:choose>
										</b><br>
										<c:out value="${jobfield.fieldName}" />
									</c:when>
									<c:when test="${empty jobfield && work.workJobFieldOth == ''}">
										<b class='caption'>
											<c:choose>
												<c:when test="${not empty jobfield && not empty aSubfield}">
													<c:out value="${workEquivalentPosition}" />:
												</c:when>
												<c:when test="${true}">
													<c:out value="${position}" />:
												</c:when>
											</c:choose>
										</b><br>
										<c:out value="${work.workJobFieldOth}" />
									</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${not empty aSubfield}">
										:&nbsp;
										<c:out value="${aSubfield.subfieldName}" />
									</c:when>
									<c:when test="${empty aSubfield && work.workSubFieldOth != ''}">
										:&nbsp;
										<c:out value="${work.workSubFieldOth}" />
									</c:when>
								</c:choose>
								<br>
							</c:if>
							<c:if test="${work.subordinate >0}">
								<b class="caption"><c:out value="${workSub}" />:</b><br>
								<c:out value="${work.subordinate}" />
								<br>
							</c:if>
							<c:if test="${LateSalaryAmount > 0 }">
								<b class="caption"><c:out value="${workLateSalary}" />:</b><br>
								<c:out value="${LateSalary}" />
							
								<c:if test="${work.idCurrency >0}">
									&nbsp;<c:out value="${currency}" />
								</c:if>&nbsp;:&nbsp;
								<c:out value="${per}" />
							</c:if>
							<br>
							<%
								jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
								aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
								request.setAttribute("jobfield", jobfield);
								request.setAttribute("aSubfield", aSubfield);
							%>
							<c:if test="${work.positionStart != ''}">
								<b class="caption"><c:out value="${latePosition}" />:</b><br>
								<c:out value="${work.positionStart}" />
								<br>
							</c:if>
							<c:if test="${work.positionStart != ''}">
								<c:choose>
									<c:when test="${not empty jobfield}">
										<b class='caption'>
											<c:choose>
												<c:when test="${not empty jobfield && not empty aSubfield}">
													<c:out value="${workEquivalentPosition}" />:
												</c:when>
												<c:when test="${true}">
													<c:out value="${position}" />:
												</c:when>
											</c:choose>
										</b><br>
										<c:out value="${jobfield.fieldName}" />
									</c:when>
									<c:when test="${empty jobfield && work.workJobFieldOthStart != ''}">
										<b class='caption'>
											<c:choose>
												<c:when test="${not empty jobfield && not empty aSubfield}">
													<c:out value="${workEquivalentPosition}" />:
												</c:when>
												<c:when test="${true}">
													<c:out value="${position}" />:
												</c:when>
											</c:choose>
										</b><br>
										<c:out value="${work.workJobFieldOthStart}" />
									</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${not empty aSubfield}">
										:&nbsp;
										<c:out value="${aSubfield.subfieldName}" />
									</c:when>
									<c:when test="${empty aSubfield && work.workSubFieldOthStart != ''}">
										:&nbsp;
										<c:out value="${work.workSubFieldOthStart}" />
									</c:when>
								</c:choose>
								<br>
							</c:if>
							<c:if test="${StartSalartAmount > 0}">
								<b class="caption"><c:out value="${startSalary}" />:</b><br>
								<c:out value="${StartSalary }"></c:out>
								<c:if test="${work.idCurrencyStart >0}">
									<c:out value="${currencyStart}" />
								</c:if>
								&nbsp;:&nbsp;
								<c:out value="${perStart}" />
								<br>
							</c:if>
							<c:if test="${work.jobDesc != ''}">
								<b class="caption"><c:out value="${responsd}" />:</b><br>
								<c:out value="${work.jobDesc}" />
								<br>
							</c:if>
							<c:if test="${work.achieve != ''}">
								<b class="caption"><c:out value="${achive}" />:</b><br>
								<c:out value="${work.achieve}" />
								<br>
							</c:if>
							<c:if test="${work.reasonQuit != ''}">
								<b class="caption"><c:out value="${reason}" />:</b><br>
								<c:out value="${work.reasonQuit}" />
							</c:if>
							<div align='left'>
								<a name="edit" onClick="document.location.href='/SRIX?view=experienceEdit&sequence=0&idWork=<c:out value="${work.id}" />&idResume=<c:out value="${idResume}" />'" ><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_EDIT")%></a>
								&nbsp;|&nbsp;<a name='remove' class="deleteExperience" onClick='confirmDeleteExperience(<c:out value="${work.id}" />,<c:out value="${idResume}" />)' ><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_DELETE")%></a>
							</div>	
							<br>		
						</c:forEach>
						</c:if>
						<div style='text-align:right;'>
							<a href='/SRIX?view=experienceEdit&sequence=0&idWork=<%=idWorkLastest%>&idResume=<%=idResume%>' class='button_link'><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_ADD")%></a>
						</div>
					</c:when>
					<c:otherwise>
						<div style='text-align:right;'>
							<a href='/SRIX?view=experienceEdit&sequence=0&idWork=<%=idWorkLastest%>&idResume=<%=idResume%>' class='button_link'><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_ADD")%></a>
						</div>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div align="right">
					<a href='/SRIX?view=experienceEdit&sequence=0&idWork=<%=idWorkLastest%>&idResume=<%=idResume%>' class='button_link'><%=propMgr.getMessage(resume.getLocale(), "GLOBAL_ADD")%></a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<!-- modal skip delete -->
<div class="modal fade bs-example-modal-sm" id="modalDeleteExperience" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<input type="hidden" id="idExperienceDelete" value="">
				<input type="hidden" id="idResumeDelete" value="">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalDeleteExperienceLabel">Confirm</h4>
			</div>
			<div class="modal-body">
				<fmt:message key="CONFIRM_DELETE"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="GLOBAL_CANCEL"/></button>
				<button type="button" class="btn btn-primary" onclick="deleteExperience()"><fmt:message key="GLOBAL_CONFIRM"/></button>
			</div>
		</div>
	</div>
</div>
<!-- /end modal skip delete -->
