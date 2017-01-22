package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class SkillLanguageManager 
{
	public SkillLanguage get(int idJsk,int idResume,int idSkillLang)
    {
		SkillLanguage result=null;
    	String SQL=	"SELECT " +
    				"	ID_JSK, ID_RESUME, OTHER_LANG,READING,LISTENING,WRITING,SPEAKING,LEVEL_SKILL " +
    				"FROM " +
    				"	INTER_SKILL_LANGUAGE " +
    				"WHERE " +
    	           "	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_SKILL_LANGUAGE=?)"; 
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idSkillLang);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new SkillLanguage();
    			result.setIdJsk(idJsk);
    			result.setIdResume(idResume);
    			result.setOthLang(db.getString("OTHER_LANG"));
    			result.setReading(db.getInt("READING"));
    			result.setListening(db.getInt("LISTENING"));
    			result.setWriting(db.getInt("WRITING"));	
    			result.setSpeaking(db.getInt("SPEAKING"));	
    			result.setLevelSkill(db.getInt("LEVEL_SKILL"));	
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
	
	public List<SkillLanguage> getAll(int idJsk,int idResume)
	{
		List<SkillLanguage> result = new ArrayList<SkillLanguage>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID_SKILL_LANGUAGE,OTHER_LANG," +
					"	READING,LISTENING,WRITING,SPEAKING,LEVEL_SKILL " +
					"FROM " +
					"	INTER_SKILL_LANGUAGE WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) " +
					"ORDER BY " +
					"	ID_SKILL_LANGUAGE"; 
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	SkillLanguage lang=new SkillLanguage();
		    	lang.setIdJsk(db.getInt("ID_JSK"));
		    	lang.setIdResume(db.getInt("ID_RESUME"));
		    	lang.setIdSkillLang(db.getInt("ID_SKILL_LANGUAGE"));
		    	lang.setOthLang(db.getString("OTHER_LANG"));
		    	lang.setReading(db.getInt("READING"));
		    	lang.setListening(db.getInt("LISTENING"));
		    	lang.setWriting(db.getInt("WRITING"));	
		    	lang.setSpeaking(db.getInt("SPEAKING"));	
		    	lang.setLevelSkill(db.getInt("LEVEL_SKILL"));
		    	result.add(lang);
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
	
	public int add(SkillLanguage lang)
	{
		int result = 0;
		String SQL= "INSERT INTO " +
					"	INTER_SKILL_LANGUAGE(ID_JSK,ID_RESUME,ID_SKILL_LANGUAGE,OTHER_LANG,READING,LISTENING,WRITING,SPEAKING,LEVEL_SKILL) " +
					"	VALUES(?,?,?,?,?,?,?,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, lang.getIdJsk());
			db.setInt(2, lang.getIdResume());
			db.setInt(3, lang.getIdSkillLang());
			db.setString(4, lang.getOthLang());
			db.setInt(5, lang.getReading());
			db.setInt(6, lang.getListening());
			db.setInt(7, lang.getWriting());
			db.setInt(8, lang.getSpeaking());
			db.setInt(9, lang.getLevelSkill());
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
	
	public int delete(SkillLanguage lang)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_SKILL_LANGUAGE WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_SKILL_LANGUAGE=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, lang.getIdJsk());
			db.setInt(2, lang.getIdResume());
			db.setInt(3, lang.getIdSkillLang());
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
		String SQL= "DELETE FROM INTER_SKILL_LANGUAGE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
	
    public int update(SkillLanguage lang)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_SKILL_LANGUAGE " +
    				"SET " +
    				"	OTHER_LANG=?,READING=?,LISTENING=?," +
    				"	WRITING=?,SPEAKING=?,LEVEL_SKILL=? " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_SKILL_LANGUAGE=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, lang.getOthLang());
    		db.setInt(2, lang.getReading());
    		db.setInt(3, lang.getListening());
    		db.setInt(4, lang.getWriting());
    		db.setInt(5, lang.getSpeaking());
    		db.setInt(6, lang.getLevelSkill());
    		db.setInt(7, lang.getIdJsk());
    		db.setInt(8, lang.getIdResume());
    		db.setInt(9, lang.getIdSkillLang());
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
