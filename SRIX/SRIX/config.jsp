<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.topgun.util.Util" %>
<%@page import="com.topgun.resume.Resume" %>
<%@page import="com.topgun.resume.ResumeManager" %>
<%
	String country="TH";
	String language="th";
	int idCountry=216;
	int idLanguage=38;
	String locale="th_TH";
	
	locale=Util.getStr(request.getParameter("locale"));
	if(locale.equals(""))
	{
		locale=Util.getStr(session.getAttribute("SESSION_LOCALE"));
	}	
	
	if(locale.equals("en_TH")) //en-thai
	{
		locale="en_TH";
		language="en";
		idLanguage=11;
		idCountry=216;
		country="TH";
	}
	else if(locale.equals("en_ID")) //Indonesia
	{
		locale="en_ID";
		language="en";
		idLanguage=11;
		idCountry=102;
		country="ID";
	}
	else if(locale.equals("de_TH")) //Germany
	{
		locale="de_TH";
		language="de";
		idLanguage=16;
		idCountry=216;
		country="TH";
	}
	else if(locale.equals("es_TH")) //Spain
	{
		locale="es_TH";
		language="es";
		idLanguage=36;
		idCountry=216;
		country="TH";
	}
	else if(locale.equals("ja_TH")) //Japan
	{
		locale="ja_TH";
		language="ja";
		idLanguage=23;
		idCountry=216;
		country="TH";
	}
	else if(locale.equals("zh_TH")) //Chinese(Simplify)
	{
		locale="zh_TH";
		language="zh";
		idLanguage=5;
		idCountry=216;
		country="TH";
	}
	else if(locale.equals("zt_TH")) //Chinese(Tradition)
	{
		locale="zt_TH";
		language="zt";
		idLanguage=6;
		idCountry=216;
		country="TH";
	}
	else
	{
		locale="th_TH";
		language="th";
		idLanguage=38;
		idCountry=216;
		country="TH";
	}
	
	int idResume= Util.getInt(request.getParameter("idResume"));
	int idJsk= Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	if(idResume != -1 && idJsk != -1)
	{
		Resume resume= new ResumeManager().get(idJsk, idResume);
		if(resume != null)
		{
			idLanguage= resume.getIdLanguage();
			locale=resume.getLocale();
		}
	}
	
	
	String refer= Util.getStr(request.getParameter("refer"));
	if(!refer.equals(""))
	{
		session.setAttribute("SESSION_REFER",refer);
	}
	session.setAttribute("SESSION_LOCALE",locale);
	request.getSession().setAttribute("SESSION_COUNTRY",country);
	request.getSession().setAttribute("SESSION_ID_COUNTRY",idCountry);
	request.getSession().setAttribute("SESSION_LANGUAGE",language);
	request.getSession().setAttribute("SESSION_ID_LANGUAGE",idLanguage);
%>
