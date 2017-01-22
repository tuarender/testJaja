package com.topgun.resume;

import java.sql.Date;
import java.sql.Timestamp;

public class Resume 
{
	private int idResume;
	private int idJsk;
	private String resumeName="";
	private String resumePrivacy;
	private String firstName;
	private String lastName;
	private int idCountry;
	private int idState;
	private int idCity;
	private int idLanguage;
	private String homeAddress;
	private String postal;
	private String primaryPhone;
	private String primaryPhoneType;
	private String secondaryPhone;
	private String secondaryPhoneType;
	private String secondaryEmail;
	private Timestamp createDate;
	private Timestamp timeStamp;
	private String expStatus;
	private int expCompany;
	private String otherState;
	private String otherCity;
	private String salutation;
	private String firstNameThai;
	private String lastNameThai;
	private Date birthDate;
	private float height;
	private float weight;
	private String citizenship;
	private String ownCarStatus;
	private int applyIdCountry;
	private int templateIdCountry;
	private int expMonth;
	private int expYear;
	private int expFullYear ;
	private int expFullMonth ;
	private int expPtYear ;
	private int expPtMonth ;
	private String completeStatus;
	private String locale;
	private int yearPass;
	private int monthPass;
	private int dayPass;
	private int idParent;
	private Timestamp lastApply ; 
	
	
	
