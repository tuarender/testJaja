package com.topgun.resume;

public class SkillLanguage 
{
	private int idJsk;
    private int idResume;
    private int idSkillLang;
    private int reading;
    private int listening ;
    private int writing ;
    private int speaking ;
    private String othLang ;
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
	public int getIdSkillLang() 
	{
		return idSkillLang;
	}
	public void setIdSkillLang(int idSkillLang) 
	{
		this.idSkillLang = idSkillLang;
	}
	public int getReading() 
	{
		return reading;
	}
	public void setReading(int reading) 
	{
		this.reading = reading;
	}
	public int getListening() 
	{
		return listening;
	}
	public void setListening(int listening) 
	{
		this.listening = listening;
	}
	public int getWriting() 
	{
		return writing;
	}
	public void setWriting(int writing) 
	{
		this.writing = writing;
	}
	public int getSpeaking() 
	{
		return speaking;
	}
	public void setSpeaking(int speaking) 
	{
		this.speaking = speaking;
	}
	public String getOthLang() 
	{
		return othLang != null ? othLang : "";
	}
	public void setOthLang(String othLang) 
	{
		this.othLang = othLang != null ? othLang : "";
	}
	public int getLevelSkill() {
		return levelSkill;
	}
	public void setLevelSkill(int levelSkill) 
	{
		this.levelSkill = levelSkill;
	}
}
