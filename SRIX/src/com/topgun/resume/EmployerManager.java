package com.topgun.resume;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;
import com.topgun.util.Util;
import com.topgun.shris.masterdata.*;

public class EmployerManager 
{
	public Employer get(int idEmp) 
	{
		Employer result =null;
		String sql=	"SELECT ID_EMP, COMPANY_NAME, COMPANY_NAME_THAI,PREMIUM FROM EMPLOYER WHERE ID_EMP=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.executeQuery();
			if(db.next())
			{
				result=new Employer();
				result.setId(db.getInt("ID_EMP"));
				result.setNameEng(db.getString("COMPANY_NAME"));
				result.setNameThai(db.getString("COMPANY_NAME_THAI"));
				result.setCompanyType(db.getInt("PREMIUM"));
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
	
	public boolean isAcceptMail(int idEmp)
	{
		boolean result=true;
		String SQL="SELECT FWD_MAIL FROM CALL WHERE (ID=?) AND FWD_MAIL=1"; 
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idEmp);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=false;
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

	public boolean isERecruit(int idEmp)
	{
		boolean result=true;
		String SQL="SELECT SUPER FROM CALL WHERE (ID=?) AND SUPER=1"; 
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idEmp);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=false;
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
	
	public String getLogoPath(int idEmp) 
	{
		String result="/oracle/bea/user_projects/domains/content/filejobtopgun/logo/sup_lg"+idEmp+".gif";
		if(!(new File(result).exists()))
		{
			result="";
		}		
    	return result;
    }
	
	public String getLogoURL(int idEmp)
    {
		String result="";
		if(!getLogoPath(idEmp).equals(""))
		{
			result="http://www.jobtopgun.com/content/filejobtopgun/logo/sup_lg"+idEmp+".gif";
		}
		return result;
    }
	
	public String getResumeTemplate(int idEmp)
    {
		String result="";
		String SQL="SELECT RESUME_TEMPLATE FROM INTER_COMPANY WHERE ID_COMPANY=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.executeQuery();
			if(db.next())
			{
				result=db.getString("RESUME_TEMPLATE");
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
	
	public boolean chkNowPkgSuperERecruit(int idEmp)
	{
		   
		 boolean result=false;
		 DBManager db = null;
		 String sql="";
		 
		 try
		 {
			 db= new DBManager();
			 
			   String  today = Util.nowDateFormat("yyyyMMdd");
               sql =  "select p.id_emp from call c , pkg_sup p "+
					  "where p.id_emp=c.id  and p.id_emp = ?  and    expire_date >= ? and c.super=1 and c.enable=1 order by expire_date desc";
			   db.createPreparedStatement(sql);
			   db.setInt(1,idEmp);
			   db.setString(2, today);
			   db.executeQuery();
			   if(db.next())
			   {
				   result=true;
			   }
		 }
		 catch (Exception e) 
		 {
			e.printStackTrace();
		}
		 finally
		 {
			 db.close();
		 }
		 
		 return result;
		    
	}	
	
	public List<Language> getRequireLanguage(int idEmp)
	{
		DBManager db = null ;
		String sql = "";
		List<Language> result  =new ArrayList<Language>();
		try{
			db = new DBManager();
			sql = "select a.id_company , a.id_language , b.language_name "
					+ " from inter_company_resume_language a  , inter_language b "
					+ " where a.id_language = b.id_language and a.id_company = ? ";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.executeQuery();
			while(db.next())
			{
				Language language = new Language();
				language.setId(db.getInt("id_language"));
				language.setName(db.getString("language_name"));
				result.add(language);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			db.close();
		}
		return result ;
	}
	
	public boolean chkRequireLanguage(int idEmp , int idLanguage)
	{
		DBManager db = null ;
		String sql = "";
		boolean result = false ;
		try{
			db = new DBManager();
			sql = "select * from inter_company_resume_language where id_company = ? and id_language = ?";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result = true ;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result ;
	}
	
	public boolean chkFirstViewEngLangauge(int idEmp)
	{
		DBManager db = null ;
		String sql = "";
		boolean result = false ;
		try{
			db = new DBManager();
			sql = "select * from inter_company where id_company = ? ";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.executeQuery();
			if(db.next())
			{
				if(db.getInt("view_eng_resume_first")>0)
				{
					result = true ;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result ;
	}
}
