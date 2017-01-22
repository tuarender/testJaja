package com.topgun.shris.masterdata;

public class ComputerGroup 
{
	private int idLangauge;
	private int idGroup;
	private String groupName;
	public int getIdLangauge() 
	{
		return idLangauge;
	}
	
	public void setIdLangauge(int idLangauge) 
	{
		this.idLangauge = idLangauge;
	}
	
	public int getIdGroup() 
	{
		return idGroup;
	}
	
	public void setIdGroup(int idGroup) 
	{
		this.idGroup = idGroup;
	}
	
	public String getGroupName() 
	{
		return groupName!=null?groupName:"";
	}
	
	public void setGroupName(String groupName) 
	{
		this.groupName = groupName;
	}
	
	
	
}
