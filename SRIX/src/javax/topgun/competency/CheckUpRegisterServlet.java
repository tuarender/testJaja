package javax.topgun.competency;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.Encoder;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class CheckUpRegisterServlet extends HttpServlet {
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
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        String device = Util.getStr(request.getSession().getAttribute("SESSION_DEVICE"),"desktop");
        String agent = Util.getStr(request.getHeader("User-Agent"));
        CheckUpUserManager userMgr=new CheckUpUserManager();
        String urlErrorRedirect = "";
        
		String username="";
		String password="";
		String confirm="";
        
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
			else if(userMgr.isExist(username))
			{
				errors.add("<b>อีเมล</b> มีอยู่แล้วในระบบ");//pending
				elements.add("username");
			}
			
			//password
			password=Util.getStr(request.getParameter("password"));
			if(password.equals(""))
			{
				errors.add("โปรดระบุ <b>รหัสผ่าน</b>");
				elements.add("password");
			}
	
			//confirm password
			confirm=Util.getStr(request.getParameter("confirm"));
			if(!confirm.equals(password))
			{
				errors.add("<b>ยืนยันรหัสผ่าน</b> ไม่ถูกต้อง");
				elements.add("confirm");
			}
			
			if(errors.size()==0){
				idUser=userMgr.getNextId();
				CheckUpUser user =new CheckUpUser();			
				user.setIdUser(idUser);
				user.setUsername(username);
				user.setPassword(password);
				if(userMgr.add(user)==1){
					request.getSession().setAttribute("SESSION_ID_USER", idUser);
				}
				else{
					errors.add("ระบบขัดข้อง ไม่สามารถลงทะเบียนได้ในขณะนี้ โปรดลองใหม่ในภายหลัง ขออภัยในความไม่สะดวก");
					elements.add("SYSTEM");
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
