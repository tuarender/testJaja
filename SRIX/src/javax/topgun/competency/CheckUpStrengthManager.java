package javax.topgun.competency;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CheckUpStrengthManager {
	
	public List<CheckUpStrength> getByIdUser(int idUser) 
	{
		List<CheckUpStrength> result = new ArrayList<CheckUpStrength>();
		String SQL = 	"SELECT ID_USER, ID_STRENGTH, ID_STRENGTH_ORDER" +
						" FROM CHKUP_STRENGTH WHERE ID_USER=? ORDER BY ID_STRENGTH_ORDER";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.executeQuery();
			while(db.next()) {
				CheckUpStrength chkStrength = new CheckUpStrength();
				chkStrength.setIdUser(db.getInt("ID_USER"));
				chkStrength.setIdStrength(db.getInt("ID_STRENGTH"));
				chkStrength.setIdStrengthOrder(db.getInt("ID_STRENGTH_ORDER"));
				result.add(chkStrength);
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
	
	public CheckUpStrength get(int idUser,int idStrengthOrder) 
	{
		CheckUpStrength result = null;
		String SQL = 	"SELECT ID_USER, ID_STRENGTH, ID_STRENGTH_ORDER" +
						" FROM CHKUP_STRENGTH WHERE ID_USER=? AND ID_STRENGTH_ORDER=? ";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.setInt(2,idStrengthOrder);
			db.executeQuery();
			if(db.next()) {
				result =  new CheckUpStrength();
				result.setIdUser(db.getInt("ID_USER"));
				result.setIdStrength(db.getInt("ID_STRENGTH"));
				result.setIdStrengthOrder(db.getInt("ID_STRENGTH_ORDER"));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}


	public int delete(int idUser, int idStrengthOrder)
	{
		int result = 0;
		String sql="DELETE FROM CHKUP_STRENGTH WHERE ID_USER=? AND ID_STRENGTH_ORDER=?";
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idUser);
			db.setInt(2, idStrengthOrder);
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
	
	public int delete(int idUser)
	{
		int result = 0;
		String sql="DELETE FROM CHKUP_STRENGTH WHERE ID_USER=?";
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idUser);
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

	public int add(CheckUpStrength chkExp) 
	{
		
		int result = 0;
		String SQL = 	"INSERT INTO CHKUP_STRENGTH(ID_USER, ID_STRENGTH, ID_STRENGTH_ORDER) " +
						" VALUES(?,?,?)";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,chkExp.getIdUser());
			db.setInt(2,chkExp.getIdStrength());
			db.setInt(3,chkExp.getIdStrengthOrder());
			result = db.executeUpdate();
		} 
		catch (Exception e){
			e.printStackTrace();
		} 
		finally{
			db.close();
		}
		return result;
	}
}
