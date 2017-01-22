package com.topgun.resume;

public class Pet
{
	private int idJsk;
	private int idResume;
	private int idPet;
	private String otherPet;
	
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
	public int getIdPet() 
	{
		return idPet;
	}
	public void setIdPet(int idPet) 
	{
		this.idPet = idPet;
	}

	public String getOtherPet()
    {
    	return (otherPet != null) ? otherPet : "";
    }
	public void setOtherPet(String otherPet)
    {
    	this.otherPet = (otherPet != null) ? otherPet : "";
    }
}
