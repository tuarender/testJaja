package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class HobbyManager 
{
	public Hobby get(int idJsk, int idResume, int idHobby) 
	{
		Hobby result = null;
		String SQL = "SELECT * FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_HOBBY=?) ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idHobby);
			db.executeQuery();
			if (db.next()) 
			{
				result = new Hobby();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdHobby(db.getInt("ID_HOBBY"));
				result.setHobbyOrder(db.getInt("HOBBY_ORDER"));
				result.setIdGroup(db.getInt("ID_GROUP"));
				result.setSkill(db.getInt("SKILL"));
				result.setOthHobby(db.getString("OTHER_HOBBY"));
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

	public Hobby get(int idJsk, int idResume, int idHobby, int idGroup) 
	{
		Hobby result = null;
		String SQL = "SELECT *FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_HOBBY=?) AND (ID_GROUP =?) ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idHobby);
			db.setInt(4, idGroup);
			db.executeQuery();
			if (db.next()) 
			{
				result = new Hobby();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdHobby(db.getInt("ID_HOBBY"));
				result.setHobbyOrder(db.getInt("HOBBY_ORDER"));
				result.setIdGroup(db.getInt("ID_GROUP"));
				result.setSkill(db.getInt("SKILL"));
				result.setOthHobby(db.getString("OTHER_HOBBY"));
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

	public List<Hobby> getAll(int idJsk, int idResume) 
	{
		List<Hobby> result = new ArrayList<Hobby>();
		DBManager db = null;
		String SQL = "SELECT ID_JSK,ID_RESUME,ID_GROUP,ID_HOBBY,hobby_order,other_hobby,skill FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) ORDER BY ID_GROUP,ID_HOBBY ";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while (db.next()) {
				Hobby hob = new Hobby();
				hob.setIdJsk(db.getInt("ID_JSK"));
				hob.setIdResume(db.getInt("ID_RESUME"));
				hob.setIdGroup(db.getInt("ID_GROUP"));
				hob.setIdHobby(db.getInt("ID_HOBBY"));
				hob.setHobbyOrder(db.getInt("hobby_order"));
				hob.setOthHobby(db.getString("other_hobby"));
				hob.setSkill(db.getInt("skill"));
				result.add(hob);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}
	
	public List<Hobby> getAllForLevel(int idJsk, int idResume) 
	{
		List<Hobby> result = new ArrayList<Hobby>();
		DBManager db = null;
		String SQL = "SELECT ID_JSK,ID_RESUME,ID_GROUP,ID_HOBBY,hobby_order,other_hobby,skill FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND ID_GROUP<>1 ORDER BY ID_GROUP,ID_HOBBY ";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while (db.next()) {
				Hobby hob = new Hobby();
				hob.setIdJsk(db.getInt("ID_JSK"));
				hob.setIdResume(db.getInt("ID_RESUME"));
				hob.setIdGroup(db.getInt("ID_GROUP"));
				hob.setIdHobby(db.getInt("ID_HOBBY"));
				hob.setHobbyOrder(db.getInt("hobby_order"));
				hob.setOthHobby(db.getString("other_hobby"));
				hob.setSkill(db.getInt("skill"));
				result.add(hob);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}
	
	public List<Hobby> getAllRank(int idJsk, int idResume) 
	{
		List<Hobby> result = new ArrayList<Hobby>();
		DBManager db = null;
		String SQL = "SELECT * FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND HOBBY_ORDER IS NOT NULL  AND HOBBY_ORDER > 0 ORDER BY HOBBY_ORDER ";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while (db.next()) {
				Hobby hob = new Hobby();
				hob.setIdJsk(db.getInt("ID_JSK"));
				hob.setIdResume(db.getInt("ID_RESUME"));
				hob.setIdGroup(db.getInt("ID_GROUP"));
				hob.setIdHobby(db.getInt("ID_HOBBY"));
				hob.setHobbyOrder(db.getInt("hobby_order"));
				hob.setOthHobby(db.getString("other_hobby"));
				hob.setSkill(db.getInt("skill"));
				result.add(hob);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}

	public List<Hobby> getByGroup(int idJsk, int idResume, int idGroup) 
	{
		List<Hobby> result = new ArrayList<Hobby>();
		DBManager db = null;
		String SQL = "SELECT ID_JSK,ID_RESUME,ID_GROUP,ID_HOBBY,hobby_order,other_hobby,skill FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_GROUP=?) ORDER BY ID_GROUP,ID_HOBBY ";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idGroup);
			db.executeQuery();
			while (db.next()) {
				Hobby hob = new Hobby();
				hob.setIdJsk(db.getInt("ID_JSK"));
				hob.setIdResume(db.getInt("ID_RESUME"));
				hob.setIdGroup(db.getInt("ID_GROUP"));
				hob.setIdHobby(db.getInt("ID_HOBBY"));
				hob.setHobbyOrder(db.getInt("hobby_order"));
				hob.setOthHobby(db.getString("other_hobby"));
				hob.setSkill(db.getInt("skill"));
				result.add(hob);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}
	
	public int add(Hobby hob) 
	{
		int result = 0;
		String SQL = "INSERT INTO INTER_HOBBY(ID_JSK,ID_RESUME,ID_HOBBY,ID_GROUP,HOBBY_ORDER,OTHER_HOBBY,SKILL) VALUES(?,?,?,?,?,?,?) ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, hob.getIdJsk());
			db.setInt(2, hob.getIdResume());
			db.setInt(3, hob.getIdHobby());
			db.setInt(4, hob.getIdGroup());
			db.setInt(5, hob.getHobbyOrder());
			db.setString(6, hob.getOthHobby());
			db.setInt(7, hob.getSkill());
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

	public int delete(Hobby hob) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_HOBBY WHERE "
			+ "(ID_JSK=?) AND (ID_RESUME=?) AND (ID_HOBBY=?) AND (ID_GROUP=?)";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, hob.getIdJsk());
			db.setInt(2, hob.getIdResume());
			db.setInt(3, hob.getIdHobby());
			db.setInt(4, hob.getIdGroup());
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
	
	public int deleteGroup(int idJsk, int idResume, int idGroup) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_GROUP=?) ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idJsk);
			db.setInt(2,idResume);
			db.setInt(3,idGroup);
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
	
	public int deleteAll(int idJsk, int idResume) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idJsk);
			db.setInt(2,idResume);
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
	
	public int deleteAllHbOther(int idJsk, int idResume) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND ID_HOBBY<0";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idJsk);
			db.setInt(2,idResume);
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

	public int update(Hobby hob) 
	{
		int result = 0;
		String SQL = "UPDATE INTER_HOBBY SET HOBBY_ORDER=?,OTHER_HOBBY=?,SKILL=? "
			+ "WHERE (ID_JSK=?) and (ID_RESUME=?) and (ID_HOBBY=?) and (ID_GROUP = ?)";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, hob.getHobbyOrder());
			db.setString(2, hob.getOthHobby());
			db.setInt(3, hob.getSkill());
			db.setInt(4, hob.getIdJsk());
			db.setInt(5, hob.getIdResume());
			db.setInt(6, hob.getIdHobby());
			db.setInt(7, hob.getIdGroup());
			result = db.executeUpdate();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}
	
	public int updateOrderAllHobby(int idJsk, int idResume) 
	{
		int result = 0;
		String SQL = "UPDATE INTER_HOBBY SET HOBBY_ORDER=0"
			+ "WHERE (ID_JSK=?) and (ID_RESUME=?)";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			result = db.executeUpdate();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}
	
	public boolean  checkHobby(int idJsk, int idResume, int idHobby, int idGroup) 
	{
		boolean result=false;
		DBManager db = null;
		String SQL = "SELECT ID_HOBBY FROM INTER_HOBBY WHERE  ID_JSK=? AND ID_RESUME=? AND ID_HOBBY=? AND ID_GROUP=?";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idHobby);
			db.setInt(4, idGroup);
			db.executeQuery();
			if (db.next()) {
				result=true;
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}
	
	public int deleteNotChoose(int idJsk, int idResume,String idAp) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_HOBBY WHERE ID_JSK=? AND ID_RESUME=? AND ID_HOBBY NOT IN ("+idAp+") AND ID_HOBBY > 0 ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idJsk);
			db.setInt(2,idResume);
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
	
	public int deleteNotChoose(int idJsk, int idResume) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_HOBBY WHERE ID_JSK=? AND ID_RESUME=? AND ID_HOBBY > 0 ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idJsk);
			db.setInt(2,idResume);
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
