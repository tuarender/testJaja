package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;


import com.topgun.util.DBManager;

public class ReadingManager 
{
	//ID_JSK, ID_RESUME, ID_BOOK, OTHER_BOOK
	public Reading get(int idJsk,int idResume,int idBook)
    {
		Reading result=null;
    	String SQL=	"SELECT * FROM INTER_BOOK WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_BOOK=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idBook);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Reading();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setIdBook(db.getInt("ID_BOOK"));
    			result.setOtherBook(db.getString("OTHER_BOOK"));
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
    
	public List<Reading> getAll(int idJsk,int idResume)
	{
		List<Reading> result = new ArrayList<Reading>();
		DBManager db=null;
		String SQL=	"SELECT * FROM INTER_BOOK WHERE (ID_JSK=?) AND (ID_RESUME=?) ORDER BY ID_BOOK";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Reading read=new Reading();
		    	read.setIdJsk(db.getInt("ID_JSK"));
		    	read.setIdResume(db.getInt("ID_RESUME"));
		    	read.setIdBook(db.getInt("ID_BOOK"));
		    	read.setOtherBook(db.getString("OTHER_BOOK"));
		    	result.add(read);
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
	
    public int add(Reading read)
    {
    	int result = 0;
    	String SQL= "INSERT INTO INTER_BOOK" +
    	"(" +
    	"ID_JSK," +
    	"ID_RESUME," +
    	"ID_BOOK," +
    	"OTHER_BOOK " +
    	") " +
    	"VALUES(?,?,?,?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, read.getIdJsk());
    		db.setInt(2, read.getIdResume());
    		db.setInt(3, read.getIdBook());
    		db.setString(4, read.getOtherBook());
    		
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
    
    public int delete(Reading read)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_BOOK WHERE " +
    	"(ID_JSK=?) AND (ID_RESUME=?) AND (ID_BOOK=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, read.getIdJsk());
    		db.setInt(2, read.getIdResume());
    		db.setInt(3, read.getIdBook());
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
    
    public int deleteAllBook(int idJsk, int idResume)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_BOOK WHERE " +
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
}
