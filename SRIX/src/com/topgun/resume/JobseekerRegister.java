package com.topgun.resume;

public class JobseekerRegister
{
	private int idJsk; 
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String primaryPhone="";
	private String primaryPhoneType=""; 
	private String lastStep;
	private String agent;
	private String reference;
	private String sponsor_email;
	
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
	public String getFirstName() 
	{
		return firstName;
	}
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	public String getLastName() 
	{
		return lastName;
	}
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	public String getPrimaryPhone() 
	{
		return primaryPhone= (primaryPhone != null) ? primaryPhone : "";
	}
	public void setPrimaryPhone(String primaryPhone) 
	{
		this.primaryPhone = primaryPhone= (primaryPhone != null) ? primaryPhone : "";
	}
	public String getPrimaryPhoneType() 
	{
		return primaryPhoneType= (primaryPhoneType != null) ? primaryPhoneType : "";
	}
	public void setPrimaryPhoneType(String primaryPhoneType)
	{
		this.primaryPhoneType= (primaryPhoneType != null) ? primaryPhoneType : "";
	}
	public String getLastStep()
	{
		return lastStep;
	}
	public void setLastStep(String lastStep) 
	{
		this.lastStep = lastStep;
	}
	public String getAgent() 
	{
		return agent;
	}
	public void setAgent(String agent) 
	{
		this.agent = agent;
	}
	public String getReference() 
	{
		return reference;
	}
	public void setReference(String reference) 
	{
		this.reference = reference;
	}
	public String getSponsor_email() 
	{
		return sponsor_email;
	}
	public void setSponsor_email(String sponsor_email) 
	{
		this.sponsor_email = sponsor_email;
	}
}
