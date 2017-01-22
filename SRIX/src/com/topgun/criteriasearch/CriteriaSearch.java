package com.topgun.criteriasearch;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.topgun.util.Util;
import com.topgun.util.WebConnection;

 

public class CriteriaSearch 
{
	private static final String[] oldJsonURL={"http://apps.jobtopgun.com/JTGServices"};
	private static final String[] jsonURL={"http://apps.jobtopgun.com/JTGXIISearchServices"};
	private Resume resume=null;
	
	private int jobsBeforeDay = 1;
	
	CriteriaSearch(Resume resume)
	{
		this.resume=resume;
		 	 
	}
	public JobListJSON getJobList()
	{
		JobListJSON result =new JobListJSON();
		int idx=(int)(new java.util.Date().getTime()%jsonURL.length);
		WebConnection webConnection = null;
		String urlService=jsonURL[idx]+"/iServ.php?service=getCriteriaEmailSearchResult";
		//System.out.println("urlService"+urlService);
		try 
		{	
			webConnection = new WebConnection(urlService);
			webConnection.addParameter("compressed", 1);
			webConnection.addParameter("idJsk", this.resume.getIdJsk());
			webConnection.addParameter("idSearch", this.resume.getIdSearch());
			webConnection.addParameter("idLanguage", this.resume.getIdLanguage());
			webConnection.addParameter("criteriaSearchMailFlag", this.resume.getCriteriaSearchMailFlag());
			
			JsonReader reader = webConnection.getJsonReaderFromGzip();	        
    		reader.beginObject();
    		while (reader.hasNext()) 
    		{
    			String key = reader.nextName();
    			if(reader.peek() != JsonToken.NULL)
    			{
    				if(key.equals("total"))	result.setTotal(reader.nextInt());
    				else if(key.equals("_scroll_id"))	result.setScrollId(reader.nextString());
    				else if(key.equals("hits"))
    				{
    					reader.beginArray();
    					while(reader.hasNext())
    					{
    						reader.beginObject();
    						while(reader.hasNext())
    						{
    							String key2 = reader.nextName();
        						if(key2.equals("_source"))
        						{
        							JobList jobList=new JobList();
        							reader.beginObject();
        							while(reader.hasNext())
        							{
        		        				String key3=reader.nextName();
        		        				if (key3.equals("ID_EMP"))	jobList.setIdEmp(reader.nextInt());
        		        				else if (key3.equals("ID_POSITION"))	jobList.setIdPosition(reader.nextInt());
        		        				else if (key3.equals("COMPANY_NAME"))	jobList.setNameEng(reader.nextString());
        			        			else if (key3.equals("COMPANY_NAME_THAI"))	jobList.setNameTha(reader.nextString());
        		        				else if (key3.equals("PREMIUM"))	jobList.setPremium(reader.nextInt());
        		        				else if (key3.equals("POST_DATE"))	jobList.setPostDate(toDate(reader.nextString(),"dd/MM/yyyy"));
        		        				else if (key3.equals("E_D2")) jobList.setExpireDate(toDate(reader.nextString(),"dd/MM/yyyy"));
        		        				else if (key3.equals("EXP_LESS")) jobList.setExpLess(reader.nextInt());
        		        				else if (key3.equals("EXP_MOST")) jobList.setExpMost(reader.nextInt());
        		        				else if (key3.equals("SALARY_LESS")) jobList.setSalaryLess(reader.nextInt());
        		        				else if (key3.equals("SALARY_MOST")) 	jobList.setSalaryMost(reader.nextInt());
        		        				else if (key3.equals("POSITION_NAME")) 	jobList.setPositionName(reader.nextString());
        		        				else if (key3.equals("DEGREE")) 	jobList.setDegree(reader.nextInt());
        			        			else if (key3.equals("EXPERIENCE")) 	jobList.setExperience(reader.nextString());
        			        			else if (key3.equals("SALARY_ENG"))	jobList.setSalaryEng(reader.nextString());
        			        			else if (key3.equals("SALARY_THA"))	jobList.setSalaryTha(reader.nextString());
        			        			else if (key3.equals("DEGREE_ENG")) 	jobList.setDegreeEng(reader.nextString());
        			        			else if (key3.equals("DEGREE_THA")) jobList.setDegreeTha(reader.nextString());
        			        			else if (key3.equals("JOBPOST_URL")) jobList.setJobPostURL(reader.nextString());
        			        			else if (key3.equals("LOGO")) jobList.setLogo(reader.nextString());
        			        			else if (key3.equals("LOCATION_THA"))	jobList.setLocationTha(reader.nextString());
        			        			else if (key3.equals("LOCATION_ENG"))	jobList.setLocationEng(reader.nextString());
        			        			else if (key3.equals("POSTDAY_DIFF")) jobList.setDayPostJob(reader.nextInt());
        			        			else if (key3.equals("POSTHOUR_DIFF")) 	jobList.setHourPostJob(reader.nextInt());
        			        			else if (key3.equals("POSTMINUTE_DIFF"))	jobList.setMinutePostJob(reader.nextInt());
        			        			else if (key3.equals("SHOW_SALARY"))	jobList.setShowSalary(reader.nextInt());
        			        			else reader.skipValue();
        			        		
        							}
        							reader.endObject();
        							result.addData(jobList);
        						}
        						else reader.skipValue();
    						}
    						reader.endObject();
    					}
    					reader.endArray();
    				}
    				else	reader.skipValue();
	        	}else	reader.skipValue();
	        }
			reader.endObject();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("urlService"+urlService);
		}
		finally
		{
			webConnection.close();
		}
		return result;
	}
	
