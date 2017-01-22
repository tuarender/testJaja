package com.topgun.search;
import java.sql.Date;

public class WishList
{
	private int idEmp;
	private int idPosition;
	private int idJsk;
	private Date timettamp;
	private int read;
	
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
	public int getIdJsk()
	{
		return idJsk;
	}
	public void setIdJsk(int idJsk)
	{
		this.idJsk = idJsk;
	}
	public Date getTimettamp()
	{
		return timettamp;
	}
	public void setTimettamp(Date timettamp)
	{
		this.timettamp = timettamp;
	}
	public int getRead()
	{
		return read;
	}
	public void setRead(int read)
	{
		this.read = read;
	}
}
