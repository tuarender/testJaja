package com.topgun.supermatch;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import com.topgun.util.DBManager;
import com.topgun.util.Util;
import org.apache.commons.codec.binary.Base64;


public class SuperMatch
{
	private int idJsk=-1;
	private int idResume=-1;
	private int curYear=-1;
	private Resume resume=null;
	private Education lastEdu=null;
	private Experience lastExp=null;
	private TargetJob target=null;

	public SuperMatch(int idJsk, int idResume)
	{
		this.idJsk = idJsk;
		this.idResume = idResume;
		//System.out.println("idJsk: "+idJsk+"    idResume: "+idResume);
		initResume();
		if(this.resume!=null)
		{
			initLastExperience();
			initLastEducation();
			initTargetJob();
			if(this.resume.getLastUpdate()>365)//total day on update resume
			{
				calculateExpYear();
			}
		}
	}
	
	private void initResume()
	{
		if(this.idJsk==-1 || this.idResume==-1) return;

		String SQL= " SELECT "
				+	"	BIRTHDATE,TRUNC(MONTHS_BETWEEN(SYSDATE, BIRTHDATE)/12) AS AGE,ID_COUNTRY,ID_STATE, ID_LANGUAGE,"
				+	"	DECODE(UPPER(SALUTATION),'MISS',2,'MRS',2,'MR',1,-1) AS SEX ,"
				+	"	TO_CHAR(SYSDATE,'YYYY') AS CUR_YEAR,EXP_FULL_YEAR,"
				+ 	"	FLOOR(SYSDATE-TIMESTAMP) AS LAST_UPDATE ,FIRST_NAME,FIRST_NAME_TH,LAST_NAME,LAST_NAME_TH, TIMESTAMP "
				+	" FROM "
				+	"	INTER_RESUME "
				+ 	" WHERE "
				+ 	"	ID_JSK=? AND ID_RESUME=?  AND DELETE_STATUS<>'TRUE' ";

		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			if (db.next())
			{
				this.resume=new Resume();
				this.resume.setAge(db.getInt("AGE"));
				this.resume.setSex(db.getInt("SEX"));
				this.resume.setIdCountry(db.getInt("ID_COUNTRY"));
				this.resume.setIdState(db.getInt("ID_STATE"));
				this.resume.setExpYear(db.getInt("EXP_FULL_YEAR"));
				
				if(this.idResume==0)
				{
					int summary=getSummaryExp();
					if(summary > db.getInt("EXP_FULL_YEAR"))
					{
						this.resume.setExpYear(summary);
					}
				}
				
				this.resume.setLastUpdate(db.getInt("LAST_UPDATE"));
				this.curYear=db.getInt("CUR_YEAR");
				this.resume.setIdLanguage(db.getInt("ID_LANGUAGE"));
				this.resume.setLocation(getStateName(getStateTarget(), this.resume.getIdLanguage()));
				this.resume.setGender(getGender(db.getInt("SEX"), this.resume.getIdLanguage()));
				this.resume.setFirstName((db.getString("FIRST_NAME_TH")));
				this.resume.setLastName((db.getString("LAST_NAME_TH")));
				this.resume.setTimestamp(db.getString("TIMESTAMP"));
				if(db.getInt("ID_LANGUAGE")==11)
				{
					this.resume.setFirstName((db.getString("FIRST_NAME")));
					this.resume.setLastName((db.getString("LAST_NAME")));
				}
				this.resume.setLocationExclude(getStateTarget());
				this.resume.setCityList(getCityList());
				this.resume.setHasIndustrial(hasIndustrialLocation());
				//System.out.println("Location: "+this.resume.getLocationExclude());
				//System.out.println("City: "+this.resume.getCityList());
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
	}
	
	private void initLastExperience()
	{
		String SQL ="SELECT "
				+ 	"	ID,WORKJOB_FIELD,WORKSUB_FIELD,"
				+ 	"	SALARY_PER,SALARY_LAST,ID_CURRENCY,WORK_END,"
				+ 	"	TO_CHAR(WORK_END,'YYYY') AS WORK_END_YEAR, "
				+ 	"	WORKING_STATUS "
				+ 	"FROM "
				+ 	"	INTER_WORK_EXPERIENCE "
				+ 	"WHERE "
				+ 	"	ID_JSK=?  AND ID_RESUME=? AND "
				+ 	"	WORK_JOBTYPE NOT IN(2,4,7) AND "
				+ 	"	DELETE_STATUS<>'TRUE' "
				+ 	"ORDER BY "
				+ 	"	WORK_END DESC ,ID DESC";
		//System.out.println("initLastExperience=>"+SQL);
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			if (db.next())
			{
				this.lastExp=new Experience(this.idJsk,this.idResume);
				this.lastExp.setIdWork(db.getInt("ID"));
				this.lastExp.setIdJobField(db.getInt("WORKJOB_FIELD"));
				this.lastExp.setIdSubField(db.getInt("WORKSUB_FIELD"));
				this.lastExp.setJobfieldName(getJobfieldName(db.getInt("WORKJOB_FIELD"), this.resume.getIdLanguage()));
				this.lastExp.setSubfieldName(getSubfieldName(db.getInt("WORKJOB_FIELD"), db.getInt("WORKSUB_FIELD"), this.resume.getIdLanguage()));
				this.lastExp.setSalaryMin(getSalaryPerMonth(db.getInt("SALARY_LAST"),db.getInt("SALARY_PER"),db.getInt("ID_CURRENCY")));
				if(this.lastExp.getSalaryMin()<50000)
				{
					this.lastExp.setSalaryMax(this.lastExp.getSalaryMin()+10000);
				}
				else
				{
					this.lastExp.setSalaryMax(this.lastExp.getSalaryMin()+20000);
				}
				String isWorking=getStr(db.getString("WORKING_STATUS")).toUpperCase().trim();
				if(isWorking.equals("TRUE"))
				{
					this.lastExp.setWorking(true);
				}
				else
				{
					this.lastExp.setWorking(false);					
				}
				this.lastExp.setWorkEndYear(db.getInt("WORK_END_YEAR"));
				this.lastExp.setIndustry(getIndustry(idJsk, idResume, db.getInt("ID")));
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
	}
	
	private String getIndustry(int idJsk, int idResume, int idWork) 
	{
		String result="";
		String SQL = " SELECT " + 
						"	ID_INDUSTRY " + 
						" FROM " + 
						"	INTER_EXPERIENCE_INDUSTRY " + 
						" WHERE " + 
						"	ID_JSK=? AND ID_RESUME=? AND ID_WORK=? AND ID_INDUSTRY>0";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
			db.executeQuery();
			while (db.next()) 
			{
				if (result.equals("")) 
				{
					result = db.getString("ID_INDUSTRY");
				} 
				else
				{
					result = result + "," + db.getString("ID_INDUSTRY");
				}
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			db.close();
		}
		return result;
	}
	
	private void initLastEducation()
	{
		DBManager db = null;
		try
		{
			String SQL = "SELECT "
						+ "		A.ID,A.ID_DEGREE,A.ID_FAC_MAJOR,A.ID_MAJOR,TO_CHAR(FINISH_DATE,'YYYY') AS FINISH_YEAR "
						+ "FROM "
						+ "		INTER_EDUCATION A LEFT JOIN INTER_DEGREE B "
						+ "ON "
						+ "		A.ID_DEGREE=B.ID_DEGREE "
						+ "WHERE "
						+ "		ID_JSK=? AND A.ID_RESUME=? AND "
						+ "		A.DELETE_STATUS<>'TRUE' AND "
						+ "		A.ID_DEGREE=B.ID_DEGREE "
						+ "ORDER BY "
						+ "		B.DEGREE_ORDER,A.ID DESC";
			//System.out.println("initLastEducation=>"+SQL);
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			if(db.next())
			{
				this.lastEdu=new Education();
				this.lastEdu.setIdJsk(this.idJsk);
				this.lastEdu.setIdResume(this.idResume);
				this.lastEdu.setIdEdu(db.getInt("ID"));
				this.lastEdu.setIdDegree(db.getInt("ID_DEGREE"));
				this.lastEdu.setIdFaculty(db.getInt("ID_FAC_MAJOR"));
				this.lastEdu.setIdMajor(db.getInt("ID_MAJOR"));
				this.lastEdu.setFinishYear(db.getInt("FINISH_YEAR"));
				this.lastEdu.setDegreeNameTha(getDegreeName(db.getInt("ID_DEGREE"), 38));
				this.lastEdu.setDegreeNameEng(getDegreeName(db.getInt("ID_DEGREE"), 11));
				this.lastEdu.setFacultyName(getFacultName(db.getInt("ID_FAC_MAJOR"), this.resume.getIdLanguage()));
				this.lastEdu.setMajorName(getMajorName(db.getInt("ID_FAC_MAJOR"),db.getInt("ID_MAJOR"), this.resume.getIdLanguage()));
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
	}
	
	private void initTargetJob()
	{
		String SQL=	"SELECT " +
					"	WORK_PERMIT,RELOCATE,TRAVEL,START_JOB,MIN_EXPECTED_SALARY,MAX_EXPECTED_SALARY," +
					"	EXPECTED_SALARY_PER,SALARY_CURRENCY,START_JOB_NOTICE " +
					"FROM " +
					"	INTER_TARGETJOB_EXTENSION " +
					"WHERE " +
					"	ID_JSK=? AND ID_RESUME=? ";
		//System.out.println("initTargetJob=>"+SQL);
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
				this.target=new TargetJob();
				this.target.setSalaryLess(getSalaryPerMonth(db.getInt("MIN_EXPECTED_SALARY"),db.getInt("EXPECTED_SALARY_PER"),db.getInt("SALARY_CURRENCY")));
				this.target.setSalaryMost(getSalaryPerMonth(db.getInt("MAX_EXPECTED_SALARY"),db.getInt("EXPECTED_SALARY_PER"),db.getInt("SALARY_CURRENCY")));
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
	
	private String getJobTypeSQL()
	{	
		String result="";	
		String SQL="SELECT JOBTYPE FROM INTER_JOBTYPE WHERE ID_JSK=? AND ID_RESUME = ?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while(db.next())
			{
				if(result.equals(""))
				{
					result+="(WORK_TYPE like '%"+db.getString("JOBTYPE")+"%')";
				}else
				{
					result+=" OR (WORK_TYPE like '%"+db.getString("JOBTYPE")+"%')"; 	
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
	
	private float getSalaryScore(int idEmp, int idPosition)
	{
		if(this.target==null) return 0.0f;
		float result=0.0f;
		int min=this.target.getSalaryLess();
		int max=this.target.getSalaryMost();
		String salSql="";
		if(min!=-1 && max!=-1)
		{
			//for position has exact salary_less and salary_most
			String normalCase=	"(SALARY_MOST_MATCH<>999999999) AND "
							+	"( "
							+	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_MOST_MATCH) OR"
							+	"	("+min+"<=SALARY_MOST_MATCH AND "+max+">=SALARY_MOST_MATCH) OR"
							+	"	("+min+">=SALARY_LESS AND "+max+"<=SALARY_MOST_MATCH) OR"
							+	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_MOST_MATCH)"
							+ 	")";

			//form position has salary_less and above
			String aboveCase=	"(SALARY_MOST_MATCH=999999999) AND "
							+	"( "
							+ 	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_LESS) OR"
							+ 	"	("+min+"<=SALARY_LESS*1.5 AND "+max+">=SALARY_LESS*1.5) OR"
							+ 	"	("+min+">=SALARY_LESS AND "+max+"<=SALARY_LESS*1.5) OR"
							+ 	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_LESS*1.5)"
							+ 	")";
			
			salSql=" AND (("+normalCase+") OR ("+aboveCase+"))";
			String SQL="SELECT ID_EMP,ID_POSITION FROM POSITION WHERE ID_EMP=? AND ID_POSITION =? "+salSql;
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1,idEmp);
				db.setInt(2,idPosition);
				db.executeQuery();
				if(db.next())
				{
					result=2.5f;
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
	
	private float getJobTypeScore(int idEmp, int idPosition)
	{	
		float result=0.0f;
		String jtSQL=getJobTypeSQL();
		if(jtSQL.equals(""))
		{
			return 2.5f;
		}
		else
		{
			jtSQL=" AND ("+jtSQL+")";
			String SQL="SELECT ID_EMP,ID_POSITION FROM POSITION WHERE ID_EMP=? AND ID_POSITION =? "+jtSQL;
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1,idEmp);
				db.setInt(2,idPosition);
				db.executeQuery();
				if(db.next())
				{
					result=2.5f;
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
	
	private String getTargetIndustry()
	{	
		String result="";
		String SQL="SELECT ID_INDUSTRY FROM INTER_INDUSTRY WHERE   ID_JSK=?  AND ID_RESUME = ?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while(db.next())
			{
				if(result.equals(""))
				{
					result=db.getString("ID_INDUSTRY");
				}
				else
				{
					result+=","+db.getString("ID_INDUSTRY");	
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
	
	private float getTargetIndustryScore(int idEmp)
	{	
		float result=0.0f;
		String targetIndus=getTargetIndustry();
		if(targetIndus.equals(""))
		{
			return 0.0f;
		}
		else
		{
			targetIndus=" AND ID_INDUSTRY IN ("+targetIndus+")";
		}
		String SQL="SELECT ID_EMP FROM EMP_INDUSTRY WHERE ID_EMP=? "+targetIndus;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.executeQuery();
			if(db.next())
			{
				result=2.5f;
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
	
	private String getTargetJobfieldSql()
	{	
		String result="";
		String SQL=" SELECT ID_JOBFIELD ,ID_SUBFIELD FROM INTER_TARGETJOB WHERE  ID_JSK=? AND ID_RESUME = ? AND ID_JOBFIELD>0 ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while(db.next())
			{
				String sub="";
				if(db.getInt("ID_SUBFIELD")>0)
				{
					sub=" AND WORKSUB_FIELD="+db.getInt("ID_SUBFIELD");
				}
				if(result=="")
				{
					result=" (WORKJOB_FIELD="+db.getInt("ID_JOBFIELD")+" "+sub+")";
				}
				else
				{
					result=" (WORKJOB_FIELD="+db.getInt("ID_JOBFIELD")+" "+sub+")";
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
	
	private float getTargetJobFieldScore(int idEmp, int idPosition)
	{	
		float result=0.0f;
		String targetJf=getTargetJobfieldSql();
		if(targetJf.equals(""))
		{
			return 0;
		}
		else
		{
			targetJf=" AND ("+targetJf+")";
		}
		String SQL="SELECT ID_EMP,ID_POSITION FROM POSITION_WORKEQUI WHERE ID_EMP=? AND ID_POSITION =? "+targetJf;
		//System.out.println("SELECT ID_EMP,ID_POSITION FROM POSITION_WORKEQUI WHERE ID_EMP="+idEmp+" AND ID_POSITION ="+idPosition+" "+targetJf);
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			if(db.next())
			{
				result=2.5f;
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
	
	private void calculateExpYear()
	{
		if(this.resume==null) return;
		if(this.resume.getExpYear()==0)
		{	
			if(this.lastEdu!=null)
			{
				if((this.lastEdu.getFinishYear()<this.curYear) && this.lastEdu.getFinishYear()!=-1)
				{
					if(this.lastEdu.getFinishYear()<=this.curYear-10)
					{
						this.resume.setExpYear(10);
					}
					else
					{ 
						this.resume.setExpYear(this.curYear-this.lastEdu.getFinishYear());
					}
				}
			}
		}
		else
		{
			if(this.lastExp!=null)
			{
				if(this.lastExp.getWorkEndYear()!=-1 && this.lastExp.getWorkEndYear()<=this.curYear)
				{
					if(this.lastExp.getWorkEndYear()<=this.curYear-10)
					{
						this.resume.setExpYear(10);
					}
					else
					{ 
						this.resume.setExpYear(this.resume.getExpYear()+(this.curYear-this.lastExp.getWorkEndYear()));
					}				
				}
			}
		}
	}
	
	private String getSalarySQL()
	{
		if(this.lastExp==null) return "";		
		String result="";
		int min=this.lastExp.getSalaryMin();
		int max=this.lastExp.getSalaryMax();
		if(min!=-1 && max!=-1)
		{
			//for position has exact salary_less and salary_most
			String normalCase=	"(SALARY_MOST_MATCH<>999999999) AND "
							+	"( "
							+	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_MOST_MATCH) OR"
							+	"	("+min+"<=SALARY_MOST_MATCH AND "+max+">=SALARY_MOST_MATCH) OR"
							+	"	("+min+">=SALARY_LESS AND "+max+"<=SALARY_MOST_MATCH) OR"
							+	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_MOST_MATCH)"
							+ 	")";

			//form position has salary_less and above
			String aboveCase=	"(SALARY_MOST_MATCH=999999999) AND "
							+	"( "
							+ 	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_LESS) OR"
							+ 	"	("+min+"<=SALARY_LESS*1.5 AND "+max+">=SALARY_LESS*1.5) OR"
							+ 	"	("+min+">=SALARY_LESS AND "+max+"<=SALARY_LESS*1.5) OR"
							+ 	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_LESS*1.5)"
							+ 	")";
			
			result="(("+normalCase+") OR ("+aboveCase+") )";
		}
		return result;
	}
	
	
	private String getExpSQL()
	{
		String result="";
		if(this.resume==null) return "";
		String salarySQL=getSalarySQL();
		if(salarySQL!="")	
		{
			if(this.resume.getExpYear()>0)
			{
			//	result+=" ("+salarySQL+") AND (((exp_less<="+this.resume.getExpYear()+" AND exp_most>="+this.resume.getExpYear()+") AND SALARY_LESS=0 AND SALARY_MOST_MATCH=0 ) OR (exp_less<="+this.resume.getExpYear()+" AND exp_most>="+this.resume.getExpYear()+")) ";
				result+=" ("+salarySQL+") OR ((exp_less<="+this.resume.getExpYear()+" AND exp_most>="+this.resume.getExpYear()+") AND SALARY_LESS=0 AND SALARY_MOST_MATCH=0 ) ";
			}
			else
			{	
				//result+=" ("+salarySQL+") AND ((exp_less=0 AND SALARY_LESS=0 AND SALARY_MOST_MATCH=0) OR exp_less=0) ";
				result+=" ("+salarySQL+") OR (exp_less=0 AND SALARY_LESS=0 AND SALARY_MOST_MATCH=0) ";
			}
		}
		else//jsk dont's have salary
		{
			if(this.resume.getExpYear()>0)
			{	
				result+=" (exp_less<="+this.resume.getExpYear()+" AND exp_most>="+this.resume.getExpYear()+") ";
			}
			else
			{	
				result+=" (exp_less=0) ";
			}
		}
		
		return result;
	}
	
	private String getIndustrySQL()
	{
		if(this.lastExp==null) return "";
		String result="";
		if(!this.lastExp.getIndustry().equals(""))
		{
			result = "((POSITION.ID_EMP, POSITION.ID_POSITION) IN (SELECT ID_EMP,ID_POSITION FROM POSITION_INDUSTRY WHERE   ID_INDUSTRY IN (" + this.lastExp.getIndustry() + ")) OR";
			result += " (POSITION.ID_EMP, POSITION.ID_POSITION) NOT IN (SELECT ID_EMP,ID_POSITION FROM POSITION_INDUSTRY)) ";
		}
		return result;
	}
	
	
	private String getEduSQL()
	{
		if(this.lastEdu==null) return "";
		String result="";
		String jobFamilySQL=getJobFamilySQL(this.lastEdu.getIdFaculty(),this.lastEdu.getIdMajor());
		if(!jobFamilySQL.equals(""))
		{
			result=getJobFamilySQL(this.lastEdu.getIdFaculty(),this.lastEdu.getIdMajor());
		}
		else
		{
			if(this.lastEdu.getIdFaculty()>0)
			{
				String majorSql="";
				if(this.lastEdu.getIdMajor()>0)
				{
					majorSql+=" AND ID_MAJOR="+this.lastEdu.getIdMajor();
				}
				result="SELECT ID_EMP, ID_POSITION FROM POSITION_MAJOR WHERE ID_FACULTY="+this.lastEdu.getIdFaculty()+" "+majorSql+"";
			}
		}
		return result;
	}
	
	private String getJobFieldSQL()
	{
//		if(this.lastExp==null) return "";
		String result="";
		if(this.lastExp!=null)
		{
			if(this.lastExp.getIdJobField()>0)//HAS EXP
			{
				String subfieldSql="";
				if(this.lastExp.getIdSubField()>0)
				{
					subfieldSql+=" AND WORKSUB_FIELD="+this.lastExp.getIdSubField() ;
				}
				result="SELECT ID_EMP, ID_POSITION FROM POSITION_WORKEQUI WHERE WORKJOB_FIELD="+this.lastExp.getIdJobField()+" "+subfieldSql+"";
			}
		}
		else //NO EXP, check faculty and major
		{
			result=getEduSQL();
		}
		return result;
	}
	
	private float getEduScore(int idEmp, int idPosition)
	{
		float result=0.0f;
		float scroe=0.0f;
		if(this.lastEdu!=null)
		{
			String SQL="SELECT ID_EMP,ID_POSITION,ID_MAJOR FROM POSITION_MAJOR WHERE ID_EMP=? AND ID_POSITION=? AND ID_FACULTY=?";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idEmp);
				db.setInt(2, idPosition);
				db.setInt(3, this.lastEdu.getIdFaculty());
				db.executeQuery();
				while(db.next())
				{
					scroe=0.5f;
					if(this.lastEdu.getIdMajor()==db.getInt("ID_MAJOR"))
					{
						scroe=1f;
						break;
					}
				}
				if(this.resume.getExpYear()==0)
				{
					result=10;
				}
				else if(this.resume.getExpYear()==1)
				{
					result=9;						
				}
				else if(this.resume.getExpYear()==2)
				{
					result=8;
				}
				else if(this.resume.getExpYear()==3)
				{
					result=7;
				}
				else if(this.resume.getExpYear()==4)
				{
					result=6;
				}
				else if(this.resume.getExpYear()==5)
				{
					result=5;
				}
				else if(this.resume.getExpYear()==6)
				{
					result=4;
				}
				else if(this.resume.getExpYear()==7)
				{
					result=3;
				}
				else if(this.resume.getExpYear()==8)
				{
					result=2;
				}
				else if(this.resume.getExpYear()>=9)
				{
					result=1;
				}
				result=result*scroe;
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
	
	private float getExpYearScore(int idEmp, int idPosition)
	{
		float result=0.0f;
		String SQL="SELECT * FROM POSITION WHERE EXP_LESS <= ? AND EXP_MOST >= ? AND ID_EMP=? AND ID_POSITION=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.resume.getExpYear());
			db.setInt(2, this.resume.getExpYear());
			db.setInt(3, idEmp);
			db.setInt(4, idPosition);
			db.executeQuery();
			if(db.next())
			{
				result=1f;
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
	
	private float getJobfieldScore(int idEmp, int idPosition)
	{
		if(this.lastExp==null) return 0.0f;
		float result=0.0f;
		String SQL="SELECT ID_EMP,ID_POSITION ,WORKSUB_FIELD FROM POSITION_WORKEQUI WHERE ID_EMP=? AND ID_POSITION=? AND WORKJOB_FIELD=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, this.lastExp.getIdJobField());
			db.executeQuery();
			while(db.next())
			{
				result=0.5f;
				if(this.lastExp.getIdSubField()==db.getInt("WORKSUB_FIELD"))
				{
					result=1f;
					break;
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
	
	private float getSalaryLastScore(int idEmp, int idPosition)
	{
		float result=0.0f;
		if(this.lastExp==null) return 0.0f;
		int min=this.lastExp.getSalaryMin();
		int max=this.lastExp.getSalaryMax();
		String salSql="";
		if(min!=-1 && max!=-1)
		{
			//for position has exact salary_less and SALARY_MOST_MATCH
			String normalCase=	"(SALARY_MOST_MATCH<>999999999) AND "
							+	"( "
							+	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_MOST_MATCH) OR"
							+	"	("+min+"<=SALARY_MOST_MATCH AND "+max+">=SALARY_MOST_MATCH) OR"
							+	"	("+min+">=SALARY_LESS AND "+max+"<=SALARY_MOST_MATCH) OR"
							+	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_MOST_MATCH)"
							+ 	")";

			//form position has salary_less and above
			String aboveCase=	"(SALARY_MOST_MATCH=999999999) AND "
							+	"( "
							+ 	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_LESS) OR"
							+ 	"	("+min+"<=SALARY_LESS*1.5 AND "+max+">=SALARY_LESS*1.5) OR"
							+ 	"	("+min+">=SALARY_LESS AND "+max+"<=SALARY_LESS*1.5) OR"
							+ 	"	("+min+"<=SALARY_LESS AND "+max+">=SALARY_LESS*1.5)"
							+ 	")";
			
			salSql=" AND (("+normalCase+") OR ("+aboveCase+"))";
			String SQL="SELECT ID_EMP,ID_POSITION FROM POSITION WHERE ID_EMP=? AND ID_POSITION =? "+salSql;
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1,idEmp);
				db.setInt(2,idPosition);
				db.executeQuery();
				if(db.next())
				{
					result=1f;
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
	private float getExpScore(int idEmp, int idPosition)
	{
		float result=0.0f;
		float jfScore=getJobfieldScore(idEmp, idPosition);
		float expYearScore=getExpYearScore(idEmp, idPosition);
		float salaryScore=getSalaryLastScore(idEmp, idPosition);
		float score=(jfScore+expYearScore+salaryScore)/3.0f;
		if(this.lastExp!=null)
		{
			if(this.resume.getExpYear()==0)
			{
				result=0;						
			}
			else if(this.resume.getExpYear()==1)
			{
				result=1;						
			}
			else if(this.resume.getExpYear()==2)
			{
				result=2;
			}
			else if(this.resume.getExpYear()==3)
			{
				result=3;
			}
			else if(this.resume.getExpYear()==4)
			{
				result=4;
			}
			else if(this.resume.getExpYear()==5)
			{
				result=5;
			}
			else if(this.resume.getExpYear()==6)
			{
				result=6;
			}
			else if(this.resume.getExpYear()==7)
			{
				result=7;
			}
			else if(this.resume.getExpYear()==8)
			{
				result=8;
			}
			else if(this.resume.getExpYear()>=9)
			{
				result=9;
			}
			result=result*score;
		}
		return result;
	}
	
	
	private float getLocationScore(int idEmp, int idPosition)
	{
		float result=1f;
		if(this.resume.getLocationExclude().equals(""))
		{
			return result;
		}
		String SQL="SELECT "
				+ "		ID_EMP, ID_POSITION "
				+ "	FROM "
				+ "		POSITION_LOCATION_MATCH"
				+ "	WHERE "
				+ "		ID_COUNTRY=216  AND ID_EMP=? AND ID_POSITION=? AND "
				+ "		ID_STATE IN("+this.resume.getLocationExclude()+")";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idEmp);
			db.setInt(2,idPosition);
			db.executeQuery();
			if(db.next())
			{
				String list=getLocationJob(idEmp, idPosition);
				//System.out.println(idEmp+":"+idPosition+ "  list getLocationJob>>>"+list+"  list indexOf>>"+list.indexOf("77"));
				result=10;
				if(!Util.getStr(this.resume.getCityList()).equals("") && list.indexOf("77")>-1)
				{
					result=10+getCityScore(idEmp,idPosition);
					//System.out.println(idEmp+":"+idPosition+"  result>>>"+result);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(SQL+"   idEmp:"+idEmp+"   idPosition:"+idPosition);
			e.printStackTrace();
		} 
		finally
		{
			db.close();
		}
		
		return result;
	}
	
	private float getCityScore(int idEmp, int idPosition)
	{
		float result=-5f;
		String[] cities = this.resume.getCityList().split(",");
		if(cities.length>0)
		{
			for (int i = 0; i < cities.length; i++) 
			{
				if(cities[i].equals("0"))
				{
					return 0;
				}
			}
		}
		else
		{
			return 0;
		}
		String SQL="SELECT "
				+ "		ID_EMP, ID_POSITION "
				+ "	FROM "
				+ "		POSITION_LOCATION_MATCH"
				+ "	WHERE "
				+ "		ID_COUNTRY=216  AND ID_EMP=? AND ID_POSITION=? AND ID_STATE=77 "
				+ "		AND ID_CITY IN ("+this.resume.getCityList()+")";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idEmp);
			db.setInt(2,idPosition);
			db.executeQuery();
			if(db.next())
			{
				result=0f;
			}
		}
		catch(Exception e)
		{
			System.out.println("SELECT "
				+ "		ID_EMP, ID_POSITION "
				+ "	FROM "
				+ "		POSITION_LOCATION"
				+ "	WHERE "
				+ "		ID_COUNTRY=216  AND ID_EMP="+idEmp+" AND ID_POSITION="+idPosition+" AND ID_STATE=77 "
				+ "		AND ID_CITY IN ("+this.resume.getCityList()+")");
			e.printStackTrace();
		} 
		finally
		{
			db.close();
		}
		
		return result;
	}
	
	private String getLocatioExclude()
	{
		String result="";
		Set<Integer> list = new HashSet<Integer>();
		if (this.resume.getIdCountry()==216 && this.resume.getIdState()!=-1)
		{
			list.add(this.resume.getIdState());
		}
		Set<Integer>localTarget=getTargetLocationExclude();
		// HashSet=list unique
		list.addAll(localTarget); 
		List<Integer> lists = new ArrayList<Integer>(list);
		Collections.sort(lists);
		result=lists.toString().replaceAll("\\[", "").replaceAll("\\]","");
		return result;
	}
	
	private String getCityList()
	{
		String result="";
		Set<Integer> list = new HashSet<Integer>();
		//list.addAll(getCity());
		list.addAll(getCityTarget()); 
		// HashSet=list unique
		List<Integer> lists = new ArrayList<Integer>(list);
		Collections.sort(lists);
		result=lists.toString().replaceAll("\\[", "").replaceAll("\\]","");
		return result;
	}
	
	private float getTargerScore(int idEmp, int idPosition)
	{
		float result=0.0f;
		float jtScore=getJobTypeScore(idEmp,idPosition);
		float IndusScore=getTargetIndustryScore(idEmp);
		float jfScore=getTargetJobFieldScore(idEmp,idPosition);
		float salaryScore=getSalaryScore(idEmp,idPosition);
		result=jtScore+IndusScore+jfScore+salaryScore;
		//System.out.println("jtScore= "+jtScore+"  IndusScore= "+IndusScore+"  jfScore= "+jfScore+"  salaryScore= "+salaryScore);
		return result;
	}
	public List<Position> getSuperMatch()
	{
		if(this.resume==null) return null;
		List<Position> result = new ArrayList<Position>();	
		String ts="";
		long mainStart=new java.util.Date().getTime();
		DBManager db = null;
		String  SQL="SELECT "
				+	"	DISTINCT POSITION.ID_POSITION,EMPLOYER.ID_EMP,"
				+ 	"	EMPLOYER.COMPANY_NAME,EMPLOYER.COMPANY_NAME_THAI,"
				+ 	"	EMPLOYER.PREMIUM,E_D2, EXP_LESS, EXP_MOST, "
				+ 	"	TYPE_,SALARY_LESS,SALARY_MOST,POSITION_NAME,DEGREE,SALARY_PRESENT,"
				+ 	"	EMPLOYER.P_LOGO,POSITION.SHOW_SALARY,POST_DATE ,SEX,AGE_MIN,AGE_MAX , "
				+ 	"	TRUNC(ADD_MONTHS( TO_DATE(EXPIRE_DATE,'YYYYMMDD'), "
				+ 	"	TRUNC(MONTHS_BETWEEN(SYSDATE, TO_DATE(EXPIRE_DATE,'YYYYMMDD'))))-SYSDATE)+1 AS EXPIREDAY_DIFF ,"
				+ 	"	TRUNC(SYSDATE - ADD_MONTHS( POSITION.TIMESTAMP, TRUNC(MONTHS_BETWEEN(SYSDATE, POSITION.TIMESTAMP)))) AS POSTDAY_DIFF , "
				+ 	"	TRUNC((SYSDATE- POSITION.TIMESTAMP)*24) as POSTHOUR_DIFF, "
        		+ 	"	TRUNC((SYSDATE- POSITION.TIMESTAMP)*24*60) as POSTMINUTE_DIFF "
				+ 	" FROM "
				+ 	"	POSITION, EMPLOYER "
				+ 	" WHERE "
				+ 	"	(POST_DATE <= TO_CHAR(SYSDATE,'YYYYMMDD')) AND "
				+ 	"	(E_D2 >= TO_CHAR(SYSDATE,'YYYYMMDD')) AND "
				+ 	"	(EMPLOYER.FLAG='1') AND "
				+ 	"	(POSITION.FLAG='1') AND (POSITION.JOB_IN_COUNTRY=216) AND "
				+ 	"	(POSITION.ID_EMP = EMPLOYER.ID_EMP) "+ts;

		/*String salarySQL=getSalarySQL();
		if(!salarySQL.equals(""))
		{
			SQL+=" AND ("+salarySQL+") ";
		}
		 */
		String expSQL=getExpSQL();
		if(!expSQL.equals(""))
		{
			SQL+=" AND ("+expSQL+") ";			
		}
		
		if(this.resume.getAge()!=-1)
		{
			SQL+=" AND (("+this.resume.getAge()+" >= AGE_MIN AND "+this.resume.getAge()+" <= AGE_MAX ) OR (AGE_MIN=-1 AND AGE_MAX=-1) OR (AGE_MIN=0 AND AGE_MAX=0) OR  (AGE_MIN IS NULL AND AGE_MAX IS NULL))";
		}
	
		if(this.resume.getSex()!=-1)
		{
			if(this.resume.getSex()==2) //if female
			{
			    SQL+=" AND (SEX<>'1') "; //Exclude Male
			}
			else if(this.resume.getSex()==1) //If Male
			{
				SQL+=" AND (SEX<>'2') "; //Exclude Female
			}
		}
		else 
		{
			SQL+=" AND (SEX<>1 AND SEX<>2) "; //Exclude Female,Male
		}
		
		String industrailLocaSql="";
		if(this.resume.getHasIndustrial()==true)
		{
			industrailLocaSql="  OR (ID_EMP, ID_POSITION ) IN (SELECT ID_EMP, ID_POSITION FROM POSITION_INDUSTRIAL )";
		}
		
		String locationList =this.resume.getLocationExclude();
		if(!locationList.equals(""))
		{
			SQL+=" AND (POSITION.ID_EMP,POSITION.ID_POSITION) IN "
					+ "	(	SELECT "
					+ "				ID_EMP, ID_POSITION "
					+ "			FROM POSITION_LOCATION_MATCH "
					+ "		WHERE "
					+ "			ID_COUNTRY=216 AND ID_STATE IN ("+locationList+") "
					+ 		industrailLocaSql
					+ " )";
		}
		else
		{
			if(this.resume.getHasIndustrial()==true)
			{
				SQL+=" AND (POSITION.ID_EMP,POSITION.ID_POSITION) IN "
						+ "	(	SELECT "
						+ "				ID_EMP, ID_POSITION "
						+ "			FROM POSITION_INDUSTRIAL "
						+ " )";
			}
		}
		String indusSQL=getIndustrySQL();
		if(!indusSQL.equals(""))
		{
			SQL+=" AND ("+indusSQL+") ";			
		}
		

		
		String jobFieldSQL=getJobFieldSQL();
		if(!jobFieldSQL.equals(""))
		{
			SQL+=" AND (POSITION.ID_EMP,POSITION.ID_POSITION) IN ("+jobFieldSQL+")";
		}
		
		if(this.lastEdu!=null)
		{
			if(this.lastEdu.getIdDegree()>0)
			{	
				if(this.lastEdu.getIdDegree()==1)
				{
					SQL+=" AND (DEGREE=1) ";
				}
				else if(this.lastEdu.getIdDegree()==2)
				{	 	
					SQL+=" AND (DEGREE IN(2,6,8,9)) ";
				}
				else if(this.lastEdu.getIdDegree()==3)
				{
					SQL+=" AND (DEGREE IN(3,6,8,11,13)) ";
				}
				else if(this.lastEdu.getIdDegree()==4)
				{
					SQL+=" AND (DEGREE IN (4,9,10,12,-1)) ";
				}
				else if(this.lastEdu.getIdDegree()==5)
				{
					SQL+=" AND (DEGREE IN (5,10,12,-1)) ";
				}
				else if(this.lastEdu.getIdDegree()==7)
				{
					SQL+=" AND (DEGREE IN(7,8,11)) ";
				}
				else if(this.lastEdu.getIdDegree()==14)
				{
					SQL+=" AND (DEGREE IN(9,10,12,14,-1)) ";
				}	
				else if(this.lastEdu.getIdDegree()==15)
				{
					SQL+=" AND (DEGREE IN(9,10,12,15,-1)) ";
				}
			}
		}
		SQL+=" ORDER BY POSITION.POST_DATE DESC";
		System.out.println("SQL: "+SQL);
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			while (db.next())
			{
				Position pos=new Position();
				pos.setIdEmp(db.getInt("ID_EMP"));
				pos.setIdPosition(db.getInt("ID_POSITION"));
				pos.setPositionName(db.getString("POSITION_NAME"));
				pos.setNameEng(db.getString("COMPANY_NAME"));
				pos.setNameTha(db.getString("COMPANY_NAME_THAI"));
				pos.setPremium(db.getInt("PREMIUM"));
				pos.setExpLess(db.getInt("EXP_LESS"));
				pos.setExpMost(db.getInt("EXP_MOST"));
				pos.setExperience(getExpStr(db.getInt("EXP_LESS"), db.getInt("EXP_MOST")));
				pos.setType(db.getString("TYPE_"));
				pos.setSalaryLess(db.getInt("SALARY_LESS"));
				pos.setSalaryMost(db.getInt("SALARY_MOST"));
				if(db.getInt("SHOW_SALARY")==0)
				{
					pos.setSalaryEng(getSalary(db.getInt("SALARY_PRESENT"),0,11));
					pos.setSalaryTha(getSalary(db.getInt("SALARY_PRESENT"),0,38));
				}
				else
				{
					pos.setSalaryEng(getSalary(db.getInt("SALARY_LESS"),db.getInt("SALARY_MOST"),11));
					pos.setSalaryTha(getSalary(db.getInt("SALARY_LESS"),db.getInt("SALARY_MOST"),38));
				}
				pos.setDegree(db.getInt("DEGREE"));
				pos.setDegreeEng(getDegreeName(db.getInt("DEGREE"), 11));
				pos.setDegreeTha(getDegreeName(db.getInt("DEGREE"), 38));
				pos.setLogo(db.getString("P_LOGO"));
				
				String districtEN=getDistrict(pos.getIdEmp(),  pos.getIdPosition(), 11);
				String provinceEn="";
				if(districtEN!="")
				{
					Boolean hasBangkok= false;
					provinceEn=getLocationName(pos.getIdEmp(),  pos.getIdPosition(), hasBangkok, 11);
				}
				else
				{
					Boolean hasBangkok= true;
					provinceEn=getLocationName(pos.getIdEmp(),  pos.getIdPosition(), hasBangkok, 11);
				}
				
				String districtTH=getDistrict(pos.getIdEmp(),  pos.getIdPosition(), 38);
				String provinceTH="";
				if(districtTH!="")
				{
					Boolean hasBangkok= false;
					provinceTH=getLocationName(pos.getIdEmp(),  pos.getIdPosition(), hasBangkok, 38);
				}
				else
				{
					Boolean hasBangkok= true;
					provinceTH=getLocationName(pos.getIdEmp(),  pos.getIdPosition(), hasBangkok, 38);
				}
				pos.setLocationEng(districtEN+" "+provinceEn);
				pos.setLocationTha(districtTH+" "+provinceTH); 
				
				java.sql.Date applyDate=getApplyDate(idJsk, db.getInt("ID_EMP"), db.getInt("ID_POSITION"));
				if(applyDate!=null)
				{
					pos.setApply(true);
					pos.setApplyDate(applyDate);
				}
				pos.setEduScore(getEduScore(pos.getIdEmp(),pos.getIdPosition()));
				pos.setExpScore(getExpScore(pos.getIdEmp(),pos.getIdPosition()));
				pos.setTargetScore(getTargerScore(pos.getIdEmp(),pos.getIdPosition()));
				pos.setLocationScore(getLocationScore(pos.getIdEmp(),pos.getIdPosition()));

				result.add(pos);	 
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
		
		if(result.size()>0)
		{
			for(int i=0; i<result.size(); i++)
			{
				for(int j=i+1; j<result.size(); j++)
				{
					if(result.get(i).getTotalScore()<result.get(j).getTotalScore())
					{
						Position tmp=result.get(i);
						result.set(i, result.get(j));
						result.set(j, tmp);
					}
					else if((result.get(i).getTotalScore()==result.get(j).getTotalScore()) && (result.get(i).getSalaryLess()<result.get(j).getSalaryLess()))
					{
						Position tmp=result.get(i);
						result.set(i, result.get(j));
						result.set(j, tmp);
					}
				}
			}
		}
		long mainUsage=new java.util.Date().getTime()-mainStart;
		System.out.println("idJsk: "+idJsk+": Finish\t => Time Usage : "+mainUsage+" ms\n\n");
		return result;
	}
	
	// return list of id_state for jobseeker target location
	private Set<Integer> getTargetLocation()
	{
		Set<Integer>result=new HashSet<Integer>();
		DBManager db = null;
		try
		{
			String SQL = "SELECT ID_STATE FROM INTER_LOCATION WHERE ID_COUNTRY=216 AND ID_JSK=? AND ID_RESUME=?";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while (db.next())
			{
				result.addAll(getListStateInRegions(db.getInt("ID_STATE")));
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
	
	// return list of id_state for jobseeker target location
	private Set<Integer> getTargetLocationExclude()
	{
		Set<Integer>result=new HashSet<Integer>();
		DBManager db = null;
		try
		{
			String SQL = "SELECT ID_STATE FROM INTER_LOCATION WHERE ID_COUNTRY=216 AND ID_JSK=? AND ID_RESUME=?";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while (db.next())
			{
				result.add(db.getInt("ID_STATE"));
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
	
	private Set<Integer> getCityTarget()
	{
		Set<Integer>result=new HashSet<Integer>();
		DBManager db = null;
		try
		{
			String SQL = "	SELECT "
					+ "			ID_CITY "
					+ "		FROM "
					+ "			INTER_LOCATION "
					+ "		WHERE "
					+ "			ID_COUNTRY=216 AND ID_JSK=? AND ID_RESUME=? AND "
					+ "			ID_STATE=77 AND ID_CITY>=0";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while (db.next())
			{
				result.add(db.getInt("ID_CITY"));
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
	
	private Set<Integer> getCity()
	{
		Set<Integer>result=new HashSet<Integer>();
		DBManager db = null;
		try
		{
			String SQL = "	SELECT "
					+ "			ID_CITY "
					+ "		FROM "
					+ "			INTER_RESUME "
					+ "		WHERE "
					+ "			ID_COUNTRY=216 AND ID_JSK=? AND ID_RESUME=? AND "
					+ "			ID_STATE=77 AND ID_CITY>=0";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while (db.next())
			{
				result.add(db.getInt("ID_CITY"));
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
	
	private String getLocationJob(int idEmp, int idPosition)
	{
		String result="";
		DBManager db = null;
		try
		{
			String SQL = "SELECT ID_STATE FROM POSITION_LOCATION_MATCH WHERE ID_EMP=? AND ID_POSITION=?";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			while (db.next())
			{
				if(!result.equals(""))
				{
					result+=",";
				}
				result+=db.getInt("ID_STATE");
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

	private String  getStateTarget()
	{
		String result="";
		DBManager db = null;
		String SQL=" SELECT ID_STATE FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=216";
		Set<Integer> rs = new HashSet<Integer>();
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			while (db.next()) 
			{
				rs.add(db.getInt("ID_STATE"));
			}
			if(rs.size()>0)
			{
				// HashSet=list unique
				List<Integer> lists = new ArrayList<Integer>(rs);
				Collections.sort(lists);
				result=lists.toString().replaceAll("\\[", "").replaceAll("\\]","");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result.trim();
	}
	
	private Boolean  hasIndustrialLocation()
	{
		Boolean result=false;
		DBManager db = null;
		String SQL=" SELECT (1) FROM INTER_INDUSTRIAL_LOCATION WHERE ID_JSK=? AND ID_RESUME=?";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idResume);
			db.executeQuery();
			if (db.next()) 
			{
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}
	
	private String getLocation()
	{
		String result="";
		if(this.resume==null) return "";
		Set<Integer>localAddress=new HashSet<Integer>();
		if (this.resume.getIdCountry()==216 && this.resume.getIdState()!=-1)
		{
			localAddress.addAll(getListStateInRegions(this.resume.getIdState()));
		}
		Set<Integer>localTarget=this.getTargetLocation();
		// HashSet=list unique
		Set<Integer> list = new HashSet<Integer>();
		list.addAll(localAddress);
		list.addAll(localTarget);
		
		List<Integer> lists = new ArrayList<Integer>(list);
		Collections.sort(lists);
		result=lists.toString().replaceAll("\\[", "").replaceAll("\\]","");
		return result;
	}
	
	private String getJobFamilySQL(int faculty,int major)
	{
		String FamilySQL="";
		String jobFieldSql=getFieldFamilySQL(faculty, major);
		String subFieldSql=getSubFieldFamilySQL(faculty, major);
		if(subFieldSql!="")
		{
			FamilySQL="SELECT ID_EMP, ID_POSITION FROM POSITION_WORKEQUI WHERE (WORKJOB_FIELD,WORKSUB_FIELD) IN ("+subFieldSql+")";
		}
		else if(jobFieldSql!="")
		{
			FamilySQL="SELECT ID_EMP, ID_POSITION FROM POSITION_WORKEQUI WHERE WORKJOB_FIELD IN ("+jobFieldSql+")";
		}
		return FamilySQL.trim();
	}
	
	private String getFieldFamilySQL(int idFac, int idMajor) 
	{
		String FamilySQL = "";
		String SQL = "SELECT * FROM INTER_GROUP_SUBFIELD_EDUCATION WHERE ID_FACULTY=? AND ID_MAJOR=? AND ID_GROUP_SUBFIELD=0 ";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idFac);
			db.setInt(2, idMajor);
			db.executeQuery();
			while (db.next()) 
			{
				if (db.getInt("ID_FIELD") > 0) 
				{
					if (FamilySQL.equals(""))
					{
						FamilySQL =""+db.getInt("ID_FIELD") ;
					} else 
					{
						FamilySQL = FamilySQL + " , "+db.getInt("ID_FIELD");
					}
				}
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

		return FamilySQL.trim();
	}

	private  String getSubFieldFamilySQL(int idFac, int idMajor) 
	{
		String FamilySQL = "";
		String SQL = "SELECT * FROM INTER_GROUP_SUBFIELD_EDUCATION WHERE ID_FACULTY=? AND ID_MAJOR=? AND ID_GROUP_SUBFIELD>0";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idFac);
			db.setInt(2, idMajor);
			db.executeQuery();
			while (db.next()) 
			{
				if (FamilySQL == "") 
				{
					FamilySQL =getSubField(db.getInt("ID_FIELD"), db.getInt("ID_GROUP_SUBFIELD"));
				} 
				else 
				{
					FamilySQL = FamilySQL+"," + getSubField(db.getInt("ID_FIELD"), db.getInt("ID_GROUP_SUBFIELD"));
				}
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
		return FamilySQL.trim();
	}
	
	private String getSubField(int idField, int idGroupField) 
	{
		String FamilySQL = "";
		String SQL = "SELECT * FROM INTER_SUBFIELD WHERE ID_FIELD=? AND ID_GROUP_SUBFIELD=?";
		DBManager db = null;
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idField);
			db.setInt(2, idGroupField);
			db.executeQuery();
			while (db.next()) {
				if (FamilySQL.equals("")) 
				{
					FamilySQL = FamilySQL + "(" + db.getInt("ID_FIELD") + "," + db.getInt("ID_SUBFIELD") + ")";
				} 
				else 
				{
					FamilySQL = FamilySQL + ",(" + db.getInt("ID_FIELD") + "," + db.getInt("ID_SUBFIELD") + ")";
				}
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
		return FamilySQL.trim();
	}
	
	private Set<Integer> getListStateInRegions(int idState)
    {
    	Set<Integer> result =Location.getListStateInRegions(idState);
    	return result;
    }
	
	private String getStateName(String LocationIdSet, int idLanguage ) 
	{
		String result = "";
		if(LocationIdSet.equals(""))
		{
			return result;
		}
		String SQL= " SELECT  A.ID_STATE, A.STATE_NAME AS STATE_NAME_EN, B.STATE_NAME AS STATE_NAME_TH"+
				 " FROM   INTER_STATE_LANGUAGE A, INTER_STATE_LANGUAGE B"+
				 " WHERE   A.ID_STATE IN ("+LocationIdSet+")"+
				 " AND A.ID_STATE=B.ID_STATE"+
				 " AND A.ID_LANGUAGE=11"+
				 " AND B.ID_LANGUAGE=38"+
				 " AND A.ID_COUNTRY=216"+
				 " AND B.ID_COUNTRY=216 ";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			while(db.next())
			{
				if(!result.equals(""))
					result+=", ";
				if(idLanguage==38)
				{
					result+=db.getString("STATE_NAME_TH").trim();
				}
				else
				{
					result+=db.getString("STATE_NAME_EN").trim();
				}
			}
		}catch(Exception e)
		{
			System.out.println(" SELECT  A.ID_STATE, A.STATE_NAME AS STATE_NAME_EN, B.STATE_NAME AS STATE_NAME_TH"+
				 " FROM   INTER_STATE_LANGUAGE A, INTER_STATE_LANGUAGE B"+
				 " WHERE   A.ID_STATE IN ("+LocationIdSet+")"+
				 " AND A.ID_STATE=B.ID_STATE"+
				 " AND A.ID_LANGUAGE=11"+
				 " AND B.ID_LANGUAGE=38"+
				" AND A.ID_COUNTRY=216"+
				" AND B.ID_COUNTRY=216");
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result;
	}

	public  String getDistrict(int idEmp,int idPosition,int idLang)
	{
		String result="";
		String SQL=	"SELECT " +
					"	DISTINCT(CITY_NAME) AS DISTRICT " +
					"FROM " +
					"	POSITION_LOCATION A,INTER_CITY_LANGUAGE B " +
					"WHERE " +
					"	(A.ID_COUNTRY=216) AND (A.ID_STATE=77) AND " +
					"	(A.ID_CITY=B.ID_CITY) AND (A.ID_STATE=B.ID_STATE) AND " +
					" 	(A.ID_COUNTRY=B.ID_COUNTRY) AND (ID_EMP=?) AND " +
					" 	(ID_POSITION=?) AND (ID_LANGUAGE=?)";
		DBManager db=null;
		int count=0;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idLang);
			db.executeQuery();
			while(db.next())
			{
				if(count>0)
				{
					result+=", ";
				}
				result+=db.getString("DISTRICT");
				count++;
			}
			result=result.trim();
			if(!result.equals(""))
			{
				if(idLang==11)
				{
					result="Bangkok ("+result+")";
				}
				else
				{
					result="กทม. ("+result+")";
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
	public String getLocationName(int idEmp,int idPosition,boolean hasBangkok,int idLang)
	{
		String result="";
		if(idEmp<=0 || idPosition<=0) return "";
		int metro[]={77,26,35,36,53,54};
		int central[]={4,56,63,42,48,16,74,55,22,58,7,50,2};
		int north[]={10,11,20,44,29,43,38,19,62,71,31,41,23,76,15,45,70};
		int northeast[]={17,28,69,34,68,5,59,65,52,8,75,14,24,27,21,73,33,78,1,25};
		int east[]={12,51,9,6,47,67,61};
		int south[]={46,60,64,18,39,57,66,30,40,13,49,37,72,32};
		
		int all[]={2,4,7,15,22,26,29,35,36,38,43,44,53,54,55,56,58,62,63,70,74,77, //central
				10,11,19,20,23,31,41,45,71, //north
				1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78,//northeast 
				6,9,12,47,51,61,67,//east
				13,18,30,32,37,39,40,46,49,57,60,64,66,72,//south
				16,42,48,50,76};//west
		
		int upCountry[]={2,4,7,15,22,26,29,35,36,38,43,44,53,54,55,56,58,62,63,70,74, //central except bangkok(77)
				1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78, //northeast 
				13,18,30,32,37,39,40,46,49,57,60,64,66,72,//south
				10,11,19,20,23,31,41,45,71, //north
				6,9,12,47,51,61,67, //east
				16,42,48,50,76};//west

		String SQL="SELECT " +
					"	B.ID_STATE,B.STATE_NAME " +
					"FROM " +
					"	POSITION_LOCATION A,INTER_STATE_LANGUAGE B " +
					"WHERE " +
					"	(A.ID_COUNTRY=216) AND " +
					"	(A.ID_STATE=B.ID_STATE) AND " +
					"	(A.ID_COUNTRY=B.ID_COUNTRY) AND " +
					"	(A.ID_EMP=?) AND " +
					"	(A.ID_POSITION=?) AND " +
					"	(A.ID_STATE IS NOT NULL) AND " +
					"	(B.ID_LANGUAGE=?)";
		
		if(hasBangkok==false)
		{
			SQL+=" AND A.ID_STATE<>77 ";
		}
		SQL+=" ORDER BY B.STATE_NAME ";
		
		String provinces="";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idLang);
			db.executeQuery();
			while(db.next())
			{
				provinces+=" "+db.getInt("ID_STATE");
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
		
		provinces=provinces.trim();
		if(!provinces.equals(""))
		{
			String pros[]=provinces.split(" ");
			if(pros.length>0)
			{
				int [] province=new int[pros.length];
				for(int i=0; i<pros.length; i++)
				{
					province[i]=Integer.parseInt(pros[i]);
				}
				if(isSubset(province,all))
				{
					if(idLang==11)
					{
						result=", All Provinces";
					}
					else
					{
						result=", ทุกจังหวัด”";
					}
				}
				else if(isSubset(province,upCountry))
				{
					province=arrayMinus(province, upCountry);
					if(idLang==11)
					{
						result=", Upcountry";
					}
					else
					{
						result=", ต่างจังหวัด";
					}
				}
				else
				{
					//---- check is central----
					if(isSubset(province, central)) 
					{
						province=arrayMinus(province, central);
						if(idLang==11)
						{
							result=", Central Region";
						}
						else
						{
							result=", ภาคกลาง";
						}
					}
					
					//---- check is northeast----
					if(province!=null)
					{
						if(isSubset(province, northeast)) 
						{
							province=arrayMinus(province, northeast);
							if(idLang==11)
							{
								result+=", Northeastern Region";
							}
							else
							{
								result+=", ภาคตะวันออกเฉียงเหนือ";
							}
						}						
					}
					
					//---- check is south----
					if(province!=null)
					{
						if(isSubset(province, south))
						{
							province=arrayMinus(province, south);
							if(idLang==11)
							{
								result+=", Southern Region";
							}
							else
							{
								result+=", ภาคใต้";
							}
						}						
					}
					

					//---- check is north----
					if(province!=null)
					{
						if(isSubset(province, north)) 
						{
							province=arrayMinus(province, north);
							if(idLang==11)
							{
								result+=", Northern Region";
							}
							else
							{
								result+=", ภาคเหนือ";
							}
						}						
					}
					
					//---- check is east----
					if(province!=null)
					{
						if(isSubset(province, east)) 
						{
							province=arrayMinus(province, east);
							if(idLang==11)
							{
								result+=", Eastern Region";
							}
							else
							{
								result+=", ภาคตะวันออก";
							}
						}						
					}
					//---- check is metro----
					if(province!=null)
					{
						if(isSubset(province, metro)) 
						{
							province=arrayMinus(province, metro);
							if(idLang==11)
							{
								result+=", Bangkok and its Vicinity";
							}
							else
							{
								result+=", กรุงเทพและปริมณฑล";
							}
						}						
					}
				}
				//Check remaining province
				if(province!=null)
				{
					String ids="";
					for(int i=0; i<province.length; i++)
					{
						ids+=","+province[i];
					}
					ids=ids.substring(1).trim();
					if(!ids.equals(""))
					{
						String SQX=	"SELECT " +
									"	B.ID_STATE,B.STATE_NAME FROM INTER_STATE A,INTER_STATE_LANGUAGE B " +
									"WHERE " +
									"	A.ID_COUNTRY=B.ID_COUNTRY AND " +
									"	A.ID_STATE=B.ID_STATE AND " +
									"	A.ID_COUNTRY=216 AND B.ID_LANGUAGE=? AND " +
									"	A.ID_STATE IN("+ids+") " +
									"ORDER BY " +
									" B.STATE_NAME";
						
						DBManager dx=null;
						try
						{
							dx=new DBManager();
							dx.createPreparedStatement(SQX);
							dx.setInt(1, idLang);
							dx.executeQuery();
							while(dx.next())
							{
								if(dx.getInt("ID_STATE")==77 && idLang==38)
								{
									result+=", กทม.";
								}
								else
								{
									result+=", "+dx.getString("STATE_NAME");
								}
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							dx.close();
						}
					}
				}
			}
		}
		if(!result.equals(""))
		{
			result=result.substring(1).trim();
		}
		return result;
	}
	
	
	public static boolean isSubset(int [] a, int [] b)
	{
		boolean result=false;
		if(a.length<b.length)
			return false;
		for(int i=0; i<b.length;  i++)
		{	for(int j=0; j<a.length; j++)
			{	result=false;
				if(b[i]==a[j])
				{
					result=true;
					break;
				}
			}
			if(result==false)
				break;
		}
		return result;
	}
	
	public static int[] arrayMinus(int [] a, int [] b)
	{
		int result[]=null;
		if(a.length==0)	return null;
		if(b.length==0) return a;
		int cnt=0;
		String buf="";
		for(int i=0; i<a.length;  i++)
		{
			int found=0;
			for(int j=0; j<b.length; j++)
			{
				if(a[i]==b[j])
				{
					found=1;
					break;
				}
			}
			if(found==0)
			{
				buf+=" "+a[i];
				cnt++;
			}
		}	
		buf=buf.trim();
		if(!buf.equals(""))
		{
			String buffer[]=buf.split(" ");
			if(buffer.length>0)
			{
				result=new int[cnt];
				int idx=0;
				for(int i=0; i<buffer.length; i++)
				{
					if(Integer.parseInt(buffer[i])!=-1)
					{
						result[idx]=Integer.parseInt(buffer[i]);
						idx++;
					}
				}
			}
		}
		return result;
	}
	
	private String getSalary(int salaryLess, int salaryMost,int idLanguage)
	{
		String result="";
		String eng="";
		String tha="";
		if(salaryLess==-1)
		{
			eng="Not Specified";		
			tha="ไม่ระบุ";
		}
		else if(salaryLess==-2)
		{
			eng="Depend on qualifications and experience";
			tha="ขึ้นอยู่กับคุณสมบัติและประสบการณ์";		
		}
		else if(salaryLess==-3)
		{
			eng="Depend on qualifications";
			tha="ขึ้นอยู่กับคุณสมบัติ";
		}
		else if(salaryLess==-4)
		{
			eng="Negotiable";
			tha="สามารถเจรจาต่อรองได้";
		}
		else if(salaryLess==-5)
		{
			eng="N/A";
			tha="ไม่ระบุ";
		}
		else if(salaryLess==-6)
		{
			eng="Competitive";
			tha="Competitive";
		}
		else if(salaryLess==0)
		{
			eng="less than or equal "+salaryMost;
			tha="น้อยกว่าหรือเท่ากับ "+salaryMost;
		}
		else if(salaryMost==999999999)
		{
			eng="More than or equal "+salaryLess;
			tha="มากกว่าหรือเท่ากับ "+salaryLess;
		}
		else if(salaryLess==salaryMost)
		{
			eng=salaryLess+"";
			tha=salaryLess+"";
		}
		else
		{
			eng=salaryLess+" - "+salaryMost;
			tha=salaryLess+" - "+salaryMost;
		}

		if(idLanguage==11)
		{
			result=eng;
		}
		else
		{
			result=tha;
		}
		return result;
	}
	
	public String getDegreeName(int idDegree, int idLanguage)
	{
		String result="";
		String sql ="SELECT DEGREE_NAME FROM ";
		if(idLanguage==11)
		{
			sql+=" DEGREE ";
		}
		else
		{
			sql+=" DEGREE_THAI ";
		}
		sql+="WHERE ID_DEGREE=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idDegree);
			db.executeQuery();
			if(db.next())
			{
				result=db.getString("DEGREE_NAME");
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

	public static String getGender(int idSex,int idLang)
	{
		String result="";
		if(idLang==11)
		{
			switch (idSex) 
			{
				case 0:result = "Male/Female";break;
				case 1:result = "Male";break;
				case 2:result = "Female";break;
				case 4:result = "Not specified";break;
				case 5:result = "Female/Male";break;
				default: result="";
			}
		}
		else if(idLang==38)
		{
			switch (idSex) 
			{
				case 0:result = "ชาย/หญิง";break;
				case 1:result = "ชาย";break;
				case 2:result = "หญิง";break;
				case 4:result = "ไม่ระบุ";break;
				case 5:result = "หญิง/ชาย";break;
				default: result="";
			}
		}
		return result;
	}
	
	public String getJobfieldName(int idField,int idLang)
	{
		String result="";
		String SQL=	"	SELECT "
				+ "			FIELD_NAME "
				+ "		FROM "
				+ "			INTER_JOBFIELD_LANGUAGE "
				+ "		WHERE "
				+ "			ID_LANGUAGE=? AND ID_FIELD=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idLang);
			db.setInt(2, idField);
			db.executeQuery();
			if(db.next())
			{
				result=db.getString("FIELD_NAME");
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
	
	public String getSubfieldName(int idField, int idSubField,int idLang)
	{
		String result="";
		String SQL=	"	SELECT "
				+ "			SUBFIELD_NAME "
				+ "		FROM "
				+ "			INTER_SUBFIELD_LANGUAGE "
				+ "		WHERE "
				+ "			ID_LANGUAGE=? AND ID_FIELD=? AND ID_SUBFIELD=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idLang);
			db.setInt(2, idField);
			db.setInt(3, idSubField);
			db.executeQuery();
			if(db.next())
			{
				result=" : "+db.getString("SUBFIELD_NAME");
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
	
	public String getFacultName(int idFac,int idLang)
	{
		String result="";
		String SQL=	"	SELECT * FROM INTER_FACULTY_LANGUAGE WHERE ID_LANGUAGE=? AND ID_FACULTY=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idLang);
			db.setInt(2, idFac);
			db.executeQuery();
			if(db.next())
			{
				result=db.getString("FACULTY_NAME");
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
	private int  getSummaryExp()
	{	
		int result=0;	
		String SQL= "	SELECT "
				+ "			SY+SMTOSY AS TOTAL"
				+ "		FROM"
				+ "		(  "
				+ "			SELECT "
				+ "				SUM(SUM_YEAR ) AS SY,  FLOOR(SUM(SUM_MONTH )/12) AS SMTOSY "
				+ "			FROM "
				+ "				INTER_EXPERIENCE_SUMMARY  "
				+ "			WHERE "
				+ "				ID_JSK=? AND ID_RESUME=0 "
				+ "		)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, this.idJsk);
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
	
	public String getMajorName(int idFac, int idMajor,int idLang)
	{
		String result="";
		String SQL=	"	SELECT * FROM INTER_MAJOR_LANGUAGE WHERE ID_LANGUAGE=? AND ID_FACULTY=? AND ID_MAJOR=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idLang);
			db.setInt(2, idFac);
			db.setInt(3, idMajor);
			db.executeQuery();
			if(db.next())
			{
				result=" : "+db.getString("MAJOR_NAME");
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
	
	public static String getStr(Object obj)
	{
		return obj!=null?obj.toString():"";
	}
	
	public static String getExpStr(int min ,int max)
	{
		String result="";
		if((max>min) && (min>=0))
		{
			result=min+" - "+max;
		}
		else if((max>=0) && (min<=0))
		{
			result=max+"";
		}
		else if((max<=0) && (min>0))
		{
			result=min+"";
		}
		else if(max==min)
		{
			result=max+"";
		}
		return result;
	}

	public static int getSalaryPerMonth(int total, int saralyPer, int idCurrency)
	{
		int result = 0;
		if (saralyPer == 1)
		{
			result = total / 12;
		}
		else if (saralyPer == 2)
		{
			result = total;
		}
		else if (saralyPer == 3)
		{
			result = total * 4;
		}
		else if (saralyPer == 4)
		{
			result = total * 2;
		}
		else if (saralyPer == 5)
		{
			result = total * 30;
		}
		else if (saralyPer == 6)
		{
			result = total * 176;
		}

		// currency
		if (idCurrency != 140)
		{
			result = convertCurrency(result, idCurrency);
		}
		return result;
	}

	public static int convertCurrency(int total, int idCurrency)
	{
		int result = total;
		String SQL = "SELECT BAHT FROM RMS_CURRENCY WHERE ID_CURRENCY=?";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idCurrency);
			db.executeQuery();
			if (db.next())
			{
				result = (total * db.getInt("BAHT")) + 0;
				if (result <= 0)
				{
					result = total;
				}
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
	
	public static java.sql.Date getApplyDate(int idJsk, int idEmp, int idPosition)
	{
		java.sql.Date result=null;
		DBManager db=null;
		String sql="SELECT SENTDATE FROM INTER_TRACK WHERE ID_EMP=? AND ID_POSITION=? AND ID_JSK=? AND (SENTDATE>=SYSDATE-30)";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idEmp);
			db.setInt(2,idPosition);
			db.setInt(3,idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getDate("SENTDATE");
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
	
	public int getIdJsk() {
		return idJsk;
	}

	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}

	public int getIdResume() {
		return idResume;
	}

	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}

	public int getCurYear() {
		return curYear;
	}

	public void setCurYear(int curYear) {
		this.curYear = curYear;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public Education getLastEdu() {
		return lastEdu;
	}

	public void setLastEdu(Education lastEdu) {
		this.lastEdu = lastEdu;
	}

	public Experience getLastExp() {
		return lastExp;
	}

	public void setLastExp(Experience lastExp) {
		this.lastExp = lastExp;
	}

	public TargetJob getTarget() {
		return target;
	}

	public void setTarget(TargetJob target) {
		this.target = target;
	}
	
	public static String base64(String text)
	{
		String result="";
		if(text!=null)
		{
			result=Base64.encodeBase64String(text.getBytes());
		}
		return result;
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
