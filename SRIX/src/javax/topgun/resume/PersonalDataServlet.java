package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.Social;
import com.topgun.resume.SocialManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;


import com.google.gson.Gson;

public class PersonalDataServlet extends HttpServlet 
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
        
        Resume resume=new ResumeManager().get(idJsk, idResume);
        if(resume==null)
        {
        	String locale=Util.getStr(request.getSession().getAttribute("SESSION_SESSION_LOCALE"),"th_TH");
        	errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND")+":("+idJsk+","+idResume+")");
			elements.add("SYSTEM");	
        }
        else
        {
        	int bDay=-1;
    		int bMonth=-1;
    		int bYear=-1;
    		Date birthDate=null;
    		
    		Social social = new Social();
    		
        	//salutation
        	String salutation=Util.getStr(request.getParameter("salutation"));
        	if(salutation.equals("OTHER"))
        	{
        		salutation=Util.getStr(request.getParameter("otherSalutation"));
        	}
        	
        	if(salutation.equals(""))
        	{
				errors.add(propMgr.getMessage(resume.getLocale(), "SALUTATION_REQUIRED"));
				elements.add("saluttion");        		
        	}
        	else
        	{
        		resume.setSalutation(salutation);
        	}
        	
        	//firstName
			String firstName=Util.getStr(request.getParameter("firstName"));
			if(firstName.equals("") && resume.getIdLanguage()!=38)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "FIRSTNAME_REQUIRED"));
				elements.add("firstName");
			}
			else
			{
				resume.setFirstName(firstName);
			}
			
			//lastName
			String lastName=Util.getStr(request.getParameter("lastName"));
			if(lastName.equals("") && resume.getIdLanguage()!=38)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "LASTNAME_REQUIRED"));
				elements.add("lastName");
			}
			else
			{
				resume.setLastName(lastName);
			}
			String firstNameThai=Util.getStr(request.getParameter("firstNameThai"));
			String lastNameThai=Util.getStr(request.getParameter("lastNameThai"));
			resume.setFirstNameThai(firstNameThai);
			resume.setLastNameThai(lastNameThai);
			
			

			bDay=Util.getInt(request.getParameter("bDay"));
			bMonth=Util.getInt(request.getParameter("bMonth"));
			bYear=Util.getInt(request.getParameter("bYear"));
		
			if(bDay<=0)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_DAY_REQUIRED"));
				elements.add("bDay");				
			}

			if(bMonth<=0)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_MONTH_REQUIRED"));
				elements.add("bMonth");				
			}

			if(bYear<=0)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "GLOBAL_YEAR_REQUIRED"));
				elements.add("bYear");				
			}

			birthDate=Util.getSQLDate(bDay, bMonth, bYear);
			if(birthDate!=null)
			{
				resume.setBirthDate(birthDate);
			}
			
			//height
			float height=Util.getFloat(request.getParameter("height"));
			if(height<=0.0)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "HEIGHT_REQUIRED"));
				elements.add("height");
			}
			else
			{
				resume.setHeight(height);
			}

			//weight
			float weight=Util.getFloat(request.getParameter("weight"));
			if(height<=0.0)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "WEIGHT_REQUIRED"));
				elements.add("weight");
			}
			else
			{
				resume.setWeight(weight);
			}
			
			//citizenship
			String citizenship=Util.getStr(request.getParameter("citizenship"));
			if(citizenship.equals("OTHER"))
			{
				citizenship=Util.getStr(request.getParameter("otherCitizenship"));
			}
			if(citizenship.equals(""))
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "CITIZENSHIP_REQUIRED"));
				elements.add("citizenship");
			}
			else
			{
				resume.setCitizenship(citizenship);
			}
			
	
			int idCountry=Util.getInt(request.getParameter("idCountry"));
			if(idCountry<=0)
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "COUNTRY_RESIDENCE_REQUIRED"));
				elements.add("idCountry");
			}
			else
			{
				resume.setIdCountry(idCountry);
			}	
			
			int idState=Util.getInt(request.getParameter("idState"));
			
			if(idState<=0)
			{
				String otherState=Util.getStr(request.getParameter("otherState"));
				if(otherState.equals(""))
				{
					errors.add(propMgr.getMessage(resume.getLocale(), "STATE_REQUIRED"));
					elements.add("idState");
				}
				else
				{
					resume.setIdState(-1);
					resume.setOtherState(otherState);
				}
			}
			else
			{
				resume.setIdState(idState);
				resume.setOtherState("");
			}			
			
			int idCity=Util.getInt(request.getParameter("idCity"));
			if(idCity<=0)
			{
				String otherCity=Util.getStr(request.getParameter("otherCity"));
				if(otherCity.equals("") && idCountry == 216)
				{
					errors.add(propMgr.getMessage(resume.getLocale(), "CITY_REQUIRED"));
					elements.add("idCity");
				}
				else
				{
					resume.setIdCity(-1);
					resume.setOtherCity(otherCity);
				}
			}
			else
			{
				resume.setIdCity(idCity);
				resume.setOtherCity("");
			}			
				
			String homeAddress=Util.getStr(request.getParameter("homeAddress"));
			if(homeAddress.equals(""))
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "HOMEADDRESS_REQUIRED"));
				elements.add("homeAddress");
			}
			else
			{
				resume.setHomeAddress(homeAddress);
			}

			
			String postal=Util.getStr(request.getParameter("postal"));
			if(postal.equals(""))
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "POSTAL_REQUIRED"));
				elements.add("postal");
			}
			else
			{
				resume.setPostal(postal);
			}
			
			
			String primaryPhone=Util.getStr(request.getParameter("primaryPhone"));
			if(primaryPhone.equals(""))
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "PRIMARYPHONE_REQUIRED"));
				elements.add("primaryPhone");
			}
			else
			{
				resume.setPrimaryPhone(primaryPhone);
			}
			
			String primaryPhoneType=Util.getStr(request.getParameter("primaryPhoneType"));
			if(primaryPhoneType.equals(""))
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "PRIMARYPHONETYPE_REQUIRED"));
				elements.add("primaryPhoneType");
			}
			else
			{
				resume.setPrimaryPhoneType(primaryPhoneType);
			}
			
			String secondaryPhone=Util.getStr(request.getParameter("secondaryPhone"));
			resume.setSecondaryPhone(secondaryPhone);
			
			String secondaryPhoneType=Util.getStr(request.getParameter("secondaryPhoneType"));
			resume.setSecondaryPhoneType(secondaryPhoneType);

			String secondaryEmail=Util.getStr(request.getParameter("secondaryEmail"));
			resume.setSecondaryEmail(secondaryEmail);

			String ownCar=Util.getStr(request.getParameter("ownCar"));
			if(primaryPhoneType.equals(""))
			{
				errors.add(propMgr.getMessage(resume.getLocale(), "OWNCAR_REQUIRED"));
				elements.add("ownCar");
			}
			else
			{
				resume.setOwnCarStatus(ownCar);
			}
			
			String lineId = Util.getStr(request.getParameter("lineId"));
			social.setLineId(lineId.trim());
			
			String skype = Util.getStr(request.getParameter("skype"));
			social.setSkype(skype.trim());
			
			if(errors.size()==0)
			{
				if(new ResumeManager().update(resume)!=1)
				{
					errors.add(propMgr.getMessage(resume.getLocale(), "RESUME_UPDATE_ERROR"));
					elements.add("SYSTEM");	
					
				}
				else
				{
					ResumeManager rsmMgr=new ResumeManager();
					if(idResume==0)
					{
						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
					}
					else
					{
						rsmMgr.updateStatus(idJsk,idResume, new ResumeStatusManager().getResumeStatus(rsmMgr.get(idJsk,idResume)));
					}
					rsmMgr.updateTimestamp(idJsk,idResume);
					
					social.setIdJsk(idJsk);
					social.setIdResume(idResume);
					SocialManager socMgr = new SocialManager();
					if(socMgr.isExist(social.getIdJsk(), social.getIdResume()))
					{
						socMgr.update(social);
					}
					else
					{
						socMgr.insert(social);
					}
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
