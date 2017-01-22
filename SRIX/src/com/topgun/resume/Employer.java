package com.topgun.resume;

public class Employer 
{
	private int id;
	private String nameEng;
	private String nameThai;
	private int companyType;

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getNameEng() 
	{
		return nameEng;
	}
	
	public void setNameEng(String nameEng) 
	{
		this.nameEng = nameEng;
	}
	
	public String getNameThai() 
	{
		return nameThai;
	}
	
	public void setNameThai(String nameThai) 
	{
		this.nameThai = nameThai;
	}

	public void setCompanyType(int companyType) 
	{
		this.companyType = companyType;
	}

	public int getCompanyType() 
	{
		return companyType;
	}
}
