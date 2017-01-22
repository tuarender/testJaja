package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class MiscellaneousManager 
{
	public Miscellaneous get(int idJsk,int idResume)
    {
		Miscellaneous result=null;
    	String SQL=	"SELECT " +
    				"	ID_JSK,ID_RESUME ,RESUME_TEMPLATE,RESUME_FONT " +
    				"FROM " +
    				"	INTER_MISCELLANEOUS " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Miscellaneous();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setResumeTemplate(db.getString("RESUME_TEMPLATE"));
    			result.setResumeFont(db.getString("RESUME_FONT"));
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
	
	public List<Miscellaneous> getAll(int idJsk, int idResume)
	{
		List<Miscellaneous> result = new ArrayList<Miscellaneous>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,RESUME_TEMPLATE,RESUME_FONT " +
					"FROM " +
					"	INTER_MISCELLANEOUS " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)  " +
					"ORDER BY " +
					"	ID_RESUME ";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Miscellaneous mis = new Miscellaneous();
		    	mis.setIdJsk(db.getInt("ID_JSK"));
		    	mis.setIdResume(db.getInt("ID_RESUME"));
		    	mis.setResumeTemplate(db.getString("RESUME_TEMPLATE"));
		    	mis.setResumeFont(db.getString("RESUME_FONT"));
		    	result.add(mis);
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
    
    public int add(Miscellaneous mis)
    {
    	int result = 0;
    	String SQL= "INSERT INTO INTER_MISCELLANEOUS(ID_JSK,ID_RESUME,RESUME_TEMPLATE,RESUME_FONT) VALUES(?,?,?,?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, mis.getIdJsk());
    		db.setInt(2, mis.getIdResume());
    		db.setString(3, mis.getResumeTemplate());
    		db.setString(4, mis.getResumeFont());
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
    
    public int delete(Miscellaneous mis)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_MISCELLANEOUS WHERE (ID_JSK=?) AND (ID_RESUME=?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, mis.getIdJsk());
    		db.setInt(2, mis.getIdResume());
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

    public int delete(int idJsk, int idResume)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_MISCELLANEOUS WHERE (ID_JSK=?) AND (ID_RESUME=?) ";
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
    
    public int update(Miscellaneous mis)
    {
    	int result = 0;
    	String SQL=	"UPDATE " +
    				"	INTER_MISCELLANEOUS " +
			    	"SET " +
			    	"	RESUME_TEMPLATE=? , RESUME_FONT=? " +
			    	"WHERE " +
			    	"	(ID_JSK=?) AND (ID_RESUME=?)  ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, mis.getResumeTemplate());
    		db.setString(2, mis.getResumeFont());
    		db.setInt(3, mis.getIdJsk());
    		db.setInt(4, mis.getIdResume());
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
