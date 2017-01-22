package com.topgun.criteriasearch;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.topgun.resume.TargetJob;
import com.topgun.resume.TargetJobManager;
import com.topgun.search.JobFieldSearch;
import com.topgun.search.RegionSearch;
import com.topgun.search.Search;
import com.topgun.search.SearchParamParser;
import com.topgun.search.StateSearch;
import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class CriteriaSearchFactory {
	
	private Search search = null;
	
	public static final int ID_SEARCH_REGION_INDUSTRIAL = 11;
	public static final int ID_SEARCH_REGION_INDUSTRIAL_INTERPOLITAN = 13;
		
	public int createSearch(int idJsk, int idResume)
	{
		int result = 0;
		search = new Search(idJsk, 11);
		search.setType(1);	//Save CriteriaSearch
		search.setMail("TRUE");
		
		//create jobfield list;
		search.setJobFieldSearchList(getJobFieldSearchList(idJsk, idResume));
		
		//create salary;
		int salaryLess = -1;
		int salaryMost = -1;
		TargetJob targetJob = new TargetJobManager().get(idJsk, idResume);
		
		if(targetJob.getMinExpectedSalary() > -1)
		{
			salaryLess = getMinSalary(Util.convertSalaryPerMonth(targetJob.getMinExpectedSalary(), targetJob.getSalaryCurrency(), targetJob.getExpectedSalaryPer()));
			salaryMost = getMaxSalary(Util.convertSalaryPerMonth(targetJob.getMaxExpectedSalary(), targetJob.getSalaryCurrency(), targetJob.getExpectedSalaryPer()));
		}
		
		search.setSalaryLess(salaryLess);
		search.setSalaryMost(salaryMost);
		
		//Create location
		List<RegionSearch> regionSearchList = getStateRegionSearchList(idJsk, idResume);
		
		if(regionSearchList == null)
		{
			regionSearchList = new ArrayList<RegionSearch>();
		}
		
		
		//Create industrial location
		RegionSearch industrialSearch = getIndustrialRegionSearch(idJsk, idResume);
		
		if(industrialSearch != null)
		{
			regionSearchList.add(industrialSearch);
		}
		search.setRegionSearchList(regionSearchList);
		
		result = search.add();
		
		return result > 0 ? 1 : 0;
	}
	
	public List<RegionSearch> getStateRegionSearchList(int idJsk, int idResume)
	{
		List<RegionSearch> result = new ArrayList<RegionSearch>();
		JsonObject regionMem = new JsonObject();
		JsonObject stateMem = null;
		JsonObject cityMem = null;
		DBManager db = null;
		String sql = "SELECT A.ID_COUNTRY, A.ID_STATE, A.ID_CITY, B.ID_REGION "
						+" FROM INTER_LOCATION A LEFT JOIN INTER_STATE B ON A.ID_COUNTRY = B.ID_COUNTRY AND A.ID_STATE = B.ID_STATE "
					+" WHERE ID_JSK = ? AND ID_RESUME = ? AND A.ID_COUNTRY = 216 ";
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{
				stateMem = (JsonObject) (regionMem.has(db.getString("ID_REGION")) ? regionMem.get(db.getString("ID_REGION")) : new JsonObject());
				cityMem = (JsonObject) (stateMem.has(db.getString("ID_STATE")) ? stateMem.get(db.getString("ID_STATE")) : new JsonObject());
				
				if(db.getInt("ID_CITY") > 0)
				{
					cityMem.add(db.getString("ID_CITY"), new JsonObject());
				}
				
				stateMem.add(db.getString("ID_STATE"), cityMem);
				regionMem.add(db.getString("ID_REGION"), stateMem);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		
		try
		{
			result = SearchParamParser.parseRegionParam(new Gson().toJson(regionMem), 11);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RegionSearch getIndustrialRegionSearch(int idJsk, int idResume)
	{
		RegionSearch result = null;
		List<Integer> idIndustrialList = new ArrayList<Integer>();
		DBManager db = null;
		String sql = "SELECT ID_JSK, ID_RESUME, ID_INDUSTRIAL FROM INTER_INDUSTRIAL_LOCATION WHERE ID_JSK = ? AND ID_RESUME = ?";
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{				
				idIndustrialList.add(db.getInt("ID_INDUSTRIAL"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		
		try
		{
			if(idIndustrialList.size() > 0)
			{
				result = new RegionSearch(ID_SEARCH_REGION_INDUSTRIAL, 11);
				List<StateSearch> industrialiStateList = new ArrayList<StateSearch>();
				for (Integer idIndustrial : idIndustrialList) {
					industrialiStateList.add(new StateSearch(ID_SEARCH_REGION_INDUSTRIAL, idIndustrial, 11));
				}
				result.setStateSearchList(industrialiStateList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	public List<JobFieldSearch> getJobFieldSearchList(int idJsk, int idResume)
	{
		List<JobFieldSearch> result = new ArrayList<JobFieldSearch>();
		JsonObject jobFieldMem = new JsonObject();
		JsonObject subGroupMem = null;
		String sql = "SELECT A.ID_JOBFIELD, A.ID_SUBFIELD, B.ID_GROUP_SUBFIELD "
					+" FROM INTER_TARGETJOB A "
					+" 		LEFT JOIN INTER_SUBFIELD B "
					+" 		ON A.ID_JOBFIELD = B.ID_FIELD AND A.ID_SUBFIELD = B.ID_SUBFIELD "
					+" WHERE ID_JSK = ? AND ID_RESUME = ? ";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{
				subGroupMem = (JsonObject) (jobFieldMem.has(db.getString("ID_JOBFIELD")) ? jobFieldMem.get(db.getString("ID_JOBFIELD")) : new JsonObject());
				
				if(db.getInt("ID_GROUP_SUBFIELD") == 0)
				{
					subGroupMem = new JsonObject();
					subGroupMem.add("0", new JsonObject());	
				}
				else if(!subGroupMem.has("0"))
				{
					subGroupMem.add(db.getString("ID_GROUP_SUBFIELD"), new JsonObject());
				}
				
				jobFieldMem.add(db.getString("ID_JOBFIELD"), subGroupMem);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		
		try
		{
			result = SearchParamParser.parseJobFieldParam(new Gson().toJson(jobFieldMem), 11);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int getIdSearch()
	{
		return this.search == null ? -1 : this.search.getIdSearch();
	}
	
	public Search getSearch()
	{
		return this.search;
	}
	
	public static int getMinSalary(int salary)
	{
		int result=0;
		int salaryList[]=new int[14];
		salaryList[0]=0;
		salaryList[1]=5000;
		salaryList[2]=10000;
		salaryList[3]=15000;
		salaryList[4]=20000;
		salaryList[5]=25000;
		salaryList[6]=30000;
		salaryList[7]=40000;
		salaryList[8]=50000;
		salaryList[9]=60000;
		salaryList[10]=80000;
		salaryList[11]=100000;
		salaryList[12]=120000;
		salaryList[13]=150000;
		for(int i=salaryList.length-1; i>=0; i--)
		{
			if(salaryList[i]<=salary)
			{
				return salaryList[i];
			}
		}
		return result;
	}
	
	public static int getMaxSalary(int salary)
	{
		int result=-1;
		int salaryList[]=new int[14];
		salaryList[0]=0;
		salaryList[1]=5000;
		salaryList[2]=10000;
		salaryList[3]=15000;
		salaryList[4]=20000;
		salaryList[5]=25000;
		salaryList[6]=30000;
		salaryList[7]=40000;
		salaryList[8]=50000;
		salaryList[9]=60000;
		salaryList[10]=80000;
		salaryList[11]=100000;
		salaryList[12]=120000;
		salaryList[13]=150000;
		for(int i=0; i<salaryList.length; i++)
		{
			if(salaryList[i]>=salary)
			{
				return salaryList[i];
			}
		}
		return result;
	}
	
	
}
