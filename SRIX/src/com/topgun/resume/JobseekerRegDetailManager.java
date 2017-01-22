package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class JobseekerRegDetailManager 
{
	public static JobseekerRegDetail get(int idJsk)
	{
		JobseekerRegDetail result=null;
		String sql="SELECT ID_JSK,USERNAME,REFERENCE,SUGGESTER,SUGGESTER_TYPE,TIMESTAMP FROM INTER_JOBSEEKER_REG_DETAIL WHERE (ID_JSK=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=new JobseekerRegDetail();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setUsername(db.getString("USERNAME"));
				result.setReference(db.getString("REFERENCE"));
				result.setSuggester(db.getString("SUGGESTER"));
				result.setSuggesterType(db.getString("SUGGESTER_TYPE"));
				result.setTimestamp(db.getString("TIMESTAMP"));
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
	
	public static List<JobseekerRegDetail> getAll()
	{
		List<JobseekerRegDetail> result = new ArrayList<JobseekerRegDetail>();
		DBManager db=null;
		String sql="SELECT ID_JSK,USERNAME,REFERENCE,SUGGESTER,SUGGESTER_TYPE,TIMESTAMP FROM INTER_JOBSEEKER_REG_DETAIL ORDER BY ID_JSK";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
		    db.executeQuery();
		    while(db.next())
		    {
		    	JobseekerRegDetail detail=new JobseekerRegDetail();
		    	detail.setIdJsk(db.getInt("ID_JSK"));
		    	detail.setUsername(db.getString("USERNAME"));
		    	detail.setReference(db.getString("REFERENCE"));
		    	detail.setSuggester(db.getString("SUGGESTER"));
		    	detail.setSuggesterType(db.getString("SUGGESTER_TYPE"));
		    	detail.setTimestamp(db.getString("TIMESTAMP"));
		    	result.add(detail);
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

	public static void add(JobseekerRegDetail detail)
	{
		String sql=	"INSERT INTO INTER_JOBSEEKER_REG_DETAIL" +
					"(" +
						"ID_JSK," +
						"USERNAME," +
						"REFERENCE," +
						"SUGGESTER," +
						"SUGGESTER_TYPE," +
						"TIMESTAMP" +
					") " +
					"VALUES(?,?,?,?,?,SYSDATE)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, detail.getIdJsk());
			db.setString(2, detail.getUsername());		
			db.setString(3, detail.getReference());
			db.setString(4, detail.getSuggester());
			db.setString(5, detail.getSuggesterType());
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
