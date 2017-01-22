package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

public class Status 
{
	private int id;
	private List<String> messages=new ArrayList<String>();
	
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public List<String> getMessages() 
	{
		return messages;
	}
	public void setMessages(List<String> messages) 
	{
		this.messages = messages;
	}
}
