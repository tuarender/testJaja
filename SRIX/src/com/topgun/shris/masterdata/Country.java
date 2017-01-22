package com.topgun.shris.masterdata;

public class Country
{
	private int idLanguage;
	private int idCountry;
	private String countryName;
	private String abbreviation;
	
	public String getAbbreviation()
    {
    	return (abbreviation != null) ? abbreviation : "";
    }
	public void setAbbreviation(String abbreviation)
    {
    	this.abbreviation = (abbreviation != null) ? abbreviation : "";
    }
	public int getIdLanguage()
    {
    	return idLanguage;
    }
	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	public int getIdCountry()
    {
    	return idCountry;
    }
	public void setIdCountry(int idCountry)
    {
    	this.idCountry = idCountry;
    }
	public String getCountryName()
    {
    	return countryName;
    }
	public void setCountryName(String countryName)
    {
    	this.countryName = countryName;
    }
	
}
