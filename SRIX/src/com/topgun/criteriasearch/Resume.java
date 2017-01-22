package com.topgun.criteriasearch;

public class Resume 
{
	private int idJsk;
	private int idSearch;
	private String criteriaSearchMailFlag;
	private String username;
	private String firstName;
	private String lastName;
	private int idLanguage=38;
	private String photoPath;
	private int idResume;
	
	
	public int getIdResume() {
		return idResume;
	}
	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}
	public int getIdJsk() {
		return idJsk;
	}
	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}
	public int getIdSearch() {
		return idSearch;
	}
	public void setIdSearch(int idSearch) {
		this.idSearch = idSearch;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public int getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getCriteriaSearchMailFlag() {
		return criteriaSearchMailFlag;
	}
	public void setCriteriaSearchMailFlag(String criteriaSearchMailFlag) {
		this.criteriaSearchMailFlag = criteriaSearchMailFlag;
	}
}
