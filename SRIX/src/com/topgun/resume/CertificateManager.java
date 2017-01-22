package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CertificateManager 
{
	public Certificate get(int idJsk,int idResume,int idCertificate)
    {
    	Certificate result =  null;
    	String SQL=	"SELECT " +
    				"	ID_CERTIFICATE,CER_DATE,CER_NAME,INSTITUTION,DETAIL,TIMESTAMP,ID_CERT_FIELD,ID_CERT_SUBFIELD " +
    				"FROM " +
    				"	INTER_CERTIFICATE " +
    				"WHERE " +
    	           "	(ID_JSK=?) AND (ID_RESUME=?) AND " +
    	           "	(ID_CERTIFICATE=?) AND (DELETE_STATUS<>'TRUE')"; 
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idCertificate);
    		db.executeQuery();
    		if(db.next())
    		{
    			result = new Certificate();
    			result.setIdJsk(idJsk);
    			result.setIdResume(idResume);
    			result.setIdCertificate(db.getInt("ID_CERTIFICATE"));
    			result.setCerDate(db.getDate("CER_DATE"));
    			result.setCerName(db.getString("CER_NAME"));
    			result.setInstitution(db.getString("INSTITUTION"))	;
    			result.setDetail(db.getString("DETAIL"));
    			result.setTimeStamp(db.getDate("TIMESTAMP"));
    			result.setIdCertField(db.getInt("ID_CERT_FIELD"));
    			result.setIdCertSubfield(db.getInt("ID_CERT_SUBFIELD"));
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
	
	public List<Certificate> getAll(int idJsk, int idResume)
	{
		List<Certificate> result = new ArrayList<Certificate>();
		DBManager db=null;
		String SQL=	"SELECT * " +
					"FROM " +
					"	INTER_CERTIFICATE " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (DELETE_STATUS<>'TRUE')" +
					"ORDER BY " +
					"	ID_CERTIFICATE"; 
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Certificate cer=new Certificate();
		    	cer.setIdJsk(db.getInt("ID_JSK"));
		    	cer.setIdResume(db.getInt("ID_RESUME"));
		    	cer.setIdCertificate(db.getInt("ID_CERTIFICATE"));
		    	cer.setCerDate(db.getDate("CER_DATE"));
		    	cer.setCerName(db.getString("CER_NAME"));
		    	cer.setInstitution(db.getString("INSTITUTION"));
		    	cer.setDetail(db.getString("DETAIL"));
		    	cer.setTimeStamp(db.getDate("TIMESTAMP"));
		    	cer.setIdCertField(db.getInt("ID_CERT_FIELD"));
		    	cer.setIdCertSubfield(db.getInt("ID_CERT_SUBFIELD"));
		    	result.add(cer);
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
	
	public int add(Certificate cer)
	{
		int result = 0;
		String SQL= "INSERT INTO " +
					"	INTER_CERTIFICATE(ID_JSK,ID_RESUME,ID_CERTIFICATE,CER_DATE,CER_NAME,INSTITUTION,DETAIL,TIMESTAMP,ID_CERT_FIELD,ID_CERT_SUBFIELD) " +
					"	VALUES(?,?,?,?,?,?,?,SYSDATE,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, cer.getIdJsk());
			db.setInt(2, cer.getIdResume());
			db.setInt(3, cer.getIdCertificate());
			db.setDate(4, cer.getCerDate());
			db.setString(5, cer.getCerName());
			db.setString(6, cer.getInstitution());
			db.setString(7, cer.getDetail());
			db.setInt(8, cer.getIdCertField());
			db.setInt(9, cer.getIdCertSubfield());
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
	
	public int delete(Certificate cer)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_CERTIFICATE WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_CERTIFICATE=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, cer.getIdJsk());
			db.setInt(2, cer.getIdResume());
			db.setInt(3, cer.getIdCertificate());
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
		String SQL= "DELETE FROM INTER_CERTIFICATE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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
    
    public int update(Certificate cer)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_CERTIFICATE " +
    				"SET " +
    				"	CER_DATE=?,ID_CERT_FIELD=?,ID_CERT_SUBFIELD=?,CER_NAME=?,DETAIL=?,INSTITUTION=?,TIMESTAMP=SYSDATE " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_CERTIFICATE=?) " ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setDate(1, cer.getCerDate());
    		db.setInt(2, cer.getIdCertField());
    		db.setInt(3, cer.getIdCertSubfield());
    		db.setString(4, cer.getCerName());
    		db.setString(5, cer.getDetail());
    		db.setString(6, cer.getInstitution());
    		db.setInt(7, cer.getIdJsk());
    		db.setInt(8, cer.getIdResume());
    		db.setInt(9, cer.getIdCertificate());
    		
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
		String SQL="SELECT MAX(ID_CERTIFICATE) AS MAXID FROM INTER_CERTIFICATE WHERE ID_JSK=? AND ID_RESUME=? ";
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
