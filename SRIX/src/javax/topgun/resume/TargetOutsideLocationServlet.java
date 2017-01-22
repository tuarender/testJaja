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
import com.topgun.shris.masterdata.Country;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.shris.masterdata.State;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class TargetOutsideLocationServlet extends HttpServlet 
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
        	if(new LocationManager().countOutsideLocation(idJsk, idResume,resume.getApplyIdCountry())<20) //maximum 20 locations allow
        	{
        		int idCountry=Util.getInt(request.getParameter("idCountry"));
        		int idState=Util.getInt(request.getParameter("idStateOutside"));
        		String otherState=Util.getStr(request.getParameter("otherStateOutside"));
        		String workPermit=Util.getStr(request.getParameter("outsideWorkPermit"),"TRUE");
        		
				if(idCountry>0)
				{
					if(idState<=0 && otherState.equals("")) idState=0; //all State
					Location loc=new Location();
					loc.setIdJsk(idJsk);
					loc.setIdResume(idResume);
					loc.setIdLocation(new LocationManager().getNextId(idJsk, idResume));
					loc.setIdCountry(idCountry);
					loc.setIdState(idState);
					loc.setOtherState(otherState);
					loc.setWorkPermit(workPermit);
					if(loc.getIdState()==0) //selece all city
					{
						new LocationManager().deleteByIdCountry(loc);
						if(new LocationManager().add(loc)!=1)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_LOCATION_FAIL"));
							elements.add("SYSTEM");
						}
					}
					else
					{
						if(!new LocationManager().hasAllStates(loc))
						{
							if(new LocationManager().add(loc)!=1)
							{
								errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_LOCATION_FAIL"));
								elements.add("SYSTEM");
							}
						}
					}
				}
        	}
		}
		else if(service.equals("view"))
		{
			List<Location> locations = new LocationManager().getAllOutsideLocation(idJsk, idResume,resume.getApplyIdCountry());
			if(locations!=null)
			{
				String countryNames[]=new String[locations.size()];
				String stateNames[]=new String[locations.size()];
	
				for(int i=0; i<locations.size(); i++)
				{
					Country country=MasterDataManager.getCountry(locations.get(i).getIdCountry(), resume.getIdLanguage());
					if(country!=null)
					{
						countryNames[i]=Util.getStr(country.getCountryName());
						State state=MasterDataManager.getState(locations.get(i).getIdCountry(),locations.get(i).getIdState(), resume.getIdLanguage());
						if(state!=null)
						{
							stateNames[i]=Util.getStr(state.getStateName());
						}
						else
						{
							stateNames[i]=Util.getStr(locations.get(i).getOtherState());
						}
					}
				}
				
				json.put("locations",locations);
				json.put("countryNames",countryNames);
				json.put("stateNames",stateNames);
			}
		}
		else if(service.equals("delete"))
		{
			int idLocation = Util.getInt(request.getParameter("idLocation"));
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
