package javax.topgun.competency;

import java.util.List;

import com.topgun.resume.CompleteStatus;
import com.topgun.resume.Resume;
import com.topgun.resume.Status;
import com.topgun.util.DBManager;

public class CheckUpUserManager {
	
	public CheckUpUser get(String username,String password) 
	{
		CheckUpUser result = null;
		String SQL = "SELECT ID_USER, USERNAME, BIRTH_DATE, TIMESTAMPS FROM CHKUP_USER WHERE LOWER(USERNAME) = ? AND PASSWORD = ?";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1,username.toLowerCase());
			db.setString(2,password);
			db.executeQuery();
			if(db.next()) {
				result = new CheckUpUser();
				result.setIdUser(db.getInt("ID_USER"));
				result.setUsername((db.getString("USERNAME")));
				result.setBirthDate((db.getDate("BIRTH_DATE")));
				result.setTimestamps((db.getDate("TIMESTAMPS")));
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
	
	public CheckUpUser get(String username)
	{
		CheckUpUser result=null;
		String sql="SELECT ID_USER, USERNAME,PASSWORD, BIRTH_DATE, TIMESTAMPS FROM CHKUP_USER WHERE (LOWER(USERNAME)=?)";
		username=username!=null?username.trim():"";
		if(!username.equals(""))
		{
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setString(1, username.toLowerCase());
				db.executeQuery();
				if(db.next())
				{
					result = new CheckUpUser();
					result.setIdUser(db.getInt("ID_USER"));
					result.setUsername(db.getString("USERNAME"));
					result.setPassword(db.getString("PASSWORD"));
					result.setBirthDate(db.getDate("BIRTH_DATE"));
					result.setTimestamps(db.getDate("TIMESTAMPS"));
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
		}
		return result;
	}	
	
	public CheckUpUser getByIdUser(int idUser) 
	{
		CheckUpUser result = null;
		String SQL = "SELECT ID_USER, USERNAME, BIRTH_DATE, TIMESTAMPS FROM CHKUP_USER WHERE ID_USER = ?";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idUser);
			db.executeQuery();
			if(db.next()) {
				result = new CheckUpUser();
				result.setIdUser(db.getInt("ID_USER"));
				result.setUsername((db.getString("USERNAME")));
				result.setBirthDate((db.getDate("BIRTH_DATE")));
				result.setTimestamps((db.getDate("TIMESTAMPS")));
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
	
	public int add(CheckUpUser chkUser) 
	{
		int result = 0;
		String SQL = "INSERT INTO CHKUP_USER(ID_USER, USERNAME,PASSWORD, BIRTH_DATE, TIMESTAMPS) VALUES(?,?,?,'',SYSDATE)";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,chkUser.getIdUser());
			db.setString(2,chkUser.getUsername());
			db.setString(3,chkUser.getPassword());
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
	
	public int updateBd(CheckUpUser chkUser) 
	{
		int result = 0;
		String SQL = "UPDATE CHKUP_USER SET BIRTH_DATE=? WHERE ID_USER=?";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setDate(1,chkUser.getBirthDate());
			db.setInt(2,chkUser.getIdUser());
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
	
	public boolean isExist(String username)
	{
		boolean result=false;
		String sql="SELECT ID_USER FROM CHKUP_USER WHERE (LOWER(USERNAME)=?)";
		username=username!=null?username.trim():"";
		if(!username.equals(""))
		{
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setString(1, username.toLowerCase());
				db.executeQuery();
				if(db.next())
				{
					result=true;
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
		}
		return result;
	}	
	
	
	public String getCheckUpRegisterStatus(CheckUpUser chkUser)
	{
		String result = "TRUE";
		CheckUpHobbyManager chkHobbyMng = new CheckUpHobbyManager();
		List<CheckUpHobby> chkHobby = chkHobbyMng.getAll(chkUser.getIdUser());
		//-------check aptitude---------
		if(chkHobby.size()>0){
			boolean skillAllSet = true;
			//-------------check skill aptitude---------
			for(int i=0;i<chkHobby.size()&&skillAllSet;i++){
				if(chkHobby.get(i).getSkill()==0&&chkHobby.get(i).getIdGroup()!=1){
					skillAllSet = false;
				}
			}
			if(!skillAllSet){
				result = "APTITUDE_RANK";
			}
			else{
				//-------------check birthDate-----------
				if(chkUser.getBirthDate()==null){
					result = "BIRTH_DATE";
				}
				else{
					//---------------------check Strength---------------
					CheckUpStrengthManager chkStrength = new CheckUpStrengthManager();
					List<CheckUpStrength> strengthList = chkStrength.getByIdUser(chkUser.getIdUser());
					if(strengthList.size()>0){
						//---------------------check targetJobs------------------
						CheckUpTargetJobsManager chkTargetJobs = new CheckUpTargetJobsManager();
						List<CheckUpTargetJobs> targetJobsList = chkTargetJobs.getByIdUser(chkUser.getIdUser());
						if(targetJobsList.size()>0){
							//---------------------check education------------------
							CheckUpEducationManager chkEdu = new CheckUpEducationManager();
							List<CheckUpEducation> eduList = chkEdu.getByIdUser(chkUser.getIdUser());
							if(eduList.size()>0){
								//-------------------check exp-----------------------
								CheckUpExperienceManager  chkExp = new CheckUpExperienceManager();
								List<CheckUpEducation> expList = chkEdu.getByIdUser(chkUser.getIdUser());
								if(expList.size()<=0){
									result="EXPERIENCE";
								}
							}
							else{
								result="EDUCATION";
							}
						}
						else{
							result="TARGETJOBS";
						}
					}
					else{
						result="STRENGTH";
					}
				}
			}
		}
		else{
			result = "APTITUDE";
		}
		return result;
	}
	
	public int getNextId()
	{
		int result=1;
		DBManager db=null;
		try{
			db=new DBManager();
			db.createPreparedStatement("SELECT MAX(ID_USER) AS MAXID FROM CHKUP_USER");
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
