package com.topgun.resume;

public class FwdBean {
	private int jtg_fwd_id;
	private int id_emp;
	private String sender;
	private String recipient;
	private String cc;
	private String subject;
	private String msg;
	private String timestamp;
	private String send_name;
	private int idLang;
	private String topage;
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public int getJtg_fwd_id() {
		return jtg_fwd_id;
	}
	public void setJtg_fwd_id(int jtg_fwd_id) {
		this.jtg_fwd_id = jtg_fwd_id;
	}
	public int getId_emp() {
		return id_emp;
	}
	public void setId_emp(int id_emp) {
		this.id_emp = id_emp;
	}
	public String getSender() {
		return (sender != null) ? sender : "";
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return (recipient != null) ? recipient : "";
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getCc() {
		return (cc != null) ? cc : "";
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return (subject != null) ? subject : "";
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsg() {
		return (msg != null) ? msg : "";
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTimestamp() {
		return (timestamp != null) ? timestamp : "";
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getIdLang() {
		return idLang;
	}
	public void setIdLang(int idLang) {
		this.idLang = idLang;
	}
	public String getTopage() {
		return topage;
	}
	public void setTopage(String topage) {
		this.topage = topage;
	}
	
}