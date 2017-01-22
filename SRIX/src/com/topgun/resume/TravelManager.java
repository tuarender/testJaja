package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class TravelManager 
{
	public static Travel get(int idJsk,int idResume,int idTravel)
    {
		Travel result=null;
    	String SQL=	"SELECT ID_FREQUENCY FROM INTER_TRAVEL WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_TRAVEL=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idTravel);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Travel();
    			result.setIdFrequency(db.getInt("ID_FREQUENCY"));
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
    
	public static List<Travel> getAll(int idJsk,int idResume)
	{
		List<Travel> result = new ArrayList<Travel>();
		DBManager db=null;
		String SQL=	"SELECT * FROM INTER_TRAVEL WHERE (ID_JSK=?) AND (ID_RESUME=?) ORDER BY ID_TRAVEL";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Travel trav=new Travel();
		    	trav.setIdJsk(db.getInt("ID_JSK"));
		    	trav.setIdResume(db.getInt("ID_RESUME"));
		    	trav.setIdTravel(db.getInt("ID_TRAVEL"));
		    	trav.setIdFrequency(db.getInt("ID_FREQUENCY"));
		    	result.add(trav);
		    }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result;
	}
	
    public static int add(Travel trav)
    {
    	int result = 0;
    	String SQL= "INSERT INTO INTER_TRAVEL" +
    	"(" +
    	"ID_JSK," +
    	"ID_RESUME," +
    	"ID_TRAVEL," +
    	"ID_FREQUENCY " +
    	") " +
    	"VALUES(?,?,?,?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, trav.getIdJsk());
    		db.setInt(2, trav.getIdResume());
    		db.setInt(3, trav.getIdTravel());
    		db.setInt(4, trav.getIdFrequency());
    		
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
    
    public static int delete(Travel trav)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_TRAVEL WHERE " +
    	"(ID_JSK=?) AND (ID_RESUME=?) AND (ID_TRAVEL=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, trav.getIdJsk());
    		db.setInt(2, trav.getIdResume());
    		db.setInt(3, trav.getIdTravel());
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
    
    public static int deleteAllTravel(int idJsk,int idResume)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_TRAVEL WHERE " +
    	"(ID_JSK=?) AND (ID_RESUME=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
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
    
    public static int update(Travel trav)
    {
    	int result = 0;
    	String SQL=	"UPDATE INTER_TRAVEL " +
    	"SET " +
    	"ID_FREQUENCY=? " +
    	
    	"WHERE " +
    	"(ID_JSK=?) AND " +
    	"(ID_RESUME=?) AND " +
    	"(ID_TRAVEL=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, trav.getIdFrequency());
    		db.setInt(2, trav.getIdJsk());
    		db.setInt(3, trav.getIdResume());
    		db.setInt(4, trav.getIdTravel());
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
