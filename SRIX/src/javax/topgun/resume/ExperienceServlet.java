package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.ExperienceIndustry;
import com.topgun.resume.ExperienceIndustryManager;
import com.topgun.resume.ExperienceSummary;
import com.topgun.resume.ExperienceSummaryManager;
import com.topgun.resume.IndustryManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.shris.masterdata.Industry;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.WorkExperience;
import com.topgun.resume.WorkExperienceManager;
import com.topgun.shris.masterdata.JobField;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class ExperienceServlet  extends HttpServlet 
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
        Resume resume=new ResumeManager().get(idJsk, idResume);
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
        else if(service.equals("deleteExperience"))
		{
        	int success=0;
        	if(idResume==0)
        	{
            	new ExperienceSummaryManager().deleteAll(idJsk, idResume);
        		new WorkExperienceManager().deleteAll(idJsk,idResume);
        		new ExperienceIndustryManager().deleteAll(idJsk, idResume);
        		resume.setExpStatus("FALSE");
        		resume.setExpCompany(0);
        		resume.setExpMonth(0);
        		resume.setExpYear(0);
        		success=new ResumeManager().update(resume);
        	}
        	if(success!=1)
        	{
        		errors.add(propMgr.getMessage(resume.getLocale(), "WORKEXP_ASKEXP"));
				elements.add("expStatus");
        	}
        	int[] exp = new WorkExperienceManager().getExpTotal(idJsk, idResume);
			new WorkExperienceManager().updateWorkingExpTotal(idJsk, idResume, exp);
		}
        else if(service.equals("setExpStatus"))
		{
        	int success=0;
        	String expStatus=Util.getStr(request.getParameter("expStatus"));
        	if(!expStatus.equals(""))
        	{
        		resume.setExpStatus(expStatus);
        		success=new ResumeManager().update(resume);
        	}
        	if(success!=1)
        	{
        		errors.add(propMgr.getMessage(resume.getLocale(), "WORKEXP_ASKEXP"));
				elements.add("expStatus");
        	}
		}
        else if(service.equals("setExpCompany"))
        {
        	int success=0;
        	int expCompany=Util.getInt(request.getParameter("expCompany"));
        	if(expCompany>0)
        	{
        		resume.setExpCompany(expCompany);
        		success=new ResumeManager().update(resume);
        	}
        	if(success!=1)
        	{
        		errors.add(propMgr.getMessage(resume.getLocale(), "TOTAL_COMPANY_REQUIRED"));
				elements.add("totalCompany");
        	}        
		}
        else if(service.equals("addJobField"))
        {
        	int success=0;
        	
        	int expYear=Util.getInt(request.getParameter("expYear"),0);
        	int expMonth=Util.getInt(request.getParameter("expMonth"),0);
        	int idJobField=Util.getInt(request.getParameter("idJobField"));
        	
        	if(expYear>0 || expMonth>0)
        	{
        		ExperienceSummary expSum=new ExperienceSummaryManager().get(idJsk, idResume, idJobField);
        		if(expSum==null)
        		{
        			expSum=new ExperienceSummary();
        			expSum.setIdJsk(idJsk);
        			expSum.setIdResume(idResume);
        			expSum.setJobField(idJobField);
        			expSum.setSumYear(expYear);
        			expSum.setSumMonth(expMonth);
        			success=new ExperienceSummaryManager().add(expSum);
        		}
        		else
        		{
        			expSum.setSumYear(expYear);
        			expSum.setSumMonth(expMonth);
        			success=new ExperienceSummaryManager().update(expSum);
        		}
        		
        		int totalExp=new ExperienceSummaryManager().getTotalExperienceMonth(idJsk, idResume);
				int totalYear=totalExp/12;
				int totalMonth=totalExp-(totalYear*12);
				resume.setExpYear(totalYear);
				resume.setExpMonth(totalMonth);
				new ResumeManager().update(resume);
        	}
        	if(success!=1)
        	{
        		errors.add(propMgr.getMessage(resume.getLocale(), "EXPERIENCE_SUMMARY_ERROR"));
				elements.add("SYSTEM");
        	}
		}
        else if(service.equals("deleteJobFields"))
        {
        	int idJobField=Util.getInt(request.getParameter("idJobField"));
    		new ExperienceSummaryManager().delete(idJsk, idResume, idJobField);
    		int totalExp=new ExperienceSummaryManager().getTotalExperienceMonth(idJsk, idResume);
			int totalYear=totalExp/12;
			int totalMonth=totalExp-(totalYear*12);
			resume.setExpYear(totalYear);
			resume.setExpMonth(totalMonth);
			new ResumeManager().update(resume);
		}        
        else if(service.equals("viewJobFields"))
		{
			List<ExperienceSummary> jobFields = new ExperienceSummaryManager().getAll(idJsk, idResume);
			if(jobFields!=null)
			{
				String jobFieldNames[]=new String[jobFields.size()];
	
				for(int i=0; i<jobFields.size(); i++)
				{
					JobField jf=MasterDataManager.getJobField(jobFields.get(i).getJobField(), resume.getIdLanguage());
					if(jf!=null)
					{
						jobFieldNames[i]=Util.getStr(jf.getFieldName());
					}
					else
					{
						jobFieldNames[i]=Util.getStr(propMgr.getMessage(resume.getLocale(), "GLOBAL_OTHER"));
					}
				}
				int totalExp=new ExperienceSummaryManager().getTotalExperienceSummaryMonth(idJsk, idResume);
				int expYear=totalExp/12;
				int expMonth=totalExp-(expYear*12);
				json.put("jobFields",jobFields);
				json.put("jobFieldNames",jobFieldNames);
				json.put("expYear",expYear);
				json.put("expMonth",expMonth);
				
			}    
		}
        else if(service.equals("getLatest"))
		{
			WorkExperience experience = new WorkExperienceManager().getLatestExperience(idJsk, idResume);
			json.put("experience",experience);
		}  
        else if(service.equals("setExperience"))
        {
        	int result=0;
        	int exist=1;
        	int idWork=Util.getInt(request.getParameter("idWork"));
        	String company=Util.getStr(request.getParameter("company"));
        	int idCountry=Util.getInt(request.getParameter("idCountry"));
        	int idState=Util.getInt(request.getParameter("idState"));
        	String otherState=Util.getStr(request.getParameter("otherState"));
        	int jobType=Util.getInt(request.getParameter("jobType"));
        	int workJobField=Util.getInt(request.getParameter("workJobField"));
        	String workJobFieldOther=Util.getStr(request.getParameter("workJobFieldOther"));
        	int workSubField=Util.getInt(request.getParameter("workSubField"));
        	String workSubFieldName=Util.getStr(request.getParameter("workSubFieldName"));
        	int salaryLast=Util.getInt(request.getParameter("salaryLast"));
        	int salaryPer=Util.getInt(request.getParameter("salaryPer"));
        	int currency=Util.getInt(request.getParameter("currency"));
        	String jobDesc=Util.getStr(request.getParameter("jobDesc"));
        	String achieve=Util.getStr(request.getParameter("achieve"));
        	
        	//startDate        	
        	int startDay=1;
			int startMonth=Util.getInt(request.getParameter("startMonth"));
			int startYear=Util.getInt(request.getParameter("startYear"));      	
			Date dateStart=Util.getSQLDate(startDay, startMonth, startYear);
			
	      	int endDay=1;
			int endMonth=Util.getInt(request.getParameter("endMonth"));
			int endYear=Util.getInt(request.getParameter("endYear"));     
			Date dateEnd=Util.getSQLDate(endDay, endMonth, endYear);
	    		
			//String endDate=Util.getStr(request.getParameter("endDate"));
        	//String startDate=Util.getStr(request.getParameter("startDate"));
        	
        	String present=Util.getStr(request.getParameter("present"));
        	
        	WorkExperience workExp=new WorkExperienceManager().get(idJsk, idResume, idWork);
        	if(workExp==null)
        	{
        		exist=0;
        		workExp=new WorkExperience();
        		workExp.setId(1);
        		workExp.setIdJsk(idJsk);
        		workExp.setIdResume(idResume);
        	}
        	workExp.setCompanyName(company);
        	workExp.setIdCountry(idCountry);
        	workExp.setIdState(idState);
        	workExp.setOtherState(otherState);
        	workExp.setWorkJobType(jobType);
        	workExp.setWorkJobField(workJobField);
        	workExp.setWorkJobFieldOth(workJobFieldOther);
        	workExp.setWorkSubField(workSubField);
        	workExp.setWorkSubFieldOth(workSubFieldName);
        	workExp.setPositionLast(workSubFieldName);
        	workExp.setSalaryLast(salaryLast);
        	workExp.setSalaryPer(Util.getStr(salaryPer));
        	workExp.setSalaryPerStart(Util.getStr(salaryPer));
        	workExp.setIdCurrency(currency);
        	workExp.setIdCurrencyStart(currency);
        	if(dateStart!=null)
        	{
        		workExp.setWorkStart(dateStart);
        	}        	
        	workExp.setWorkingStatus(present);
        	if(!present.equals("TRUE"))
        	{
            	if(dateEnd!=null)
            	{
            		workExp.setWorkEnd(dateEnd);
            	}
        	}
        	else
        	{
        		workExp.setWorkEnd(Util.getCurrentSQLDate());
        	}
        	
        	if(workExp.getWorkStart()!=null && workExp.getWorkEnd()!=null)
        	{
        		int totalMonth=Util.getMonthInterval(workExp.getWorkStart(), workExp.getWorkEnd());
        		int year=totalMonth/12;
        		int month=totalMonth%12;
        		workExp.setExpM(month);
        		workExp.setExpY(year);
        	}
        	
        	if(workExp.getWorkStart()!=null && workExp.getWorkEnd()!=null)
        	{
	        	if(workExp.getWorkEnd().compareTo(workExp.getWorkStart())<0)
	        	{
	        		java.sql.Date tmpDate=workExp.getWorkEnd();
	        		workExp.setWorkEnd(workExp.getWorkStart());
	        		workExp.setWorkStart(tmpDate);
	        	}
        	}
        	
        	if(jobDesc.length()>1000)
        	{
        		jobDesc=jobDesc.substring(0,1000);
        	}
        	if(achieve.length()>1000)
        	{
        		achieve=achieve.substring(0,1000);
        	}
        	workExp.setJobDesc(jobDesc);
        	workExp.setAchieve(achieve);
        	String hashRefer=Util.getStr(workExp.getHashRefer());
			if(hashRefer.equals("")) //update parent hash refer
			{
				hashRefer=Util.getHashString();
				workExp.setHashRefer(hashRefer);
			}
        	
        	if(exist==1)
        	{
        		result=new WorkExperienceManager().update(workExp);
        	}
        	else
        	{
        		result=new WorkExperienceManager().add(workExp);
        	}
        	
        	if(result==1)
        	{
        		new ResumeManager().updateTimestamp(resume.getIdJsk(),resume.getIdResume());
        		int totalYear=0;
            	int totalMonth=0;
            	int totalExp=new ExperienceSummaryManager().getTotalExperienceMonth(idJsk, idResume);
            	if(totalExp>0)
            	{
            		totalYear=totalExp/12;
            		totalMonth=totalExp%12;
            	}
            	resume.setExpYear(totalYear);
    			resume.setExpMonth(totalMonth);
    			new ResumeManager().update(resume);
    			int[] exp = new WorkExperienceManager().getExpTotal(idJsk, idResume);
    			new WorkExperienceManager().updateWorkingExpTotal(idJsk, idResume, exp);
        	}
        	else
        	{
        		errors.add(propMgr.getMessage(resume.getLocale(), "RESUME_EXPERIENCE_ERROR"));
				elements.add("SYSTEM");	
        	}
        }
        else if(service.equals("getExperienceIndustry"))
		{
        	int idWork=Util.getInt(request.getParameter("idWork"));
			List<ExperienceIndustry> industries = new ExperienceIndustryManager().getExpIndustry(idJsk, idResume, idWork);
			String names[]=new String[industries.size()];
			if(industries.size()>0)
			{
				for(int i=0; i<industries.size(); i++)
				{
					Industry industry=MasterDataManager.getIndustry(industries.get(i).getIdIndustry(), resume.getIdLanguage());
					if(industry!=null)
					{
						names[i]=industry.getIndustryName();
					}
					else
					{
						names[i]=industries.get(i).getOtherIndustry();
					}
				}
			}
			json.put("industries",industries);
			json.put("names",names);
		}  
        
        else if(service.equals("saveWorkIndustry"))
 		{
        	int idIndustry=Util.getInt(request.getParameter("idIndustry"));
        	int idWorkLastest=Util.getInt(request.getParameter("idWorkLastest"));
        	if(idWorkLastest==-1)
        	{	idWorkLastest=1;}
        	String industryName=Util.getStr(request.getParameter("industryName"));
        	String otherIndustry=Util.getStr(request.getParameter("otherIndustry"));
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	ExperienceIndustry ind=new ExperienceIndustry();
        	int orderIndustry=new ExperienceIndustryManager().getMaxOrderIndus(idJsk, idResume, 1)+1;
        	if(idIndustry==-1)
        	{
        		idIndustry=new ExperienceIndustryManager().getMinIdIndus(idJsk, idResume, 1)-1;
        	}
			ind.setIdJsk(idJsk);
			ind.setIdResume(idResume);
			ind.setIdWork(idWorkLastest);
			ind.setIdIndustry(idIndustry);
			ind.setOrderIndustry(orderIndustry);
			ind.setOtherIndustry(otherIndustry);
			int resultAdd=new ExperienceIndustryManager().add(ind);
			if(resultAdd==0)
			{
				errors.add(propMgr.getMessage(locale, "CANCEL_ERROR")+":("+idJsk+","+idResume+")");
				elements.add("SYSTEM");
			}
			else
			{
				json.put("idIndustry",idIndustry);
				json.put("industryName",industryName);
				if(idIndustry==-1)
	        	{
					json.put("industryName",otherIndustry);
	        	}
			}
 		}
        else if(service.equals("delIndustry"))
 		{
        	int idIndustry=Util.getInt(request.getParameter("idIndustry"));
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	int resultDel=new ExperienceIndustryManager().delete(idJsk, idResume, 1, idIndustry);
			if(resultDel==0)
			{
				errors.add(propMgr.getMessage(locale, "CANCEL_ERROR")+":("+idJsk+","+idResume+")");
				elements.add("SYSTEM");
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
