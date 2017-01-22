package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.Location;
import com.topgun.resume.LocationManager;
import com.topgun.resume.TargetJobManager;
import com.topgun.shris.masterdata.City;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.shris.masterdata.MasterDataManager.*;
import com.topgun.shris.masterdata.Region;
import com.topgun.shris.masterdata.State;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class TargetLocationServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
        int central[]={4,56,63,42,48,16,74,22,55,58,7,50,2};
		int northeast[]={17,28,69,34,68,5,59,65,52,8,75,14,24,27,21,73,33,78,1,25}; 
		int south[]={46,60,64,18,39,57,66,30,40,13,49,37,72,32};
		int north[]={10,11,20,44,29,43,38,19,62,71,31,41,23,76,15,45,70}; 
		int east[]={12,51,9,6,47,67,61};
		int metro[]={77,35,36,53,54,26};
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
        String service=Util.getStr(request.getParameter("service"));
        Resume resume=new ResumeManager().get(idJsk, idResume);
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
        else if(service.equals("add"))
		{
    		int idRegion = Util.getInt(request.getParameter("idRegion"));
    		int idState=Util.getInt(request.getParameter("idState"));
    		int idCity=Util.getInt(request.getParameter("idCity"));
    		String otherCity=Util.getStr(request.getParameter("otherCity"));
    		if(idRegion>0 && idRegion!=11 &&idState==0)
    		{
    			int[] targetRegion = null ;
    			if(idRegion==8)
    			{
    				targetRegion = metro ;
    			}
    			else if(idRegion==1)
    			{
    				targetRegion = north ;
    			}
    			else if(idRegion==2)
    			{
    				targetRegion = central ;
    			}
    			else if(idRegion==3)
    			{
    				targetRegion = east;
    			}
    			else if(idRegion==4)
    			{
    				targetRegion = northeast;
    			}
    			else if(idRegion==5)
    			{
    				targetRegion = south;
    			}
    			if(targetRegion.length>0)
    			{
    				new LocationManager().deleteByIdRegion(idJsk, idResume, resume.getIdCountry(), idRegion);
    				for(int i=0;i<targetRegion.length;i++)
    				{
    					Location loc=new Location();
    					loc.setIdJsk(idJsk);
    					loc.setIdResume(idResume);
    					loc.setIdLocation(new LocationManager().getNextId(idJsk, idResume));
    					loc.setIdCountry(resume.getApplyIdCountry());
    					loc.setIdState(targetRegion[i]);
    					loc.setIdCity(0);
    					loc.setOtherCity("");
						if(new LocationManager().add(loc)!=1)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_LOCATION_FAIL"));
							elements.add("SYSTEM");
						}
    				}
    			}
    		}
    		else if(idRegion>0 && idRegion!=11 && idState>0)
			{
				if(idCity<=0 && otherCity.equals("")) idCity=0; //allCity
				Location loc=new Location();
				loc.setIdJsk(idJsk);
				loc.setIdResume(idResume);
				loc.setIdLocation(new LocationManager().getNextId(idJsk, idResume));
				loc.setIdCountry(resume.getApplyIdCountry());
				loc.setIdState(idState);
				loc.setIdCity(idCity);
				loc.setOtherCity(otherCity);
				
				if(loc.getIdCity()==0) //selece all city
				{
					new LocationManager().deleteByIdState(loc);
					if(new LocationManager().add(loc)!=1)
					{
						errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_LOCATION_FAIL"));
						elements.add("SYSTEM");
					}
				}
				else
				{
					if(!new LocationManager().hasAllCities(loc) && !new LocationManager().hasThisLocation(loc))
					{
						if(new LocationManager().add(loc)!=1)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_LOCATION_FAIL"));
							elements.add("SYSTEM");
						}
					}
				}
			}
    		else if(idRegion == 11)
    		{
    			Location loc = new Location();
    			loc.setIdJsk(idJsk);
    			loc.setIdResume(idResume);
    			loc.setIdState(idState);
    			if(idState==0)
    			{
    				new LocationManager().deleteAllIndustrial(loc);
    			}
    			if(new LocationManager().addIndustrial(loc)!=1)
				{
					errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_LOCATION_FAIL"));
					elements.add("SYSTEM");
				}
    		}
		}
		else if(service.equals("view"))
		{
			List<Location> targetLocation = new LocationManager().getAllInsideLocationRegionGroup(idJsk, idResume, 216);
			json.put("targetLocation", targetLocation);
		}
		else if(service.equals("delete"))
		{
			int idLocation = Util.getInt(request.getParameter("idLocation"));
			int idRegion = Util.getInt(request.getParameter("idRegion"));
			int type = Util.getInt(request.getParameter("type"));
			if(type==1)
			{
				new LocationManager().deleteByIdRegion(idJsk, idResume, resume.getIdCountry(), idRegion);
			}
			else if(type==2)
			{
				Location loc = new LocationManager().get(idJsk, idResume, idLocation);	
				if(loc!=null)
				{
					if(new LocationManager().delete(loc)!=1)
					{
						errors.add(propMgr.getMessage(resume.getLocale(), "DELETE_LOCATION_FAIL"));
						elements.add("SYSTEM");	
					}
				}
			}
			else if(type==3)
			{
				// type 3 จะใช้ idLocation แทน idIndustrial
				Location loc = new Location();
				loc.setIdJsk(idJsk);
				loc.setIdResume(idResume);
				loc.setIdState(idLocation); 
				if(new LocationManager().deleteIndustrial(loc)!=1)
				{
					errors.add(propMgr.getMessage(resume.getLocale(), "DELETE_LOCATION_FAIL"));
					elements.add("SYSTEM");	
				}
			}
		}
		else if(service.equals("getStateByRegion"))
		{
			int idRegion = Util.getInt(request.getParameter("idRegion"),0);
			List<State> states = null ;
			if(idRegion>0 && idRegion != 11)
			{
				states = MasterDataManager.getAllProvinceInIdRegion(idRegion, resume.getIdLanguage());
			}
			else if(idRegion == 11)
			{
				states = MasterDataManager.getAllIndustrialEstate(resume.getIdLanguage());
			}
			json.put("states", states);
		}
		else if(service.equals("getRegionList"))
		{
			List<Region> regions = MasterDataManager.getAllRegion(resume.getIdLanguage());
			
			// Industrial //
			Region industrial = new Region() ;
			industrial.setIdRegion(11);
			if(resume.getIdLanguage()==38)
			{
				industrial.setName("นิคมอุตสาหกรรม");
			}
			else
			{
				industrial.setName("Industrial Estate");
			}
			regions.add(industrial);
			json.put("regions", regions);
		}
		
        response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
		
		if(errors.size()>0)
		{
			json.put("success",0);
			json.put("errors", errors);
			json.put("elements", elements);
		}
		else
		{
			json.put("success",1);			
		}
		out.print(gson.toJson(json));
		out.close();
	}		
}
