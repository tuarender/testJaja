package com.topgun.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class JobManager {

	public static int getJobCount() {
		String nowdate = Util.nowDateFormat("yyyyMMdd");
		int result = 0;
		String SQL = " SELECT " + " COUNT(*) as NUM" + " FROM " + " EMPLOYER, EMP_PKG, POSITION,POSITION_MAJOR MJ,EMP_INDUSTRY EI ,POSITION_WORKEQUI PW" + " WHERE " + " (EMPLOYER.ID_EMP = EMP_PKG.ID_EMP) AND " + " (EMPLOYER.ID_EMP = EI.ID_EMP) AND " + " (EMPLOYER.ID_EMP = POSITION.ID_EMP) AND " + " (EMPLOYER.ID_EMP = PW.ID_EMP) AND " + " (PW.ID_POSITION=POSITION.ID_POSITION) AND " + " (POSITION.POST_DATE <='" + nowdate + "') AND " + " (POSITION.EXPIRE_DATE >='" + nowdate + "') AND "
				+ " (EMP_PKG.NO_ = POSITION.NO_) AND " + " (POSITION.FLAG = '1') AND " + " (EMPLOYER.FLAG = '1') AND " + " (EMP_PKG.FLAG_ABLE = '1') AND " + " (MJ.ID_EMP=POSITION.ID_EMP) AND " + " (MJ.ID_POSITION=POSITION.ID_POSITION) AND " + " (MJ.ID_FACULTY!='0') AND " + " (MJ.ID_FACULTY!='29') AND " + " (MJ.ID_FACULTY!='33') " + " ORDER BY EMPLOYER.ID_EMP,POSITION.ID_POSITION ";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			if (db.next()) {
				result = db.getInt("NUM");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static int getIdResumeComplete(int idJsk) {
		int result = 0;
		String SQL = " SELECT " + "	ID_RESUME  " + " FROM " + "	INTER_RESUME " + " WHERE " + " ID_JSK = ? AND " + " DELETE_STATUS <> 'TRUE' AND " + " COMPLETE_STATUS='TRUE' OR " + " ( " + "		ID_RESUME=0 AND " + "		ID_JSK = ? AND " + "		DELETE_STATUS <> 'TRUE'" + "	) ORDER BY TIMESTAMP DESC";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idJsk);
			db.executeQuery();
			if (db.next()) {
				result = db.getInt("ID_RESUME");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static Job[] getAllJob() {
		String nowdate = Util.nowDateFormat("yyyyMMdd");
		Job[] result = null;
		int cnt = getJobCount();
		if (cnt > 0) {
			result = new Job[cnt];
			String SQL = " SELECT " + " DISTINCT POSITION.ID_POSITION, EMPLOYER.ID_EMP, EMPLOYER.COMPANY_NAME, " + " POSITION.POSITION_NAME, SEX, EXP_LESS, EXP_MOST, DEGREE,MJ.ID_FACULTY , " + " EMPLOYER.PREMIUM ,POSITION.SALARY_LESS ,POSITION.SALARY_MOST ,EI.ID_INDUSTRY ,MJ.ID_MAJOR, " + " POSITION.WORK_TYPE " + " FROM " + " EMPLOYER, EMP_PKG, POSITION,POSITION_MAJOR MJ,EMP_INDUSTRY EI ,POSITION_WORKEQUI PW" + " WHERE " + " (EMPLOYER.ID_EMP = EMP_PKG.ID_EMP) AND "
					+ " (EMPLOYER.ID_EMP = EI.ID_EMP) AND " + " (EMPLOYER.ID_EMP = POSITION.ID_EMP) AND " + " (EMPLOYER.ID_EMP = PW.ID_EMP) AND " + " (PW.ID_POSITION=POSITION.ID_POSITION) AND " + " (POSITION.POST_DATE <='" + nowdate + "') AND " + " (POSITION.EXPIRE_DATE >='" + nowdate + "') AND " + " (EMP_PKG.NO_ = POSITION.NO_) AND " + " (POSITION.FLAG = '1') AND " + " (EMPLOYER.FLAG = '1') AND " + " (EMP_PKG.FLAG_ABLE = '1') AND " + " (MJ.ID_EMP=POSITION.ID_EMP) AND "
					+ " (MJ.ID_POSITION=POSITION.ID_POSITION) AND " + " (MJ.ID_FACULTY!='0') AND " + " (MJ.ID_FACULTY!='29') AND " + " (MJ.ID_FACULTY!='33') " + " ORDER BY EMPLOYER.ID_EMP,POSITION.ID_POSITION ";
			DBManager db = null;
			try {
				db = new DBManager();
				db.createPreparedStatement(SQL);
				db.executeQuery();
				int i = 0;
				while (db.next()) {

					result[i] = new Job();
					result[i].setCompanyName(db.getString("company_name"));
					result[i].setDegree(db.getString("degree"));
					result[i].setExpLess(db.getString("exp_less"));
					result[i].setExpMost(db.getString("exp_most"));
					result[i].setIdEmp(db.getInt("id_emp"));
					result[i].setIdFaculty(db.getInt("id_faculty"));
					result[i].setIdIndustry(db.getInt("id_industry"));
					result[i].setIdMajor(db.getInt("id_major"));
					result[i].setIdPosition(db.getInt("id_position"));
					result[i].setJobSection(db.getString("PREMIUM"));
					result[i].setPositionName(db.getString("position_name"));
					result[i].setSalaryLess(db.getString("salary_less"));
					result[i].setSalaryMost(db.getString("salary_most"));
					result[i].setSex(db.getString("sex"));
					result[i].setWorkType(db.getString("work_type"));
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		}
		return result;
	}

	public static int getJobMatchCount(int idJsk, int idResume) {
		String nowdate = Util.nowDateFormat("yyyyMMdd");
		String SqlJobMatch = "" + JobmatchManager.SqlJobMatch(idJsk, idResume);
		int chk = 0, idEmpChk = 0, idPosChk = 0;
		int result = 0;
		if (!SqlJobMatch.equals("")) {
			String SQL = " SELECT " + " DISTINCT POSITION.ID_POSITION, EMPLOYER.ID_EMP" + " FROM " + " EMPLOYER, EMP_PKG, POSITION,POSITION_MAJOR MJ,EMP_INDUSTRY EI ,POSITION_WORKEQUI PW" + " WHERE " + " (EMPLOYER.ID_EMP = EMP_PKG.ID_EMP) AND " + " (EMPLOYER.ID_EMP = EI.ID_EMP) AND " + " (EMPLOYER.ID_EMP = POSITION.ID_EMP) AND " + " (EMPLOYER.ID_EMP = PW.ID_EMP) AND " + " (PW.ID_POSITION=POSITION.ID_POSITION) AND " + " (POSITION.POST_DATE <='" + nowdate + "') AND " + " (POSITION.EXPIRE_DATE >='"
					+ nowdate + "') AND " + " (EMP_PKG.NO_ = POSITION.NO_) AND " + " (POSITION.FLAG = '1') AND " + " (EMPLOYER.FLAG = '1') AND " + " (EMP_PKG.FLAG_ABLE = '1') AND " + " (POSITION.JOB_IN_COUNTRY = '216') AND " + " (MJ.ID_EMP=POSITION.ID_EMP) AND " + " (MJ.ID_POSITION=POSITION.ID_POSITION) AND " + " (MJ.ID_FACULTY!='0') AND " + " (MJ.ID_FACULTY!='29') AND " + " (MJ.ID_FACULTY!='33') " + SqlJobMatch + " ORDER BY EMPLOYER.ID_EMP,POSITION.ID_POSITION ";
			// System.out.println("SQL >> "+SQL);

			DBManager db = null;
			try {
				db = new DBManager();
				db.createPreparedStatement(SQL);
				db.executeQuery();
				while (db.next()) {
					chk = 0;
					if ((idEmpChk == db.getInt("id_emp")) && (idPosChk == db.getInt("id_position"))) {
						chk = 1;
					}

					if (!(chk == 1)) {
						result++;
					}
					idEmpChk = db.getInt("id_emp");
					idPosChk = db.getInt("id_position");

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		} // chk sql
		return result;
	}

	// public List<Activity> getAll(int idJsk, int idResume) old
	public static List<Job> getJobMatchJob(int idJsk, int idResume) {
		String nowdate = Util.nowDateFormat("yyyyMMdd");
		String SqlJobMatch = "" + JobmatchManager.SqlJobMatch(idJsk, idResume);
		String indusSQL = " " + getIndustrySQL(idJsk, idResume);
		String localSQL = " " + getlocationsJsk(idJsk, idResume);
		int chk = 0, idEmpChk = 0, idPosChk = 0;
		List<Job> result = new ArrayList<Job>();
		String SQL = " SELECT " 
					+ " DISTINCT POSITION.ID_POSITION, POSITION.SPECIAL_TITLE,EMPLOYER.ID_EMP, EMPLOYER.COMPANY_NAME, " 
					+ " POSITION.POSITION_NAME, SEX, EXP_LESS, EXP_MOST, DEGREE,MJ.ID_FACULTY , " 
					+ " EMPLOYER.PREMIUM ,POSITION.SALARY_LESS ,POSITION.SALARY_MOST ,EI.ID_INDUSTRY ,MJ.ID_MAJOR, " 
					+ " POSITION.WORK_TYPE ,POSITION.POST_DATE " 
					+ " FROM " 
					+ " EMPLOYER, EMP_PKG, POSITION,POSITION_MAJOR MJ,EMP_INDUSTRY EI ,POSITION_WORKEQUI PW" 
					+ " WHERE "
					+ " (EMPLOYER.ID_EMP = EMP_PKG.ID_EMP) AND " 
					+ " (EMPLOYER.ID_EMP = EI.ID_EMP) AND " 
					+ " (EMPLOYER.ID_EMP = POSITION.ID_EMP) AND " 
					+ " (EMPLOYER.ID_EMP = PW.ID_EMP) AND " 
					+ " (PW.ID_POSITION=POSITION.ID_POSITION) AND " 
					+ " (POSITION.POST_DATE <='" + nowdate + "') AND " 
					+ " (POSITION.EXPIRE_DATE >='" + nowdate + "') AND " 
					+ " (EMP_PKG.NO_ = POSITION.NO_) AND " 
					+ " (POSITION.FLAG = '1') AND " 
					+ " (EMPLOYER.FLAG = '1') AND " 
					+ " (POSITION.JOB_IN_COUNTRY = '216') AND "
					+ " (EMP_PKG.FLAG_ABLE = '1') AND " 
					+ " (MJ.ID_EMP=POSITION.ID_EMP) AND " 
					+ " (MJ.ID_POSITION=POSITION.ID_POSITION)  " 
					//+ " (MJ.ID_FACULTY!='0') AND "
					//+ " (MJ.ID_FACULTY!='29') AND " 
					//+ " (MJ.ID_FACULTY!='33') " 
					+ SqlJobMatch + indusSQL  + localSQL
					+ " ORDER BY EMPLOYER.ID_EMP,POSITION.ID_POSITION ";
		//System.out.println("SQL "+SQL);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			while (db.next()) {
				chk = 0;
				if ((idEmpChk == db.getInt("id_emp")) && (idPosChk == db.getInt("id_position"))) {
					chk = 1;
				}
				if (!(chk == 1)) {
					Job job = new Job();
					job.setCompanyName(db.getString("company_name"));
					job.setDegree(db.getString("degree"));
					job.setExpLess(db.getString("exp_less"));
					job.setExpMost(db.getString("exp_most"));
					job.setIdEmp(db.getInt("id_emp"));
					job.setIdFaculty(db.getInt("id_faculty"));
					job.setIdIndustry(db.getInt("id_industry"));
					job.setIdMajor(db.getInt("id_major"));
					job.setIdPosition(db.getInt("id_position"));
					job.setJobSection(db.getString("PREMIUM"));
					job.setPositionName(db.getString("position_name"));
					job.setSalaryLess(db.getString("salary_less"));
					job.setSalaryMost(db.getString("salary_most"));
					job.setSex(db.getString("sex"));
					job.setWorkType(db.getString("work_type"));
					job.setPostDate(db.getString("post_date"));
					job.setSpecialTitle(db.getString("special_title"));
					result.add(job);
				}
				idEmpChk = db.getInt("id_emp");
				idPosChk = db.getInt("id_position");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static int getJobUpdateCount(int idJsk, int idResume) {
		String nowdate = Util.nowDateFormat("yyyyMMdd");
		String SqlJobUpdate = "" + JobupdateManager.SqlJobUpdate(idJsk, idResume);
		int chk = 0, idEmpChk = 0, idPosChk = 0;
		int result = 0;
		String SQL = " SELECT " + " DISTINCT POSITION.ID_POSITION, EMPLOYER.ID_EMP" + " FROM " + " EMPLOYER, EMP_PKG, POSITION,POSITION_MAJOR MJ,EMP_INDUSTRY EI ,POSITION_WORKEQUI PW ,POSITION_LOCATION PL" + " WHERE " +
				// " (EMPLOYER.ID_EMP = EMP_PKG.ID_EMP) AND "+ edit by ton
				" (EMPLOYER.ID_EMP = EI.ID_EMP) AND " + " (EMPLOYER.ID_EMP = POSITION.ID_EMP) AND " + " (EMPLOYER.ID_EMP = PW.ID_EMP) AND " + " (PW.ID_POSITION=POSITION.ID_POSITION) AND " + " (EMPLOYER.ID_EMP = PL.ID_EMP) AND " + " (PL.ID_POSITION=POSITION.ID_POSITION) AND " + " (POSITION.JOB_IN_COUNTRY='216') AND " + " (POSITION.POST_DATE <='" + nowdate + "') AND " + " (POSITION.EXPIRE_DATE >='" + nowdate + "') AND " + " (EMP_PKG.NO_ = POSITION.NO_) AND " + " (POSITION.FLAG = '1') AND "
				+ " (EMPLOYER.FLAG = '1') AND " + " (EMP_PKG.FLAG_ABLE = '1') AND " + " (MJ.ID_EMP=POSITION.ID_EMP) AND " + " (MJ.ID_POSITION=POSITION.ID_POSITION) AND " +
				// " (MJ.ID_FACULTY!='0') AND "+ edit by ton
				" (MJ.ID_FACULTY!='29') AND " + " (MJ.ID_FACULTY!='33') " + SqlJobUpdate + " ORDER BY EMPLOYER.ID_EMP,POSITION.ID_POSITION ";
		// System.out.println("SQL getJobUpdateCount : "+SQL);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			while (db.next()) {
				chk = 0;
				if ((idEmpChk == db.getInt("id_emp")) && (idPosChk == db.getInt("id_position"))) {
					chk = 1;
				}

				if (!(chk == 1)) {
					result++;
				}
				idEmpChk = db.getInt("id_emp");
				idPosChk = db.getInt("id_position");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static int getLastIdWorkField(int idJsk, int idResume) {
		int idWork = -1;
		String SQL = "SELECT " + "	ID AS ID_WORK " + "FROM " + "	INTER_WORK_EXPERIENCE " + "WHERE " + "	DELETE_STATUS<>'TRUE' AND WORKJOB_FIELD>0 AND ID_JSK=?  AND ID_RESUME=? " + " 	AND (WORK_JOBTYPE <> 2 AND WORK_JOBTYPE <> 4  AND  WORK_JOBTYPE <> 7) " + "ORDER BY " + "	WORK_END DESC ,ID DESC";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if (db.next()) {
				idWork = Util.getInt(db.getInt("ID_WORK"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return idWork;
	}

	public static String getIndustrySQL(int idJsk, int idResume) {
		int idWork = getLastIdWorkField(idJsk, idResume);
		String resultSQL = "";
		String rs = "";
		if (idWork < 0) {
			return resultSQL;
		}
		String SQL = " SELECT " + "	ID_INDUSTRY " + " FROM " + "	INTER_EXPERIENCE_INDUSTRY " + " WHERE " + "	ID_JSK=? AND ID_RESUME=? AND ID_WORK=? AND ID_INDUSTRY>0";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idWork);
			db.executeQuery();
			int j = 1;
			while (db.next()) {
				if (j == 1) {
					rs = db.getString("ID_INDUSTRY");
				} else if (j == 2) {
					rs = rs + "," + db.getString("ID_INDUSTRY");
				}
				j++;
			}
			if (!rs.equals("")) {
				resultSQL = " AND ((POSITION.ID_EMP, POSITION.ID_POSITION) IN (SELECT ID_EMP,ID_POSITION FROM POSITION_INDUSTRY WHERE   ID_INDUSTRY IN (" + rs + ")) OR";
				resultSQL += " (POSITION.ID_EMP, POSITION.ID_POSITION) NOT IN (SELECT ID_EMP,ID_POSITION FROM POSITION_INDUSTRY)) ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return resultSQL;
	}

	// public static Job[] getJobUpdateJob(int idJsk,int idResume)
	public static List<Job> getJobUpdateJob(int idJsk, int idResume) {
		List<Job> result = new ArrayList<Job>();
		String nowdate = Util.nowDateFormat("yyyyMMdd");
		String SqlJobUpdate = "" + JobupdateManager.SqlJobUpdate(idJsk, idResume);
		int chk = 0, idEmpChk = 0, idPosChk = 0;
		String SQL = " SELECT " + " DISTINCT POSITION.ID_POSITION, EMPLOYER.ID_EMP, EMPLOYER.COMPANY_NAME, " + " POSITION.POSITION_NAME, SEX, EXP_LESS, EXP_MOST, DEGREE,MJ.ID_FACULTY , " + " EMPLOYER.PREMIUM ,POSITION.SALARY_LESS ,POSITION.SALARY_MOST ,EI.ID_INDUSTRY ,MJ.ID_MAJOR, " + " POSITION.WORK_TYPE ,PL.ID_STATE, PL.ID_CITY ,POSITION.POST_DATE ,POSITION.SPECIAL_TITLE " + " FROM " + " EMPLOYER, EMP_PKG, POSITION,POSITION_MAJOR MJ,EMP_INDUSTRY EI ,POSITION_WORKEQUI PW ,POSITION_LOCATION PL "
				+ " WHERE " +
				// " (EMPLOYER.ID_EMP = EMP_PKG.ID_EMP) AND "+ edit by ton
				" (EMPLOYER.ID_EMP = EI.ID_EMP) AND " + " (EMPLOYER.ID_EMP = POSITION.ID_EMP) AND " + " (EMPLOYER.ID_EMP = PW.ID_EMP) AND " + " (PW.ID_POSITION=POSITION.ID_POSITION) AND " + " (EMPLOYER.ID_EMP = PL.ID_EMP) AND " + " (PL.ID_POSITION=POSITION.ID_POSITION) AND " + " (POSITION.JOB_IN_COUNTRY='216') AND " + " (POSITION.POST_DATE <='" + nowdate + "') AND " + " (POSITION.EXPIRE_DATE >='" + nowdate + "') AND " + " (EMP_PKG.NO_ = POSITION.NO_) AND " + " (POSITION.FLAG = '1') AND "
				+ " (EMPLOYER.FLAG = '1') AND " + " (EMP_PKG.FLAG_ABLE = '1') AND " + " (MJ.ID_EMP=POSITION.ID_EMP) AND " + " (MJ.ID_POSITION=POSITION.ID_POSITION) AND " +
				// " (MJ.ID_FACULTY!='0') AND "+ edit by ton
				" (MJ.ID_FACULTY!='29') AND " + " (MJ.ID_FACULTY!='33') " + SqlJobUpdate + " ORDER BY EMPLOYER.ID_EMP,POSITION.ID_POSITION ";
		//System.out.println("SQL JUU : " + SQL);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			while (db.next()) {
				chk = 0;
				if ((idEmpChk == db.getInt("id_emp")) && (idPosChk == db.getInt("id_position"))) {
					chk = 1;
				}
				if (!(chk == 1)) {
					Job job = new Job();
					job.setCompanyName(db.getString("company_name"));
					job.setDegree(db.getString("degree"));
					job.setExpLess(db.getString("exp_less"));
					job.setExpMost(db.getString("exp_most"));
					job.setIdEmp(db.getInt("id_emp"));
					job.setIdFaculty(db.getInt("id_faculty"));
					job.setIdIndustry(db.getInt("id_industry"));
					job.setIdMajor(db.getInt("id_major"));
					job.setIdPosition(db.getInt("id_position"));
					job.setJobSection(db.getString("PREMIUM"));
					job.setPositionName(db.getString("position_name"));
					job.setSalaryLess(db.getString("salary_less"));
					job.setSalaryMost(db.getString("salary_most"));
					job.setSex(db.getString("sex"));
					job.setWorkType(db.getString("work_type"));
					job.setPostDate(db.getString("post_date"));

					job.setSpecialTitle(db.getString("special_title"));
					result.add(job);
				}
				idEmpChk = db.getInt("id_emp");
				idPosChk = db.getInt("id_position");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static int getJobListCount(int idJsk) {
		int result = 0;
		String SQL = " SELECT COUNT(*) AS SUM FROM JOBLIST_JSK WHERE ID_JSK = " + idJsk;
		// System.out.println("SQL getJobMatchCount : "+SQL);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			if (db.next()) {
				result = db.getInt("SUM");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static int getLastIdWebboard() {
		int result = 0;
		String SQL = " SELECT MAX(TOPIC_ID) AS TOPIC_ID FROM INTER_WEBBOARD_TOPIC";
		// System.out.println("SQL getJobMatchCount : "+SQL);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			if (db.next()) {
				result = db.getInt("TOPIC_ID");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static int getMyNoteCount(int idJsk) {
		int result = 0;
		String SQL = " SELECT COUNT(*) AS SUM FROM JSK_NOTE WHERE ID_JSK = " + idJsk;
		// System.out.println("SQL getJobMatchCount : "+SQL);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			if (db.next()) {
				result = db.getInt("SUM");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static String getQuestion(int idTopic) {
		String result = "";
		String SQL = " SELECT * FROM INTER_WEBBOARD_QUESTION WHERE TOPIC_ID = " + idTopic;
		// System.out.println("SQL getJobMatchCount : "+SQL);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			if (db.next()) {
				result = db.getString("QUESTION_ID") + "," + db.getString("SUBJECT") + "," + db.getString("CREATED") + "," + db.getString("OWNER");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static String getJobListSalaryEN(String salaryLess, String salaryMost) {

		String salaryName = "";

		salaryLess = salaryLess != null ? salaryLess : "";
		salaryMost = salaryMost != null ? salaryMost : "";

		if (salaryLess.equals("-1")) {

			salaryName = "Not Specified";
		} else if (salaryLess.equals("-2")) {

			salaryName = "Depend on qualifications & experience";
		} else if (salaryLess.equals("-3")) {

			salaryName = "Depend on qualifications";
		} else if (salaryLess.equals("-4")) {

			salaryName = "Negotiable";
		} else if (salaryLess.equals("-5")) {

			salaryName = "N/A";
		} else if (salaryLess.equals("-6")) {

			salaryName = "Competitive";
		} else if (salaryLess.equals("0")) {

			salaryName = "less than or eq " + salaryMost;
		} else if (salaryMost.equals("999999999")) {

			salaryName = "more than or eq " + salaryLess;
		} else if (salaryLess.equals(salaryMost)) {

			salaryName = salaryLess + " Baht";
		} else {

			salaryName = salaryLess + " - " + salaryMost + " Baht";
		}
		return salaryName;
	}

	public static String getEXP(String min, String max, int idLanguage) {
		String result = "";
		int maxE = 0, minE = 0;
		if (!max.equals("")) {
			maxE = Integer.parseInt(max);
		}
		if (!min.equals("")) {
			minE = Integer.parseInt(min);
		}
		if (idLanguage == 38) {
			if (maxE > 0) {
				result = min + "-" + max + " ปี";
			} else if ((minE > 0) && (maxE == 0)) {
				result = min.trim() + " ปีขึ้นไป";
			} else {
				result = "-";
			}
		} else {
			if (maxE > 0) {
				result = min + "-" + max + " year(s)";
			} else if ((minE > 0) && (maxE == 0)) {
				result = min.trim() + " year(s) or more.";
			} else {
				result = "-";
			}
		}
		return result;
	}

	public static String getJobListLocationEN(String empID, String positionID) {

		empID = empID != null ? empID : "";
		positionID = positionID != null ? positionID : "";

		String locationName = "";
		int count = 0;

		String sql = " SELECT INTER_STATE.STATE_NAME,INTER_CITY.CITY_NAME " + " FROM POSITION_LOCATION,INTER_STATE,INTER_CITY " + " WHERE  POSITION_LOCATION.ID_POSITION = '" + positionID + "' " + " AND POSITION_LOCATION.ID_EMP = '" + empID + " ' " + " AND POSITION_LOCATION.ID_COUNTRY='216' " + " AND INTER_STATE.ID_COUNTRY = '216' AND POSITION_LOCATION.ID_STATE=INTER_STATE.ID_STATE " + " AND INTER_CITY.ID_COUNTRY = '216' AND POSITION_LOCATION.ID_CITY=INTER_CITY.ID_CITY ";

		DBManager db = null;
		String lName = "";
		String cName = "";
		try {
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.executeQuery();

			while (db.next()) {

				lName = db.getString("STATE_NAME") != null ? db.getString("STATE_NAME") : "";
				cName = db.getString("CITY_NAME") != null ? db.getString("CITY_NAME") : "";
				locationName = locationName + lName + " <br> " + cName;
				count++;
			}

			if (count == 0) {

				locationName = "All";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return locationName;
	}

	public static String getJobListLocationENRss(String empID, String positionID) {

		empID = empID != null ? empID : "";
		positionID = positionID != null ? positionID : "";

		String locationName = "";
		int count = 0;

		String sql = " SELECT INTER_STATE.STATE_NAME,INTER_CITY.CITY_NAME " + " FROM POSITION_LOCATION,INTER_STATE,INTER_CITY " + " WHERE  POSITION_LOCATION.ID_POSITION = '" + positionID + "' " + " AND POSITION_LOCATION.ID_EMP = '" + empID + " ' " + " AND POSITION_LOCATION.ID_COUNTRY='216' " + " AND INTER_STATE.ID_COUNTRY = '216' AND POSITION_LOCATION.ID_STATE=INTER_STATE.ID_STATE " + " AND INTER_CITY.ID_COUNTRY = '216' AND POSITION_LOCATION.ID_CITY=INTER_CITY.ID_CITY ";

		DBManager db = null;
		String lName = "";
		String cName = "";
		try {
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.executeQuery();

			while (db.next()) {

				lName = db.getString("STATE_NAME") != null ? db.getString("STATE_NAME") : "";
				cName = db.getString("CITY_NAME") != null ? db.getString("CITY_NAME") : "";
				locationName = locationName + lName + " - " + cName;
				count++;
			}

			if (count == 0) {

				locationName = "All";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return locationName;
	}

	public static String getJobsLocation(int idEmp, int idPosition, int idLanguage) {
		String district = "";
		String country = "";
		String province = "";
		try {
			district = getDistrict(idEmp, idPosition, idLanguage);
			country = getCountry(idEmp, idPosition, idLanguage);
			if (!district.equals("")) {
				province = getLocation(idEmp, idPosition, false, idLanguage);
			} else {
				province = getLocation(idEmp, idPosition, true, idLanguage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return district.trim() + " " + province.trim() + " " + country.trim();
	}

	public static String getDistrict(int idEmp, int idPosition, int idLang) {
		String result = "";
		String SQL = "SELECT " + "	DISTINCT(CITY_NAME) AS DISTRICT " + "FROM " + "	POSITION_LOCATION A,INTER_CITY_LANGUAGE B " + "WHERE " + "	(A.ID_COUNTRY=216) AND (A.ID_STATE=77) AND " + "	(A.ID_CITY=B.ID_CITY) AND (A.ID_STATE=B.ID_STATE) AND " + " 	(A.ID_COUNTRY=B.ID_COUNTRY) AND (ID_EMP=?) AND " + " 	(ID_POSITION=?) AND (ID_LANGUAGE=?)";
		DBManager db = null;
		int count = 0;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idLang);
			db.executeQuery();
			while (db.next()) {
				if (count > 0) {
					result += ", ";
				}
				result += db.getString("DISTRICT");
				count++;
			}
			result = result.trim();
			if (!result.equals("")) {
				if (idLang == 11) {
					result = "Bangkok (" + result + ")";
				} else {
					result = "กทม. (" + result + ")";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}

	public static String getCountry(int idEmp, int idPosition, int idLang) {
		String result = "";
		String SQL = "SELECT " + "	DISTINCT(COUNTRY_NAME) AS COUNTRY_NAME " + "FROM " + "	POSITION_LOCATION A, INTER_COUNTRY_LANGUAGE B " + "WHERE " + "	(ID_EMP=?) AND " + "	(ID_POSITION=?) AND " + "	(A.ID_COUNTRY=B.ID_COUNTRY) AND " + "	(A.ID_COUNTRY!=216) AND " + "	(ID_LANGUAGE=?) " + "ORDER BY " + "	COUNTRY_NAME";

		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idLang);
			db.executeQuery();
			while (db.next()) {
				result += ", " + db.getString("COUNTRY_NAME");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		if (!result.equals("")) {
			result = result.substring(1).trim();
		}
		return result;
	}

	public static String getLocation(int idEmp, int idPosition, boolean hasBangkok, int idLang) {
		String result = "";
		if (idEmp <= 0 || idPosition <= 0)
			return "";

		int central[] = { 2, 4, 7, 15, 22, 26, 29, 35, 36, 38, 43, 44, 53, 54, 55, 56, 58, 62, 63, 70, 74, 77 };
		int northeast[] = { 1, 5, 8, 14, 17, 21, 24, 25, 27, 28, 33, 34, 52, 59, 65, 68, 69, 73, 75, 78 };
		int south[] = { 13, 18, 30, 32, 37, 39, 40, 46, 49, 57, 60, 64, 66, 72 };
		int north[] = { 10, 11, 19, 20, 23, 31, 41, 45, 71 };
		int east[] = { 6, 9, 12, 47, 51, 61, 67 };
		int west[] = { 16, 42, 48, 50, 76 };

		int all[] = { 10, 11, 19, 20, 23, 31, 41, 45, 71, // north
				2, 4, 7, 15, 22, 26, 29, 35, 36, 38, 43, 44, 53, 54, 55, 56, 58, 62, 63, 70, 74, 77, // central
				6, 9, 12, 47, 51, 61, 67, // east
				1, 5, 8, 14, 17, 21, 24, 25, 27, 28, 33, 34, 52, 59, 65, 68, 69, 73, 75, 78, // northeast
				13, 18, 30, 32, 37, 39, 40, 46, 49, 57, 60, 64, 66, 72, // south
				16, 42, 48, 50, 76 };// west

		int upCountry[] = { 2, 4, 7, 15, 22, 26, 29, 35, 36, 38, 43, 44, 53, 54, 55, 56, 58, 62, 63, 70, 74, // central
																												// except
																												// bangkok(77)
				1, 5, 8, 14, 17, 21, 24, 25, 27, 28, 33, 34, 52, 59, 65, 68, 69, 73, 75, 78, // northeast
				13, 18, 30, 32, 37, 39, 40, 46, 49, 57, 60, 64, 66, 72, // south
				10, 11, 19, 20, 23, 31, 41, 45, 71, // north
				6, 9, 12, 47, 51, 61, 67, // east
				16, 42, 48, 50, 76 };// west

		String SQL = "SELECT " + "	B.ID_STATE,B.STATE_NAME " + "FROM " + "	POSITION_LOCATION A,INTER_STATE_LANGUAGE B " + "WHERE " + "	(A.ID_COUNTRY=216) AND " + "	(A.ID_STATE=B.ID_STATE) AND " + "	(A.ID_COUNTRY=B.ID_COUNTRY) AND " + "	(A.ID_EMP=?) AND " + "	(A.ID_POSITION=?) AND " + "	(A.ID_STATE IS NOT NULL) AND " + "	(B.ID_LANGUAGE=?)";

		if (hasBangkok == false) {
			SQL += " AND A.ID_STATE<>77 ";
		}
		SQL += " ORDER BY B.STATE_NAME ";

		String provinces = "";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idLang);
			db.executeQuery();
			while (db.next()) {
				provinces += " " + db.getInt("ID_STATE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		provinces = provinces.trim();
		if (!provinces.equals("")) {
			String pros[] = provinces.split(" ");
			if (pros.length > 0) {
				int[] province = new int[pros.length];
				for (int i = 0; i < pros.length; i++) {
					province[i] = Util.getInt(pros[i]);
				}
				if (Util.isSubset(province, all)) {
					if (idLang == 11) {
						result = ", All Provinces";
					} else {
						result = ", ทุกจังหวัด”";
					}
				} else if (Util.isSubset(province, upCountry)) {
					province = Util.arrayMinus(province, upCountry);
					if (idLang == 11) {
						result = ", Upcountry";
					} else {
						result = ", ต่างจังหวัด";
					}
				} else {
					// ---- check is central----
					if (Util.isSubset(province, central)) {
						province = Util.arrayMinus(province, central);
						if (idLang == 11) {
							result = ", Central Region";
						} else {
							result = ", ภาคกลาง";
						}
					}

					// ---- check is northeast----
					if (province != null) {
						if (Util.isSubset(province, northeast)) {
							province = Util.arrayMinus(province, northeast);
							if (idLang == 11) {
								result += ", Northeastern Region";
							} else {
								result += ", ภาคตะวันออกเฉียงเหนือ";
							}
						}
					}

					// ---- check is south----
					if (province != null) {
						if (Util.isSubset(province, south)) {
							province = Util.arrayMinus(province, south);
							if (idLang == 11) {
								result += ", Southern Region";
							} else {
								result += ", ภาคใต้";
							}
						}
					}

					// ---- check is north----
					if (province != null) {
						if (Util.isSubset(province, north)) {
							province = Util.arrayMinus(province, north);
							if (idLang == 11) {
								result += ", Northern Region";
							} else {
								result += ", ภาคเหนือ";
							}
						}
					}

					// ---- check is east----
					if (province != null) {
						if (Util.isSubset(province, east)) {
							province = Util.arrayMinus(province, east);
							if (idLang == 11) {
								result += ", Eastern Region";
							} else {
								result += ", ภาคตะวันออก";
							}
						}
					}

					// ---- check is west----
					if (province != null) {
						if (Util.isSubset(province, west)) {
							province = Util.arrayMinus(province, west);
							if (idLang == 11) {
								result += ", Western Region";
							} else {
								result += ", ภาคตะวันตก";
							}
						}
					}
				}

				// Check remaining province
				if (province != null) {
					String ids = "";
					for (int i = 0; i < province.length; i++) {
						ids += "," + province[i];
					}
					ids = ids.substring(1).trim();
					if (!ids.equals("")) {
						String SQX = "SELECT " + "	B.ID_STATE,B.STATE_NAME FROM INTER_STATE A,INTER_STATE_LANGUAGE B " + "WHERE " + "	A.ID_COUNTRY=B.ID_COUNTRY AND " + "	A.ID_STATE=B.ID_STATE AND " + "	A.ID_COUNTRY=216 AND B.ID_LANGUAGE=? AND " + "	A.ID_STATE IN(" + ids + ") " + "ORDER BY " + " B.STATE_NAME";

						DBManager dx = null;
						try {
							dx = new DBManager();
							dx.createPreparedStatement(SQX);
							dx.setInt(1, idLang);
							dx.executeQuery();
							while (dx.next()) {
								if (dx.getInt("ID_STATE") == 77 && idLang == 38) {
									result += ", กทม.";
								} else {
									result += ", " + dx.getString("STATE_NAME");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							dx.close();
						}
					}
				}
			}
		}
		if (!result.equals("")) {
			result = result.substring(1).trim();
		}
		return result;
	}

	public static String getCurrency(int currencyID) {
		String currencyName = "Baht";
		String sql = "SELECT CURRENCY_NAME FROM INTER_CURRENCY WHERE ID_CURRENCY = " + currencyID + "";
		// System.out.println("sql : "+sql);
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.executeQuery();

			while (db.next()) {
				currencyName = db.getString("CURRENCY_NAME") != null ? db.getString("CURRENCY_NAME") : "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return currencyName;
	}

	public static String getExp(int min, int max, int idLanguage) {
		String result = "0";
		if (max > 0) {
			if (idLanguage == 38) {
				result = min + "-" + max + " ปี";
			} else {
				result = min + "-" + max + " year(s)";
			}
		} else if ((min > 0) && (max <= 0)) {
			if (idLanguage == 38) {
				result = min + " ปีขึ้นไป";
			} else {
				result = "more than " + min + " year(s)";
			}
		} else {
			if (idLanguage == 38) {
				result = "0 ปี";
			} else {
				result = "0 Year(s)";
			}
		}
		return result;
	}

	public static String getFieldFamilySQL(int idFac, int idMajor) {
		String FamilySQL = "";
		String SQL = "SELECT * FROM INTER_GROUP_SUBFIELD_EDUCATION WHERE ID_FACULTY=? AND ID_MAJOR=? AND ID_GROUP_SUBFIELD=0 ";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idFac);
			db.setInt(2, idMajor);
			db.executeQuery();
			while (db.next()) {
				if (db.getInt("ID_FIELD") > 0) {
					if (FamilySQL.equals("")) {
						FamilySQL = FamilySQL + " (PW.WORKJOB_FIELD=" + db.getInt("ID_FIELD") + ") ";
					} else {
						FamilySQL = FamilySQL + " OR (PW.WORKJOB_FIELD=" + db.getInt("ID_FIELD") + ") ";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return FamilySQL.trim();
	}

	public static String getSubFieldFamilySQL(int idFac, int idMajor) {
		String FamilySQL = "";
		String SQL = "SELECT * FROM INTER_GROUP_SUBFIELD_EDUCATION WHERE ID_FACULTY=? AND ID_MAJOR=? AND ID_GROUP_SUBFIELD>0";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idFac);
			db.setInt(2, idMajor);
			db.executeQuery();
			while (db.next()) {
				if (FamilySQL == "") {
					FamilySQL = " " + getSubField(db.getInt("ID_FIELD"), db.getInt("ID_GROUP_SUBFIELD"));
				} else {
					FamilySQL = FamilySQL + " OR " + getSubField(db.getInt("ID_FIELD"), db.getInt("ID_GROUP_SUBFIELD"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return FamilySQL.trim();
	}

	public static String getSubField(int idField, int idGroupField) {
		String FamilySQL = "";
		String SQL = "SELECT * FROM INTER_SUBFIELD WHERE ID_FIELD=? AND ID_GROUP_SUBFIELD=?";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idField);
			db.setInt(2, idGroupField);
			db.executeQuery();
			while (db.next()) {
				if (FamilySQL.equals("")) {
					FamilySQL = FamilySQL + " (PW.WORKJOB_FIELD=" + db.getInt("ID_FIELD") + " AND PW.WORKSUB_FIELD=" + db.getInt("ID_SUBFIELD") + ") ";
				} else {
					FamilySQL = FamilySQL + " OR (PW.WORKJOB_FIELD=" + db.getInt("ID_FIELD") + " AND PW.WORKSUB_FIELD=" + db.getInt("ID_SUBFIELD") + ") ";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return FamilySQL.trim();
	}

	public static String getJobSubfieldSQL(int idJobfield, int idSubfield) {
		String SubfieldSQL = "";
		String SQL = "	SELECT B.* " + "		FROM " + "		( " + "			SELECT " + "				ID_GROUP_SUBFIELD,ID_FIELD " + "			FROM " + "				INTER_SUBFIELD " + "			WHERE ID_FIELD=? AND ID_SUBFIELD=?" + "		)A,INTER_SUBFIELD B " + "		WHERE " + "			A.ID_GROUP_SUBFIELD=B.ID_GROUP_SUBFIELD AND A.ID_FIELD=B.ID_FIELD";
		DBManager db = null;
		try {
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJobfield);
			db.setInt(2, idSubfield);
			db.executeQuery();
			while (db.next()) {
				if (SubfieldSQL.equals("")) {
					SubfieldSQL = SubfieldSQL + " (PW.WORKJOB_FIELD=" + db.getInt("ID_FIELD") + " AND PW.WORKSUB_FIELD=" + db.getInt("ID_SUBFIELD") + ") ";
				} else {
					SubfieldSQL = SubfieldSQL + " OR (PW.WORKJOB_FIELD=" + db.getInt("ID_FIELD") + " AND PW.WORKSUB_FIELD=" + db.getInt("ID_SUBFIELD") + ") ";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return SubfieldSQL.trim();
	}
	
	public static Set<Integer> getStateAddress(int idJsk,int idResume)
	{
		Set<Integer>result=new HashSet<Integer>();
		DBManager db = null;
		String SQL=" SELECT ID_STATE  FROM INTER_RESUME  WHERE ID_JSK=? AND ID_RESUME=?  AND ID_COUNTRY =216";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if (db.next()) 
			{
				result.addAll(getListStateInRegions(db.getInt("ID_STATE")));
				//System.out.println("id_state  getStateTarget="+db.getInt("ID_STATE"));
				//System.out.println("result="+result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}
	
	public static Set<Integer>  getStateTarget(int idJsk,int idResume)
	{
		Set<Integer>result=new HashSet<Integer>();
		DBManager db = null;
		String SQL=" SELECT ID_STATE FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=216";
		try 
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while (db.next()) 
			{
				result.addAll(getListStateInRegions(db.getInt("ID_STATE")));
				//System.out.println("id_state  getStateTarget="+db.getInt("ID_STATE"));
				//System.out.println("result="+result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return result;
	}
	
	
	public static String getlocationsJsk(int idJsk,int idResume)
	{
		String result="";
		String sql="";
		Set<Integer>localAddress=getStateAddress(idJsk, idResume);
		Set<Integer>localTarget=getStateTarget(idJsk, idResume);
		
		// HashSet=list unique
		Set<Integer> list = new HashSet<Integer>();
		list.addAll(localAddress);
		list.addAll(localTarget);
		
		List<Integer> lists = new ArrayList<Integer>(list);
		Collections.sort(lists);
		result=lists.toString().replaceAll("\\[", "").replaceAll("\\]","");
		if(!result.equals(""))
		{
			System.out.println("result2== "+result);
			sql=" AND (POSITION.ID_EMP, POSITION.ID_POSITION) IN (SELECT ID_EMP,ID_POSITION FROM POSITION_LOCATION WHERE ID_COUNTRY=216 AND ID_STATE IN ("+result+"))";
		}
		return sql;
	}
    public static Set<Integer> getListStateInRegions(int idState)
    {
    	Set<Integer> result = new HashSet<Integer>();
    	Set<Integer> north = new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));//17--
    	Set<Integer> center=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74));//13--
    	Set<Integer> east=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67));//7--
    	Set<Integer> northeast=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));//20--
    	Set<Integer> south=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));//14--
    	Set<Integer> metro=new HashSet<Integer>(Arrays.asList(26,35,36,53,54,77));//6--
    	if(north.contains(idState)==true)
    	{
    		result=north;
    	}
    	if(center.contains(idState)==true)
    	{
    		result=center;
    	}
    	if(east.contains(idState)==true)
    	{
    		result=east;
    	}
    	if(northeast.contains(idState)==true)
    	{
    		result=northeast;
    	}
    	if(south.contains(idState)==true)
    	{
    		result=south;
    	}
    	if(metro.contains(idState)==true)
    	{
    		result=metro;
    	}
    	return result;
    }
}
