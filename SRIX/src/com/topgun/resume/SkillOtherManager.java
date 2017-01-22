package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class SkillOtherManager 
{
	
	public SkillOther get(int idJsk,int idResume,int id)
    {
		SkillOther result=null;
    	String SQL="SELECT ID_JSK,ID_RESUME,SKILL_NAME,LEVEL_SKILL FROM INTER_SKILL_OTHER WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)"; 
    	
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
    			result=new SkillOther();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setSkillName(db.getString("SKILL_NAME"));
    			result.setLevelSkill(db.getInt("LEVEL_SKILL"));
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
	
	public List<SkillOther> getAll(int idJsk,int idResume)
	{
		List<SkillOther> result = new ArrayList<SkillOther>();
		DBManager db=null;
		String SQL="SELECT ID_JSK,ID_RESUME,ID,SKILL_NAME,LEVEL_SKILL FROM INTER_SKILL_OTHER WHERE (ID_JSK=?) AND (ID_RESUME=?) ORDER BY ID"; 
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	SkillOther other=new SkillOther();
		    	other.setIdJsk(db.getInt("ID_JSK"));
		    	other.setIdResume(db.getInt("ID_RESUME"));
		    	other.setId(db.getInt("ID"));
		    	other.setSkillName(db.getString("SKILL_NAME"));
		    	other.setLevelSkill(db.getInt("LEVEL_SKILL"));
		    	result.add(other);
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
	
	public int add(SkillOther other)
	{
		int result = 0;
		String SQL= "INSERT INTO INTER_SKILL_OTHER(ID_JSK,ID_RESUME,ID,SKILL_NAME,LEVEL_SKILL) VALUES(?,?,?,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, other.getIdJsk());
			db.setInt(2, other.getIdResume());
			db.setInt(3, other.getId());
			db.setString(4, other.getSkillName());
			db.setInt(5, other.getLevelSkill());
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
	
	public int delete(SkillOther other)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_SKILL_OTHER WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, other.getIdJsk());
			db.setInt(2, other.getIdResume());
			db.setInt(3, other.getId());
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
	
	public int delete(int idJsk, int idResume, int idSkill)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_SKILL_OTHER WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idSkill);
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
		String SQL= "DELETE FROM INTER_SKILL_OTHER WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
    
    public int update(SkillOther other)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_SKILL_OTHER " +
    				"SET " +
    				"	SKILL_NAME=?,LEVEL_SKILL=? " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, other.getSkillName());
    		db.setInt(2, other.getLevelSkill());
    		db.setInt(3, other.getIdJsk());
    		db.setInt(4, other.getIdResume());
    		db.setInt(5, other.getId());
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
		int result=1;
		String SQL="SELECT MAX(ID) AS MAXID FROM INTER_SKILL_OTHER WHERE ID_JSK=? AND ID_RESUME=? ";
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
    
    public boolean haveSkillOther(int idJsk,int idResume)
    {
    	DBManager db = null ;
    	boolean result  = false ;
    	String sql = "";
    	try
    	{
    		sql = "SELECT * FROM INTER_SKILL_OTHER WHERE ID_JSK = ? AND ID_RESUME = ?";
    		db = new DBManager() ;
    		db.createPreparedStatement(sql);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.executeQuery();
    		if(db.next())
    		{
    			result = true ; 
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
}
