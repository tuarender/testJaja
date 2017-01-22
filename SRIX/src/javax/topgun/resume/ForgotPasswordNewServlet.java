package javax.topgun.resume;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.topgun.resume.ForgotManager;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.OTPManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.util.Encoder;
import com.topgun.util.MailManager;
import com.topgun.util.Util;

public class ForgotPasswordNewServlet extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String locale = session.getAttribute("SESSION_LOCALE") != null ? (String)session.getAttribute("SESSION_LOCALE") : "th_TH";	
		String newMobileNumber = "";
		ForgotManager forgotManager = new ForgotManager();
		
		//ต้องการ Reset Password
		String cmd = Util.getStr(request.getParameter("cmd"));
		if(cmd.equals("reset"))
		{
			// from E-mail
			String email = Util.getStr(request.getParameter("email"),"").toLowerCase();;
			String key = Util.getStr(request.getParameter("key"),"");
			
			// from OTP
			int idJsk = Util.getInt(request.getParameter("idJsk"),0);
			
			String password = Util.getStr(request.getParameter("password"),"");
			Jobseeker user = null ;
			if((!email.equals("") && !key.equals("")) || idJsk > 0)
			{
				if(!email.equals("") && !key.equals(""))
				{
					 // get Jobsseker from email ;
					user = new JobseekerManager().get(email);
				}
				else if(idJsk > 0)
				{
					 // get Jobsseker from idJsk ;
					user = new JobseekerManager().get(idJsk);
				}
			}	
			if(user!=null)
			{
				if(!password.equals(""))
				{
					user.setAuthen(Encoder.getEncode((String)password));
                    new JobseekerManager().update(user, password);
                    response.sendRedirect("/SRIX?view=resetPasswordComplete"); 
				}
			}
		}
		
		String service = Util.getStr(request.getParameter("service"));
		int step = Util.getInt((String)request.getParameter("step"));
		String idJskFromChooseMobile = Util.getStr(request.getParameter("chooseMethod"));
		if(service.equals("chooseMethodSendEmailAndOTP"))
		{
			String[] idJsk_1 = idJskFromChooseMobile.split("_");	
			//value 2_ ส่ง OTP ไปที่ Phone
			if (idJsk_1[0].equals("2")) {
				int idJskFromChooseMethod = Util.getInt((String)idJsk_1[1]);
				if(idJskFromChooseMethod  != 0) {			
					if(step == 1){
						String mobile = Util.getStr(ForgotManager.getMobileByIdJsk(idJskFromChooseMethod));
				    	if(mobile.indexOf("-") != -1 && mobile.length() >= 10) {
					    	String newMobile = mobile.replace("-", "");
					    	if(newMobile.length() == 10){
					    		newMobileNumber = newMobile;	
					    	}	    			
			    		} else {
			    			newMobileNumber = mobile;
			    		}	
				    	
				    	//เบอร์โทรต้อง 10 หลักเท่านั้นถึงจะส่ง OTP
				    	if(newMobileNumber.length() == 10){
				    		int smsSuccess = this.sendSMS(idJskFromChooseMethod, newMobileNumber);
							if (smsSuccess == 1) {
				                request.setAttribute("number", newMobileNumber);
				                request.setAttribute("mobile", newMobileNumber);
				                request.setAttribute("idJskFromChooseMethod", idJskFromChooseMethod);
				                RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=confirmOTP");
				                page.forward((ServletRequest)request, (ServletResponse)response);
				            }
				    	}	
					} 
				}
			}
			
			//other value ส่ง Mail
			else {
				int idJskFromChooseEmail = Util.getInt(request.getParameter("chooseMethod"));
				if(idJskFromChooseEmail  != -1){
					this.getContentMail(locale, idJskFromChooseEmail);
			        RequestDispatcher forgotSelectNewPage = this.getServletContext().getRequestDispatcher("/SRIX?view=confirmSendMail");
			        forgotSelectNewPage.forward((ServletRequest)request, (ServletResponse)response);
				} 
			} 
			
			//Step 2 ไปหน้า Confirm OTP กับ Reset Password
			if (step == 2) {
		        String telephoneNo = Util.getStr(request.getParameter("telephoneNo"));		        
		        OTPManager otpManager = new OTPManager();
		        int otp = Util.getInt((String)request.getParameter("otp"));
		        int idJsk = otpManager.getIdJskByOTP(telephoneNo, otp);
		        int OTP = otpManager.readOTP(idJsk, telephoneNo);
		        
		        if (OTP != otp) {
		        	request.setAttribute("wrong", "1");
		            request.setAttribute("mobile", telephoneNo);
		            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=confirmOTP");
		            page.forward((ServletRequest)request, (ServletResponse)response);
		            return;
		        }
		        
		        if (idJsk > 0) {
		        	request.setAttribute("idJskOTP", idJsk);
		            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=resetPassword");		
		            page.forward((ServletRequest)request, (ServletResponse)response);
		            return;
		        }
			}
		}
		
		//TODO รับ e-mail ,เบอร์โทรศัพท์และ Captcha จากการกรอกในหน้า forgotNew.jsp แล้วส่งไปที่ OTPManager
		else if(service.equals("inputData")) {    
			String userName =Util.getStr(request.getParameter("username")).toLowerCase();
			String primaryPhone = Util.getStr(request.getParameter("mobile"));	
			request.setAttribute("emailInput",userName);
			request.setAttribute("primaryPhoneInput",primaryPhone);
			String captcha = request.getParameter("verification") != null ? request.getParameter("verification") : "";
						
			if (Util.checkCaptcha(request.getSession().getId(), captcha) != 1) {
				request.setAttribute("errorId", "4");
				RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgotNew");
		        page.forward((ServletRequest)request, (ServletResponse)response);
		    } 
			
			int idJskFromEmail = forgotManager.getIdJskFromEmailInInterJobSeeker(userName);		//รับ idjsk ขึ้นมาจากอีเมลล์ จาก INTER_JOBSEEKER
			int countIdJskFromTelephone = forgotManager.getResumeFromZeroNumber(primaryPhone);	//หา idJsk  จากเบอร์โทรศัพท์ของ resume เบอร์ 0
	    	List<Jobseeker> email = new ArrayList<Jobseeker>();
	    	int flag = 1;
	    	
	        //ถ้า idJsk (PK) จาก Email มีค่า แต่ไม่มีเบอร์โทรศัพท์
	    	if(idJskFromEmail > 0 && primaryPhone.equals("")) {
	    		flag = 1;		
	    		email = new ForgotManager().getEmailFromIdJsk(idJskFromEmail); 	
	    	}
	    	//ถ้า idJsk (PK) จาก Email ไม่มีค่า แต่่มีเบอร์โทรศัพท์
	    	else if(!primaryPhone.equals(""))
	    	{		
	    		if(countIdJskFromTelephone > 1)		
	    		{
	    			flag = 2;
	    		//หาจำนวน idJsk = 1 -> แสดง email กับเบอร์โทร
	    		} 
	    		else if(countIdJskFromTelephone == 1) {
	    			flag = 3;
	    			List<Jobseeker> forgot = new ForgotManager().getListJskFromZeroNumber(primaryPhone);	
	    			int idJsk = forgot.get(0).getIdJsk();
    				String mobile = Util.getStr(ForgotManager.getMobileByIdJsk(idJsk));
    				String newMobileFormat = "*******"+ mobile.substring(7, 10);
					request.setAttribute("mobile", newMobileFormat);
	    		}
	    		
	    		if(idJskFromEmail == 0)
	    		{
	    			email = new ForgotManager().getListJskFromZeroNumber(primaryPhone);  
	    		}//ถ้ามีทั้ง idJsk (PK) จาก Email และเบอร์โทรศัพท์
	    		else if(idJskFromEmail > 0)
	    		{
	    			flag = 4;		
	    			email = new ForgotManager().getEmailFromIdJsk(idJskFromEmail); 	  
	    		}
	    	}
	    	
	    	for (Jobseeker jobseeker : email) {				
	    		int index = jobseeker.getUsername().indexOf('@');
	    		String user = "*" + jobseeker.getUsername().substring(1, index) + "***" + jobseeker.getUsername().substring(index);
	    		jobseeker.setUsername(user);
	    	}
	    	request.setAttribute("flag", flag);
	    	request.setAttribute("email", email);
	    	request.setAttribute("primaryPhone", primaryPhone);
	        RequestDispatcher forgotSelectNewPage = this.getServletContext().getRequestDispatcher("/SRIX?view=forgotSelectNew");
	        forgotSelectNewPage.forward((ServletRequest)request, (ServletResponse)response);	
		}
	}
		
	public String getContentMail(String locale, int idJsk)
	{
        Resume resume = new ResumeManager().get(idJsk, 0);
        String flname = "";
        String username = ForgotManager.getUsernameByIdJsk(idJsk);
        if(resume!=null)
        {
        	if(resume.getFirstName()!=null && resume.getLastName()!=null)
        	{
        		flname = resume.getFirstName()+" "+resume.getLastName();
        	}
        }
        else
        {
        	flname = username ;
        }
        Jobseeker jsk = new JobseekerManager().get(username);
		String result =  "<html>"
				+ "<head>"
				+ "	<title>"
				+ "		Your password from SuperResume.com"
				+ "	</title>"
				+ "</head>"
				+ "<body><table border='0' width='600'>";
		
		if(!locale.equals("th_TH"))
		{
			try {
	            MailManager mailMgr = new MailManager();
	            mailMgr.setSubject("Your password from SuperResume.com");
	            mailMgr.setRecepient(username);		
	            mailMgr.setSender("support@jobtopgun.com");
	            result = "<tr><td>To " + flname + ",<br><br></td></tr> " 
	            		+ "<tr><td><font color='#ff0000'><b>Your username is: " + username.toLowerCase() + "<br> " 
	            		+ "Please <a href='http://www.superresume.com/SRIX?view=resetPassword&locale=" + locale + "&email=" + username + "&key=" + jsk.getAuthen() + "'>Click Here</a> to reset your password</b></font><br><br></td><tr>" 
	            		+ "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;To get our full service especially our E-newsletter service via email, " + "please set Joptopgun as a safe sender so that our service emails will not be sent to Junk Folder\u201d <br><br> " 
	            		//Hotmail
	            		+ "<u><b>Setting junk email option in <font color='#ff0000'>Hotmail</font></b></u>" + "<ol>" 
	            		+ "<li>Click \u201coptions\u201d (on the upper right corner of the page) and choose \u201cmore options\u201d</li> " 
	            		+ "<li>Go to \u201cJunk e-mail\u201d and choose \u201csafe and blocked senders\u201d</li>" 
	            		+ "<li>Click \u201csafe senders\u201d</li>" 
	            		+ "<li>Type the senders\u2019 names or emails which you consider as the safe senders in the space below  \u201cSender and domain to mark as safe\u201d " + "*For service emails from Jobtopgun, type <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a> in the space.</li>" 
	            		+ "<li>Click \u201cadd to list\u201d</li>" + "</ol>" 
	            		//Gmail
	            		+ "<u><b>Setting junk email option in <font color='#ff0000'>Gmail</font></b></u>" + "<ol>" 
	            		+ "<li>Click \u201ccreate a filter\u201d</li>" 
	            		+ "<li>Type a sender\u2019s name or an email which you consider as safe senders in the space after \u201cfrom\u201d " + "* Email from Jobtopgun, type  <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li>" 
	            		+ "<li>Click \u201cnext step\u201d</li>" 
	            		+ "<li>Mark \u201cnever send it to spam\u201d</li>" 
	            		+ "<li>Click \u201ccreate filter\u201d</li>" + "</ol>" 
	            		//Yahoo
	            		+ "<u><b>Setting junk email option in <font color='#ff0000'>Yahoo</font></b></u>" + "<ol>" 
	            		+ "<li>Click \u201coptions\u201d then choose \u201cMore options\u201d</li><li>Choose \u201cfilter\u201d, click \u201ccreate or edit filters\u201d</li> " 
	            		+ "<li>Click \u201cadd\u201d</li>" 
	            		+ "<li>Type a name in \u201cFilter Name\u201d *Email from Joptopgun, type Jobtopgun</li>" 
	            		+ "<li>Type a sender\u2019s name or an email which you consider as a safe sender in \u201cFrom Header\u201d " + "* Email from Jobtopgun, type  <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li>" 
	            		+ "<li>Choose your destination folder (generally, \u201cinbox\u201d) in \u201cMove the message to:\u201d</li>"; 
	            result = String.valueOf(result) + "</td></tr></table></body></html>";
	            mailMgr.addHTML(result);
	            mailMgr.send();
			} catch (Exception e) {
                e.printStackTrace();
            } 
		} 
		
		//local = EN
		else {
			try {
	            MailManager mailMgr = new MailManager();
	            mailMgr.setSubject("Your password from SuperResume.com");
	            mailMgr.setRecepient(username);		
	            mailMgr.setSender("support@jobtopgun.com");
	            result = "<tr><td>\u0e16\u0e36\u0e07 " + flname + "<br><br></td></tr>" 
	            		+ "<tr><td><font color='#ff0000'><b>\u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e43\u0e0a\u0e49\u0e02\u0e2d\u0e07\u0e04\u0e38\u0e13\u0e04\u0e37\u0e2d: " + username.toLowerCase() + "</b><br>" 
	            		+ "<b>\u0e01\u0e23\u0e38\u0e13\u0e32<a href='http://www.superresume.com/SRIX?view=resetPassword&locale=" + locale + "&email=" + username + "&key=" + jsk.getAuthen() + "'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e15\u0e31\u0e49\u0e07\u0e23\u0e2b\u0e31\u0e2a\u0e1c\u0e48\u0e32\u0e19\u0e02\u0e2d\u0e07\u0e04\u0e38\u0e13\u0e43\u0e2b\u0e21\u0e48</b></font><br><br></td></tr>" 
	            		+ "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;\u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e43\u0e2b\u0e49\u0e04\u0e38\u0e13\u0e2a\u0e32\u0e21\u0e32\u0e23\u0e16\u0e43\u0e0a\u0e49\u0e1a\u0e23\u0e34\u0e01\u0e32\u0e23\u0e02\u0e2d\u0e07\u0e40\u0e23\u0e32\u0e44\u0e14\u0e49\u0e2d\u0e22\u0e48\u0e32\u0e07\u0e40\u0e15\u0e47\u0e21\u0e1b\u0e23\u0e30\u0e2a\u0e34\u0e17\u0e18\u0e34\u0e20\u0e32\u0e1e \u0e43\u0e19\u0e2a\u0e48\u0e27\u0e19\u0e02\u0e2d\u0e07\u0e01\u0e32\u0e23\u0e23\u0e31\u0e1a\u0e02\u0e48\u0e32\u0e27\u0e2a\u0e32\u0e23\u0e41\u0e25\u0e30\u0e15\u0e33\u0e41\u0e2b\u0e19\u0e48\u0e07\u0e07\u0e32\u0e19\u0e15\u0e48\u0e32\u0e07\u0e46 \u0e1c\u0e48\u0e32\u0e19\u0e17\u0e32\u0e07 E-mail " + "\u0e01\u0e23\u0e38\u0e13\u0e32\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32 Jobtopgun \u0e43\u0e2b\u0e49\u0e40\u0e1b\u0e47\u0e19 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u201d \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e1b\u0e49\u0e2d\u0e07\u0e01\u0e31\u0e19\u0e44\u0e21\u0e48\u0e43\u0e2b\u0e49 E-mail \u0e08\u0e32\u0e01\u0e40\u0e23\u0e32\u0e40\u0e02\u0e49\u0e32\u0e42\u0e1f\u0e25\u0e40\u0e14\u0e2d\u0e23\u0e4c  \u201c\u0e2d\u0e35\u0e40\u0e21\u0e25\u0e4c\u0e02\u0e22\u0e30\u201d <br><br> "
						//Hotmail
	            		+ "<u><b>\u0e01\u0e32\u0e23\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a <font color='#ff0000'>Hotmail</font></b></u>" + "<ol>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e15\u0e31\u0e27\u0e40\u0e25\u0e37\u0e2d\u0e01\u201d (\u0e21\u0e38\u0e21\u0e02\u0e27\u0e32\u0e1a\u0e19) \u0e41\u0e25\u0e49\u0e27\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201c\u0e15\u0e31\u0e27\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e40\u0e15\u0e34\u0e21\u201d </li>" + "<li>\u0e44\u0e1b\u0e17\u0e35\u0e48\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d  \u201c\u0e2d\u0e35\u0e40\u0e21\u0e25\u0e4c\u0e02\u0e22\u0e30\u201d \u0e41\u0e25\u0e30\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u0e41\u0e25\u0e30\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e16\u0e39\u0e01\u0e1a\u0e25\u0e47\u0e2d\u0e01\u201d</li>" + "<li>\u0e08\u0e32\u0e01\u0e19\u0e31\u0e49\u0e19\u0e04\u0e25\u0e34\u0e01 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u201d</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c\u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d E-mail \u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22 \u0e25\u0e07\u0e43\u0e19\u0e0a\u0e48\u0e2d\u0e07 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d\u0e42\u0e14\u0e40\u0e21\u0e19\u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u201d \u0e40\u0e0a\u0e48\u0e19 E-mail \u0e08\u0e32\u0e01 Jobtopgun \u0e43\u0e2b\u0e49\u0e1e\u0e34\u0e21\u0e1e\u0e4c <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li> " + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e25\u0e07\u0e43\u0e19\u0e23\u0e32\u0e22\u0e01\u0e32\u0e23\u201d</li>" + "</ol>" + "<a href='http://www.jobtopgun.com/projects/faq/junk_mail/hotmail.html'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e14\u0e39\u0e20\u0e32\u0e1e\u0e1b\u0e23\u0e30\u0e01\u0e2d\u0e1a<br><br>"
	            		//Gmail
	            		+ "<u><b>\u0e01\u0e32\u0e23\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a <font color='#ff0000'>Gmail</font></b></u>" + "<ol>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e2a\u0e23\u0e49\u0e32\u0e07\u0e15\u0e31\u0e27\u0e01\u0e23\u0e2d\u0e07\u0e08\u0e14\u0e2b\u0e21\u0e32\u0e22\u201d</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c \u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d E-mail \u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u0e25\u0e07\u0e43\u0e19\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d \u201c\u0e08\u0e32\u0e01 :\u201d \u0e40\u0e0a\u0e48\u0e19  E-mail \u0e08\u0e32\u0e01 Jobtopgun \u0e43\u0e2b\u0e49 \u0e1e\u0e34\u0e21\u0e1e\u0e4c <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e02\u0e31\u0e49\u0e19\u0e15\u0e48\u0e2d\u0e44\u0e1b\u201d</li>" + "<li>\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22  \u0e2b\u0e19\u0e49\u0e32 \u201c\u0e44\u0e21\u0e48\u0e2a\u0e48\u0e07\u0e44\u0e1b\u0e22\u0e31\u0e07\u0e2a\u0e41\u0e1b\u0e21\u201d \u0e41\u0e25\u0e49\u0e27\u0e04\u0e25\u0e34\u0e01 \u201c \u0e2a\u0e23\u0e49\u0e32\u0e07\u0e15\u0e31\u0e27\u0e01\u0e23\u0e2d\u0e07\u201d</li>" + "</ol>" + "<a href='http://www.jobtopgun.com/projects/faq/junk_mail/gmail.html'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e14\u0e39\u0e20\u0e32\u0e1e\u0e1b\u0e23\u0e30\u0e01\u0e2d\u0e1a<br><br>" 
	            		//Yahoo
	            		+ "<u><b>\u0e01\u0e32\u0e23\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a <font color='#ff0000'>Yahoo</font></b></u>" + "<ol>" + "<li>\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201coptions\u201d \u0e08\u0e32\u0e01\u0e19\u0e31\u0e49\u0e19 \u0e04\u0e25\u0e34\u0e01 \u201cMore Options\u201d</li>" + "<li>\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201cFilter\u201d \u0e08\u0e32\u0e01\u0e19\u0e31\u0e49\u0e19 \u0e04\u0e25\u0e34\u0e01 \u201cCreate or edit filters\u201d</li>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201cAdd\u201d</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c\u0e0a\u0e37\u0e48\u0e2d\u0e15\u0e31\u0e27\u0e01\u0e23\u0e2d\u0e07\u0e43\u0e19 Filter Name \u0e40\u0e0a\u0e48\u0e19  Jobtopgun</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c \u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d E-mail \u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u0e25\u0e07\u0e43\u0e19\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d \u201c From Header : \u201d \u0e40\u0e0a\u0e48\u0e19  E-mail \u0e08\u0e32\u0e01 Jobtopgun \u0e43\u0e2b\u0e49 \u0e1e\u0e34\u0e21\u0e1e\u0e4c <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li> " + "<li>\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e42\u0e1f\u0e25\u0e40\u0e14\u0e2d\u0e23\u0e4c\u0e17\u0e35\u0e48\u0e15\u0e49\u0e2d\u0e07\u0e01\u0e32\u0e23 (\u0e17\u0e31\u0e48\u0e27\u0e44\u0e1b\u0e21\u0e31\u0e01\u0e43\u0e0a\u0e49 \u201cInbox\u201d) \u0e15\u0e23\u0e07\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d \u201cMove the message to : \u201d</li>" + "</ol>" + "<a href='http://www.jobtopgun.com/projects/faq/junk_mail/yahoo.html'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e14\u0e39\u0e20\u0e32\u0e1e\u0e1b\u0e23\u0e30\u0e01\u0e2d\u0e1a<br><br> " + "\u0e2b\u0e32\u0e01\u0e04\u0e38\u0e13\u0e21\u0e35\u0e02\u0e49\u0e2d\u0e2a\u0e07\u0e2a\u0e31\u0e22\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e40\u0e15\u0e34\u0e21\u0e01\u0e23\u0e38\u0e13\u0e32\u0e15\u0e34\u0e14\u0e15\u0e48\u0e2d 081-989-9779 \u0e43\u0e19\u0e40\u0e27\u0e25\u0e32\u0e17\u0e33\u0e01\u0e32\u0e23\u0e04\u0e48\u0e30<br>Super Resume/Jobtopgun Team<br><br>";
	            result = String.valueOf(result) + "</td></tr></table></body></html>";
	            mailMgr.addHTML(result);
	            mailMgr.send();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return result ;
	}
	
	//Send OTP to Phone
	public int sendSMS(int idJsk, String telephone) {
        int result = 0;
        OTPManager otpManager = new OTPManager();
        ForgotManager forgotManager = new ForgotManager();
        String message = "Your confirmation code from JOBTOPGUN is :" + otpManager.getOTP(idJsk, telephone);
        URLConnection conn = null;
        OutputStreamWriter wr = null;
        try {
            String data = String.valueOf(URLEncoder.encode("recipient", "UTF-8")) + "=" + URLEncoder.encode(telephone, "UTF-8") + "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8") + "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("T", "UTF-8");
            URL url = new URL("http://203.146.250.66/SMS/sms.php");
            conn = url.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(conn.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("transaction");
            int i = 0;
            while (i < nList.getLength()) {
                Element eElement;
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == 1 && Util.getStr(forgotManager.getTagValue("desc", eElement = (Element)nNode)).toUpperCase().equals("SUCCESS")) {
                    result = 1;
                }
                ++i;
            }
            wr.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }
}