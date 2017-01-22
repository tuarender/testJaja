package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class TrainingManager 
{
	public Training get(int idJsk,int idResume,int id)
    {
		Training result=null;
    	String SQL=	"SELECT " +
    				"	ID_JSK, ID_RESUME, START_DATE,END_DATE,COURSE_NAME,COURSE_DESCRIPTION,INSTITUTE,TIMESTAMP " +
    				"FROM " +
    				"	INTER_TRAINING " +
    				"WHERE " +
    	           "	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?) AND (DELETE_STATUS<>'TRUE')"; 
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, id);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Training();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setStartDate(db.getDate("START_DATE"));
    			result.setEndDate(db.getDate("END_DATE"));
    			result.setCourseName( db.getString("COURSE_NAME"));
    			result.setCourseDesc(db.getString("COURSE_DESCRIPTION"));	
    			result.setInstitute(db.getString("INSTITUTE"));
    			result.setTimeStamp(db.getDate("TIMESTAMP"));
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
	
	public List<Training> getAll(int idJsk,int idResume)
	{
		List<Training> result = new ArrayList<Training>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID,START_DATE,END_DATE,COURSE_NAME," +
					"	COURSE_DESCRIPTION,INSTITUTE,TIMESTAMP FROM INTER_TRAINING " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)AND (DELETE_STATUS<>'TRUE') " +
					"ORDER BY " +
					"	ID"; 
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Training train=new Training();
		    	train.setIdJsk(db.getInt("ID_JSK"));
		    	train.setIdResume(db.getInt("ID_RESUME"));
		    	train.setId(db.getInt("ID"));
		    	train.setStartDate(db.getDate("START_DATE"));
		    	train.setEndDate(db.getDate("END_DATE"));
		    	train.setCourseName( db.getString("COURSE_NAME"));
		    	train.setCourseDesc(db.getString("COURSE_DESCRIPTION"));	
		    	train.setInstitute(db.getString("INSTITUTE"));
		    	train.setTimeStamp(db.getDate("TIMESTAMP"));
		    	result.add(train);
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
	
	public int add(Training train)
	{
		int result = 0;
		String SQL= "INSERT INTO " +
					"	INTER_TRAINING(ID_JSK,ID_RESUME,ID,START_DATE,END_DATE,COURSE_NAME,COURSE_DESCRIPTION,INSTITUTE,DELETE_STATUS,TIMESTAMP) " +
					"	VALUES(?,?,?,?,?,?,?,?,'FALSE',SYSDATE) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, train.getIdJsk());
			db.setInt(2, train.getIdResume());
			db.setInt(3, train.getId());
			db.setDate(4,train.getStartDate());
			db.setDate(5, train.getEndDate());
			db.setString(6, train.getCourseName());
			db.setString(7, train.getCourseDesc());
			db.setString(8, train.getInstitute());
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
	
	
	public int delete(Training train)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_TRAINING WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, train.getIdJsk());
			db.setInt(2, train.getIdResume());
			db.setInt(3, train.getId());
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
	
	public int deleteAll(int idJsk, int idResume)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_TRAINING  WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
    	
    
    public int update(Training train)
    {
    	
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_TRAINING " +
    				"SET " +
    				"	START_DATE=?,END_DATE=?,COURSE_NAME=?," +
    				"	COURSE_DESCRIPTION=?,INSTITUTE=? ,TIMESTAMP=SYSDATE " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setDate(1, train.getStartDate());
    		db.setDate(2, train.getEndDate());
    		db.setString(3, train.getCourseName());
    		db.setString(4, train.getCourseDesc());
    		db.setString(5, train.getInstitute());
    		db.setInt(6, train.getIdJsk());
    		db.setInt(7, train.getIdResume());
    		db.setInt(8, train.getId());
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
    
	public int getNextTrainingId(int idJsk,int idResume)
	{
		int result=1;
		String SQL="SELECT MAX(ID) AS MAXID FROM INTER_TRAINING WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db=null;
		try{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next()){
				result=db.getInt("MAXID")+1;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
}
