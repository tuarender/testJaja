package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class StrengthManager 
{
	public Strength get(int idJsk,int idResume,int idStrength)
    {
		Strength result=null;
    	String SQL=	"SELECT " +
    				"	ID_JSK,ID_RESUME,ID_STRENGTH,STRENGTH_ORDER,OTHER_STRENGTH,STRENGTH_REASON FROM INTER_STRENGTH " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_STRENGTH=?)"; 
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idStrength);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Strength();
    			result.setIdJsk(db.getInt("ID_JSK"));
    			result.setIdResume(db.getInt("ID_RESUME"));
    			result.setIdStrength(db.getInt("ID_STRENGTH"));
    			result.setStrengthOrder(db.getInt("STRENGTH_ORDER"));
    			result.setOthStrength(db.getString("OTHER_STRENGTH"));	
    			result.setStrengthReason(db.getString("STRENGTH_REASON"));
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
	
	public List<Strength> getAll(int idJsk,int idResume)
	{
		List<Strength> result = new ArrayList<Strength>();
		DBManager db=null;
		String SQL="SELECT " +
					"	ID_JSK,ID_RESUME,ID_STRENGTH,STRENGTH_ORDER,OTHER_STRENGTH," +
					"	STRENGTH_REASON FROM INTER_STRENGTH " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) " +
					"ORDER BY " +
					"	STRENGTH_ORDER";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Strength st=new Strength();
		    	st.setIdJsk(db.getInt("ID_JSK"));
		    	st.setIdResume(db.getInt("ID_RESUME"));
		    	st.setIdStrength(db.getInt("ID_STRENGTH"));
		    	st.setStrengthOrder(db.getInt("STRENGTH_ORDER"));
		    	st.setOthStrength(db.getString("OTHER_STRENGTH"));	
		    	st.setStrengthReason(db.getString("STRENGTH_REASON"));
		    	result.add(st);
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
	
	public List<Strength> getAllChoose(int idJsk,int idResume)
	{
		List<Strength> result = new ArrayList<Strength>();
		DBManager db=null;
		String SQL="SELECT " +
					"	ID_JSK,ID_RESUME,ID_STRENGTH,STRENGTH_ORDER,OTHER_STRENGTH," +
					"	STRENGTH_REASON FROM INTER_STRENGTH " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (STRENGTH_ORDER=9) " +
					"ORDER BY " +
					"	STRENGTH_ORDER";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Strength st=new Strength();
		    	st.setIdJsk(db.getInt("ID_JSK"));
		    	st.setIdResume(db.getInt("ID_RESUME"));
		    	st.setIdStrength(db.getInt("ID_STRENGTH"));
		    	st.setStrengthOrder(db.getInt("STRENGTH_ORDER"));
		    	st.setOthStrength(db.getString("OTHER_STRENGTH"));	
		    	st.setStrengthReason(db.getString("STRENGTH_REASON"));
		    	result.add(st);
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
	
	public void reOrder(int idJsk, int idResume)
	{
		 List<Strength> strengths=getAll(idJsk,idResume);
		 if(strengths!=null)
		 {
			 for(int i=0; i<strengths.size(); i++)
			 {
				 strengths.get(i).setStrengthOrder(i+1);
				 update(strengths.get(i));
			 }
		 }
	}
	
	public int add(Strength strenth)
	{
		int result = 0;
		String SQL= "INSERT INTO " +
					"	INTER_STRENGTH(ID_JSK,ID_RESUME,ID_STRENGTH,STRENGTH_ORDER,OTHER_STRENGTH,STRENGTH_REASON) " +
					"VALUES(?,?,?,?,?,?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, strenth.getIdJsk());
			db.setInt(2, strenth.getIdResume());
			db.setInt(3, strenth.getIdStrength());
			db.setInt(4, strenth.getStrengthOrder());
			db.setString(5, strenth.getOthStrength());
			db.setString(6, strenth.getStrengthReason());
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
		
	public int delete(Strength strenth)
	{
		int result = 0;
		String SQL= "DELETE FROM INTER_STRENGTH WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_STRENGTH=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, strenth.getIdJsk());
			db.setInt(2, strenth.getIdResume());
			db.setInt(3, strenth.getIdStrength());
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
		String SQL="DELETE FROM INTER_STRENGTH WHERE ID_JSK=? AND ID_RESUME=?";
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
	
    public int update(Strength strenth)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_STRENGTH " +
    				"SET " +
    				"	STRENGTH_ORDER=?, OTHER_STRENGTH=?, STRENGTH_REASON=? " +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_STRENGTH=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, strenth.getStrengthOrder());
    		db.setString(2, strenth.getOthStrength());
    		db.setString(3, strenth.getStrengthReason());
    		db.setInt(4, strenth.getIdJsk());
    		db.setInt(5, strenth.getIdResume());
    		db.setInt(6, strenth.getIdStrength());
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
    
    public int update(int idJsk, int idResume, int idStrength, int idOrder)
    {
    	int result=0;
    	String SQL="update inter_strength "
    			+ "set STRENGTH_ORDER="+idOrder+" "
    			+ "where ID_JSK="+idJsk+" AND ID_RESUME="+idResume+" AND ID_STRENGTH="+idStrength+" ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
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
    
    public int updateAllNotRank(int idJsk, int idResume)
    {
    	int result=0;
    	String SQL="UPDATE INTER_STRENGTH "
    			+ "SET STRENGTH_ORDER=0 "
    			+ "WHERE ID_JSK="+idJsk+" AND ID_RESUME="+idResume+" AND STRENGTH_ORDER=9";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
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
    
    public int updateAllRank(int idJsk, int idResume)
    {
    	int result=0;
    	String SQL="UPDATE INTER_STRENGTH "
    			+ " SET STRENGTH_ORDER=9 "
    			+ " WHERE ID_JSK=? AND ID_RESUME=?";
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
    
    public static int clearOrderStrength(int idJsk, int idResume)
    {
    	int result = 0;
    	String SQL= "UPDATE " +
    				"	INTER_STRENGTH " +
    				"SET " +
    				"	STRENGTH_ORDER=''" +
    				"WHERE " +
    				"	(ID_JSK=?) AND (ID_RESUME=?)";
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
    
    public int getMinStrength(int idJsk, int idResume){
    	int result = 0;
    	String SQL= "SELECT MIN(ID_STRENGTH) AS MIN_OTHER_STR FROM INTER_STRENGTH WHERE ID_JSK = ? AND ID_RESUME = ? ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.executeQuery();
    		if(db.next()){
    			result = db.getInt("MIN_OTHER_STR");
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
    
    public static List<Strength> getAllSelect(int idJsk,int idResume)
	{
		List<Strength> result = new ArrayList<Strength>();
		DBManager db=null;
		String SQL="SELECT " +
					"	ID_JSK,ID_RESUME,ID_STRENGTH,STRENGTH_ORDER,OTHER_STRENGTH," +
					"	STRENGTH_REASON FROM INTER_STRENGTH " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) " +
					"ORDER BY " +
					"	STRENGTH_ORDER";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Strength st=new Strength();
		    	st.setIdJsk(db.getInt("ID_JSK"));
		    	st.setIdResume(db.getInt("ID_RESUME"));
		    	st.setIdStrength(db.getInt("ID_STRENGTH"));
		    	st.setStrengthOrder(db.getInt("STRENGTH_ORDER"));
		    	st.setOthStrength(db.getString("OTHER_STRENGTH"));	
		    	st.setStrengthReason(db.getString("STRENGTH_REASON"));
		    	result.add(st);
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
	public int deleteAllStrengthOther(int idJsk, int idResume) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_STRENGTH WHERE (ID_JSK=?) AND (ID_RESUME=?) AND ID_STRENGTH=-1";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idJsk);
			db.setInt(2,idResume);
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
	
	public boolean  checkStrength(int idJsk, int idResume, int idStrength) 
	{
		boolean result=false;
		DBManager db = null;
		String SQL = "SELECT ID_STRENGTH FROM INTER_STRENGTH WHERE  ID_JSK=? AND ID_RESUME=? AND ID_STRENGTH=?";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idStrength);
			db.executeQuery();
			if (db.next()) {
				result=true;
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			db.close();
		}
		return result;
	}
	public int deleteNotChoose(int idJsk, int idResume,String idStrength) 
	{
		int result = 0;
		String SQL = "DELETE FROM INTER_STRENGTH WHERE ID_JSK=? AND ID_RESUME=? AND ID_STRENGTH NOT IN ("+idStrength+") AND ID_STRENGTH <> -1 ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idJsk);
			db.setInt(2,idResume);
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
	
	public static String getStrengthName(int idLanguage, int idStrength) 
	{
		String result = "";
		String SQL = "SELECT * FROM INTER_STRENGTH_LANGUAGE WHERE (ID_STRENGTH=?) AND (ID_LANGUAGE=?) ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idStrength);
			db.setInt(2,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result= db.getString("STRENGTH_NAME");
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
}
