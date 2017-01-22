package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class ReferenceManager 
{
	public Reference get(int idJsk,int idResume,int id)
    {
    	Reference result=null;
		String SQL="SELECT " +
					"	ID_JSK, ID_RESUME,ID, REFTYPE,OTHER_REFTYPE,REFER_NAME," +
					"	REFER_COMPANY, REFER_TEL,REFER_TITLE FROM INTER_REFERENCE " +
					"WHERE " +
    	           "	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)"; 
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, id);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Reference();
    			result.setId(db.getInt("ID"));
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setRefType(db.getString("REFTYPE"));
    			result.setOthRefType(db.getString("OTHER_REFTYPE"));
    			result.setRefName(db.getString("REFER_NAME"));
    			result.setRefCompany(db.getString("REFER_COMPANY"));
    			result.setRefTel(db.getString("REFER_TEL"));
    			result.setRefTitle(db.getString("REFER_TITLE"));
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
	
	public List<Reference> getAll(int idJsk,int idResume)
	{
		List<Reference> result = new ArrayList<Reference>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID,REFTYPE,OTHER_REFTYPE,REFER_NAME," +
					"	REFER_COMPANY,REFER_TEL,REFER_TITLE FROM INTER_REFERENCE " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) " +
					"ORDER BY " +
					"	ID"; 
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Reference ref=new Reference();
		    	ref.setIdJsk(db.getInt("ID_JSK"));
		    	ref.setIdResume(db.getInt("ID_RESUME"));
		    	ref.setId(db.getInt("ID"));
		    	ref.setRefType(db.getString("REFTYPE"));
		    	ref.setOthRefType(db.getString("OTHER_REFTYPE"));
		    	ref.setRefName(db.getString("REFER_NAME"));
		    	ref.setRefCompany(db.getString("REFER_COMPANY"));
		    	ref.setRefTel(db.getString("REFER_TEL"));
		    	ref.setRefTitle(db.getString("REFER_TITLE"));
		    	result.add(ref);
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
	
	public int add(Reference ref)
	{
		int result = 0;
		String SQL= "INSERT INTO " +
					"	INTER_REFERENCE(ID_JSK,ID_RESUME,ID,REFTYPE,OTHER_REFTYPE,REFER_NAME,REFER_COMPANY,REFER_TEL,REFER_TITLE) " +
					"	VALUES(?,?,?,?,?,?,?,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, ref.getIdJsk());
			db.setInt(2, ref.getIdResume());
			db.setInt(3, ref.getId());
			db.setString(4, ref.getRefType());
			db.setString(5, ref.getOthRefType());
			db.setString(6, ref.getRefName());
			db.setString(7, ref.getRefCompany());
			db.setString(8, ref.getRefTel());
			db.setString(9, ref.getRefTitle());
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
	
	public int delete(Reference ref)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_REFERENCE WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, ref.getIdJsk());
			db.setInt(2, ref.getIdResume());
			db.setInt(3, ref.getId());
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
		String SQL= "DELETE FROM INTER_REFERENCE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
    
    public int update(Reference ref)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_REFERENCE " +
    				"SET " +
    				"	REFTYPE=?,OTHER_REFTYPE=?,REFER_NAME=?," +
    				"	REFER_COMPANY=?,REFER_TEL=?,REFER_TITLE=? " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)" ;
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, ref.getRefType());
    		db.setString(2, ref.getOthRefType());
    		db.setString(3, ref.getRefName());
    		db.setString(4, ref.getRefCompany());
    		db.setString(5, ref.getRefTel());
    		db.setString(6, ref.getRefTitle());
    		db.setInt(7, ref.getIdJsk());
    		db.setInt(8, ref.getIdResume());
    		db.setInt(9, ref.getId());
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
    
    public int getNextId(int idJsk,int idResume)
	{
		int result=0;
		String SQL="SELECT MAX(ID) AS MAXID FROM INTER_REFERENCE WHERE ID_JSK=? AND ID_RESUME=? ";
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
					result=db.getInt("MAXID")+1;
				}
				else 
				{
					result = 1;
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
