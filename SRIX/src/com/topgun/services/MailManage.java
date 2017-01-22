

package com.topgun.services;

import java.sql.Date;
import java.sql.Timestamp;

import com.topgun.util.DBManager;

public class MailManage 
{
	private int idJsk;
    private String jmCancelStatus;
    private String juCancelStatus;
    private Timestamp jmNextSenddate;
    private Timestamp juNextSenddate;
    private Timestamp lastChkmailDate ;
    private String samkokStatus ;
    private String natureStatus ;
    private String partnerStatus ;
    private String telephone ;
    private String jmComment ;
    private String juComment ;
    private String jobtopgunStatus ;
    private String jmSpecialStatus ;
	private Timestamp timeStamp;
    private boolean exists=false;
    
	public MailManage (int idJsk)
    {
    	this.idJsk=idJsk;
    	
    	String SQL="SELECT * FROM MAIL_MANAGE WHERE (ID_JSK=?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, this.idJsk);
    		db.executeQuery();
    		if(db.next())
    		{
    			this.jmCancelStatus = db.getString("JM_CANCEL_STATUS");
    			this.juCancelStatus = db.getString("JU_CANCEL_STATUS");
    			this.jmNextSenddate = db.getTimestamp("JM_NEXT_SENDDATE");	
    			this.juNextSenddate = db.getTimestamp("JU_NEXT_SENDDATE");	
    			this.lastChkmailDate = db.getTimestamp("LAST_CHKMAIL_DATE");
    			this.samkokStatus = db.getString("SAMKOK_STATUS");	
    			this.natureStatus=db.getString("NATURE_STATUS");
    			this.partnerStatus=db.getString("PARTNER_STATUS");
    			this.telephone=db.getString("TELEPHONE");
    			this.jmComment=db.getString("JM_COMMENT");
    			this.juComment=db.getString("JU_COMMENT");
    			this.jobtopgunStatus=db.getString("JOBTOPGUN_STATUS");
    			this.jmSpecialStatus=db.getString("JM_SPECIAL_STATUS");
    			this.timeStamp=db.getTimestamp("TIMESTAMP");
    			this.exists=true;
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    }
	
	public int add()
	{
		int result = 0;
		if(!this.exists)
		{
			String SQL= "INSERT INTO MAIL_MANAGE (" +
					"ID_JSK," +
					"JM_CANCEL_STATUS," +
					"JU_CANCEL_STATUS," +
					"JM_NEXT_SENDDATE," +
					"JU_NEXT_SENDDATE," +
					"LAST_CHKMAIL_DATE," +
					"SAMKOK_STATUS," +
					"NATURE_STATUS," +
					"PARTNER_STATUS," +
					"TELEPHONE," +
					"JM_COMMENT," +
					"JU_COMMENT," +
					"JOBTOPGUN_STATUS," +
					"JM_SPECIAL_STATUS," +
					"TIMESTAMP" +
					") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, this.idJsk);
				db.setString(2, this.jmCancelStatus);
				db.setString(3, this.juCancelStatus);
				db.setTimestamp(4,this.jmNextSenddate);
				db.setTimestamp(5, this.juNextSenddate);
				db.setTimestamp(6, this.lastChkmailDate);
				db.setString(7, this.samkokStatus);
				db.setString(8, this.natureStatus);
				db.setString(9, this.partnerStatus);
				db.setString(10, this.telephone);
				db.setString(11, this.jmComment);
				db.setString(12, this.juComment);
				db.setString(13, this.jobtopgunStatus);
				db.setString(14, this.jmSpecialStatus);
				result=db.executeUpdate();
				if(result==1)
				{
					this.exists=true;
				}		    
			}
			catch(Exception e)
			{  
				e.printStackTrace();
			}
			finally
			{
				db.close();
			}	    
		}
		return result;	
	}
	
	
    public int update()
    {
    	int result = 0;
    	if(this.exists)
    	{
    		String SQL= "UPDATE MAIL_MANAGE SET JM_CANCEL_STATUS=?,JU_CANCEL_STATUS=?,JM_NEXT_SENDDATE=?,JU_NEXT_SENDDATE=?,LAST_CHKMAIL_DATE=?,SAMKOK_STATUS=?,NATURE_STATUS=?,PARTNER_STATUS=?,TELEPHONE=?,JM_COMMENT=?,JU_COMMENT=?,JOBTOPGUN_STATUS=?,JM_SPECIAL_STATUS=?,TIMESTAMP=SYSDATE WHERE (ID_JSK=?)";
    		DBManager db=null;
    		try
    		{
    			db=new DBManager();
    			db.createPreparedStatement(SQL);
    			db.setString(1, this.jmCancelStatus);
    			db.setString(2, this.juCancelStatus);
    			db.setTimestamp(3, this.jmNextSenddate);
    			db.setTimestamp(4, this.juNextSenddate);
    			db.setTimestamp(5, this.lastChkmailDate);
    			db.setString(6, this.samkokStatus);
    			db.setString(7, this.natureStatus);
    			db.setString(8, this.partnerStatus);
    			db.setString(9, this.telephone);
    			db.setString(10, this.jmComment);
    			db.setString(11, this.juComment);
    			db.setString(12, this.jobtopgunStatus);
    			db.setString(13, this.jmSpecialStatus);
    			db.setInt(14, this.idJsk);
    			result=db.executeUpdate();
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		finally
    		{
    			db.close();
    		}	    
    		
    	}
    	return result;	
    }

	
	public String getJmCancelStatus() {
		return (jmCancelStatus != null) ? jmCancelStatus : "";
	}

	public void setJmCancelStatus(String jmCancelStatus) {
		this.jmCancelStatus = (jmCancelStatus != null) ? jmCancelStatus : "";;
	}

	public String getJuCancelStatus() {
		return (juCancelStatus != null) ? juCancelStatus : "";
	}

	public void setJuCancelStatus(String juCancelStatus) {
		this.juCancelStatus = (juCancelStatus != null) ? juCancelStatus : "";
	}

	

	public String getJobtopgunStatus() {
		return (jobtopgunStatus != null) ? jobtopgunStatus : "";
	}

	public void setJobtopgunStatus(String jobtopgunStatus) {
		this.jobtopgunStatus = (jobtopgunStatus != null) ? jobtopgunStatus : "";;
	}
	
	public String getJmSpecialStatus() {
		return (jmSpecialStatus != null) ? jmSpecialStatus : "";
	}

	public void setJmSpecialStatus(String jmSpecialStatus) {
		this.jmSpecialStatus = (jmSpecialStatus != null) ? jmSpecialStatus : "";;
	}

	public String getSamkokStatus() {
		return (samkokStatus != null) ? samkokStatus : "";
	}

	public void setSamkokStatus(String samkokStatus) {
		this.samkokStatus = (samkokStatus != null) ? samkokStatus : "";
	}

	public String getNatureStatus() {
		return (natureStatus != null) ? natureStatus : "";
	}

	public void setNatureStatus(String natureStatus) {
		this.natureStatus = (natureStatus != null) ? natureStatus : "";
	}

	public String getPartnerStatus() {
		return (partnerStatus != null) ? partnerStatus : "";
	}

	public void setPartnerStatus(String partnerStatus) {
		this.partnerStatus = (partnerStatus != null) ? partnerStatus : "";
	}

	public boolean isExists() {
		return exists;
	}

	public int getIdJsk() {
		return idJsk;
	}

	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}

	

	public Timestamp getJmNextSenddate() {
		return jmNextSenddate;
	}

	public void setJmNextSenddate(Timestamp jmNextSenddate) {
		this.jmNextSenddate = jmNextSenddate;
	}

	public Timestamp getJuNextSenddate() {
		return juNextSenddate;
	}

	public void setJuNextSenddate(Timestamp juNextSenddate) {
		this.juNextSenddate = juNextSenddate;
	}

	public Timestamp getLastChkmailDate() {
		return lastChkmailDate;
	}

	public void setLastChkmailDate(Timestamp lastChkmailDate) {
		this.lastChkmailDate = lastChkmailDate;
	}

	public String getJmComment() {
		return jmComment;
	}

	public void setJmComment(String jmComment) {
		this.jmComment = jmComment;
	}

	public String getJuComment() {
		return juComment;
	}

	public void setJuComment(String juComment) {
		this.juComment = juComment;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
    
	
	
}
