package com.topgun.search;
import java.util.*;
import com.topgun.util.DBManager;

public class SearchManager 
{
	public static List<Search> getAllSearch(String idSession, int searchType,int idLanguage) //use for non member
	{
		List<Search> result=new ArrayList<Search>();
		int quota=searchType==0?2:5;
		DBManager db=null;
		try
		{
			String sql="SELECT ID_JSK,ID_SEARCH FROM INTER_SEARCH WHERE TYPE=? AND ID_JSK=-1 AND ID_SESSION=? AND DELETED<>1 ORDER BY TIMESTAMP DESC";
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, searchType);
			db.setString(2, idSession);
			db.executeQuery();
			int i=0;
			while(db.next())
			{
				if(i<quota)
				{
					result.add(getSearch(db.getInt("ID_JSK"),db.getInt("ID_SEARCH"),idLanguage));
				}
				else
				{
					new Search(db.getInt("ID_JSK"),db.getInt("ID_SEARCH"),idLanguage).delete();
				}
				i++;
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
	
	public static List<Search> getAllSearch(int idJsk, int searchType,int idLanguage)
	{
		List<Search> result=new ArrayList<Search>();
		int quota=searchType==0?2:5;
		DBManager db=null;
		try
		{
			String sql="SELECT ID_JSK,ID_SEARCH FROM INTER_SEARCH WHERE TYPE=? AND ID_JSK=? AND ID_JSK>0  AND DELETED<>1 ORDER BY TIMESTAMP DESC";
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, searchType);
			db.setInt(2, idJsk);
			db.executeQuery();
			int i=0;
			while(db.next())
			{
				if(i<quota)
				{
					result.add(getSearch(db.getInt("ID_JSK"),db.getInt("ID_SEARCH"),idLanguage));
				}
				else
				{
					new Search(db.getInt("ID_JSK"),db.getInt("ID_SEARCH"),idLanguage).delete();
				}
				i++;
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
	
	public static List<Search> getAllSearchForNotification(int idJsk, int searchType,int idLanguage)
	{
		List<Search> result=null;
		ArrayList<Integer> idJsks = new ArrayList<Integer>();
		ArrayList<Integer> idSearchs = new ArrayList<Integer>();
		DBManager db=null;
		try
		{
			String sql="SELECT ID_JSK,ID_SEARCH FROM INTER_SEARCH WHERE TYPE=? AND ID_JSK=? AND ID_JSK>0  AND DELETED<>1 AND SYSDATE >= LAST_VIEW ORDER BY TIMESTAMP DESC";
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, searchType);
			db.setInt(2, idJsk);
			db.executeQuery();
			result = new ArrayList<Search>();
			while(db.next())
			{
				idJsks.add(db.getInt("ID_JSK"));
				idSearchs.add(db.getInt("ID_SEARCH"));
				
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
		if(idJsks.size() > 0)
		{
			result = new ArrayList<Search>();
			for(int i = 0 ; i < idJsks.size() ; i++)
			{
				result.add(getSearch(idJsks.get(i), idSearchs.get(i), idLanguage));
			}
			
		}
		return result;
	}
	
	public static Search getSearch(int idJsk, int idSearch,int idLanguage)
	{
		Search result = getLightSearch(idJsk, idSearch, idLanguage);
		
		if(result != null)
		{
			result.fetchDegreeSearchList();
			result.fetchIndustrySearchList();
			result.fetchJobFieldSearchList();
			result.fetchRegionSearchList();
			result.fetchJobTypeSearchList();
			
		}
		return result;
	}

	public static Search getLightSearch(int idSearch, int idLanguage)
	{
		Search result=null;
		DBManager db=null;
		try
		{
			String sql="SELECT ID_JSK, TITLE,TYPE,KEYWORD,SALARY_LESS,SALARY_MOST,EXP_LESS,EXP_MOST,BTS_RADIUS,MRT_RADIUS,ID_SESSION,MAIL,ID_SEARCH_REF, TO_CHAR(TIMESTAMP,'dd/mm/yyyy') AS TIMESTAMP,ID_EMP,ID_POSITION,SEARCH_TYPE,SHOW_SALARY FROM INTER_SEARCH WHERE ID_SEARCH=?";
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idSearch);
			db.executeQuery();
			if(db.next())
			{
				result=new Search(db.getInt("ID_JSK"), idSearch, idLanguage);
				result.setIdSession(db.getString("ID_SESSION"));
				result.setTitle(db.getString("TITLE"));
				result.setType(db.getInt("TYPE"));
				result.setKeyword(db.getString("KEYWORD"));
				result.setSalaryLess(db.getInt("SALARY_LESS"));
				result.setSalaryMost(db.getInt("SALARY_MOST"));
				result.setExpLess(db.getInt("EXP_LESS"));
				result.setExpMost(db.getInt("EXP_MOST"));
				result.setBTSRadius(db.getFloat("BTS_RADIUS"));
				result.setMRTRadius(db.getFloat("MRT_RADIUS"));
				result.setIdSession(db.getString("ID_SESSION"));
				result.setMail(db.getString("MAIL"));
				result.setTimeStamp(db.getString("TIMESTAMP"));
				result.setIdSearchRef(db.getInt("ID_SEARCH_REF"));
				result.setIdEmp(db.getInt("ID_EMP"));
				result.setIdPosition(db.getInt("ID_POSITION"));
				result.setSearchType(db.getInt("SEARCH_TYPE"));
				result.setShowSalary(db.getInt("SHOW_SALARY"));
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
	
	public static Search getSearch(int idSearch,int idLanguage)
	{
		Search result = getLightSearch(idSearch, idLanguage);
		
		if(result != null)
		{
			result.fetchDegreeSearchList();
			result.fetchIndustrySearchList();
			result.fetchJobFieldSearchList();
			result.fetchRegionSearchList();
			result.fetchJobTypeSearchList();
			
		}
		return result;
	}

	public static Search getLightSearch(int idJsk, int idSearch, int idLanguage)
	{
		Search result=null;
		DBManager db=null;
		try
		{
			String sql="SELECT TITLE,TYPE,KEYWORD,SALARY_LESS,SALARY_MOST,EXP_LESS,EXP_MOST,BTS_RADIUS,MRT_RADIUS,ID_SESSION,MAIL,NOTIFICATION,ID_SEARCH_REF, TO_CHAR(TIMESTAMP,'dd/mm/yyyy') AS TIMESTAMP,ID_EMP,ID_POSITION,SEARCH_TYPE,SHOW_SALARY FROM INTER_SEARCH WHERE ID_JSK=? AND ID_SEARCH=?";
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.setInt(2,idSearch);
			db.executeQuery();
			if(db.next())
			{
				result=new Search(idJsk,idSearch, idLanguage);
				result.setTitle(db.getString("TITLE"));
				result.setType(db.getInt("TYPE"));
				result.setKeyword(db.getString("KEYWORD"));
				result.setSalaryLess(db.getInt("SALARY_LESS"));
				result.setSalaryMost(db.getInt("SALARY_MOST"));
				result.setExpLess(db.getInt("EXP_LESS"));
				result.setExpMost(db.getInt("EXP_MOST"));
				result.setBTSRadius(db.getFloat("BTS_RADIUS"));
				result.setMRTRadius(db.getFloat("MRT_RADIUS"));
				result.setIdSession(db.getString("ID_SESSION"));
				result.setMail(db.getString("MAIL"));
				result.setTimeStamp(db.getString("TIMESTAMP"));
				result.setIdSearchRef(db.getInt("ID_SEARCH_REF"));
				result.setIdEmp(db.getInt("ID_EMP"));
				result.setIdPosition(db.getInt("ID_POSITION"));
				result.setSearchType(db.getInt("SEARCH_TYPE"));
				result.setShowSalary(db.getInt("SHOW_SALARY"));
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
	
	public static Search getLightSearch(String idSession, int idSearch,int idLanguage)
	{
		Search result=null;
		DBManager db=null;
		try
		{
			String sql="SELECT ID_JSK,TITLE,TYPE,KEYWORD,SALARY_LESS,SALARY_MOST,EXP_LESS,EXP_MOST,BTS_RADIUS,MRT_RADIUS,ID_SESSION,MAIL,ID_SEARCH_REF, TO_CHAR(TIMESTAMP,'dd/mm/yyyy') AS TIMESTAMP,ID_EMP,ID_POSITION,SEARCH_TYPE,SHOW_SALARY FROM INTER_SEARCH WHERE ID_SESSION=? AND ID_SEARCH=?";
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1,idSession);
			db.setInt(2,idSearch);
			db.executeQuery();
			if(db.next())
			{
				result=new Search(db.getInt("ID_JSK"),idSearch, idLanguage);
				result.setTitle(db.getString("TITLE"));
				result.setType(db.getInt("TYPE"));
				result.setKeyword(db.getString("KEYWORD"));
				result.setSalaryLess(db.getInt("SALARY_LESS"));
				result.setSalaryMost(db.getInt("SALARY_MOST"));
				result.setExpLess(db.getInt("EXP_LESS"));
				result.setExpMost(db.getInt("EXP_MOST"));
				result.setBTSRadius(db.getFloat("BTS_RADIUS"));
				result.setMRTRadius(db.getFloat("MRT_RADIUS"));
				result.setIdSession(db.getString("ID_SESSION"));
				result.setMail(db.getString("MAIL"));
				result.setTimeStamp(db.getString("TIMESTAMP"));
				result.setIdSearchRef(db.getInt("ID_SEARCH_REF"));
				result.setIdEmp(db.getInt("ID_EMP"));
				result.setIdPosition(db.getInt("ID_POSITION"));
				result.setSearchType(db.getInt("SEARCH_TYPE"));
				result.setShowSalary(db.getInt("SHOW_SALARY"));
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

	public static Search getSearch(String idSession, int idSearch,int idLanguage)
	{
		Search result = getLightSearch(idSession, idSearch, idLanguage);
		if(result != null)
		{
			result.fetchDegreeSearchList();
			result.fetchIndustrySearchList();
			result.fetchJobFieldSearchList();
			result.fetchRegionSearchList();
			result.fetchJobTypeSearchList();
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
	public static int update(Search search)
	{
	int result=0;
	String sql="UPDATE INTER_SEARCH SET TITLE=?, TYPE=?, TIMESTAMP=SYSDATE,EXP_LESS=?, EXP_MOST=?, SALARY_LESS=?,SALARY_MOST=?,KEYWORD=?, BTS_RADIUS=?, MRT_RADIUS=? , MAIL=? WHERE ID_SEARCH=? AND ID_JSK=?";
	DBManager db=null;
	try
	{
		db=new DBManager();
		db.createPreparedStatement(sql);
		db.setString(1, search.getTitle());
		db.setInt(2, search.getType());
		db.setInt(3, search.getExpLess());
		db.setInt(4, search.getExpMost());
		db.setInt(5, search.getSalaryLess());
		db.setInt(6, search.getSalaryMost());
		db.setString(7, search.getKeyword());
		db.setFloat(8, search.getBTSRadius());
		db.setFloat(9, search.getMRTRadius());
		db.setString(10, search.getMail());
		db.setInt(11, search.getIdSearch());
		db.setInt(12, search.getIdJsk());
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
	
	public static int updateIdJskForSearch(Search search)
	{
		String sql = "";
		DBManager db=null;
		int result = 0;
		try
		{
			db = new DBManager();
			sql = "UPDATE INTER_SEARCH SET ID_JSK = ? WHERE ID_SEARCH = ?";
			db.createPreparedStatement(sql);
			db.setInt(1,search.getIdJsk());
			db.setInt(2,search.getIdSearch());
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
		return result ;
	}
	
	public static int isExist(int idJsk, int searchType, int idSearch)
	{
		int idSearchExist = -1;
		List<Search> searchList = getAllSearch(idJsk, searchType, 11);
		Search searchToCheck = getSearch(idJsk, idSearch, 11);
		if(searchToCheck!=null)
		{
			searchToCheck.setIdSearchRef(-1);
			searchToCheck.setType(searchType);
			searchToCheck.setIdSession("");
			searchToCheck.setTotalJobList(0);
			String param1=searchToCheck.getJsonParameter();
			param1=param1.replaceAll(":"+searchToCheck.getIdSearch()+",", "");
			for(int i=0; i<searchList.size(); i++)
			{
				idSearchExist = searchList.get(i).getIdSearch();
				searchList.get(i).setIdSearchRef(-1);
				searchList.get(i).setType(searchType);
				searchList.get(i).setIdSession("");
				searchList.get(i).setTotalJobList(0);
				String param2=searchList.get(i).getJsonParameter();
				param2=param2.replaceAll(":"+searchList.get(i).getIdSearch()+",", "");
				if(param1.equals(param2)) 
				{
					return idSearchExist;
				}
			}
		}
		return -1;
	}
}
