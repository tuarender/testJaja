package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class ActivityManager
{
	public Activity get(int idJsk,int idResume, int idActivity)
	{
		Activity result = null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, CLUB, POSITION, ID_ACTIVITY, " +
					"	TIMESTAMP, START_DATE, END_DATE, DESCRIPTION " +
					"FROM " +
					"	INTER_ACTIVITY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? " +
					"	AND ID_ACTIVITY=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idActivity);
			db.executeQuery();
			if(db.next())
			{ 
				result = new Activity();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setId(db.getInt("ID_ACTIVITY"));
				result.setClub(db.getString("CLUB"));
				result.setPosition(db.getString("POSITION"));
				result.setStartDate(db.getDate("START_DATE"));
				result.setEndDate(db.getDate("END_DATE"));
				result.setDescription(db.getString("DESCRIPTION"));
				result.setTimeStamp(db.getDate("TIMESTAMP"));
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
	
	public List<Activity> getAll(int idJsk, int idResume)
	{
		List<Activity> result = new ArrayList<Activity>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_JSK,ID_RESUME,CLUB,POSITION,ID_ACTIVITY," +
					"	TIMESTAMP,START_DATE,END_DATE,DESCRIPTION " +
					"FROM " +
					"	INTER_ACTIVITY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? " +
					"ORDER BY " +
					"	ID_ACTIVITY";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Activity activity = new Activity();
		    	activity.setIdJsk(db.getInt("ID_JSK"));
		    	activity.setIdResume(db.getInt("ID_RESUME"));
		    	activity.setClub(db.getString("CLUB"));
		    	activity.setPosition(db.getString("POSITION"));
		    	activity.setId(db.getInt("ID_ACTIVITY"));
		    	activity.setTimeStamp(db.getDate("TIMESTAMP"));
		    	activity.setStartDate(db.getDate("START_DATE"));
		    	activity.setEndDate(db.getDate("END_DATE"));
		    	activity.setDescription(db.getString("DESCRIPTION"));
		    	result.add(activity);
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
	
	public int add(Activity activity)
	{
		int result=0;
		DBManager db=null;
		String sql=	"INSERT INTO " +
					"	INTER_ACTIVITY(ID_JSK,ID_RESUME,ID_ACTIVITY,CLUB,POSITION,START_DATE,END_DATE,DESCRIPTION,TIMESTAMP) " +
					"	VALUES(?,?,?,?,?,?,?,?,SYSDATE)";
		
		try
		{
			db=new DBManager();
			
			db.createPreparedStatement(sql);
			db.setInt(1, activity.getIdJsk());
			db.setInt(2, activity.getIdResume());
			db.setInt(3, activity.getId());
			db.setString(4, activity.getClub());
			db.setString(5, activity.getPosition());
			db.setDate(6, activity.getStartDate());
			db.setDate(7, activity.getEndDate());
			db.setString(8, activity.getDescription());
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
	
	public int update(Activity activity)
	{
		int result=0;
		DBManager db=null;
		String sql=	"UPDATE " +
					"	INTER_ACTIVITY " +
					"SET " +
					"	CLUB=?," +
					"	POSITION=?," +
					"	START_DATE=?," +
					"	END_DATE=?," +
					"	DESCRIPTION=?," +
					"	TIMESTAMP=SYSDATE " +
					"WHERE " +
					"	ID_JSK=? AND " +
					"	ID_RESUME=? AND " +
					"	ID_ACTIVITY=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, activity.getClub());
			db.setString(2, activity.getPosition());
			db.setDate(3, activity.getStartDate());
			db.setDate(4, activity.getEndDate());
			db.setString(5, activity.getDescription());
			db.setInt(6, activity.getIdJsk());
			db.setInt(7, activity.getIdResume());
			db.setInt(8, activity.getId());
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
	
	public int delete(Activity activity)
	{
		int result=0;
		DBManager db=null;
		String sql=	"DELETE FROM INTER_ACTIVITY WHERE ID_JSK=? AND ID_RESUME=? AND ID_ACTIVITY=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, activity.getIdJsk());
			db.setInt(2, activity.getIdResume());
			db.setInt(3, activity.getId());
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
	
	public int delete(int idJsk, int idResume, int idActivity)
	{
		int result=0;
		DBManager db=null;
		String sql=	"DELETE FROM INTER_ACTIVITY WHERE ID_JSK=? AND ID_RESUME=? AND ID_ACTIVITY=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idActivity);
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
		int result=0;
		DBManager db=null;
		String sql=	"DELETE FROM INTER_ACTIVITY WHERE ID_JSK=? AND ID_RESUME=?";
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
	
	public int getNextActivityId(int idJsk,int idResume)
	{
		int result=1;
		String SQL="SELECT MAX(ID_ACTIVITY) AS MAXID FROM INTER_ACTIVITY WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db=null;
		try{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next()){
				result=db.getInt("MAXID")+1;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
}
