package com.topgun.resume;


public class CareerObjective 
{
    private int idJsk;
    private int idResume;
    private int idCareerObjective;
    private String otherObjective=null;

    public String getOtherObjective()
    {
    	return (otherObjective != null) ? otherObjective : "";
    }

	public void setOtherObjective(String otherObjective)
    {
    	this.otherObjective = (otherObjective != null) ? otherObjective : "";
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
	public int getIdCareerObjective() 
	{
		return idCareerObjective;
	}
	public void setIdCareerObjective(int idCareerObjective) 
	{
		this.idCareerObjective = idCareerObjective;
	}
}
