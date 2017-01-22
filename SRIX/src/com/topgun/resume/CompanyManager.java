package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class CompanyManager 
{
	public Company get(int idCompany)
	{
		Company result= null;
		String sql=	"SELECT ID_COMPANY, RESUME_TEMPLATE, RESUME_LANGUAGE1, RESUME_LANGUAGE2 FROM INTER_COMPANY WHERE ID_COMPANY=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idCompany);
			db.executeQuery();
			if(db.next())
			{
				result=new Company();
				result.setIdCompany(db.getInt("ID_COMPANY"));
				if(db.getString("RESUME_TEMPLATE") == null)
				{
					result.setResumeTemplate("TH_A.html");
				}
				else
				{
					result.setResumeTemplate(db.getString("RESUME_TEMPLATE"));
				}
				result.setResumeLanguage1(db.getInt("RESUME_LANGUAGE1"));
				result.setResumeLanguage2(db.getInt("RESUME_LANGUAGE2"));
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
	
	public static int add(Company company)
	{
		int result=0;
		DBManager db=null;
		String sql=	"INSERT INTO INTER_COMPANY(ID_COMPANY, RESUME_TEMPLATE,RESUME_LANGUAGE1,RESUME_LANGUAGE2) VALUES(?,?,?,?)";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, company.getIdCompany());
			db.setString(2, company.getResumeTemplate());
			db.setInt(3, company.getResumeLanguage1());
			db.setInt(4, company.getResumeLanguage2());
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
	
	public static List<Company> getAll()
	{
		List<Company> result=null;
		DBManager db=null;
		String sql=	"SELECT ID_COMPANY, RESUME_TEMPLATE,RESUME_LANGUAGE1,RESUME_LANGUAGE2 FROM INTER_COMPANY ORDER BY ID_COMPANY DESC";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.executeQuery();
			while(db.next())
			{
				if(result==null)
				{
					result=new ArrayList<Company>();
				}
				Company com=new Company();
				com.setIdCompany(db.getInt("ID_COMPANY"));
				com.setResumeTemplate(db.getString("RESUME_TEMPLATE"));
				com.setResumeLanguage1(db.getInt("RESUME_LANGUAGE1"));
				com.setResumeLanguage2(db.getInt("RESUME_LANGUAGE2"));
				result.add(com);
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

}
