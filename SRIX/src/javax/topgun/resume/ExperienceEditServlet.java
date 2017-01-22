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
import com.topgun.shris.masterdata.Industry;
import com.topgun.resume.Education;
import com.topgun.resume.ExperienceSummary;
import com.topgun.resume.ExperienceSummaryManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.WorkExperience;
import com.topgun.resume.WorkExperienceManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class ExperienceEditServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Gson gson = new Gson();
		long startTime=new java.util.Date().getTime();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
        
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
        String service=Util.getStr(request.getParameter("service"));
        
        ResumeManager rsmMgr=new ResumeManager();
        ExperienceSummaryManager sumMgr=new ExperienceSummaryManager();
        WorkExperienceManager expMgr=new WorkExperienceManager();
        
        Resume resume=rsmMgr.get(idJsk, idResume);
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
        else if(service.equals("delete"))
        {
        	int idWork=Util.getInt(request.getParameter("idWork"));
        	WorkExperience workExp=expMgr.get(idJsk, idResume, idWork);
        	expMgr.delete(idJsk, idResume, idWork);//update INTER_WORK_EXPERIENCE
        	new ExperienceIndustryManager().deleteAll(idJsk, idResume, idWork);//update INTER_EXPERIENCE_INDUSTRY
        	int totalYear=0;
        	int totalMonth=0;
        	int totalExp=sumMgr.getTotalExperienceMonth(idJsk, idResume);
        	if(totalExp>0)
        	{
        		totalYear=totalExp/12;
        		totalMonth=totalExp%12;
        	}
        	resume.setExpYear(totalYear);//set exp_year
			resume.setExpMonth(totalMonth);//set exp_month
        	List<WorkExperience> rsWorkExp=expMgr.getAll(idJsk, idResume);
    		if(rsWorkExp.size()>0)
    		{
    			resume.setExpStatus("TRUE");//set exp_status
    			resume.setExpCompany(rsWorkExp.size());//set exp_company
    		}
    		else// no work exp
    		{
    			resume.setExpStatus("FALSE");
    			resume.setExpCompany(0);
    		}
    		rsmMgr.update(resume);// update inter_resume(exp_status, exp_company, exp_month,exp_year)
    		
    		
    		sumMgr.deleteAll(idJsk, idResume);
    		List<ExperienceSummary> expSumList=sumMgr.getExperienceSummaryInfo(idJsk, idResume);
    		if(expSumList.size()>0)
    		{
    			for(int i=0; i<expSumList.size();i++)
    			{
					ExperienceSummary expSum=new ExperienceSummary();
					expSum.setIdJsk(idJsk);
					expSum.setIdResume(idResume);
					expSum.setJobField(expSumList.get(i).getJobField());
					expSum.setSumMonth(expSumList.get(i).getSumMonth()%12);
					expSum.setSumYear(expSumList.get(i).getSumMonth()/12);
					sumMgr.add(expSum);//update inter_experience_summary
    			}
    		}
    		int[] exp = expMgr.getExpTotal(idJsk, idResume);
			new WorkExperienceManager().updateWorkingExpTotal(idJsk, idResume, exp);
        }
        else if(service.equals("getExperience"))
		{
        	int idWork=Util.getInt(request.getParameter("idWork"));
        	if(idWork>=0)
        	{
        		WorkExperience experience = expMgr.get(resume.getIdJsk(), resume.getIdResume(), idWork);
        		json.put("experience",experience);
        	}
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
        	int workJobFieldStart=Util.getInt(request.getParameter("workJobFieldStart"));
        	String workJobFieldStartText=Util.getStr(request.getParameter("workJobFieldStart"));
        	String workJobFieldStartOther=Util.getStr(request.getParameter("workJobFieldStartOther"));
        	int workSubFieldStart=Util.getInt(request.getParameter("workSubFieldStart"));
        	String workSubFieldStartName=Util.getStr(request.getParameter("workSubFieldStartName"));
        	int salaryLast=Util.getInt(request.getParameter("salaryLast"),0);
        	int salaryStart=Util.getInt(request.getParameter("salaryStart"),0);
        	int salaryPer=Util.getInt(request.getParameter("salaryPer"));
        	int salaryPerStart=Util.getInt(request.getParameter("salaryPerStart"));
        	int currency=Util.getInt(request.getParameter("currency"));
        	int currencyStart=Util.getInt(request.getParameter("currencyStart"));
        	String present=Util.getStr(request.getParameter("present"));
        	String jobDesc=Util.getStr(request.getParameter("jobDesc"));
        	String achieve=Util.getStr(request.getParameter("achieve"));
        	String reasonQuit=Util.getStr(request.getParameter("reasonQuit"));
        	String comBusiness=Util.getStr(request.getParameter("comBusiness"));
        	int comSize=Util.getInt(request.getParameter("comSize"));
        	int subordinate=Util.getInt(request.getParameter("subordinate"));
        	
        	//startDate        	
        	int startDay=1;
			int startMonth=Util.getInt(request.getParameter("startMonth"));
			int startYear=Util.getInt(request.getParameter("startYear"));      	
			Date dateStart=Util.getSQLDate(startDay, startMonth, startYear);
			
	      	int endDay=1;
			int endMonth=Util.getInt(request.getParameter("endMonth"));
			int endYear=Util.getInt(request.getParameter("endYear"));     
			Date dateEnd=Util.getSQLDate(endDay, endMonth, endYear);
        	
        	
        	
        	WorkExperience workExp=expMgr.get(idJsk, idResume, idWork);
        	if(workExp==null)
        	{
        		exist=0;
        		workExp=new WorkExperience();
        		workExp.setId(expMgr.getNextId(resume.getIdJsk(), resume.getIdResume()));
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
        	
        	if(workJobFieldStartText.trim().length() > 0)
        	{
	        	workExp.setWorkJobFieldStart(workJobFieldStart);
	        	workExp.setWorkJobFieldOthStart(workJobFieldStartOther);
	        	workExp.setWorkSubFieldStart(workSubFieldStart);
	        	workExp.setWorkSubFieldOthStart(workSubFieldStartName);
	        	workExp.setPositionStart(workSubFieldStartName);
        	}
        	else
        	{
        		workExp.setWorkJobFieldStart(Integer.MIN_VALUE);
	        	workExp.setWorkJobFieldOthStart("");
	        	workExp.setWorkSubFieldStart(Integer.MIN_VALUE);
	        	workExp.setWorkSubFieldOthStart("");
	        	workExp.setPositionStart("");
        	}
        	
        	if(jobDesc.length()>1000)
        	{
        		jobDesc=jobDesc.substring(0,1000);
        	}
        	if(reasonQuit.length()>1000)
        	{
        		reasonQuit=reasonQuit.substring(0,1000);
        	}
        	if(achieve.length()>1000)
        	{
        		achieve=achieve.substring(0,1000);
        	}
        	workExp.setSalaryLast(salaryLast);
        	workExp.setSalaryStart(salaryStart);
        	workExp.setSalaryPer(Util.getStr(salaryPer));
        	workExp.setSalaryPerStart(Util.getStr(salaryPerStart));
        	workExp.setIdCurrency(currency);
        	workExp.setIdCurrencyStart(currencyStart);
        	workExp.setJobDesc(jobDesc);
        	workExp.setReasonQuit(reasonQuit);
        	workExp.setAchieve(achieve);
        	workExp.setComBusiness(comBusiness);
        	workExp.setComSize(comSize);
        	workExp.setSubordinate(subordinate);
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
        	
        	if(workExp.getWorkEnd().compareTo(workExp.getWorkStart())<0)
        	{
        		java.sql.Date tmpDate=workExp.getWorkEnd();
        		workExp.setWorkEnd(workExp.getWorkStart());
        		workExp.setWorkStart(tmpDate);
        	}
        	String hashRefer=Util.getStr(workExp.getHashRefer());
			if(hashRefer.equals("")) //update parent hash refer
			{
				hashRefer=Util.getHashString();
				workExp.setHashRefer(hashRefer);
			}
        	
        	if(exist==1)
        	{
        		result=expMgr.update(workExp);
        	}
        	else
        	{
        		result=expMgr.add(workExp);
        	}
        	workExp.setIdResume(idResume);
        	workExp.setIdRefer(0);
        	if(result==0)
        	{
        		System.out.println("ExperienceEditServlet Error ADD/Update->"+idJsk+"=>"+idResume+"=>"+workExp.getId());
        	}
        	
        	if(result==1)
        	{
        		String industries[]=new String[3];
        		industries[0]=Util.getStr(request.getParameter("idIndustry_1"));
        		industries[1]=Util.getStr(request.getParameter("idIndustry_2"));
        		industries[2]=Util.getStr(request.getParameter("idIndustry_3"));
        		
        		if(industries[0].split("_").length==2 || industries[1].split("_").length==2|| industries[2].split("_").length==2)
        		{
					new ExperienceIndustryManager().deleteAll(resume.getIdJsk(), resume.getIdResume(),workExp.getId());	
					int rank=1;
	        		for(int i=0; i<industries.length; i++)
	        		{
	        			if(!industries[i].equals(""))
	        			{
	        				String industryOther="";
	        				if(industries[i].indexOf("_")!=-1)
	        				{
	        					String ids[]=industries[i].split("_");
	        					if(ids.length==2)
	        					{
	        						int idIndustry=Util.getInt(ids[0]);
	        						industryOther=Util.getStr(ids[1]);
	        						if(idIndustry>0) industryOther="";
	        						if((idIndustry>0) || !industryOther.equals(""))
	        						{
	        							ExperienceIndustry ind=new ExperienceIndustry();
	        							ind.setIdJsk(resume.getIdJsk());
	        							ind.setIdResume(resume.getIdResume());
	        							ind.setIdWork(workExp.getId());
	        							ind.setIdIndustry(idIndustry);
	        							ind.setOrderIndustry(rank);
	        							ind.setOtherIndustry(industryOther);
	        							if(new ExperienceIndustryManager().add(ind)==1)
	        							{
	        								rank++;
	        							}
	        						}
	        					}
	        				}
	        			}
	        		}
        		}        		
        		
        		
        		int totalYear=0;
            	int totalMonth=0;
            	int totalExp=sumMgr.getTotalExperienceMonth(idJsk, idResume);
            	if(totalExp>0)
            	{
            		totalYear=totalExp/12;
            		totalMonth=totalExp%12;
            	}
            	resume.setExpYear(totalYear);
    			resume.setExpMonth(totalMonth);
    			List<WorkExperience> rsWorkExp=expMgr.getAll(idJsk, idResume);
        		if(rsWorkExp.size()>0)
        		{
        			resume.setExpStatus("TRUE");
        			resume.setExpCompany(rsWorkExp.size());
        		}
        		else// no work exp
        		{
        			resume.setExpStatus("FALSE");
        			resume.setExpCompany(0);
        		}
        		rsmMgr.update(resume);
        		sumMgr.deleteAll(idJsk, idResume);
        		List<ExperienceSummary> expSumList=sumMgr.getExperienceSummaryInfo(idJsk, idResume);
        		if(expSumList.size()>0)
        		{
        			for(int i=0; i<expSumList.size();i++)
        			{
    					ExperienceSummary expSum=new ExperienceSummary();
    					expSum.setIdJsk(idJsk);
    					expSum.setIdResume(idResume);
    					expSum.setJobField(expSumList.get(i).getJobField());
    					expSum.setSumMonth(expSumList.get(i).getSumMonth()%12);
    					expSum.setSumYear(expSumList.get(i).getSumMonth()/12);
    					sumMgr.add(expSum);//update inter_experience_summary
        			}
        		}
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
        	int idWork=Util.getInt(request.getParameter("idWork"));
        	String industryName=Util.getStr(request.getParameter("industryName"));
        	String otherIndustry=Util.getStr(request.getParameter("otherIndustry"));
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	ExperienceIndustry ind=new ExperienceIndustry();
        	int orderIndustry=new ExperienceIndustryManager().getMaxOrderIndus(idJsk, idResume, idWork)+1;
        	if(idIndustry==-1)
        	{
        		idIndustry=new ExperienceIndustryManager().getMinIdIndus(idJsk, idResume, idWork)-1;
        	}
			ind.setIdJsk(idJsk);
			ind.setIdResume(idResume);
			ind.setIdWork(idWork);
			ind.setIdIndustry(idIndustry);
			ind.setOrderIndustry(orderIndustry);
			ind.setOtherIndustry(otherIndustry);
			int resultAdd=0;
			ExperienceIndustry resultIndus=new ExperienceIndustryManager().get(idJsk, idResume, idWork, idIndustry);
			if(resultIndus==null)
			{
				resultAdd=new ExperienceIndustryManager().add(ind);
			}
			else
			{
				resultAdd=1;
			}
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
        	int idWork=Util.getInt(request.getParameter("idWork"));
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	int resultDel=new ExperienceIndustryManager().delete(idJsk, idResume, idWork, idIndustry);
			if(resultDel==0)
			{
				errors.add(propMgr.getMessage(locale, "CANCEL_ERROR")+":("+idJsk+","+idResume+")");
				elements.add("SYSTEM");
			}
 		}
        else if(service.equals("fillExperience"))
        {
        	int idWork=Util.getInt(request.getParameter("idWork"));
        	WorkExperience exp = new WorkExperienceManager().get(idJsk, idResume, idWork);
        	String jobDesc=Util.getStr(request.getParameter("jobDesc"));
        	String achieve=Util.getStr(request.getParameter("achieve"));
        	if(jobDesc.length()>1000)
        	{
        		jobDesc=jobDesc.substring(0,1000);
        	}
        	if(achieve.length()>1000)
        	{
        		achieve=achieve.substring(0,1000);
        	}
        	if(!jobDesc.equals(exp.getJobDesc()))
        	{
        		expMgr.addResponsibilityLog(idJsk, idResume);
        	}
        	if(!achieve.equals(exp.getAchieve()))
        	{
        		expMgr.addAchievementLog(idJsk, idResume);
        	}
        	exp.setJobDesc(jobDesc);
        	exp.setAchieve(achieve);
        	if(expMgr.update(exp)==0)
        	{
        		errors.add("Can't Fill Exp");
				elements.add("SYSTEM");
        	}
        	expMgr.addSubmitLog(idJsk, idResume);
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
		long usage=new java.util.Date().getTime()-startTime;
		System.out.println("ExperienceEditServlet Usage=>"+usage+" msec for=>ID_JSK= "+idJsk+",ID_RESUME= "+idResume);
	}
}
