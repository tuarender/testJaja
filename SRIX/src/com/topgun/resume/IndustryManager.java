package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class IndustryManager 
{
	public Industry get(int idJsk,int idResume, int idIndustry)
	{
		Industry result=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID_INDUSTRY,INDUSTRY_ORDER,OTHER_INDUSTRY " +
					"FROM " +
					"	INTER_INDUSTRY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID_INDUSTRY=?";
		
		DBManager db=null;
		
		try
		{
			db=new DBManager();
			
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idIndustry);
			db.executeQuery();
			if(db.next())
			{ 
				result=new Industry();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdIndustry(db.getInt("ID_INDUSTRY"));
				result.setIndustryOrder(db.getInt("INDUSTRY_ORDER"));
				result.setOtherIndustry(db.getString("OTHER_INDUSTRY"));
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
	
	public List<Industry> getAll(int idJsk,int idResume)
	{
		List<Industry> result = new ArrayList<Industry>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID_INDUSTRY,INDUSTRY_ORDER,OTHER_INDUSTRY " +
					"FROM " +
					"	INTER_INDUSTRY " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? " +
					"ORDER BY " +
					"	INDUSTRY_ORDER";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Industry ind = new Industry();
		    	ind.setIdJsk(db.getInt("ID_JSK"));
		    	ind.setIdResume(db.getInt("ID_RESUME"));
		    	ind.setIdIndustry(db.getInt("ID_INDUSTRY"));
		    	ind.setIndustryOrder(db.getInt("INDUSTRY_ORDER"));
		    	ind.setOtherIndustry(db.getString("OTHER_INDUSTRY"));
		    	result.add(ind);
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
	
	public int add(Industry industry)
	{
		int result = 0;
		DBManager db=null;
		String SQL="INSERT INTO INTER_INDUSTRY(ID_JSK,ID_RESUME,ID_INDUSTRY,INDUSTRY_ORDER,OTHER_INDUSTRY) VALUES(?,?,?,?,?)";
		
		try
		{
			db=new DBManager();
			
			db.createPreparedStatement(SQL);
			db.setInt(1, industry.getIdJsk());
			db.setInt(2, industry.getIdResume());
			db.setInt(3, industry.getIdIndustry());
			db.setInt(4, industry.getIndustryOrder());
			db.setString(5, industry.getOtherIndustry());
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
	
	public int update(Industry industry)
	{
		int result = 0;
		DBManager db=null;
		String SQL="UPDATE INTER_INDUSTRY SET INDUSTRY_ORDER=?,OTHER_INDUSTRY=? WHERE ID_JSK=? AND ID_RESUME=? AND ID_INDUSTRY=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, industry.getIndustryOrder());
			db.setString(2,industry.getOtherIndustry());
			db.setInt(3, industry.getIdJsk());
			db.setInt(4, industry.getIdResume());
			db.setInt(5, industry.getIdIndustry());
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
	
	public int delete(Industry industry)
	{
		int result = 0;
		DBManager db=null;
		String SQL="DELETE FROM INTER_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_INDUSTRY=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, industry.getIdJsk());
			db.setInt(2, industry.getIdResume());
			db.setInt(3, industry.getIdIndustry());
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
	
	public int delete(int idJsk, int idResume, int idIndustry)
	{
		int result = 0;
		DBManager db=null;
		String SQL="DELETE FROM INTER_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_INDUSTRY=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idIndustry);
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
		String SQL="DELETE FROM INTER_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=?";
		
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
	
	public int count(int idJsk, int idResume)
	{
		int result=0; 
		DBManager db=null;
		String SQL="SELECT COUNT(ID_INDUSTRY) AS TOTAL FROM INTER_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=?";
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
	
	public int getMaxIndustryOrder(int idJsk, int idResume)
	{
		int result=0; 
		DBManager db=null;
		String SQL="SELECT MAX(INDUSTRY_ORDER) AS MAXID FROM INTER_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("MAXID");
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
	
	public int getNextOtherIndustryId(int idJsk, int idResume)
	{
		int result=-1;
		String sql=	"SELECT MIN(ID_INDUSTRY) AS MINID FROM INTER_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_INDUSTRY<0";
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
		    	result=db.getInt("MINID")-1;
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
