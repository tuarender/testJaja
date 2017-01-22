<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.StrengthManager"%>
<%@page import="com.topgun.shris.masterdata.Language" %>
<%@page import="com.topgun.shris.masterdata.MasterDataManager" %>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<%
	int idJsk=Util.StrToInt(""+session.getAttribute("SESSION_ID_JSK"));
	int idResume = Util.StrToInt(request.getParameter("ResumeId")!=null?request.getParameter("ResumeId"):"");
	int idStrength= Util.getInt(request.getParameter("id")!=null?request.getParameter("id"):"");
	Resume resume = new ResumeManager().get(idJsk, idResume);
	if(resume != null)
	{
		int idLanguage=resume.getIdLanguage();
		int idCountry=resume.getIdCountry();
		String ct="",ln="";
		int idLang=resume.getIdLanguage();	
		ln= MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		ct= MasterDataManager.getCountry(idCountry, idLanguage).getAbbreviation();
		String strengthName= (String)StrengthManager.getStrengthName(idLanguage, idStrength);
		String fileName="/contents/"+ct+"/"+ln+"/strength/"+idStrength+".txt";
		
		request.setAttribute("strengthName",strengthName);
		request.setAttribute("fileName",fileName);	 
	} 
	
%>
<div align="left" style="padding-top:10px;">
	<h4 style="color:#725fa7;">The Topgun's Strengths</h4>
	<div align="center" class="suggestion">
		<b><c:out value="${strengthName}" /></b>
	</div>
	<div class="caption">
		<c:import url="${fileName}" charEncoding="UTF-8"></c:import>
	</div>
</div>
