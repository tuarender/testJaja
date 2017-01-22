<%@page import="com.topgun.resume.StrengthManager"%>
<%@page import="com.topgun.resume.Strength"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.*"%>
<%@page import="com.topgun.*"%>
<%@page import="com.topgun.util.*"%>
<%
	PropertiesManager propMgr=new PropertiesManager();
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResume=Util.getInt(request.getParameter("idResume"));
	Resume resume=new ResumeManager().get(idJsk, idResume);
	if(resume!=null)
	{
		out.print("<div class='row answer' style='padding:10pt;padding-right:10pt;'>");
			List<Strength> strengths=new StrengthManager().getAll(idJsk,idResume);
			if(strengths.size()>0)
			{
				out.print("<div style='padding-right:10pt;' class='caption'>");
				int countRank=0;
				String list="";
				for(int i=0; i<strengths.size(); i++)
				{
					if(Util.getInt(strengths.get(i).getStrengthOrder())>0  && Util.getInt(strengths.get(i).getStrengthOrder())!=9)
					{
						com.topgun.shris.masterdata.Strength strength=MasterDataManager.getStrength(strengths.get(i).getIdStrength(),resume.getIdLanguage());
						list+="<li>" ;
						if(strength!=null)
						{
							list+="<label class='caption'>"+strength.getStrengthName()+"</label>";		 
						}
						else
						{
							list+="<label class='caption'>"+strengths.get(i).getOthStrength()+"<label>";	
						}
						list+="</li>";
						if(!Util.getStr(strengths.get(i).getStrengthReason()).equals(""))
						{
							list+="<font class='answer'>"+Util.getStr(strengths.get(i).getStrengthReason()).replace("\n", "<br>")+"</font><br>";
						}
						countRank++;
					}
				}
				if(strengths.size()>5)
				{
					if(countRank>=5)
					{
						out.print("<ol>");
						out.print(list);
						out.print("</ol>");
						out.print("<div style='text-align:right; padding-right:10pt;'>");
						out.print("<a href='/SRIX?view=strength&sequence=0&idResume="+idResume+"' class='button_link'>"+propMgr.getMessage(resume.getLocale(), "GLOBAL_EDIT")+"</a>"); 
						out.print("</div>");
					}
					else
					{
						out.print("<ul><li>"+propMgr.getMessage(resume.getLocale(), "PLEASE_RANK")+"</li></ul><br>"); 
						out.print("<div style='text-align:right; padding-right:10pt;'>");
						out.print("<a href='/SRIX?view=strength&sequence=0&idResume="+idResume+"' class='button_link'>"+propMgr.getMessage(resume.getLocale(), "GLOBAL_EDIT")+"</a>"); 
						out.print("</div>");
					}
				}
				else
				{
					if(strengths.size()==countRank)
					{
						out.print("<ol>");
						out.print(list);
						out.print("</ol>"); 
						out.print("<div style='text-align:right; padding-right:10pt;'>");
						out.print("<a href='/SRIX?view=strength&sequence=0&idResume="+idResume+"' class='button_link'>"+propMgr.getMessage(resume.getLocale(), "GLOBAL_EDIT")+"</a>"); 
						out.print("</div>");
					}
					else
					{
						out.print("<ul><li>"+propMgr.getMessage(resume.getLocale(), "PLEASE_RANK")+"</li></ul><br>");
						out.print("<div style='text-align:right; padding-right:10pt;'>");
						out.print("<a href='/SRIX?view=strength&sequence=0&idResume="+idResume+"' class='button_link'>"+propMgr.getMessage(resume.getLocale(), "GLOBAL_EDIT")+"</a>"); 
						out.print("</div>");
					}
				}
				out.print("</div>");
			}
			else
			{
				out.print("<div align='center'><a href='/SRIX?view=strength&sequence=0&idResume="+ idResume +"' class='button_link'>"+propMgr.getMessage(resume.getLocale(), "GLOBAL_ADD")+"</a></div>");
			}
		out.print("</div>");
	}
%>
