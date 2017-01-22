<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="java.util.*"%>
<%@page import="com.topgun.resume.Education"%>
<%@page import="com.topgun.resume.EducationManager"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<%
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idLanguage=Util.getInt(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	int sequence=Util.getInt(request.getParameter("sequence"));
	String backToView = Util.getStr(request.getParameter("backToView")).trim();
	Resume resume=new ResumeManager().get(idJsk, idResume);
	PropertiesManager propMgr=new PropertiesManager();
	String locale=Util.getStr(request.getParameter("locale"));
	if(resume!=null)
	{
	 	idLanguage = resume.getIdLanguage();
	 	locale = resume.getLocale();
	}else if(!locale.equals("") && !request.getSession().getAttribute("SESSION_LOCALE").equals(""))
	{
		locale=Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"));
	}else{
		locale="th_TH";
	}
	String localeArray[] = locale.split("_");
	
	List<Education> eduList = new EducationManager().getAll(idJsk,idResume);
	List<String> eduDetailList = new ArrayList<String>();
	
	for(int i=0;i<eduList.size();i++)
	{
		String eduDetail = "";
		String degreeDetail = MasterDataManager.getDegree(eduList.get(i).getIdDegree(), idLanguage).getDegreeName();
		String facultyDetail = "";
		String majorDetail = "";
		String minorDetail = "";
		String studentIdDetail = "";
		String dateDetail = "";
		String schoolDetail = "";
		String countryDetail = MasterDataManager.getCountry(eduList.get(i).getIdCountry(), idLanguage).getCountryName();
		String gpaDetail = "";
		
		if(eduList.get(i).getIdFacMajor()!=-1&&eduList.get(i).getIdFacMajor()!=0)
		{
			com.topgun.shris.masterdata.Faculty faculty = null ;
			faculty=MasterDataManager.getFacultyExclusive(eduList.get(i).getIdSchool(), eduList.get(i).getIdDegree(), eduList.get(i).getIdFacMajor(), idLanguage);
			if(faculty==null){
				faculty=MasterDataManager.getFaculty(eduList.get(i).getIdFacMajor(), idLanguage);
			}
			if(faculty!=null)
			{
				facultyDetail = propMgr.getMessage(locale, "EDU_FACULTY")+" "+faculty.getFacultyName();
			}
			
			
			if(eduList.get(i).getIdMajor()!=-1&&eduList.get(i).getIdFacMajor()!=0)
			{
				com.topgun.shris.masterdata.Major major = null ;
				major=MasterDataManager.getMajorExclusive(eduList.get(i).getIdSchool(), eduList.get(i).getIdDegree(), eduList.get(i).getIdFacMajor(), eduList.get(i).getIdMajor(), idLanguage);
				if(major==null){
					major=MasterDataManager.getMajor(eduList.get(i).getIdFacMajor(), eduList.get(i).getIdMajor(), idLanguage);
				}
				if(major!=null)
				{
					majorDetail +=", "+propMgr.getMessage(locale, "EDU_MAJOR")+" "+major.getMajorName();
				}
			}
			else
			{
				majorDetail += ", "+propMgr.getMessage(locale, "EDU_MAJOR")+" "+eduList.get(i).getOtherMajor();
			}
		}
		else
		{
			if(!eduList.get(i).getOtherFaculty().equals(""))
			{
				facultyDetail = propMgr.getMessage(locale, "EDU_FACULTY")+" "+eduList.get(i).getOtherFaculty();
			}
			if(!eduList.get(i).getOtherMajor().equals(""))
			{
				majorDetail = ", "+propMgr.getMessage(locale, "EDU_MAJOR")+" "+eduList.get(i).getOtherMajor();
			}
		}
		
		if(eduList.get(i).getIdMinor()!=-1&&eduList.get(i).getIdMinor()!=0)
		{
			com.topgun.shris.masterdata.Major minor=MasterDataManager.getMajor(eduList.get(i).getIdFacMajor(), eduList.get(i).getIdMajor(), idLanguage);
			if(minor!=null)
			{
				minorDetail = propMgr.getMessage(locale, "EDU_MINOR")+" "+minor.getMajorName();
			}
		}
		else
		{
			if(!eduList.get(i).getOtherMinor().equals(""))
			{
				minorDetail = propMgr.getMessage(locale, "EDU_MINOR")+" "+eduList.get(i).getOtherMinor();
			}
		}
		
		if(eduList.get(i).getIdStudent()!=null)
		{
			studentIdDetail = "<br>"+propMgr.getMessage(locale, "STUDENT_ID")+" "+eduList.get(i).getIdStudent();
		}
		
		
		if(eduList.get(i).getFinishDate()!=null)
		{	
			if(Util.DateToStr(eduList.get(i).getFinishDate(), "d",new Locale(localeArray[0],localeArray[1])).equals("1") || Util.DateToStr(eduList.get(i).getFinishDate(), "d",new Locale(localeArray[0],localeArray[1])).equals("2"))
			{
				dateDetail+=Util.DateToStr(eduList.get(i).getFinishDate(), "yyyy",new Locale(localeArray[0],localeArray[1]))+" , ";
			}else
			{
				dateDetail+= "" ;
			}		
		}
		
		if(eduList.get(i).getIdSchool()!=-1)
		{
			com.topgun.shris.masterdata.School school=MasterDataManager.getSchool(eduList.get(i).getIdCountry(), eduList.get(i).getIdState(), eduList.get(i).getIdSchool(), idLanguage);
			if(school!=null)
			{
				schoolDetail += school.getSchoolName();
			}
		}
		else
		{
			schoolDetail += eduList.get(i).getOtherSchool();
		}
		
		gpaDetail += eduList.get(i).getGpa();
		if(!eduList.get(i).getUnit().equals("-1"))
		{
			gpaDetail+= eduList.get(i).getUnit();
		}
		else
		{
			gpaDetail+= eduList.get(i).getOtherUnit();
		}
		//-----------------Combine Education--------------------------------
		eduDetail = "<b>"+degreeDetail+" "+facultyDetail+" "+majorDetail+" "+minorDetail+"</b>";
		eduDetail += studentIdDetail;
		eduDetail += "<br>"+dateDetail+" "+schoolDetail+" "+countryDetail+" "+gpaDetail;
		eduDetail += "<br><a href='?view=education&idResume="+idResume+"&idEducation="+eduList.get(i).getId()+"&sequence="+sequence+(backToView.length() > 0 ? "&backToView="+backToView : "")+"'>"+propMgr.getMessage(locale, "EDU_EDIT")+"</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='deleteEducation("+eduList.get(i).getId()+")'>"+propMgr.getMessage(locale, "EDU_DELETE")+"</a>";
		eduDetailList.add(eduDetail);
	}
	request.setAttribute("eduDetailList",eduDetailList);
%>
<c:if test="${not empty eduDetailList}">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<c:forEach items="${eduDetailList}" var="eduDetailList">
		<div>
			<font><c:out value="${eduDetailList}" escapeXml="false"/></font>
		</div>
	</c:forEach>
	</div>
</c:if>
