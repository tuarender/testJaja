package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.util.Encoder;
import com.topgun.util.Util;

public class AccountServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//String target = "/LogoutServ";		
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		int idResume=Util.getInt(request.getParameter("matchIdResume"));
		String service=request.getParameter("Service")!=null?request.getParameter("Service"):"";
		JobseekerManager jskMgr=new JobseekerManager();
		Jobseeker jsk=jskMgr.get(idJsk);
		if((jsk!=null) && (!service.equals("")))
		{
			int idError = 0;
			if (service.equals("changeUsername")) 
			{
				String newUsername = request.getParameter("new_username").trim().toLowerCase();
				String confirmUsername = request.getParameter("confirm_username").trim().toLowerCase();
				String password = request.getParameter("password");
				//Encode
				String curEncode=Encoder.getEncode(password);
				String oldEncode=Encoder.getEncode(jskMgr.get(idJsk).getPassword());
				
				if (newUsername.equals("")) 
				{
					idError = 250;  // check fill new_username 
				}
				else if (confirmUsername.equals("")) 
				{
					idError = 251;  // check fill confirm_username
				}
				else if (password.equals("")) 
				{
					idError = 252;  // check fill password
				}
				else if (!newUsername.equals(confirmUsername)) 
				{
					idError = 253;  // new username not equal confirm username
				}
				else if (jskMgr.isExist(newUsername)) 
				{
					idError = 254;  // new username already exist
				}
				else if (!curEncode.equals(oldEncode)) 
				{
					idError = 255;  // password not correct
				}
				if (idError == 0) 
				{
					jsk.setUsername(newUsername);
					if(jskMgr.update(jsk) == 0)
					{
						idError = 209;
					}
					if (idError == 0) 
					{
						response.sendRedirect("/SRIX?view=changeComplete");
					}
					else 
					{
						request.setAttribute("ErrorId", idError);
						request.getRequestDispatcher("/SRIX?view=changeUsername").forward(request, response);
					}
				}
				else 
				{
					request.setAttribute("ErrorId", idError);
					request.getRequestDispatcher("/SRIX?view=changeUsername").forward(request, response);
				}
			}
			else if(service.equals("changePassword")) 
			{
				String newPassword = request.getParameter("new_password").trim();
				String curPassword = request.getParameter("cur_password").trim();
				String confirmPassword = request.getParameter("confirm_password").trim();
				//Encode
				String curEncode=Encoder.getEncode(curPassword);
				String oldEncode=Encoder.getEncode(jskMgr.get(idJsk).getPassword());
				String newEncode=Encoder.getEncode(newPassword);
				
				if (curPassword.equals("")) 
				{
					idError = 256;  // check fill current_password 
				}
				else if (newPassword.equals("")) 
				{
					idError = 257;  // check fill new_password 
				}
				else if (newPassword.length() <6) 
				{
					idError = 106;  // check password len > 6
				}
				else if (confirmPassword.equals("")) 
				{
					idError = 258;  // check fill confirm_password 
				}
				else if (!newPassword.equals(confirmPassword)) 
				{
					idError = 259;  // new password not equal confirm password
				}
				else if (!curEncode.equals(oldEncode)) 
				{
					idError = 255;  // password not correct
				}
				if(idError == 0) 
				{
					jsk.setAuthen(newEncode);
					jskMgr.update(jsk, newPassword);
					response.sendRedirect("/SRIX?view=changeComplete");
				}
				else 
				{
					request.setAttribute("ErrorId", idError);
					request.getRequestDispatcher("/SRIX?view=changePassword").forward(request, response);
				}
			}
		}
		
		if(idJsk!=-1 && idResume!=-1){
			int success = new ResumeManager().insSuperMatchIdResume(idJsk,idResume);
			LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			json.put("success",success);
			out.print(gson.toJson(json));
			out.close();
		}
	}
	private static String msgEmail (Jobseeker jskData,Resume resumeData) 
	{
        String body = "<html>";
        if (resumeData.getIdLanguage() == 38) 
        {
        	body = body + "ถึง คุณ  "
        	+ resumeData.getFirstName()
        	+ " "
        	+ resumeData.getLastName()
        	+ "<br><br>    username ของคุณคือ : "
        	+ "<b>" + jskData.getUsername()+"</b>"
        	+ "<br><br>    password ของคุณคือ:  "
        	+ "<b>" +jskData.getPassword()+"</b>"
        	+ "<br><br>You can go to our web site http://www.superresume.com , www.jobtopgun.com or www.superattachfiled.com <br>If you have any questions or problems logging on to your account, please contact us at  webmaster@superresume.com<br><br>Sincerely<br>Superresume Team";
        }
        else 
        {
        	body = body + "To .."
        	+ resumeData.getFirstName()
    		+ " "
    		+ resumeData.getLastName()
    		+ "<br><br>    Your user name is:  "
    		+ "<b>" + jskData.getUsername()+"</b>"
    		+ "<br><br>    Your password is:  "
    		+ "<b>" +jskData.getPassword()+"</b>"
        	+ "<br><br>You can go to our web site http://www.superresume.com , www.jobtopgun.com or www.superattachfiled.com <br>If you have any questions or problems logging on to your account, please contact us at  webmaster@superresume.com<br><br>Sincerely<br>Superresume Team";
        }
        body = body + "</html>";
        return body;
	}

}
