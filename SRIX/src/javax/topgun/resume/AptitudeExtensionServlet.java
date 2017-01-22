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

import com.topgun.resume.Hobby;
import com.topgun.resume.HobbyManager;
import com.topgun.resume.Pet;
import com.topgun.resume.PetManager;
import com.topgun.resume.Reading;
import com.topgun.resume.ReadingManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.Travel;
import com.topgun.resume.TravelManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.google.gson.Gson;

public class AptitudeExtensionServlet extends HttpServlet 
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
        ResumeManager rsmMgr=new ResumeManager();
        
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
        
        String service=Util.getStr(request.getParameter("service"));
        Resume resume=rsmMgr.get(idJsk, idResume);
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
		else if(service.equals("view"))
		{
			int travelFlag=0;
			int readingFlag=0;
			int petFlag=0;
			int hbSize=0;
			List<Travel> jskTravels=null;
			List<Reading> jskReading=null;
			List<Pet> jskPet=null;
			List<Hobby> hobies=new HobbyManager().getAll(idJsk, idResume);
			if(hobies.size()>0)
			{
				for(int i=0; i<hobies.size(); i++)
				{
					if(hobies.get(i).getIdHobby()==43)//have travel
					{
						travelFlag=1;
						jskTravels=TravelManager.getAll(idJsk,idResume);
						if(jskTravels.size()>0)
						{
							hbSize++;
						}
					}
					else if(hobies.get(i).getIdHobby()==45)//have reading
					{
						readingFlag=1;
						jskReading=new ReadingManager().getAll(idJsk, idResume);
						if(jskReading.size()>0)
						{
							hbSize++;
						}
					}
					else if(hobies.get(i).getIdHobby()==56 )//have pet
					{
						petFlag=1;
						jskPet=new PetManager().getAll(idJsk, idResume);
						if(jskPet.size()>0)
						{
							hbSize++;
						}
					}
				}
			}
			json.put("jskTravels",jskTravels);
			json.put("jskReading",jskReading);
			json.put("jskPet",jskPet);
			json.put("travelFlag",travelFlag);
			json.put("readingFlag",readingFlag);
			json.put("petFlag",petFlag);
			json.put("hbSize",hbSize);
		}
		else if(service.equals("editTravel"))
		{
			Hobby hobies=new HobbyManager().get(idJsk, idResume, 43);
			if(hobies!=null)
			{
				String [] traveltravelPlace =  request.getParameterValues("travelPlace");
				if(traveltravelPlace!=null)
				{
					TravelManager.deleteAllTravel(idJsk, idResume);
					for (int i = 0; i < traveltravelPlace.length ; i++)
					{
						int travelFreq =Util.getInt(request.getParameter("travelFreq"+traveltravelPlace[i]));
						if(travelFreq!=-1)
						{
							Travel trav=new Travel();
							trav.setIdJsk(idJsk);
							trav.setIdResume(idResume);
							trav.setIdTravel(Util.getInt(traveltravelPlace[i]));
							trav.setIdFrequency(travelFreq);
							if(TravelManager.add(trav)==0)
							{
								errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_TRAVEL_FAIL"));
								elements.add("SYSTEM");
							}
						}
					}
					if(resume.getIdResume()==0)
					{
						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
					}
					else
					{
						rsmMgr.updateStatus(idJsk,resume.getIdResume(), new ResumeStatusManager().getResumeStatus(rsmMgr.get(idJsk,resume.getIdResume())));
					}
					rsmMgr.updateTimestamp(resume.getIdJsk(), resume.getIdResume());
				}
			}
			else
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_NOT_FOUND"));
				elements.add("SYSTEM");	
			}
		}
		else if(service.equals("editReading"))
		{
			Hobby hobies=new HobbyManager().get(idJsk, idResume, 45);
			if(hobies!=null)
			{
				String [] readingBook =  request.getParameterValues("readingBook");
				if(readingBook!=null)
				{
					new ReadingManager().deleteAllBook(idJsk, idResume);
					for (int i = 0; i < readingBook.length ; i++)
					{
						
						Reading read=new Reading();
						read.setIdJsk(idJsk);
						read.setIdResume(idResume);
						read.setIdBook(Util.getInt(readingBook[i]));
						if(Util.getInt(readingBook[i])==-1)
						{
							String otherBook=Util.getStr(request.getParameter("otherReading"));
							read.setOtherBook(otherBook);
						}
						if(new ReadingManager().add(read)==0)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_READING_FAIL"));
							elements.add("SYSTEM");
						}
					}
					if(resume.getIdResume()==0)
					{
						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
					}
					else
					{
						rsmMgr.updateStatus(idJsk,resume.getIdResume(), new ResumeStatusManager().getResumeStatus(rsmMgr.get(idJsk,resume.getIdResume())));
					}
					rsmMgr.updateTimestamp(resume.getIdJsk(), resume.getIdResume());
				}
			}
			else
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_NOT_FOUND"));
				elements.add("SYSTEM");	
			}
		}
		else if(service.equals("editPet"))
		{
			Hobby hobies=new HobbyManager().get(idJsk, idResume, 56);
			if(hobies!=null)
			{
				String [] pet =  request.getParameterValues("pet");
				if(pet!=null)
				{
					new PetManager().deleteAllPet(idJsk, idResume);
					for (int i = 0; i < pet.length ; i++)
					{
						Pet pets=new Pet();
						pets.setIdJsk(idJsk);
						pets.setIdResume(idResume);
						pets.setIdPet(Util.getInt(pet[i]));
						if(Util.getInt(pet[i])==-1)
						{
							String otherPet=Util.getStr(request.getParameter("otherPet"));
							pets.setOtherPet(otherPet);
						}
						if(new PetManager().add(pets)==0)
						{
							errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_PET_FAIL"));
							elements.add("SYSTEM");
						}
						/*for(int z=0;z<subResumeList.size();z++)
						{
							Hobby hobiesSub=new HobbyManager().get(idJsk, subResumeList.get(z).getIdResume(), 56);
							if(hobiesSub!=null)
							{
								pets.setIdResume(subResumeList.get(z).getIdResume());
								if(new PetManager().add(pets)==0)
								{
									errors.add(propMgr.getMessage(resume.getLocale(), "INSERT_PET_FAIL"));
									elements.add("SYSTEM");
								}
							}
						}*/
					}
					if(resume.getIdResume()==0)
					{
						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
					}
					else
					{
						rsmMgr.updateStatus(idJsk,resume.getIdResume(), new ResumeStatusManager().getResumeStatus(rsmMgr.get(idJsk,resume.getIdResume())));
					}
					rsmMgr.updateTimestamp(resume.getIdJsk(), resume.getIdResume());
				}
				
			}
			else
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_NOT_FOUND"));
				elements.add("SYSTEM");	
			}
		}
		else if(service.equals("checkExtension"))
		{
			List<Travel> jskTravels=TravelManager.getAll(idJsk,idResume);
			List<Reading> jskReading=new ReadingManager().getAll(idJsk, idResume);
			List<Pet> jskPet=new PetManager().getAll(idJsk, idResume);
			if(jskTravels.size()==0 && jskReading.size()==0 && jskPet.size()==0)
			{
				json.put("canNext",false);
			}
			else
			{
				json.put("canNext",true);
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
	}
}
