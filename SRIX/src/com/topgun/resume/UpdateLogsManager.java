package com.topgun.resume;

import com.topgun.util.DBManager;

public class UpdateLogsManager 
{
	public static int countUpdate(int idJsk)
	{
		int result=0;
		String SQL=	"SELECT COUNT(*) AS TOTAL FROM INTER_UPDATE_LOGS WHERE ID_JSK=?";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=db.getInt("TOTAL");;
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
	
	public static int add(int idJsk)
	{
		int result = 0;
    	String SQL= "	INSERT INTO "
	    			+ "		INTER_UPDATE_LOGS ( ID_JSK )  "
	    			+ "	VALUES (?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
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
	
	public static int getDiffDateLastRemind(int idJsk)
	{
		int result=-1;
		String SQL=" SELECT "
				+ "		SYSDATE - TIMESTAMP AS DIFF_DATE "
				+ "	FROM "
				+ "		INTER_UPDATE_LOGS "
				+ "	WHERE "
				+ "		ID_JSK=? "
				+ "	ORDER BY "
				+ "		TIMESTAMP DESC";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("DIFF_DATE");
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
}
