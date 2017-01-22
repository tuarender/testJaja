package com.topgun.search;


import com.topgun.util.DBManager;

public class SubGroupSearch 
{
	private int idSearch;
	private int idJobField;
	private int idSubGroup;
	private String nameEng="";
	private String nameTha="";
	private int idLanguage;

	public SubGroupSearch(int idJobField,int idSubGroup,int idLanguage)
	{
		this.idJobField=idJobField;
		this.idSubGroup=idSubGroup;
		this.idLanguage=idLanguage;
	}
	
	public SubGroupSearch(int idSearch, int idJobField,int idSubGroup,int idLanguage)
	{
		this.idJobField=idJobField;
		this.idSubGroup=idSubGroup;
		this.idLanguage=idLanguage;
		this.idSearch=idSearch;
	}

	public int add()
	{
		int result=0;
		String sql="INSERT INTO INTER_SEARCH_SUBGROUP (ID_SEARCH,ID_JOBFIELD,ID_SUBGROUP) VALUES(?,?,?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJobField);
			db.setInt(3, this.idSubGroup);
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
		String sql="DELETE FROM INTER_SEARCH_SUBGROUP WHERE ID_SEARCH=? AND ID_JOBFIELD=? AND ID_SUBGROUP=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJobField);
			db.setInt(3, this.idSubGroup);
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

	public int getIdJobField()
	{
		return idJobField;
	}

	public void setIdJobField(int idJobField)
	{
		this.idJobField = idJobField;
	}

	public int getIdSubGroup()
	{
		return idSubGroup;
	}

	public void setIdSubGroup(int idSubGroup)
	{
		this.idSubGroup = idSubGroup;
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
