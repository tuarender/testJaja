package com.topgun.shris.masterdata;

public class HobbyGroup 
{
	private int id;
	private String name;
	private int idLanguage;
	
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

	public int getIdLanguage()
    {
    	return idLanguage;
    }

	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	
}
