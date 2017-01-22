package com.topgun.resume;

public class Location 
{
	private int idJsk;
	private int idResume;
	private int idLocation;
	private int idCountry;
	private int idState;
	private int idCity;
	private String otherState;
	private String otherCity;
	private String workPermit; //TRUE/FALSE
	private int idRegion ;
	private String regionName ;
	private String stateName ;
	private String cityName ;
	private int type ;
	
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
	public int getIdLocation() 
	{
		return idLocation;
	}
	public void setIdLocation(int idLocation) 
	{
		this.idLocation = idLocation;
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
	
	public String getOtherState() 
	{
		return (otherState != null) ? otherState : "";
	}
	
	public void setOtherState(String otherState) 
	{
		this.otherState=otherState;
	}
	
	public String getOtherCity()
    {
    	return (otherCity != null) ? otherCity : "";
    }
	
	public void setOtherCity(String otherCity)
    {
    	this.otherCity = (otherCity != null) ? otherCity : "";
    }
	
	public String getWorkPermit()
    {
    	return (workPermit != null) ? workPermit : "";
    }
	
	public void setWorkPermit(String workPermit)
    {
    	this.workPermit = (workPermit != null) ? workPermit : "";
    }
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
