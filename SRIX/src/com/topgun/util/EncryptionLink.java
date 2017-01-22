package com.topgun.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class EncryptionLink 
{
	int idJsk=-1;
	int idResume=-1;
	int idEmp=-1;
	int idPos=-1;
	String company=""; 
	String position=""; 
	String email=""; 
	String acceptMail="true";
	String source="";
	String cmd="";
	int idState=-1;
	String atms="";
	int idResume2=-1;
	String state ="0@0";
	String positionStr = "";
	String referer = "";
	String shortReferer = "";
	
	
	public static String getEncoding(EncryptionLink enc)
	{
		String result="";
		String E1=encoding(" "+enc.idJsk);
		String E2=encoding(" "+enc.idResume);
		String E3=encoding(" "+enc.idEmp);
		String E4=encoding(" "+enc.idPos);
		String E5=encoding(" "+enc.company);
		String E6=encoding(" "+enc.position);
		String E7=encoding(" "+enc.email);
		String E8=encoding(" "+enc.acceptMail);
		String E9=encoding(" "+enc.source);
		String E10=encoding(" "+enc.cmd);
		String E11=encoding(" "+enc.idState);
		String E12=encoding(" "+enc.atms);
		String E13=encoding(" "+enc.idResume2);
		String E14=encoding(" "+enc.state);
		String E15=encoding(" "+enc.positionStr);
		String E16=encoding(" "+enc.referer);
		String E17=encoding(" "+enc.shortReferer);
		result=encoding(E1+","+E2+","+E3+","+E4+","+E5+","+E6+","+E7+","+E8+","+E9+","+E10+","+E11+","+E12+","+E13+","+E14+","+E15+","+E16+","+E17);
		return result;		
	}
	
	public static String encoding(String key) 
	{
	    return Base64.encodeBase64String(StringUtils.getBytesUtf8(key));
	}
	
	public int getIdJsk() {
		return idJsk;
	}

	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}

	public int getIdResume() {
		return idResume;
	}

	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}

	public int getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}

	public int getIdPos() {
		return idPos;
	}

	public void setIdPos(int idPos) {
		this.idPos = idPos;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

 

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public int getIdState() {
		return idState;
	}

	public void setIdState(int idState) {
		this.idState = idState;
	}

	public String getAtms() {
		return atms;
	}

	public void setAtms(String atms) {
		this.atms = atms;
	}
	
	public String getAcceptMail() {
		return acceptMail;
	}
	public void setAcceptMail(String acceptMail) {
		this.acceptMail = acceptMail;
	}
	
	public int getIdResume2() {
		return idResume2;
	}

	public void setIdResume2(int idResume2) {
		this.idResume2 = idResume2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPositionStr() {
		return positionStr;
	}

	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getShortReferer() {
		return shortReferer;
	}

	public void setShortReferer(String shortReferer) {
		this.shortReferer = shortReferer;
	}
	
}
