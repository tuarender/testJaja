package com.topgun.shris.masterdata;

public class Language 
{
	private int id;
	private String name;
	private String abbreviation;
	
	public String getAbbreviation() 
	{
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) 
	{
		this.abbreviation = abbreviation;
	}

	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getName() 
	{
		return name!=null?name:"";
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
}
