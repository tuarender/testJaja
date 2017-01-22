package com.topgun.search;
import java.util.List;
import java.util.ArrayList;

import com.topgun.util.DBManager;
import com.topgun.util.PropertiesManager;

public class JobFieldSearch
{
	private int idSearch;
	private int idJobField;
	private int idLanguage;
	private String nameEng;
	private String nameTha;
	private List<SubGroupSearch> subGroupSearchList;
	
	public JobFieldSearch(int idJobField, int idLanguage)
	{
		this.idJobField=idJobField;
		this.idLanguage=idLanguage;
	}
	
	public void fetchAllSubGroupSearchList()
	{
		this.subGroupSearchList=new ArrayList<SubGroupSearch>();
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ 	"	A.ID_GROUP_SUBFIELD, A.GROUP_FIELD_NAME AS NAME_ENG,B.GROUP_FIELD_NAME AS NAME_THA "
					+ 	"FROM "
					+ 	"	INTER_GROUP_SUBFIELD_LANGUAGE A,INTER_GROUP_SUBFIELD_LANGUAGE B,INTER_SEARCH_SUBGROUP C "
					+ 	"WHERE "
					+ 	"	A.ID_LANGUAGE=11 AND B.ID_LANGUAGE=38 AND A.ID_FIELD=B.ID_FIELD AND "
					+ 	"	A.ID_GROUP_SUBFIELD=B.ID_GROUP_SUBFIELD AND C.ID_JOBFIELD=A.ID_FIELD AND "
					+ 	"	C.ID_SUBGROUP=A.ID_GROUP_SUBFIELD AND C.ID_JOBFIELD=? AND C.ID_SEARCH=? ";
			
			if(this.getIdLanguage()==11)
			{
				sql+=" ORDER BY NAME_ENG";
			}
			else
			{
				sql+=" ORDER BY NAME_THA";
			}
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,this.idJobField);
			db.setInt(2,this.idSearch);
			db.executeQuery();
			while(db.next())
			{
				SubGroupSearch subGroupSearch=new SubGroupSearch(this.idSearch,this.idJobField,db.getInt("ID_GROUP_SUBFIELD"),this.idLanguage);
				subGroupSearch.setNameEng(db.getString("NAME_ENG"));
				subGroupSearch.setNameTha(db.getString("NAME_THA"));
				this.subGroupSearchList.add(subGroupSearch);
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
		String sql="INSERT INTO INTER_SEARCH_JOBFIELD (ID_SEARCH,ID_JOBFIELD) VALUES(?,?)";
		DBManager db=null;
		try
		{   
			this.delete();
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJobField);
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
			this.addSubGroupSearchList();
		}
		return result;
	}

	public int delete()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_JOBFIELD WHERE ID_SEARCH=? AND ID_JOBFIELD=?";
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJobField);
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
	
	private int addSubGroupSearchList()
	{
		int result=0;
		//this.deleteSubGroupSearchList();
		if(this.subGroupSearchList!=null)
		{
			for(int i=0; i<this.subGroupSearchList.size(); i++)
			{
				if(this.subGroupSearchList.get(i).getIdSubGroup()>0)
				{
					SubGroupSearch subGroupSearch=this.subGroupSearchList.get(i);
					subGroupSearch.setIdSearch(this.idSearch);
					result+=subGroupSearch.add();
				}
			}
		}
		return result;
	}
	
	private int deleteSubGroupSearchList()
	{
		int result=0;
		String sql="DELETE FROM INTER_SEARCH_SUBGROUP WHERE ID_SEARCH=? AND ID_JOBFIELD=?"; 
		DBManager db=null;
		try
		{   
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, this.idSearch);
			db.setInt(2, this.idJobField);
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
		if(this.subGroupSearchList!=null)
		{
			for(int i=0; i<this.subGroupSearchList.size(); i++)
			{
				if(tmp.equals(""))
				{
					tmp=this.subGroupSearchList.get(i).getDescription();
				}
				else
				{
					tmp+=","+this.subGroupSearchList.get(i).getDescription();
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

	public int getIdJobField()
	{
		return idJobField;
	}

	public void setIdJobField(int idJobField)
	{
		this.idJobField = idJobField;
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

	public List<SubGroupSearch> getSubGroupSearchList()
	{
		return subGroupSearchList;
	}

	public void setSubGroupSearchList(List<SubGroupSearch> subGroupSearchList)
	{
		this.subGroupSearchList = subGroupSearchList;
	}
}
