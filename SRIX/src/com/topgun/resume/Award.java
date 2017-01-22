package com.topgun.resume;

import java.sql.Date;

public class Award 
{
	private int idJsk;
    private int idResume;
    private int id;
    private Date awardDate ;
    private String awardName ;
    private String detail ;
    private String institute ;
	private Date timeStamp;
    
	public String getAwardName() 
	{
		return awardName != null ? awardName : "";
	}
	public void setAwardName(String awardName) 
	{
		this.awardName = awardName != null ? awardName : "";
	}
	public String getDetail() 
	{
		return detail != null ? detail : "";
	}
	public void setDetail(String detail) 
	{
		this.detail = detail != null ? detail : "";
	}
	public String getInstitute() 
	{
		return institute != null ? institute : "";
	}
	public void setInstitute(String institute) 
	{
		this.institute = institute != null ? institute : "";
	}
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
	public Date getAwardDate() 
	{
		return awardDate;
	}
	public void setAwardDate(Date awardDate)
	{
		this.awardDate = awardDate;
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
