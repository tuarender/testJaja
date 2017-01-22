package com.topgun.resume;

import com.topgun.util.DBManager;

public class MailJobseekerCancelManager 
{
	public static MailJobseekerCancel get(int id)
	{
		MailJobseekerCancel result = null;
		String sql;
		DBManager db=null;
		
		try
		{
			db=new DBManager();
			sql="select * from MAIL_JOBSEEKER_CANCEL where id=?";
			db.createPreparedStatement(sql);
			db.setInt(1, id);
			db.executeQuery();
			if(db.next())
			{
				result = new MailJobseekerCancel();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setType(db.getString("TYPE"));
				result.setAction(db.getString("ACTION"));
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
	public static int add(MailJobseekerCancel jsk)
	{
		int result = 0;
		DBManager db=null;
		String sql;
		
		try
		{
			db=new DBManager();
			sql="insert into MAIL_JOBSEEKER_CANCEL(id_jsk,type,action) values(?,?,?)";
			db.createPreparedStatement(sql);
			db.setInt(1, jsk.getIdJsk());
			db.setString(2, jsk.getType());
			db.setString(3, jsk.getAction());
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
	
	public static int update(MailJobseekerCancel jsk)
	{
		int result = 0;
		DBManager db=null;
		String sql;
		
		try
		{
			db=new DBManager();
			sql="update MAIL_JOBSEEKER_CANCEL set id_jsk =? ,type=? ,action=? where id=?";
			db.createPreparedStatement(sql);
			db.setInt(1, jsk.getIdJsk());
			db.setString(2, jsk.getType());
			db.setString(3, jsk.getAction());
			db.setInt(4, jsk.getId());
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
	
	public static int delete(MailJobseekerCancel jsk)
	{
		int result = 0;
		DBManager db=null;
		String sql;
		
		try
		{
			db=new DBManager();
			sql="delete from MAIL_JOBSEEKER_CANCEL where id=?";
			db.createPreparedStatement(sql);
			db.setInt(1, jsk.getId());
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
