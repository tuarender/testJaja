package com.topgun.shris.masterdata;

public class Faculty
{
	private int idLanguage;
	private int idFaculty;
	private String facultyName;
	private int facultyLevel;
	
	public int getIdLanguage()
    {
    	return idLanguage;
    }
	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	public int getIdFaculty()
    {
    	return idFaculty;
    }
	public void setIdFaculty(int idFaculty)
    {
    	this.idFaculty = idFaculty;
    }
	public String getFacultyName()
    {
    	return facultyName!=null?facultyName.trim():"";
    }
	public void setFacultyName(String facultyName)
    {
    	this.facultyName = facultyName;
    }
	public void setFacultyLevel(int facultyLevel) {
		this.facultyLevel = facultyLevel;
	}
	public int getFacultyLevel() {
		return facultyLevel;
	}
	
}
