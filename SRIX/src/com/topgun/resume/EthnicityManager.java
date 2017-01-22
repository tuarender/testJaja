package com.topgun.resume;

import com.topgun.util.DBManager;

public class EthnicityManager 
{
	public Ethnicity get(int idJsk, int idResume) 
	{
		Ethnicity result = null;
		String SQL = "SELECT ID_JSK, ID_RESUME,ETHNICITY,TIMESTAMP FROM INTER_ETHNICITY WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
				result = new Ethnicity();
				result.setIdJsk(idJsk);
				result.setIdResume(idResume);
				result.setEthnicity(db.getString("ETHNIC,ITY"));
				result.setTimeStamp(db.getDate("TIMESTAMP"));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally {
			db.close();
		}
		return result;
	}
	
	public int add(Ethnicity eth) 
	{
		int result = 0;
		String SQL = "INSERT INTO INTER_ETHNICITY(ID_JSK,ID_RESUME,ETHNICITY,DELETE_STATUS,TIMESTAMP) VALUES(?,?,?,'FALSE',SYSDATE)";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, eth.getIdJsk());
			db.setInt(2, eth.getIdResume());
			db.setString(3, eth.getEthnicity());
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

	public int delete(Ethnicity eth) 
	{
		int result = 0;
		String SQL="DELETE FROM INTER_ETHNICITY WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, eth.getIdJsk());
			db.setInt(2, eth.getIdResume());
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

	public int update(Ethnicity eth) 
	{
		int result = 0;
		String SQL = "UPDATE INTER_ETHNICITY SET ETHNICITY=?,TIMESTAMP=SYSDATE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, eth.getEthnicity());
			db.setInt(2, eth.getIdJsk());
			db.setInt(3, eth.getIdResume());
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
