package com.topgun.resume;


public class ExperienceSummary
{
    private int idJsk;
    private int idResume;
    private int jobField;
    private int sumYear = 0;
    private int sumMonth = 0;
    
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
	public int getJobField() 
	{
		return jobField;
	}
	public void setJobField(int jobField) 
	{
		this.jobField = jobField;
	}
	public int getSumYear() 
	{
		return sumYear;
	}
	public void setSumYear(int sumYear) 
	{
		this.sumYear = sumYear;
	}
	public int getSumMonth() 
	{
		return sumMonth;
	}
	public void setSumMonth(int sumMonth) 
	{
		this.sumMonth = sumMonth;
	}
}
