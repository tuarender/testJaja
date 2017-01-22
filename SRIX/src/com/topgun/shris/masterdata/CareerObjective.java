package com.topgun.shris.masterdata;

public class CareerObjective
{
	private int idLanguage;
	private int idCareerobj;
	private String careerobjName;
	private int priority;
	
	public int getPriority()
    {
    	return priority;
    }
	public void setPriority(int priority)
    {
    	this.priority = priority;
    }
	public int getIdLanguage()
    {
    	return idLanguage;
    }
	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	public int getIdCareerobj()
    {
    	return idCareerobj;
    }
	public void setIdCareerobj(int idCareerobj)
    {
    	this.idCareerobj = idCareerobj;
    }
	public String getCareerobjName()
    {
    	return careerobjName;
    }
	public void setCareerobjName(String careerobjName)
    {
    	this.careerobjName = careerobjName;
    }
	
}
