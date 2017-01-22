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
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<%
	PropertiesManager proMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{	
		List<WorkExperience> workList=new WorkExperienceManager().getAll(resume.getIdJsk(), resume.getIdResume());
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
%>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" align="left">
			<br />
			<font class="caption_bold"><fmt:message key="WORKEXP_TOTAL_WORKING"/></font><br />
			<font class="answer">
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
			</font>
			<br />
			<div align="right">
				<a href="/SRIX?view=experienceEdit&idWork=<c:out value="${idWorkLastest}"/>&idResume=<c:out value="${idResume}"/>&sequence=2"><fmt:message key="GLOBAL_ADD"/><fmt:message key="GLOBAL_EXPERIENCE"/></a>&nbsp;&nbsp;
				<hr size="1" width="100%" color="#cccccc">
			</div>
			<br>
			<div>
				<c:if test="${not empty workList}">
					<c:forEach var="workExp" items="${workList}">
						<div style="width:100%;" align="left">
						<c:set var="idWork" value="${workExp.id}"/>
						<div>
							<font class="section_header">
								<c:choose>
									<c:when test="${jskWorkList.workingStatus eq 'TRUE'}">
										<fmt:formatDate value="${workExp.workStart}" pattern="MMMM yyyy"/> - <fmt:message key="WORKEXP_PRESENT"/>
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${workExp.workStart}" pattern="MMMM yyyy"/> - <fmt:formatDate value="${workExp.workEnd}" pattern="MMMM yyyy"/>
									</c:otherwise>
								</c:choose>
							</font>
							<br />
							<font class="caption" style="font-weight:normal;">
								<c:out value="${workExp.companyName}"/>
							</font>
							<br />
							<div class="answer" style="font-weight:normal;">
								<c:set var="workExp" value="${workExp}" scope="request"/>
								<%
									WorkExperience workExp=(WorkExperience) request.getAttribute("workExp");
									if(workExp!=null)
									{
										//location
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
										out.print("<div style='height:5pt;'></div>");
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
										out.print("<div style='height:5pt;'></div>");
										//industry
										List<ExperienceIndustry> industries=new ExperienceIndustryManager().getAll(workExp.getIdJsk(), workExp.getIdResume(), workExp.getId());
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
										out.print("<div style='height:5pt;'></div>");
										//company business
										if(!Util.getStr(workExp.getComBusiness()).equals(""))
										{
											out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_COMPANY_BUSINESS")+" : </span><br>");
											out.print("<span style='display:inline-block; width:100%;>" +Util.getStr(workExp.getComBusiness())+"</span><br>");
										}
										out.print("<div style='height:5pt;'></div>");										
										//company size
										if(Util.getInt(workExp.getComSize())>0)
										{
											out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+" : </span><br>");
											out.print("<font style='font-weight:normal;' class='answer'>");
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
											out.print("<font>");
											out.print("<br>");
										}
										out.print("<div style='height:5pt;'></div>");	
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
										
										out.print("<div style='height:5pt;'></div>");	
										//latest position
										if(!Util.getStr(workExp.getPositionLast()).equals(""))
										{
											out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_POS")+" : </span><br>");
											out.print("<span class='answer'>"+workExp.getPositionLast()+"</span><br>");
											if(Util.getInt(workExp.getSubordinate())>0)
											{
												out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_SUBORDINATE")+" : </span>");
												out.print("<span class='answer'>"+workExp.getSubordinate()+"</span><br>");
											}
											out.print("<div style='height:5pt;'></div>");	
											//Job Field
											if(Util.getInt(workExp.getWorkJobField())>0)
											{
												com.topgun.shris.masterdata.JobField jobField=MasterDataManager.getJobField(workExp.getWorkJobField(),resume.getIdLanguage());
												if(jobField!=null)
												{
													out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+" : </span><br>");
													out.print(Util.getStr(jobField.getFieldName())+" : ");
												}
												
											}
											else if(!Util.getStr(workExp.getWorkJobFieldOth()).equals(""))
											{
												out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+" : </span><br>");
												out.print(Util.getStr(workExp.getWorkJobFieldOth())+" : ");
											}
											out.print("<div style='height:5pt;'></div>");	
											//subField
											if(Util.getInt(workExp.getWorkJobField())>0 && Util.getInt(workExp.getWorkSubField())>0)
											{
												com.topgun.shris.masterdata.SubField subField=MasterDataManager.getSubField(workExp.getWorkJobField(),workExp.getWorkSubField(),resume.getIdLanguage());
												if(subField!=null)
												{
													out.print(Util.getStr(subField.getSubfieldName())+"<br>");
												}
												
											}
											else if(!Util.getStr(workExp.getWorkJobFieldOth()).equals(""))
											{
												out.print(Util.getStr(workExp.getWorkSubFieldOth())+"<br>");
											}
											out.print("<div style='height:5pt;'></div>");	
											//lastest salary
											if(workExp.getSalaryLast()>0)
											{
												out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+" : </span><br>");												
												out.print(workExp.getSalaryLast()+" "+currencyCode+" : "+salaryPer+"<br>");
											}
										}
										out.print("<div style='height:5pt;'></div>");
										//position start
										if(!Util.getStr(workExp.getPositionStart()).equals(""))
										{
											out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_START_POS")+" : </span><br>");
											out.print("<span class='answer'>"+Util.getStr(workExp.getPositionStart())+"</span><br>");
											out.print("<div style='height:10pt;'></div>");	
											//Job Field
											if(Util.getInt(workExp.getWorkJobFieldStart())>0)
											{
												com.topgun.shris.masterdata.JobField jobField=MasterDataManager.getJobField(workExp.getWorkJobFieldStart(),resume.getIdLanguage());
												if(jobField!=null)
												{
													out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+" : </span><br>");
													out.print(Util.getStr(jobField.getFieldName())+" : ");
												}
												
											}
											else if(!Util.getStr(workExp.getWorkJobFieldOthStart()).equals(""))
											{
												out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+" : </span><br>");
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
											out.print("<div style='height:5pt;'></div>");	
											//salary start
											if(workExp.getSalaryStart()>0)
											{
												out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+" : </span><br>");												
												out.print(workExp.getSalaryStart()+" "+currencyCode+" : "+salaryPer+"<br>");
											}
											
										}
										out.print("<div style='height:5pt;'></div>");
										if(!Util.getStr(workExp.getJobDesc()).equals(""))
										{
											out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_RESPONSIBILITIES")+" : </span><br>");												
											out.print("<span style='display:inline-block; width:200px;'>"+Util.getStr(workExp.getJobDesc())+"</span><br>");
										}
										out.print("<div style='height:5pt;'></div>");
										if(!Util.getStr(workExp.getAchieve()).equals(""))
										{
											out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_ACHIEVEMENT")+" : </span><br>");												
											out.print("<span style='display:inline-block; width:200px;'>"+Util.getStr(workExp.getAchieve())+"</span><br>");
										}
										out.print("<div style='height:5pt;'></div>");
										if(!Util.getStr(workExp.getReasonQuit()).equals(""))
										{
											out.print("<span class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_REASON_FOR_LEAVING")+" : </span><br>");												
											out.print("<span style='display:inline-block; width:200px;'>"+Util.getStr(workExp.getReasonQuit())+"</span><br>");
										}
									}
								%>
							</div>
							<br>
							<div align="right">
								<a href="/SRIX?view=experienceEdit&idWork=<c:out value="${idWork}"/>&idResume=<c:out value="${idResume}"/>&sequence=2"><fmt:message key="GLOBAL_EDIT"/></a>&nbsp;&nbsp;|&nbsp;&nbsp;
								<a href="javascript:void(0);" class="deleteWorkExp" onClick='confirmDeleteWorkExp(<c:out value="${idWork}"/>,<c:out value="${idResume}"/>)'><fmt:message key="GLOBAL_DELETE"/></a>&nbsp;&nbsp;
							</div>
						</div>
						<hr size="1" color="#cccccc" width="100%">
						</div>
					</c:forEach>
				</c:if>
			</div>
			<div align="center">
			<font class='caption_bold'>
				<input type='button' value='<fmt:message key="GLOBAL_NEXT"/>' class='btn btn-lg btn-success' onClick='document.location.href="/SRIX?view=skillLanguage&idResume=<c:out value="${idResume}"/>&sequence=<c:out value="${param.sequence}"/>"' >
			</font>
			<br>
			</div>
			<div>&nbsp;</div>
		</div>
</c:if>