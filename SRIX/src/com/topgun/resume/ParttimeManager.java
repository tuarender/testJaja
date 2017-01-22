package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class ParttimeManager 
{	
	public Parttime get(int idJsk,int idResume, int id)
	{
		Parttime result=null;
		String sql;
		DBManager db=null;
		
		try
		{
			db=new DBManager();
			sql="select id_jsk,id_resume,id,company,position,wage,start_date,end_date,wage_per,achievement from INTER_PARTTIME where id_jsk=? and id_resume=? and id=?";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, id);
			db.executeQuery();
			if(db.next())
			{ 
				result=new Parttime();
				result.setIdJsk(db.getInt("id_jsk"));
				result.setIdResume(db.getInt("id_resume"));
				result.setId(db.getInt("id"));
				result.setCompany(db.getString("company"));
				result.setPosition(db.getString("position"));
				result.setWage(db.getInt("wage"));
				result.setStartDate(db.getDate("start_date"));
				result.setEndDate(db.getDate("end_date"));
				result.setWagePer(db.getString("wage_per"));
				result.setAchievement(db.getString("achievement"));
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
	
	public List<Parttime> getAll(int idJsk,int idResume)
	{
		List<Parttime> result = new ArrayList<Parttime>();
		DBManager db=null;
		String sql="select id_jsk,id_resume,id,company,position,wage,start_date,end_date,wage_per,achievement from INTER_PARTTIME where id_jsk=? and id_resume=? order by id";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Parttime pt=new Parttime();
		    	pt.setIdJsk(db.getInt("id_jsk"));
		    	pt.setIdResume(db.getInt("id_resume"));
		    	pt.setId(db.getInt("id"));
		    	pt.setCompany(db.getString("company"));
		    	pt.setPosition(db.getString("position"));
		    	pt.setWage(db.getInt("wage"));
		    	pt.setStartDate(db.getDate("start_date"));
		    	pt.setEndDate(db.getDate("end_date"));
		    	pt.setWagePer(db.getString("wage_per"));
		    	pt.setAchievement(db.getString("achievement"));
		    	result.add(pt);
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
	
	public int add(Parttime pt)
	{
		int result = 0;
		DBManager db=null;
		String sql;
		
		try
		{
			db=new DBManager();
			sql="insert into INTER_PARTTIME(id_jsk,id_resume,id,company,position,wage,start_date,end_date,wage_per,achievement) " +
					"values(?,?,?,?,?,?,?,?,?,?)";
			db.createPreparedStatement(sql);
			db.setInt(1, pt.getIdJsk());
			db.setInt(2, pt.getIdResume());
			db.setInt(3, pt.getId());
			db.setString(4, pt.getCompany());
			db.setString(5, pt.getPosition());
			db.setInt(6, pt.getWage());
			db.setDate(7, pt.getStartDate());
			db.setDate(8, pt.getEndDate());
			db.setString(9, pt.getWagePer());
			db.setString(10, pt.getAchievement());
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
	
	public int update(Parttime pt)
	{
		int result = 0;
		DBManager db=null;
		String sql="update INTER_PARTTIME set company=?,position=?,wage=?,start_date=?,end_date=?,wage_per=?,achievement=? where id_jsk=? and id_resume=? and id=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
		    db.setString(1, pt.getCompany());
		    db.setString(2, pt.getPosition());
		    db.setInt(3, pt.getWage());
		    db.setDate(4, pt.getStartDate());
		    db.setDate(5, pt.getEndDate());
		    db.setString(6, pt.getWagePer());
		    db.setString(7, pt.getAchievement());
			db.setInt(8, pt.getIdJsk());
			db.setInt(9, pt.getIdResume());
			db.setInt(10, pt.getId());
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
	
	public int delete(Parttime pt)
	{
		int result = 0;
		DBManager db=null;
		String sql;
		
		try
		{
			db=new DBManager();
			sql="delete from INTER_PARTTIME where id_jsk=? and id_resume=? and id=?";
			db.createPreparedStatement(sql);
			db.setInt(1, pt.getIdJsk());
			db.setInt(2, pt.getIdResume());
			db.setInt(3, pt.getId());
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
