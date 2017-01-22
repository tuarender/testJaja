package javax.topgun.competency;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CheckUpTargetJobsManager {

	public List<CheckUpTargetJobs> getByIdUser(int idUser) 
	{
		List<CheckUpTargetJobs> result = new ArrayList<CheckUpTargetJobs>();
		String SQL = 	"SELECT ID_USER,ID_JOBFIELD,ID_JOBFIELD_ORDER" +
						" FROM CHKUP_TARGETJOB WHERE ID_USER=? ORDER BY ID_JOBFIELD_ORDER";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.executeQuery();
			while(db.next()) {
				CheckUpTargetJobs chkTargetJobs = new CheckUpTargetJobs();
				chkTargetJobs.setIdUser(db.getInt("ID_USER"));
				chkTargetJobs.setIdJobfield(db.getInt("ID_JOBFIELD"));
				chkTargetJobs.setIdJobfieldOrder(db.getInt("ID_JOBFIELD_ORDER"));
				result.add(chkTargetJobs);
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
	
	public CheckUpTargetJobs get(int idUser,int idTargetJobOrder) 
	{
		CheckUpTargetJobs result = null;
		String SQL = 	"SELECT ID_USER, ID_JOBFIELD, ID_JOBFIELD_ORDER" +
						" FROM CHKUP_TARGETJOB WHERE ID_USER=? AND ID_JOBFIELD_ORDER=? ";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.setInt(2,idTargetJobOrder);
			db.executeQuery();
			if(db.next()) {
				result =  new CheckUpTargetJobs();
				result.setIdUser(db.getInt("ID_USER"));
				result.setIdJobfield(db.getInt("ID_JOBFIELD"));
				result.setIdJobfieldOrder(db.getInt("ID_JOBFIELD_ORDER"));
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


	public int delete(int idUser, int idTargetJobOrder)
	{
		int result = 0;
		String sql="DELETE FROM CHKUP_TARGETJOB WHERE ID_USER=? AND ID_JOBFIELD_ORDER=?";
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idUser);
			db.setInt(2, idTargetJobOrder);
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
		String sql="DELETE FROM CHKUP_TARGETJOB WHERE ID_USER=?";
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

	public int add(CheckUpTargetJobs chkTargetJob) 
	{
		
		int result = 0;
		String SQL = 	"INSERT INTO CHKUP_TARGETJOB(ID_USER, ID_JOBFIELD, ID_JOBFIELD_ORDER) " +
						" VALUES(?,?,?)";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,chkTargetJob.getIdUser());
			db.setInt(2,chkTargetJob.getIdJobfield());
			db.setInt(3,chkTargetJob.getIdJobfieldOrder());
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
