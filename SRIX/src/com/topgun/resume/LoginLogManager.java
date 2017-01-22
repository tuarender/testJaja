package com.topgun.resume;

import com.topgun.util.DBManager;

public class LoginLogManager
{
	public int add(LoginLog log)
	{
		int result=0;
		String sql="INSERT INTO INTER_LOGIN_LOG(ID_JSK, USERNAME, PASSWORD, RESULT, REASON, HEADERS, IP, TIMESTAMPS,DEVICE,AGENT) VALUES(?,?,?,?,?,?,?,SYSDATE,?,?)";
		DBManager db=null;
		try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(sql);
    		db.setInt(1,log.getIdJsk());
    		db.setString(2,log.getUsername());
    		db.setString(3,log.getPassword());
    		db.setString(4,log.getResult());
    		db.setString(5,log.getReason());
    		db.setString(6,log.getInfo());
    		db.setString(7,log.getIp());
    		db.setString(8, log.getDevice());
    		db.setString(9, log.getAgent());
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
	
	public int add(String username, String password)
	{
		int result=0;
		String sql="INSERT INTO INTER_LOGIN_LOG(USERNAME, PASSWORD, TIMESTAMPS) VALUES(?,?,SYSDATE)";
		DBManager db=null;
		try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(sql);
    		db.setString(1,username);
    		db.setString(2,password);
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
}
