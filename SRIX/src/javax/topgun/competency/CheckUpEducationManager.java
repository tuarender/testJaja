package javax.topgun.competency;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CheckUpEducationManager {

	public List<CheckUpEducation> getByIdUser(int idUser) 
	{
		List<CheckUpEducation> result = new ArrayList<CheckUpEducation>();
		String SQL = 	"SELECT ID_USER, ID_FACULTY, ID_MAJOR, ID_EDUCATION, ID_DEGREE" +
						" FROM CHKUP_EDUCATION WHERE ID_USER=? ";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.executeQuery();
			while(db.next()) {
				CheckUpEducation chkEdu = new CheckUpEducation();
				chkEdu.setIdUser(db.getInt("ID_USER"));
				chkEdu.setIdFaculty((db.getInt("ID_FACULTY")));
				chkEdu.setIdMajor((db.getInt("ID_MAJOR")));
				chkEdu.setIdEducation((db.getInt("ID_EDUCATION")));
				chkEdu.setIdDegree((db.getInt("ID_DEGREE")));
				result.add(chkEdu);
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
	
	public CheckUpEducation get(int idUser,int idEducation) 
	{
		CheckUpEducation result = null;
		String SQL = 	"SELECT ID_USER, ID_FACULTY, ID_MAJOR, ID_EDUCATION, ID_DEGREE" +
						" FROM CHKUP_EDUCATION WHERE ID_USER=? AND ID_EDUCATION=? ";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idUser);
			db.setInt(2,idEducation);
			db.executeQuery();
			if(db.next()) {
				result =  new CheckUpEducation();
				result.setIdUser(db.getInt("ID_USER"));
				result.setIdFaculty((db.getInt("ID_FACULTY")));
				result.setIdMajor((db.getInt("ID_MAJOR")));
				result.setIdEducation((db.getInt("ID_EDUCATION")));
				result.setIdDegree((db.getInt("ID_DEGREE")));
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
	
	public int delete(int idUser, int idEducation)
	{
		int result = 0;
		String sql="DELETE FROM CHKUP_EDUCATION WHERE ID_USER=? AND ID_EDUCATION=?";
		DBManager db=null;		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idUser);
			db.setInt(2, idEducation);
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
	
	public int addEdu(CheckUpEducation chkEdu) 
	{
		
		int result = 0;
		String SQL = 	"INSERT INTO CHKUP_EDUCATION(ID_USER, ID_FACULTY, ID_MAJOR, ID_EDUCATION, ID_DEGREE) " +
						" VALUES(?,?,?,?,?)";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,chkEdu.getIdUser());
			db.setInt(2,chkEdu.getIdFaculty());
			db.setInt(3,chkEdu.getIdMajor());
			db.setInt(4,getNextId(chkEdu.getIdUser()));
			db.setInt(5,chkEdu.getIdDegree());
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
	
	public int updateEdu(CheckUpEducation chkEdu) 
	{
		int result = 0;
		String SQL = 	"UPDATE CHKUP_EDUCATION SET ID_DEGREE=?,ID_FACULTY=?,ID_MAJOR=? WHERE ID_USER=? AND ID_EDUCATION = ?";
		DBManager db = null;
		try{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,chkEdu.getIdDegree());
			db.setInt(2,chkEdu.getIdFaculty());
			db.setInt(3,chkEdu.getIdMajor());
			db.setInt(4,chkEdu.getIdUser());
			db.setInt(5,chkEdu.getIdEducation());
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
			db.createPreparedStatement("SELECT MAX(ID_EDUCATION) AS MAXID FROM CHKUP_EDUCATION WHERE ID_USER=?");
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
