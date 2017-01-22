package com.topgun.search;

import java.util.*;

import com.google.gson.Gson;
import com.topgun.resume.Employer;
import com.topgun.resume.EmployerManager;
import com.topgun.util.DBManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class Search 
{
	public int ID_TYPE_TEMP = 10;
	
	private int idJsk;
	private int idSearch;
	private String idSession;
	private String title;
	private int type=0;
	private int idLanguage;
	private String keyword;
	private int salaryLess=-1;
	private int salaryMost=-1;
	private int expLess=-1;
	private int expMost=-1;
	private float BTSRadius;
	private float MRTRadius;
	private int totalJobList;
	private int newlyJobList;
	private String mail ;
	private String notification ;
	private int idSearchRef ;
	private String timeStamp ;
	private int idEmp;
	private int idPosition;
	private int searchType;
	private int showSalary;
	
	private List<JobTypeSearch> jobTypeSearchList;
	private List<DegreeSearch> degreeSearchList;
	private List<IndustrySearch> industrySearchList;
	private List<JobFieldSearch> jobFieldSearchList;
	private List<RegionSearch> regionSearchList;
	
	public Search(int idJsk, int idLanguage)
	{
		this.idJsk=idJsk;
		this.idLanguage=idLanguage;
	}
	
	public Search(int idJsk, int idSearch, int idLanguage)
	{
		this.idJsk=idJsk;
		this.idLanguage=idLanguage;
		this.idSearch=idSearch;
	}
	
	public void fetchJobFieldSearchList()
	{
		this.jobFieldSearchList=new ArrayList<JobFieldSearch>();
		DBManager db=null;
		try
		{
			String sql=	"SELECT "
						+ "		C.ID_JOBFIELD,A.FIELD_NAME AS NAME_ENG,B.FIELD_NAME AS NAME_THA "
						+ "FROM	"
						+ "	INTER_JOBFIELD_LANGUAGE  A, INTER_JOBFIELD_LANGUAGE  B ,INTER_SEARCH_JOBFIELD C "
						+ "WHERE "
						+ "		A.ID_LANGUAGE=11 AND B.ID_LANGUAGE=38 AND"
						+ "		A.ID_FIELD=B.ID_FIELD AND"
						+ "		C.ID_JOBFIELD=A.ID_FIELD AND"
						+ "		C.ID_SEARCH=? ";
			
			if(this.idLanguage==11)
			{
				sql+=" ORDER BY NAME_ENG";
			}
			else
			{
				sql+=" ORDER BY NAME_THA";				
			}
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,this.idSearch);
			db.executeQuery();
			while(db.next())
			{
				JobFieldSearch jobFieldSearch=new JobFieldSearch(db.getInt("ID_JOBFIELD"),this.idLanguage);
				jobFieldSearch.setIdSearch(this.idSearch);
				jobFieldSearch.setNameEng(db.getString("NAME_ENG"));
				jobFieldSearch.setNameTha(db.getString("NAME_THA"));
				this.jobFieldSearchList.add(jobFieldSearch);
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
		
		if(this.jobFieldSearchList != null)
		{
			for (JobFieldSearch jobFieldSearch : jobFieldSearchList) {
				jobFieldSearch.fetchAllSubGroupSearchList();
			}
		}
	}
	
	public void fetchIndustrySearchList()
	{
		this.industrySearchList=new ArrayList<IndustrySearch>();
		DBManager db=null;
		try
		{
			String sql=	"SELECT "
						+ "		C.ID_INDUSTRY,A.INDUSTRY_NAME AS NAME_ENG,B.INDUSTRY_NAME AS NAME_THA "
						+ "FROM	"
						+ "	INTER_INDUSTRY_LANGUAGE  A, INTER_INDUSTRY_LANGUAGE  B ,INTER_SEARCH_INDUSTRY C "
						+ "WHERE "
						+ "		A.ID_LANGUAGE=11 AND B.ID_LANGUAGE=38 AND"
						+ "		A.ID_INDUSTRY=B.ID_INDUSTRY AND"
						+ "		C.ID_INDUSTRY=A.ID_INDUSTRY AND"
						+ "		C.ID_SEARCH=? ";
			
			if(this.idLanguage==11)
			{
				sql+=" ORDER BY NAME_ENG";
			}
			else
			{
				sql+=" ORDER BY NAME_THA";				
			}
			
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,this.idSearch);
			db.executeQuery();
			while(db.next())
			{
				IndustrySearch industrySearch=new IndustrySearch(db.getInt("ID_INDUSTRY"),this.idLanguage);
				industrySearch.setIdSearch(this.idSearch);
				industrySearch.setNameEng(db.getString("NAME_ENG"));
				industrySearch.setNameTha(db.getString("NAME_THA"));
				this.industrySearchList.add(industrySearch);
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
	
	public void fetchDegreeSearchList()
	{
		this.degreeSearchList=new ArrayList<DegreeSearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ "		ID_DEGREE, NAME_ENG, NAME_THA "
					+ "FROM	"
					+ "		INTER_SEARCH_DEGREE_LANGUAGE ,INTER_SEARCH_DEGREE "
					+ "WHERE "
					+ "	ID_DEGREE=ID AND ID_SEARCH=? ";
			
			if(this.idLanguage==11)
			{
				sql+=" ORDER BY NAME_ENG";
			}
			else
			{
				sql+=" ORDER BY NAME_THA";				
			}
			
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,this.idSearch);
			db.executeQuery();
			while(db.next())
			{
				DegreeSearch degreeSearch=new DegreeSearch(db.getString("ID_DEGREE"),this.idLanguage);
				degreeSearch.setIdSearch(this.idSearch);
				degreeSearch.setNameEng(db.getString("NAME_ENG"));
				degreeSearch.setNameTha(db.getString("NAME_THA"));
				this.degreeSearchList.add(degreeSearch);
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
	
	public void fetchRegionSearchList()
	{
		this.regionSearchList=new ArrayList<RegionSearch>();
		DBManager db=null;
		try
		{
			String sql=	"SELECT "
					+ 	"	A.ID_REGION,A.NAME_THA,A.NAME_ENG "
					+ 	"FROM "
					+ 	"	INTER_SEARCH_MASTERREGION A, INTER_SEARCH_REGION B "
					+ 	"WHERE "
					+ 	"	A.ID_REGION=B.ID_REGION AND ID_SEARCH=? ";
			
			if(this.idLanguage==11)
			{
				sql+=" ORDER BY NAME_ENG";
			}
			else
			{
				sql+=" ORDER BY NAME_THA";				
			}
			
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,this.idSearch);
			db.executeQuery();
			while(db.next())
			{
				RegionSearch regionSearch=new RegionSearch(db.getInt("ID_REGION"),this.idLanguage);
				regionSearch.setIdSearch(this.idSearch);
				regionSearch.setNameEng(db.getString("NAME_ENG"));
				regionSearch.setNameTha(db.getString("NAME_THA"));
				this.regionSearchList.add(regionSearch);
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
		if(regionSearchList != null)
		{
			for (RegionSearch regionSearch : regionSearchList) {
				regionSearch.fetchStateSearchList(); //fetch all state and city from database
			}
		}
	}
	
	public void fetchJobTypeSearchList()
	{
		this.jobTypeSearchList=new ArrayList<JobTypeSearch>();
		DBManager db=null;
		try
		{
			String sql=	"SELECT "
					+ 	"	A.ID_TYPE,B.NAME_THA,B.NAME_ENG "
					+ 	"FROM "
					+ 	"	INTER_SEARCH_JOBTYPE A, "
					+	" ("
					+	" 	SELECT A.ID_TYPE , A.NAME_ENG, B.NAME_THA FROM "
					+	"		(SELECT ID_TYPE,TYPE_NAME AS NAME_ENG FROM INTER_TYPEOFJOB_LANGUAGE WHERE ID_LANGUAGE=11) A, "
					+	" 		(SELECT ID_TYPE,TYPE_NAME AS NAME_THA FROM INTER_TYPEOFJOB_LANGUAGE WHERE ID_LANGUAGE=38) B "
					+	" 	WHERE "
					+	" 	A.ID_TYPE = B.ID_TYPE "
					+ 	" ) B "
					+ 	" WHERE "
					+ 	"	A.ID_TYPE=B.ID_TYPE AND ID_SEARCH=? ";
			
			if(this.idLanguage==11)
			{
				sql+=" ORDER BY NAME_ENG";
			}
			else
			{
				sql+=" ORDER BY NAME_THA";				
			}
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,this.idSearch);
			db.executeQuery();
			while(db.next())
			{
				JobTypeSearch jobTypeSearch = new JobTypeSearch(db.getInt("ID_TYPE"), this.idLanguage);
				jobTypeSearch.setIdSearch(this.idSearch);
				jobTypeSearch.setNameEng(db.getString("NAME_ENG"));
				jobTypeSearch.setNameTha(db.getString("NAME_THA"));
				
				this.jobTypeSearchList.add(jobTypeSearch);
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
	
	public int add()
	{
		int latestId = 0;
		String sql="INSERT INTO "
				+ "		INTER_SEARCH (ID_SEARCH, ID_JSK, TITLE, TYPE, TIMESTAMP, EXP_LESS, EXP_MOST, SALARY_LESS,SALARY_MOST, KEYWORD, BTS_RADIUS, MRT_RADIUS, ID_SESSION,MAIL,ID_EMP,ID_POSITION,SEARCH_TYPE,SHOW_SALARY) "
				+ "VALUES(SEQ_SEARCH.NEXTVAL,?,?,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		DBManager db=null;
		try
		{   
			if(this.idJsk>0) this.idSession="";
			db=new DBManager();
			db.createPreparedStatement(sql, "ID_SEARCH");
			db.setInt(1, this.idJsk);
			db.setString(2, this.title);
			db.setInt(3, this.type);
			db.setInt(4, this.expLess);
			db.setInt(5, this.expMost);
			db.setInt(6, this.salaryLess);
			db.setInt(7, this.salaryMost);
			db.setString(8, this.keyword);
			db.setFloat(9, this.BTSRadius);
			db.setFloat(10, this.MRTRadius);
			db.setString(11, this.idSession);
			db.setString(12, this.mail);
			db.setInt(13, this.idEmp);
			db.setInt(14, this.idPosition);
			db.setInt(15, this.searchType);
			db.setInt(16, this.showSalary);
			if(db.executeUpdate()> 0)
			{
				latestId = db.getGeneratedId();
				this.idSearch = latestId;
				//this.addDegreeSearchList();
				//this.addIndustrySearchList();
				this.addJobFieldSearchList();
				this.addRegionSearchList();
				//this.addJobTypeSearchList();
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
		
		return latestId;
	}
	
	public int update()
	{
		int result=0;
		String sql="UPDATE "
				+ "		INTER_SEARCH "
				+ "SET "
				+ "		TITLE=?, TYPE=?, TIMESTAMP=SYSDATE, LAST_VIEW = SYSDATE, "
				+ "		EXP_LESS=?, EXP_MOST=?, SALARY_LESS=?,SALARY_MOST=?, "
				+ "		KEYWORD=?, BTS_RADIUS=?, MRT_RADIUS=? , MAIL=? , ID_SEARCH_REF = ?, "
				+ " 	SEARCH_TYPE = ?, ID_EMP = ?, ID_POSITION = ?"
				+ "WHERE "
				+ "		ID_SEARCH=? AND ID_JSK=?" + (this.idJsk < 0 ? " AND ID_SESSION = ? " : "");
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, this.title);
			db.setInt(2,this.type);
			db.setInt(3, this.expLess);
			db.setInt(4, this.expMost);
			db.setInt(5, this.salaryLess);
			db.setInt(6, this.salaryMost);
			db.setString(7, this.keyword);
			db.setFloat(8, this.BTSRadius);
			db.setFloat(9, this.MRTRadius);
			db.setString(10, this.mail);
			db.setInt(11, this.idSearchRef);
			db.setInt(12, this.searchType);
			db.setInt(13, this.idEmp);
			db.setInt(14, this.idPosition);
			db.setInt(15, this.idSearch);
			db.setInt(16, this.idJsk);
			if(this.idJsk < 0)
			{
				db.setString(17, this.idSession);
			}
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
		
		if(result > 0)
		{
			this.addDegreeSearchList();
			this.addJobTypeSearchList();
			this.addIndustrySearchList();
			this.addJobFieldSearchList();
			this.addRegionSearchList();
			this.addJobTypeSearchList();
			result=1;
		}
		return result;
	}
	
	public int updateForNewlyJobs()
	{
		int result=0;
		String sql="UPDATE "
				+ "		INTER_SEARCH "
				+ "SET "
				+ "		TITLE=?, TYPE=?, TIMESTAMP=SYSDATE, "
				+ "		EXP_LESS=?, EXP_MOST=?, SALARY_LESS=?,SALARY_MOST=?, "
				+ "		KEYWORD=?, BTS_RADIUS=?, MRT_RADIUS=? , MAIL=? , ID_SEARCH_REF = ?, "
				+ " 	SEARCH_TYPE = ?, ID_EMP = ?, ID_POSITION = ?"
				+ "WHERE "
				+ "		ID_SEARCH=? AND ID_JSK=?"+(this.idJsk < 0 ? " AND ID_SESSION = ? " : "");
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, this.title);
			db.setInt(2,this.type);
			db.setInt(3, this.expLess);
			db.setInt(4, this.expMost);
			db.setInt(5, this.salaryLess);
			db.setInt(6, this.salaryMost);
			db.setString(7, this.keyword);
			db.setFloat(8, this.BTSRadius);
			db.setFloat(9, this.MRTRadius);
			db.setString(10, this.mail);
			db.setInt(11, this.idSearchRef);
			db.setInt(12, this.searchType);
			db.setInt(13, this.idEmp);
			db.setInt(14, this.idPosition);
			db.setInt(15, this.idSearch);
			db.setInt(16, this.idJsk);
			if(this.idJsk < 0)
			{
				db.setString(17, this.idSession);
			}
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
		
		if(result > 0)
		{
			this.addDegreeSearchList();
			this.addJobTypeSearchList();
			this.addIndustrySearchList();
			this.addJobFieldSearchList();
			this.addRegionSearchList();
			this.addJobTypeSearchList();
			result=1;
		}
		return result;
	}
	
	public int delete()
	{
		int result=0;
		String sql="UPDATE INTER_SEARCH SET DELETED = 1 WHERE ID_SEARCH=? AND ID_JSK=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJsk);
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
	
	private int getMaxIdSearch() 
	{
		int result=-1;
		String sql="SELECT MAX(ID_SEARCH) AS MAXID FROM INTER_SEARCH WHERE ID_JSK=?";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(sql);
    		db.setInt(1, this.idJsk);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=db.getInt("MAXID");
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
	
	private int getMaxIdSearchBySession()
	{
		int result=-1;
		String sql="SELECT MAX(ID_SEARCH) AS MAXID FROM INTER_SEARCH WHERE ID_SESSION=? AND ID_JSK=-1";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(sql);
    		db.setString(1, this.idSession);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=db.getInt("MAXID");
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
	
	private int addJobFieldSearchList()
	{
		int result=0;
		this.deleteJobFieldSearchList();
		if(this.jobFieldSearchList!=null)
		{
			for(int i=0; i<this.jobFieldSearchList.size(); i++)
			{
				JobFieldSearch jobFieldSearch=this.jobFieldSearchList.get(i);
				jobFieldSearch.setIdSearch(this.idSearch);
				result+=jobFieldSearch.add();
			}
		}
		return result;
	}

	private int deleteJobFieldSearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_JOBFIELD WHERE ID_SEARCH=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
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
	
	public String getJobFieldSearchDescription()
	{
		String result="";
		
		if(this.jobFieldSearchList!=null)
		{
			for(int i=0; i<this.jobFieldSearchList.size(); i++)
			{
				String tmp=this.jobFieldSearchList.get(i).getDescription();
				if(!tmp.equals(""))
				{
					if(result.equals(""))
					{
						result=tmp;						
					}
					else
					{
						result+=", "+tmp;						
					}
				}
			}
		}
		return result;
	}
	
	private int addJobTypeSearchList()
	{
		int result = 0;
		this.deleteJobTypeSearchList();
		if(this.jobTypeSearchList!=null)
		{
			for(int i=0; i<this.jobTypeSearchList.size(); i++)
			{
				JobTypeSearch jobTypeSearch=this.jobTypeSearchList.get(i);
				jobTypeSearch.setIdSearch(this.idSearch);
				result+=jobTypeSearch.add();
			}
		}
		return result;
	}
	
	private int deleteJobTypeSearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_JOBTYPE WHERE ID_SEARCH=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
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
	
	public String getJobTypeSearchDescription()
	{
		String result="";
		if(this.jobTypeSearchList!=null)
		{
			for(int i=0; i<this.jobTypeSearchList.size(); i++)
			{
				String buf=this.jobTypeSearchList.get(i).getDescription();
				if(!buf.equals(""))
				{
					if(result.equals(""))
					{
						result=buf;					
					}	
					else
					{
						result+=", "+buf;						
					}
				}
			}
		}
		return result;
	}
	
	private int addIndustrySearchList()
	{
		int result=0;
		this.deleteIndustrySearchList();
		if(this.industrySearchList!=null)
		{
			for(int i=0; i<this.industrySearchList.size(); i++)
			{
				IndustrySearch industrySearch=this.industrySearchList.get(i);
				industrySearch.setIdSearch(this.idSearch);
				result+=industrySearch.add();
			}
		}
		return result;
	}

	private int deleteIndustrySearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_INDUSTRY WHERE ID_SEARCH=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
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
	
	public String getIndustrySearchDescription()
	{
		String result="";
		if(this.industrySearchList!=null)
		{
			for(int i=0; i<this.industrySearchList.size(); i++)
			{
				String buf=this.industrySearchList.get(i).getDescription();
				if(!buf.equals(""))
				{
					if(result.equals(""))
					{
						result=buf;					
					}	
					else
					{
						result+=", "+buf;						
					}
				}
			}
		}
		return result;
	}
	
	private int addDegreeSearchList()
	{
		int result=0;
		this.deleteDegreeSearchList();
		if(this.degreeSearchList!=null)
		{
			for(int i=0; i<this.degreeSearchList.size(); i++)
			{
				if(!this.degreeSearchList.get(i).getIdDegree().equals(""))
				{
					DegreeSearch degreeSearch=this.degreeSearchList.get(i);
					degreeSearch.setIdSearch(this.idSearch);
					result+=degreeSearch.add();
				}
			}
		}
		return result;
	}
	
	public int deleteDegreeSearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_DEGREE WHERE ID_SEARCH=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
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
	
	public String getDegreeSearchDescription()
	{
		String result="";
		if(this.degreeSearchList!=null)
		{
			for(int i=0; i<this.degreeSearchList.size(); i++)
			{
				String buf=this.degreeSearchList.get(i).getDescription();
				if(!buf.equals(""))
				{
					if(result.equals(""))
					{
						result=buf;						
					}
					else
					{
						result+=", "+buf;						
					}
				}
			}
		}
		return result;
	}
	
	
	private int addRegionSearchList()
	{
		int result=0;
		this.deleteRegionSearchList();
		if(this.regionSearchList!=null)
		{
			for(int i=0; i<this.regionSearchList.size(); i++)
			{
				RegionSearch regionSearch=this.regionSearchList.get(i);
				regionSearch.setIdSearch(this.idSearch);
				result+=regionSearch.add();
			}
		}
		return result;
	}
	
	private int deleteRegionSearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_REGION WHERE ID_SEARCH=?"; 
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
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
	
	public String getRegionSearchDescription()
	{
		String result="";
		if(this.regionSearchList!=null)
		{
			for(int i=0; i<this.regionSearchList.size(); i++)
			{
				String buf = "";
				if(this.regionSearchList.get(i).getIdRegion() == 9)
				{
					buf = this.getBtsSearchDescription();
				}
				else if(this.regionSearchList.get(i).getIdRegion() == 10)
				{
					buf = this.getMrtSearchDescription();
				}
				else
				{
					buf = this.regionSearchList.get(i).getDescription();
				}
				if(!buf.equals(""))
				{
					if(result.equals(""))
					{
						result=buf;						
					}
					else
					{
						result+=", "+buf;						
					}
				}
			}
		}
		return result;
	}

	public String getRegionSearchParams()
	{
		String result="";
		String region="";
		if(this.regionSearchList!=null)
		{			
			for(int i=0; i<this.regionSearchList.size(); i++)
			{
				String state="\"0\":{}";
				if(this.regionSearchList.get(i).getStateSearchList()!=null)
				{
					for(int j=0; j<this.regionSearchList.get(i).getStateSearchList().size(); j++)
					{
						String city="\"0\":{}";
						if(this.getRegionSearchList().get(i).getStateSearchList().get(j).getCitySearchList()!=null)
						{
							for(int k=0; k<this.regionSearchList.get(i).getStateSearchList().get(j).getCitySearchList().size(); k++)
							{
								if(city.equals("\"0\":{}"))
								{
									city="\""+this.regionSearchList.get(i).getStateSearchList().get(j).getCitySearchList().get(k).getIdCity()+"\":{}";
								}
								else
								{
									city+=",\""+this.regionSearchList.get(i).getStateSearchList().get(j).getCitySearchList().get(k).getIdCity()+"\":{}";
								}
							}
						}
						
						if(state.equals("\"0\":{}"))
						{
							state="\""+this.regionSearchList.get(i).getStateSearchList().get(j).getIdState()+"\":{"+city+"}";
						}
						else
						{
							state+=",\""+this.regionSearchList.get(i).getStateSearchList().get(j).getIdState()+"\":{"+city+"}";
						}
					}					
				}
				if(region.equals(""))
				{
					region="\""+this.regionSearchList.get(i).getIdRegion()+"\":{"+state+"}";
				}
				else
				{
					region+=",\""+this.regionSearchList.get(i).getIdRegion()+"\":{"+state+"}";
				}
			}
		}
		if(!region.equals(""))
		{
			result="{"+region+"}";
		}
		return result;
	}

	public String getJobFieldSearchParams()
	{
		String result="";
		String jobField="";
		if(this.jobFieldSearchList!=null)
		{			
			for(int i=0; i<this.jobFieldSearchList.size(); i++)
			{
				if(this.jobFieldSearchList.get(i).getSubGroupSearchList()!=null)
				{
					String subGroup="\"0\":{}";
					for(int j=0; j<this.jobFieldSearchList.get(i).getSubGroupSearchList().size(); j++)
					{
						if(subGroup.equals("\"0\":{}"))
						{
							subGroup="\""+this.jobFieldSearchList.get(i).getSubGroupSearchList().get(j).getIdSubGroup()+"\":{}";
						}
						else
						{
							subGroup+=",\""+this.jobFieldSearchList.get(i).getSubGroupSearchList().get(j).getIdSubGroup()+"\":{}";
						}
					}
					if(jobField.equals(""))
					{
						jobField="\""+this.jobFieldSearchList.get(i).getIdJobField()+"\":{"+subGroup+"}";
						
					}
					else
					{
						jobField+=",\""+this.jobFieldSearchList.get(i).getIdJobField()+"\":{"+subGroup+"}";
						
					}
				}
			}
		}
		if(!jobField.equals(""))
		{
			result="{"+jobField+"}";
		}
		return result;
	}
	
	public String getIndustrySearchParams()
	{
		String result="";
		if(this.industrySearchList!=null)
		{			
			String industry="";
			for(int i=0; i<this.industrySearchList.size(); i++)
			{
				if(industry.equals(""))
				{
					industry="\""+this.industrySearchList.get(i).getIdIndustry()+"\":{}";					
				}
				else
				{
					industry+=",\""+this.industrySearchList.get(i).getIdIndustry()+"\":{}";			
				}
			}
			if(!industry.equals(""))
			{
				result="{"+industry+"}";
			}
		}
		return result;	
	}
	
	public String getDegreeSearchParams()
	{
		String result="";
		if(this.degreeSearchList!=null)
		{			
			String degree="";
			for(int i=0; i<this.degreeSearchList.size(); i++)
			{
				if(degree.equals(""))
				{
					degree="\""+this.degreeSearchList.get(i).getIdDegree()+"\":{}";			
				}
				else
				{
					degree+=",\""+this.degreeSearchList.get(i).getIdDegree()+"\":{}";	
				}
			}
			if(!degree.equals(""))
			{
				result="{"+degree+"}";
			}
		}
		return result;	
	}
	
	public String getJobTypeSearchParams()
	{
		String result="";
		if(this.jobTypeSearchList!=null)
		{			
			String jobtype="";
			for(int i=0; i<this.jobTypeSearchList.size(); i++)
			{
				if(jobtype.equals(""))
				{
					jobtype="\""+this.jobTypeSearchList.get(i).getIdJobType()+"\":{}";			
				}
				else
				{
					jobtype+=",\""+this.jobTypeSearchList.get(i).getIdJobType()+"\":{}";	
				}
			}
			if(!jobtype.equals(""))
			{
				result="{"+jobtype+"}";
			}
		}
		return result;	
	}
	
	public String getSalarySearchDescription()
	{
		String result = "";
		PropertiesManager propertiesManager = new PropertiesManager();
		String locale = "";
		if(this.idLanguage == 11)
		{
			locale = "en_TH";
		}
		else
		{
			locale = "th_TH";
		}
		if(this.salaryLess > 0 && this.salaryMost > 0)
		{
			result = Util.getFormatNumber(this.salaryLess, "#,###.##")+ " - " + Util.getFormatNumber(this.salaryMost, "#,###.##") + " " + propertiesManager.getMessage(locale, "BAHT");
		}
		else
		{
			if(this.salaryLess > 0)
			{
				result = propertiesManager.getMessage(locale, "MORE_EQUAL") + " " + Util.getFormatNumber(this.salaryLess, "#,###.##") + " " + propertiesManager.getMessage(locale, "BAHT");
			}
			else if(this.salaryMost > 0)
			{
				result = propertiesManager.getMessage(locale, "LESS_EQUAL") + " " + Util.getFormatNumber(this.salaryMost, "#,###.##") + " " + propertiesManager.getMessage(locale, "BAHT");
			}
		}
		return result;
	}
	
	public String getExperienceSearchDescription()
	{
		String result = "";
		PropertiesManager propertiesManager = new PropertiesManager();
		String locale = "";
		if(this.idLanguage == 11)
		{
			locale = "en_TH";
		}
		else
		{
			locale = "th_TH";
		}
		if(this.expLess == -1 && this.expMost == -1)
		{
			result = "";
		}
		else if(this.expLess == this.expMost)
		{
			result = this.expLess+" "+propertiesManager.getMessage(locale, "YEAR");
		}
		else
		{
			if(this.expMost < 0)
			{
				if(this.expMost == -2)
				{
					String yearUpper= propertiesManager.getMessage(locale, "YEAR_UPPER");
					result = yearUpper.replace("{0}", this.expLess+"");
				}
				else
				{
					result = this.expLess+" "+propertiesManager.getMessage(locale, "YEAR");
				}
			}
			else
			{
				result = this.expLess+" - "+this.expMost+" "+propertiesManager.getMessage(locale, "YEAR");
			}
		}
		return result;
	}
	
	public String getBtsSearchDescription()
	{
		String result = "";
		PropertiesManager propertiesManager = new PropertiesManager();
		String locale = "";
		if(this.idLanguage == 11)
		{
			locale = "en_TH";
		}
		else
		{
			locale = "th_TH";
		}
		if(this.regionSearchList != null)
		{
			for(int i = 0 ; i < regionSearchList.size() ; i++)
			{
				if(this.regionSearchList.get(i).getIdRegion() == 9)
				{
					result = this.regionSearchList.get(i).getDescription();
					result = result.replace("S(", "S : "+propertiesManager.getMessage(locale, "RADIUS") + " " + this.BTSRadius +" "+propertiesManager.getMessage(locale, "KILOMETRE")+" (");
					break;
				}
			}
		}
		return result;
	}
	
	public String getMrtSearchDescription()
	{
		String result = "";
		PropertiesManager propertiesManager = new PropertiesManager();
		String locale = "";
		if(this.idLanguage == 11)
		{
			locale = "en_TH";
		}
		else
		{
			locale = "th_TH";
		}
		if(this.regionSearchList != null)
		{
			for(int i = 0 ; i < regionSearchList.size() ; i++)
			{
				if(this.regionSearchList.get(i).getIdRegion() == 10)
				{
					result = this.regionSearchList.get(i).getDescription();
					result = result.replace("T(", "T : "+propertiesManager.getMessage(locale, "RADIUS") + " " + this.MRTRadius +" "+propertiesManager.getMessage(locale, "KILOMETRE")+" (");
					break;
				}
			}
		}
		return result;
	}
	
	public String getOverSeaSearchDescription()
	{
		String result = "";
		if(this.regionSearchList != null)
		{
			for(int i = 0 ; i < regionSearchList.size() ; i++)
			{
				if(this.regionSearchList.get(i).getIdRegion() == 12)
				{
					result = this.regionSearchList.get(i).getDescription();
					break;
				}
			}
		}
		return result;
	}
	
	public String getIndustrialSearchDescription()
	{
		String result = "";
		if(this.regionSearchList != null)
		{
			for(int i = 0 ; i < regionSearchList.size() ; i++)
			{
				if(this.regionSearchList.get(i).getIdRegion() == 11)
				{
					result = this.regionSearchList.get(i).getDescription();
					break;
				}
			}
		}
		return result;
	}
	
	public static String encodeUnicode(String str) 
	{
		StringBuilder sb = new StringBuilder();
		char[] chars = str.toCharArray();
		for (char ch : chars) 
		{
			if (' ' <= ch && ch <= '\u007E')
				sb.append(ch);
			else
				sb.append(String.format("\\u%04x", ch & 0xFFFF));
		}
		return sb.toString();
	}
	
	public String getJsonParameter()
	{
		String result="";
		try
		{
			result=encodeUnicode(new Gson().toJson(this));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public String getDescription()
	{
		String result="";
		if(this.idEmp > 0)
		{
			Employer employer = new EmployerManager().get(this.idEmp);
			if(employer != null)
			{
				result = this.idLanguage == 11 ? employer.getNameEng() : employer.getNameThai();
			}
		}
		else if(!this.keyword.equals(""))
		{
			if(result.equals(""))
			{
				result=this.keyword;
			}
		}
		
		if(!this.getDegreeSearchDescription().equals(""))
		{
			if(!result.equals(""))
			{
				result+=", ";
			}
			result+=this.getDegreeSearchDescription();
		}
		
		if(!this.getSalarySearchDescription().equals(""))
		{
			if(!result.equals(""))
			{
				result+=", ";
			}
			result+=this.getSalarySearchDescription();
		}
		
		if(!this.getExperienceSearchDescription().equals(""))
		{
			if(!result.equals(""))
			{
				result+=", ";
			}
			result+=this.getExperienceSearchDescription();
		}
		
		if(!this.getIndustrySearchDescription().equals(""))
		{
			if(!result.equals(""))
			{
				result+=", ";
			}
			result+=this.getIndustrySearchDescription();
		}
		
		if(!this.getJobFieldSearchDescription().equals(""))
		{
			if(!result.equals(""))
			{
				result+=", ";
			}
			result+=this.getJobFieldSearchDescription();
		}
		
		if(!this.getRegionSearchDescription().equals(""))
		{
			if(!result.equals(""))
			{
				result+=", ";
			}
			result+=this.getRegionSearchDescription();
		}		
		
		if(!this.getJobTypeSearchDescription().equals(""))
		{
			if(!result.equals(""))
			{
				result+=", ";
			}
			result+=this.getJobTypeSearchDescription();
		}	
		
		return result;
	}
	
	public int cloneLastView()
	{
		int result = 0;
		DBManager db = null;
		String sql = "";
		try
		{
			db = new DBManager();
			sql = "UPDATE INTER_SEARCH SET LAST_VIEW = (SELECT LAST_VIEW FROM INTER_SEARCH WHERE ID_JSK = ? AND ID_SEARCH = ?) WHERE ID_JSK = ? AND ID_SEARCH = ?";
			db.createPreparedStatement(sql);
			db.setInt(1, this.idJsk);
			db.setInt(2, this.idSearchRef);
			db.setInt(3, this.idJsk);
			db.setInt(4, this.idSearch);
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
	
	public boolean isEmpty()
	{
		boolean result = false;
		if(this.expLess == -1
			&& this.expMost == -1
			&& this.expMost == -1
			&& this.salaryMost == -1
			&& this.salaryLess == -1
			&& this.keyword.isEmpty()
			&& this.isDegreeSearchEmpty()
			&& this.isIndustrySearchEmpty()
			&& this.isJobFieldSearchEmpty()
			&& this.isJobTypeSearchEmpty()
			&& this.isRegionSearchEmpty()
			)
		{
			result = true;
		}
		return result;
	}
	
	public boolean isDegreeSearchEmpty()
	{
		return this.degreeSearchList == null || this.degreeSearchList.size() == 0 ? true : false;
	}
	
	public boolean isIndustrySearchEmpty()
	{
		return this.industrySearchList == null || this.industrySearchList.size() == 0 ? true : false;
	}
	
	public boolean isJobFieldSearchEmpty()
	{
		return this.jobFieldSearchList == null || this.jobFieldSearchList.size() == 0 ? true : false;
	}
	
	public boolean isJobTypeSearchEmpty()
	{
		return this.jobTypeSearchList == null || this.jobTypeSearchList.size() == 0 ? true : false;
	}
	
	public boolean isRegionSearchEmpty()
	{
		return this.regionSearchList == null || this.regionSearchList.size() == 0 ? true : false;
	}
	

	public int getIdJsk()
	{
		return idJsk;
	}

	public void setIdJsk(int idJsk)
	{
		this.idJsk = idJsk;
	}

	public int getIdSearch()
	{
		return idSearch;
	}

	public void setIdSearch(int idSearch)
	{
		this.idSearch = idSearch;
	}

	public String getIdSession()
	{
		return idSession;
	}

	public void setIdSession(String idSession)
	{
		this.idSession = idSession;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getIdLanguage()
	{
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage)
	{
		this.idLanguage = idLanguage;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = Util.getStr(keyword);
	}

	public int getSalaryLess()
	{
		return salaryLess;
	}

	public void setSalaryLess(int salaryLess)
	{
		this.salaryLess = salaryLess;
	}

	public int getSalaryMost()
	{
		return salaryMost;
	}

	public void setSalaryMost(int salaryMost)
	{
		this.salaryMost = salaryMost;
	}

	public int getExpLess()
	{
		return expLess;
	}

	public void setExpLess(int expLess)
	{
		this.expLess = expLess;
	}

	public int getExpMost()
	{
		return expMost;
	}

	public void setExpMost(int expMost)
	{
		this.expMost = expMost;
	}

	public float getBTSRadius()
	{
		return BTSRadius;
	}

	public void setBTSRadius(float bTSRadius)
	{
		BTSRadius = bTSRadius;
	}

	public float getMRTRadius()
	{
		return MRTRadius;
	}

	public void setMRTRadius(float mRTRadius)
	{
		MRTRadius = mRTRadius;
	}

	public List<DegreeSearch> getDegreeSearchList()
	{
		return degreeSearchList;
	}

	public void setDegreeSearchList(List<DegreeSearch> degreeSearchList)
	{
		this.degreeSearchList = degreeSearchList;
	}

	public List<IndustrySearch> getIndustrySearchList()
	{
		return industrySearchList;
	}

	public void setIndustrySearchList(List<IndustrySearch> industrySearchList)
	{
		this.industrySearchList = industrySearchList;
	}

	public List<JobFieldSearch> getJobFieldSearchList()
	{
		return jobFieldSearchList;
	}

	public void setJobFieldSearchList(List<JobFieldSearch> jobFieldSearchList)
	{
		this.jobFieldSearchList = jobFieldSearchList;
	}

	public List<RegionSearch> getRegionSearchList()
	{
		return regionSearchList;
	}

	public void setRegionSearchList(List<RegionSearch> regionSearchList)
	{
		this.regionSearchList = regionSearchList;
	}

	public int getTotalJobList()
	{
		return totalJobList;
	}

	public void setTotalJobList(int totalJobList)
	{
		this.totalJobList = totalJobList;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getIdSearchRef() {
		return idSearchRef;
	}

	public void setIdSearchRef(int idSearchRef) {
		this.idSearchRef = idSearchRef;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNewlyJobList() {
		return newlyJobList;
	}

	public void setNewlyJobList(int newlyJobList) {
		this.newlyJobList = newlyJobList;
	}

	public List<JobTypeSearch> getJobTypeSearchList() {
		return jobTypeSearchList;
	}

	public void setJobTypeSearchList(List<JobTypeSearch> jobTypeSearchList) {
		this.jobTypeSearchList = jobTypeSearchList;
	}

	public int getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}

	public int getIdPosition() {
		return idPosition;
	}

	public void setIdPosition(int idPosition) {
		this.idPosition = idPosition;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public int getShowSalary() {
		return showSalary;
	}

	public void setShowSalary(int showSalary) {
		this.showSalary = showSalary;
	}
	
}
