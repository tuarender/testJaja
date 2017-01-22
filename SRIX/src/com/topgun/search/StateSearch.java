package com.topgun.search;
import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;
import com.topgun.util.PropertiesManager;

public class StateSearch 
{
	private int idSearch;
	private int idLanguage;
	private int idState;
	private int idRegion;
	private String nameEng;
	private String nameTha;
	private List<CitySearch> citySearchList;
	
	public StateSearch(int idRegion, int idState, int idLanguage)
	{
		this.idRegion=idRegion;
		this.idState=idState;
		this.idLanguage=idLanguage;
	}
	
	public StateSearch(int idSearch,int idRegion, int idState, int idLanguage)
	{
		this.idRegion=idRegion;
		this.idState=idState;
		this.idLanguage=idLanguage;
		this.idSearch=idSearch;
	}
	
	public int add()
	{
		int result=0;
		this.delete();
		String sql="INSERT INTO INTER_SEARCH_STATE (ID_SEARCH, ID_STATE, ID_REGION) VALUES(?,?,?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idState);
			db.setInt(3, this.idRegion);
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
			this.addCitySearchList();
		}
		return result;
	}
	
	private int addCitySearchList()
	{
		int result=0;
		//this.deleteCitySearchList();
		if(this.citySearchList!=null)
		{
			for(int i=0; i<this.citySearchList.size(); i++)
			{
				if(this.citySearchList.get(i).getIdCity()>0)
				{
					CitySearch citySearch=this.citySearchList.get(i);
					citySearch.setIdSearch(this.idSearch);
					result+=citySearch.add();
				}
			}
		}
		return result;
	}
	
	private int deleteCitySearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_CITY WHERE ID_SEARCH=? AND ID_REGION=? AND ID_STATE=?"; 
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idRegion);
			db.setInt(3, this.idState);
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
	
	public int delete()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_STATE WHERE ID_SEARCH=? AND ID_STATE=? AND ID_REGION=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idState);
			db.setInt(3, this.idRegion);
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
	
	public void fetchAllCitySearchList()
	{
		this.citySearchList=new ArrayList<CitySearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ "		A.ID_CITY, A.CITY_NAME AS NAME_ENG,B.CITY_NAME AS NAME_THA "
					+ "	FROM "
					+ "		INTER_CITY_LANGUAGE A,INTER_CITY_LANGUAGE B ,INTER_SEARCH_CITY C "
					+ "WHERE "
					+ "		A.ID_LANGUAGE=11 AND B.ID_LANGUAGE=38 AND "
					+ "		A.ID_COUNTRY=216 AND B.ID_COUNTRY=216 AND "
					+ "		A.ID_STATE=B.ID_STATE AND A.ID_STATE=77 AND "
					+ "		A.ID_CITY=B.ID_CITY AND A.ID_CITY=C.ID_CITY AND "
					+ "		C.ID_SEARCH=? AND C.ID_STATE=?";
			
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
			db.setInt(2,this.idState);
			db.executeQuery();
			while(db.next())
			{
				CitySearch citySearch=new CitySearch(this.idRegion,this.idState,db.getInt("ID_CITY"),this.idLanguage);
				citySearch.setIdSearch(this.idSearch);
				citySearch.setNameEng(db.getString("NAME_ENG"));
				citySearch.setNameTha(db.getString("NAME_THA"));
				this.citySearchList.add(citySearch);
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

	public String getCityDescription()
	{
		String result="";
		String tmp="";
		PropertiesManager propertiesManager = new PropertiesManager();
		if(this.citySearchList!=null)
		{
			for(int i=0; i<this.citySearchList.size(); i++)
			{
				if(tmp.equals(""))
				{
					tmp=this.citySearchList.get(i).getDescription();
				}
				else
				{
					tmp+=","+this.citySearchList.get(i).getDescription();
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

	public int getIdState()
	{
		return idState;
	}

	public void setIdState(int idState)
	{
		this.idState = idState;
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

	public List<CitySearch> getCitySearchList()
	{
		return citySearchList;
	}

	public void setCitySearchList(List<CitySearch> citySearchList)
	{
		this.citySearchList = citySearchList;
	}
}
