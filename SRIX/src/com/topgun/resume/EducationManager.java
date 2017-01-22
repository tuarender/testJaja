package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class EducationManager 
{
	public int add(Education edu)
	{
		int result = 0;
		String sql=	"INSERT INTO INTER_EDUCATION(ID_JSK, ID_RESUME, ID_SCHOOL, ID_DEGREE, " +
					"ID_FAC_MAJOR, OTHER_FACULTY, ID_MAJOR, OTHER_MAJOR, ID_FAC_MINOR, ID_MINOR, " +
					"OTHER_MINOR, FINISH_DATE, START_DATE, ID, OTHER_SCHOOL, ID_COUNTRY, GPA, UNIT, " +
					"AWARD, ID_STATE, REF_ID, GRADE_A, GRADE_B, GRADE_C, GRADE_F,OTHER_UNIT,ID_STUDENT,ID_REFER,HASH_REFER) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, edu.getIdJsk());
			db.setInt(2, edu.getIdResume());
			db.setInt(3, edu.getIdSchool());
			db.setInt(4, edu.getIdDegree());
			db.setInt(5, edu.getIdFacMajor());
			db.setString(6, edu.getOtherFaculty());
			db.setInt(7, edu.getIdMajor());
			db.setString(8, edu.getOtherMajor());
			db.setInt(9, edu.getIdFacMinor());
			db.setInt(10, edu.getIdMinor());
			db.setString(11, edu.getOtherMinor());
			db.setDate(12, edu.getFinishDate());
			db.setDate(13, edu.getStartDate());
			db.setInt(14, getNextId(edu.getIdJsk()));
			db.setString(15, edu.getOtherSchool());
			db.setInt(16, edu.getIdCountry());
			db.setFloat(17, edu.getGpa());
			db.setString(18, edu.getUnit());
			db.setString(19, edu.getAward());
			db.setInt(20, edu.getIdState());
			db.setInt(21, edu.getRefId());
			db.setInt(22, edu.getGrade_a());
			db.setInt(23, edu.getGrade_b());
			db.setInt(24, edu.getGrade_c());
			db.setInt(25, edu.getGrade_f());
			db.setString(26, edu.getOtherUnit());
			db.setString(27, edu.getIdStudent());
			db.setInt(28, edu.getIdRefer());
			db.setString(29, edu.getHashRefer());
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

	public int update(Education edu)
	{
		int result = 0;
		String sql=	"UPDATE " +
					"	INTER_EDUCATION " +
					"SET " +
					"	ID_SCHOOL=?, " +
					"	ID_DEGREE=?, " +
					"	ID_FAC_MAJOR=?, " +
					"	OTHER_FACULTY=?, " +
					"	ID_MAJOR=?, " +
					"	OTHER_MAJOR=?, " +
					"	ID_FAC_MINOR=?, " +
					"	ID_MINOR=?, " +
					"	OTHER_MINOR=?, " +
					"	FINISH_DATE=?, " +
					"	START_DATE=?, " +
					"	OTHER_SCHOOL=?, " +
					"	ID_COUNTRY=?, " +
					"	GPA=?, " +
					"	UNIT=?, " +
					"	AWARD=?, " +
					"	ID_STATE=?, " +
					"	REF_ID=?, " +
					"	GRADE_A=?, " +
					"	GRADE_B=?, " +
					"	GRADE_C=?, " +
					"	GRADE_F=?, " +
					"	OTHER_UNIT=?, " +
					"	ID_STUDENT=?, " +
					"	ID_REFER=?, " +
					"	HASH_REFER=? " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, edu.getIdSchool());
			db.setInt(2, edu.getIdDegree());
			db.setInt(3, edu.getIdFacMajor());
			db.setString(4, edu.getOtherFaculty());
			db.setInt(5, edu.getIdMajor());
			db.setString(6, edu.getOtherMajor());
			db.setInt(7, edu.getIdFacMinor());
			db.setInt(8, edu.getIdMinor());
			db.setString(9, edu.getOtherMinor());
			db.setDate(10, edu.getFinishDate());
			db.setDate(11, edu.getStartDate());
			db.setString(12, edu.getOtherSchool());
			db.setInt(13, edu.getIdCountry());
			db.setFloat(14, edu.getGpa());
			db.setString(15, edu.getUnit());
			db.setString(16, edu.getAward());
			db.setInt(17, edu.getIdState());
			db.setInt(18, edu.getRefId());
			db.setInt(19, edu.getGrade_a());
			db.setInt(20, edu.getGrade_b());
			db.setInt(21, edu.getGrade_c());
			db.setInt(22, edu.getGrade_f());
			db.setString(23, edu.getOtherUnit());
			db.setString(24, edu.getIdStudent());
			db.setInt(25, edu.getIdRefer());
			db.setString(26, edu.getHashRefer());
			db.setInt(27, edu.getIdJsk());
			db.setInt(28, edu.getIdResume());
			db.setInt(29, edu.getId());
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
	
	public int delete(int idJsk, int idResume, int id)
	{
		int result = 0;
		String sql="DELETE FROM INTER_EDUCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID=?";
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, id);
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
		String sql="DELETE FROM INTER_EDUCATION WHERE ID_JSK=? AND ID_RESUME=?";
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
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
	
	public Education get(int idJsk,int idResume, int id)
	{
		Education result=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_SCHOOL, ID_DEGREE, ID_FAC_MAJOR, OTHER_FACULTY, " +
					"	ID_MAJOR, OTHER_MAJOR, ID_FAC_MINOR, ID_MINOR, OTHER_MINOR, FINISH_DATE, " +
					"	START_DATE, ID, OTHER_SCHOOL, ID_COUNTRY, GPA, UNIT, AWARD, ID_STATE, " +
					"	REF_ID, GRADE_A, GRADE_B, GRADE_C, GRADE_F,DELETE_STATUS,OTHER_UNIT,ID_STUDENT,ID_REFER,HASH_REFER " +
					"FROM " +
					"	INTER_EDUCATION " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?) AND (DELETE_STATUS<>'TRUE')";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, id);
			db.executeQuery();
			if(db.next())
			{ 
				result = new Education();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdSchool(db.getInt("ID_SCHOOL"));
				result.setIdDegree(db.getInt("ID_DEGREE"));
				result.setIdFacMajor(db.getInt("ID_FAC_MAJOR"));
				result.setOtherFaculty(db.getString("OTHER_FACULTY"));
				result.setIdMajor(db.getInt("ID_MAJOR"));
				result.setOtherMajor(db.getString("OTHER_MAJOR"));
				result.setIdFacMinor(db.getInt("ID_FAC_MINOR"));
				result.setIdMinor(db.getInt("ID_MINOR"));
				result.setOtherMinor(db.getString("OTHER_MINOR"));
				result.setFinishDate(db.getDate("FINISH_DATE"));
				result.setStartDate(db.getDate("START_DATE"));
				result.setId(db.getInt("ID"));
				result.setOtherSchool(db.getString("OTHER_SCHOOL"));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setGpa(db.getFloat("GPA"));
				result.setUnit(db.getString("UNIT"));
				result.setAward(db.getString("AWARD"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setGrade_a(db.getInt("GRADE_A"));
				result.setGrade_b(db.getInt("GRADE_B"));
				result.setGrade_c(db.getInt("GRADE_C"));
				result.setGrade_f(db.getInt("GRADE_F"));
				result.setDeleteStatus(db.getString("DELETE_STATUS"));
				result.setOtherUnit(db.getString("OTHER_UNIT"));
				result.setIdStudent(db.getString("ID_STUDENT"));
				result.setIdRefer(db.getInt("ID_REFER"));
				result.setHashRefer(db.getString("HASH_REFER"));
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
	
	public List<Education> getAll(int idJsk, int idResume)
	{
		List<Education> result = new ArrayList<Education>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_SCHOOL, A.ID_DEGREE, ID_FAC_MAJOR, OTHER_FACULTY, " +
					"	ID_MAJOR, OTHER_MAJOR, ID_FAC_MINOR, ID_MINOR, OTHER_MINOR, FINISH_DATE, " +
					"	START_DATE, ID, OTHER_SCHOOL, ID_COUNTRY, GPA, UNIT, AWARD, ID_STATE, " +
					"	REF_ID, GRADE_A, GRADE_B, GRADE_C, GRADE_F,OTHER_UNIT,ID_STUDENT,ID_REFER,HASH_REFER " +
					"FROM " +
					"	INTER_EDUCATION A,INTER_DEGREE B " +
					"WHERE " +
					"	(ID_JSK=?) AND A.ID_DEGREE = B.ID_DEGREE AND (ID_RESUME=?) AND (DELETE_STATUS<>'TRUE') " +
					"ORDER BY " +
					"	DEGREE_ORDER";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Education edu = new Education();
				edu.setIdJsk(db.getInt("ID_JSK"));
				edu.setIdResume(db.getInt("ID_RESUME"));
				edu.setIdSchool(db.getInt("ID_SCHOOL"));
				edu.setIdDegree(db.getInt("ID_DEGREE"));
				edu.setIdFacMajor(db.getInt("ID_FAC_MAJOR"));
				edu.setOtherFaculty(db.getString("OTHER_FACULTY"));
				edu.setIdMajor(db.getInt("ID_MAJOR"));
				edu.setOtherMajor(db.getString("OTHER_MAJOR"));
				edu.setIdFacMinor(db.getInt("ID_FAC_MINOR"));
				edu.setIdMinor(db.getInt("ID_MINOR"));
				edu.setOtherMinor(db.getString("OTHER_MINOR"));
				edu.setFinishDate(db.getDate("FINISH_DATE"));
				edu.setStartDate(db.getDate("START_DATE"));
				edu.setId(db.getInt("ID"));
				edu.setOtherSchool(db.getString("OTHER_SCHOOL"));
				edu.setIdCountry(db.getInt("ID_COUNTRY"));
				edu.setGpa(db.getFloat("GPA"));
				edu.setUnit(db.getString("UNIT"));
				edu.setAward(db.getString("AWARD"));
				edu.setIdState(db.getInt("ID_STATE"));
				edu.setRefId(db.getInt("REF_ID"));
				edu.setGrade_a(db.getInt("GRADE_A"));
				edu.setGrade_b(db.getInt("GRADE_B"));
				edu.setGrade_c(db.getInt("GRADE_C"));
				edu.setGrade_f(db.getInt("GRADE_F"));
				edu.setOtherUnit(db.getString("OTHER_UNIT"));
				edu.setIdStudent(db.getString("ID_STUDENT"));
				edu.setIdRefer(db.getInt("ID_REFER"));
				edu.setHashRefer(db.getString("HASH_REFER"));
				result.add(edu);
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
	
	public Education getEucationReference(int idJsk,int idResume,int idRefer)
	{
		Education result=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_SCHOOL, ID_DEGREE, ID_FAC_MAJOR, OTHER_FACULTY, " +
					"	ID_MAJOR, OTHER_MAJOR, ID_FAC_MINOR, ID_MINOR, OTHER_MINOR, FINISH_DATE, " +
					"	START_DATE, ID, OTHER_SCHOOL, ID_COUNTRY, GPA, UNIT, AWARD, ID_STATE, " +
					"	REF_ID, GRADE_A, GRADE_B, GRADE_C, GRADE_F,DELETE_STATUS,OTHER_UNIT,ID_STUDENT,ID_REFER,HASH_REFER " +
					"FROM " +
					"	INTER_EDUCATION " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_REFER=?) AND (DELETE_STATUS<>'TRUE')";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idRefer);
			db.executeQuery();
			if(db.next())
			{ 
				result = new Education();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdSchool(db.getInt("ID_SCHOOL"));
				result.setIdDegree(db.getInt("ID_DEGREE"));
				result.setIdFacMajor(db.getInt("ID_FAC_MAJOR"));
				result.setOtherFaculty(db.getString("OTHER_FACULTY"));
				result.setIdMajor(db.getInt("ID_MAJOR"));
				result.setOtherMajor(db.getString("OTHER_MAJOR"));
				result.setIdFacMinor(db.getInt("ID_FAC_MINOR"));
				result.setIdMinor(db.getInt("ID_MINOR"));
				result.setOtherMinor(db.getString("OTHER_MINOR"));
				result.setFinishDate(db.getDate("FINISH_DATE"));
				result.setStartDate(db.getDate("START_DATE"));
				result.setId(db.getInt("ID"));
				result.setOtherSchool(db.getString("OTHER_SCHOOL"));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setGpa(db.getFloat("GPA"));
				result.setUnit(db.getString("UNIT"));
				result.setAward(db.getString("AWARD"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setGrade_a(db.getInt("GRADE_A"));
				result.setGrade_b(db.getInt("GRADE_B"));
				result.setGrade_c(db.getInt("GRADE_C"));
				result.setGrade_f(db.getInt("GRADE_F"));
				result.setDeleteStatus(db.getString("DELETE_STATUS"));
				result.setOtherUnit(db.getString("OTHER_UNIT"));
				result.setIdStudent(db.getString("ID_STUDENT"));
				result.setIdRefer(db.getInt("ID_REFER"));
				result.setHashRefer(db.getString("HASH_REFER"));
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
	
	public Education getEucationHashReference(int idJsk,int idResume,String hashRefer)
	{
		Education result=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_SCHOOL, ID_DEGREE, ID_FAC_MAJOR, OTHER_FACULTY, " +
					"	ID_MAJOR, OTHER_MAJOR, ID_FAC_MINOR, ID_MINOR, OTHER_MINOR, FINISH_DATE, " +
					"	START_DATE, ID, OTHER_SCHOOL, ID_COUNTRY, GPA, UNIT, AWARD, ID_STATE, " +
					"	REF_ID, GRADE_A, GRADE_B, GRADE_C, GRADE_F,DELETE_STATUS,OTHER_UNIT,ID_STUDENT,ID_REFER,HASH_REFER " +
					"FROM " +
					"	INTER_EDUCATION " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (HASH_REFER like ?) AND (DELETE_STATUS<>'TRUE')";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setString(3, hashRefer);
			db.executeQuery();
			if(db.next())
			{ 
				result = new Education();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdSchool(db.getInt("ID_SCHOOL"));
				result.setIdDegree(db.getInt("ID_DEGREE"));
				result.setIdFacMajor(db.getInt("ID_FAC_MAJOR"));
				result.setOtherFaculty(db.getString("OTHER_FACULTY"));
				result.setIdMajor(db.getInt("ID_MAJOR"));
				result.setOtherMajor(db.getString("OTHER_MAJOR"));
				result.setIdFacMinor(db.getInt("ID_FAC_MINOR"));
				result.setIdMinor(db.getInt("ID_MINOR"));
				result.setOtherMinor(db.getString("OTHER_MINOR"));
				result.setFinishDate(db.getDate("FINISH_DATE"));
				result.setStartDate(db.getDate("START_DATE"));
				result.setId(db.getInt("ID"));
				result.setOtherSchool(db.getString("OTHER_SCHOOL"));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setGpa(db.getFloat("GPA"));
				result.setUnit(db.getString("UNIT"));
				result.setAward(db.getString("AWARD"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setGrade_a(db.getInt("GRADE_A"));
				result.setGrade_b(db.getInt("GRADE_B"));
				result.setGrade_c(db.getInt("GRADE_C"));
				result.setGrade_f(db.getInt("GRADE_F"));
				result.setDeleteStatus(db.getString("DELETE_STATUS"));
				result.setOtherUnit(db.getString("OTHER_UNIT"));
				result.setIdStudent(db.getString("ID_STUDENT"));
				result.setIdRefer(db.getInt("ID_REFER"));
				result.setHashRefer(db.getString("HASH_REFER"));
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
	
	public int chkExistEducation(Education edu)
	{
		int result = 0 ;
		if(edu!=null)
		{
			if(edu.getIdSchool()!=-1 && edu.getIdFacMajor()!=-1 && edu.getIdMajor()!=-1)
			{
				DBManager db = null;
				String sql = "";
				try{
					sql = "SELECT * FROM INTER_EDUCATION WHERE ID_JSK = ? AND ID_RESUME = ? AND ID_SCHOOL = ? AND ID_FAC_MAJOR = ? AND ID_MAJOR = ? AND ID_DEGREE = ? ";
					db = new DBManager();
					db.createPreparedStatement(sql);
					db.setInt(1, edu.getIdJsk());
					db.setInt(2, edu.getIdResume());
					db.setInt(3, edu.getIdSchool());
					db.setInt(4, edu.getIdFacMajor());
					db.setInt(5, edu.getIdMajor());
					db.setInt(6, edu.getIdDegree());
					db.executeQuery();
					if(db.next())
					{
						result = db.getInt("ID");
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}finally
				{
					db.close();
				}
			}
		}
		return result ;
	}
	
	public Education getHighestEducation(int idJsk, int idResume)
	{
		Education result = null;
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID_SCHOOL, A.ID_DEGREE, ID_FAC_MAJOR, OTHER_FACULTY, " +
					"	ID_MAJOR, OTHER_MAJOR, ID_FAC_MINOR, ID_MINOR, OTHER_MINOR, FINISH_DATE, " +
					"	START_DATE, ID, OTHER_SCHOOL, ID_COUNTRY, GPA, UNIT, AWARD, ID_STATE, " +
					"	REF_ID, GRADE_A, GRADE_B, GRADE_C, GRADE_F,OTHER_UNIT,A.ID_STUDENT " +
					"FROM " +
					"	INTER_EDUCATION A,INTER_DEGREE B " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (DELETE_STATUS<>'TRUE') AND A.ID_DEGREE=B.ID_DEGREE " +
					"ORDER BY " +
					"	B.DEGREE_ORDER,FINISH_DATE DESC";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    if(db.next())
		    {
		    	result = new Education();
		    	result.setIdJsk(db.getInt("ID_JSK"));
		    	result.setIdResume(db.getInt("ID_RESUME"));
		    	result.setIdSchool(db.getInt("ID_SCHOOL"));
		    	result.setIdDegree(db.getInt("ID_DEGREE"));
		    	result.setIdFacMajor(db.getInt("ID_FAC_MAJOR"));
		    	result.setOtherFaculty(db.getString("OTHER_FACULTY"));
		    	result.setIdMajor(db.getInt("ID_MAJOR"));
		    	result.setOtherMajor(db.getString("OTHER_MAJOR"));
		    	result.setIdFacMinor(db.getInt("ID_FAC_MINOR"));
		    	result.setIdMinor(db.getInt("ID_MINOR"));
		    	result.setOtherMinor(db.getString("OTHER_MINOR"));
		    	result.setFinishDate(db.getDate("FINISH_DATE"));
		    	result.setStartDate(db.getDate("START_DATE"));
		    	result.setId(db.getInt("ID"));
		    	result.setOtherSchool(db.getString("OTHER_SCHOOL"));
		    	result.setIdCountry(db.getInt("ID_COUNTRY"));
		    	result.setGpa(db.getFloat("GPA"));
		    	result.setUnit(db.getString("UNIT"));
		    	result.setAward(db.getString("AWARD"));
		    	result.setIdState(db.getInt("ID_STATE"));
		    	result.setRefId(db.getInt("REF_ID"));
		    	result.setGrade_a(db.getInt("GRADE_A"));
		    	result.setGrade_b(db.getInt("GRADE_B"));
		    	result.setGrade_c(db.getInt("GRADE_C"));
		    	result.setGrade_f(db.getInt("GRADE_F"));
		    	result.setOtherUnit(db.getString("OTHER_UNIT"));
		    	result.setIdStudent(db.getString("ID_STUDENT"));
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
	
	public int count(int idJsk, int idResume)
	{
		int result=0;
		DBManager db=null;
		String sql=	"SELECT COUNT(ID) AS TOTAL FROM INTER_EDUCATION WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (DELETE_STATUS<>'TRUE')";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
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
	
	public int getNextId(int idJsk)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT MAX(ID) AS MAXID FROM INTER_EDUCATION WHERE ID_JSK=?");
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("MAXID")+1;
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
	
	public boolean chkBachelorDegree(int idJsk,int idResume)
	{
		boolean result=false;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID FROM INTER_EDUCATION WHERE ID_DEGREE IN (1,2,4,5,14,15) AND ID_JSK = ? AND ID_RESUME = ?");
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
	
	public boolean chkMasterDegree(int idJsk,int idResume)
	{
		boolean result=false;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID FROM INTER_EDUCATION WHERE ID_DEGREE IN (3) AND ID_JSK = ? AND ID_RESUME = ?");
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
	
	public boolean chkDoctorDegree(int idJsk,int idResume)
	{
		boolean result=false;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID FROM INTER_EDUCATION WHERE ID_DEGREE IN (7) AND ID_JSK = ? AND ID_RESUME = ?");
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
