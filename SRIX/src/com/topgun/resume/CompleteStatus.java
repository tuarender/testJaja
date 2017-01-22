package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

public class CompleteStatus 
{
	private String section;
	private String message;
	private List<Status> statusList=new ArrayList<Status>();
	public String getSection() 
	{
		return section;
	}
	public void setSection(String section) 
	{
		this.section = section;
	}
	public String getMessage() 
	{
		return message;
	}
	public void setMessage(String message) 
	{
		this.message = message;
	}
	public List<Status> getStatusList() 
	{
		return statusList;
	}
	public void setStatusList(List<Status> statusList) 
	{
		this.statusList = statusList;
	}
}


