package javax.topgun.resume;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.ApplyAll;
import com.topgun.resume.Employer;
import com.topgun.resume.EmployerManager;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.MatchResumeAndJob;
import com.topgun.resume.Position;
import com.topgun.resume.PositionManager;
import com.topgun.resume.PositionRequired;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.SendResumeApplyAllThred;
import com.topgun.resume.SendResumeManager;
import com.topgun.resume.SkillComputerManager;
import com.topgun.resume.Track;
import com.topgun.resume.TrackManager;
import com.topgun.resume.WishListManager;
import com.topgun.resume.WorkExperience;
import com.topgun.resume.WorkExperienceManager;
import com.topgun.util.DBManager;
import com.topgun.util.DecryptionLink;
import com.topgun.util.Encryption;
import com.topgun.util.EncryptionLink;
import com.topgun.util.MailManager;
import com.topgun.util.Util;


public class ApplyAllServlet extends HttpServlet 
{
	Resume resume = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int idJsk=Util.getInt(request.getParameter("idJsk"));
		int idResume=Util.getInt(request.getParameter("idResume"));
		String positionsList=request.getParameter("positionsList");
		String positions[]=request.getParameterValues("position");
		String positionStr = "";
		int chkFillResume = Util.getInt(request.getParameter("chkFillResume"));
        int skipExp = Util.getInt(request.getParameter("skipExp"),0);
        int skipSkillCom = Util.getInt(request.getParameter("skipSkillCom"),0);
        int submitExp = Util.getInt(request.getParameter("submitExp"),0);
        int submitSkillCom = Util.getInt(request.getParameter("submitSkillCom"),0);
        int clickSkipExp = Util.getInt(request.getParameter("clickSkipExp"),0);
        String atms="";
        String enc=Util.getStr(request.getParameter("enc"));
        String enc2 = "";
		if(Util.getStr(positionsList)!="")
		{
			positions=positionsList.split("_"); 
		}
		String idState[]=request.getParameterValues("idState");
		if(positions!=null)
		{
			for (String position : positions) {
				if(!positionStr.equals(""))
				{
					positionStr+="_";
				}
				String pos[] = position.split(",");
				int idStates = 0 ;
				if(pos.length>=2)
				{
					String stateStr = pos[0]+"_"+pos[1];
					idStates = Util.getInt(request.getParameter(stateStr),0);
				}
				positionStr+=position+","+idStates;
			}
			positions = positionStr.split("_");
		}
		
		
		String referer=Util.getStr(request.getParameter("referer"));
        String shortReferer="";
        //cookies
        Cookie[] cookies  = request.getCookies();
        if(cookies!=null)
	   	{
	   		for (int i = 0; i < cookies.length; i++)
			{
				Cookie cookie = cookies[i];
				if(cookie.getName().equals("COOKIE_REFERER"))
		        {
					referer=Util.getStr(cookies[i].getValue());
		        }
				else if(cookie.getName().equals("COOKIE_SHORT_REFERER"))
				{
					shortReferer=Util.getStr(cookies[i].getValue());
				}
			}
	   		
	   		Cookie myCookie = new Cookie("COOKIE_REFERER", "");
	   		myCookie.setMaxAge(0);
	   		myCookie.setPath("/");
	   		response.addCookie(myCookie);
	   		myCookie = new Cookie("COOKIE_SHORT_REFERER", "");
	   		myCookie.setMaxAge(0);
	   		myCookie.setPath("/");
	   		response.addCookie(myCookie);
	   	}
        
     // create enc2 parameter for pass to fullfill resume page 
        EncryptionLink encLink = new EncryptionLink() ;
    	encLink.setPositionStr(positionStr);
    	encLink.setIdJsk(idJsk);
    	encLink.setIdResume(idResume);
    	encLink.setAcceptMail("true");
    	encLink.setReferer(referer);
    	encLink.setShortReferer(shortReferer);
    	
    	String docs2[]=request.getParameterValues("docs");
		if(docs2!=null)
		{
			for(int i=0; i<docs2.length; i++)
			{
				if(Util.getInt(docs2[i])>0)
				{
					if(atms.equals(""))
					{
						atms=docs2[i];
					}
					else
					{
						atms+=","+docs2[i];
					}
				}
			}
		}
		int saf2=Util.getInt(request.getParameter("saf"));
		if(saf2>0)
		{
			if(atms.equals(""))
			{
				atms=Util.getStr(saf2);
			}
			else
			{
				atms+=","+saf2;
			}
		}
    	encLink.setAtms(atms);
    	enc2 = EncryptionLink.getEncoding(encLink);
    	
