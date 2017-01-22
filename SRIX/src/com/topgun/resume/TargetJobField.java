package com.topgun.resume;

public class TargetJobField 
{
	private int idJsk;
	private int idResume;
	private int idJobfield;
	private int idSubfield;
	private int jobfieldOrder;
	private String otherJobfield;
	private String otherSubfield;
	
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
	public int getIdJobfield()
	{
		return idJobfield;
	}
	public void setIdJobfield(int idJobfield)
	{
		this.idJobfield = idJobfield;
	}
	public int getIdSubfield() 
	{
		return idSubfield;
	}
	public void setIdSubfield(int idSubfield) 
	{
		this.idSubfield = idSubfield;
	}
	public int getJobfieldOrder()
	{
		return jobfieldOrder;
	}
	public void setJobfieldOrder(int jobfieldOrder) 
	{
		this.jobfieldOrder = jobfieldOrder;
	}
	public String getOtherJobfield()
    {
    	return (otherJobfield != null) ? otherJobfield : "";
    }
	public void setOtherJobfield(String otherJobfield)
    {
    	this.otherJobfield = (otherJobfield != null) ? otherJobfield : "";
    }
	public String getOtherSubfield()
    {
    	return (otherSubfield != null) ? otherSubfield : "";
    }
	public void setOtherSubfield(String otherSubfield)
    {
    	this.otherSubfield = (otherSubfield != null) ? otherSubfield : "";
    }
}
