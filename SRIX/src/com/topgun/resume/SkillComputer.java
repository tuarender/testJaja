package com.topgun.resume;

public class SkillComputer 
{
	private int idJsk;
    private int idResume;
    private int idComputer;
    private String othComputer;
    private int levelSkill ;
    private int idGroup;
    
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
	public int getIdComputer() 
	{
		return idComputer;
	}
	public void setIdComputer(int idComputer) 
	{
		this.idComputer = idComputer;
	}
	public String getOthComputer() 
	{
		return othComputer != null ? othComputer : "";
	}
	public void setOthComputer(String othComputer) 
	{
		this.othComputer = othComputer != null ? othComputer : "";
	}
	public int getLevelSkill()
	{
		return levelSkill;
	}
	public void setLevelSkill(int levelSkill) 
	{
		this.levelSkill = levelSkill;
	}
	public int getIdGroup() 
	{
		return idGroup;
	}
	public void setIdGroup(int idGroup) 
	{
		this.idGroup = idGroup;
	}
}
