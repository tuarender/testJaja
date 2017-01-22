package com.topgun.resume;

public class ExperienceIndustry 
{
	private int idJsk;
	private int idResume;
	private int idWork;
	private int idIndustry;
    private String otherIndustry;
    private int orderIndustry;

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
	public int getIdWork() 
	{
		return idWork;
	}
	public void setIdWork(int idWork) 
	{
		this.idWork = idWork;
	}
	public int getIdIndustry() 
	{
		return idIndustry;
	}
	public void setIdIndustry(int idIndustry) 
	{
		this.idIndustry = idIndustry;
	}
	public String getOtherIndustry() 
	{
		return (otherIndustry != null) ? otherIndustry : "";
	}
	public void setOtherIndustry(String otherIndustry) 
	{
		this.otherIndustry = (otherIndustry != null) ? otherIndustry : "";
	}
	public int getOrderIndustry() 
	{
		return orderIndustry;
	}
	public void setOrderIndustry(int orderIndustry) 
	{
		this.orderIndustry = orderIndustry;
	}	
}
