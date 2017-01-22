package javax.topgun.competency;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CheckUpHobbyManager 
{
	public int deleteAll(int idUser) 
	{
		int result = 0;
		String SQL = "DELETE FROM CHKUP_HOBBY WHERE ID_USER=? ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
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
	
	public int deleteNotChoose(int idUser,String idAp) 
	{
		int result = 0;
		String SQL = "DELETE FROM CHKUP_HOBBY WHERE ID_USER=? AND ID_HOBBY NOT IN ("+idAp+") ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
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
	
	public int add(CheckUpHobby hob) 
	{
		int result = 0;
		String SQL = "INSERT INTO CHKUP_HOBBY(ID_USER,ID_HOBBY,ID_GROUP,SKILL) VALUES(?,?,?,?) ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, hob.getIdUser());
			db.setInt(2, hob.getIdHobby());
			db.setInt(3, hob.getIdGroup());
			db.setInt(4, hob.getSkill());
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
	
	public List<CheckUpHobby> getAll(int idUser) 
	{
		List<CheckUpHobby> result = new ArrayList<CheckUpHobby>();
		DBManager db = null;
		String SQL = "SELECT ID_USER,ID_GROUP,ID_HOBBY,SKILL FROM CHKUP_HOBBY WHERE (ID_USER=?)  ORDER BY ID_GROUP,ID_HOBBY ";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idUser);
			db.executeQuery();
			while (db.next()) {
				CheckUpHobby hob = new CheckUpHobby();
				hob.setIdUser(db.getInt("ID_USER"));
				hob.setIdGroup(db.getInt("ID_GROUP"));
				hob.setIdHobby(db.getInt("ID_HOBBY"));
				hob.setSkill(db.getInt("SKILL"));
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
	
	public boolean  checkAptitude(int idUser, int idAptitude) 
	{
		boolean result=false;
		DBManager db = null;
		String SQL = "SELECT ID_HOBBY FROM CHKUP_HOBBY WHERE  ID_USER=? AND ID_HOBBY=?";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idUser);
			db.setInt(2, idAptitude);
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
	
	public List<CheckUpHobby> getAllForLevel(int idUser) 
	{
		List<CheckUpHobby> result = new ArrayList<CheckUpHobby>();
		DBManager db = null;
		String SQL = "SELECT * FROM CHKUP_HOBBY WHERE (ID_USER=?) AND ID_GROUP<>1 ORDER BY ID_GROUP,ID_HOBBY ";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idUser);
			db.executeQuery();
			while (db.next()) {
				CheckUpHobby hob = new CheckUpHobby();
				hob.setIdUser(db.getInt("ID_USER"));
				hob.setIdGroup(db.getInt("ID_GROUP"));
				hob.setIdHobby(db.getInt("ID_HOBBY"));
				hob.setSkill(db.getInt("SKILL"));
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
	
	public int update(CheckUpHobby hob) 
	{
		int result = 0;
		String SQL = "UPDATE CHKUP_HOBBY SET SKILL=? "
			+ "WHERE (ID_USER=?) and (ID_HOBBY=?) and (ID_GROUP = ?)";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, hob.getSkill());
			db.setInt(2, hob.getIdUser());
			db.setInt(3, hob.getIdHobby());
			db.setInt(4, hob.getIdGroup());
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

}
