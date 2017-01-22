package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

public class Popup {

	private int idJsk;
	private int idResume;
	private String resumeName;
	private String fieldNameNew;
	private String fieldNameOld;
	private String subFieldNameNew;
	private String subFieldNameOld;
	private List<Popup> popupList=new ArrayList<Popup>();
	
	public int getIdJsk() {
		return idJsk;
	}
	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}
	public int getIdResume() {
		return idResume;
	}
	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}
	public String getResumeName() {
		return resumeName;
	}
	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}
	public List<Popup> getPopupList() {
		return popupList;
	}
	public void setPopupList(List<Popup> popupList) {
		this.popupList = popupList;
	}
	public String getFieldNameNew() {
		return fieldNameNew;
	}
	public void setFieldNameNew(String fieldNameNew) {
		this.fieldNameNew = fieldNameNew;
	}
	public String getFieldNameOld() {
		return fieldNameOld;
	}
	public void setFieldNameOld(String fieldNameOld) {
		this.fieldNameOld = fieldNameOld;
	}
	public String getSubFieldNameNew() {
		return subFieldNameNew;
	}
	public void setSubFieldNameNew(String subFieldNameNew) {
		this.subFieldNameNew = subFieldNameNew;
	}
	public String getSubFieldNameOld() {
		return subFieldNameOld;
	}
	public void setSubFieldNameOld(String subFieldNameOld) {
		this.subFieldNameOld = subFieldNameOld;
	}
	
	
	
}
