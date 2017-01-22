package com.topgun.resume;

import java.sql.Date;

public class Parttime 
{
	private int idJsk;
	private int idResume;
	private int id;
	private String company;
	private String position;
	private int wage;
	private Date startDate;
	private Date endDate;
	private String wagePer;
	private String achievement;
	
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
	public String getCompany()
	{
		return (company != null) ? company : "";
	}
	public void setCompany(String company)
	{
		this.company = (company != null) ? company : "";
	}
	public String getPosition()
	{
		return (position != null) ? position : "";
	}
	
	public void setPosition(String position)
	{
		this.position = (position != null) ? position : "";
	}
	public int getWage() 
	{
		return wage;
	}
	public void setWage(int wage) 
	{
		this.wage = wage;
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
	public String getWagePer()
	{
		return (wagePer != null) ? wagePer : "";
	}
	public void setWagePer(String wagePer)
	{
		this.wagePer = (wagePer != null) ? wagePer : "";
	}
	public String getAchievement()
	{
		return (achievement != null) ? achievement : "";
	}
	
	public void setAchievement(String achievement)
	{
		this.achievement = (achievement != null) ? achievement : "";
	}
}
