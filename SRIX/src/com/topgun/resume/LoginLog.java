package com.topgun.resume;

import java.sql.Timestamp;

public class LoginLog
{
	private int idJsk;
	private String username;
	private String password;
	private String result;
	private String reason;
	private String ip;
	private String info;
	private Timestamp loginTime;
	private String device;
	private String agent;
	
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
	public String getPassword()
    {
    	return password;
    }
	public void setPassword(String password)
    {
    	this.password = password;
    }
	public String getResult()
    {
    	return result;
    }
	public void setResult(String result)
    {
    	this.result = result;
    }
	public String getReason()
    {
    	return reason;
    }
	public void setReason(String reason)
    {
    	this.reason = reason;
    }
	public String getIp()
    {
    	return ip;
    }
	public void setIp(String ip)
    {
    	this.ip = ip;
    }
	public Timestamp getLoginTime()
    {
    	return loginTime;
    }
	public void setLoginTime(Timestamp loginTime)
    {
    	this.loginTime = loginTime;
    }
	public String getInfo()
    {
    	return info;
    }
	public void setInfo(String info)
    {
    	this.info = info;
    }
	
	public String getDevice() 
	{
		return device;
	}
	public void setDevice(String device) 
	{
		this.device = device;
	}
	public String getAgent() 
	{
		return agent;
	}
	
	public void setAgent(String agent) 
	{
		this.agent = agent;
	}
	
	
}
