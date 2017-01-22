package com.topgun.resume;

import java.sql.Date;

public class WorkExperience 
{
	private int idJsk;
	private int idResume;
	private int id;
	private String companyName;
	private String positionLast;
	private int salaryLast;
	private Date workStart;
	private Date workEnd;
	private String workingStatus;
	private String jobDesc;
	private String jobDesc2;
	private String achieve;
	private String achieve2;
	private int workJobType;
	private int idCountry;
	private int idState;
	private String otherState;
	private int workJobField;
	private int workSubField;
	private int workJobFieldStart;
	private int workSubFieldStart;
	private int comSize;
	private int subordinate;
	private int expY;
	private int expM;
	private String comBusiness;
	private String workJobFieldOth;
	private String workSubFieldOth;
	private String workJobFieldOthStart;
	private String workSubFieldOthStart;
	private String reasonQuit;
	private int salaryStart;
	private String positionStart;
	private String salaryPer;
	private String salaryPerStart;
	private int refId;
	private int idCurrency;
	private int idCurrencyStart;
	private int idRefer;
	private String hashRefer;
	
	
	public String getHashRefer() {
		return hashRefer;
	}
	public void setHashRefer(String hashRefer) {
		this.hashRefer = hashRefer;
	}
	public int getIdRefer() {
		return idRefer;
	}
	public void setIdRefer(int idRefer) {
		this.idRefer = idRefer;
	}
	public String getAchieve()
	{
		return (achieve != null) ? achieve : "";
	}
	public void setAchieve(String achieve)
	{
		this.achieve = (achieve != null) ? achieve : "";
	}
	public String getAchieve2()
	{
		return (achieve2 != null) ? achieve2 : "";
	}
	public void setAchieve2(String achieve2)
	{
		this.achieve2 = (achieve2 != null) ? achieve2 : "";
	}
	public String getComBusiness()
	{
		return (comBusiness != null) ? comBusiness : "";
	}
	public void setComBusiness(String comBusiness)
	{
		this.comBusiness = (comBusiness != null) ? comBusiness : "";
	}
	public String getCompanyName()
	{
		return (companyName != null) ? companyName : "";
	}
	public void setCompanyName(String companyName)
	{
		this.companyName = (companyName != null) ? companyName : "";
	}
	public int getComSize()
	{
		return comSize;
	}
	public void setComSize(int comSize)
	{
		this.comSize = comSize;
	}
	public int getExpM()
	{
		return expM;
	}
	public void setExpM(int expM)
	{
		this.expM = expM;
	}
	public int getExpY()
	{
		return expY;
	}
	public void setExpY(int expY)
	{
		this.expY = expY;
	}
	public int getWorkJobType()
	{
		return workJobType;
	}
	public void setWorkJobType(int workJobType)
	{
		this.workJobType = workJobType;
	}
	public int getIdState()
	{
		return idState;
	}
	public void setIdState(int idState)
	{
		this.idState = idState;
	}
	public String getJobDesc()
	{
		return (jobDesc != null) ? jobDesc : "";
	}
	public void setJobDesc(String jobDesc)
	{
		this.jobDesc = (jobDesc != null) ? jobDesc : "";
	}
	public String getJobDesc2()
	{
		return (jobDesc2 != null) ? jobDesc2 : "";
	}
	public void setJobDesc2(String jobDesc2)
	{
		this.jobDesc2 = (jobDesc2 != null) ? jobDesc2 : "";
	}
	public String getOtherState()
	{
		return (otherState != null) ? otherState : "";
	}
	public void setOtherState(String otherState)
	{
		this.otherState = (otherState != null) ? otherState : "";
	}
	public String getPositionLast()
	{
		return (positionLast != null) ? positionLast : "";
	}
	public void setPositionLast(String positionLast)
	{
		this.positionLast = (positionLast != null) ? positionLast : "";
	}
	public String getPositionStart()
	{
		return (positionStart != null) ? positionStart : "";
	}
	public void setPositionStart(String positionStart)
	{
		this.positionStart = (positionStart != null) ? positionStart : "";
	}
	public String getReasonQuit()
	{
		return (reasonQuit != null) ? reasonQuit : "";
	}
	public void setReasonQuit(String reasonQuit)
	{
		this.reasonQuit = (reasonQuit != null) ? reasonQuit : "";
	}
	public int getSalaryLast()
	{
		return salaryLast;
	}
	public void setSalaryLast(int salaryLast)
	{
		this.salaryLast = salaryLast;
	}
	public String getSalaryPer()
	{
		return (salaryPer != null) ? salaryPer : "";
	}
	public void setSalaryPer(String salaryPer)
	{
		this.salaryPer = (salaryPer != null) ? salaryPer : "";
	}
	public String getSalaryPerStart()
	{
		return (salaryPerStart != null) ? salaryPerStart : "";
	}
	public void setSalaryPerStart(String salaryPerStart)
	{
		this.salaryPerStart = (salaryPerStart != null) ? salaryPerStart : "";
	}
	public int getSalaryStart()
	{
		return salaryStart;
	}
	public void setSalaryStart(int salaryStart)
	{
		this.salaryStart = salaryStart;
	}
	public int getSubordinate()
	{
		return subordinate;
	}
	public void setSubordinate(int subordinate)
	{
		this.subordinate = subordinate;
	}
	public Date getWorkEnd()
	{
		return workEnd;
	}
	public void setWorkEnd(Date workEnd)
	{
		this.workEnd = workEnd;
	}
	public String getWorkingStatus()
	{
		return (workingStatus != null) ? workingStatus : "";
	}
	public void setWorkingStatus(String workingStatus)
	{
		this.workingStatus = (workingStatus != null) ? workingStatus : "";
	}
	public int getWorkJobField()
	{
		return workJobField;
	}
	public void setWorkJobField(int workJobField)
	{
		this.workJobField = workJobField;
	}
	public String getWorkJobFieldOth()
	{
		return (workJobFieldOth != null) ? workJobFieldOth : "";
	}
	public void setWorkJobFieldOth(String workJobFieldOth)
	{
		this.workJobFieldOth = (workJobFieldOth != null) ? workJobFieldOth : "";
	}
	public int getWorkJobFieldStart()
	{
		return workJobFieldStart;
	}
	public void setWorkJobFieldStart(int workJobFieldStart)
	{
		this.workJobFieldStart = workJobFieldStart;
	}
	public String getWorkJobFieldOthStart()
	{
		return (workJobFieldOthStart != null) ? workJobFieldOthStart : "";
	}
	public void setWorkJobFieldOthStart(String workJobFieldOthStart)
	{
		this.workJobFieldOthStart = (workJobFieldOthStart != null) ? workJobFieldOthStart : "";
	}
	public Date getWorkStart()
	{
		return workStart;
	}
	public void setWorkStart(Date workStart)
	{
		this.workStart = workStart;
	}
	public int getWorkSubField()
	{
		return workSubField;
	}
	public void setWorkSubField(int workSubField)
	{
		this.workSubField = workSubField;
	}
	public String getWorkSubFieldOth()
	{
		return (workSubFieldOth != null) ? workSubFieldOth : "";
	}
	public void setWorkSubFieldOth(String workSubFieldOth)
	{
		this.workSubFieldOth = (workSubFieldOth != null) ? workSubFieldOth : "";
	}
	public int getWorkSubFieldStart()
	{
		return workSubFieldStart;
	}
	public void setWorkSubFieldStart(int workSubFieldStart)
	{
		this.workSubFieldStart = workSubFieldStart;
	}
	public String getWorkSubFieldOthStart()
	{
		return (workSubFieldOthStart != null) ? workSubFieldOthStart : "";
	}
	public void setWorkSubFieldOthStart(String workSubFieldOthStart)
	{
		this.workSubFieldOthStart = (workSubFieldOthStart != null) ? workSubFieldOthStart : "";
	}
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
	public int getIdCountry() 
	{
		return idCountry;
	}
	public void setIdCountry(int idCountry) 
	{
		this.idCountry = idCountry;
	}
	public int getRefId() 
	{
		return refId;
	}
	public void setRefId(int refId) 
	{
		this.refId = refId;
	}
	public int getIdCurrency() 
	{
		return idCurrency;
	}
	public void setIdCurrency(int idCurrency) 
	{
		this.idCurrency = idCurrency;
	}
	
	public int getIdCurrencyStart() 
	{
		return idCurrencyStart;
	}
	public void setIdCurrencyStart(int idCurrencyStart) 
	{
		this.idCurrencyStart = idCurrencyStart;
	}
}
