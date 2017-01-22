package com.topgun.shris.masterdata;
import java.util.List;
import java.util.ArrayList;

import com.topgun.util.DBManager;

public class MasterDataManager
{
	public static CareerObjective getCareerObjective(int idCareerObjective, int idLanguage)
	{
		CareerObjective result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_CAREEROBJ,CAREEROBJ_NAME,PRIORITY FROM INTER_CAREEROBJECTIVE_LANGUAGE WHERE ID_LANGUAGE=? AND ID_CAREEROBJ=?");
			db.setInt(1,idLanguage);
			db.setInt(2, idCareerObjective);
			db.executeQuery();
			if(db.next())
			{
				result=new CareerObjective();
				result.setIdLanguage(idLanguage);
				result.setIdCareerobj(db.getInt("ID_CAREEROBJ"));
				result.setPriority(db.getInt("PRIORITY"));
				result.setCareerobjName(db.getString("CAREEROBJ_NAME"));
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
	
	public static List<CareerObjective> getAllCareerObjective(int idLanguage)
	{
		List<CareerObjective> result=new ArrayList<CareerObjective>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_CAREEROBJ,CAREEROBJ_NAME,PRIORITY FROM INTER_CAREEROBJECTIVE_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY CAREEROBJ_NAME");
			db.setInt(1,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				CareerObjective career=new CareerObjective();
				career.setIdLanguage(idLanguage);
				career.setIdCareerobj(db.getInt("ID_CAREEROBJ"));
				career.setPriority(db.getInt("PRIORITY"));
				career.setCareerobjName(db.getString("CAREEROBJ_NAME"));
				result.add(career);
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
	

	public static int countCity(int idCountry, int idState, int idLanguage)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT COUNT(ID_CITY) AS TOTAL FROM INTER_CITY_LANGUAGE WHERE ID_COUNTRY=? AND ID_LANGUAGE=? AND ID_STATE=?");
			db.setInt(1, idCountry);
			db.setInt(2,idLanguage);
			db.setInt(3,idState);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	
	public static List<State> getAllIndustrialEstate(int idLanguage)
	{
		List<State> result = new ArrayList<State>();
		DBManager db = null ;
		String sql = "" ;
		String sqlOrder = "ORDER BY NAME_ENG";
		if(idLanguage == 38)
		{
			sqlOrder = "ORDER BY NAME_THA";
		}
		try
		{
			sql = "SELECT * FROM INTER_INDUSTRIAL "+sqlOrder;
			db = new DBManager() ;
			db.createPreparedStatement(sql);
			db.executeQuery();
			while(db.next())
			{
				State indus = new State();
				indus.setIdCountry(216);
				indus.setIdLanguage(idLanguage);
				indus.setIdState(db.getInt("ID"));
				if(idLanguage == 38)
				{
					indus.setStateName(db.getString("NAME_THA"));
				}
				else
				{
					indus.setStateName(db.getString("NAME_ENG"));
				}
				result.add(indus);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			db.close();
		}
		return result ;
	}
	
	public static State getIndustrialEstate(int idIndustrial , int idLanguage)
	{
		State result = null ;
		DBManager db = null ;
		String sql = "" ;
		try
		{
			sql = "SELECT * FROM INTER_INDUSTRIAL WHERE ID = ?";
			db = new DBManager() ;
			db.createPreparedStatement(sql);
			db.setInt(1, idIndustrial);
			db.executeQuery();
			while(db.next())
			{
				result = new State() ;
				result.setIdCountry(216);
				result.setIdLanguage(idLanguage);
				result.setIdState(db.getInt("ID"));
				if(idLanguage == 38)
				{
					result.setStateName(db.getString("NAME_THA"));
				}
				else
				{
					result.setStateName(db.getString("NAME_ENG"));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			db.close();
		}
		return result ;
	}
	
	public static List<Language> getAllSkillLanguage(int idLanguage)
	{
		List<Language> result=new ArrayList<Language>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE_MEANING,LANGUAGE_NAME FROM INTER_LANGUAGE_LANGUAGE WHERE ID_LANGUAGE=?");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Language language=new Language();
				language.setId(db.getInt("ID_LANGUAGE_MEANING"));
				language.setName(db.getString("LANGUAGE_NAME"));
				result.add(language);
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
	
	public static Language getSkillLanguage(int idSkillLanguage,int idLanguage)
	{
		Language result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE_MEANING,LANGUAGE_NAME FROM INTER_LANGUAGE_LANGUAGE WHERE ID_LANGUAGE=? AND ID_LANGUAGE_MEANING=?");
			db.setInt(1, idLanguage);
			db.setInt(2, idSkillLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Language();
				result.setId(db.getInt("ID_LANGUAGE_MEANING"));
				result.setName(db.getString("LANGUAGE_NAME"));
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
	
	public static int countCity(int idCountry,int idState)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT COUNT(ID_CITY) AS TOTAL FROM INTER_CITY WHERE ID_COUNTRY=? AND ID_STATE=?");
			db.setInt(1, idCountry);
			db.setInt(2,idState);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	public static City getCity(int idCountry,int idState, int idCity, int idLanguage)
	{
		City result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,ID_COUNTRY,ID_STATE,ID_CITY,CITY_NAME FROM INTER_CITY_LANGUAGE WHERE ID_COUNTRY=? AND ID_STATE=? AND ID_CITY=? AND ID_LANGUAGE=?");
			db.setInt(1, idCountry);
			db.setInt(2,idState);
			db.setInt(3,idCity);
			db.setInt(4,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new City();
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setIdCity(db.getInt("ID_CITY"));
				result.setCityName(db.getString("CITY_NAME"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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

	public static List<City> getAllCity(int idCountry, int idState, int idLanguage)
	{
		List<City> result=new ArrayList<City>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,ID_COUNTRY,ID_STATE,ID_CITY,CITY_NAME FROM INTER_CITY_LANGUAGE WHERE ID_COUNTRY=? AND ID_STATE=? AND ID_LANGUAGE=? ORDER BY CITY_NAME");
			db.setInt(1, idCountry);
			db.setInt(2,idState);
			db.setInt(3,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				City city=new City();
				city.setIdCountry(db.getInt("ID_COUNTRY"));
				city.setIdState(db.getInt("ID_STATE"));
				city.setIdCity(db.getInt("ID_CITY"));
				city.setCityName(db.getString("CITY_NAME"));
				city.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(city);
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


	public static int countState(int idCountry)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT COUNT(ID_STATE) AS TOTAL FROM INTER_STATE WHERE ID_COUNTRY=?");
			db.setInt(1, idCountry);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	
	public static State getState(int idCountry, int idState, int idLanguage)
	{
		State result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,ID_COUNTRY,ID_STATE,STATE_NAME FROM INTER_STATE_LANGUAGE WHERE ID_COUNTRY=? AND ID_STATE=? AND ID_LANGUAGE=?");
			db.setInt(1, idCountry);
			db.setInt(2,idState);
			db.setInt(3,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new State();
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setStateName(db.getString("STATE_NAME"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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
	
	public static List<State> getAllState(int idCountry, int idLanguage)
	{
		List<State> result=new ArrayList<State>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,ID_COUNTRY,ID_STATE,STATE_NAME FROM INTER_STATE_LANGUAGE WHERE ID_COUNTRY=? AND ID_LANGUAGE=? ORDER BY STATE_NAME");
			db.setInt(1, idCountry);
			db.setInt(2,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				State state=new State();
				state.setIdCountry(db.getInt("ID_COUNTRY"));
				state.setIdState(db.getInt("ID_STATE"));
				state.setStateName(db.getString("STATE_NAME"));
				state.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(state);
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
	
	public static Country getCountry(int idCountry, int idLanguage)
	{
		Country result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT A.ID_LANGUAGE,A.ID_COUNTRY,A.COUNTRY_NAME,B.ABBREVIATION FROM INTER_COUNTRY_LANGUAGE A,INTER_COUNTRY B WHERE A.ID_COUNTRY=? AND A.ID_LANGUAGE=? AND A.ID_COUNTRY=B.ID_COUNTRY");
			db.setInt(1, idCountry);
			db.setInt(2,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Country();
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setCountryName(db.getString("COUNTRY_NAME"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setAbbreviation(db.getString("ABBREVIATION"));
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
	
	public static List<Country> getAllCountry(int idLanguage)
	{
		List<Country> result=new ArrayList<Country>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT A.ID_LANGUAGE,A.ID_COUNTRY,A.COUNTRY_NAME,B.ABBREVIATION FROM INTER_COUNTRY_LANGUAGE A,INTER_COUNTRY B WHERE A.ID_LANGUAGE=? AND A.ID_COUNTRY=B.ID_COUNTRY ORDER BY COUNTRY_NAME");
			db.setInt(1,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Country country=new Country();
				country.setIdCountry(db.getInt("ID_COUNTRY"));
				country.setCountryName(db.getString("COUNTRY_NAME"));
				country.setIdLanguage(db.getInt("ID_LANGUAGE"));
				country.setAbbreviation(db.getString("ABBREVIATION"));
				result.add(country);
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

	public static Currency getCurrency(int idCurrency)
	{
		Currency result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_CURRENCY,CURRENCY_NAME,CURRENCY_CODE FROM INTER_CURRENCY WHERE ID_CURRENCY=?");
			db.setInt(1, idCurrency);
			db.executeQuery();
			if(db.next())
			{
				result=new Currency();
				result.setId(db.getInt("ID_CURRENCY"));
				result.setName(db.getString("CURRENCY_NAME"));
				result.setCode(db.getString("CURRENCY_CODE"));
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
	
	public static List<Currency> getAllCurrency()
	{
		List<Currency> result=new ArrayList<Currency>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_CURRENCY,CURRENCY_NAME,CURRENCY_CODE FROM INTER_CURRENCY ORDER BY CURRENCY_NAME");
			db.executeQuery();
			while(db.next())
			{
				Currency currency=new Currency();
				currency.setId(db.getInt("ID_CURRENCY"));
				currency.setName(db.getString("CURRENCY_NAME"));
				currency.setCode(db.getString("CURRENCY_CODE"));
				result.add(currency);
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

	public static Degree getDegree(int idDegree, int idLanguage)
	{
		Degree result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,ID_DEGREE,DEGREE_NAME,DEGREE_ORDER FROM INTER_DEGREE_LANGUAGE WHERE ID_DEGREE=? AND ID_LANGUAGE=?");
			db.setInt(1, idDegree);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Degree();
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setIdDegree(db.getInt("ID_DEGREE"));
				result.setDegreeName(db.getString("DEGREE_NAME"));
				result.setDegreeOrder(db.getInt("DEGREE_ORDER"));
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
	
	public static List<Degree> getAllDegree(int idLanguage)
	{
		List<Degree> result=new ArrayList<Degree>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,ID_DEGREE,DEGREE_NAME,DEGREE_ORDER FROM INTER_DEGREE_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY DEGREE_ORDER DESC");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Degree degree=new Degree();
				degree.setIdLanguage(db.getInt("ID_LANGUAGE"));
				degree.setIdDegree(db.getInt("ID_DEGREE"));
				degree.setDegreeName(db.getString("DEGREE_NAME"));
				degree.setDegreeOrder(db.getInt("DEGREE_ORDER"));
				result.add(degree);
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
	
	public static School getSchool(int idCountry, int idState, int idSchool, int idLanguage)
	{
		School result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_SCHOOL,SCHOOL_NAME,ID_LANGUAGE,ID_COUNTRY,ID_STATE FROM INTER_SCHOOL_LANGUAGE WHERE ID_COUNTRY=? AND ID_STATE=? AND ID_SCHOOL=? AND ID_LANGUAGE=?");
			db.setInt(1,idCountry);
			db.setInt(2,idState);
			db.setInt(3,idSchool);
			db.setInt(4,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new School();
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdSchool(db.getInt("ID_SCHOOL"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setSchoolName(db.getString("SCHOOL_NAME"));
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
	
	public static School getSchool(int idCountry, int idSchool, int idLanguage)
	{
		School result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_SCHOOL,SCHOOL_NAME,ID_LANGUAGE,ID_COUNTRY,ID_STATE FROM INTER_SCHOOL_LANGUAGE WHERE ID_COUNTRY=? AND ID_SCHOOL=? AND ID_LANGUAGE=?");
			db.setInt(1,idCountry);
			db.setInt(2,idSchool);
			db.setInt(3,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new School();
				result.setIdCountry(db.getInt("ID_COUNTRY"));
				result.setIdSchool(db.getInt("ID_SCHOOL"));
				result.setIdState(db.getInt("ID_STATE"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setSchoolName(db.getString("SCHOOL_NAME"));
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
	
	public static List<School> getAllSchoolForSearch()
	{
		List<School> result=new ArrayList<School>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT DISTINCT(SCHOOL_NAME) AS SCHOOL_NAME,ID_SCHOOL,ID_COUNTRY,ID_STATE FROM INTER_SCHOOL_LANGUAGE ORDER BY SCHOOL_NAME");
			db.executeQuery();
			while(db.next())
			{
				School school=new School();
				school.setIdCountry(db.getInt("ID_COUNTRY"));
				school.setIdSchool(db.getInt("ID_SCHOOL"));
				school.setIdState(db.getInt("ID_STATE"));
				school.setSchoolName(db.getString("SCHOOL_NAME"));
				result.add(school);
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
	
	public static List<School> getAllSchool(int idCountry, int idState, int idLanguage)
	{
		List<School> result=new ArrayList<School>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_SCHOOL,SCHOOL_NAME,ID_LANGUAGE,ID_COUNTRY,ID_STATE FROM INTER_SCHOOL_LANGUAGE WHERE ID_COUNTRY=? AND ID_STATE=? AND ID_LANGUAGE=? ORDER BY SCHOOL_NAME");
			db.setInt(1, idCountry);
			db.setInt(2,idState);
			db.setInt(3,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				School school=new School();
				school.setIdCountry(db.getInt("ID_COUNTRY"));
				school.setIdSchool(db.getInt("ID_SCHOOL"));
				school.setIdState(db.getInt("ID_STATE"));
				school.setIdLanguage(db.getInt("ID_LANGUAGE"));
				school.setSchoolName(db.getString("SCHOOL_NAME"));
				result.add(school);
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
	
	public static List<School> getAllSchoolByIdCountryIdLanguage(int idCountry,int idLanguage)
	{
		List<School> result=new ArrayList<School>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT DISTINCT(SCHOOL_NAME) AS SCHOOL_NAME,ID_SCHOOL,ID_COUNTRY,ID_STATE FROM INTER_SCHOOL_LANGUAGE WHERE ID_COUNTRY=? AND ID_LANGUAGE=? ORDER BY SCHOOL_NAME");
			db.setInt(1, idCountry);
			db.setInt(2, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				School school=new School();
				school.setIdCountry(db.getInt("ID_COUNTRY"));
				school.setIdSchool(db.getInt("ID_SCHOOL"));
				school.setIdState(db.getInt("ID_STATE"));
				//school.setIdLanguage(db.getInt("ID_LANGUAGE"));
				school.setSchoolName(db.getString("SCHOOL_NAME"));
				result.add(school);
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
	public static List<School> getAllSchoolByIdCountry(int idCountry)
	{
		List<School> result=new ArrayList<School>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT DISTINCT(SCHOOL_NAME) AS SCHOOL_NAME,ID_SCHOOL,ID_COUNTRY,ID_STATE FROM INTER_SCHOOL_LANGUAGE WHERE ID_COUNTRY=? ORDER BY SCHOOL_NAME");
			db.setInt(1, idCountry);
			db.executeQuery();
			while(db.next())
			{
				School school=new School();
				school.setIdCountry(db.getInt("ID_COUNTRY"));
				school.setIdSchool(db.getInt("ID_SCHOOL"));
				school.setIdState(db.getInt("ID_STATE"));
				school.setSchoolName(db.getString("SCHOOL_NAME"));
				result.add(school);
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
	
	public static int countSchool(int idCountry, int idLanguage)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT COUNT(ID_SCHOOL) AS TOTAL FROM INTER_SCHOOL_LANGUAGE WHERE ID_COUNTRY=? AND ID_LANGUAGE=?");
			db.setInt(1, idCountry);
			db.setInt(2,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	
	public static int countSchool(int idCountry, int idState,int idLanguage)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT COUNT(ID_SCHOOL) AS TOTAL FROM INTER_SCHOOL_LANGUAGE WHERE ID_COUNTRY=? AND ID_LANGUAGE=? AND ID_STATE=?");
			db.setInt(1, idCountry);
			db.setInt(2,idLanguage);
			db.setInt(3, idState);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	

	public static Faculty getFaculty(int idFaculty, int idLanguage)
	{
		Faculty result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_FACULTY,FACULTY_NAME,FACULTY_LEVEL FROM INTER_FACULTY_LANGUAGE WHERE ID_FACULTY=? AND ID_LANGUAGE=?");
			db.setInt(1, idFaculty);
			db.setInt(2,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Faculty();
				result.setFacultyName(db.getString("FACULTY_NAME"));
				result.setIdFaculty(db.getInt("ID_FACULTY"));
				result.setFacultyLevel(db.getInt("FACULTY_LEVEL"));
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
	public static Faculty getFacultyExclusive(int idSchool,int idDegree,int idFaculty,int idLanguage)
	{
		Faculty result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_FACULTY,NAME,ID_DEGREE FROM INTER_UNV_FACULTY WHERE ID_SCHOOL = ? AND ID_DEGREE = ? AND ID_FACULTY=? AND ID_LANGUAGE=?");
			db.setInt(1, idSchool);
			db.setInt(2, idDegree);
			db.setInt(3, idFaculty);
			db.setInt(4,idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Faculty();
				result.setFacultyName(db.getString("NAME"));
				result.setIdFaculty(db.getInt("ID_FACULTY"));
				result.setFacultyLevel(db.getInt("ID_DEGREE"));
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
	public static List<Faculty> getAllFaculty(int idLanguage)
	{
		List<Faculty> result=new ArrayList<Faculty>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_FACULTY,FACULTY_NAME,FACULTY_LEVEL FROM INTER_FACULTY_LANGUAGE WHERE ID_FACULTY>0 AND ID_LANGUAGE=? ORDER BY FACULTY_NAME");
			db.setInt(1,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Faculty faculty=new Faculty();
				faculty.setIdLanguage(idLanguage);
				faculty.setFacultyName(db.getString("FACULTY_NAME"));
				faculty.setIdFaculty(db.getInt("ID_FACULTY"));
				faculty.setFacultyLevel(db.getInt("FACULTY_LEVEL"));
				result.add(faculty);
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
	
	public static Major getMajor(int idFaculty, int idMajor, int idLanguage)
	{
		Major result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_FACULTY,ID_MAJOR,MAJOR_NAME FROM INTER_MAJOR_LANGUAGE WHERE ID_FACULTY=? AND ID_MAJOR=? AND ID_LANGUAGE=?");
			db.setInt(1, idFaculty);
			db.setInt(2, idMajor);
			db.setInt(3, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Major();
				result.setIdLanguage(idLanguage);
				result.setIdFaculty(db.getInt("ID_FACULTY"));
				result.setIdMajor(db.getInt("ID_MAJOR"));
				result.setMajorName(db.getString("MAJOR_NAME"));				
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
	
	public static Major getMajorExclusive(int idSchool,int idDegree,int idFaculty, int idMajor, int idLanguage)
	{
		Major result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_FACULTY,ID_MAJOR,NAME FROM INTER_UNV_MAJOR WHERE ID_SCHOOL = ? AND ID_DEGREE = ? AND ID_FACULTY=? AND ID_MAJOR=? AND ID_LANGUAGE=?");
			db.setInt(1, idSchool);
			db.setInt(2, idDegree);
			db.setInt(3, idFaculty);
			db.setInt(4, idMajor);
			db.setInt(5, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Major();
				result.setIdLanguage(idLanguage);
				result.setIdFaculty(db.getInt("ID_FACULTY"));
				result.setIdMajor(db.getInt("ID_MAJOR"));
				result.setMajorName(db.getString("NAME"));				
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
	
	public static List<Major> getAllMajor(int idFaculty,int idLanguage)
	{
		List<Major> result=new ArrayList<Major>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_FACULTY,ID_MAJOR,MAJOR_NAME FROM INTER_MAJOR_LANGUAGE WHERE ID_FACULTY=? AND ID_LANGUAGE=? ORDER BY MAJOR_NAME");
			db.setInt(1, idFaculty);
			db.setInt(2, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Major major=new Major();
				major.setIdLanguage(idLanguage);
				major.setIdFaculty(db.getInt("ID_FACULTY"));
				major.setIdMajor(db.getInt("ID_MAJOR"));
				major.setMajorName(db.getString("MAJOR_NAME"));	
				result.add(major);
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

	public static Industry getIndustry(int idIndustry, int idLanguage)
	{
		Industry result=null;
		String sql="SELECT ID_INDUSTRY,INDUSTRY_NAME FROM INTER_INDUSTRY_LANGUAGE WHERE ID_LANGUAGE=? AND ID_INDUSTRY=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idIndustry);
			db.executeQuery();
			if(db.next())
			{
				result=new Industry();
				result.setIdIndustry(db.getInt("ID_INDUSTRY"));
				result.setIdLanguage(idLanguage);
				result.setIndustryName(db.getString("INDUSTRY_NAME"));
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
	
	public static List<Industry> getAllIndustry(int idLanguage)
	{
		List<Industry> result=new ArrayList<Industry>();
		String sql="SELECT ID_INDUSTRY,INDUSTRY_NAME FROM INTER_INDUSTRY_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY DECODE(ID_INDUSTRY,9999,0,1),INDUSTRY_NAME,INDUSTRY_NAME";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Industry industry=new Industry();
				industry.setIdIndustry(db.getInt("ID_INDUSTRY"));
				industry.setIdLanguage(idLanguage);
				industry.setIndustryName(db.getString("INDUSTRY_NAME"));
				result.add(industry);
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
	
	public static List<Industry> getAllIndustry(int idLanguage,boolean getAll)
	{
		List<Industry> result=new ArrayList<Industry>();
		String sql="SELECT ID_INDUSTRY,INDUSTRY_NAME FROM INTER_INDUSTRY_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY DECODE(ID_INDUSTRY,9999,0,1),INDUSTRY_NAME,INDUSTRY_NAME";
		if(!getAll){
			sql="SELECT ID_INDUSTRY,INDUSTRY_NAME FROM INTER_INDUSTRY_LANGUAGE WHERE ID_LANGUAGE=? AND ID_INDUSTRY <> 9999 ORDER BY INDUSTRY_NAME,INDUSTRY_NAME";
		}
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Industry industry=new Industry();
				industry.setIdIndustry(db.getInt("ID_INDUSTRY"));
				industry.setIdLanguage(idLanguage);
				industry.setIndustryName(db.getString("INDUSTRY_NAME"));
				result.add(industry);
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
	
	public static JobField getJobField(int idJobField, int idLanguage)
	{
		JobField result=null;
		String sql="SELECT ID_FIELD,FIELD_NAME FROM INTER_JOBFIELD_LANGUAGE WHERE ID_LANGUAGE=? AND ID_FIELD=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idJobField);
			db.executeQuery();
			if(db.next())
			{
				result=new JobField();
				result.setIdField(db.getInt("ID_FIELD"));
				result.setIdLanguage(idLanguage);
				result.setFieldName(db.getString("FIELD_NAME"));
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
	
	public static List<JobField> getAllJobField(int idLanguage)
	{
		List<JobField> result=new ArrayList<JobField>();
		String sql="SELECT ID_FIELD,FIELD_NAME FROM INTER_JOBFIELD_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY FIELD_NAME";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				JobField jobField=new JobField();
				jobField.setIdField(db.getInt("ID_FIELD"));
				jobField.setIdLanguage(idLanguage);
				jobField.setFieldName(db.getString("FIELD_NAME"));
				result.add(jobField);
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
	
	public static SubField getSubField(int idJobField, int idSubField, int idLanguage)
	{
		SubField result=null;
		String sql="SELECT ID_LANGUAGE,ID_FIELD,ID_SUBFIELD,SUBFIELD_NAME FROM INTER_SUBFIELD_LANGUAGE WHERE ID_LANGUAGE=? AND ID_FIELD=? AND ID_SUBFIELD=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idJobField);
			db.setInt(3, idSubField);
			db.executeQuery();
			if(db.next())
			{
				result=new SubField();
				result.setIdField(db.getInt("ID_FIELD"));
				result.setIdLanguage(idLanguage);
				result.setIdSubfield(db.getInt("ID_SUBFIELD"));
				result.setSubfieldName(db.getString("SUBFIELD_NAME"));
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
	
	public static List<SubField> getAllSubField(int idJobField, int idLanguage)
	{
		List<SubField> result=new ArrayList<SubField>();
		String sql="SELECT ID_LANGUAGE,ID_FIELD,ID_SUBFIELD,SUBFIELD_NAME FROM INTER_SUBFIELD_LANGUAGE WHERE ID_LANGUAGE=? AND ID_FIELD=? ORDER BY SUBFIELD_NAME";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idJobField);
			db.executeQuery();
			while(db.next())
			{
				SubField subField=new SubField();
				subField.setIdField(db.getInt("ID_FIELD"));
				subField.setIdLanguage(idLanguage);
				subField.setIdSubfield(db.getInt("ID_SUBFIELD"));
				subField.setSubfieldName(db.getString("SUBFIELD_NAME"));
				result.add(subField);	
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
	
	public static Strength getStrength(int idStrength, int idLanguage)
	{
		Strength result=null;
		String sql="SELECT ID_STRENGTH,STRENGTH_NAME FROM INTER_STRENGTH_LANGUAGE WHERE ID_LANGUAGE=? AND ID_STRENGTH=? ORDER BY STRENGTH_NAME";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idStrength);
			db.executeQuery();
			if(db.next())
			{
				result=new Strength();
				result.setIdLanguage(idLanguage);
				result.setIdStrength(db.getInt("ID_STRENGTH"));
				result.setStrengthName(db.getString("STRENGTH_NAME"));
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
	
	public static List<Strength> getAllStrength(int idLanguage)
	{
		List<Strength> result=new ArrayList<Strength>();
		String sql="SELECT ID_STRENGTH,STRENGTH_NAME FROM INTER_STRENGTH_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY STRENGTH_NAME";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Strength strength=new Strength();
				strength.setIdLanguage(idLanguage);
				strength.setIdStrength(db.getInt("ID_STRENGTH"));
				strength.setStrengthName(db.getString("STRENGTH_NAME"));
				result.add(strength);
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
	
	
	public static Hobby getHobby(int idHobby,int idLanguage)
	{
		Hobby result=null;
		String sql="SELECT ID_INTEREST,INTEREST_NAME,ID_GROUP,ID_LANGUAGE FROM INTER_INTEREST_LANGUAGE WHERE ID_LANGUAGE=? AND ID_INTEREST=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idHobby);
			db.executeQuery();
			if(db.next())
			{
				result=new Hobby();
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setIdHobby(db.getInt("ID_INTEREST"));
				result.setHobbyName(db.getString("INTEREST_NAME"));
				result.setIdGroup(db.getInt("ID_GROUP"));
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
	
	
	public static List<Hobby> getAllHobby(int idGroup, int idLanguage)
	{
		List<Hobby> result=new ArrayList<Hobby>();
		String sql="SELECT ID_INTEREST,INTEREST_NAME,ID_GROUP,ID_LANGUAGE FROM INTER_INTEREST_LANGUAGE WHERE ID_LANGUAGE=? AND ID_GROUP=? ORDER BY INTEREST_NAME";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idGroup);
			db.executeQuery();
			while(db.next())
			{
				Hobby hobby=new Hobby();
				hobby.setIdLanguage(db.getInt("ID_LANGUAGE"));
				hobby.setIdHobby(db.getInt("ID_INTEREST"));
				hobby.setHobbyName(db.getString("INTEREST_NAME"));
				hobby.setIdGroup(db.getInt("ID_GROUP"));
				result.add(hobby);
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

	public static List<Hobby> getAllHobbyByCountry(int idCountry,int idGroup, int idLanguage)
	{
		List<Hobby> result=new ArrayList<Hobby>();
		String sql=	"SELECT " +
					"	ID_INTEREST,INTEREST_NAME,ID_GROUP,ID_LANGUAGE " +
					"FROM " +
					"	INTER_INTEREST_LANGUAGE A, HOBBY_COUNTRY B " +
					"WHERE " +
					"	A.ID_LANGUAGE=? AND A.ID_GROUP=? AND " +
					"	A.ID_INTEREST=B.ID_HOBBY AND B.ID_COUNTRY=? " +
					"ORDER BY " +
					"	INTEREST_NAME";
		
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.setInt(2, idGroup);
			db.setInt(3, idCountry);
			db.executeQuery();
			while(db.next())
			{
				Hobby hobby=new Hobby();
				hobby.setIdLanguage(db.getInt("ID_LANGUAGE"));
				hobby.setIdHobby(db.getInt("ID_INTEREST"));
				hobby.setHobbyName(db.getString("INTEREST_NAME"));
				hobby.setIdGroup(db.getInt("ID_GROUP"));
				result.add(hobby);
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
	
	
	public static List<HobbyGroup> getAllHobbyGroup(int idLanguage)
	{
		List<HobbyGroup> result=new ArrayList<HobbyGroup>();
		String sql="SELECT ID_GROUP,NAME,ID_LANGUAGE FROM INTER_HOBBY_GROUP_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY ID_GROUP";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				HobbyGroup group=new HobbyGroup();
				group.setId(db.getInt("ID_GROUP"));
				group.setName(db.getString("NAME"));
				group.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(group);
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
	
	public static HobbyGroup getHobbyGroup(int idGroup,int idLanguage)
	{
		HobbyGroup result=null;
		String sql="SELECT ID_GROUP,NAME,ID_LANGUAGE FROM INTER_HOBBY_GROUP_LANGUAGE WHERE ID_GROUP=? AND ID_LANGUAGE = ?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idGroup);
			db.setInt(2, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				result=new HobbyGroup();
				result.setId(db.getInt("ID_GROUP"));
				result.setName(db.getString("NAME"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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
	
	public static List<JobType> getAllJobType(int idLanguage)
	{
		List<JobType> result=new ArrayList<JobType>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_TYPE,TYPE_NAME,PRIORITY FROM INTER_TYPEOFJOB_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY PRIORITY");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				JobType jobType=new JobType();
				jobType.setIdLanguage(idLanguage);
				jobType.setPriority(db.getInt("PRIORITY"));
				jobType.setTypeName(db.getString("TYPE_NAME"));
				jobType.setIdType(db.getInt("ID_TYPE"));
				result.add(jobType);
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
	
	public static JobType getJobType(int id,int idLanguage)
	{
		JobType result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_TYPE,TYPE_NAME,PRIORITY FROM INTER_TYPEOFJOB_LANGUAGE WHERE ID_LANGUAGE=? AND ID_TYPE=?");
			db.setInt(1, idLanguage);
			db.setInt(2, id);
			db.executeQuery();
			if(db.next())
			{
				result=new JobType();
				result.setIdLanguage(idLanguage);
				result.setPriority(db.getInt("PRIORITY"));
				result.setTypeName(db.getString("TYPE_NAME"));
				result.setIdType(db.getInt("ID_TYPE"));
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
	
	public static int countComputer(int idComputerGroup)
	{
		int result=0;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT COUNT(ID_COMPUTER) AS TOTAL FROM INTER_COMPUTER WHERE ID_GROUP=?");
			db.setInt(1, idComputerGroup);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	
	public static List<ComputerGroup> getAllComputerGroup(int idLanguage)
	{
		List<ComputerGroup> result=new ArrayList<ComputerGroup>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_GROUP,GROUP_NAME,ID_LANGUAGE FROM INTER_COMPUTER_GROUP_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY GROUP_NAME");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				ComputerGroup group=new ComputerGroup();
				group.setGroupName(db.getString("GROUP_NAME"));
				group.setIdGroup(db.getInt("ID_GROUP"));
				group.setIdLangauge(db.getInt("ID_LANGUAGE"));
				result.add(group);
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
	
	
	
	public static ComputerGroup getComputerGroup(int idGroup,int idLanguage)
	{
		ComputerGroup result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_GROUP,GROUP_NAME,ID_LANGUAGE FROM INTER_COMPUTER_GROUP_LANGUAGE WHERE ID_LANGUAGE=? AND ID_GROUP=?");
			db.setInt(1, idLanguage);
			db.setInt(2, idGroup);
			db.executeQuery();
			if(db.next())
			{
				result=new ComputerGroup();
				result.setGroupName(db.getString("GROUP_NAME"));
				result.setIdGroup(db.getInt("ID_GROUP"));
				result.setIdLangauge(db.getInt("ID_LANGUAGE"));
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

	public static List<Computer> getAllComputer(int idGroup)
	{
		List<Computer> result=new ArrayList<Computer>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_COMPUTER,ID_GROUP,COMPUTER_NAME FROM INTER_COMPUTER WHERE ID_GROUP=? ORDER BY ID_COMPUTER");
			db.setInt(1, idGroup);
			db.executeQuery();
			while(db.next())
			{
				Computer com=new Computer();
				com.setComputerName(db.getString("COMPUTER_NAME"));
				com.setIdComputer(db.getInt("ID_COMPUTER"));
				com.setIdGroup(db.getInt("ID_GROUP"));
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
	
	public static Computer getComputer(int idGroup,int idComputer)
	{
		Computer result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_COMPUTER,ID_GROUP,COMPUTER_NAME FROM INTER_COMPUTER WHERE ID_GROUP=? AND ID_COMPUTER=?");
			db.setInt(1, idGroup);
			db.setInt(2, idComputer);
			db.executeQuery();
			if(db.next())
			{
				result=new Computer();
				result.setComputerName(db.getString("COMPUTER_NAME"));
				result.setIdComputer(db.getInt("ID_COMPUTER"));
				result.setIdGroup(db.getInt("ID_GROUP"));
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
	
	public static List<Proficiency> getAllProficiency(int idLanguage)
	{
		List<Proficiency> result=new ArrayList<Proficiency>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_PRO, ID_LANGUAGE, NAME FROM INTER_PROFICIENCY_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY ID_PRO");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Proficiency proficiency=new Proficiency();
				proficiency.setName(db.getString("NAME"));
				proficiency.setId(db.getInt("ID_PRO"));
				proficiency.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(proficiency);
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
	
	public static Proficiency getProficiency(int idLanguage,int idProficiency)
	{
		Proficiency result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_PRO, ID_LANGUAGE, NAME FROM INTER_PROFICIENCY_LANGUAGE WHERE ID_LANGUAGE=? AND ID_PRO=?");
			db.setInt(1, idLanguage);
			db.setInt(2, idProficiency);
			db.executeQuery();
			if(db.next())
			{
				result=new Proficiency();
				result.setName(db.getString("NAME"));
				result.setId(db.getInt("ID_PRO"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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
	
	//this method only.. that differ from shris package
	public static Error getError(int idError, int idLanguage)
	{
		Error result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,ID_ERROR,ERROR_MESSAGE FROM INTER_ERROR_LANGUAGE WHERE ID_ERROR=? AND ID_LANGUAGE=?");
			db.setInt(1, idError);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Error();
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setIdError(db.getInt("ID_ERROR"));
				result.setErrorMessage(db.getString("ERROR_MESSAGE"));
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
	
	public static List<Gender> getAllGender(int idLanguage)
	{
		DBManager db=null;
		List<Gender> result = new ArrayList<Gender>();
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT TYP_ID,GENDER_NAME,ID_LANGUAGE FROM INTER_GENDER_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY TYP_ID ");
		    db.setInt(1, idLanguage);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Gender gender = new Gender();
		    	gender.setTypeId(db.getInt("TYP_ID"));
		    	gender.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	gender.setGenderName(db.getString("gender_name"));
		    	result.add(gender);
		    }
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.close();
		}
		
		return result;
	}
	
	public static Gender getGender(int typeId,int idLanguage)
	{
		DBManager db=null;
		Gender result = null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT TYP_ID,GENDER_NAME,ID_LANGUAGE FROM INTER_GENDER_LANGUAGE WHERE TYP_ID=? AND ID_LANGUAGE=? ORDER BY TYP_ID ");
		    db.setInt(1, typeId);
			db.setInt(2, idLanguage);
		    db.executeQuery();
		    if(db.next())
		    {
		    	result = new Gender();
		    	result.setTypeId(db.getInt("TYP_ID"));
		    	result.setIdLanguage(db.getInt("ID_LANGUAGE"));
		    	result.setGenderName(db.getString("gender_name"));
		    
		    }
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.close();
		}
		
		return result;
	}
	
	
    public static List<Integer> getAllSalary()
    {
    	List<Integer> result = new ArrayList<Integer>();
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement("SELECT SALARY_B FROM SALARY WHERE SALARY_B > 0 AND SALARY_B < 999999999  ORDER BY SALARY_B ");
    		db.executeQuery();
    		while(db.next())
    		{
    			result.add(db.getInt("SALARY_B"));
    		}
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    	return result;
    }

    public static List<Degree> getDegreeAllOfJobPost(int idLanguage)
    {
    	List<Degree> result = new ArrayList<Degree>();
    	DBManager db=null;
        String table ="DEGREE";
        if(idLanguage == 38)
        {
        	table="DEGREE_THAI";
        }
    	
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement("SELECT ID_DEGREE,DEGREE_NAME FROM "+table+" ORDER BY NO ");
    		while(db.next())
    		{
    			Degree deg = new Degree();
    			deg.setIdDegree(db.getInt("ID_DEGREE"));
    			deg.setDegreeName(db.getString("DEGREE_NAME"));
    			deg.setDegreeOrder(db.getInt("NO"));
    			result.add(deg);
    		}
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    	
    	return result;
    }
    
    public static List<Salary> getAllSalaryType(int idLanguage)
    {
		List<Salary> result=new ArrayList<Salary>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT TYP_ID,TYP_NAME FROM INTER_SALARY_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY TYP_ID");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Salary salary=new Salary();
				salary.setTypeId(db.getInt("TYP_ID"));
				salary.setTypeName(db.getString("TYP_NAME"));
				result.add(salary);
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
    
    public static Salary getSalaryType(int typeId,int idLanguage)
    {
    	Salary result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT TYP_ID,TYP_NAME FROM INTER_SALARY_LANGUAGE WHERE TYP_ID=? AND ID_LANGUAGE=?");
			db.setInt(1, typeId);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Salary();
				result.setTypeId(db.getInt("TYP_ID"));
				result.setTypeName(db.getString("TYP_NAME"));
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
    
    public static List<SalaryPer> getAllSalaryPer(int idLanguage)
    {
		List<SalaryPer> result=new ArrayList<SalaryPer>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_PER,NAME,ID_LANGUAGE FROM INTER_SALARY_PER_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY ID_PER ");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				SalaryPer salary=new SalaryPer();
				salary.setId(db.getInt("ID_PER"));
				salary.setName(db.getString("NAME"));
				salary.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(salary);
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
    
    public static SalaryPer getSalaryPer(int perId,int idLanguage)
    {
    	SalaryPer result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_PER,NAME,ID_LANGUAGE FROM INTER_SALARY_PER_LANGUAGE WHERE ID_PER=? and  ID_LANGUAGE=? ");
			db.setInt(1, perId);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new SalaryPer();
				result.setId(db.getInt("ID_PER"));
				result.setName(db.getString("NAME"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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
    
    public static List<SkillLevel> getAllSkillLevel(int idLanguage)
    {
		List<SkillLevel> result=new ArrayList<SkillLevel>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LEVEL,NAME,ID_LANGUAGE FROM INTER_SKILL_LEVEL_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY ID_LEVEL DESC ");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				SkillLevel level=new SkillLevel();
				level.setId(db.getInt("ID_LEVEL"));
				level.setName(db.getString("NAME"));
				level.setIdLanguage(db.getInt("ID_LANGUAGE"));
				
				result.add(level);
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
    
    public static SkillLevel getSkillLevel(int levelId,int idLanguage)
    {
    	SkillLevel result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LEVEL,NAME,ID_LANGUAGE FROM INTER_SKILL_LEVEL_LANGUAGE WHERE ID_LEVEL=? AND  ID_LANGUAGE=? ");
			db.setInt(1, levelId);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new SkillLevel();
				result.setId(db.getInt("ID_LEVEL"));
				result.setName(db.getString("NAME"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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
    
    
    public static List<LanguageLevel> getAllLanguageLevel(int idLanguage)
    {
		List<LanguageLevel> result=new ArrayList<LanguageLevel>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LEVEL,NAME,ID_LANGUAGE FROM INTER_LANGUAGE_LEVEL_LANGUAGE WHERE ID_LANGUAGE=? ORDER BY ID_LEVEL DESC ");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				LanguageLevel level=new LanguageLevel();
				level.setId(db.getInt("ID_LEVEL"));
				level.setName(db.getString("NAME"));
				level.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(level);
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
    
    public static LanguageLevel getLanguageLevel(int levelId,int idLanguage)
    {
    	LanguageLevel result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LEVEL,NAME,ID_LANGUAGE FROM INTER_LANGUAGE_LEVEL_LANGUAGE WHERE ID_LEVEL=? and  ID_LANGUAGE=? ");
			db.setInt(1, levelId);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new LanguageLevel();
				result.setId(db.getInt("ID_LEVEL"));
				result.setName(db.getString("NAME"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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
    
    public static List<Language> getAllLanguage()
    {
		List<Language> result=new ArrayList<Language>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,LANGUAGE_NAME,ABBREVIATION FROM INTER_LANGUAGE ORDER BY LANGUAGE_NAME");
			db.executeQuery();
			while(db.next())
			{
				Language lang=new Language();
				lang.setId(db.getInt("ID_LANGUAGE"));
				lang.setName(db.getString("LANGUAGE_NAME"));
				lang.setAbbreviation(db.getString("ABBREVIATION"));
				result.add(lang);
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
    
    public static List<Language> getAllLanguage(int idLanguage)
    {
		List<Language> result=new ArrayList<Language>();
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE_MEANING,LANGUAGE_NAME FROM INTER_LANGUAGE_LANGUAGE WHERE ID_LANGUAGE = ?  ORDER BY LANGUAGE_NAME");
			db.setInt(1, idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Language lang=new Language();
				lang.setId(db.getInt("ID_LANGUAGE_MEANING"));
				lang.setName(db.getString("LANGUAGE_NAME"));
				result.add(lang);
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
    
	public static List<Language> getAvailableLanguage(int idLanguage)
	{
		List<Language> result=new ArrayList<Language>();
		String SQL="SELECT ID_LANGUAGE FROM INTER_LANGUAGE WHERE ENABLED=1 ORDER BY ID_LANGUAGE";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			while(db.next())
			{
				String langName=getLanguageName(db.getInt("ID_LANGUAGE"), idLanguage);
				Language lang=new Language();
				lang.setId(db.getInt("ID_LANGUAGE"));
				lang.setName(langName);
				result.add(lang);
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
    
    public static Language getLanguage(int idLanguage)
    {
		Language result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE,LANGUAGE_NAME,ABBREVIATION FROM INTER_LANGUAGE WHERE ID_LANGUAGE=?");
			db.setInt(1, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=new Language();
				result.setId(db.getInt("ID_LANGUAGE"));
				result.setName(db.getString("LANGUAGE_NAME"));
				result.setAbbreviation(db.getString("ABBREVIATION"));
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
    
    public static Language getLanguage(int idLanguage,int idLanguageMeaning)
    {
		Language result=null;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT ID_LANGUAGE_MEANING,LANGUAGE_NAME FROM INTER_LANGUAGE_LANGUAGE WHERE ID_LANGUAGE=? AND ID_LANGUAGE_MEANING = ?");
			db.setInt(1, idLanguage);
			db.setInt(2, idLanguageMeaning);
			db.executeQuery();
			if(db.next())
			{
				result=new Language();
				result.setId(db.getInt("ID_LANGUAGE_MEANING"));
				result.setName(db.getString("LANGUAGE_NAME"));
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
    
    public static String getLanguageName(int idMeaning, int idLanguage)
    {
    	String result="";
		DBManager db=null;
		try
		{
			String SQL="SELECT LANGUAGE_NAME FROM INTER_LANGUAGE_LANGUAGE WHERE ID_LANGUAGE_MEANING=? AND ID_LANGUAGE=? ";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idMeaning);
			db.setInt(2, idLanguage);
			db.executeQuery();
			if(db.next())
			{
				result=db.getString("LANGUAGE_NAME");
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
    
    public static Region getRegion(int idRegion, int idLanguage)
    {
    	Region result=null;
    	DBManager db=null;
    	try
    	{
    		String SQL="SELECT ID_REGION,REGION_NAME FROM INTER_REGION_LANGUAGE WHERE ID_REGION=? AND ID_LANGUAGE=?";
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1,idRegion);
    		db.setInt(2, idLanguage);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=new Region();
    			result.setIdRegion(db.getInt("ID_REGION"));
    			result.setName(db.getString("REGION_NAME"));
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
    
    public static List<Region> getAllRegion(int idLanguage)
    {
    	List<Region> result = new ArrayList<Region>();
    	DBManager db=null ;
    	try
    	{
    		String SQL = "SELECT B.ID_REGION,B.REGION_NAME,A.RANK FROM INTER_REGION A , INTER_REGION_LANGUAGE B WHERE A.ID_REGION = B.ID_REGION AND B.ID_LANGUAGE = ? ORDER BY A.RANK ";
    		db = new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idLanguage);
    		db.executeQuery();
    		while(db.next())
    		{
    			Region region = new Region();
    			region.setIdRegion(db.getInt("ID_REGION"));
    			region.setName(db.getString("REGION_NAME"));
    			region.setRank(db.getInt("RANK"));
    			result.add(region);
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
    	return result ;
    }
    
    public static List<State> getAllProvinceInIdRegion(int idRegion,int idLanguage)
    {
    	List<State> result=new ArrayList<State>();
		DBManager db=null;
		try
		{
			String SQL=	"SELECT " +
						"	A.ID_LANGUAGE,A.ID_COUNTRY,A.ID_STATE,A.STATE_NAME " +
						"FROM " +
						"	INTER_STATE_LANGUAGE A,INTER_STATE B " +
						"WHERE " +
						"	A.ID_COUNTRY=216 AND " +
						"	B.ID_COUNTRY=216 AND " +
						"	A.ID_STATE=B.ID_STATE AND " +
						"	A.ID_LANGUAGE=? AND " +
						"	B.ID_REGION=? " +
						"ORDER BY " +
						"	A.STATE_NAME";
			
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.setInt(2,idRegion);
			db.executeQuery();
			while(db.next())
			{
				State state=new State();
				state.setIdCountry(db.getInt("ID_COUNTRY"));
				state.setIdState(db.getInt("ID_STATE"));
				state.setStateName(db.getString("STATE_NAME"));
				state.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(state);
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
    
    public static List<Travel> getAllTravel(int idLanguage)
    {
    	List<Travel> result=new ArrayList<Travel>();
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT * FROM INTER_TRAVEL_LANGUAGE WHERE ID_LANGUAGE = ? ORDER BY ID_TRAVEL ";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Travel travel=new Travel();
				travel.setIdLanguage(db.getInt("ID_LANGUAGE"));
				travel.setIdTravel(db.getInt("ID_TRAVEL"));
				travel.setTravelName(db.getString("TRAVEL_NAME"));
				result.add(travel);
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
    public static Travel getTravel(int idTraval, int idLanguage)
    {
    	Travel result=null;
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT * FROM INTER_TRAVEL_LANGUAGE WHERE ID_LANGUAGE = ? AND ID_TRAVEL=? ORDER BY ID_TRAVEL ";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.setInt(2,idTraval);
			db.executeQuery();
			if(db.next())
			{
				result=new Travel();
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setIdTravel(db.getInt("ID_TRAVEL"));
				result.setTravelName(db.getString("TRAVEL_NAME"));
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
    
    public static List<TravelFreq> getAllTravelFreq(int idLanguage)
    {
    	List<TravelFreq> result=new ArrayList<TravelFreq>();
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT * FROM INTER_TRAVEL_FREQ_LANGUAGE WHERE ID_LANGUAGE = ? ORDER BY ID_ORDER";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				TravelFreq travelFreq=new TravelFreq();
				travelFreq.setIdLanguage(db.getInt("ID_LANGUAGE"));
				travelFreq.setIdFequency(db.getInt("ID_FREQUENCY"));
				travelFreq.setFrequencyName(db.getString("FREQUENCY_NAME"));
				travelFreq.setIdOder(db.getInt("ID_ORDER"));
				result.add(travelFreq);
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
    public static TravelFreq getTravelFreq(int idFrequency,int idLanguage)
    {
    	TravelFreq result=null;
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT * FROM INTER_TRAVEL_FREQ_LANGUAGE WHERE ID_LANGUAGE = ? AND ID_FREQUENCY=? ORDER BY ID_ORDER";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.setInt(2,idFrequency);
			db.executeQuery();
			if(db.next())
			{
				result=new TravelFreq();
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setIdFequency(db.getInt("ID_FREQUENCY"));
				result.setFrequencyName(db.getString("FREQUENCY_NAME"));
				result.setIdOder(db.getInt("ID_ORDER"));
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
    public static List<ReadingBook> getAllReadingBook(int idLanguage)
    {
    	List<ReadingBook> result=new ArrayList<ReadingBook>();
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT * FROM INTER_READING_BOOK_LANGUAGE  WHERE ID_LANGUAGE=? ORDER BY BOOK_NAME";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				ReadingBook readingBook=new ReadingBook();
				readingBook.setIdLanguage(db.getInt("ID_LANGUAGE"));
				readingBook.setIdBook(db.getInt("ID_BOOK"));
				readingBook.setBookName(db.getString("BOOK_NAME"));
				result.add(readingBook);
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
    public static ReadingBook getReadingBook(int idBook,int idLanguage)
    {
    	ReadingBook result=null;
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT * FROM INTER_READING_BOOK_LANGUAGE  WHERE ID_LANGUAGE=? AND ID_BOOK=?  ORDER BY BOOK_NAME";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.setInt(2,idBook);
			db.executeQuery();
			if(db.next())
			{
				result=new ReadingBook();
				result.setIdBook(db.getInt("ID_BOOK"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setBookName(db.getString("BOOK_NAME"));
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
    public static List<Pet> getAllPet(int idLanguage)
    {
    	List<Pet> result=new ArrayList<Pet>();
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT *  FROM INTER_PET_LANGUAGE  WHERE ID_LANGUAGE = ? ORDER BY PET_NAME";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.executeQuery();
			while(db.next())
			{
				Pet pet=new Pet();
				pet.setIdLanguage(db.getInt("ID_LANGUAGE"));
				pet.setIdPet(db.getInt("ID_PET"));
				pet.setPetName(db.getString("PET_NAME"));
				result.add(pet);
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
    public static Pet getPet(int idPet,int idLanguage)
    {
    	Pet result=null;
    	DBManager db=null;
		try
		{
			String SQL=	"SELECT *  FROM INTER_PET_LANGUAGE  WHERE ID_LANGUAGE = ? AND ID_PET = ? ORDER BY PET_NAME";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.setInt(2,idPet);
			db.executeQuery();
			if(db.next())
			{
				result=new Pet();
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setIdPet(db.getInt("ID_PET"));
				result.setPetName(db.getString("PET_NAME"));
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
    
	public static ArrayList<CertFieldLanguage> getAllCertFieldLanguage(int idLanguage )
	{
		ArrayList<CertFieldLanguage> result = new ArrayList<CertFieldLanguage>();
		DBManager db = null;
		try
		{	
			String SQL = "SELECT * FROM INTER_CERT_FIELD_LANGUAGE WHERE (ID_LANGUAGE=?)";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.executeQuery();
			while (db.next())
			{	
				CertFieldLanguage certField=new CertFieldLanguage();
				certField.setCertFieldName(db.getString("CERT_FIELD_NAME"));
				certField.setIdCertField(db.getInt("ID_CERT_FIELD"));
				certField.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.add(certField);
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
	
	public static CertFieldLanguage getCertFieldLanguage(int idCertField,int idLanguage )
	{
		CertFieldLanguage result=null;
		DBManager db = null;
		try
		{	
			String SQL = "SELECT * FROM INTER_CERT_FIELD_LANGUAGE WHERE (ID_LANGUAGE=?) AND (ID_CERT_FIELD=?)";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idLanguage);
			db.setInt(2,idCertField);
			db.executeQuery();
			if (db.next())
			{	
				result=new CertFieldLanguage();
				result.setCertFieldName(db.getString("CERT_FIELD_NAME"));
				result.setIdCertField(db.getInt("ID_CERT_FIELD"));
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
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
	
	public static ArrayList<CertSubFieldLanguage> getAllCertSubFieldLanguage (int idCertField,int idLanguage)
	{
		ArrayList<CertSubFieldLanguage> result= new ArrayList<CertSubFieldLanguage>();
		DBManager db = null;
		try
		{
			String SQL = "SELECT * FROM INTER_CERT_SUBFIELD_LANGUAGE WHERE(ID_CERT_FIELD=?) AND (ID_LANGUAGE=?)";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idCertField);
			db.setInt(2,idLanguage);
			db.executeQuery();
			while (db.next())
			{
				CertSubFieldLanguage certSubField=new CertSubFieldLanguage();
				certSubField.setIdLanguage(db.getInt("ID_LANGUAGE"));
				certSubField.setIdCertSubfield(db.getInt("ID_CERT_SUBFIELD"));
				certSubField.setIdCertFiled(db.getInt("ID_CERT_FIELD"));
				certSubField.setCertSubfieldName(db.getString("CERT_SUBFIELD_NAME"));
				result.add(certSubField);
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
	
	public static CertSubFieldLanguage getCertSubFieldLanguage (int idCertField,int idLanguage, int idCertSubfield )
	{
		CertSubFieldLanguage result=null;
		DBManager db = null;
		try
		{
			String SQL = "SELECT * FROM INTER_CERT_SUBFIELD_LANGUAGE WHERE(ID_CERT_FIELD=?) AND (ID_LANGUAGE=?) AND (ID_CERT_SUBFIELD=?)";
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idCertField);
			db.setInt(2,idLanguage);
			db.setInt(3,idCertSubfield);
			db.executeQuery();
			if (db.next())
			{
				result=new CertSubFieldLanguage();
				result.setIdLanguage(db.getInt("ID_LANGUAGE"));
				result.setIdCertSubfield(db.getInt("ID_CERT_SUBFIELD"));
				result.setIdCertFiled(db.getInt("ID_CERT_FIELD"));
				result.setCertSubfieldName(db.getString("CERT_SUBFIELD_NAME"));
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
    
	public static String proficiencyComAndSkill(int level)
	{
		String word="";
		try
		{
			if(level == 5){
				word="SKILL_COM_EXPERT";
			}
			else if(level == 4)
			{
				word="SKILL_COM_ADVANCED";
			}
			else if(level == 3)
			{
				word="SKILL_COM_INTERMEDIATE";
			}
			else if(level == 2)
			{
				word="SKILL_COM_BEGINNER";
			}
			else if(level == 1)
			{
				word="SKILL_COM_LEARNING";
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return word;
	}
	
	public static String proficiencyLanguage(int level)
	{
		String word="";
		try
		{
			if(level == 5){
				word="SKILL_LANGUAGE_FLUENT";
			}
			else if(level == 4)
			{
				word="SKILL_LANGUAGE_ADVANCED";
			}
			else if(level == 3)
			{
				word="SKILL_LANGUAGE_INTERMEDIATE";
			}
			else if(level == 2)
			{
				word="SKILL_LANGUAGE_BEGINNER";
			}
			else if(level == 1)
			{
				word="SKILL_LANGUAGE_LEARNING";
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return word;
	}

	public static String desiredTravel(int code)
	{
		String word="";
		try
		{

				if(code==1)
				{
					word="TARGETJOB_NEGLIGIBLE";
				}
				else if(code==2)
				{
					word="TARGETJOB_UPTO25";
				}
				else if(code==3)
				{
					word="TARGETJOB_UPTO50";
				}
				else if(code==4)
				{
					word="TARGETJOB_UPTO75";
				}
				else if(code==5)
				{
					word="TARGETJOB_UPTO100";
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return word;
	}
	
	public static int getIdQuestionInquiry(int idEmp)
	{
		int result = 0;
		String SQL="select max(id_question) as maxID from poll_company where id_emp='"+ idEmp + "'";
		DBManager db=null;


		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("maxID");
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
	public static PollQuestion getQuestion (int idQuestion, int idLanguage) 
	{
		PollQuestion result=null;
		int lan=1;
		if(idLanguage==38)
		{lan=2;}
		String SQL = "SELECT * FROM POLL_QUESTION WHERE (ID_QUESTION=?) AND (LAN=?) ";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1,idQuestion);
			db.setInt(2, lan);
			db.executeQuery();
			if (db.next())
			{
				result = new PollQuestion();
				result.setIdQuestion(db.getInt("ID_QUESTION"));
				result.setQuestion(db.getString("QUESTION"));
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
	
	public static List<PollAnswer> getAnswerInquiry(int idEmp,int idQuestion ,int idLanguage)
	{
		List<PollAnswer> result=new ArrayList<PollAnswer>();
		String SQL=	"select * from poll_company where id_emp=? and id_question=? order by id_answer ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idQuestion);
			db.executeQuery();
			while(db.next())
			{
				PollAnswer ans=new PollAnswer();
				ans.setIdEmp(db.getInt("ID_EMP"));
				ans .setIdQuestion(db.getInt("ID_QUESTION"));
				ans.setIdAnswer(db.getInt("ID_ANSWER"));
				ans.setAnswer(db.getString("ANSWER"));
				if(idLanguage==38)
				{
					ans.setAnswer(db.getString("ANSWER_TH"));
				}
				ans.setScore(db.getInt("SCORE"));
				result.add(ans);
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
