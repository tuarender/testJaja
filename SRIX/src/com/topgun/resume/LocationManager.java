package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.shris.masterdata.City;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.shris.masterdata.State;
import com.topgun.util.DBManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class LocationManager 
{
	public Location get(int idJsk,int idResume,int idLocation)
	{
		Location result=null;
		String SQL=	"SELECT " +
					"	ID_COUNTRY,ID_STATE,ID_CITY,OTHER_CITY,OTHER_STATE,WORK_PERMIT " +
					"FROM " +
					"	INTER_LOCATION " +
					"WHERE " +
					"	(ID_JSK=?) AND (ID_RESUME=?) AND (ID_LOCATION=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idLocation);
    	    db.executeQuery();
    		if(db.next())
    		{
    			result=new Location();
    			result.setIdJsk(idJsk);
    			result.setIdResume(idResume);
    			result.setIdLocation(idLocation);
    			result.setIdCountry(db.getInt("ID_COUNTRY"));
    			result.setIdState(db.getInt("ID_STATE"));
    			result.setIdCity(db.getInt("ID_CITY"));
    			result.setOtherCity(db.getString("OTHER_CITY"));
    			result.setOtherState(db.getString("OTHER_STATE"));
    			result.setWorkPermit(db.getString("WORK_PERMIT"));
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
	
	public List<Location> getAll(int idJsk,int idResume)
	{
		List<Location> result = new ArrayList<Location>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID_LOCATION,ID_COUNTRY,ID_STATE,ID_CITY,OTHER_CITY," +
					"	OTHER_STATE,WORK_PERMIT " +
					"FROM " +
					"	INTER_LOCATION " +
					"WHERE "+
					"	ID_JSK=? AND ID_RESUME=?  " +
					"ORDER BY " +
					"	ID_LOCATION";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Location loc = new Location();
		    	loc.setIdJsk(db.getInt("ID_JSK"));
		    	loc.setIdResume(db.getInt("ID_RESUME"));
		    	loc.setIdLocation(db.getInt("ID_LOCATION"));
		    	loc.setIdCountry(db.getInt("ID_COUNTRY"));
		    	loc.setIdState(db.getInt("ID_STATE"));
		    	loc.setIdCity(db.getInt("ID_CITY"));
		    	loc.setOtherCity(db.getString("OTHER_CITY"));
		    	loc.setOtherState(db.getString("OTHER_STATE"));
		    	loc.setWorkPermit(db.getString("WORK_PERMIT"));
		    	result.add(loc);
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
	
	public List<Location> getAllLocationByRegion(int idJsk,int idResume,int idCountry,int idRegion)
	{
		List<Location> result = new ArrayList<Location>();
		DBManager db=null ;
		String SQL = "";
		try
		{
			db = new DBManager();
			SQL=	"SELECT A.* , B.ID_REGION FROM " + 
					" ( SELECT " +
					"	ID_JSK,ID_RESUME,ID_LOCATION,ID_COUNTRY,ID_STATE,ID_CITY,OTHER_CITY," +
					"	OTHER_STATE,WORK_PERMIT " +
					"FROM " +
					"	INTER_LOCATION " +
					"WHERE "+
					"	ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=? ) A ," +
					" INTER_STATE B" +
					" WHERE A.ID_STATE = B.ID_STATE AND A.ID_COUNTRY = B.ID_COUNTRY AND ID_REGION = ? " +
					"ORDER BY " +
					"	A.ID_LOCATION";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idCountry);
			db.setInt(4, idRegion);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Location loc = new Location();
		    	loc.setIdJsk(db.getInt("ID_JSK"));
		    	loc.setIdResume(db.getInt("ID_RESUME"));
		    	loc.setIdLocation(db.getInt("ID_LOCATION"));
		    	loc.setIdCountry(db.getInt("ID_COUNTRY"));
		    	loc.setIdState(db.getInt("ID_STATE"));
		    	loc.setIdCity(db.getInt("ID_CITY"));
		    	loc.setOtherCity(db.getString("OTHER_CITY"));
		    	loc.setOtherState(db.getString("OTHER_STATE"));
		    	loc.setWorkPermit(db.getString("WORK_PERMIT"));
		    	loc.setIdRegion(db.getInt("ID_REGION"));
		    	result.add(loc);
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
	
	public List<Location> getAllOutsideLocation(int idJsk,int idResume, int idCountry)
	{
		List<Location> result = new ArrayList<Location>();
		DBManager db=null;
		String SQL=	"SELECT " +
					"	ID_JSK,ID_RESUME,ID_LOCATION,ID_COUNTRY,ID_STATE,ID_CITY,OTHER_CITY," +
					"	OTHER_STATE,WORK_PERMIT " +
					"FROM " +
					"	INTER_LOCATION " +
					"WHERE "+
					"	ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY<>? " +
					"ORDER BY " +
					"	ID_LOCATION";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idCountry);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Location loc = new Location();
		    	loc.setIdJsk(db.getInt("ID_JSK"));
		    	loc.setIdResume(db.getInt("ID_RESUME"));
		    	loc.setIdLocation(db.getInt("ID_LOCATION"));
		    	loc.setIdCountry(db.getInt("ID_COUNTRY"));
		    	loc.setIdState(db.getInt("ID_STATE"));
		    	loc.setIdCity(db.getInt("ID_CITY"));
		    	loc.setOtherCity(db.getString("OTHER_CITY"));
		    	loc.setOtherState(db.getString("OTHER_STATE"));
		    	loc.setWorkPermit(db.getString("WORK_PERMIT"));
		    	result.add(loc);
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
	
	public List<Location> getAllInsideLocation(int idJsk,int idResume, int idCountry)
	{
		List<Location> result = new ArrayList<Location>();
		DBManager db=null;
		String SQL=	"SELECT A.* , B.ID_REGION FROM " + 
					" ( SELECT " +
					"	ID_JSK,ID_RESUME,ID_LOCATION,ID_COUNTRY,ID_STATE,ID_CITY,OTHER_CITY," +
					"	OTHER_STATE,WORK_PERMIT " +
					"FROM " +
					"	INTER_LOCATION " +
					"WHERE "+
					"	ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=? ) A ," +
					" INTER_STATE B" +
					" WHERE A.ID_STATE = B.ID_STATE AND A.ID_COUNTRY = B.ID_COUNTRY " +
					"ORDER BY " +
					"	A.ID_LOCATION";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idCountry);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Location loc = new Location();
		    	loc.setIdJsk(db.getInt("ID_JSK"));
		    	loc.setIdResume(db.getInt("ID_RESUME"));
		    	loc.setIdLocation(db.getInt("ID_LOCATION"));
		    	loc.setIdCountry(db.getInt("ID_COUNTRY"));
		    	loc.setIdState(db.getInt("ID_STATE"));
		    	loc.setIdCity(db.getInt("ID_CITY"));
		    	loc.setOtherCity(db.getString("OTHER_CITY"));
		    	loc.setOtherState(db.getString("OTHER_STATE"));
		    	loc.setWorkPermit(db.getString("WORK_PERMIT"));
		    	loc.setIdRegion(db.getInt("ID_REGION"));
		    	result.add(loc);
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
	
	public List<Location> getAllInsideLocationRegionGroup(int idJsk,int idResume, int idCountry)
	{
		List<Location> locations = this.getAllInsideLocation(idJsk, idResume, idCountry);
		List<Location> industrialLocation  = this.getAllIndustrialLocation(idJsk, idResume);
		List<Location> targetLocation = new ArrayList<Location>();
		List<Location> metroLocation = new ArrayList<Location>();
		List<Location> northLocation = new ArrayList<Location>();
		List<Location> centralLocation = new ArrayList<Location>();
		List<Location> eastLocation = new ArrayList<Location>();
		List<Location> northEastLocation = new ArrayList<Location>();
		List<Location> southLocation = new ArrayList<Location>();
		List<Location> bangkokLocation = new ArrayList<Location>();
		int central[]={4,56,63,42,48,16,74,22,55,58,7,50,2};
		int northeast[]={17,28,69,34,68,5,59,65,52,8,75,14,24,27,21,73,33,78,1,25}; 
		int south[]={46,60,64,18,39,57,66,30,40,13,49,37,72,32};
		int north[]={10,11,20,44,29,43,38,19,62,71,31,41,23,76,15,45,70}; 
		int east[]={12,51,9,6,47,67,61};
		int metro[]={35,36,53,54,26};
		boolean fullBangkok = false ;
		Resume resume=new ResumeManager().get(idJsk, idResume);
		PropertiesManager propMgr=new PropertiesManager();
		if((locations!=null || industrialLocation!=null) && resume!=null)
		{
			if(locations!=null)
			{
				for(int i=0; i<locations.size();i++)
				{
					if(locations.get(i).getIdRegion()==8)
					{
						if(locations.get(i).getIdState()==77)
						{
							if(locations.get(i).getIdCity()==0)
							{
								fullBangkok = true ;
							}
							bangkokLocation.add(locations.get(i));
						}
						else
						{
							metroLocation.add(locations.get(i));
						}
					}
					else if(locations.get(i).getIdRegion()==1)
					{
						northLocation.add(locations.get(i));
					}
					else if(locations.get(i).getIdRegion()==2)
					{
						centralLocation.add(locations.get(i));
					}
					else if(locations.get(i).getIdRegion()==3)
					{
						eastLocation.add(locations.get(i));
					}
					else if(locations.get(i).getIdRegion()==4)
					{
						northEastLocation.add(locations.get(i));
					}
					else if(locations.get(i).getIdRegion()==5)
					{
						southLocation.add(locations.get(i));
					}
				}
				if(bangkokLocation.size()==50)
				{
					fullBangkok = true ;
				}
				
				if(metroLocation.size()==metro.length && fullBangkok)
				{
					Location location = new Location();
					location.setIdRegion(8);
					location.setStateName(new MasterDataManager().getRegion(8, resume.getIdLanguage()).getName()+" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL"));
					location.setType(1);
					targetLocation.add(location);
				}
				if(northLocation.size()==north.length)
				{
					Location location = new Location();
					location.setIdRegion(1);
					location.setStateName(new MasterDataManager().getRegion(1, resume.getIdLanguage()).getName()+" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL"));
					location.setType(1);
					targetLocation.add(location);
				}
				if(centralLocation.size()==central.length)
				{
					Location location = new Location();
					location.setIdRegion(2);
					location.setStateName(new MasterDataManager().getRegion(2, resume.getIdLanguage()).getName()+" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL"));
					location.setType(1);
					targetLocation.add(location);
				}
				if(eastLocation.size()==east.length)
				{
					Location location = new Location();
					location.setIdRegion(3);
					location.setStateName(new MasterDataManager().getRegion(3, resume.getIdLanguage()).getName()+" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL"));
					location.setType(1);
					targetLocation.add(location);
				}
				if(northEastLocation.size()==northeast.length)
				{
					Location location = new Location();
					location.setIdRegion(4);
					location.setStateName(new MasterDataManager().getRegion(4, resume.getIdLanguage()).getName()+" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL"));
					location.setType(1);
					targetLocation.add(location);
				}
				if(southLocation.size()==south.length)
				{
					Location location = new Location();
					location.setIdRegion(5);
					location.setStateName(new MasterDataManager().getRegion(5, resume.getIdLanguage()).getName()+" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL"));
					location.setType(1);
					targetLocation.add(location);
				}
				
				if(metroLocation.size()!=metro.length || !fullBangkok)
				{
					for(int i=0;i<bangkokLocation.size();i++)
					{
						Location location = new Location();
						State state=MasterDataManager.getState(bangkokLocation.get(i).getIdCountry(),bangkokLocation.get(i).getIdState(), resume.getIdLanguage());
						City  city = null ;
						String stateName = state.getStateName() ;
						location.setIdRegion(8);
						if(bangkokLocation.get(i).getIdState()==77)
						{
							if(bangkokLocation.get(i).getIdCity()>0)
							{
								city=MasterDataManager.getCity(bangkokLocation.get(i).getIdCountry(),bangkokLocation.get(i).getIdState(),bangkokLocation.get(i).getIdCity(), resume.getIdLanguage());
								location.setIdCity(bangkokLocation.get(i).getIdCity());
								stateName+=" "+city.getCityName();
							}
							else
							{
								stateName+=" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL");
							}
						}
						location.setIdState(bangkokLocation.get(i).getIdState());
						location.setType(2);
						location.setIdLocation(bangkokLocation.get(i).getIdLocation());
						location.setStateName(stateName);
						targetLocation.add(location);
					}
					for(int i=0;i<metroLocation.size();i++)
					{
						Location location = new Location();
						State state=MasterDataManager.getState(metroLocation.get(i).getIdCountry(),metroLocation.get(i).getIdState(), resume.getIdLanguage());
						String stateName = state.getStateName() ;
						location.setIdRegion(1);
						location.setIdState(metroLocation.get(i).getIdState());
						location.setType(2);
						location.setIdLocation(metroLocation.get(i).getIdLocation());
						if(metroLocation.get(i).getIdCity()<=0)
						{
							if(!metroLocation.get(i).getOtherCity().equals(""))
							{
								stateName+=" "+metroLocation.get(i).getOtherCity();
							}
							else
							{
								stateName+=" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL");
							}
						}
						location.setStateName(stateName);
						targetLocation.add(location);
					}
				}
				if(northLocation.size()!=north.length)
				{
					for(int i=0;i<northLocation.size();i++)
					{
						Location location = new Location();
						State state=MasterDataManager.getState(northLocation.get(i).getIdCountry(),northLocation.get(i).getIdState(), resume.getIdLanguage());
						String stateName = state.getStateName() ;
						location.setIdRegion(1);
						location.setIdState(northLocation.get(i).getIdState());
						location.setType(2);
						location.setIdLocation(northLocation.get(i).getIdLocation());
						if(northLocation.get(i).getIdCity()<=0)
						{
							if(!northLocation.get(i).getOtherCity().equals(""))
							{
								stateName+=" "+northLocation.get(i).getOtherCity();
							}
							else
							{
								stateName+=" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL");
							}
						}
						location.setStateName(stateName);
						targetLocation.add(location);
					}
				}
				
				if(centralLocation.size()!=central.length)
				{
					for(int i=0;i<centralLocation.size();i++)
					{
						Location location = new Location();
						State state=MasterDataManager.getState(centralLocation.get(i).getIdCountry(),centralLocation.get(i).getIdState(), resume.getIdLanguage());
						String stateName = state.getStateName() ;
						location.setIdRegion(2);
						location.setIdState(centralLocation.get(i).getIdState());
						location.setType(2);
						location.setIdLocation(centralLocation.get(i).getIdLocation());
						if(centralLocation.get(i).getIdCity()<=0)
						{
							if(!centralLocation.get(i).getOtherCity().equals(""))
							{
								stateName+=" "+centralLocation.get(i).getOtherCity();
							}
							else
							{
								stateName+=" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL");
							}
						}
						location.setStateName(stateName);
						targetLocation.add(location);
					}
				}
				
				if(eastLocation.size()!=east.length)
				{
					for(int i=0;i<eastLocation.size();i++)
					{
						Location location = new Location();
						State state=MasterDataManager.getState(eastLocation.get(i).getIdCountry(),eastLocation.get(i).getIdState(), resume.getIdLanguage());
						String stateName = state.getStateName() ;
						location.setIdRegion(3);
						location.setIdState(eastLocation.get(i).getIdState());
						location.setType(2);
						location.setIdLocation(eastLocation.get(i).getIdLocation());
						if(eastLocation.get(i).getIdCity()<=0)
						{
							if(!eastLocation.get(i).getOtherCity().equals(""))
							{
								stateName+=" "+eastLocation.get(i).getOtherCity();
							}
							else
							{
								stateName+=" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL");
							}
						}
						location.setStateName(stateName);
						targetLocation.add(location);
					}
				}
				
				if(northEastLocation.size()!=northeast.length)
				{
					for(int i=0;i<northEastLocation.size();i++)
					{
						Location location = new Location();
						State state=MasterDataManager.getState(northEastLocation.get(i).getIdCountry(),northEastLocation.get(i).getIdState(), resume.getIdLanguage());
						String stateName = state.getStateName() ;
						location.setIdRegion(4);
						location.setIdState(northEastLocation.get(i).getIdState());
						location.setType(2);
						location.setIdLocation(northEastLocation.get(i).getIdLocation());
						if(northEastLocation.get(i).getIdCity()<=0)
						{
							if(!northEastLocation.get(i).getOtherCity().equals(""))
							{
								stateName+=" "+northEastLocation.get(i).getOtherCity();
							}
							else
							{
								stateName+=" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL");
							}
						}
						location.setStateName(stateName);
						targetLocation.add(location);
					}
				}
				
				if(southLocation.size()!=south.length)
				{
					for(int i=0;i<southLocation.size();i++)
					{
						Location location = new Location();
						State state=MasterDataManager.getState(southLocation.get(i).getIdCountry(),southLocation.get(i).getIdState(), resume.getIdLanguage());
						String stateName = state.getStateName() ;
						location.setIdRegion(5);
						location.setIdState(southLocation.get(i).getIdState());
						location.setType(2);
						location.setIdLocation(southLocation.get(i).getIdLocation());
						if(southLocation.get(i).getIdCity()<=0)
						{
							if(!southLocation.get(i).getOtherCity().equals(""))
							{
								stateName+=" "+southLocation.get(i).getOtherCity();
							}
							else
							{
								stateName+=" "+propMgr.getMessage(resume.getLocale(), "GLOBAL_ALL");
							}
						}
						location.setStateName(stateName);
						targetLocation.add(location);
					}
				}
			}
			
			if(industrialLocation!=null)
			{
				for(int i=0;i<industrialLocation.size();i++)
				{
					State state = MasterDataManager.getIndustrialEstate(industrialLocation.get(i).getIdState(), resume.getIdLanguage());
					String stateName = "" ;
					if(state!=null)
					{
						stateName = state.getStateName();
					}
					else if(industrialLocation.get(i).getIdState()==0)
					{
						stateName = propMgr.getMessage(resume.getLocale(), "TARGET_ALL_INDUSTRIAL");
					}
					Location location = new Location();
					location.setIdRegion(11);
					location.setType(3);
					location.setIdLocation(industrialLocation.get(i).getIdState());
					location.setStateName(stateName);
					targetLocation.add(location);
				}
			}
		}
		return targetLocation ;
	}
	
	public int add(Location loc)
    {
    	int result = 0;
    	String SQL= "INSERT INTO INTER_LOCATION " +
    					"(" +
    						"ID_JSK," +
    						"ID_RESUME," +
    						"ID_LOCATION," +
    						"ID_COUNTRY,"+
    						"ID_STATE," +
    						"ID_CITY," +
    						"OTHER_STATE," +
    						"OTHER_CITY," +
    						"WORK_PERMIT" +
    						") " +
    						"VALUES(?,?,?,?,?,?,?,?,?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, loc.getIdJsk());
    		db.setInt(2, loc.getIdResume());
    		db.setInt(3, loc.getIdLocation());
    		db.setInt(4, loc.getIdCountry());
    		db.setInt(5, loc.getIdState());
    		db.setInt(6, loc.getIdCity());
    		db.setString(7, loc.getOtherState());
    		db.setString(8, loc.getOtherCity());
    		db.setString(9, loc.getWorkPermit());
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
	
    public int delete(Location loc)
    {
    	DBManager db=null;
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_LOCATION WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_LOCATION=?)";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, loc.getIdJsk());
			db.setInt(2, loc.getIdResume());
			db.setInt(3, loc.getIdLocation());
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
    
    public int deleteAll(int idJsk, int idResume)
    {
    	DBManager db=null;
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_LOCATION WHERE (ID_JSK=?) AND (ID_RESUME=?) ";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
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

    public int delete(int idJsk, int idResume, int idLocation)
    {
    	DBManager db=null;
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_LOCATION WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_LOCATION=?)";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idLocation);
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
    
    public int update(Location loc)
    {
    	int result = 0;
    	DBManager db=null;
    	String SQL=	"UPDATE " +
    				"	INTER_LOCATION " +
    				"SET" +
    				"	OTHER_STATE=?," +
    				"	OTHER_CITY=?," +
    				"	WORK_PERMIT=?," +
    				"	ID_COUNTRY=?," +
    				"	ID_STATE=?," +
    				"	ID_CITY=? " +
    				"WHERE " +
    				"	ID_JSK=? AND " +
    				"	ID_RESUME=? AND " +
    				"	ID_LOCATION ? ";
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, loc.getOtherState());
    		db.setString(2, loc.getOtherCity());
    		db.setString(3, loc.getWorkPermit());
    		db.setInt(4, loc.getIdCountry());
    		db.setInt(5, loc.getIdState());
    		db.setInt(6, loc.getIdCity());
    		db.setInt(7, loc.getIdJsk());
    		db.setInt(8, loc.getIdResume());
    		db.setInt(9, loc.getIdLocation());
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
    
    public int getNextId(int idJsk,int idResume)
	{
		int result=1;
		String SQL="SELECT MAX(ID_LOCATION) AS MAXID FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				if(db.getString("MAXID")!=null)
				{
					result=db.getInt("MAXID")+1;
				}
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
    
    public int countInsideLocation(int idJsk, int idResume, int idCountry)
    {
    	int result=0;
    	String SQL="SELECT COUNT(ID_LOCATION) AS TOTAL FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idCountry);
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
    
    public int countOutsideLocation(int idJsk, int idResume, int idCountry)
    {
    	int result=0;
    	String SQL="SELECT COUNT(ID_LOCATION) AS TOTAL FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY<>?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idCountry);
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
    
	public boolean hasThisLocation(Location loc)
	{
		boolean result=false;
		String SQL="SELECT ID_JSK FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=? AND ID_STATE=? AND ID_CITY=?";
		if(loc.getIdCity()==-1)
		{
			SQL="SELECT ID_JSK FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=? AND ID_STATE=? AND ID_CITY=? AND OTHER_CITY='"+loc.getOtherCity()+"'";
		}
		
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, loc.getIdJsk());
			db.setInt(2, loc.getIdResume());
			db.setInt(3, loc.getIdCountry());
			db.setInt(4, loc.getIdState());
			db.setInt(5, loc.getIdCity());
    	    db.executeQuery();
    		if(db.next())
    		{
    			result=true;
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
	
    public boolean hasAllCities(Location loc)
    {
    	boolean result=false;
    	String SQL="SELECT ID_JSK FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=? AND ID_STATE=? AND ID_CITY=0";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, loc.getIdJsk());
			db.setInt(2, loc.getIdResume());
			db.setInt(3, loc.getIdCountry());
			db.setInt(4, loc.getIdState());
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
    
    public boolean hasAllStates(Location loc)
    {
    	boolean result=false;
    	String SQL="SELECT ID_JSK FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=? AND ID_STATE=0";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, loc.getIdJsk());
			db.setInt(2, loc.getIdResume());
			db.setInt(3, loc.getIdCountry());
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
    
    public int deleteByIdRegion(int idJsk,int idResume,int idCountry,int idRegion)
    {
    	int result = 0 ;
    	String sql = "" ;
    	DBManager db = null ;
    	try
    	{
    		db = new DBManager() ;
    		sql = "DELETE FROM INTER_LOCATION WHERE ID_LOCATION IN "
    				+ "(SELECT A.ID_LOCATION FROM INTER_LOCATION A , INTER_STATE B "
    				+ "WHERE A.ID_JSK = ? AND A.ID_RESUME = ? AND A.ID_COUNTRY = ? AND A.ID_STATE = B.ID_STATE AND B.ID_REGION = ?) "
    				+ "AND ID_JSK = ? AND ID_RESUME = ? AND ID_COUNTRY = ?";
    		db.createPreparedStatement(sql);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idCountry);
    		db.setInt(4, idRegion);
    		db.setInt(5, idJsk);
    		db.setInt(6, idResume);
    		db.setInt(7, idCountry);
    		result = db.executeUpdate();
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
    
    public int deleteByIdState(Location loc)
    {
    	int result=0;
    	String SQL="DELETE FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=? AND ID_STATE=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, loc.getIdJsk());
			db.setInt(2, loc.getIdResume());
			db.setInt(3, loc.getIdCountry());
			db.setInt(4, loc.getIdState());
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

    public int deleteByIdCountry(Location loc)
    {
    	int result=0;
    	String SQL="DELETE FROM INTER_LOCATION WHERE ID_JSK=? AND ID_RESUME=? AND ID_COUNTRY=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, loc.getIdJsk());
			db.setInt(2, loc.getIdResume());
			db.setInt(3, loc.getIdCountry());
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
    
    public int addIndustrial(Location location)
    {
    	DBManager db = null ;
    	String sql = "";
    	int result = 0 ;
    	try
    	{
    		sql = "INSERT INTO INTER_INDUSTRIAL_LOCATION(ID_JSK,ID_RESUME,ID_INDUSTRIAL) VALUES(?,?,?)";
    		db = new DBManager();
    		db.createPreparedStatement(sql);
    		db.setInt(1, location.getIdJsk());
    		db.setInt(2, location.getIdResume());
    		db.setInt(3, location.getIdState());
    		result = db.executeUpdate();
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
    
    public int deleteIndustrial(Location location)
    {
    	String sql = "";
    	DBManager db = null ;
    	int result = 0 ;
    	try
    	{
    		sql = "DELETE FROM INTER_INDUSTRIAL_LOCATION WHERE ID_JSK = ? AND ID_RESUME = ? AND ID_INDUSTRIAL = ? ";
    		db = new DBManager() ;
    		db.createPreparedStatement(sql);
    		db.setInt(1,location.getIdJsk());
    		db.setInt(2, location.getIdResume());
    		db.setInt(3, location.getIdState());
    		result = db.executeUpdate() ;
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
    
    public int deleteAllIndustrial(int idJsk,int idResume)
    {
    	String sql = "";
    	DBManager db = null ;
    	int result = 0 ;
    	try
    	{
    		sql = "DELETE FROM INTER_INDUSTRIAL_LOCATION WHERE ID_JSK = ? AND ID_RESUME = ?";
    		db = new DBManager() ;
    		db.createPreparedStatement(sql);
    		db.setInt(1,idJsk);
    		db.setInt(2,idResume);
    		result = db.executeUpdate() ;
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
    
    public int deleteAllIndustrial(Location location)
    {
    	String sql = "";
    	DBManager db = null ;
    	int result = 0 ;
    	try
    	{
    		sql = "DELETE FROM INTER_INDUSTRIAL_LOCATION WHERE ID_JSK = ? AND ID_RESUME = ?";
    		db = new DBManager() ;
    		db.createPreparedStatement(sql);
    		db.setInt(1,location.getIdJsk());
    		db.setInt(2, location.getIdResume());
    		result = db.executeUpdate() ;
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
    
    public List<Location> getAllIndustrialLocation(int idJsk , int idResume)
    {
    	List<Location> result = new ArrayList<Location>();
    	DBManager db = null ;
    	String sql = "";
    	try
    	{
    		sql = "SELECT * FROM INTER_INDUSTRIAL_LOCATION WHERE ID_JSK = ? AND ID_RESUME = ?";
    		db = new DBManager() ;
    		db.createPreparedStatement(sql);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.executeQuery();
    		while(db.next())
    		{
    			Location location = new Location();
    			location.setIdJsk(db.getInt("ID_JSK"));
    			location.setIdResume(db.getInt("ID_RESUME"));
    			location.setIdState(db.getInt("ID_INDUSTRIAL"));
    			result.add(location);
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

}
