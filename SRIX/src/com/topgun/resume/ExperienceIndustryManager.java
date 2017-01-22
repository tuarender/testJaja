package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class ExperienceIndustryManager 
{
	public ExperienceIndustry get(int idJsk,int idResume,int idWork, int idIndustry)
	{	
		ExperienceIndustry result = null;
		String SQL=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_WORK, ID_INDUSTRY, OTHER_INDUSTRY,ORDER_INDUSTRY " +
					"FROM " +
					"	INTER_EXPERIENCE_INDUSTRY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND " +
					"	ID_WORK=? AND ID_INDUSTRY=?";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
			db.setInt(4, idIndustry);
			db.executeQuery();
			if(db.next())
			{ 
				result=new ExperienceIndustry();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdWork(db.getInt("ID_WORK"));
				result.setIdIndustry(db.getInt("ID_INDUSTRY"));
				result.setOtherIndustry(db.getString("OTHER_INDUSTRY"));
				result.setOrderIndustry(db.getInt("ORDER_INDUSTRY"));
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
	
	public List<ExperienceIndustry> get(int idJsk,int idResume,int idWork)
	{
		List<ExperienceIndustry> result = new ArrayList<ExperienceIndustry>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_WORK, ID_INDUSTRY,OTHER_INDUSTRY,ORDER_INDUSTRY " +
					"FROM " +
					"	INTER_EXPERIENCE_INDUSTRY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID_WORK=?" +
					"ORDER BY " +
					"	ORDER_INDUSTRY";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
		    db.executeQuery();
		    while(db.next())
		    {
		    	ExperienceIndustry industry= new ExperienceIndustry();
		    	industry.setIdJsk(db.getInt("ID_JSK"));
		    	industry.setIdResume(db.getInt("ID_RESUME"));
		    	industry.setIdWork(db.getInt("ID_WORK"));
		    	industry.setIdIndustry(db.getInt("ID_INDUSTRY"));
		    	industry.setOtherIndustry(db.getString("OTHER_INDUSTRY"));
		    	industry.setOrderIndustry(db.getInt("ORDER_INDUSTRY"));
		    	result.add(industry);
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
	
	public List<ExperienceIndustry> getAll(int idJsk,int idResume, int idWork)
	{
		List<ExperienceIndustry> result = new ArrayList<ExperienceIndustry>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_WORK, ID_INDUSTRY,OTHER_INDUSTRY,ORDER_INDUSTRY " +
					"FROM " +
					"	INTER_EXPERIENCE_INDUSTRY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID_WORK=?" +
					"ORDER BY " +
					"	ORDER_INDUSTRY";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
		    db.executeQuery();
		    while(db.next())
		    {
		    	ExperienceIndustry industry= new ExperienceIndustry();
		    	industry.setIdJsk(db.getInt("ID_JSK"));
		    	industry.setIdResume(db.getInt("ID_RESUME"));
		    	industry.setIdWork(db.getInt("ID_WORK"));
		    	industry.setIdIndustry(db.getInt("ID_INDUSTRY"));
		    	industry.setOtherIndustry(db.getString("OTHER_INDUSTRY"));
		    	industry.setOrderIndustry(db.getInt("ORDER_INDUSTRY"));
		    	result.add(industry);
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
	
	public List<ExperienceIndustry> getAllIdWork(int idJsk,int idResume)
	{
		List<ExperienceIndustry> result = new ArrayList<ExperienceIndustry>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_WORK, ID_INDUSTRY,OTHER_INDUSTRY,ORDER_INDUSTRY " +
					"FROM " +
					"	INTER_EXPERIENCE_INDUSTRY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=?" +
					"ORDER BY " +
					"	ORDER_INDUSTRY";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	ExperienceIndustry industry= new ExperienceIndustry();
		    	industry.setIdJsk(db.getInt("ID_JSK"));
		    	industry.setIdResume(db.getInt("ID_RESUME"));
		    	industry.setIdWork(db.getInt("ID_WORK"));
		    	industry.setIdIndustry(db.getInt("ID_INDUSTRY"));
		    	industry.setOtherIndustry(db.getString("OTHER_INDUSTRY"));
		    	industry.setOrderIndustry(db.getInt("ORDER_INDUSTRY"));
		    	result.add(industry);
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
  
	public int add(ExperienceIndustry industry)
	{
		int result = 0;
		DBManager db=null;
		String SQL=	"INSERT INTO " +
					"	INTER_EXPERIENCE_INDUSTRY(ID_JSK, ID_RESUME, ID_WORK,ID_INDUSTRY,OTHER_INDUSTRY,ORDER_INDUSTRY) " +
					"	VALUES(?,?,?,?,?,?)";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, industry.getIdJsk());
			db.setInt(2, industry.getIdResume());
			db.setInt(3, industry.getIdWork());
			db.setInt(4, industry.getIdIndustry());
			db.setString(5, industry.getOtherIndustry());
			db.setInt(6,industry.getOrderIndustry());
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
	
	public int delete(int idJsk, int idResume, int idWork, int idIndustry)
	{
		int result = 0;
		DBManager db=null;
		String SQL="DELETE FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_WORK=? AND ID_INDUSTRY=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
			db.setInt(4, idIndustry);
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
		DBManager db=null;
		String SQL="DELETE FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? ";
		
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
	
	public int deleteAll(int idJsk, int idResume,int idWork)
	{
		int result = 0;
		DBManager db=null;
		String SQL="DELETE FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_WORK=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
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
	
	public int getExpIndustryCount(int idJsk,int idResume,int idWork)
	{
		int result=0;
		String SQL="SELECT COUNT(ID_WORK) AS TOTAL FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_WORK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
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
	
	public int getMinIdIndus(int idJsk,int idResume,int idWork)
	{
		int result=0;
		String SQL="SELECT MIN(ID_INDUSTRY) AS ID_INDUSTRY FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_WORK=? AND ID_INDUSTRY<0 ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ID_INDUSTRY");
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
	
	public int getMaxOrderIndus(int idJsk,int idResume,int idWork)
	{
		int result=0;
		String SQL="SELECT MAX(ORDER_INDUSTRY) AS ORDER_INDUSTRY FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_WORK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ORDER_INDUSTRY");
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
	
	public List<ExperienceIndustry> getExpIndustry (int idJsk,int idResume,int idWork)
	{
		List<ExperienceIndustry> result = new ArrayList<ExperienceIndustry>();
		String SQL="SELECT * FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? and ID_RESUME=? AND ID_WORK=? ORDER BY ORDER_INDUSTRY";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
			db.executeQuery();
			while(db.next())
			{
				ExperienceIndustry expIn= new ExperienceIndustry();
		    	expIn.setIdJsk(db.getInt("ID_JSK"));
		    	expIn.setIdResume(db.getInt("ID_RESUME"));
		    	expIn.setIdWork(db.getInt("ID_WORK"));
		    	expIn.setIdIndustry(db.getInt("ID_INDUSTRY"));
		    	expIn.setOtherIndustry(db.getString("OTHER_INDUSTRY"));
		    	expIn.setOrderIndustry(db.getInt("ORDER_INDUSTRY"));
		    	result.add(expIn);
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
