package com.topgun.resume;

public class SkillLanguageScore 
{
	private int idJsk;
    private int idResume;
    private String examType;
    private float reading;
    private float listening ;
    private float writing ;
    private float speaking ;
    private float total ;
    
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
	public String getExamType() {
		return examType != null ? examType : "";
	}
	public void setExamType(String examType) {
		this.examType = examType != null ? examType : "";
	}
	public float getReading() 
	{
		return reading;
	}
	public void setReading(float reading) 
	{
		this.reading = reading;
	}
	public float getListening() 
	{
		return listening;
	}
	public void setListening(float listening) 
	{
		this.listening = listening;
	}
	public float getWriting() 
	{
		return writing;
	}
	public void setWriting(float writing) 
	{
		this.writing = writing;
	}
	public float getSpeaking() 
	{
		return speaking;
	}
	public void setSpeaking(float speaking) 
	{
		this.speaking = speaking;
	}
	public float getTotal() 
	{
		return total;
	}
	public void setTotal(float total) 
	{
		this.total = total;
	}
}
