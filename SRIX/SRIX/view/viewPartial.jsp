<%@page import="com.topgun.util.Base64Coder"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.text.DateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.ViewResumeManager"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Track"%>
<%@page import="com.topgun.resume.TrackManager"%>
<%@page import="com.topgun.util.Decryption"%>
<%@page import="com.topgun.resume.CompanyManager"%>
<%@page import="com.topgun.resume.Company"%>
<%@page import="com.topgun.resume.MiscellaneousManager"%>
<%@page import="com.topgun.resume.Miscellaneous"%>
<%@page import="com.topgun.shris.masterdata.*"%>
<%
	AttachmentManager atmMgr=new AttachmentManager();
	String key=Util.getStr(request.getParameter("Key"));
	String enc=Util.getStr(request.getParameter("Enc"));
	
	for(int i=0;i<9;i++)
	{
		enc=Base64Coder.decodeString(enc);
		key=Base64Coder.decodeString(key);
	}
	int idJsk=-1;
	int idResume=-1;
	if((!enc.equals(""))&&(!key.equals("")))
	{
		Decryption decryption=new Decryption(enc, key);
		if(decryption.isValid())
		{
			idJsk=decryption.getIdJsk();
			idResume=decryption.getIdResume();
		}
	}
	
	//System.out.println(idJsk);
	//System.out.println(idResume);
	
	Resume resume=new ResumeManager().getIncludeDeleted(idJsk,idResume);
	if(resume!=null)
	{
		String template=request.getRealPath("")+"/templates/TH_PARTIAL.html";
		String curDate=Util.getCurrentDate("d MMMM yyyy", "th");
		if(resume.getIdLanguage()!=38)
		{
			curDate=Util.getCurrentDate("d MMMM yyyy", "en");
		}
		
		String photo="<img width=\"86\" src=\"http://mail.jobinthailand.com/content/superMatch/images/partial_no_photo.png\">";
		Attachment att=atmMgr.getPhoto(idJsk);
		if(att!=null)
		{
			photo = "<img width=\"86\" src=\"http://mail.jobinthailand.com/content/superMatch/images/partial_anonymous.png\">";
		}
		
		int idLang=Util.getInt(request.getParameter("idLang"))!=-1?Util.getInt(request.getParameter("idLang")):resume.getIdLanguage();

		ViewResumeManager viewMgr=new ViewResumeManager();
		boolean isTranslate=false;
		String resumeType = "Original";
		if(idLang!= resume.getIdLanguage())
		{
			String loc= new ResumeManager().getLocale(idLang, resume.getIdCountry());
			loc= loc!=""?loc:resume.getLocale();
			resume.setLocale(loc);
			resume.setIdLanguage(idLang);
			isTranslate=true;
			resumeType = "Translated";
		}
		String buffer = null;
		buffer = viewMgr.viewResumePartial(resume,template,"",1,isTranslate,0).toString();
		buffer=buffer.replaceAll("superresume_logo.gif","http://www.superresume.com/images/superresume_logo.png");
		
		Jobseeker jsk=new JobseekerManager().get(idJsk);
		
	 	SimpleDateFormat ft =   new SimpleDateFormat ("dd MMMM yy ");
		String sendDateStr="เข้าสู่ระบบล่าสุด "+ ft.format(jsk.getTimeStamp()).toString();
		if(idLang==11)
		{
			sendDateStr="Last login "+ ft.format(jsk.getTimeStamp()).toString();
		}
	    PropertiesManager propMgr=new PropertiesManager();
		String phoneStr=propMgr.getMessage(resume.getLocale(), "GLOBAL_PHONE_TYPE_MOBILE");
		String email=propMgr.getMessage(resume.getLocale(), "GLOBAL_EMAIL");
		String homePhone=propMgr.getMessage(resume.getLocale(), "GLOBAL_PHONE_TYPE_HOME");
		
		String mainPhoneStr="";
		String homePhoneStr="";
		if(resume.getPrimaryPhone() != "")
		{
			mainPhoneStr=phoneStr+ ": xx-xxx-xxx";
		}
		if(resume.getSecondaryPhone() != "")
		{
			homePhoneStr=homePhone+ ": xx-xxx-xxx";
		}
		
		resumeType+=" "+new MasterDataManager().getLanguageName(resume.getIdLanguage(),11);
	 
		String superSearchId = Util.encodeJskSuperSearch(idJsk, idResume);
		
		buffer=buffer.replaceAll("#SS_JSK_ID#", superSearchId);
		buffer=buffer.replaceAll("#ORIGINAL#",resumeType);
		buffer=buffer.replaceAll("#MAINPHONE_PARTIAL#",mainPhoneStr);
		buffer=buffer.replaceAll("#HOMEPHONE_PARTIAL#",homePhoneStr);
		buffer=buffer.replaceAll("#SEND_DATE#",sendDateStr);
		buffer=buffer.replaceAll("#EMAIL#",email+ ": xxxxxxxx");
		buffer=buffer.replaceAll("#PHOTOGRAPH#",photo);
		buffer=buffer.replaceAll("#BEGIN_COMPANY_LOGO#","<!--");
		buffer=buffer.replaceAll("#END_COMPANY_LOGO#","-->");
		buffer=buffer.replaceAll("#BEGIN_COMPANY_NAME#","<!--");
		buffer=buffer.replaceAll("#END_COMPANY_NAME#","-->");
		out.println(buffer);	
	}
%>


