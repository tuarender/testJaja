package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class AwardManager 
{
	public Award get(int idJsk,int idResume,int id)
    {
    	Award result =  null;
    	String SQL=	"SELECT " +
    				"	ID,ID_JSK,ID_RESUME,AWARD_DATE,AWARD_NAME,DETAIL,INSTITUTE,TIMESTAMP " +
    				"FROM " +
    				"	INTER_AWARD " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?) AND DELETE_STATUS<>'TRUE'";
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
    			result =  new Award();
    			result.setAwardDate(db.getDate("AWARD_DATE"));
    			result.setAwardName(db.getString("AWARD_NAME"));
    			result.setDetail(db.getString("DETAIL"));
    			result.setInstitute(db.getString("INSTITUTE"));	
    			result.setTimeStamp(db.getDate("TIMESTAMP"));
    			result.setId(db.getInt("ID"));
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
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
	
	public List<Award> getAll(int idJsk, int idResume)
	{
		List<Award> result = new ArrayList<Award>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID,AWARD_NAME,AWARD_DATE,DETAIL,INSTITUTE,TIMESTAMP " +
					"FROM " +
					"	INTER_AWARD " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND DELETE_STATUS<>'TRUE' " +
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
		    	Award aw=new Award();
		    	aw.setIdJsk(db.getInt("ID_JSK"));
		    	aw.setIdResume(db.getInt("ID_RESUME"));
		    	aw.setId(db.getInt("ID"));
		    	aw.setAwardName(db.getString("AWARD_NAME"));
		    	aw.setAwardDate(db.getDate("AWARD_DATE"));
		    	aw.setDetail(db.getString("DETAIL"));
		    	aw.setInstitute(db.getString("INSTITUTE"));
		    	aw.setTimeStamp(db.getDate("TIMESTAMP"));
		    	result.add(aw);
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
	
	public int add(Award award)
	{
		int result = 0;
		String SQL= "INSERT INTO INTER_AWARD(ID_JSK,ID_RESUME,ID,AWARD_DATE,AWARD_NAME,DETAIL,INSTITUTE,TIMESTAMP) VALUES(?,?,?,?,?,?,?,SYSDATE)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, award.getIdJsk());
			db.setInt(2, award.getIdResume());
			db.setInt(3, award.getId());
			db.setDate(4,award.getAwardDate());
			db.setString(5, award.getAwardName());
			db.setString(6, award.getDetail());
			db.setString(7, award.getInstitute());
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
	
	public int delete(Award award)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_AWARD WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, award.getIdJsk());
			db.setInt(2, award.getIdResume());
			db.setInt(3, award.getId());
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

	public int delete(int idJsk, int idResume, int idAward)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_AWARD WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idAward);
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
		String SQL= "DELETE FROM INTER_AWARD WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
	
    public int update(Award award)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_AWARD " +
    				"SET " +
    				"	AWARD_DATE=?,AWARD_NAME=?,DETAIL=?,INSTITUTE=?,TIMESTAMP=SYSDATE " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setDate(1, award.getAwardDate());
    		db.setString(2, award.getAwardName());
    		db.setString(3, award.getDetail());
    		db.setString(4, award.getInstitute());
    		db.setInt(5, award.getIdJsk());
    		db.setInt(6, award.getIdResume());
    		db.setInt(7, award.getId());
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
    
    public int getNextId(int idJsk,int idResume)
	{
		int result=0;
		String SQL="SELECT MAX(ID) AS MAXID FROM INTER_AWARD WHERE ID_JSK=? AND ID_RESUME=? ";
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
				if(db.getString("MAXID")!=null)
				{
					result=db.getInt("MAXID")+1;
				}
				else 
				{
					result = 1;
				}
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
