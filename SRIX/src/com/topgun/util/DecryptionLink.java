package com.topgun.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class DecryptionLink 
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
	
	public static DecryptionLink getDecode(String encode)
	{
		DecryptionLink result=new DecryptionLink();
		if(encode!=null)
		{
			if(!encode.equals(""))
			{
				encode=decode(encode);
				String[] encArray = encode.split(","); 
				result.setIdJsk(Util.getInt(decode(encArray[0]).trim()));
				result.setIdResume(Util.getInt(decode(encArray[1]).trim()));
				result.setIdEmp(Util.getInt(decode(encArray[2]).trim()));
				result.setIdPos(Util.getInt(decode(encArray[3]).trim()));
				result.setCompany(decode(encArray[4]).trim());
				result.setPosition(decode(encArray[5]).trim());
				result.setEmail(decode(encArray[6]).trim());
				result.setAcceptMail(decode(encArray[7]).trim());
				result.setSource(decode(encArray[8]).trim());
				result.setCmd(decode(encArray[9]).trim());
				result.setIdState(Util.getInt(decode(encArray[10]).trim()));
				result.setAtms(decode(encArray[11]).trim());
				result.setIdResume2(Util.getInt(decode(encArray[12]).trim()));
				result.setState(decode(encArray[13]).trim());
				result.setPositionStr(Util.getStr(decode(encArray[14].trim())));
				result.setReferer(Util.getStr(decode(encArray[15].trim())));
				result.setShortReferer(Util.getStr(decode(encArray[16].trim())));
			}
		}
		return result;
	}
	public static String decode(String key) 
	{
	    return StringUtils.newStringUtf8(Base64.decodeBase64(key));
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
