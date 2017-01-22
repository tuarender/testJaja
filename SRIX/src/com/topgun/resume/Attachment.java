package com.topgun.resume;

import java.sql.Timestamp;

public class Attachment 
{
	private int  idJsk;
	private int idFile;
	private String fileTitle;
	private String fileType;
	private String fileFormat;
	private long fileSize;
	private Timestamp fileDate;
	private Timestamp timeStamp;
	
	public int getIdJsk() 
	{
		return idJsk;
	}
	public void setIdJsk(int idJsk) 
	{
		this.idJsk = idJsk;
	}
	public int getIdFile() 
	{
		return idFile;
	}
	public void setIdFile(int idFile) 
	{
		this.idFile = idFile;
	}
	public String getFileTitle()
	{
		return (fileTitle != null) ? fileTitle : "";
	}
	public void setFileTitle(String fileTitle)
	{
		this.fileTitle = (fileTitle != null) ? fileTitle : "";
	}
	public String getFileType()
	{
		return (fileType != null) ? fileType : "";
	}
	public void setFileType(String fileType)
	{
		this.fileType = (fileType != null) ? fileType : "";
	}
	public String getFileFormat()
	{
		return (fileFormat != null) ? fileFormat : "";
	}
	public void setFileFormat(String fileFormat)
	{
		this.fileFormat = (fileFormat != null) ? fileFormat : "";
	}
	public long getFileSize() 
	{
		return fileSize;
	}
	public void setFileSize(long fileSize)
	{
		this.fileSize = fileSize;
	}
	public Timestamp getFileDate() 
	{
		return fileDate;
	}
	public void setFileDate(Timestamp fileDate) 
	{
		this.fileDate = fileDate;
	}
	public Timestamp getTimeStamp() 
	{
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "Attachment [idJsk=" + idJsk + ", idFile=" + idFile
				+ ", fileTitle=" + fileTitle + ", fileType=" + fileType
				+ ", fileFormat=" + fileFormat + ", fileSize=" + fileSize
				+ ", fileDate=" + fileDate + ", timeStamp=" + timeStamp + "]";
	}
}
