package com.topgun.resume;

import java.sql.Date;
public class Activity 
{
	private int idJsk;
	private int idResume;
	private int id;
	private String club;
	private String position;
	private Date timeStamp;
	private Date startDate;
	private Date endDate;
	private String description;
	
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
	public int getId()
    {
    	return id;
    }
	public void setId(int id)
    {
    	this.id = id;
    }
	public String getClub()
    {
    	return club;
    }
	public void setClub(String club)
    {
    	this.club = club;
    }
	public String getPosition()
    {
    	return position;
    }
	public void setPosition(String position)
    {
    	this.position = position;
    }
	
	public Date getTimeStamp()
    {
    	return timeStamp;
    }
	public void setTimeStamp(Date timeStamp)
    {
    	this.timeStamp = timeStamp;
    }
	public String getDescription()
    {
    	return description;
    }
	public void setDescription(String description)
    {
    	this.description = description;
    }
	public Date getStartDate() 
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public Date getEndDate() 
	{
		return endDate;
	}
	public void setEndDate(Date endDate) 
	{
		this.endDate = endDate;
	}
}
