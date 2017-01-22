package com.topgun.resume;


import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class JobseekerManager 
{	
	/*
	 * @Author : Anuwat Palasak 
	 * @Modified : 8/7/2010 
	 * Temporary usage this method if remove password field from INTER_JOBSEEKER don't use its.
	 */
	public int add(Jobseeker jsk,String password)
	{
		int result=0;
		String sql=	"INSERT INTO " +
					"	INTER_JOBSEEKER" +
					"	(" +
					"		ID_JSK, USERNAME, PASSWORD, REGISTER_DATE, TIMESTAMP, " +
					"		ID_COUNTRY_REGISTER, LAST_LOGIN, JOBUPDATE_STATUS, " +
					"		JOBMATCH_STATUS, AUTHEN" +
					"	) " +
					"VALUES(?,?,?,SYSDATE,SYSDATE,?,SYSDATE,?,?,?)";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);	
				db.setInt(1, jsk.getIdJsk());
				db.setString(2, jsk.getUsername().toLowerCase());
				db.setString(3, password);
				db.setInt(4, jsk.getIdCountryRegister());
				db.setString(5,jsk.getJobUpdateStatus());
				db.setString(6,jsk.getJobMatchStatus());
				db.setString(7,jsk.getAuthen());
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
		//}
		return result;
	}
	
	/*
	 * @Author : Anuwat Palasak 
	 * @Modified : 8/7/2010 
	 * If remove password field from INTER_JOBSEEKER use this method replace old method.
	 */
	public int add(Jobseeker jsk)
	{
		int result=0;
		String sql=	"INSERT INTO " +
					"INTER_JOBSEEKER" +
					"	(" +
					"		ID_JSK, USERNAME, REGISTER_DATE, TIMESTAMP, ID_COUNTRY_REGISTER, " +
					"		LAST_LOGIN, JOBUPDATE_STATUS, JOBMATCH_STATUS, AUTHEN" +
					"	) " +
					"VALUES(?,?,?,SYSDATE,SYSDATE,?,SYSDATE,?,?,?)";
			
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);	
				db.setInt(1, jsk.getIdJsk());
				db.setString(2, jsk.getUsername().toLowerCase());
				db.setInt(3, jsk.getIdCountryRegister());
				db.setString(4,jsk.getJobUpdateStatus());
				db.setString(5,jsk.getJobMatchStatus());
				db.setString(6,jsk.getAuthen());
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
		return result;
	}	
	
	/*
	 * @Author : Anuwat Palasak 
	 * @Modified : 8/7/2010 
	 * Temporary usage this method if remove password field from INTER_JOBSEEKER don't use its.
	 */
	public int update(Jobseeker jsk,String password)
	{
		int result=0;
		if(jsk!=null && jsk.getIdJsk()>0)
		{
			String sql=	"UPDATE INTER_JOBSEEKER SET USERNAME=?, TIMESTAMP=SYSDATE, JOBUPDATE_STATUS=?, JOBMATCH_STATUS=?, AUTHEN=?,PASSWORD=? WHERE ID_JSK=?";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setString(1, jsk.getUsername().toLowerCase());
				db.setString(2, jsk.getJobUpdateStatus());
				db.setString(3, jsk.getJobMatchStatus());
				db.setString(4, jsk.getAuthen());
				db.setString(5, password);
				db.setInt(6, jsk.getIdJsk());
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
	
	public int updateOtherMailStatus(Jobseeker jsk)
	{
		int result=0;
		if(jsk.getIdJsk()>0)
		{
			String sql=	"UPDATE "
					+ "		INTER_JOBSEEKER"
					+ " SET "
					+ "		USERNAME=?, TIMESTAMP=SYSDATE, JOBUPDATE_STATUS=?, JOBMATCH_STATUS=?, AUTHEN=?,MAIL_OTHER_STATUS=? WHERE ID_JSK=?";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setString(1, jsk.getUsername().toLowerCase());
				db.setString(2, jsk.getJobUpdateStatus());
				db.setString(3, jsk.getJobMatchStatus());
				db.setString(4, jsk.getAuthen());
				db.setString(5, jsk.getMailOtherStatus());
				db.setInt(6, jsk.getIdJsk());
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
	/*
	 * @Author : Anuwat Palasak 
	 * @Modified : 8/7/2010 
	 * If remove password field from INTER_JOBSEEKER use this method replace old method.
	 */	
	public  int update(Jobseeker jsk)
	{
		int result=0;
		if(jsk!=null && jsk.getIdJsk()>0)
		{
			String sql=	"UPDATE INTER_JOBSEEKER SET USERNAME=?, TIMESTAMP=SYSDATE, JOBUPDATE_STATUS=?, JOBMATCH_STATUS=?, AUTHEN=? ,MAIL_OTHER_STATUS=? WHERE ID_JSK=?";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setString(1, jsk.getUsername().toLowerCase());
				db.setString(2, jsk.getJobUpdateStatus());
				db.setString(3, jsk.getJobMatchStatus());
				db.setString(4, jsk.getAuthen());
				db.setString(5, jsk.getMailOtherStatus());
				db.setInt(6, jsk.getIdJsk());
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
	
	public int delete(int idJsk)
	{
		int result=0;
		String sql="UPDATE INTER_JOBSEEKER SET DELETE_STATUS='TRUE',TIMESTAMP=SYSDATE WHERE ID_JSK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
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
		return result;
	}
	
	public Jobseeker get(int idJsk)
	{
		Jobseeker result=null;
		String sql=	"SELECT " +
					"	ID_JSK,PASSWORD, USERNAME, REGISTER_DATE, TIMESTAMP, ID_COUNTRY_REGISTER, " +
					"	LAST_LOGIN, JOBUPDATE_STATUS, JOBMATCH_STATUS, AUTHEN, MAIL_OTHER_STATUS " +
					"FROM " +
					"	INTER_JOBSEEKER " +
					"WHERE " +
					"	(ID_JSK=?) AND (DELETE_STATUS <>'TRUE')";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=new Jobseeker();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setUsername(db.getString("USERNAME"));
				result.setRegisterDate(db.getTimestamp("REGISTER_DATE"));
				result.setTimeStamp(db.getDate("TIMESTAMP"));
				result.setIdCountryRegister(db.getInt("ID_COUNTRY_REGISTER"));
				result.setLastLogin(db.getTimestamp("LAST_LOGIN"));
				result.setJobUpdateStatus(db.getString("JOBUPDATE_STATUS"));
				result.setJobMatchStatus(db.getString("JOBMATCH_STATUS"));
				result.setAuthen(db.getString("AUTHEN"));
				result.setPassword(db.getString("PASSWORD"));
				result.setMailOtherStatus(db.getString("MAIL_OTHER_STATUS"));
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
		
		return result;
	}
	
	public Jobseeker get(String username,String authen)
	{
		Jobseeker result=null;
		String sql=	"SELECT " +
					"	ID_JSK, USERNAME, REGISTER_DATE, TIMESTAMP, ID_COUNTRY_REGISTER, " +
					"	LAST_LOGIN, JOBUPDATE_STATUS, JOBMATCH_STATUS, AUTHEN " +
					"FROM " +
					"	INTER_JOBSEEKER " +
					"WHERE " +
					"	(LOWER(USERNAME)=?) AND (AUTHEN=?) AND (DELETE_STATUS <>'TRUE')";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, username.trim().toLowerCase());
			db.setString(2, authen.trim());
			db.executeQuery();
			if(db.next())
			{
				result=new Jobseeker();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setUsername(db.getString("USERNAME"));
				result.setRegisterDate(db.getTimestamp("REGISTER_DATE"));
				result.setTimeStamp(db.getDate("TIMESTAMP"));
				result.setIdCountryRegister(db.getInt("ID_COUNTRY_REGISTER"));
				result.setLastLogin(db.getTimestamp("LAST_LOGIN"));
				result.setJobUpdateStatus(db.getString("JOBUPDATE_STATUS"));
				result.setJobMatchStatus(db.getString("JOBMATCH_STATUS"));
				result.setAuthen(db.getString("AUTHEN"));
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
		return result;
	}

	public Jobseeker get(String username)
	{
		Jobseeker result=null;
		String sql=	"SELECT " +
					"	ID_JSK, USERNAME, REGISTER_DATE, TIMESTAMP, ID_COUNTRY_REGISTER, " +
					"	LAST_LOGIN, JOBUPDATE_STATUS, JOBMATCH_STATUS, AUTHEN " +
					"FROM " +
					"	INTER_JOBSEEKER " +
					"WHERE " +
					"	(LOWER(USERNAME)=?) AND (DELETE_STATUS <>'TRUE')";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, username.trim().toLowerCase());
			db.executeQuery();
			if(db.next())
			{
				result=new Jobseeker();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setUsername(db.getString("USERNAME"));
				result.setRegisterDate(db.getTimestamp("REGISTER_DATE"));
				result.setTimeStamp(db.getDate("TIMESTAMP"));
				result.setIdCountryRegister(db.getInt("ID_COUNTRY_REGISTER"));
				result.setLastLogin(db.getTimestamp("LAST_LOGIN"));
				result.setJobUpdateStatus(db.getString("JOBUPDATE_STATUS"));
				result.setJobMatchStatus(db.getString("JOBMATCH_STATUS"));
				result.setAuthen(db.getString("AUTHEN"));
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
		return result;
	}
	
	public Jobseeker getFromPrimaryPhone(int idJsk){
    	Jobseeker result = null ;
    	DBManager db = null;
    	String sql = "";
    	try {
    		sql = "SELECT USERNAME,ID_JSK FROM INTER_JOBSEEKER WHERE ID_JSK = ?";
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setInt(1, idJsk);
    		db.executeQuery();
    		if(db.next()){
    			result = new Jobseeker();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setUsername(db.getString("USERNAME"));
    		} 
    	}catch(Exception e) {
    		e.printStackTrace();
    		db.close();
    	} finally {
    		db.close();
    	}
    	return result; 
    }
	
	
	public boolean isExist(String username)
	{
		boolean result=false;
		String sql="SELECT ID_JSK FROM INTER_JOBSEEKER WHERE (LOWER(USERNAME)=?)";
		username=username!=null?username.trim():"";
		if(!username.equals(""))
		{
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setString(1, username.toLowerCase());
				db.executeQuery();
				if(db.next())
				{
					result=true;
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
	
	public boolean isExist(int idJsk)
	{
		boolean result=false;
		String sql="SELECT ID_JSK FROM INTER_JOBSEEKER WHERE ID_JSK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
		return result;
	}	
	
	public int getMaxIdJsk()
	{
		int result=0;
		String sql="SELECT MAX(ID_JSK) AS MAXID FROM INTER_JOBSEEKER";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("MAXID");
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
		return result;
	}
	
	public int updateLastLogin(int idJsk)
	{
		int result=0;
		String sql=	"UPDATE INTER_JOBSEEKER SET LAST_LOGIN=SYSDATE,TIMESTAMP=SYSDATE WHERE ID_JSK=?";			
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
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
		return result;
	}
	
	public int updateJobmatchStatusTrue(int idJsk)
	{
		int result=0;
		String sql=	"UPDATE INTER_JOBSEEKER SET JOBMATCH_STATUS='TRUE' WHERE ID_JSK=?";			
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
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
		return result;
	}
	
	public int updateJobmatchStatus(int idJsk,String jmStatus)
	{
		int result=0;
		String sql=	"UPDATE INTER_JOBSEEKER SET JOBMATCH_STATUS=? WHERE ID_JSK=?";			
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, jmStatus);
			db.setInt(2, idJsk);
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
		return result;
	}
	
	public int updateJobupdateStatusAll(int idJsk)
	{
		int result=0;
		String sql=	"UPDATE INTER_JOBSEEKER SET JOBUPDATE_STATUS='ALL' WHERE ID_JSK=?";			
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
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
		return result;
	}
	
	public int setJobUpdateStatus(int idJsk,String status)
	{
		int result=0;
		String sql=	"UPDATE INTER_JOBSEEKER SET JOBUPDATE_STATUS=? WHERE ID_JSK=?";			
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, status.toUpperCase());
			db.setInt(2, idJsk);
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
		return result;
	}
	
	public String getJobUpdateStatus(int idJsk)
	{
		String result="";
		String sql=	"SELECT JOBUPDATE_STATUS FROM INTER_JOBSEEKER WHERE ID_JSK=?";			
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getString("JOBUPDATE_STATUS");
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
		return result;
	}	
	
	public int clearLogin(int idJsk , String idSession)
	{
		int result=0;
		String sql="UPDATE INTER_LOGIN SET ACTIVE=0 WHERE ID_JSK=? OR ID_SESSION=?";
		DBManager db = null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.setString(2,idSession);
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
		return result;
	}
	
	public static int clearLogin(int idJsk)
	{
		int result=0;
		String sql="DELETE FROM INTER_LOGIN WHERE ID_JSK=? ";
		DBManager db = null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
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
		return result;
	}
	
	public int addLogin(int idJsk, String idSession)
	{
		int result=0;
		clearLogin(idJsk);
		String sql="INSERT INTO INTER_LOGIN(ID_JSK,ID_SESSION,ACTIVE) VALUES(?,?,1)";
		DBManager db = null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.setString(2,idSession);
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
		return result;
	}

	public int addLogin(int idJsk, String idSession,String device, String agent)
	{
		int result=0;
		clearLogin(idJsk,idSession);
		String sql="INSERT INTO INTER_LOGIN(ID_JSK,ID_SESSION,ACTIVE,DEVICE,AGENT) VALUES(?,?,1,?,?)";
		DBManager db = null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.setString(2,idSession);
			db.setString(3,device);
			db.setString(4, agent);
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
		return result;
	}
	
	public int updateLastRegisterView(int idJsk, String idSession, String lastView)
	{
		int result=0;
		String sql="UPDATE INTER_LOGIN SET LAST_VIEW=?,LAST_ACTIVE=SYSDATE WHERE ID_JSK=? AND ID_SESSION=?";
		DBManager db = null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1,lastView);
			db.setInt(2,idJsk);
			db.setString(3,idSession);
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
		return result;		
	}
	
	public boolean isLogin(int idJsk, String idSession)
	{
		boolean result=false;
		String sql="SELECT ID_JSK FROM INTER_LOGIN WHERE ID_JSK=? AND ID_SESSION=? AND ACTIVE=1";
		DBManager db = null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.setString(2,idSession);
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
		return result;
	}
	
	public boolean updateGoldStatus(int idJsk)
	{
		boolean result = false ;
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "UPDATE INTER_JOBSEEKER SET REGISTER_TYPE = 0 WHERE ID_JSK = ?";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeUpdate();
			result = true; 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result ;
	}
	
	public int isExistsAppsRegister(int idJsk)
	{
		 int result = 0;
		 DBManager db = null ;
		 String sql = "";
		 try
		 {
			 db = new DBManager();
			 sql = "SELECT * FROM APPS_REGISTER WHERE ID_JSK = ?";
			 db.createPreparedStatement(sql);
			 db.setInt(1, idJsk);
			 db.executeQuery();
			 if(db.next())
			 {
				 result = 1 ; 
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
		 return result ;
	}
	
	public int addAppsRegister(int idJsk, String device)
	{
		String sql = "";
		DBManager db = null ;
		int result = 0 ;
		try
		{
			sql = "INSERT INTO APPS_REGISTER(ID_JSK,DEVICE) VALUES(?,?)";
			db = new DBManager() ;
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setString(2, device);
			result = db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result ;
	}
	
	
}
