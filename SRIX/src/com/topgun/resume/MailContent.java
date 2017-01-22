package com.topgun.resume;

import com.topgun.util.PropertiesManager;

public class MailContent {

	PropertiesManager propertieMgr = new PropertiesManager();
	
	public String getAppStoreImageLink(String locale)
	{
		if(locale.equals("en_TH"))
		{
			return "http://mail.jobinthailand.com/content/superMatch/images/EN/btn_appStore.png";
		}
		else
		{
			return "http://mail.jobinthailand.com/content/superMatch/images/TH/btn_appStore.png";
		}
	}
	
	public String getPlayStoreImageLink(String locale)
	{
		if(locale.equals("en_TH"))
		{
			return "http://mail.jobinthailand.com/content/superMatch/images/EN/btn_googleplay.png";
		}
		else
		{
			return "http://mail.jobinthailand.com/content/superMatch/images/TH/btn_googleplay.png";
		}
	}
	
	public String getJobtopgunAppStoreLink(String locale)
	{
		if(locale.equals("en_TH"))
		{
			return "https://itunes.apple.com/en/app/jobtopgun/id605367531?mt=8";
		}
		else
		{
			return "https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8";
		}
	}
	
	public String getJobtopgunPlayStoreLink(String locale)
	{
		if(locale.equals("en_TH"))
		{
			return "https://play.google.com/store/apps/details?id=com.topgun.jobtopgun&hl=en";
		}
		else
		{
			return "https://play.google.com/store/apps/details?id=com.topgun.jobtopgun&hl=th";
		}
	}
	
