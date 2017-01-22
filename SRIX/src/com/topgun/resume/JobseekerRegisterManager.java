package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class JobseekerRegisterManager 
{
	public static JobseekerRegister get(int idJsk)
	{
		JobseekerRegister result=null;
		String sql="SELECT ID_JSK,FIRSTNAME,LASTNAME,PRIMARY_PHONE,PRIMARY_PHONE_TYPE,USERNAME,PASSWORD,LASTSTEP,AGENT,REFERENCE,SPONSOR_EMAIL FROM INTER_JOBSEEKER_REGISTER WHERE (ID_JSK=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=new JobseekerRegister();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setFirstName(db.getString("FIRSTNAME"));
				result.setLastName(db.getString("LASTNAME"));
				result.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
				result.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
				result.setUsername(db.getString("USERNAME"));
				result.setPassword(db.getString("PASSWORD"));
				result.setLastStep(db.getString("LASTSTEP"));
				result.setAgent(db.getString("AGENT"));
				result.setReference(db.getString("REFERENCE"));
				result.setSponsor_email(db.getString("SPONSOR_EMAIL"));
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
	
	public static List<JobseekerRegister> getAll(String reference)
	{
		List<JobseekerRegister> result = new ArrayList<JobseekerRegister>();
		DBManager db=null;
		String sql="SELECT ID_JSK,FIRSTNAME,LASTNAME,PRIMARY_PHONE,PRIMARY_PHONE_TYPE,USERNAME,PASSWORD,LASTSTEP,AGENT,REFERENCE,SPONSOR_EMAIL FROM INTER_JOBSEEKER_REGISTER WHERE REFERENCE=? ORDER BY ID_JSK";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, reference);
		    db.executeQuery();
		    while(db.next())
		    {
		    	JobseekerRegister reg=new JobseekerRegister();
		    	reg.setIdJsk(db.getInt("ID_JSK"));
		    	reg.setFirstName(db.getString("FIRSTNAME"));
		    	reg.setFirstName(db.getString("LASTNAME"));
		    	reg.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	reg.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	reg.setUsername(db.getString("USERNAME"));
		    	reg.setPassword(db.getString("PASSWORD"));
		    	reg.setLastStep(db.getString("LASTSTEP"));
		    	reg.setAgent(db.getString("AGENT"));
		    	reg.setReference(db.getString("REFERENCE"));
		    	reg.setSponsor_email(db.getString("SPONSOR_EMAIL"));
		    	result.add(reg);
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
	
	public static void add(JobseekerRegister reg)
	{
		String sql=	"INSERT INTO INTER_JOBSEEKER_REGISTER" +
					"(" +
						"ID_JSK," +
						"USERNAME," +
						"PASSWORD," +
						"FIRSTNAME," +
						"LASTNAME," +
						"STARTTIME," +
						"LASTTIME," +
						"PRIMARY_PHONE," +
						"PRIMARY_PHONE_TYPE," +
						"LASTSTEP," +
						"AGENT," +
						"REFERENCE," +
						"SPONSOR_EMAIL" +
					") " +
					"VALUES(?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, reg.getIdJsk());
			db.setString(2, reg.getUsername());
			db.setString(3, reg.getPassword());
			db.setString(4, reg.getFirstName());
			db.setString(5, reg.getLastName());
			db.setString(6, reg.getPrimaryPhone());
			db.setString(7, reg.getPrimaryPhoneType());
			db.setString(8, reg.getLastStep());
			db.setString(9, reg.getAgent());				
			db.setString(10, reg.getReference());	
			db.setString(11, reg.getSponsor_email());
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
	
	public static void delete(int idJsk)
	{
		String sql=	"DELETE FROM INTER_JOBSEEKER_REGISTER WHERE ID_JSK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
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
