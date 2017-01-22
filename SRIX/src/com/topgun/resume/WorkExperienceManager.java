package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class WorkExperienceManager 
{	
	public int add(WorkExperience workExp)
	{
		int result = 0;
		if(workExp!=null && workExp.getIdJsk()>0 && workExp.getIdResume()>=0)
		{
			DBManager db=null;
			String sql="INSERT INTO INTER_WORK_EXPERIENCE(ID_JSK, ID_RESUME, ID, COMPANY_NAME, " +
						"POSITION_LAST, SALARY_LAST, WORK_START, WORK_END,WORKING_STATUS, JOB_DESC, " +
						"ACHIEVE, ID_COUNTRY, ID_STATE, OTHER_STATE, WORKJOB_FIELD, WORKSUB_FIELD, " +
						"COM_SIZE, SUBORDINATE, EXP_Y, EXP_M, COM_BUSINESS, WORKJOB_FIELD_OTH, " +
						"WORKSUB_FIELD_OTH, REASON_QUIT, SALARY_START, POSITION_START,SALARY_PER, SALARY_PER_START, " +
						"WORK_JOBTYPE, WORKJOB_FIELD_START, WORKSUB_FIELD_START, WORKJOB_FIELD_START_OTH, " +
						"WORKSUB_FIELD_START_OTH,REF_ID,JOB_DESC2, ACHIEVE2, ID_CURRENCY, ID_CURRENCY_START,ID_REFER,HASH_REFER) " +
						"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try
			{
				String tmpDesc = workExp.getJobDesc()!=null?workExp.getJobDesc():"";
				if (!tmpDesc.equals("") && tmpDesc.length() > 2000) 
				{
					workExp.setJobDesc(tmpDesc.substring(0,2000));
					workExp.setJobDesc2(tmpDesc.substring(2000,tmpDesc.length()));
				}
				else 
				{
					workExp.setJobDesc2("");
				}
				
				String tmpAchieve = workExp.getAchieve()!=null?workExp.getAchieve():"";
				if (!tmpAchieve.equals("") && tmpAchieve.length() > 2000) 
				{
					workExp.setAchieve(tmpAchieve.substring(0,2000));
					workExp.setAchieve2(tmpAchieve.substring(2000,tmpAchieve.length()));
				}
				else 
				{
					workExp.setAchieve2("");
				}
				
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setInt(1, workExp.getIdJsk());
				db.setInt(2, workExp.getIdResume());
				db.setInt(3, getNextId(workExp.getIdJsk(),workExp.getIdResume()));
				db.setString(4, workExp.getCompanyName());
				db.setString(5, workExp.getPositionLast());
				db.setInt(6, workExp.getSalaryLast());
				db.setDate(7, workExp.getWorkStart());
				db.setDate(8, workExp.getWorkEnd());
				db.setString(9, workExp.getWorkingStatus());
				db.setString(10, workExp.getJobDesc());
				db.setString(11, workExp.getAchieve());
				db.setInt(12, workExp.getIdCountry());
				db.setInt(13, workExp.getIdState());
				db.setString(14, workExp.getOtherState());
				db.setInt(15, workExp.getWorkJobField());
				db.setInt(16, workExp.getWorkSubField());
				db.setInt(17, workExp.getComSize());
				db.setInt(18, workExp.getSubordinate());
				int year=0;
				int month=0;
				if(workExp.getWorkStart()!=null && workExp.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(workExp.getWorkStart(), workExp.getWorkEnd());
	        		year=totalMonth/12;
	        		month=totalMonth%12;
	        	}
				db.setInt(19, year);
				db.setInt(20, month);
				db.setString(21, workExp.getComBusiness());
				db.setString(22, workExp.getWorkJobFieldOth());
				db.setString(23, workExp.getWorkSubFieldOth());
				db.setString(24, workExp.getReasonQuit());
				db.setInt(25, workExp.getSalaryStart());
				db.setString(26, workExp.getPositionStart());
				db.setString(27, workExp.getSalaryPer());
				db.setString(28, workExp.getSalaryPerStart());
				db.setInt(29, workExp.getWorkJobType());
				db.setInt(30, workExp.getWorkJobFieldStart());
				db.setInt(31, workExp.getWorkSubFieldStart());
				db.setString(32, workExp.getWorkJobFieldOthStart());
				db.setString(33, workExp.getWorkSubFieldOthStart());
				db.setInt(34, workExp.getRefId());
				db.setString(35, workExp.getJobDesc2());
				db.setString(36, workExp.getAchieve2());
				db.setInt(37,workExp.getIdCurrency());
				db.setInt(38, workExp.getIdCurrencyStart());
				db.setInt(39, workExp.getIdRefer());
				db.setString(40, workExp.getHashRefer());
				
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
		}
		return result;
	}
	
	public int update(WorkExperience workExp)
	{
		int result = 0;
		DBManager db=null;
		String sql=	"UPDATE " +
					"	INTER_WORK_EXPERIENCE " +
					"SET " +
					"	COMPANY_NAME=?, " +
					"	POSITION_LAST=?, " +
					"	SALARY_LAST=?, " +
					"	WORK_START=?, " +
					"	WORK_END=?," +
					"	WORKING_STATUS=?, " +
					"	JOB_DESC=?, " +
					"	ACHIEVE=?, " +
					"	ID_COUNTRY=?, " +
					"	ID_STATE=?, " +
					"	OTHER_STATE=?, " +
					"	WORKJOB_FIELD=?, " +
					"	WORKSUB_FIELD=?, " +
					"	COM_SIZE=?, " +
					"	SUBORDINATE=?, " +
					"	EXP_Y=?, " +
					"	EXP_M=?, " +
					"	COM_BUSINESS=?, " +
					"	WORKJOB_FIELD_OTH=?, " +
					"	WORKSUB_FIELD_OTH=?, " +
					"	REASON_QUIT=?, " +
					"	SALARY_START=?, " +
					"	POSITION_START=?," +
					"	SALARY_PER=?, " +
					"	SALARY_PER_START=?, "+	
					"	WORK_JOBTYPE=?, " +
					"	WORKJOB_FIELD_START=?, " +
					"	WORKSUB_FIELD_START=?, " +
					"	WORKJOB_FIELD_START_OTH=?, " +
					"	WORKSUB_FIELD_START_OTH=?," +
					"	REF_ID=?," +
					"	JOB_DESC2=?, " +
					"	ACHIEVE2=?, " +
					"	ID_CURRENCY=?, "+ 
					"	ID_CURRENCY_START=?,"+
					" 	ID_REFER = ?, " +
					" 	HASH_REFER = ? " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID=?";
		try
		{
			String tmpDesc = workExp.getJobDesc()!=null?workExp.getJobDesc():"";
			if (!tmpDesc.equals("") && tmpDesc.length() > 2000) 
			{
				workExp.setJobDesc(tmpDesc.substring(0,2000));
				workExp.setJobDesc2(tmpDesc.substring(2000,tmpDesc.length()));
			}
			else 
			{
				workExp.setJobDesc2("");
			}
			
			String tmpAchieve = workExp.getAchieve()!=null?workExp.getAchieve():"";
			if (!tmpAchieve.equals("") && tmpAchieve.length() > 2000) 
			{
				workExp.setAchieve(tmpAchieve.substring(0,2000));
				workExp.setAchieve2(tmpAchieve.substring(2000,tmpAchieve.length()));
			}
			else 
			{
				workExp.setAchieve2("");
			}
			
			db=new DBManager();			
			db.createPreparedStatement(sql);
			db.setString(1, workExp.getCompanyName());
			db.setString(2, workExp.getPositionLast());
			db.setInt(3, workExp.getSalaryLast());
			db.setDate(4, workExp.getWorkStart());
			db.setDate(5, workExp.getWorkEnd());
			db.setString(6, workExp.getWorkingStatus());
			db.setString(7, workExp.getJobDesc());
			db.setString(8, workExp.getAchieve());
			db.setInt(9, workExp.getIdCountry());
			db.setInt(10, workExp.getIdState());
			db.setString(11, workExp.getOtherState());
			db.setInt(12, workExp.getWorkJobField());
			db.setInt(13, workExp.getWorkSubField());
			db.setInt(14, workExp.getComSize());
			db.setInt(15, workExp.getSubordinate());
			int year=0;
			int month=0;
			if(workExp.getWorkStart()!=null && workExp.getWorkEnd()!=null)
        	{
        		int totalMonth=Util.getMonthInterval(workExp.getWorkStart(), workExp.getWorkEnd());
        		year=totalMonth/12;
        		month=totalMonth%12;
        	}
			db.setInt(16, year);
			db.setInt(17, month);
			db.setString(18, workExp.getComBusiness());
			db.setString(19, workExp.getWorkJobFieldOth());
			db.setString(20, workExp.getWorkSubFieldOth());
			db.setString(21, workExp.getReasonQuit());
			db.setInt(22, workExp.getSalaryStart());
			db.setString(23, workExp.getPositionStart());
			db.setString(24, workExp.getSalaryPer());
			db.setString(25, workExp.getSalaryPerStart());
			db.setInt(26, workExp.getWorkJobType());
			db.setInt(27, workExp.getWorkJobFieldStart());
			db.setInt(28, workExp.getWorkSubFieldStart());
			db.setString(29, workExp.getWorkJobFieldOthStart());
			db.setString(30, workExp.getWorkSubFieldOthStart());
			db.setInt(31, workExp.getRefId());
			db.setString(32, workExp.getJobDesc2());
			db.setString(33, workExp.getAchieve2());
			db.setInt(34,workExp.getIdCurrency());
			db.setInt(35, workExp.getIdCurrencyStart());
			db.setInt(36, workExp.getIdRefer());
			db.setString(37, workExp.getHashRefer());
			db.setInt(38, workExp.getIdJsk());
			db.setInt(39, workExp.getIdResume());
			db.setInt(40, workExp.getId());			
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
	
	public int delete(WorkExperience workExp)
	{
		int result = 0;
		DBManager db=null;
		String sql="DELETE FROM INTER_WORK_EXPERIENCE  WHERE ID_JSK=? AND ID_RESUME=? AND ID=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, workExp.getIdJsk());
			db.setInt(2, workExp.getIdResume());
			db.setInt(3, workExp.getId());
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
	
	public int delete(int idJsk, int idResume, int id)
	{
		int result = 0;
		DBManager db=null;
		String sql="DELETE FROM INTER_WORK_EXPERIENCE  WHERE ID_JSK=? AND ID_RESUME=? AND ID=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, id);
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
	
	public int deleteAll(int idJsk, int idResume )
	{
		int result = 0;
		DBManager db=null;
		String sql="DELETE FROM INTER_WORK_EXPERIENCE  WHERE ID_JSK=? AND ID_RESUME=? ";
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
	
	public WorkExperience get(int idJsk,int idResume, int id)
	{
		WorkExperience result=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID, COMPANY_NAME, POSITION_LAST, SALARY_LAST, WORK_START, " +
					"	WORK_END, WORKING_STATUS, JOB_DESC, ACHIEVE, ID_COUNTRY, ID_STATE, OTHER_STATE, " +
					"	WORKJOB_FIELD, WORKSUB_FIELD, COM_SIZE, SUBORDINATE, EXP_Y, EXP_M, COM_BUSINESS, " +
					"	WORKJOB_FIELD_OTH, WORKSUB_FIELD_OTH, REASON_QUIT, SALARY_START, POSITION_START, " +
					"	SALARY_PER, SALARY_PER_START, WORK_JOBTYPE, WORKJOB_FIELD_START, WORKSUB_FIELD_START, WORKJOB_FIELD_START_OTH, " +
					"	WORKSUB_FIELD_START_OTH, REF_ID, DELETE_STATUS, JOB_DESC2, ACHIEVE2, SALARY_PER_MONTH, ID_CURRENCY, ID_CURRENCY_START, ID_REFER, HASH_REFER " +
					"FROM " +
					"	INTER_WORK_EXPERIENCE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID=? AND DELETE_STATUS<>'TRUE' " +
					"ORDER BY" +
					"	ID";
		DBManager db=null;
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, id);
			db.executeQuery();
			if(db.next())
			{ 
				result=new WorkExperience();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setId(db.getInt("ID"));
				result.setIdRefer(db.getInt("ID_REFER"));
				result.setHashRefer(db.getString("HASH_REFER"));
				result.setCompanyName(db.getString("COMPANY_NAME"));
				result.setPositionLast(db.getString("POSITION_LAST"));
				result.setSalaryLast(db.getInt("SALARY_LAST"));
				result.setJobDesc(Util.getStr(db.getString("JOB_DESC"))+Util.getStr(db.getString("JOB_DESC2")));
				result.setAchieve(Util.getStr(db.getString("ACHIEVE"))+Util.getStr(db.getString("ACHIEVE2")));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				result.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				result.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				result.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				result.setComSize(db.getInt("COM_SIZE"));
				result.setSubordinate(db.getInt("SUBORDINATE"));
				result.setComBusiness(db.getString("COM_BUSINESS"));
				result.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				result.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				result.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				result.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				result.setReasonQuit(db.getString("REASON_QUIT"));
				result.setSalaryStart(db.getInt("SALARY_START"));
				result.setPositionStart(db.getString("POSITION_START"));
				result.setSalaryPer(db.getString("SALARY_PER"));
				result.setSalaryPerStart(db.getString("SALARY_PER_START"));
				result.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setIdCurrency(db.getInt("ID_CURRENCY"));
				result.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				result.setWorkStart(db.getDate("WORK_START"));
				result.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
				if(result.getWorkingStatus().equals("TRUE"))
		    	{
					result.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		result.setWorkEnd(db.getDate("WORK_END"));
		    	}
				if(result.getWorkStart()!=null && result.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(result.getWorkStart(), result.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		result.setExpM(month);
	        		result.setExpY(year);
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
	
	public WorkExperience getWorkExperienceReference(int idJsk,int idResume, int idReference)
	{
		WorkExperience result=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID, COMPANY_NAME, POSITION_LAST, SALARY_LAST, WORK_START, " +
					"	WORK_END, WORKING_STATUS, JOB_DESC, ACHIEVE, ID_COUNTRY, ID_STATE, OTHER_STATE, " +
					"	WORKJOB_FIELD, WORKSUB_FIELD, COM_SIZE, SUBORDINATE, EXP_Y, EXP_M, COM_BUSINESS, " +
					"	WORKJOB_FIELD_OTH, WORKSUB_FIELD_OTH, REASON_QUIT, SALARY_START, POSITION_START, " +
					"	SALARY_PER, SALARY_PER_START, WORK_JOBTYPE, WORKJOB_FIELD_START, WORKSUB_FIELD_START, WORKJOB_FIELD_START_OTH, " +
					"	WORKSUB_FIELD_START_OTH, REF_ID, DELETE_STATUS, JOB_DESC2, ACHIEVE2, SALARY_PER_MONTH, ID_CURRENCY, ID_CURRENCY_START, ID_REFER , HASH_REFER " +
					"FROM " +
					"	INTER_WORK_EXPERIENCE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND ID_REFER=? AND DELETE_STATUS<>'TRUE' ";
		DBManager db=null;
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idReference);
			db.executeQuery();
			if(db.next())
			{ 
				result=new WorkExperience();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setId(db.getInt("ID"));
				result.setIdRefer(db.getInt("ID_REFER"));
				result.setHashRefer(db.getString("HASH_REFER"));
				result.setCompanyName(db.getString("COMPANY_NAME"));
				result.setPositionLast(db.getString("POSITION_LAST"));
				result.setSalaryLast(db.getInt("SALARY_LAST"));
				result.setJobDesc(Util.getStr(db.getString("JOB_DESC"))+Util.getStr(db.getString("JOB_DESC2")));
				result.setAchieve(Util.getStr(db.getString("ACHIEVE"))+Util.getStr(db.getString("ACHIEVE2")));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				result.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				result.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				result.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				result.setComSize(db.getInt("COM_SIZE"));
				result.setSubordinate(db.getInt("SUBORDINATE"));
				result.setComBusiness(db.getString("COM_BUSINESS"));
				result.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				result.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				result.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				result.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				result.setReasonQuit(db.getString("REASON_QUIT"));
				result.setSalaryStart(db.getInt("SALARY_START"));
				result.setPositionStart(db.getString("POSITION_START"));
				result.setSalaryPer(db.getString("SALARY_PER"));
				result.setSalaryPerStart(db.getString("SALARY_PER_START"));
				result.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setIdCurrency(db.getInt("ID_CURRENCY"));
				result.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				result.setWorkStart(db.getDate("WORK_START"));
				result.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
				if(result.getWorkingStatus().equals("TRUE"))
		    	{
					result.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		result.setWorkEnd(db.getDate("WORK_END"));
		    	}
				if(result.getWorkStart()!=null && result.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(result.getWorkStart(), result.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		result.setExpM(month);
	        		result.setExpY(year);
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
	
	public WorkExperience getWorkExperienceHashReference(int idJsk,int idResume,String hashRefer)
	{
		WorkExperience result=null;
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID, COMPANY_NAME, POSITION_LAST, SALARY_LAST, WORK_START, " +
					"	WORK_END, WORKING_STATUS, JOB_DESC, ACHIEVE, ID_COUNTRY, ID_STATE, OTHER_STATE, " +
					"	WORKJOB_FIELD, WORKSUB_FIELD, COM_SIZE, SUBORDINATE, EXP_Y, EXP_M, COM_BUSINESS, " +
					"	WORKJOB_FIELD_OTH, WORKSUB_FIELD_OTH, REASON_QUIT, SALARY_START, POSITION_START, " +
					"	SALARY_PER, SALARY_PER_START, WORK_JOBTYPE, WORKJOB_FIELD_START, WORKSUB_FIELD_START, WORKJOB_FIELD_START_OTH, " +
					"	WORKSUB_FIELD_START_OTH, REF_ID, DELETE_STATUS, JOB_DESC2, ACHIEVE2, SALARY_PER_MONTH, ID_CURRENCY, ID_CURRENCY_START, ID_REFER , HASH_REFER " +
					"FROM " +
					"	INTER_WORK_EXPERIENCE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND HASH_REFER=? AND DELETE_STATUS<>'TRUE' ";
		DBManager db=null;
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setString(3, hashRefer);
			db.executeQuery();
			if(db.next())
			{ 
				result=new WorkExperience();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setId(db.getInt("ID"));
				result.setIdRefer(db.getInt("ID_REFER"));
				result.setHashRefer(db.getString("HASH_REFER"));
				result.setCompanyName(db.getString("COMPANY_NAME"));
				result.setPositionLast(db.getString("POSITION_LAST"));
				result.setSalaryLast(db.getInt("SALARY_LAST"));
				result.setJobDesc(Util.getStr(db.getString("JOB_DESC"))+Util.getStr(db.getString("JOB_DESC2")));
				result.setAchieve(Util.getStr(db.getString("ACHIEVE"))+Util.getStr(db.getString("ACHIEVE2")));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				result.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				result.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				result.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				result.setComSize(db.getInt("COM_SIZE"));
				result.setSubordinate(db.getInt("SUBORDINATE"));
				result.setComBusiness(db.getString("COM_BUSINESS"));
				result.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				result.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				result.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				result.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				result.setReasonQuit(db.getString("REASON_QUIT"));
				result.setSalaryStart(db.getInt("SALARY_START"));
				result.setPositionStart(db.getString("POSITION_START"));
				result.setSalaryPer(db.getString("SALARY_PER"));
				result.setSalaryPerStart(db.getString("SALARY_PER_START"));
				result.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setIdCurrency(db.getInt("ID_CURRENCY"));
				result.setIdCurrencyStart(db.getInt("ID_CURRENCY"));
				result.setWorkStart(db.getDate("WORK_START"));
				result.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
				if(result.getWorkingStatus().equals("TRUE"))
		    	{
					result.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		result.setWorkEnd(db.getDate("WORK_END"));
		    	}
				if(result.getWorkStart()!=null && result.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(result.getWorkStart(), result.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		result.setExpM(month);
	        		result.setExpY(year);
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
	
	public List<WorkExperience> getAll(int idJsk,int idResume)
	{
		List<WorkExperience> result = new ArrayList<WorkExperience>();
		String sql=	"SELECT " +
					"	ID_JSK, ID_RESUME, ID, COMPANY_NAME, POSITION_LAST, SALARY_LAST, WORK_START, " +
					"	WORK_END, WORKING_STATUS, JOB_DESC, ACHIEVE, ID_COUNTRY, ID_STATE, OTHER_STATE, " +
					"	WORKJOB_FIELD, WORKSUB_FIELD, COM_SIZE, SUBORDINATE, EXP_Y, EXP_M, COM_BUSINESS, " +
					"	WORKJOB_FIELD_OTH, WORKSUB_FIELD_OTH, REASON_QUIT, SALARY_START, POSITION_START, " +
					"	SALARY_PER, SALARY_PER_START, WORK_JOBTYPE, WORKJOB_FIELD_START, WORKSUB_FIELD_START, WORKJOB_FIELD_START_OTH, " +
					"	WORKSUB_FIELD_START_OTH, REF_ID, DELETE_STATUS, JOB_DESC2, ACHIEVE2, SALARY_PER_MONTH, ID_CURRENCY, ID_CURRENCY_START,ID_REFER,HASH_REFER  " +
					"FROM " +
					"	INTER_WORK_EXPERIENCE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS<>'TRUE' " +
					"ORDER BY" +
					"	ID";
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
		    	WorkExperience workExp=new WorkExperience();
		    	workExp.setIdJsk(db.getInt("ID_JSK"));
		    	workExp.setIdResume(db.getInt("ID_RESUME"));
		    	workExp.setId(db.getInt("ID"));
		    	workExp.setIdRefer(db.getInt("ID_REFER"));
		    	workExp.setHashRefer(db.getString("HASH_REFER"));
		    	workExp.setCompanyName(db.getString("COMPANY_NAME"));
		    	workExp.setPositionLast(db.getString("POSITION_LAST"));
		    	workExp.setSalaryLast(db.getInt("SALARY_LAST"));
				workExp.setJobDesc(db.getString("JOB_DESC")!=null?db.getString("JOB_DESC"):""+db.getString("JOB_DESC2")!=null?db.getString("JOB_DESC2"):"");
				workExp.setAchieve(db.getString("ACHIEVE")!=null?db.getString("ACHIEVE"):""+ db.getString("ACHIEVE2")!=null?db.getString("ACHIEVE2"):"");
				workExp.setIdCountry(db.getInt("ID_COUNTRY"));
				workExp.setIdState(db.getInt("ID_STATE"));
				workExp.setOtherState(db.getString("OTHER_STATE"));
				workExp.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				workExp.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				workExp.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				workExp.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				workExp.setComSize(db.getInt("COM_SIZE"));
				workExp.setSubordinate(db.getInt("SUBORDINATE"));
				workExp.setComBusiness(db.getString("COM_BUSINESS"));
				workExp.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				workExp.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				workExp.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				workExp.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				workExp.setReasonQuit(db.getString("REASON_QUIT"));
				workExp.setSalaryStart(db.getInt("SALARY_START"));
				workExp.setPositionStart(db.getString("POSITION_START"));
				workExp.setSalaryPer(db.getString("SALARY_PER"));
				workExp.setSalaryPerStart(db.getString("SALARY_PER_START"));
				workExp.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				workExp.setRefId(db.getInt("REF_ID"));
				workExp.setIdCurrency(db.getInt("ID_CURRENCY"));
				workExp.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				workExp.setWorkStart(db.getDate("WORK_START"));
		    	workExp.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
		    	
		    	if(workExp.getWorkingStatus().equals("TRUE"))
		    	{
		    		workExp.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		workExp.setWorkEnd(db.getDate("WORK_END"));
		    	}
				if(workExp.getWorkStart()!=null && workExp.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(workExp.getWorkStart(), workExp.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		workExp.setExpM(month);
	        		workExp.setExpY(year);
	        	}				
		    	result.add(workExp);
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
	
	public int getNextId(int idJsk,int idResume)
	{
		int result=1;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT MAX(ID) AS MAXID FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=?");
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("MAXID")+1;
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
	
	//used for jobseekers that registered before version 9.2 only
	public WorkExperience getLatestExperience (int idJsk,int idResume)
	{
		WorkExperience result=null;
		String SQL="SELECT * FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS<>'TRUE' ORDER BY WORK_END DESC";
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
				result=new WorkExperience();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setId(db.getInt("ID"));
				result.setCompanyName(db.getString("COMPANY_NAME"));
				result.setPositionLast(db.getString("POSITION_LAST"));
				result.setSalaryLast(db.getInt("SALARY_LAST"));
				result.setJobDesc(db.getString("JOB_DESC")!=null?db.getString("JOB_DESC"):""+db.getString("JOB_DESC2")!=null?db.getString("JOB_DESC2"):"");
				result.setAchieve(db.getString("ACHIEVE")!=null?db.getString("ACHIEVE"):""+ db.getString("ACHIEVE2")!=null?db.getString("ACHIEVE2"):"");
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				result.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				result.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				result.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				result.setComSize(db.getInt("COM_SIZE"));
				result.setSubordinate(db.getInt("SUBORDINATE"));
				result.setComBusiness(db.getString("COM_BUSINESS"));
				result.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				result.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				result.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				result.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				result.setReasonQuit(db.getString("REASON_QUIT"));
				result.setSalaryStart(db.getInt("SALARY_START"));
				result.setPositionStart(db.getString("POSITION_START"));
				result.setSalaryPer(db.getString("SALARY_PER"));
				result.setSalaryPerStart(db.getString("SALARY_PER_START"));
				result.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setIdCurrency(db.getInt("ID_CURRENCY"));
				result.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				result.setWorkStart(db.getDate("WORK_START"));
				result.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
		    	if(result.getWorkingStatus().equals("TRUE"))
		    	{
		    		result.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		result.setWorkEnd(db.getDate("WORK_END"));
		    	}
		    	
				if(result.getWorkStart()!=null && result.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(result.getWorkStart(), result.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		result.setExpM(month);
	        		result.setExpY(year);
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
	
	public WorkExperience getLatestExperience (int idJsk,int idResume,int section)
	{
		String jobTypes="";
		if(section==1)
		{
			jobTypes=" AND WORK_JOBTYPE in (1,3,5)";
		}
		else if(section==2)
		{
			jobTypes=" AND WORK_JOBTYPE in (2,4)";
		}
		else if(section==3)
		{
			jobTypes=" AND WORK_JOBTYPE in (7)";
		}
		WorkExperience result=null;
		String SQL="SELECT * FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS<>'TRUE' "+jobTypes+" ORDER BY WORK_END DESC";
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
				result=new WorkExperience();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setId(db.getInt("ID"));
				result.setCompanyName(db.getString("COMPANY_NAME"));
				result.setPositionLast(db.getString("POSITION_LAST"));
				result.setSalaryLast(db.getInt("SALARY_LAST"));
				result.setJobDesc(db.getString("JOB_DESC")!=null?db.getString("JOB_DESC"):""+db.getString("JOB_DESC2")!=null?db.getString("JOB_DESC2"):"");
				result.setAchieve(db.getString("ACHIEVE")!=null?db.getString("ACHIEVE"):""+ db.getString("ACHIEVE2")!=null?db.getString("ACHIEVE2"):"");
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				result.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				result.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				result.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				result.setComSize(db.getInt("COM_SIZE"));
				result.setSubordinate(db.getInt("SUBORDINATE"));
				result.setComBusiness(db.getString("COM_BUSINESS"));
				result.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				result.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				result.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				result.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				result.setReasonQuit(db.getString("REASON_QUIT"));
				result.setSalaryStart(db.getInt("SALARY_START"));
				result.setPositionStart(db.getString("POSITION_START"));
				result.setSalaryPer(db.getString("SALARY_PER"));
				result.setSalaryPerStart(db.getString("SALARY_PER_START"));
				result.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setIdCurrency(db.getInt("ID_CURRENCY"));
				result.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				result.setWorkStart(db.getDate("WORK_START"));
				result.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
		    	if(result.getWorkingStatus().equals("TRUE"))
		    	{
		    		result.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		result.setWorkEnd(db.getDate("WORK_END"));
		    	}
		    	
				if(result.getWorkStart()!=null && result.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(result.getWorkStart(), result.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		result.setExpM(month);
	        		result.setExpY(year);
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
	
	public WorkExperience getLatestExperienceNew(int idJsk, int idResume)
	{
		WorkExperience result=null;
		String SQL=" SELECT B.* FROM   "
				+ " 	(SELECT ID_JSK,ID_RESUME,MAX(WORK_END) AS WORK_END "
				+ " 	FROM INTER_WORK_EXPERIENCE   "
				+ " 	WHERE  DELETE_STATUS<>'TRUE' "
				+ "  	GROUP BY ID_JSK,ID_RESUME ) A,INTER_WORK_EXPERIENCE B  "
				+ "  WHERE A.ID_JSK=B.ID_JSK AND A.ID_RESUME = B.ID_RESUME  AND  A.WORK_END = B.WORK_END "
				+ "  AND A.ID_JSK = ? AND A.ID_RESUME = ?  AND B.DELETE_STATUS<>'TRUE' ORDER BY A.ID_JSK ";
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
				result=new WorkExperience();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setId(db.getInt("ID"));
				result.setCompanyName(db.getString("COMPANY_NAME"));
				result.setPositionLast(db.getString("POSITION_LAST"));
				result.setSalaryLast(db.getInt("SALARY_LAST"));
				result.setJobDesc(db.getString("JOB_DESC")!=null?db.getString("JOB_DESC"):""+db.getString("JOB_DESC2")!=null?db.getString("JOB_DESC2"):"");
				result.setAchieve(db.getString("ACHIEVE")!=null?db.getString("ACHIEVE"):""+ db.getString("ACHIEVE2")!=null?db.getString("ACHIEVE2"):"");
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				result.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				result.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				result.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				result.setComSize(db.getInt("COM_SIZE"));
				result.setSubordinate(db.getInt("SUBORDINATE"));
				result.setComBusiness(db.getString("COM_BUSINESS"));
				result.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				result.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				result.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				result.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				result.setReasonQuit(db.getString("REASON_QUIT"));
				result.setSalaryStart(db.getInt("SALARY_START"));
				result.setPositionStart(db.getString("POSITION_START"));
				result.setSalaryPer(db.getString("SALARY_PER"));
				result.setSalaryPerStart(db.getString("SALARY_PER_START"));
				result.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				result.setRefId(db.getInt("REF_ID"));
				result.setIdCurrency(db.getInt("ID_CURRENCY"));
				result.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				result.setWorkStart(db.getDate("WORK_START"));
				result.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
		    	if(result.getWorkingStatus().equals("TRUE"))
		    	{
		    		result.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		result.setWorkEnd(db.getDate("WORK_END"));
		    	}
		    	
				if(result.getWorkStart()!=null && result.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(result.getWorkStart(), result.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		result.setExpM(month);
	        		result.setExpY(year);
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
	
	public List<WorkExperience> getAllParttimes(int idJsk, int idResume)
	{
		List<WorkExperience> result = new ArrayList<WorkExperience>();
		String SQL=	"SELECT * FROM " +
					"	INTER_WORK_EXPERIENCE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS='FALSE' AND " +
					"	(WORK_JOBTYPE = 2  OR WORK_JOBTYPE = 4  OR  WORK_JOBTYPE = 7) " +
					"ORDER BY " +
					"	WORK_END DESC";		

		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	WorkExperience workExp=new WorkExperience();
		    	workExp.setIdJsk(db.getInt("ID_JSK"));
		    	workExp.setIdResume(db.getInt("ID_RESUME"));
		    	workExp.setId(db.getInt("ID"));
		    	workExp.setCompanyName(db.getString("COMPANY_NAME"));
		    	workExp.setPositionLast(db.getString("POSITION_LAST"));
		    	workExp.setSalaryLast(db.getInt("SALARY_LAST"));
				workExp.setJobDesc(db.getString("JOB_DESC")!=null?db.getString("JOB_DESC"):""+db.getString("JOB_DESC2")!=null?db.getString("JOB_DESC2"):"");
				workExp.setAchieve(db.getString("ACHIEVE")!=null?db.getString("ACHIEVE"):""+ db.getString("ACHIEVE2")!=null?db.getString("ACHIEVE2"):"");
				workExp.setIdCountry(db.getInt("ID_COUNTRY"));
				workExp.setIdState(db.getInt("ID_STATE"));
				workExp.setOtherState(db.getString("OTHER_STATE"));
				workExp.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				workExp.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				workExp.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				workExp.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				workExp.setComSize(db.getInt("COM_SIZE"));
				workExp.setSubordinate(db.getInt("SUBORDINATE"));
				workExp.setComBusiness(db.getString("COM_BUSINESS"));
				workExp.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				workExp.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				workExp.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				workExp.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				workExp.setReasonQuit(db.getString("REASON_QUIT"));
				workExp.setSalaryStart(db.getInt("SALARY_START"));
				workExp.setPositionStart(db.getString("POSITION_START"));
				workExp.setSalaryPer(db.getString("SALARY_PER"));
				workExp.setSalaryPerStart(db.getString("SALARY_PER_START"));
				workExp.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				workExp.setRefId(db.getInt("REF_ID"));
				workExp.setIdCurrency(db.getInt("ID_CURRENCY"));
				workExp.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				workExp.setWorkStart(db.getDate("WORK_START"));
		    	workExp.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
		    	if(workExp.getWorkingStatus().equals("TRUE"))
		    	{
		    		workExp.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		workExp.setWorkEnd(db.getDate("WORK_END"));
		    	}
		    	
				if(workExp.getWorkStart()!=null && workExp.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(workExp.getWorkStart(), workExp.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		workExp.setExpM(month);
	        		workExp.setExpY(year);
	        	}
				
		    	result.add(workExp);
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

	public List<WorkExperience> getAllFulltimes(int idJsk, int idResume)
	{
		List<WorkExperience> result = new ArrayList<WorkExperience>();
		String SQL=	"SELECT * FROM " +
					"	INTER_WORK_EXPERIENCE " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS='FALSE' AND " +
					"	(WORK_JOBTYPE <> 2 AND WORK_JOBTYPE <> 4  AND  WORK_JOBTYPE <> 7) " +
					"ORDER BY " +
					"	WORK_END DESC";		

		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	WorkExperience workExp=new WorkExperience();
		    	workExp.setIdJsk(db.getInt("ID_JSK"));
		    	workExp.setIdResume(db.getInt("ID_RESUME"));
		    	workExp.setId(db.getInt("ID"));
		    	workExp.setCompanyName(db.getString("COMPANY_NAME"));
		    	workExp.setPositionLast(db.getString("POSITION_LAST"));
		    	workExp.setSalaryLast(db.getInt("SALARY_LAST"));
				workExp.setJobDesc(db.getString("JOB_DESC")!=null?db.getString("JOB_DESC"):""+db.getString("JOB_DESC2")!=null?db.getString("JOB_DESC2"):"");
				workExp.setAchieve(db.getString("ACHIEVE")!=null?db.getString("ACHIEVE"):""+ db.getString("ACHIEVE2")!=null?db.getString("ACHIEVE2"):"");
				workExp.setIdCountry(db.getInt("ID_COUNTRY"));
				workExp.setIdState(db.getInt("ID_STATE"));
				workExp.setOtherState(db.getString("OTHER_STATE"));
				workExp.setWorkJobField(db.getInt("WORKJOB_FIELD"));
				workExp.setWorkSubField(db.getInt("WORKSUB_FIELD"));
				workExp.setWorkJobFieldStart(db.getInt("WORKJOB_FIELD_START"));
				workExp.setWorkSubFieldStart(db.getInt("WORKSUB_FIELD_START"));
				workExp.setComSize(db.getInt("COM_SIZE"));
				workExp.setSubordinate(db.getInt("SUBORDINATE"));
				workExp.setComBusiness(db.getString("COM_BUSINESS"));
				workExp.setWorkJobFieldOth(db.getString("WORKJOB_FIELD_OTH"));
				workExp.setWorkSubFieldOth(db.getString("WORKSUB_FIELD_OTH"));
				workExp.setWorkJobFieldOthStart(db.getString("WORKJOB_FIELD_START_OTH"));
				workExp.setWorkSubFieldOthStart(db.getString("WORKSUB_FIELD_START_OTH"));
				workExp.setReasonQuit(db.getString("REASON_QUIT"));
				workExp.setSalaryStart(db.getInt("SALARY_START"));
				workExp.setPositionStart(db.getString("POSITION_START"));
				workExp.setSalaryPer(db.getString("SALARY_PER"));
				workExp.setSalaryPerStart(db.getString("SALARY_PER_START"));
				workExp.setWorkJobType(db.getInt("WORK_JOBTYPE"));
				workExp.setRefId(db.getInt("REF_ID"));
				workExp.setIdCurrency(db.getInt("ID_CURRENCY"));
				workExp.setIdCurrencyStart(db.getInt("ID_CURRENCY_START"));
				workExp.setWorkStart(db.getDate("WORK_START"));
		    	workExp.setWorkingStatus(Util.getStr(db.getString("WORKING_STATUS")));
		    	if(workExp.getWorkingStatus().equals("TRUE"))
		    	{
		    		workExp.setWorkEnd(Util.getCurrentSQLDate());
		    	}
		    	else
		    	{
		    		workExp.setWorkEnd(db.getDate("WORK_END"));
		    	}
				if(workExp.getWorkStart()!=null && workExp.getWorkEnd()!=null)
	        	{
	        		int totalMonth=Util.getMonthInterval(workExp.getWorkStart(), workExp.getWorkEnd());
	        		int year=totalMonth/12;
	        		int month=totalMonth%12;
	        		workExp.setExpM(month);
	        		workExp.setExpY(year);
	        	}
	        	
		    	result.add(workExp);
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
		String sql=	"SELECT COUNT(ID) AS TOTAL FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS<>'TRUE'";
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
	public boolean isExist(int idJsk)
	{
		boolean result=false;
		String sql="SELECT ID_JSK FROM INTER_EXPERIENCE_QUESTION WHERE ID_JSK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
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
		return result;
	}	
	
	public int addExpQuestion(int idJsk, int fullTime, int parttime, int intern)
	{
		int result = 0;
			DBManager db=null;
			String sql=" INSERT INTO INTER_EXPERIENCE_QUESTION(ID_JSK, FULLTIME, PARTTIME, INTERN) " +
						" VALUES(?,?,?,?)";
			try
			{
				
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setInt(1, idJsk);
				db.setInt(2, fullTime);
				db.setInt(3, parttime);
				db.setInt(4, intern);
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
	
	public int updateExpQuestion(int idJsk, int fullTime, int parttime, int intern)
	{
		int result = 0;
		DBManager db=null;
		String sql=	" UPDATE " +
					"	INTER_EXPERIENCE_QUESTION " +
					" SET " +
					"	FULLTIME=?, " +
					"	PARTTIME=?, " +
					"	INTERN=?, " +
					"	SKIPED_PARTTIME=0, SKIPED_INTERN=0" +
					" WHERE " +
					"	ID_JSK=? ";
		try
		{
			db=new DBManager();			
			db.createPreparedStatement(sql);
			db.setInt(1, fullTime);
			db.setInt(2, parttime);
			db.setInt(3, intern);
			db.setInt(4, idJsk);
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
	
	public int updateSkip(int idJsk, int workType)
	{
		int result = 0;
		String skip="";
		if(workType==2)
		{
			skip="SKIPED_PARTTIME=1";
		}
		else if(workType==7)
		{
			skip="SKIPED_INTERN=1";
		}
		DBManager db=null;
		String sql=	" UPDATE " +
					"	INTER_EXPERIENCE_QUESTION " +
					" SET " +skip+
					" WHERE " +
					"	ID_JSK=? ";
		try
		{
			db=new DBManager();			
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
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
	
	public int[] getAnsQuestion(int idJsk)
	{
		int[] result=null;
		String sql="SELECT * FROM INTER_EXPERIENCE_QUESTION WHERE ID_JSK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=new int[5];
				result[0]=db.getInt("FULLTIME");
				result[1]=db.getInt("PARTTIME");
				result[2]=db.getInt("INTERN");
				result[3]=db.getInt("SKIPED_PARTTIME");
				result[4]=db.getInt("SKIPED_INTERN");
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
	
	public int  getNextExpStep(Resume resume)
	{
		int result=0;
		int[] rs=getAnsQuestion(resume.getIdJsk());
		if(rs!=null)
		{
         	if(rs[0]==1)
			{
         		WorkExperience workLast=new WorkExperienceManager().getLatestExperience(resume.getIdJsk(), resume.getIdResume(),1);
         		if(workLast!= null)
				{
         			List<String> messages=new ResumeStatusManager().checkCompleteWorkingExperience(workLast, resume);
					if(messages.size()>0)
					{
						result=1;
					}
					else
					{
						result=4;
					}
				}
				else 
				{
					result=1;
				}
			}
			else
			{
				if(rs[1]==1 && rs[2]==1 )
				{
					if(rs[3]==1)//JSK has skip parttime
					{
						if(rs[4]==1)//JSK has skip intern
						{
							result=4;//goto education page
						}
						else
						{
			         		WorkExperience workLast=new WorkExperienceManager().getLatestExperience(resume.getIdJsk(), resume.getIdResume(),3);
			         		if(workLast!= null)
							{
			         			List<String> messages=new ResumeStatusManager().checkCompleteWorkingExperience(workLast, resume);
								if(messages.size()>0)
								{
									result=3;//goto intern page
								}
								else
								{
									result=4;
								}
							}
							else 
							{
								result=3;//goto intern page
							}
						}
					}
					else
					{
		         		WorkExperience workLast=new WorkExperienceManager().getLatestExperience(resume.getIdJsk(), resume.getIdResume(),2);
		         		if(workLast!= null)
						{
		         			List<String> messages=new ResumeStatusManager().checkCompleteWorkingExperience(workLast, resume);
							if(messages.size()>0)
							{
								result=2;//goto parttime page
							}
							else
							{
								result=4;
							}
						}
						else 
						{
							result=2;//goto parttime page
						}
					}
				}
				else if(rs[1]==1 && rs[2]==0 )
				{
					if(rs[3]==1)//JSK has skip parttime
					{
						result=4;//goto education page
					}
					else
					{
						WorkExperience workLast=new WorkExperienceManager().getLatestExperience(resume.getIdJsk(), resume.getIdResume(),2);
		         		if(workLast!= null)
						{
		         			List<String> messages=new ResumeStatusManager().checkCompleteWorkingExperience(workLast, resume);
							if(messages.size()>0)
							{
								result=2;//goto parttime page
							}
							else
							{
								result=4;
							}
						}
						else 
						{
							result=2;//goto parttime page
						}
					}
				}
				else if(rs[1]==0 && rs[2]==1 )
				{
					if(rs[4]==1)//JSK has skip intern
					{
						result=4;//goto education page
					}
					else
					{
						WorkExperience workLast=new WorkExperienceManager().getLatestExperience(resume.getIdJsk(), resume.getIdResume(),3);
		         		if(workLast!= null)
						{
		         			List<String> messages=new ResumeStatusManager().checkCompleteWorkingExperience(workLast, resume);
							if(messages.size()>0)
							{
								result=3;//goto intern page
							}
							else
							{
								result=4;
							}
						}
						else 
						{
							result=3;//goto intern page
						}
					}
				}
				else if(rs[1]==0 && rs[2]==0 )
				{
					result=4;//goto education page
				}
			}
		}
		return result;
	}
	
	public static int[] getExpTotal(int idJsk,int idResume)
	{
		int[] result=new int[4];
		result[0]=0;
		result[1]=0;
		result[2]=0;
		result[3]=0;
		DBManager db = null;
		String sql="";
		try
		{
			db = new DBManager();
			sql=" 	SELECT   FLOOR (TOTAL_FULL / 12) AS TOTAL_FULL_Y, MOD (TOTAL_FULL, 12) AS TOTAL_FULL_M, "
					+ "          FLOOR (TOTAL_PT / 12) AS TOTAL_PT_Y, MOD (TOTAL_PT, 12) AS TOTAL_PT_M "
					+ "   FROM   ("
					+ "				SELECT   "
					+ "					SUM ( ( (EXP_Y * 12) + EXP_M)) AS TOTAL_FULL "
					+ "             FROM   "
					+ "					INTER_WORK_EXPERIENCE "
					+ "           	WHERE   "
					+ "					ID_JSK = ? AND ID_RESUME = ? AND DELETE_STATUS = 'FALSE' "
					+ "                 AND WORK_JOBTYPE not in ( 2,4,7)"
					+ "			 ), "
					+ "          ("
					+ "				SELECT   "
					+ "					SUM ( ( (EXP_Y * 12) + EXP_M)) AS TOTAL_PT "
					+ "             FROM   "
					+ "					INTER_WORK_EXPERIENCE "
					+ "           	WHERE   "
					+ "					ID_JSK = ? AND ID_RESUME = ? AND DELETE_STATUS = 'FALSE' "
					+ "                   AND WORK_JOBTYPE in ( 2,4,7)"
					+ "			) ";
		    db.createPreparedStatement(sql);
		    db.setInt(1, idJsk);
		    db.setInt(2, idResume);
		    db.setInt(3, idJsk);
		    db.setInt(4, idResume);
		    db.executeQuery();
		    if(db.next())
		    {
		    	result[0]=db.getInt("TOTAL_FULL_Y");
		    	result[1]=db.getInt("TOTAL_FULL_M");
		    	result[2]=db.getInt("TOTAL_PT_Y");
		    	result[3]=db.getInt("TOTAL_PT_M");
		    }
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return result;
	}
	
	public int updateWorkingExpTotal(int idJsk, int idResume, int[] exp)
	{
		DBManager db = null;
		int updated = 0;
		String sql = "";
		try
		{
			if(exp != null && exp.length >=4)
			{
				db = new DBManager();
				sql = "UPDATE "
						+ " INTER_RESUME "
					+ " SET "
						+ " EXP_FULL_YEAR = ?"
						+ ", EXP_FULL_MONTH = ?"
						+ ", EXP_PT_YEAR = ? "
						+ ", EXP_PT_MONTH = ? "
					+ " WHERE "
						+ " ID_JSK = ? "
						+ " AND ID_RESUME = ?";
				db.createPreparedStatement(sql);
				db.setInt(1, exp[0]);
				db.setInt(2, exp[1]);
				db.setInt(3, exp[2]);
				db.setInt(4, exp[3]);
				db.setInt(5, idJsk);
				db.setInt(6, idResume);
				updated = db.executeUpdate();
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
		return updated;
	}
	
	
	
	public void addExpLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_WORKEXP_VIEW_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
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
	
	public void addResponsibilityLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_WORKEXP_RES_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
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
	
	public void addAchievementLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_WORKEXP_ACHIEVE_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
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
	
	public void addSkipLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_WORKEXP_SKIP_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
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
	
	public void addSubmitLog(int idJsk, int idResume)
	{
		String sql = "";
		DBManager db = null ;
		try
		{
			sql = "INSERT INTO FILL_WORKEXP_SUBMIT_LOG(ID_JSK,ID_RESUME) VALUES(?,?)";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeUpdate();
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
	
	
	
	
}
