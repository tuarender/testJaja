package com.topgun.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.topgun.util.Util;

public class SearchParamParser {
	static JsonParser parser=new JsonParser();
	public static List<JobFieldSearch> parseJobFieldParam(String idJobFieldList, int idLanguage)
	{
		List<JobFieldSearch> jobFieldSearchList=new ArrayList<JobFieldSearch>();
		if(!idJobFieldList.equals(""))
		{
			
			JsonObject jobFieldObj = parser.parse(idJobFieldList).getAsJsonObject();
			for (Map.Entry<String,JsonElement> jobField : jobFieldObj.entrySet()) 
			{
				JobFieldSearch jobFieldSearch=new JobFieldSearch(Util.getInt(jobField.getKey()),idLanguage);
				List<SubGroupSearch> subGroupSearchList=new ArrayList<SubGroupSearch>();
				JsonObject subGroupList = jobField.getValue().getAsJsonObject();
				for (Map.Entry<String,JsonElement> subGroup : subGroupList.entrySet()) 
				{
					SubGroupSearch subGroupSearch=new SubGroupSearch(Util.getInt(jobField.getKey()),Util.getInt(subGroup.getKey()),idLanguage);
					subGroupSearchList.add(subGroupSearch);
				}
				jobFieldSearch.setSubGroupSearchList(subGroupSearchList);
				jobFieldSearchList.add(jobFieldSearch);
			};
		}
		return jobFieldSearchList;
	}
	
	public static List<RegionSearch> parseRegionParam(String idRegionList, int idLanguage)
	{
		List<RegionSearch> regionSearchList=new ArrayList<RegionSearch>();
		if(!idRegionList.equals(""))
		{
			JsonObject regionObj = parser.parse(idRegionList).getAsJsonObject();
			for (Map.Entry<String,JsonElement> region : regionObj.entrySet()) 
			{
				RegionSearch regionSearch=new RegionSearch(Util.getInt(region.getKey()),idLanguage);
				List<StateSearch> stateSearchList=new ArrayList<StateSearch>();
				JsonObject stateObj = region.getValue().getAsJsonObject();
				for (Map.Entry<String,JsonElement> state : stateObj.entrySet()) 
				{
					StateSearch stateSearch=new StateSearch(Util.getInt(region.getKey()),Util.getInt(state.getKey()),idLanguage);
					List<CitySearch> citySearchList=new ArrayList<CitySearch>();
					JsonObject cityObj = state.getValue().getAsJsonObject();
					for (Map.Entry<String,JsonElement> city : cityObj.entrySet()) 
					{
						CitySearch citySearch=new CitySearch(Util.getInt(region.getKey()),Util.getInt(state.getKey()),Util.getInt(city.getKey()),idLanguage);
						citySearchList.add(citySearch);
					}
					stateSearch.setCitySearchList(citySearchList);
					stateSearchList.add(stateSearch);
				}
				regionSearch.setStateSearchList(stateSearchList);
				regionSearchList.add(regionSearch);
			};
		}
		return regionSearchList;
	}
	
	public static List<DegreeSearch> parseDegreeParam(String idDegreeList, int idLanguage)
	{
		List<DegreeSearch> degreeSearchList=new ArrayList<DegreeSearch>();
		if(!idDegreeList.equals(""))
		{
			JsonObject degreeObj = parser.parse(idDegreeList).getAsJsonObject();
			for (Map.Entry<String,JsonElement> degree : degreeObj.entrySet()) 
			{
				DegreeSearch degreeSearch=new DegreeSearch(degree.getKey(),idLanguage);
				degreeSearchList.add(degreeSearch);
			}
		}
		return degreeSearchList;
	}
	
	public static List<JobTypeSearch> parseJobTypeParam(String idJobTypeList, int idLanguage)
	{
		List<JobTypeSearch> jobTypeSearchList=new ArrayList<JobTypeSearch>();
		if(!idJobTypeList.equals(""))
		{
			JsonObject jobTypeObj = parser.parse(idJobTypeList).getAsJsonObject();
			for (Map.Entry<String,JsonElement> jobType : jobTypeObj.entrySet()) 
			{
				JobTypeSearch jobTypeSearch=new JobTypeSearch(Util.getInt(jobType.getKey()),idLanguage);
				jobTypeSearchList.add(jobTypeSearch);
			}
		}
		return jobTypeSearchList;
	}
	
	public static List<IndustrySearch> parseIndustryParam(String idIndustryList, int idLanguage)
	{
		List<IndustrySearch> industrySearchList=new ArrayList<IndustrySearch>();
		if(!idIndustryList.equals(""))
		{
			JsonObject industryObj = parser.parse(idIndustryList).getAsJsonObject();
			for (Map.Entry<String,JsonElement> industry : industryObj.entrySet()) 
			{
				IndustrySearch industrySearch=new IndustrySearch(Util.getInt(industry.getKey()),idLanguage);
				industrySearchList.add(industrySearch);
			}
		}	
		return industrySearchList;
	}
}
