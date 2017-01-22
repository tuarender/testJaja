package com.topgun.resume;

import com.topgun.util.Util;

public class CancelService 
{
	private int idJsk = 0;
	private String email = "";
	private String service = "";
	private int reasonType = 0;
	private int howLong = 0;
	private int changeMail = 0;
	private int howOften = 0;
	private int notMatch = 0;
	private String otherReason = "";
	private String tel = "";
	private String newEmail = "";
	private String timeStamp = "";
	private String nextSend = "";
	private String disable = "";
	private String disableTimeStamp = "";
	
	public int getIdJsk() {
		return idJsk;
	}
	public void setIdJsk(int idJsk) {
		this.idJsk = Util.getInt(idJsk);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = Util.getStr(email);
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = Util.getStr(service);
	}
	public int getReasonType() {
		return reasonType;
	}
	public void setReasonType(int reasonType) {
		this.reasonType = Util.getInt(reasonType);
	}
	public int getHowLong() {
		return howLong;
	}
	public void setHowLong(int howLong) {
		this.howLong = Util.getInt(howLong);
	}
	public int getChangeMail() {
		return changeMail;
	}
	public void setChangeMail(int changeMail) {
		this.changeMail = Util.getInt(changeMail);
	}
	public int getHowOften() {
		return howOften;
	}
	public void setHowOften(int howOften) {
		this.howOften = Util.getInt(howOften);
	}
	public int getNotMatch() {
		return notMatch;
	}
	public void setNotMatch(int notMatch) {
		this.notMatch = Util.getInt(notMatch);
	}
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = Util.getStr(otherReason);
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = Util.getStr(tel);
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = Util.getStr(newEmail);
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = Util.getStr(timeStamp);
	}
	public void setNextSend(String nextSend) {
		this.nextSend = nextSend;
	}
	public String getNextSend() {
		return nextSend;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	public String getDisable() {
		return disable;
	}
	public void setDisableTimeStamp(String disableTimeStamp) {
		this.disableTimeStamp = disableTimeStamp;
	}
	public String getDisableTimeStamp() {
		return disableTimeStamp;
	}
}
