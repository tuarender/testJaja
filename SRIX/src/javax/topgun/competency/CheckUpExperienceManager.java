package javax.topgun.competency;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CheckUpExperienceManager {

	public List<CheckUpExperience> getByIdUser(int idUser) 
	{
		List<CheckUpExperience> result = new ArrayList<CheckUpExperience>();
		String SQL = 	"SELECT ID_USER, ID_JOBFIELD, YEAR, MONTH, ID_EXP" +
						" FROM CHKUP_EXP WHERE ID_USER=? ORDER BY ID_EXP";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.executeQuery();
			while(db.next()) {
				CheckUpExperience chkExp = new CheckUpExperience();
				chkExp.setIdUser(db.getInt("ID_USER"));
				chkExp.setIdJobfield(db.getInt("ID_JOBFIELD"));
				chkExp.setYear(db.getInt("YEAR"));
				chkExp.setMonth(db.getInt("MONTH"));
				chkExp.setIdExp(db.getInt("ID_EXP"));
				result.add(chkExp);
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
	
	public CheckUpExperience get(int idUser,int idExp) 
	{
		CheckUpExperience result = null;
		String SQL = 	"SELECT ID_USER, ID_JOBFIELD, YEAR, MONTH, ID_EXP" +
						" FROM CHKUP_EXP WHERE ID_USER=? AND ID_EXP=? ";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.setInt(2,idExp);
			db.executeQuery();
			if(db.next()) {
				result =  new CheckUpExperience();
				result.setIdUser(db.getInt("ID_USER"));
				result.setIdJobfield(db.getInt("ID_JOBFIELD"));
				result.setYear(db.getInt("YEAR"));
				result.setMonth(db.getInt("MONTH"));
				result.setIdExp(db.getInt("ID_EXP"));
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
	
	public int delete(int idUser, int idExperience)
	{
		int result = 0;
		String sql="DELETE FROM CHKUP_EXP WHERE ID_USER=? AND ID_EXP=?";
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idUser);
			db.setInt(2, idExperience);
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
	
	public int add(CheckUpExperience chkExp) 
	{
		
		int result = 0;
		String SQL = 	"INSERT INTO CHKUP_EXP(ID_USER, ID_JOBFIELD, YEAR, MONTH, ID_EXP) " +
						" VALUES(?,?,?,?,?)";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,chkExp.getIdUser());
			db.setInt(2,chkExp.getIdJobfield());
			db.setInt(3,chkExp.getYear());
			db.setInt(4,chkExp.getMonth());
			db.setInt(5,getNextId(chkExp.getIdUser()));
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
	
	public int update(CheckUpExperience chkExp) 
	{
		int result = 0;
		String SQL = 	"UPDATE CHKUP_EXP SET ID_JOBFIELD=?,YEAR=?,MONTH=? WHERE ID_USER=? AND ID_EXP = ?";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,chkExp.getIdJobfield());
			db.setInt(2,chkExp.getYear());
			db.setInt(3,chkExp.getMonth());
			db.setInt(4,chkExp.getIdUser());
			db.setInt(5,chkExp.getIdExp());
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
	
	private int getNextId(int idUser)
	{
		int result=1;
		DBManager db=null;
		try{
			db=new DBManager();
			db.createPreparedStatement("SELECT MAX(ID_EXP) AS MAXID FROM CHKUP_EXP WHERE ID_USER=?");
			db.setInt(1, idUser);
			db.executeQuery();
			if(db.next()){
				result=db.getInt("MAXID")+1;
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
