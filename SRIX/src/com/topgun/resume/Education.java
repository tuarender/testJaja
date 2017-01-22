package com.topgun.resume;

import java.sql.Date;

public class Education 
{
	private int idJsk;
	private int idResume;
	private int id;
	private int idSchool;
	private int idDegree;
	private int idFacMajor;
	private String otherFaculty;
	private int idMajor;
	private String otherMajor;
	private int idFacMinor;
	private int idMinor;
	private String otherMinor;
	private Date finishDate;
	private Date startDate;
	private String otherSchool;
	private int idCountry;
	private float gpa;
	private int grade_a;
	private int grade_b;
	private int grade_c;
	private int grade_f;
	private String unit;
	private String award;
	private int idState;
	private int refId;
	private String deleteStatus;
	private String otherUnit;
	private String idStudent ;
	private int idRefer ;
	private String hashRefer;
	
	
	
	public int getIdRefer() {
		return idRefer;
	}
	public void setIdRefer(int idRefer) {
		this.idRefer = idRefer;
	}
	public String getIdStudent() {
		return idStudent;
	}
	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
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
	public int getIdSchool() 
	{
		return idSchool;
	}
	public void setIdSchool(int idSchool) 
	{
		this.idSchool = idSchool;
	}
	public int getIdDegree() 
	{
		return idDegree;
	}
	public void setIdDegree(int idDegree) 
	{
		this.idDegree = idDegree;
	}
	public int getIdFacMajor() 
	{
		return idFacMajor;
	}
	public void setIdFacMajor(int idFacMajor) 
	{
		this.idFacMajor = idFacMajor;
	}
	public String getOtherFaculty()
	{
		return (otherFaculty != null) ? otherFaculty : "";
	}
	public void setOtherFaculty(String otherFaculty)
	{
		this.otherFaculty = (otherFaculty != null) ? otherFaculty : "";
	}
	public int getIdMajor() 
	{
		return idMajor;
	}
	public void setIdMajor(int idMajor) 
	{
		this.idMajor = idMajor;
	}
	public String getOtherMajor()
	{
		return (otherMajor != null) ? otherMajor : "";
	}
	public void setOtherMajor(String otherMajor)
	{
		this.otherMajor = (otherMajor != null) ? otherMajor : "";
	}
	public int getIdFacMinor() 
	{
		return idFacMinor;
	}
	public void setIdFacMinor(int idFacMinor) 
	{
		this.idFacMinor = idFacMinor;
	}
	public int getIdMinor() 
	{
		return idMinor;
	}
	public void setIdMinor(int idMinor) 
	{
		this.idMinor = idMinor;
	}
	public String getOtherMinor()
	{
		return (otherMinor != null) ? otherMinor : "";
	}
	public void setOtherMinor(String otherMinor)
	{
		this.otherMinor = (otherMinor != null) ? otherMinor : "";
	}
	public void setFinishDate(Date finishDate) 
	{
		this.finishDate = finishDate;
	}
	public Date getStartDate() 
	{
		return startDate;
	}
	public void setStartDate(Date startDate) 
	{
		this.startDate = startDate;
	}
	public String getOtherSchool()
	{
		return (otherSchool != null) ? otherSchool : "";
	}
	public void setOtherSchool(String otherSchool)
	{
		this.otherSchool = (otherSchool != null) ? otherSchool : "";
	}
	public int getIdCountry() 
	{
		return idCountry;
	}
	public void setIdCountry(int idCountry) 
	{
		this.idCountry = idCountry;
	}
	public float getGpa() 
	{
		return gpa;
	}
	public void setGpa(float gpa) 
	{
		this.gpa = gpa;
	}
	public int getGrade_a() 
	{
		return grade_a;
	}
	public void setGrade_a(int grade_a) 
	{
		this.grade_a = grade_a;
	}
	public int getGrade_b() 
	{
		return grade_b;
	}
	public void setGrade_b(int grade_b) 
	{
		this.grade_b = grade_b;
	}
	public int getGrade_c() 
	{
		return grade_c;
	}
	public void setGrade_c(int grade_c) 
	{
		this.grade_c = grade_c;
	}
	public int getGrade_f() 
	{
		return grade_f;
	}
	public void setGrade_f(int grade_f) 
	{
		this.grade_f = grade_f;
	}
	public String getUnit()
	{
		return (unit != null) ? unit : "";
	}
	public void setUnit(String unit)
	{
		this.unit = (unit != null) ? unit : "";
	}
	public String getAward()
	{
		return (award != null) ? award : "";
	}
	public void setAward(String award)
	{
		this.award = (award != null) ? award : "";
	}
	public int getIdState() 
	{
		return idState;
	}
	public void setIdState(int idState) 
	{
		this.idState = idState;
	}
	public int getRefId() 
	{
		return refId;
	}
	public void setRefId(int refId) 
	{
		this.refId = refId;
	}
	public Date getFinishDate() 
	{
		return finishDate;
	}
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public String getDeleteStatus() {
		return deleteStatus;
	}
	public void setOtherUnit(String otherUnit) {
		this.otherUnit = otherUnit;
	}
	public String getOtherUnit() {
		return otherUnit;
	}

	public String getHashRefer() {
		return hashRefer;
	}
	public void setHashRefer(String hashRefer) {
		this.hashRefer = hashRefer;
	}
	@Override
	public String toString() {
		return "Education [idJsk=" + idJsk + ", idResume=" + idResume + ", id="
				+ id + ", idSchool=" + idSchool + ", idDegree=" + idDegree
				+ ", idFacMajor=" + idFacMajor + ", otherFaculty="
				+ otherFaculty + ", idMajor=" + idMajor + ", otherMajor="
				+ otherMajor + ", idFacMinor=" + idFacMinor + ", idMinor="
				+ idMinor + ", otherMinor=" + otherMinor + ", finishDate="
				+ finishDate + ", startDate=" + startDate + ", otherSchool="
				+ otherSchool + ", idCountry=" + idCountry + ", gpa=" + gpa
				+ ", grade_a=" + grade_a + ", grade_b=" + grade_b
				+ ", grade_c=" + grade_c + ", grade_f=" + grade_f + ", unit="
				+ unit + ", award=" + award + ", idState=" + idState
				+ ", refId=" + refId + ", deleteStatus=" + deleteStatus
				+ ", otherUnit=" + otherUnit + "]";
	}
	
	
}
