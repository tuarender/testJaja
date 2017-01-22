package com.topgun.resume;

public class Book
{
	private int idJsk;
	private int idResume;
	private int idBook;
	private String otherBook;
	
	public String getOtherBook()
    {
    	return (otherBook != null) ? otherBook : "";
    }
	public void setOtherBook(String otherBook)
    {
    	this.otherBook = (otherBook != null) ? otherBook : "";
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
	public int getIdBook() 
	{
		return idBook;
	}
	public void setIdBook(int idBook) 
	{
		this.idBook = idBook;
	}
}
