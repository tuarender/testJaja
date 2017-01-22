package com.topgun.resume;

import java.sql.Date;

public class Family
{
	private int idJsk;
	private int idResume;
	private int idFamily;
	private String firstName;
	private String lastName;
	private String citizenshipOther;
	private String company;
	private String position;
	private String telephone;
	private String diedStatus;
	private String familyStatus;
	private String workStatus;
	private String marriedStatus;
	private int age;
	private String siblingRelationship;
	private Date timeStamp;
	
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
	public int getIdFamily() 
	{
		return idFamily;
	}
	public void setIdFamily(int idFamily) 
	{
		this.idFamily = idFamily;
	}
	public String getFirstName()
	{
		return (firstName != null) ? firstName : "";
	}

	public void setFirstName(String firstName)
	{
		this.firstName = (firstName != null) ? firstName : "";
	}
	public String getLastName()
	{
		return (lastName != null) ? lastName : "";
	}

	public void setLastName(String lastName)
	{
		this.lastName = (lastName != null) ? lastName : "";
	}
	public String getCitizenshipOther()
	{
		return (citizenshipOther != null) ? citizenshipOther : "";
	}

	public void setCitizenshipOther(String citizenshipOther)
	{
		this.citizenshipOther = (citizenshipOther != null) ? citizenshipOther
				: "";
	}
	public String getCompany()
	{
		return (company != null) ? company : "";
	}

	public void setCompany(String company)
	{
		this.company = (company != null) ? company : "";
	}
	public String getPosition()
	{
		return (position != null) ? position : "";
	}

	public void setPosition(String position)
	{
		this.position = (position != null) ? position : "";
	}

	public String getTelephone()
	{
		return (telephone != null) ? telephone : "";
	}

	public void setTelephone(String telephone)
	{
		this.telephone = (telephone != null) ? telephone : "";
	}
	public String getDiedStatus()
	{
		return (diedStatus != null) ? diedStatus : "";
	}

	public void setDiedStatus(String diedStatus)
	{
		this.diedStatus = (diedStatus != null) ? diedStatus : "";
	}
	public String getFamilyStatus()
	{
		return (familyStatus != null) ? familyStatus : "";
	}

	public void setFamilyStatus(String familyStatus)
	{
		this.familyStatus = (familyStatus != null) ? familyStatus : "";
	}

	public String getWorkStatus()
	{
		return (workStatus != null) ? workStatus : "";
	}

	public void setWorkStatus(String workStatus)
	{
		this.workStatus = (workStatus != null) ? workStatus : "";
	}


	public String getMarriedStatus()
	{
		return (marriedStatus != null) ? marriedStatus : "";
	}

	public void setMarriedStatus(String marriedStatus)
	{
		this.marriedStatus = (marriedStatus != null) ? marriedStatus : "";
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) 
	{
		this.age = age;
	}
	public String getSiblingRelationship()
	{
		return (siblingRelationship != null) ? siblingRelationship : "";
	}

	public void setSiblingRelationship(String siblingRelationship)
	{
		this.siblingRelationship = (siblingRelationship != null) ? siblingRelationship: "";
	}
	public Date getTimeStamp() 
	{
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "Family [idJsk=" + idJsk + ", idResume=" + idResume
				+ ", idFamily=" + idFamily + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", citizenshipOther="
				+ citizenshipOther + ", company=" + company + ", position="
				+ position + ", telephone=" + telephone + ", diedStatus="
				+ diedStatus + ", familyStatus=" + familyStatus
				+ ", workStatus=" + workStatus + ", marriedStatus="
				+ marriedStatus + ", age=" + age + ", siblingRelationship="
				+ siblingRelationship + ", timeStamp=" + timeStamp + "]";
	}
	
}
