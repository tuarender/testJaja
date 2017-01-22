package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class JobTypeManager 
{
	public JobType get(int idJsk,int idResume,int jobType)
	{
		JobType result = null;
		String sql=	"SELECT " +
					"	ID_RESUME, JOBTYPE, ID_JSK " +
					"FROM " +
					"	INTER_JOBTYPE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND JOBTYPE=?";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, jobType);
			db.executeQuery();
			if(db.next())
			{
				result = new JobType();
				result.setIdJsk(idJsk);
				result.setIdResume(idResume);
				result.setJobType(db.getInt("JOBTYPE"));
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
	
	public List<JobType> getAll(int idJsk,int idResume)
	{
		List<JobType> result = new ArrayList<JobType>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, JOBTYPE " +
					"FROM " +
					"	INTER_JOBTYPE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? " +
					"ORDER BY " +
					"	JOBTYPE";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	JobType type = new JobType();
		    	type.setIdJsk(db.getInt("ID_JSK"));
		    	type.setIdResume(db.getInt("ID_RESUME"));
		    	type.setJobType(db.getInt("JOBTYPE"));
		    	result.add(type);
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
	
	public int add(JobType type)
	{
		int result = 0;
		DBManager db=null;
		String sql="INSERT INTO INTER_JOBTYPE(ID_JSK,ID_RESUME,JOBTYPE) VALUES(?,?,?)";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, type.getIdJsk());
			db.setInt(2, type.getIdResume());
			db.setInt(3, type.getJobType());
			result = db.executeUpdate();
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
	
	public int update(JobType type)
	{
		int result = 0;
		DBManager db=null;
		String sql="UPDATE INTER_JOBTYPE SET JOBTYPE=? WHERE ID_JSK=? AND ID_RESUME=? AND JOBTYPE=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, type.getJobType());
			db.setInt(2, type.getIdJsk());
			db.setInt(3, type.getIdResume());
			db.setInt(4, type.getJobType());
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
	
	public int delete(JobType type)
	{
		int result = 0;
		DBManager db=null;
		String sql="DELETE FROM INTER_JOBTYPE WHERE ID_JSK=? AND ID_RESUME=? AND JOBTYPE=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, type.getIdJsk());
			db.setInt(2, type.getIdResume());
			db.setInt(3, type.getJobType());
			result=db.executeUpdate();
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
	
	public int deleteAll(int idJsk, int idResume)
	{
		int result = 0;
		DBManager db=null;
		String sql="DELETE FROM INTER_JOBTYPE WHERE ID_JSK=? AND ID_RESUME=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			result=db.executeUpdate();
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
}
