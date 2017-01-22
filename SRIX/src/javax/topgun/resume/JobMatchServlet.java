package javax.topgun.resume;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.JobseekerManager;
import com.topgun.resume.MailJobseekerCancel;
import com.topgun.resume.MailJobseekerCancelManager;
import com.topgun.resume.CancelService;
import com.topgun.resume.CancelServiceManager;
import com.topgun.resume.Jobseeker;
import com.topgun.util.Base64Coder;
import com.topgun.util.Encryption;
import com.topgun.util.Util;

public class JobMatchServlet extends HttpServlet 
{	
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cancel = request.getParameter("cancel")!=null?request.getParameter("cancel"):"";
		if(cancel.equals("true"))
		{
			String newschk = request.getParameter("newschk")!=null?request.getParameter("newschk"):"";
			String param = request.getParameter("param")!=null?request.getParameter("param"):"";
			String[] idDecode=null;
			String[] paramDecode =null;
			int idJsk=0;
			if(!param.equals("") && param!=null && !param.equals("null"))
			{
				paramDecode = Base64Coder.decodeString(param).split("#");
				idDecode=null;
				if(paramDecode!=null)
				{
					if(paramDecode.length>0)
					{
						idDecode = paramDecode[0].split("@");
					}
				}
				if(idDecode!=null)
				{
					if(idDecode.length>0)
					{
						 idJsk = Integer.parseInt(idDecode[1]);
					}
				}
			}
			if(idJsk==0)
			{
				idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
				if(idJsk<=0)
				{
					//decode
					String enc = request.getParameter("enc")!=null?request.getParameter("enc"):"";
					String key = request.getParameter("key")!=null?request.getParameter("key"):"";
					Encryption.getDecoding(enc,key);
					idJsk = Encryption.idJsk;
					if(idJsk<=0)
					{
						response.sendRedirect("/LogoutServ");
						return;
					}
					request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
				}	
			}
			if(idJsk>0)
			{
				Jobseeker jsk =new JobseekerManager().get(idJsk);
				CancelService cs=new CancelService();
				cs.setIdJsk(idJsk);
				cs.setEmail(jsk.getUsername());
				cs.setService("mailOther");
				if(newschk.equals("1"))
				{
					jsk.setMailOtherStatus("TRUE");
					cs.setReasonType(0);
					cs.setHowLong(0);
					cs.setDisable("TRUE");
				}
				else
				{
					jsk.setMailOtherStatus("FALSE");
					cs.setReasonType(6);
					cs.setHowLong(5);
					cs.setDisable("FALSE");
				}
				int rs=new JobseekerManager().updateOtherMailStatus(jsk);
				if(rs==1)
				{
					int resultAdd = 0;
					if(CancelServiceManager.checkExist(cs,"mailOther"))
					{
						resultAdd =	CancelServiceManager.update(cs);
					}
					else
					{
						resultAdd =	CancelServiceManager.add(cs);
					}
					System.out.println("CancelServiceManager "+resultAdd);
				}
				response.sendRedirect("/SRIX?view=thank");
			}
			else
			{
				response.sendRedirect("http://www.jobtopgun.com/");
			}
		}
		else
		{
			int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
			if(idJsk<=0)
			{
				//decode
				String enc = request.getParameter("enc")!=null?request.getParameter("enc"):"";
				String key = request.getParameter("key")!=null?request.getParameter("key"):"";
				Encryption.getDecoding(enc,key);
				idJsk = Encryption.idJsk;
				if(idJsk<=0)
				{
					response.sendRedirect("/LogoutServ");
					return;
				}
				request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
			}	
			
			if(idJsk>0)
			{
				int updateStatus = Util.StrToInt(""+request.getParameter("updateStatus"));
				if (updateStatus==1)
				{
					String jobmatchStatus = request.getParameter("jmStatusBar")!=null?request.getParameter("jmStatusBar"):"";
							Jobseeker jsk = new JobseekerManager().get(idJsk);
							jsk.setJobMatchStatus(jobmatchStatus);
							new JobseekerManager().update(jsk);
							if(jobmatchStatus.equals("FALSE")){
								MailJobseekerCancel mjc = new MailJobseekerCancel();
								mjc.setIdJsk(idJsk);
								mjc.setAction("UNSUB");
								mjc.setType("JOBMATCH");
								MailJobseekerCancelManager.add(mjc);
							}else{
								MailJobseekerCancel mjc = new MailJobseekerCancel();
								mjc.setIdJsk(idJsk);
								mjc.setAction("SUB");
								mjc.setType("JOBMATCH");
								MailJobseekerCancelManager.add(mjc);
							}
						response.sendRedirect("/SRIX?view=jobmatchDetail");
				}else{
					response.sendRedirect("/SRIX?view=jobmatchDetail");
				}
			}else{
				response.sendRedirect("http://www.jobtopgun.com/");
			}
		}
	}
}
