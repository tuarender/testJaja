package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.CancelServiceManager;
import com.topgun.util.Util;
import javax.xml.bind.DatatypeConverter;

public class NonMemberServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        long startTime=new java.util.Date().getTime();

		String urlErrorRedirect = "";
		String email = "";
		String emailEncode=Util.getStr(request.getParameter("emailEncode"));
		if(!emailEncode.equals("")){
			String service=Util.getStr(request.getParameter("service"));
			if(service.equals("cancelNonMember")){
				email = new String(DatatypeConverter.parseBase64Binary(emailEncode));
				if(CancelServiceManager.cancelNonMember(email)<0){
					errors.add("Update data error,try again later.");
					elements.add("SYSTEM");
				}
			}
			else{
				errors.add("Cann't found any service.");
				elements.add("SYSTEM");
			}
		}
		else{
			errors.add("Cann't found email.");
			elements.add("SYSTEM");
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
			json.put("urlError", urlErrorRedirect);
		}
		else{
			json.put("success",1);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		if(usageTime>500){
			System.out.println("\nNonMemberServlet => Usage "+usageTime+" msec");
			System.out.println("email=> "+email);
		}
	}

}
