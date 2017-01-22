package com.topgun.resume;

public class AccessLog 
{
	private String sessionID;
	private String host;
	private String refer;
	private String referUrl;
	private String requestUrl;
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getRefer() {
		return refer;
	}
	public void setRefer(String refer) {
		this.refer = refer;
	}
	public String getReferUrl() {
		return referUrl;
	}
	public void setReferUrl(String referUrl) {
		this.referUrl = referUrl;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	
}
