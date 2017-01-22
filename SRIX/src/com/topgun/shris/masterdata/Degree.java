package com.topgun.shris.masterdata;

public class Degree
{
	private int idDegree;
	private String degreeName;
	private int idLanguage;
	private int degreeOrder;
	
	public int getDegreeOrder()
    {
    	return degreeOrder;
    }
	public void setDegreeOrder(int degreeOrder)
    {
    	this.degreeOrder = degreeOrder;
    }
	public int getIdDegree()
    {
    	return idDegree;
    }
	public void setIdDegree(int idDegree)
    {
    	this.idDegree = idDegree;
    }
	public String getDegreeName()
    {
    	return degreeName;
    }
	public void setDegreeName(String degreeName)
    {
    	this.degreeName = degreeName;
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
