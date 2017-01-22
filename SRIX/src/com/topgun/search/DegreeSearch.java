package com.topgun.search;


import com.topgun.util.DBManager;

public class DegreeSearch 
{
	private int idSearch;
	private String idDegree;
	private String nameEng="";
	private String nameTha="";
	private int idLanguage;
	
	public DegreeSearch(String idDegree, int idLanguage)
	{
		this.idDegree=idDegree;
		this.idLanguage=idLanguage;
	}

	public DegreeSearch(int idSearch, String idDegree, int idLanguage)
	{
		this.idDegree=idDegree;
		this.idLanguage=idLanguage;
		this.idSearch=idSearch;
	}
	
	public int add()
	{
		int result=0;
		String sql="INSERT INTO INTER_SEARCH_DEGREE(ID_SEARCH,ID_DEGREE) VALUES(?,?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setString(2, this.idDegree);
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
		String sql="DELETE FROM INTER_SEARCH_DEGREE WHERE ID_SEARCH=? AND ID_DEGREE=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setString(2, this.idDegree);
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

	public String getIdDegree()
	{
		return idDegree;
	}

	public void setIdDegree(String idDegree)
	{
		this.idDegree = idDegree;
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

	public int getIdLanguage()
	{
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage)
	{
		this.idLanguage = idLanguage;
	}
}
