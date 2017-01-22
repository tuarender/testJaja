package com.topgun.shris.masterdata;
public class CertFieldLanguage
{ 	
	private int idLanguage;
	private int idCertField;
	private String certFieldName;
	private boolean exists = false;
	
	public int getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}

	public int getIdCertField() {
		return idCertField;
	}

	public void setIdCertField(int idCertField) {
		this.idCertField = idCertField;
	}

	public String getCertFieldName() {
		return certFieldName;
	}

	public void setCertFieldName(String certFieldName) {
		this.certFieldName = certFieldName;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}
}