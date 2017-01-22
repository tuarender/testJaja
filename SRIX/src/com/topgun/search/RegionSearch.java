package com.topgun.search;
import java.util.*;

import com.topgun.util.DBManager;
import com.topgun.util.PropertiesManager;

public class RegionSearch 
{
	private int idSearch;
	private int idLanguage;
	private int idRegion;
	private String nameEng;
	private String nameTha;
	private List<StateSearch> stateSearchList;
	
	public RegionSearch(int idRegion, int idLanguage)
	{
		this.idRegion=idRegion;
		this.idLanguage=idLanguage;
	}
	
	public RegionSearch(int idSearch,int idRegion, int idLanguage)
	{
		this.idRegion=idRegion;
		this.idLanguage=idLanguage;
		this.idSearch=idSearch;
	}
	
	public void fetchStateSearchList()
	{
		if(this.idRegion==9)
		{
			this.fetchBTS();
		}
		else if(this.idRegion==10)
		{
			this.fetchMRT();
		}
		else if(this.idRegion==11)
		{
			this.fetchIndustrial();
		}
		else if(this.idRegion==12)
		{
			this.fetchOversea();
		}
		else if(this.idRegion == 13)	//Industrial Estate and Metropolitan Region
		{
			this.fetchIndustrialAndMetroPolitan();
		}
		else
		{
			this.fetchState();
		}
	}
	
