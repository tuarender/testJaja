package com.topgun.search;

import com.topgun.util.DBManager;

public class IndustrySearch
{
	private int idSearch;
	private int idLanguage;
	private int idIndustry;
	private String nameEng;
	private String nameTha;
	
	public IndustrySearch(int idIndustry, int idLanguage)
	{
		this.idIndustry=idIndustry;
		this.idLanguage=idLanguage;
	}

	public IndustrySearch(int idSearch,int idIndustry, int idLanguage)
	{
		this.idIndustry=idIndustry;
		this.idLanguage=idLanguage;
		this.idSearch=idSearch;
	}
	
	public int add()
	{
		int result=0;
		String sql="INSERT INTO INTER_SEARCH_INDUSTRY(ID_SEARCH,ID_INDUSTRY) VALUES(?,?)";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idIndustry);
			result=db.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println(sql+"=>"+this.idSearch+","+idIndustry);
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
		String sql="DELETE FROM INTER_SEARCH_INDUSTRY WHERE ID_SEARCH=? AND ID_INDUSTRY=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idIndustry);
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

	public int getIdLanguage()
	{
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage)
	{
		this.idLanguage = idLanguage;
	}

	public int getIdIndustry()
	{
		return idIndustry;
	}

	public void setIdIndustry(int idIndustry)
	{
		this.idIndustry = idIndustry;
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
