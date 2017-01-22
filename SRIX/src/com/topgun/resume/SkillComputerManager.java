package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class SkillComputerManager 
{
	public SkillComputer get(int idJsk,int idResume,int idComputer)
    {
		SkillComputer result=null;
    	String SQL=	"SELECT " +
    				"	ID_JSK, ID_RESUME, OTHER_COMPUTER, LEVEL_SKILL, ID_GROUP " +
    				"FROM " +
    				"	INTER_SKILL_COMPUTER " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_COMPUTER=?)"; 
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idComputer);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new SkillComputer();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setOthComputer(db.getString("OTHER_COMPUTER"));
    			result.setLevelSkill(db.getInt("LEVEL_SKILL"));
    			result.setIdGroup(db.getInt("ID_GROUP"));
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
	
	public List<SkillComputer> getAll(int idJsk,int idResume)
	{
		List<SkillComputer> result = new ArrayList<SkillComputer>();
		DBManager db=null;
		String SQL="SELECT  " +
				   "	ID_JSK,ID_RESUME,ID_COMPUTER,OTHER_COMPUTER,LEVEL_SKILL,A.ID_GROUP  " +
				   "FROM  " +
				   "	INTER_SKILL_COMPUTER A , INTER_COMPUTER_GROUP B " +
				   "WHERE " +
				   "	(ID_JSK=?) AND (ID_RESUME=?) AND A.ID_GROUP = B.ID_GROUP " +
				   "ORDER BY " +
				   "	ID_COMPUTER"; 
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	SkillComputer com=new SkillComputer();
		    	com.setIdJsk(db.getInt("ID_JSK"));
		    	com.setIdResume(db.getInt("ID_RESUME"));
		    	com.setIdComputer(db.getInt("ID_COMPUTER"));
		    	com.setOthComputer(db.getString("OTHER_COMPUTER"));
		    	com.setLevelSkill(db.getInt("LEVEL_SKILL"));
		    	com.setIdGroup(db.getInt("ID_GROUP"));
		    	result.add(com);
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
	
	public int add(SkillComputer com)
	{
		int result = 0;
		String SQL= "INSERT INTO " +
					"	INTER_SKILL_COMPUTER(ID_JSK,ID_RESUME,ID_COMPUTER,OTHER_COMPUTER,LEVEL_SKILL,ID_GROUP) " +
					"	VALUES(?,?,?,?,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, com.getIdJsk());
			db.setInt(2, com.getIdResume());
			db.setInt(3, com.getIdComputer());
			db.setString(4, com.getOthComputer());
			db.setInt(5, com.getLevelSkill());
			db.setInt(6, com.getIdGroup());
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
	
	public int delete(SkillComputer com)
	{
		int result = 0;
		String SQL= "DELETE FROM " +
					"	INTER_SKILL_COMPUTER " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_COMPUTER=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, com.getIdJsk());
			db.setInt(2, com.getIdResume());
			db.setInt(3, com.getIdComputer());
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
		String SQL= "DELETE FROM " +
					"	INTER_SKILL_COMPUTER " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)";
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
    	
    public int update(SkillComputer com)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_SKILL_COMPUTER " +
    				"SET " +
    				"	OTHER_COMPUTER=?,LEVEL_SKILL=? " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND " +
    				"	(ID_COMPUTER=?) AND (ID_GROUP=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, com.getOthComputer());
    		db.setInt(2, com.getLevelSkill());
    		db.setInt(3, com.getIdJsk());
    		db.setInt(4, com.getIdResume());
    		db.setInt(5, com.getIdComputer());
    		db.setInt(6, com.getIdGroup());
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
    
    public int getMinIdSkillComputer(int idJsk,int idResume){
    	int result = 0 ;
    	String sql = " select a.id_computer from inter_skill_computer a where id_jsk = ? and a.id_resume = ? "
    			+ "and a.id_computer = "
    			+ " (select min(id_computer) from inter_skill_computer where id_jsk = a.id_jsk and id_resume = a.id_resume )";
    	DBManager db = null;
    	try{
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setInt(1,idJsk);
    		db.setInt(2,idResume);
    		db.executeQuery();
    		if(db.next()){
    			result = db.getInt("id_computer");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		db.close();
    	}
    	return result ;
    }
    
    public int chkSkillComputer(int idJsk,int idResume,int idGroupComputer,int idSkillComputer)
    {
    	int result = 0;
    	DBManager db = null ;
    	String sql= "select * from inter_skill_computer where id_jsk = ? and id_resume = ? and id_group = ? and id_computer = ?";
    	try{
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setInt(1,idJsk);
    		db.setInt(2,idResume);
    		db.setInt(3, idGroupComputer);
    		db.setInt(4, idSkillComputer);
    		db.executeQuery();
    		if(db.next()){
    			result = 1 ;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		db.close();
    	}
    	return result ;
    }
    
    public boolean haveSkillComputer(int idJsk,int idResume)
    {
    	DBManager db = null ;
    	boolean result  = false ;
    	String sql = "";
    	try
    	{
    		sql = "SELECT * FROM INTER_SKILL_COMPUTER WHERE ID_JSK = ? AND ID_RESUME = ?";
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
    
    public void addViewSkillLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_SKILL_VIEW_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
	
	public void addSkillComLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_SKILL_COM_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
	
	public void addSkillSubmitLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_SKILL_SUBMIT_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
	
	public void addSkillSkipLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_SKILL_SKIP_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
    
}
