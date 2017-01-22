package com.topgun.resume;

import com.topgun.util.DBManager;

public class TargetJobManager 
{
	public int add(TargetJob target)
	{
		int result = 0;
		DBManager db=null;
		String sql=	"INSERT INTO INTER_TARGETJOB_EXTENSION(ID_JSK, ID_RESUME, WORK_PERMIT, " +
					"RELOCATE,TRAVEL, START_JOB, MIN_EXPECTED_SALARY, EXPECTED_SALARY_PER, " +
					"MAX_EXPECTED_SALARY, SALARY_CURRENCY,START_JOB_NOTICE) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			db=new DBManager();
			
			db.createPreparedStatement(sql);
			db.setInt(1, target.getIdJsk());
			db.setInt(2, target.getIdResume());
			db.setString(3, target.getWorkPermit());
			db.setString(4, target.getRelocate());
			db.setInt(5, target.getTravel());
			db.setDate(6, target.getStartJob());
			db.setInt(7, target.getMinExpectedSalary());
			db.setInt(8, target.getExpectedSalaryPer());
			db.setInt(9, target.getMaxExpectedSalary());
			db.setInt(10, target.getSalaryCurrency());
			db.setInt(11, target.getStartJobNotice());
			result = db.executeUpdate();
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
	
	public int update(TargetJob target)
	{
		int result = 0;
		DBManager db=null;
		String sql=	"UPDATE " +
					"	INTER_TARGETJOB_EXTENSION " +
					"SET " +
					"	WORK_PERMIT=?, " +
					"	RELOCATE=?, " +
					"	TRAVEL=?, " +
					"	START_JOB=?, " +
					"	MIN_EXPECTED_SALARY=?, " +
					"	EXPECTED_SALARY_PER=?, " +
					"	MAX_EXPECTED_SALARY=?, " +
					"	SALARY_CURRENCY=?," +
					"	START_JOB_NOTICE=? " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, target.getWorkPermit());
			db.setString(2, target.getRelocate());
			db.setInt(3, target.getTravel());
			db.setDate(4, target.getStartJob());
			db.setInt(5, target.getMinExpectedSalary());
			db.setInt(6, target.getExpectedSalaryPer());
			db.setInt(7, target.getMaxExpectedSalary());
			db.setInt(8, target.getSalaryCurrency());
			db.setInt(9, target.getStartJobNotice());
			db.setInt(10, target.getIdJsk());
			db.setInt(11, target.getIdResume());
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
		String sql="DELETE FROM INTER_TARGETJOB_EXTENSION WHERE ID_JSK=? AND ID_RESUME=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
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
	
	public TargetJob get(int idJsk,int idResume)
	{
		TargetJob result=null;
		String sql=	"SELECT " +
					"	WORK_PERMIT,RELOCATE,TRAVEL,START_JOB,MIN_EXPECTED_SALARY,MAX_EXPECTED_SALARY," +
					"	EXPECTED_SALARY_PER,SALARY_CURRENCY,START_JOB_NOTICE " +
					"FROM " +
					"	INTER_TARGETJOB_EXTENSION " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{ 
				result=new TargetJob();
				result.setIdJsk(idJsk);
				result.setIdResume(idResume);
				result.setWorkPermit(db.getString("WORK_PERMIT"));
				result.setRelocate(db.getString("RELOCATE"));
				result.setTravel(db.getInt("TRAVEL"));
				result.setStartJob(db.getDate("START_JOB"));
				result.setMinExpectedSalary(db.getInt("MIN_EXPECTED_SALARY"));
				result.setMaxExpectedSalary(db.getInt("MAX_EXPECTED_SALARY"));
				result.setExpectedSalaryPer(db.getInt("EXPECTED_SALARY_PER"));
				result.setSalaryCurrency(db.getInt("SALARY_CURRENCY"));
				result.setStartJobNotice(db.getInt("START_JOB_NOTICE"));
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
	
	public boolean isExis(int idJsk,int idResume)
	{
		boolean result=false;
		String sql=	"SELECT ID_JSK FROM INTER_TARGETJOB_EXTENSION WHERE ID_JSK=? AND ID_RESUME=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{ 
				result=true;
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
