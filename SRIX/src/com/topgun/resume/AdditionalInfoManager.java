package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class AdditionalInfoManager
{
	public AdditionalInfo get(int idJsk,int idResume)
	{
		AdditionalInfo result=null;
		String SQL = "SELECT ID_JSK,ID_RESUME,ADDITIONAL1,ADDITIONAL2,ADDITIONAL3,ADDITIONAL4 FROM INTER_ADDITIONAL_INFO WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		System.out.println( idJsk+"   <>   "+idResume);
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if (db.next())
			{
				result = new AdditionalInfo();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setAdditional1(db.getString("ADDITIONAL1"));
				result.setAdditional2(db.getString("ADDITIONAL2"));
				result.setAdditional4(db.getString("ADDITIONAL3"));
				result.setAdditional4(db.getString("ADDITIONAL4"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result;
	}
	
	public List<AdditionalInfo> getAll(int idJsk)
	{
		List<AdditionalInfo> result = new ArrayList<AdditionalInfo>();
		DBManager db=null;
		String SQL = "SELECT ID_JSK,ID_RESUME,ADDITIONAL1,ADDITIONAL2,ADDITIONAL3,ADDITIONAL4 FROM INTER_ADDITIONAL_INFO WHERE (ID_JSK=?) ORDER BY ID_RESUME";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
		    db.executeQuery();
		    while(db.next())
		    {
		    	AdditionalInfo addIn = new AdditionalInfo();
		    	addIn.setIdJsk(db.getInt("ID_JSK"));
		    	addIn.setIdResume(db.getInt("ID_RESUME"));
		    	addIn.setAdditional1(db.getString("ADDITIONAL1"));
		    	addIn.setAdditional2(db.getString("ADDITIONAL2"));
		    	addIn.setAdditional3(db.getString("ADDITIONAL3"));
		    	addIn.setAdditional4(db.getString("ADDITIONAL4"));
		    	result.add(addIn);
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

	public int add(AdditionalInfo addInfo)
	{
		int result = 0;
		String SQL ="INSERT INTO INTER_ADDITIONAL_INFO(ID_JSK,ID_RESUME,ADDITIONAL1,ADDITIONAL2,ADDITIONAL3,ADDITIONAL4) VALUES(?,?,?,?,?,?)";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, addInfo.getIdJsk());
			db.setInt(2, addInfo.getIdResume());
			db.setString(3, addInfo.getAdditional1());
			db.setString(4, addInfo.getAdditional2());
			db.setString(5, addInfo.getAdditional3());
			db.setString(6, addInfo.getAdditional4());
			
			result = db.executeUpdate();				
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result;
	}

	public int delete(AdditionalInfo addInfo)
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_ADDITIONAL_INFO WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, addInfo.getIdJsk());
			db.setInt(2, addInfo.getIdResume());
			result = db.executeUpdate();
		}
		catch (Exception e)
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
		String SQL = "DELETE FROM INTER_ADDITIONAL_INFO WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			result = db.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result;
	}
	

	public int update(AdditionalInfo addInfo)
	{
		int result = 0;
		String SQL ="UPDATE " +
					"	INTER_ADDITIONAL_INFO " +
					"SET " +
					"	ADDITIONAL1=?," +
					"	ADDITIONAL2=?," +
					"	ADDITIONAL3=?," +
					"	ADDITIONAL4=? " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)";
		
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, addInfo.getAdditional1());
			db.setString(2, addInfo.getAdditional2());
			db.setString(3, addInfo.getAdditional3());
			db.setString(4, addInfo.getAdditional4());
			db.setInt(5, addInfo.getIdJsk());
			db.setInt(6, addInfo.getIdResume());
			
			result = db.executeUpdate();
		}
		catch (Exception e)
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
