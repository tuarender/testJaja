package com.topgun.shris.masterdata;

public class Proficiency 
{
	private int id;
	private int idLanguage;
	private String name;
	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}
	
	public int getIdLanguage() 
	{
		return idLanguage;
	}
	
	public void setIdLanguage(int idLanguage) 
	{
		this.idLanguage = idLanguage;
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
