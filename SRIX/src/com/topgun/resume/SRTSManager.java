package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.shris.masterdata.Faculty;
import com.topgun.shris.masterdata.Major;
import com.topgun.shris.masterdata.School;
import com.topgun.util.DBManager;

public class SRTSManager 
{
	public SRTS getStudentByIdJdk(int idJsk)
    {
		SRTS result=null;
    	String SQL=	"	SELECT  *"
    			+ "	FROM "
    			+ "		SRTS_STUDENT "
    			+ "	WHERE ID_JSK=?"; 
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new SRTS();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdStu(db.getString("ID_STU"));
    			result.setIdUnv(db.getInt("ID_UNV"));
    			result.setIdFac(db.getInt("ID_FAC"));
    			result.setIdMajor(db.getInt("ID_MAJOR"));
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
	public boolean checkStudentExist(int idUnv, int idFac, int idMajor, String academicYear, String idStu)
	{
		boolean result=false;
		String SQL="SELECT * "
				+ "	FROM "
				+ "		SRTS_STUDENT "
				+ "	WHERE "
				+ "		ID_UNV=? AND ID_FAC=? AND ID_MAJOR=? AND ACADEMIC_YEAR=? AND ID_STU=?";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idUnv);
    		db.setInt(2, idFac);
    		db.setInt(3, idMajor);
    		db.setString(4, academicYear);
    		db.setString(5, idStu);
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
	
	
	public List<School> getAllSchool(int idLang, int degree)
    {
		List<School> result=new ArrayList<School>();
    	String SQL=	"SELECT "
    			+ "		ID_SCHOOL,SCHOOL_NAME "
    			+ "	FROM  INTER_SCHOOL_LANGUAGE "
    			+ "	WHERE ID_COUNTRY=216 AND "
    			+ "	ID_LANGUAGE=? AND " 
    			+ "	ID_SCHOOL IN  ( SELECT ID_SCHOOL FROM INTER_UNV_FACULTY WHERE ID_DEGREE=? GROUP BY ID_SCHOOL) "
    			+ "	ORDER BY SCHOOL_NAME"; 
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idLang); 
    		db.setInt(2, degree);
    		db.executeQuery();
    		while(db.next())
    		{
    			School school=new School();
    			school.setIdSchool(db.getInt("ID_SCHOOL"));
    			school.setSchoolName(db.getString("SCHOOL_NAME"));
    			result.add(school);
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
	
	public static List<Faculty> getAllFaculty(int idLanguage,int idSchool, int degree)
	{
		List<Faculty> result=new ArrayList<Faculty>();
		List<Faculty> rsExclusive=getFaculty(idLanguage, idSchool, degree);
		if(rsExclusive.size()>0)
		{
			result.addAll(rsExclusive);
		}
		List<Faculty> rsFaculty=getAllFaculty(idLanguage, degree);
		if(rsFaculty.size()>0)
		{
			result.addAll(rsFaculty);
		}
		return result;
	}
	public static List<Major> getAllMajor(int idLanguage,int idSchool,int idFac, int degree)
	{
		List<Major> result=new ArrayList<Major>();
		List<Major> rsExclusive=getMajor(idLanguage, idSchool, idFac, degree);
		if(rsExclusive.size()>0)
		{
			result.addAll(rsExclusive);
		}
		List<Major> rsMajor=getMajor(idLanguage, idFac);
		if(rsMajor.size()>0)
		{
			result.addAll(rsMajor);
		}
		return result;
	}
	public static int getFacultyLevel(int degree) 
	{
		int facLevel=0;//0 มหาลัย, 1 โวแคต
		if(degree==1 || degree==4|| degree==5|| degree==14|| degree==15)
		{
			facLevel=1;
		}
		return facLevel;
	}
	
	public static List<Faculty> getAllFaculty(int idLanguage,int degree)
	{
		List<Faculty> result=new ArrayList<Faculty>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_FACULTY,FACULTY_NAME,FACULTY_LEVEL FROM INTER_FACULTY_LANGUAGE WHERE ID_FACULTY>0 AND ID_LANGUAGE=? AND FACULTY_LEVEL=? ORDER BY FACULTY_NAME");
			db.setInt(1,idLanguage);
			db.setInt(2,getFacultyLevel(degree) );
			db.executeQuery();
			while(db.next())
			{
				Faculty faculty=new Faculty();
				faculty.setIdLanguage(idLanguage);
				faculty.setFacultyName(db.getString("FACULTY_NAME"));
				faculty.setIdFaculty(db.getInt("ID_FACULTY"));
				result.add(faculty);
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
	public static List<Faculty> getFaculty(int idLanguage,int idSchool, int degree)
	{
		List<Faculty> result=new ArrayList<Faculty>();
		DBManager db=null;
		String SQL=	"SELECT "
				+ "			ID_FACULTY,NAME "
				+ "		FROM "
				+ "			INTER_UNV_FACULTY "
				+ "		WHERE "
				+ "			ID_SCHOOL=? AND ID_LANGUAGE=? AND ID_DEGREE=?  ORDER BY NAME";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idSchool);
			db.setInt(2, idLanguage);
			db.setInt(3,degree);
			db.executeQuery();
			while(db.next())
			{
				Faculty fac=new Faculty();
				fac.setFacultyName(db.getString("NAME"));
				fac.setIdFaculty(db.getInt("ID_FACULTY"));
				result.add(fac);
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
	
	public static List<Major> getMajor(int idLanguage, int idFac)
	{
		List<Major> result=new ArrayList<Major>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_MAJOR, MAJOR_NAME FROM INTER_MAJOR_LANGUAGE  WHERE ID_FACULTY=? AND ID_LANGUAGE=? ORDER BY MAJOR_NAME");
			db.setInt(1,idFac);
			db.setInt(2,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Major major=new Major();
				major.setIdLanguage(idLanguage);
				major.setMajorName(db.getString("MAJOR_NAME"));
				major.setIdMajor(db.getInt("ID_MAJOR"));
				result.add(major);
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
	public static List<Major> getMajor(int idLanguage,int idSchool, int idFac, int degree)
	{
		List<Major> result=new ArrayList<Major>();
		DBManager db=null;
		String SQL=	"SELECT "
				+ "			ID_MAJOR,NAME "
				+ "		FROM "
				+ "			INTER_UNV_MAJOR "
				+ "		WHERE "
				+ "			ID_SCHOOL=? AND ID_FACULTY=? AND ID_LANGUAGE=? AND ID_DEGREE=? ORDER BY NAME";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idSchool);
			db.setInt(2, idFac);
			db.setInt(3, idLanguage);
			db.setInt(4,degree);
			db.executeQuery();
			while(db.next())
			{
				Major major=new Major();
				major.setMajorName(db.getString("NAME"));
				major.setIdMajor(db.getInt("ID_MAJOR"));
				result.add(major);
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
	
	 public int updateStudent(SRTS stu)
	 {
		 int result = 0;
		 String SQL=	"UPDATE SRTS_STUDENT SET ID_JSK=? ,EMAIL=?  WHERE ID_STU=? AND ID_UNV=? AND ID_FAC=?";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, stu.getIdJsk());
			 db.setString(2, stu.getEmail());
			 db.setString(3, stu.getIdStu());
			 db.setInt(4, stu.getIdUnv());
			 db.setInt(5, stu.getIdFac());
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
	 
	 public int  addStudent(SRTS stu)
	 {
		 int result = 0;
		 String SQL=	"INSERT INTO  SRTS_STUDENT (ID_STU, ID_UNV, ID_FAC, ID_MAJOR, FIRSTNAME, LASTNAME, EMAIL, ACADEMIC_YEAR, ID_JSK, DEGREE ) VALUES (?,?,?,?,?,?,?,?,?,?)";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setString(1, stu.getIdStu());
			 db.setInt(2, stu.getIdUnv());
			 db.setLong(3, stu.getIdFac());
			 db.setInt(4, stu.getIdMajor());
			 db.setString(5, stu.getFirstName());
			 db.setString(6, stu.getLastName());
			 db.setString(7, stu.getEmail());
			 db.setString(8, stu.getAcademicYear());
			 db.setInt(9, stu.getIdJsk());
			 db.setInt(10, stu.getDegree());
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
	
}
