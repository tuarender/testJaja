package com.topgun.resume;

public class JobseekerRegDetail
{
	private int idJsk; 
	private String username;
	private String reference;
	private String suggester;
	private String suggesterType;
	private String timestamp;
	
	public int getIdJsk() 
	{
		return idJsk;
	}
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk;
	}
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
	public String getReference() 
	{
		return reference;
	}
	public void setReference(String reference) 
	{
		this.reference = reference;
	}
	public String getSuggester() 
	{
		return suggester;
	}
	public void setSuggester(String suggester) 
	{
		this.suggester = suggester;
	}
	public String getSuggesterType() 
	{
		return suggesterType;
	}
	public void setSuggesterType(String suggesterType) 
	{
		this.suggesterType = suggesterType;
	}
	public String getTimestamp() 
	{
		return timestamp;
	}
	public void setTimestamp(String timestamp) 
	{
		this.timestamp = timestamp;
	}
}
