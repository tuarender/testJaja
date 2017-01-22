package com.topgun.resume;

public class SkillOther 
{
	private int idJsk;
    private int idResume;
    private int id;
    private String skillName;
    private int levelSkill;
    
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
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getSkillName() 
	{
		return skillName != null ? skillName : "";
	}
	
	public void setSkillName(String skillName) 
	{
		this.skillName = skillName != null ? skillName : "";
	}
	public int getLevelSkill() 
	{
		return levelSkill;
	}
	public void setLevelSkill(int levelSkill) 
	{
		this.levelSkill = levelSkill;
	}
}
