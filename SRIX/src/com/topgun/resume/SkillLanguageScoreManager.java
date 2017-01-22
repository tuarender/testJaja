package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class SkillLanguageScoreManager 
{
	public SkillLanguageScore get(int idJsk,int idResume,String examType)
    {
		SkillLanguageScore result=null;
    	String SQL=	"SELECT " +
    				"	ID_JSK, ID_RESUME,EXAM_TYPE, READING,LISTENING,WRITING,SPEAKING,TOTAL " +
    				"FROM " +
    				"	INTER_SKILL_LANGUAGE_SCORE " +
    				"WHERE " +
    	           "	(ID_JSK=?) AND (ID_RESUME=?) AND (EXAM_TYPE=?)"; 
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setString(3, examType);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new SkillLanguageScore();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setExamType(db.getString("EXAM_TYPE"));
    			result.setReading(db.getFloat("READING"));
    			result.setListening(db.getFloat("LISTENING"));	
    			result.setWriting(db.getFloat("WRITING"));	
    			result.setSpeaking(db.getFloat("SPEAKING"));
    			result.setTotal(db.getFloat("TOTAL"));	
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
	
	public List<SkillLanguageScore> getAll(int idJsk,int idResume)
	{
		List<SkillLanguageScore> result = new ArrayList<SkillLanguageScore>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,EXAM_TYPE,READING,LISTENING,WRITING,SPEAKING,TOTAL " +
					"FROM " +
					"	INTER_SKILL_LANGUAGE_SCORE " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) " +
					"ORDER BY " +
					"	EXAM_TYPE";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	SkillLanguageScore sc=new SkillLanguageScore();
		    	sc.setIdJsk(db.getInt("ID_JSK"));
		    	sc.setIdResume(db.getInt("ID_RESUME"));
		    	sc.setExamType(db.getString("EXAM_TYPE"));
		    	sc.setReading(db.getFloat("READING"));
		    	sc.setListening(db.getFloat("LISTENING"));	
		    	sc.setWriting(db.getFloat("WRITING"));	
		    	sc.setSpeaking(db.getFloat("SPEAKING"));
		    	sc.setTotal(db.getFloat("TOTAL"));	
		    	result.add(sc);
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
	
	public List<SkillLanguageScore> getOtherAll(int idJsk,int idResume)
	{
		List<SkillLanguageScore> result = new ArrayList<SkillLanguageScore>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,EXAM_TYPE,READING,LISTENING,WRITING,SPEAKING,TOTAL " +
					"FROM " +
					"	INTER_SKILL_LANGUAGE_SCORE " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)  AND (EXAM_TYPE NOT IN ('IELTS', 'TOEFL', 'TOEIC'))" +
					"ORDER BY " +
					"	EXAM_TYPE";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	SkillLanguageScore sc=new SkillLanguageScore();
		    	sc.setIdJsk(db.getInt("ID_JSK"));
		    	sc.setIdResume(db.getInt("ID_RESUME"));
		    	sc.setExamType(db.getString("EXAM_TYPE"));
		    	sc.setReading(db.getFloat("READING"));
		    	sc.setListening(db.getFloat("LISTENING"));	
		    	sc.setWriting(db.getFloat("WRITING"));	
		    	sc.setSpeaking(db.getFloat("SPEAKING"));
		    	sc.setTotal(db.getFloat("TOTAL"));	
		    	result.add(sc);
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
	
	public int add(SkillLanguageScore sc)
	{
		int result = 0;
		String SQL= "INSERT INTO INTER_SKILL_LANGUAGE_SCORE(ID_JSK,ID_RESUME,EXAM_TYPE,READING,LISTENING,WRITING,SPEAKING,TOTAL) VALUES(?,?,?,?,?,?,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, sc.getIdJsk());
			db.setInt(2, sc.getIdResume());
			db.setString(3, sc.getExamType());
			db.setFloat(4, sc.getReading());
			db.setFloat(5, sc.getListening());
			db.setFloat(6, sc.getWriting());
			db.setFloat(7, sc.getSpeaking());
			db.setFloat(8, sc.getTotal());
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
	
	
	public int delete(SkillLanguageScore sc)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_SKILL_LANGUAGE_SCORE WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (EXAM_TYPE=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, sc.getIdJsk());
			db.setInt(2, sc.getIdResume());
			db.setString(3, sc.getExamType());
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
		String SQL= "DELETE FROM INTER_SKILL_LANGUAGE_SCORE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db=null;
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
    
    public int update(SkillLanguageScore sc)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_SKILL_LANGUAGE_SCORE " +
    				"SET " +
    				"	READING=?,LISTENING=?,WRITING=?,SPEAKING=?,TOTAL=? " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (EXAM_TYPE=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setFloat(1, sc.getReading());
    		db.setFloat(2, sc.getListening());
    		db.setFloat(3, sc.getWriting());
    		db.setFloat(4, sc.getSpeaking());
    		db.setFloat(5, sc.getTotal());
    		db.setInt(6, sc.getIdJsk());
    		db.setInt(7, sc.getIdResume());
    		db.setString(8, sc.getExamType());
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
