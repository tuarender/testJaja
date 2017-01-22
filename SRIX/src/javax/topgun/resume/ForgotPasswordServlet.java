/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  com.topgun.resume.Jobseeker
 *  com.topgun.resume.JobseekerManager
 *  com.topgun.resume.Resume
 *  com.topgun.resume.ResumeManager
 *  com.topgun.util.DBManager
 *  com.topgun.util.Encoder
 *  com.topgun.util.MailManager
 *  com.topgun.util.Util
 *  javax.servlet.RequestDispatcher
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 */
package javax.topgun.resume;

import com.topgun.resume.ForgotManager;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.OTPManager;
import com.topgun.util.DBManager;
import com.topgun.util.Encoder;
import com.topgun.util.MailManager;
import com.topgun.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

public class ForgotPasswordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String country = session.getAttribute("SESSION_COUNTRY") != null ? (String)session.getAttribute("SESSION_COUNTRY") : "TH";
        String locale = session.getAttribute("SESSION_LOCALE") != null ? (String)session.getAttribute("SESSION_LOCALE") : "th_TH";
        String ln = session.getAttribute("SESSION_LANGUAGE") != null ? (String)session.getAttribute("SESSION_LANGUAGE") : "th";
        String cmd = Util.getStr((Object)request.getParameter("cmd"));
        String Username = Util.getStr((Object)request.getParameter("username"));
        String telephone = Util.getStr((Object)request.getParameter("mobile"));
        String captcha = request.getParameter("verification") != null ? request.getParameter("verification") : "";
        int otp = Util.getInt((String)request.getParameter("otp"));
        int step = Util.getInt((String)request.getParameter("step"));
        OTPManager otpManager = new OTPManager();
        ForgotManager forgotManager = new ForgotManager();
        
        if (cmd.equals("reset")) {
            int errorId = 0;
            String password = Util.getStr((Object)request.getParameter("password"));
            String confirm = Util.getStr((Object)request.getParameter("confirm"));
            String email = Util.getStr((Object)request.getParameter("email"));
            String key = Util.getStr((Object)request.getParameter("key"));
            if (password.equals("") || confirm.equals("") || key.equals("") || email.equals("")) {
                errorId = 100;
            } else if (!Util.isEmail((String)email)) {
                errorId = 101;
            } else if (!password.equals(confirm)) {
                errorId = 102;
            } else {
                Jobseeker jsk = new JobseekerManager().get(email);
                if (jsk == null) {
                    errorId = 260;
                } else if (!jsk.getAuthen().equals(key)) {
                    errorId = 102;
                } else {
                    jsk.setAuthen(Encoder.getEncode((String)password));
                    new JobseekerManager().update(jsk, password);
                }
            }
            if (errorId != 0) {
            	response.sendRedirect("/SRIX?view=password&locale="+locale+"&email="+email+"&key="+key);
                /*request.setAttribute("ErrorId", (Object)errorId);
                request.getRequestDispatcher("/SRIX?view=resetPassword").forward((ServletRequest)request, (ServletResponse)response);
                return;*/
            }
            response.sendRedirect("/SRIX?view=resetPasswordComplete");
        }
        if (cmd.equals("email")) {
            String mobile = Util.getStr((Object)request.getParameter("mobile"));
            int idJsk = forgotManager.getIdJskByTelephone(mobile);
            String user = ForgotManager.getUsernameByIdJsk(idJsk);
            forgotManager.insertEmailLog(idJsk, user);
            this.sendEmail(user, country, ln, locale);
            response.sendRedirect("/SRIX?view=confirmSendMail");
            return;
        }
        if (cmd.equals("SelectAccount")) {
            int idJsk = Util.getInt((String)request.getParameter("id_Jsk"));
            String eMail = Util.getStr((Object)otpManager.getEmailByIdJsk(idJsk));
            forgotManager.insertEmailLog(idJsk, eMail);
            this.sendEmail(eMail, country, ln, locale);
            response.sendRedirect("/SRIX?view=confirmSendMail");
            return;
        }
        
        if (step == 2) {
            String telephoneNo = Util.getStr((Object)request.getParameter("telephoneNo"));
            int idJsk = otpManager.getIdJskByOTP(telephoneNo, otp);
            int OTP = otpManager.readOTP(idJsk, telephoneNo);
            if (OTP != otp) {
                request.setAttribute("wrong", (Object)"1");
                request.setAttribute("mobile", (Object)telephoneNo);
                RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=confirmOTP");
                page.forward((ServletRequest)request, (ServletResponse)response);
                return;
            }
            if (idJsk > 0) {
                Jobseeker jsk = new JobseekerManager().get(idJsk);
                request.setAttribute("locale", (Object)locale);
                request.setAttribute("email", (Object)jsk.getUsername());
                request.setAttribute("key", (Object)jsk.getAuthen());
                RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=resetPassword");
                page.forward((ServletRequest)request, (ServletResponse)response);
                return;
            }
            request.setAttribute("wrong", (Object)"1");
            request.setAttribute("mobile", (Object)telephoneNo);
            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=confirmOTP");
            page.forward((ServletRequest)request, (ServletResponse)response);
            return;
        }
        
        
        if (step == 1) {
            String result = Util.getStr((Object)request.getParameter("result"));
            if (result == "") {
                response.sendRedirect("/SRIX");
                return;
            }
            String[] result2 = result.split("_");
            int id = Util.getInt((String)result2[1]);
            String mail = ForgotManager.getUsernameByIdJsk(id);
            if (!result2[0].equals("1")) {
            	forgotManager.insertEmailLog(id, mail);
                this.sendEmail(mail, country, ln, locale);
                response.sendRedirect("/SRIX?view=confirmSendMail");
                return;
            }
            String mobile = forgotManager.getPrimaryNumber(mail);
            if (mobile.length() != 10) {
            	forgotManager.insertEmailLog(id, mail);
                this.sendEmail(mail, country, ln, locale);
                response.sendRedirect("/SRIX?view=listResume");
                return;
            }
            if (mobile.indexOf("-") != -1) {
            	forgotManager.insertEmailLog(id, mail);
                this.sendEmail(mail, country, ln, locale);
                response.sendRedirect("/SRIX?view=listResume");
                return;
            }
            char SecondNo = mobile.charAt(1);
            if (SecondNo != '8' && SecondNo != '9') {
            	forgotManager.insertEmailLog(id, mail);
                this.sendEmail(mail, country, ln, locale);
                response.sendRedirect("/SRIX?view=listResume");
                return;
            }
            int smsSuccess = this.sendSMS(id, mobile);
            if (smsSuccess == 1) {
                request.setAttribute("mobile", (Object)mobile);
                RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=confirmOTP");
                page.forward((ServletRequest)request, (ServletResponse)response);
                return;
            }
            forgotManager.insertEmailLog(id, mail);
            this.sendEmail(mail, country, ln, locale);
            response.sendRedirect("/SRIX?view=listResume");
            return;
        }
        
        if (Util.checkCaptcha((String)request.getSession().getId(), (String)captcha) != 1) {
            request.setAttribute("errorId", (Object)"6");
            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgot");
            page.forward((ServletRequest)request, (ServletResponse)response);
            return;
        }
        if (Username.equals("") && telephone.equals("")) {
            request.setAttribute("errorId", (Object)"1");
            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgot");
            page.forward((ServletRequest)request, (ServletResponse)response);
            return;
        }
        if (!Username.equals("")) {
            if (forgotManager.isUsernameExist(Username.toLowerCase()) != 1) {
                request.setAttribute("errorId", (Object)"3");
                RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgot");
                page.forward((ServletRequest)request, (ServletResponse)response);
                return;
            }
            String device = Util.getStr((Object)request.getSession().getAttribute("SESSION_DEVICE"));
            String idJsk = Util.getStr(forgotManager.getIdJskByEmail(Username.toLowerCase()));
            if (idJsk.equals("-1")) {
                request.setAttribute("errorId", (Object)"3");
                RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgot");
                page.forward((ServletRequest)request, (ServletResponse)response);
                return;
            }
            if (device.equals("mobile")) {
                response.sendRedirect("/SRIX?view=forgotSelect&i=" + idJsk);
                return;
            }
            request.setAttribute("idJSK", (Object)idJsk);
            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgotSelect");
            page.forward((ServletRequest)request, (ServletResponse)response);
            return;
        }
        if (telephone.equals("")) return;
        if (telephone.indexOf("-") != -1) {
            request.setAttribute("errorId", (Object)"5");
            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgot");
            page.forward((ServletRequest)request, (ServletResponse)response);
            return;
        }
        if (forgotManager.isTelephoneExist(telephone) > 0) {
            request.setAttribute("mobile", (Object)telephone);
            RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgotSelect");
            page.forward((ServletRequest)request, (ServletResponse)response);
            return;
        }
        request.setAttribute("errorId", (Object)"2");
        RequestDispatcher page = this.getServletContext().getRequestDispatcher("/SRIX?view=forgot");
        page.forward((ServletRequest)request, (ServletResponse)response);
    }
    
    public int sendEmail(String username, String country, String ln, String locale) {
        int result = 0;
        OTPManager otpManager = new OTPManager();
        String target = "/" + country + "/ForgotPasswordResult.jsp?errorId=200";
        Jobseeker jsk = new JobseekerManager().get(username);
        if (jsk != null) {
            try {
                String html = "<html><head><title>Your password from SuperResume.com</title></head><body><table border='0' width='600'>";
                MailManager mailMgr = new MailManager();
                mailMgr.setSubject("Your password from SuperResume.com");
                mailMgr.setRecepient(username);
                mailMgr.setSender("support@jobtopgun.com");
                Resume resume = new ResumeManager().get(jsk.getIdJsk(), 0);
                String fname = "";
                String lname = "";
                if (resume != null) {
                    fname = resume.getFirstName();
                    lname = resume.getLastName();
                } else {
                    fname = jsk.getUsername();
                }
                html = !ln.equals("th") ? String.valueOf(html) + "<tr><td>To " + fname + " " + lname + ",<br><br></td></tr> " + "<tr><td><font color='#ff0000'><b>Your username is: " + jsk.getUsername().toLowerCase() + "<br> " + "Please <a href='http://www.superresume.com/SRIX?view=password&locale=" + locale + "&email=" + jsk.getUsername() + "&key=" + jsk.getAuthen() + "'>Click Here</a> to reset your password</b></font><br><br></td><tr>" + "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;To get our full service especially our E-newsletter service via email, " + "please set Joptopgun as a safe sender so that our service emails will not be sent to Junk Folder\u201d <br><br> " + "<u><b>Setting junk email option in <font color='#ff0000'>Hotmail</font></b></u>" + "<ol>" + "<li>Click \u201coptions\u201d (on the upper right corner of the page) and choose \u201cmore options\u201d</li> " + "<li>Go to \u201cJunk e-mail\u201d and choose \u201csafe and blocked senders\u201d</li>" + "<li>Click \u201csafe senders\u201d</li>" + "<li>Type the senders\u2019 names or emails which you consider as the safe senders in the space below  \u201cSender and domain to mark as safe\u201d " + "*For service emails from Jobtopgun, type <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a> in the space.</li>" + "<li>Click \u201cadd to list\u201d</li>" + "</ol>" + "<u><b>Setting junk email option in <font color='#ff0000'>Gmail</font></b></u>" + "<ol>" + "<li>Click \u201ccreate a filter\u201d</li>" + "<li>Type a sender\u2019s name or an email which you consider as safe senders in the space after \u201cfrom\u201d " + "* Email from Jobtopgun, type  <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li>" + "<li>Click \u201cnext step\u201d</li>" + "<li>Mark \u201cnever send it to spam\u201d</li>" + "<li>Click \u201ccreate filter\u201d</li>" + "</ol>" + "<u><b>Setting junk email option in <font color='#ff0000'>Yahoo</font></b></u>" + "<ol>" + "<li>Click \u201coptions\u201d then choose \u201cMore options\u201d</li><li>Choose \u201cfilter\u201d, click \u201ccreate or edit filters\u201d</li> " + "<li>Click \u201cadd\u201d</li>" + "<li>Type a name in \u201cFilter Name\u201d *Email from Joptopgun, type Jobtopgun</li>" + "<li>Type a sender\u2019s name or an email which you consider as a safe sender in \u201cFrom Header\u201d " + "* Email from Jobtopgun, type  <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li>" + "<li>Choose your destination folder (generally, \u201cinbox\u201d) in \u201cMove the message to:\u201d</li>" + "</ol><br><br>" : String.valueOf(html) + "<tr><td>\u0e16\u0e36\u0e07 " + fname + " " + lname + "<br><br></td></tr>" + "<tr><td><font color='#ff0000'><b>\u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e43\u0e0a\u0e49\u0e02\u0e2d\u0e07\u0e04\u0e38\u0e13\u0e04\u0e37\u0e2d: " + jsk.getUsername().toLowerCase() + "</b><br>" + "<b>\u0e01\u0e23\u0e38\u0e13\u0e32<a href='http://www.superresume.com/SRIX?view=password&locale=" + locale + "&email=" + jsk.getUsername() + "&key=" + jsk.getAuthen() + "'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e15\u0e31\u0e49\u0e07\u0e23\u0e2b\u0e31\u0e2a\u0e1c\u0e48\u0e32\u0e19\u0e02\u0e2d\u0e07\u0e04\u0e38\u0e13\u0e43\u0e2b\u0e21\u0e48</b></font><br><br></td></tr>" + "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;\u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e43\u0e2b\u0e49\u0e04\u0e38\u0e13\u0e2a\u0e32\u0e21\u0e32\u0e23\u0e16\u0e43\u0e0a\u0e49\u0e1a\u0e23\u0e34\u0e01\u0e32\u0e23\u0e02\u0e2d\u0e07\u0e40\u0e23\u0e32\u0e44\u0e14\u0e49\u0e2d\u0e22\u0e48\u0e32\u0e07\u0e40\u0e15\u0e47\u0e21\u0e1b\u0e23\u0e30\u0e2a\u0e34\u0e17\u0e18\u0e34\u0e20\u0e32\u0e1e \u0e43\u0e19\u0e2a\u0e48\u0e27\u0e19\u0e02\u0e2d\u0e07\u0e01\u0e32\u0e23\u0e23\u0e31\u0e1a\u0e02\u0e48\u0e32\u0e27\u0e2a\u0e32\u0e23\u0e41\u0e25\u0e30\u0e15\u0e33\u0e41\u0e2b\u0e19\u0e48\u0e07\u0e07\u0e32\u0e19\u0e15\u0e48\u0e32\u0e07\u0e46 \u0e1c\u0e48\u0e32\u0e19\u0e17\u0e32\u0e07 E-mail " + "\u0e01\u0e23\u0e38\u0e13\u0e32\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32 Jobtopgun \u0e43\u0e2b\u0e49\u0e40\u0e1b\u0e47\u0e19 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u201d \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e1b\u0e49\u0e2d\u0e07\u0e01\u0e31\u0e19\u0e44\u0e21\u0e48\u0e43\u0e2b\u0e49 E-mail \u0e08\u0e32\u0e01\u0e40\u0e23\u0e32\u0e40\u0e02\u0e49\u0e32\u0e42\u0e1f\u0e25\u0e40\u0e14\u0e2d\u0e23\u0e4c  \u201c\u0e2d\u0e35\u0e40\u0e21\u0e25\u0e4c\u0e02\u0e22\u0e30\u201d <br><br> " + "<u><b>\u0e01\u0e32\u0e23\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a <font color='#ff0000'>Hotmail</font></b></u>" + "<ol>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e15\u0e31\u0e27\u0e40\u0e25\u0e37\u0e2d\u0e01\u201d (\u0e21\u0e38\u0e21\u0e02\u0e27\u0e32\u0e1a\u0e19) \u0e41\u0e25\u0e49\u0e27\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201c\u0e15\u0e31\u0e27\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e40\u0e15\u0e34\u0e21\u201d </li>" + "<li>\u0e44\u0e1b\u0e17\u0e35\u0e48\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d  \u201c\u0e2d\u0e35\u0e40\u0e21\u0e25\u0e4c\u0e02\u0e22\u0e30\u201d \u0e41\u0e25\u0e30\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u0e41\u0e25\u0e30\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e16\u0e39\u0e01\u0e1a\u0e25\u0e47\u0e2d\u0e01\u201d</li>" + "<li>\u0e08\u0e32\u0e01\u0e19\u0e31\u0e49\u0e19\u0e04\u0e25\u0e34\u0e01 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e17\u0e35\u0e48\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u201d</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c\u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d E-mail \u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22 \u0e25\u0e07\u0e43\u0e19\u0e0a\u0e48\u0e2d\u0e07 \u201c\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d\u0e42\u0e14\u0e40\u0e21\u0e19\u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u201d \u0e40\u0e0a\u0e48\u0e19 E-mail \u0e08\u0e32\u0e01 Jobtopgun \u0e43\u0e2b\u0e49\u0e1e\u0e34\u0e21\u0e1e\u0e4c <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li> " + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e25\u0e07\u0e43\u0e19\u0e23\u0e32\u0e22\u0e01\u0e32\u0e23\u201d</li>" + "</ol>" + "<a href='http://www.jobtopgun.com/projects/faq/junk_mail/hotmail.html'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e14\u0e39\u0e20\u0e32\u0e1e\u0e1b\u0e23\u0e30\u0e01\u0e2d\u0e1a<br><br>" + "<u><b>\u0e01\u0e32\u0e23\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a <font color='#ff0000'>Gmail</font></b></u>" + "<ol>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e2a\u0e23\u0e49\u0e32\u0e07\u0e15\u0e31\u0e27\u0e01\u0e23\u0e2d\u0e07\u0e08\u0e14\u0e2b\u0e21\u0e32\u0e22\u201d</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c \u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d E-mail \u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u0e25\u0e07\u0e43\u0e19\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d \u201c\u0e08\u0e32\u0e01 :\u201d \u0e40\u0e0a\u0e48\u0e19  E-mail \u0e08\u0e32\u0e01 Jobtopgun \u0e43\u0e2b\u0e49 \u0e1e\u0e34\u0e21\u0e1e\u0e4c <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201c\u0e02\u0e31\u0e49\u0e19\u0e15\u0e48\u0e2d\u0e44\u0e1b\u201d</li>" + "<li>\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22  \u0e2b\u0e19\u0e49\u0e32 \u201c\u0e44\u0e21\u0e48\u0e2a\u0e48\u0e07\u0e44\u0e1b\u0e22\u0e31\u0e07\u0e2a\u0e41\u0e1b\u0e21\u201d \u0e41\u0e25\u0e49\u0e27\u0e04\u0e25\u0e34\u0e01 \u201c \u0e2a\u0e23\u0e49\u0e32\u0e07\u0e15\u0e31\u0e27\u0e01\u0e23\u0e2d\u0e07\u201d</li>" + "</ol>" + "<a href='http://www.jobtopgun.com/projects/faq/junk_mail/gmail.html'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e14\u0e39\u0e20\u0e32\u0e1e\u0e1b\u0e23\u0e30\u0e01\u0e2d\u0e1a<br><br>" + "<u><b>\u0e01\u0e32\u0e23\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a <font color='#ff0000'>Yahoo</font></b></u>" + "<ol>" + "<li>\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201coptions\u201d \u0e08\u0e32\u0e01\u0e19\u0e31\u0e49\u0e19 \u0e04\u0e25\u0e34\u0e01 \u201cMore Options\u201d</li>" + "<li>\u0e40\u0e25\u0e37\u0e2d\u0e01 \u201cFilter\u201d \u0e08\u0e32\u0e01\u0e19\u0e31\u0e49\u0e19 \u0e04\u0e25\u0e34\u0e01 \u201cCreate or edit filters\u201d</li>" + "<li>\u0e04\u0e25\u0e34\u0e01 \u201cAdd\u201d</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c\u0e0a\u0e37\u0e48\u0e2d\u0e15\u0e31\u0e27\u0e01\u0e23\u0e2d\u0e07\u0e43\u0e19 Filter Name \u0e40\u0e0a\u0e48\u0e19  Jobtopgun</li>" + "<li>\u0e1e\u0e34\u0e21\u0e1e\u0e4c \u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e2a\u0e48\u0e07\u0e2b\u0e23\u0e37\u0e2d E-mail \u0e17\u0e35\u0e48\u0e08\u0e30\u0e17\u0e33\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e2b\u0e21\u0e32\u0e22\u0e27\u0e48\u0e32\u0e1b\u0e25\u0e2d\u0e14\u0e20\u0e31\u0e22\u0e25\u0e07\u0e43\u0e19\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d \u201c From Header : \u201d \u0e40\u0e0a\u0e48\u0e19  E-mail \u0e08\u0e32\u0e01 Jobtopgun \u0e43\u0e2b\u0e49 \u0e1e\u0e34\u0e21\u0e1e\u0e4c <a href='mailto:support@jobtopgun.com'>support@jobtopgun.com</a></li> " + "<li>\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e42\u0e1f\u0e25\u0e40\u0e14\u0e2d\u0e23\u0e4c\u0e17\u0e35\u0e48\u0e15\u0e49\u0e2d\u0e07\u0e01\u0e32\u0e23 (\u0e17\u0e31\u0e48\u0e27\u0e44\u0e1b\u0e21\u0e31\u0e01\u0e43\u0e0a\u0e49 \u201cInbox\u201d) \u0e15\u0e23\u0e07\u0e2b\u0e31\u0e27\u0e02\u0e49\u0e2d \u201cMove the message to : \u201d</li>" + "</ol>" + "<a href='http://www.jobtopgun.com/projects/faq/junk_mail/yahoo.html'>\u0e04\u0e25\u0e34\u0e01\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48</a> \u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e14\u0e39\u0e20\u0e32\u0e1e\u0e1b\u0e23\u0e30\u0e01\u0e2d\u0e1a<br><br> " + "\u0e2b\u0e32\u0e01\u0e04\u0e38\u0e13\u0e21\u0e35\u0e02\u0e49\u0e2d\u0e2a\u0e07\u0e2a\u0e31\u0e22\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e40\u0e15\u0e34\u0e21\u0e01\u0e23\u0e38\u0e13\u0e32\u0e15\u0e34\u0e14\u0e15\u0e48\u0e2d 085-220-1123-9 \u0e43\u0e19\u0e40\u0e27\u0e25\u0e32\u0e17\u0e33\u0e01\u0e32\u0e23\u0e04\u0e48\u0e30<br>Super Resume/Jobtopgun Team<br><br>";
                html = String.valueOf(html) + "</td></tr></table></body></html>";
                mailMgr.addHTML(html);
                mailMgr.send();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            target = "/" + country + "/ForgotPasswordResult.jsp?errorId=100";
        }
        return result;
    }

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