    	// in case pass enc parameter from another page
        if(enc!=null)
        {
        	if(!enc.equals(""))
			{
                DecryptionLink decLink = new DecryptionLink();
                decLink = DecryptionLink.getDecode(enc);
                idJsk=decLink.getIdJsk();
                idResume=decLink.getIdResume();
                positions=decLink.getPositionStr().trim().split("_"); 
                atms=decLink.getAtms();
                referer=decLink.getReferer();
                shortReferer=decLink.getShortReferer();
                enc2 = enc ;
			}
        }
        if(chkFillResume==1)
		{
	        // chk fill responsibility and achievement in exp.
	    	WorkExperience exp = new WorkExperienceManager().getLatestExperienceNew(idJsk, idResume);
	    	if(clickSkipExp == 1)
	    	{
	    		new WorkExperienceManager().addSkipLog(idJsk, idResume);
	    	}
	    	if(exp!=null && skipExp!=1)
	    	{
	    		if(exp.getJobDesc().equals("") || exp.getAchieve().equals(""))
	    		{
	    			new WorkExperienceManager().addExpLog(idJsk, idResume);
	    			response.sendRedirect("/SRIX?view=fillExperience&applyAll=1&idWork="+exp.getId()+"&idResume="+idResume+"&enc="+enc2);
	    			return ;
	    		}
	    	}
	    	
	    	
	    	
	    	// chk fill skill computer if job is IT field.
	    	//boolean chkPositionIT = new PositionManager().chkPositionJobField(idEmp, idPos, 6);
	    	boolean chkPositionIT = false ;
	    	for (int i = 0; i < positions.length ; i++)
			{
				String position[]=positions[i].split(",");
				if(position.length>=2)
				{
					int idEmp = Util.getInt(position[0]);
					int idPos = Util.getInt(position[1]);
					if(idEmp>0 && idPos>0)
					{
						chkPositionIT = new PositionManager().chkPositionJobField(idEmp, idPos, 6);
						if(chkPositionIT)
						{
							break ;
						}
					}
				}
			}
	    	
	    	
	    	if(chkPositionIT && skipSkillCom!=1)
	    	{
	    		if(!new SkillComputerManager().haveSkillComputer(idJsk, idResume))
	    		{
	    			response.sendRedirect("/SRIX?view=fillSkillComputer&applyAll=1&idResume="+idResume+"&enc="+enc2);
	    			return ;
	    		}
	    	}
	    	
	    	
	    	
	    	if(submitExp==1 || submitSkillCom==1)
	    	{
	    		response.sendRedirect("/SRIX?view=checkUpdateResume&applyAll=1&idResume="+idResume+"&enc="+enc2);
    			return ;
	    	}
		}
		
