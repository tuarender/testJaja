package com.topgun.resume;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;

import com.topgun.criteriasearch.CriteriaManager;
import com.topgun.services.Job;
import com.topgun.services.JobManager;
import com.topgun.supermatch.SuperMatch;
import com.topgun.util.MailManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.topgun.util.Encryption;
import com.topgun.supermatch.Position;

public class MailThread extends Thread 
{
	private int mailType=0;
	private int idJsk=-1;
	private int idResume = -1;
	private String locale = "th_TH";
	private boolean mockupLocale = false;
	public MailThread(int idJsk,int mailType)
	{
		this.idJsk=idJsk;
		this.mailType=mailType;
	}
	
	public MailThread(int idJsk, int idResume,int mailType)
	{
		this.idJsk=idJsk;
		this.idResume = idResume;
		this.mailType=mailType;
	}
	
	public void run()
	{
		try
		{
			if(idJsk>0)
			{
				if(mailType==1)
				{
					sendWelcomeGoldMember(this.idJsk);
				}
				else if(mailType==2)
				{
					//sendJobMatch(this.idJsk);
				}
				else if(mailType==3)
				{
					//sentJobUpdate(this.idJsk);
					sentSuperMatch(this.idJsk);
				}
				else if(mailType==4)
				{
					CriteriaManager criteriaManager = new CriteriaManager();
					criteriaManager.sendCriteriaSearch(this.idJsk, this.idResume);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendWelcomeGoldMember(int idJsk)
	{
		Jobseeker jsk = new JobseekerManager().get(idJsk);
		if(jsk!=null)
		{
			Resume rms = new ResumeManager().get(idJsk, 0);
			if(rms!=null)
			{
				if(!mockupLocale)
				{
					locale=rms.getLocale();
				}
				
				String subject="Welcome to Jobtopgun";
				MailContent mailContent = new MailContent();
				String contentMail =mailContent.getBody(mailContent.getGoldMailContent(idJsk, locale)).toString();
				MailManager mailMgr=null;
				try
				{
					mailMgr=new MailManager();
					mailMgr.setSubject(subject);
					mailMgr.setRecepient(jsk.getUsername());
					mailMgr.setSender("support@jobtopgun.com");
					mailMgr.addHTML(contentMail);
					mailMgr.send();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}			
			}
		}
	}
	
	public void sendWelcome(int idJsk)
	{
		PropertiesManager propMgr=new PropertiesManager();
		Jobseeker jsk = new JobseekerManager().get(idJsk);
		if(jsk!=null)
		{
			Resume rms = new ResumeManager().get(idJsk, 0);
			if(rms!=null)
			{
				if(!mockupLocale)
				{
					locale=rms.getLocale();
				}
				String name = "";
				if(rms.getIdLanguage()==38)
				{
					name = Util.getStr(rms.getFirstNameThai()).equals("")?Util.getStr(rms.getFirstName()):Util.getStr(rms.getFirstNameThai());
				}
				else
				{
					name = Util.getStr(rms.getFirstName());
				}
				
				String subject="Welcome to Jobtopgun";
				String content=	"<table width='400'><tr><td>"+
								"<p><b>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_WELCOME")+"</b>&nbsp;"+name+",</p>"+
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_HEADER_1")+"</p>"+
								"<br>"+
								"<p>Username : "+jsk.getUsername()+"</p>"+
								"<p>Password : "+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_FORGET_PASSWORD")+"&nbsp;" +
								"<a href='http://www.superresume.com/SRIX?view=forgot&locale="+locale+"' target='_blank'>"+propMgr.getMessage(locale, "CLICK_HERE")+"</a></p>" +
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_CONTENT_1")+
								"<a href='http:///www.superresume.com'>www.superresume.com</a>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_CONTENT_1_1")+"" +
								"<a href='http:///www.jobtopgun.com'>www.jobtopgun.com</a>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_CONTENT_1_2")+"</p>"+
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_CONTENT_2")+"</p>"+
								"<p><b>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_SETTING_EMAIL")+"</b></p>"+
								"<p>" +
								"	<ul>" +
								"		<li>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_HOTMAIL")+"&nbsp;<a href='http://www.jobtopgun.com/projects/faq/junk_mail/hotmail.html' target='_blank'>"+propMgr.getMessage(locale, "CLICK_HERE")+"</a></li>" +
								"		<li>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_GMAIL")+"&nbsp;<a href='http://www.jobtopgun.com/projects/faq/junk_mail/gmail.html' target='_blank'>"+propMgr.getMessage(locale, "CLICK_HERE")+"</a></li>" +
								"		<li>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_YAHOO")+"&nbsp;<a href='http://www.jobtopgun.com/projects/faq/junk_mail/yahoo.html' target='_blank'>"+propMgr.getMessage(locale, "CLICK_HERE")+"</a></li>" +
								"	</ul>" +
								"</p>"+
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_SUGGESTION")+"<a href='http://www.jobtopgun.com/?view=comment&language="+(locale.equals("th_Th")?"th":"en")+"' target='_blank'>"+propMgr.getMessage(locale, "CLICK_HERE")+"</a>&nbsp;"+
								propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_CONTACT")+"</p>"+
								"<br>" +
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_REGISTER_GOODLUCK")+"</p>"+
								"</td></tr></table>";	

			
				String contentMail =	"	<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">	"+
						"	<html xmlns=\"http://www.w3.org/1999/xhtml\">	"+
						"	<head>	"+
						"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />	"+
						"	</head>	"+
						"	<style>	"+
						"	body{	"+
						"		font-family:tahoma;" +
						"		font-size:13px;	"+
						"	}	"+
						"		"+
						"	ul{	"+
						"		font-size: 12px;	"+
						"		padding-left:20px;	"+
						"		padding-bottom:0px;	"+
						"		padding-right:0px;	"+
						"		padding-top:0px;	"+
						"	}	"+
						"		"+
						"	li{	"+
						"		color:#56B5E6; "+
						"		padding-left:0px;	"+
						"	}	"+
						"	</style>	"+
						"		"+
						"	<body>	"+
						content +
						"	</body>	"+
						"	</html>	";
				MailManager mailMgr=null;
				try
				{
					mailMgr=new MailManager();
					mailMgr.setSubject(subject);
					mailMgr.setRecepient(jsk.getUsername());
					mailMgr.setSender("support@jobtopgun.com");
					mailMgr.addHTML(contentMail);
					mailMgr.send();
//					System.out.println("it's ok");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}			
			}
		}
	}
	
	public void sendJobMatch(int idJsk)
	{
		PropertiesManager propMgr=new PropertiesManager();
		Jobseeker jsk = new JobseekerManager().get(idJsk);
		if(jsk!=null)
		{
			Resume rms = new ResumeManager().get(idJsk, 0);
			if(rms!=null)
			{
				if(!mockupLocale)
				{
					locale=rms.getLocale();
				}
				String language = locale.split("_")[0];
				String name = "";
				if(rms.getIdLanguage()==38)
				{
					name = Util.getStr(rms.getFirstNameThai()).equals("")?Util.getStr(rms.getFirstName()):Util.getStr(rms.getFirstNameThai());
				}
				else
				{
					name = Util.getStr(rms.getFirstName());
				}		
				List<Job>jmJobs= JobManager.getJobMatchJob(idJsk,0);
				int cnt=jmJobs.size();
				if(cnt>50) cnt=50;
				String subject = propMgr.getMessage(locale, "CONTENT_EMAIL_JM_SUBJECT")+" "+cnt+" Job(s)";
				String content="";
				if(cnt>0)
				{
					//Job[] jmJobs = JobManager.getJobMatchJob(idJsk,0);
					String htmlJobList = "";
					for(int i=0;i<cnt;i++)
					{
						Job jmJob=jmJobs.get(i);
						String view="<img border=\"0\" src=\"http://mail.jobinthailand.com/content/jobmatch4_image/Images/view_detail.png\"/>";
						String apply="<img border=\"0\" src=\"http://mail.jobinthailand.com/content/jobmatch4_image/Images/apply_now.png\"/>";
						String url1 ="http://www.jobtopgun.com/search/companyList.jsp?id="+jmJob.getIdEmp()+"&language="+language;
						String url2 ="http://www.jobtopgun.com/search/jobpost.jsp?idEmp="+jmJob.getIdEmp()+"&idPosition="+jmJob.getIdPosition()+"&language="+language+"&reference=jobmatch&id_jsk="+jsk.getIdJsk();
						String url3 ="http://www.superresume.com/SRIX?view=apply&idEmp="+jmJob.getIdEmp()+"&idPosition="+jmJob.getIdPosition()+"&locale="+locale+"&reference=jobmatch";
						
						htmlJobList+="<tr bgcolor='#ffffff' >" +
									"<td width=\"125\" align=\"center\" valign=\"top\">" +
									"<a href=\""+url1+"\"><img class=\"logo\" src=\"http://mail.jobinthailand.com/logo/lg"+jmJob.getIdEmp()+".gif\" border=\"0\" width=\"116\"></a>" +
									"</td>" +
									"<td width=\"350\" align=\"left\"><a href=\""+url2+"\"><font style=\"font-size:14px; font-weight:bold\">"+jmJob.getPositionName()+"</font></a>" +
									"<br><br><font style=\"color:#8E8E8E; font-size:13px\"><b>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_EXP")+"</b>:"+JobManager.getExp(Util.getInt(jmJob.getExpLess(),0),Util.getInt(jmJob.getExpMost(),0),rms.getIdLanguage())+"<br>"+
									"<b>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_LOCATION")+":</b>"+JobManager.getJobsLocation(jmJob.getIdEmp(), jmJob.getIdPosition(), rms.getIdLanguage())+"</font>" +
									"</td>" +
									"<td valign=\"top\">" +
									"<a href=\""+url2+"\">"+view+"</a>"+
									"</td>" +
									"<td>" +
									"&nbsp;"+
									"</td>" +
									"<td valign=\"top\">"+
									"<a href=\""+url3+"\">"+apply+"</a>"+
									"</td>"+
									"</tr>"+
									"<tr>" +
									"<td colspan=\"5\" style=\"border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(212, 212, 212);\">" +
									"</td>" +
									"</tr>";
		
					}
					
					//headerContent
					content="<table align=\"center\" style=\"width:550px; margin-bottom:10px\"><tr><td>"+
							"<p><b><u>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_SUBJECT")+"&nbsp;"+cnt+"&nbsp;Job(s)</u></b></p>" +
							"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_HEADER")+"</p>" +
							"<p><b>"+propMgr.getMessage(locale, "HOME_WELLCOME")+"&nbsp;"+name+",</b></p>" +
							"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_MATCH_1")+"&nbsp;"+cnt+"&nbsp;"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_MATCH_2")+"</p>"+
							"</td></tr></table>" +
							"	<div id=\"main\" style=\"width:550px; margin:auto\">	"+
							"		<div id=\"head\" style=\"margin:auto\">	" +
							"			<br>"+
							"	        <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"520\" height=\"95\">	"+
							"	            <tr>	"+
							"	           		<td><img src=\"http://mail.jobinthailand.com/content/jobmatch4_image/Images/header.png\" width=\"520\" height=\"95\"/></td>	"+
							"	            </tr>	"+
							"	        </table>	"+
							"	    </div>	"+
							"	    <div id=\"contents\">	"+
							"	    	<div style=\"padding:10px\">	"+
							"	        	<table width=\"520\" align=\"center\" cellpadding=\"3\">	"+htmlJobList +
							"	            </table>	"+
							"	        </div>	"+
							"	    </div>	"+
							"	</div>	";
				}
				else
				{
					content="<table align='center' width='550'><tr><td>"+
							"<p><b><u>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_SUBJECT")+"&nbsp;"+cnt+"&nbsp;Job(s)</u></b></p>" +
							"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_HEADER")+"&nbsp;"+name+",</p>" +
							"<p><b>"+propMgr.getMessage(locale, "HOME_WELLCOME")+"&nbsp;"+name+",</b></p>" +
							"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_NOTMATCH")+"</p>"+
							"</td></tr></table>";
				}
				if(!content.equals(""))
				{
					String contentMail =	"	<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">	"+
							"	<html xmlns=\"http://www.w3.org/1999/xhtml\">	"+
							"	<head>	"+
							"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />	"+
							"	</head>	"+
							"	<style>	"+
							"	body{	"+
							"		font-family:tahoma;" +
							"		font-size:13px;	"+
							"	}	"+
							"		"+
							"	ul{	"+
							"		font-size: 12px;	"+
							"		padding-left:20px;	"+
							"		padding-bottom:0px;	"+
							"		padding-right:0px;	"+
							"		padding-top:0px;	"+
							"	}	"+
							"		"+
							"	li{	"+
							"		color:#56B5E6; "+
							"		padding-left:0px;	"+
							"	}	"+
							"	</style>	"+
							"		"+
							"	<body>	"+
							content +
							"	</body>	"+
							"	</html>	";				
					
					MailManager mailMgr=null;
					try
					{
						mailMgr=new MailManager();
						mailMgr.setSubject(subject);
						mailMgr.setRecepient(jsk.getUsername());
						mailMgr.setSender("support@jobtopgun.com");
						mailMgr.addHTML(contentMail);
						mailMgr.send();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void sentSuperMatch(int idJsk)
	{
		Jobseeker jsk = new JobseekerManager().get(idJsk);
//		System.out.println("1 sentSuperMatch");
		String encode = Encryption.getEncoding(0,0,jsk.getIdJsk(),0);
		String key = Encryption.getKey(0,0,jsk.getIdJsk(),0);
		if(jsk!=null)
		{
//			System.out.println("2 sentSuperMatch");
			SuperMatch superMatch=new SuperMatch(idJsk,0);
//			System.out.println("2.11 sentSuperMatch"+superMatch.getIdJsk());
			List<Position> jobList=superMatch.getSuperMatch();
//			System.out.println("2.2 sentSuperMatch"+jobList.size());
			if(jobList!=null)
			{
//				System.out.println("3 sentSuperMatch");
				int jobSize=jobList.size();
				if(jobSize>0)
				{
//					System.out.println("4 sentSuperMatch");
					if(jobSize>10)
					{
						jobSize=10;
					}
					//String photo="<div align='center' style='margin:auto'><img class='img-circle' style='width:69px;' src='"+getPhoto(idJsk)+"' /></div>";
					String photo="<div align='center' style='margin:auto'><img width='50' height='50' style='border-radius:25px' src='"+getPhoto(jsk.getIdJsk())+"' /></div>";
//					String greeting1="<b   style='color:#4f84c4; font-weight:bold;   font-size:15px;'>สวัสดี คุณ "+superMatch.getResume().getFirstName()+" </b>";
//					String greeting2="<b  style='color:#4f84c4; font-weight:bold; font-size:15px; '>คุณมี Super Match "+jobList.size()+" ตำแหน่ง </b>";
					String greeting1="<b  style='font-size:20px;color:#444444'>สวัสดี คุณ "+superMatch.getResume().getFirstName()+" </b>";
					String greeting2="<b  style='font-size:20px;color:#444444'>คุณมี Super Match <span style='font-size:22px;color:#0093d8;font-weight:bold'>"+jobList.size()+"</span> ตำแหน่ง </b>";
					
					String expStr="ประสบการณ์ : ";
					String locationStr="สถานที่ : ";
					String remarkStr="หมายเหตุ : ";
					String remarkDetailStr="คุณอาจได้ตำแหน่งที่ปีประสบการณ์ไม่ตรง เพราะระบบ match ตามความสำคัญของเงินเดือนก่อนปีประสบการณ์";
					String localStr="TH";
					String jobBetterStr="งานที่คุณมีโอกาสมากว่า";
					String jobBetterDetailStr="(ยังมีผู้สมัครน้อย)";
					String jobBetterLinkStr="<a href='http://www.jobtopgun.com/?view=allPositionListMoreChance'>ดูเพิ่มเติม</a>";
					String smDetail2Str="เป็นบริการพิเศษสำหรับสมาชิก Jobtopgun เท่านั้น";
					String contactUsStr="ติดต่อเรา";
					String genderStr="เพศ";
					String degreeStr="วุฒิการศึกษา";
					String facStr="คณะ";
					String salaryStr="เงินเดือน : ";
					String majorStr="สาขาวิชาเอก";
					String positionStr="ประสบการณ์ทำงานล่าสุด";
					String expJskStr="ประสบการณ์ทำงานทั้งหมด";
					String locale="th_TH";
					String subject=jobList.size()+" งานใหม่สำหรับ Super Match ของคุณ";
					String surveyContent="ให้คะแนนความพอใจผลการจับคู่ตำแหน่งวันนี้";
					String seeMoreJobs="ดูงาน Super Match เพิ่มเติม";
					String very_unsatisfied="ไม่พอใจมาก";
					String unsatisfied="ไม่พอใจ";
					String neutral="ปานกลาง";
					String satisfied="พอใจ";
					String very_satisfied="พอใจมาก";
					String unsubscribeStr="ยกเลิกอีเมล";
					String editCriteriaStr="หากงานไม่ถูกใจ ปรับเปลี่ยนเงื่อนไขได้ที่นี่";
				      
					if(superMatch.getResume().getIdLanguage()==11)
					{
//						subject=" Super Match found "+jobList.size()+" jobs";
						
						subject=jobList.size()+" jobs from Super Match";
//						greeting1="<div  style='color:#4f84c4; font-weight:bold; font-size:15px;'>Hi "+superMatch.getResume().getFirstName()+",</div>";
//						greeting2="<div  style='color:#4f84c4; font-weight:bold; font-size:15px;'>You have "+jobList.size()+" Super Match today </div>";
						greeting1="<b  style='font-size:20px;color:#444444'>Hello "+superMatch.getResume().getFirstName()+"</b>";
						greeting2="<b  style='font-size:20px;color:#444444'>You have <span style='font-size:22px;color:#0093d8;font-weight:bold'>"+jobList.size()+"</span> Super Match today </b>";
						
						expStr="Year Exp : ";
						locationStr="Location : ";
						remarkStr="Remark";
						remarkDetailStr="The system firstly matches jobs with your salary range before year of experience, therefore you might get some jobs that do not match your year of experience.";
						localStr="EN";
						jobBetterStr="Jobs with better chance";
						jobBetterDetailStr="(Still not many applicants)";
						jobBetterLinkStr="<a href='http://www.jobtopgun.com/?view=allPositionListMoreChance'>see more</a>";
						smDetail2Str="is an exclusive service for Jobtopgun members only.";
						contactUsStr="Contact Us";
						genderStr="Gender";
						degreeStr="Degree";
						facStr="Faculty";
						majorStr="Major";
						salaryStr="Salary : ";
						positionStr="Lastest Job Field";
						expJskStr="Total Experience";
						unsubscribeStr="Unsubscribe";
						editCriteriaStr="If you're not satisfied with your job,edit here ";
						locale="en_TH";
						surveyContent="Rate your Super Match satisfaction today";
						seeMoreJobs="View more jobs from Super Match";
						very_unsatisfied="very unsatisfied";
						unsatisfied="unsatisfied";
						neutral="neutral";
						satisfied="satisfied";
					}
					String smContent=surveyContent; 
					String content=greeting1;
					String content2=greeting2;
					String content1=photo;
				    StringBuilder body = new StringBuilder();
					body.append(" <div  style=\"width:590px;\">");
					String today=Util.nowDateFormat("yyyyMMdd");
					String img="<img src=\"http://rider1.jobinthailand.com/open.php?datematch="+Util.nowDateFormat("yyyyMMdd")+"&id_jsk="+jsk.getIdJsk()+"\" width=\"1px\" height=\"1px\">";
					String analytics="&utm_source=jobtopgun.com&utm_medium=email&utm_content=superMatch_"+today+"&utm_campaign=superMatch";
					String editSuperMatch="http://job.jobinthailand.com/click/click.php?url="+Util.base64("http://www.superresume.com/SRIX?view=jobmatchEdit&idResume=0&encode="+encode+"&key="+key+"&id_jsk="+jsk.getIdJsk()+"&reference=jobmatch&datematch="+today+analytics+"&locale="+locale);

					body.append("<tr>");
					body.append("<td style='padding-left:22px;padding-right:22px;padding-top:25px'>");
					
					for(int i=0; i<jobSize; i++)
					{ 
						body.append("<table style='border-bottom:1px solid #d9d9d9;padding-bottom:30px;padding-top:15px' width='100%' >");
						body.append("	<tr valign='top'>");
						body.append("		<td width='110'><img style='border:1px solid #d9d9d9' src='http://mail.jobinthailand.com/logo/lg"+jobList.get(i).getIdEmp()+".gif''width='116' height='41'></td>");
						body.append("		<td width='40' align='right' style='font-size:20px;color:#0060cf;font-weight:bold;vertical-align:baseline'>	<span style='color:white;font-size:20px;'>&#3670;</span>"+(i+1)+".</td>");
						body.append("		<td align='left'>");
						String jplink="http://rider1.jobinthailand.com/click.php?url="+Util.base64("http://www.jobtopgun.com/StartSearch?idEmp="+jobList.get(i).getIdEmp()+"&idPosition="+jobList.get(i).getIdPosition()+"&locale="+locale+"&id_jsk="+jsk.getIdJsk()+"&PositionId="+jobList.get(i).getIdPosition()+"&EmployerId="+jobList.get(i).getIdEmp()+"&Member=1&reference=jobmatch&datematch="+today+analytics);
						body.append("		<a href='"+jplink+"'  style='font-size:20px;color:#0060cf;font-weight:bold'>"+jobList.get(i).getPositionName()+"</a ><span style='color:white;text-decoration:none;font-size:20px;'>&#3670;</span><br>");
						body.append("        <div style='height:10px'></div>");
						body.append("		<span style='font-size:20px;color:#0093d8;padding-top:5px'>"+jobList.get(i).getNameEng()+"</span><br>");
						if(superMatch.getResume().getIdLanguage()==11)
						{
							body.append("<span style='font-size:18px;color:#666666' >"+locationStr+"</span><span style='font-size:18px;color:#444444' >"+jobList.get(i).getLocationEng()+"</span>");
							if(jobList.get(i).getShowSalary()==1 && jobList.get(i).getSalaryLess()>0 && jobList.get(i).getSalaryMost()>0)
							{
								body.append("<br><span style='font-size:18px;color:#666666' >"+salaryStr+"</span><span style='font-size:18px;color:#444444' >"+jobList.get(i).getSalaryEng()+"</span>");
							}

						}
						else
						{
							body.append("<span style='font-size:18px;color:#666666' >"+locationStr+"</span><span style='font-size:18px;color:#444444' >"+jobList.get(i).getLocationTha()+"</span>");
							if(jobList.get(i).getShowSalary()==1 && jobList.get(i).getSalaryLess()>0 && jobList.get(i).getSalaryMost()>0)
							{
								body.append("<br><span style='font-size:18px;color:#666666' >"+salaryStr+"</span><span style='font-size:18px;color:#444444' >"+jobList.get(i).getSalaryTha()+"</span>");
							}
						}
					
						body.append("		</td>");
						body.append("	</tr>");
						body.append("</table>");
						
					}
					body.append("</td>");
					body.append("</tr>");
					
					
					body.append(" <!--Button bottom section-->");
					body.append("	<tr>");
					body.append("		<td align='center' style='padding:40px;'>");
					String smlink="http://rider1.jobinthailand.com/click.php?url="+Util.base64("http://www.jobtopgun.com/AutoLogonServ?view=superMatch&locale="+locale+"&encode="+encode+"&key="+key+"&id_jsk="+jsk.getIdJsk()+"&PositionId=1&EmployerId=1&Member=1&reference=jobmatch&datematch="+today+analytics);
					body.append("			<a  href='"+smlink+"' style='text-decoration: none;'><button style='padding:8px;border-radius:5px;width:300px;font-size:18px;color:#ffffff;background-color:#6d2a79;border:1px solid #6d2a79;cursor:pointer'>"+seeMoreJobs+"</button></a>	");
					body.append("		</td>");
					body.append("	</tr>");
					
					body.append(" <!--Divider section-->");
					body.append("	<tr>");
					body.append("		<td align='center' width='100%' height='10px' style='background-color:#f0f0f0;''>");
					body.append("		</td>");
					body.append("	</tr>");
					
					
					body.append("<tr>");
					body.append("<td align='center' style='padding:22px;color:#444444;font-size:18px'>");
					body.append(surveyContent);
					body.append("</td>");
					body.append("</tr>");
					
					
					
					body.append("<tr>");
					body.append("<td align='center' style='color:#0093d8;font-size:16px;padding-bottom:25px'>");
					body.append("	<table align='center'>");
					body.append("		<tr align='center'>");
					body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=smsurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("0")+"&idType="+Encryption.encode("1")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_1.png\" width='48' height='48'></a><br>"+very_unsatisfied+"</td>");
					body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=smsurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("0")+"&idType="+Encryption.encode("2")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_2.png\" width='48' height='48'></a><br>"+unsatisfied+"</td>");
					body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=smsurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("0")+"&idType="+Encryption.encode("3")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_3.png\" width='48' height='48'></a><br>"+neutral+"</td>");
					body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=smsurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("1")+"&idType="+Encryption.encode("4")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_4.png\" width='48' height='48'></a><br>"+satisfied+"</td>");
					body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=smsurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("1")+"&idType="+Encryption.encode("5")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_5.png\" width='48' height='48'></a><br>"+very_satisfied+"</td>");
					body.append("			</tr>");
					body.append("		</table>");
					body.append("	</td>");
					body.append("	</tr>");
					
					body.append(" <!--Subscribe section-->");
					body.append("	<tr>");
					body.append("		<td align='center' width='100%' style='background-color:#0bb1db;padding:22px;color:#fff;font-size:18px'>");
					body.append("			• <a href='http://www.jobtopgun.com/AutoLogonServ?encode="+encode+"&key="+key+"&view=setting&locale="+locale+"' style='color:#fff; text-decoration: none;'>"+unsubscribeStr+"</a> • <a href='"+editSuperMatch+"' style='color:#fff;text-decoration: none;'>"+editCriteriaStr+"</a>");
					body.append("		</td>");
					body.append("	</tr>");
					body.append("</table>");
					body.append(img);
					body.append("</body>");
					body.append("</html>"); 
					
					MailManager mailMgr=null;
					try
					{
						System.out.println("5 sentSuperMatch");
						mailMgr=new MailManager();
						mailMgr.setSubject(encodeSubject(subject));
						mailMgr.setRecepient(jsk.getUsername());
						mailMgr.setSender("SuperMatch@jobinthailand.com");
						mailMgr.addHTML(getBodyMail(content,content2,content1,body).toString());
						mailMgr.send();
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	private static  StringBuilder getBodyMail(String content,String content2,String content1,StringBuilder joblistBody)
	{
		StringBuilder ct = new StringBuilder();

		ct.append("<html>");
		ct.append("<head>");
		ct.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		ct.append("<meta name='viewport'	content='width=device-width,initial-scale=1.0'/>");
		ct.append("</head>");
		
		
		ct.append("	<body style='background:#f0f0f0'>");
		ct.append("	<table align='center' width='650' style='border-spacing: 0px;background:#ffffff'>");
		
		ct.append("	<!--BG header-->");
		ct.append("	<tr align='left' >");
		ct.append("		<td >");
		ct.append("	      <div>");
		ct.append("	        	<img src=\"http://mail.jobinthailand.com/content/superMatch/images/header_email.jpg\" style='width:650px;height:100px;'>");
		ct.append("	      </div>");
		ct.append("	     </td>");
		ct.append("	 </tr>");
		ct.append("	 <tr>");
		ct.append("		<td	width='16'>&nbsp;</td>	");
		ct.append("	 </tr>");
		
		
		
		ct.append("		<!--Title header-->");
		ct.append("			<tr align='center' ><td style='padding-top:5px'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/logo_supermatch.png\" width='46' height='45'></td></tr>");
		ct.append("			<tr align='center' ><td style='font-size:26px;color:#6d2a79'>Super Match</td></tr>");
																							
		ct.append("<!--	Job seeker section-->																											");
		ct.append("		<tr>");
		ct.append("			<td style='padding-left:22px;padding-right:22px;padding-top:30px'>");
		ct.append("				<table style='border-bottom:1px solid #6d2a79;padding-bottom:15px;background:#ffffff'' width='100%'>								");
		ct.append("					<tr>");
		ct.append("						<td	width='16'>&nbsp;</td>																											");
		ct.append("						<td width='60' valign='bottom'>"+content1+"</td>");
		ct.append("						<td style='font-size:20px;color:#444444'>"+content+"<br><span style='font-size:22px;color:#0093d8;font-weight:bold'>"+content2+"</span></td>");
		ct.append("					</tr>");
		ct.append("				</table>");
		ct.append("			</td>");
		ct.append("		</tr>");
		ct.append(joblistBody);
		return ct;
	}
	public void sentJobUpdate(int idJsk)
	{
		PropertiesManager propMgr=new PropertiesManager();
		Jobseeker jsk = new JobseekerManager().get(idJsk);
		if(jsk!=null)
		{
			Resume rms = new ResumeManager().get(idJsk, 0);
			if(rms!=null)
			{
				if(!mockupLocale)
				{
					locale=rms.getLocale();
				}
				String language = locale.split("_")[0];
				String name = "";
				if(rms.getIdLanguage()==38)
				{
					name = Util.getStr(rms.getFirstNameThai()).equals("")?Util.getStr(rms.getFirstName()):Util.getStr(rms.getFirstNameThai());
				}
				else
				{
					name = Util.getStr(rms.getFirstName());
				}		
				String content="";
				List<Job>juJobs = JobManager.getJobUpdateJob(idJsk,0);
				int cnt=juJobs.size();
				if(cnt>50) cnt=50;
				String subject = propMgr.getMessage(locale, "CONTENT_EMAIL_JU_SUBJECT")+" "+cnt+" Job(s)";
				if(cnt>0)
				{
					//Job[] juJobs = JobManager.getJobUpdateJob(idJsk,0);
					String htmlJobList = "";
					for(int i=0;i<cnt;i++)
					{
						Job juJob=juJobs.get(i);
						String view="<img border=\"0\" src=\"http://mail.jobinthailand.com/content/jobupdate4_image/Images/view_detail.png\"/>";
						String apply="<img border=\"0\" src=\"http://mail.jobinthailand.com/content/jobupdate4_image/Images/apply_now.png\"/>";
						String url1 = 	"http://www.jobtopgun.com/search/companyList.jsp?id="+juJob.getIdEmp()+"&language="+language;
						String url2 = 	"http://www.jobtopgun.com/search/jobpost.jsp?idEmp="+juJob.getIdEmp()+"&idPosition="+juJob.getIdPosition()+"&language="+language+"&reference=jobmatch&id_jsk="+jsk.getIdJsk();
						String url3 = 	"http://www.superresume.com/SRIX?view=apply&idEmp="+juJob.getIdEmp()+"&idPosition="+juJob.getIdPosition()+"&locale="+locale+"&reference=jobupdate";
						htmlJobList+=	"<tr bgcolor='#ffffff' >" +
										"<td width=\"125\" align=\"center\" valign=\"top\">" +
										"<a href=\""+url1+"\"><img class=\"logo\" src=\"http://mail.jobinthailand.com/logo/lg"+juJob.getIdEmp()+".gif\" border=\"0\" width=\"116\"></a>" +
										"</td>" +
										"<td width=\"350\" align=\"left\"><a href=\""+url2+"\"><font style=\"font-size:14px; font-weight:bold\">"+juJob.getPositionName()+"</font></a>" +
										"<br><br><font style=\"color:#8E8E8E; font-size:13px\"><b>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_EXP")+"</b>:"+JobManager.getExp(Util.getInt(juJob.getExpLess(),0),Util.getInt(juJob.getExpMost(),0),rms.getIdLanguage())+"<br>"+
										"<b>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JM_LOCATION")+"</b>"+JobManager.getJobsLocation(juJob.getIdEmp(), juJob.getIdPosition(), rms.getIdLanguage())+"</font>" +
										"</td>" +
										"<td valign=\"top\">" +
										"<a href=\""+url2+"\">"+view+"</a>"+
										"</td>" +
										"<td>" +
										"&nbsp;"+
										"</td>" +
										"<td valign=\"top\">"+
										"<a href=\""+url3+"\">"+apply+"</a>"+
										"</td>"+
										"</tr>"+
										"<tr>" +
										"<td colspan=\"5\" style=\"border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(212, 212, 212);\">" +
										"</td>" +
										"</tr>";
					}
					
					//headerContent
					content = 	"<table align=\"center\" style=\"width:550px; margin-bottom:10px\"><tr><td>"+
								"<p><b><u>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JU_SUBJECT")+"&nbsp;"+cnt+"&nbsp;Job(s)</u></b></p>" +
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JU_HEADER")+"</p>" +
								"<p><b>"+propMgr.getMessage(locale, "HOME_WELLCOME")+"&nbsp;"+name+",</b></p>" +
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JU_MATCH_1")+"&nbsp;"+cnt+"&nbsp;"+propMgr.getMessage(locale, "CONTENT_EMAIL_JU_MATCH_2")+"</p>"+
								"</td></tr></table>";
					
					//Jobmatch Content
					content+=	"	<div id=\"main\" style=\"width:550px; margin:auto\">	"+
								"		<div id=\"head\" style=\"margin:auto\">	" +
								"			<br>"+
								"	        <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"520\" height=\"95\">	"+
								"	            <tr>	"+
								"	           		<td><img src=\"http://mail.jobinthailand.com/content/jobupdate4_image/Images/header.png\" width=\"520\" height=\"95\"/></td>	"+
								"	            </tr>	"+
								"	        </table>	"+
								"	    </div>	"+
								"	    <div id=\"contents\">	"+
								"	    	<div style=\"padding:10px\">	"+
								"	        	<table width=\"520\" align=\"center\" cellpadding=\"3\">	"+
								htmlJobList+
								"	            </table>	"+
								"	        </div>	"+
								"	    </div>	"+
								"	</div>	";
				}
				else
				{
					content = 	"<table align='center' width='550'><tr><td>"+
								"<p><b><u>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JU_SUBJECT")+"&nbsp;"+cnt+"&nbsp;Job(s)</u></b></p>" +
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JU_HEADER")+"&nbsp;"+name+",</p>" +
								"<p><b>"+propMgr.getMessage(locale, "HOME_WELLCOME")+"&nbsp;"+name+",</b></p>" +
								"<p>"+propMgr.getMessage(locale, "CONTENT_EMAIL_JU_NOTMATCH")+"</p>"+
								"</td></tr></table>";
				}
				if(!content.equals(""))
				{
					String contentMail =	"	<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">	"+
							"	<html xmlns=\"http://www.w3.org/1999/xhtml\">	"+
							"	<head>	"+
							"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />	"+
							"	</head>	"+
							"	<style>	"+
							"	body{	"+
							"		font-family:tahoma;" +
							"		font-size:13px;	"+
							"	}	"+
							"		"+
							"	ul{	"+
							"		font-size: 12px;	"+
							"		padding-left:20px;	"+
							"		padding-bottom:0px;	"+
							"		padding-right:0px;	"+
							"		padding-top:0px;	"+
							"	}	"+
							"		"+
							"	li{	"+
							"		color:#56B5E6; "+
							"		padding-left:0px;	"+
							"	}	"+
							"	</style>	"+
							"		"+
							"	<body>	"+
							content +
							"	</body>	"+
							"	</html>	";				
					
					MailManager mailMgr=null;
					try
					{
						mailMgr=new MailManager();
						mailMgr.setSender("support@jobtopgun.com", "JOBTOPGUN.COM");
						mailMgr.setMailReturn("returnmail@jobinthailand.com");
						mailMgr.setSubject(subject);
						mailMgr.setRecepient(jsk.getUsername());
						mailMgr.addHTML(contentMail);
						mailMgr.send();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}		
		}
	}

	public String getLocale() {
		return locale != null ? locale : "";
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public boolean isMockupLocale() {
		return mockupLocale;
	}

	public void setMockupLocale(boolean mockupLocale) {
		this.mockupLocale = mockupLocale;
	}
	public static String getPhoto(int idJsk)
	{
		String result="http://apps.jobtopgun.com/JTGServices/photoServ.php?url="+Base64.encodeBase64String(Util.getStr(idJsk).getBytes());
		return result;
	}
	public static String encodeSubject(String subject) throws UnsupportedEncodingException
	{
        return toQuotedPrintable(subject);
    }
	public static String toQuotedPrintable(String unicode) throws UnsupportedEncodingException{
    	return MimeUtility.encodeText(unicode, "UTF-8", "Q");
	}

	public int getIdResume() {
		return idResume;
	}

	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}
}
