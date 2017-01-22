package com.topgun.shris.masterdata;

public class JobType
{
	private int idLanguage;
	private String typeName;
	private int idType;
	private int priority;
	
	public int getIdLanguage()
    {
    	return idLanguage;
    }
	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	public String getTypeName()
    {
    	return typeName;
    }
	public void setTypeName(String typeName)
    {
    	this.typeName = typeName;
    }
	public int getIdType()
    {
    	return idType;
    }
	public void setIdType(int idType)
    {
    	this.idType = idType;
    }
	public int getPriority()
    {
    	return priority;
    }
	public void setPriority(int priority)
    {
    	this.priority = priority;
    }
	
}
