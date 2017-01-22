package com.topgun.shris.masterdata;

public class Strength
{
	private int idLanguage;
	private int idStrength;
	private String strengthName;
	
	public int getIdLanguage() 
	{
		return idLanguage;
	}
	
	public void setIdLanguage(int idLanguage) 
	{
		this.idLanguage = idLanguage;
	}
	
	public int getIdStrength() 
	{
		return idStrength;
	}
	
	public void setIdStrength(int idStrength) 
	{
		this.idStrength = idStrength;
	}
	
	public String getStrengthName() 
	{
		return strengthName!=null?strengthName:"";
	}
	
	public void setStrengthName(String strengthName) 
	{
		this.strengthName = strengthName;
	}
}