	public StringBuilder getBody(StringBuilder content)
	{
		StringBuilder result = new StringBuilder();
		result.append(" <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">	 ");
		result.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\">	 ");
		result.append(" <head>	 ");
		result.append(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />	 ");
		result.append(" <title>JobMatch From Jobtopgun</title>	 ");
		result.append(" </head>	 ");
		result.append(" <style>	 ");
		result.append(" @font-face  ");
		result.append(" { ");
		result.append("     font-family: 'SukhumvitRegular'; ");
		result.append("     src: url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitreg-webfont.eot'); ");
		result.append("     src: url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitreg-webfont.eot?#iefix') format('embedded-opentype'), ");
		result.append("          url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitreg-webfont.woff') format('woff'), ");
		result.append("          url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitreg-webfont.ttf') format('truetype'), ");
		result.append("          url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitreg-webfont.svg#SukhumvitRegular') format('svg'); ");
		result.append("     font-weight: normal; ");
		result.append("     font-style: normal; ");
		result.append(" } ");
		result.append(" @font-face  ");
		result.append(" { ");
		result.append("     font-family: 'SukhumvitBold'; ");
		result.append("     src: url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitbold-webfont.eot'); ");
		result.append("     src: url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitbold-webfont.eot?#iefix') format('embedded-opentype'), ");
		result.append("          url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitbold-webfont.woff') format('woff'), ");
		result.append("          url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitbold-webfont.ttf') format('truetype'), ");
		result.append("          url('http://mail.jobinthailand.com/content/superMatch/fonts/sukhumvitbold-webfont.svg#SukhumvitBold') format('svg'); ");
		result.append("     font-weight: Bold; ");
		result.append("     font-style: Bold; ");
		result.append(" } ");
		result.append(" body{ font-family:SukhumvitRegular; font-size:24px; }	 ");
		result.append(" ul {  list-style-type: none;  } ");
		result.append(" .contents {  display:inline-block; vertical-align: text-top;  } ");
		result.append(" .seresultion_header { font-family:'SukhumvitBold'; color:#1d2455; font-weight:bold; font-size:20px;  } ");
		result.append(" .fontBold { font-weight:bold; } ");
		result.append(" .font35 { font-size:35px; } ");
		result.append(" .font25 { font-size:25px; } ");
		result.append(" .font20 { font-size:20px; } ");
		result.append(" .font16 { font-size:16px; } ");
		result.append(" .font15 { font-size:15px; } ");
		result.append(" .font11 { font-size:11px; } ");
		result.append(" .font13 { font-size:13px; } ");
		result.append(" .font12 { font-size:12px; } ");
		result.append(" .font18 { font-size:18px; } ");
		result.append(" .color4f84c4 { color:#4f84c4; } ");
		result.append(" .color1d2354 { color:#1d2354; } ");
		result.append(" .color0f4a90 { color:#0f4a90; } ");
		result.append(" .color696969 { color:#696969; } ");
		result.append(" .color993e41 { color:#993e41; } ");
		result.append(" .color000000 { color:#000000; } ");
		result.append(" .colorec1a39 { color:#ec1a39; } ");
		result.append(" .colore0613fb{ color:#0613fb; } ");
		result.append(" .colore0044ff{ color:#0044ff; } ");
		result.append(" .coloref5f5f5{ color:#f5f5f5; } ");
		result.append(" .hrone { border: 0; height: 2px; width: 600px; background: #4f84c4; text-align: center; } ");
		result.append(" td.columnc:nth-child(2nd) { background-color:#f5f5f5; } ");
		result.append(" .center { text-align: center; } ");
		result.append(" @media only screen and (max-width: 590px)  ");
		result.append(" { div table ");
		result.append("   { width: 480px;  } ");
		result.append("   .mainCol { display: table-header-group; } ");
		result.append("   .subCol { display: table-footer-group; } ");
		result.append("   .font35{ font-size:25px;} ");
		result.append("   .contents	 ");
		result.append("   { display:inline-block !important; vertical-align: text-top; padding:0px; margin:0px; } ");
		result.append(" } ");
		result.append(" .unLine{text-decoration:none;} ");
		result.append(" .img-circle { border-radius: 50%; border-color:#4f84c4;  border-style: solid; } ");
		result.append(" </style>	 ");
		result.append(" <body>	  ");
		result.append(" <div id=\"main\" style=\"width:590px; margin:auto\">	 ");
		result.append("     <div id=\"contents\">	 ");
		result.append(content);
		result.append("     </div>	 ");
		result.append(" </div>	 ");
		result.append(" </body>	 ");
		result.append(" </html>	");
		return result ; 
	}
	
	public StringBuilder getSilverMailContent(int idJsk ,String locale)
	{
		StringBuilder result = new StringBuilder();
		Jobseeker jsk = new JobseekerManager().get(idJsk);
		String forgotPasswordLink = "http://register.superresume.com/SRIX?view=forgot&locale="+propertieMgr.getMessage(locale, "locale");
		String howToDownloadLink = "http://www.superresume.com/SRIX?view=applyLater&locale="+propertieMgr.getMessage(locale, "locale");
		String questionLink = "http://www.jobtopgun.com/?view=comment&locale="+propertieMgr.getMessage(locale, "locale");
		String hotmailSettingLink = "http://www.jobtopgun.com/projects/faq/junk_mail/hotmail.html";
		String gmailSettingLink = "http://www.jobtopgun.com/projects/faq/junk_mail/gmail.html";
		String yahooSettingLink = "http://www.jobtopgun.com/projects/faq/junk_mail/yahoo.html";
		result.append("<div style=\"width:590px;\">");
		result.append("<div style=\"margin:auto;\">");
		result.append("<table border='0' style='width:590px;'>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT1")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT2")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT3")+" : "+jsk.getUsername()+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT4")+" : ");
		result.append(propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT4_1")+" ");
		result.append(propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT4_2")+" "+" <a href='"+forgotPasswordLink+"'>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CLICKHERE")+"</a>");
		result.append("</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT5")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT6")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT6_1")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT7")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT7_1")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT8")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT9")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT9_1")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT10")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT10_1")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT11")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT11_1")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT12")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT13")+"</td></tr>");
		result.append("<tr><td><a href='"+getJobtopgunAppStoreLink(locale)+"'><img src='"+getAppStoreImageLink(locale)+"'/></a>&nbsp;&nbsp;<a href='"+getJobtopgunPlayStoreLink(locale)+"'><img src='"+getPlayStoreImageLink(locale)+"'/></a></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT15")+" <a href='"+questionLink+"'>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CLICKHERE")+"</a></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT16"));
		result.append("<a>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CLICKHERE")+"</a>");
		result.append("&nbsp;"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT16_1")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT17")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT18")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT19")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT20")+"&nbsp;<a href='"+hotmailSettingLink+"'>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CLICKHERE")+"</a></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT21")+"&nbsp;<a href='"+gmailSettingLink+"'>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CLICKHERE")+"</a></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CONTENT22")+"&nbsp;<a href='"+yahooSettingLink+"'>"+propertieMgr.getMessage(locale, "MAIL_SILVER_CLICKHERE")+"</a></td></tr>");
		result.append("</table>");
		result.append("</div>");
		result.append("</div>");
		
		return result ;
	}
	
	public StringBuilder getGoldMailContent(int idJsk ,String locale)
	{
		StringBuilder result = new StringBuilder();
		Jobseeker jsk = new JobseekerManager().get(idJsk);
		Resume resume = new ResumeManager().get(idJsk, 0);
		String jskName = "";
		if(resume != null)
		{
			jskName = resume.getIdLanguage() == 11 ? resume.getFirstName() : resume.getFirstNameThai();
		}
		String forgotPasswordLink = "http://register.superresume.com/SRIX?view=forgot&locale="+propertieMgr.getMessage(locale, "locale");
		String howToDownloadLink = "http://www.superresume.com/SRIX?view=applyLater&locale="+propertieMgr.getMessage(locale, "locale");
		String questionLink = "http://www.jobtopgun.com?view=comment&locale="+propertieMgr.getMessage(locale, "locale");
		String hotmailSettingLink = "http://www.jobtopgun.com/projects/faq/junk_mail/hotmail.html";
		String gmailSettingLink = "http://www.jobtopgun.com/projects/faq/junk_mail/gmail.html";
		String yahooSettingLink = "http://www.jobtopgun.com/projects/faq/junk_mail/yahoo.html";
		result.append("<div style=\"width:590px;\">");
		result.append("<div style=\"margin:auto;\">");
		result.append("<table border='0' style='width:590px;'>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_WELCOME")+" "+jskName+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT1")+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_USERNAME")+" : "+jsk.getUsername()+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_PASSWORD")+" : "+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT2_1")+" <a href='"+forgotPasswordLink+"'>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CLICKHERE")+"</a>"+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT3")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT4")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT4_1")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT5")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT5_1")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT6")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT6_1")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT7")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT7_1")+"</td></tr>");
		result.append("<tr><td><b>&nbsp;-&nbsp;&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT8")+"</b>&nbsp;"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT8_1")+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT9")+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td><a href='"+getJobtopgunAppStoreLink(locale)+"'><img src='"+getAppStoreImageLink(locale)+"'/></a>&nbsp;&nbsp;<a href='"+getJobtopgunPlayStoreLink(locale)+"'><img src='"+getPlayStoreImageLink(locale)+"'/></a></td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_HOWTO_DOWNLOAD")+" <a href='"+howToDownloadLink+"'>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CLICKHERE")+"</a></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT10_1")+" <a href='"+questionLink+"'>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CLICKHERE")+"</a> " +propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT10_2")+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT11")+"</td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td><br/></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CONTENT12")+"</td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_EMAIL_CLIENT_SETTING").replace("{0}", "Hotmail")+"&nbsp;<a href='"+hotmailSettingLink+"'>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CLICKHERE")+"</a></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_EMAIL_CLIENT_SETTING").replace("{0}", "Gmail")+"&nbsp;<a href='"+gmailSettingLink+"'>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CLICKHERE")+"</a></td></tr>");
		result.append("<tr><td>"+propertieMgr.getMessage(locale, "MAIL_GOLD_EMAIL_CLIENT_SETTING").replace("{0}", "Yahoo")+"&nbsp;<a href='"+yahooSettingLink+"'>"+propertieMgr.getMessage(locale, "MAIL_GOLD_CLICKHERE")+"</a></td></tr>");
		result.append("</table>");
		result.append("</div>");
		result.append("</div>");
		
		return result ;
	}
	
}