	public String getSearchDescription()
	{
		String result = "";
		int idx=(int)(new java.util.Date().getTime()%jsonURL.length);
		WebConnection webConnection = null;
		String urlService=oldJsonURL[idx]+"/iServ.php?action=getSearchDescription";
		//System.out.println("urlService"+urlService);
		try 
		{	
			webConnection = new WebConnection(urlService);
			webConnection.addParameter("test", 1);
			webConnection.addParameter("idJsk", this.resume.getIdJsk());
			webConnection.addParameter("idSearch", this.resume.getIdSearch());
			webConnection.addParameter("idLanguage", this.resume.getIdLanguage());
		
			
			JsonReader reader = webConnection.getJsonReader();	
			reader.beginObject();
    		while (reader.hasNext()) 
    		{
    			String key = reader.nextName();
    			if(reader.peek() != JsonToken.NULL)
    			{
    				if(key.equals("data"))	result = reader.nextString();
    				else reader.skipValue();
    				
    				
    			}else	reader.skipValue();
    		}
    		reader.endObject();
    		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("urlService"+urlService);
		}
		finally
		{
			webConnection.close();
		}
		return result;
	}
	
	public JobListJSON getJobListForSaveReport(String scrollId)
	{
		JobListJSON result =new JobListJSON();
		int idx=(int)(new java.util.Date().getTime()%jsonURL.length);
		WebConnection webConnection = null;
		String urlService=jsonURL[idx]+"/iServ.php?service=getCriteriaEmailSearchResult";
		String[] tmpArr = null;
		try 
		{	
			webConnection = new WebConnection(urlService);
			webConnection.addParameter("compressed", 1);
			webConnection.addParameter("idJsk", this.resume.getIdJsk());
			webConnection.addParameter("idSearch", this.resume.getIdSearch());
			webConnection.addParameter("idLanguage", this.resume.getIdLanguage());
			webConnection.addParameter("criteriaSearchMailFlag", this.resume.getCriteriaSearchMailFlag());
			webConnection.addParameter("getScrollId", 1);
			webConnection.addParameter("fields", "_id");
			webConnection.addParameter("vpp", 1000);
			if(scrollId != null && !scrollId.trim().isEmpty()){webConnection.addParameter("scroll_id", scrollId);}
			
			JsonReader reader = webConnection.getJsonReaderFromGzip();	        
    		reader.beginObject();
    		while (reader.hasNext()) 
    		{
    			String key = reader.nextName();
    			if(reader.peek() != JsonToken.NULL)
    			{
    				if(key.equals("total"))	result.setTotal(reader.nextInt());
    				else if(key.equals("_scroll_id"))	result.setScrollId(reader.nextString());
    				else if(key.equals("hits"))
    				{
    					reader.beginArray();
    					while(reader.hasNext())
    					{
    						reader.beginObject();
    						while(reader.hasNext())
    						{
    							String key2 = reader.nextName();
        						if(key2.equals("_id"))
        						{
        							tmpArr = reader.nextString().split("_");
        							if(tmpArr.length >= 2)
        							{
        								JobList jobList = new JobList();
        								jobList.setIdEmp(Util.getInt(tmpArr[0]));
        								jobList.setIdPosition(Util.getInt(tmpArr[1]));
        								result.addData(jobList);
        							}
        						}
        						else reader.skipValue();
    						}
    						reader.endObject();
    					}
    					reader.endArray();
    				}
    				else	reader.skipValue();
	        	}else	reader.skipValue();
	        }
			reader.endObject();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("urlService"+urlService);
		}
		finally
		{
			webConnection.close();
		}
		return result;
	}
	
	public static Date toDate(String str,String format)
	{
		Date result=null;
		try
		{
			result = new SimpleDateFormat(format, Locale.ENGLISH).parse(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public int getJobsBeforeDay() {
		return jobsBeforeDay;
	}
	public void setJobsBeforeDay(int jobsBeforeDay) {
		this.jobsBeforeDay = jobsBeforeDay;
	}
	

}
