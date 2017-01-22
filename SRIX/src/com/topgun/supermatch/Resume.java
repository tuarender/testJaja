package com.topgun.supermatch;

public class Resume
{
	private int idJsk=-1;
	private int idResume=-1;
	private int age=-1;
	private int sex=-1;
	private int idCountry=-1;
	private int idState=-1;
	private int expYear=-1;
	private int lastUpdate;
	private int idLanguage=38;
	private String location;
	private String gender;
	private String firstName;
	private String lastName;
	private String timestamp;
	private String locationExclude;
	private String cityList;
	private Boolean hasIndustrial;
	
	public String getCityList() {
		return cityList;
	}
	public void setCityList(String cityList) {
		this.cityList = cityList;
	}
	public String getLocationExclude() {
		return locationExclude;
	}
	public void setLocationExclude(String locationExclude) {
		this.locationExclude = locationExclude;
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
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public int getSex()
	{
		return sex;
	}
	public void setSex(int sex)
	{
		this.sex = sex;
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
	public int getExpYear()
	{
		return expYear;
	}
	public void setExpYear(int expYear)
	{
		this.expYear = expYear;
	}
	public int getLastUpdate()
	{
		return lastUpdate;
	}
	public void setLastUpdate(int lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}
	public int getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Boolean getHasIndustrial() {
		return hasIndustrial;
	}
	public void setHasIndustrial(Boolean hasIndustrial) {
		this.hasIndustrial = hasIndustrial;
	}
	 
	
}
