package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.JobType;
import com.topgun.resume.JobTypeManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.TargetJobField;
import com.topgun.resume.TargetJobFieldManager;
import com.topgun.shris.masterdata.JobField;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.shris.masterdata.SubField;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.google.gson.Gson;

public class TargetJobFieldServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{       
        Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
        ResumeManager rsmMgr=new ResumeManager();
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
        String service=Util.getStr(request.getParameter("service"));
        Resume resume=rsmMgr.get(idJsk, idResume);
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
        else if(service.equals("add"))
		{
        	if(new TargetJobFieldManager().count(idJsk, idResume)<3) //maximum 3 jobfields per jobseeker
        	{
				int idJobField=Util.getInt(request.getParameter("idJobField"),-1);
				int idSubField=Util.getInt(request.getParameter("idSubField"),-1);
				String otherJobField=Util.getStr(request.getParameter("otherJobField"));
				String otherSubField=Util.getStr(request.getParameter("otherSubField"));
				if((idJobField>0 || !otherJobField.equals("")) && (idSubField>0 || !otherSubField.equals("")))
				{
					if(idJobField<0)
					{
						idJobField=new TargetJobFieldManager().getNextOtherJobFieldId(idJsk, idResume);
					}
					if(idSubField<0)
					{
						idSubField=new TargetJobFieldManager().getNextOtherSubFieldId(idJsk, idResume,idJobField);
					}
					TargetJobField jf=new TargetJobFieldManager().get(idJsk, idResume, idJobField,idSubField);
					if(jf==null)
					{
						jf=new TargetJobField();
						jf.setIdJsk(idJsk);
						jf.setIdResume(idResume);
						jf.setIdJobfield(idJobField);
						jf.setIdSubfield(idSubField);
						jf.setOtherJobfield(otherJobField);
						jf.setOtherSubfield(otherSubField);
						jf.setJobfieldOrder(new TargetJobFieldManager().getMaxJobFieldOrder(idJsk, idResume)+1);
						if(new TargetJobFieldManager().add(jf)!=1)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_JOBFIELD_FAIL"));
							elements.add("SYSTEM");	
						}
					}
				}
        	}
		}
		else if(service.equals("view"))
		{
			List<TargetJobField> jobFields = new TargetJobFieldManager().getAll(idJsk, idResume);
			if(jobFields!=null)
			{
				String jobFieldNames[]=new String[jobFields.size()];
				String subFieldNames[]=new String[jobFields.size()];
	
				for(int i=0; i<jobFields.size(); i++)
				{
					JobField jf=MasterDataManager.getJobField(jobFields.get(i).getIdJobfield(), resume.getIdLanguage());
					if(jf!=null)
					{
						jobFieldNames[i]=Util.getStr(jf.getFieldName());
					}
					else
					{
						jobFieldNames[i]=Util.getStr(jobFields.get(i).getOtherJobfield());
					}
					SubField sf=MasterDataManager.getSubField(jobFields.get(i).getIdJobfield(),jobFields.get(i).getIdSubfield(), resume.getIdLanguage());
					if(sf!=null)
					{
						subFieldNames[i]=Util.getStr(sf.getSubfieldName());
					}
					else
					{
						subFieldNames[i]=Util.getStr(jobFields.get(i).getOtherSubfield());
					}
				}
				
				json.put("jobFields",jobFields);
				json.put("jobFieldNames",jobFieldNames);
				json.put("subFieldNames",subFieldNames);
			}
		}
		else if(service.equals("delete"))
		{
			int idJobField = Util.getInt(request.getParameter("idJobField"));
			int idSubField = Util.getInt(request.getParameter("idSubField"));
			
			TargetJobField jf = new TargetJobFieldManager().get(idJsk, idResume, idJobField, idSubField);
			if(jf!=null)
			{
				if(new TargetJobFieldManager().delete(jf)!=1)
				{
					errors.add(propMgr.getMessage(resume.getLocale(), "DELETE_JOBFIELD_FAIL"));
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
