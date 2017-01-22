package com.topgun.search;

import com.topgun.util.DBManager;

public class CitySearch 
{
	private int idSearch;
	private int idRegion;
	private int idState;
	private int idCity;
	private int idLanguage;
	private String nameEng="";
	private String nameTha="";
	
	public CitySearch(int idRegion,int idState, int idCity, int idLanguage)
	{
		this.idRegion=idRegion;
		this.idState=idState;
		this.idCity=idCity;
		this.idLanguage=idLanguage;
	}
	
	public int add()
	{
		int result=0;
		
		String sql="INSERT INTO INTER_SEARCH_CITY(ID_SEARCH, ID_CITY, ID_STATE,ID_REGION) VALUES(?,?,?,?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idCity);
			db.setInt(3, this.idState);
			db.setInt(4, this.idRegion);
			result=db.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("ID_SEARCH="+this.idSearch+", ID_REGION="+this.idRegion+", ID_STATE="+idState+", ID_CITY="+idCity);
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
		String sql="DELETE FROM INTER_SEARCH_CITY WHERE ID_SEARCH=? AND ID_CITY=? AND ID_STATE=? AND ID_REGION=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idCity);
			db.setInt(3, this.idState);
			db.setInt(4, this.idRegion);
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
		return this.idLanguage==11?this.nameEng:this.nameTha;
	}

	public int getIdSearch()
	{
		return idSearch;
	}

	public void setIdSearch(int idSearch)
	{
		this.idSearch = idSearch;
	}

	public int getIdRegion()
	{
		return idRegion;
	}

	public void setIdRegion(int idRegion)
	{
		this.idRegion = idRegion;
	}

	public int getIdState()
	{
		return idState;
	}

	public void setIdState(int idState)
	{
		this.idState = idState;
	}

	public int getIdCity()
	{
		return idCity;
	}

	public void setIdCity(int idCity)
	{
		this.idCity = idCity;
	}

	public int getIdLanguage()
	{
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage)
	{
		this.idLanguage = idLanguage;
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
}
