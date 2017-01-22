package com.topgun.resume;

import java.sql.Date;
import java.sql.Timestamp;

public class Jobseeker 
{
	private int idJsk;
	private String username;
	private String authen;
	private String password;
	private Timestamp registerDate;
	private Timestamp lastLogin;
	private int idCountryRegister;
	private Date timeStamp;
	private String jobUpdateStatus;
	private String jobMatchStatus;
	private String mailOtherStatus;
	
	public String getAuthen()
    {
    	return (authen != null) ? authen : "";
    }
	public void setAuthen(String authen)
    {
    	this.authen = (authen != null) ? authen : "";
    }
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
	public Timestamp getRegisterDate() 
	{
		return registerDate;
	}
	public void setRegisterDate(Timestamp registerDate) 
	{
		this.registerDate = registerDate;
	}
	public Timestamp getLastLogin() 
	{
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) 
	{
		this.lastLogin = lastLogin;
	}
	public int getIdCountryRegister() 
	{
		return idCountryRegister;
	}
	public void setIdCountryRegister(int idCountryRegister) 
	{
		this.idCountryRegister = idCountryRegister;
	}
	public Date getTimeStamp() 
	{
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
	public String getJobUpdateStatus()
    {
    	return (jobUpdateStatus != null) ? jobUpdateStatus : "";
    }
	public void setJobUpdateStatus(String jobUpdateStatus)
    {
    	this.jobUpdateStatus = (jobUpdateStatus != null) ? jobUpdateStatus : "";
    }
	public String getJobMatchStatus()
    {
    	return (jobMatchStatus != null) ? jobMatchStatus : "";
    }
	public void setJobMatchStatus(String jobMatchStatus)
    {
    	this.jobMatchStatus = (jobMatchStatus != null) ? jobMatchStatus : "";
    }
	public String getPassword()
    {
    	return password;
    }
	public void setPassword(String password)
    {
    	this.password = password;
    }
	public String getMailOtherStatus() {
		return (mailOtherStatus != null) ? mailOtherStatus : "";
	}
	public void setMailOtherStatus(String mailOtherStatus) {
		this.mailOtherStatus = (mailOtherStatus != null) ? mailOtherStatus : "";
	}
}