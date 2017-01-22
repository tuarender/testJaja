package com.topgun.resume;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.topgun.shris.masterdata.Country;
import com.topgun.shris.masterdata.Language;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.DBManager;
import com.topgun.util.Util;
import com.topgun.util.Encoder;

public class ResumeManager 
{
	public Resume get(int idJsk, int idResume)
	{
		Resume result=null;
		String sql=	"SELECT " +
					"	TRUNC((MONTHS_BETWEEN(SYSDATE, TIMESTAMP))/12)  AS YEAR_DIFF , "
					+ " TRUNC( MOD(MONTHS_BETWEEN(SYSDATE, TIMESTAMP),12))  AS MONTH_DIFF, "
					+ " TRUNC(SYSDATE - ADD_MONTHS( TIMESTAMP, TRUNC(MONTHS_BETWEEN(SYSDATE,TIMESTAMP)))+1) AS DAY_DIFF,"
					+ " ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, EXP_FULL_YEAR, EXP_FULL_MONTH, EXP_PT_YEAR, EXP_PT_MONTH, COMPLETE_STATUS, LOCALE , ID_PARENT , LAST_APPLY FROM INTER_RESUME " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (DELETE_STATUS<>'TRUE')";
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
				result=new Resume();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setResumeName(db.getString("RESUME_NAME"));
				result.setResumePrivacy(db.getString("RESUME_PRIVACY"));
				result.setFirstName(db.getString("FIRST_NAME"));
				result.setLastName(db.getString("LAST_NAME"));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setIdCity(db.getInt("ID_CITY"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setHomeAddress(db.getString("HOME_ADDRESS"));
				result.setPostal(db.getString("POSTAL"));
				result.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
				result.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
				result.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
				result.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
				result.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
				result.setCreateDate(db.getTimestamp("CREATE_DATE"));
				result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
				result.setExpStatus(db.getString("EXP_STATUS"));
				result.setExpCompany(db.getInt("EXP_COMPANY"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setOtherCity(db.getString("OTHER_CITY"));
				result.setSalutation(db.getString("SALUTATION"));
				result.setFirstNameThai(db.getString("FIRST_NAME_TH"));
				result.setLastNameThai(db.getString("LAST_NAME_TH"));
				result.setBirthDate(db.getDate("BIRTHDATE"));
				result.setHeight(db.getFloat("HEIGHT"));
				result.setWeight(db.getFloat("WEIGHT"));
				result.setCitizenship(db.getString("CITIZENSHIP"));
				result.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
				result.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
				result.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
				result.setExpMonth(db.getInt("EXP_MONTH"));	
				result.setExpYear(db.getInt("EXP_YEAR"));
				result.setExpFullYear(db.getInt("EXP_FULL_YEAR"));
				result.setExpFullMonth(db.getInt("EXP_FULL_MONTH"));
				result.setExpPtYear(db.getInt("EXP_PT_YEAR"));
				result.setExpPtMonth(db.getInt("EXP_PT_MONTH"));
				result.setCompleteStatus(db.getString("COMPLETE_STATUS"));
				result.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
				result.setDayPass(db.getInt("DAY_DIFF"));
				result.setMonthPass(db.getInt("MONTH_DIFF"));
				result.setYearPass(db.getInt("YEAR_DIFF"));
				result.setIdParent(db.getInt("ID_PARENT"));
				result.setLastApply(db.getTimestamp("LAST_APPLY"));
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
	
	public Resume getIncludeDeleted(int idJsk, int idResume)
	{
		Resume result=null;
		String sql=	"SELECT " +
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE , ID_PARENT FROM INTER_RESUME " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?)";
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
				result=new Resume();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setResumeName(db.getString("RESUME_NAME"));
				result.setResumePrivacy(db.getString("RESUME_PRIVACY"));
				result.setFirstName(db.getString("FIRST_NAME"));
				result.setLastName(db.getString("LAST_NAME"));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setIdCity(db.getInt("ID_CITY"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setHomeAddress(db.getString("HOME_ADDRESS"));
				result.setPostal(db.getString("POSTAL"));
				result.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
				result.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
				result.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
				result.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
				result.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
				result.setCreateDate(db.getTimestamp("CREATE_DATE"));
				result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
				result.setExpStatus(db.getString("EXP_STATUS"));
				result.setExpCompany(db.getInt("EXP_COMPANY"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setOtherCity(db.getString("OTHER_CITY"));
				result.setSalutation(db.getString("SALUTATION"));
				result.setFirstNameThai(db.getString("FIRST_NAME_TH"));
				result.setLastNameThai(db.getString("LAST_NAME_TH"));
				result.setBirthDate(db.getDate("BIRTHDATE"));
				result.setHeight(db.getFloat("HEIGHT"));
				result.setWeight(db.getFloat("WEIGHT"));
				result.setCitizenship(db.getString("CITIZENSHIP"));
				result.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
				result.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
				result.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
				result.setExpMonth(db.getInt("EXP_MONTH"));	
				result.setExpYear(db.getInt("EXP_YEAR"));
				result.setCompleteStatus(db.getString("COMPLETE_STATUS"));
				result.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
				result.setIdParent(db.getInt("ID_PARENT"));
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
	
	public List<Resume> getAll(int idJsk)
	{
		List<Resume> result = new ArrayList<Resume>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE, ID_PARENT , LAST_APPLY FROM INTER_RESUME " +
					"WHERE " +
					"	(ID_JSK=?) AND (DELETE_STATUS<>'TRUE') " +
					"ORDER BY ID_RESUME";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);			
		    db.executeQuery();
		    while(db.next())
		    {
		    	Resume res=new Resume();
		    	res.setIdJsk(db.getInt("ID_JSK"));
		    	res.setIdResume(db.getInt("ID_RESUME"));
		    	res.setResumeName(db.getString("RESUME_NAME"));
		    	res.setResumePrivacy(db.getString("RESUME_PRIVACY"));
		    	res.setFirstName(db.getString("FIRST_NAME"));
		    	res.setLastName(db.getString("LAST_NAME"));
		    	res.setIdCountry(db.getInt("ID_COUNTRY"));
		    	res.setIdState(db.getInt("ID_STATE"));
		    	res.setIdCity(db.getInt("ID_CITY"));
		    	res.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	res.setHomeAddress(db.getString("HOME_ADDRESS"));
		    	res.setPostal(db.getString("POSTAL"));
		    	res.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	res.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	res.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
		    	res.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
		    	res.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
		    	res.setCreateDate(db.getTimestamp("CREATE_DATE"));
		    	res.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	res.setExpStatus(db.getString("EXP_STATUS"));
		    	res.setExpCompany(db.getInt("EXP_COMPANY"));
		    	res.setOtherState(db.getString("OTHER_STATE"));
		    	res.setOtherCity(db.getString("OTHER_CITY"));
		    	res.setSalutation(db.getString("SALUTATION"));
		    	res.setFirstNameThai(db.getString("FIRST_NAME_TH"));
		    	res.setLastNameThai(db.getString("LAST_NAME_TH"));
		    	res.setBirthDate(db.getDate("BIRTHDATE"));
		    	res.setHeight(db.getFloat("HEIGHT"));
		    	res.setWeight(db.getFloat("WEIGHT"));
		    	res.setCitizenship(db.getString("CITIZENSHIP"));
		    	res.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
		    	res.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
		    	res.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
		    	res.setExpMonth(db.getInt("EXP_MONTH"));	
		    	res.setExpYear(db.getInt("EXP_YEAR"));
		    	res.setCompleteStatus(db.getString("COMPLETE_STATUS"));
		    	res.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
		    	res.setIdParent(db.getInt("ID_PARENT"));
		    	res.setLastApply(db.getTimestamp("LAST_APPLY"));
		    	result.add(res);
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
	
	public List<Resume> getSubResume(int idJsk,int idResumeParent)
	{
		List<Resume> result = new ArrayList<Resume>();
		if(idResumeParent==0)
		{
			idResumeParent = -1;
		}
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE, ID_PARENT FROM INTER_RESUME " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_PARENT=?) AND (DELETE_STATUS<>'TRUE') AND ID_RESUME <> 0" +
					"ORDER BY ID_RESUME";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);	
			db.setInt(2, idResumeParent);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Resume res=new Resume();
		    	res.setIdJsk(db.getInt("ID_JSK"));
		    	res.setIdResume(db.getInt("ID_RESUME"));
		    	res.setResumeName(db.getString("RESUME_NAME"));
		    	res.setResumePrivacy(db.getString("RESUME_PRIVACY"));
		    	res.setFirstName(db.getString("FIRST_NAME"));
		    	res.setLastName(db.getString("LAST_NAME"));
		    	res.setIdCountry(db.getInt("ID_COUNTRY"));
		    	res.setIdState(db.getInt("ID_STATE"));
		    	res.setIdCity(db.getInt("ID_CITY"));
		    	res.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	res.setHomeAddress(db.getString("HOME_ADDRESS"));
		    	res.setPostal(db.getString("POSTAL"));
		    	res.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	res.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	res.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
		    	res.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
		    	res.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
		    	res.setCreateDate(db.getTimestamp("CREATE_DATE"));
		    	res.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	res.setExpStatus(db.getString("EXP_STATUS"));
		    	res.setExpCompany(db.getInt("EXP_COMPANY"));
		    	res.setOtherState(db.getString("OTHER_STATE"));
		    	res.setOtherCity(db.getString("OTHER_CITY"));
		    	res.setSalutation(db.getString("SALUTATION"));
		    	res.setFirstNameThai(db.getString("FIRST_NAME_TH"));
		    	res.setLastNameThai(db.getString("LAST_NAME_TH"));
		    	res.setBirthDate(db.getDate("BIRTHDATE"));
		    	res.setHeight(db.getFloat("HEIGHT"));
		    	res.setWeight(db.getFloat("WEIGHT"));
		    	res.setCitizenship(db.getString("CITIZENSHIP"));
		    	res.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
		    	res.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
		    	res.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
		    	res.setExpMonth(db.getInt("EXP_MONTH"));	
		    	res.setExpYear(db.getInt("EXP_YEAR"));
		    	res.setCompleteStatus(db.getString("COMPLETE_STATUS"));
		    	res.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
		    	res.setIdParent(db.getInt("ID_PARENT"));
		    	result.add(res);
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
	

	
	public int chkSubResumeLanguage(int idJsk,int idResumeParent,int idLanguage)
	{
		int result = 0 ;
		DBManager db = null ;
		if(idResumeParent==0)
		{
			idResumeParent = -1;
		}
		String sql=	" SELECT * FROM INTER_RESUME " +
					" WHERE " +
					" (ID_JSK=?) AND (ID_PARENT=?) AND (ID_LANGUAGE = ?) AND (DELETE_STATUS<>'TRUE') ";
					
		try{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResumeParent);
			db.setInt(3, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result = db.getInt("ID_RESUME") ;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			db.close();
		}
		return result ;
	}
	
	public List<Resume> getAll(int idJsk,boolean withSAF, boolean isComplete)
	{
		List<Resume> result = new ArrayList<Resume>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	TRUNC((MONTHS_BETWEEN(SYSDATE, TIMESTAMP))/12)  AS YEAR_DIFF , "+ 
					" 	TRUNC( MOD(MONTHS_BETWEEN(SYSDATE, TIMESTAMP),12))  AS MONTH_DIFF, "+ 
					" 	TRUNC(SYSDATE - ADD_MONTHS( TIMESTAMP, TRUNC(MONTHS_BETWEEN(SYSDATE,TIMESTAMP)))+1) AS DAY_DIFF,"+
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE, ID_PARENT , LAST_APPLY "+ 
					"	FROM INTER_RESUME " +
					"	WHERE " +
					"	(ID_JSK=?) AND (DELETE_STATUS<>'TRUE') ";
		if(!withSAF)
		{
			sql+=" AND (ID_RESUME>0) ";
		}
		
		if(isComplete)
		{
			sql+=" AND (COMPLETE_STATUS='TRUE') ";
		}
		
		sql+=" ORDER BY ID_RESUME";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);			
		    db.executeQuery();
		    while(db.next())
		    {
		    	Resume res=new Resume();
		    	res.setIdJsk(db.getInt("ID_JSK"));
		    	res.setIdResume(db.getInt("ID_RESUME"));
		    	res.setResumeName(db.getString("RESUME_NAME"));
		    	res.setResumePrivacy(db.getString("RESUME_PRIVACY"));
		    	res.setFirstName(db.getString("FIRST_NAME"));
		    	res.setLastName(db.getString("LAST_NAME"));
		    	res.setIdCountry(db.getInt("ID_COUNTRY"));
		    	res.setIdState(db.getInt("ID_STATE"));
		    	res.setIdCity(db.getInt("ID_CITY"));
		    	res.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	res.setHomeAddress(db.getString("HOME_ADDRESS"));
		    	res.setPostal(db.getString("POSTAL"));
		    	res.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	res.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	res.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
		    	res.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
		    	res.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
		    	res.setCreateDate(db.getTimestamp("CREATE_DATE"));
		    	res.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	res.setExpStatus(db.getString("EXP_STATUS"));
		    	res.setExpCompany(db.getInt("EXP_COMPANY"));
		    	res.setOtherState(db.getString("OTHER_STATE"));
		    	res.setOtherCity(db.getString("OTHER_CITY"));
		    	res.setSalutation(db.getString("SALUTATION"));
		    	res.setFirstNameThai(db.getString("FIRST_NAME_TH"));
		    	res.setLastNameThai(db.getString("LAST_NAME_TH"));
		    	res.setBirthDate(db.getDate("BIRTHDATE"));
		    	res.setHeight(db.getFloat("HEIGHT"));
		    	res.setWeight(db.getFloat("WEIGHT"));
		    	res.setCitizenship(db.getString("CITIZENSHIP"));
		    	res.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
		    	res.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
		    	res.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
		    	res.setExpMonth(db.getInt("EXP_MONTH"));	
		    	res.setExpYear(db.getInt("EXP_YEAR"));
		    	res.setCompleteStatus(db.getString("COMPLETE_STATUS"));
		    	res.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
		    	res.setDayPass(db.getInt("DAY_DIFF"));
		    	res.setMonthPass(db.getInt("MONTH_DIFF"));
		    	res.setYearPass(db.getInt("YEAR_DIFF"));
		    	res.setIdParent(db.getInt("ID_PARENT"));
		    	res.setLastApply(db.getTimestamp("LAST_APPLY"));
		    	result.add(res);
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
	
	public List<Resume> getAll(int idJsk,boolean withSAF, boolean isComplete,boolean isParent)
	{
		List<Resume> result = new ArrayList<Resume>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	TRUNC((MONTHS_BETWEEN(SYSDATE, TIMESTAMP))/12)  AS YEAR_DIFF , "+ 
					" 	TRUNC( MOD(MONTHS_BETWEEN(SYSDATE, TIMESTAMP),12))  AS MONTH_DIFF, "+ 
					" 	TRUNC(SYSDATE - ADD_MONTHS( TIMESTAMP, TRUNC(MONTHS_BETWEEN(SYSDATE,TIMESTAMP)))+1) AS DAY_DIFF,"+
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE, ID_PARENT , LAST_APPLY "+ 
					"	FROM INTER_RESUME " +
					"	WHERE " +
					"	(ID_JSK=?) AND (DELETE_STATUS<>'TRUE') ";
		if(!withSAF)
		{
			sql+=" AND (ID_RESUME>0) ";
		}
		
		if(isComplete)
		{
			sql+=" AND (COMPLETE_STATUS='TRUE') ";
		}
		
		if(isParent)
		{
			sql+=" AND (ID_PARENT = 0 OR ID_PARENT IS NULL) ";
		}
		
		sql+=" ORDER BY ID_RESUME";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);			
		    db.executeQuery();
		    while(db.next())
		    {
		    	Resume res=new Resume();
		    	res.setIdJsk(db.getInt("ID_JSK"));
		    	res.setIdResume(db.getInt("ID_RESUME"));
		    	res.setResumeName(db.getString("RESUME_NAME"));
		    	res.setResumePrivacy(db.getString("RESUME_PRIVACY"));
		    	res.setFirstName(db.getString("FIRST_NAME"));
		    	res.setLastName(db.getString("LAST_NAME"));
		    	res.setIdCountry(db.getInt("ID_COUNTRY"));
		    	res.setIdState(db.getInt("ID_STATE"));
		    	res.setIdCity(db.getInt("ID_CITY"));
		    	res.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	res.setHomeAddress(db.getString("HOME_ADDRESS"));
		    	res.setPostal(db.getString("POSTAL"));
		    	res.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	res.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	res.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
		    	res.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
		    	res.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
		    	res.setCreateDate(db.getTimestamp("CREATE_DATE"));
		    	res.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	res.setExpStatus(db.getString("EXP_STATUS"));
		    	res.setExpCompany(db.getInt("EXP_COMPANY"));
		    	res.setOtherState(db.getString("OTHER_STATE"));
		    	res.setOtherCity(db.getString("OTHER_CITY"));
		    	res.setSalutation(db.getString("SALUTATION"));
		    	res.setFirstNameThai(db.getString("FIRST_NAME_TH"));
		    	res.setLastNameThai(db.getString("LAST_NAME_TH"));
		    	res.setBirthDate(db.getDate("BIRTHDATE"));
		    	res.setHeight(db.getFloat("HEIGHT"));
		    	res.setWeight(db.getFloat("WEIGHT"));
		    	res.setCitizenship(db.getString("CITIZENSHIP"));
		    	res.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
		    	res.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
		    	res.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
		    	res.setExpMonth(db.getInt("EXP_MONTH"));	
		    	res.setExpYear(db.getInt("EXP_YEAR"));
		    	res.setCompleteStatus(db.getString("COMPLETE_STATUS"));
		    	res.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
		    	res.setDayPass(db.getInt("DAY_DIFF"));
		    	res.setMonthPass(db.getInt("MONTH_DIFF"));
		    	res.setYearPass(db.getInt("YEAR_DIFF"));
		    	res.setIdParent(db.getInt("ID_PARENT"));
		    	res.setLastApply(db.getTimestamp("LAST_APPLY"));
		    	result.add(res);
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
	
	public List<Resume> getAllCanApply(int idJsk,boolean withSAF)
	{
		List<Resume> result = new ArrayList<Resume>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	TRUNC((MONTHS_BETWEEN(SYSDATE, TIMESTAMP))/12)  AS YEAR_DIFF , "+ 
					" 	TRUNC( MOD(MONTHS_BETWEEN(SYSDATE, TIMESTAMP),12))  AS MONTH_DIFF, "+ 
					" 	TRUNC(SYSDATE - ADD_MONTHS( TIMESTAMP, TRUNC(MONTHS_BETWEEN(SYSDATE,TIMESTAMP)))+1) AS DAY_DIFF,"+
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE, ID_PARENT , LAST_APPLY "+ 
					"	FROM INTER_RESUME " +
					"	WHERE " +
					"	(ID_JSK=?) AND (DELETE_STATUS<>'TRUE') ";
		if(!withSAF)
		{
			sql+=" AND (ID_RESUME>0) ";
		}
		
		
		sql+=" ORDER BY ID_RESUME";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);			
		    db.executeQuery();
		    while(db.next())
		    {
		    	Resume res=new Resume();
		    	res.setIdJsk(db.getInt("ID_JSK"));
		    	res.setIdResume(db.getInt("ID_RESUME"));
		    	res.setResumeName(db.getString("RESUME_NAME"));
		    	res.setResumePrivacy(db.getString("RESUME_PRIVACY"));
		    	res.setFirstName(db.getString("FIRST_NAME"));
		    	res.setLastName(db.getString("LAST_NAME"));
		    	res.setIdCountry(db.getInt("ID_COUNTRY"));
		    	res.setIdState(db.getInt("ID_STATE"));
		    	res.setIdCity(db.getInt("ID_CITY"));
		    	res.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	res.setHomeAddress(db.getString("HOME_ADDRESS"));
		    	res.setPostal(db.getString("POSTAL"));
		    	res.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	res.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	res.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
		    	res.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
		    	res.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
		    	res.setCreateDate(db.getTimestamp("CREATE_DATE"));
		    	res.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	res.setExpStatus(db.getString("EXP_STATUS"));
		    	res.setExpCompany(db.getInt("EXP_COMPANY"));
		    	res.setOtherState(db.getString("OTHER_STATE"));
		    	res.setOtherCity(db.getString("OTHER_CITY"));
		    	res.setSalutation(db.getString("SALUTATION"));
		    	res.setFirstNameThai(db.getString("FIRST_NAME_TH"));
		    	res.setLastNameThai(db.getString("LAST_NAME_TH"));
		    	res.setBirthDate(db.getDate("BIRTHDATE"));
		    	res.setHeight(db.getFloat("HEIGHT"));
		    	res.setWeight(db.getFloat("WEIGHT"));
		    	res.setCitizenship(db.getString("CITIZENSHIP"));
		    	res.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
		    	res.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
		    	res.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
		    	res.setExpMonth(db.getInt("EXP_MONTH"));	
		    	res.setExpYear(db.getInt("EXP_YEAR"));
		    	res.setCompleteStatus(db.getString("COMPLETE_STATUS"));
		    	res.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
		    	res.setDayPass(db.getInt("DAY_DIFF"));
		    	res.setMonthPass(db.getInt("MONTH_DIFF"));
		    	res.setYearPass(db.getInt("YEAR_DIFF"));
		    	res.setIdParent(db.getInt("ID_PARENT"));
		    	res.setLastApply(db.getTimestamp("LAST_APPLY"));
		    	if(new ResumeStatusManager().getRegisterStatus(res).equals("TRUE"))
		    	{
		    		result.add(res);
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
	
	
	public String getLocale(int idLanguage, int idCountry)
	{
		String result="";
		Country country=MasterDataManager.getCountry(idCountry, 11);
		Language language=MasterDataManager.getLanguage(idLanguage);
		if(idCountry!=216 && idCountry!=231)
		{
			result=language.getAbbreviation()+"_TH";
		}
		else
		{
			if(country!=null && language!=null)
			{
				result=language.getAbbreviation()+"_"+country.getAbbreviation();
			}
		}
		return result;
	}
	
	public String getResumeLocale(int resumeLanguage,int idLanguage, int idCountry)
	{
		String result="";
		Country country=MasterDataManager.getCountry(idCountry, idLanguage);
		String language=MasterDataManager.getLanguageName(resumeLanguage,idLanguage);
		if(country!=null && !language.equals(""))
		{
			result=country.getCountryName()+"-"+language;
		}
		return result;
	}
	
	public String getResumeLanguage(int resumeLanguage,int idLanguage)
	{
		String result=MasterDataManager.getLanguageName(resumeLanguage,idLanguage);
		return result;
	}
	
	public int add(Resume resume)
	{
		int result=0;
		if(resume!=null && resume.getIdJsk()>0 && resume.getIdResume()>=0 && resume.getIdLanguage()>0)
		{
			String sql=	"INSERT INTO INTER_RESUME(ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, " +
						"ID_COUNTRY, ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, " +
						"PRIMARY_PHONE_TYPE, SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, " +
						"CREATE_DATE, TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, " +
						"FIRST_NAME_TH, LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, " +
						"APPLY_ID_COUNTRY, TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, EXP_FULL_YEAR, EXP_FULL_MONTH, EXP_PT_YEAR, EXP_PT_MONTH, COMPLETE_STATUS, LOCALE,ID_PARENT,LAST_APPLY) " +
						"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setInt(1, resume.getIdResume());
				db.setInt(2, resume.getIdJsk());
				db.setString(3, resume.getResumeName());
				db.setString(4, resume.getResumePrivacy());
				db.setString(5, resume.getFirstName());
				db.setString(6, resume.getLastName());
				db.setInt(7, resume.getIdCountry());
				db.setInt(8, resume.getIdState());
				db.setInt(9, resume.getIdCity());
				db.setInt(10, resume.getIdLanguage());
				db.setString(11, resume.getHomeAddress());
				db.setString(12, resume.getPostal());
				db.setString(13, resume.getPrimaryPhone());
				db.setString(14, resume.getPrimaryPhoneType());
				db.setString(15, resume.getSecondaryPhone());
				db.setString(16, resume.getSecondaryPhoneType());
				db.setString(17, resume.getSecondaryEmail());
				db.setString(18, resume.getExpStatus());
				db.setInt(19, resume.getExpCompany());
				db.setString(20, resume.getOtherState());
				db.setString(21, resume.getOtherCity());
				db.setString(22, resume.getSalutation());
				db.setString(23, resume.getFirstNameThai());
				db.setString(24, resume.getLastNameThai());
				db.setDate(25, resume.getBirthDate());
				db.setFloat(26, resume.getHeight());
				db.setFloat(27, resume.getWeight());
				db.setString(28, resume.getCitizenship());
				db.setString(29, resume.getOwnCarStatus());
				db.setInt(30, resume.getApplyIdCountry());
				db.setInt(31, resume.getTemplateIdCountry());
				db.setInt(32, resume.getExpMonth());
				db.setInt(33, resume.getExpYear());
				db.setInt(34, resume.getExpFullYear());
				db.setInt(35, resume.getExpFullMonth());
				db.setInt(36, resume.getExpPtYear());
				db.setInt(37, resume.getExpPtMonth());
				db.setString(38, resume.getCompleteStatus());
				db.setString(39, resume.getLocale());
				db.setInt(40, resume.getIdParent());
				db.setTimestamp(41, resume.getLastApply());
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
		}
		return result;
	}
	
	public int update(Resume resume)
	{
		int result=0;
		if(resume!=null)
		{			
			String sql=	"UPDATE " +
						"	INTER_RESUME " +
						"SET " +
						"	RESUME_NAME=?," +
						"	RESUME_PRIVACY=?," +
						"	FIRST_NAME=?," +
						"	LAST_NAME=?," +
						"	ID_COUNTRY=?," +
						"	ID_STATE=?," +
						"	ID_CITY=?," +
						"	ID_LANGUAGE=?," +
						"	HOME_ADDRESS=?," +
						"	POSTAL=?," +
						"	PRIMARY_PHONE=?," +
						"	PRIMARY_PHONE_TYPE=?," +
						"	SECONDARY_PHONE=?," +
						"	SECONDARY_PHONE_TYPE=?," +
						"	SECONDARY_EMAIL=?," +
						"	TIMESTAMP=SYSDATE," +							
						"	EXP_STATUS=?," +
						"	EXP_COMPANY=?," +
						"	OTHER_STATE=?," +
						"	OTHER_CITY=?," +
						"	SALUTATION=?," +
						"	FIRST_NAME_TH=?," +
						"	LAST_NAME_TH=?," +
						"	BIRTHDATE=?," +
						"	HEIGHT=?," +
						"	WEIGHT=?," +
						"	CITIZENSHIP=?," +
						"	OWNCAR_STATUS=?," +
						"	APPLY_ID_COUNTRY=?," +
						"	TEMPLATE_ID_COUNTRY=?, " +
						"	EXP_MONTH=?, " +
						"	EXP_YEAR=?," +
						"	EXP_FULL_YEAR=?," +
						"	EXP_FULL_MONTH=?," +
						"	EXP_PT_YEAR=?," +
						"	EXP_PT_MONTH=?," +
						"	COMPLETE_STATUS=?, " +
						"	LOCALE=?, " +
						"	ID_PARENT=?, "+
						"	LAST_APPLY=? "+
						"WHERE " +
						"	(ID_RESUME=?) AND (ID_JSK=?)";
			
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setString(1, resume.getResumeName());
				db.setString(2, resume.getResumePrivacy());
				db.setString(3, resume.getFirstName());
				db.setString(4, resume.getLastName());
				db.setInt(5, resume.getIdCountry());
				db.setInt(6, resume.getIdState());
				db.setInt(7, resume.getIdCity());
				db.setInt(8, resume.getIdLanguage());
				db.setString(9, resume.getHomeAddress());
				db.setString(10, resume.getPostal());
				db.setString(11, resume.getPrimaryPhone());
				db.setString(12, resume.getPrimaryPhoneType());
				db.setString(13, resume.getSecondaryPhone());
				db.setString(14, resume.getSecondaryPhoneType());
				db.setString(15, resume.getSecondaryEmail());
				db.setString(16, resume.getExpStatus());
				db.setInt(17, resume.getExpCompany());		
				db.setString(18, resume.getOtherState());
				db.setString(19, resume.getOtherCity());
				db.setString(20, resume.getSalutation());
				db.setString(21, resume.getFirstNameThai());
				db.setString(22, resume.getLastNameThai());
				db.setDate(23, resume.getBirthDate());
				db.setFloat(24, resume.getHeight());
				db.setFloat(25, resume.getWeight());
				db.setString(26, resume.getCitizenship());
				db.setString(27, resume.getOwnCarStatus());				
				db.setInt(28, resume.getApplyIdCountry());				
				db.setInt(29, resume.getTemplateIdCountry());		
				db.setInt(30,resume.getExpMonth());
				db.setInt(31, resume.getExpYear());
				db.setInt(32, resume.getExpFullYear());
				db.setInt(33, resume.getExpFullMonth());
				db.setInt(34, resume.getExpPtYear());
				db.setInt(35, resume.getExpPtMonth());
				db.setString(36, resume.getCompleteStatus());
				db.setString(37, resume.getLocale());
				db.setInt(38, resume.getIdParent());
				db.setTimestamp(39, resume.getLastApply());
				db.setInt(40, resume.getIdResume());
				db.setInt(41, resume.getIdJsk());	
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
		}		
		return result;
	}
	
	public int updateStatus(int idJsk, int idResume, String status)
	{
		int result=0;
		if(Util.getStr(status).equals("")) return 0;
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("UPDATE INTER_RESUME SET COMPLETE_STATUS=? WHERE ID_JSK=? AND ID_RESUME=?");
			db.setString(1, status.toUpperCase());
			db.setInt(2, idJsk);
			db.setInt(3, idResume);
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
	
	public Resume getLatestCompleted(int idJsk)
	{
		Resume result=null;
		String sql=	"SELECT " +
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE FROM INTER_RESUME " +
					"WHERE " +
					"	(ID_JSK=?) AND (DELETE_STATUS<>'TRUE') AND (ID_RESUME<>0)" +
					"ORDER BY " +
					"	TIMESTAMP DESC";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=new Resume();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdResume(db.getInt("ID_RESUME"));
				result.setResumeName(db.getString("RESUME_NAME"));
				result.setResumePrivacy(db.getString("RESUME_PRIVACY"));
				result.setFirstName(db.getString("FIRST_NAME"));
				result.setLastName(db.getString("LAST_NAME"));
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setIdCity(db.getInt("ID_CITY"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setHomeAddress(db.getString("HOME_ADDRESS"));
				result.setPostal(db.getString("POSTAL"));
				result.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
				result.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
				result.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
				result.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
				result.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
				result.setCreateDate(db.getTimestamp("CREATE_DATE"));
				result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
				result.setExpStatus(db.getString("EXP_STATUS"));
				result.setExpCompany(db.getInt("EXP_COMPANY"));
				result.setOtherState(db.getString("OTHER_STATE"));
				result.setOtherCity(db.getString("OTHER_CITY"));
				result.setSalutation(db.getString("SALUTATION"));
				result.setFirstNameThai(db.getString("FIRST_NAME_TH"));
				result.setLastNameThai(db.getString("LAST_NAME_TH"));
				result.setBirthDate(db.getDate("BIRTHDATE"));
				result.setHeight(db.getFloat("HEIGHT"));
				result.setWeight(db.getFloat("WEIGHT"));
				result.setCitizenship(db.getString("CITIZENSHIP"));
				result.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
				result.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
				result.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
				result.setExpMonth(db.getInt("EXP_MONTH"));	
				result.setExpYear(db.getInt("EXP_YEAR"));
				result.setCompleteStatus(db.getString("COMPLETE_STATUS"));
				result.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
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
	
	public int delete(Resume resume)
	{
		int result=0;
		String sql="UPDATE INTER_RESUME SET DELETE_STATUS='TRUE' ,TIMESTAMP=SYSDATE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
	
	public int delete(int idJsk,int idResume)
	{
		int result=0;
		String sql="UPDATE INTER_RESUME SET DELETE_STATUS='TRUE' ,TIMESTAMP=SYSDATE WHERE (ID_JSK=?) AND (ID_RESUME=?)";
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

	public int getNextIdResume(int idJsk)
	{
		int result=0;
		String SQL="SELECT MAX(ID_RESUME) AS MAXID FROM INTER_RESUME WHERE ID_JSK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
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
	
	public int copyResume(int idJsk,int idResume,String resumeName,int applyIdCountryDst,int idLanguageDst,int templateIdCountryDst,String privacyDst)
	{
		int idResumeDst=-1;
		
		idResumeDst=getNextIdResume(idJsk);
		Resume rs=get(idJsk,idResume);
		rs.setIdResume(idResumeDst);
		rs.setResumeName(resumeName);
		rs.setTemplateIdCountry(templateIdCountryDst);
		rs.setResumePrivacy(privacyDst);
		rs.setApplyIdCountry(applyIdCountryDst);
		if(rs.getIdLanguage()!=idLanguageDst)
		{
			rs.setHomeAddress("");
			rs.setOtherState("");
			rs.setOtherCity("");
		}
		rs.setIdLanguage(idLanguageDst);
		if(add(rs)==1)
		{
			copyActivity(idJsk, idResume, idResumeDst, idLanguageDst);
			copyAdditional(idJsk, idResume, idResumeDst, idLanguageDst);
			copyAdditionalInfo(idJsk, idResume, idResumeDst, idLanguageDst);
			copyAward(idJsk, idResume, idResumeDst, idLanguageDst);
			copyCareerObjective(idJsk, idResume, idResumeDst, idLanguageDst);
			copyCertificate(idJsk, idResume, idResumeDst, idLanguageDst);
			copyEducation(idJsk, idResume, idResumeDst, idLanguageDst);
			copyEthnicity(idJsk, idResume, idResumeDst, idLanguageDst);
			copyReference(idJsk, idResume, idResumeDst, idLanguageDst);
			copySkillComputer(idJsk, idResume, idResumeDst, idLanguageDst);
			copySkillLanguage(idJsk, idResume, idResumeDst, idLanguageDst);
			copySkillOther(idJsk, idResume, idResumeDst, idLanguageDst);
			copySkillLanguageScore(idJsk, idResume, idResumeDst, idLanguageDst);
			copyStrength(idJsk, idResume, idResumeDst, idLanguageDst);
			copyFamily(idJsk, idResume, idResumeDst, idLanguageDst);
			copyTargetjob(idJsk, idResume, idResumeDst, idLanguageDst);
			copyTraining(idJsk, idResume, idResumeDst, idLanguageDst);
			copyWorkExperience(idJsk, idResume, idResumeDst, idLanguageDst);
			copyMiscellaneous(idJsk, idResume, idResumeDst, idLanguageDst);
			copyAptitude(idJsk, idResume, idResumeDst, idLanguageDst);
			copySocial(idJsk, idResume, idResumeDst, idLanguageDst);
		}
		return idResumeDst;
	}

	
	public int copyPersonalData(Resume resumeSrc, Resume resumeDst)
	{
		int result=-1;		
		resumeSrc.setIdResume(resumeDst.getIdResume());
		resumeSrc.setResumeName(resumeDst.getResumeName());
		resumeSrc.setResumePrivacy(resumeDst.getResumePrivacy());
		if(resumeSrc.getIdLanguage()!=resumeDst.getIdLanguage())
		{
			resumeSrc.setHomeAddress("");
			resumeSrc.setOtherState("");
			resumeSrc.setOtherCity("");
		}
		result=update(resumeSrc);
		return result;
	}
	
	
	public int copyActivity(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Activity> activities =new ActivityManager().getAll(idJsk, idResume);
		if(activities.size()> 0)
		{
			new ActivityManager().deleteAll(idJsk, idResumeDst);
			for (int i = 0; i < activities.size(); i++)
			{
				activities.get(i).setIdResume(idResumeDst);
				result+=new ActivityManager().add(activities.get(i));
			}
		}
		return result;
	}
	
	public int copyAdditional(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		Additional adt = new AdditionalManager().get(idJsk,idResume);
		if(adt!=null)
		{
			adt.setIdResume(idResumeDst);
			new AdditionalManager().delete(adt);
			result=new AdditionalManager().add(adt);
		}
		return result;
	}
	
	public int copyAdditionalInfo(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		AdditionalInfo adf = new AdditionalInfoManager().get(idJsk, idResume);
		if(adf!=null)
		{
			adf.setIdResume(idResumeDst);
			new AdditionalInfoManager().delete(adf);
			result=new AdditionalInfoManager().add(adf);					
		}
		return result;
	}
		
	public int copyAward(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Award> awards =new AwardManager().getAll(idJsk, idResume);
		if(awards.size() > 0)
		{
			new AwardManager().deleteAll(idJsk, idResumeDst);
			for (int i=0; i<awards.size(); i++)
			{
				awards.get(i).setIdResume(idResumeDst);
				result+=new AwardManager().add(awards.get(i));
			}
		}
		return result;
	}
	
	public int copyCareerObjective(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<CareerObjective> careers=new CareerObjectiveManager().getAll(idJsk, idResume);
		if(careers.size()>0)
		{
			new CareerObjectiveManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<careers.size(); i++)
			{
				careers.get(i).setIdResume(idResumeDst);
				result+=new CareerObjectiveManager().add(careers.get(i));
			}
		}
		return result;
	}
	
	public int copyCertificate(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		
		List<Certificate> cers = new CertificateManager().getAll(idJsk, idResume);
		
		if(cers.size() > 0)
		{
			new CertificateManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<cers.size(); i++)
			{	
				cers.get(i).setIdResume(idResumeDst);
				result+=new CertificateManager().add(cers.get(i));
			}
		}
		return result;
	}
	
	
	
	
	
	public int copyEducation(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		Resume rs=this.get(idJsk, idResume);
		EducationManager mgr=new EducationManager();
		List<Education> edus=mgr.getAll(idJsk, idResume);
		if(edus.size()>0 && rs!=null)
		{
			new EducationManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<edus.size(); i++)
			{
				String hashRefer=Util.getStr(edus.get(i).getHashRefer());
				if(hashRefer.equals("")) //update parent hash refer
				{
					hashRefer=Util.getHashString();
					edus.get(i).setHashRefer(hashRefer);
					mgr.update(edus.get(i));
				}
				int idEducation = edus.get(i).getId() ;
				edus.get(i).setIdResume(idResumeDst);
				edus.get(i).setIdRefer(idEducation);
				edus.get(i).setHashRefer(hashRefer);
				if (rs.getIdLanguage()!= idLanguageDst)
				{
					edus.get(i).setAward("");
					edus.get(i).setOtherFaculty("");
					edus.get(i).setOtherMajor("");
					edus.get(i).setOtherMinor("");
					edus.get(i).setOtherSchool("");
				}				
				result+=new EducationManager().add(edus.get(i));
			}
		}
		return result;
	}
	
	public int copyEthnicity(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		Ethnicity eth = new EthnicityManager().get(idJsk,idResume);
		if(eth!=null)
		{
			eth.setIdResume(idResumeDst);
			new EthnicityManager().delete(eth);
			result=new EthnicityManager().add(eth);
		}
		return result;
	}
	
	public int copyReference(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		
		List<Reference> refs = new ReferenceManager().getAll(idJsk, idResume);
		if(refs.size()>0)
		{
			new ReferenceManager().deleteAll(idJsk, idResumeDst);	
			/*for(int i=0; i<refs.size(); i++)
			{
				refs.get(i).setIdResume(idResumeDst);
				new ReferenceManager().delete(refs.get(i));
				result+=new ReferenceManager().add(refs.get(i));				
			}*/
		}
		return result;
	}
	
	public int copySkillComputer(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<SkillComputer> skills = new SkillComputerManager().getAll(idJsk,idResume);
		if(skills.size()>0)
		{
			new SkillComputerManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<skills.size(); i++)
			{
				skills.get(i).setIdResume(idResumeDst);
				result+=new SkillComputerManager().add(skills.get(i));
			}
		}		
		return result;
	}
	
	public int copySkillLanguage(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<SkillLanguage> skills = new SkillLanguageManager().getAll(idJsk, idResume);
		if(skills.size()>0)
		{
			new SkillLanguageManager().deleteAll(idJsk,idResumeDst);
			for(int i=0; i<skills.size(); i++)
			{
				skills.get(i).setIdResume(idResumeDst);
				result+=new SkillLanguageManager().add(skills.get(i));
			}
		}		
		return result;
	}
	
	public int copySkillOther(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<SkillOther> skills = new SkillOtherManager().getAll(idJsk, idResume);
		if(skills.size()>0)
		{
			new SkillOtherManager().deleteAll(idJsk,idResumeDst);
			for(int i=0; i<skills.size(); i++)
			{
				skills.get(i).setIdResume(idResumeDst);
				result+=new SkillOtherManager().add(skills.get(i));
			}
		}
		
		return result;
	}

	public int copySkillLanguageScore(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<SkillLanguageScore> skills=new SkillLanguageScoreManager().getAll(idJsk, idResume);
		if(skills.size()>0)
		{
			new SkillLanguageScoreManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<skills.size(); i++)
			{
				skills.get(i).setIdResume(idResumeDst);
				result+=new SkillLanguageScoreManager().add(skills.get(i));
			}
		}
		return result;
	}

	
	public int copyStrength(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Strength> strengths=new StrengthManager().getAll(idJsk, idResume);
		Resume resume = new ResumeManager().get(idJsk, idResume);
		if(strengths.size()>0)
		{
			new StrengthManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<strengths.size(); i++)
			{
				strengths.get(i).setIdResume(idResumeDst);
				if(resume.getIdLanguage()!=idLanguageDst)
				{
					strengths.get(i).setStrengthReason("");
				}
				result+=new StrengthManager().add(strengths.get(i));
			}
		}
		return result;
	}
	
	public int copyFamily(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Family> families = new FamilyManager().getAll(idJsk,idResume);
		if(families.size()>0)
		{
			new FamilyManager().deleteAll(idJsk,idResumeDst);
			/*for(int i=0; i<families.size(); i++)
			{
				families.get(i).setIdResume(idResumeDst);
				result+=new FamilyManager().add(families.get(i));
			}*/
		}
		return result;
	}
	
	public int copyTargetjob(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		result+=copyTargetJobField(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyTargetJobIndus(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyTargetJobExtensionTable(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyTargetJobLocation(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyJobType(idJsk, idResume, idResumeDst, idLanguageDst);
		return result;
	}
	
	public int copyTargetJobField(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<TargetJobField> targetJobs=new TargetJobFieldManager().getAll(idJsk, idResume);
		if(targetJobs.size()>0)
		{
			new TargetJobFieldManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<targetJobs.size(); i++)
			{
				targetJobs.get(i).setIdResume(idResumeDst);
				result+=new TargetJobFieldManager().add(targetJobs.get(i));
			}
		}
		return result;
	}
	
	public int copyTargetJobIndus(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Industry> indus=new IndustryManager().getAll(idJsk, idResume);
		if(indus.size()>0)
		{
			new IndustryManager().deleteAll(idJsk, idResumeDst);
			for (int i = 0; i < indus.size(); i++) 
			{
				indus.get(i).setIdResume(idResumeDst);
				result+=new IndustryManager().add(indus.get(i));
			}
		}
		return result;
	}
	
	public int copyTargetJobExtensionTable(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		TargetJob jobExtension=new TargetJobManager().get(idJsk, idResume);
		if(jobExtension!=null)
		{
			new TargetJobManager().delete(idJsk, idResumeDst);
			jobExtension.setIdResume(idResumeDst);
			new TargetJobManager().add(jobExtension);
		}
		return result;
	}
	
	public int copyTargetJobLocation(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Location> locations=new LocationManager().getAll(idJsk, idResume);
		if(locations.size()>0)
		{
			new LocationManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<locations.size(); i++)
			{
				locations.get(i).setIdResume(idResumeDst);
				result+=new LocationManager().add(locations.get(i));
			}
		}
		
		List<Location> industrials = new LocationManager().getAllIndustrialLocation(idJsk, idResume);
		if(industrials!=null)
		{
			new LocationManager().deleteAllIndustrial(idJsk,idResumeDst);
			for(int i = 0 ; i<industrials.size(); i++)
			{
				industrials.get(i).setIdResume(idResumeDst);
				result+=new LocationManager().addIndustrial(industrials.get(i));
			}
		}
		
		return result;
	}
	
	
	public int copyJobType(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<JobType> jobtypes=new JobTypeManager().getAll(idJsk, idResume);
		if(jobtypes.size()>0)
		{
			new JobTypeManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<jobtypes.size(); i++)
			{
				jobtypes.get(i).setIdResume(idResumeDst);
				result+=new JobTypeManager().add(jobtypes.get(i));
			}
		}
		return result;
	}
	
	public int copyTraining(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Training> trains = new TrainingManager().getAll(idJsk, idResume);
		if(trains.size()>0)
		{
			new TrainingManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<trains.size(); i++)
			{
				trains.get(i).setIdResume(idResumeDst);
				result+=new TrainingManager().add(trains.get(i));
			}
		}		
		return result;
	}
	
	public int copyWorkExperience(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		result+=copyWorkExp(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyExperienceIndustry(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyExperienceSummary(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=setExperienceSummaryInResume(idJsk, idResume, idResumeDst, idLanguageDst);
		return result;
	}
	
	public int copyWorkExp(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<WorkExperience> workExps=new WorkExperienceManager().getAll(idJsk, idResume);
		Resume resume = get(idJsk, idResume);
		if(workExps.size()>0)
		{
			new WorkExperienceManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<workExps.size(); i++)
			{
				String hashRefer=Util.getStr(workExps.get(i).getHashRefer());
				if(hashRefer.equals("")) //update parent hash refer
				{
					hashRefer=Util.getHashString();
					workExps.get(i).setHashRefer(hashRefer);
					new WorkExperienceManager().update(workExps.get(i));
				}
				int idWorkExp = workExps.get(i).getId();
				workExps.get(i).setIdResume(idResumeDst);
				workExps.get(i).setIdRefer(idWorkExp);
				workExps.get(i).setHashRefer(hashRefer);
				if(resume.getIdLanguage()!=idLanguageDst)
				{
					if(workExps.get(i).getWorkJobField()!=-1 && workExps.get(i).getWorkSubField()!=-1)
					{
						com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workExps.get(i).getWorkJobField(),workExps.get(i).getWorkSubField(),idLanguageDst);
						if(aSubfield!=null)
						{
							workExps.get(i).setPositionLast(aSubfield.getSubfieldName());
						}
					}
					
					if(workExps.get(i).getWorkJobFieldStart()!=-1 && workExps.get(i).getWorkSubFieldStart()!=-1)
					{
						com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workExps.get(i).getWorkJobFieldStart(),workExps.get(i).getWorkSubFieldStart(),idLanguageDst);
						if(aSubfield!=null)
						{
							workExps.get(i).setPositionStart(aSubfield.getSubfieldName());
						}
					}
					workExps.get(i).setComBusiness("");
					workExps.get(i).setJobDesc("");
					workExps.get(i).setAchieve("");
					workExps.get(i).setReasonQuit("");
				}
				result+=new WorkExperienceManager().add(workExps.get(i));
			}
		}
		return result;
	}
	
	public int copyExperienceIndustry(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<ExperienceIndustry> exps=new ExperienceIndustryManager().getAllIdWork(idJsk, idResume);
		if(exps != null)
		{
			new ExperienceIndustryManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<exps.size(); i++)
			{
				exps.get(i).setIdResume(idResumeDst);
				result+=new ExperienceIndustryManager().add(exps.get(i));
			}
		}
		return result;
	}
	
	public int copyExperienceSummary(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<ExperienceSummary> expSum=new ExperienceSummaryManager().getAll(idJsk, idResume);
		if(expSum.size()>0)
		{
			new ExperienceSummaryManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<expSum.size(); i++)
			{
				expSum.get(i).setIdResume(idResumeDst);
				result+=new ExperienceSummaryManager().add(expSum.get(i));
			}
		}
		return result;
	}
	
	public int setExperienceSummaryInResume(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<WorkExperience> expSum=new WorkExperienceManager().getAllFulltimes(idJsk, idResume);
		if(expSum.size()>0)
		{
			int totalM = 0;
			int totalY = 0;
			for (int i=0;i<expSum.size();i++) 
			{
				totalM = totalM + (expSum.get(i).getExpY())*12+(expSum.get(i).getExpM());
			}
			totalY = totalM / 12;
			totalM = totalM % 12 ;
			Resume resume = get(idJsk,idResume);
			Resume resumeInf =get(idJsk, idResumeDst);
			resumeInf.setExpMonth(totalM);
			resumeInf.setExpYear(totalY);
			result+=update(resumeInf);
		}
		return result;
	}
	
	public int copyMiscellaneous(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Miscellaneous> mis=new MiscellaneousManager().getAll(idJsk, idResume);
		if(mis.size()>0)
		{
			new MiscellaneousManager().delete(idJsk, idResumeDst);
			for(int i=0; i<mis.size(); i++)
			{
				mis.get(i).setIdResume(idResumeDst);
				result+=new MiscellaneousManager().add(mis.get(i));
			}
		}
		return result;
	}
	
	public int copyAptitude(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		result+=copyHobby(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyBook(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyPet(idJsk, idResume, idResumeDst, idLanguageDst);
		result+=copyTravel(idJsk, idResume, idResumeDst, idLanguageDst);
		return result;
	}
	
	public int copyHobby(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Hobby> hobbies=new HobbyManager().getAll(idJsk, idResume);
		if(hobbies.size()>0)
		{
			new HobbyManager().deleteAll(idJsk, idResumeDst);
			for(int i=0; i<hobbies.size(); i++)
			{
				hobbies.get(i).setIdResume(idResumeDst);
				result+=new HobbyManager().add(hobbies.get(i));
			}
		}
		return result;
	}
	
	public int copyBook(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Book> bookList=new BookManager().getAll(idJsk, idResume);
		if(bookList.size()>0)
		{
			new BookManager().deleteAll(idJsk, idResumeDst); 
			for(int i=0; i<bookList.size(); i++)
			{
				bookList.get(i).setIdResume(idResumeDst);
				result+=new BookManager().add(bookList.get(i));	
			}
		}
		return result;
	}
	
	public int copyPet(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Pet> pets=new PetManager().getAll(idJsk, idResume);
		if(pets.size()>0)
		{
			new PetManager().deleteAllPet(idJsk, idResumeDst);
			for(int i=0; i<pets.size(); i++)
			{
				pets.get(i).setIdResume(idResumeDst);
				result+=new PetManager().add(pets.get(i));
			}
		}
		return result;
	}
	public int copyTravel(int idJsk, int idResume,int idResumeDst, int idLanguageDst)
	{
		int result=0;
		List<Travel> travels=TravelManager.getAll(idJsk, idResume);
		if(travels.size()>0)
		{
			TravelManager.deleteAllTravel(idJsk, idResumeDst);
			for(int i=0; i<travels.size(); i++)
			{
				travels.get(i).setIdResume(idResumeDst);
				result+=TravelManager.add(travels.get(i));
			}
		}
		return result;
	}
	
	public int copySocial(int idJsk, int idResume,int idResumeDst,int idLanguageDst)
	{
		int result=0;
		Social social  = new SocialManager().get(idJsk, idResume);
		Social socialDst = new SocialManager().get(idJsk, idResumeDst);
		socialDst.setIdJsk(idJsk);
		socialDst.setIdResume(idResumeDst);
		if(!Util.getStr(social.getLineId()).equals(""))
		{
			socialDst.setLineId(social.getLineId());
		}
		if(!Util.getStr(social.getSkype()).equals(""))
		{
			socialDst.setSkype(social.getSkype());
		}
		
		if(new SocialManager().isExist(idJsk, idResumeDst))
		{
			result =new SocialManager().update(socialDst);
		}else
		{
			result =new SocialManager().insert(socialDst);
		}
		return result ;
	}
	public int updateTimestamp(int idJsk, int idResume)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("UPDATE INTER_RESUME SET TIMESTAMP=SYSDATE WHERE ID_JSK=? AND ID_RESUME=?");
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
	
	public int getIdResumeLastUpDate(int idJsk)
	{
		int result=-1;
		String SQL=" SELECT   "
				+ "		SYSDATE - TIMESTAMP AS DIFF_DATE, INTER_RESUME.* "
				+ "	FROM   "
				+ "		INTER_RESUME  "
				+ "	WHERE   "
				+ "		ID_JSK = ? AND DELETE_STATUS <> 'TRUE'  "
				+ "	ORDER BY   "
				+ "		TIMESTAMP DESC ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				if(db.getInt("DIFF_DATE")>180)
				{
			    	result=db.getInt("ID_RESUME");
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
	
	public List<Resume> getAllChildResume(int idJsk, int idParent)
	{
		List<Resume> result = new ArrayList<Resume>();
		String sql= "SELECT " +
				"	TRUNC((MONTHS_BETWEEN(SYSDATE, TIMESTAMP))/12)  AS YEAR_DIFF , "+ 
				" 	TRUNC( MOD(MONTHS_BETWEEN(SYSDATE, TIMESTAMP),12))  AS MONTH_DIFF, "+ 
				" 	TRUNC(SYSDATE - ADD_MONTHS( TIMESTAMP, TRUNC(MONTHS_BETWEEN(SYSDATE,TIMESTAMP)))+1) AS DAY_DIFF,"+
				"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
				"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
				"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
				"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
				"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
				"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE, ID_PARENT "+ 
				"	FROM INTER_RESUME " +
				"	WHERE " +
				"ID_JSK=? and ID_PARENT=? and DELETE_STATUS<>'TRUE'";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idParent);
			db.executeQuery();
			while(db.next())
			{
				Resume res=new Resume();
		    	res.setIdJsk(db.getInt("ID_JSK"));
		    	res.setIdResume(db.getInt("ID_RESUME"));
		    	res.setResumeName(db.getString("RESUME_NAME"));
		    	res.setResumePrivacy(db.getString("RESUME_PRIVACY"));
		    	res.setFirstName(db.getString("FIRST_NAME"));
		    	res.setLastName(db.getString("LAST_NAME"));
		    	res.setIdCountry(db.getInt("ID_COUNTRY"));
		    	res.setIdState(db.getInt("ID_STATE"));
		    	res.setIdCity(db.getInt("ID_CITY"));
		    	res.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	res.setHomeAddress(db.getString("HOME_ADDRESS"));
		    	res.setPostal(db.getString("POSTAL"));
		    	res.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	res.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	res.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
		    	res.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
		    	res.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
		    	res.setCreateDate(db.getTimestamp("CREATE_DATE"));
		    	res.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	res.setExpStatus(db.getString("EXP_STATUS"));
		    	res.setExpCompany(db.getInt("EXP_COMPANY"));
		    	res.setOtherState(db.getString("OTHER_STATE"));
		    	res.setOtherCity(db.getString("OTHER_CITY"));
		    	res.setSalutation(db.getString("SALUTATION"));
		    	res.setFirstNameThai(db.getString("FIRST_NAME_TH"));
		    	res.setLastNameThai(db.getString("LAST_NAME_TH"));
		    	res.setBirthDate(db.getDate("BIRTHDATE"));
		    	res.setHeight(db.getFloat("HEIGHT"));
		    	res.setWeight(db.getFloat("WEIGHT"));
		    	res.setCitizenship(db.getString("CITIZENSHIP"));
		    	res.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
		    	res.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
		    	res.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
		    	res.setExpMonth(db.getInt("EXP_MONTH"));	
		    	res.setExpYear(db.getInt("EXP_YEAR"));
		    	res.setCompleteStatus(db.getString("COMPLETE_STATUS"));
		    	res.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
		    	res.setDayPass(db.getInt("DAY_DIFF"));
		    	res.setMonthPass(db.getInt("MONTH_DIFF"));
		    	res.setYearPass(db.getInt("YEAR_DIFF"));
		    	res.setIdParent(db.getInt("ID_PARENT"));
		    	result.add(res);
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
	
	public int getLatestCompleteIdResume(int idJsk)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_RESUME FROM INTER_RESUME WHERE ID_JSK=? AND ID_RESUME > 0 AND DELETE_STATUS <> 'TRUE' AND COMPLETE_STATUS = 'TRUE' ORDER BY TIMESTAMP DESC");
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ID_RESUME");
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
	
	public List<Resume> getAllResumeByIdLanguage(int idJsk,int idLanguage,int idResume)
	{
		List<Resume> result = new ArrayList<Resume>();
		DBManager db=null;
		String sql=	"SELECT " +
					"	ID_RESUME, ID_JSK, RESUME_NAME, RESUME_PRIVACY, FIRST_NAME, LAST_NAME, ID_COUNTRY, " +
					"	ID_STATE, ID_CITY, ID_LANGUAGE, HOME_ADDRESS, POSTAL, PRIMARY_PHONE, PRIMARY_PHONE_TYPE, " +
					"	SECONDARY_PHONE, SECONDARY_PHONE_TYPE, SECONDARY_EMAIL, CREATE_DATE, " +
					"	TIMESTAMP, EXP_STATUS, EXP_COMPANY, OTHER_STATE, OTHER_CITY, SALUTATION, FIRST_NAME_TH, " +
					"	LAST_NAME_TH, BIRTHDATE, HEIGHT, WEIGHT, CITIZENSHIP, OWNCAR_STATUS, APPLY_ID_COUNTRY, " +
					"	TEMPLATE_ID_COUNTRY, EXP_MONTH, EXP_YEAR, COMPLETE_STATUS, LOCALE, ID_PARENT FROM INTER_RESUME " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_LANGUAGE=?) AND (DELETE_STATUS<>'TRUE') AND ID_RESUME <> ? " +
					"ORDER BY ID_RESUME";
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idLanguage);
			db.setInt(3, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Resume res=new Resume();
		    	res.setIdJsk(db.getInt("ID_JSK"));
		    	res.setIdResume(db.getInt("ID_RESUME"));
		    	res.setResumeName(db.getString("RESUME_NAME"));
		    	res.setResumePrivacy(db.getString("RESUME_PRIVACY"));
		    	res.setFirstName(db.getString("FIRST_NAME"));
		    	res.setLastName(db.getString("LAST_NAME"));
		    	res.setIdCountry(db.getInt("ID_COUNTRY"));
		    	res.setIdState(db.getInt("ID_STATE"));
		    	res.setIdCity(db.getInt("ID_CITY"));
		    	res.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	res.setHomeAddress(db.getString("HOME_ADDRESS"));
		    	res.setPostal(db.getString("POSTAL"));
		    	res.setPrimaryPhone(db.getString("PRIMARY_PHONE"));
		    	res.setPrimaryPhoneType(db.getString("PRIMARY_PHONE_TYPE"));
		    	res.setSecondaryPhone(db.getString("SECONDARY_PHONE"));
		    	res.setSecondaryPhoneType(db.getString("SECONDARY_PHONE_TYPE"));
		    	res.setSecondaryEmail(db.getString("SECONDARY_EMAIL"));
		    	res.setCreateDate(db.getTimestamp("CREATE_DATE"));
		    	res.setTimeStamp(db.getTimestamp("TIMESTAMP"));
		    	res.setExpStatus(db.getString("EXP_STATUS"));
		    	res.setExpCompany(db.getInt("EXP_COMPANY"));
		    	res.setOtherState(db.getString("OTHER_STATE"));
		    	res.setOtherCity(db.getString("OTHER_CITY"));
		    	res.setSalutation(db.getString("SALUTATION"));
		    	res.setFirstNameThai(db.getString("FIRST_NAME_TH"));
		    	res.setLastNameThai(db.getString("LAST_NAME_TH"));
		    	res.setBirthDate(db.getDate("BIRTHDATE"));
		    	res.setHeight(db.getFloat("HEIGHT"));
		    	res.setWeight(db.getFloat("WEIGHT"));
		    	res.setCitizenship(db.getString("CITIZENSHIP"));
		    	res.setOwnCarStatus(db.getString("OWNCAR_STATUS"));
		    	res.setApplyIdCountry(db.getInt("APPLY_ID_COUNTRY"));
		    	res.setTemplateIdCountry(db.getInt("TEMPLATE_ID_COUNTRY"));
		    	res.setExpMonth(db.getInt("EXP_MONTH"));	
		    	res.setExpYear(db.getInt("EXP_YEAR"));
		    	res.setCompleteStatus(db.getString("COMPLETE_STATUS"));
		    	res.setLocale(getLocale(db.getInt("ID_LANGUAGE"),db.getInt("TEMPLATE_ID_COUNTRY")));
		    	res.setIdParent(db.getInt("ID_PARENT"));
		    	result.add(res);
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
	
	public int getSuperMatchIdResume(int idJsk)
	{
		int result=-1;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_RESUME FROM INTER_SUPERMATCH_RESUME WHERE ID_JSK=?");
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ID_RESUME");
				
			}
			if(result==-1){
				db=new DBManager();
				db.createPreparedStatement("SELECT ID_RESUME FROM MAIL_JOBSEEKER WHERE ID_JSK=?");
				db.setInt(1, idJsk);
				db.executeQuery();
				if(db.next())
				{
					result=db.getInt("ID_RESUME");
					
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
	
	public int insSuperMatchIdResume(int idJsk,int idResume)
	{
		int result=-1;
		DBManager rs=null;
		try
		{
			
			if(DeleteSuperMatchIdResume(idJsk)>0){
			
			rs=new DBManager();
			rs.createPreparedStatement("INSERT INTO INTER_SUPERMATCH_RESUME(ID_RESUME, ID_JSK) VALUES(?,?) ");
			rs.setInt(1, idResume);
			rs.setInt(2, idJsk);
			result=rs.executeUpdate();	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rs.close();
		}
		return result;
	}
	
	public int DeleteSuperMatchIdResume(int idJsk)
	{
		DBManager db=null;
		int result=0;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("DELETE INTER_SUPERMATCH_RESUME WHERE ID_JSK=?");
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
	
	public int getIdResumeLastUpdate(int idJsk)
	{
		int result=-1;
		DBManager db=null;
		try
		{
			db=new DBManager();
			String sql= "SELECT ID_RESUME FROM INTER_RESUME WHERE ID_JSK=? AND (DELETE_STATUS<>'TRUE')"
					+"AND ID_RESUME > 0 AND (ID_PARENT = 0 OR ID_PARENT IS NULL) ORDER BY TIMESTAMP DESC";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ID_RESUME");
				
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
	
	public int getIdResumeLastApply(int idJsk)
	{
		int result=-1;
		DBManager db=null;
		try
		{
			db=new DBManager();
			String sql= "SELECT ID_RESUME FROM INTER_RESUME WHERE ID_JSK=? AND (DELETE_STATUS<>'TRUE')"
					+"AND ID_RESUME > 0 AND (ID_PARENT = 0 OR ID_PARENT IS NULL) AND LAST_APPLY IS NOT NULL ORDER BY LAST_APPLY DESC";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ID_RESUME");
				
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
	
	public int updateLastApply(int idJsk , int idResume)
	{
		int result = 0 ;
		String sql = "UPDATE INTER_RESUME SET LAST_APPLY = SYSDATE WHERE ID_JSK = ? AND ID_RESUME = ?";
		DBManager db = null ;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			result = db.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			db.close();
		}
		return result ;
	}
}
