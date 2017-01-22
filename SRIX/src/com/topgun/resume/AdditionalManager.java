package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class AdditionalManager
{
	public Additional get(int idJsk,int idResume)
	{
		Additional result = null;
		String SQL= "SELECT " +
					"	ID_JSK, ID_RESUME, HANDICAP_STATUS,HANDICAP_REASON,ILLNESS_REASON,ILLNESS_STATUS," +
					"	FIRE_REASON,FIRE_STATUS,CRIMINAL_STATUS,CRIMINAL_REASON,MILITARY_STATUS," +
					"	DRIVE_STATUS,ID_CARD,ID_DRIVE,ADDITIONAL_INFO " +
					"FROM " +
					"	INTER_ADDITIONAL_PERSONAL " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)";
		
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
				result = new Additional();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setHandicapStatus(db.getString("HANDICAP_STATUS"));
				result.setHandicapReason(db.getString("HANDICAP_REASON"));
				result.setIllnessReason(db.getString("ILLNESS_REASON"));
				result.setIllnessStatus(db.getString("ILLNESS_STATUS"));
				result.setFireReason(db.getString("FIRE_REASON"));
				result.setFireStatus(db.getString("FIRE_STATUS"));
				result.setCriminalReason(db.getString("CRIMINAL_REASON"));
				result.setCriminalStatus(db.getString("CRIMINAL_STATUS"));
				result.setMilitaryStatus(db.getString("MILITARY_STATUS"));
				result.setDriveStatus(db.getString("DRIVE_STATUS"));
				result.setIdCard(db.getString("ID_CARD"));
				result.setIdDrive(db.getString("ID_DRIVE"));
				result.setAdditionalInfo(db.getString("ADDITIONAL_INFO"));
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
	
	public List<Additional> getAll(int idJsk)
	{
		List<Additional> result = new ArrayList<Additional>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_JSK,ID_RESUME,HANDICAP_STATUS,HANDICAP_REASON,ILLNESS_STATUS," +
					"	ILLNESS_REASON,FIRE_STATUS,FIRE_REASON,CRIMINAL_STATUS,CRIMINAL_REASON," +
					"	MILITARY_STATUS,DRIVE_STATUS,ID_DRIVE,ID_CARD,ADDITIONAL_INFO " +
					"FROM " +
					"	INTER_ADDITIONAL_PERSONAL " +
					"WHERE " +
					"	(ID_JSK=?) ORDER BY ID_RESUME";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Additional add=new Additional();
		    	add.setIdJsk(db.getInt("ID_JSK"));
		    	add.setIdResume(db.getInt("ID_RESUME"));
		    	add.setHandicapStatus(db.getString("HANDICAP_STATUS"));
		    	add.setHandicapReason(db.getString("HANDICAP_REASON"));
		    	add.setIllnessStatus(db.getString("ILLNESS_STATUS"));
		    	add.setIllnessReason(db.getString("ILLNESS_REASON"));
		    	add.setFireStatus(db.getString("FIRE_STATUS"));
		    	add.setFireReason(db.getString("FIRE_REASON"));
		    	add.setCriminalStatus(db.getString("CRIMINAL_STATUS"));
		    	add.setCriminalReason(db.getString("CRIMINAL_REASON"));
		    	add.setMilitaryStatus(db.getString("MILITARY_STTAUS"));
		    	add.setDriveStatus(db.getString("DRIVE_STATUS"));
		    	add.setIdCard(db.getString("ID_CARD"));
		    	add.setAdditionalInfo(db.getString("ADDITIONAL_INFO"));
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

	public int add(Additional add)
	{
		int result = 0;
		String SQL ="INSERT INTO INTER_ADDITIONAL_PERSONAL" +
					"(" +
					"	ID_JSK," +
					"	ID_RESUME," +
					"	HANDICAP_STATUS," +
					"	HANDICAP_REASON," +
					"	ILLNESS_REASON," +
					"	ILLNESS_STATUS," +
					"	FIRE_REASON," +
					"	FIRE_STATUS," +
					"	CRIMINAL_REASON," +
					"	CRIMINAL_STATUS," +
					"	MILITARY_STATUS," +
					"	DRIVE_STATUS," +
					"	ID_CARD," +
					"	ID_DRIVE," +
					"	ADDITIONAL_INFO" +
					") " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, add.getIdJsk());
			db.setInt(2, add.getIdResume());
			db.setString(3, add.getHandicapStatus());
			db.setString(4, add.getHandicapReason());
			db.setString(5, add.getIllnessReason());
			db.setString(6, add.getIllnessStatus());
			db.setString(7, add.getFireReason());
			db.setString(8, add.getFireStatus());
			db.setString(9, add.getCriminalReason());
			db.setString(10, add.getCriminalStatus());
			db.setString(11, add.getMilitaryStatus());
			db.setString(12, add.getDriveStatus());
			db.setString(13, add.getIdCard());
			db.setString(14, add.getIdDrive());
			db.setString(15, add.getAdditionalInfo());
			
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
		String SQL = "DELETE FROM INTER_ADDITIONAL_PERSONAL WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
	
	public int delete(Additional adt)
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_ADDITIONAL_PERSONAL WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, adt.getIdJsk());
			db.setInt(2, adt.getIdResume());
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
	
	public int update(Additional add)
	{
		int result = 0;
		String SQL ="UPDATE " +
					"	INTER_ADDITIONAL_PERSONAL " +
					"SET " +
					"	HANDICAP_STATUS=?," +
					"	HANDICAP_REASON=?," +
					"	ILLNESS_REASON=?," +
					"	ILLNESS_STATUS=?," +
					"	FIRE_REASON=?," +
					"	FIRE_STATUS=?," +
					"	CRIMINAL_REASON=?," +
					"	CRIMINAL_STATUS=?," +
					"	MILITARY_STATUS=?," +
					"	DRIVE_STATUS=?," +
					"	ID_CARD=?," +
					"	ID_DRIVE=?, " +
					"	ADDITIONAL_INFO=? " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)";
		
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, add.getHandicapStatus());
			db.setString(2, add.getHandicapReason());
			db.setString(3, add.getIllnessReason());
			db.setString(4, add.getIllnessStatus());
			db.setString(5, add.getFireReason());
			db.setString(6, add.getFireStatus());
			db.setString(7, add.getCriminalReason());
			db.setString(8, add.getCriminalStatus());
			db.setString(9, add.getMilitaryStatus());
			db.setString(10, add.getDriveStatus());
			db.setString(11, add.getIdCard());
			db.setString(12, add.getIdDrive());
			db.setString(13, add.getAdditionalInfo());
			db.setInt(14, add.getIdJsk());
			db.setInt(15, add.getIdResume());
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
