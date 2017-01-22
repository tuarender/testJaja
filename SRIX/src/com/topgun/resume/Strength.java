package com.topgun.resume;

public class Strength 
{
	private int idJsk;
    private int idResume;
    private int idStrength;
    private int strengthOrder;
    private String othStrength ;
    private String strengthReason ;
    private String reason;
    
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
	public int getIdStrength() 
	{
		return idStrength;
	}
	public void setIdStrength(int idStrength) 
	{
		this.idStrength = idStrength;
	}
	public int getStrengthOrder() 
	{
		return strengthOrder;
	}
	public void setStrengthOrder(int strengthOrder) 
	{
		this.strengthOrder = strengthOrder;
	}
	public String getOthStrength()
    {
    	return (othStrength != null) ? othStrength : "";
    }
	public void setOthStrength(String othStrength)
    {
    	this.othStrength = (othStrength != null) ? othStrength : "";
    }
	public String getStrengthReason()
    {
    	return (strengthReason != null) ? strengthReason : "";
    }
	public void setStrengthReason(String strengthReason)
    {
    	this.strengthReason = (strengthReason != null) ? strengthReason : "";
    }
	public String getReason() 
	{
		return (reason != null) ? reason : "";
	}
	public void setReason(String reason) 
	{
		this.reason = (reason != null) ? reason : "";
	}
	
}
