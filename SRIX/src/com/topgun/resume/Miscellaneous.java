package com.topgun.resume;

public class Miscellaneous
{
	private int idJsk;
	private int idResume;
	private String resumeTemplate;
	private String resumeFont;
	
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
	public String getResumeTemplate()
    {
    	return (resumeTemplate != null) ? resumeTemplate : "";
    }
	public void setResumeTemplate(String resumeTemplate)
    {
    	this.resumeTemplate = (resumeTemplate != null) ? resumeTemplate : "";
    }
	public String getResumeFont() 
	{
		return resumeFont!=null?resumeFont:"";
	}
	public void setResumeFont(String resumeFont) 
	{
		this.resumeFont = resumeFont;
	}
}
