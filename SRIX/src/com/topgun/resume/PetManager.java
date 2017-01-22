package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class PetManager 
{
	public Pet get(int idJsk,int idResume,int idPet)
    {
		Pet result=null;
    	String SQL=	"SELECT OTHER_PET FROM INTER_PET WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_PET=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idPet);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Pet();
    			result.setOtherPet(db.getString("OTHER_PET"));
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
    
	public  List<Pet> getAll(int idJsk,int idResume)
	{
		List<Pet> result = new ArrayList<Pet>();
		DBManager db=null;
		String SQL=	"SELECT ID_JSK,ID_RESUME,ID_PET,OTHER_PET FROM INTER_PET WHERE (ID_JSK=?) AND (ID_RESUME=?) ORDER BY ID_PET";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Pet pet=new Pet();
		    	pet.setIdJsk(db.getInt("ID_JSK"));
		    	pet.setIdResume(db.getInt("ID_RESUME"));
		    	pet.setIdPet(db.getInt("ID_PET"));
		    	pet.setOtherPet(db.getString("OTHER_PET"));
		    	result.add(pet);
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
	
    public  int add(Pet pet)
    {
    	int result = 0;
    	String SQL= "INSERT INTO INTER_PET" +
    	"(" +
    	"ID_JSK," +
    	"ID_RESUME," +
    	"ID_PET," +
    	"OTHER_PET " +
    	") " +
    	"VALUES(?,?,?,?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, pet.getIdJsk());
    		db.setInt(2, pet.getIdResume());
    		db.setInt(3, pet.getIdPet());
    		db.setString(4, pet.getOtherPet());
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
    
    public  int delete(Pet pet)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_PET WHERE " +
    	"(ID_JSK=?) AND (ID_RESUME=?) AND (ID_PET=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, pet.getIdJsk());
    		db.setInt(2, pet.getIdResume());
    		db.setInt(3, pet.getIdPet());
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
    
    public  int deleteAllPet(int idJsk, int idResume)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_PET WHERE " +
    	"(ID_JSK=?) AND (ID_RESUME=?)";
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
    
    public  int update(Pet pet)
    {
    	int result = 0;
    	String SQL=	"UPDATE INTER_PET " +
    	"SET " +
    	"OTHER_PET=? " +
    	
    	"WHERE " +
    	"(ID_JSK=?) AND " +
    	"(ID_RESUME=?) AND " +
    	"(ID_PET=?)" ;
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, pet.getOtherPet());
    		db.setInt(2, pet.getIdJsk());
    		db.setInt(3, pet.getIdResume());
    		db.setInt(4, pet.getIdPet());
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
