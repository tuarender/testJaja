package com.topgun.shris.masterdata;
public class CertSubFieldLanguage
{ 	
	private int idLanguage;
	private int idCertField;
	private int idCertSubfield;
	private String certSubfieldName;
	private boolean exists = false;
	
	public int getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}
	public int getIdCertFiled() {
		return idCertField;
	}
	public void setIdCertFiled(int idCertFiled) {
		this.idCertField = idCertFiled;
	}
	public int getIdCertSubfield() {
		return idCertSubfield;
	}
	public void setIdCertSubfield(int idCertSubfield) {
		this.idCertSubfield = idCertSubfield;
	}
	public String getCertSubfieldName() {
		return certSubfieldName;
	}
	public void setCertSubfieldName(String certSubfieldName) {
		this.certSubfieldName = certSubfieldName;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
}