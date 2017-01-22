package com.topgun.shris.masterdata;

public class Hobby
{
	private int idLanguage;
	private int idHobby;
	private int idGroup;
	private String hobbyName;
	
	public int getIdLanguage()
    {
    	return idLanguage;
    }
	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	public int getIdHobby()
    {
    	return idHobby;
    }
	public void setIdHobby(int idHobby)
    {
    	this.idHobby = idHobby;
    }
	public int getIdGroup()
    {
    	return idGroup;
    }
	public void setIdGroup(int idGroup)
    {
    	this.idGroup = idGroup;
    }
	public String getHobbyName()
    {
    	return hobbyName!=null?hobbyName:"";
    }
	public void setHobbyName(String hobbyName)
    {
    	this.hobbyName = hobbyName;
    }	
}
