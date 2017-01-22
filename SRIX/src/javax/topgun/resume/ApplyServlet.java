package javax.topgun.resume;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.Education;
import com.topgun.resume.EducationManager;
import com.topgun.resume.Employer;
import com.topgun.resume.EmployerManager;
import com.topgun.resume.Position;
import com.topgun.resume.PositionManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.SendResumeManager;
import com.topgun.resume.SendResumeThread;
import com.topgun.resume.SkillComputerManager;
import com.topgun.resume.TrackManager;
import com.topgun.resume.WishListManager;
import com.topgun.resume.MatchResumeAndJob;
import com.topgun.resume.PositionRequired;
import com.topgun.resume.Track;
import com.topgun.resume.WorkExperience;
import com.topgun.resume.WorkExperienceManager;
import com.topgun.shris.masterdata.Language;
import com.topgun.util.DBManager;
import com.topgun.util.DecryptionLink;
import com.topgun.util.EncryptionLink;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class ApplyServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String enc=Util.getStr(request.getParameter("enc"));
		if(!enc.equals(""))
		{
			doPost(request,response);
		}
		else
		{
			response.sendRedirect("/LogoutServ");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		int idResume=Util.getInt(request.getParameter("idResume"));
		int idEmp=Util.getInt(request.getParameter("idEmp"));
		int idPos=Util.getInt(request.getParameter("idPos"));
		String company=Util.getStr(request.getParameter("company"));
		String position=Util.getStr(request.getParameter("position"));
		String email=Util.getStr(request.getParameter("email"));
		boolean acceptMail=true;
		String source="SuperResume";
		String cmd=Util.getStr(request.getParameter("cmd"));
        String state = Util.getStr(request.getParameter("state"), "0@0");
        int idState=Util.getInt(request.getParameter("idState"));
        String atms="";
        String enc=Util.getStr(request.getParameter("enc"));
        String enc2 = "";
        String locale = "";
        int chkFillResume = Util.getInt(request.getParameter("chkFillResume"));
        int skipExp = Util.getInt(request.getParameter("skipExp"),0);
        int skipSkillCom = Util.getInt(request.getParameter("skipSkillCom"),0);
        int submitExp = Util.getInt(request.getParameter("submitExp"),0);
        int submitSkillCom = Util.getInt(request.getParameter("submitSkillCom"),0);
        int clickSkipExp = Util.getInt(request.getParameter("clickSkipExp"),0);
        int clickSkipSkillCom = Util.getInt(request.getParameter("clickSkipSkillCom"),0);
        
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
    	encLink.setIdEmp(idEmp);
    	encLink.setIdPos(idPos);
    	encLink.setIdJsk(idJsk);
    	encLink.setIdResume(idResume);
    	encLink.setCompany(company);
    	encLink.setPosition(position);
    	encLink.setEmail(email);
    	encLink.setAcceptMail("true");
    	encLink.setSource(source);
    	encLink.setCmd(cmd);
    	encLink.setState(state);
    	encLink.setIdState(idState);
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
                idEmp=decLink.getIdEmp();
                idPos=decLink.getIdPos();
                company=decLink.getCompany();
                position=decLink.getPosition();
                email=decLink.getEmail();
                if(decLink.getAcceptMail().equals("false")){acceptMail=false;}
                source=decLink.getSource();
                cmd=decLink.getCmd();
                state=decLink.getState();
                idState=decLink.getIdState();
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
	    			response.sendRedirect("/SRIX?view=fillExperience&idWork="+exp.getId()+"&idResume="+idResume+"&enc="+enc2);
	    			return ;
	    		}
	    	}
	    	
	    	
	    	
	    	// chk fill skill computer if job is IT field.
	    	boolean chkPositionIT = new PositionManager().chkPositionJobField(idEmp, idPos, 6);
	    	if(submitSkillCom == 1)
	    	{
	    		new SkillComputerManager().addSkillSubmitLog(idJsk, idResume);
	    	}
	    	if(clickSkipSkillCom == 1)
	    	{
	    		new SkillComputerManager().addSkillSkipLog(idJsk, idResume);
	    	}
	    	if(chkPositionIT && skipSkillCom!=1)
	    	{
	    		if(!new SkillComputerManager().haveSkillComputer(idJsk, idResume))
	    		{
	    			new SkillComputerManager().addViewSkillLog(idJsk, idResume);
	    			response.sendRedirect("/SRIX?view=fillSkillComputer&idResume="+idResume+"&enc="+enc2);
	    			return ;
	    		}
	    	}
	    	
	    	
	    	if(submitExp==1 || submitSkillCom==1)
	    	{
	    		response.sendRedirect("/SRIX?view=checkUpdateResume&idResume="+idResume+"&enc="+enc2);
    			return ;
	    	}
	    	
		}
		
        if(idJsk<0)
		{
			response.sendRedirect("/LogoutServ");
		}
		else if(idResume<0)
		{
			//error not specify resume
			System.out.println("error not specify resume");
			response.sendRedirect("/SRIX?view=apply&idEmp="+idEmp+"&idPos="+idPos+"&errorId=136");
		}
		else if(idEmp<0 && company.equals(""))
		{
			//error not specify company (error code 137)
			System.out.println("error not specify company (error code 137)");
			response.sendRedirect("/SRIX?view=apply&idEmp="+idEmp+"&idPos="+idPos+"&errorId=137");
		}
		else if(idPos<0 && position.equals(""))
		{
			//error not specify position(error code 138)
			System.out.println("error not specify position(error code 138)");
			response.sendRedirect("/SRIX?view=apply&idEmp="+idEmp+"&idPos="+idPos+"&errorId=138");
		}
		// gsb  and baac not allow saf -------------------
		else if((idEmp==2775 || idEmp==17113)&& idResume==0)
		{
			//errorId=145;
			System.out.println("errorId=145");
			response.sendRedirect("/SRIX?view=apply&idEmp="+idEmp+"&idPos="+idPos+"&errorId=145");
		}
		else if(idEmp<0 && idPos<0 && email.equals(""))
		{
			//error not specify email(error code 139)		
			System.out.println("error not specify email(error code 139)");
			response.sendRedirect("/SRIX?view=apply&idEmp="+idEmp+"&idPos="+idPos+"&errorId=139");
		}
		else 
		{
			Resume resume=new ResumeManager().get(idJsk, idResume);
			if(resume==null)
			{
				//error resume not exist
				System.out.println("error resume not exist");
			}
			else
			{
				if(idResume>=0)
		        {
		        	locale = resume.getLocale();
		        }
				else{
					locale=Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"));
				}
				if(idEmp>0 && idPos>0)
				{
					Position pos=new PositionManager().getPosition(idEmp, idPos);
					Employer com=new EmployerManager().get(idEmp);
					company=com.getNameEng();
					email=pos.getHrEmail();
					position=pos.getPositionName();
					acceptMail=new EmployerManager().isAcceptMail(idEmp);
					source="Jobtopgun";
					//String content=Viewnew ResumeManager().viewResume(resume, templateFile, position, allPage);
				}				
				if(email==null||email.equals(""))
				{
					acceptMail=false;
				}
				
				Timestamp curTime = new Timestamp(new java.util.Date().getTime());
				Timestamp lastTime = new SendResumeManager().getLastTimeApply(idJsk,idResume,idEmp,idPos,email,position);
				
				//Gsb check 60 days Other Company check 30 days
				if((idEmp==2775) && (Util.getDayInterval(lastTime,curTime)<=60))
				{
						//error code 149
					response.sendRedirect("/SRIX?view=apply&idEmp="+idEmp+"&idPos="+idPos+"&errorId=149");
				}
				else if(Util.getDayInterval(lastTime,curTime)<=30)
				{
					response.sendRedirect("/SRIX?view=apply&idEmp="+idEmp+"&idPos="+idPos+"&errorId=143");
				}
				else
				{
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
					
					
					//--  inquiry
					if(cmd.equals("inquiry"))
					{
						String target=poll(request);
					}
					String ip=Util.getStr(request.getRemoteAddr());
					if(ip.equals("203.146.208.152"))
					{
						ip=Util.getStr(request.getHeader("X-Forwarded-For"));
						if(ip.equals(""))
						{
							ip=request.getHeader("Proxy-Client-IP");
						}
					}
					
					
					Track track=new Track();
					track.setIdJsk(idJsk);
					track.setIdResume(idResume);
					track.setIdEmp(idEmp);
					track.setIdPosition(idPos);
					track.setAttachments(atms);
					track.setEmpOther(company);
					track.setIp(ip);
					track.setPositionOther(position);
					track.setRecipient(email);
					track.setSource(source);
					track.setReferer(referer);
					track.setShortReferer(shortReferer);
					//System.out.println("referer= "+referer+"     shortReferer= "+shortReferer);
					track.setSent(0);
					if(acceptMail==true)
					{
						track.setSent(0);
					}
					else
					{
						track.setSent(1);
					}
					int result =new TrackManager().add(track);
					new ResumeManager().updateLastApply(idJsk, idResume);
					if((idEmp>0)&&(idPos>0) && result==1) // match resume and job for stat
					{
						 try
						 {
						   List<PositionRequired> posList =  MatchResumeAndJob.getPositionRequired(idEmp,idPos);
						   MatchResumeAndJob.compareJobAndResume(idJsk,idResume, posList );
						 }
						 catch(Exception e)
						 {
							e.printStackTrace();
						 }
					}
					if(result==1)
					{
						new TrackManager().addTrackAll(idJsk, idResume, idEmp, company, idPos, position,"");
						int idTrack=new TrackManager().getTrackId(idJsk, idResume);
						track.setIdTrack(Util.getInt(idTrack));
						if(idState>0)
						{
							int rs=insertToTrackNationWideJob(idTrack, idState);
						}
					}
					new SendResumeThread(track).start();
					new WishListManager().deleteWishList(idJsk, idEmp, idPos);
					
					response.sendRedirect("/SRIX?view=applyResult&idEmp="+idEmp+"&idPos="+idPos);
					
				}			
			}
		}
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
	private String  poll(HttpServletRequest request) throws ServletException, IOException
	{
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		String target= "";
		if(idJsk<=0)
		{
			return target;
		}
		int result = 0;
		String ans1="0";
		String ans2="0";
		String ans3="0";
		String ans4="0";
		int idAnswer = Util.getInt(request.getParameter("answer"));
		String location = Util.getStr(request.getParameter("location"), "0@0");
        int idquest=Util.getInt(request.getParameter("idquest"));
        int idResume=Util.getInt(request.getParameter("idResume"));
        int idEmp =Util.getInt(request.getParameter("idEmp"));
        int idPos =Util.getInt(request.getParameter("idPos"));
        /*=======CERTIFICATE========*/
        String[] certificate=request.getParameterValues("certificate");
        String cert=""; 
        if(certificate!=null)
        {
        	for (int i = 0; i < certificate.length; i++) 
			{
        		String cert_name="";
        		if(certificate[i].equals("CPA (Certified Public Accountant)"))
        		{ 
					if(Util.getStr(request.getParameter("capNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("capNo"))+"]";
					}else{
						cert_name = certificate[i];	
					}
				}
				else if(certificate[i].equals("Single License"))
				{	
					if(Util.getStr(request.getParameter("slNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("slNo"))+"]";
					}else{
							cert_name = certificate[i];	
					}  
				}
				else if(certificate[i].equals("CISA (Certified Investment and Securities Analyst)"))
				{
					if(Util.getStr(request.getParameter("cisaNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("cisaNo"))+"]";
					}else{
							cert_name = certificate[i];	
					}  
					  
				}
				else if(certificate[i].equals("CFP (Certified Financial Planning)"))
				{
					if(Util.getStr(request.getParameter("cfpNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("cfpNo"))+"]";
					}else{
							cert_name = certificate[i];	
					}  
				}
				else if(certificate[i].equals("SAP License"))
				{
					if(Util.getStr(request.getParameter("sapNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("sapNo"))+"]";
				    }else{
				    	cert_name = certificate[i];	
				    }  
				}
				else if(certificate[i].equals("ตั๋วทนาย"))
				{
					if(Util.getStr(request.getParameter("lawyerNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("lawyerNo"))+"]";
					}else{
						cert_name = certificate[i];	
					}  
				}
				else if(certificate[i].equals("ใบประกอบวิชาชีพวิศวกรรม"))
				{
					if(Util.getStr(request.getParameter("engNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("engNo"))+"]";
					}else{
						cert_name = certificate[i];	
					}  
				}
				else if(certificate[i].equals("CIA"))
				{
					if(Util.getStr(request.getParameter("ciaNo"))!="")
					{
						cert_name = certificate[i]+"["+Util.getStr(request.getParameter("ciaNo"))+"]";
					}else{
						cert_name = certificate[i];	
					}  
				 }
        		if(cert.equals("")){
        			cert=cert_name;
        		}else{
					cert=cert+", "+cert_name;
				}
			}
        }
        //  System.out.println(cert);
        /*=======CERTIFICATE========*/
        String atms="";
        if(request.getParameter("atms")!=null)
		{
			atms=request.getParameter("atms");  // send direct from inquiry page
		}
        if(idAnswer != -1) 
        {
		        if(idAnswer > 0 && idquest > 0 &&  idEmp > 0 )
		        {
		        	result=new SendResumeManager().saveInquiry(idquest, idAnswer, idEmp);
		        }
		        if(result==0)
		        {
		        	 target= "?view=inquiry&ErrorId=537&idResume="+idResume+"&idEmp="+idEmp+"&idPos="+idPos;
		        }
        }
        
        if(idEmp==2775 && (idPos== 37 || idPos== 63) )
        {
        	StringTokenizer st=new StringTokenizer(location,"@");
        	if(st.hasMoreTokens())
        	{
        		ans1=st.nextToken();
        	}
        	if(st.hasMoreTokens())
        	{
        		ans3=st.nextToken();
        	}
        	String state = Util.getStr(request.getParameter("state_"+ans1), "0@0");
        	st=new StringTokenizer(state,"@");
        	if(st.hasMoreTokens())
        	{
        		ans2=st.nextToken();
        	}
        	if(st.hasMoreTokens())
        	{
        		ans4=st.nextToken();
        	}
        	if(!ans1.equals("0") || !ans2.equals("0") || !ans3.equals("0") || !ans4.equals("0"))
        	{
        		result=new SendResumeManager().saveInquiryEmp2775AndPos37(ans1, ans2, ans3, ans4, idEmp, idJsk, idResume, idPos, cert);
        	}
        	if(result==0)
            {
        		target= "?view=inquiry&ErrorId=537&idResume="+idResume+"&idEmp="+idEmp+"&idPos="+idPos;
            }
        }else if(idEmp==2775){
        	if(!cert.equals("")){
        		result=new SendResumeManager().saveInquiryEmp2775Cert(idEmp, idJsk, idResume, idPos, cert);
            }
        	if(result==0)
            {
        		target= "?view=inquiry&ErrorId=537&idResume="+idResume+"&idEmp="+idEmp+"&idPos="+idPos;
            }
        }
        
        else if(idEmp==17113)
        {	
        	String idCard= (request.getParameter("id_card")!=null)?request.getParameter("id_card"):"";
            String phoneNum = request.getParameter("phone_number")!=null?request.getParameter("phone_number"):"";
        	
        	if( !idCard.equals("") && !phoneNum.equals(""))
        	{
				// check exists idcard 
				boolean isExists =new SendResumeManager().chkExistsIDCARD(idCard);
				
				if(!isExists)
				{
					result=new SendResumeManager().saveInquiryEmp17113AndPos14(idEmp, idJsk, idResume, idPos, idCard, phoneNum, 0, 0, 0, 0, 0, "");
    			   	if(result==0)
    	            {
    			   		target= "?view=inquiry&ErrorId=537&idResume="+idResume+"&idEmp="+idEmp+"&idPos="+idPos;
    	            }
				}
				else 
				{
					target= "?view=inquiry&ErrorId=546&idResume="+idResume+"&idEmp="+idEmp+"&idPos="+idPos;				}
        	}
        	else
    		{
        		target= "?view=inquiry&ErrorId=545&idResume="+idResume+"&idEmp="+idEmp+"&idPos="+idPos;
    		}
        }
        return target;
	}
}
