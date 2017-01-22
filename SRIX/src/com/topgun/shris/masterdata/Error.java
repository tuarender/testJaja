package com.topgun.shris.masterdata;

public class Error 
{
	private int idError;
	private int idLanguage;
	private String errorMessage;
	public Error()
	{
		
	}
	
	public Error(int idError,int idLanguage,String errorMessage)
	{
		this.idError=idError;
		this.idLanguage=idLanguage;
		this.errorMessage=errorMessage;
	}
	
	public int getIdError()
    {
    	return idError;
    }
	
	public void setIdError(int idError)
    {
    	this.idError = idError;
    }
	
	public int getIdLanguage()
    {
    	return idLanguage;
    }
	
	public void setIdLanguage(int idLanguage)
    {
    	this.idLanguage = idLanguage;
    }
	
	public String getErrorMessage()
    {
    	return (errorMessage != null) ? errorMessage : "";
    }
	public void setErrorMessage(String errorMessage)
    {
    	this.errorMessage = (errorMessage != null) ? errorMessage : "";
    }
}
