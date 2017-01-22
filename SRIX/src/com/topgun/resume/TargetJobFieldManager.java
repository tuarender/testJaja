package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class TargetJobFieldManager 
{
	public int add(TargetJobField jf)
	{
		int result = 0;
		String sql=	"INSERT INTO INTER_TARGETJOB(ID_RESUME, ID_JOBFIELD, ID_SUBFIELD, " +
					"JOBFIELD_ORDER, OTHER_JOBFIELD, OTHER_SUBFIELD, ID_JSK) " +
					"VALUES(?,?,?,?,?,?,?)";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, jf.getIdResume());
			db.setInt(2, jf.getIdJobfield());
			db.setInt(3, jf.getIdSubfield());
			db.setInt(4, jf.getJobfieldOrder());
			db.setString(5, jf.getOtherJobfield());
			db.setString(6, jf.getOtherSubfield());
			db.setInt(7, jf.getIdJsk());
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

	public int update(TargetJobField jf)
	{
		int result = 0;
		DBManager db=null;
		String sql=	"UPDATE " +
					"	INTER_TARGETJOB " +
					"SET " +
					"	JOBFIELD_ORDER=?," +
					"	OTHER_JOBFIELD=?," +
					"	OTHER_SUBFIELD=? " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID_JOBFIELD=? AND ID_SUBFIELD=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, jf.getJobfieldOrder());
			db.setString(2, jf.getOtherJobfield());
			db.setString(3, jf.getOtherSubfield());
			db.setInt(4, jf.getIdJsk());
			db.setInt(5, jf.getIdResume());
			db.setInt(6, jf.getIdJobfield());
			db.setInt(7, jf.getIdSubfield());
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
	
	public int delete(TargetJobField jf)
	{
		int result = 0;
		String sql="DELETE FROM INTER_TARGETJOB WHERE ID_JSK=? AND ID_RESUME=? AND ID_JOBFIELD=? AND ID_SUBFIELD=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, jf.getIdJsk());
			db.setInt(2, jf.getIdResume());
			db.setInt(3, jf.getIdJobfield());
			db.setInt(4, jf.getIdSubfield());
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
		String sql="DELETE FROM INTER_TARGETJOB WHERE ID_JSK=? AND ID_RESUME=? ";
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
		
	public TargetJobField get(int idJsk,int idResume, int idJobField, int idSubField)
	{
		TargetJobField result=null;
		String sql=	"SELECT " +
					"	ID_RESUME, ID_JOBFIELD, ID_SUBFIELD, JOBFIELD_ORDER, OTHER_JOBFIELD, OTHER_SUBFIELD, ID_JSK " +
					"FROM " +
					"	INTER_TARGETJOB " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID_JOBFIELD=? AND ID_SUBFIELD=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idJobField);
			db.setInt(4, idSubField);
			db.executeQuery();
			if(db.next())
			{ 
				result=new TargetJobField();
				result.setIdJobfield(db.getInt("ID_JOBFIELD"));
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setIdSubfield(db.getInt("ID_SUBFIELD"));
				result.setJobfieldOrder(db.getInt("JOBFIELD_ORDER"));
				result.setOtherJobfield(db.getString("OTHER_JOBFIELD"));
				result.setOtherSubfield(db.getString("OTHER_SUBFIELD"));
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
	
	public List<TargetJobField> getAll(int idJsk,int idResume)
	{
		List<TargetJobField> result = new ArrayList<TargetJobField>();
		String sql=	"SELECT " +
					"	ID_RESUME, ID_JOBFIELD, ID_SUBFIELD, JOBFIELD_ORDER, OTHER_JOBFIELD, OTHER_SUBFIELD, ID_JSK " +
					"FROM " +
					"	INTER_TARGETJOB " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? " +
					"ORDER BY " +
					"	JOBFIELD_ORDER";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	TargetJobField jf=new TargetJobField();
		    	jf.setIdJsk(db.getInt("ID_JSK"));
		    	jf.setIdResume(db.getInt("ID_RESUME"));
		    	jf.setJobfieldOrder(db.getInt("JOBFIELD_ORDER"));
		    	jf.setIdJobfield(db.getInt("ID_JOBFIELD"));
		    	jf.setIdSubfield(db.getInt("ID_SUBFIELD"));
		    	jf.setOtherJobfield(db.getString("OTHER_JOBFIELD"));
		    	jf.setOtherSubfield(db.getString("OTHER_SUBFIELD"));
		    	result.add(jf);
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
	
	public int count(int idJsk,int idResume)
	{
		int result=0;
		String sql=	"SELECT COUNT(ID_JSK) AS TOTAL FROM INTER_TARGETJOB WHERE ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
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
	
	public int getMaxJobFieldOrder(int idJsk, int idResume)
	{
		int result=0; 
		DBManager db=null;
		String SQL="SELECT MAX(JOBFIELD_ORDER) AS MAXID FROM INTER_TARGETJOB WHERE ID_JSK=? AND ID_RESUME=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("MAXID");
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

	public int getNextOtherJobFieldId(int idJsk,int idResume)
	{
		int result=-1;
		String sql=	"SELECT MIN(ID_JOBFIELD) AS MINID FROM INTER_TARGETJOB WHERE ID_JSK=? AND ID_RESUME=? AND ID_JOBFIELD<0";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    if(db.next())
		    {
		    	result=db.getInt("MINID")-1;
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
	
	public int getNextOtherSubFieldId(int idJsk,int idResume, int idJobfield)
	{
		int result=-1;
		String sql=	"SELECT MIN(ID_SUBFIELD) AS MINID FROM INTER_TARGETJOB WHERE ID_JSK=? AND ID_RESUME=? AND ID_JOBFIELD=? AND ID_SUBFIELD<0";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idJobfield);
		    db.executeQuery();
		    if(db.next())
		    {
		    	result=db.getInt("MINID")-1;
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
