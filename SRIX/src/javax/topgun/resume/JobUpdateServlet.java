package javax.topgun.resume;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.JobseekerManager;
import com.topgun.resume.MailJobseekerCancel;
import com.topgun.resume.MailJobseekerCancelManager;
import com.topgun.resume.Jobseeker;
import com.topgun.util.Base64Coder;
import com.topgun.util.Encoder;
import com.topgun.util.Util;

public class JobUpdateServlet extends HttpServlet 
{

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{	
		int idJsk = Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		String changeEmail = Util.getStr(request.getParameter("changeEmail"),"0");
		String paramJsk = Util.getStr(request.getParameter("paramJsk"),"0");
		int param_jsk = Util.getInt(paramJsk, 0);
		RequestDispatcher success=null;
		int idError = 0;
		String login = request.getParameter("login")!=null?request.getParameter("login"):"0";
		
		if(login.equals("1")){
			String EMAIL = request.getParameter("EMAIL")!=null?request.getParameter("EMAIL"):"";
			String PASSWORD = request.getParameter("PASSWORD")!=null?request.getParameter("PASSWORD"):"";
			
			com.topgun.resume.Jobseeker jsk =null;
			String authen=Encoder.getEncode(PASSWORD);
			jsk = new JobseekerManager().get(EMAIL, authen);
			
			if(jsk != null){
				String curEncode=Encoder.getEncode(PASSWORD);
				String oldEncode=Encoder.getEncode(jsk.getPassword());
				
				if (EMAIL.equals("")){
					idError = 250;  // check fill new_username 
				}
				else if (PASSWORD.equals("")){
					idError = 252;  // check fill password
				}
				else if (!Util.isEmail(EMAIL)){
					idError = 101;  // invalid email
				}
				else if (!curEncode.equals(oldEncode)){
					idError = 255;  // password not correct
				}
				
				if(idError > 0){
					response.sendRedirect("/TH/Services/RecJm_non_login.jsp?idError="+idError);
				}
				else{
					String param = Base64Coder.encodeString("id@"+jsk.getIdJsk()+"#");
					response.sendRedirect("http://www.superresume.com/TH/Services/cancel.jsp?reference=RecJm_set_fin&param="+param);
				}
			}
			else{
				idError = 262; // invalid username in system
				response.sendRedirect("/TH/Services/RecJm_non_login.jsp?idError="+idError);
			}
		}else{
			if(changeEmail.equals("1"))  //----> Non Receive Jobmatch Solve Services (call working by RecJm_ChangeEmail.jsp) 
			{			
				if(param_jsk > 0){	
					String NEWEMAIL_JSK = request.getParameter("NEWEMAIL_JSK")!=null?request.getParameter("NEWEMAIL_JSK"):"";
					String CFNEWEMAIL_JSK = request.getParameter("CFNEWEMAIL_JSK")!=null?request.getParameter("CFNEWEMAIL_JSK"):"";
					String PASSWORD_JSK = request.getParameter("PASSWORD_JSK")!=null?request.getParameter("PASSWORD_JSK"):"";
					String curEncode=Encoder.getEncode(PASSWORD_JSK);
					String oldEncode=Encoder.getEncode(new JobseekerManager().get(param_jsk).getPassword());
					
					if (NEWEMAIL_JSK.equals("")){
						idError = 250;  // check fill new_username 
					}
					else if (CFNEWEMAIL_JSK.equals("")){
						idError = 251;  // check fill confirm_username
					}
					else if (PASSWORD_JSK.equals("")){
						idError = 252;  // check fill password
					}
					else if (!Util.isEmail(NEWEMAIL_JSK) || !Util.isEmail(CFNEWEMAIL_JSK)) {
						idError = 101;  // invalid email
					}
					else if (!NEWEMAIL_JSK.equals(CFNEWEMAIL_JSK)) {
						idError = 253;  // new username not equal confirm username
					}
					else if (new JobseekerManager().get(NEWEMAIL_JSK)!=null) {
						idError = 254;  // new username already exist
					}
					else if (!curEncode.equals(oldEncode)){
						idError = 255;  // password not correct
					}
					
					if(idError>0){
						response.sendRedirect("/TH/Services/RecJm_ChangeEmail.jsp?idError="+idError);	
					}else{
						// update jsk
						Jobseeker jskData = new JobseekerManager().get(param_jsk);
						if (jskData!=null){
							jskData.setUsername(NEWEMAIL_JSK);
							if (new JobseekerManager().update(jskData)!=1){
								idError = 209;
							}
						}
						if(idError>0){
							response.sendRedirect("/TH/Services/RecJm_ChangeEmail.jsp?idError="+idError);	
						}else{
							response.sendRedirect("/TH/Services/RecJm_finish.jsp?reference=RecJm_change_fin&ne="+NEWEMAIL_JSK);	
						}
					}
				}else{
					String OLDEMAIL = request.getParameter("OLDEMAIL")!=null?request.getParameter("OLDEMAIL"):"";
					String CFOLDEMAIL = request.getParameter("CFOLDEMAIL")!=null?request.getParameter("CFOLDEMAIL"):"";
					String NEWEMAIL = request.getParameter("NEWEMAIL")!=null?request.getParameter("NEWEMAIL"):"";
					String CFNEWEMAIL = request.getParameter("CFNEWEMAIL")!=null?request.getParameter("CFNEWEMAIL"):"";
					String PASSWORD = request.getParameter("PASSWORD")!=null?request.getParameter("PASSWORD"):"";
					
					//--- check username in system
					com.topgun.resume.Jobseeker jsk =null;
					String authen=Encoder.getEncode(PASSWORD);
					jsk = new JobseekerManager().get(OLDEMAIL, authen);
					
					if (jsk != null){
						//--- check username in system
						String curEncode=Encoder.getEncode(PASSWORD);
						String oldEncode=Encoder.getEncode(jsk.getPassword());
						if (NEWEMAIL.equals("")){
							idError = 250;  // check fill new_username 
						}
						else if (CFNEWEMAIL.equals("")){
							idError = 251;  // check fill confirm_username
						}
						else if (PASSWORD.equals("")){
							idError = 252;  // check fill password
						}
						else if (!Util.isEmail(NEWEMAIL) || !Util.isEmail(CFNEWEMAIL)){
							idError = 101;  // invalid email
						}
						else if (!NEWEMAIL.equals(CFNEWEMAIL)){
							idError = 253;  // new username not equal confirm username
						}
						else if (new JobseekerManager().get(NEWEMAIL)!=null){
							idError = 254;  // new username already exist
						}
						else if (!curEncode.equals(oldEncode)){
							idError = 255;  // password not correct
						}
					}else{
						idError = 262; // invalid username in system
					}
					
					if(idError>0){
						response.sendRedirect("/TH/Services/RecJm_ChangeEmail.jsp?idError="+idError);	
					}else{
						// update jsk
						if (jsk != null){
							Jobseeker jskData = new JobseekerManager().get(jsk.getIdJsk());
							if (jskData!=null){
								jskData.setUsername(NEWEMAIL);
								if (new JobseekerManager().update(jskData) != 1){
									idError = 209;
								}
							}
							if(idError>0){
								response.sendRedirect("/TH/Services/RecJm_ChangeEmail.jsp?idError="+idError);	
								
							}else{
								response.sendRedirect("/TH/Services/RecJm_finish.jsp?reference=RecJm_change_fin&ne="+NEWEMAIL);	
							}
						}else{
							response.sendRedirect("/TH/Services/RecJm_ChangeEmail.jsp?idError=260");	
						}
					}
				}
			//----> *****Finish**** Non Receive Jobmatch Solve Services (call working by RecJm_ChangeEmail.jsp)	
			}else{
	
				if(idJsk<=0){
					response.sendRedirect("/LogoutServ");
					return;
				}
				int updateStatus = Util.StrToInt(""+request.getParameter("updateStatus"));
				if(idJsk>0){
					if (updateStatus==1){
						String jobupdateDay = request.getParameter("JOBUPDATEDAY")!=null?request.getParameter("JOBUPDATEDAY"):"";
						Jobseeker jsk = new JobseekerManager().get(idJsk);
						jsk.setJobUpdateStatus(jobupdateDay);
						new JobseekerManager().update(jsk);
						if(jobupdateDay.equals("NO")){
							MailJobseekerCancel mjc = new MailJobseekerCancel();
							mjc.setIdJsk(idJsk);
							mjc.setAction("UNSUB");
							mjc.setType("JOBUPDATE");
							MailJobseekerCancelManager.add(mjc);
						}else{
							MailJobseekerCancel mjc = new MailJobseekerCancel();
							mjc.setIdJsk(idJsk);
							mjc.setAction("SUB");
							mjc.setType("JOBUPDATE");
							MailJobseekerCancelManager.add(mjc);
						}
						response.sendRedirect("/SRIX?view=jobupdateDetail");
					}else{
						response.sendRedirect("/SRIX?view=jobupdateDetail");
					}
				}else{
					response.sendRedirect("http://www.jobtopgun.com/");
				}
			}
		}//chk login
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		response.sendRedirect("http://www.jobtopgun.com/");
	}

}
