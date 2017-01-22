package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.resume.Attachment;
import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class AttachmentManager 
{	
	public Attachment get(int idJsk,int idFile)
	{
		Attachment result=null;
		String SQL="SELECT ID_JSK,ID_FILE,FILE_TITLE,FILE_TYPE,FILE_FORMAT,FILE_SIZE,FILE_DATE,TIMESTAMP FROM INTER_FILEATTACHMENT WHERE (ID_JSK=?)  AND (ID_FILE=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idFile);
			db.executeQuery();
			if(db.next())
			{
				result = new Attachment();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdFile(db.getInt("ID_FILE"));
				result.setFileTitle(db.getString("FILE_TITLE"));
				result.setFileType(db.getString("FILE_TYPE"));
				result.setFileFormat(db.getString("FILE_FORMAT"));
				result.setFileSize(db.getLong("FILE_SIZE"));
				result.setFileDate(db.getTimestamp("FILE_DATE"));
				result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
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
	
	public List<Attachment> getAll(int idJsk)
	{
		List<Attachment> result = new ArrayList<Attachment>();
		DBManager db=null;
		String SQL="SELECT ID_JSK,ID_FILE,FILE_TITLE,FILE_TYPE,FILE_FORMAT,FILE_SIZE,FILE_DATE,TIMESTAMP FROM INTER_FILEATTACHMENT WHERE (ID_JSK=?) ORDER BY ID_FILE";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Attachment att = new Attachment();
		    	att.setIdJsk(db.getInt("ID_JSK"));
		    	att.setIdFile(db.getInt("ID_FILE"));
		    	att.setFileTitle(db.getString("FILE_TITLE"));
		    	att.setFileType(db.getString("FILE_TYPE"));
		    	att.setFileFormat(db.getString("FILE_FORMAT"));
		    	att.setFileSize(db.getLong("FILE_SIZE"));
		    	att.setFileDate(db.getTimestamp("FILE_DATE"));
		    	att.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	if(Util.isFileExist(att)){
		    		//result.add(att);
		    	}
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
	
	/*
	 * @fileType can be RESUME or DOCUMENT
	 */
	public List<Attachment> getAllByFileType(int idJsk,String fileType)
	{
		List<Attachment> result = new ArrayList<Attachment>();
		String SQL="SELECT ID_JSK,ID_FILE,FILE_TITLE,FILE_TYPE,FILE_FORMAT,FILE_SIZE,FILE_DATE,TIMESTAMP FROM INTER_FILEATTACHMENT WHERE (ID_JSK=?) AND (DELETE_STATUS<>'TRUE') AND (FILE_TYPE =?) ORDER BY ID_FILE DESC";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setString(2, fileType);
			db.executeQuery();
			while(db.next())
			{
		    	Attachment att = new Attachment();
		    	att.setIdJsk(db.getInt("ID_JSK"));
		    	att.setIdFile(db.getInt("ID_FILE"));
		    	att.setFileTitle(db.getString("FILE_TITLE"));
		    	att.setFileType(db.getString("FILE_TYPE"));
		    	att.setFileFormat(db.getString("FILE_FORMAT"));
		    	att.setFileSize(db.getLong("FILE_SIZE"));
		    	att.setFileDate(db.getTimestamp("FILE_DATE"));
		    	att.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	if(Util.isFileExist(att)){
		    		result.add(att);
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
	
	public Attachment getPhoto(int idJsk)
	{
		Attachment result = null;
		String SQL="SELECT ID_JSK,ID_FILE,FILE_TITLE,FILE_TYPE,FILE_FORMAT,FILE_SIZE,FILE_DATE,TIMESTAMP FROM INTER_FILEATTACHMENT WHERE (ID_JSK=?) AND (DELETE_STATUS<>'TRUE') AND (FILE_TYPE ='PHOTOGRAPH') ORDER BY ID_FILE DESC";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
		    	result = new Attachment();
		    	result.setIdJsk(db.getInt("ID_JSK"));
		    	result.setIdFile(db.getInt("ID_FILE"));
		    	result.setFileTitle(db.getString("FILE_TITLE"));
		    	result.setFileType(db.getString("FILE_TYPE"));
		    	result.setFileFormat(db.getString("FILE_FORMAT"));
		    	result.setFileSize(db.getLong("FILE_SIZE"));
		    	result.setFileDate(db.getTimestamp("FILE_DATE"));
		    	result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	if(!Util.isFileExist(result))
		    	{
		    		result=null;
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
		
	
	/*
	 * @fileType can be RESUME or DOCUMENT
	 */
	public int getTotalFileSize(int idJsk,String fileType)
	{
		int result=0;
		List<Attachment> attList = getAllByFileType(idJsk,fileType);
		for(int i=0;i<attList.size();i++){
			result+=attList.get(i).getFileSize();
		}
		return result;
	}
	
	
	public int add(Attachment att)
	{
		int result = 0;
		String SQL= "INSERT INTO INTER_FILEATTACHMENT(ID_JSK,ID_FILE,FILE_TITLE,FILE_TYPE,FILE_FORMAT,FILE_SIZE,FILE_DATE,DELETE_STATUS,TIMESTAMP) " +
		"VALUES(?,?,?,?,?,?,SYSDATE,'FALSE',SYSDATE)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, att.getIdJsk());
			db.setInt(2, att.getIdFile());
			db.setString(3, att.getFileTitle());
			db.setString(4, att.getFileType());
			db.setString(5, att.getFileFormat());
			db.setLong(6, att.getFileSize());
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
	
	public int delete(Attachment att)
	{
		int result = 0;
		String SQL= "UPDATE INTER_FILEATTACHMENT SET DELETE_STATUS='TRUE',TIMESTAMP=SYSDATE WHERE (ID_JSK=?) AND (ID_FILE=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, att.getIdJsk());
			db.setInt(2, att.getIdFile());
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
	
    public int update(Attachment att)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_FILEATTACHMENT " +
    				"SET " +
    				"	FILE_TITLE=?," +
    				"	FILE_TYPE=?," +
    				"	FILE_FORMAT=?," +
    				"	FILE_SIZE=?," +
    				"	FILE_DATE=SYSDATE," +
    				"	TIMESTAMP=SYSDATE " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_FILE=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, att.getFileTitle());
    		db.setString(2,att.getFileType());
    		db.setString(3, att.getFileFormat());
    		db.setLong(4, att.getFileSize());
    		db.setInt(5, att.getIdJsk());
    		db.setInt(6,att.getIdFile());
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
    
	public int getNextIdFileAttachment(int idJsk){
		int result=0;
		String SQL="SELECT MAX(ID_FILE) AS TOTAL FROM INTER_FILEATTACHMENT WHERE (ID_JSK=?) ";
		DBManager db=null;
		try{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next()){
				result=db.getInt("TOTAL")+1;
			}else{
				result=1;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
}
