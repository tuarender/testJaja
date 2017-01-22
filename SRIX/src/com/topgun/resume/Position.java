package com.topgun.resume;


public class Position
{	
	private int idPosition;
	private int idCompany;
	private String positionName;
	private String hrEmail;
	private boolean exists=false;
	public int getIdPosition() {
		return idPosition;
	}
	public void setIdPosition(int idPosition) {
		this.idPosition = idPosition;
	}
	public int getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getHrEmail() {
		return hrEmail;
	}
	public void setHrEmail(String hrEmail) {
		this.hrEmail = hrEmail;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
}
