package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class ExperienceSummaryManager 
{
	public ExperienceSummary get(int idJsk,int idResume,int JobField)
    {
    	ExperienceSummary result=null;
		String SQL="SELECT " +
					"	ID_JSK, ID_RESUME, JOBFIELD, SUM_YEAR, SUM_MONTH " +
					"FROM " +
					"	INTER_EXPERIENCE_SUMMARY " +
					"WHERE " +
    	           "(ID_JSK=?) AND (ID_RESUME=?) AND (JOBFIELD=?)"; 
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, JobField);
    		db.executeQuery();
    		if(db.next())
    		{
    			result = new ExperienceSummary();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setJobField(db.getInt("JOBFIELD"));
    			result.setSumMonth(db.getInt("SUM_MONTH"));
    			result.setSumYear(db.getInt("SUM_YEAR"));
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
	
	public List<ExperienceSummary> getAll(int idJsk,int idResume)
	{
		List<ExperienceSummary> result = new ArrayList<ExperienceSummary>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK, ID_RESUME, JOBFIELD, SUM_YEAR, SUM_MONTH " +
					"FROM " +
					"	INTER_EXPERIENCE_SUMMARY " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) " +
					"ORDER BY " +
					"	JOBFIELD DESC";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	ExperienceSummary expSum = new ExperienceSummary();
		    	expSum.setIdJsk(db.getInt("ID_JSK"));
		    	expSum.setIdResume(db.getInt("ID_RESUME"));
		    	expSum.setJobField(db.getInt("JOBFIELD"));
		    	expSum.setSumMonth(db.getInt("SUM_MONTH"));
		    	expSum.setSumYear(db.getInt("SUM_YEAR"));
		    	result.add(expSum);
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
	
	public List<ExperienceSummary> getExperienceSummaryInfo(int idJsk,int idResume)
	{
		List<ExperienceSummary> result = new ArrayList<ExperienceSummary>();
		DBManager db=null;
		String SQL=	"SELECT WORKJOB_FIELD, (SUM(EXP_Y)*12+SUM(EXP_M)) AS TOTAL_MONTH FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=? GROUP BY WORKJOB_FIELD";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	ExperienceSummary expSum = new ExperienceSummary();
		    	expSum.setIdJsk(idJsk);
		    	expSum.setIdResume(idResume);
		    	expSum.setJobField(db.getInt("WORKJOB_FIELD"));
		    	expSum.setSumMonth(db.getInt("TOTAL_MONTH"));
		    	result.add(expSum);
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
    
	public int add(ExperienceSummary expSum)
	{
		int result = 0;
		String SQL= "INSERT INTO INTER_EXPERIENCE_SUMMARY(ID_JSK,ID_RESUME,JOBFIELD,SUM_YEAR,SUM_MONTH) VALUES(?,?,?,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, expSum.getIdJsk());
			db.setInt(2, expSum.getIdResume());
			db.setInt(3, expSum.getJobField());
			db.setInt(4, expSum.getSumYear());
			db.setInt(5, expSum.getSumMonth());
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
	
	public int delete(ExperienceSummary expSum)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_EXPERIENCE_SUMMARY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (JOBFIELD=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, expSum.getIdJsk());
			db.setInt(2, expSum.getIdResume());
			db.setInt(3, expSum.getJobField());
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
	
	public int delete(int idJsk, int idResume,int jobField)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_EXPERIENCE_SUMMARY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (JOBFIELD=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, jobField);
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
		String SQL= "DELETE FROM INTER_EXPERIENCE_SUMMARY WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
    
    public int update(ExperienceSummary expSum)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_EXPERIENCE_SUMMARY " +
    				"SET " +
    				"	SUM_YEAR=?,SUM_MONTH=? " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (JOBFIELD=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, expSum.getSumYear());
    		db.setInt(2, expSum.getSumMonth());
    		db.setInt(3, expSum.getIdJsk());
    		db.setInt(4, expSum.getIdResume());
    		db.setInt(5, expSum.getJobField());
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
    
    public int getExpSummaryCount(int idJsk,int idResume)
	{
		int result=0;
		String SQL="SELECT COUNT(JOBFIELD) AS TOTAL FROM INTER_EXPERIENCE_SUMMARY WHERE ID_JSK=? AND ID_RESUME=?";
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
				result=db.getInt("TOTAL");
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
    
    
    
    public int getTotalExperienceMonth(int idJsk, int idResume)
    {
    	int result=0;
    	List<WorkExperience> expList=new WorkExperienceManager().getAll(idJsk, idResume);
    	if(expList!=null)
    	{
    		for(int i=0; i<expList.size(); i++)
    		{
    			result+=expList.get(i).getExpY()*12+expList.get(i).getExpM();
    		}
    	}
    	return result;
    }
    
    public int getTotalExperienceSummaryMonth(int idJsk, int idResume)
    {
    	int result=0;
    	List<ExperienceSummary>expList=new  ExperienceSummaryManager().getAll(idJsk, idResume);
    	if(expList.size()>0)
    	{
    		for(int i=0; i<expList.size(); i++)
    		{
    			result+=expList.get(i).getSumYear()*12+expList.get(i).getSumMonth();
    		}
    	}
    	return result;
    }
}
