package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CareerObjectiveManager 
{
	public CareerObjective get(int idJsk,int idResume,int idCareerObjective)
    {
		CareerObjective result=null;
    	String SQL=	"SELECT " +
    				"	OTHER_OBJECTIVE " +
    				"FROM " +
    				"	INTER_CAREER_OBJECTIVE " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_CAREEROBJECTIVE=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idCareerObjective);
    		db.executeQuery();
    		if(db.next())
    		{
    			result = new CareerObjective();
    			result.setIdJsk(idJsk);
    			result.setIdResume(idResume);
    			result.setIdCareerObjective(idCareerObjective);
    			result.setOtherObjective(db.getString("OTHER_OBJECTIVE"));
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
	
	public List<CareerObjective> getAll(int idJsk, int idResume)
	{
		List<CareerObjective> result = new ArrayList<CareerObjective>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID_CAREEROBJECTIVE,OTHER_OBJECTIVE " +
					"FROM " +
					"	INTER_CAREER_OBJECTIVE " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) " +
					"ORDER BY " +
					"	ID_CAREEROBJECTIVE";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	CareerObjective careerObj=new CareerObjective();
		    	careerObj.setIdJsk(db.getInt("ID_JSK"));
		    	careerObj.setIdResume(db.getInt("ID_RESUME"));
		    	careerObj.setIdCareerObjective(db.getInt("ID_CAREEROBJECTIVE"));
		    	careerObj.setOtherObjective(db.getString("OTHER_OBJECTIVE"));
		    	result.add(careerObj);
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
    
    public int add(CareerObjective career)
    {
    	int result = 0;
    	String SQL= "INSERT INTO INTER_CAREER_OBJECTIVE(ID_JSK,ID_RESUME,ID_CAREEROBJECTIVE,OTHER_OBJECTIVE) VALUES(?,?,?,?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, career.getIdJsk());
    		db.setInt(2, career.getIdResume());
    		db.setInt(3, career.getIdCareerObjective());
    		db.setString(4, career.getOtherObjective());
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
    
    public int delete(CareerObjective career)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_CAREER_OBJECTIVE WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_CAREER_OBJECTIVE=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, career.getIdJsk());
    		db.setInt(2, career.getIdResume());
    		db.setInt(3, career.getIdCareerObjective());
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
    	String SQL=	"DELETE FROM INTER_CAREER_OBJECTIVE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
    
    public int update(CareerObjective career)
    {
    	int result = 0;
    	String SQL=	"UPDATE " +
    				"	CAREER_OBJECTIVE " +
    				"SET " +
    				"	OTHER_OBJECTIVE=?," +
    				"WHERE " +
    				"	(ID_JSK=?) AND " +
    				"	(ID_RESUME=?) AND " +
    				"	(ID_CAREEROBJECTIVE=?)" ;
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, career.getOtherObjective());
    		db.setInt(2, career.getIdJsk());
    		db.setInt(3, career.getIdResume());
    		db.setInt(4, career.getIdCareerObjective());
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
