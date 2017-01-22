package com.topgun.shris.masterdata;

public class Computer 
{
	private int idComputer;
	private int idGroup;
	private String computerName;
	
	public int getIdComputer() 
	{
		return idComputer;
	}
	public void setIdComputer(int idComputer) 
	{
		this.idComputer = idComputer;
	}
	
	public int getIdGroup() 
	{
		return idGroup;
	}
	public void setIdGroup(int idGroup) 
	{
		this.idGroup = idGroup;
	}
	public String getComputerName() 
	{
		return computerName!=null?computerName:"";
	}
	public void setComputerName(String computerName) 
	{
		this.computerName = computerName;
	}
	
}
