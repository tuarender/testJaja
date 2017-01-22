package com.topgun.resume;

public class Hobby 
{
	private int idJsk;
    private int idResume;
    private int idHobby;   
    private int idGroup;   
    private int hobbyOrder ;
    private String othHobby ;   
    private int skill ;
    
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
	public int getIdHobby() 
	{
		return idHobby;
	}
	public void setIdHobby(int idHobby) 
	{
		this.idHobby = idHobby;
	}
	public int getIdGroup() 
	{
		return idGroup;
	}
	public void setIdGroup(int idGroup) 
	{
		this.idGroup = idGroup;
	}
	public int getHobbyOrder() 
	{
		return hobbyOrder;
	}
	public void setHobbyOrder(int hobbyOrder) 
	{
		this.hobbyOrder = hobbyOrder;
	}
	public String getOthHobby() 
	{
		return othHobby != null ? othHobby : "";
	}
	public void setOthHobby(String othHobby) 
	{
		this.othHobby = othHobby != null ? othHobby : "";
	}
	public int getSkill() 
	{
		return skill;
	}
	public void setSkill(int skill) 
	{
		this.skill = skill;
	}
}
