package com.topgun.resume;

import java.sql.Date;

public class Certificate
{
	 private int idJsk;
	    private int idResume;
	    private int idCertificate;
	    private String cerName;
	    private Date cerDate;
	    private String institution;
	    private String detail;
	    private String deleteStatus;
		private int idCertField;
		private int idCertSubfield;
		private Date timeStamp;
		private boolean exists=false;
    
	public int getIdJsk() 
	{
		return idJsk;
    }
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk ;
	}
	public int getIdResume() 
	{
		return idResume;
	}
	public void setIdResume(int idResume) 
	{
		this.idResume = idResume ;
	}
	public int getId()
	{
		return idCertificate ;
	}
	public void setId(int id)
	{
		this.idCertificate = id;
	}
	public Date getTimeStamp() 
	{
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
	public Date getCerDate() {
		return cerDate;
	}
	public void setCerDate(Date cerDate)
	{
		this.cerDate =cerDate;
	}
	public String getCerName()
	{
		return (cerName != null) ? cerName : "";
	}
	public void setCerName(String cerName)
	{
		this.cerName = (cerName != null) ? cerName : "";
	}
	public String getDetail()
	{
		return (detail != null) ? detail : "";
	}
	public void setDetail(String detail)
	{
		this.detail = (detail != null) ? detail : "";
	}
	public int getIdCertificate()
	{
		return idCertificate ;
	}
	public void setIdCertificate(int idCertificate)
	{
		this.idCertificate = idCertificate ;
	}
	public String getInstitution()
	{
		return (institution != null) ? institution : "";
	}
	public void setInstitution(String institution)
	{
		this.institution = (institution != null) ? institution : "";
	}
	public String getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public int getIdCertField() {
		return idCertField;
	}
	public void setIdCertField(int idCertField) {
		this.idCertField = idCertField;
	}
	public int getIdCertSubfield() {
		return idCertSubfield;
	}
	public void setIdCertSubfield(int idCertSubfield) {
		this.idCertSubfield = idCertSubfield;
	}
	public boolean isExists() {
		return exists;
	}
}
