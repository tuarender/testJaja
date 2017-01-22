package com.topgun.resume;

import java.sql.Date;

public class Ethnicity
{
	private int idJsk;
    private int idResume;
    private String ethnicity ;
    private Date timeStamp;
    
	public int getIdJsk() 
	{
		return idJsk;
	}
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk;
	}
	public int getIdResume() 
	{
		return idResume;
	}
	public void setIdResume(int idResume) 
	{
		this.idResume = idResume;
	}
	public String getEthnicity() 
	{

		return (ethnicity != null) ? ethnicity : "";
	}
	public void setEthnicity(String ethnicity) 
	{
		
		this.ethnicity = (ethnicity != null) ? ethnicity : "";
	}
	public Date getTimeStamp() 
	{
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
}
