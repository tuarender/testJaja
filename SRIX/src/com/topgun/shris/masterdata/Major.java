package com.topgun.shris.masterdata;

public class Major
{
	private int idLanguage;
	private int idMajor;
	private int idFaculty;
	private String majorName;
	
	public int getIdLanguage()
    {
    	return idLanguage;
    }
	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	public int getIdMajor()
    {
    	return idMajor;
    }
	public void setIdMajor(int idMajor)
    {
    	this.idMajor = idMajor;
    }
	public int getIdFaculty()
    {
    	return idFaculty;
    }
	public void setIdFaculty(int idFaculty)
    {
    	this.idFaculty = idFaculty;
    }
	public String getMajorName()
    {
    	return majorName!=null?majorName.trim():"";
    }
	public void setMajorName(String majorName)
    {
    	this.majorName = majorName;
    }

}