	private void fetchState()
	{
		this.stateSearchList=new ArrayList<StateSearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ "		A.ID_STATE, A.STATE_NAME AS NAME_ENG,B.STATE_NAME AS NAME_THA "
					+ "FROM "
					+ "		INTER_STATE_LANGUAGE A,INTER_STATE_LANGUAGE B,INTER_SEARCH_STATE C "
					+ "WHERE  "
					+ "		A.ID_LANGUAGE=11 AND B.ID_LANGUAGE=38 AND "
					+ "		A.ID_COUNTRY=216 AND B.ID_COUNTRY=216 AND "
					+ "		A.ID_STATE=B.ID_STATE AND C.ID_SEARCH=? AND "
					+ "		A.ID_STATE=C.ID_STATE AND C.ID_REGION=? ";
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
			db.setInt(2,this.idRegion);
			db.executeQuery();
			while(db.next())
			{
				StateSearch stateSearch=new StateSearch(this.idRegion,db.getInt("ID_STATE"),this.idLanguage);
				stateSearch.setIdSearch(this.idSearch);
				stateSearch.setNameEng(db.getString("NAME_ENG"));
				stateSearch.setNameTha(db.getString("NAME_THA"));
				this.stateSearchList.add(stateSearch);
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
		if(stateSearchList != null)
		{
			for (StateSearch stateSearch : stateSearchList) {
				stateSearch.fetchAllCitySearchList();
			}
		}
	}
	
	//Industrial Estate ID_REGION=11
	private void fetchIndustrial()
	{
		this.stateSearchList=new ArrayList<StateSearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT B.ID_STATE,A.NAME_ENG,A.NAME_THA FROM INTER_INDUSTRIAL A, INTER_SEARCH_STATE B WHERE A.ID=B.ID_STATE AND B.ID_SEARCH=? AND B.ID_REGION=? ";
					
			
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
			db.setInt(2,this.idRegion);
			db.executeQuery();
			while(db.next())
			{
				StateSearch stateSearch=new StateSearch(this.idRegion,db.getInt("ID_STATE"),this.idLanguage);
				stateSearch.setIdSearch(this.idSearch);
				stateSearch.setNameEng(db.getString("NAME_ENG"));
				stateSearch.setNameTha(db.getString("NAME_THA"));
				this.stateSearchList.add(stateSearch);
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
	
	//ID_REGION=9
	private void fetchBTS()
	{
		this.stateSearchList=new ArrayList<StateSearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ "		A.ID, A.NAME_ENG, A.NAME_THA "
					+ "	FROM "
					+ "		INTER_STATION A,INTER_SEARCH_STATE B "
					+ "	WHERE "
					+ "		A.KIND='BTS' AND A.ID=B.ID_STATE AND "
					+ "		B.ID_REGION=9 AND B.ID_SEARCH=? AND B.ID_REGION=? ";
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
			db.setInt(2,this.idRegion);
			db.executeQuery();
			while(db.next())
			{
				StateSearch stateSearch=new StateSearch(this.idRegion,db.getInt("ID"),this.idLanguage);
				stateSearch.setIdSearch(this.idSearch);
				stateSearch.setNameEng(db.getString("NAME_ENG"));
				stateSearch.setNameTha(db.getString("NAME_THA"));
				this.stateSearchList.add(stateSearch);
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
	
	//ID_REGION=10
	private void fetchMRT()
	{
		this.stateSearchList=new ArrayList<StateSearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ "		A.ID, A.NAME_ENG, A.NAME_THA "
					+ "	FROM "
					+ "		INTER_STATION A,INTER_SEARCH_STATE B "
					+ "	WHERE "
					+ "		A.KIND='MRT' AND A.ID=B.ID_STATE AND "
					+ "		B.ID_REGION=10 AND B.ID_SEARCH=? AND B.ID_REGION=? ";
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
			db.setInt(2,this.idRegion);
			db.executeQuery();
			while(db.next())
			{
				StateSearch stateSearch=new StateSearch(this.idRegion,db.getInt("ID"),this.idLanguage);
				stateSearch.setIdSearch(this.idSearch);
				stateSearch.setNameEng(db.getString("NAME_ENG"));
				stateSearch.setNameTha(db.getString("NAME_THA"));
				this.stateSearchList.add(stateSearch);
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
	
	//ID_REGION=12
	private void fetchOversea()
	{
		this.stateSearchList=new ArrayList<StateSearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ "		A.ID_COUNTRY,A.COUNTRY_NAME AS NAME_ENG,B.COUNTRY_NAME AS NAME_THA "
					+ "	FROM "
					+ "		INTER_COUNTRY_LANGUAGE A,INTER_COUNTRY_LANGUAGE B,INTER_SEARCH_STATE C "
					+ "WHERE "
					+ "		A.ID_COUNTRY=B.ID_COUNTRY AND"
					+ "		A.ID_LANGUAGE=11 AND B.ID_LANGUAGE=38 AND "
					+ "		C.ID_STATE=A.ID_COUNTRY AND C.ID_REGION=12 AND"
					+ "		C.ID_SEARCH=? AND C.ID_REGION=?";
			
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
			db.setInt(2,this.idRegion);
			db.executeQuery();
			while(db.next())
			{
				StateSearch stateSearch=new StateSearch(this.idRegion,db.getInt("ID_COUNTRY"),this.idLanguage);
				stateSearch.setIdSearch(this.idSearch);
				stateSearch.setNameEng(db.getString("NAME_ENG"));
				stateSearch.setNameTha(db.getString("NAME_THA"));
				this.stateSearchList.add(stateSearch);
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
	
	//ID_REGION=13
	private void fetchIndustrialAndMetroPolitan()
	{
		this.stateSearchList=new ArrayList<StateSearch>();
		DBManager db=null;
		try
		{
			String sql="  SELECT * "
					+ " FROM (SELECT A.ID_STATE, A.STATE_NAME AS NAME_ENG, B.STATE_NAME AS NAME_THA "
					+ " 		FROM INTER_STATE_LANGUAGE A, "
					+ " 			INTER_STATE_LANGUAGE B, "
					+ " 			INTER_SEARCH_STATE C "
					+ " 		WHERE     A.ID_LANGUAGE = 11 "
					+ " 				AND B.ID_LANGUAGE = 38 "
					+ " 				AND A.ID_COUNTRY = 216 "
					+ " 				AND B.ID_COUNTRY = 216 "
					+ " 				AND A.ID_STATE = B.ID_STATE "
					+ " 				AND A.ID_STATE = C.ID_STATE "
					+ " 				AND C.ID_SEARCH = ? "
					+ " 				AND C.ID_REGION = ? "
					+ " 		UNION "
					+ " 			SELECT ID AS ID_STATE, NAME_ENG, NAME_THA "
					+ " 			FROM INTER_INDUSTRIAL D, INTER_SEARCH_STATE E "
					+ " 			WHERE     D.ID = E.ID_STATE "
					+ " 					AND E.ID_SEARCH = ? "
					+ " 					AND E.ID_REGION = ?) ";
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
			db.setInt(2,this.idRegion);
			db.setInt(3,this.idSearch);
			db.setInt(4,this.idRegion);
			db.executeQuery();
			while(db.next())
			{
				StateSearch stateSearch=new StateSearch(this.idRegion,db.getInt("ID_STATE"),this.idLanguage);
				stateSearch.setIdSearch(this.idSearch);
				stateSearch.setNameEng(db.getString("NAME_ENG"));
				stateSearch.setNameTha(db.getString("NAME_THA"));
				this.stateSearchList.add(stateSearch);
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
		int result=0;
		this.delete();
		String sql="INSERT INTO INTER_SEARCH_REGION (ID_SEARCH,ID_REGION) VALUES(?,?)";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idRegion);
			if(db.executeUpdate()==1)
			{
				result=1;
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
		if(result == 1)
		{
			this.addStateSearchList();
		}
		return result;
	}

	
	public int delete()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_REGION WHERE ID_SEARCH=? AND ID_REGION=?";  
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idRegion);
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
	
	public String getDescription()
	{
		String result="";
		String tmp="";
		PropertiesManager propertiesManager = new PropertiesManager();
		if(this.stateSearchList!=null)
		{
			for(int i=0; i<this.stateSearchList.size(); i++)
			{
				if(this.stateSearchList.get(i).getIdState()==77)
				{
					if(tmp.equals(""))
					{
						tmp+=this.stateSearchList.get(i).getCityDescription();
					}
					else
					{
						tmp+=","+this.stateSearchList.get(i).getCityDescription();
					}
				}
				else
				{
					if(tmp.equals(""))
					{
						if(idLanguage==11)
						{
							tmp=this.stateSearchList.get(i).getNameEng();
						}
						else
						{
							tmp=this.stateSearchList.get(i).getNameTha();						
						}
					}
					else
					{
						if(idLanguage==11)
						{
							tmp+=","+this.stateSearchList.get(i).getNameEng();
						}
						else
						{
							tmp+=", "+this.stateSearchList.get(i).getNameTha();						
						}
					}
				}
			}
		}
		if(tmp.equals(""))
		{
			if(this.idLanguage==11)
			{
				tmp = propertiesManager.getMessage("en_TH", "ALL");
			}
			else
			{
				tmp = propertiesManager.getMessage("th_TH", "ALL");
			}
		}
		if(this.idLanguage==11)
		{
			result=this.nameEng+"("+tmp+")";
		}
		else
		{
			result=this.nameTha+"("+tmp+")";
		}
		return result;
	}
	
	private int addStateSearchList()
	{
		int result=0;
		//this.deleteStateSearchList();
		if(this.stateSearchList!=null)
		{
			for(int i=0; i<this.stateSearchList.size(); i++)
			{
				if(this.stateSearchList.get(i).getIdState()>0)
				{
					StateSearch stateSearch=this.stateSearchList.get(i);
					stateSearch.setIdSearch(this.idSearch);
					result+=stateSearch.add();
				}
			}
		}
		return result;
	}
	
	private int deleteStateSearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_STATE WHERE ID_SEARCH=? AND ID_REGION=?"; 
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idRegion);
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

	public int getIdSearch()
	{
		return idSearch;
	}

	public void setIdSearch(int idSearch)
	{
		this.idSearch = idSearch;
	}

	public int getIdLanguage()
	{
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage)
	{
		this.idLanguage = idLanguage;
	}

	public int getIdRegion()
	{
		return idRegion;
	}

	public void setIdRegion(int idRegion)
	{
		this.idRegion = idRegion;
	}

	public String getNameEng()
	{
		return nameEng;
	}

	public void setNameEng(String nameEng)
	{
		this.nameEng = nameEng;
	}

	public String getNameTha()
	{
		return nameTha;
	}

	public void setNameTha(String nameTha)
	{
		this.nameTha = nameTha;
	}

	public List<StateSearch> getStateSearchList()
	{
		return stateSearchList;
	}

	public void setStateSearchList(List<StateSearch> stateSearchList)
	{
		this.stateSearchList = stateSearchList;
	}
}
