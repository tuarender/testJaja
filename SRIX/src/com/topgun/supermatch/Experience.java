package com.topgun.supermatch;

public class Experience
{
	private int idJsk=-1;
	private int idResume=-1;
	private int idWork=-1;
	private int exp=-1;
	private int salaryMin=-1;
	private int salaryMax=-1;
	private int idJobField=-1;
	private int idSubField=-1;
	private boolean isWorking;
	private int workEndYear;
	private String industry;
	private String jobfieldName;
	private String subfieldName;
	
	Experience(int idJsk,int idResume)
	{
		this.idJsk=idJsk;
		this.idResume=idResume;
	}
	
	public int getIdJsk()
	{
		return idJsk;
	}


	public int getIdResume()
	{
		return idResume;
	}

	public int getIdWork()
	{
		return idWork;
	}
	public void setIdWork(int idWork)
	{
		this.idWork = idWork;
	}
	public int getExp()
	{
		return exp;
	}
	public void setExp(int exp)
	{
		this.exp = exp;
	}
	public int getSalaryMin()
	{
		return salaryMin;
	}
	public void setSalaryMin(int salaryMin)
	{
		this.salaryMin = salaryMin;
	}
	public int getSalaryMax()
	{
		return salaryMax;
	}
	public void setSalaryMax(int salaryMax)
	{
		this.salaryMax = salaryMax;
	}
	public int getIdJobField()
	{
		return idJobField;
	}
	public void setIdJobField(int idJobField)
	{
		this.idJobField = idJobField;
	}
	public int getIdSubField()
	{
		return idSubField;
	}
	public void setIdSubField(int idSubField)
	{
		this.idSubField = idSubField;
	}

	public boolean isWorking()
	{
		return isWorking;
	}

	public void setWorking(boolean isWorking)
	{
		this.isWorking = isWorking;
	}

	public int getWorkEndYear()
	{
		return workEndYear;
	}

	public void setWorkEndYear(int workEndYear)
	{
		this.workEndYear = workEndYear;
	}

	public void setIdJsk(int idJsk)
	{
		this.idJsk = idJsk;
	}

	public void setIdResume(int idResume)
	{
		this.idResume = idResume;
	}

	public String getIndustry() 
	{
		return industry;
	}

	public void setIndustry(String industry) 
	{
		this.industry = industry;
	}

	public String getJobfieldName() {
		return jobfieldName;
	}

	public void setJobfieldName(String jobfieldName) {
		this.jobfieldName = jobfieldName;
	}

	public String getSubfieldName() {
		return subfieldName;
	}

	public void setSubfieldName(String subfieldName) {
		this.subfieldName = subfieldName;
	}		
}
