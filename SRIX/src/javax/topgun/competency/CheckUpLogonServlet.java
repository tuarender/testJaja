package javax.topgun.competency;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.CancelService;
import com.topgun.resume.CancelServiceManager;
import com.topgun.resume.CompleteStatus;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.util.Encoder;
import com.topgun.util.Encryption;
import com.topgun.util.Util;
import javax.topgun.competency.CheckUpUserManager;

public class CheckUpLogonServlet extends HttpServlet  {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{      
		String device = Util.getStr(request.getSession().getAttribute("SESSION_DEVICE"),"desktop");
		String agent = Util.getStr(request.getHeader("User-Agent"));
        String username=Util.getStr(request.getParameter("username"));
		String password=Util.getStr(request.getParameter("password"));
		String referer=Util.getStr(request.getHeader("referer"));
		int isSRIX = Util.getInt(request.getParameter("isSRIX"));
		String idSession=Encoder.getEncode(request.getSession().getId());
		String target="/CheckUp?invalid=1"; //not specify username or password
		String status="";
		JobseekerManager jskMgr=new JobseekerManager();
		CheckUpUserManager chkMgr=new CheckUpUserManager();
		ResumeManager rsmMgr=new ResumeManager();
		System.out.println("SRIX?"+isSRIX);
		if(isSRIX==1){
			if(!username.equals("") && !password.equals("")){
				Jobseeker jobseeker=jskMgr.get(username, Encoder.getEncode(password));
				if(jobseeker!=null){
					target="/SRIX?invalid=0";
					int idJsk=jobseeker.getIdJsk();
					
					jskMgr.updateLastLogin(idJsk);
					jskMgr.addLogin(idJsk,idSession,device,agent);
					
					request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
					Resume resume=rsmMgr.get(idJsk, 0);
					if(resume!=null){
						status=new ResumeStatusManager().getRegisterStatus(resume);
					}
					else{
						status="PERSONALDATA";
					}
					
					//old jobseeker that complete register, don't have strength or aptitude
					//transfer aptitude and strength from the latest complete resume. and recheck status again
					if(status.equals("STRENGTH") || status.equals("APTITUDE")){
						Resume rs=rsmMgr.getLatestCompleted(idJsk);
						if(rs!=null){
							if(rs.getIdResume()>0){
								rsmMgr.copyStrength(idJsk, rs.getIdResume(), 0, resume.getIdLanguage());
								rsmMgr.copyAptitude(idJsk, rs.getIdResume(), 0, resume.getIdLanguage());
								rsmMgr.updateTimestamp(idJsk, 0);
								status=new ResumeStatusManager().getRegisterStatus(resume);
							}
						}
					}
					
					if(status.equals("TRUE"))
					{
						if(referer.indexOf("www.jobtopgun.com")!=-1) //Login from Jobtopgun.com
						{
							String loginParam="idJsk="+idJsk+"&key="+idSession;
							if(referer.indexOf("Mobile")!=-1) //Jobtopgun Mobile
							{
								target="http://www.jobtopgun.com/Mobile?view=home&"+loginParam;
							}
							else //Jobtopgun Desktop
							{
								target="http://www.jobtopgun.com/main.jsp?"+loginParam;
							}
						}
						else
						{
							if(referer.indexOf("view")!=-1)
							{
								if(referer.indexOf("cancelService")!=-1)
								{
									//cancel service
									String service = "";
									String method = "";
									if(referer.indexOf("cancelService")!=-1)
									{
										String param[] = referer.split("&");
										for(int i=0;i<param.length;i++)
										{
											String paramDetail[] = param[i].split("=");
											if(paramDetail[0].equals("cancelService"))
											{
												service = Util.getStr(paramDetail[1]);
											}
											else if(paramDetail[0].equals("method"))
											{
												method = Util.getStr(paramDetail[1]);
											}
										}
									}
										
									if(service.equals("jobmatch")||service.equals("jobupdate"))
									{
										CancelService cs = CancelServiceManager.getCancelByIdJsk(idJsk,service);
										if(cs!=null)
										{ 
											if(service.equals("jobmatch"))
											{
												target="/SRIX?view=actionService";
											}
											else if(service.equals("jobupdate"))
											{
												target="/SRIX?view=actionServiceJu";
											}
										}
										else
										{
											if(method.equals("jmcrit"))
											{
												target="/SRIX?view=critService";
											}
											else if(method.equals("jmfreq"))
											{
												target="/SRIX?view=freqService";
											}
											else 
											{
												target="/SRIX?view=cancelService";
											}
										}
									}
								}
								else if(referer.indexOf("view=apply")!=-1)
								{
									int idEmp=Util.getInt(request.getParameter("idEmp"));
									int idPosition=Util.getInt(request.getParameter("idPosition"));
									if(idEmp>0 && idPosition>0)
									{
										target="/SRIX?view=apply&idEmp="+idEmp+"&idPosition="+idPosition;
									}
									else
									{
										target=referer;
									}
								}
								else
								{
									target=referer;
								}
							}
							else
							{
								ResumeManager rsmMng = new ResumeManager();
								Resume rsm = rsmMng.getLatestCompleted(jobseeker.getIdJsk());
								if(rsm!=null){
									String encode = Encryption.getEncoding(0, 0, jobseeker.getIdJsk(),rsm.getIdResume());
									String key = Encryption.getKey(0, 0, jobseeker.getIdJsk(), rsm.getIdResume());
									target="http://www.topgunthailand.com/jtg/research/prediction.php?Enc="+encode+"&Key="+key;
								}
								else{
									target="/SRIX?view=home";
								}
							}
						}
					}
					
					else if(status.equals("PERSONALDATA"))
					{
						target="/SRIX?view=register&idResume=0&sequence=1";
					}
					else if(status.equals("EDUCATION"))
					{
						target="/SRIX?view=education&idResume=0&sequence=1";
						CompleteStatus edu=new ResumeStatusManager().getEducationCompleteStatus(resume);
						if(edu!=null)
						{
							if(edu.getStatusList()!=null)
							{
								if(edu.getStatusList().size()>0)
								{
									int idEdu=edu.getStatusList().get(0).getId();
									target="/SRIX?view=education&idResume=0&idEducation="+idEdu+"&sequence=1";
								}
							}
						}
					}
					else if(status.equals("EXPERIENCE"))
					{
						target="/SRIX?view=experience&idResume=0&sequence=1";
					}
					else if(status.equals("TARGETJOB"))
					{
						target="/SRIX?view=targetJob&idResume=0&sequence=1";
					}
					else if(status.equals("STRENGTH"))
					{
						target="/SRIX?view=strength&idResume=0&sequence=1";
					}
					else if(status.equals("APTITUDE"))
					{
						target="/SRIX?view=aptitude&idResume=0&sequence=1";
					}
					
					rsmMgr.updateStatus(idJsk, 0, status);
				}
			}
		}
		else //check in CHK_USER
		{
			if(!username.equals("") && !password.equals("")){
				CheckUpUser chkUser = chkMgr.get(username,password);
				if(chkUser!=null){
					request.getSession().setAttribute("SESSION_ID_USER", chkUser.getIdUser());
					String chkRegStatus = chkMgr.getCheckUpRegisterStatus(chkUser);
					//System.out.println(chkRegStatus);
					if(!chkRegStatus.equals("TRUE")){
						if(chkRegStatus.equals("APTITUDE")){
							target="/CheckUp?view=aptitude";
						}
						else if(chkRegStatus.equals("APTITUDE_RANK")||chkRegStatus.equals("BIRTH_DATE")){
							target="/CheckUp?view=aptitudeLevel";
						}
						else if(chkRegStatus.equals("STRENGTH")||chkRegStatus.equals("TARGETJOBS")||chkRegStatus.equals("EDUCATION")||chkRegStatus.equals("EXPERIENCE")){
							target="/CheckUp?view=briefCheckUp";
						}
					}
					else{
						target="/CheckUp?view=briefCheckUp";
					}
				}
				else{
					target="/CheckUp?invalidChk=1"; //username not exist
				}
			}
		}
		response.sendRedirect(target);
	}
}
