package com.topgun.resume;

import java.sql.Date;

public class Training 
{
    private int idJsk;
    private int idResume;
    private int id;
    private Date startDate ;
    private Date endDate ;
    private String courseName ;
    private String courseDesc ;
    private String institute ;
	private Date timeStamp;
    
	public String getCourseDesc() 
	{
	return courseDesc!=null?courseDesc:"";
	}
	public void setCourseDesc(String courseDesc) 
	{
		this.courseDesc = courseDesc != null ? courseDesc : "";
	}
	public String getCourseName() 
	{
	return courseName!=null?courseName:"";
	}	
	public void setCourseName(String courseName) {
		this.courseName = courseName != null ? courseName : "";
	}
	public int getIdJsk() 
	{
		return idJsk;
    }
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk ;
	}
	public int getIdResume() 
	{
		return idResume;
	}
	public void setIdResume(int idResume) 
	{
		this.idResume = idResume ;
	}
	public String getInstitute() 
	{
		return institute != null ? institute : "";
	}
	public void setInstitute(String institute)
	{
		this.institute = institute != null ? institute : "";
	}
	public Date getEndDate()
	{
		return endDate ;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate =endDate;
	}
	public int getId()
	{
		return id ;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate ;
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
