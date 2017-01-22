package javax.topgun.resume;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.CancelService;
import com.topgun.resume.CancelServiceManager;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.TrackManager;
import com.topgun.util.Encryption;
import com.topgun.util.Util;
import com.topgun.util.Encoder;

public class SRIXServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String locale = Util.getStr(request.getParameter("locale"));
		if(locale.length() == 0 && request.getSession() != null)
		{
			locale = Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"));
		}
		String view=Util.getStr(request.getParameter("view"));
		String idSession=Encoder.getEncode(request.getSession().getId());
		boolean campusFlag = false ;
		int idSchoolExclusive = Util.getInt(request.getParameter("campus"),0) ;
		Cookie[] cookies  = request.getCookies();
		String[] publicView=
			{
				"stepRegister","register","registerTracking","studentTracking","studentLogin","forgot","confirmOTP","resetPassword",
				"resetPasswordComplete","confirmSendMail","listResume","privacy","term","password",
				"forgotSelect","passwordUpdateSuccess","applyLogin","applyLoginNew","cancel","cancelJmSpecial","cancelNonMember","thank","test","campusList",
				"applyLater","forgotNew" , "forgotSelectNew","registerIOS"
			};
		int idEmp=Util.getInt(request.getParameter("idEmp"));
		int idPosition=Util.getInt(request.getParameter("idPosition"));
		int idSm=Util.getInt(request.getParameter("idSm"));
		
		int idJsk=Util.getInt(request.getParameter("idJsk"),-1);
		String jSession=Util.getStr(request.getParameter("jSession"));
		String deviceMobile = Util.getStr(request.getParameter("device"),"");
		JobseekerManager jskManager = new JobseekerManager();
		/*********************got link from jobtopgun.com, need auto sign-in***************/
		String referer=Util.getStr(request.getHeader("Referer"));
		//if(idJsk>=0 && !jSession.equals("") && referer.indexOf("jobtopgun.com")!=-1) //
		if(idJsk>=0 && !jSession.equals("")) //
		{
			if(jskManager.isLogin(idJsk, jSession))
			{
				request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
			}
			else
			{
				idJsk=-1;
			}
		}
		
		if(idJsk>0 && !deviceMobile.equals(""))
		{
			request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
		}
		
		
		
		/*********************got link from mail or other system, required parameter (key,encode)***************/		
		String encode = Util.getStr(request.getParameter("encode"));
		String key = Util.getStr(request.getParameter("key"));
		Encryption.getDecoding(encode, key);
		if(idJsk<=0 && !key.equals("") && !encode.equals(""))
		{
			idJsk = Encryption.idJsk;
			if(new JobseekerManager().get(idJsk)!=null)
			{
				//logout previous jobseeker
				request.getSession().removeAttribute("SESSION_ID_JSK");
				request.getSession().invalidate();
				
				request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
			}
			else
			{
				idJsk=-1;
			}
		}
		
		if(idJsk<0)
		{
			idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		}
		if(view.equals("apply"))
		{
			Cookie idEmpCookie  = new Cookie("COOKIE_ID_EMP",Util.getStr(idEmp));
			Cookie idPositionCookie = new Cookie("COOKIE_ID_POSITION",Util.getStr(idPosition));
			if(idSm>0)
			{
				Cookie idSuperMatchCookie  = new Cookie("COOKIE_ID_SUPERMATCH",Util.getStr(idSm));
				idSuperMatchCookie.setMaxAge(365*24*60*60); 
				idSuperMatchCookie.setDomain(".superresume.com");
				response.addCookie(idSuperMatchCookie);
			}
			idEmpCookie.setMaxAge(365*24*60*60); 
			idPositionCookie.setMaxAge(365*24*60*60); 
			idEmpCookie.setDomain(".superresume.com");
			idPositionCookie.setDomain(".superresume.com");
			response.addCookie(idEmpCookie);
	   		response.addCookie(idPositionCookie);
			if(cookies!=null)
		   	{
				boolean hasCookie=false;
		   		for (int i = 0; i < cookies.length; i++)
				{
					Cookie cookie = cookies[i];
					if(cookie.getName().equals("COOKIE_SHORT_REFERER"))
			        {
						hasCookie=true;
						break;
			        }
				}
		   		if(hasCookie==false)
		   		{
		   			String referrerURL = Util.getStr(request.getHeader("referer"));
					if(referrerURL!="")
					{
						//System.out.println("View=apply no Cookie");
						String shortURL=getShortURL(referrerURL.toLowerCase());
						Cookie shortReferer=new Cookie("COOKIE_SHORT_REFERER",shortURL);
						Cookie ref=new Cookie("COOKIE_REFERER",Util.getStr(referrerURL));
						shortReferer.setMaxAge(365*24*60*60);
						shortReferer.setDomain(".superresume.com");
						ref.setMaxAge(365*24*60*60); 
						ref.setDomain(".superresume.com");
						response.addCookie(shortReferer);
				   		response.addCookie(ref);
					}
		   		}
		   	}
		}
		if(view.equals("applyLoginNew"))
		{
			String referrerURL = Util.getStr(request.getHeader("referer"));
			if(referrerURL!="")
			{
				//System.out.println("View=applyLoginNew");
				String shortURL=getShortURL(referrerURL);
				Cookie shortReferer=new Cookie("COOKIE_SHORT_REFERER",shortURL);
				Cookie ref=new Cookie("COOKIE_REFERER",Util.getStr(referrerURL));
				shortReferer.setMaxAge(365*24*60*60);
				shortReferer.setDomain(".superresume.com");
				ref.setMaxAge(365*24*60*60); 
				ref.setDomain(".superresume.com");
				response.addCookie(shortReferer);
		   		response.addCookie(ref);
			}
			Cookie idEmpCookie  = new Cookie("COOKIE_ID_EMP",Util.getStr(idEmp));
			Cookie idPositionCookie = new Cookie("COOKIE_ID_POSITION",Util.getStr(idPosition));
			if(idSm>0)
			{
				Cookie idSuperMatchCookie  = new Cookie("COOKIE_ID_SUPERMATCH",Util.getStr(idSm));
				idSuperMatchCookie.setMaxAge(365*24*60*60); 
				idSuperMatchCookie.setDomain(".superresume.com");
				response.addCookie(idSuperMatchCookie);
			}
			idEmpCookie.setMaxAge(365*24*60*60); 
			idPositionCookie.setMaxAge(365*24*60*60); 
			idEmpCookie.setDomain(".superresume.com");
			idPositionCookie.setDomain(".superresume.com");
			response.addCookie(idEmpCookie);
	   		response.addCookie(idPositionCookie);
		}
		if(request.getHeader("Referer")!=null && view.equals("register")){
			if((request.getHeader("Referer").indexOf("ku.superresume.com")!=-1 )&& idSchoolExclusive==0 )
			{
				campusFlag = true ;
				idSchoolExclusive = 11 ;
			}else if(request.getHeader("Referer").indexOf("tu.superresume.com")!=-1 && idSchoolExclusive==0)
			{
				idSchoolExclusive = 38 ;
				request.getSession().setAttribute("SESSION_ID_SCHOOL_EXCLUSIVE",idSchoolExclusive);
				
			}else if(request.getHeader("Referer").indexOf("cu.superresume.com")!=-1 && idSchoolExclusive==0)
			{
				idSchoolExclusive = 6 ;
				request.getSession().setAttribute("SESSION_ID_SCHOOL_EXCLUSIVE",idSchoolExclusive);
				
			}else if(idSchoolExclusive!=0)
			{
				request.getSession().setAttribute("SESSION_ID_SCHOOL_EXCLUSIVE",idSchoolExclusive);
			}
		}
		if(idJsk>0)
		{
			Resume resume=null;
			int idResume=Util.getInt(request.getParameter("idResume"));
			if(idResume>=0)
			{
				resume=new ResumeManager().get(idJsk,idResume);
				if(resume!=null)
				{
					if(Util.getStr(request.getSession().getAttribute("SESSION_LOCALE")).equals(""))
					{
						request.getSession().setAttribute("SESSION_LOCALE",resume.getLocale());
						request.getSession().setAttribute("SESSION_COUNTRY",resume.getTemplateIdCountry());
						request.getSession().setAttribute("SESSION_LANGUAGE",resume.getIdLanguage());
					}
					request.setAttribute("resume",resume);
					if(resume.getIdResume()==0)
					{
						new ResumeManager().updateStatus(idJsk, resume.getIdResume(), new ResumeStatusManager().getRegisterStatus(resume));
					}
					else
					{
						new ResumeManager().updateStatus(idJsk, resume.getIdResume(), new ResumeStatusManager().getResumeStatus(resume));
					}
				}
			}
			if((Jobseeker)request.getAttribute("jobseeker")==null)
			{
				Jobseeker jobseeker=new JobseekerManager().get(idJsk);
				request.setAttribute("jobseeker",jobseeker);
			}
			
			//copy resume if idResume in inter_track and update resume timestamp
			if(resume!=null)
			{
				ResumeManager rsmMgr=new ResumeManager();
				if(Util.getInt(request.getParameter("sequence"))==0 || view.equals("resumeInfo")) 
				{
					if(resume.getIdResume()>0)
					{
						int cnt=new TrackManager().countAll(idJsk, idResume);
						if(cnt>0)
						{
							idResume=rsmMgr.copyResume(idJsk, idResume, resume.getResumeName(), resume.getApplyIdCountry(), resume.getIdLanguage(), resume.getTemplateIdCountry(), resume.getResumePrivacy());
							if(idResume>0)
							{
								rsmMgr.delete(idJsk,resume.getIdResume());
								response.sendRedirect("/SRIX?view="+view+"&idResume="+idResume+"&sequence=0&jSession="+idSession);
								return;
							}
						}
					}
					rsmMgr.updateTimestamp(idJsk, idResume);
				}
				else if(Util.getInt(request.getParameter("sequence"))==1)
				{
					rsmMgr.updateTimestamp(idJsk, idResume);
				}
			}
		}

		if(view.equals(""))
		{
			response.sendRedirect(response.encodeRedirectURL("/"));
		}
		else if(idJsk<=0) //jobseeker not login yet
		{
			if (view.equals("apply") && idEmp>0 && idPosition>0) 
			{
				if(cookies != null)
				{
					int idJskCookie=-1;
					String idSessionCookie="";
					for (int i = 0; i < cookies.length; i++)
					{
						if(cookies[i].getName().equals("COOKIE_ID_JSK"))
						{
							idJskCookie=Util.getInt(cookies[i].getValue());
						} 
						else if(cookies[i].getName().equals("COOKIE_ID_SESSION"))
						{
							idSessionCookie=cookies[i].getValue();
						}
					}
					if(idJskCookie>0 && !idSessionCookie.equals(""))
					{
						if(new JobseekerManager().isLogin(idJskCookie,idSessionCookie))
						{
							request.getSession().setAttribute("SESSION_ID_JSK", idJskCookie);
							request.getSession().setAttribute("SET_COOKIE","TRUE");
							request.getRequestDispatcher("/view/index.jsp").forward(request, response);	
						}
					}
				}

				response.sendRedirect("/SRIX?view=applyLoginNew&idEmp="+idEmp+"&idPosition="+idPosition+"&jSession="+idSession+"&locale="+locale);
				//return ;
			}
			else if(Util.inArray(view, publicView)!=-1)
			{
				if(view.equals("register") && campusFlag==true){ //Exclusive University have campus
					response.sendRedirect("/SRIX?view=campusList&idSchoolExclusive="+idSchoolExclusive);
				}else{ //normal case
					request.getRequestDispatcher("/view/index.jsp").forward(request, response);			
				}
			}
			else //jobseeker must be login first for permission view
			{
				response.sendRedirect("/LogoutServ");
			}
		}
		else //jobseeker already login
		{
			if(view.equals("cancelService"))
			{
				String service = Util.getStr(request.getParameter("cancelService"));
				String method = Util.getStr(request.getParameter("method"));
				int valid = Util.getInt(request.getParameter("valid"));
				if(valid!=1)
				{
					if(service.equals("jobmatch")||service.equals("jobupdate"))
					{
						String target = "/SRIX?view=home";
						CancelService cs = CancelServiceManager.getCancelByIdJsk(idJsk,service);
						if(cs!=null)
						{ 
							if(service.equals("jobmatch"))
							{
								target="/SRIX?view=actionService&cancelService="+service+"&method="+Util.getStr(method)+"&valid=1&jSession="+idSession;
							}
							else if(service.equals("jobupdate"))
							{
								target="/SRIX?view=actionServiceJu&cancelService="+service+"&method="+Util.getStr(method)+"&valid=1&jSession="+idSession;
							}
						}
						else
						{
							if(method.equals("jmcrit"))
							{
								target="/SRIX?view=critService&cancelService="+service+"&method="+Util.getStr(method)+"&valid=1&jSession="+idSession;
							}
							else if(method.equals("jmfreq"))
							{
								target="/SRIX?view=freqService&cancelService="+service+"&method="+Util.getStr(method)+"&valid=1&jSession="+idSession;
							}
							else 
							{
								target="/SRIX?view=cancelService&cancelService="+service+"&method="+Util.getStr(method)+"&valid=1&jSession="+idSession;
							}
						}
						response.sendRedirect(target);
					}
					else
					{
						response.sendRedirect("/LogoutServ");
					}
				}
				else
				{
					request.getRequestDispatcher("/view/index.jsp").forward(request, response);
				}
			}
			else
			{
				request.getRequestDispatcher("/view/index.jsp").forward(request, response);	
			}		
		}
	}
	
	public static String getShortURL(String referrerURL)
	{
		String shortURL="other";
		if(Util.getStr(referrerURL)!="")
		{
			String[] urlJobs = {"jobPost","jobinthailand","supperresume","superresume","jobtopgun","srix","jtgxii","jobsdb", "jobbkk", "jobthai", "jobnisit", "linkedin", "jobth"};
			for(int i=0; i<urlJobs.length; i++)
			{
				if(referrerURL.indexOf(urlJobs[i])!=-1)
				{
					shortURL=urlJobs[i];
					break;
				}
			}
		}
		if(shortURL.equals("jobPost") || shortURL.equals("jobinthailand")|| shortURL.equals("supperresume")|| shortURL.equals("superresume")|| shortURL.equals("srix")|| shortURL.equals("jtgxii"))
		{
			shortURL="jobtopgun";
		}
		return shortURL;
	}
}
