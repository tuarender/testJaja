package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class FamilyManager 
{
	public Family get(int idJsk, int idResume, int idFamily)
	{	
		Family result=null;
		
		String SQL=	"SELECT " +
					"	ID_JSK, ID_RESUME,ID_FAMILY,FIRSTNAME,LASTNAME,CITIZENSHIP_OTHER,COMPANY,POSITION," +
					"	TELEPHONE,DIED_STATUS,FAMILY_STATUS,MARRIED_STATUS,WORK_STATUS," +
					"	AGE,DELETE_STATUS,TIMESTAMP,SIBLING_RELATIONSHIP " +
					"FROM " +
					"	INTER_FAMILY " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND " +
					"	(ID_FAMILY=?) AND DELETE_STATUS<>'TRUE'";

		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idFamily);
			db.executeQuery();
			if (db.next())
			{
				result=new Family();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdFamily(db.getInt("ID_FAMILY"));
				result.setFirstName(db.getString("FIRSTNAME"));
				result.setLastName(db.getString("LASTNAME"));
				result.setCitizenshipOther(db.getString("CITIZENSHIP_OTHER"));
				result.setCompany(db.getString("COMPANY"));
				result.setPosition(db.getString("POSITION"));
				result.setTelephone(db.getString("TELEPHONE"));
				result.setDiedStatus(db.getString("DIED_STATUS"));
				result.setFamilyStatus(db.getString("FAMILY_STATUS"));
				result.setMarriedStatus(db.getString("MARRIED_STATUS"));
				result.setWorkStatus(db.getString("WORK_STATUS"));
				result.setAge(db.getInt("AGE"));
				result.setTimeStamp(db.getDate("TIMESTAMP"));
				result.setSiblingRelationship(db.getString("SIBLING_RELATIONSHIP"));
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
	
	public List<Family> getAll(int idJsk,int idResume)
	{
		List<Family> result = new ArrayList<Family>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID_FAMILY,FIRSTNAME,LASTNAME,CITIZENSHIP_OTHER," +
					"	COMPANY,POSITION,TELEPHONE,DIED_STATUS,FAMILY_STATUS,MARRIED_STATUS," +
					"	WORK_STATUS,AGE,DELETE_STATUS,TIMESTAMP,SIBLING_RELATIONSHIP " +
					"FROM " +
					"	INTER_FAMILY " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (DELETE_STATUS<>'TRUE') " +
					"ORDER BY " +
					"	ID_FAMILY";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Family fam = new Family();
		    	fam.setIdJsk(db.getInt("ID_JSK"));
		    	fam.setIdResume(db.getInt("ID_RESUME"));
		    	fam.setIdFamily(db.getInt("ID_FAMILY"));
		    	fam.setFirstName(db.getString("FIRSTNAME"));
		    	fam.setLastName(db.getString("LASTNAME"));
		    	fam.setCitizenshipOther(db.getString("CITIZENSHIP_OTHER"));
		    	fam.setCompany(db.getString("COMPANY"));
		    	fam.setPosition(db.getString("POSITION"));
		    	fam.setTelephone(db.getString("TELEPHONE"));
		    	fam.setDiedStatus(db.getString("DIED_STATUS"));
		    	fam.setFamilyStatus(db.getString("FAMILY_STATUS"));
		    	fam.setMarriedStatus(db.getString("MARRIED_STATUS"));
		    	fam.setWorkStatus(db.getString("WORK_STATUS"));
		    	fam.setAge(db.getInt("AGE"));
		    	fam.setTimeStamp(db.getDate("TIMESTAMP"));
		    	fam.setSiblingRelationship(db.getString("SIBLING_RELATIONSHIP"));
		    	result.add(fam);
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
    
	public int add(Family fam)
	{
		int result = 0;
		String SQL=	"INSERT INTO " +
					"	INTER_FAMILY" +
					"	(" +
					"		ID_JSK,ID_RESUME,ID_FAMILY,FIRSTNAME,LASTNAME,CITIZENSHIP_OTHER," +
					"		COMPANY,POSITION,TELEPHONE,DIED_STATUS,FAMILY_STATUS,AGE,SIBLING_RELATIONSHIP," +
					"		WORK_STATUS,MARRIED_STATUS,TIMESTAMP" +
					"	)" +
					"	VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, fam.getIdJsk());
			db.setInt(2, fam.getIdResume());
			db.setInt(3, fam.getIdFamily());
			db.setString(4, fam.getFirstName());
			db.setString(5, fam.getLastName());
			db.setString(6, fam.getCitizenshipOther());
			db.setString(7, fam.getCompany());
			db.setString(8, fam.getPosition());
			db.setString(9, fam.getTelephone());
			db.setString(10, fam.getDiedStatus());
			db.setString(11, fam.getFamilyStatus());
			db.setInt(12, fam.getAge());
			db.setString(13, fam.getSiblingRelationship());
			db.setString(14, fam.getWorkStatus());
			db.setString(15, fam.getMarriedStatus());
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

	public int delete(Family fam)
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_FAMILY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_FAMILY=?)";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, fam.getIdJsk());
			db.setInt(2, fam.getIdResume());
			db.setInt(3,fam.getIdFamily());
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
		String SQL = "DELETE FROM INTER_FAMILY WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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

	public int update(Family fam)
	{
		int result = 0;
		String SQL=	"UPDATE " +
					"	INTER_FAMILY " +
					"SET " +
					"	FIRSTNAME=?," +
					"	LASTNAME=?," +
					"	CITIZENSHIP_OTHER=?," +
					"	COMPANY=?," +
					"	POSITION=?," +
					"	TELEPHONE=?," +
					"	DIED_STATUS=?," +
					"	FAMILY_STATUS=?," +
					"	AGE=?," +
					"	SIBLING_RELATIONSHIP=?," +
					"	WORK_STATUS=?," +
					"	MARRIED_STATUS=?," +
					"	TIMESTAMP=SYSDATE " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_FAMILY=?)";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, fam.getFirstName());
			db.setString(2, fam.getLastName());
			db.setString(3, fam.getCitizenshipOther());
			db.setString(4, fam.getCompany());
			db.setString(5, fam.getPosition());
			db.setString(6, fam.getTelephone());
			db.setString(7, fam.getDiedStatus());
			db.setString(8, fam.getFamilyStatus());
			db.setInt(9, fam.getAge());
			db.setString(10, fam.getSiblingRelationship());
			db.setString(11, fam.getWorkStatus());
			db.setString(12, fam.getMarriedStatus());
			db.setInt(13, fam.getIdJsk());
			db.setInt(14, fam.getIdResume());
			db.setInt(15, fam.getIdFamily());
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
	
	public List<Family> getFamilySibling(int idJsk,int idResume)
	{
		List<Family> result = new ArrayList<Family>();
		String SQL="SELECT * FROM INTER_FAMILY WHERE (ID_JSK=?) AND (ID_RESUME=?)  AND (ID_FAMILY <> '1') AND (ID_FAMILY <> '2') AND (DELETE_STATUS<>'TRUE') AND (FAMILY_STATUS = 'SIBLING') ORDER BY ID_FAMILY ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{
		    	Family fam = new Family();
		    	fam.setIdJsk(db.getInt("ID_JSK"));
		    	fam.setIdResume(db.getInt("ID_RESUME"));
		    	fam.setIdFamily(db.getInt("ID_FAMILY"));
		    	fam.setFirstName(db.getString("FIRSTNAME"));
		    	fam.setLastName(db.getString("LASTNAME"));
		    	fam.setCitizenshipOther(db.getString("CITIZENSHIP_OTHER"));
		    	fam.setCompany(db.getString("COMPANY"));
		    	fam.setPosition(db.getString("POSITION"));
		    	fam.setTelephone(db.getString("TELEPHONE"));
		    	fam.setDiedStatus(db.getString("DIED_STATUS"));
		    	fam.setFamilyStatus(db.getString("FAMILY_STATUS"));
		    	fam.setMarriedStatus(db.getString("MARRIED_STATUS"));
		    	fam.setWorkStatus(db.getString("WORK_STATUS"));
		    	fam.setAge(db.getInt("AGE"));
		    	fam.setTimeStamp(db.getDate("TIMESTAMP"));
		    	fam.setSiblingRelationship(db.getString("SIBLING_RELATIONSHIP"));
		    	result.add(fam);
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
	
	public int getIdFamilyMarried(int idJsk,int idResume)
	{
		int result=0;
		String SQL="SELECT ID_FAMILY FROM INTER_FAMILY WHERE ID_JSK=? AND ID_RESUME=? AND (DELETE_STATUS<>'TRUE') AND (FAMILY_STATUS = 'SPOUSE')";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ID_FAMILY");
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
	
	public List<Family> getFamilyChild(int idJsk,int idResume)
	{
		List<Family> result = new ArrayList<Family>();
		String SQL=	"SELECT * FROM INTER_FAMILY WHERE ID_JSK=? and ID_RESUME=?  AND (ID_FAMILY <> '1') AND " +
					"(ID_FAMILY <> '2') AND (DELETE_STATUS<>'TRUE') AND (FAMILY_STATUS = 'CHILD') ORDER BY ID_FAMILY";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{
				Family fam = new Family();
		    	fam.setIdJsk(db.getInt("ID_JSK"));
		    	fam.setIdResume(db.getInt("ID_RESUME"));
		    	fam.setIdFamily(db.getInt("ID_FAMILY"));
		    	fam.setFirstName(db.getString("FIRSTNAME"));
		    	fam.setLastName(db.getString("LASTNAME"));
		    	fam.setCitizenshipOther(db.getString("CITIZENSHIP_OTHER"));
		    	fam.setCompany(db.getString("COMPANY"));
		    	fam.setPosition(db.getString("POSITION"));
		    	fam.setTelephone(db.getString("TELEPHONE"));
		    	fam.setDiedStatus(db.getString("DIED_STATUS"));
		    	fam.setFamilyStatus(db.getString("FAMILY_STATUS"));
		    	fam.setMarriedStatus(db.getString("MARRIED_STATUS"));
		    	fam.setWorkStatus(db.getString("WORK_STATUS"));
		    	fam.setAge(db.getInt("AGE"));
		    	fam.setTimeStamp(db.getDate("TIMESTAMP"));
		    	fam.setSiblingRelationship(db.getString("SIBLING_RELATIONSHIP"));
		    	result.add(fam);
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
	
    public int getNextId(int idJsk,int idResume)
	{
		int result=3;
		String SQL="SELECT MAX(ID_FAMILY) AS MAXID FROM INTER_FAMILY WHERE ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				if(db.getString("MAXID")!=null)
				{
					if(db.getInt("MAXID")<3){
						result = 3;
					}
					else{
						result=db.getInt("MAXID")+1;
					}
				}
				else 
				{
					result = 3;
				}
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
