package com.topgun.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class JobmatchManager {
	
	public static String getLastEducation(int idJsk,int idResume)
	{
		String result="",idDeg="",idMaj="",idFac="", finishYear="";
		String SQL=" SELECT  e.id,e.id_degree,e.id_major,e.id_fac_major,to_char(finish_date,'YYYY') as finishYear FROM  INTER_EDUCATION e LEFT JOIN INTER_DEGREE d ON e.id_degree = d.id_degree "+ 
		   " WHERE id_jsk = ? AND id_resume= ? AND delete_status='FALSE' AND degree_order IN "+
		   "(SELECT MIN(degree_order) FROM  INTER_EDUCATION e LEFT JOIN	INTER_DEGREE d ON e.id_degree = d.id_degree "+
		   " WHERE id_jsk = ? AND id_resume=? AND delete_status='FALSE') ORDER BY e.id desc";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idJsk);
			db.setInt(4, idResume);
			db.executeQuery();
			if(db.next())
			{	
				idDeg=db.getString("id_degree")!=null?db.getString("id_degree"):"";
				idMaj =db.getString("id_major")!=null?db.getString("id_major"):"";
				idFac = db.getString("id_fac_major")!=null?db.getString("id_fac_major"):"";
				finishYear = db.getString("finishYear")!=null?db.getString("finishYear"):"";
				result = idDeg+","+idMaj+","+idFac+","+finishYear;
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
	
	public static int convertToSaralyPerMonth(int total, int saralyPer,int idCurrency)
	{
		int result=0;
		if(saralyPer==1)//1=ต่อปี
		{
			result=total/12;
		}
		else if(saralyPer==2)//2=ต่อเดือน
		{
			result=total;
		}
		else if(saralyPer==3)// 3=ต่อสัปดาห์
		{
			result=total*4 ;
		}
		else if(saralyPer==4)//4=ต่อ 2 สัปดาห์
		{
			result=total*2;
		}
		else if(saralyPer==5)//5=ต่อวัน
		{
			result=total*30;
		}
		else if(saralyPer==6)//6=ต่อชั่วโมง
		{
			result=total*176;
		}
		//currency
		if(idCurrency!=140)
		{
			result=convertCurrency(result,idCurrency);
		}
		return result;
	}
	
	public static int convertCurrency(int total, int idCurrency)
	{
		int result=total;
		String SQL=" SELECT BAHT FROM RMS_CURRENCY WHERE ID_CURRENCY=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idCurrency);
			db.executeQuery();
			if(db.next())
			{	
				result=(total*db.getInt("BAHT"))+0;
				if(result<=0)
				{
					result=total;
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
	
	public static String getLastWorkField(int idJsk,int idResume)
	{
		String result="",jf="",sf="",sl="",sp="",cur="",workEnd="",idWork="",salary="";
		/*String SQL=	"SELECT id,workjob_field,worksub_field,salary_last,salary_per,id_currency, TO_CHAR(work_end,'YYYY') AS workEnd FROM INTER_WORK_EXPERIENCE "+
					"WHERE ID_JSK=? and ID_RESUME=? AND DELETE_STATUS = 'FALSE' AND "+
					"WORK_END = (SELECT MAX(WORK_END) FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS = 'FALSE' ) ORDER BY ID DESC";*/
		String SQL=
				"SELECT " +
				"	WORKJOB_FIELD,WORKSUB_FIELD,SALARY_PER_MONTH," +
				"	SALARY_PER,SALARY_LAST,ID_CURRENCY,TO_CHAR(WORK_END,'YYYY') AS WORK_END ,ID,WORKING_STATUS,WORK_START,ID AS ID_WORK " +
				"FROM " +
				"	INTER_WORK_EXPERIENCE " +
				"WHERE " +
				"	DELETE_STATUS<>'TRUE' AND WORKJOB_FIELD>0 AND ID_JSK=?  AND ID_RESUME=? " +
				" 	AND (WORK_JOBTYPE <> 2 AND WORK_JOBTYPE <> 4  AND  WORK_JOBTYPE <> 7) " +
				"ORDER BY " +
				"	WORK_END DESC ,ID DESC";
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
				jf=db.getString("WORKJOB_FIELD")!=null?db.getString("WORKJOB_FIELD"):"";
				sf=db.getString("WORKSUB_FIELD")!=null?db.getString("WORKSUB_FIELD"):"";
				sl=db.getString("SALARY_LAST")!=null?db.getString("SALARY_LAST"):"";
				sp=db.getString("SALARY_PER")!=null?db.getString("SALARY_PER"):"";
				cur=db.getString("ID_CURRENCY")!=null?JobManager.getCurrency(Integer.parseInt(db.getString("ID_CURRENCY"))):"Baht";
				workEnd=db.getString("WORK_END")!=null?db.getString("WORK_END"):"";
				idWork=db.getString("ID_WORK")!=null?db.getString("ID_WORK"):"";
				if(sl!="")
				{
					sl=Util.getStr(convertToSaralyPerMonth(db.getInt("SALARY_LAST"), db.getInt("SALARY_PER"),db.getInt("ID_CURRENCY")));
				}
				result=jf+","+sf+","+sl+","+sp+","+cur+","+workEnd+","+idWork;
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
	
	public static String SqlJobMatch(int idJsk,int idResume)
	{	
		String education[]= getLastEducation(idJsk,idResume).split(",");
		String workfield[]= getLastWorkField(idJsk,idResume).split(",");
		String idDegree="",idMajor="",idFaculty="",jobField="",subField="",salaryLast="",finishYear="",workEnd="";
		int idWork=-1;
		Resume resume = new ResumeManager().get(idJsk, idResume);
		int totalDay=Util.getInt(Util.getDayInterval(resume.getTimeStamp(), new Timestamp(new Date().getTime())),0) ;
		int month = Util.getMonthInterval(resume.getBirthDate(), Util.getCurrentSQLDate());
		int age = month/12;
		int exp = resume.getExpYear();
		String sex = resume.getSalutation();
		if(education !=null){
			for (int i=0 ; i < education.length ; i++){
			   if(i==0){
				   idDegree=  education[i]; 
			   }else if(i==1){
				   idMajor=  education[i]; 
			   }else if(i==2){
				   idFaculty=  education[i]; 
			   }else if(i==3){
				   finishYear=  education[i]; 
			   }
			}	
		}
		if(workfield!=null){
			if(workfield.length>=3){
				for (int i=0 ; i < workfield.length ; i++){
					   if(i==0){
						   jobField =  workfield[i]; 
					   }else if(i==1){
						   subField =  workfield[i]; 
					   }else if(i==2){
						   salaryLast = workfield[i]!=null?workfield[i]:"0";
						   salaryLast = salaryLast!=""?salaryLast:"0";
					   }else if(i==5){
						   workEnd = workfield[i]!=null&&workfield[i]!=""?workfield[i]:"";
					   }else if(i==6){
						   idWork =Util.getInt(workfield[i],-1);
					   }
				}	
			}
		}
		String sl = salaryLast; String sm="",SQL_SAL="";
		if(!salaryLast.equals(""))
		{
			if(Integer.parseInt(salaryLast)<50000)
			{
				sm = String.valueOf(Integer.parseInt(salaryLast) + 10000);
			}
			else
			{
				sm = String.valueOf(Integer.parseInt(salaryLast) + 20000);
			}
			if(!(sl.equals("")))
			{
				SQL_SAL+="(POSITION.SALARY_LESS <="+sl+" AND POSITION.SALARY_MOST >="+sl+" AND POSITION.SALARY_MOST <> 999999999)";
				SQL_SAL+=" OR (POSITION.SALARY_LESS <="+sl+" AND (POSITION.SALARY_LESS*1.5) >="+sl+" AND POSITION.SALARY_MOST=999999999)";
			}
			if(!(sm.equals("")))
			{
				if(SQL_SAL.equals(""))
				{ 
					SQL_SAL+=" (POSITION.SALARY_LESS <="+sm+" AND POSITION.SALARY_MOST >="+sm+" AND POSITION.SALARY_MOST <> 999999999)";	
					SQL_SAL+=" OR (POSITION.SALARY_LESS <="+sm+" AND (POSITION.SALARY_LESS*1.5) >="+sm+" AND POSITION.SALARY_MOST=999999999)";
				}else
				{
					SQL_SAL+=" OR (POSITION.SALARY_LESS <="+sm+" AND POSITION.SALARY_MOST >="+sm+" AND POSITION.SALARY_MOST <> 999999999)";
					SQL_SAL+=" OR (POSITION.SALARY_LESS <="+sm+" AND (POSITION.SALARY_LESS*1.5) >="+sm+" AND POSITION.SALARY_MOST=999999999)";
					SQL_SAL+=" OR (POSITION.SALARY_LESS <="+sl+" AND POSITION.SALARY_MOST >="+sl+" AND POSITION.SALARY_MOST<>999999999)";
					SQL_SAL+=" OR (POSITION.SALARY_LESS <="+sm+" AND POSITION.SALARY_MOST >="+sm+" AND POSITION.SALARY_MOST<>999999999)";
				}
			}
		}
		String JobFamilySQL="",SQL_MATCH="";
		int jobfield=0,subfield=0,idmajor=0,idfaculty=0,iddegree=0;
		if(!jobField.equals("")){jobfield = Integer.parseInt(jobField);}
		if(!subField.equals("")){subfield = Integer.parseInt(subField);}
		if(!idMajor.equals("")){idmajor = Integer.parseInt(idMajor);}
		if(!idFaculty.equals("")){idfaculty = Integer.parseInt(idFaculty);}
		if(!idDegree.equals("")){iddegree = Integer.parseInt(idDegree);}
		//New
		if(jobfield>0)// have exp
		{
			SQL_MATCH+=" (PW.WORKJOB_FIELD='"+jobfield+"' ) ";
			if(subfield>0){
				SQL_MATCH+=" AND (PW.WORKSUB_FIELD='"+subfield+"' ) ";
			}
		}
		else// no exp
		{
			JobFamilySQL=getJobFamilySQL(idfaculty,idmajor);
			//System.out.println("JobFamilySQL= "+JobFamilySQL);
			if(JobFamilySQL.equals(""))// ไม่มี family ก็ match ตรงๆ
			{
				if(idfaculty>0)
				{
					SQL_MATCH+="(MJ.ID_FACULTY='"+idfaculty+"') ";
					if(idmajor>0)
					{
						SQL_MATCH+=" AND (MJ.ID_MAJOR='"+idMajor+"' ) ";
					}
				}
				else // กรอก faculty and major เป็น other
				{ 
					SQL_MATCH="";
				}
			}
			else
			{
				SQL_MATCH=" ( "+JobFamilySQL+" ) ";
			}
		}
		if(!SQL_MATCH.equals("")){
			/*************************
			* Experience Matching
			*************************/	
			int minExp=0,maxExp=0;
			int newExpYear=exp;
			if(totalDay>365)//total day on update resume
			{
				newExpYear=getTotalNewExp(exp,finishYear,workEnd);
			}
			//System.out.println(" total day (update resume )is"+totalDay+"   idJsk= "+idJsk+ "   idResume= "+idResume+"  jobfield ="+jobfield+" subField ="+subField+"   idFaculty ="+idFaculty+" idMajor ="+idMajor+" idWork ="+idWork+"  old= "+exp+"   newExpYear > "+newExpYear+"  SQL_MATCH > "+SQL_MATCH);
			switch(newExpYear){
			//Edited by Ton
				case 0: minExp=0;maxExp=0; break;
		    	case 1: minExp=1;maxExp=2; break;
		        case 2: minExp=1;maxExp=3; break;
		        case 3: minExp=1;maxExp=5; break;
		        case 4: minExp=2;maxExp=6; break;
		        case 5: minExp=3;maxExp=8; break;
		        case 6: minExp=4;maxExp=9; break;
		        case 7: minExp=5;maxExp=10; break;
		        case 8: minExp=5;maxExp=25; break;		        
		        case 9: minExp=6;maxExp=25; break;
		        case 10: minExp=7;maxExp=25; break;
		        case 11: minExp=8;maxExp=25; break;
		        case 12: minExp=8;maxExp=25; break;
		        case 13: minExp=8;maxExp=25; break;
		        case 14: minExp=8;maxExp=25; break;
		        case 15: minExp=8;maxExp=25; break;
		        default : minExp=8;maxExp=25; break;
			}
			
			String SQL_MATCH_TEMP1="",SQL_MATCH_TEMP2="";
			//new
			if(!salaryLast.equals(""))//jsk  have salary
			{
				if(newExpYear>0)
				{
					SQL_MATCH_TEMP1="("+SQL_SAL+") OR ((((exp_less<= "+minExp+") AND (exp_most>="+minExp+")) or ((exp_less>="+minExp+") and (exp_less<="+maxExp+")) or exp_less = "+newExpYear+") AND POSITION.SALARY_LESS=0 AND POSITION.SALARY_MOST=0 ) ";
					SQL_MATCH=" AND (("+SQL_MATCH+") AND ("+SQL_MATCH_TEMP1+"))";
				}
				else
				{	
					SQL_MATCH="  AND (("+SQL_MATCH+")  AND ("+SQL_SAL+" OR (exp_less=0 AND POSITION.SALARY_LESS=0 AND POSITION.SALARY_MOST=0) ))";
				}
			}
			else//jsk dont's have salary
			{
				if(newExpYear>0)
				{	
					SQL_MATCH_TEMP1="("+SQL_MATCH+") AND (((exp_less<= "+minExp+") AND (exp_most>="+minExp+")) or ((exp_less>="+minExp+") and (exp_less<="+maxExp+")) or exp_less = "+newExpYear+") ";
					SQL_MATCH=" AND ("+SQL_MATCH_TEMP1+")";
				}
				else
				{	
					SQL_MATCH=" AND ("+SQL_MATCH+") AND (exp_less=0) ";
				}
			}
			/////////////////old
			/*	if(newExpYear>0)
				{	
					SQL_MATCH_TEMP1="("+SQL_MATCH+") AND (((exp_less<= "+minExp+") AND (exp_most>="+minExp+")) or ((exp_less>="+minExp+") and (exp_less<="+maxExp+")) or exp_less = "+newExpYear+") ";
					if(!SQL_SAL.equals(""))
					{
						SQL_MATCH_TEMP2="(("+SQL_MATCH+") AND ("+SQL_SAL+"))";
						SQL_MATCH=" AND ("+SQL_MATCH_TEMP1+" OR "+SQL_MATCH_TEMP2+")";
					}
					else
					{
						SQL_MATCH=" AND ("+SQL_MATCH_TEMP1+")";	
					}
				}
				else
				{	
					SQL_MATCH=" AND ("+SQL_MATCH+") AND (exp_less=0) ";
				}
			*/
			//old
			/**********************
			* Gender Matching
			**********************/		
			
			if((sex.equals("MRS"))||sex.equals("MISS"))  //If Female 
			{
				SQL_MATCH+=" AND (SEX<>'1') "; //Exclude Male
			}
			else if(sex.equals("MR")) //If Male
			{
				SQL_MATCH+=" AND (SEX<>'2') "; //Exclude Female
			}
	
			/**********************
			* Degree Matching
			**********************/	
			
			if(!idDegree.equals(""))
			{	
				if(iddegree==1)
				{
					SQL_MATCH+=" AND (DEGREE=1) ";
				}
				else if(iddegree==2)
				{	 	
					SQL_MATCH+=" AND (DEGREE IN(2,6,8,9)) ";
				}
				else if(iddegree==3)
				{
					SQL_MATCH+=" AND (DEGREE IN(3,6,8,11,13)) ";
				}
				else if(iddegree==4)
				{
					SQL_MATCH+=" AND (DEGREE IN (4,9,10,12,-1)) ";
				}
				else if(iddegree==5)
				{
					SQL_MATCH+=" AND (DEGREE IN (5,10,12,-1)) ";
				}
				else if(iddegree==7)
				{
					SQL_MATCH+=" AND (DEGREE IN(7,8,11)) ";
				}
				else if(iddegree==14)
				{
					SQL_MATCH+=" AND (DEGREE IN(9,10,12,14,-1)) ";
				}
				else if(iddegree==15)
				{
					SQL_MATCH+=" AND (DEGREE IN(9,10,12,15,-1)) ";
				}
			}
		}
		else //No Jobfield
		{
			/**********************
			* Degree Matching
			**********************/	
			
			if(!idDegree.equals(""))
			{	
				if(iddegree==1)
				{
					SQL_MATCH+=" AND (DEGREE=1) ";
				}
				else if(iddegree==2)
				{	 	
					SQL_MATCH+=" AND (DEGREE IN(2,6,8,9)) ";
				}
				else if(iddegree==3)
				{
					SQL_MATCH+=" AND (DEGREE IN(3,6,8,11,13)) ";
				}
				else if(iddegree==4)
				{
					SQL_MATCH+=" AND (DEGREE IN (4,9,10,12,-1)) ";
				}
				else if(iddegree==5)
				{
					SQL_MATCH+=" AND (DEGREE IN (5,10,12,-1)) ";
				}
				else if(iddegree==7)
				{
					SQL_MATCH+=" AND (DEGREE IN(7,8,11)) ";
				}
				else if(iddegree==14)
				{
					SQL_MATCH+=" AND (DEGREE IN(9,10,12,14,-1)) ";
				}
				else if(iddegree==15)
				{
					SQL_MATCH+=" AND (DEGREE IN(9,10,12,15,-1)) ";
				}
			}
		}
		if(age>0)
		{
			SQL_MATCH+=" AND (("+age+" >= AGE_MIN AND "+age+" <= AGE_MAX ) OR (AGE_MIN=-1 AND AGE_MAX=-1) OR (AGE_MIN=0 AND AGE_MAX=0) OR  (AGE_MIN IS NULL AND AGE_MAX IS NULL))";
		}
		return SQL_MATCH;
	}
	
	public static String getJobSubfieldSQL(int idJobfield,int idSubfield)
	{
		String SubfieldSQL=JobManager.getJobSubfieldSQL(idJobfield, idSubfield);
		return SubfieldSQL.trim();
	}
	
	
	public static String getJobFamilySQL(int faculty,int major)
	{

		String FamilySQL="";
		String jobFieldSql=JobManager.getFieldFamilySQL(faculty, major);
		String subFieldSql=JobManager.getSubFieldFamilySQL(faculty, major);
		if(jobFieldSql!="")
		{
			FamilySQL=jobFieldSql;
		}
		if(subFieldSql!="")
		{
			/*if(FamilySQL=="")
			{
				FamilySQL=subFieldSql;
			}
			else
			{
				FamilySQL="("+FamilySQL+")  OR ("+subFieldSql+")";
			}*/
			FamilySQL=subFieldSql;
		}
		return FamilySQL.trim();
	}
	public static String getLastEducationName(int idJsk,int idResume){
		String reSult="";
		String education[]= getLastEducation(idJsk,idResume).split(",");
		String idDegree="",idMajor="",idFaculty="",majorName="",facultyName="",degreeName="";
		if(education !=null){
			for (int i=0 ; i < education.length ; i++){
			   if(i==0){
				   idDegree=  education[i]; 
			   }else if(i==1){
				   idMajor=  education[i]; 
			   }else if(i==2){
				   idFaculty=  education[i]; 
			   }
			}	
		}
		String SQL="SELECT F.FACULTY_NAME,M.MAJOR_NAME FROM INTER_FACULTY F,INTER_MAJOR M WHERE F.ID_FACULTY = M.ID_FACULTY "+
		           " AND M.ID_FACULTY = ? AND M.ID_MAJOR= ?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, idFaculty);
			db.setString(2, idMajor);
			
			db.executeQuery();
			if(db.next())
			{	
				majorName=db.getString("MAJOR_NAME")!=null?db.getString("MAJOR_NAME"):"";
				facultyName =db.getString("FACULTY_NAME")!=null?db.getString("FACULTY_NAME"):"";
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
		SQL="SELECT DEGREE_NAME FROM INTER_DEGREE WHERE ID_DEGREE=?";
		db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, idDegree);
			db.executeQuery();
			if(db.next())
			{	
				degreeName=db.getString("DEGREE_NAME")!=null?db.getString("DEGREE_NAME"):"";
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
		if(degreeName.equals("")){degreeName="-";}
		if(majorName.equals("")){majorName="-";}
		if(facultyName.equals("")){facultyName="-";}
		reSult = degreeName+","+majorName+","+facultyName;
		return reSult;
	}
	public static String getLastWorkFieldName(int idJsk,int idResume){
		String reSult="";
		String workfield[]= getLastWorkField(idJsk,idResume).split(",");
		String jobField="",subField="",jobFieldName="",subFieldName="";
		if(workfield!=null){
			for (int i=0 ; i < workfield.length ; i++){
				   if(i==0){
					   jobField=  workfield[i]; 
				   }else if(i==1){
					   subField=  workfield[i]; 
				   }
			}	
		}
		String SQL="SELECT FIELD_NAME,SUBFIELD_NAME FROM INTER_JOBFIELD j,INTER_SUBFIELD S WHERE J.ID_FIELD = S.ID_FIELD "+
					"AND S.ID_FIELD=? AND S.ID_SUBFIELD=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, jobField);
			db.setString(2, subField);
			
			db.executeQuery();
			if(db.next())
			{	
				jobFieldName = db.getString("FIELD_NAME")!=null?db.getString("FIELD_NAME"):"";
				subFieldName = db.getString("SUBFIELD_NAME")!=null?db.getString("SUBFIELD_NAME"):"";
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
		if(subFieldName.equals("")){
			reSult=jobFieldName;	
		}else{
			reSult=jobFieldName+" - "+subFieldName;
		}
		if(reSult.equals("")){reSult="-";}
		return reSult;
	}
	public static String getLastEducationId(int idJsk,int idResume)
	{
		String result="";
		String SQL=" SELECT e.id FROM  INTER_EDUCATION e LEFT JOIN INTER_DEGREE d ON e.id_degree = d.id_degree "+ 
				   " WHERE id_jsk = ? AND id_resume= ? AND degree_order IN "+
				   "(SELECT MIN(degree_order) FROM  INTER_EDUCATION e LEFT JOIN	INTER_DEGREE d ON e.id_degree = d.id_degree "+
		           " WHERE id_jsk = ? AND id_resume=? AND delete_status='FALSE')";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idJsk);
			db.setInt(4, idResume);
			db.executeQuery();
			if(db.next())
			{	
				result=db.getString("id")!=null?db.getString("id"):"";
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
	public static String getLastWorkFieldId(int idJsk,int idResume)
	{
		String result="";
		String SQL=	"SELECT id FROM INTER_WORK_EXPERIENCE "+
					"WHERE ID_JSK=? and ID_RESUME=? AND "+
					"WORK_END = (SELECT MAX(WORK_END) FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS = 'FALSE' )";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idJsk);
			db.setInt(4, idResume);
			db.executeQuery();
			while(db.next())
			{
				result=db.getString("id")!=null?db.getString("id"):"";
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
	
	public static int getTotalNewExp(int totalExp, String finishDate, String finishWork )
	{
		int expTotal=totalExp;
		int thisYear= Integer.parseInt(nowDateFormat("yyyy"));
		int finishEduYear=finishDate!=null &&finishDate!=""?Integer.parseInt(finishDate):0;//end date of education
		int finishWorkYear=finishWork!=null && finishWork!=""?Integer.parseInt(finishWork):0;//end date of exp
		if(expTotal==0)
		{
			//ถ้าเกิน 10 ปี จะปรับเป็น 10 ปี
			if(finishEduYear<=thisYear)
			{
				if(finishEduYear<=thisYear-10)
				{	expTotal=10;}
				else
				{	expTotal=thisYear-finishEduYear;}
			}
		}else 
		{
			if(finishWorkYear<=thisYear && finishWorkYear>0)
			{
				if(finishWorkYear<=thisYear-10)
				{	expTotal=10;}
				else
				{	expTotal=expTotal+(thisYear-finishWorkYear);}
			}
			else if(finishEduYear==0)
			{
				//ถ้าเกิน 10 ปี จะปรับเป็น 10 ปี
				if(finishEduYear<=thisYear)
				{
					if(finishEduYear<=thisYear-10)
					{	expTotal=10;}
					else
					{	expTotal=thisYear-finishEduYear;}
				}
			}
		}
		return expTotal;
	}
	 public static String nowDateFormat (String strFormat) 
	    {
	        String sDate = "";
			try 
			{
				Date today = new Date();
				SimpleDateFormat dataformat = new SimpleDateFormat(strFormat,Locale.US); //Create format Date
				sDate = dataformat.format( today ); //Convert Date to String (format(Date))
			}
			catch (Exception e) 
			{
			    e.printStackTrace();
			}
	    return sDate;
	}
}
