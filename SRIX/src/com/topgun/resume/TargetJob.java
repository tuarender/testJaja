package com.topgun.resume;

import java.sql.Date;

public class TargetJob
{
	private int idJsk;
	private int idResume;
	private String workPermit;
	private String relocate;
	private int travel;
	private Date startJob;
	private int minExpectedSalary;
	private int maxExpectedSalary;
	private int expectedSalaryPer;
	private int salaryCurrency;
	private int startJobNotice;
	
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
	public String getWorkPermit() 
	{
		return workPermit;
	}
	public void setWorkPermit(String workPermit) 
	{
		this.workPermit = workPermit;
	}
	public String getRelocate() 
	{
		return relocate;
	}
	public void setRelocate(String relocate) 
	{
		this.relocate = relocate;
	}
	public int getTravel() 
	{
		return travel;
	}
	public void setTravel(int travel)
	{
		this.travel = travel;
	}
	public Date getStartJob()
	{
		return startJob;
	}
	public void setStartJob(Date startJob)
	{
		this.startJob = startJob;
	}
	public int getMinExpectedSalary() 
	{
		return minExpectedSalary;
	}
	public void setMinExpectedSalary(int minExpectedSalary)
	{
		this.minExpectedSalary = minExpectedSalary;
	}
	public int getMaxExpectedSalary()
	{
		return maxExpectedSalary;
	}
	public void setMaxExpectedSalary(int maxExpectedSalary)
	{
		this.maxExpectedSalary = maxExpectedSalary;
	}
	public int getExpectedSalaryPer() 
	{
		return expectedSalaryPer;
	}
	public void setExpectedSalaryPer(int expectedSalaryPer) 
	{
		this.expectedSalaryPer = expectedSalaryPer;
	}
	public int getSalaryCurrency() 
	{
		return salaryCurrency;
	}
	public void setSalaryCurrency(int salaryCurrency) 
	{
		this.salaryCurrency = salaryCurrency;
	}
	public int getStartJobNotice() 
	{
		return startJobNotice;
	}
	
	public void setStartJobNotice(int startJobNotice) 
	{
		this.startJobNotice = startJobNotice;
	}
}
