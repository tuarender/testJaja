package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.Gson;
import com.topgun.util.DBManager;
import com.topgun.util.Util;
import com.topgun.resume.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.*;

public class ResumeFullFillServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String service = Util.getStr(request.getParameter("service"));
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
        
        if(service.equals("update"))
        {
        	int idJsk = Util.getInt(request.getParameter("idJsk"));
    		int idResume1 = Util.getInt(request.getParameter("idResume1"));
    		int idResume2 = Util.getInt(request.getParameter("idResume2"));
    		List<Resume> subResume = new ResumeManager().getSubResume(idJsk, idResume1);
    		if(idJsk>0 && idResume1>=0 && idResume2>0)
    		{
    			for(int k=0;k<subResume.size();k++)
    			{
    				idResume2 = subResume.get(k).getIdResume();
					Resume resume2 = new ResumeManager().get(idJsk, idResume2);
					// personal data part //
		    		String firstName = Util.getStr(request.getParameter("firstName"));
		    		String lastName = Util.getStr(request.getParameter("lastName"));
		    		resume2.setFirstName(firstName);
		    		resume2.setLastName(lastName);
		    		if(new ResumeManager().update(resume2)!=1)
		    		{
		    			errors.add("update Personal Data Errors");
		    		}
		    		
		    		// education part //
		    		List<Education> eduList2 = new EducationManager().getAll(idJsk,idResume2);
		    		for(int i=0;i<eduList2.size();i++)
		    		{
		    			Education edu2 = eduList2.get(i);
		    			String schoolNameEng = Util.getStr(request.getParameter("school_"+edu2.getHashRefer()));
		    			String facultyNameEng = Util.getStr(request.getParameter("faculty_"+edu2.getHashRefer()));
		    			String majorNameEng = Util.getStr(request.getParameter("major_"+edu2.getHashRefer()));
		    			if(edu2.getIdSchool()==-1)
		    			{
		    				edu2.setOtherSchool(schoolNameEng);
		    			}
		    			
		    			if(edu2.getIdFacMajor()==-1)
		    			{
		    				edu2.setOtherFaculty(facultyNameEng);
		    			}
		    			
		    			if(edu2.getIdMajor()==-1)
		    			{
		    				edu2.setOtherMajor(majorNameEng);
		    			}
		    			
		    			if(new EducationManager().update(edu2)!=1)
		    			{
		    				errors.add("update Education Errors");
		    			}
		    		}
		    		
		    		// experience part //
		    		List<WorkExperience> expList2 = new WorkExperienceManager().getAll(idJsk,idResume2);
		    		
		    		for(int i=0;i<expList2.size();i++)
		    		{
		    			WorkExperience exp2 = expList2.get(i);
		    			String companyNameEng = Util.getStr(request.getParameter("company_"+exp2.getHashRefer()));
		    			String jobfieldNameEng = Util.getStr(request.getParameter("jobfield_"+exp2.getHashRefer()));
		    			String positionNameEng = Util.getStr(request.getParameter("position_"+exp2.getHashRefer()));
		    			String jobFiledNameStartEng = Util.getStr(request.getParameter("jobfieldStart_"+exp2.getHashRefer()));
		    			String positionNameStartEng = Util.getStr(request.getParameter("positionStart_"+exp2.getHashRefer()));
		    			exp2.setCompanyName(companyNameEng);
		    			if(exp2.getWorkJobField()==-1)
		    			{
		    				exp2.setWorkJobFieldOth(jobfieldNameEng);
		    			}
		    			if(exp2.getWorkSubField()==-1)
		    			{
		    				exp2.setPositionLast(positionNameEng);
		    				exp2.setWorkSubFieldOth(positionNameEng);
		    			}
		    			if(exp2.getWorkJobFieldStart()==-1 && !exp2.getWorkJobFieldOthStart().equals(""))
		    			{
		    				exp2.setWorkJobFieldOthStart(jobFiledNameStartEng);
		    			}
		    			if(exp2.getWorkSubFieldStart()==-1 && !exp2.getWorkSubFieldOthStart().equals(""))
		    			{
		    				exp2.setPositionStart(positionNameStartEng);
		    				exp2.setWorkSubFieldOthStart(positionNameStartEng);
		    			}
		    			if(new WorkExperienceManager().update(exp2)!=1)
		    			{
		    				errors.add("update Exp Errors");
		    			}
		    		}
    			}
    		}else{
    			errors.add("not have idJsk and idResume");
    		}
    		
        }
        response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
  		if(errors.size()>0)
  		{	
  			json.put("success",0);
  			json.put("errors", errors);
  			json.put("elements", elements);
  		}
  		else
  		{
  			json.put("success",1);			
  		}
  		out.print(gson.toJson(json));
  		out.close();
		
		
	}

}
