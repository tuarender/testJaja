package com.topgun.criteriasearch;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.topgun.util.DBManager;
import com.topgun.util.Encryption;
import com.topgun.util.MailManager;
import com.topgun.util.Util;

public class CriteriaManager 
{
	private int idJsk;
	private int idResume;
	private int totalJob;
	private String mailFlag = "";
	
	public int sendCriteriaSearch(int idJsk, int idResume)
	{
		Resume resume = this.createResume(idJsk, idResume);
		CriteriaSearchFactory factory = new CriteriaSearchFactory();
		factory.createSearch(idJsk, idResume);
		if(factory.getSearch() != null)
		{
			resume.setIdSearch(factory.getIdSearch());
			resume.setCriteriaSearchMailFlag("TRUE");
			return sendJobMatch(resume);
		}
		return 0;
	}
	
	private Resume createResume(int idJsk, int idResume)
	{
		Resume resume = null;
		DBManager db = null;
		String sql="  SELECT  "
				+ "		B.ID_JSK, C.ID_RESUME, B.USERNAME, "
				+ "		C.FIRST_NAME, C.FIRST_NAME_TH,  C.LAST_NAME, C.LAST_NAME_TH, C.ID_LANGUAGE "
				+ "	FROM   "
				+ "		INTER_JOBSEEKER B, INTER_RESUME C "
				+ " WHERE       "
				+ "		B.ID_JSK = C.ID_JSK "
				+ " 	AND C.ID_JSK = ? "
				+ "		AND C.ID_RESUME = ? ";
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{
				resume = new Resume();
				resume.setIdJsk(db.getInt("ID_JSK"));
				resume.setIdResume(db.getInt("ID_RESUME"));
				resume.setIdLanguage(db.getInt("ID_LANGUAGE"));
				//resume.setIdSearch(db.getInt("ID_SEARCH"));
				resume.setUsername(db.getString("USERNAME"));
				resume.setIdLanguage(db.getInt("ID_LANGUAGE"));
				if(resume.getIdLanguage()==11)
				{
					if(db.getString("FIRST_NAME") == null)
					{
						resume.setFirstName(db.getString("FIRST_NAME_TH"));
						resume.setLastName(db.getString("LAST_NAME_TH"));
					}
					else
					{
						resume.setFirstName(db.getString("FIRST_NAME"));
						resume.setLastName(db.getString("LAST_NAME"));
					}
				}
				else
				{
					if(db.getString("FIRST_NAME_TH") == null)
					{
						resume.setFirstName(db.getString("FIRST_NAME"));
						resume.setLastName(db.getString("LAST_NAME"));
					}
					else
					{
						resume.setFirstName(db.getString("FIRST_NAME_TH"));
						resume.setLastName(db.getString("LAST_NAME_TH"));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return resume;
	}
	
	public int sendJobMatch(Resume resume)
	{
		int result=0;
		String today=Util.nowDateFormat("yyyyMMdd");
		CriteriaSearch saveSearch=new CriteriaSearch(resume);
		JobListJSON jobListJson = saveSearch.getJobList();
		List<JobList> jobLists=jobListJson.getData();
		String descriptionSearch = saveSearch.getSearchDescription();
		String description = "";
		String descriptionCheck = "";
		if(descriptionSearch.length()>200)
		{
		     descriptionCheck = descriptionSearch.substring(0,120);
			 description = "<span style='color:#444444;decoration:none;'>"+descriptionCheck+"...</span>";
		}
		else
		{
			 description = "<span style='color:#444444;decoration:none;'>"+descriptionSearch+"</span>";
		}
		
		//System.out.println("ID_JSK = "+resume.getIdJsk()+", ID_SEARCH = "+resume.getIdSearch()+", MAIL="+resume.getCriteriaSearchMailFlag()+", JOB COUNT = "+jobListJson.getTotal());
		if(jobLists != null && jobLists.size()>0 && jobListJson.getTotal() > 0)
		{
			String subject=jobListJson.getTotal()+" งานใหม่สำหรับ Job Update ของคุณ";
			//String subject=" welcome";
			String locale="th_TH";
			String photo="<div align='center' style='margin:auto'><img class='img-circle' width='50' height='50' style='border-radius:25px' src='"+getPhoto(resume.getIdJsk())+"' /></div>";
			String greeting1="<b  style='font-size:20px;color:#444444'>สวัสดีคุณ "+resume.getFirstName()+" </b>";
			String greeting2="<b  style='font-size:20px;color:#444444'> คุณมีงานตามเงื่อนไขที่คุณต้องการ <span style='font-size:22px;color:#0093d8;font-weight:bold'>"+jobListJson.getTotal()+"</span> ตำแหน่ง</b>";
			String criteria="<span style='color:#444444;decoration:none;'>เงื่อนไข : "+description+"</span>";
			if(resume.getIdLanguage()==11)
			{
				subject=jobListJson.getTotal()+(jobListJson.getTotal() > 1 ? " jobs" : " job")+" from Job Update";
				locale="en_TH";
				//subject="welcome";
				greeting1="<b  style='font-size:20px;color:#444444'>Hello "+resume.getFirstName()+" </b>";
				greeting2="<b  style='font-size:20px;color:#444444'>You have <span style='font-size:22px;color:#0093d8;font-weight:bold'>"+jobListJson.getTotal()+"</span> "+(jobListJson.getTotal() > 1 ? " jobs" : " job")+" from your criteria search.</b>";
				criteria="<span style='color:#444444;decoration:none;'>Criteria : "+description+"</span>";
			}
			
			String content=greeting1+greeting2+"<br><hr class='hrone' style='background-color:#e03a16; height:1px; border:1px;'><br>";
			String jlHtml=getJobListHtmlTH(resume, jobListJson) ;
			String bodyMail=getBodyMail(greeting1,greeting2,photo,criteria,jlHtml);

			MailManager mail=null;
			try
			{
				mail=new MailManager();
				//mail.setSenderName("JOBTOPGUN.COM");
				mail.setMailReturn("returnmail@jobinthailand.com");
				mail.setSender("jobtopgun@jobtopgun.com");
				mail.setRecepient(resume.getUsername());
				mail.setSubject(subject);
				mail.addHTML(bodyMail);
				mail.send();
			} 
			catch(Exception e)
			{
				System.out.println("SEND ERROR : "+resume.getIdJsk()+"\n => "+e.getMessage());
				e.printStackTrace();
			}

		}
		
		System.out.println("DONE!    idJsk="+resume.getIdJsk()+" idSearch="+resume.getIdSearch()+" Criteria_Mail_Flag="+resume.getCriteriaSearchMailFlag()+"  total ="+jobListJson.getTotal());
		this.idJsk = resume.getIdJsk();
		this.idResume = resume.getIdResume();
		this.mailFlag = resume.getCriteriaSearchMailFlag();
		this.totalJob = jobListJson.getTotal();
		return result;
	}
	
    public static String getPhoto(int idJsk)
	{
		String result="http://apps.jobtopgun.com/JTGServices/photoServ.php?url="+Base64.encodeBase64String(Util.getStr(idJsk).getBytes());
		return result;
	}
	
	public static String  getJobListHtmlTH(Resume resume, JobListJSON jobListJson) 
	{
		List<JobList> jobLists = jobListJson.getData();
		StringBuilder body = new StringBuilder();
		String expStr="ประสบการณ์ : ";
		String locationStr="สถานที่ : ";
		String localStr="TH";
		String criteriaStr="งานตามเงื่อนไขที่บันทึกไว้";
		String contactUsStr="ติดต่อเรา";
		String unsubscribeStr="ยกเลิกอีเมล";
		String editCriteriaStr="หากงานไม่ถูกใจ ปรับเปลี่ยนเงื่อนไขได้ที่นี่";
		String locale="th_TH";
		String surveyContent="ให้คะแนน <span style='font-size:22px;'> ความพอใจและแสดงความคิดเห็น </span> ผลการจับคู่ตำแหน่งนี้";
		String apply="สมัครงาน";
		String very_unsatisfied="ไม่พอใจมาก";
		String unsatisfied="ไม่พอใจ";
		String neutral="ปานกลาง";
		String satisfied="พอใจ";
		String seeMoreJobs="ดูตำแหน่งงานทั้งหมดตามเงื่อนไขนี้";
		String very_satisfied="พอใจมาก";
		String salaryStr="เงินเดือน : ";
		
		
		if(resume.getIdLanguage()==11)
		{
			criteriaStr="My Criteria Search";
			locale="en_TH";
			expStr="Year Exp : ";
			locationStr="Location : ";
			localStr="EN";
			contactUsStr="Contact Us";
			unsubscribeStr="Unsubscribe";
			editCriteriaStr="If you're not satisfied with your job,edit here";
			surveyContent="Rate your <span style='font-size:22px;'>  satisfaction and leave your comment </span> for Job update";
			apply="Apply";
			very_unsatisfied="very unsatisfied";
			unsatisfied="unsatisfied";
			neutral="neutral";
			satisfied="satisfied";
			seeMoreJobs="View all Job Update";
			very_satisfied="very satisfied";
			salaryStr="Salary : ";
		}
		if(jobListJson.getTotal()>0)
		{
			body.append("<img src=\"http://rider1.jobinthailand.com/open_criteria_search.php?criteriaDate="+Util.nowDateFormat("yyyyMMdd")+"&idJsk="+resume.getIdJsk()+"\" width=\"1\" height=\"1\">");
			String today=Util.nowDateFormat("yyyyMMdd");
			String analytics="&utm_source=jobtopgun.com&utm_medium=email&utm_content=criteria_search_"+today+"&utm_campaign=criteria_search";
			String encode = Encryption.getEncoding(0,0,resume.getIdJsk(),resume.getIdResume());
			String key = Encryption.getKey(0,0,resume.getIdJsk(),resume.getIdResume());
			
			String vmlink="http://www.jobtopgun.com/AutoLogonServ?encode="+encode+"&key="+key+"&view=criteriaList&idSearch="+resume.getIdSearch()+"&type=email&locale="+locale+"&id="+resume.getIdJsk()+"&PositionId=1&EmployerId=1&Member=1&reference=criteria_search&criteriaDate="+today+analytics;
			String viewMoreLink="http://rider1.jobinthailand.com/click_criteria_search.php?url="+Util.base64(vmlink);
			body.append(" <!--Button bottom section-->");
			body.append("	<tr>");
			body.append("		<td align='center' style='padding:40px 40px 15px 40px;'>");
			body.append("			<a href='"+viewMoreLink+"'  style='text-decoration: none;'><button style='padding:8px;border-radius:5px;width:300px;font-size:18px;color:#ffffff;background-color:#05786d;border:1px solid #05786d;cursor:pointer'>"+seeMoreJobs+"</button></a>	");
			body.append("		</td>");
			body.append("	</tr>");
			body.append("<tr>");
			body.append("<td style='padding-left:22px;padding-right:22px;padding-top:15px'>");
			for(int i=0; i<jobLists.size(); i++)
			{
				body.append("<table style='border-bottom:1px solid #d9d9d9;padding-bottom:30px;padding-top:20px' width='100%' >");
				body.append("	<tr valign='top'>");
				body.append("		<td width='110'><img style='border:1px solid #d9d9d9' src='http://mail.jobinthailand.com/logo/lg"+jobLists.get(i).getIdEmp()+".gif'' width='106' height='40'></td>");
				body.append("		<td width='40' align='right' style='font-size:20px;color:#0060cf;font-weight:bold;vertical-align:baseline'>	<span style='color:white;font-size:20px;'>&#3670;</span>"+(i+1)+".</td>");
				body.append("		<td align='left'>");
				String click="http://rider1.jobinthailand.com/click_criteria_search.php?url="+Util.base64("http://www.jobtopgun.com/StartSearch?locale="+locale+"&idEmp="+jobLists.get(i).getIdEmp()+"&idPosition="+jobLists.get(i).getIdPosition()+"&criteriaDate="+today+"&id="+resume.getIdJsk());
				body.append("		<a href='"+click+"'  style='font-size:20px;color:#0060cf;font-weight:bold'>"+jobLists.get(i).getPositionName()+"</a ><span style='color:white;text-decoration:none;font-size:20px;'>&#3670;</span><br>");
				body.append("        <div style='height:8px'></div>");
				body.append("		<span style='font-size:20px;color:#0093d8;padding-top:5px'>"+jobLists.get(i).getNameEng()+"</span><br>");
				if(resume.getIdLanguage()==11)
				{
					body.append("<span style='font-size:18px;color:#666666'  >"+locationStr+"</span><span style='font-size:18px;color:#444444' >"+jobLists.get(i).getLocationEng()+"</span>");
					if(jobLists.get(i).getShowSalary()==1 && jobLists.get(i).getSalaryLess()>0 && jobLists.get(i).getSalaryMost()>0)
					{
						body.append("<br><span style='font-size:18px;color:#666666' >"+salaryStr+"</span><span style='font-size:18px;color:#444444' >"+jobLists.get(i).getSalaryEng()+"</span>");
					}
				}
				else
				{
					body.append("<span style='font-size:18px;color:#666666' >"+locationStr+"</span><span style='font-size:18px;color:#444444' >"+jobLists.get(i).getLocationTha()+"</span>");
					if(jobLists.get(i).getShowSalary()==1 && jobLists.get(i).getSalaryLess()>0 && jobLists.get(i).getSalaryMost()>0)
					{
						body.append("<br><span style='font-size:18px;color:#666666' >"+salaryStr+"</span><span style='font-size:18px;color:#444444' >"+jobLists.get(i).getSalaryTha()+"</span>");
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
			body.append("			<a href='"+viewMoreLink+"'  style='text-decoration: none;'><button style='padding:8px;border-radius:5px;width:300px;font-size:18px;color:#ffffff;background-color:#05786d;border:1px solid #05786d;cursor:pointer'>"+seeMoreJobs+"</button></a>	");
			body.append("		</td>");
			body.append("	</tr>");
			
			body.append(" <!--Divider section-->");
			body.append("	<tr>");
			body.append("		<td align='center' width='100%' height='10px' style='background-color:#f0f0f0;''>");
			body.append("		</td>");
			body.append("	</tr>");
			
			
			body.append("<tr>");
			body.append("	<td align='center' style='padding:22px;color:#444444;font-size:18px'>");
			body.append(surveyContent);
			body.append("	</td>");
			body.append("</tr>");
			
			
			
			body.append("<tr>");
			body.append("<td align='center' style='color:#0093d8;font-size:16px;padding-bottom:25px'>");
			body.append("	<table align='center'>");
			body.append("		<tr align='center'>");
			body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=criteriaSearchSurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("0")+"&idType="+Encryption.encode("1")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"&idSearch="+resume.getIdSearch()+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_1.png\" width='48' height='48'></a><br>"+very_unsatisfied+"</td>");
			body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=criteriaSearchSurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("0")+"&idType="+Encryption.encode("2")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"&idSearch="+resume.getIdSearch()+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_2.png\" width='48' height='48'></a><br>"+unsatisfied+"</td>");
			body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=criteriaSearchSurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("0")+"&idType="+Encryption.encode("3")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"&idSearch="+resume.getIdSearch()+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_3.png\" width='48' height='48'></a><br>"+neutral+"</td>");
			body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=criteriaSearchSurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("1")+"&idType="+Encryption.encode("4")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"&idSearch="+resume.getIdSearch()+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_4.png\" width='48' height='48'></a><br>"+satisfied+"</td>");
			body.append("			<td width='90' valign='top' style='color:#0093d8;font-size:16px;padding-bottom:25px'><a href='http://www.jobtopgun.com/?view=criteriaSearchSurvey&locale="+locale+"&login=0&encode="+encode+"&key="+key+"&surveyType="+Encryption.encode("1")+"&idType="+Encryption.encode("5")+"&smdate="+Util.nowDateFormat("yyyyMMdd")+"&idSearch="+resume.getIdSearch()+"'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/emo_5.png\" width='48' height='48'></a><br>"+very_satisfied+"</td>");
			body.append("		</tr>");
			body.append("	</table>");
			body.append("</td>");
			body.append("</tr>");
			
			body.append(" <!--Subscribe section-->");
			body.append("	<tr>");
			body.append("		<td align='center' width='100%' style='background-color:#0bb1db;padding:22px;color:#fff;font-size:18px'>");
			body.append("			• <a href='http://www.jobtopgun.com/AutoLogonServ?encode="+encode+"&key="+key+"&view=settingEmailCriteria&idSearch="+resume.getIdSearch()+"&id="+resume.getIdSearch()+"&locale="+locale+"' style='color:#fff; text-decoration: none;'>"+unsubscribeStr+"</a>  • <a href='http://www.jobtopgun.com/AutoLogonServ?encode="+encode+"&key="+key+"&view=saveSearch&idSearch="+resume.getIdSearch()+"&type=email&locale="+locale+"' style='color:#fff;text-decoration: none;'>"+editCriteriaStr+"</a>");
			body.append("		</td>");
			body.append("	</tr>");
			body.append("</table>");
			body.append("</body>");
			body.append("</html>"); 
		
		}
		return body.toString();
	}
	
	public static  String getBodyMail(String greeting1,String greeting2,String photo,String criteria,String joblistBody)
	{
		StringBuilder ct = new StringBuilder();
		ct.append("<!DOCTYPE>");
		ct.append("<html>");
		ct.append("<head>");
		ct.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		ct.append("<meta name='viewport'	content='width=device-width,initial-scale=1.0'/>");
		ct.append("<title>jobAlert	(responsive)</title>");
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
		ct.append("			<tr align='center' ><td style='padding-top:5px'><img src=\"http://mail.jobinthailand.com/content/superMatch/images/logo_save.png\" width='46' height='45'></td></tr>");
		ct.append("			<tr align='center' ><td style='font-size:26px;color:#05786d'>Job Update</td></tr>");
																							
		ct.append("<!--	Job seeker section-->																											");
		ct.append("		<tr>");
		ct.append("			<td style='padding-left:22px;padding-right:22px;padding-top:30px'>");
		ct.append("				<table style='border-bottom:1px solid #05786d;padding-bottom:15px;background:#ffffff'' width='100%'>								");
		ct.append("					<tr>");
		ct.append("						<td	width='16'>&nbsp;</td>																											");
		ct.append("						<td width='60' valign='bottom'>"+photo+"</td>");
		ct.append("						<td style='font-size:20px;color:#444444'>"+greeting1+"<br><span style='font-size:22px;color:#0093d8;font-weight:bold'>"+greeting2+"</span></td>");
		ct.append("					</tr>");
		ct.append("				</table>");
		ct.append("			</td>");
		ct.append("		</tr>");
		ct.append("		<tr>");
		ct.append("			<td style='padding-left:22px;padding-right:22px;padding-top:15px;color:#444444;font-size:18px'>");
		ct.append(criteria);
		ct.append("			</td>");
		ct.append("		</tr>");
		ct.append(joblistBody);
		return ct.toString();
	}

	public int getIdJsk() {
		return idJsk;
	}

	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}

	public int getIdResume() {
		return idResume;
	}

	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}

	public int getTotalJob() {
		return totalJob;
	}

	public void setTotalJob(int totalJob) {
		this.totalJob = totalJob;
	}

	public String getMailFlag() {
		return mailFlag;
	}

	public void setMailFlag(String mailFlag) {
		this.mailFlag = mailFlag;
	}

}
