package com.topgun.resume;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import com.topgun.shris.masterdata.*;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class ViewResumeManager 
{
	private PropertiesManager propMgr=new PropertiesManager();
	private StringBuilder result=null;
	private boolean isTranslate=false;
	private int viewType=0;
	private boolean additionalFlag = false ; 
	private String resumeFont = "Tahoma";
	
	private String viewInOriginal = "<text style='color: blue'>(See in original resume)</text>";
	
	public boolean checkLogoPath(int idEmp) 
	{
		boolean havePath=true;
		String companyLogoPath="/oracle/bea/user_projects/domains/content/filejobtopgun/logo/sup_lg"+idEmp+".gif";
		if(!(new File(companyLogoPath).exists()))
		{
			havePath=false;
		}		
    	return havePath;
    }
	
	public void replaceAll(StringBuilder builder, String from, String to)
	{
	
		int index = builder.indexOf(from);
	    while (index>0)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}	
	
	public StringBuilder viewResume(Resume resume, String templateFile,String positionName,int allPage,boolean isTranslate)	
	{
		BufferedReader input = null;
		this.isTranslate=isTranslate;
	    try
	    { 
			result=new StringBuilder();
	        input = new BufferedReader(new FileReader(templateFile));	
	        String buffer =null;
			while((buffer = input.readLine())!= null)
			{
				result.append(buffer);
			}
			replaceAll(result,"#SUPERRESUME_LOGO#", "superresume_logo.gif");
			replaceAll(result,"#SAF_LOGO#", "saf_logo.gif");
			replaceAll(result,"#POSITION_NAME#", positionName);
			
			Miscellaneous mis =new MiscellaneousManager().get(resume.getIdJsk(), resume.getIdResume());
			if(mis!=null)
			{
				replaceAll(result,"#resumeFont#", Util.getStr(mis.getResumeFont(), this.resumeFont));
			}
			else 
			{
				replaceAll(result,"#resumeFont#", this.resumeFont);
			}
			if(allPage == 1)
			{
				ReplaceProcess(resume);
			}
			else 
			{
				ReplaceProcessNotAllPage(resume);
			}		
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {	   
	    	if(input != null)
	    	{
	    		try
                {
	                input.close();
                }
                catch (IOException e)
                {
	                e.printStackTrace();
                }
	    	}
	    }
	    return result;
	}
	
	public StringBuilder viewResume(Resume resume, String templateFile,String positionName,int allPage,boolean isTranslate, int viewType)	
	{
		BufferedReader input = null;
		this.isTranslate=isTranslate;
		this.viewType=viewType;
	    try
	    { 
			result=new StringBuilder();
	        input = new BufferedReader(new FileReader(templateFile));	
	        String buffer =null;
			while((buffer = input.readLine())!= null)
			{
				result.append(buffer);
			}
			replaceAll(result,"#SUPERRESUME_LOGO#", "superresume_logo.gif");
			replaceAll(result,"#SAF_LOGO#", "saf_logo.gif");
			
			replaceAll(result,"#POSITION_NAME#", positionName);
			
			Miscellaneous mis =new MiscellaneousManager().get(resume.getIdJsk(), resume.getIdResume());
			if(mis!=null)
			{
				replaceAll(result,"#resumeFont#", Util.getStr(mis.getResumeFont(), this.resumeFont));
			}
			else 
			{
				replaceAll(result,"#resumeFont#", this.resumeFont);
			}
			if(allPage == 1)
			{
				ReplaceProcess(resume);
			}
			else 
			{
				ReplaceProcessNotAllPage(resume);
			}		
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {	   
	    	if(input != null)
	    	{
	    		try
                {
	                input.close();
                }
                catch (IOException e)
                {
	                e.printStackTrace();
                }
	    	}
	    }
	    return result;
	}
	
	public StringBuilder viewResumeChild(Resume resume, String templateFile,String positionName,int allPage,boolean isTranslate, int viewType)	
	{
		BufferedReader input = null;
		this.isTranslate=isTranslate;
		this.viewType=viewType;
	    try
	    { 
			result=new StringBuilder();
	        input = new BufferedReader(new FileReader(templateFile));	
	        String buffer =null;
			while((buffer = input.readLine())!= null)
			{
				result.append(buffer);
			}
			replaceAll(result,"#SUPERRESUME_LOGO#", "superresume_logo.gif");
			replaceAll(result,"#SAF_LOGO#", "saf_logo.gif");
			
			replaceAll(result,"#POSITION_NAME#", positionName);
			
			Miscellaneous mis =new MiscellaneousManager().get(resume.getIdJsk(), resume.getIdResume());
			if(mis!=null)
			{
				replaceAll(result,"#resumeFont#", Util.getStr(mis.getResumeFont(), this.resumeFont));
			}
			else 
			{
				replaceAll(result,"#resumeFont#", this.resumeFont);
			}
			viewInOriginal = "<text style='color: blue'>("+propMgr.getMessage(resume.getLocale(), "SEE_IN_ORIGINAL_RESUME")+")</text>";
			ReplaceProcessHideOther(resume);

	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {	   
	    	if(input != null)
	    	{
	    		try
                {
	                input.close();
                }
                catch (IOException e)
                {
	                e.printStackTrace();
                }
	    	}
	    }
	    return result;
	}
	
	public StringBuilder viewResumePartial(Resume resume, String templateFile,String positionName,int allPage,boolean isTranslate, int viewType)	
	{
		BufferedReader input = null;
		this.isTranslate=isTranslate;
		this.viewType=viewType;
	    try
	    { 
			result=new StringBuilder();
	        input = new BufferedReader(new FileReader(templateFile));	
	        String buffer =null;
			while((buffer = input.readLine())!= null)
			{
				result.append(buffer);
			}
			replaceAll(result,"#SUPERRESUME_LOGO#", "superresume_logo.gif");
			replaceAll(result,"#resumeFont#", this.resumeFont);
			ReplaceProcessPartial(resume);
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {	   
	    	if(input != null)
	    	{
	    		try
                {
	                input.close();
                }
                catch (IOException e)
                {
	                e.printStackTrace();
                }
	    	}
	    }
	    return result;
	}
	
	private void ReplaceProcess(Resume resume)
	{
		ReplaceResume(resume);
		ReplaceEducation(resume);
		ReplaceWorkingExperience(resume);
		ReplaceParttimeEmployment(resume);
		ReplaceTargetJob(resume);
		ReplaceCarreerObjective(resume);
		ReplaceSkill(resume);
		ReplaceTraining(resume);
		ReplaceAward(resume);
		ReplaceCertificate(resume);
		ReplaceActivities(resume);
		ReplaceAdditionalInfo(resume);// plus additional personal data and military
		ReplaceEthnicity(resume);
		ReplaceFamilyBackground(resume);
		ReplaceReferences(resume);
		ReplaceAptitude(resume);
		ReplaceStrength(resume);
		ReplaceMoreLocation(resume);
		ReplaceMoreOversea(resume);
		ReplaceAdditionalInfoTargetJob(resume);
	}
	
	private void ReplaceProcessNotAllPage(Resume resume)
	{
		ReplaceResume(resume);
		ReplaceEducation(resume); // not all working
		ReplaceWorkingExperienceNotAllPart(resume);
		ReplaceParttimeEmploymentNotAllPart(resume);
		ReplaceTargetJob(resume);
		ReplaceCarreerObjective(resume);
		ReplaceSkill(resume);
		ReplaceAdditionalInfoNotAllPart(resume);// plus additional personal data and military (not all part)
		ReplaceEthnicity(resume);
		ReplaceFamilyBackgroundNotAllPart(resume);
		ReplaceAptitude(resume);
		ReplaceStrengthNotAllPart(resume);
		ReplaceMoreLocation(resume);
		ReplaceMoreOversea(resume);
		ReplaceAdditionalInfoTargetJob(resume);
	}
	
	private void ReplaceProcessHideOther(Resume resume)
	{
		ReplaceResumeHideOther(resume);
		ReplaceEducation(resume);
		ReplaceWorkingExperienceHideOther(resume);
		ReplaceParttimeEmploymentHideOther(resume);
		ReplaceTargetJobHideOther(resume);
		ReplaceCarreerObjectiveHideOther(resume);
		ReplaceSkillHideOther(resume);
		ReplaceTrainingHideOther(resume);
		ReplaceAwardHideOther(resume);
		ReplaceCertificateOther(resume);
		ReplaceActivitiesHideOther(resume);
		ReplaceAdditionalInfoHideOther(resume);// plus additional personal data and military
		ReplaceEthnicity(resume);
		ReplaceFamilyBackgroundHideOther(resume);
		ReplaceReferencesHideOther(resume);
		ReplaceAptitudeHideOthers(resume);
		ReplaceStrengthHideOther(resume);
		ReplaceMoreLocationHideOther(resume);
		ReplaceMoreOverseaHideOther(resume);
		ReplaceAdditionalInfoTargetJob(resume);
	}
	
	private void ReplaceProcessPartial(Resume resume)
	{
		ReplaceResume(resume);
		ReplaceEducation(resume);
		ReplaceWorkingExperiencePartial(resume);
		ReplaceParttimeEmployment(resume);
		ReplaceTargetJob(resume);
		ReplaceCarreerObjective(resume);
		ReplaceSkill(resume);
		ReplaceTraining(resume);
		ReplaceAward(resume);
		ReplaceCertificate(resume);
		ReplaceActivities(resume);
		ReplaceEthnicity(resume);
		ReplaceFamilyBackground(resume);
		ReplaceReferences(resume);
		ReplaceAptitude(resume);
		ReplaceStrength(resume);
		ReplaceMoreLocation(resume);
		ReplaceMoreOversea(resume);
		ReplaceAdditionalInfoTargetJob(resume);
	}
	
	private void ReplaceResume(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
        String salutation="";
        String firstName="";
        String lastName="";
        String thainame="";
        String jskUsername="";
        String cityName="";
        String stateName="";
        String countryName="";
        String citizenship="";
        String birthDate="";
        String ownCarStatus="";
        
        String mainPhone="";
        String secondaryPhone="";
        String lineId="";
        String skype="";
        String locale= "en_TH";
        
        if(chkLocation(resume.getLocale()))
        {
        	locale= resume.getLocale();
        }
        
		
        Jobseeker jsk =new JobseekerManager().get(resume.getIdJsk());
        if(jsk!=null)
        {
        	jskUsername=jsk.getUsername();
        }
		if(!resume.getSecondaryEmail().equals(""))
		{
			jskUsername+=", <span class='f11'>"+resume.getSecondaryEmail()+"</span>";
		}
		
		if(resume.getSalutation().equals("MR"))
		{
			salutation=propMgr.getMessage(locale,"GLOBAL_MR");
		}
		else if(resume.getSalutation().equals("MISS"))
		{
			salutation=propMgr.getMessage(locale,"GLOBAL_MISS");
		}
		else if(resume.getSalutation().equals("MRS"))
		{
			salutation=propMgr.getMessage(locale,"GLOBAL_MRS");
		}
		
		if(resume.getBirthDate() != null)
		{
			if(aLanguage.equals("ja"))
			{
				birthDate=Util.getLocaleDateFormatFull(resume.getBirthDate(),"FULL",aLanguage,aCountry);
			}
			else 
			{
				birthDate=Util.getLocaleDateFormatFull(resume.getBirthDate(),"MEDIUM",aLanguage,aCountry);
			}
			birthDate+=" ("+Util.getAge(resume.getBirthDate())+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT")+")";
		}
		if(resume.getIdLanguage() == 38)
		{
			firstName=resume.getFirstNameThai();
			lastName=resume.getLastNameThai();
			
			if(resume.getTemplateIdCountry()==216 && resume.getIdLanguage() == 38)
			{
				if(!resume.getFirstName().equals("")){
					
					if(resume.getSalutation().equals("MR"))
					{
						thainame=propMgr.getMessage("en_TH","GLOBAL_MR");
					}
					else if(resume.getSalutation().equals("MISS"))
					{
						thainame=propMgr.getMessage("en_TH","GLOBAL_MISS");
					}
					else if(resume.getSalutation().equals("MRS"))
					{
						thainame=propMgr.getMessage("en_TH","GLOBAL_MRS");
					}
					thainame+=" "+resume.getFirstName()+" "+resume.getLastName();
					thainame="("+thainame+")"+"<br>";
				}
			}
		}
		else
		{
			firstName=resume.getFirstName();
			lastName=resume.getLastName();
			
			if(resume.getTemplateIdCountry()==216 && resume.getIdLanguage() == 11)
			{
				if(!resume.getFirstNameThai().equals("")){
					
					if(resume.getSalutation().equals("MR"))
					{
						thainame=propMgr.getMessage("th_TH","GLOBAL_MR");
					}
					else if(resume.getSalutation().equals("MISS"))
					{
						thainame=propMgr.getMessage("th_TH","GLOBAL_MISS");
					}
					else if(resume.getSalutation().equals("MRS"))
					{
						thainame=propMgr.getMessage("th_TH","GLOBAL_MRS");
					}
					thainame+=" "+resume.getFirstNameThai()+" "+resume.getLastNameThai();
					thainame="("+thainame+")"+"<br/>";
				}
			}
		}
		com.topgun.shris.masterdata.State aState=MasterDataManager.getState(resume.getIdCountry(), resume.getIdState(), resume.getIdLanguage());
		if(aState!=null)
		{
			stateName=aState.getStateName();
			com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(resume.getIdCountry(),resume.getIdState(), resume.getIdCity(), resume.getIdLanguage());
			if(aCity!=null)
			{
				cityName=aCity.getCityName();
			}
			else if(aCity==null &&! resume.getOtherCity().equals(""))
			{
				cityName = resume.getOtherCity();
			}
		}
		else
		{  
			stateName=resume.getOtherState();
			com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(resume.getIdCountry(),resume.getIdState(), resume.getIdCity(), resume.getIdLanguage());
			if(aCity!=null)
			{
				cityName=aCity.getCityName();
			}
			else if(aCity==null &&! resume.getOtherCity().equals(""))
			{
				cityName = resume.getOtherCity();
			}
		}
		Country country=MasterDataManager.getCountry(resume.getIdCountry(), resume.getIdLanguage());
	    countryName=country!=null?Util.getStr(country.getCountryName()):"";
	    if(!resume.getPrimaryPhone().equals(""))
	    {
	    	//mainPhone=resume.getPrimaryPhone();
	    	if(!resume.getPrimaryPhoneType().equals(""))
	    	{
	    		if(resume.getPrimaryPhoneType().equals("HOME"))
			    {
	    			mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_HOME")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("WORK"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_WORK")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("MOBILE"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_MOBILE")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("PAGER"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_PAGER")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("FAX"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_FAX")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("DSN"))
			    {
			    	mainPhone+="("+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_DSN")+")";
			    }
	    		mainPhone+=resume.getPrimaryPhone();
	    	}
	    }
	    if(!resume.getSecondaryPhone().equals(""))
	    {
	    	secondaryPhone+=", ";
	    	if(!resume.getSecondaryPhone().equals(""))
	    	{
	    		if(resume.getSecondaryPhoneType().equals("HOME"))
			    {
	    			secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_HOME")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("WORK"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_WORK")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("MOBILE"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_MOBILE")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("PAGER"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_PAGER")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("FAX"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_FAX")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("DSN"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_DSN")+" : </b>";
			    }
	    	}
	    	secondaryPhone+=resume.getSecondaryPhone();
	    }
	    
	    if(resume.getCitizenship().equals("THAI"))
	    {
	    	citizenship=propMgr.getMessage(locale,"GLOBAL_THAI");
	    }
	    else if(resume.getCitizenship().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
	    {
	    	citizenship=propMgr.getMessage(locale,"GLOBAL_THAI");
	    }
	    else 
	    {
	    	citizenship=resume.getCitizenship();
	    }
	    
	    if(resume.getOwnCarStatus().equals("TRUE"))
	    {
	    	ownCarStatus=propMgr.getMessage(locale,"GLOBAL_YES");
	    }
	    else if(resume.getOwnCarStatus().equals("FALSE"))
	    {
	    	ownCarStatus=propMgr.getMessage(locale,"GLOBAL_NO");
	    }
	    Social social=new SocialManager().get(resume.getIdJsk(), resume.getIdResume());
	    if(social!=null)
	    {
	    	lineId=!social.getLineId().equals("")?"<b>Line : </b>"+social.getLineId()+"<br/>":"";
	    	skype=!social.getSkype().equals("")?"<b>Skype : </b>"+social.getSkype()+"<br/>":"";
	    }
	    String fcolor="";
	    String fccolor="";
	    if(viewType==1){fcolor="<font color='#82363a'>";fccolor="</font>";}
		replaceAll(result,"#JSK_SALUTATION#", salutation);
		replaceAll(result,"#JSK_FIRSTNAME#", firstName);
		replaceAll(result,"#JSK_LASTNAME#", lastName);
		replaceAll(result,"#THAINAME#", thainame);
		replaceAll(result,"#JSK_USERNAME#", jskUsername);
		
		//replaceAll(result,"#HOME_ADDRESS#", "<b>"+propMgr.getMessage(locale,"GLOBAL_HOME_ADDRESS")+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;"+fcolor+resume.getHomeAddress());
		//replaceAll(result,"#HOME_ADDRESS#",fcolor+resume.getHomeAddress());
		replaceAll(result,"#HOME_ADDRESS#", "<b>"+propMgr.getMessage(locale,"GLOBAL_HOME_ADDRESS")+"</b>:"+fcolor+resume.getHomeAddress());
		replaceAll(result,"#CITY#", cityName);
		replaceAll(result,"#STATE#", stateName );
		replaceAll(result,"#POSTAL#", resume.getPostal());
		replaceAll(result,"#COUNTRY#", countryName+fccolor );
		replaceAll(result,"#MAINPHONE#", mainPhone );
		replaceAll(result,"#SECONDARYPHONE#", secondaryPhone );
		replaceAll(result,"#CITIZENSHIP#", citizenship);
		replaceAll(result,"#HEIGHT#", Float.toString(resume.getHeight()));
		replaceAll(result,"#WEIGHT#", Float.toString(resume.getWeight()));
		replaceAll(result,"#BIRTHDATE#", birthDate);
		replaceAll(result,"#OWN_CAR#", ownCarStatus);
		replaceAll(result,"#LINEID#", lineId);
		replaceAll(result,"#SKYPE#", skype);
				 
		replaceAll(result,"#PREVIEW_RESUME_PERSONAL_DATA#", propMgr.getMessage(locale,"PREVIEW_RESUME_PERSONAL_DATA"));
		replaceAll(result,"#PREVIEW_NATIONALITY#", propMgr.getMessage(locale,"PREVIEW_NATIONALITY"));
		replaceAll(result,"#GLOBAL_HEIGHT#", propMgr.getMessage(locale,"GLOBAL_HEIGHT"));
		replaceAll(result,"#GLOBAL_WEIGHT#", propMgr.getMessage(locale,"GLOBAL_WEIGHT"));
		replaceAll(result,"#PREVIEW_BIRTH_DATE#", propMgr.getMessage(locale,"PREVIEW_BIRTH_DATE"));
		replaceAll(result,"#PREVIEW_OWN_CAR#", propMgr.getMessage(locale,"PREVIEW_OWN_CAR"));
		replaceAll(result,"#GLOBAL_CENTIMETER#", propMgr.getMessage(locale,"GLOBAL_CENTIMETER"));
		replaceAll(result,"#GLOBAL_KILOGRAM#", propMgr.getMessage(locale,"GLOBAL_KILOGRAM"));
		replaceAll(result,"#GLOBAL_HOME_ADDRESS#", propMgr.getMessage(locale,"GLOBAL_HOME_ADDRESS"));
		replaceAll(result,"#PREVIEW_APPLY_FOR_POSITION_OF#", propMgr.getMessage(locale,"PREVIEW_APPLY_FOR_POSITION_OF"));
    }
	
	private void ReplaceResumeHideOther(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
        String salutation="";
        String firstName="";
        String lastName="";
        String thainame="";
        String jskUsername="";
        String cityName="";
        String stateName="";
        String countryName="";
        String citizenship="";
        String birthDate="";
        String ownCarStatus="";
        
        String mainPhone="";
        String secondaryPhone="";
        String lineId="";
        String skype="";
        String locale= "en_TH";
        
        if(chkLocation(resume.getLocale()))
        {
        	locale= resume.getLocale();
        }
        
		
        Jobseeker jsk =new JobseekerManager().get(resume.getIdJsk());
        if(jsk!=null)
        {
        	jskUsername=jsk.getUsername();
        }
		if(!resume.getSecondaryEmail().equals(""))
		{
			jskUsername+=", <span class='f11'>"+resume.getSecondaryEmail()+"</span>";
		}
		
		if(resume.getSalutation().equals("MR"))
		{
			salutation=propMgr.getMessage(locale,"GLOBAL_MR");
		}
		else if(resume.getSalutation().equals("MISS"))
		{
			salutation=propMgr.getMessage(locale,"GLOBAL_MISS");
		}
		else if(resume.getSalutation().equals("MRS"))
		{
			salutation=propMgr.getMessage(locale,"GLOBAL_MRS");
		}
		
		if(resume.getBirthDate() != null)
		{
			if(aLanguage.equals("ja"))
			{
				birthDate=Util.getLocaleDateFormatFull(resume.getBirthDate(),"FULL",aLanguage,aCountry);
			}
			else 
			{
				birthDate=Util.getLocaleDateFormatFull(resume.getBirthDate(),"MEDIUM",aLanguage,aCountry);
			}
			birthDate+=" ("+Util.getAge(resume.getBirthDate())+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT")+")";
		}
		if(resume.getIdLanguage() == 38)
		{
			firstName=resume.getFirstNameThai();
			lastName=resume.getLastNameThai();
			
			if(resume.getTemplateIdCountry()==216 && resume.getIdLanguage() == 38)
			{
				if(!resume.getFirstName().equals("")){
					
					if(resume.getSalutation().equals("MR"))
					{
						thainame=propMgr.getMessage("en_TH","GLOBAL_MR");
					}
					else if(resume.getSalutation().equals("MISS"))
					{
						thainame=propMgr.getMessage("en_TH","GLOBAL_MISS");
					}
					else if(resume.getSalutation().equals("MRS"))
					{
						thainame=propMgr.getMessage("en_TH","GLOBAL_MRS");
					}
					thainame+=" "+resume.getFirstName()+" "+resume.getLastName();
					thainame="("+thainame+")"+"<br>";
				}
			}
		}
		else
		{
			firstName=resume.getFirstName();
			lastName=resume.getLastName();
			
			if(resume.getTemplateIdCountry()==216 && resume.getIdLanguage() == 11)
			{
				if(!resume.getFirstNameThai().equals("")){
					
					if(resume.getSalutation().equals("MR"))
					{
						thainame=propMgr.getMessage("th_TH","GLOBAL_MR");
					}
					else if(resume.getSalutation().equals("MISS"))
					{
						thainame=propMgr.getMessage("th_TH","GLOBAL_MISS");
					}
					else if(resume.getSalutation().equals("MRS"))
					{
						thainame=propMgr.getMessage("th_TH","GLOBAL_MRS");
					}
					thainame+=" "+resume.getFirstNameThai()+" "+resume.getLastNameThai();
					thainame="("+thainame+")"+"<br/>";
				}
			}
		}
		com.topgun.shris.masterdata.State aState=MasterDataManager.getState(resume.getIdCountry(), resume.getIdState(), resume.getIdLanguage());
		if(aState!=null)
		{
			stateName=aState.getStateName();
			com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(resume.getIdCountry(),resume.getIdState(), resume.getIdCity(), resume.getIdLanguage());
			if(aCity!=null)
			{
				cityName=aCity.getCityName();
			}
			else if(aCity==null &&! resume.getOtherCity().equals(""))
			{
				cityName = resume.getOtherCity();
			}
		}
		else
		{  
			stateName=resume.getOtherState();
			com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(resume.getIdCountry(),resume.getIdState(), resume.getIdCity(), resume.getIdLanguage());
			if(aCity!=null)
			{
				cityName=aCity.getCityName();
			}
			else if(aCity==null &&! resume.getOtherCity().equals(""))
			{
				cityName = resume.getOtherCity();
			}
		}
		Country country=MasterDataManager.getCountry(resume.getIdCountry(), resume.getIdLanguage());
	    countryName=country!=null?Util.getStr(country.getCountryName()):"";
	    if(!resume.getPrimaryPhone().equals(""))
	    {
	    	//mainPhone=resume.getPrimaryPhone();
	    	if(!resume.getPrimaryPhoneType().equals(""))
	    	{
	    		if(resume.getPrimaryPhoneType().equals("HOME"))
			    {
	    			mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_HOME")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("WORK"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_WORK")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("MOBILE"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_MOBILE")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("PAGER"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_PAGER")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("FAX"))
			    {
			    	mainPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_FAX")+" : </b>";
			    }
			    else if(resume.getPrimaryPhoneType().equals("DSN"))
			    {
			    	mainPhone+="("+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_DSN")+")";
			    }
	    		mainPhone+=resume.getPrimaryPhone();
	    	}
	    }
	    if(!resume.getSecondaryPhone().equals(""))
	    {
	    	secondaryPhone+=", ";
	    	if(!resume.getSecondaryPhone().equals(""))
	    	{
	    		if(resume.getSecondaryPhoneType().equals("HOME"))
			    {
	    			secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_HOME")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("WORK"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_WORK")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("MOBILE"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_MOBILE")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("PAGER"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_PAGER")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("FAX"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_FAX")+" : </b>";
			    }
			    else if(resume.getSecondaryPhoneType().equals("DSN"))
			    {
			    	secondaryPhone+="<b>"+propMgr.getMessage(locale,"GLOBAL_PHONE_TYPE_DSN")+" : </b>";
			    }
	    	}
	    	secondaryPhone+=resume.getSecondaryPhone();
	    }
	    
	    if(resume.getCitizenship().equals("THAI"))
	    {
	    	citizenship=propMgr.getMessage(locale,"GLOBAL_THAI");
	    }
	    else if(resume.getCitizenship().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
	    {
	    	citizenship=propMgr.getMessage(locale,"GLOBAL_THAI");
	    }
	    else 
	    {
	    	//citizenship=resume.getCitizenship();
	    	citizenship=viewInOriginal;
	    }
	    
	    if(resume.getOwnCarStatus().equals("TRUE"))
	    {
	    	ownCarStatus=propMgr.getMessage(locale,"GLOBAL_YES");
	    }
	    else if(resume.getOwnCarStatus().equals("FALSE"))
	    {
	    	ownCarStatus=propMgr.getMessage(locale,"GLOBAL_NO");
	    }
	    Social social=new SocialManager().get(resume.getIdJsk(), resume.getIdResume());
	    if(social!=null)
	    {
	    	lineId=!social.getLineId().equals("")?"<b>Line : </b>"+social.getLineId()+"<br/>":"";
	    	skype=!social.getSkype().equals("")?"<b>Skype : </b>"+social.getSkype()+"<br/>":"";
	    }
	    String fcolor="";
	    String fccolor="";
	    if(viewType==1){fcolor="<font color='#82363a'>";fccolor="</font>";}
		replaceAll(result,"#JSK_SALUTATION#", salutation);
		replaceAll(result,"#JSK_FIRSTNAME#", firstName);
		replaceAll(result,"#JSK_LASTNAME#", lastName);
		replaceAll(result,"#THAINAME#", thainame);
		replaceAll(result,"#JSK_USERNAME#", jskUsername);
		
		//replaceAll(result,"#HOME_ADDRESS#", "<b>"+propMgr.getMessage(locale,"GLOBAL_HOME_ADDRESS")+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;"+fcolor+resume.getHomeAddress());
		//replaceAll(result,"#HOME_ADDRESS#",fcolor+resume.getHomeAddress());
		replaceAll(result,"#HOME_ADDRESS#", "<b>"+propMgr.getMessage(locale,"GLOBAL_HOME_ADDRESS")+"</b>:"+fcolor+resume.getHomeAddress());
		replaceAll(result,"#CITY#", cityName);
		replaceAll(result,"#STATE#", stateName );
		replaceAll(result,"#POSTAL#", resume.getPostal());
		replaceAll(result,"#COUNTRY#", countryName+fccolor );
		replaceAll(result,"#MAINPHONE#", mainPhone );
		replaceAll(result,"#SECONDARYPHONE#", secondaryPhone );
		replaceAll(result,"#CITIZENSHIP#", citizenship);
		replaceAll(result,"#HEIGHT#", Float.toString(resume.getHeight()));
		replaceAll(result,"#WEIGHT#", Float.toString(resume.getWeight()));
		replaceAll(result,"#BIRTHDATE#", birthDate);
		replaceAll(result,"#OWN_CAR#", ownCarStatus);
		replaceAll(result,"#LINEID#", lineId);
		replaceAll(result,"#SKYPE#", skype);
				 
		replaceAll(result,"#PREVIEW_RESUME_PERSONAL_DATA#", propMgr.getMessage(locale,"PREVIEW_RESUME_PERSONAL_DATA"));
		replaceAll(result,"#PREVIEW_NATIONALITY#", propMgr.getMessage(locale,"PREVIEW_NATIONALITY"));
		replaceAll(result,"#GLOBAL_HEIGHT#", propMgr.getMessage(locale,"GLOBAL_HEIGHT"));
		replaceAll(result,"#GLOBAL_WEIGHT#", propMgr.getMessage(locale,"GLOBAL_WEIGHT"));
		replaceAll(result,"#PREVIEW_BIRTH_DATE#", propMgr.getMessage(locale,"PREVIEW_BIRTH_DATE"));
		replaceAll(result,"#PREVIEW_OWN_CAR#", propMgr.getMessage(locale,"PREVIEW_OWN_CAR"));
		replaceAll(result,"#GLOBAL_CENTIMETER#", propMgr.getMessage(locale,"GLOBAL_CENTIMETER"));
		replaceAll(result,"#GLOBAL_KILOGRAM#", propMgr.getMessage(locale,"GLOBAL_KILOGRAM"));
		replaceAll(result,"#GLOBAL_HOME_ADDRESS#", propMgr.getMessage(locale,"GLOBAL_HOME_ADDRESS"));
		
				 
		replaceAll(result,"#PREVIEW_APPLY_FOR_POSITION_OF#", propMgr.getMessage(locale,"PREVIEW_APPLY_FOR_POSITION_OF"));
    }
	
	private boolean chkLocation(String locale)
	{
		boolean result= false;
		if(locale.equals("en_TH") || locale.equals("en_ID") || locale.equals("de_TH") || locale.equals("es_TH") || locale.equals("ja_TH") || locale.equals("zh_TH") || locale.equals("zt_TH") || locale.equals("th_TH"))
		{
			result=true;
		}
		else
		{
			result=false;
		}
		return result;
	}
	
	private void ReplaceEducation(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Education> eduList=new EducationManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String eduTable="";
		String eduTableHead="";
		
		//chk location of resume
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
        {
        	locale= resume.getLocale();
        }
		if(eduList != null)
		{
			eduTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\">";
			for(int c=0;c<eduList.size();c++)
			{
				eduTable += "<tr valign=\"top\"><td style='max-width:305px;'><b>";
				Degree aDegree=MasterDataManager.getDegree(eduList.get(c).getIdDegree(), resume.getIdLanguage());
				if(aDegree!=null)
				{
					eduTable+=Util.getStr(aDegree.getDegreeName());
				}
				if(eduList.get(c).getIdFacMajor()!=0)
				{
					eduTable += " "+propMgr.getMessage(locale, "PREVIEW_EDUCATION_FACULTY");
					com.topgun.shris.masterdata.Faculty aFaculty = null;
					aFaculty=MasterDataManager.getFacultyExclusive(eduList.get(c).getIdSchool(), eduList.get(c).getIdDegree(), eduList.get(c).getIdFacMajor(), resume.getIdLanguage());
					if(aFaculty==null || eduList.get(c).getIdSchool()==-1){
						aFaculty=MasterDataManager.getFaculty(eduList.get(c).getIdFacMajor(), resume.getIdLanguage());
					}
					if(eduList.get(c).getIdFacMajor()!=-1)
					{
						if(aFaculty!=null)
						{
							eduTable+=aFaculty.getFacultyName();
						}
					}
					else 
					{
						if(isTranslate!=true)
						{
							eduTable += eduList.get(c).getOtherFaculty();
						}
					}
				}
				if (eduList.get(c).getIdMajor() != 0)
				{
					eduTable += ", "+ propMgr.getMessage(locale, "EDUCATION_MAJOR")+" ";
					com.topgun.shris.masterdata.Major aMajor = null;
					aMajor=MasterDataManager.getMajorExclusive(eduList.get(c).getIdSchool(), eduList.get(c).getIdDegree(), eduList.get(c).getIdFacMajor(), eduList.get(c).getIdMajor(), resume.getIdLanguage());
					if(aMajor==null)
					{
						aMajor=MasterDataManager.getMajor(eduList.get(c).getIdFacMajor(), eduList.get(c).getIdMajor(), resume.getIdLanguage());
					}
					if(aMajor!=null)
					{
						if(aMajor!=null)
						{
							eduTable+=aMajor.getMajorName();
						}
					}
					else 
					{
						if(isTranslate!=true)
						{
							eduTable += eduList.get(c).getOtherMajor();
						}
					}
				}
				eduTable += "</b>";
				eduTable += "<br>";
				if(resume.getIdLanguage() == 23)
				{
					
					if (eduList.get(c).getFinishDate() != null)
					{
						
						if(Util.getLocaleDate(eduList.get(c).getFinishDate(), "d",aLanguage,aCountry).equals("1") || Util.getLocaleDate(eduList.get(c).getFinishDate(), "d",aLanguage,aCountry).equals("2"))
						{
							eduTable += Util.getLocaleDate(eduList.get(c).getFinishDate(), "yyyy年",aLanguage,aCountry) + ", ";
						}
					}
				}
				else  // 11 = English
				{
					
					if (eduList.get(c).getFinishDate() != null)
					{
						if(Util.getLocaleDate(eduList.get(c).getFinishDate(), "d",aLanguage,aCountry).equals("1") || Util.getLocaleDate(eduList.get(c).getFinishDate(), "d",aLanguage,aCountry).equals("2"))
						{
							eduTable += Util.getLocaleDate(eduList.get(c).getFinishDate(), "yyyy",aLanguage,aCountry) + ", " ;
						}
					}
				}
				School aSchool=null;
				if(eduList.get(c).getIdState()>0)
				{
					aSchool=MasterDataManager.getSchool(eduList.get(c).getIdCountry(), eduList.get(c).getIdState(), eduList.get(c).getIdSchool(), resume.getIdLanguage());
				}
				else
				{
					aSchool=MasterDataManager.getSchool(eduList.get(c).getIdCountry(),0, eduList.get(c).getIdSchool(), resume.getIdLanguage());
				}
				if(aSchool!=null)
				{
					eduTable += aSchool.getSchoolName();
				}
				else
				{
					eduTable += eduList.get(c).getOtherSchool();
				}
				
				eduTable += ", "+ MasterDataManager.getCountry(eduList.get(c).getIdCountry(), resume.getIdLanguage()).getCountryName();
				if (eduList.get(c).getGpa() != 0.0)
				{
					eduTable += ", "+ propMgr.getMessage(locale, "GLOBAL_GPA")+ " "+eduList.get(c).getGpa() + eduList.get(c).getUnit();
				}
				if (!eduList.get(c).getAward().equals(""))
				{
					if(isTranslate!=true)
					{
						String fcolor="";
					    String fccolor="";
					    if(viewType==1){fcolor="<font color='#82363a'>";fccolor="</font>";}
						eduTable += "<br/>"+fcolor+"<span style='word-wrap:break-word;'>"+ eduList.get(c).getAward().replaceAll("\n","<br/>")+"</span>"+fccolor;
					}
				}
				eduTable += "</td></tr>";    
			}
			eduTable=eduTableHead+eduTable+"</table>";
		}
	     replaceAll(result,"#PREVIEW_EDUCATION#",propMgr.getMessage(locale, "PREVIEW_EDUCATION"));
	     replaceAll(result,"#EDUCATION_DETAIL#",eduTable );
	}
	
	private void ReplaceWorkingExperiencePartial(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		int totalCom = 0;
		int totalM =0;
		int totalY = 0;
		String workingTable="";
		String workingHeadTable="";
		String startComment="";
		String endComment="";
		String startWork ="";
		String endWork="";
		String expIndustryTable = "";
		String expField="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
		List <WorkExperience>workList=new WorkExperienceManager().getAllFulltimes(resume.getIdJsk(),resume.getIdResume());
	    if (workList.size()>0) 
	    {
	    	if(resume.getIdResume() == 0 )
	    	{
	    		//totalCom = resume.getExpCompany();
	    		totalCom = workList.size();
	    		totalY = resume.getExpYear();
	    		totalM = resume.getExpMonth();
	    		List<ExperienceSummary> sumField=new ExperienceSummaryManager().getAll(resume.getIdJsk(),resume.getIdResume());
	    		if(sumField != null)
	    		{
	    			for(int c=0;c<sumField.size();c++)
	    			{
	    				expField+="<br>: ";
	    				if(sumField.get(c).getSumYear() > 0)
	    				{
	    					expField+=sumField.get(c).getSumYear();
	    					 if(sumField.get(c).getSumYear()  > 1)
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
	    					 }
	    					 else 
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
	    					 }
	    				}
	    				if(sumField.get(c).getSumMonth() > 0)
	    				{
	    					expField+=" "+sumField.get(c).getSumMonth();
	    					 if(sumField.get(c).getSumMonth()  > 1)
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
	    					 }
	    					 else 
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
	    					 }
	    				}
	    				expField+=", "+propMgr.getMessage(locale,"GLOBAL_IN_THE_FIELD_OF")+": ";
	    				com.topgun.shris.masterdata.JobField aJobfield=MasterDataManager.getJobField(sumField.get(c).getJobField(), resume.getIdLanguage());
	    			   if(aJobfield!=null)
	    			   {
	    				   expField+=aJobfield.getFieldName();
	    			   }	
	    			} // close for
	    			expField+="<br>"; 
	    		}//close if
	    	}
	    	else 
	    	{
	    		totalCom = workList.size();
	    		for (int co=0;co<workList.size();co++) 
	       		{
	           		totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
	      	    }
	       		 totalY = totalM / 12;
	      		 totalM = totalM % 12;
	    	}
      		workingHeadTable+="<B>"+propMgr.getMessage(locale,"WORKEXP_TOTAL_WORKING")+": "; 
			 if(totalY != 0)
			 {
				 workingHeadTable+=totalY;
				 if(totalY  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
				 }
			 }	
			 
			 if(totalM != 0)
			 {
				 workingHeadTable+=" "+totalM;
				 if(totalM  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
				 }
			 }	
			 workingHeadTable+=", "+totalCom;
			 if(totalCom > 1)
			 {
				 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_COMPANIES_UNIT");
			 }
			 else 
			 {
				 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_COMPANY_UNIT");
			 }
			 workingHeadTable+="</B>";
      		workingHeadTable+=expField+"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\">";
      		for(int c=0;c<workList.size();c++)
      		{
      			startWork ="";
    			endWork="";
    			expIndustryTable="";
    			List<ExperienceIndustry>expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
    			workingTable+="<tr><td style='padding-top:5px;'>";
				if(resume.getIdLanguage() == 38) // thai language
				{
				   if(workList.get(c).getWorkStart() != null)
				   { 
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(),"MMM yyyy",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+(Util.getYear(workList.get(c).getWorkEnd())+543);
						
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(),"MMM yyyy",aLanguage,aCountry);
					   }
					   
				   }
				}
				else if(resume.getIdLanguage() == 23) // japanese language
				{
						   
				   if(workList.get(c).getWorkStart() != null)
				   {
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "yyyy年MMMM",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {   
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd())+"年";
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "yyyy年MMMM",aLanguage,aCountry);
					   }
					  
				   }
				}
				else  // english  and other language
				{
				   if(workList.get(c).getWorkStart() != null)
				   {
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {   
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd());
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
					   }
					  
				   }
				}
				workingTable+="<u><b>"+startWork+" - "+endWork+"( ";
				if(workList.get(c).getExpY()!=0)
				{
				   workingTable+=workList.get(c).getExpY();
				   if(workList.get(c).getExpY() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
				   }
				}
				if(workList.get(c).getExpM() != 0)
				{
				   workingTable+=" "+workList.get(c).getExpM();
				   if(workList.get(c).getExpM() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
				   }
				}
				workingTable+=" )</b></u><br/>";
				String comname="";
				if(!workList.get(c).getWorkingStatus().equals("TRUE"))
				{
					comname=workList.get(c).getCompanyName();
				}
				else 
				{
					comname=propMgr.getMessage(locale,"GLOBAL_COMPANY_UNIT")+"  xxxxx";
				}
				workingTable+="<u><b>"+comname;
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
				if(aState!=null)
				{
					workingTable+=", "+aState.getStateName();
				}
				else if(aState==null&& !workList.get(c).getOtherState().equals("") && isTranslate!=true )
				{
					
					workingTable+=", "+workList.get(c).getOtherState();
				}
				com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
				if(countrySelect!=null)
				{
					workingTable+=", "+countrySelect.getCountryName();
				}
				workingTable+="</b></u>";
				if(expIndustry!=null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_INDUSTRY")+":</b> ";
					for(int k=0;k<expIndustry.size();k++)
					{
						if(k>0)
						{
							expIndustryTable+=", ";
						}
						com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
						
						if(aInsdus!=null)
						{
							expIndustryTable+=aInsdus.getIndustryName();
						}
						else if(aInsdus==null && !expIndustry.get(k).getOtherIndustry().equals("") && isTranslate!=true)
						{
							expIndustryTable+=expIndustry.get(k).getOtherIndustry();
						}
					}
				}
				workingTable+=expIndustryTable;
				
				if(!workList.get(c).getComBusiness().equals("") && isTranslate!=true)
				{
					if(viewType==1){workingTable+="<font color='#82363a'>";}
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_COMPANY_BUSINESS")+": </b>";
					if(workList.get(c).getWorkingStatus().equals("TRUE"))//present
					{
						workingTable += "xxxxxxx";
					}
					else
					{
						workingTable+= workList.get(c).getComBusiness().replace("\n", "<br/>");
					}
					if(viewType==1){workingTable+="</font>";}
				}
				if(workList.get(c).getComSize()!=0)
				{
				   if(workList.get(c).getComSize()!=-1)
				   {
					   workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+": </b>";
					   if(workList.get(c).getComSize() == 1)
					   {
						   workingTable+="1-15<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 2)
					   {
						   workingTable+="15-30<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 3)
					   {
						   workingTable+="30-50<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 4)
					   {
						   workingTable+="50-100<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 5)
					   {
						   workingTable+="100-150<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 6)
					   {
						   workingTable+="150-300<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 7)
					   {
						   workingTable+="300-500<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 8)
					   {
						   workingTable+="500-1000<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 9)
					   {
						   workingTable+="1000 <span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_UP_EMPLOYEES");
					   }else 
					   {
						   workingTable+=propMgr.getMessage(locale,"GLOBAL_NOT_SPECIFY");
					   }
				   }			   
				}				   
				if(!workList.get(c).getPositionLast().equals("") && isTranslate!=true)
				{
				   workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_LASTEST_POS")+":</b> "+workList.get(c).getPositionLast();
				}
				com.topgun.shris.masterdata.JobField jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
				com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
				
				if(jobfield != null && aSubfield != null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
				}
				else
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
				}
				/*
				 * 
				 * Jobfield
				 * 
				 */
				if(jobfield!=null)
				{
					workingTable+=jobfield.getFieldName();
				}
				else if(!workList.get(c).getWorkJobFieldOth().equals(""))
				{
					workingTable+=workList.get(c).getWorkJobFieldOth();
				}
				
				/*
				 * 
				 * Subfield
				 * 
				 */
				
				if(aSubfield!=null)
				{
					workingTable+=" : "+aSubfield.getSubfieldName();
				}
				else if(!workList.get(c).getWorkSubFieldOth().equals("") && isTranslate!=true)
				{
					workingTable+=" : "+workList.get(c).getWorkSubFieldOth();
				}
				
				if(workList.get(c).getSubordinate() > 0) 
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_SUBORDINATE")+": </b>"+workList.get(c).getSubordinate();
				}
				com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
				if(aJobbtype!=null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_TYPE")+":</b>"+aJobbtype.getTypeName();		
				}
				DecimalFormat aDecimal = new DecimalFormat( "###,###" );
				
				SalaryPer aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
				
				if(workList.get(c).getSalaryLast()>0){
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
					if(workList.get(c).getIdCurrency() > 0)
					{
						if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
						}else{
							workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
						}
					}
				
					aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
					if(aSalPer!=null)
					{
						String salPer=aSalPer.getName();
						workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
					}
				}
				
				/*
				if(workList.get(c).getSalaryLast()>0)
				{
					workingTable+="<br><b>"+propMgr.getMessage(locale,"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
				}
				if(workList.get(c).getIdCurrency() > 0)
				{
					if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
					{
						workingTable+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
					}else{
						workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
					}
				}
				if(aSalPer!=null)
				{
					String salPer=aSalPer.getName();
					workingTable+=" "+propMgr.getMessage(locale,salPer);
				}
				*/
				jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
				if(!workList.get(c).getPositionStart().equals("") && isTranslate!=true)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_START_POS")+": </b>"+workList.get(c).getPositionStart();
					jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
					aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
					
					if(jobfield != null && aSubfield != null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
					}
					else
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
					}
					/*
					 * 
					 * Jobfield
					 * 
					 */
					if(jobfield!=null)
					{
						workingTable+=jobfield.getFieldName();
					}
					else if(!workList.get(c).getWorkJobFieldOthStart().equals(""))
					{
						workingTable+=workList.get(c).getWorkJobFieldOthStart();
					}
					
					/*
					 * 
					 * Subfield
					 * 
					 */
					
					if(aSubfield!=null)
					{
						workingTable+=" : "+aSubfield.getSubfieldName();
					}
					else if(!workList.get(c).getWorkSubFieldOthStart().equals("")  && isTranslate!=true)
					{
						workingTable+=" : "+workList.get(c).getWorkSubFieldOthStart();
					}
					
					if(workList.get(c).getSalaryStart()>0)
					{
						workingTable+="<br><b>"+propMgr.getMessage(locale,"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
						if(workList.get(c).getIdCurrencyStart() > 0)
						{
							if(workList.get(c).getIdCurrencyStart()==140 && resume.getIdLanguage()==38)
							{
								workingTable+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
							}else{
								workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode();
							}
							aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage());
							if(aSalPer!=null)
							{
								String salPer=aSalPer.getName();
								workingTable+=" "+propMgr.getMessage(locale,salPer);
							}
						}
					}
					
				}
				if(viewType==1){workingTable+="<font color='#82363a'>";}
				if(!workList.get(c).getJobDesc().equals("") && isTranslate!=true)
				{
					 workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_RESPONSIBILITIES")+": </b>"+workList.get(c).getJobDesc().replace("\n", "<br/>");
				}
				if(!workList.get(c).getAchieve().equals("") && isTranslate!=true)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_ACHIEVEMENT")+": </b>"+workList.get(c).getAchieve().replace("\n", "<br/>");
				}
				if(!workList.get(c).getReasonQuit().equals("") && isTranslate!=true)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_REASON_FOR_LEAVING")+": </b>"+workList.get(c).getReasonQuit().replace("\n", "<br/>");
				}
				if(viewType==1){workingTable+="</font>";}
				workingTable+="<br/></td></tr>";
      		}
      		workingTable=workingHeadTable+workingTable+"</table>";
	  } 
	 else  // no work exp
	 {
		 startComment="<!--";
			endComment="-->";
	 }
		replaceAll(result,"#PREVIEW_WORKING_EXPERIENCE#", propMgr.getMessage(locale,"PREVIEW_WORKING_EXPERIENCE"));
		replaceAll(result,"#WORKING_EXPERIENCE_DETAIL#", workingTable);
		replaceAll(result,"#BEGIN_WORKING_EXPERIENCE#", startComment);
		replaceAll(result,"#END_WORKING_EXPERIENCE#", endComment);
    }
	
	private void ReplaceWorkingExperience(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		int totalCom = 0;
		int totalM =0;
		int totalY = 0;
		String workingTable="";
		String workingHeadTable="";
		String startComment="";
		String endComment="";
		String startWork ="";
		String endWork="";
		String expIndustryTable = "";
		String expField="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
		List <WorkExperience>workList=new WorkExperienceManager().getAllFulltimes(resume.getIdJsk(),resume.getIdResume());
	    if (workList.size()>0) 
	    {
	    	if(resume.getIdResume() == 0 )
	    	{
	    		//totalCom = resume.getExpCompany();
	    		totalCom = workList.size();
	    		totalY = resume.getExpYear();
	    		totalM = resume.getExpMonth();
	    		List<ExperienceSummary> sumField=new ExperienceSummaryManager().getAll(resume.getIdJsk(),resume.getIdResume());
	    		if(sumField != null)
	    		{
	    			for(int c=0;c<sumField.size();c++)
	    			{
	    				expField+="<br>: ";
	    				if(sumField.get(c).getSumYear() > 0)
	    				{
	    					expField+=sumField.get(c).getSumYear();
	    					 if(sumField.get(c).getSumYear()  > 1)
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
	    					 }
	    					 else 
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
	    					 }
	    				}
	    				if(sumField.get(c).getSumMonth() > 0)
	    				{
	    					expField+=" "+sumField.get(c).getSumMonth();
	    					 if(sumField.get(c).getSumMonth()  > 1)
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
	    					 }
	    					 else 
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
	    					 }
	    				}
	    				expField+=", "+propMgr.getMessage(locale,"GLOBAL_IN_THE_FIELD_OF")+": ";
	    				com.topgun.shris.masterdata.JobField aJobfield=MasterDataManager.getJobField(sumField.get(c).getJobField(), resume.getIdLanguage());
	    			   if(aJobfield!=null)
	    			   {
	    				   expField+=aJobfield.getFieldName();
	    			   }	
	    			} // close for
	    			expField+="<br>"; 
	    		}//close if
	    	}
	    	else 
	    	{
	    		totalCom = workList.size();
	    		for (int co=0;co<workList.size();co++) 
	       		{
	           		totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
	      	    }
	       		 totalY = totalM / 12;
	      		 totalM = totalM % 12;
	    	}
      		workingHeadTable+="<B>"+propMgr.getMessage(locale,"WORKEXP_TOTAL_WORKING")+": "; 
			 if(totalY != 0)
			 {
				 workingHeadTable+=totalY;
				 if(totalY  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
				 }
			 }	
			 
			 if(totalM != 0)
			 {
				 workingHeadTable+=" "+totalM;
				 if(totalM  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
				 }
			 }	
			 workingHeadTable+=", "+totalCom;
			 if(totalCom > 1)
			 {
				 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_COMPANIES_UNIT");
			 }
			 else 
			 {
				 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_COMPANY_UNIT");
			 }
			 workingHeadTable+="</B>";
      		workingHeadTable+=expField+"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\">";
      		for(int c=0;c<workList.size();c++)
      		{
      			startWork ="";
    			endWork="";
    			expIndustryTable="";
    			List<ExperienceIndustry>expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
    			workingTable+="<tr><td style='padding-top:5px;'>";
				if(resume.getIdLanguage() == 38) // thai language
				{
				   if(workList.get(c).getWorkStart() != null)
				   { 
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(),"MMM yyyy",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+(Util.getYear(workList.get(c).getWorkEnd())+543);
						
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(),"MMM yyyy",aLanguage,aCountry);
					   }
					   
				   }
				}
				else if(resume.getIdLanguage() == 23) // japanese language
				{
						   
				   if(workList.get(c).getWorkStart() != null)
				   {
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "yyyy年MMMM",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {   
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd())+"年";
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "yyyy年MMMM",aLanguage,aCountry);
					   }
					  
				   }
				}
				else  // english  and other language
				{
				   if(workList.get(c).getWorkStart() != null)
				   {
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {   
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd());
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
					   }
					  
				   }
				}
				workingTable+="<u><b>"+startWork+" - "+endWork+"( ";
				if(workList.get(c).getExpY()!=0)
				{
				   workingTable+=workList.get(c).getExpY();
				   if(workList.get(c).getExpY() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
				   }
				}
				if(workList.get(c).getExpM() != 0)
				{
				   workingTable+=" "+workList.get(c).getExpM();
				   if(workList.get(c).getExpM() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
				   }
				}
				workingTable+=" )</b></u><br/>";
				workingTable+="<u><b>"+workList.get(c).getCompanyName();
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
				if(aState!=null)
				{
					workingTable+=", "+aState.getStateName();
				}
				else if(aState==null&& !workList.get(c).getOtherState().equals("") && isTranslate!=true )
				{
					
					workingTable+=", "+workList.get(c).getOtherState();
				}
				com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
				if(countrySelect!=null)
				{
					workingTable+=", "+countrySelect.getCountryName();
				}
				workingTable+="</b></u>";
				if(expIndustry!=null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_INDUSTRY")+":</b> ";
					for(int k=0;k<expIndustry.size();k++)
					{
						if(k>0)
						{
							expIndustryTable+=", ";
						}
						com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
						if(aInsdus!=null)
						{
							expIndustryTable+=aInsdus.getIndustryName();
						}
						else if(aInsdus==null && !expIndustry.get(k).getOtherIndustry().equals("") && isTranslate!=true)
						{
							expIndustryTable+=expIndustry.get(k).getOtherIndustry();
						}
					}
				}
				workingTable+=expIndustryTable;
				if(!workList.get(c).getComBusiness().equals("") && isTranslate!=true)
				{
					if(viewType==1){workingTable+="<font color='#82363a'>";}
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_COMPANY_BUSINESS")+": </b>"+workList.get(c).getComBusiness().replace("\n", "<br/>");
					if(viewType==1){workingTable+="</font>";}
				}
				if(workList.get(c).getComSize()!=0)
				{
				   if(workList.get(c).getComSize()!=-1)
				   {
					   workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+": </b>";
					   if(workList.get(c).getComSize() == 1)
					   {
						   workingTable+="1-15<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 2)
					   {
						   workingTable+="15-30<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 3)
					   {
						   workingTable+="30-50<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 4)
					   {
						   workingTable+="50-100<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 5)
					   {
						   workingTable+="100-150<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 6)
					   {
						   workingTable+="150-300<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 7)
					   {
						   workingTable+="300-500<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 8)
					   {
						   workingTable+="500-1000<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 9)
					   {
						   workingTable+="1000 <span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_UP_EMPLOYEES");
					   }else 
					   {
						   workingTable+=propMgr.getMessage(locale,"GLOBAL_NOT_SPECIFY");
					   }
				   }			   
				}				   
				if(!workList.get(c).getPositionLast().equals("") && isTranslate!=true)
				{
				   workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_LASTEST_POS")+":</b> "+workList.get(c).getPositionLast();
				}
				com.topgun.shris.masterdata.JobField jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
				com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
				
				if(jobfield != null && aSubfield != null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
				}
				else
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
				}
				/*
				 * 
				 * Jobfield
				 * 
				 */
				if(jobfield!=null)
				{
					workingTable+=jobfield.getFieldName();
				}
				else if(!workList.get(c).getWorkJobFieldOth().equals(""))
				{
					workingTable+=workList.get(c).getWorkJobFieldOth();
				}
				
				/*
				 * 
				 * Subfield
				 * 
				 */
				
				if(aSubfield!=null)
				{
					workingTable+=" : "+aSubfield.getSubfieldName();
				}
				else if(!workList.get(c).getWorkSubFieldOth().equals("") && isTranslate!=true)
				{
					workingTable+=" : "+workList.get(c).getWorkSubFieldOth();
				}
				
				if(workList.get(c).getSubordinate() > 0) 
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_SUBORDINATE")+": </b>"+workList.get(c).getSubordinate();
				}
				com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
				if(aJobbtype!=null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_TYPE")+":</b>"+aJobbtype.getTypeName();		
				}
				DecimalFormat aDecimal = new DecimalFormat( "###,###" );
				
				SalaryPer aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
				
				if(workList.get(c).getSalaryLast()>0){
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
					if(workList.get(c).getIdCurrency() > 0)
					{
						if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
						}else{
							workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
						}
					}
				
					aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
					if(aSalPer!=null)
					{
						String salPer=aSalPer.getName();
						workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
					}
				}
				
				/*
				if(workList.get(c).getSalaryLast()>0)
				{
					workingTable+="<br><b>"+propMgr.getMessage(locale,"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
				}
				if(workList.get(c).getIdCurrency() > 0)
				{
					if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
					{
						workingTable+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
					}else{
						workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
					}
				}
				if(aSalPer!=null)
				{
					String salPer=aSalPer.getName();
					workingTable+=" "+propMgr.getMessage(locale,salPer);
				}
				*/
				jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
				if(!workList.get(c).getPositionStart().equals("") && isTranslate!=true)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_START_POS")+": </b>"+workList.get(c).getPositionStart();
					jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
					aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
					
					if(jobfield != null && aSubfield != null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
					}
					else
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
					}
					/*
					 * 
					 * Jobfield
					 * 
					 */
					if(jobfield!=null)
					{
						workingTable+=jobfield.getFieldName();
					}
					else if(!workList.get(c).getWorkJobFieldOthStart().equals(""))
					{
						workingTable+=workList.get(c).getWorkJobFieldOthStart();
					}
					
					/*
					 * 
					 * Subfield
					 * 
					 */
					
					if(aSubfield!=null)
					{
						workingTable+=" : "+aSubfield.getSubfieldName();
					}
					else if(!workList.get(c).getWorkSubFieldOthStart().equals("")  && isTranslate!=true)
					{
						workingTable+=" : "+workList.get(c).getWorkSubFieldOthStart();
					}
					
					if(workList.get(c).getSalaryStart()>0)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
						if(workList.get(c).getIdCurrencyStart() > 0)
						{
							if(workList.get(c).getIdCurrencyStart()==140 && resume.getIdLanguage()==38)
							{
								workingTable+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
							}else{
								workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode();
							}
							aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage());
							if(aSalPer!=null)
							{
								String salPer=aSalPer.getName();
								workingTable+=" "+propMgr.getMessage(locale,salPer);
							}
						}
					}
					
				}
				if(viewType==1){workingTable+="<font color='#82363a'>";}
				if(!workList.get(c).getJobDesc().equals("") && isTranslate!=true)
				{
					 workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_RESPONSIBILITIES")+": </b>"+workList.get(c).getJobDesc().replace("\n", "<br/>");
				}
				if(!workList.get(c).getAchieve().equals("") && isTranslate!=true)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_ACHIEVEMENT")+": </b>"+workList.get(c).getAchieve().replace("\n", "<br/>");
				}
				if(!workList.get(c).getReasonQuit().equals("") && isTranslate!=true)
				{
					workingTable+="<br><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_REASON_FOR_LEAVING")+": </b>"+workList.get(c).getReasonQuit().replace("\n", "<br/>");
				}
				if(viewType==1){workingTable+="</font>";}
				workingTable+="<br/></td></tr>";
      		}
      		workingTable=workingHeadTable+workingTable+"</table>";
	  } 
	 else  // no work exp
	 {
		 startComment="<!--";
			endComment="-->";
	 }
		replaceAll(result,"#PREVIEW_WORKING_EXPERIENCE#", propMgr.getMessage(locale,"PREVIEW_WORKING_EXPERIENCE"));
		replaceAll(result,"#WORKING_EXPERIENCE_DETAIL#", workingTable);
		replaceAll(result,"#BEGIN_WORKING_EXPERIENCE#", startComment);
		replaceAll(result,"#END_WORKING_EXPERIENCE#", endComment);
    }

	private void ReplaceWorkingExperienceHideOther(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		int totalCom = 0;
		int totalM =0;
		int totalY = 0;
		String workingTable="";
		String workingHeadTable="";
		String startComment="";
		String endComment="";
		String startWork ="";
		String endWork="";
		String expIndustryTable = "";
		String expField="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
		List <WorkExperience>workList=new WorkExperienceManager().getAllFulltimes(resume.getIdJsk(),resume.getIdResume());
	    if (workList.size()>0) 
	    {
	    	if(resume.getIdResume() == 0 )
	    	{
	    		//totalCom = resume.getExpCompany();
	    		totalCom = workList.size();
	    		totalY = resume.getExpYear();
	    		totalM = resume.getExpMonth();
	    		List<ExperienceSummary> sumField=new ExperienceSummaryManager().getAll(resume.getIdJsk(),resume.getIdResume());
	    		if(sumField != null)
	    		{
	    			for(int c=0;c<sumField.size();c++)
	    			{
	    				expField+="<br>: ";
	    				if(sumField.get(c).getSumYear() > 0)
	    				{
	    					expField+=sumField.get(c).getSumYear();
	    					 if(sumField.get(c).getSumYear()  > 1)
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
	    					 }
	    					 else 
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
	    					 }
	    				}
	    				if(sumField.get(c).getSumMonth() > 0)
	    				{
	    					expField+=" "+sumField.get(c).getSumMonth();
	    					 if(sumField.get(c).getSumMonth()  > 1)
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
	    					 }
	    					 else 
	    					 {
	    						 expField+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
	    					 }
	    				}
	    				expField+=", "+propMgr.getMessage(locale,"GLOBAL_IN_THE_FIELD_OF")+": ";
	    				com.topgun.shris.masterdata.JobField aJobfield=MasterDataManager.getJobField(sumField.get(c).getJobField(), resume.getIdLanguage());
	    			   if(aJobfield!=null)
	    			   {
	    				   expField+=aJobfield.getFieldName();
	    			   }	
	    			} // close for
	    			expField+="<br>"; 
	    		}//close if
	    	}
	    	else 
	    	{
	    		totalCom = workList.size();
	    		for (int co=0;co<workList.size();co++) 
	       		{
	           		totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
	      	    }
	       		 totalY = totalM / 12;
	      		 totalM = totalM % 12;
	    	}
      		workingHeadTable+="<B>"+propMgr.getMessage(locale,"WORKEXP_TOTAL_WORKING")+": "; 
			 if(totalY != 0)
			 {
				 workingHeadTable+=totalY;
				 if(totalY  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
				 }
			 }	
			 
			 if(totalM != 0)
			 {
				 workingHeadTable+=" "+totalM;
				 if(totalM  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
				 }
			 }	
			 workingHeadTable+=", "+totalCom;
			 if(totalCom > 1)
			 {
				 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_COMPANIES_UNIT");
			 }
			 else 
			 {
				 workingHeadTable+=" "+propMgr.getMessage(locale,"GLOBAL_COMPANY_UNIT");
			 }
			 workingHeadTable+="</B>";
      		workingHeadTable+=expField+"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\">";
      		for(int c=0;c<workList.size();c++)
      		{
      			startWork ="";
    			endWork="";
    			expIndustryTable="";
    			List<ExperienceIndustry>expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
    			workingTable+="<tr><td style='padding-top:5px;'>";
				if(resume.getIdLanguage() == 38) // thai language
				{
				   if(workList.get(c).getWorkStart() != null)
				   { 
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(),"MMM yyyy",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+(Util.getYear(workList.get(c).getWorkEnd())+543);
						
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(),"MMM yyyy",aLanguage,aCountry);
					   }
					   
				   }
				}
				else if(resume.getIdLanguage() == 23) // japanese language
				{
						   
				   if(workList.get(c).getWorkStart() != null)
				   {
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "yyyy年MMMM",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {   
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd())+"年";
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "yyyy年MMMM",aLanguage,aCountry);
					   }
					  
				   }
				}
				else  // english  and other language
				{
				   if(workList.get(c).getWorkStart() != null)
				   {
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {   
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(locale,"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd());
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
					   }
					  
				   }
				}
				workingTable+="<u><b>"+startWork+" - "+endWork+"( ";
				if(workList.get(c).getExpY()!=0)
				{
				   workingTable+=workList.get(c).getExpY();
				   if(workList.get(c).getExpY() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEARS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_YEAR_UNIT");
				   }
				}
				if(workList.get(c).getExpM() != 0)
				{
				   workingTable+=" "+workList.get(c).getExpM();
				   if(workList.get(c).getExpM() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTHS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(locale,"GLOBAL_MONTH_UNIT");
				   }
				}
				workingTable+=" )</b></u><br/>";
				workingTable+="<u><b>"+workList.get(c).getCompanyName();
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
				if(aState!=null)
				{
					workingTable+=", "+aState.getStateName();
				}
				else if(aState==null&& !workList.get(c).getOtherState().equals("") && isTranslate!=true )
				{
					
					//workingTable+=", "+workList.get(c).getOtherState();
					workingTable+=", "+viewInOriginal;
				}
				com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
				if(countrySelect!=null)
				{
					workingTable+=", "+countrySelect.getCountryName();
				}
				workingTable+="</b></u>";
				if(expIndustry!=null)
				{
					workingTable+="<br><b>"+propMgr.getMessage(locale,"WORKEXP_INDUSTRY")+":</b> ";
					for(int k=0;k<expIndustry.size();k++)
					{
						if(k>0)
						{
							expIndustryTable+=", ";
						}
						com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
						if(aInsdus!=null)
						{
							expIndustryTable+=aInsdus.getIndustryName();
						}
						else if(aInsdus==null && !expIndustry.get(k).getOtherIndustry().equals("") && isTranslate!=true)
						{
							//expIndustryTable+=expIndustry.get(k).getOtherIndustry();
							expIndustryTable+=viewInOriginal;
						}
					}
				}
				workingTable+=expIndustryTable;
				if(!workList.get(c).getComBusiness().equals("") && isTranslate!=true)
				{
					if(viewType==1){workingTable+="<font color='#82363a'>";}
					//workingTable+="<br><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_COMPANY_BUSINESS")+": </b>"+workList.get(c).getComBusiness().replace("\n", "<br>");
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_COMPANY_BUSINESS")+": </b>"+viewInOriginal;
					if(viewType==1){workingTable+="</font>";}
				}
				if(workList.get(c).getComSize()!=0)
				{
				   if(workList.get(c).getComSize()!=-1)
				   {
					   workingTable+="<br><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+": </b>";
					   if(workList.get(c).getComSize() == 1)
					   {
						   workingTable+="1-15<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 2)
					   {
						   workingTable+="15-30<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 3)
					   {
						   workingTable+="30-50<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 4)
					   {
						   workingTable+="50-100<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 5)
					   {
						   workingTable+="100-150<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 6)
					   {
						   workingTable+="150-300<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 7)
					   {
						   workingTable+="300-500<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 8)
					   {
						   workingTable+="500-1000<span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 9)
					   {
						   workingTable+="1000 <span>&nbsp;</span>"+propMgr.getMessage(locale,"PREVIEW_WORKING_UP_EMPLOYEES");
					   }else 
					   {
						   workingTable+=propMgr.getMessage(locale,"GLOBAL_NOT_SPECIFY");
					   }
				   }
				}				   
				if(!workList.get(c).getPositionLast().equals("") && isTranslate!=true)
				{
				   workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_LASTEST_POS")+":</b> "+workList.get(c).getPositionLast();
				}
			   com.topgun.shris.masterdata.JobField jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
			   com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
			   
			   if(jobfield != null && aSubfield != null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
				}
				else
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
				}
				/*
				 * 
				 * Jobfield
				 * 
				 */
				if(jobfield!=null)
				{
					workingTable+=jobfield.getFieldName();
				}
				else if(!workList.get(c).getWorkJobFieldOth().equals(""))
				{
					workingTable+=workList.get(c).getWorkJobFieldOth();
				}
				
				/*
				 * 
				 * Subfield
				 * 
				 */
				
				if(aSubfield!=null)
				{
					workingTable+=" : "+aSubfield.getSubfieldName();
				}
				else if(!workList.get(c).getWorkSubFieldOth().equals("")  && isTranslate!=true)
				{
					workingTable+=" : "+workList.get(c).getWorkSubFieldOth();
				}
			   
				if(workList.get(c).getSubordinate() > 0) 
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_SUBORDINATE")+": </b>"+workList.get(c).getSubordinate();
				}
				com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
				if(aJobbtype!=null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_TYPE")+":</b>"+aJobbtype.getTypeName();		
				}
				DecimalFormat aDecimal = new DecimalFormat( "###,###" );
				if(workList.get(c).getSalaryLast()>0)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
				}
				if(workList.get(c).getIdCurrency() > 0)
				{
					if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
					{
						workingTable+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
					}else{
						workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
					}
				}
				SalaryPer aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
				if(aSalPer!=null)
				{
					String salPer=aSalPer.getName();
					workingTable+=" "+propMgr.getMessage(locale,salPer);
				}
				jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
				if(!workList.get(c).getPositionStart().equals("") && isTranslate!=true)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_START_POS")+": </b>"+workList.get(c).getPositionStart();
					jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
					aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
					
					if(jobfield != null && aSubfield != null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
					}
					else
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
					}
					/*
					 * 
					 * Jobfield
					 * 
					 */
					if(jobfield!=null)
					{
						workingTable+=jobfield.getFieldName();
					}
					else if(!workList.get(c).getWorkJobFieldOthStart().equals(""))
					{
						workingTable+=workList.get(c).getWorkJobFieldOthStart();
					}
					
					/*
					 * 
					 * Subfield
					 * 
					 */
					
					if(aSubfield!=null)
					{
						workingTable+=" : "+aSubfield.getSubfieldName();
					}
					else if(!workList.get(c).getWorkSubFieldOthStart().equals("")  && isTranslate!=true)
					{
						workingTable+=" : "+workList.get(c).getWorkSubFieldOthStart();
					}
					
					if(workList.get(c).getSalaryStart()>0)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(locale,"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
					}
					if(workList.get(c).getSalaryStart()>0 && workList.get(c).getIdCurrencyStart() > 0)
					{
						if(workList.get(c).getIdCurrencyStart()==140 && resume.getIdLanguage()==38)
						{
							workingTable+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
						}else{
							workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode();
						}
						aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage());
						if(aSalPer!=null)
						{
							String salPer=aSalPer.getName();
							workingTable+=" "+propMgr.getMessage(locale,salPer);
						}
					}
					
				}
				if(viewType==1){workingTable+="<font color='#82363a'>";}
				if(!workList.get(c).getJobDesc().equals("") && isTranslate!=true)
				{
					 //workingTable+="<br><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_RESPONSIBILITIES")+": </b>"+workList.get(c).getJobDesc().replace("\n", "<br>");
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_RESPONSIBILITIES")+": </b>"+viewInOriginal;
				}
				if(!workList.get(c).getAchieve().equals("") && isTranslate!=true)
				{
					//workingTable+="<br><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_ACHIEVEMENT")+": </b>"+workList.get(c).getAchieve().replace("\n", "<br>");
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_ACHIEVEMENT")+": </b>"+viewInOriginal;
				}
				if(!workList.get(c).getReasonQuit().equals("") && isTranslate!=true)
				{
					//workingTable+="<br><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_REASON_FOR_LEAVING")+": </b>"+workList.get(c).getReasonQuit().replace("\n", "<br>");
					workingTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_WORKING_REASON_FOR_LEAVING")+": </b>"+viewInOriginal;
				}
				if(viewType==1){workingTable+="</font>";}
				workingTable+="<br/></td></tr>";
      		}
      		workingTable=workingHeadTable+workingTable+"</table>";
	  } 
	 else  // no work exp
	 {
		 startComment="<!--";
			endComment="-->";
	 }
		replaceAll(result,"#PREVIEW_WORKING_EXPERIENCE#", propMgr.getMessage(locale,"PREVIEW_WORKING_EXPERIENCE"));
		replaceAll(result,"#WORKING_EXPERIENCE_DETAIL#", workingTable);
		replaceAll(result,"#BEGIN_WORKING_EXPERIENCE#", startComment);
		replaceAll(result,"#END_WORKING_EXPERIENCE#", endComment);
    }


	private void ReplaceWorkingExperienceNotAllPart(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		int totalCom = 0;
		int totalM =0;
		int totalY = 0;
		String workingTable="";
		String workingHeadTable="";
		String startComment="";
		String endComment="";
		String startWork ="";
		String endWork="";
		String expIndustryTable = "";
		String expField="";
		List<WorkExperience> workList=new WorkExperienceManager().getAllFulltimes(resume.getIdJsk(),resume.getIdResume());
	    if (workList != null) 
	    {
			if(resume.getIdResume() == 0 )
			{
				//totalCom = resume.getExpCompany();
				totalCom = workList.size();
				totalY = resume.getExpYear();
				totalM = resume.getExpMonth();
				List<ExperienceSummary> sumField=new ExperienceSummaryManager().getAll(resume.getIdJsk(),resume.getIdResume());
				if(sumField != null)
				{
					for(int c=0;c<sumField.size();c++)
					{
						expField+="<br/>: ";
						if(sumField.get(c).getSumYear() > 0)
						{
							expField+=sumField.get(c).getSumYear();
							if(sumField.get(c).getSumYear()  > 1)
							{
								expField+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
							}
							else 
							{
								expField+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
							}
						}
						if(sumField.get(c).getSumMonth() > 0)
						{
							expField+=" "+sumField.get(c).getSumMonth();
							if(sumField.get(c).getSumMonth()  > 1)
							{
								expField+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
							}
							else 
							{
								expField+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
							}
						}
						expField+=", "+propMgr.getMessage(resume.getLocale(),"GLOBAL_IN_THE_FIELD_OF")+": ";
						com.topgun.shris.masterdata.JobField aJobfield=MasterDataManager.getJobField(sumField.get(c).getJobField(), resume.getIdLanguage());
						if(aJobfield!=null)
						{
							expField+=aJobfield.getFieldName();
						}
					} // close for
					expField+="<br/><br/>"; 
				}//close if
			}
	    	else 
	    	{
	    		totalCom = workList.size();
				for (int co=0;co<workList.size();co++) 
				{
					totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
				}
				totalY = totalM / 12;
				totalM = totalM % 12;
	    	}
      		workingHeadTable+="<B>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_TOTAL_WORKING")+": "; 
			 if(totalY != 0)
			 { 
				 workingHeadTable+=totalY;
				 if(totalY  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
				 }
			 }	
			 if(totalM != 0)
			 {
				 workingHeadTable+=" "+totalM;
				 if(totalM  > 1)
				 {
					 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
				 }
				 else 
				 {
					 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
				 }
			 }	
			 workingHeadTable+=", "+totalCom;
			 if(totalCom > 1)
			 {
				 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_COMPANIES_UNIT");
			 }
			 else 
			 {
				 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_COMPANY_UNIT");
			 }
			 workingHeadTable+="</B>";
			 workingHeadTable+=expField+"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\">";
      		for(int c=0;c<workList.size();c++)
      		{
      			startWork ="";
    			endWork="";
    			expIndustryTable="";
    			List<ExperienceIndustry>expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
    			workingTable+="<tr><td  style='padding-top:5px;'>";
				if(resume.getIdLanguage() == 38) // thai language
				{
					if(workList.get(c).getWorkStart() != null)
					{
						startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
					}
					if(workList.get(c).getWorkEnd() != null)
					{
						if(workList.get(c).getWorkingStatus().equals("TRUE"))
						{
							endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+(Util.getYear(workList.get(c).getWorkEnd())+543);
						}
						else 
						{
							endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
						}
					}
				}
				else if(resume.getIdLanguage() == 23)  // japanese language
				{
					if(workList.get(c).getWorkStart() != null)
					{
						startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "yyyy年MMMM",aLanguage,aCountry);
					}
					if(workList.get(c).getWorkEnd() != null)
					{
						if(workList.get(c).getWorkingStatus().equals("TRUE"))
						{
							endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd())+"年";
						}
						else 
						{
							endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "yyyy年MMMM",aLanguage,aCountry);
						}
					}
				}
				else  // english language
				{
				   if(workList.get(c).getWorkStart() != null)
				   {
					   startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
				   }
				   
				   if(workList.get(c).getWorkEnd() != null)
				   {   
					   if(workList.get(c).getWorkingStatus().equals("TRUE"))
					   {
						   endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd());
					   }
					   else 
					   {
						   endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
					   }
					  
				   }
				}
				workingTable+="<u><b>"+startWork+" - "+endWork+"( ";
				if(workList.get(c).getExpY()!=0)
				{
				   workingTable+=workList.get(c).getExpY();
				   if(workList.get(c).getExpY() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
				   }
				}
				if(workList.get(c).getExpM() != 0)
				{
				   workingTable+=" "+workList.get(c).getExpM();
				   if(workList.get(c).getExpM() > 1)
				   {
					   workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
				   }
				   else 
				   {
					   workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
				   }
				}
				workingTable+=" )</b></u><br/>";
				workingTable+="<u><b>"+workList.get(c).getCompanyName();
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
				if(aState!=null)
				{
					workingTable+=", "+aState.getStateName();
				}
				else if(aState==null&& !workList.get(c).getOtherState().equals("") )
				{
					workingTable+=", "+workList.get(c).getOtherState();
				}
				com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
				if(countrySelect!=null)
				{
					workingTable+=", "+countrySelect.getCountryName();
				}
				workingTable+="</b></u>";
				if(expIndustry!=null)
				{
				   workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_INDUSTRY")+":</b> ";
					for(int k=0;k<expIndustry.size();k++)
					{
						if(k>0)
						{
							expIndustryTable+=", ";
						}
						com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
						if(aInsdus!=null)
						{
							expIndustryTable+=aInsdus.getIndustryName();
						}
						else if(aInsdus==null && !expIndustry.get(k).getOtherIndustry().equals(""))
						{
							expIndustryTable+=expIndustry.get(k).getOtherIndustry();
						}
					}
				}
				workingTable+=expIndustryTable;
				if(workList.get(c).getComSize()!=0)
				{
				   if(workList.get(c).getComSize()!=-1)
				   {
					   workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+": </b>";
					   if(workList.get(c).getComSize() == 1)
					   {
						   workingTable+="1-15<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 2)
					   {
						   workingTable+="15-30<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 3)
					   {
						   workingTable+="30-50<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 4)
					   {
						   workingTable+="50-100<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 5)
					   {
						   workingTable+="100-150<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 6)
					   {
						   workingTable+="150-300<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 7)
					   {
						   workingTable+="300-500<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 8)
					   {
						   workingTable+="500-1000<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
					   }
					   else if(workList.get(c).getComSize() == 9)
					   {
						   workingTable+="1000 <span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_UP_EMPLOYEES");
					   }
				   }
				}				   
				if(!workList.get(c).getPositionLast().equals(""))
				{
				   workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_POS")+":</b> "+workList.get(c).getPositionLast();
				}
				com.topgun.shris.masterdata.JobField jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
				com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
				
				if(jobfield != null && aSubfield != null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
				}
				else
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
				}
				/*
				 * 
				 * Jobfield
				 * 
				 */
				if(jobfield!=null)
				{
					workingTable+=jobfield.getFieldName();
				}
				else if(!workList.get(c).getWorkJobFieldOth().equals(""))
				{
					workingTable+=workList.get(c).getWorkJobFieldOth();
				}
				
				/*
				 * 
				 * Subfield
				 * 
				 */
				
				if(aSubfield!=null)
				{
					workingTable+=" : "+aSubfield.getSubfieldName();
				}
				else if(!workList.get(c).getWorkSubFieldOth().equals(""))
				{
					workingTable+=" : "+workList.get(c).getWorkSubFieldOth();
				}
				
				if(workList.get(c).getSubordinate() != 0)
				{
				   workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_SUBORDINATE")+": </b>"+workList.get(c).getSubordinate();
				}
				com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
				if(aJobbtype!=null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_TYPE")+":</b>"+aJobbtype.getTypeName();		
				}
				DecimalFormat aDecimal = new DecimalFormat( "###,###" );
				if(workList.get(c).getSalaryLast()>0)
				{
					workingTable+="<br><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
				}
				if(workList.get(c).getIdCurrency() > 0)
				{
					if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
					{
						workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
					}else{
						workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
					}
				}
				SalaryPer aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
				if(aSalPer!=null)
				{
					String salPer=aSalPer.getName();
					workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
				}
				if(!workList.get(c).getPositionStart().equals(""))
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_POS")+": </b>"+workList.get(c).getPositionStart();
					jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
					aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
					if(jobfield != null && aSubfield != null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
					}
					else
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
					}
					/*
					 * 
					 * Jobfield
					 * 
					 */
					if(jobfield!=null)
					{
						workingTable+=jobfield.getFieldName();
					}
					else if(!workList.get(c).getWorkJobFieldOthStart().equals(""))
					{
						workingTable+=workList.get(c).getWorkJobFieldOthStart();
					}
					
					/*
					 * 
					 * Subfield
					 * 
					 */
					
					if(aSubfield!=null)
					{
						workingTable+=" : "+aSubfield.getSubfieldName();
					}
					else if(!workList.get(c).getWorkSubFieldOthStart().equals(""))
					{
						workingTable+=" : "+workList.get(c).getWorkSubFieldOthStart();
					}
					
					if(workList.get(c).getSalaryStart()>0)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
					}
					if(workList.get(c).getSalaryStart()>0 && workList.get(c).getIdCurrencyStart() > 0)
					{
						if(workList.get(c).getIdCurrencyStart()==140 && resume.getIdLanguage()==38)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
						}else{
							workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode();
						}
						aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage());
						if(aSalPer!=null)
						{
							String salPer=aSalPer.getName();
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
						}
					}
				}			
				workingTable+="<br/></td></tr>";
      		}
      		workingTable=workingHeadTable+workingTable+"</table>";
	    } 
		 else  // no work exp
		 {
			    startComment="<!--";
				endComment="-->";
		 }
	    replaceAll(result,"#PREVIEW_WORKING_EXPERIENCE#", propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EXPERIENCE"));
		replaceAll(result,"#WORKING_EXPERIENCE_DETAIL#", workingTable);
		replaceAll(result,"#BEGIN_WORKING_EXPERIENCE#", startComment);
		replaceAll(result,"#END_WORKING_EXPERIENCE#", endComment);
    }
	
	private void ReplaceParttimeEmployment(Resume resume)
	{
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		int totalCom = 0;
		int totalM =0;
		int totalY = 0;
		String workingTable="";
		String workingHeadTable="";
		String startComment="";
		String endComment="";
		String startWork ="";
		String endWork="";
		String expIndustryTable = "";
		List<WorkExperience> workList=new WorkExperienceManager().getAllParttimes(resume.getIdJsk(),resume.getIdResume());
		if (workList.size()>0) 
		{
			totalCom = workList.size();
			for (int co=0;co<workList.size();co++) 
			{
				totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
			}
			totalY = totalM / 12;
			totalM = totalM % 12;
			 
			workingHeadTable+="<B>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_TOTAL_PART_TIME_EXPERIENCE")+": ";
			workingHeadTable+=totalCom;
			if(totalCom > 1)
			{
				 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_JOBS_UNIT");
			}
			else 
			{
				 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_JOB_UNIT");
			}
			workingHeadTable+=", "+propMgr.getMessage(resume.getLocale(),"PREVIEW_TOTAL_DURATION");
			if(totalY!=0 || totalM != 0)
			{
			workingHeadTable+=" "+totalY;
			if(totalY>1)
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
			}
			else
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
				}
			if(totalM != 0)
			{
				workingHeadTable+=""+totalM;
			if(totalM>1)
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
			}
			else
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
				}
			}	
			workingHeadTable+="</B>";
			workingHeadTable+="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\">";
			for(int c=0;c<workList.size();c++)
			{
				startWork ="";
				endWork="";
				expIndustryTable="";
				List<ExperienceIndustry>expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
				workingTable+="<tr><td style='padding-top:5px;'>";
				   if(resume.getIdLanguage() == 38) // thai language
				   {
						if(workList.get(c).getWorkStart() != null)
						{
							startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
						}
						if(workList.get(c).getWorkEnd() != null)
						{
							if(workList.get(c).getWorkingStatus().equals("TRUE"))
							{
								endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+(Util.getYear(workList.get(c).getWorkEnd())+543);
							}
							else 
							{
								endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
							}
						}
				   }
				   else if(resume.getIdLanguage() == 23) // japanese language
				   {	
						if(workList.get(c).getWorkStart() != null)
						{
							startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "yyyy年MMMM",aLanguage,aCountry);
						}
						if(workList.get(c).getWorkEnd() != null)
						{
							if(workList.get(c).getWorkingStatus().equals("TRUE"))
							{
								endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd())+"年";
							}
							else 
							{
								endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "yyyy年MMMM",aLanguage,aCountry);
							}
						}
				   	}
					else  // english and other language
					{
						if(workList.get(c).getWorkStart() != null)
						{
							startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
						}
						if(workList.get(c).getWorkEnd() != null)
						{
							if(workList.get(c).getWorkingStatus().equals("TRUE"))
							{
								endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd());
							}
							else 
							{
								endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
							}
						}
					}
				   	workingTable+="<u><b>"+startWork+" - "+endWork+"( ";
					if(workList.get(c).getExpY()!=0)
					{
						workingTable+=workList.get(c).getExpY();
						if(workList.get(c).getExpY() > 1)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
					}
					else 
					{
						workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
						}
					}
			
					if(workList.get(c).getExpM() != 0)
					{
						workingTable+=" "+workList.get(c).getExpM();
						if(workList.get(c).getExpM() > 1)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
						}
						else 
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
						}
					}
					workingTable+=" )</b></u><br/>";
					workingTable+="<u><b>"+workList.get(c).getCompanyName();
					com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
					if(aState!=null)
					{
						workingTable+=", "+aState.getStateName();
					}
					else if(aState==null&& !workList.get(c).getOtherState().equals("") )
					{
						workingTable+=", "+workList.get(c).getOtherState();
					}
					com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
					if(countrySelect!=null)
					{
						workingTable+=", "+countrySelect.getCountryName();
					}
					workingTable+="</b></u>";
					if(expIndustry!=null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_INDUSTRY")+":</b> ";
						for(int k=0;k<expIndustry.size();k++)
						{
							if(k>0)
							{
								expIndustryTable+=", ";
							}
							com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
							if(aInsdus!=null)
							{
								expIndustryTable+=aInsdus.getIndustryName();
							}
							else if(aInsdus==null && !expIndustry.get(k).getOtherIndustry().equals(""))
							{
								expIndustryTable+=expIndustry.get(k).getOtherIndustry();
							}
						}
					}
					workingTable+=expIndustryTable;
					if(!workList.get(c).getComBusiness().equals("") && isTranslate!=true)
					{
						if(viewType==1){workingTable+="<font color='#82363a'>";}
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_COMPANY_BUSINESS")+": </b>"+workList.get(c).getComBusiness().replace("\n", "<br/>");
						if(viewType==1){workingTable+="</font>";}
					}
					if(workList.get(c).getComSize()!=0)
					{
						if(workList.get(c).getComSize()!=-1)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+": </b>";
							if(workList.get(c).getComSize() == 1)
							{
								workingTable+="1-15<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 2)
							{
								workingTable+="15-30<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 3)
							{
								workingTable+="30-50<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 4)
							{
								workingTable+="50-100<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 5)
							{
								workingTable+="100-150<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 6)
							{
								workingTable+="150-300<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 7)
							{
								workingTable+="300-500<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 8)
							{
								workingTable+="500-1000<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 9)
							{
								workingTable+="1000 <span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_UP_EMPLOYEES");
							}
						}
					}				
				
					if(!workList.get(c).getPositionLast().equals("") && isTranslate!=true)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_POS")+":</b> "+workList.get(c).getPositionLast();
					}
					com.topgun.shris.masterdata.JobField jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
					com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
					
					if(jobfield != null && aSubfield != null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
					}
					else
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
					}
					
					if(jobfield!=null)
					{
						workingTable+=jobfield.getFieldName();
					}
					else if(!workList.get(c).getWorkJobFieldOth().equals(""))
					{
						workingTable+=workList.get(c).getWorkJobFieldOth();
					}
					
					if(aSubfield!=null)
					{
						workingTable+=" : "+aSubfield.getSubfieldName();
					}
					else if(!workList.get(c).getWorkSubFieldOth().equals(""))
					{
						workingTable+=" : "+workList.get(c).getWorkSubFieldOth();
					}
					
					
					if(workList.get(c).getSubordinate() > 0) 
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_SUBORDINATE")+": </b>"+workList.get(c).getSubordinate();
					}
					com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
					if(aJobbtype!=null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_TYPE")+":</b>"+aJobbtype.getTypeName();		
					}
					DecimalFormat aDecimal = new DecimalFormat( "###,###" );
					SalaryPer aSalPer=null;
					if(workList.get(c).getSalaryLast()>0){
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
						if(workList.get(c).getIdCurrency() > 0)
						{
							if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
							{
								workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
							}else{
								workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
							}
						}
					
						aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
						if(aSalPer!=null)
						{
							String salPer=aSalPer.getName();
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
						}
					}
					if(!workList.get(c).getPositionStart().equals("") )
					{
						//start position
						if(isTranslate!=true)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_POS")+": </b>"+workList.get(c).getPositionStart();
						}
						jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
						aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
						//Subfield of position
						
						if(jobfield != null && aSubfield != null)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
						}
						else
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
						}
						
						if(jobfield!=null)
						{
							workingTable+=jobfield.getFieldName();
						}
						else if(!workList.get(c).getWorkJobFieldOthStart().equals("")&& isTranslate!=true)
						{
							workingTable+=workList.get(c).getWorkJobFieldOthStart();
						}
						
						if(aSubfield!=null)
						{
							workingTable+=" : "+aSubfield.getSubfieldName();
						}
						else if(!workList.get(c).getWorkSubFieldOthStart().equals("") && isTranslate!=true)
						{
							workingTable+=" : "+workList.get(c).getWorkSubFieldOthStart();
						}
						
						if(workList.get(c).getSalaryStart()>0)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
							if(workList.get(c).getIdCurrencyStart() > 0)
							{
								if(workList.get(c).getIdCurrencyStart()==140 && resume.getIdLanguage()==38)
								{
									workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
								}else{
									workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode();
								}
								aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage());
								if(aSalPer!=null)
								{
									String salPer=aSalPer.getName();
									workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
								}
							}
						}
						
						/*
						if(workList.get(c).getIdCurrency() > 0)
						{
							workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getName();
							aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
						}
						if(aSalPer!=null)
						{
							String salPer=aSalPer.getName();
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
						}
						*/
					}	
					if(viewType==1){workingTable+="<font color='#82363a'>";}
					if(!workList.get(c).getJobDesc().equals("") && isTranslate!=true)
					{
						 workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_RESPONSIBILITIES")+": </b>"+workList.get(c).getJobDesc().replace("\n", "<br/>");
					}
					if(!workList.get(c).getAchieve().equals("")&& isTranslate!=true)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_ACHIEVEMENT")+": </b>"+workList.get(c).getAchieve().replace("\n", "<br/>");
					}
					if(!workList.get(c).getReasonQuit().equals("")&& isTranslate!=true)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_REASON_FOR_LEAVING")+": </b>"+workList.get(c).getReasonQuit().replace("\n", "<br/>");
					}
					if(viewType==1){workingTable+="</font>";}
					workingTable+="<br/></td></tr>";
					}
					workingTable=workingHeadTable+workingTable+"</table>";
				} 
				else  // no work exp
				{
					startComment="<!--";
					endComment="-->";
				}
				replaceAll(result,"#PREVIEW_PART_TIME#", propMgr.getMessage(resume.getLocale(),"PREVIEW_PART_TIME"));
				replaceAll(result,"#PART_TIME_DETAIL#", workingTable);
				replaceAll(result,"#BEGIN_PART_TIME#", startComment);
				replaceAll(result,"#END_PART_TIME#", endComment);
			}
			else
			{
				replaceAll(result,"#BEGIN_PART_TIME#", "<!--");
				replaceAll(result,"#END_PART_TIME#", "-->");
			}
		}
	private void ReplaceParttimeEmploymentHideOther(Resume resume)
	{
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		int totalCom = 0;
		int totalM =0;
		int totalY = 0;
		String workingTable="";
		String workingHeadTable="";
		String startComment="";
		String endComment="";
		String startWork ="";
		String endWork="";
		String expIndustryTable = "";
		List<WorkExperience> workList=new WorkExperienceManager().getAllParttimes(resume.getIdJsk(),resume.getIdResume());
		if (workList.size()>0) 
		{
			totalCom = workList.size();
			for (int co=0;co<workList.size();co++) 
			{
				totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
			}
			totalY = totalM / 12;
			totalM = totalM % 12;
			 
			workingHeadTable+="<B>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_TOTAL_PART_TIME_EXPERIENCE")+": ";
			workingHeadTable+=totalCom;
			if(totalCom > 1)
			{
				 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_JOBS_UNIT");
			}
			else 
			{
				 workingHeadTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_JOB_UNIT");
			}
			workingHeadTable+=", "+propMgr.getMessage(resume.getLocale(),"PREVIEW_TOTAL_DURATION");
			if(totalY!=0 || totalM != 0)
			{
			workingHeadTable+=" "+totalY;
			if(totalY>1)
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
			}
			else
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
				}
			if(totalM != 0)
			{
				workingHeadTable+=""+totalM;
			if(totalM>1)
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
			}
			else
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
				}
			}	
			workingHeadTable+="</B>";
			workingHeadTable+="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\">";
			for(int c=0;c<workList.size();c++)
			{
				startWork ="";
				endWork="";
				expIndustryTable="";
				List<ExperienceIndustry>expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
				workingTable+="<tr><td style='padding-top:5px;'>";
				   if(resume.getIdLanguage() == 38) // thai language
				   {
						if(workList.get(c).getWorkStart() != null)
						{
							startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
						}
						if(workList.get(c).getWorkEnd() != null)
						{
							if(workList.get(c).getWorkingStatus().equals("TRUE"))
							{
								endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+(Util.getYear(workList.get(c).getWorkEnd())+543);
							}
							else 
							{
								endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
							}
						}
				   }
				   else if(resume.getIdLanguage() == 23) // japanese language
				   {	
						if(workList.get(c).getWorkStart() != null)
						{
							startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "yyyy年MMMM",aLanguage,aCountry);
						}
						if(workList.get(c).getWorkEnd() != null)
						{
							if(workList.get(c).getWorkingStatus().equals("TRUE"))
							{
								endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd())+"年";
							}
							else 
							{
								endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "yyyy年MMMM",aLanguage,aCountry);
							}
						}
				   	}
					else  // english and other language
					{
						if(workList.get(c).getWorkStart() != null)
						{
							startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
						}
						if(workList.get(c).getWorkEnd() != null)
						{
							if(workList.get(c).getWorkingStatus().equals("TRUE"))
							{
								endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+Util.getYear(workList.get(c).getWorkEnd());
							}
							else 
							{
								endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
							}
						}
					}
				   	workingTable+="<u><b>"+startWork+" - "+endWork+"( ";
					if(workList.get(c).getExpY()!=0)
					{
						workingTable+=workList.get(c).getExpY();
						if(workList.get(c).getExpY() > 1)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
					}
					else 
					{
						workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
						}
					}
			
					if(workList.get(c).getExpM() != 0)
					{
						workingTable+=" "+workList.get(c).getExpM();
						if(workList.get(c).getExpM() > 1)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
						}
						else 
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
						}
					}
					workingTable+=" )</b></u><br/>";
					workingTable+="<u><b>"+workList.get(c).getCompanyName();
					com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
					if(aState!=null)
					{
						workingTable+=", "+aState.getStateName();
					}
					else if(aState==null&& !workList.get(c).getOtherState().equals("") )
					{
						//workingTable+=", "+workList.get(c).getOtherState();
						workingTable+=", "+viewInOriginal;
					}
					com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
					if(countrySelect!=null)
					{
						workingTable+=", "+countrySelect.getCountryName();
					}
					workingTable+="</b></u>";
					if(expIndustry!=null)
					{
						workingTable+="<br><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_INDUSTRY")+":</b> ";
						for(int k=0;k<expIndustry.size();k++)
						{
							if(k>0)
							{
								expIndustryTable+=", ";
							}
							com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
							if(aInsdus!=null)
							{
								expIndustryTable+=aInsdus.getIndustryName();
							}
							else if(aInsdus==null && !expIndustry.get(k).getOtherIndustry().equals(""))
							{
								//expIndustryTable+=expIndustry.get(k).getOtherIndustry();
								expIndustryTable+=viewInOriginal;
							}
						}
					}
					workingTable+=expIndustryTable;
					if(!workList.get(c).getComBusiness().equals("") && isTranslate!=true)
					{
						if(viewType==1){workingTable+="<font color='#82363a'>";}
						//workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_COMPANY_BUSINESS")+": </b>"+workList.get(c).getComBusiness().replace("\n", "<br/>");
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_COMPANY_BUSINESS")+": </b>"+viewInOriginal;
						if(viewType==1){workingTable+="</font>";}
					}
					if(workList.get(c).getComSize()!=0)
					{
						if(workList.get(c).getComSize()!=-1)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+": </b>";
							if(workList.get(c).getComSize() == 1)
							{
								workingTable+="1-15<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 2)
							{
								workingTable+="15-30<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 3)
							{
								workingTable+="30-50<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 4)
							{
								workingTable+="50-100<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 5)
							{
								workingTable+="100-150<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 6)
							{
								workingTable+="150-300<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 7)
							{
								workingTable+="300-500<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 8)
							{
								workingTable+="500-1000<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
							}
							else if(workList.get(c).getComSize() == 9)
							{
								workingTable+="1000 <span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_UP_EMPLOYEES");
							}
						}
					}				
				
					if(!workList.get(c).getPositionLast().equals("") && isTranslate!=true)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_POS")+":</b> "+workList.get(c).getPositionLast();
					}
					com.topgun.shris.masterdata.JobField jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
					com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
					
					if(jobfield != null && aSubfield != null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
					}
					else
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
					}
					/*
					 * 
					 * Jobfield
					 * 
					 */
					if(jobfield!=null)
					{
						workingTable+=jobfield.getFieldName();
					}
					else if(!workList.get(c).getWorkJobFieldOth().equals(""))
					{
						workingTable+=workList.get(c).getWorkJobFieldOth();
					}
					
					/*
					 * 
					 * Subfield
					 * 
					 */
					
					if(aSubfield!=null)
					{
						workingTable+=" : "+aSubfield.getSubfieldName();
					}
					else if(!workList.get(c).getWorkSubFieldOth().equals(""))
					{
						workingTable+=" : "+workList.get(c).getWorkSubFieldOth();
					}
					
					if(workList.get(c).getSubordinate() > 0) 
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_SUBORDINATE")+": </b>"+workList.get(c).getSubordinate();
					}
					com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
					if(aJobbtype!=null)
					{
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_TYPE")+":</b>"+aJobbtype.getTypeName();		
					}
					DecimalFormat aDecimal = new DecimalFormat( "###,###" );
					SalaryPer aSalPer=null;
					if(workList.get(c).getSalaryLast()>0){
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
						if(workList.get(c).getIdCurrency() > 0)
						{
							if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
							{
								workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
							}else{
								workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
							}
						}
					
						aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
						if(aSalPer!=null)
						{
							String salPer=aSalPer.getName();
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
						}
					}
					if(!workList.get(c).getPositionStart().equals("") )
					{
						//start position
						if(isTranslate!=true)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_POS")+": </b>"+workList.get(c).getPositionStart();
						}
						jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
						aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
						//position
						if(jobfield != null && aSubfield != null)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
						}
						else
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
						}
						
						if(jobfield!=null)
						{
							workingTable+=jobfield.getFieldName();
						}
						else if(!workList.get(c).getWorkJobFieldOthStart().equals("")&& isTranslate!=true)
						{
							workingTable+=workList.get(c).getWorkJobFieldOthStart();
						}
						
						//Subfield of position
						
						if(aSubfield!=null)
						{
							workingTable+=" : "+aSubfield.getSubfieldName();
						}
						else if(!workList.get(c).getWorkSubFieldOthStart().equals("") && isTranslate!=true)
						{
							workingTable+=" : "+workList.get(c).getWorkSubFieldOthStart();
						}
						
						if(workList.get(c).getSalaryStart()>0)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
							if(workList.get(c).getIdCurrencyStart() > 0)
							{
								if(workList.get(c).getIdCurrencyStart()==140 && resume.getIdLanguage()==38)
								{
									workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
								}else{
									workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode();
								}
								aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage());
								if(aSalPer!=null)
								{
									String salPer=aSalPer.getName();
									workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
								}
							}
						}
						
						/*
						if(workList.get(c).getSalaryStart()>0)
						{
							workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
						}
						if(workList.get(c).getIdCurrency() > 0)
						{
							workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getName();
							aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
						}
						if(aSalPer!=null)
						{
							String salPer=aSalPer.getName();
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
						}
						*/
					}	
					if(viewType==1){workingTable+="<font color='#82363a'>";}
					if(!workList.get(c).getJobDesc().equals("") && isTranslate!=true)
					{
						 //workingTable+="<br><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_RESPONSIBILITIES")+": </b>"+workList.get(c).getJobDesc().replace("\n", "<br/>");
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_RESPONSIBILITIES")+": </b>"+viewInOriginal;
					}
					if(!workList.get(c).getAchieve().equals("")&& isTranslate!=true)
					{
						//workingTable+="<br><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_ACHIEVEMENT")+": </b>"+workList.get(c).getAchieve().replace("\n", "<br/>");
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_ACHIEVEMENT")+": </b>"+viewInOriginal;
					}
					if(!workList.get(c).getReasonQuit().equals("")&& isTranslate!=true)
					{
						//workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_REASON_FOR_LEAVING")+": </b>"+workList.get(c).getReasonQuit().replace("\n", "<br/>");
						workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_REASON_FOR_LEAVING")+": </b>"+viewInOriginal;
					}
					if(viewType==1){workingTable+="</font>";}
					workingTable+="<br/></td></tr>";
					}
					workingTable=workingHeadTable+workingTable+"</table>";
				} 
				else  // no work exp
				{
					startComment="<!--";
					endComment="-->";
				}
				replaceAll(result,"#PREVIEW_PART_TIME#", propMgr.getMessage(resume.getLocale(),"PREVIEW_PART_TIME"));
				replaceAll(result,"#PART_TIME_DETAIL#", workingTable);
				replaceAll(result,"#BEGIN_PART_TIME#", startComment);
				replaceAll(result,"#END_PART_TIME#", endComment);
			}
			else
			{
				replaceAll(result,"#BEGIN_PART_TIME#", "<!--");
				replaceAll(result,"#END_PART_TIME#", "-->");
			}
		}
	
	private void ReplaceParttimeEmploymentNotAllPart(Resume resume)
	{
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		int totalCom = 0;
		int totalM =0;
		int totalY = 0;
		String workingTable="";
		String workingHeadTable="";
		String startComment="";
		String endComment="";
		String startWork ="";
		String endWork="";
		String expIndustryTable = "";
		List<WorkExperience> workList=new WorkExperienceManager().getAllParttimes(resume.getIdJsk(),resume.getIdResume());
	    if (workList.size()>0) 
	    {
	   		totalCom = workList.size();
	   		for (int co=0;co<workList.size();co++) 
	   		{
	       		totalM = totalM + (workList.get(co).getExpY() * 12) + workList.get(co).getExpM();
	  	    }
	   		totalY = totalM / 12;
	   		totalM = totalM % 12;
	    		
			workingHeadTable+="<B>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_TOTAL_PART_TIME_EXPERIENCE")+":";
			workingHeadTable+=totalCom;
			if(totalCom>1)
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_JOBS_UNIT");
			}
			else
			{
				workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_JOB_UNIT");
			}
			workingHeadTable+=","+propMgr.getMessage(resume.getLocale(),"PREVIEW_TOTAL_DURATION");
			
			if(totalY!=0)
			{
				workingHeadTable+=totalY;
				if(totalY>1)
				{
					workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
				}
				else
				{
					workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
				}
			}	
			if(totalM!=0)
			{
				workingHeadTable+=totalM;
				if(totalM>1)
				{
					workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
				}
				else
				{
					workingHeadTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
				}
			}	
			workingHeadTable+="</B>";
			workingHeadTable+="<table border=\"0\"cellpadding=\"0\"cellspacing=\"0\"class=\"f11\">";
			for(int c=0;c<workList.size();c++)
			{
				startWork="";
				endWork="";
				expIndustryTable="";
				List<ExperienceIndustry>expIndustry=new ExperienceIndustryManager().getExpIndustry(resume.getIdJsk(), resume.getIdResume(), workList.get(c).getId());
				workingTable+="<tr><td style='padding-top:5px;'>";
				if(resume.getIdLanguage()==38)//thai language
				{
					
					if(workList.get(c).getWorkStart() != null)
					{
						startWork=Util.getLocaleDate(workList.get(c).getWorkStart(), "MMM yyyy",aLanguage,aCountry);
					}
					if(workList.get(c).getWorkEnd() != null)
					{
						if(workList.get(c).getWorkingStatus().equals("TRUE"))
						{
							endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+" "+(Util.getYear(workList.get(c).getWorkEnd())+543);
						}
						else 
						{
							endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(), "MMM yyyy",aLanguage,aCountry);
						}
					}
				}
				else if(resume.getIdLanguage()==23)//japanese language
				{
					if(workList.get(c).getWorkStart()!=null)
					{
						startWork=Util.getLocaleDate(workList.get(c).getWorkStart(),"yyyy年MMMM",aLanguage,aCountry);
					}
					
					if(workList.get(c).getWorkEnd()!=null)
					{  
						if(workList.get(c).getWorkingStatus().equals("TRUE"))
						{
							endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+""+Util.getYear(workList.get(c).getWorkEnd())+"年";
						}
						else
						{
							endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(),"yyyy年MMMM",aLanguage,aCountry);
						}
						
					}
				}
				else//english language
				{
					if(workList.get(c).getWorkStart()!=null)
					{
						startWork=Util.getLocaleDate(workList.get(c).getWorkStart(),"MMMyyyy",aLanguage,aCountry);
					}
					
					if(workList.get(c).getWorkEnd()!=null)
					{
						if(workList.get(c).getWorkingStatus().equals("TRUE"))
						{
							endWork=propMgr.getMessage(resume.getLocale(),"WORKEXP_PRESENT")+""+Util.getYear(workList.get(c).getWorkEnd());
						}
						else
						{
							endWork=Util.getLocaleDate(workList.get(c).getWorkEnd(),"MMMyyyy",aLanguage,aCountry);
						}
						
					}
				}
				workingTable+="<u><b>"+startWork+"-"+endWork+"(";
				if(workList.get(c).getExpY()!=0)
				{
					workingTable+=workList.get(c).getExpY();
					if(workList.get(c).getExpY()>1)
					{
						workingTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEARS_UNIT");
					}
					else
					{
						workingTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_YEAR_UNIT");
					}
				}
				if(workList.get(c).getExpM()!=0)
				{
					workingTable+=""+workList.get(c).getExpM();
					if(workList.get(c).getExpM()>1)
					{
						workingTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTHS_UNIT");
					}
					else
					{
						workingTable+=""+propMgr.getMessage(resume.getLocale(),"GLOBAL_MONTH_UNIT");
					}
				}
			workingTable+=")</b></u>";
			workingTable+="<u><b>"+workList.get(c).getCompanyName()+",";

			com.topgun.shris.masterdata.State aState=MasterDataManager.getState(workList.get(c).getIdCountry(), workList.get(c).getIdState(), resume.getIdLanguage());
			if(aState!=null)
			{
				workingTable+=", "+aState.getStateName();
			}
			else if(aState==null&& !workList.get(c).getOtherState().equals("") )
			{
				workingTable+=", "+workList.get(c).getOtherState();
			}
			com.topgun.shris.masterdata.Country countrySelect=MasterDataManager.getCountry(workList.get(c).getIdCountry(), resume.getIdLanguage());
			if(countrySelect!=null)
			{
				workingTable+=", "+countrySelect.getCountryName();
			}	
			workingTable+="</b></u>";
			if(expIndustry!=null)
			{
				workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_INDUSTRY")+":</b>";
				for(int k=0;k<expIndustry.size();k++)
				{
					if(k>0)
					{
						expIndustryTable+=", ";
					}
					com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(expIndustry.get(k).getIdIndustry(), resume.getIdLanguage());
					if(aInsdus!=null)
					{
						expIndustryTable+=aInsdus.getIndustryName();
					}
					else if(aInsdus==null && !expIndustry.get(k).getOtherIndustry().equals(""))
					{
						expIndustryTable+=expIndustry.get(k).getOtherIndustry();
					}
				}
			}
			workingTable+=expIndustryTable;
			if(workList.get(c).getComSize()!=0)
			{
				workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_NUMBER_OF_EMPLOYEE")+":</b>";
				if(workList.get(c).getComSize()==1)
				{
					workingTable+="1-15<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
				else if(workList.get(c).getComSize()==2)
				{
					workingTable+="15-30<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
				else if(workList.get(c).getComSize() == 3)
				{
				   workingTable+="30-50<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
			   else if(workList.get(c).getComSize() == 4)
				{
					workingTable+="50-100<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
				else if(workList.get(c).getComSize() == 5)
				{
					workingTable+="100-150<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
				else if(workList.get(c).getComSize() == 6)
				{
					workingTable+="150-300<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
				else if(workList.get(c).getComSize() == 7)
				{
					workingTable+="300-500<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
				else if(workList.get(c).getComSize() == 8)
				{
					workingTable+="500-1000<span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_EMPLOYEES");
				}
				else if(workList.get(c).getComSize() == 9)
				{
					workingTable+="1000 <span>&nbsp;</span>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_WORKING_UP_EMPLOYEES");
				}
			}				   
		   if(!workList.get(c).getPositionLast().equals(""))
		   {
			   workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_POS")+":</b> "+workList.get(c).getPositionLast();
		   }
		   com.topgun.shris.masterdata.JobField jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobField(), resume.getIdLanguage());
		   com.topgun.shris.masterdata.SubField aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobField(),workList.get(c).getWorkSubField(),resume.getIdLanguage());
		   
		   if(jobfield != null && aSubfield != null)
			{
				workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
			}
			else
			{
				workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
			}
			/*
			 * 
			 * Jobfield
			 * 
			 */
			if(jobfield!=null)
			{
				workingTable+=jobfield.getFieldName();
			}
			else if(!workList.get(c).getWorkJobFieldOth().equals(""))
			{
				workingTable+=workList.get(c).getWorkJobFieldOth();
			}
			
			/*
			 * 
			 * Subfield
			 * 
			 */
			
			if(aSubfield!=null)
			{
				workingTable+=" : "+aSubfield.getSubfieldName();
			}
			else if(!workList.get(c).getWorkSubFieldOth().equals(""))
			{
				workingTable+=" : "+workList.get(c).getWorkSubFieldOth();
			}
		   
		   if(workList.get(c).getSubordinate() > 0) 
		   {
			   workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_SUBORDINATE")+": </b>"+workList.get(c).getSubordinate();
		   }
		   com.topgun.shris.masterdata.JobType aJobbtype=MasterDataManager.getJobType(workList.get(c).getWorkJobType(), resume.getIdLanguage());
			if(aJobbtype!=null)
			{
				workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_TYPE")+":</b>"+aJobbtype.getTypeName();		
			}
			DecimalFormat aDecimal = new DecimalFormat( "###,###" );
			SalaryPer aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
			if(workList.get(c).getSalaryLast()>0)
			{
				workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
				if(workList.get(c).getIdCurrency() > 0)
				{
					if(workList.get(c).getIdCurrency()==140 && resume.getIdLanguage()==38)
					{
						workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
					}else{
						workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getCode();
					}
					aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
					if(aSalPer!=null)
					{
						String salPer=aSalPer.getName();
						workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
					}
				}
			}
			
			/*
			if(workList.get(c).getSalaryLast()>0)
			{
				workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_LASTEST_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryLast()).toString();
			}
			if(workList.get(c).getIdCurrency() > 0)
			{
				workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getName();
			}
			
			if(aSalPer!=null)
			{
				String salPer=aSalPer.getName();
				workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
			}
			*/
			if(!workList.get(c).getPositionStart().equals(""))
			{
				if(isTranslate!=true)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_POS")+": </b>"+workList.get(c).getPositionStart();
				}
				jobfield=MasterDataManager.getJobField(workList.get(c).getWorkJobFieldStart(), resume.getIdLanguage());
				aSubfield=MasterDataManager.getSubField(workList.get(c).getWorkJobFieldStart(),workList.get(c).getWorkSubFieldStart(),resume.getIdLanguage());
				
				if(jobfield != null && aSubfield != null)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"EQUIVALENT_MARKET_POSITION")+"</b>: ";
				}
				else
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_EQUIVALENT")+"</b>: ";
				}
				/*
				 * 
				 * Jobfield
				 * 
				 */
				if(jobfield!=null)
				{
					workingTable+=jobfield.getFieldName();
				}
				else if(!workList.get(c).getWorkJobFieldOthStart().equals(""))
				{
					workingTable+=workList.get(c).getWorkJobFieldOthStart();
				}
				
				/*
				 * 
				 * Subfield
				 * 
				 */
				
				if(aSubfield!=null)
				{
					workingTable+=" : "+aSubfield.getSubfieldName();
				}
				else if(!workList.get(c).getWorkSubFieldOthStart().equals(""))
				{
					workingTable+=" : "+workList.get(c).getWorkSubFieldOthStart();
				}
				
				if(workList.get(c).getSalaryStart()>0)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
					if(workList.get(c).getIdCurrencyStart() > 0)
					{
						if(workList.get(c).getIdCurrencyStart()==140 && resume.getIdLanguage()==38)
						{
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),"CURRENCY_BATH");
						}else{
							workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrencyStart()).getCode();
						}
						aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPerStart()), resume.getIdLanguage());
						if(aSalPer!=null)
						{
							String salPer=aSalPer.getName();
							workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
						}
					}
				}
				
				/*
				if(workList.get(c).getSalaryStart()>0)
				{
					workingTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"WORKEXP_START_SAL")+": </b>"+aDecimal.format(workList.get(c).getSalaryStart()).toString();
				}
				if(workList.get(c).getIdCurrency() > 0)
				{
					workingTable+=" "+MasterDataManager.getCurrency(workList.get(c).getIdCurrency()).getName();
				}
				aSalPer=MasterDataManager.getSalaryPer(Util.getInt(workList.get(c).getSalaryPer()), resume.getIdLanguage());
				if(aSalPer!=null)
				{
					String salPer=aSalPer.getName();
					workingTable+=" "+propMgr.getMessage(resume.getLocale(),salPer);
				}
				*/
			}			
			workingTable+="<br/></td></tr>";
		}
		workingTable=workingHeadTable+workingTable+"</table>";
	  } 
	 else  // no work exp
	 {
		startComment="<!--"; 
		endComment="-->";
	 }
		replaceAll(result,"#PREVIEW_PART_TIME#", propMgr.getMessage(resume.getLocale(),"PREVIEW_PART_TIME"));
		replaceAll(result,"#PART_TIME_DETAIL#", workingTable);
		replaceAll(result,"#BEGIN_PART_TIME#", startComment);
		replaceAll(result,"#END_PART_TIME#", endComment);
    }

	private void ReplaceStrength(Resume resume)
    {
		String strengthTable="";
		String startComment="";
		String endComment="";
		String othCompetenciesTitle="";
		String othCompetencies="";
		String locale= "en_TH";
		String strengthTopic = "Topgun Strengths";
        
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		
		if(resume.getFirstName()!=null && !resume.getFirstName().equals(""))
		{
			strengthTopic = resume.getFirstName()+"'s "+strengthTopic;
		}

		List<Strength> strengths=new StrengthManager().getAll(resume.getIdJsk(),resume.getIdResume());			
		if(strengths != null)
		{
			int no=0;
			for(int i=0; i<strengths.size(); i++)
			{
				if(Util.getInt(strengths.get(i).getStrengthOrder())>0  && Util.getInt(strengths.get(i).getStrengthOrder())!=9)
				{
					com.topgun.shris.masterdata.Strength strength=MasterDataManager.getStrength(strengths.get(i).getIdStrength(),resume.getIdLanguage());
					strengthTable+="<b>"+(no+1)+". ";
					if(strength!=null)
					{
						strengthTable+=Util.getStr(strength.getStrengthName());
					}
					else
					{
						if(isTranslate!=true)
						{
							strengthTable+=Util.getStr(strengths.get(i).getOthStrength());
						}
					}
					strengthTable+="</b><br/>";
					if(!Util.getStr(strengths.get(i).getStrengthReason()).equals("") && isTranslate!=true)
					{
						if(viewType==1){strengthTable+="<font color='#82363a'>";}
						strengthTable+=Util.getStr(strengths.get(i).getStrengthReason()).replace("\n", "<br/>")+"<br/>";
						if(viewType==1){strengthTable+="</font>";}
					}
					no++;
				}
				else
				{
					com.topgun.shris.masterdata.Strength strength=MasterDataManager.getStrength(strengths.get(i).getIdStrength(),resume.getIdLanguage());
					if(strength!=null)
					{
						if(othCompetencies=="")
						{
							othCompetencies+=Util.getStr(strength.getStrengthName());
						}
						else
						{
							othCompetencies+=", "+Util.getStr(strength.getStrengthName());
						}
					}
					else
					{
						if(isTranslate!=true)
						{
							if(othCompetencies=="")
							{
								othCompetencies+=Util.getStr(strengths.get(i).getOthStrength());
							}
							else
							{
								othCompetencies+=", "+Util.getStr(strengths.get(i).getOthStrength());
							}
						}
					}
				}
				if(othCompetencies!="")
				{
					othCompetenciesTitle="<b>"+propMgr.getMessage(locale, "OTHER_COMPETENCIES")+" : </b><br/>";
				}
				
			}
			replaceAll(result,"#STRENGTH_DETAIL#", strengthTable);
		}
		else 
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_RANKED_IN_ORDER#", propMgr.getMessage(locale, "PREVIEW_RANKED_IN_ORDER"));
		replaceAll(result,"#TOPGUN_STRENGTH_TOPIC#", strengthTopic);
		replaceAll(result,"#OTHER_COMPETENCIES#", othCompetenciesTitle+othCompetencies);
		replaceAll(result,"#BEGIN_STRENGTH#", startComment);
		replaceAll(result,"#END_STRENGTH#", endComment);
    }
	
	private void ReplaceStrengthHideOther(Resume resume)
    {
		String strengthTable="";
		String startComment="";
		String endComment="";
		String othCompetenciesTitle="";
		String othCompetencies="";
		String locale= "en_TH";
		String strengthTopic = "Topgun Strengths";
        
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		
		if(resume.getFirstName()!=null && !resume.getFirstName().equals(""))
		{
			strengthTopic = resume.getFirstName()+"'s "+strengthTopic;
		}

		List<Strength> strengths=new StrengthManager().getAll(resume.getIdJsk(),resume.getIdResume());			
		if(strengths != null)
		{
			int no=0;
			boolean isOther = false;
			for(int i=0; i<strengths.size(); i++)
			{
				isOther = false;
				if(Util.getInt(strengths.get(i).getStrengthOrder())>0  && Util.getInt(strengths.get(i).getStrengthOrder())!=9)
				{
					com.topgun.shris.masterdata.Strength strength=MasterDataManager.getStrength(strengths.get(i).getIdStrength(),resume.getIdLanguage());
					strengthTable+="<b>"+(no+1)+". ";
					if(strength!=null)
					{
						strengthTable+=Util.getStr(strength.getStrengthName());
					}
					else
					{
						isOther = true;
						if(isTranslate!=true)
						{
							//strengthTable+=Util.getStr(strengths.get(i).getOthStrength());
							strengthTable+="<text style='font-weight: normal'>"+viewInOriginal+"</text>";
						}
					}
					strengthTable+="</b><br>";
					if(!Util.getStr(strengths.get(i).getStrengthReason()).equals("") && isTranslate!=true)
					{
						if(viewType==1){strengthTable+="<font color='#82363a'>";}
						if(isOther)
						{
							//strengthTable+=viewInOriginal+"<br/>";
						}
						/*if(!isOther)
						{
							strengthTable+=Util.getStr(strengths.get(i).getStrengthReason()).replace("\n", "<br/>")+"<br/>";
						}
						else
						{
							strengthTable+=viewInOriginal+"<br/>";
						}*/
						if(viewType==1){strengthTable+="</font>";}
					}
					no++;
				}
				else
				{
					com.topgun.shris.masterdata.Strength strength=MasterDataManager.getStrength(strengths.get(i).getIdStrength(),resume.getIdLanguage());
					if(strength!=null)
					{
						if(othCompetencies=="")
						{
							othCompetencies+=Util.getStr(strength.getStrengthName());
							//othCompetencies+=viewInOriginal;
						}
						else
						{
							othCompetencies+=", "+Util.getStr(strength.getStrengthName());
							//othCompetencies+=", "+viewInOriginal;
						}
					}
					else
					{
						if(isTranslate!=true)
						{
							if(othCompetencies=="")
							{
								othCompetencies+=viewInOriginal;
								//othCompetencies+=Util.getStr(strengths.get(i).getOthStrength());
							}
							else
							{
								othCompetencies+=", "+viewInOriginal;
								//othCompetencies+=", "+Util.getStr(strengths.get(i).getOthStrength());
							}
						}
					}
				}
				if(othCompetencies!="")
				{
					othCompetenciesTitle="<b>"+propMgr.getMessage(locale, "OTHER_COMPETENCIES")+" : </b><br/>";
				}
				
			}
			replaceAll(result,"#STRENGTH_DETAIL#", strengthTable);
		}
		else 
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_RANKED_IN_ORDER#", propMgr.getMessage(locale, "PREVIEW_RANKED_IN_ORDER"));
		replaceAll(result,"#TOPGUN_STRENGTH_TOPIC#", strengthTopic);
		replaceAll(result,"#OTHER_COMPETENCIES#", othCompetenciesTitle+othCompetencies);
		replaceAll(result,"#BEGIN_STRENGTH#", startComment);
		replaceAll(result,"#END_STRENGTH#", endComment);
    }
	
	private void ReplaceStrengthNotAllPart(Resume resume)
    {
		List<Strength> strengths=new StrengthManager().getAll(resume.getIdJsk(),resume.getIdResume());	
		String strengthTable="";
		String startComment="";
		String endComment="";
		String othCompetenciesTitle="";
		String othCompetencies="";
		String strengthTopic = "Topgun Strengths";
        
		if(resume.getFirstName()!=null && !resume.getFirstName().equals(""))
		{
			strengthTopic = resume.getFirstName()+"'s "+strengthTopic;
		}
		if(strengths != null)
		{
			int no=0;
			for(int i=0;i<strengths.size();i++)
			{
				if(Util.getInt(strengths.get(i).getStrengthOrder())>0  && Util.getInt(strengths.get(i).getStrengthOrder())!=9)
				{
					com.topgun.shris.masterdata.Strength strength=MasterDataManager.getStrength(strengths.get(i).getIdStrength(),resume.getIdLanguage());
					strengthTable+="<b>"+(no+1)+". ";
					if(strength!=null)
					{
						strengthTable+=Util.getStr(strength.getStrengthName());
					}
					else
					{
						strengthTable+=Util.getStr(strengths.get(i).getOthStrength());
					}
					strengthTable+="</b><br/>";
					if(!Util.getStr(strengths.get(i).getStrengthReason()).equals("") && isTranslate!=true)
					{
						strengthTable+=Util.getStr(strengths.get(i).getStrengthReason()).replace("\n", "<br/>")+"<br/>";
					}
					no++;
				}
				else
				{
					com.topgun.shris.masterdata.Strength strength=MasterDataManager.getStrength(strengths.get(i).getIdStrength(),resume.getIdLanguage());
					if(strength!=null)
					{
						if(othCompetencies=="")
						{
							othCompetencies+=Util.getStr(strength.getStrengthName());
						}
						else
						{
							othCompetencies+=", "+Util.getStr(strength.getStrengthName());
						}
					}
					else
					{
						if(isTranslate!=true)
						{
							if(othCompetencies=="")
							{
								othCompetencies+=Util.getStr(strengths.get(i).getOthStrength());
							}
							else
							{
								othCompetencies+=", "+Util.getStr(strengths.get(i).getOthStrength());
							}
						}
					}
				}
				if(othCompetencies!="")
				{
					othCompetenciesTitle="<b>"+propMgr.getMessage(resume.getLocale(), "OTHER_COMPETENCIES")+" : </b><br/>";
				}
			}				
			replaceAll(result,"#STRENGTH_DETAIL#", strengthTable);
		}
		else 
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_RANKED_IN_ORDER#", propMgr.getMessage(resume.getLocale(), "PREVIEW_RANKED_IN_ORDER"));
		replaceAll(result,"#TOPGUN_STRENGTH_TOPIC#", strengthTopic);
		replaceAll(result,"#OTHER_COMPETENCIES#", othCompetenciesTitle+othCompetencies);
		replaceAll(result,"#BEGIN_STRENGTH#", startComment);
		replaceAll(result,"#END_STRENGTH#", endComment);
    }
	
	private void ReplaceAptitude(Resume resume)
    {
			String startComment="";
			String endComment="";
			String aptitudeTopic = "Topgun Aptitudes and Hidden Competencies";
			
			if(resume.getFirstName()!=null && !resume.getFirstName().equals(""))
			{
				aptitudeTopic = resume.getFirstName()+"'s "+aptitudeTopic;
			}
			List<Hobby> hobbyRanked=new HobbyManager().getAllRank(resume.getIdJsk(),resume.getIdResume());
			List<Hobby> hobbyListening=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 1);
		    
			List<Hobby> hobbyThaiIns= null;
		    if(resume.getTemplateIdCountry() == 110) // Japanese
		    {
		    	hobbyThaiIns=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 11);
		    }
		    else 
		    {
		    	hobbyThaiIns=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 2);
		    }
		    List<Hobby> hobbyStrings=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 3);
		    List<Hobby> hobbyPercussion=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 4);
		    List<Hobby> hobbyWoodwinds=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 5);
		    List<Hobby> hobbyBrass=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 6);
		    List<Hobby> hobbyKeyboards=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 7);
		    List<Hobby> hobbySports=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 8);
		    List<Hobby> hobbyOutdoor=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 9);
		    List<Hobby> hobbyOther=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 10);
		    List<Reading> readBook=new ReadingManager().getAll(resume.getIdJsk(),resume.getIdResume());
		    List<Pet> jskPet=new PetManager().getAll(resume.getIdJsk(),resume.getIdResume());
		    List <Travel> jskTravel=TravelManager.getAll(resume.getIdJsk(),resume.getIdResume());
		    
		    String hobbyTable="";
		    String hobbyHeadTable="";
		    String hobbyTable_1="";
		    String hobbyTable_2="";
		    String hobbyTable_3="";
		    String hobbyTable_4="";
		    String hobbyTable_5="";
		    String hobbyTable_6="";
		    String hobbyTable_7="";
		    String hobbyTable_8="";
		    String hobbyTable_9="";
		    String hobbyTable_9_1="";
		    String hobbyTable_9_2="";
		    String hobbyTable_9_3="";
		    String hobbyTable_9_4="";
		    String rankedTable="";
		    String head_hobbyTable_8="";
		    String head_hobbyTable_9="";
		    
		    hobbyHeadTable="<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class =\"f11\">";
		    String locale= "en_TH";
	        
		    if(chkLocation(resume.getLocale()))
		    {
		    	locale= resume.getLocale();
		    }

		    
		    if(hobbyListening.size()>0 || hobbyThaiIns.size()>0 || hobbyStrings.size()>0 || hobbyPercussion.size()>0 || hobbyWoodwinds.size()>0 || hobbyBrass.size()>0 || hobbyKeyboards.size()>0 )
		    {
				hobbyTable+="<tr><td><b>"+propMgr.getMessage(locale,"APTITUDE_MUSIC")+":</b></td><td><span>&nbsp;</span></td><td ><span>&nbsp;</span></td></tr>";
				if(hobbyListening.size()>0)
				{
					hobbyTable+="<tr><td width='110'>"+propMgr.getMessage(locale, "APTITUDE_LISTENING")+":</td><td colspan=\"2\">";
					for(int c=0;c<hobbyListening.size();c++)
					{  
						com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyListening.get(c).getIdHobby(), resume.getIdLanguage());
						if(c>0)
						{
							hobbyTable_1+=", ";
						}
						if(hobby!=null)
						{
							hobbyTable_1+=" "+hobby.getHobbyName();
						}
						else  
						{
							hobbyTable_1+=" "+hobbyListening.get(c).getOthHobby();
						}
					}
					hobbyTable=hobbyTable+hobbyTable_1+"</td></tr>";
               }
               if(hobbyThaiIns.size()>0 || hobbyStrings.size()>0 || hobbyPercussion.size()>0|| hobbyWoodwinds.size()>0 || hobbyBrass.size()>0 || hobbyKeyboards.size()>0 )
               {
            	   hobbyTable+="<tr><td>"+propMgr.getMessage(locale,"APTITUDE_PLAYING")+":</td><td><span>&nbsp;</span></td><td><span>&nbsp;</span></td></tr>";                  
            	     if(hobbyThaiIns.size()>0)
                     {
                  	   for(int c=0;c<hobbyThaiIns.size();c++)
                  	   {
                  		   hobbyTable_2+="<tr><td valign=\"top\" width='110'>";
                  		   if(c == 0)
                  		   {
                  			   if(resume.getTemplateIdCountry()== 110)
                  			   {
                  				   hobbyTable_2+="- "+propMgr.getMessage(locale,"APTITUDE_JAPAN_INSTUMENTS")+":";
                  			   }
                  			   else 
                  			   {
                  				   hobbyTable_2+="- "+propMgr.getMessage(locale,"APTITUDE_THAI_INSTRUMENTS")+":";
                  			   }
                  		   }
                  		   hobbyTable_2+="</td><td  width=\"95\">";
                  		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyThaiIns.get(c).getIdHobby(), resume.getIdLanguage());
                  		   if(hobby!=null)
                  		   {
                  			   hobbyTable_2+=" "+hobby.getHobbyName();
                  		   }
                  		   else  
                  		   {
                  			   hobbyTable_2+=" "+hobbyThaiIns.get(c).getOthHobby();
                           }
                  		   hobbyTable_2+="</td><td>";
                  		   if(Util.getInt(hobbyThaiIns.get(c).getSkill(),0) != 0)
                  		   {
                  			   String skill=proficiencyComAndSkill(Util.getInt(hobbyThaiIns.get(c).getSkill()));
            				   hobbyTable_2+=propMgr.getMessage(locale,skill);
                  		   }
                  		   hobbyTable_2+="</td></tr>";
                  	     }    
                     }
            	   
            	   if(hobbyStrings.size()>0)
                   {
                	   for(int c=0;c<hobbyStrings.size();c++)
                	   {
                		   hobbyTable_2+="<tr><td valign=\"top\" width='110'>";
                		   if(c == 0)
                		   {
                			   hobbyTable_2+="- "+propMgr.getMessage(locale,"APTITUDE_STRINGS")+":";
                		   }
                		   hobbyTable_2+="</td><td width=\"95\">";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyStrings.get(c).getIdHobby(), resume.getIdLanguage());
                		   if(hobby!=null)
                		   {
                			   hobbyTable_2+=" "+hobby.getHobbyName();
                		   }
                		   else
                		   {	 
                			   hobbyTable_2+=" "+hobbyStrings.get(c).getOthHobby();
                           }
                		   hobbyTable_2+="</td><td>";
                		   if(Util.getInt(hobbyStrings.get(c).getSkill(),0) != 0)
                		   {
                 			   	String skill=proficiencyComAndSkill(Util.getInt(hobbyStrings.get(c).getSkill()));
                 			   	hobbyTable_2+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_2+="</td></tr>";
                	   }    
                   }
                   if(hobbyPercussion.size()>0)
                   {
                	   for(int c=0;c<hobbyPercussion.size();c++)
                	   {
                		   hobbyTable_3+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_3+="- "+propMgr.getMessage(locale,"APTITUDE_PERCUSSION")+":";
                		   }
                		   hobbyTable_3+="</td><td width=\"95\">";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyPercussion.get(c).getIdHobby(), resume.getIdLanguage());
                		   if(hobby!=null)
                		   {
                			   hobbyTable_3+=" "+hobby.getHobbyName();
                		   }
                		   else
                		   {
                			   hobbyTable_3+=" "+hobbyPercussion.get(c).getOthHobby();
                		   }
                		   hobbyTable_3+="</td><td>";
                		   if(Util.getInt(hobbyPercussion.get(c).getSkill(),0)!=0)
                		   {
                			   	String skill=proficiencyComAndSkill(Util.getInt(hobbyPercussion.get(c).getSkill()));
                			   	hobbyTable_3+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_3+="</td></tr>";
                	   }
                   }
                   if(hobbyWoodwinds.size()>0)
                   {
                	   for(int c=0;c<hobbyWoodwinds.size();c++)
                	   {
                		   hobbyTable_4+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_4+=propMgr.getMessage(locale,"APTITUDE_WOODWINDS");
                		   }
                		   hobbyTable_4+="</td><td width=\"95\">";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyWoodwinds.get(c).getIdHobby(), resume.getIdLanguage());
                		   if(hobby!=null)
                		   {
                			   hobbyTable_4+=" "+hobby.getHobbyName();
                		   }
                		   else 
                		   {
                			   hobbyTable_4+=" "+hobbyWoodwinds.get(c).getOthHobby();
                		   }
                		   hobbyTable_4+="</td><td>";
                		   if(Util.getInt(hobbyWoodwinds.get(c).getSkill(),0)!=0)
                		   {
               			   		String skill=proficiencyComAndSkill(Util.getInt(hobbyWoodwinds.get(c).getSkill()));
               			   		hobbyTable_4+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_4+="</td></tr>";
                	   }
                   }
                   if(hobbyBrass.size()>0)
                   {
                	   for(int c=0;c<hobbyBrass.size();c++)
                	   {
                		   hobbyTable_5+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_5+=propMgr.getMessage(locale,"APTITUDE_BRASS");
                		   }
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyBrass.get(c).getIdHobby(),resume.getIdLanguage());
                		   hobbyTable_5+="</td><td width=\"95\">";
                		   if(hobby!=null)
                		   {
                			   hobbyTable_5+=" "+hobby.getHobbyName();
                		   }
                		   else 
                		   {
                			   hobbyTable_5+=" "+hobbyBrass.get(c).getOthHobby();
                		   }
                		   hobbyTable_5+="</td><td>";
                		   if(Util.getInt(hobbyBrass.get(c).getSkill(),0)!=0)
                		   {
              			   		String skill=proficiencyComAndSkill(Util.getInt(hobbyBrass.get(c).getSkill()));
              			   		hobbyTable_5+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_5+="</td></tr>";
                	   }
                   }
         
               if(hobbyKeyboards.size()>0)
               {
            	   for(int c=0;c<hobbyKeyboards.size();c++)
            	   {
            		   hobbyTable_6+="<tr><td valign=\"top\" width='110'>";
            		   if(c==0)
            		   {
            			   hobbyTable_6+=propMgr.getMessage(locale,"APTITUDE_KEYBOARDS");
            		   }
            		   hobbyTable_6+="</td><td width=\"95\">";
            		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyKeyboards.get(c).getIdHobby(), resume.getIdLanguage());
            		   if(hobby!=null)
            		   {
            			   hobbyTable_6+=" "+hobby.getHobbyName();
            		   }
            		   else
            		   {
            			   hobbyTable_6+=" "+hobbyKeyboards.get(c).getOthHobby();
            		   }
            		   hobbyTable_6+="</td><td>";
            		   if(Util.getInt(hobbyKeyboards.get(c).getSkill(),0) != 0)
            		   {
            			   	String skill=proficiencyComAndSkill(Util.getInt(hobbyKeyboards.get(c).getSkill()));
            			   	hobbyTable_6+=propMgr.getMessage(locale,skill);
            		   }
            		   hobbyTable_6+="</td></tr>";
            	   }
               } 
		    }
    }
		    
	if(hobbySports.size()>0)
	{
		for(int c=0;c<hobbySports.size();c++)
		{
			hobbyTable_7+="<tr><td valign=\"top\" width='110'>";
			if(c==0)
			{
				hobbyTable_7+="<b>"+propMgr.getMessage(locale,"APTITUDE_SPORTS")+":</b>";
			}
			hobbyTable_7+="</td><td width=\"95\">";
			com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbySports.get(c).getIdHobby(), resume.getIdLanguage());
			if(hobby!=null)
			{
				hobbyTable_7+=" "+hobby.getHobbyName();
			}
			else
			{
				hobbyTable_7+=" "+hobbySports.get(c).getOthHobby();
			}
			hobbyTable_7+="</td><td>";
			if(hobbySports.get(c).getSkill() != 0)
			{
				String skill=proficiencyComAndSkill(Util.getInt(hobbySports.get(c).getSkill()));
				hobbyTable_7+=propMgr.getMessage(locale,skill);
			}
			hobbyTable_7+="</td></tr>";
		}
	}
	if(hobbyOutdoor.size()>0)
	{
		head_hobbyTable_8+="<tr><td valign=\"top\" colspan=3><b>"+propMgr.getMessage(locale,"APTITUDE_OUTDOOR_ADVENTURE")+":</b></td></tr>";
		for(int c=0;c<hobbyOutdoor.size();c++)
		{
			hobbyTable_8+="<tr><td valign=\"top\" width='110'><span>&nbsp;</span>";
			hobbyTable_8+="</td><td width=\"95\">";
			com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyOutdoor.get(c).getIdHobby(), resume.getIdLanguage());
			if(hobby!=null)
			{
				hobbyTable_8+=" "+hobby.getHobbyName();
			}
			else
			{
				hobbyTable_8+=" "+hobbyOutdoor.get(c).getOthHobby();
			}
			hobbyTable_8+="</td><td>";
			if(hobbyOutdoor.get(c).getSkill() != 0)
			{
				String skill=proficiencyComAndSkill(Util.getInt(hobbyOutdoor.get(c).getSkill()));
				hobbyTable_8+=propMgr.getMessage(locale,skill);
			}
			hobbyTable_8+="</td></tr>";
		}
	}
        
    if(hobbyOther.size()>0)
    {   
    	head_hobbyTable_9+="<tr><td valign=\"top\" colspan=3><b>"+propMgr.getMessage(locale,"APTITUDE_OTHER_HOBBIES")+":</b></td></tr>";
    	for(int c=0;c<hobbyOther.size();c++)
    	{
    	   hobbyTable_9+="<tr><td valign=\"top\" width='110'><span>&nbsp;</span>";
    	   hobbyTable_9+="</td><td valign=\"top\" width=\"95\">";
    	   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyOther.get(c).getIdHobby(), resume.getIdLanguage());
    	   if(hobby!=null)
    	   {
    		   hobbyTable_9+=" "+hobby.getHobbyName();
    	   }
    	   else
    	   {
    		   hobbyTable_9+=" "+hobbyOther.get(c).getOthHobby();
    	   }
    	   hobbyTable_9+="</td><td>";
    	   hobbyTable_9_1="";
    	   hobbyTable_9_2="";
    	   hobbyTable_9_3="";
    	   hobbyTable_9_4="";
    	   if(readBook != null && hobbyOther.get(c).getIdHobby() == 45 )
    	   {  
    		   for(int r=0;r<readBook.size();r++)
    		   {
    			   com.topgun.shris.masterdata.ReadingBook aBook=MasterDataManager.getReadingBook(readBook.get(r).getIdBook(),  resume.getIdLanguage());
    			   if(r > 0)
    			   {
    				   hobbyTable_9_1+=", ";
    			   }
    			   if(aBook!=null)
    			   {
        			   hobbyTable_9_1+=aBook.getBookName(); 
    			   }
    			   else
    			   {
    				   if(isTranslate!=true)
    				   {
    					    hobbyTable_9_1+=readBook.get(r).getOtherBook();
    				   }
    			   }
    		   }
    	   }
    	   else if(jskPet != null && hobbyOther.get(c).getIdHobby() == 56 )
    	   {
    		   for(int r=0;r<jskPet.size();r++)
    		   {
    			   com.topgun.shris.masterdata.Pet aPet=MasterDataManager.getPet(jskPet.get(r).getIdPet(),  resume.getIdLanguage());
    			   if(r > 0)
    			   {
    				   hobbyTable_9_2+=", ";
    			   }
    			   if(aPet!=null)
    			   {
    				   hobbyTable_9_2+=aPet.getPetName(); 
    			   }
    			   else
    			   {
    				   hobbyTable_9_2+=jskPet.get(r).getOtherPet();
    			   }
    		   }
    	   }
    	   else if(jskTravel != null && hobbyOther.get(c).getIdHobby() == 43 )
    	   {
    		   for(int r=0;r<jskTravel.size();r++)
    		   {
    			   com.topgun.shris.masterdata.Travel aTavel=MasterDataManager.getTravel(jskTravel.get(r).getIdTravel(), resume.getIdLanguage());
    			   com.topgun.shris.masterdata.TravelFreq aFreq=MasterDataManager.getTravelFreq(jskTravel.get(r).getIdFrequency(), resume.getIdLanguage());
    			   if(r > 0)
    			   {
    				   hobbyTable_9_3+="<br/>";
    			   }
    			   if(aTavel!=null && aFreq!=null)
    			   {
    				   hobbyTable_9_3+=aTavel.getTravelName()+" "+aFreq.getFrequencyName(); 
    				   
    			   }
    		   }
    	   }
    	   else 
    	   {
    		   if(hobbyOther.get(c).getSkill() != 0)
    		   {
   					String skill=proficiencyComAndSkill(Util.getInt(hobbyOther.get(c).getSkill()));
   					hobbyTable_9_4+=propMgr.getMessage(locale,skill);
    		   }
    	   }
    	   hobbyTable_9=hobbyTable_9+hobbyTable_9_1+hobbyTable_9_2+hobbyTable_9_3+hobbyTable_9_4+"</td></tr>";
       }

    }
    hobbyTable=hobbyHeadTable+hobbyTable+hobbyTable_2+hobbyTable_3+hobbyTable_4+hobbyTable_5+hobbyTable_6+hobbyTable_7+head_hobbyTable_8+hobbyTable_8+head_hobbyTable_9+hobbyTable_9+"</table>";
    if(hobbyRanked.size()>0)
    {
    	/*
    	for(int no=1;no<=6;no++)
    	{
    		for(int i=0;i<hobbyRanked.size();i++)
    		{
    			if(hobbyRanked.get(i).getHobbyOrder()==no)
    			{
    				com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyRanked.get(i).getIdHobby(), resume.getIdLanguage());
    				rankedTable+=no+". ";
    				if(hobby!=null)
            		{
            			rankedTable+=Util.getStr(hobby.getHobbyName())+"<span>&nbsp;</span><span>&nbsp;</span>";
            		}
            		else 
            		{
            			rankedTable+=hobbyRanked.get(i).getOthHobby()+"<span>&nbsp;</span><span>&nbsp;</span>";
            		}
        			break;
    			}
    		}
    	}
    	*/
    	for(int i=0 ;i<hobbyRanked.size() ; i++)
    	{
    		com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyRanked.get(i).getIdHobby(), resume.getIdLanguage());
			rankedTable+=(i+1)+". ";
			if(hobby!=null)
    		{
    			rankedTable+=Util.getStr(hobby.getHobbyName())+"<span>&nbsp;</span><span>&nbsp;</span>";
    		}
    		else 
    		{
    			rankedTable+=hobbyRanked.get(i).getOthHobby()+"<span>&nbsp;</span><span>&nbsp;</span>";
    		}
    	}
    }
    else 
    {
    	
		startComment="<!--";
		endComment="-->";
		
	}
	replaceAll(result,"#BEGIN_APTITUDE#", startComment);
	replaceAll(result,"#END_APTITUDE#", endComment);
	replaceAll(result,"#APTITUDE_DETAIL#", hobbyTable);
	replaceAll(result,"#TOPGUN_APTITUDE_TOPIC#", aptitudeTopic);
	replaceAll(result,"#PREVIEW_RANKED_FAVOURITE_HOBBY#", propMgr.getMessage(locale, "PREVIEW_RANKED_FAVOURITE_HOBBY"));
	replaceAll(result,"#PREVIEW_MAX_TOP_5#", propMgr.getMessage(locale, "PREVIEW_MAX_TOP_5"));
	replaceAll(result,"#APTITUDE_RANKED#", rankedTable);
    }
	
	private void ReplaceAptitudeHideOthers(Resume resume)
    {
			String startComment="";
			String endComment="";
			String aptitudeTopic = "Topgun Aptitudes and Hidden Competencies";
			
			if(resume.getFirstName()!=null && !resume.getFirstName().equals(""))
			{
				aptitudeTopic = resume.getFirstName()+"'s "+aptitudeTopic;
			}
			List<Hobby> hobbyRanked=new HobbyManager().getAllRank(resume.getIdJsk(),resume.getIdResume());
			List<Hobby> hobbyListening=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 1);
		    
			List<Hobby> hobbyThaiIns= null;
		    if(resume.getTemplateIdCountry() == 110) // Japanese
		    {
		    	hobbyThaiIns=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 11);
		    }
		    else 
		    {
		    	hobbyThaiIns=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 2);
		    }
		    List<Hobby> hobbyStrings=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 3);
		    List<Hobby> hobbyPercussion=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 4);
		    List<Hobby> hobbyWoodwinds=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 5);
		    List<Hobby> hobbyBrass=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 6);
		    List<Hobby> hobbyKeyboards=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 7);
		    List<Hobby> hobbySports=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 8);
		    List<Hobby> hobbyOutdoor=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 9);
		    List<Hobby> hobbyOther=new HobbyManager().getByGroup(resume.getIdJsk(), resume.getIdResume(), 10);
		    List<Reading> readBook=new ReadingManager().getAll(resume.getIdJsk(),resume.getIdResume());
		    List<Pet> jskPet=new PetManager().getAll(resume.getIdJsk(),resume.getIdResume());
		    List <Travel> jskTravel=TravelManager.getAll(resume.getIdJsk(),resume.getIdResume());
		    
		    String hobbyTable="";
		    String hobbyHeadTable="";
		    String hobbyTable_1="";
		    String hobbyTable_2="";
		    String hobbyTable_3="";
		    String hobbyTable_4="";
		    String hobbyTable_5="";
		    String hobbyTable_6="";
		    String hobbyTable_7="";
		    String hobbyTable_8="";
		    String hobbyTable_9="";
		    String hobbyTable_9_1="";
		    String hobbyTable_9_2="";
		    String hobbyTable_9_3="";
		    String hobbyTable_9_4="";
		    String rankedTable="";
		    String head_hobbyTable_8="";
		    String head_hobbyTable_9="";
		    
		    hobbyHeadTable="<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class =\"f11\">";
		    String locale= "en_TH";
	        
		    if(chkLocation(resume.getLocale()))
		    {
		    	locale= resume.getLocale();
		    }

		    
		    if(hobbyListening.size()>0 || hobbyThaiIns.size()>0 || hobbyStrings.size()>0 || hobbyPercussion.size()>0 || hobbyWoodwinds.size()>0 || hobbyBrass.size()>0 || hobbyKeyboards.size()>0 )
		    {
				hobbyTable+="<tr><td><b>"+propMgr.getMessage(locale,"APTITUDE_MUSIC")+":</b></td><td><span>&nbsp;</span></td><td><span>&nbsp;</span></td></tr>";
				if(hobbyListening.size()>0)
				{
					hobbyTable+="<tr><td width='110'>"+propMgr.getMessage(locale, "APTITUDE_LISTENING")+":</td><td colspan=\"2\">";
					for(int c=0;c<hobbyListening.size();c++)
					{  
						com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyListening.get(c).getIdHobby(), resume.getIdLanguage());
						if(c>0)
						{
							hobbyTable_1+=", ";
						}
						if(hobby!=null)
						{
							hobbyTable_1+=" "+hobby.getHobbyName();
						}
						else  
						{
							hobbyTable_1+=" "+viewInOriginal;
							//hobbyTable_1+=" "+hobbyListening.get(c).getOthHobby();
						}
					}
					hobbyTable=hobbyTable+hobbyTable_1+"</td></tr>";
               }
               if(hobbyThaiIns.size()>0 || hobbyStrings.size()>0 || hobbyPercussion.size()>0|| hobbyWoodwinds.size()>0 || hobbyBrass.size()>0 || hobbyKeyboards.size()>0 )
               {
            	   hobbyTable+="<tr><td>"+propMgr.getMessage(locale,"APTITUDE_PLAYING")+":</td><td><span>&nbsp;</span></td><td><span>&nbsp;</span></td></tr>";                  
            	     if(hobbyThaiIns.size()>0)
                     {
                  	   for(int c=0;c<hobbyThaiIns.size();c++)
                  	   {
                  		   hobbyTable_2+="<tr><td valign=\"top\" width='110'>";
                  		   if(c == 0)
                  		   {
                  			   if(resume.getTemplateIdCountry()== 110)
                  			   {
                  				   hobbyTable_2+="- "+propMgr.getMessage(locale,"APTITUDE_JAPAN_INSTUMENTS")+":";
                  			   }
                  			   else 
                  			   {
                  				   hobbyTable_2+="- "+propMgr.getMessage(locale,"APTITUDE_THAI_INSTRUMENTS")+":";
                  			   }
                  		   }
                  		   hobbyTable_2+="</td>";
                  		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyThaiIns.get(c).getIdHobby(), resume.getIdLanguage());
                  		   if(hobby!=null)
                  		   {
                  			   hobbyTable_2+="<td  width=\"95\">";
                  			   hobbyTable_2+=" "+hobby.getHobbyName();
                  			   hobbyTable_2+="</td>";
                  			   hobbyTable_2+="<td>";
                  			   if(Util.getInt(hobbyThaiIns.get(c).getSkill(),0) != 0)
                    		   {
                    			   String skill=proficiencyComAndSkill(Util.getInt(hobbyThaiIns.get(c).getSkill()));
                    			   hobbyTable_2+=propMgr.getMessage(locale,skill);
                    		   }
                  			 hobbyTable_2+="</td>";
                  		   }
                  		   else  
                  		   {
                  			   hobbyTable_2+="<td colspan='2'>";
                  			   hobbyTable_2+=" "+viewInOriginal;
                  			   hobbyTable_2+="</td>";
                  			   //hobbyTable_2+=" "+hobbyThaiIns.get(c).getOthHobby();
                           }
                  		   /*
                  		   hobbyTable_2+="</td><td>";
                  		   if(Util.getInt(hobbyThaiIns.get(c).getSkill(),0) != 0)
	              		   {
	              			   String skill=proficiencyComAndSkill(Util.getInt(hobbyThaiIns.get(c).getSkill()));
	              			   hobbyTable_2+=propMgr.getMessage(locale,skill);
	              		   }
                  		   hobbyTable_2+="</td></tr>";
                  		   */
                  		   hobbyTable_2+="</tr>";
                  	     }    
                     }
            	   
            	   if(hobbyStrings.size()>0)
                   {
                	   for(int c=0;c<hobbyStrings.size();c++)
                	   {
                		   /*
                		   hobbyTable_2+="<tr><td valign=\"top\" width='110'>";
                		   if(c == 0)
                		   {
                			   hobbyTable_2+="- "+propMgr.getMessage(locale,"APTITUDE_STRINGS")+":";
                		   }
                		   hobbyTable_2+="</td><td width=\"95\">";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyStrings.get(c).getIdHobby(), resume.getIdLanguage());
                		   if(hobby!=null)
                		   {
                			   hobbyTable_2+=" "+hobby.getHobbyName();
                		   }
                		   else
                		   {	 
                			   hobbyTable_2+=" "+viewInOriginal;
                			   //hobbyTable_2+=" "+hobbyStrings.get(c).getOthHobby();
                           }
                		   hobbyTable_2+="</td><td>";
                		   if(Util.getInt(hobbyStrings.get(c).getSkill(),0) != 0)
                		   {
                 			   	String skill=proficiencyComAndSkill(Util.getInt(hobbyStrings.get(c).getSkill()));
                 			   	hobbyTable_2+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_2+="</td></tr>";
                		   */
                		   hobbyTable_2+="<tr><td valign=\"top\" width='110'>";
                		   if(c == 0)
                		   {
                			   hobbyTable_2+="- "+propMgr.getMessage(locale,"APTITUDE_STRINGS")+":";
                		   }
                  		   hobbyTable_2+="</td>";
                  		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyStrings.get(c).getIdHobby(), resume.getIdLanguage());
                  		   if(hobby!=null)
                  		   {
                  			   hobbyTable_2+="<td  width=\"95\">";
                  			   hobbyTable_2+=" "+hobby.getHobbyName();
                  			   hobbyTable_2+="</td>";
                  			   hobbyTable_2+="<td>";
                  			   if(Util.getInt(hobbyStrings.get(c).getSkill(),0) != 0)
                    		   {
                    			   String skill=proficiencyComAndSkill(Util.getInt(hobbyStrings.get(c).getSkill()));
                    			   hobbyTable_2+=propMgr.getMessage(locale,skill);
                    		   }
                  			 hobbyTable_2+="</td>";
                  		   }
                  		   else  
                  		   {
                  			   hobbyTable_2+="<td colspan='2'>";
                  			   hobbyTable_2+=" "+viewInOriginal;
                  			   hobbyTable_2+="</td>";
                           }
                	   }    
                   }
                   if(hobbyPercussion.size()>0)
                   {
                	   for(int c=0;c<hobbyPercussion.size();c++)
                	   {
                		   /*
                		   hobbyTable_3+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_3+="- "+propMgr.getMessage(locale,"APTITUDE_PERCUSSION")+":";
                		   }
                		   hobbyTable_3+="</td><td width=\"95\">";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyPercussion.get(c).getIdHobby(), resume.getIdLanguage());
                		   if(hobby!=null)
                		   {
                			   hobbyTable_3+=" "+hobby.getHobbyName();
                		   }
                		   else
                		   {
                			   hobbyTable_3+=" "+viewInOriginal;
                			   //hobbyTable_3+=" "+hobbyPercussion.get(c).getOthHobby();
                		   }
                		   hobbyTable_3+="</td><td>";
                		   if(Util.getInt(hobbyPercussion.get(c).getSkill(),0)!=0)
                		   {
                			   	String skill=proficiencyComAndSkill(Util.getInt(hobbyPercussion.get(c).getSkill()));
                			   	hobbyTable_3+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_3+="</td></tr>";
                		   */
                		   
                		   hobbyTable_3+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_3+="- "+propMgr.getMessage(locale,"APTITUDE_PERCUSSION")+":";
                		   }
                		   hobbyTable_3+="</td>";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyPercussion.get(c).getIdHobby(), resume.getIdLanguage());
                		   if(hobby!=null)
                  		   {
                  			   hobbyTable_3+="<td  width=\"95\">";
                  			   hobbyTable_3+=" "+hobby.getHobbyName();
                  			   hobbyTable_3+="</td>";
                  			   hobbyTable_3+="<td>";
                  			   if(Util.getInt(hobbyPercussion.get(c).getSkill(),0) != 0)
                    		   {
                    			   String skill=proficiencyComAndSkill(Util.getInt(hobbyPercussion.get(c).getSkill()));
                    			   hobbyTable_3+=propMgr.getMessage(locale,skill);
                    		   }
                  			 hobbyTable_3+="</td>";
                  		   }
                  		   else  
                  		   {
                  			   hobbyTable_3+="<td colspan='2'>";
                  			   hobbyTable_3+=" "+viewInOriginal;
                  			   hobbyTable_3+="</td>";
                           }
                		   hobbyTable_3+="</td></tr>";
                		   
                	   }
                   }
                   if(hobbyWoodwinds.size()>0)
                   {
                	   for(int c=0;c<hobbyWoodwinds.size();c++)
                	   {
                		   /*
                		   hobbyTable_4+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_4+=propMgr.getMessage(locale,"APTITUDE_WOODWINDS");
                		   }
                		   hobbyTable_4+="</td><td width=\"95\">";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyWoodwinds.get(c).getIdHobby(), resume.getIdLanguage());
                		   if(hobby!=null)
                		   {
                			   hobbyTable_4+=" "+hobby.getHobbyName();
                		   }
                		   else 
                		   {
                			   //hobbyTable_4+=" "+hobbyWoodwinds.get(c).getOthHobby();
                			   hobbyTable_4+=" "+viewInOriginal;
                		   }
                		   hobbyTable_4+="</td><td>";
                		   if(Util.getInt(hobbyWoodwinds.get(c).getSkill(),0)!=0)
                		   {
               			   		String skill=proficiencyComAndSkill(Util.getInt(hobbyWoodwinds.get(c).getSkill()));
               			   		hobbyTable_4+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_4+="</td></tr>";
                		   */
                		   
                		   hobbyTable_4+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_4+=propMgr.getMessage(locale,"APTITUDE_WOODWINDS");
                		   }
                		   hobbyTable_4+="</td>";
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyWoodwinds.get(c).getIdHobby(), resume.getIdLanguage());
                		   
                		   if(hobby!=null)
                  		   {
                  			   hobbyTable_4+="<td  width=\"95\">";
                  			   hobbyTable_4+=" "+hobby.getHobbyName();
                  			   hobbyTable_4+="</td>";
                  			   hobbyTable_4+="<td>";
                  			   if(Util.getInt(hobbyWoodwinds.get(c).getSkill(),0) != 0)
                    		   {
                    			   String skill=proficiencyComAndSkill(Util.getInt(hobbyWoodwinds.get(c).getSkill()));
                    			   hobbyTable_4+=propMgr.getMessage(locale,skill);
                    		   }
                  			 hobbyTable_4+="</td>";
                  		   }
                  		   else  
                  		   {
                  			   hobbyTable_4+="<td colspan='2'>";
                  			   hobbyTable_4+=" "+viewInOriginal;
                  			   hobbyTable_4+="</td>";
                           }
                		   hobbyTable_4+="</tr>";
                		   
                	   }
                   }
                   if(hobbyBrass.size()>0)
                   {
                	   for(int c=0;c<hobbyBrass.size();c++)
                	   {
                		   /*
                		   hobbyTable_5+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_5+=propMgr.getMessage(locale,"APTITUDE_BRASS");
                		   }
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyBrass.get(c).getIdHobby(),resume.getIdLanguage());
                		   hobbyTable_5+="</td><td width=\"95\">";
                		   if(hobby!=null)
                		   {
                			   hobbyTable_5+=" "+hobby.getHobbyName();
                		   }
                		   else 
                		   {
                			   //hobbyTable_5+=" "+hobbyBrass.get(c).getOthHobby();
                			   hobbyTable_5+=" "+viewInOriginal;
                		   }
                		   hobbyTable_5+="</td><td>";
                		   if(Util.getInt(hobbyBrass.get(c).getSkill(),0)!=0)
                		   {
              			   		String skill=proficiencyComAndSkill(Util.getInt(hobbyBrass.get(c).getSkill()));
              			   		hobbyTable_5+=propMgr.getMessage(locale,skill);
                		   }
                		   hobbyTable_5+="</td></tr>";
                		   */
                		   
                		   hobbyTable_5+="<tr><td valign=\"top\" width='110'>";
                		   if(c==0)
                		   {
                			   hobbyTable_5+=propMgr.getMessage(locale,"APTITUDE_BRASS");
                		   }
                		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyBrass.get(c).getIdHobby(),resume.getIdLanguage());
                		   hobbyTable_5+="</td>";
                		   if(hobby!=null)
                  		   {
                  			   hobbyTable_5+="<td  width=\"95\">";
                  			   hobbyTable_5+=" "+hobby.getHobbyName();
                  			   hobbyTable_5+="</td>";
                  			   hobbyTable_5+="<td>";
                  			   if(Util.getInt(hobbyBrass.get(c).getSkill(),0) != 0)
                    		   {
                    			   String skill=proficiencyComAndSkill(Util.getInt(hobbyBrass.get(c).getSkill()));
                    			   hobbyTable_5+=propMgr.getMessage(locale,skill);
                    		   }
                  			 hobbyTable_5+="</td>";
                  		   }
                  		   else  
                  		   {
                  			   hobbyTable_5+="<td colspan='2'>";
                  			   hobbyTable_5+=" "+viewInOriginal;
                  			   hobbyTable_5+="</td>";
                           }
                		   hobbyTable_5+="</tr>";
                		   
                	   }
                   }
         
               if(hobbyKeyboards.size()>0)
               {
            	   for(int c=0;c<hobbyKeyboards.size();c++)
            	   {
            		   /*
            		   hobbyTable_6+="<tr><td valign=\"top\" width='110'>";
            		   if(c==0)
            		   {
            			   hobbyTable_6+=propMgr.getMessage(locale,"APTITUDE_KEYBOARDS");
            		   }
            		   hobbyTable_6+="</td><td width=\"95\">";
            		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyKeyboards.get(c).getIdHobby(), resume.getIdLanguage());
            		   if(hobby!=null)
            		   {
            			   hobbyTable_6+=" "+hobby.getHobbyName();
            		   }
            		   else
            		   {
            			   //hobbyTable_6+=" "+hobbyKeyboards.get(c).getOthHobby();
            			   hobbyTable_6+=" "+viewInOriginal;
            		   }
            		   hobbyTable_6+="</td><td>";
            		   if(Util.getInt(hobbyKeyboards.get(c).getSkill(),0) != 0)
            		   {
            			   	String skill=proficiencyComAndSkill(Util.getInt(hobbyKeyboards.get(c).getSkill()));
            			   	hobbyTable_6+=propMgr.getMessage(locale,skill);
            		   }
            		   hobbyTable_6+="</td></tr>";
            		   */
            		   
            		   hobbyTable_6+="<tr><td valign=\"top\" width='110'>";
            		   if(c==0)
            		   {
            			   hobbyTable_6+=propMgr.getMessage(locale,"APTITUDE_KEYBOARDS");
            		   }
            		   hobbyTable_6+="</td>";
            		   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyKeyboards.get(c).getIdHobby(), resume.getIdLanguage());
            		   if(hobby!=null)
              		   {
              			   hobbyTable_6+="<td  width=\"95\">";
              			   hobbyTable_6+=" "+hobby.getHobbyName();
              			   hobbyTable_6+="</td>";
              			   hobbyTable_6+="<td>";
              			   if(Util.getInt(hobbyKeyboards.get(c).getSkill(),0) != 0)
                		   {
                			   String skill=proficiencyComAndSkill(Util.getInt(hobbyKeyboards.get(c).getSkill()));
                			   hobbyTable_6+=propMgr.getMessage(locale,skill);
                		   }
              			 hobbyTable_6+="</td>";
              		   }
              		   else  
              		   {
              			   hobbyTable_6+="<td colspan='2'>";
              			   hobbyTable_6+=" "+viewInOriginal;
              			   hobbyTable_6+="</td>";
                       }
            		   hobbyTable_6+="</tr>";
            	   }
               } 
		    }
    }
		    
	if(hobbySports.size()>0)
	{
		for(int c=0;c<hobbySports.size();c++)
		{
			/*
			hobbyTable_7+="<tr><td valign=\"top\" width='110'>";
			if(c==0)
			{
				hobbyTable_7+="<b>"+propMgr.getMessage(locale,"APTITUDE_SPORTS")+":</b>";
			}
			hobbyTable_7+="</td><td width=\"95\">";
			com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbySports.get(c).getIdHobby(), resume.getIdLanguage());
			if(hobby!=null)
			{
				hobbyTable_7+=" "+hobby.getHobbyName();
			}
			else
			{
				hobbyTable_7+=" "+viewInOriginal;
				//hobbyTable_7+=" "+hobbySports.get(c).getOthHobby();
			}
			hobbyTable_7+="</td><td>";
			if(hobbySports.get(c).getSkill() != 0)
			{
				String skill=proficiencyComAndSkill(Util.getInt(hobbySports.get(c).getSkill()));
				hobbyTable_7+=propMgr.getMessage(locale,skill);
			}
			hobbyTable_7+="</td></tr>";
			*/
			
			hobbyTable_7+="<tr><td valign=\"top\" width='110'>";
			if(c==0)
			{
				hobbyTable_7+="<b>"+propMgr.getMessage(locale,"APTITUDE_SPORTS")+":</b>";
			}
			hobbyTable_7+="</td>";
			com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbySports.get(c).getIdHobby(), resume.getIdLanguage());
			if(hobby!=null)
			{
				hobbyTable_7+="<td  width=\"95\">";
   			   	hobbyTable_7+=" "+hobby.getHobbyName();
   			   	hobbyTable_7+="</td>";
   			   	hobbyTable_7+="<td>";
   			   	if(Util.getInt(hobbySports.get(c).getSkill(),0) != 0)
   			   	{
   			   		String skill=proficiencyComAndSkill(Util.getInt(hobbySports.get(c).getSkill()));
   			   		hobbyTable_7+=propMgr.getMessage(locale,skill);
   			   	}
   			   	hobbyTable_7+="</td>";
   		   	}
   		   	else  
   		   	{
   		   		hobbyTable_7+="<td colspan='2'>";
   		   		hobbyTable_7+=" "+viewInOriginal;
   		   		hobbyTable_7+="</td>";
   		   	}
			hobbyTable_7+="</tr>";
			
		}
	}
	if(hobbyOutdoor.size()>0)
	{
		head_hobbyTable_8+="<tr><td valign=\"top\" colspan=3><b>"+propMgr.getMessage(locale,"APTITUDE_OUTDOOR_ADVENTURE")+":</b></td></tr>";
		for(int c=0;c<hobbyOutdoor.size();c++)
		{
			/*
			hobbyTable_8+="<tr><td valign=\"top\" width='110'>&nbsp";
			hobbyTable_8+="</td><td width=\"95\">";
			com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyOutdoor.get(c).getIdHobby(), resume.getIdLanguage());
			if(hobby!=null)
			{
				hobbyTable_8+=" "+hobby.getHobbyName();
			}
			else
			{
				hobbyTable_8+=" "+viewInOriginal;
				//hobbyTable_8+=" "+hobbyOutdoor.get(c).getOthHobby();
			}
			hobbyTable_8+="</td><td>";
			if(hobbyOutdoor.get(c).getSkill() != 0)
			{
				String skill=proficiencyComAndSkill(Util.getInt(hobbyOutdoor.get(c).getSkill()));
				hobbyTable_8+=propMgr.getMessage(locale,skill);
			}
			hobbyTable_8+="</td></tr>";
			*/
			
			hobbyTable_8+="<tr><td valign=\"top\" width='110'><span>&nbsp;</span>";
			hobbyTable_8+="</td>";
			com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyOutdoor.get(c).getIdHobby(), resume.getIdLanguage());
			if(hobby!=null)
			{
				hobbyTable_8+="<td  width=\"95\">";
   			   	hobbyTable_8+=" "+hobby.getHobbyName();
   			   	hobbyTable_8+="</td>";
   			   	hobbyTable_8+="<td>";
   			   	if(Util.getInt(hobbyOutdoor.get(c).getSkill(),0) != 0)
   			   	{
   			   		String skill=proficiencyComAndSkill(Util.getInt(hobbyOutdoor.get(c).getSkill()));
   			   		hobbyTable_8+=propMgr.getMessage(locale,skill);
   			   	}
   			   	hobbyTable_8+="</td>";
   		   	}
   		   	else  
   		   	{
   		   		hobbyTable_8+="<td colspan='2'>";
   		   		hobbyTable_8+=" "+viewInOriginal;
   		   		hobbyTable_8+="</td>";
   		   	}
			hobbyTable_8+="</tr>";
			
		}
	}
        
    if(hobbyOther.size()>0)
    {   
    	head_hobbyTable_9+="<tr><td valign=\"top\" colspan=3><b>"+propMgr.getMessage(locale,"APTITUDE_OTHER_HOBBIES")+":</b></td></tr>";
    	for(int c=0;c<hobbyOther.size();c++)
    	{
    	   hobbyTable_9+="<tr><td valign=\"top\" width='110'>&nbsp;";
    	   hobbyTable_9+="</td><td valign=\"top\" width=\"95\">";
    	   com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyOther.get(c).getIdHobby(), resume.getIdLanguage());
    	   if(hobby!=null)
    	   {
    		   hobbyTable_9+=" "+hobby.getHobbyName();
    	   }
    	   else
    	   {
    		   hobbyTable_9+=" "+viewInOriginal;
    		   //hobbyTable_9+=" "+hobbyOther.get(c).getOthHobby();
    	   }
    	   hobbyTable_9+="</td><td>";
    	   hobbyTable_9_1="";
    	   hobbyTable_9_2="";
    	   hobbyTable_9_3="";
    	   hobbyTable_9_4="";
    	   if(readBook != null && hobbyOther.get(c).getIdHobby() == 45 )
    	   {  
    		   for(int r=0;r<readBook.size();r++)
    		   {
    			   com.topgun.shris.masterdata.ReadingBook aBook=MasterDataManager.getReadingBook(readBook.get(r).getIdBook(),  resume.getIdLanguage());
    			   if(r > 0)
    			   {
    				   hobbyTable_9_1+=", ";
    			   }
    			   if(aBook!=null)
    			   {
        			   hobbyTable_9_1+=aBook.getBookName(); 
    			   }
    			   else
    			   {
    				   if(isTranslate!=true)
    				   {
    					   hobbyTable_9_1+=viewInOriginal;
    					    //hobbyTable_9_1+=readBook.get(r).getOtherBook();
    				   }
    			   }
    		   }
    	   }
    	   else if(jskPet != null && hobbyOther.get(c).getIdHobby() == 56 )
    	   {
    		   for(int r=0;r<jskPet.size();r++)
    		   {
    			   com.topgun.shris.masterdata.Pet aPet=MasterDataManager.getPet(jskPet.get(r).getIdPet(),  resume.getIdLanguage());
    			   if(r > 0)
    			   {
    				   hobbyTable_9_2+=", ";
    			   }
    			   if(aPet!=null)
    			   {
    				   hobbyTable_9_2+=aPet.getPetName(); 
    			   }
    			   else
    			   {
    				   hobbyTable_9_2+=viewInOriginal;
    				   //hobbyTable_9_2+=jskPet.get(r).getOtherPet();
    			   }
    		   }
    	   }
    	   else if(jskTravel != null && hobbyOther.get(c).getIdHobby() == 43 )
    	   {
    		   for(int r=0;r<jskTravel.size();r++)
    		   {
    			   com.topgun.shris.masterdata.Travel aTavel=MasterDataManager.getTravel(jskTravel.get(r).getIdTravel(), resume.getIdLanguage());
    			   com.topgun.shris.masterdata.TravelFreq aFreq=MasterDataManager.getTravelFreq(jskTravel.get(r).getIdFrequency(), resume.getIdLanguage());
    			   if(r > 0)
    			   {
    				   hobbyTable_9_3+="<br>";
    			   }
    			   if(aTavel!=null && aFreq!=null)
    			   {
    				   hobbyTable_9_3+=aTavel.getTravelName()+" "+aFreq.getFrequencyName(); 
    				   
    			   }
    		   }
    	   }
    	   else 
    	   {
    		   if(hobbyOther.get(c).getSkill() != 0)
    		   {
   					String skill=proficiencyComAndSkill(Util.getInt(hobbyOther.get(c).getSkill()));
   					hobbyTable_9_4+=propMgr.getMessage(locale,skill);
    		   }
    	   }
    	   hobbyTable_9=hobbyTable_9+hobbyTable_9_1+hobbyTable_9_2+hobbyTable_9_3+hobbyTable_9_4+"</td></tr>";
       }

    }
    hobbyTable=hobbyHeadTable+hobbyTable+hobbyTable_2+hobbyTable_3+hobbyTable_4+hobbyTable_5+hobbyTable_6+hobbyTable_7+head_hobbyTable_8+hobbyTable_8+head_hobbyTable_9+hobbyTable_9+"</table>";
    if(hobbyRanked.size()>0)
    {
    	for(int no=1;no<=6;no++)
    	{
    		for(int i=0;i<hobbyRanked.size();i++)
    		{
    			if(hobbyRanked.get(i).getHobbyOrder()==no)
    			{
    				com.topgun.shris.masterdata.Hobby hobby=MasterDataManager.getHobby(hobbyRanked.get(i).getIdHobby(), resume.getIdLanguage());
    				rankedTable+=no+". ";
    				if(hobby!=null)
            		{
            			rankedTable+=Util.getStr(hobby.getHobbyName())+"<span>&nbsp;</span><span>&nbsp;</span>";
            		}
            		else 
            		{
            			rankedTable+=viewInOriginal+"<span>&nbsp;</span><span>&nbsp;</span>";
            			//rankedTable+=hobbyRanked.get(i).getOthHobby()+"<span>&nbsp;</span><span>&nbsp;</span>";
            		}
        			break;
    			}
    		}
    	}
    }
    else 
    {
    	
		startComment="<!--";
		endComment="-->";
		
	}
	replaceAll(result,"#BEGIN_APTITUDE#", startComment);
	replaceAll(result,"#END_APTITUDE#", endComment);
	replaceAll(result,"#APTITUDE_DETAIL#", hobbyTable);
	replaceAll(result,"#TOPGUN_APTITUDE_TOPIC#", aptitudeTopic);
	replaceAll(result,"#PREVIEW_RANKED_FAVOURITE_HOBBY#", propMgr.getMessage(locale, "PREVIEW_RANKED_FAVOURITE_HOBBY"));
	replaceAll(result,"#PREVIEW_MAX_TOP_5#", propMgr.getMessage(locale, "PREVIEW_MAX_TOP_5"));
	replaceAll(result,"#APTITUDE_RANKED#", rankedTable);
    }
	
	private String proficiencyComAndSkill(int level)
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
	
	public String proficiencyLanguage(int level)
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
	
	private void ReplaceReferences(Resume resume)
    {
		String refTable1="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		List<Reference> reference=new ReferenceManager().getAll(resume.getIdJsk(), resume.getIdResume());
		
		if(reference.size()>0)
		{
			for(int i=0;i<reference.size();i++)
			{
				if(!reference.get(i).getRefName().equals(""))
				{
					if(viewType==1){refTable1+="<font color='#82363a'>";}
					refTable1+="<b>"+reference.get(i).getRefName()+"</b><span>&nbsp;</span>";
					if(reference.get(i).getRefType().equals("1"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_PREVIOUS")+")";
					}
					else if(reference.get(i).getRefType().equals("2"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_CURRENT")+")";
					}
					else if(reference.get(i).getRefType().equals("3"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_PROFESSOR")+")";
					}
					else if(reference.get(i).getRefType().equals("4") && !reference.get(i).getOthRefType().equals(""))
					{
						refTable1+="("+reference.get(i).getOthRefType()+")";
					}
					else if(reference.get(i).getRefType().equals("5"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_BUSINESS")+")";
					}
					else if(reference.get(i).getRefType().equals("6"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_BUSINESS_COLLEAGUE")+")";
					}
					if(!reference.get(i).getRefTitle().equals(""))
					{
						refTable1+="<br/>"+reference.get(i).getRefTitle();
					}
					if(!reference.get(i).getRefCompany().equals(""))
					{
						if(!reference.get(i).getRefTitle().equals(""))
						{
							refTable1+=", "+reference.get(i).getRefCompany();
						}
						else 
						{
							refTable1+="<br/>"+reference.get(i).getRefCompany();
						}
					}
					if(!reference.get(i).getRefTel().equals(""))
					{
						refTable1+="<br/>"+propMgr.getMessage(locale,"PREVIEW_TEL")+reference.get(i).getRefTel();
					}
					if(viewType==1){refTable1+="</font>";}
					refTable1+="<br/>";
				}
			}
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		if(isTranslate==true)
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_REFERENCES#", propMgr.getMessage(locale, "PREVIEW_REFERENCES"));
		replaceAll(result,"#REFERENCES_DETAIL#", refTable1);
		replaceAll(result,"#BEGIN_REFERENCES#", startComment);
		replaceAll(result,"#END_REFERENCES#", endComment);
    }
	
	private void ReplaceReferencesHideOther(Resume resume)
    {
		String refTable1="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		List<Reference> reference=new ReferenceManager().getAll(resume.getIdJsk(), resume.getIdResume());
		
		if(reference.size()>0)
		{
			for(int i=0;i<reference.size();i++)
			{
				if(!reference.get(i).getRefName().equals(""))
				{
					if(viewType==1){refTable1+="<font color='#82363a'>";}
					refTable1+="<b>"+viewInOriginal+"</b><span>&nbsp;</span>";
					if(reference.get(i).getRefType().equals("1"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_PREVIOUS")+")";
					}
					else if(reference.get(i).getRefType().equals("2"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_CURRENT")+")";
					}
					else if(reference.get(i).getRefType().equals("3"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_PROFESSOR")+")";
					}
					else if(reference.get(i).getRefType().equals("4") && !reference.get(i).getOthRefType().equals(""))
					{
						refTable1+=viewInOriginal;
					}
					else if(reference.get(i).getRefType().equals("5"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_BUSINESS")+")";
					}
					else if(reference.get(i).getRefType().equals("6"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_BUSINESS_COLLEAGUE")+")";
					}
					if(!reference.get(i).getRefTitle().equals(""))
					{
						refTable1+="<br/>"+viewInOriginal;
					}
					if(!reference.get(i).getRefCompany().equals(""))
					{
						if(!reference.get(i).getRefTitle().equals(""))
						{
							refTable1+=", "+viewInOriginal;
						}
						else 
						{
							refTable1+="<br/>"+viewInOriginal;
						}
					}
					if(!reference.get(i).getRefTel().equals(""))
					{
						refTable1+="<br/>"+propMgr.getMessage(locale,"PREVIEW_TEL")+reference.get(i).getRefTel();
					}
					if(viewType==1){refTable1+="</font>";}
					refTable1+="<br/>";
				}
			}
			
			/*
			for(int i=0;i<reference.size();i++)
			{
				if(!reference.get(i).getRefName().equals(""))
				{
					if(viewType==1){refTable1+="<font color='#82363a'>";}
					refTable1+="<b>"+reference.get(i).getRefName()+"</b><span>&nbsp;</span>";
					if(reference.get(i).getRefType().equals("1"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_PREVIOUS")+")";
					}
					else if(reference.get(i).getRefType().equals("2"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_CURRENT")+")";
					}
					else if(reference.get(i).getRefType().equals("3"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_PROFESSOR")+")";
					}
					else if(reference.get(i).getRefType().equals("4") && !reference.get(i).getOthRefType().equals(""))
					{
						refTable1+="("+reference.get(i).getOthRefType()+")";
					}
					else if(reference.get(i).getRefType().equals("5"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_BUSINESS")+")";
					}
					else if(reference.get(i).getRefType().equals("6"))
					{
						refTable1+="("+propMgr.getMessage(locale,"REFERENCES_RELATIONSHIP_BUSINESS_COLLEAGUE")+")";
					}
					if(!reference.get(i).getRefTitle().equals(""))
					{
						refTable1+="<br/>"+reference.get(i).getRefTitle();
					}
					if(!reference.get(i).getRefCompany().equals(""))
					{
						if(!reference.get(i).getRefTitle().equals(""))
						{
							refTable1+=", "+reference.get(i).getRefCompany();
						}
						else 
						{
							refTable1+="<br>"+reference.get(i).getRefCompany();
						}
					}
					if(!reference.get(i).getRefTel().equals(""))
					{
						refTable1+="<br/>"+propMgr.getMessage(locale,"PREVIEW_TEL")+reference.get(i).getRefTel();
					}
					if(viewType==1){refTable1+="</font>";}
					refTable1+="<br/>";
				}
			}
			*/
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		if(isTranslate==true)
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_REFERENCES#", propMgr.getMessage(locale, "PREVIEW_REFERENCES"));
		replaceAll(result,"#REFERENCES_DETAIL#", refTable1);
		replaceAll(result,"#BEGIN_REFERENCES#", startComment);
		replaceAll(result,"#END_REFERENCES#", endComment);
    }
	
	private void ReplaceFamilyBackground(Resume resume)
    {
		String familyTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}

			
		Family f_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 1); // dad
		Family m_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 2); // dad
		List<Family> siblingList=new FamilyManager().getFamilySibling(resume.getIdJsk(),resume.getIdResume());// sibling
		int idSpouse =new FamilyManager().getIdFamilyMarried(resume.getIdJsk(),resume.getIdResume());// id spouse 
		List<Family>childList =new FamilyManager().getFamilyChild(resume.getIdJsk(),resume.getIdResume()); // child  
		if(f_family==null && m_family==null && siblingList.size()==0 && childList.size()==0 && idSpouse==0)
		{
			startComment="<!--";
			endComment="-->";
		}
		else 
		{
			if(idSpouse > 0)
			{
				Family s_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(),idSpouse);
				if(viewType==1){familyTable+="<font color='#82363a'>";}
				familyTable+="<b>"+propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS")+": </b>";
				if(s_family.getMarriedStatus().equals("SINGLE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS_SINGLE");
				}
				else if(s_family.getMarriedStatus().equals("MARRIED"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS_MARRIED");
				}
				else if(s_family.getMarriedStatus().equals("DIVORCED"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS_DIVORCED");
				}
				if(!Util.getStr(s_family.getFirstName()).equals(""))
				{
					familyTable+="<br/><b>"+propMgr.getMessage(locale,"FAMILY_MARRIED_NAME")+":</b> ";
					familyTable+=s_family.getFirstName()+" "+s_family.getLastName()+" ";
					if(!Util.getStr(s_family.getCitizenshipOther()).equals(""))
					{
						if(s_family.getCitizenshipOther().equals("THAI"))
						{
							familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
						}
						else if(s_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
						{
							familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
						}
						else 
						{
							familyTable+="("+s_family.getCitizenshipOther()+")";
						}
					}
					familyTable+="<br/>";
				    if(s_family.getWorkStatus().equals("RETIRED") && (!Util.getStr(s_family.getPosition()).equals("") || !Util.getStr(s_family.getCompany()).equals("")))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
					}
					else if(s_family.getWorkStatus().equals("NONWORKING"))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
					}
					else if(s_family.getWorkStatus().equals("HOUSEWIFE"))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_WORK_STATUS_HOME");
					}
					if((s_family.getWorkStatus().equals("WORKING") || s_family.getWorkStatus().equals("RETIRED")) && (!s_family.getPosition().equals("") || !s_family.getCompany().equals("")))
					{
						familyTable+=" "+s_family.getPosition()+" ";
						if(!s_family.getCompany().equals(""))
						{
							if(!s_family.getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+s_family.getCompany();
						}
					}
				if(!s_family.getTelephone().equals("")){
					familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+s_family.getTelephone();
				}
				}
			}
			if(childList != null && childList.size() > 0)
			{
				familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_NUMBER_OF_CHILDREN")+":</b> "+childList.size();
				for(int c=0;c<childList.size();c++)
				{
					familyTable+="<br/>"+(c+1)+". "+childList.get(c).getFirstName()+" "+childList.get(c).getLastName();
					if(childList.get(c).getAge()!=-1)
					{
						familyTable+=" "+propMgr.getMessage(locale,"FAMILY_AGE")+": "+childList.get(c).getAge();
					}
					if(childList.get(c).getWorkStatus().equals("LEARNING") && !childList.get(c).getCompany().equals(""))
					{
						familyTable+="<br/>"+propMgr.getMessage(locale,"FAMILY_EDUCATION")+": "+childList.get(c).getCompany();
					}
					else if(childList.get(c).getWorkStatus().equals("WORKING") && (!childList.get(c).getPosition().equals("") || !childList.get(c).getCompany().equals("")))
					{
						familyTable+="<br/>"+childList.get(c).getPosition()+" ";
						if(!childList.get(c).getCompany().equals(""))
						{
							if(!childList.get(c).getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+childList.get(c).getCompany();
						}
					}
					if(!childList.get(c).getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+childList.get(c).getTelephone();
					}
				}
			}
			
			if(f_family!=null && !f_family.getFirstName().equals(""))
			{
				familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_FATHER")+":</b> "+propMgr.getMessage(locale,"GLOBAL_MR")+" "+f_family.getFirstName()+" "+f_family.getLastName();
				if(f_family.getDiedStatus().equals("DIED"))
				{
					familyTable+="("+propMgr.getMessage(locale,"FAMILY_STATUS_DIED")+")";
				}
				if(!f_family.getCitizenshipOther().equals(""))
				{
					if(f_family.getCitizenshipOther().equals("THAI"))
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else if(f_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else 
					{
						familyTable+="("+f_family.getCitizenshipOther()+")";
					}
				}
				familyTable+="<br/>";
			     if(f_family.getWorkStatus().equals("RETIRED") && (!f_family.getPosition().equals("") || !f_family.getCompany().equals("")))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
				}
				else if(f_family.getWorkStatus().equals("NONWORKING"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
				}
				else if(f_family.getWorkStatus().equals("HOUSEWIFE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_FATHER_HOME");
				}
				if((f_family.getWorkStatus().equals("WORKING") || f_family.getWorkStatus().equals("RETIRED")) && (!f_family.getPosition().equals("") || !f_family.getCompany().equals("")))
				{
					familyTable+=" "+f_family.getPosition()+" ";
					if(!f_family.getCompany().equals(""))
					{
						if(!f_family.getPosition().equals(""))
						{
							familyTable+=", ";
						}
						familyTable+=" "+f_family.getCompany();
					}
				}
				if(!f_family.getTelephone().equals(""))
				{
					familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+f_family.getTelephone();
				}
			}
			//Mother
			if(m_family!=null && !m_family.getFirstName().equals(""))
			{
				familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_MOTHER")+":</b> "+propMgr.getMessage(locale,"GLOBAL_MRS")+" "+m_family.getFirstName()+" "+m_family.getLastName();
				if(m_family.getDiedStatus().equals("DIED"))
				{
					familyTable+="("+propMgr.getMessage(locale,"FAMILY_STATUS_DIED")+")";
				}
				if(!m_family.getCitizenshipOther().equals(""))
				{
					if(m_family.getCitizenshipOther().equals("THAI"))
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else if(m_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else 
					{
						familyTable+="("+m_family.getCitizenshipOther()+")";
					}
				}
				familyTable+="<br/>";
			     if(m_family.getWorkStatus().equals("RETIRED") && (!m_family.getPosition().equals("") || !m_family.getCompany().equals("")))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
				}
				else if(m_family.getWorkStatus().equals("NONWORKING"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
				}
				else if(m_family.getWorkStatus().equals("HOUSEWIFE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_MOTHER_HOME");
				}
				if((m_family.getWorkStatus().equals("WORKING") || m_family.getWorkStatus().equals("RETIRED")) && (!m_family.getPosition().equals("") || !m_family.getCompany().equals("")))
				{
					familyTable+=" "+m_family.getPosition()+" ";
					if(!m_family.getCompany().equals(""))
					{
						if(!m_family.getPosition().equals(""))
						{
							familyTable+=", ";
						}
						familyTable+=" "+m_family.getCompany();
					}
				}
			if(!m_family.getTelephone().equals("")){
				familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+m_family.getTelephone();
			}
			}
			
			//sibling
			if(siblingList != null && siblingList.size() > 0)
			{
				familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_NUMBER_OF_SIBLINGS")+":</b> "+siblingList.size();
				for(int c=0;c<siblingList.size();c++)
				{
					familyTable+="<br/>"+(c+1)+". "+siblingList.get(c).getFirstName()+" "+siblingList.get(c).getLastName();
					if(siblingList.get(c).getSiblingRelationship().equals("OLDER_SISTER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_OLDER_SISTER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("YOUNGER_SISTER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_YOUNGER_SISTER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("OLDER_BROTHER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_OLDER_BROTHER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("YOUNGER_BROTHER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_YOUNGER_BROTHER")+")";
					}
					if(siblingList.get(c).getAge()!=-1)
					{
						familyTable+=" "+propMgr.getMessage(locale,"FAMILY_AGE")+": "+siblingList.get(c).getAge();
					}
					if(siblingList.get(c).getWorkStatus().equals("LEARNING") && !siblingList.get(c).getCompany().equals(""))
					{
						familyTable+="<br/>"+propMgr.getMessage(locale,"FAMILY_EDUCATION")+": "+siblingList.get(c).getCompany();
					}
					else if(siblingList.get(c).getWorkStatus().equals("WORKING") && (!siblingList.get(c).getPosition().equals("") || !siblingList.get(c).getCompany().equals("")))
					{
						familyTable+="<br/>"+siblingList.get(c).getPosition()+" ";
						if(!siblingList.get(c).getCompany().equals(""))
						{
							if(!siblingList.get(c).getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+siblingList.get(c).getCompany();
						}
					}
					if(!siblingList.get(c).getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+siblingList.get(c).getTelephone();
					}
				}
			}
		}
		if(viewType==1){familyTable+="</font>";}
		if(isTranslate==true)
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_FAMILY_BACKGROUND#", propMgr.getMessage(locale, "PREVIEW_FAMILY_BACKGROUND"));
		replaceAll(result,"#FAMILY_BACKGROUND_DETAIL#", familyTable);
		replaceAll(result,"#BEGIN_FAMILY_BACKGROUND#", startComment);
		replaceAll(result,"#END_FAMILY_BACKGROUND#", endComment);
    }
	
	private void ReplaceFamilyBackgroundHideOther(Resume resume)
    {
		String familyTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}

		startComment="<!--";
		endComment="-->";	
		
		Family f_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 1); // dad
		Family m_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 2); // dad
		List<Family> siblingList=new FamilyManager().getFamilySibling(resume.getIdJsk(),resume.getIdResume());// sibling
		int idSpouse =new FamilyManager().getIdFamilyMarried(resume.getIdJsk(),resume.getIdResume());// id spouse 
		List<Family>childList =new FamilyManager().getFamilyChild(resume.getIdJsk(),resume.getIdResume()); // child  
		if(f_family==null && m_family==null && siblingList.size()==0 && childList.size()==0 && idSpouse==0)
		{
			startComment="<!--";
			endComment="-->";
		}
		else 
		{
			if(idSpouse > 0)
			{
				Family s_family=new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(),idSpouse);
				if(viewType==1){familyTable+="<font color='#82363a'>";}
				familyTable+="<b>"+propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS")+": </b>";
				if(s_family.getMarriedStatus().equals("SINGLE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS_SINGLE");
				}
				else if(s_family.getMarriedStatus().equals("MARRIED"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS_MARRIED");
				}
				else if(s_family.getMarriedStatus().equals("DIVORCED"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_STATUS_DIVORCED");
				}
				if(!Util.getStr(s_family.getFirstName()).equals(""))
				{
					
					familyTable+="<br><b>"+propMgr.getMessage(locale,"FAMILY_MARRIED_NAME")+":</b> ";
					
					familyTable+=viewInOriginal+" "+viewInOriginal+" ";
					if(!Util.getStr(s_family.getCitizenshipOther()).equals(""))
					{
						if(s_family.getCitizenshipOther().equals("THAI"))
						{
							familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
						}
						else if(s_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
						{
							familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
						}
						else 
						{
							familyTable+=viewInOriginal;
						}
					}
					familyTable+="<br>";
				    if(s_family.getWorkStatus().equals("RETIRED") && (!Util.getStr(s_family.getPosition()).equals("") || !Util.getStr(s_family.getCompany()).equals("")))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
					}
					else if(s_family.getWorkStatus().equals("NONWORKING"))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
					}
					else if(s_family.getWorkStatus().equals("HOUSEWIFE"))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_WORK_STATUS_HOME");
					}
					if((s_family.getWorkStatus().equals("WORKING") || s_family.getWorkStatus().equals("RETIRED")) && (!s_family.getPosition().equals("") || !s_family.getCompany().equals("")))
					{
						familyTable+=" "+viewInOriginal+" ";
						if(!s_family.getCompany().equals(""))
						{
							if(!s_family.getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+viewInOriginal;
						}
					}
					if(!s_family.getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+s_family.getTelephone();
					}
					
					//familyTable+=viewInOriginal;
					/*familyTable+=s_family.getFirstName()+" "+s_family.getLastName()+" ";
					if(!Util.getStr(s_family.getCitizenshipOther()).equals(""))
					{
						if(s_family.getCitizenshipOther().equals("THAI"))
						{
							familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
						}
						else if(s_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
						{
							familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
						}
						else 
						{
							familyTable+="("+s_family.getCitizenshipOther()+")";
						}
					}
					familyTable+="<br/>";
				    if(s_family.getWorkStatus().equals("RETIRED") && (!Util.getStr(s_family.getPosition()).equals("") || !Util.getStr(s_family.getCompany()).equals("")))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
					}
					else if(s_family.getWorkStatus().equals("NONWORKING"))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
					}
					else if(s_family.getWorkStatus().equals("HOUSEWIFE"))
					{
						familyTable+=propMgr.getMessage(locale,"FAMILY_MARRIED_WORK_STATUS_HOME");
					}
					if((s_family.getWorkStatus().equals("WORKING") || s_family.getWorkStatus().equals("RETIRED")) && (!s_family.getPosition().equals("") || !s_family.getCompany().equals("")))
					{
						familyTable+=" "+s_family.getPosition()+" ";
						if(!s_family.getCompany().equals(""))
						{
							if(!s_family.getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+s_family.getCompany();
						}
					}
					if(!s_family.getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+s_family.getTelephone();
					}*/
				}
			}
			if(childList != null && childList.size() > 0)
			{
				familyTable+="<br/>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_NUMBER_OF_CHILDREN")+":</b> "+childList.size();
				
				for(int c=0;c<childList.size();c++)
				{
					familyTable+="<br/>"+(c+1)+". "+viewInOriginal+" "+viewInOriginal;
					if(childList.get(c).getAge()!=-1)
					{
						familyTable+=" "+propMgr.getMessage(locale,"FAMILY_AGE")+": "+childList.get(c).getAge();
					}
					if(childList.get(c).getWorkStatus().equals("LEARNING") && !childList.get(c).getCompany().equals(""))
					{
						familyTable+="<br/>"+propMgr.getMessage(locale,"FAMILY_EDUCATION")+": "+viewInOriginal;
					}
					else if(childList.get(c).getWorkStatus().equals("WORKING") && (!childList.get(c).getPosition().equals("") || !childList.get(c).getCompany().equals("")))
					{
						familyTable+="<br/>"+viewInOriginal+" ";
						if(!childList.get(c).getCompany().equals(""))
						{
							if(!childList.get(c).getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+viewInOriginal;
						}
					}
					if(!childList.get(c).getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+childList.get(c).getTelephone();
					}
				}
				
				//familyTable+=viewInOriginal;
				/*for(int c=0;c<childList.size();c++)
				{
					familyTable+="<br/>"+(c+1)+". "+childList.get(c).getFirstName()+" "+childList.get(c).getLastName();
					if(childList.get(c).getAge()!=-1)
					{
						familyTable+=" "+propMgr.getMessage(locale,"FAMILY_AGE")+": "+childList.get(c).getAge();
					}
					if(childList.get(c).getWorkStatus().equals("LEARNING") && !childList.get(c).getCompany().equals(""))
					{
						familyTable+="<br/>"+propMgr.getMessage(locale,"FAMILY_EDUCATION")+": "+childList.get(c).getCompany();
					}
					else if(childList.get(c).getWorkStatus().equals("WORKING") && (!childList.get(c).getPosition().equals("") || !childList.get(c).getCompany().equals("")))
					{
						familyTable+="<br/>"+childList.get(c).getPosition()+" ";
						if(!childList.get(c).getCompany().equals(""))
						{
							if(!childList.get(c).getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+childList.get(c).getCompany();
						}
					}
					if(!childList.get(c).getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+childList.get(c).getTelephone();
					}
				}*/
			}
			
			if(f_family!=null && !f_family.getFirstName().equals(""))
			{
				//familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_FATHER")+":</b> "+propMgr.getMessage(locale,"GLOBAL_MR")+" "+f_family.getFirstName()+" "+f_family.getLastName();
				familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_FATHER")+":</b> "+propMgr.getMessage(locale,"GLOBAL_MR")+" "+viewInOriginal+" "+viewInOriginal;
				
				if(f_family.getDiedStatus().equals("DIED"))
				{
					familyTable+="("+propMgr.getMessage(locale,"FAMILY_STATUS_DIED")+")";
				}
				if(!f_family.getCitizenshipOther().equals(""))
				{
					if(f_family.getCitizenshipOther().equals("THAI"))
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else if(f_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else 
					{
						familyTable+=viewInOriginal;
					}
				}
				familyTable+="<br/>";
			     if(f_family.getWorkStatus().equals("RETIRED") && (!f_family.getPosition().equals("") || !f_family.getCompany().equals("")))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
				}
				else if(f_family.getWorkStatus().equals("NONWORKING"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
				}
				else if(f_family.getWorkStatus().equals("HOUSEWIFE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_FATHER_HOME");
				}
				if((f_family.getWorkStatus().equals("WORKING") || f_family.getWorkStatus().equals("RETIRED")) && (!f_family.getPosition().equals("") || !f_family.getCompany().equals("")))
				{
					familyTable+=" "+viewInOriginal+" ";
					if(!f_family.getCompany().equals(""))
					{
						if(!f_family.getPosition().equals(""))
						{
							familyTable+=", ";
						}
						familyTable+=" "+viewInOriginal;
					}
				}
				if(!f_family.getTelephone().equals(""))
				{
					familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+f_family.getTelephone();
				}
				
				//familyTable+="View Original";
				/*if(f_family.getDiedStatus().equals("DIED"))
				{
					familyTable+="("+propMgr.getMessage(locale,"FAMILY_STATUS_DIED")+")";
				}
				if(!f_family.getCitizenshipOther().equals(""))
				{
					if(f_family.getCitizenshipOther().equals("THAI"))
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else if(f_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else 
					{
						familyTable+="("+f_family.getCitizenshipOther()+")";
					}
				}
				familyTable+="<br/>";
			     if(f_family.getWorkStatus().equals("RETIRED") && (!f_family.getPosition().equals("") || !f_family.getCompany().equals("")))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
				}
				else if(f_family.getWorkStatus().equals("NONWORKING"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
				}
				else if(f_family.getWorkStatus().equals("HOUSEWIFE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_FATHER_HOME");
				}
				if((f_family.getWorkStatus().equals("WORKING") || f_family.getWorkStatus().equals("RETIRED")) && (!f_family.getPosition().equals("") || !f_family.getCompany().equals("")))
				{
					familyTable+=" "+f_family.getPosition()+" ";
					if(!f_family.getCompany().equals(""))
					{
						if(!f_family.getPosition().equals(""))
						{
							familyTable+=", ";
						}
						familyTable+=" "+f_family.getCompany();
					}
				}
				if(!f_family.getTelephone().equals(""))
				{
					familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+f_family.getTelephone();
				}*/
			}
			//Mother
			if(m_family!=null && !m_family.getFirstName().equals(""))
			{
				familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_MOTHER")+":</b> "+propMgr.getMessage(locale,"GLOBAL_MRS")+" "+viewInOriginal+" "+viewInOriginal;
				
				if(m_family.getDiedStatus().equals("DIED"))
				{
					familyTable+="("+propMgr.getMessage(locale,"FAMILY_STATUS_DIED")+")";
				}
				if(!m_family.getCitizenshipOther().equals(""))
				{
					if(m_family.getCitizenshipOther().equals("THAI"))
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else if(m_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else 
					{
						familyTable+=viewInOriginal;
					}
				}
				familyTable+="<br>";
			     if(m_family.getWorkStatus().equals("RETIRED") && (!m_family.getPosition().equals("") || !m_family.getCompany().equals("")))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
				}
				else if(m_family.getWorkStatus().equals("NONWORKING"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
				}
				else if(m_family.getWorkStatus().equals("HOUSEWIFE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_MOTHER_HOME");
				}
				if((m_family.getWorkStatus().equals("WORKING") || m_family.getWorkStatus().equals("RETIRED")) && (!m_family.getPosition().equals("") || !m_family.getCompany().equals("")))
				{
					familyTable+=" "+viewInOriginal+" ";
					if(!m_family.getCompany().equals(""))
					{
						if(!m_family.getPosition().equals(""))
						{
							familyTable+=", ";
						}
						familyTable+=" "+viewInOriginal;
					}
				}
				if(!m_family.getTelephone().equals("")){
					familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+m_family.getTelephone();
				}
				
				//familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_MOTHER")+":</b> "+propMgr.getMessage(locale,"GLOBAL_MRS")+" "+m_family.getFirstName()+" "+m_family.getLastName();
				/*if(m_family.getDiedStatus().equals("DIED"))
				{
					familyTable+="("+propMgr.getMessage(locale,"FAMILY_STATUS_DIED")+")";
				}
				if(!m_family.getCitizenshipOther().equals(""))
				{
					if(m_family.getCitizenshipOther().equals("THAI"))
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else if(m_family.getCitizenshipOther().toLowerCase().equals("filipino") && resume.getTemplateIdCountry() == 174)
					{
						familyTable+="("+propMgr.getMessage(locale,"GLOBAL_THAI")+")";
					}
					else 
					{
						familyTable+="("+m_family.getCitizenshipOther()+")";
					}
				}
				familyTable+="<br/>";
			     if(m_family.getWorkStatus().equals("RETIRED") && (!m_family.getPosition().equals("") || !m_family.getCompany().equals("")))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE")+" "+propMgr.getMessage(locale,"FAMILY_WORK_STATUS_RETIRE_DETAIL");
				}
				else if(m_family.getWorkStatus().equals("NONWORKING"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_NOT");
				}
				else if(m_family.getWorkStatus().equals("HOUSEWIFE"))
				{
					familyTable+=propMgr.getMessage(locale,"FAMILY_WORK_STATUS_MOTHER_HOME");
				}
				if((m_family.getWorkStatus().equals("WORKING") || m_family.getWorkStatus().equals("RETIRED")) && (!m_family.getPosition().equals("") || !m_family.getCompany().equals("")))
				{
					familyTable+=" "+m_family.getPosition()+" ";
					if(!m_family.getCompany().equals(""))
					{
						if(!m_family.getPosition().equals(""))
						{
							familyTable+=", ";
						}
						familyTable+=" "+m_family.getCompany();
					}
				}
				if(!m_family.getTelephone().equals("")){
					familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+m_family.getTelephone();
				}*/
			}
			
			//sibling
			if(siblingList != null && siblingList.size() > 0)
			{
				familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_NUMBER_OF_SIBLINGS")+":</b> "+siblingList.size();
				
				for(int c=0;c<siblingList.size();c++)
				{
					familyTable+="<br/>"+(c+1)+". "+viewInOriginal+" "+viewInOriginal;
					if(siblingList.get(c).getSiblingRelationship().equals("OLDER_SISTER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_OLDER_SISTER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("YOUNGER_SISTER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_YOUNGER_SISTER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("OLDER_BROTHER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_OLDER_BROTHER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("YOUNGER_BROTHER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_YOUNGER_BROTHER")+")";
					}
					if(siblingList.get(c).getAge()!=-1)
					{
						familyTable+=" "+propMgr.getMessage(locale,"FAMILY_AGE")+": "+siblingList.get(c).getAge();
					}
					if(siblingList.get(c).getWorkStatus().equals("LEARNING") && !siblingList.get(c).getCompany().equals(""))
					{
						familyTable+="<br/>"+propMgr.getMessage(locale,"FAMILY_EDUCATION")+": "+viewInOriginal;
					}
					else if(siblingList.get(c).getWorkStatus().equals("WORKING") && (!siblingList.get(c).getPosition().equals("") || !siblingList.get(c).getCompany().equals("")))
					{
						familyTable+="<br/>"+viewInOriginal+" ";
						if(!siblingList.get(c).getCompany().equals(""))
						{
							if(!siblingList.get(c).getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+viewInOriginal;
						}
					}
					if(!siblingList.get(c).getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+siblingList.get(c).getTelephone();
					}
				}
				
				//familyTable+="<br/><b>"+propMgr.getMessage(locale,"PREVIEW_FAMILY_NUMBER_OF_SIBLINGS")+":</b> "+siblingList.size();
				/*for(int c=0;c<siblingList.size();c++)
				{
					familyTable+="<br/>"+(c+1)+". "+siblingList.get(c).getFirstName()+" "+siblingList.get(c).getLastName();
					if(siblingList.get(c).getSiblingRelationship().equals("OLDER_SISTER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_OLDER_SISTER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("YOUNGER_SISTER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_YOUNGER_SISTER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("OLDER_BROTHER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_OLDER_BROTHER")+")";
					}
					else if(siblingList.get(c).getSiblingRelationship().equals("YOUNGER_BROTHER"))
					{
						familyTable+="("+propMgr.getMessage(locale,"FAMILY_SIBLING_YOUNGER_BROTHER")+")";
					}
					if(siblingList.get(c).getAge()!=-1)
					{
						familyTable+=" "+propMgr.getMessage(locale,"FAMILY_AGE")+": "+siblingList.get(c).getAge();
					}
					if(siblingList.get(c).getWorkStatus().equals("LEARNING") && !siblingList.get(c).getCompany().equals(""))
					{
						familyTable+="<br/>"+propMgr.getMessage(locale,"FAMILY_EDUCATION")+": "+siblingList.get(c).getCompany();
					}
					else if(siblingList.get(c).getWorkStatus().equals("WORKING") && (!siblingList.get(c).getPosition().equals("") || !siblingList.get(c).getCompany().equals("")))
					{
						familyTable+="<br/>"+siblingList.get(c).getPosition()+" ";
						if(!siblingList.get(c).getCompany().equals(""))
						{
							if(!siblingList.get(c).getPosition().equals(""))
							{
								familyTable+=", ";
							}
							familyTable+=" "+siblingList.get(c).getCompany();
						}
					}
					if(!siblingList.get(c).getTelephone().equals("")){
						familyTable+=", "+propMgr.getMessage(locale,"PREVIEW_TEL")+" "+siblingList.get(c).getTelephone();
					}
				}*/
			}
		}
		if(viewType==1){familyTable+="</font>";}
		if(isTranslate==true)
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_FAMILY_BACKGROUND#", propMgr.getMessage(locale, "PREVIEW_FAMILY_BACKGROUND"));
		replaceAll(result,"#FAMILY_BACKGROUND_DETAIL#", familyTable);
		replaceAll(result,"#BEGIN_FAMILY_BACKGROUND#", startComment);
		replaceAll(result,"#END_FAMILY_BACKGROUND#", endComment);
    }
	
	private void ReplaceFamilyBackgroundNotAllPart(Resume resume)
    {
		String familyTable="";
		String startComment="";
		String endComment="";
		Family f_family =new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 1);// dad
		Family m_family =new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), 2);// mom
		List<Family> siblingList=new FamilyManager().getFamilySibling(resume.getIdJsk(),resume.getIdResume());// sibling
		int idSpouse =new FamilyManager().getIdFamilyMarried(resume.getIdJsk(),resume.getIdResume());// id spouse 
		List<Family>childList =new FamilyManager().getFamilyChild(resume.getIdJsk(),resume.getIdResume()); // child  
		if(f_family==null && m_family==null && siblingList.size()==0 && childList.size()==0 && idSpouse==0)
		{
			startComment="<!--";
			endComment="-->";
		}
		else 
		{
			if(idSpouse != 0)
			{
				Family s_family = new FamilyManager().get(resume.getIdJsk(),resume.getIdResume(), idSpouse); 
				familyTable+="<b>"+propMgr.getMessage(resume.getLocale(),"FAMILY_MARRIED_STATUS")+": </b>";
				if(s_family.getMarriedStatus().equals("SINGLE"))
				{
					familyTable+=propMgr.getMessage(resume.getLocale(),"FAMILY_MARRIED_STATUS_SINGLE");
				}
				else if(s_family.getMarriedStatus().equals("MARRIED"))
				{
					familyTable+=propMgr.getMessage(resume.getLocale(),"FAMILY_MARRIED_STATUS_MARRIED");
				}
				else if(s_family.getMarriedStatus().equals("DIVORCED"))
				{
					familyTable+=propMgr.getMessage(resume.getLocale(),"FAMILY_MARRIED_STATUS_DIVORCED");
				}
			}
			if(childList.size() > 0)
			{
				familyTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_FAMILY_NUMBER_OF_CHILDREN")+":</b> "+childList.size();
			}
			if(siblingList.size() > 0)
			{
				familyTable+="<br/><b>"+propMgr.getMessage(resume.getLocale(),"PREVIEW_FAMILY_NUMBER_OF_SIBLINGS")+":</b> "+siblingList.size();
			}
		}
		replaceAll(result,"#PREVIEW_FAMILY_BACKGROUND#", propMgr.getMessage(resume.getLocale(), "PREVIEW_FAMILY_BACKGROUND"));
		replaceAll(result,"#FAMILY_BACKGROUND_DETAIL#", familyTable);
		replaceAll(result,"#BEGIN_FAMILY_BACKGROUND#", startComment);
		replaceAll(result,"#END_FAMILY_BACKGROUND#", endComment);
    }
	
	private void ReplaceEthnicity(Resume resume)
    {
		if(resume.getTemplateIdCountry() != 216) // 216 is Thailand
		{   
			String ethnicityTable="";
			String startComment="";
			String endComment="";
			Ethnicity et=new EthnicityManager().get(resume.getIdJsk(),resume.getIdResume());
			if(et!=null)
			{
				if(et.getEthnicity().equals("WHITE"))
				{
					ethnicityTable=propMgr.getMessage(resume.getLocale(),"ETHNICITY_WHITE");
				}
				else if(et.getEthnicity().equals("BLACK"))
				{
					ethnicityTable=propMgr.getMessage(resume.getLocale(),"ETHNICITY_BLACK");
				}
				else if(et.getEthnicity().equals("HISPANIC"))
				{
					ethnicityTable=propMgr.getMessage(resume.getLocale(),"ETHNICITY_HISPANIC");
				}
				else if(et.getEthnicity().equals("ASIAN"))
				{
					ethnicityTable=propMgr.getMessage(resume.getLocale(),"ETHNICITY_ASIAN");
				}
				else if(et.getEthnicity().equals("AMERICAN"))
				{
					ethnicityTable=propMgr.getMessage(resume.getLocale(),"ETHNICITY_AMERICAN");
				}
				else 
				{
					startComment="<!--";
					endComment="-->";
				}
				if(propMgr.getMessage(resume.getLocale(),"PREVIEW_ETHNICITY")!=null)
				{
					replaceAll(result,"#PREVIEW_ETHNICITY#", propMgr.getMessage(resume.getLocale(), "PREVIEW_ETHNICITY"));
					replaceAll(result,"#ETHNICITY_DETAIL#", ethnicityTable);
					replaceAll(result,"#BEGIN_ETHNICITY#", startComment);
					replaceAll(result,"#END_ETHNICITY#", endComment);
				}
			}
			else
			{
				replaceAll(result,"#BEGIN_ETHNICITY#", "<!--");
				replaceAll(result,"#END_ETHNICITY#", "-->");
			}
		}
		else
		{
			replaceAll(result,"#BEGIN_ETHNICITY#", "<!--");
			replaceAll(result,"#END_ETHNICITY#", "-->");
		}
    }
	
	private void ReplaceAdditionalInfo(Resume resume)
    {
		String additionalTable="";
		String addPersTable="";
		String startCommentAdd="";
		String endCommentAdd="";
		String startCommentPers="";
		String endCommentPers="";
		String additionalInfoAll="";
		AdditionalInfo additionalInfo=new AdditionalInfoManager().get(resume.getIdJsk(),resume.getIdResume());
		Additional additional=new AdditionalManager().get(resume.getIdJsk(),resume.getIdResume());
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		
		if(additionalInfo!=null)
		{
			String additionalInfo1 = Util.getStr(additionalInfo.getAdditional1());
			String additionalInfo2 = Util.getStr(additionalInfo.getAdditional2());
			String additionalInfo3 = Util.getStr(additionalInfo.getAdditional3());
			String additionalInfo4 = Util.getStr(additionalInfo.getAdditional4());
			additionalInfoAll=additionalInfo1+additionalInfo2+additionalInfo3+additionalInfo4;
		}
		if(!additionalInfoAll.trim().equals(""))
		{
			additionalTable=additionalInfoAll.trim().replace("\n", "<br/>");
		}
		else 
		{
			startCommentAdd="<!--";
			endCommentAdd="-->";
		}
		replaceAll(result,"#PREVIEW_ADDITIONAL_INFORMATION#", propMgr.getMessage(locale, "PREVIEW_ADDITIONAL_INFORMATION"));
		replaceAll(result,"#ADDITIONAL_INFORMATION_DETAIL#", additionalTable);
		replaceAll(result,"#BEGIN_ADDITIONAL_INFORMATION#", startCommentAdd);
		replaceAll(result,"#END_ADDITIONAL_INFORMATION#", endCommentAdd);
		if(additional!=null)
		{
			if(resume.getTemplateIdCountry() == 216) // THAILAND have additional personal data 
			{
				if(!Util.getStr(additional.getIdCard()).equals(""))
				{
					addPersTable+="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
					addPersTable+="<tr><td><b>- </b> </td><td><b>"+propMgr.getMessage(locale,"ADDITION_IDCARD")+":</b> "+additional.getIdCard()+"</td></tr>";
					addPersTable+="<tr><td><b>- </b></td><td><b>"+propMgr.getMessage(locale,"ADDITION_DRIVE_QUESTION")+":</b>";
					
					if(Util.getStr(additional.getDriveStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_DRIVE_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getDriveStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_DRIVE_STATUS_YES")+"</td></tr>";
						if(!Util.getStr(additional.getIdDrive()).equals(""))
						{
							addPersTable+="<tr><td><b>- </b></td><td><b>"+propMgr.getMessage(locale,"ADDITION_DRIVE_ID")+":</b> "+additional.getIdDrive()+"</td></tr>";
						}
					}
					
					addPersTable+="<tr><td><b>- </b></td><td><b>"+propMgr.getMessage(locale,"ADDITION_HADICAP_QUESTION")+":</b> ";
					if(Util.getStr(additional.getHandicapStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_HADICAP_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getHandicapStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_HADICAP_STATUS_YES");
						if(!Util.getStr(additional.getHandicapReason()).equals(""))
						{
							addPersTable+=", "+additional.getHandicapReason();
						}
						addPersTable+="</td></tr>";
					}
					
					addPersTable+="<tr><td><b>-</b></td><td><b> "+propMgr.getMessage(locale,"ADDITION_ILLNESS_QUESTION")+":</b> ";
					if(Util.getStr(additional.getIllnessStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_ILLNESS_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getIllnessStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_ILLNESS_STATUS_YES");
						if(!Util.getStr(additional.getIllnessReason()).equals(""))
						{
							addPersTable+=", "+additional.getIllnessReason();
						}
						addPersTable+="</td></tr>";
					}
					
					addPersTable+="<tr><td valign=top><b>-</b></td><td><b> "+propMgr.getMessage(locale,"ADDITION_FIRE_QUESTION")+":</b> ";
					if(Util.getStr(additional.getFireStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_FIRE_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getFireStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_FIRE_STATUS_YES");
						if(!Util.getStr(additional.getFireReason()).equals(""))
						{
							addPersTable+=", "+additional.getFireReason();
						}
						addPersTable+="</td></tr>";
					}
					
					addPersTable+="<tr><td valign=top><b>-</b></td><td><b> "+propMgr.getMessage(locale,"ADDITION_CRIMINAL_QUESTION")+":</b> ";
					if(Util.getStr(additional.getCriminalStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_CRIMINAL_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getCriminalStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_CRIMINAL_STATUS_YES");
						if(!Util.getStr(additional.getCriminalReason()).equals(""))
						{
							addPersTable+=", "+additional.getCriminalReason();
						}
						addPersTable+="</td></tr>";
					}
					addPersTable+="</table>";
				}
				else 
				{
					startCommentPers="<!--";
					endCommentPers="-->";
				}
				if(Util.getStr(resume.getSalutation()).equals("MR"))
				{
					addPersTable+="<b>-"+propMgr.getMessage(locale,"ADDITION_MILITARY")+":</b> ";
					if(Util.getStr(additional.getMilitaryStatus()).equals("0"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_PASS");
					}
					else if(Util.getStr(additional.getMilitaryStatus()).equals("1"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_WAIT");
					}
					else if(Util.getStr(additional.getMilitaryStatus()).equals("2"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_EXCEPT");
					}
					else if(Util.getStr(additional.getMilitaryStatus()).equals("3"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_RD");
					}
			
				}
				replaceAll(result,"#PREVIEW_ADDITIONAL_PERSONAL_DATA#", propMgr.getMessage(locale, "PREVIEW_ADDITIONAL_PERSONAL_DATA"));
				replaceAll(result,"#ADDITIONAL_PERSONAL_DATA_DETAIL#", addPersTable);
				replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", startCommentPers);
				replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", endCommentPers);
			}
			else //military status
			{
	
			    if(Util.getStr(additional.getMilitaryStatus()).equals("1"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ACTIVE");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("2"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_RESERV");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("3"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_NATIONAL");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("4"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_RETIRED");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("5"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ANG");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("6"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_IR");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("7"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ING");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("8"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_VETERAN");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("9"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_DISABLED");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("10"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SERVICE");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("11"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ROTC");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("12"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SSF");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("13"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SSR");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("14"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SOV");
				}
				else 
				{
					startCommentPers="<!--";
					endCommentPers="-->";
					
				}
				replaceAll(result,"#PREVIEW_MILITARY_STATUS#", propMgr.getMessage(locale, "PREVIEW_MILITARY_STATUS"));
				replaceAll(result,"#MILITARY_STATUS_DETAIL#", addPersTable);
				replaceAll(result,"#BEGIN_MILITARY_STATUS#", startCommentPers);
				replaceAll(result,"#END_MILITARY_STATUS#", endCommentPers);
				replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", "<!--");
				replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", "-->");
			}
		}
		else
		{
			replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", "<!--");
			replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", "-->");
		}
    }
	
	private void ReplaceAdditionalInfoHideOther(Resume resume)
    {
		String additionalTable="";
		String addPersTable="";
		String startCommentAdd="";
		String endCommentAdd="";
		String startCommentPers="";
		String endCommentPers="";
		String additionalInfoAll="";
		AdditionalInfo additionalInfo=new AdditionalInfoManager().get(resume.getIdJsk(),resume.getIdResume());
		Additional additional=new AdditionalManager().get(resume.getIdJsk(),resume.getIdResume());
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		
		if(additionalInfo!=null)
		{
			String additionalInfo1 = Util.getStr(additionalInfo.getAdditional1());
			String additionalInfo2 = Util.getStr(additionalInfo.getAdditional2());
			String additionalInfo3 = Util.getStr(additionalInfo.getAdditional3());
			String additionalInfo4 = Util.getStr(additionalInfo.getAdditional4());
			additionalInfoAll=additionalInfo1+additionalInfo2+additionalInfo3+additionalInfo4;
		}
		
		startCommentAdd="<!--";
		endCommentAdd="-->";
		
		if(!additionalInfoAll.trim().equals(""))
		{
			//additionalTable=additionalInfoAll.trim().replace("\n", "<br>");
			additionalTable = viewInOriginal;
		}
		else 
		{
			startCommentAdd="<!--";
			endCommentAdd="-->";
		}
		replaceAll(result,"#PREVIEW_ADDITIONAL_INFORMATION#", propMgr.getMessage(locale, "PREVIEW_ADDITIONAL_INFORMATION"));
		replaceAll(result,"#ADDITIONAL_INFORMATION_DETAIL#", additionalTable);
		replaceAll(result,"#BEGIN_ADDITIONAL_INFORMATION#", startCommentAdd);
		replaceAll(result,"#END_ADDITIONAL_INFORMATION#", endCommentAdd);
		if(additional!=null)
		{
			if(resume.getTemplateIdCountry() == 216) // THAILAND have additional personal data 
			{
				startCommentPers="<!--";
				endCommentPers="-->";
				
				if(!Util.getStr(additional.getIdCard()).equals(""))
				{
					addPersTable+="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
					addPersTable+="<tr><td><b>- </b> </td><td><b>"+propMgr.getMessage(locale,"ADDITION_IDCARD")+":</b> "+additional.getIdCard()+"</td></tr>";
					addPersTable+="<tr><td><b>- </b></td><td><b>"+propMgr.getMessage(locale,"ADDITION_DRIVE_QUESTION")+":</b>";
					
					if(Util.getStr(additional.getDriveStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_DRIVE_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getDriveStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_DRIVE_STATUS_YES")+"</td></tr>";
						if(!Util.getStr(additional.getIdDrive()).equals(""))
						{
							addPersTable+="<tr><td><b>- </b></td><td><b>"+propMgr.getMessage(locale,"ADDITION_DRIVE_ID")+":</b> "+additional.getIdDrive()+"</td></tr>";
						}
					}
					
					addPersTable+="<tr><td><b>- </b></td><td><b>"+propMgr.getMessage(locale,"ADDITION_HADICAP_QUESTION")+":</b> ";
					if(Util.getStr(additional.getHandicapStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_HADICAP_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getHandicapStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_HADICAP_STATUS_YES");
						if(!Util.getStr(additional.getHandicapReason()).equals(""))
						{
							addPersTable+=", "+viewInOriginal;
						}
						addPersTable+="</td></tr>";
					}
					
					addPersTable+="<tr><td><b>-</b></td><td><b> "+propMgr.getMessage(locale,"ADDITION_ILLNESS_QUESTION")+":</b> ";
					if(Util.getStr(additional.getIllnessStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_ILLNESS_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getIllnessStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_ILLNESS_STATUS_YES");
						if(!Util.getStr(additional.getIllnessReason()).equals(""))
						{
							addPersTable+=", "+viewInOriginal;
						}
						addPersTable+="</td></tr>";
					}
					
					addPersTable+="<tr><td valign=top><b>-</b></td><td><b> "+propMgr.getMessage(locale,"ADDITION_FIRE_QUESTION")+":</b> ";
					if(Util.getStr(additional.getFireStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_FIRE_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getFireStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_FIRE_STATUS_YES");
						if(!Util.getStr(additional.getFireReason()).equals(""))
						{
							addPersTable+=", "+viewInOriginal;
						}
						addPersTable+="</td></tr>";
					}
					
					addPersTable+="<tr><td valign=top><b>-</b></td><td><b> "+propMgr.getMessage(locale,"ADDITION_CRIMINAL_QUESTION")+":</b> ";
					if(Util.getStr(additional.getCriminalStatus()).equals("FALSE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_CRIMINAL_STATUS_NO")+"</td></tr>";
					}
					else if(Util.getStr(additional.getCriminalStatus()).equals("TRUE"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_CRIMINAL_STATUS_YES");
						if(!Util.getStr(additional.getCriminalReason()).equals(""))
						{
							addPersTable+=", "+viewInOriginal;
						}
						addPersTable+="</td></tr>";
					}
					addPersTable+="</table>";
				}
				else 
				{
					startCommentPers="<!--";
					endCommentPers="-->";
				}
				if(Util.getStr(resume.getSalutation()).equals("MR"))
				{
					addPersTable+="<b>-"+propMgr.getMessage(locale,"ADDITION_MILITARY")+":</b> ";
					if(Util.getStr(additional.getMilitaryStatus()).equals("0"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_PASS");
					}
					else if(Util.getStr(additional.getMilitaryStatus()).equals("1"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_WAIT");
					}
					else if(Util.getStr(additional.getMilitaryStatus()).equals("2"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_EXCEPT");
					}
					else if(Util.getStr(additional.getMilitaryStatus()).equals("3"))
					{
						addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_RD");
					}
			
				}
				replaceAll(result,"#PREVIEW_ADDITIONAL_PERSONAL_DATA#", propMgr.getMessage(locale, "PREVIEW_ADDITIONAL_PERSONAL_DATA"));
				replaceAll(result,"#ADDITIONAL_PERSONAL_DATA_DETAIL#", addPersTable);
				replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", startCommentPers);
				replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", endCommentPers);
			}
			else //military status
			{
	
			    if(Util.getStr(additional.getMilitaryStatus()).equals("1"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ACTIVE");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("2"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_RESERV");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("3"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_NATIONAL");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("4"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_RETIRED");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("5"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ANG");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("6"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_IR");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("7"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ING");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("8"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_VETERAN");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("9"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_DISABLED");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("10"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SERVICE");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("11"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_ROTC");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("12"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SSF");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("13"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SSR");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("14"))
				{
					addPersTable+=propMgr.getMessage(locale,"ADDITION_MILITARY_STATUS_SOV");
				}
				else 
				{
					startCommentPers="<!--";
					endCommentPers="-->";
					
				}
				replaceAll(result,"#PREVIEW_MILITARY_STATUS#", propMgr.getMessage(locale, "PREVIEW_MILITARY_STATUS"));
				replaceAll(result,"#MILITARY_STATUS_DETAIL#", addPersTable);
				replaceAll(result,"#BEGIN_MILITARY_STATUS#", startCommentPers);
				replaceAll(result,"#END_MILITARY_STATUS#", endCommentPers);
				replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", "<!--");
				replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", "-->");
			}
		}
		else
		{
			replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", "<!--");
			replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", "-->");
		}
    }
	
	private void ReplaceAdditionalInfoNotAllPart(Resume resume)
    {
		String additionalTable="";
		String addPersTable="";
		String startCommentAdd="";
		String endCommentAdd="";
		String startCommentPers="";
		String endCommentPers="";
		String locale= "en_TH";
        
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}

		
		AdditionalInfo additionalInfo=new AdditionalInfoManager().get(resume.getIdJsk(),resume.getIdResume());
		Additional additional=new AdditionalManager().get(resume.getIdJsk(),resume.getIdResume());
	
		String additionalInfo1="";
		String additionalInfo2="";
		String additionalInfo3="";
		String additionalInfo4="";
		if(additionalInfo!=null)
		{
			additionalInfo1 = Util.getStr(additionalInfo.getAdditional1());
			additionalInfo2 = Util.getStr(additionalInfo.getAdditional2());
			additionalInfo3 = Util.getStr(additionalInfo.getAdditional3());
			additionalInfo4 = Util.getStr(additionalInfo.getAdditional4());
		}
		String additionalInfoAll=additionalInfo1+additionalInfo2+additionalInfo3+additionalInfo4;
		if(!Util.getStr(additionalInfoAll).trim().equals(""))
		{
			additionalTable=additionalInfoAll.trim().replace("\n", "<br>");
		}
		else 
		{
			startCommentAdd="<!--";
			endCommentAdd="-->";
		}
		replaceAll(result,"#PREVIEW_ADDITIONAL_INFORMATION#", propMgr.getMessage(locale, "PREVIEW_ADDITIONAL_INFORMATION"));
		replaceAll(result,"#ADDITIONAL_INFORMATION_DETAIL#", additionalTable);
		replaceAll(result,"#BEGIN_ADDITIONAL_INFORMATION#", startCommentAdd);
		replaceAll(result,"#END_ADDITIONAL_INFORMATION#", endCommentAdd);
		if(resume.getTemplateIdCountry() == 216 && additional!=null) // THAILAND have additional personal data 
		{
			if(!Util.getStr(additional.getIdCard()).equals(""))
			{
				addPersTable+="<b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_IDCARD")+":</b> "+additional.getIdCard();
				addPersTable+="<br/><b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_QUESTION")+":</b> ";
				if(Util.getStr(additional.getDriveStatus()).equals("FALSE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_STATUS_NO");
				}
				else if(Util.getStr(additional.getDriveStatus()).equals("TRUE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_STATUS_YES");
					if(!Util.getStr(additional.getIdDrive()).equals(""))
					{
						addPersTable+="<br/><b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_DRIVE_ID")+":</b> "+additional.getIdDrive();
					}
				}
				
				addPersTable+="<br/><b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_HADICAP_QUESTION")+":</b> ";
				if(Util.getStr(additional.getHandicapStatus()).equals("FALSE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_HADICAP_STATUS_NO");
				}
				else if(Util.getStr(additional.getHandicapStatus()).equals("TRUE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_HADICAP_STATUS_YES");
					if(!Util.getStr(additional.getHandicapReason()).equals(""))
					{
						addPersTable+=", "+additional.getHandicapReason();
					}
				}
				
				addPersTable+="<br/><b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_ILLNESS_QUESTION")+":</b> ";
				if(Util.getStr(additional.getIllnessStatus()).equals("FALSE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_ILLNESS_STATUS_NO");
				}
				else if(Util.getStr(additional.getIllnessStatus()).equals("TRUE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_ILLNESS_STATUS_YES");
					if(!Util.getStr(additional.getIllnessReason()).equals(""))
					{
						addPersTable+=", "+additional.getIllnessReason();
					}
				}
				
				addPersTable+="<br/><b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_FIRE_QUESTION")+":</b> ";
				if(additional.getFireStatus().equals("FALSE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_FIRE_STATUS_NO");
				}
				else if(additional.getFireStatus().equals("TRUE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_FIRE_STATUS_YES");
					if(!Util.getStr(additional.getFireReason()).equals(""))
					{
						addPersTable+=", "+additional.getFireReason();
					}
				}
				
				addPersTable+="<br/><b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_CRIMINAL_QUESTION")+":</b> ";
				if(Util.getStr(additional.getCriminalStatus()).equals("FALSE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_CRIMINAL_STATUS_NO");
				}
				else if(Util.getStr(additional.getCriminalStatus()).equals("TRUE"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_CRIMINAL_STATUS_YES");
					if(!Util.getStr(additional.getCriminalReason()).equals(""))
					{
						addPersTable+=", "+additional.getCriminalReason();
					}
				}
			}
			else 
			{
				startCommentPers="<!--";
				endCommentPers="-->";
			}
			
			if(resume.getSalutation().equals("MR") && additional!=null)
			{
				addPersTable+="<br/><b>- "+propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY")+":</b> ";
				if(Util.getStr(additional.getMilitaryStatus()).equals("0"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_PASS");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("1"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_WAIT");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("2"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_EXCEPT");
				}
				else if(Util.getStr(additional.getMilitaryStatus()).equals("3"))
				{
					addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_RD");
				}
			}
			replaceAll(result,"#PREVIEW_ADDITIONAL_PERSONAL_DATA#", propMgr.getMessage(resume.getLocale(), "PREVIEW_ADDITIONAL_PERSONAL_DATA"));
			replaceAll(result,"#ADDITIONAL_PERSONAL_DATA_DETAIL#", addPersTable);
			replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", startCommentPers);
			replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", endCommentPers);
		}
		else if(additional!=null) //military status
		{

		    if(Util.getStr(additional.getMilitaryStatus()).equals("1"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ACTIVE");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("2"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_RESERV");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("3"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_NATIONAL");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("4"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_RETIRED");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("5"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ANG");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("6"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_IR");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("7"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ING");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("8"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_VETERAN");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("9"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_DISABLED");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("10"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SERVICE");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("11"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_ROTC");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("12"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SSF");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("13"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SSR");
			}
			else if(Util.getStr(additional.getMilitaryStatus()).equals("14"))
			{
				addPersTable+=propMgr.getMessage(resume.getLocale(),"ADDITION_MILITARY_STATUS_SOV");
			}
			else 
			{
				startCommentPers="<!--";
				endCommentPers="-->";
				
			}
			replaceAll(result,"#PREVIEW_MILITARY_STATUS#", propMgr.getMessage(resume.getLocale(), "PREVIEW_MILITARY_STATUS"));
			replaceAll(result,"#MILITARY_STATUS_DETAIL#", addPersTable);
			replaceAll(result,"#BEGIN_MILITARY_STATUS#", startCommentPers);
			replaceAll(result,"#END_MILITARY_STATUS#", endCommentPers);
			replaceAll(result,"#BEGIN_ADDITIONAL_PERSONAL_DATA#", "<!--");
			replaceAll(result,"#END_ADDITIONAL_PERSONAL_DATA#", "-->");
		}
    }
	
	private void ReplaceActivities(Resume resume)
    {
		List <Activity> activityList=new ActivityManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String activityTableHead="";
		String activityTable="";
		String startComment="";
		String endComment="";
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		String locale= "en_TH";
        
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		if(activityList!=null && activityList.size()>0)
		{
			activityTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
			for(int c=0;c<activityList.size();c++)
			{
				if(activityList.get(c)!=null)
				{
					activityTable+="<tr><td>";
					activityTable+="<B>"+(c+1)+". "+Util.getStr(activityList.get(c).getClub())+"</B>";
					if(!Util.getStr(activityList.get(c).getPosition()).equals(""))
					{
						activityTable+="("+activityList.get(c).getPosition()+")";
					}
					if(aLanguage.equals("ja"))
					{
						if (!Util.getStr(activityList.get(c).getStartDate()).equals(""))
						{
							activityTable +="<br/>"+ Util.getLocaleDateFormatFull(activityList.get(c).getStartDate(),"FULL",aLanguage,aCountry) + " - ";
						}
						if (activityList.get(c).getEndDate() != null)
						{
							activityTable += Util.getLocaleDateFormatFull(activityList.get(c).getEndDate(),"FULL",aLanguage,aCountry);
						}
					}
					else 
					{
						if (activityList.get(c).getStartDate() != null)
						{
							activityTable +="<br/>"+ Util.getLocaleDateFormatFull(activityList.get(c).getStartDate(),"MEDIUM",aLanguage,aCountry) + " - ";
						}
						if (activityList.get(c).getEndDate() != null)
						{
							activityTable += Util.getLocaleDateFormatFull(activityList.get(c).getEndDate(),"MEDIUM",aLanguage,aCountry);
						}
					}
					if(!Util.getStr(activityList.get(c).getDescription()).equals(""))
					{
						activityTable+="<br/>"+Util.getStr(activityList.get(c).getDescription()).replace("\n", "<br/>");
					}
					activityTable+="</td></tr>";
				}
			}
			activityTable=activityTableHead+activityTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_ACTIVITIES#", propMgr.getMessage(locale, "PREVIEW_ACTIVITIES"));
		replaceAll(result,"#ACTIVITIES_DETAIL#", activityTable);
		replaceAll(result,"#BEGIN_ACTIVITY#", startComment);
		replaceAll(result,"#END_ACTIVITY#", endComment);
    }
	
	private void ReplaceActivitiesHideOther(Resume resume)
    {
		List <Activity> activityList=new ActivityManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String activityTableHead="";
		String activityTable="";
		String startComment="";
		String endComment="";
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		String locale= "en_TH";
        
		startComment = "<!--";
		endComment = "-->";
		
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		if(activityList!=null && activityList.size()>0)
		{
			activityTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
			

			for(int c=0;c<activityList.size();c++)
			{
				if(activityList.get(c)!=null)
				{
					activityTable+="<tr><td>";
					//activityTable+="<B>"+(c+1)+". "+Util.getStr(activityList.get(c).getClub())+"</B>";
					activityTable+="<B>"+(c+1)+". "+viewInOriginal+"</B>";
					if(!Util.getStr(activityList.get(c).getPosition()).equals(""))
					{
						activityTable += viewInOriginal;
						//activityTable+="("+activityList.get(c).getPosition()+")";
					}
					if(aLanguage.equals("ja"))
					{
						if (!Util.getStr(activityList.get(c).getStartDate()).equals(""))
						{
							activityTable +="<br/>"+ Util.getLocaleDateFormatFull(activityList.get(c).getStartDate(),"FULL",aLanguage,aCountry) + " - ";
						}
						if (activityList.get(c).getEndDate() != null)
						{
							activityTable += Util.getLocaleDateFormatFull(activityList.get(c).getEndDate(),"FULL",aLanguage,aCountry);
						}
					}
					else 
					{
						if (activityList.get(c).getStartDate() != null)
						{
							activityTable +="<br/>"+ Util.getLocaleDateFormatFull(activityList.get(c).getStartDate(),"MEDIUM",aLanguage,aCountry) + " - ";
						}
						if (activityList.get(c).getEndDate() != null)
						{
							activityTable += Util.getLocaleDateFormatFull(activityList.get(c).getEndDate(),"MEDIUM",aLanguage,aCountry);
						}
					}
					if(!Util.getStr(activityList.get(c).getDescription()).equals(""))
					{
						//activityTable+="<br/>"+Util.getStr(activityList.get(c).getDescription()).replace("\n", "<br/>");
						activityTable += "<br/>"+viewInOriginal+"<br/>";
					}
					activityTable+="</td></tr>";
				}
			}
			activityTable=activityTableHead+activityTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_ACTIVITIES#", propMgr.getMessage(locale, "PREVIEW_ACTIVITIES"));
		replaceAll(result,"#ACTIVITIES_DETAIL#", activityTable);
		replaceAll(result,"#BEGIN_ACTIVITY#", startComment);
		replaceAll(result,"#END_ACTIVITY#", endComment);
    }
	
	private void ReplaceCertificate(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Certificate> certificateList =  new CertificateManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String certificateTableHead="";
		String certificateTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
        
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}

		if(certificateList.size()>0)
		{
			certificateTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
			for(int c=0;c<certificateList.size();c++)
			{	
				int idCertField = certificateList.get(c).getIdCertField();
				int idCertSubfield = certificateList.get(c).getIdCertSubfield();
				CertFieldLanguage cf =MasterDataManager.getCertFieldLanguage(idCertField, resume.getIdLanguage());
				CertSubFieldLanguage sf = MasterDataManager.getCertSubFieldLanguage(idCertField, resume.getIdLanguage(), idCertSubfield);
				String cerNameField=(cf!=null)?Util.getStr(cf.getCertFieldName()):"";
				String subNameField =(sf!=null)?Util.getStr(sf.getCertSubfieldName()):"";
				String cerName = certificateList.get(c).getCerName();
				certificateTable+="<tr><td>";
				if(cerNameField == "" && subNameField == ""){// idCertField=-1 && idCertSubfield =-1
					certificateTable+="<B>"+(c+1)+". "+cerName;
				}else
				if(cerNameField != "" && subNameField != ""){// idCertField!=-1 && idCertSubfield !=-1
					certificateTable+="<B>"+(c+1)+". "+cerNameField+" / "+subNameField;
				}else
				if(cerNameField != "" && subNameField == ""){// idCertField!=-1 && idCertSubfield =-1
					certificateTable+="<B>"+(c+1)+". "+cerNameField+" / "+cerName;
				}
				if(!certificateList.get(c).getInstitution().equals(""))
				{
					certificateTable+=", "+certificateList.get(c).getInstitution();
				}
				certificateTable+="</B>";
				if(resume.getIdLanguage() == 23)
				{
					if (certificateList.get(c).getCerDate() != null)
					{
						certificateTable+="<br/>"+Util.getLocaleDate(certificateList.get(c).getCerDate(), "yyyy年MMMM",aLanguage,aCountry);
					}
				}
				else  // 11 = English
				{
					if (certificateList.get(c).getCerDate() != null)
					{
						certificateTable +="<br/>"+ Util.getLocaleDate(certificateList.get(c).getCerDate(), "MMM yyyy",aLanguage,aCountry);
					}
				}
				if(!certificateList.get(c).getDetail().equals(""))
				{
					certificateTable+="<br/>"+certificateList.get(c).getDetail().replace("\n", "<br/>");
				}
				certificateTable+="</td></tr>";
			}
			certificateTable=certificateTableHead+certificateTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_CERTIFICATE#", propMgr.getMessage(locale, "PREVIEW_CERTIFICATE"));
		replaceAll(result,"#CERTIFICATE_DETAIL#", certificateTable);
		replaceAll(result,"#BEGIN_CERTIFICATE#", startComment);
		replaceAll(result,"#END_CERTIFICATE#", endComment);
    }
	
	private void ReplaceCertificateOther(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Certificate> certificateList =  new CertificateManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String certificateTableHead="";
		String certificateTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		
		startComment = "<!--";
		endComment = "-->";
        
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}

		if(certificateList.size()>0)
		{
			certificateTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
						
			for(int c=0;c<certificateList.size();c++)
			{	
				int idCertField = certificateList.get(c).getIdCertField();
				int idCertSubfield = certificateList.get(c).getIdCertSubfield();
				CertFieldLanguage cf =MasterDataManager.getCertFieldLanguage(idCertField, resume.getIdLanguage());
				CertSubFieldLanguage sf = MasterDataManager.getCertSubFieldLanguage(idCertField, resume.getIdLanguage(), idCertSubfield);
				String cerNameField=(cf!=null)?Util.getStr(cf.getCertFieldName()):"";
				String subNameField =(sf!=null)?Util.getStr(sf.getCertSubfieldName()):"";
				String cerName = certificateList.get(c).getCerName();
				certificateTable+="<tr><td>";
				if(cerNameField == "" && subNameField == ""){// idCertField=-1 && idCertSubfield =-1
					//certificateTable+="<B>"+(c+1)+". "+cerName;
					certificateTable+="<B>"+(c+1)+". "+viewInOriginal;
				}else
				if(cerNameField != "" && subNameField != ""){// idCertField!=-1 && idCertSubfield !=-1
					//certificateTable+="<B>"+(c+1)+". "+cerNameField+" / "+subNameField;
					certificateTable+="<B>"+(c+1)+". "+viewInOriginal+" / "+viewInOriginal;
				}else
				if(cerNameField != "" && subNameField == ""){// idCertField!=-1 && idCertSubfield =-1
					//certificateTable+="<B>"+(c+1)+". "+cerNameField+" / "+cerName;
					certificateTable+="<B>"+(c+1)+". "+viewInOriginal+" / "+viewInOriginal;
				}
				if(!certificateList.get(c).getInstitution().equals(""))
				{
					//certificateTable+=", "+certificateList.get(c).getInstitution();
					certificateTable+=", "+viewInOriginal;
				}
				certificateTable+="</B>";
				if(resume.getIdLanguage() == 23)
				{
					if (certificateList.get(c).getCerDate() != null)
					{
						certificateTable+="<br/>"+Util.getLocaleDate(certificateList.get(c).getCerDate(), "yyyy年MMMM",aLanguage,aCountry);
					}
				}
				else  // 11 = English
				{
					if (certificateList.get(c).getCerDate() != null)
					{
						certificateTable +="<br/>"+ Util.getLocaleDate(certificateList.get(c).getCerDate(), "MMM yyyy",aLanguage,aCountry);
					}
				}
				if(!certificateList.get(c).getDetail().equals(""))
				{
					//certificateTable+="<br/>"+certificateList.get(c).getDetail().replace("\n", "<br/>");
					certificateTable+="<br/>"+viewInOriginal;
				}
				certificateTable+="</td></tr>";
			}
			certificateTable=certificateTableHead+certificateTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_CERTIFICATE#", propMgr.getMessage(locale, "PREVIEW_CERTIFICATE"));
		replaceAll(result,"#CERTIFICATE_DETAIL#", certificateTable);
		replaceAll(result,"#BEGIN_CERTIFICATE#", startComment);
		replaceAll(result,"#END_CERTIFICATE#", endComment);
    }
	
	private void ReplaceAward(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Award> awardList=new AwardManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String awardTableHead="";
		String awardTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
        
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}

		if(awardList.size()>0)
		{
			awardTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
			for(int c=0;c<awardList.size();c++)
			{
				awardTable+="<tr><td>";
				awardTable+="<B>"+(c+1)+". "+awardList.get(c).getAwardName();
				if(!awardList.get(c).getInstitute().equals(""))
				{
					awardTable+=", "+awardList.get(c).getInstitute();
				}
				awardTable+="</B>";
				if(resume.getIdLanguage() == 23)
				{
					if (awardList.get(c).getAwardDate() != null)
					{
						awardTable +="<br/>"+Util.getLocaleDate(awardList.get(c).getAwardDate(), "yyyy年MMMM",aLanguage,aCountry);
					}
				}
				else  // 11 = English
				{
					if (awardList.get(c).getAwardDate() != null)
					{
						awardTable +="<br/>"+ Util.getLocaleDate(awardList.get(c).getAwardDate(), "MMM yyyy",aLanguage,aCountry);
					}
				}
				if(!awardList.get(c).getDetail().equals(""))
				{
					awardTable+="<br/>"+awardList.get(c).getDetail().replace("\n", "<br/>");
				}
				awardTable+="</td></tr>";
			}
			awardTable=awardTableHead+awardTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_AWARD#", propMgr.getMessage(locale, "PREVIEW_AWARD"));
		replaceAll(result,"#AWARD_DETAIL#", awardTable);
		replaceAll(result,"#BEGIN_AWARD#", startComment);
		replaceAll(result,"#END_AWARD#", endComment);
    }
	
	private void ReplaceAwardHideOther(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Award> awardList=new AwardManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String awardTableHead="";
		String awardTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
        
		startComment = "<!--";
		endComment = "-->";
		
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}

		if(awardList.size()>0)
		{
			awardTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
			for(int c=0;c<awardList.size();c++)
			{
				awardTable+="<tr><td>";
				//awardTable+="<B>"+(c+1)+". "+awardList.get(c).getAwardName();
				awardTable+="<B>"+(c+1)+". "+viewInOriginal;
				if(!awardList.get(c).getInstitute().equals(""))
				{
					//awardTable+=", "+awardList.get(c).getInstitute();
					awardTable+=", "+viewInOriginal;
				}
				awardTable+="</B>";
				if(resume.getIdLanguage() == 23)
				{
					if (awardList.get(c).getAwardDate() != null)
					{
						awardTable +="<br/>"+Util.getLocaleDate(awardList.get(c).getAwardDate(), "yyyy年MMMM",aLanguage,aCountry);
					}
				}
				else  // 11 = English
				{
					if (awardList.get(c).getAwardDate() != null)
					{
						awardTable +="<br/>"+ Util.getLocaleDate(awardList.get(c).getAwardDate(), "MMM yyyy",aLanguage,aCountry);
					}
				}
				if(!awardList.get(c).getDetail().equals(""))
				{
					//awardTable+="<br>"+awardList.get(c).getDetail().replace("\n", "<br>");
					awardTable+="<br/>"+viewInOriginal;
				}
				awardTable+="</td></tr>";
			}
			awardTable=awardTableHead+awardTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_AWARD#", propMgr.getMessage(locale, "PREVIEW_AWARD"));
		replaceAll(result,"#AWARD_DETAIL#", awardTable);
		replaceAll(result,"#BEGIN_AWARD#", startComment);
		replaceAll(result,"#END_AWARD#", endComment);
    }
	
	private void ReplaceTraining(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Training> trainingList = new TrainingManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String trainingTableHead="";
		String trainingTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
		if(trainingList.size()!=0)
		{
			trainingTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
			for(int c=0;c<trainingList.size();c++)
			{
				trainingTable+="<tr><td>";
				trainingTable+="<B>"+(c+1)+". "+trainingList.get(c).getCourseName();
				if(!trainingList.get(c).getInstitute().equals(""))
				{
					trainingTable+=", "+trainingList.get(c).getInstitute();
				}
				trainingTable+="</B>";

			if(aLanguage.equals("ja"))
			{
				if(trainingList.get(c).getStartDate()!=null)
				{
					trainingTable +="<br/>"+ Util.getLocaleDateFormatFull(trainingList.get(c).getStartDate(),"FULL",aLanguage,aCountry) + " - ";
				}
				if(trainingList.get(c).getEndDate()!=null)
				{
					trainingTable += Util.getLocaleDateFormatFull(trainingList.get(c).getEndDate(),"FULL",aLanguage,aCountry);
				}
			}
			else 
			{
				if(trainingList.get(c).getStartDate()!=null)
				{
					trainingTable +="<br/>"+ Util.getLocaleDateFormatFull(trainingList.get(c).getStartDate(),"MEDIUM",aLanguage,aCountry) + " - ";
				}
				if(trainingList.get(c).getEndDate()!=null)
				{
					trainingTable += Util.getLocaleDateFormatFull(trainingList.get(c).getEndDate(),"MEDIUM",aLanguage,aCountry);
				}
			}
			
				if(!trainingList.get(c).getCourseDesc().equals(""))
				{
					trainingTable+="<br/>"+trainingList.get(c).getCourseDesc().replace("\n", "<br/>");
				}
				
				trainingTable+="</td></tr>";
			}
			trainingTable=trainingTableHead+trainingTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		if(isTranslate==true)
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_TRAINING#", propMgr.getMessage(locale, "PREVIEW_TRAINING"));
		replaceAll(result,"#TRAINING_DETAIL#", trainingTable);
		replaceAll(result,"#BEGIN_TRAINING#", startComment);
		replaceAll(result,"#END_TRAINING#", endComment);
    }
	
	private void ReplaceTrainingHideOther(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		List<Training> trainingList = new TrainingManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String trainingTableHead="";
		String trainingTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
	    
		startComment = "<!--";
		endComment = "-->";
		
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
		if(trainingList.size()!=0)
		{
			trainingTableHead="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"f11\" >";
			for(int c=0;c<trainingList.size();c++)
			{
				trainingTable+="<tr><td>";
				//trainingTable+="<B>"+(c+1)+". "+trainingList.get(c).getCourseName();
				trainingTable+="<B>"+(c+1)+". "+viewInOriginal;
				if(!trainingList.get(c).getInstitute().equals(""))
				{
					//trainingTable+=", "+trainingList.get(c).getInstitute();
					trainingTable+=", "+viewInOriginal;
				}
				trainingTable+="</B>";

			if(aLanguage.equals("ja"))
			{
				if(trainingList.get(c).getStartDate()!=null)
				{
					trainingTable +="<br/>"+ Util.getLocaleDateFormatFull(trainingList.get(c).getStartDate(),"FULL",aLanguage,aCountry) + " - ";
				}
				if(trainingList.get(c).getEndDate()!=null)
				{
					trainingTable += Util.getLocaleDateFormatFull(trainingList.get(c).getEndDate(),"FULL",aLanguage,aCountry);
				}
			}
			else 
			{
				if(trainingList.get(c).getStartDate()!=null)
				{
					trainingTable +="<br/>"+ Util.getLocaleDateFormatFull(trainingList.get(c).getStartDate(),"MEDIUM",aLanguage,aCountry) + " - ";
				}
				if(trainingList.get(c).getEndDate()!=null)
				{
					trainingTable += Util.getLocaleDateFormatFull(trainingList.get(c).getEndDate(),"MEDIUM",aLanguage,aCountry);
				}
			}
			
				if(!trainingList.get(c).getCourseDesc().equals(""))
				{
					//trainingTable+="<br>"+trainingList.get(c).getCourseDesc().replace("\n", "<br>");
					trainingTable+="<br>"+viewInOriginal;
				}
				
				trainingTable+="</td></tr>";
			}
			trainingTable=trainingTableHead+trainingTable+"</table>";
		}
		else
		{
			startComment="<!--";
			endComment="-->";
		}
		if(isTranslate==true)
		{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#PREVIEW_TRAINING#", propMgr.getMessage(locale, "PREVIEW_TRAINING"));
		replaceAll(result,"#TRAINING_DETAIL#", trainingTable);
		replaceAll(result,"#BEGIN_TRAINING#", startComment);
		replaceAll(result,"#END_TRAINING#", endComment);
    }
	
	private void ReplaceSkill(Resume resume)
    {
		String langTable="";
		String langTableHead="";
		String comTable="";
		String comTableHead="";
		String scoreTable="";
		String scoreTableHead="";
		String otherTable="";
		String otherTableHead="";
		String otherScoreTable="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
	    else
	    {
	    	locale= "en_TH";
	    }
		List<SkillComputer> coms=new SkillComputerManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<SkillLanguageScore> testOther=new SkillLanguageScoreManager().getOtherAll(resume.getIdJsk(),resume.getIdResume());
		List<SkillLanguage> langs=new SkillLanguageManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<SkillOther> others=new SkillOtherManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String languageHeader = "";
		String computerHeader = "";
		if(langs.size()>0 || testOther.size()>0)
		{
			languageHeader = propMgr.getMessage(locale, "GLOBAL_LANGUAGE");
		}
		if(coms.size()>0 || others.size()>0)
		{
			computerHeader = propMgr.getMessage(locale, "GLOBAL_COMPUTER");
		}
		SkillLanguageScore toefl=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"TOEFL");
		SkillLanguageScore ielts=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"IELTS");
		SkillLanguageScore toeic=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"TOEIC");
		if(coms.size()==0 && langs.size()==0 && others.size()==0 && toefl==null && ielts==null && toeic==null)
		{
			startComment="<!--";
			endComment="-->";
		}
		else 
		{
			if(resume.getTemplateIdCountry() == 216||resume.getTemplateIdCountry() == 151)  // THAILAND or MYANMAR
			{
				if(langs.size()>0)
				{
					langTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
				
					for(int c=0;c<langs.size();c++)
					{
						langTable+="<TR><TD vAlign=top width=80 rowSpan=4><span class=\"f11\">";
						if(langs.get(c).getIdSkillLang()>0)
						{
							langTable+=MasterDataManager.getLanguageName(langs.get(c).getIdSkillLang(), resume.getIdLanguage());
						}
						else 
						{
							langTable+=langs.get(c).getOthLang();
						}
						langTable+="</span></TD>"+
			            "<TD width=80 class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+":</TD>"+
			            "<TD width=80 class=\"f11\">";
						if(langs.get(c).getReading() != 0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getReading()));
						}
						langTable+="</TD>"+
			            "</TR>"+
			            "<TR>"+
			            "<TD class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+":</TD>"+
			            "<TD class=\"f11\">";
						if(langs.get(c).getListening() !=0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getListening()));
						}
						langTable+="</TD>"+
			            "</TR>"+
			            "<TR>"+
			            "<TD class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+":</TD>"+
			            "<TD class=\"f11\">";
						if(langs.get(c).getWriting() != 0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getWriting()));
						}
						langTable+="</TD>" +
			            "</TR>"+
			            "<TR>"+
			            "<TD class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+":</TD>"+
			            "<TD class=\"f11\">";
						if(langs.get(c).getSpeaking() != 0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getSpeaking()));
						}
						langTable+="</TD>"+
			            "</TR>";
					}
				     langTable=langTableHead+langTable+"</TABLE>";
				}	
			}
			else  // other country
			{
				if(langs.size()>0)
				{
					langTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
				
					for(int c=0;c<langs.size();c++)
					{
						langTable+="<TR><TD vAlign=top width=80 ><B class=\"f11\">"; 
						if(langs.get(c).getIdSkillLang()>0)
						{
							langTable+=MasterDataManager.getLanguageName(langs.get(c).getIdSkillLang(), resume.getIdLanguage());
						}
						else 
						{
							langTable+=langs.get(c).getOthLang();
						}
						langTable+="</B></TD>"+
			            "<TD width=80 class=\"f11\">";
						if(langs.get(c).getLevelSkill() != 0 )
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getLevelSkill()));
						}
						langTable+="</TD>"+
			            "</TR>";
					}
				     langTable=langTableHead+langTable+"</TABLE>";
				     
				
				}
			}
            if(coms.size()>0)
            {
            	comTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
				for(int c=0;c<coms.size();c++)
				{
					com.topgun.shris.masterdata.Computer aCom=MasterDataManager.getComputer(coms.get(c).getIdGroup(), coms.get(c).getIdComputer());
					comTable+="<TR><TD vAlign=top width=80 class=\"f11\">"; 
					if(aCom!=null)
					{
						comTable+=aCom.getComputerName();
					}
					else 
					{
						comTable+=coms.get(c).getOthComputer();
					}
					comTable+="</TD>"+
		            "<TD width=80 class=\"f11\">";
					if(coms.get(c).getLevelSkill() != 0)
					{
						comTable+=propMgr.getMessage(locale,proficiencyComAndSkill(coms.get(c).getLevelSkill()))+"</TD>";
					}
					comTable+="</TR>";
				}
			     comTable=comTableHead+comTable+"</TABLE>";
            } 
            if((toefl!=null && (toefl.getListening()>0 || toefl.getReading()>0 || toefl.getSpeaking()>0 || toefl.getWriting()>0)) || (ielts!=null && (ielts.getListening() >0 || ielts.getReading() > 0 || ielts.getSpeaking() >0 || ielts.getWriting() >0)) || (toeic!=null && (toeic.getListening() >0 || toeic.getReading() > 0 || toeic.getSpeaking()>0 || toeic.getWriting()>0)))
            {
            	scoreTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
            	if(toefl!=null && (toefl.getListening()>0 || toefl.getReading()>0 || toefl.getSpeaking()>0 || toefl.getWriting()>0))
            	{
            		scoreTable+="<TR><TD vAlign=top width=80 ><span class=\"f11\">"+toefl.getExamType()+"</span></TD>"+
            		"<TD width=80 class=\"f11\"></TD>"+
  		            "</TR>";
            		if(toefl.getReading() != 0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></TD>"+
                        "<TD width=80 class=\"f11\">"+toefl.getReading()+"</TD>"+
              		    "</TR>";
            		}
            		if(toefl.getListening() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toefl.getListening()+"</TD>"+
      		            "</TR>";
            		}
            		if(toefl.getWriting() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toefl.getWriting()+"</TD>"+
      		            "</TR>";
            		}
            		if(toefl.getSpeaking() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toefl.getSpeaking()+"</TD>"+
      		            "</TR>";
            		}
            	}
            	if(ielts!=null && (ielts.getListening() >0 || ielts.getReading() > 0 || ielts.getSpeaking() >0 || ielts.getWriting() >0))
            	{
            		scoreTable+="<TR><TD vAlign=top width=80 ><span class=\"f11\">"+ielts.getExamType()+"</span></TD>"+
            		"<TD width=80 class=\"f11\"></TD>"+
  		            "</TR>";
            		if(ielts.getReading() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></TD>"+
                        "<TD width=80 class=\"f11\">"+ielts.getReading()+"</TD>"+
              		    "</TR>";
            		}
            		if(ielts.getListening() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+ielts.getListening()+"</TD>"+
      		            "</TR>";
            		}
            		if(ielts.getWriting() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+ielts.getWriting()+"</TD>"+
      		            "</TR>";
            		}
            		if(ielts.getSpeaking() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+ielts.getSpeaking()+"</TD>"+
      		            "</TR>";
            		}
            	}
            	if(toeic!=null && (toeic.getListening() >0 || toeic.getReading() > 0 || toeic.getSpeaking()>0 || toeic.getWriting()>0))
            	{
            		scoreTable+="<TR><TD vAlign=top width=80 ><span class=\"f11\">"+toeic.getExamType()+"</span></TD>"+
            		"<TD width=80 class=\"f11\"></TD>"+
  		            "</TR>";
            		if(toeic.getReading() != 0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></TD>"+
                        "<TD width=80 class=\"f11\">"+toeic.getReading()+"</TD>"+
              		    "</TR>";
            		}
            		if(toeic.getListening() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toeic.getListening()+"</TD>"+
      		            "</TR>";
            		}
            		if(toeic.getWriting() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toeic.getWriting()+"</TD>"+
      		            "</TR>";
            		}
            		if(toeic.getSpeaking() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toeic.getSpeaking()+"</TD>"+
      		            "</TR>";
            		}
            	}
            	scoreTable="<BR>"+scoreTableHead+scoreTable+"</TABLE>";
            }
            if(testOther.size()>0)
            {
            	otherScoreTable+="<TABLE cellSpacing=0 cellPadding=0 border=0>";
            	for(int c=0;c<testOther.size();c++)
            	{
            		otherScoreTable+="<tr><td valign=top width=80 ><span class=\"f11\">"+testOther.get(c).getExamType()+"</span></td><td width=80 class=\"f11\"> </td></tr>";
            		if(testOther.get(c).getReading()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getReading()+"</td></tr>";
            		}
            		if(testOther.get(c).getListening()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getListening()+"</td></tr>";
            		}
            		if(testOther.get(c).getWriting()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getWriting()+"</td></tr>";
            		}
            		if(testOther.get(c).getSpeaking()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getSpeaking()+"</td></tr>";
            		}
				}
            	otherScoreTable+="</TABLE>";
            	scoreTable="<BR/>"+scoreTable+otherScoreTable;
            	
            }
            if(others.size()>0)
            {
            	otherTableHead="<b>"+propMgr.getMessage(locale, "SKILLS_OTHER")+": </b>";
            	otherTableHead+="<TABLE cellSpacing=0 cellPadding=0 border=0>";
				for(int c=0;c<others.size();c++)
				{
					otherTable+="<TR><TD vAlign=top width=80 ><a class='f11'>"+others.get(c).getSkillName()+"</a></TD>";
					otherTable+="<TD width=80 class=\"f11\">"+propMgr.getMessage(locale,proficiencyComAndSkill(others.get(c).getLevelSkill()))+"</TD></TR>";
				}
			     otherTable=otherTableHead+otherTable+"</TABLE>";
            } 
		}
		String previewSkill = "";
		if(!computerHeader.equals("") || !languageHeader.equals(""))
		{
			previewSkill =  propMgr.getMessage(locale, "PREVIEW_SKILL");
		}
		replaceAll(result,"#SKILL_LANGUAGE#",langTable);
		replaceAll(result,"#SKILL_COMPUTER#", comTable);
		replaceAll(result,"#SKILL_SCORE#", scoreTable);
		replaceAll(result,"#SKILL_OTHER#", otherTable);
		replaceAll(result,"#PREVIEW_SKILL#", previewSkill);
		replaceAll(result,"#GLOBAL_COMPUTER#",computerHeader);
		replaceAll(result,"#GLOBAL_LANGUAGE#",languageHeader);
		replaceAll(result,"#BEGIN_SKILL#", startComment);
		replaceAll(result,"#END_SKILL#", endComment);
    }
	
	private void ReplaceSkillHideOther(Resume resume)
    {
		String langTable="";
		String langTableHead="";
		String comTable="";
		String comTableHead="";
		String scoreTable="";
		String scoreTableHead="";
		String otherTable="";
		String otherTableHead="";
		String otherScoreTable="";
		String otherScoreTableHead="";
		String startComment="";
		String endComment="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
	    else
	    {
	    	locale= "en_TH";
	    }
		List<SkillComputer> coms=new SkillComputerManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<SkillLanguageScore> testOther=new SkillLanguageScoreManager().getOtherAll(resume.getIdJsk(),resume.getIdResume());
		List<SkillLanguage> langs=new SkillLanguageManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<SkillOther> others=new SkillOtherManager().getAll(resume.getIdJsk(),resume.getIdResume());
		SkillLanguageScore toefl=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"TOEFL");
		SkillLanguageScore ielts=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"IELTS");
		SkillLanguageScore toeic=new SkillLanguageScoreManager().get(resume.getIdJsk(),resume.getIdResume(),"TOEIC");
		if(coms.size()==0 && langs.size()==0 && others.size()==0 && toefl==null && ielts==null && toeic==null)
		{
			startComment="<!--";
			endComment="-->";
		}
		else 
		{
			if(resume.getTemplateIdCountry() == 216||resume.getTemplateIdCountry() == 151)  // THAILAND or MYANMAR
			{
				if(langs.size()>0)
				{
					langTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
				
					for(int c=0;c<langs.size();c++)
					{
						langTable+="<TR><TD vAlign=top width=80 rowSpan=4><span class=\"f11\">";
						if(langs.get(c).getIdSkillLang()>0)
						{
							langTable+=MasterDataManager.getLanguageName(langs.get(c).getIdSkillLang(), resume.getIdLanguage());
						}
						else 
						{
							langTable+=langs.get(c).getOthLang();
						}
						langTable+="</span></TD>"+
			            "<TD width=80 class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+":</TD>"+
			            "<TD width=80 class=\"f11\">";
						if(langs.get(c).getReading() != 0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getReading()));
						}
						langTable+="</TD>"+
			            "</TR>"+
			            "<TR>"+
			            "<TD class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+":</TD>"+
			            "<TD class=\"f11\">";
						if(langs.get(c).getListening() !=0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getListening()));
						}
						langTable+="</TD>"+
			            "</TR>"+
			            "<TR>"+
			            "<TD class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+":</TD>"+
			            "<TD class=\"f11\">";
						if(langs.get(c).getWriting() != 0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getWriting()));
						}
						langTable+="</TD>" +
			            "</TR>"+
			            "<TR>"+
			            "<TD class=\"f11\">"+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+":</TD>"+
			            "<TD class=\"f11\">";
						if(langs.get(c).getSpeaking() != 0)
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getSpeaking()));
						}
						langTable+="</TD>"+
			            "</TR>";
					}
				     langTable=langTableHead+langTable+"</TABLE>";
				}	
			}
			else  // other country
			{
				if(langs.size()>0)
				{
					langTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
				
					for(int c=0;c<langs.size();c++)
					{
						langTable+="<TR><TD vAlign=top width=80 ><B class=\"f11\">"; 
						if(langs.get(c).getIdSkillLang()>0)
						{
							langTable+=MasterDataManager.getLanguageName(langs.get(c).getIdSkillLang(), resume.getIdLanguage());
						}
						else 
						{
							langTable+=langs.get(c).getOthLang();
						}
						langTable+="</B></TD>"+
			            "<TD width=80 class=\"f11\">";
						if(langs.get(c).getLevelSkill() != 0 )
						{
							langTable+=propMgr.getMessage(locale,proficiencyLanguage(langs.get(c).getLevelSkill()));
						}
						langTable+="</TD>"+
			            "</TR>";
					}
				     langTable=langTableHead+langTable+"</TABLE>";
				     
				
				}
			}
            if(coms.size()>0)
            {
            	comTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
				for(int c=0;c<coms.size();c++)
				{
					com.topgun.shris.masterdata.Computer aCom=MasterDataManager.getComputer(coms.get(c).getIdGroup(), coms.get(c).getIdComputer());
					comTable+="<TR><TD vAlign=top width=80 class=\"f11\">"; 
					if(aCom!=null)
					{
						comTable+=aCom.getComputerName();
					}
					else 
					{
						comTable+=coms.get(c).getOthComputer();
					}
					comTable+="</TD>"+
		            "<TD width=80 class=\"f11\">";
					if(coms.get(c).getLevelSkill() != 0)
					{
						comTable+=propMgr.getMessage(locale,proficiencyComAndSkill(coms.get(c).getLevelSkill()))+"</TD>";
					}
					comTable+="</TR>";
				}
			     comTable=comTableHead+comTable+"</TABLE>";
            } 
            if((toefl!=null && (toefl.getListening()>0 || toefl.getReading()>0 || toefl.getSpeaking()>0 || toefl.getWriting()>0)) || (ielts!=null && (ielts.getListening() >0 || ielts.getReading() > 0 || ielts.getSpeaking() >0 || ielts.getWriting() >0)) || (toeic!=null && (toeic.getListening() >0 || toeic.getReading() > 0 || toeic.getSpeaking()>0 || toeic.getWriting()>0)))
            {
            	scoreTableHead="<TABLE cellSpacing=0 cellPadding=0 border=0>";
            	if(toefl!=null && (toefl.getListening()>0 || toefl.getReading()>0 || toefl.getSpeaking()>0 || toefl.getWriting()>0))
            	{
            		scoreTable+="<TR><TD vAlign=top width=80 ><span class=\"f11\">"+toefl.getExamType()+"</span></TD>"+
            		"<TD width=80 class=\"f11\"></TD>"+
  		            "</TR>";
            		if(toefl.getReading() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toefl.getReading()+"</TD>"+
      		            "</TR>";
            		}
            		if(toefl.getListening() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toefl.getListening()+"</TD>"+
      		            "</TR>";
            		}
            		if(toefl.getWriting() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toefl.getWriting()+"</TD>"+
      		            "</TR>";
            		}
            		if(toefl.getSpeaking() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toefl.getSpeaking()+"</TD>"+
      		            "</TR>";
            		}
            	}
            	if(ielts!=null && (ielts.getListening() >0 || ielts.getReading() > 0 || ielts.getSpeaking() >0 || ielts.getWriting() >0))
            	{
            		scoreTable+="<TR><TD vAlign=top width=80 ><span class=\"f11\">"+ielts.getExamType()+"</span></TD>"+
            		"<TD width=80 class=\"f11\"></TD>"+
  		            "</TR>";
            		if(ielts.getReading() != 0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></TD>"+
                        "<TD width=80 class=\"f11\">"+ielts.getReading()+"</TD>"+
              		    "</TR>";
            		}
            		if(ielts.getListening() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+ielts.getListening()+"</TD>"+
      		            "</TR>";
            		}
            		if(ielts.getWriting() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+ielts.getWriting()+"</TD>"+
      		            "</TR>";
            		}
            		if(ielts.getSpeaking() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+ielts.getSpeaking()+"</TD>"+
      		            "</TR>";
            		}
            	}
            	if(toeic!=null && (toeic.getListening() >0 || toeic.getReading() > 0 || toeic.getSpeaking()>0 || toeic.getWriting()>0))
            	{
            		scoreTable+="<TR><TD vAlign=top width=80 ><span class=\"f11\">"+toeic.getExamType()+"</span></TD>"+
            		"<TD width=80 class=\"f11\"></TD>"+
  		            "</TR>";
            		if(toeic.getReading() != 0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></TD>"+
            		    "<TD width=80 class=\"f11\">"+toeic.getReading()+"</TD>"+
              		    "</TR>";
            		}
            		if(toeic.getListening() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toeic.getListening()+"</TD>"+
      		            "</TR>";
            		}
            		if(toeic.getWriting() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toeic.getWriting()+"</TD>"+
      		            "</TR>";
            		}
            		if(toeic.getSpeaking() !=0)
            		{
            			scoreTable+="<TR><TD vAlign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></TD>"+
                		"<TD width=80 class=\"f11\">"+toeic.getSpeaking()+"</TD>"+
      		            "</TR>";
            		}
            	}
            	scoreTable="<BR>"+scoreTableHead+scoreTable+"</TABLE>";
            }
            if(testOther.size()>0)
            {
            	/*
            	otherScoreTable+="<TABLE cellSpacing=0 cellPadding=0 border=0>";
            	for(int c=0;c<testOther.size();c++)
            	{
            		//otherScoreTable+="<tr><td valign=top width=80 ><span class=\"f11\">"+testOther.get(c).getExamType()+"</span></td><td width=80 class=\"f11\"> </td></tr>";
            		otherScoreTable+="<tr><td valign=top width=80 ><span class=\"f11\">"+viewInOriginal+"</span></td><td width=80 class=\"f11\"> </td></tr>";
            		if(testOther.get(c).getReading()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_READING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getReading()+"</td></tr>";
            		}
            		if(testOther.get(c).getListening()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_LISTENING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getListening()+"</td></tr>";
            		}
            		if(testOther.get(c).getWriting()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_WRITING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getWriting()+"</td></tr>";
            		}
            		if(testOther.get(c).getSpeaking()>0)
            		{
            			otherScoreTable+="<tr><td valign=top width=80 ><a class=\"f11\">- "+propMgr.getMessage(locale, "GLOBAL_LANGUAGE_SPEAKING")+"</a></td><td width=80 class=\"f11\">"+testOther.get(c).getSpeaking()+"</td></tr>";
            		}
				}
            	otherScoreTable+="</TABLE>";
            	*/
            	otherScoreTable+="<span class=\"f11\" style='white-space:nowrap'>"+viewInOriginal+"</span>";
            	scoreTable="<BR>"+scoreTable+otherScoreTable;
            	
            }
            if(others.size()>0)
            {
            	otherTableHead="<b>"+propMgr.getMessage(locale, "SKILLS_OTHER")+": </b>";
            	/*
            	otherTableHead+="<TABLE cellSpacing=0 cellPadding=0 border=0 teras=''>";
				for(int c=0;c<others.size();c++)
				{
					//otherTable+="<TR><TD vAlign=top width=80 ><a class='f11'>"+others.get(c).getSkillName()+"</a></TD>";
					otherTable+="<TR><TD vAlign=top width=80 ><a class='f11'>"+viewInOriginal+"</a></TD>";
					otherTable+="<TD width=80 class=\"f11\">"+propMgr.getMessage(locale,proficiencyComAndSkill(others.get(c).getLevelSkill()))+"</TD></TR>";
				}
            	otherTable=otherTableHead+otherTable+"</TABLE>";
            	*/
            	otherTable+="<br/><a class='f11'>"+viewInOriginal+"</a>";
            	otherTable=otherTableHead+otherTable;
			     
            } 
		}
		replaceAll(result,"#SKILL_LANGUAGE#",langTable);
		replaceAll(result,"#SKILL_COMPUTER#", comTable);
		replaceAll(result,"#SKILL_SCORE#", scoreTable);
		replaceAll(result,"#SKILL_OTHER#", otherTable);
		replaceAll(result,"#PREVIEW_SKILL#", propMgr.getMessage(locale, "PREVIEW_SKILL"));
		replaceAll(result,"#GLOBAL_COMPUTER#", propMgr.getMessage(locale, "GLOBAL_COMPUTER"));
		replaceAll(result,"#GLOBAL_LANGUAGE#",propMgr.getMessage(locale, "GLOBAL_LANGUAGE"));
		replaceAll(result,"#BEGIN_SKILL#", startComment);
		replaceAll(result,"#END_SKILL#", endComment);
    }
	
	private void ReplaceCarreerObjective(Resume resume)
    {
		List<CareerObjective> jsk_CareerObjective=new CareerObjectiveManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
	    else
	    {
	     	locale= "en_TH";
	    }
		String career="";
		if(jsk_CareerObjective.size()>0)
		{
			for(int c=0;c<jsk_CareerObjective.size() ;c++)
			{ 
				if(c>0){career=career+"<br>";}
				com.topgun.shris.masterdata.CareerObjective aCareerObj=MasterDataManager.getCareerObjective(jsk_CareerObjective.get(c).getIdCareerObjective(), resume.getIdLanguage());
				if(aCareerObj!=null)
				{
					career=career+"- "+aCareerObj.getCareerobjName();
				}
				else
				{
					career=career+"- "+jsk_CareerObjective.get(c).getOtherObjective();
				}
			}
		}
	    replaceAll(result,"#CAREER_OBJECTIVE#",career);
		replaceAll(result,"#PREVIEW_CAREEROBJECTIVE#", propMgr.getMessage(locale,"PREVIEW_CAREEROBJECTIVE"));
    }
	
	private void ReplaceCarreerObjectiveHideOther(Resume resume)
    {
		List<CareerObjective> jsk_CareerObjective=new CareerObjectiveManager().getAll(resume.getIdJsk(),resume.getIdResume());
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	    	locale= resume.getLocale();
	    }
	    else
	    {
	     	locale= "en_TH";
	    }
		String career="";
		if(jsk_CareerObjective.size()>0)
		{
			for(int c=0;c<jsk_CareerObjective.size() ;c++)
			{ 
				if(c>0){career=career+"<br>";}
				com.topgun.shris.masterdata.CareerObjective aCareerObj=MasterDataManager.getCareerObjective(jsk_CareerObjective.get(c).getIdCareerObjective(), resume.getIdLanguage());
				if(aCareerObj!=null)
				{
					career=career+"- "+aCareerObj.getCareerobjName();
				}
				else
				{
					//career=career+"- "+jsk_CareerObjective.get(c).getOtherObjective();
					career=career+"- "+viewInOriginal;
				}
			}
		}
	    replaceAll(result,"#CAREER_OBJECTIVE#",career);
		replaceAll(result,"#PREVIEW_CAREEROBJECTIVE#", propMgr.getMessage(locale,"PREVIEW_CAREEROBJECTIVE"));
    }

	private void ReplaceTargetJob(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		String availableDate="";
		String jobType="";
		String expectedSalary="";
		String targetJob="";
		String industry="";
		String locationInside="";
		String locationInsideTopic="";
		String workPermit="";
		String relocate="";
		String travel="";
		String locationOutside="";
		String locationOutsideTopic="";
		String desiredTravel="";
		String cityTopic="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	      	locale= resume.getLocale();
	    }
	    else
	    {
	     	locale= "en_TH";
	    }
		TargetJob tarExt =new TargetJobManager().get(resume.getIdJsk(),resume.getIdResume());
		List<JobType> jsk_jobtype=new JobTypeManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<Industry> jsk_industry=new IndustryManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<TargetJobField> jsk_targetjob=new TargetJobFieldManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<Location> locList=new LocationManager().getAllInsideLocationRegionGroup(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		List<Location> locOutsideList=new LocationManager().getAllOutsideLocation(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		
      	if(tarExt!=null)
      	{
	    	String salaryPer="";
	    	SalaryPer aSalPer=MasterDataManager.getSalaryPer(Util.getInt(tarExt.getExpectedSalaryPer()), resume.getIdLanguage());
	    	if(aSalPer!=null)
	    	{
	    		String salPer=Util.getStr(aSalPer.getName());
	    		salaryPer=propMgr.getMessage(locale,salPer);
	    	}
	        cityTopic=propMgr.getMessage(locale,"GLOBAL_CITY");
	        if(!Util.getStr(tarExt.getTravel()).equals(""))
	        {
	        	/*
	        	com.topgun.shris.masterdata.Travel trv=MasterDataManager.getTravel(tarExt.getTravel(), resume.getIdLanguage());
	        	if(trv!=null)
	        	{
	        		desiredTravel=Util.getStr(trv.getTravelName());
	        	}
	        	*/
	        	if(tarExt.getTravel()==1)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_NEGLIGIBLE")+"<br>";
	        	}
	        	else if(tarExt.getTravel()==2)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO25")+"<br>";
	        	}
	        	else if(tarExt.getTravel()==3)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO50")+"<br>";
	        	}
	        	else if(tarExt.getTravel()==4)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO75")+"<br>";
	        	}
	        	else if(tarExt.getTravel()==5)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO100")+"<br>";
	        	}
	        	if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
	        }
	        if(tarExt.getStartJob() !=null )
			{   
				if(aLanguage.equals("ja"))
				{
					availableDate=Util.getLocaleDateFormatFull(tarExt.getStartJob(),"FULL",aLanguage,aCountry);
				}
				else 
				{
					availableDate=Util.getLocaleDateFormatFull(tarExt.getStartJob(),"MEDIUM",aLanguage,aCountry);
				}
			}
			else
			{
				if(tarExt.getStartJobNotice() == 1)
				 {
					availableDate=propMgr.getMessage(locale,"TARGET_1WEEK");
				 }
				 else  if(tarExt.getStartJobNotice() == 2)
				 {
					 availableDate=propMgr.getMessage(locale,"TARGET_2WEEK");
				 }
				 else  if(tarExt.getStartJobNotice() == 3)
				 {
					 availableDate=propMgr.getMessage(locale,"TARGET_1MONTH");
				 }
				 else  if(tarExt.getStartJobNotice() == 4)
				 {
					 availableDate=propMgr.getMessage(locale,"START_IMMEDIATELY");
				 }
			}
      	
      	
			if(jsk_jobtype!=null &jsk_jobtype.size()>0)
			{
				for(int c=0;c<jsk_jobtype.size();c++)
				{
					com.topgun.shris.masterdata.JobType aJobType=MasterDataManager.getJobType(jsk_jobtype.get(c).getJobType(), resume.getIdLanguage());
					if(c>0){jobType=jobType+", ";}
					jobType=jobType+aJobType.getTypeName();
				}
				jobType="<b>"+propMgr.getMessage(locale,"TARGETJOB_TYPE")+":</b> "+jobType+"<br/>";
			}
			if(tarExt.getMinExpectedSalary() == -1 && tarExt.getMaxExpectedSalary() == -1)
			{
				expectedSalary=propMgr.getMessage(resume.getLocale(),"TARGET_NEGOTIABLE");
			}
			else 
			{
				if(tarExt.getMinExpectedSalary() > 0)
				{
					DecimalFormat aDecimal = new DecimalFormat( "###,###" );
					expectedSalary=aDecimal.format(tarExt.getMinExpectedSalary()).toString();
					if(tarExt.getMaxExpectedSalary() > 0)
					{
						expectedSalary+=" - "+aDecimal.format(tarExt.getMaxExpectedSalary()).toString();
					}
					if(tarExt.getSalaryCurrency()>0)
					{
						if(tarExt.getSalaryCurrency()==140 && resume.getIdLanguage()==38)
						{
							expectedSalary+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
						}else{
							expectedSalary+=" "+MasterDataManager.getCurrency(tarExt.getSalaryCurrency()).getCode();
						}
					}
					expectedSalary+=" "+salaryPer;
				}
			}
      	}
			
		if(jsk_targetjob!=null && jsk_targetjob.size()>0)
		{
			targetJob+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<jsk_targetjob.size();c++)
			{
				targetJob+="<li>";
				if(jsk_targetjob.get(c).getIdJobfield() > 0)
				{
					com.topgun.shris.masterdata.JobField aJobField=MasterDataManager.getJobField(jsk_targetjob.get(c).getIdJobfield(), resume.getIdLanguage());
					if(aJobField!=null)
					{
						targetJob+=aJobField.getFieldName();
					}
					if(jsk_targetjob.get(c).getIdSubfield() > 0)
					{
						com.topgun.shris.masterdata.SubField aSubField=MasterDataManager.getSubField(jsk_targetjob.get(c).getIdJobfield(), jsk_targetjob.get(c).getIdSubfield(), resume.getIdLanguage());
						if(aSubField!=null)
						{
							targetJob+=": "+aSubField.getSubfieldName();
						}
					}
					else 
					{
						if(isTranslate!=true)
						{
							targetJob+=": "+jsk_targetjob.get(c).getOtherSubfield();
						}
					}
				}
				else 
				{
					if(isTranslate!=true)
					{
						targetJob+=jsk_targetjob.get(c).getOtherJobfield();
						targetJob+=": "+jsk_targetjob.get(c).getOtherSubfield();
					}
				}
				targetJob+="</li>";
			}
			targetJob+="</ol>";
		}
			
		if(jsk_industry!=null && jsk_industry.size()>0)
		{
			industry+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<jsk_industry.size();c++)
			{
				com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(jsk_industry.get(c).getIdIndustry(), resume.getIdLanguage());
				if(aInsdus!=null)
				{
					industry+="<li>"+aInsdus.getIndustryName()+"</li>";
				}
				else
				{
					if(isTranslate!=true)
					{
						industry+="<li>"+jsk_industry.get(c).getOtherIndustry()+"</li>";
					}
				}
			}
			industry+="</ol>";
		}
		
		if(locList!=null && locList.size()>0)
		{
			locationInsideTopic="<b>"+propMgr.getMessage(locale,"PREVIEW_LOCATIONINSIDE")+"</b>";
			if((locList.size()>2 && resume.getIdResume()!=0) || (locList.size()>10 && resume.getIdResume()==0))
			{
				locationInsideTopic+="<font class='style8'> ("+propMgr.getMessage(locale,"VIEW_OTHER")+") </font>";
			}
			locationInside+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<locList.size();c++)
			{
				if(c>=2)
				{
					break ;
				}
				locationInside+="<li>";
				locationInside+=locList.get(c).getStateName();
				locationInside+="</li>";
			}
			if(locOutsideList!=null && locOutsideList.size()>0)
			{   
				locationInside+="<li>"+propMgr.getMessage(locale,"GLOBAL_OVERSEA")+"</li>";
			}
			locationInside+="</ol>";
			locationInside=locationInsideTopic+locationInside;
		}
		
		if(tarExt!=null)
		{
			String targetCountryName=propMgr.getMessage(locale,"TARGETJOB_LEGALLY_ELIGIBLE");
			targetCountryName=targetCountryName.replace("{0}", MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getCountryName());
			if(Util.getStr(tarExt.getWorkPermit()).equals("TRUE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				workPermit="<b>"+targetCountryName+":</b> "+propMgr.getMessage(locale,"TARGETJOB_ELIGIBLE_YES")+"<br/>";
			}
			else if(Util.getStr(tarExt.getWorkPermit()).equals("FALSE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				workPermit="<b>"+targetCountryName+":</b> "+propMgr.getMessage(locale,"TARGETJOB_ELIGIBLE_NO")+"<br/>";
			}
			if(Util.getStr(tarExt.getRelocate()).equals("TRUE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				relocate="<b>"+propMgr.getMessage(locale,"TARGETJOB_RELOCATE")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_RELOCATE_YES")+"<br/>";
			}		
			else if(Util.getStr(tarExt.getRelocate()).equals("FALSE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				relocate="<b>"+propMgr.getMessage(locale,"TARGETJOB_RELOCATE")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_RELOCATE_NO")+"<br/>";
			}
		}
		/*
		if(!desiredTravel.equals(""))
		{
			travel="<b>"+propMgr.getMessage(resume.getLocale(),"TARGETJOB_DISIRED_TRAVEL")+":</b> "+desiredTravel+"<br/>";
		}
		*/
		if(locOutsideList!=null && locOutsideList.size()>0)
		{   
			locationOutsideTopic="<b>"+propMgr.getMessage(locale,"PREVIEW_LOCATIONOUTSIDE")+"</b>";
			if((locOutsideList.size()>2 && resume.getIdResume()!=0) || (locOutsideList.size()>2 && resume.getIdResume()==0))
			{
				locationOutsideTopic+=" <font class='style8'> ("+propMgr.getMessage(locale,"VIEW_OTHER")+") </font>";
			}
			locationOutside+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<locOutsideList.size();c++)
			{  
				if(c>=2)
				{
					break ;
				}
				locationOutside+="<li>";
				Country ct=MasterDataManager.getCountry(Util.getInt(locOutsideList.get(c).getIdCountry()), resume.getIdLanguage());
				if(ct!=null)
				{
					locationOutside+=Util.getStr(ct.getCountryName());
				}
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()),resume.getIdLanguage());
				if(aState!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aState.getStateName());
				}
				else if(aState==null && Util.getStr(locOutsideList.get(c).getOtherState())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(locOutsideList.get(c).getOtherState());
				}
				com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()), Util.getInt(locOutsideList.get(c).getIdCity()), resume.getIdLanguage());
				if(aCity!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aCity.getCityName());
				}
				
				else if(aCity==null && Util.getStr(locOutsideList.get(c).getOtherCity())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(locOutsideList.get(c).getOtherCity());
				}
				if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("TRUE"))
				{   
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_AUTHORIZED");
				}
				else if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("FALSE"))
				{
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_NOT_AUTHORIZED");
				}
				locationOutside+="</li>";
			}
			locationOutside+="</ol>";
			locationOutside=locationOutsideTopic+locationOutside;
		}
		
		replaceAll(result,"#AVAILABLE_DATE#",Util.getStr(availableDate));
		replaceAll(result,"#JOB_TYPE#",Util.getStr(jobType));
		replaceAll(result,"#EXPECTED_SALARY#", Util.getStr(expectedSalary));
		replaceAll(result,"#TARGET_JOBFIELD#", Util.getStr(targetJob));
		replaceAll(result,"#TARGET_INDUSTRY#", Util.getStr(industry));
		replaceAll(result,"#LOCATION_INSIDE#", Util.getStr(locationInside));
		replaceAll(result,"#WORK_PERMIT#", Util.getStr(workPermit));
		replaceAll(result,"#RELOCATE#", Util.getStr(relocate));
		replaceAll(result,"#TRAVEL#", Util.getStr(travel));
		replaceAll(result,"#LOCATION_OUTSIDE#", locationOutside);
		replaceAll(result,"#PREVIEW_TARGETJOB#", propMgr.getMessage(locale,"PREVIEW_TARGETJOB"));
		replaceAll(result,"#PREVIEW_TARGETJOB_AVAILABLE#", propMgr.getMessage(locale,"PREVIEW_TARGETJOB_AVAILABLE"));
		replaceAll(result,"#TARGETJOB_EXPECTED_SALARY#", propMgr.getMessage(locale,"TARGETJOB_EXPECTED_SALARY"));
		replaceAll(result,"#PREVIEW_TARGET_JOBFIELD#", propMgr.getMessage(locale,"PREVIEW_TARGET_JOBFIELD"));
		replaceAll(result,"#GLOBAL_RANKED_IN_ORDER#", propMgr.getMessage(locale,"GLOBAL_RANKED_IN_ORDER"));
		replaceAll(result,"#PREVIEW_INDUSTRY_WANTED#", propMgr.getMessage(locale,"PREVIEW_INDUSTRY_WANTED"));
		replaceAll(result,"#GLOBAL_RANKED_IN_ORDER#", propMgr.getMessage(locale,"GLOBAL_RANKED_IN_ORDER"));
		String targetCountryName=propMgr.getMessage(locale,"TARGETJOB_LEGALLY_ELIGIBLE");
		targetCountryName=targetCountryName.replace("{0}", MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getCountryName());
		replaceAll(result,"#TARGETJOB_LEGALLY_ELIGIBLE#", Util.getStr(targetCountryName));
    }
	
	private void ReplaceTargetJobHideOther(Resume resume)
    {
		String aLanguage=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
		String aCountry=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
		String availableDate="";
		String jobType="";
		String expectedSalary="";
		String targetJob="";
		String industry="";
		String locationInside="";
		String locationInsideTopic="";
		String workPermit="";
		String relocate="";
		String travel="";
		String locationOutside="";
		String locationOutsideTopic="";
		String desiredTravel="";
		String cityTopic="";
		String locale= "en_TH";
	        
	    if(chkLocation(resume.getLocale()))
	    {
	      	locale= resume.getLocale();
	    }
	    else
	    {
	     	locale= "en_TH";
	    }
		TargetJob tarExt =new TargetJobManager().get(resume.getIdJsk(),resume.getIdResume());
		List<JobType> jsk_jobtype=new JobTypeManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<Industry> jsk_industry=new IndustryManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<TargetJobField> jsk_targetjob=new TargetJobFieldManager().getAll(resume.getIdJsk(),resume.getIdResume());
		List<Location> locList=new LocationManager().getAllInsideLocationRegionGroup(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		List<Location> locOutsideList=new LocationManager().getAllOutsideLocation(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		
      	if(tarExt!=null)
      	{
	    	String salaryPer="";
	    	SalaryPer aSalPer=MasterDataManager.getSalaryPer(Util.getInt(tarExt.getExpectedSalaryPer()), resume.getIdLanguage());
	    	if(aSalPer!=null)
	    	{
	    		String salPer=Util.getStr(aSalPer.getName());
	    		salaryPer=propMgr.getMessage(locale,salPer);
	    	}
	        cityTopic=propMgr.getMessage(locale,"GLOBAL_CITY");
	        if(!Util.getStr(tarExt.getTravel()).equals(""))
	        {
	        	/*
	        	com.topgun.shris.masterdata.Travel trv=MasterDataManager.getTravel(tarExt.getTravel(), resume.getIdLanguage());
	        	if(trv!=null)
	        	{
	        		desiredTravel=Util.getStr(trv.getTravelName());
	        	}
	        	*/
	        	if(tarExt.getTravel()==1)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_NEGLIGIBLE")+"<br/>";
	        	}
	        	else if(tarExt.getTravel()==2)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO25")+"<br/>";
	        	}
	        	else if(tarExt.getTravel()==3)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO50")+"<br/>";
	        	}
	        	else if(tarExt.getTravel()==4)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO75")+"<br/>";
	        	}
	        	else if(tarExt.getTravel()==5)
	        	{
	        		travel="<b>"+propMgr.getMessage(locale,"TARGETJOB_DISIRED_TRAVEL")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_UPTO100")+"<br/>";
	        	}
	        	if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
	        }
	        if(tarExt.getStartJob() !=null )
			{   
				if(aLanguage.equals("ja"))
				{
					availableDate=Util.getLocaleDateFormatFull(tarExt.getStartJob(),"FULL",aLanguage,aCountry);
				}
				else 
				{
					availableDate=Util.getLocaleDateFormatFull(tarExt.getStartJob(),"MEDIUM",aLanguage,aCountry);
				}
			}
			else
			{
				if(tarExt.getStartJobNotice() == 1)
				 {
					availableDate=propMgr.getMessage(locale,"TARGET_1WEEK");
				 }
				 else  if(tarExt.getStartJobNotice() == 2)
				 {
					 availableDate=propMgr.getMessage(locale,"TARGET_2WEEK");
				 }
				 else  if(tarExt.getStartJobNotice() == 3)
				 {
					 availableDate=propMgr.getMessage(locale,"TARGET_1MONTH");
				 }
				 else  if(tarExt.getStartJobNotice() == 4)
				 {
					 availableDate=propMgr.getMessage(locale,"START_IMMEDIATELY");
				 }
			}
      	
      	
			if(jsk_jobtype!=null &jsk_jobtype.size()>0)
			{
				for(int c=0;c<jsk_jobtype.size();c++)
				{
					com.topgun.shris.masterdata.JobType aJobType=MasterDataManager.getJobType(jsk_jobtype.get(c).getJobType(), resume.getIdLanguage());
					if(c>0){jobType=jobType+", ";}
					jobType=jobType+aJobType.getTypeName();
				}
				jobType="<b>"+propMgr.getMessage(locale,"TARGETJOB_TYPE")+":</b> "+jobType+"<br/>";
			}
			if(tarExt.getMinExpectedSalary() == -1 && tarExt.getMaxExpectedSalary() == -1)
			{
				expectedSalary=propMgr.getMessage(resume.getLocale(),"TARGET_NEGOTIABLE");
			}
			else 
			{
				if(tarExt.getMinExpectedSalary() > 0)
				{
					DecimalFormat aDecimal = new DecimalFormat( "###,###" );
					expectedSalary=aDecimal.format(tarExt.getMinExpectedSalary()).toString();
					if(tarExt.getMaxExpectedSalary() > 0)
					{
						expectedSalary+=" - "+aDecimal.format(tarExt.getMaxExpectedSalary()).toString();
					}
					if(tarExt.getSalaryCurrency()>0)
					{
						if(tarExt.getSalaryCurrency()==140 && resume.getIdLanguage()==38)
						{
							expectedSalary+=" "+propMgr.getMessage(locale,"CURRENCY_BATH");
						}else{
							expectedSalary+=" "+MasterDataManager.getCurrency(tarExt.getSalaryCurrency()).getCode();
						}
					}
					expectedSalary+=" "+salaryPer;
				}
			}
      	}
			
		if(jsk_targetjob!=null && jsk_targetjob.size()>0)
		{
			targetJob+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<jsk_targetjob.size();c++)
			{
				targetJob+="<li>";
				if(jsk_targetjob.get(c).getIdJobfield() <= 0 && jsk_targetjob.get(c).getIdSubfield() <= 0)
				{
					targetJob+=viewInOriginal;
				}
				else
				{
					if(jsk_targetjob.get(c).getIdJobfield() > 0)
					{
						com.topgun.shris.masterdata.JobField aJobField=MasterDataManager.getJobField(jsk_targetjob.get(c).getIdJobfield(), resume.getIdLanguage());
						if(aJobField!=null)
						{
							targetJob+=aJobField.getFieldName();
						}
						if(jsk_targetjob.get(c).getIdSubfield() > 0)
						{
							com.topgun.shris.masterdata.SubField aSubField=MasterDataManager.getSubField(jsk_targetjob.get(c).getIdJobfield(), jsk_targetjob.get(c).getIdSubfield(), resume.getIdLanguage());
							if(aSubField!=null)
							{
								targetJob+=": "+aSubField.getSubfieldName();
							}
						}
						else 
						{
							if(isTranslate!=true)
							{
								//targetJob+=": "+jsk_targetjob.get(c).getOtherSubfield();
								targetJob+=": "+viewInOriginal;
							}
						}
					}
					else 
					{
						if(isTranslate!=true)
						{
							//targetJob+=jsk_targetjob.get(c).getOtherJobfield();
							//targetJob+=": "+jsk_targetjob.get(c).getOtherSubfield();
							
							targetJob+=viewInOriginal;
							targetJob+=": "+viewInOriginal;
						}
					}
				}
				targetJob+="</li>";
			}
			targetJob+="</ol>";
		}
			
		if(jsk_industry!=null && jsk_industry.size()>0)
		{
			industry+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<jsk_industry.size();c++)
			{
				com.topgun.shris.masterdata.Industry aInsdus=MasterDataManager.getIndustry(jsk_industry.get(c).getIdIndustry(), resume.getIdLanguage());
				if(aInsdus!=null)
				{
					industry+="<li>"+aInsdus.getIndustryName()+"</li>";
				}
				else
				{
					if(isTranslate!=true)
					{
						//industry+="<li>"+jsk_industry.get(c).getOtherIndustry()+"</li>";
						industry+="<li>"+viewInOriginal;
					}
				}
			}
			industry+="</ol>";
		}
		
		if(locList!=null && locList.size()>0)
		{
			locationInsideTopic="<b>"+propMgr.getMessage(locale,"PREVIEW_LOCATIONINSIDE")+"</b>";
			if((locList.size()>2 && resume.getIdResume()!=0) || (locList.size()>10 && resume.getIdResume()==0))
			{
				locationInsideTopic+="<font class='style8'> ("+propMgr.getMessage(locale,"VIEW_OTHER")+") </font>";
			}
			locationInside+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<locList.size();c++)
			{
				if(c>=2)
				{
					break ;
				}
				locationInside+="<li>";
				locationInside+=locList.get(c).getStateName();
				locationInside+="</li>";
			}
			if(locOutsideList!=null && locOutsideList.size()>0)
			{   
				locationInside+="<li>"+propMgr.getMessage(locale,"GLOBAL_OVERSEA")+"</li>";
			}
			locationInside+="</ol>";
			locationInside=locationInsideTopic+locationInside;
		}
		
		if(tarExt!=null)
		{
			String targetCountryName=propMgr.getMessage(locale,"TARGETJOB_LEGALLY_ELIGIBLE");
			targetCountryName=targetCountryName.replace("{0}", MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getCountryName());
			if(Util.getStr(tarExt.getWorkPermit()).equals("TRUE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				workPermit="<b>"+targetCountryName+":</b> "+propMgr.getMessage(locale,"TARGETJOB_ELIGIBLE_YES")+"<br/>";
			}
			else if(Util.getStr(tarExt.getWorkPermit()).equals("FALSE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				workPermit="<b>"+targetCountryName+":</b> "+propMgr.getMessage(locale,"TARGETJOB_ELIGIBLE_NO")+"<br/>";
			}
			if(Util.getStr(tarExt.getRelocate()).equals("TRUE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				relocate="<b>"+propMgr.getMessage(locale,"TARGETJOB_RELOCATE")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_RELOCATE_YES")+"<br/>";
			}		
			else if(Util.getStr(tarExt.getRelocate()).equals("FALSE"))
			{
				if(resume.getIdResume()!=0 && resume.getIdParent()!=-1)
				{
					additionalFlag = true ;
				}
				relocate="<b>"+propMgr.getMessage(locale,"TARGETJOB_RELOCATE")+":</b> "+propMgr.getMessage(locale,"TARGETJOB_RELOCATE_NO")+"<br/>";
			}
		}
		/*
		if(!desiredTravel.equals(""))
		{
			travel="<b>"+propMgr.getMessage(resume.getLocale(),"TARGETJOB_DISIRED_TRAVEL")+":</b> "+desiredTravel+"<br/>";
		}
		*/
		if(locOutsideList!=null && locOutsideList.size()>0)
		{   
			locationOutsideTopic="<b>"+propMgr.getMessage(locale,"PREVIEW_LOCATIONOUTSIDE")+"</b>";
			if((locOutsideList.size()>2 && resume.getIdResume()!=0) || (locOutsideList.size()>2 && resume.getIdResume()==0))
			{
				locationOutsideTopic+=" <font class='style8'> ("+propMgr.getMessage(locale,"VIEW_OTHER")+") </font>";
			}
			locationOutside+="<ol style='padding-left:18;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<locOutsideList.size();c++)
			{  
				if(c>=2)
				{
					break ;
				}
				locationOutside+="<li>";
				Country ct=MasterDataManager.getCountry(Util.getInt(locOutsideList.get(c).getIdCountry()), resume.getIdLanguage());
				if(ct!=null)
				{
					locationOutside+=Util.getStr(ct.getCountryName());
				}
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()),resume.getIdLanguage());
				if(aState!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aState.getStateName());
				}
				else if(aState==null && Util.getStr(locOutsideList.get(c).getOtherState())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					//locationOutside+=Util.getStr(locOutsideList.get(c).getOtherState());
					locationOutside+=viewInOriginal;
				}
				com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()), Util.getInt(locOutsideList.get(c).getIdCity()), resume.getIdLanguage());
				if(aCity!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aCity.getCityName());
				}
				
				else if(aCity==null && Util.getStr(locOutsideList.get(c).getOtherCity())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					//locationOutside+=Util.getStr(locOutsideList.get(c).getOtherCity());
					locationOutside+=viewInOriginal;
				}
				if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("TRUE"))
				{   
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_AUTHORIZED");
				}
				else if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("FALSE"))
				{
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_NOT_AUTHORIZED");
				}
				locationOutside+="</li>";
			}
			locationOutside+="</ol>";
			locationOutside=locationOutsideTopic+locationOutside;
		}
		replaceAll(result,"#AVAILABLE_DATE#",Util.getStr(availableDate));
		replaceAll(result,"#JOB_TYPE#",Util.getStr(jobType));
		replaceAll(result,"#EXPECTED_SALARY#", Util.getStr(expectedSalary));
		replaceAll(result,"#TARGET_JOBFIELD#", Util.getStr(targetJob));
		replaceAll(result,"#TARGET_INDUSTRY#", Util.getStr(industry));
		replaceAll(result,"#LOCATION_INSIDE#", Util.getStr(locationInside));
		replaceAll(result,"#WORK_PERMIT#", Util.getStr(workPermit));
		replaceAll(result,"#RELOCATE#", Util.getStr(relocate));
		replaceAll(result,"#TRAVEL#", Util.getStr(travel));
		replaceAll(result,"#LOCATION_OUTSIDE#", locationOutside);
		replaceAll(result,"#PREVIEW_TARGETJOB#", propMgr.getMessage(locale,"PREVIEW_TARGETJOB"));
		replaceAll(result,"#PREVIEW_TARGETJOB_AVAILABLE#", propMgr.getMessage(locale,"PREVIEW_TARGETJOB_AVAILABLE"));
		replaceAll(result,"#TARGETJOB_EXPECTED_SALARY#", propMgr.getMessage(locale,"TARGETJOB_EXPECTED_SALARY"));
		replaceAll(result,"#PREVIEW_TARGET_JOBFIELD#", propMgr.getMessage(locale,"PREVIEW_TARGET_JOBFIELD"));
		replaceAll(result,"#GLOBAL_RANKED_IN_ORDER#", propMgr.getMessage(locale,"GLOBAL_RANKED_IN_ORDER"));
		replaceAll(result,"#PREVIEW_INDUSTRY_WANTED#", propMgr.getMessage(locale,"PREVIEW_INDUSTRY_WANTED"));
		replaceAll(result,"#GLOBAL_RANKED_IN_ORDER#", propMgr.getMessage(locale,"GLOBAL_RANKED_IN_ORDER"));
		String targetCountryName=propMgr.getMessage(locale,"TARGETJOB_LEGALLY_ELIGIBLE");
		targetCountryName=targetCountryName.replace("{0}", MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getCountryName());
		replaceAll(result,"#TARGETJOB_LEGALLY_ELIGIBLE#", Util.getStr(targetCountryName));
    }
	
	private void ReplaceMoreLocation(Resume resume)
	{
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		String locationInside = "";
		String cityTopic=propMgr.getMessage(locale,"GLOBAL_CITY");
		List<Location> locList=new LocationManager().getAllInsideLocationRegionGroup(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		if(locList!=null && ((locList.size()>2 && resume.getIdResume()!=0) || (locList.size()>2 && resume.getIdResume()==0)))
		{
			additionalFlag = true;
			locationInside="<b>"+propMgr.getMessage(locale,"PREVIEW_LOCATIONINSIDE");
			locationInside+="</b>";
			locationInside+="<ol style='padding-left:18px;  margin-top:0px; margin-bottom:0px;'>";
			for(int c=0;c<locList.size();c++)
			{
				locationInside+="<li>";
				locationInside+=locList.get(c).getStateName();
				locationInside+="</li>";
			}
			locationInside+="</ol>";
		}
		else{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#BEGIN_TARGET_LOCATION_MORE#", startComment);
		replaceAll(result,"#END_TARGET_LOCATION_MORE#", endComment);
		replaceAll(result,"#LOCATION_INSIDE_MORE#", Util.getStr(locationInside));
		
	}
	
	private void ReplaceMoreLocationHideOther(Resume resume)
	{
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		String locationInside = "";
		String cityTopic=propMgr.getMessage(locale,"GLOBAL_CITY");
		List<Location> locList=new LocationManager().getAllInsideLocationRegionGroup(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		if(locList!=null && ((locList.size()>2 && resume.getIdResume()!=0) || (locList.size()>2 && resume.getIdResume()==0)))
		{
			additionalFlag = true;
			locationInside="<b>"+propMgr.getMessage(locale,"PREVIEW_LOCATIONINSIDE");
			locationInside+="</b>";
			locationInside+="<ol style='padding-left:18px;  margin-top:0px; margin-bottom:0px;'>";
			for(int c=0;c<locList.size();c++)
			{
				locationInside+="<li>";
				locationInside+=locList.get(c).getStateName();
				locationInside+="</li>";
			}
			locationInside+="</ol>";
		}
		else{
			startComment="<!--";
			endComment="-->";
		}
		replaceAll(result,"#BEGIN_TARGET_LOCATION_MORE#", startComment);
		replaceAll(result,"#END_TARGET_LOCATION_MORE#", endComment);
		replaceAll(result,"#LOCATION_INSIDE_MORE#", Util.getStr(locationInside));
		
	}
	
	private void ReplaceMoreOversea(Resume resume)
	{
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		List<Location> locOutsideList=new LocationManager().getAllOutsideLocation(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		String locationOutside = "";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		
		if(locOutsideList!=null && locOutsideList.size()>0)
		{
			additionalFlag = true;
			locationOutside="<b>"+propMgr.getMessage(locale,"GLOBAL_OVERSEA_TARGET")+"</b>";
			locationOutside+="<ol style='padding-left:18px;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<locOutsideList.size();c++)
			{  
				locationOutside+="<li>";
				Country ct=MasterDataManager.getCountry(Util.getInt(locOutsideList.get(c).getIdCountry()), resume.getIdLanguage());
				if(ct!=null)
				{
					locationOutside+=Util.getStr(ct.getCountryName());
				}
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()),resume.getIdLanguage());
				if(aState!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aState.getStateName());
				}
				else if(aState==null && Util.getStr(locOutsideList.get(c).getOtherState())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(locOutsideList.get(c).getOtherState());
				}
				com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()), Util.getInt(locOutsideList.get(c).getIdCity()), resume.getIdLanguage());
				if(aCity!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aCity.getCityName());
				}
				
				else if(aCity==null && Util.getStr(locOutsideList.get(c).getOtherCity())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(locOutsideList.get(c).getOtherCity());
				}
				if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("TRUE"))
				{   
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_AUTHORIZED");
				}
				else if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("FALSE"))
				{
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_NOT_AUTHORIZED");
				}
				locationOutside+="</li>";
			}
			locationOutside+="</ol>";
		}
		else{
			startComment="<!--";
			endComment="-->";
		}
		
		replaceAll(result,"#BEGIN_TARGET_OUTSIDE_MORE#", startComment);
		replaceAll(result,"#END_TARGET_OUTSIDE_MORE#", endComment);
		replaceAll(result,"#LOCATION_OUTSIDE_MORE#", Util.getStr(locationOutside));
	}
	
	private void ReplaceMoreOverseaHideOther(Resume resume)
	{
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		List<Location> locOutsideList=new LocationManager().getAllOutsideLocation(resume.getIdJsk(),resume.getIdResume(), resume.getIdCountry());
		String locationOutside = "";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		
		if(locOutsideList!=null && locOutsideList.size()>0)
		{
			additionalFlag = true;
			locationOutside="<b>"+propMgr.getMessage(locale,"GLOBAL_OVERSEA_TARGET")+"</b>";
			locationOutside+="<ol style='padding-left:18px;  margin-top:0px;margin-bottom:0px;'>";
			for(int c=0;c<locOutsideList.size();c++)
			{  
				locationOutside+="<li>";
				Country ct=MasterDataManager.getCountry(Util.getInt(locOutsideList.get(c).getIdCountry()), resume.getIdLanguage());
				if(ct!=null)
				{
					locationOutside+=Util.getStr(ct.getCountryName());
				}
				com.topgun.shris.masterdata.State aState=MasterDataManager.getState(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()),resume.getIdLanguage());
				if(aState!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aState.getStateName());
				}
				else if(aState==null && Util.getStr(locOutsideList.get(c).getOtherState())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					//locationOutside+=Util.getStr(locOutsideList.get(c).getOtherState());
					locationOutside+=viewInOriginal;
				}
				com.topgun.shris.masterdata.City aCity=MasterDataManager.getCity(Util.getInt(locOutsideList.get(c).getIdCountry()), Util.getInt(locOutsideList.get(c).getIdState()), Util.getInt(locOutsideList.get(c).getIdCity()), resume.getIdLanguage());
				if(aCity!=null)
				{
					locationOutside+=", ";
					locationOutside+=Util.getStr(aCity.getCityName());
				}
				
				else if(aCity==null && Util.getStr(locOutsideList.get(c).getOtherCity())!="" && isTranslate!=true)
				{
					locationOutside+=", ";
					//locationOutside+=Util.getStr(locOutsideList.get(c).getOtherCity());
					locationOutside+=viewInOriginal;
				}
				if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("TRUE"))
				{   
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_AUTHORIZED");
				}
				else if(Util.getStr(locOutsideList.get(c).getWorkPermit()).equals("FALSE"))
				{
					locationOutside+=" "+propMgr.getMessage(locale,"TARGETJOB_NOT_AUTHORIZED");
				}
				locationOutside+="</li>";
			}
			locationOutside+="</ol>";
		}
		else{
			startComment="<!--";
			endComment="-->";
		}
		
		replaceAll(result,"#BEGIN_TARGET_OUTSIDE_MORE#", startComment);
		replaceAll(result,"#END_TARGET_OUTSIDE_MORE#", endComment);
		replaceAll(result,"#LOCATION_OUTSIDE_MORE#", Util.getStr(locationOutside));
	}
	
	public void ReplaceAdditionalInfoTargetJob(Resume resume)
	{
		String startComment="";
		String endComment="";
		String locale= "en_TH";
		if(chkLocation(resume.getLocale()))
		{
			locale= resume.getLocale();
		}
		String additionalTopic=propMgr.getMessage(locale,"ADDITIONAL_DATA_TARGETJOB");
		if(!additionalFlag){
			startComment="<!--";
			endComment="-->";
			additionalTopic="";
		}
		replaceAll(result,"#BEGIN_ADDITIONAL_DATA_TARGETJOB#", startComment);
		replaceAll(result,"#END_ADDITIONAL_DATA_TARGETJOB#", endComment);
		replaceAll(result,"#ADDITIONAL_DATA_TARGETJOB_PREVIEW#", additionalTopic);
	}

	public String getResumeFont() {
		return resumeFont;
	}

	public void setResumeFont(String resumeFont) {
		this.resumeFont = resumeFont;
	}
}
