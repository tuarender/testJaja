package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.topgun.util.DBManager;

public class ForgotManager 
{
	public List<Forgot> getAllAccount(String mobile)
	{
		String eMail;
		List<Forgot> result=null;
		int position=0;
		String neweMail=null;
		DBManager db=null;
		String sql=	"SELECT DISTINCT(a.ID_JSK), a.USERNAME, b.ID_JSK, b.PRIMARY_PHONE "
				+ "FROM INTER_JOBSEEKER a, INTER_RESUME b "
				+ "WHERE B.PRIMARY_PHONE=? AND a.ID_JSK = b.ID_JSK "
				+ "ORDER BY a.ID_JSK";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, mobile);
			db.executeQuery();
			while(db.next())
			{
				if(result==null)
				{
					result=new ArrayList<Forgot>();
				}
				Forgot otp=new Forgot();
				eMail= db.getString("USERNAME");
				position= eMail.indexOf("@");
				neweMail= '*'+eMail.substring(1, (position-3))+"***"+eMail.substring((position));
				otp.setId_Jsk(db.getInt("ID_JSK"));
				otp.setUsername(neweMail);
				result.add(otp);
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
	
	public static Forgot getAccountByMobile(String mobile)
	{
		Forgot result=null;
		DBManager db=null;
		String sql=	"SELECT a.ID_JSK, a.USERNAME, b.ID_JSK, b.PRIMARY_PHONE "
				+ "FROM INTER_JOBSEEKER a, INTER_RESUME b "
				+ "WHERE B.PRIMARY_PHONE=? AND a.ID_JSK = b.ID_JSK AND b.ID_RESUME = 0 "
				+ "ORDER BY a.ID_JSK";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, mobile);
			db.executeQuery();
			if(db.next())
			{
				result = new Forgot() ;
				int position=0;
				String email = "" ;
				String newEmail = "" ;
				email = db.getString("USERNAME");
				
				position= email.indexOf("@");
				newEmail= '*'+email.substring(1, (position-3))+"***"+email.substring((position));
				result.setId_Jsk(db.getInt("ID_JSK"));
				result.setUsername(newEmail);
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
	
	public static String getUsernameByIdJsk(int idJsk)
	{
		String result="";
		DBManager db=null;
		
		String sql= "select USERNAME from inter_jobseeker where ID_JSK=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result= db.getString("USERNAME");
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
	
	public static String getMobileByIdJsk(int idJsk)
	{
		String result="";
		DBManager db=null;
		String sql= "SELECT PRIMARY_PHONE FROM INTER_RESUME WHERE ID_JSK= ? AND ID_RESUME = 0 ";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result= db.getString("PRIMARY_PHONE");
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
	
	public static int tatalSMS(String telephone)
	{
		int result=0;
		DBManager db=null;
		
		String sql= "SELECT count(CODE) AS TOTAL FROM INTER_OTP WHERE MOBILE=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, telephone);
			db.executeQuery();
			if(db.next())
			{
				result= db.getInt("TOTAL");
			}
			else
			{
				result=0;
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
	
	public int isTelephoneExist(String telephone) {
        int result =  0;
        DBManager db = null;
        String sql = "SELECT COUNT(DISTINCT(ID_JSK)) AS TOTAL FROM INTER_RESUME WHERE ((PRIMARY_PHONE=? AND (PRIMARY_PHONE LIKE '08%' OR PRIMARY_PHONE LIKE '09%')) OR (SECONDARY_PHONE=? AND (SECONDARY_PHONE LIKE '08%' OR SECONDARY_PHONE LIKE '09%')) )    AND ID_RESUME=0 AND DELETE_STATUS<>'TRUE'";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setString(1, telephone);
            db.setString(2, telephone);
            db.executeQuery();
            if (db.next()) {
                result = db.getInt("TOTAL");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
    return result;
    }

    public int getIdJskByTelephone(String telephone) {
        int result = 0;
        DBManager db = null;
        String sql = "SELECT ID_JSK FROM INTER_RESUME WHERE ((PRIMARY_PHONE=? AND (PRIMARY_PHONE LIKE '08%' OR PRIMARY_PHONE LIKE '09%')) OR (SECONDARY_PHONE=? AND (SECONDARY_PHONE LIKE '08%' OR SECONDARY_PHONE LIKE '09%')) )    AND ID_RESUME=0 AND DELETE_STATUS<>'TRUE'";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setString(1, telephone);
            db.setString(2, telephone);
            db.executeQuery();
            if (db.next()) {
                result = db.getInt("ID_JSK");
            }
            result = 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }

    public int isUsernameExist(String Username) {
        int result = 0;
        DBManager db = null;
        String sql = "SELECT ID_JSK,USERNAME FROM INTER_JOBSEEKER WHERE DELETE_STATUS<>'TRUE' AND LOWER(USERNAME)=?";
        try {
        db = new DBManager();
        db.createPreparedStatement(sql);
        db.setString(1, Username);
        db.executeQuery();
        if (db.next()) {
            result = 1;
        }
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }

    public String getTagValue(String sTag, Element eElement) {
        String result = "";
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        if (nValue != null) {
            result = nValue.getNodeValue();
        }
        return result;
    }

    public String getPrimaryNumber(String username) {
    	String result = "";
    	String sql = "";
    	DBManager db = new DBManager();
    	try
    	{
    		sql = "SELECT B.PRIMARY_PHONE FROM "
    				+ "(SELECT ID_JSK FROM INTER_JOBSEEKER WHERE USERNAME LIKE ?) A ,"
    				+ "INTER_RESUME B "
    				+ "WHERE A.ID_JSK = B.ID_JSK AND B.ID_RESUME = 0 ";
    		db = new DBManager() ;
    		db.createPreparedStatement(sql);
    		db.setString(1, username);
    		db.executeQuery();
    		if(db.next())
    		{
    			result = db.getString("PRIMARY_PHONE");
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally {
			db.close();
		}
    	return result ;
       
    }


    public int totalSMS(String telephone) {
        int result;
        result = 0;
        DBManager db = null;
        String sql = "SELECT count(CODE) AS TOTAL FROM INTER_OTP WHERE MOBILE=? AND TO_CHAR (TIMESTAMP, 'YYYYMMDD')= TO_CHAR (SYSDATE, 'YYYYMMDD')";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setString(1, telephone);
            db.executeQuery();
            if (db.next()) {
                result = db.getInt("TOTAL");
            }
            result = 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }

    public int getIdJskByEmail(String username) {
        int result;
        result = -1;
        DBManager db = null;
        String sql = "SELECT ID_JSK FROM INTER_JOBSEEKER WHERE DELETE_STATUS<>'TRUE' AND LOWER(USERNAME)=?";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setString(1, username);
            db.executeQuery();
            if (db.next()) {
                result = db.getInt("ID_JSK");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }

    public int insertEmailLog(int idJsk, String email) {
        int result;
        result = 0;
        DBManager db = null;
        String sql = "insert into INTER_EMAIL_FORGOT(ID_JSK, USERNAME) values (?, ?)";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setInt(1, idJsk);
            db.setString(2, email);
            result = db.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }
    
    public List<Resume> getAllResumeByIdJsk(int idJsk)
    {
    	 List<Resume> result = new ArrayList<Resume>();
    	 String sql = "";
    	 DBManager db = null ;
    	 try
    	 {
    		 sql = "SELECT * FROM INTER_RESUME WHERE ID_JSK = ? AND DELETE_STATUS != 'TRUE'";
    		 db = new DBManager();
    		 db.createPreparedStatement(sql);
    		 db.setInt(1, idJsk);
    		 db.executeQuery();
    		 while(db.next())
    		 {
    			 Resume resume = new Resume();
    			 resume.setIdResume(db.getInt("ID_RESUME"));
    			 resume.setResumeName(db.getString("RESUME_NAME"));
    			 resume.setFirstName(db.getString("FIRST_NAME"));
    			 result.add(resume);
    		 }
    	 }
    	 catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
    	 finally {
			db.close();
		}
    	 return result ;
    }
    
    //get idJsk ขึ้นมาจากอีเมลล์ จาก INTER_JOBSEEKER Table
    public int getIdJskFromEmailInInterJobSeeker(String email){
    	int result = 0;
    	DBManager db = null;
    	String sql = "";
    	try {
    		sql = "SELECT ID_JSK FROM INTER_JOBSEEKER WHERE USERNAME = ?";
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setString(1, email);
    		db.executeQuery();
    		if(db.next()){
    			result = db.getInt("ID_JSK");
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    	} finally {
    		db.close();
    	}
    	return result;
    }
    
    //Count หาจำนวน idJsk  จากเบอร์โทรศัพท์ของ resume ที่เบอร์ 0
    public int getResumeFromZeroNumber(String primaryPhone){
    	int result = 0;
    	DBManager db = null;
    	String sql = "";
    	try {
    		sql = "SELECT COUNT(i.ID_JSK) AS TOTAL FROM INTER_JOBSEEKER i , INTER_RESUME i1 "
    				+ " WHERE i.ID_JSK = i1.ID_JSK"
    				+ " AND (REPLACE(i1.PRIMARY_PHONE,'-','') = ?)"
    				+ " AND i1.ID_RESUME = '0'";
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setString(1, primaryPhone.replace("-",""));
    		db.executeQuery();
    		if(db.next()){
    			result = db.getInt("TOTAL");
    		}else {
    			result = 0;
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    		db.close();
    	} finally {
    		db.close();
    	}
    	return result;
    }
    
    //จำนวน idJsk  จากเบอร์โทรศัพท์ของ resume ที่เบอร์ 0
    public List<Jobseeker> getListJskFromZeroNumber(String primaryPhone){
    	List<Jobseeker> result = new ArrayList<Jobseeker>();
    	DBManager db = null;
    	String sql = "";
    	try {
    		sql = "SELECT i.ID_JSK, i.USERNAME FROM INTER_JOBSEEKER i , INTER_RESUME i1 "
    				+ " WHERE i.ID_JSK = i1.ID_JSK"
    				+ " AND (REPLACE(i1.PRIMARY_PHONE,'-','') = ?)"
    				+ " AND i1.ID_RESUME = '0'";
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setString(1, primaryPhone.replace("-",""));
    		db.executeQuery();
    		while(db.next()){
    			Jobseeker jsk = new Jobseeker();
    			jsk.setIdJsk(db.getInt("ID_JSK"));
    			jsk.setUsername(db.getString("USERNAME"));
   			 	result.add(jsk);
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    		db.close();
    	} finally {
    		db.close();
    	}
    	return result;
    }
    
    //Query List Email From idJsk
    public List<Jobseeker> getEmailFromIdJsk(int idJsk){
    	List<Jobseeker> result = new ArrayList<Jobseeker>();
    	DBManager db = null;
    	String sql = "";
    	try {
    		sql = "SELECT USERNAME,ID_JSK FROM INTER_JOBSEEKER WHERE ID_JSK = ?";
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setInt(1, idJsk);
    		db.executeQuery();
    		if(db.next()){
    			Jobseeker jsk = new Jobseeker();
    			jsk.setIdJsk(db.getInt("ID_JSK"));
    			jsk.setUsername(db.getString("USERNAME"));
   			 	result.add(jsk);
    		} 
    	}catch(Exception e) {
    		e.printStackTrace();
    		db.close();
    	} finally {
    		db.close();
    	}
    	return result; 
    }
    
    //หา idJsk, Email ของ username = ? || primaryPhone ของ Resume  เบอร์  0 = ? 
    public List<Jobseeker> getIdJskandEmailFromUserNameOrPrimaryPhone(String username, String primaryPhone){
    	List<Jobseeker> result = new ArrayList<Jobseeker>();
    	DBManager db = null;
    	String sql = "";
    	try {
    		sql = "SELECT i.ID_JSK, i.USERNAME FROM INTER_JOBSEEKER i , INTER_RESUME i1 "
    				+ " WHERE i.ID_JSK = i1.ID_JSK"
    				+ " AND (i.USERNAME = ? OR i1.PRIMARY_PHONE = ?)"
    				+ " AND i1.ID_RESUME = '0'";
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setString(1, username);
    		db.setString(2, primaryPhone);
    		db.executeQuery();
    		if(db.next()){
    			Jobseeker jsk = new Jobseeker();
    			jsk.setIdJsk(db.getInt("ID_JSK"));
    			jsk.setUsername(db.getString("USERNAME"));
   			 	result.add(jsk);
    		} 
    	}catch(Exception e) {
    		e.printStackTrace();
    		db.close();
    	} finally {
    		db.close();
    	}
    	return result; 
    }

}
