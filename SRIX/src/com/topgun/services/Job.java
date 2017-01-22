package com.topgun.services;

public class Job {
	public int idEmp;
	public int idPosition;
	public String companyName;
	public String positionName;
	public String sex;
	public String expLess;
	public String expMost;
	public String degree;
	public int idFaculty;
	public String jobSection;
	public String salaryLess;
	public String salaryMost;
	public int idIndustry;
	public int idMajor;
	public String workType;
	public String postDate;
	public String specialTitle;

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public int getIdIndustry() {
		return idIndustry;
	}

	public void setIdIndustry(int idIndustry) {
		this.idIndustry = idIndustry;
	}

	public int getIdMajor() {
		return idMajor;
	}

	public void setIdMajor(int idMajor) {
		this.idMajor = idMajor;
	}

	public int getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}

	public int getIdPosition() {
		return idPosition;
	}

	public void setIdPosition(int idPosition) {
		this.idPosition = idPosition;
	}

	public String getCompanyName() {
		return companyName != null ? companyName : "";
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName != null ? companyName : "";
	}

	public String getPositionName() {
		return positionName != null ? positionName : "";
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName != null ? positionName : "";
	}

	public String getSex() {
		return sex != null ? sex : "";
	}

	public void setSex(String sex) {
		this.sex = sex != null ? sex : "";
	}

	public String getExpLess() {
		return expLess != null ? expLess : "";
	}

	public void setExpLess(String expLess) {
		this.expLess = expLess != null ? expLess : "";
	}

	public String getExpMost() {
		return expMost != null ? expMost : "";
	}

	public void setExpMost(String expMost) {
		this.expMost = expMost != null ? expMost : "";
	}

	public String getDegree() {
		return degree != null ? expMost : "";
	}

	public void setDegree(String degree) {
		this.degree = degree != null ? expMost : "";
	}

	public String getJobSection() {
		return jobSection != null ? jobSection : "";
	}

	public void setJobSection(String jobSection) {
		this.jobSection = jobSection != null ? jobSection : "";
	}

	public String getSalaryLess() {
		return salaryLess != null ? salaryLess : "";
	}

	public void setSalaryLess(String salaryLess) {
		this.salaryLess = salaryLess != null ? salaryLess : "";
	}

	public String getSalaryMost() {
		return salaryMost != null ? salaryMost : "";
	}

	public void setSalaryMost(String salaryMost) {
		this.salaryMost = salaryMost != null ? salaryMost : "";
	}

	public String getWorkType() {
		return workType != null ? workType : "";
	}

	public void setWorkType(String workType) {
		this.workType = workType != null ? workType : "";
	}

	public int getIdFaculty() {
		return idFaculty;
	}

	public void setIdFaculty(int idFaculty) {
		this.idFaculty = idFaculty;
	}

	public String getSpecialTitle() {
		return specialTitle != null ? specialTitle : "";
	}

	public void setSpecialTitle(String special_title) {
		if (special_title != null) {
			String[] special_titles = special_title.split(",");
			String str = "";
			for (int i = 0; i < special_titles.length; i++) {
				if (special_titles[i].equals("1")) {
					str += "Urgently Required!!";
				} else if (special_titles[i].equals("2")) {
					str += "Urgent!!";
				} else if (special_titles[i].equals("3")) {
					str += "Fresh graduate are welcomed.";
				} else if (special_titles[i].equals("4")) {
					str += "Only English resume will be considered.";
				}
				if (i != special_titles.length - 1) {
					str += " ,";
				}
			}
			specialTitle = str;
		} else {
			specialTitle = null;
		}
	}

}