	public int getExpFullYear() {
		return expFullYear;
	}
	public void setExpFullYear(int expFullYear) {
		this.expFullYear = expFullYear;
	}
	public int getExpFullMonth() {
		return expFullMonth;
	}
	public void setExpFullMonth(int expFullMonth) {
		this.expFullMonth = expFullMonth;
	}
	public int getExpPtYear() {
		return expPtYear;
	}
	public void setExpPtYear(int expPtYear) {
		this.expPtYear = expPtYear;
	}
	public int getExpPtMonth() {
		return expPtMonth;
	}
	public void setExpPtMonth(int expPtMonth) {
		this.expPtMonth = expPtMonth;
	}
	public int getIdParent() {
		return idParent;
	}
	public void setIdParent(int idParent) {
		this.idParent = idParent;
	}
	public String getCompleteStatus()
    {
    	return completeStatus;
    }
	public void setCompleteStatus(String completeStatus)
    {
    	this.completeStatus = completeStatus;
    }
	public int getIdResume() 
	{
		return idResume;
	}
	public void setIdResume(int idResume) 
	{
		this.idResume = idResume;
	}
	public int getIdJsk() 
	{
		return idJsk;
	}
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk;
	}
	public String getResumeName()
    {
    	return resumeName;
    }
	public void setResumeName(String resumeName)
    {
    	this.resumeName = resumeName;
    }
	public String getResumePrivacy()
    {
    	return resumePrivacy;
    }
	public void setResumePrivacy(String resumePrivacy)
    {
    	this.resumePrivacy = resumePrivacy;
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
	public int getIdCountry() 
	{
		return idCountry;
	}
	public void setIdCountry(int idCountry) 
	{
		this.idCountry = idCountry;
	}
	public int getIdState() 
	{
		return idState;
	}
	public void setIdState(int idState) 
	{
		this.idState = idState;
	}
	public int getIdCity() 
	{
		return idCity;
	}
	public void setIdCity(int idCity) 
	{
		this.idCity = idCity;
	}
	public int getIdLanguage() 
	{
		return idLanguage;
	}
	public void setIdLanguage(int idLanguage) 
	{
		this.idLanguage = idLanguage;
	}
	public String getHomeAddress()
    {
    	return (homeAddress != null) ? homeAddress : "";
    }
	public void setHomeAddress(String homeAddress)
    {
    	this.homeAddress = (homeAddress != null) ? homeAddress : "";
    }
	public String getPostal()
    {
    	return (postal != null) ? postal : "";
    }
	public void setPostal(String postal)
    {
    	this.postal = (postal != null) ? postal : "";
    }
	public String getPrimaryPhone()
    {
    	return (primaryPhone != null) ? primaryPhone : "";
    }
	public void setPrimaryPhone(String primaryPhone)
    {
    	this.primaryPhone = (primaryPhone != null) ? primaryPhone : "";
    }
	public String getPrimaryPhoneType()
    {
    	return (primaryPhoneType != null) ? primaryPhoneType : "";
    }
	public void setPrimaryPhoneType(String primaryPhoneType)
    {
    	this.primaryPhoneType = (primaryPhoneType != null) ? primaryPhoneType : "";
    }
	public String getSecondaryPhone()
    {
    	return (secondaryPhone != null) ? secondaryPhone : "";
    }
	public void setSecondaryPhone(String secondaryPhone)
    {
    	this.secondaryPhone = (secondaryPhone != null) ? secondaryPhone : "";
    }
	public String getSecondaryPhoneType()
    {
    	return (secondaryPhoneType != null) ? secondaryPhoneType : "";
    }
	public void setSecondaryPhoneType(String secondaryPhoneType)
    {
    	this.secondaryPhoneType = (secondaryPhoneType != null) ? secondaryPhoneType
    	: "";
    }
	public String getSecondaryEmail()
    {
    	return (secondaryEmail != null) ? secondaryEmail : "";
    }
	public void setSecondaryEmail(String secondaryEmail)
    {
    	this.secondaryEmail = (secondaryEmail != null) ? secondaryEmail : "";
    }
	public Timestamp getCreateDate() 
	{
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) 
	{
		this.createDate = createDate;
	}
	public Timestamp getTimeStamp() 
	{
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
	public String getExpStatus()
    {
    	return (expStatus != null) ? expStatus : "";
    }
	public void setExpStatus(String expStatus)
    {
    	this.expStatus = (expStatus != null) ? expStatus : "";
    }
	public int getExpCompany() 
	{
		return expCompany;
	}
	public void setExpCompany(int expCompany) 
	{
		this.expCompany = expCompany;
	}
	public String getOtherState()
    {
    	return (otherState != null) ? otherState : "";
    }
	public void setOtherState(String otherState)
    {
    	this.otherState = (otherState != null) ? otherState : "";
    }
	public String getOtherCity()
    {
    	return (otherCity != null) ? otherCity : "";
    }
	public void setOtherCity(String otherCity)
    {
    	this.otherCity = (otherCity != null) ? otherCity : "";
    }
	public String getSalutation()
    {
    	return (salutation != null) ? salutation : "";
    }
	public void setSalutation(String salutation)
    {
    	this.salutation = (salutation != null) ? salutation : "";
    }
	public String getFirstNameThai()
    {
    	return (firstNameThai != null) ? firstNameThai : "";
    }
	public void setFirstNameThai(String firstNameThai)
    {
    	this.firstNameThai = (firstNameThai != null) ? firstNameThai : "";
    }
	public String getLastNameThai()
    {
    	return (lastNameThai != null) ? lastNameThai : "";
    }
	public void setLastNameThai(String lastNameThai)
    {
    	this.lastNameThai = (lastNameThai != null) ? lastNameThai : "";
    }
	public Date getBirthDate() 
	{
		return birthDate;
	}
	public void setBirthDate(Date birthDate) 
	{
		this.birthDate = birthDate;
	}
	public float getHeight() 
	{
		return height;
	}
	public void setHeight(float height) 
	{
		this.height = height;
	}
	public float getWeight() 
	{
		return weight;
	}
	public void setWeight(float weight) 
	{
		this.weight = weight;
	}
	public String getCitizenship()
    {
    	return (citizenship != null) ? citizenship : "";
    }
	public void setCitizenship(String citizenship)
    {
    	this.citizenship = (citizenship != null) ? citizenship : "";
    }
	public String getOwnCarStatus()
    {
    	return (ownCarStatus != null) ? ownCarStatus : "";
    }
	public void setOwnCarStatus(String ownCarStatus)
    {
    	this.ownCarStatus = (ownCarStatus != null) ? ownCarStatus : "";
    }
	public int getApplyIdCountry() 
	{
		return applyIdCountry;
	}
	public void setApplyIdCountry(int applyIdCountry) 
	{
		this.applyIdCountry = applyIdCountry;
	}
	public int getTemplateIdCountry() 
	{
		return templateIdCountry;
	}
	public void setTemplateIdCountry(int templateIdCountry) 
	{
		this.templateIdCountry = templateIdCountry;
	}
	public int getExpMonth()
	{
		return expMonth;
	}
	public void setExpMonth(int expMonth) 
	{
		this.expMonth = expMonth;
	}
	public int getExpYear() 
	{
		return expYear;
	}
	public void setExpYear(int expYear) 
	{
		this.expYear = expYear;
	}
	public String getLocale() 
	{
		return locale;
	}
	public void setLocale(String locale) 
	{
		this.locale = locale;
	}
	public int getYearPass() {
		return yearPass;
	}
	public void setYearPass(int yearPass) {
		this.yearPass = yearPass;
	}
	public int getMonthPass() {
		return monthPass;
	}
	public void setMonthPass(int monthPass) {
		this.monthPass = monthPass;
	}
	public int getDayPass() {
		return dayPass;
	}
	public void setDayPass(int dayPass) {
		this.dayPass = dayPass;
	}
	public Timestamp getLastApply() {
		return lastApply;
	}
	public void setLastApply(Timestamp lastApply) {
		this.lastApply = lastApply;
	}
	
	
}
