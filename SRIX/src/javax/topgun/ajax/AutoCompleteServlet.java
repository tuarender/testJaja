package javax.topgun.ajax;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.util.Util;



public class AutoCompleteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        String service = Util.getStr(request.getParameter("service")!=null?request.getParameter("service"):"");
        int idCountry = Integer.parseInt(request.getParameter("idCountry")!=null?request.getParameter("idCountry"):"0");
		int idState = Integer.parseInt(request.getParameter("idState")!=null?request.getParameter("idState"):"0");
		int facultyLevel = Integer.parseInt(request.getParameter("facultyLevel")!=null?request.getParameter("facultyLevel"):"0");
		int idFaculty = Integer.parseInt(request.getParameter("idFaculty")!=null?request.getParameter("idFaculty"):"0");
		int idField = Integer.parseInt(request.getParameter("idField")!=null?request.getParameter("idField"):"0");
		String query = request.getParameter("query")!=null?request.getParameter("query"):"";
		DataInputStream in = null;
		//System.out.println(query);
        try{
        	//long startTime=new java.util.Date().getTime();
        	Gson gson = new Gson();
			ServletContext context = request.getSession().getServletContext();
			String fileParth = "";
			if(service.equals("university")){
				fileParth = "/contents/university/university_"+idCountry+"_"+idState+".txt";
			}
			else if(service.equals("faculty")){
				fileParth = "/contents/faculty/faculty_"+facultyLevel+".txt";
			}
			else if(service.equals("major")){
				fileParth = "/contents/faculty/major_"+idFaculty+".txt";
			}
			else if(service.equals("subField")){
				fileParth = "/contents/subField/subField_"+idField+".txt";
			}
			if(!fileParth.equals("")){
				String propertiesPath = context.getRealPath(fileParth);
				if(new File(propertiesPath).exists()){				
					FileInputStream fstream = new FileInputStream(propertiesPath);
					in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
					String strLine;
					String json = "";
					ArrayList<AutoComplete> searchDataList = new ArrayList<AutoComplete>();
					while ((strLine = br.readLine()) != null){
						String[] data = strLine.split("%");
						if(data[0].toLowerCase().indexOf(query.toLowerCase())>=0){
							AutoComplete searchData = new AutoComplete();
							searchData.setLabel(data[0]);
							searchData.setId(data[1]);
							searchDataList.add(searchData);
						}
					}
					json = gson.toJson(searchDataList);
					out.println (json);
				}
				else{
					System.out.println("file not exist!");
				}
			}
			else{
				System.out.println("Access parameter error!");
			}
			//long usage=new java.util.Date().getTime()-startTime;
		}catch (Exception e){//Catch exception if any
			e.printStackTrace();
		}
		finally{
			if(in!=null){
				in.close();
			}
			out.flush();
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("POST");
	}

}
