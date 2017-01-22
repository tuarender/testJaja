package com.topgun.resume;

public class Social {
	private int idJsk;
	private int idResume;
	private String lineId;
	private String skype;
	public int getIdJsk() {
		return idJsk ;
	}
	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}
	public int getIdResume() {
		return idResume ;
	}
	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}
	public String getLineId() {
		return lineId != null ? lineId : "";
	}
	public void setLineId(String lineId) {
		String temp = lineId != null ? lineId : "";
		for( int i = 0 ; i < temp.length() ; i++ )
		{
			if(temp.charAt(i) != '@')
			{
				temp = temp.substring(i, lineId.length());
				break;
			}
			else if(i == lineId.length() - 1)
			{
				temp = "";
			}
		}
		this.lineId = temp;
	}
	public String getSkype() {
		return skype != null ? skype : "";
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
}