		//ลอง print  state
		if(idJsk<0)
		{
			response.sendRedirect("/LogoutServ");
		}
		else if(idResume<0)
		{
			//System.out.println("error not specify resume");
			response.sendRedirect("/SRIX?view=applyAll&errorId=136");
		}
		else if(positions==null)
		{
			//System.out.println("error position null");
			response.sendRedirect("/SRIX?view=applyAll&errorId=136");
		}
		else
		{
			resume=new ResumeManager().get(idJsk, idResume);
			if(resume==null)
			{
				//System.out.println("error resume not exist");
				response.sendRedirect("/SRIX?view=applyAll&errorId=136");
			}
			else
			{
				//attachment
				String docs[]=request.getParameterValues("docs");
				if(docs!=null)
				{
					for(int i=0; i<docs.length; i++)
					{
						if(Util.getInt(docs[i])>0)
						{
							if(atms.equals(""))
							{
								atms=docs[i];
							}
							else
							{
								atms+=","+docs[i];
							}
						}
					}
				}
				int saf=Util.getInt(request.getParameter("saf"));
				if(saf>0)
				{
					if(atms.equals(""))
					{
						atms=Util.getStr(saf);
					}
					else
					{
						atms+=","+saf;
					}
				}
				//end attachment
				String ip=Util.getStr(request.getRemoteAddr());
				if(ip.equals("203.146.208.152"))
				{
					ip=Util.getStr(request.getHeader("X-Forwarded-For"));
					if(ip.equals(""))
					{
						ip=request.getHeader("Proxy-Client-IP");
					}
				}
				
		    	
		    	
		    	// Apply part
		    	
				ApplyAll applyBean=new ApplyAll();
				applyBean.setAtms(atms);
				applyBean.setIdJsk(idJsk);
				applyBean.setIdResume(idResume);
				applyBean.setIp(ip);
				applyBean.setReferer(referer);
				
				//position=idEmp,idPosition
				int count=0;
				String posCannotApply="";
				String posCanApply="";
				String idTrackStr="";
				
				for (int i = 0; i < positions.length ; i++)
				{
					String position[]=positions[i].split(",");
					if(position.length>=2)
					{
						int idEmp=Util.getInt(position[0]);
						int idPos=Util.getInt(position[1]);
						int idStateNationWide = 0 ;
						
						if(position.length==3)
						{
							idStateNationWide=Util.getInt(position[2]);
						}
						if(idJsk>0 && idResume>=0 && idEmp>0 && idPos>0)
						{
							Timestamp curTime = new Timestamp(new java.util.Date().getTime());
							Timestamp lastTime = new SendResumeManager().getLastTimeApply(idJsk,idResume,idEmp,idPos,"","");
							if(Util.getDayInterval(lastTime,curTime)<=30)
							{
								if(posCannotApply.equals(""))
								{
									posCannotApply+=positions[i];
								}
								else
								{
									posCannotApply+="-"+positions[i];
								}
							}
							else
							{
								applyBean.setIdEmp(idEmp);
								applyBean.setIdPos(idPos);
								applyBean.setIdState(idStateNationWide);
								boolean applyResult=applyJob(applyBean,request);
								//boolean applyResult = false ;
								if(applyResult==false)
								{
									if(posCannotApply.equals(""))
									{
										posCannotApply+=positions[i];
									}
									else
									{
										posCannotApply+="-"+positions[i];
									}
								}
								else
								{
									if(posCanApply.equals(""))
									{
										posCanApply+=positions[i];
									}
									else
									{
										posCanApply+="-"+positions[i];
									}
									int idTrack = new TrackManager().getIdTrackIn2Month(idJsk, idResume, idEmp, idPos);
									if(!idTrackStr.equals(""))
									{
										idTrackStr+=",";
									}
									idTrackStr+=idTrack;
								}
								
							}
							count++;
						}
					}
				}
				new ResumeManager().updateLastApply(idJsk, idResume);
				if(Util.getStr(posCanApply)!="")
				{
					sendAutoReply(posCanApply,idJsk,idResume,resume.getIdLanguage());
				}
				posCannotApply=Encryption.encode(posCannotApply);
				posCanApply=Encryption.encode(posCanApply);
				idTrackStr = Encryption.encode(idTrackStr);
				//response.sendRedirect("/SRIX?view=applyAllResult&idJsk=1673238&jSession=d4627a9f380ec1e373cf19c7a8f1a7604e5cd1fd1aceacf2deeb195947988e4c&posCannotApply="+posCannotApply+"&posCanApply="+posCanApply+"&track="+idTrackStr);
				response.sendRedirect("/SRIX?view=applyAllResult&posCannotApply="+posCannotApply+"&posCanApply="+posCanApply+"&track"+idTrackStr);

			}
		}
	}
	
	private int  sendAutoReply(String posCanApply, int idJsk, int idResume,int idLanguage)
	{
		int result = 0;
		StringBuilder joblist=getContent(posCanApply,idLanguage);
		int countPosition=0;
		if(Util.getStr(posCanApply)!="")
		{
			countPosition = posCanApply.split("-").length;
		}
		if(countPosition>0)
		{
			String subject="Super Resume ของคุณได้ถูกส่งไปสมัครงาน เมื่อวันที่ "+Util.DateToStr(new java.util.Date(), "d/M/yyyy")+" เป็นจำนวน "+countPosition+"  ตำแหน่ง";
			if(resume.getIdLanguage()==11)
			{
				subject="Your Super Resume has been sent to apply on "+Util.DateToStr(new java.util.Date(), "d/M/yyyy")+" for " +countPosition+" jobs.";
			}
			Jobseeker jsk=new JobseekerManager().get(idJsk);
			if(jsk!=null)
			{
				MailManager mail=null;
				try
				{
						mail=new MailManager();
						mail.setSender("autoreply@superresume.com");
						mail.setRecepient(jsk.getUsername());
						mail.setSubject(subject);
						mail.addHTML(getBodyMail(joblist,countPosition).toString());
						mail.send();
						result=1;
				} 
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	private StringBuilder getBodyMail(StringBuilder joblistBody,int countPosition)
	{
		String greeting1="<b   style='color:#1d2455; font-weight:bold;   font-size:15px;'>สวัสดีค่ะ คุณ"+resume.getFirstNameThai()+" </b>";
		String seeMore="<td   style='color:#0060cf;    font-size:15px; text-decoration: none; padding-left:25px;'><a href='http://www.jobtopgun.com/?view=applyRecord&email=1' style='color:0060cf; text-decoration: none'> >> ดูตำแหน่งที่คุณสมัครไปแล้วเพิ่มเติม</a></td>";
		String record="<b   style='color:#2aadd3; font-weight:bold;   font-size:20px;'> บันทึกการสมัครงาน</b>";
		String content="<b   style='color:#2aadd3; font-weight:bold;   font-size:15px;'>สมัครงานเป็น 100 ไม่อั้นในครั้งเดียว </b>";
		String localStr="TH";
		String local="th_TH";
		String contactUs="ติดต่อเรา";
		String checkStatus="<td  style='color:#2b4c6c; font-size:15px; padding-left:25px;'> คุณสามารถตรวจสอบสถานะการสมัครงานทั้งหมดของคุณได้ <a href='http://www.jobtopgun.com/?view=applyRecord' style='color:0060cf; text-decoration: none'>คลิกที่นี่</a></td>";
		String applyMoreJob="<td  style='color:#2b4c6c; font-size:15px; padding-left:25px;'> สมัครงานตำแหน่งอื่นเพิ่มเติมที่  <a href='http://www.jobtopgun.com' style='color:0060cf; text-decoration: none'>www.jobtopgun.com</a></td>";
		String wishYouLuck="<td  style='color:#2b4c6c; font-size:15px; padding-left:25px;'> ขอให้คุณโชคดีในการสมัครงานค่ะ</td>";
		String ourTeam="<td  style='color:#2b4c6c; font-size:15px; padding-left:25px;'> ทีมงาน <a href='http://www.jobtopgun.com/' style='color:#0060cf;'> JOBTOPGUN.com </a>และ <a href='http://www.superresume.com/' style='color:#0060cf;'>SUPERRESUME.com</a></td>";
		String applyDate="<td style='color:#2b4c6c; text-indent:27px; padding-left:25px;'>Super Resume ของคุณได้ถูกส่งไปสมัครงาน เมื่อวันที่ <b style='color:#944926;'>"+Util.DateToStr(new java.util.Date(), "d/M/yyyy")+"</b> เป็นจำนวน <b style='color:#944926;'>" +countPosition+" ตำแหน่ง </b> ดังนี้ </td>";
		if(resume.getIdLanguage() == 11)
		{
			greeting1="<b   style='color:#1d2455; font-weight:bold;   font-size:15px;'>Hi "+resume.getFirstName()+" </b>";
			seeMore="<td   style='color:#0060cf;   font-size:15px; padding-left:25px;'><a href='http://www.jobtopgun.com/?view=applyRecord&email=1'  style='color:0060cf; text-decoration: none'> >> View more job you applied.</a></td>";
			content="<b   style='color:#2aadd3; font-weight:bold;   font-size:15px;'>Apply jobs as much as you want at once.</b>";
			record="<b   style='color:#2aadd3; font-weight:bold;   font-size:20px;'> Job Application History </b>";
			localStr="EN";
			local="en_TH";
			checkStatus="<td style='color:#2b4c6c; font-size:15px; padding-left:25px;'>You can view all your application status<a href='http://www.jobtopgun.com/?view=applyRecord' style='color:0060cf; text-decoration: none'> click here</a></td>";
			applyMoreJob="<td  style='color:#2b4c6c; font-size:15px; padding-left:25px;'> Apply more jobs at  <a href='http://www.jobtopgun.com' style='color:0060cf; text-decoration: none'>www.jobtopgun.com</a></td>";
			wishYouLuck="<td  style='color:#2b4c6c; font-size:15px; padding-left:25px;'> Good Luck with your job application.</td>";
			ourTeam="<td  style='color:#2b4c6c; font-size:15px; padding-left:25px;'>  <a href='http://www.jobtopgun.com/' style='color:#0060cf;'>JOBTOPGUN.com </a>and   <a href='http://www.superresume.com/' style='color:#0060cf;'>SUPERRESUME.com </a>Team.</td>";
			applyDate="<td style='color:#2b4c6c; text-indent:29px; padding-left:25px;'>Your Super Resume has been sent to apply on  <b style='color:#944926;'>"+Util.DateToStr(new java.util.Date(), "d/M/yyyy")+"</b> for <b style='color:#944926;'>" +countPosition+" jobs.</b></td>";
			contactUs="Contact us ";
		}
		StringBuilder ct = new StringBuilder();
		ct.append("<html>");
		ct.append("<head>");
		ct.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		ct.append("<meta name='viewport'	content='width=device-width,initial-scale=1.0'/>");
		ct.append("</head>");
		ct.append("<table align='center' style='width:590px;' border='0'>");  
		ct.append("<tr>");  
		ct.append("		<td align='middle' valign='top'>");  
		ct.append("			<img style='text-center' src=\"http://mail.jobinthailand.com/content/superMatch/images/View.jpg\" />");												
		ct.append("		</td> "); 
		ct.append("</tr> "); 
		ct.append("	<tr>");
		ct.append("		<td height='10'> "); 
		ct.append("		</td> "); 
		ct.append("</tr> "); 
		ct.append("	<tr>");
		ct.append("		<td align='center' style='color:#2aadd3; font-size:15px;'>");
		ct.append(		record);
		ct.append("		</td>");
		ct.append("	</tr>");
		ct.append("	<tr>");
		ct.append("		<td align='center'>");
		ct.append("			<hr style='width: 80%; height: 2px; background-color:#2aadd3; color:#2aadd3; border: 0 none;'>	");
		ct.append("		</td>");
		ct.append("	</tr>");
		ct.append("</table>"); 
		ct.append("<table border=0 width=590 align='center'>");
		ct.append("	<tr>");
		ct.append("		<td style='color:#1d2455; padding-left:25px;'>"+greeting1+"</td>");
		ct.append("	</tr>");
		ct.append("	<tr>");
		ct.append("		<td height='8'></td>");
		ct.append("	</tr>");
		ct.append("	<tr>");
		ct.append(applyDate);
		ct.append("	</tr>");
		ct.append("	<tr>");
		ct.append("		<td height='8'>&nbsp;&nbsp;</td>");
		ct.append("	</tr>");
		ct.append("</table>");
		ct.append("<!--JOBLISTs-->");
		ct.append(" <table border=0 width=590 align='center'>	");
		ct.append(" <tr>	");
		ct.append("     <td>	");
		ct.append(joblistBody);
		ct.append("     </td>	");
		ct.append(" </tr>	");
		if(countPosition>10)
		{
			ct.append(" <tr>	");
			ct.append(seeMore);
			ct.append(" </tr>	");
			ct.append(" <tr>	");
			ct.append(" 	<td height='10'>&nbsp;&nbsp;</td>");
			ct.append(" <tr>	");
		}
		ct.append("<tr>	");
		ct.append(		checkStatus);
		ct.append(" </tr>	");
		ct.append(" <tr>	");
		ct.append(		applyMoreJob);
		ct.append(" </tr>	");
		ct.append(" <tr>	");
		ct.append(" 	<td height='10'>&nbsp;&nbsp;</td>");
		ct.append(" <tr>	");
		ct.append(" <tr>	");
		ct.append(		wishYouLuck);
		ct.append(" </tr>	");
		ct.append(" <tr>	");
		ct.append(		ourTeam);
		ct.append(" </tr>	");
		ct.append(" </table>	");
		
		ct.append("<br>");
		ct.append("<table border='0' style='width:590px;' align='center'>");
		ct.append("<tr>");
		ct.append("		<td style='margin:auto' align='middle'>");
		ct.append("		</td>");
		ct.append("</tr>");
		ct.append("<tr valign='top'>");
		ct.append("	<td><br>");
		ct.append("		<div style='margin:auto' align='left' padding-top='10px'>");
		ct.append("			<a href='https://www.facebook.com/JobTopGun'><img src='http://mail.jobinthailand.com/content/superMatch/images/Icon_Facebook.png' /></a>");
		ct.append("			<a href='https://www.youtube.com/playlist?list=PL_iAr5G3PB_I-0ECktHbYtgKgJ4m1w_OU'><img src='http://mail.jobinthailand.com/content/superMatch/images/Icon_youtube.png' /></a>");
		ct.append("		</div>");
		ct.append("	</td>");
		ct.append("</tr>");
		ct.append("</table>");
		
		ct.append("<table style='width:590px;' border='0'  align='center'>");  
		ct.append("<tr>");  
		ct.append("		<td align='right' valign='top' colspan='2'>");  
		ct.append("			<img src='http://mail.jobinthailand.com/content/superMatch/images/"+localStr+"/Banner.jpg'>"); 
		ct.append("		</td> "); 
		ct.append("</tr> "); 
		ct.append("<tr> "); 
		ct.append("		<td  align='right' style='padding:10px'> "); 
		ct.append("			<div><a href='https://itunes.apple.com/th/app/jobtopgun/id605367531?mt=8'><img class='logo' src='http://mail.jobinthailand.com/content/superMatch/images/"+localStr+"/btn_appStore.png' border='0'/></a></div> "); 
		ct.append("		</td> "); 
		ct.append("		<td  align='left' style='padding:10px' > "); 
		ct.append("			<div><a href='https://play.google.com/store/apps/details?id=com.topgun.jobtopgun&hl=th'><img class='logo' src='http://mail.jobinthailand.com/content/superMatch/images/"+localStr+"/btn_googleplay.png' border='0'/></a></div> "); 
		ct.append("		</td> "); 
		ct.append("</tr> "); 
		ct.append("<tr> "); 
		ct.append("		<td colspan='2' style='height:20px;' ></td>"); 
		ct.append("</tr> "); 
		ct.append("</table>"); 
		ct.append("<table border='0' style='width:590px;'  align='center'>");
		ct.append("	<tr>");
		ct.append("		<td border='0' style='width:100%;' align='middle'>");
		ct.append("			<div style='margin:auto'><img src='http://mail.jobinthailand.com/content/superMatch/images/Icon_Super-Apply.png' /></div>");
		ct.append("		</td>");
		ct.append("	</tr>");
		ct.append("	<tr>");
		ct.append("		<td align='middle'>");
		ct.append("			<div color4f84c4' style='color:#2aadd3; font-weight:bold; font-size:15px;'>Super Apply</div>");
		ct.append("		</td>");
		ct.append("	</tr>");
		ct.append("	<tr>");
		ct.append("		<td align='middle'>");
		ct.append("			<div style='color:#4f84c4; font-size:15px;'>"+content+"</div>");
		ct.append("		</td>");
		ct.append("	</tr>");
		ct.append("	<tr>");
		ct.append("		<td align='middle'>");
		ct.append("			<div style='font-size:15px;'><a href='http://www.jobtopgun.com/?view=comment&locale="+local+"' target='_blank'>"+contactUs+"</a></div>");
		ct.append("		</td>");
		ct.append("	</tr>");
		ct.append("</table>");
		
		ct.append("</body>");
		ct.append("</html>");									
		return ct;
		
	}
	private StringBuilder getContent(String posCanApply,int idLanguage)
	{
		StringBuilder body=new StringBuilder();
		String[] pos=posCanApply.split("-");
		int size = 10;
		if(pos.length<size)
		{
			size=pos.length;
		}
		for(int i=0;i<size;i++)
		{
			String posList[]=pos[i].split(",");
			if(posList.length==2)
			{
				int idEmp=Util.getInt(posList[0]);
				int idPos=Util.getInt(posList[1]);
				Position position=new PositionManager().getPosition(idEmp,idPos);
				Employer com=new EmployerManager().get(idEmp);
				String comName = "";
				position.getPositionName();
				if(idLanguage == 11)
				{
					comName = com.getNameEng();
				}
				else
				{
					comName = com.getNameThai();
				}
				body.append("<table border='0' style='width:590px;'> "); 
				body.append("<tr> ");
				body.append("	<td  valign='top' rowspan='2' style='color:#1d2354; font-size:15px; width:25px; padding-left:25px;'> ");
				body.append(			(i+1)+".");
				body.append("	</td> ");
				
				body.append("	<td valign='top' style='color:#0060cf; font-size:15px;  font-weight:bold;'> ");
				body.append(		position.getPositionName());
				body.append("	</td> ");
				body.append("</tr> ");
				
				body.append("<tr> ");
				body.append("	<td valign='top' style='color:#525454; font-size:15px;   font-weight:bold;'> ");
				body.append(		comName);
				body.append("	</td> ");
				body.append("</tr> ");
				body.append("<tr> ");
				body.append("	<td height='8'>&nbsp; ");
				body.append("	</td> ");
				body.append("</tr> ");
				body.append("</table> ");
			}
		}
		return body;
	}
	
	private boolean  applyJob(ApplyAll applyBean,HttpServletRequest request)
	{
		boolean result=false;
		Position pos=new PositionManager().getPosition(applyBean.getIdEmp(),applyBean.getIdPos());
		Employer com=new EmployerManager().get(applyBean.getIdEmp());
		String company=com.getNameEng();
		String email=pos.getHrEmail();
		String position=pos.getPositionName();
		boolean acceptMail=new EmployerManager().isAcceptMail(applyBean.getIdEmp());
		String source="Jobtopgun";
		if(email==null||email.equals(""))
		{
			acceptMail=false;
		}
		Track track=new Track();
		track.setIdJsk(applyBean.getIdJsk());
		track.setIdResume(applyBean.getIdResume());
		track.setIdEmp(applyBean.getIdEmp());
		track.setIdPosition(applyBean.getIdPos());
		track.setAttachments(applyBean.getAtms());
		track.setEmpOther(company);
		track.setIp(applyBean.getIp());
		track.setPositionOther(position);
		track.setRecipient(email);
		track.setSource(source);
		track.setReferer(applyBean.getReferer());
		track.setSent(0);
		if(acceptMail==true)
		{
			track.setSent(0);
		}
		else
		{
			track.setSent(1);
		}
		int rsTrack =0;
		if(new SendResumeManager().canApplyJob(applyBean.getIdJsk(), applyBean.getIdResume(), applyBean.getIdEmp(), applyBean.getIdPos(), email, position))
		{
			rsTrack=new TrackManager().add(track);
		}
		if((applyBean.getIdEmp()>0)&&(applyBean.getIdPos()>0) && rsTrack==1) // match resume and job for stat
		{
			new TrackManager().addApplyLog(track);
			try
			{
			  List<PositionRequired> posList =  MatchResumeAndJob.getPositionRequired(applyBean.getIdEmp(),applyBean.getIdPos());
			  MatchResumeAndJob.compareJobAndResume(applyBean.getIdJsk(),applyBean.getIdResume(), posList );
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(rsTrack==1)
		{
			new TrackManager().addTrackAll(applyBean.getIdJsk(), applyBean.getIdResume(), applyBean.getIdEmp(), company, applyBean.getIdPos(), position,"");
			int idTrack=new TrackManager().getTrackId(applyBean.getIdJsk(), applyBean.getIdResume());
			track.setIdTrack(Util.getInt(idTrack));
			//String state=applyBean.getIdEmp()+"_"+applyBean.getIdPos();
			int idState= applyBean.getIdState();
			if(idState>0)
			{
				insertToTrackNationWideJob(idTrack, idState);
			}
			new SendResumeApplyAllThred(track).start();
			new WishListManager().deleteWishList(applyBean.getIdJsk(), applyBean.getIdEmp(), applyBean.getIdPos());
			result=true;
		}
		return result;
	}
	
	private int  insertToTrackNationWideJob(int idTrack, int idState) 
	{
		int result=0;
			String sql=	"INSERT INTO INTER_TRACK_NATIONWIDE(ID_TRACK, ID_STATE) VALUES(?,?)";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setInt(1, idTrack);
				db.setInt(2, idState);
				result=db.executeUpdate();	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				db.close();
			}
		return result;
	}

}
