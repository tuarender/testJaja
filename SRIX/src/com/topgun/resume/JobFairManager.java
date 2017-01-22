package com.topgun.resume;

import com.topgun.util.DBManager;

public class JobFairManager 
{	
	public JobFair get(String idJobFair, int idEmp, int idPosition)
	{
		JobFair result=null;
		String SQL="SELECT ID_EMP, COM_NAME, EMAIL FROM JOBFAIR_EMP WHERE ID_EMP=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);			
			db.executeQuery();
			if(db.next())
			{
				result=new JobFair();
				result.setCompanyId(db.getInt("ID_EMP"));
				result.setCompanyName(db.getString("COM_NAME"));
				result.setEmail(db.getString("EMAIL"));
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
	
	public String getPlace(JobFair jobfair) 
	{
		String place="";
		String SQL="SELECT UNAME1, DNAME FROM JOBFAIR_UNI WHERE (ID=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, jobfair.getJobFairId());
			db.executeQuery();
			if(db.next())
			{
				place =db.getString("UNAME1")!=null?db.getString("UNAME1"):"";
				place+=db.getString("DNAME")!=null?db.getString("DNAME"):"";
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
    	return place!=null?place:"";
    }
	
	public String getPositionName(JobFair jobfair) 
	{
		String positionName="";
		String SQL="SELECT POSITION_NAME FROM JOBFAIR_POS WHERE (ID_EMP=?) AND (ID_POSITION=?) AND (IDU=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, jobfair.getCompanyId());
			db.setInt(2, jobfair.getPositionId());
			db.setString(3, jobfair.getJobFairId());
			db.executeQuery();
			if(db.next())
			{
				positionName=db.getString("POSITION_NAME");
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
    	return positionName!=null?positionName:"";
    }

}
