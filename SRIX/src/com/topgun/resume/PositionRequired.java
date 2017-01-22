package com.topgun.resume;

import java.util.List;

/**
 * @author katanyu
 *
 */
/**
 * @author katanyu
 *
 */
public class PositionRequired 
{
	private int idEmp;
	private int idPosition;
	private int sex;
	private int degree;
	private int expLess;
	private int expMost;
	private int salaryLess;
	private int salaryMost;
	private int[] workType;
	private int showInfield;
	private int unitSalary;
	private int ageMin;
	private int ageMax;
	private List<FacMajor> facMajor;
	private List<JobLocation> jobLocation;
	private List<WorkEqui> workEqui;
	private List<JobIndustry> jobIndustry;
	
	public int getUnitSalary() 
	{
		return unitSalary;
	}
	public void setUnitSalary(int unitSalary) 
	{
		this.unitSalary = unitSalary;
	}
	public int getIdEmp() 
	{
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
	public int getSex() 
	{
		return sex;
	}
	public void setSex(int sex) 
	{
		this.sex = sex;
	}
	public int getDegree() 
	{
		return degree;
	}
	public void setDegree(int degree) 
	{
		this.degree = degree;
	}
	public int getExpLess() 
	{
		return expLess;
	}
	public void setExpLess(int expLess) 
	{
		this.expLess = expLess;
	}
	public int getExpMost() 
	{
		return expMost;
	}
	public void setExpMost(int expMost) 
	{
		this.expMost = expMost;
	}
	public int getSalaryLess() 
	{
		return salaryLess;
	}
	public void setSalaryLess(int salaryLess) 
	{
		this.salaryLess = salaryLess;
	}
	public int getSalaryMost() 
	{
		return salaryMost;
	}
	public void setSalaryMost(int salaryMost) 
	{
		this.salaryMost = salaryMost;
	}
	public int[] getWorkType() 
	{
		return workType;
	}
	public void setWorkType(int[] workType) 
	{
		this.workType = workType;
	}
	public int getShowInfield() 
	{
		return showInfield;
	}
	public void setShowInfield(int showInfield) 
	{
		this.showInfield = showInfield;
	}
	public List<FacMajor> getFacMajor() 
	{
		return facMajor;
	}
	public void setFacMajor(List<FacMajor> facMajor) 
	{
		this.facMajor = facMajor;
	}
	public List<JobLocation> getJobLocation() 
	{
		return jobLocation;
	}
	public void setJobLocation(List<JobLocation> jobLocation) 
	{
		this.jobLocation = jobLocation;
	}
	public List<WorkEqui> getWorkEqui() 
	{
		return workEqui;
	}
	public void setWorkEqui(List<WorkEqui> workEqui) 
	{
		this.workEqui = workEqui;
	}
	public int getAgeMin() 
	{
		return ageMin;
	}
	public void setAgeMin(int ageMin) 
	{
		this.ageMin = ageMin;
	}
	public int getAgeMax() 
	{
		return ageMax;
	}
	public void setAgeMax(int ageMax) 
	{
		this.ageMax = ageMax;
	}
	public List<JobIndustry> getJobIndustry()
	{
		return jobIndustry;
	}
	public void setJobIndustry(List<JobIndustry> jobIndustry)
	{
		this.jobIndustry = jobIndustry;
	}
	
}
