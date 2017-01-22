package com.topgun.resume;

public class Industry 
{
	private int idJsk;
	private int idResume;
	private int idIndustry;
	private int industryOrder;
    private String otherIndustry;

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
	public int getIdIndustry() 
	{
		return idIndustry;
	}
	public void setIdIndustry(int idIndustry) 
	{
		this.idIndustry = idIndustry;
	}
	public int getIndustryOrder() 
	{
		return industryOrder;
	}
	public void setIndustryOrder(int industryOrder) 
	{
		this.industryOrder = industryOrder;
	}
	public String getOtherIndustry()
    {
    	return (otherIndustry != null) ? otherIndustry : "";
    }
	public void setOtherIndustry(String otherIndustry)
    {
    	this.otherIndustry = (otherIndustry != null) ? otherIndustry : "";
    }
}
