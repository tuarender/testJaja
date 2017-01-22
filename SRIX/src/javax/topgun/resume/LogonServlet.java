package javax.topgun.resume;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.CancelService;
import com.topgun.resume.CancelServiceManager;
import com.topgun.resume.CompleteStatus;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.LoginLogManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.util.Encoder;
import com.topgun.util.Encryption;
import com.topgun.util.Util;


public class LogonServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{      
		String locale=Util.getStr(request.getParameter("locale"));
		
		if(locale.equals(""))
		{
			locale=Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH");
		}
		String target="/?invalid=1"; //not specify username or password
		String referer=Util.getStr(request.getHeader("referer"));
		String encode = Util.getStr(request.getParameter("encode"));
		String key = Util.getStr(request.getParameter("key"));
        String username=Util.getStr(request.getParameter("username"));
		String password=Util.getStr(request.getParameter("password"));
		String idSession=Encoder.getEncode(request.getSession().getId());
		int remember=Util.getInt(request.getParameter("remember"));
		String status="";
		JobseekerManager jskMgr=new JobseekerManager();
		ResumeManager rsmMgr=new ResumeManager();
		int idJsk=-1;
		if(!username.equals("") && !password.equals(""))
		{
			Jobseeker jobseeker=jskMgr.get(username, Encoder.getEncode(password));
			if(jobseeker!=null)
			{
				target="/?invalid=0";
				idJsk=jobseeker.getIdJsk();
								
				jskMgr.updateLastLogin(idJsk);
				jskMgr.addLogin(idJsk,idSession,"","");
				request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
				Resume resume=rsmMgr.get(idJsk, 0);
				if(resume!=null)
				{
					status=new ResumeStatusManager().getRegisterStatus(resume);
				}
				else
				{
					status="PERSONALDATA";
				}
				//old jobseeker that complete register, don't have strength or aptitude
				//transfer aptitude and strength from the latest complete resume. and recheck status again
				if(status.equals("STRENGTH") || status.equals("APTITUDE"))
				{
					Resume rs=rsmMgr.getLatestCompleted(idJsk);
					if(rs!=null)
					{
						if(rs.getIdResume()>0)
						{
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
						String loginParam="idJsk="+idJsk+"&key="+idSession+"&remember="+remember;
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
									target="/SRIX?view=apply&idEmp="+idEmp+"&idPosition="+idPosition+"&remember="+remember;
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
							target="/SRIX?view=home&remember="+remember;
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
					CompleteStatus strStatus=new ResumeStatusManager().getStrengthStatus(resume);
					if(strStatus!=null)
					{
						if(Util.getStr(strStatus.getSection()).equals("STRENGTH_ORDER")) 
						{
							target="/SRIX?view=strengthOrder&idResume=0&sequence=1";
						}
						else
						{
							target="/SRIX?view=strength&idResume=0&sequence=1";
						}
					}
				}
				else if(status.equals("APTITUDE"))
				{
					target="/SRIX?view=aptitude&idResume=0&sequence=1";
				}
				
				rsmMgr.updateStatus(idJsk, 0, status);
			}
			else
			{
				new LoginLogManager().add(username, password);
				if(referer.indexOf("www.jobtopgun.com")!=-1) //Login from Jobtopgun.com
				{
					if(referer.indexOf("Mobile")!=-1) //Jobtopgun Mobile
					{
						target="http://www.jobtopgun.com/Mobile?view=login&invalid=2";
					}
					else //Jobtopgun Desktop
					{
						target="http://www.jobtopgun.com/index.jsp?invalid=2";
					}
				}
				else
				{
					target="/?invalid=2"; //username not exist
				}
			}
		}
		response.sendRedirect(target+"&jSession="+idSession);
	}
}

