package com.topgun.criteriasearch;


import java.util.*;

public class JobListJSON 
{
	private int success=0;
	private String exception="";
	private String scrollId;
	private List<JobList> data=null;
	private int total = 0;
	private int idSearch=-1;
	
	JobListJSON()
	{
		this.data=new ArrayList<JobList>();
	}
	
	
	public int getIdSearch() {
		return idSearch;
	}


	public void setIdSearch(int idSearch) {
		this.idSearch = idSearch;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getSuccess() 
	{
		return success;
	}
	public void setSuccess(int success) 
	{
		this.success = success;
	}
	public String getException() 
	{
		return exception;
	}
	public void setException(String exception) 
	{
		this.exception = exception;
	}
	public List<JobList> getData() 
	{
		return data;
	}
	public void addData(JobList data) 
	{
		this.data.add(data);
	}


	public String getScrollId() {
		return scrollId;
	}


	public void setScrollId(String scrollId) {
		this.scrollId = scrollId;
	}
}
