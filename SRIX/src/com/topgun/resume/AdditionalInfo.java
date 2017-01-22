package com.topgun.resume;


public class AdditionalInfo
{
	private int idResume;
	private int idJsk;
	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;

	public int getIdResume() 
	{
		return idResume;
	}
	public void setIdResume(int idResume) 
	{
		this.idResume = idResume;
	}
	public int getIdJsk() 
	{
		return idJsk;
	}
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk;
	}
	public String getAdditional1()
	{
		return (additional1 != null) ? additional1 : "";
	}
	public void setAdditional1(String additional1)
	{
		this.additional1 = (additional1 != null) ? additional1 : "";
	}
	public String getAdditional2()
	{
		return (additional2 != null) ? additional2 : "";
	}
	public void setAdditional2(String additional2)
	{
		this.additional2 = (additional2 != null) ? additional2 : "";
	}
	public String getAdditional3()
	{
		return (additional3 != null) ? additional3 : "";
	}
	public void setAdditional3(String additional3)
	{
		this.additional3 = (additional3 != null) ? additional3 : "";
	}
	public String getAdditional4()
	{
		return (additional4 != null) ? additional4 : "";
	}
	public void setAdditional4(String additional4)
	{
		this.additional4 = (additional4 != null) ? additional4 : "";
	}
}
