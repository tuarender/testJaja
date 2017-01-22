package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class PopupManager {
	
	public int checkPopup(int idJsk,int flag){
		int result=0;
		DBManager db=null;
		String sql=	"SELECT * FROM UPDATE_JOBFIELD_OTHER "+
					"WHERE " +
					"	ID_JSK=? AND FLAG_POPUP=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, flag);
			db.executeQuery();
		
			if(db.next())
			{ 
				result=1;
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
	
	
	public List<Popup> getResumePopup(int idJsk,int idLanguage){
		List<Popup> result = new ArrayList<Popup>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	DISTINCT(A.ID_RESUME),A.ID_JSK, B.RESUME_NAME  " +
					"FROM " +
					"	UPDATE_JOBFIELD_OTHER A " +
					"LEFT JOIN INTER_RESUME B ON A.ID_JSK = B.ID_JSK AND  A.ID_RESUME = B.ID_RESUME "+
					"WHERE " +
					"	A.ID_JSK=?";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			while(db.next())
			{ 
				Popup pop = new Popup();
				pop.setIdJsk(db.getInt("ID_JSK"));
				pop.setIdResume(db.getInt("ID_RESUME"));
				pop.setResumeName(db.getString("RESUME_NAME"));
				pop.setPopupList(getNameJobfield(db.getInt("ID_JSK"),db.getInt("ID_RESUME"),idLanguage));;
				result.add(pop);
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
	
	public List<Popup> getNameJobfield(int idJsk,int idResume,int idLanguage){
		List<Popup> result = new ArrayList<Popup>();
		DBManager db=null;
		String sql=	"SELECT DISTINCT(ID_RESUME), WORKJOB_FIELD_NEW,WORKJOB_FIELD_OLD,WORKSUB_FIELD,WORKSUB_FIELD_OTH,B.FIELD_NAME AS FIELD_NAME_NEW, "+
				"C.FIELD_NAME AS FIELD_NAME_OLD,SUBFIELD_NAME  FROM UPDATE_JOBFIELD_OTHER A "+
				"LEFT JOIN INTER_JOBFIELD_LANGUAGE B ON A.WORKJOB_FIELD_NEW=B.ID_FIELD "+
				"LEFT JOIN INTER_JOBFIELD_LANGUAGE C ON A.WORKJOB_FIELD_OLD=C.ID_FIELD AND B.ID_LANGUAGE=C.ID_LANGUAGE "+
				"LEFT JOIN INTER_SUBFIELD_LANGUAGE D ON A.WORKJOB_FIELD_NEW=D.ID_FIELD AND A.WORKSUB_FIELD=D.ID_SUBFIELD AND B.ID_LANGUAGE=D.ID_LANGUAGE "+
				"WHERE ID_JSK=? AND ID_RESUME = ? AND B.ID_LANGUAGE = ?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idLanguage);
			db.executeQuery();
			while(db.next())
			{ 
				Popup pop = new Popup();
				pop.setFieldNameNew(db.getString("FIELD_NAME_NEW"));
				pop.setFieldNameOld(db.getString("FIELD_NAME_OLD"));
				pop.setSubFieldNameOld(db.getString("WORKSUB_FIELD_OTH"));
				pop.setSubFieldNameNew(db.getString("SUBFIELD_NAME"));
				result.add(pop);
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
	
	public void updateFlg(int idJsk)
	{
		String sql=	"UPDATE " +
					"	UPDATE_JOBFIELD_OTHER " +
					"SET " +
					"	FLAG_POPUP=1 " +
					"WHERE " +
					"	ID_JSK=?";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			
			db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
}
