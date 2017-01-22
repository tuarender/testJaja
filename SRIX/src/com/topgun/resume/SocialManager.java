package com.topgun.resume;

import com.topgun.util.DBManager;

public class SocialManager {
	public Social get(int idJsk, int idResume)
	{
		Social social = null;
		String sql = " SELECT "
						+ " ID_JSK, ID_RESUME, LINE_ID, SKYPE "
					+ " FROM "
						+ " INTER_SOCIAL "
					+ " WHERE "
						+ " ID_JSK = ? AND ID_RESUME = ?";
		DBManager db = null;
		try
		{
			db = new DBManager();
			social = new Social();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				social.setIdJsk(db.getInt("ID_JSK"));
				social.setIdResume(db.getInt("ID_RESUME"));
				social.setLineId(db.getString("LINE_ID"));
				social.setSkype(db.getString("SKYPE"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db != null)db.close();
		}
		return social;
	}
	public int insert(Social social)
	{
		int result = 0;
		String sql = " INSERT INTO "
						+ " INTER_SOCIAL (ID_JSK, ID_RESUME, LINE_ID, SKYPE) "
					+ " VALUES "
						+ "	(?, ?, ?, ?) ";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, social.getIdJsk());
			db.setInt(2, social.getIdResume());
			db.setString(3, social.getLineId());
			db.setString(4, social.getSkype());
			result = db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db != null)db.close();
		}
		return result;
	}
	public int update(Social social)
	{
		int result = 0;
		String sql = " UPDATE "
						+ " INTER_SOCIAL "
					+ " SET "
						+ "	LINE_ID = ? , SKYPE = ? "
					+ " WHERE "
						+ " ID_JSK = ? AND ID_RESUME = ? ";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, social.getLineId());
			db.setString(2, social.getSkype());
			db.setInt(3, social.getIdJsk());
			db.setInt(4, social.getIdResume());
			result = db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db != null)db.close();
		}
		return result;
	} 
	public int delete(Social social)
	{
		int result = 0 ;
		String sql = " DELETE FROM "
						+ " INTER_SOCIAL "
					+ " WHERE "
						+ " ID_JSK = ? AND ID_RESUME = ? ";
		DBManager db = null ;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, social.getIdJsk());
			db.setInt(2, social.getIdResume());
			result = db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db != null)db.close();
		}
		return result;
	}
	public boolean isExist(int idJsk, int idResume)
	{
		boolean exist = false;
		DBManager db = null;
		String sql = " SELECT "
						+ " * "
					+ " FROM "
						+ " INTER_SOCIAL "
					+ " WHERE "
						+ " ID_JSK = ? AND ID_RESUME = ? ";
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				exist = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db != null)db.close();
		}
		return exist;
	}
}
