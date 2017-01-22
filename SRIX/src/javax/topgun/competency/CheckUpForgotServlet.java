package javax.topgun.competency;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.util.MailManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class CheckUpForgotServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  
	{
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        String device = Util.getStr(request.getSession().getAttribute("SESSION_DEVICE"),"desktop");
        String agent = Util.getStr(request.getHeader("User-Agent"));
        CheckUpUserManager userMgr=new CheckUpUserManager();
        String urlErrorRedirect = "";
        
		String username="";
        
        int idUser=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_USER"));
        
		if(idUser<0)
		{
			username=Util.getStr(request.getParameter("username"));
			if(username.equals(""))
			{
				errors.add("โปรดระบุ <b>อีเมล</b>");
				elements.add("username");
			}
			else if(!Util.isEmail(username))
			{
				errors.add("<b>อีเมล</b> ไม่ถูกต้อง");
				elements.add("username");
			}
			
			if(errors.size()==0){
				if(userMgr.isExist(username)){
					CheckUpUser chkupUser = userMgr.get(username);
					if(chkupUser!=null){
						String subject = "แจ้ง Username password สำหรับเข้าใช้ระบบ Super competency and job check-up by Superresume";
						String content=	"<table align='center' width='550'><tr><td>"+
										"<p><b><u>แจ้ง Username password <br>สำหรับเข้าใช้ระบบ Super competency and job check-up by Superresume</u></b></p>" +
										"<p><b>Username&nbsp;:&nbsp;</b>"+chkupUser.getUsername()+"</p>" +
										"<p><b>Password&nbsp;:&nbsp;</b>"+chkupUser.getPassword()+"</p>" +
										"</td></tr></table>";
						String contentMail =	"	<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">	"+
												"	<html xmlns=\"http://www.w3.org/1999/xhtml\">	"+
												"	<head>	"+
												"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />	"+
												"	</head>	"+
												"	<style>	"+
												"	body{	"+
												"		font-family:tahoma;" +
												"		font-size:13px;	"+
												"	}	"+
												"		"+
												"	ul{	"+
												"		font-size: 12px;	"+
												"		padding-left:20px;	"+
												"		padding-bottom:0px;	"+
												"		padding-right:0px;	"+
												"		padding-top:0px;	"+
												"	}	"+
												"		"+
												"	li{	"+
												"		color:#56B5E6; "+
												"		padding-left:0px;	"+
												"	}	"+
												"	</style>	"+
												"		"+
												"	<body>	"+
												content +
												"	</body>	"+
												"	</html>	";				
				
						MailManager mailMgr=null;
						try
						{
							mailMgr=new MailManager();
							mailMgr.setSubject(subject);
							mailMgr.setRecepient(chkupUser.getUsername());
							//mailMgr.setRecepient("sittiporn@topgunthailand.com");
							mailMgr.setSender("support@jobtopgun.com");
							mailMgr.addHTML(contentMail);
							mailMgr.send();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					else{
						errors.add("<b>ไม่พบข้อมูลในระบบ</b>");
						elements.add("username");
					}
				}
				else{
					errors.add("<b>ไม่พบข้อมูลในระบบ</b>");
					elements.add("username");
				}
			}
		}
        
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
		LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
		if(errors.size()>0){
			json.put("success",0);
			json.put("errors", errors);
			json.put("elements", elements);
			json.put("urlError", urlErrorRedirect);
		}
		else{
			json.put("success",1);
		}
		out.print(gson.toJson(json));
		out.close();
	}
}
