package com.topgun.resume;

public class Reference
{
	private int idJsk;
    private int idResume;
    private int id;
    private String refType;
    private String othRefType;
    private String refName ;
    private String refCompany ;
    private String refTel ;
    private String refTitle ;
    
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
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getRefType() 
	{
		return refType != null ? refType : "";
	}
	public void setRefType(String refType) 
	{
		this.refType = refType != null ? refType : "";
	}
	public String getOthRefType() 
	{
		return othRefType != null ? othRefType : "";
	}
	public void setOthRefType(String othRefType) 
	{
		this.othRefType = othRefType != null ? othRefType : "";
	}
	public String getRefName() 
	{
		return refName != null ? refName : "";
	}
	public void setRefName(String refName) 
	{
		this.refName = refName != null ? refName : "";
	}
	public String getRefCompany() 
	{
		return refCompany != null ? refCompany : "";
	}
	public void setRefCompany(String refCompany) 
	{
		this.refCompany = refCompany != null ? refCompany : "";
	}
	public String getRefTel() 
	{
		return refTel != null ? refTel : "";
	}
	public void setRefTel(String refTel) 
	{
		this.refTel = refTel != null ? refTel : "";
	}
	public String getRefTitle() 
	{
		return refTitle != null ? refTitle : "";
	}
	public void setRefTitle(String refTitle) 
	{
		this.refTitle = refTitle != null ? refTitle : "";
	}
}
