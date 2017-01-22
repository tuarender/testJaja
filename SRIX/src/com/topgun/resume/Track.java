package com.topgun.resume;

import java.sql.Timestamp;

public class Track
{
	
	private int idJsk;
	private int idResume;
	private int idEmp;
	private int idPosition;
	private String empOther;
	private String positionOther;
	private String recipient;
	private Timestamp sendDate;
	private String ip;
	private String note;
	private String attachments;
	private String defaultStatus;
	private Timestamp noteUpdate;
	private int idTrack;
	private String source;
	private Timestamp timeStamp;
	private int sent;
	private String place="";
	private String referer="";
	private String shortReferer="";
	
	
	public String getPlace() 
	{
    	return place;
    }
	public void setPlace(String place) 
	{
    	this.place = place;
    }
	public String getDefaultStatus()
	{
		return (defaultStatus != null) ? defaultStatus : "";
	}
	public void setDefaultStatus(String defaultStatus)
	{
		this.defaultStatus = (defaultStatus != null) ? defaultStatus : "";
	}
	public String getEmpOther()
	{
		return (empOther != null) ? empOther : "";
	}
	public void setEmpOther(String empOther)
	{
		this.empOther = (empOther != null) ? empOther : "";
	}
	public String getIp()
	{
		return (ip != null) ? ip : "";
	}
	public void setIp(String ip)
	{
		this.ip = (ip != null) ? ip : "";
	}
	public String getNote()
	{
		return (note != null) ? note : "";
	}
	public void setNote(String note)
	{
		this.note = (note != null) ? note : "";
	}
	public Timestamp getNoteUpdate()
	{
		return noteUpdate;
	}
	public void setNoteUpdate(Timestamp noteUpdate)
	{
		this.noteUpdate = noteUpdate;
	}
	public String getPositionOther()
	{
		return (positionOther != null) ? positionOther : "";
	}
	public void setPositionOther(String positionOther)
	{
		this.positionOther = (positionOther != null) ? positionOther : "";
	}
	public String getRecipient()
	{
		return (recipient != null) ? recipient : "";
	}
	public void setRecipient(String recipient)
	{
		this.recipient = (recipient != null) ? recipient : "";
	}
	public String getAttachments()
	{
		return (attachments != null) ? attachments : "";
	}
	public void setAttachments(String attachments)
	{
		this.attachments = (attachments != null) ? attachments : "";
	}
	public String getSource() 
	{
    	return source;
    }
	public void setSource(String source) 
	{
    	this.source = source;
    }
	public int getSent() 
	{
    	return sent;
    }
	public void setSent(int sent) 
	{
    	this.sent = sent;
    }
	public String getReferer() 
	{
		return referer;
	}
	public void setReferer(String referer) 
	{
		this.referer = referer;
	}
	public int getIdJsk() 
	{
		return idJsk;
	}
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk;
	}
	public int getIdResume() {
		return idResume;
	}
	public void setIdResume(int idResume) 
	{
		this.idResume = idResume;
	}
	public int getIdEmp() {
		return idEmp;
	}
	public void setIdEmp(int idEmp) 
	{
		this.idEmp = idEmp;
	}
	public int getIdPosition() 
	{
		return idPosition;
	}
	public void setIdPosition(int idPosition) 
	{
		this.idPosition = idPosition;
	}
	public Timestamp getSendDate() 
	{
		return sendDate;
	}
	public void setSendDate(Timestamp sendDate) 
	{
		this.sendDate = sendDate;
	}
	public int getIdTrack() {
		return idTrack;
	}
	public void setIdTrack(int idTrack) 
	{
		this.idTrack = idTrack;
	}
	public Timestamp getTimeStamp() 
	{
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
	public String getShortReferer() {
		return shortReferer;
	}
	public void setShortReferer(String shortReferer) {
		this.shortReferer = shortReferer;
	}	
}
