package com.topgun.search;


import com.topgun.util.DBManager;

public class JobTypeSearch 
{
	private int idSearch;
	private int idJobType;
	private String nameEng="";
	private String nameTha="";
	private int idLanguage;
	
	public JobTypeSearch(int idJobType, int idLanguage)
	{
		this.idJobType=idJobType;
		this.idLanguage=idLanguage;
	}

	public JobTypeSearch(int idSearch, int idJobType, int idLanguage)
	{
		this.idJobType=idJobType;
		this.idLanguage=idLanguage;
		this.idSearch=idSearch;
	}
	
	public int add()
	{
		int result=0;
		String sql="INSERT INTO INTER_SEARCH_JOBTYPE(ID_SEARCH, ID_TYPE) VALUES(?,?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJobType);
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
		String sql="DELETE FROM INTER_SEARCH_JOBTYPE WHERE ID_SEARCH=? AND ID_JOBTYPE=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJobType);
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
	
	public int getIdJobType() {
		return idJobType;
	}

	public void setIdJobType(int idJobType) {
		this.idJobType = idJobType;
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
