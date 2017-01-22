package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class ResumeStatusManager 
{
	public String getResumeStatus(Resume resume)
	{
		if(resume==null) return "PERSONAL";
		Status psnStatus=getPersonalStatus(resume);
		if(psnStatus!=null)
		{
			return "PERSONALDATA";
		}
		else
		{
			CompleteStatus eduStatus=getEducationCompleteStatus(resume);
			if(eduStatus!=null)
			{
				return "EDUCATION";
			}
			else
			{
				CompleteStatus expStatus=getWorkingExperienceStatus(resume);
				if(expStatus!=null)
				{
					return "EXPERIENCE";
				}
				else
				{
					Status tarStatus=getTargetJobStatus(resume);
					if(tarStatus!=null)
					{
						return "TARGETJOB";
					}
					else
					{
						CompleteStatus strStatus=getStrengthStatus(resume);
						if(strStatus!=null)
						{
							return "STRENGTH";
						}
						else
						{
							Status careerStatus=getCareerObjectiveStatus(resume);
							if(careerStatus!=null)
							{
								return "CAREER_OBJECTIVE";
							}
							else
							{
								CompleteStatus aptStatus=getAptitudeStatus(resume);
								if(aptStatus!=null)
								{
									return "APTITUDE";
								}
								else
								{
									CompleteStatus comStatus=getSkillComputerStatus(resume);
									if(comStatus!=null)
									{
										return "SKILL_COMPUTER";
									}
									else
									{
										CompleteStatus lanStatus=getSkillLanguageStatus(resume);
										if(lanStatus!=null)
										{
											return "SKILL_LANGUAGE";
										}									
									}
								}
							}
						}
					}
				}
			}
		}
		return "TRUE";	
	}
	
	
	public String getRegisterStatus(Resume resume)
	{
		if(resume==null) return null;
		Status psnStatus=getPersonalStatus(resume);
		if(psnStatus!=null)
		{
			return "PERSONALDATA";
		}
		else
		{
			CompleteStatus eduStatus=getEducationCompleteStatus(resume);
			if(eduStatus!=null)
			{
				return "EDUCATION";
			}
			else
			{
				CompleteStatus expStatus=getWorkingExperienceStatus(resume);
				if(expStatus!=null)
				{
					return "EXPERIENCE";
				}
				else
				{
					Status tarStatus=getTargetJobStatus(resume);
					if(tarStatus!=null)
					{
						return "TARGETJOB";
					}
					else
					{
						CompleteStatus strStatus=getStrengthStatus(resume);
						if(strStatus!=null)
						{
							return "STRENGTH";
						}
						else
						{
							CompleteStatus aptStatus=getAptitudeStatus(resume);
							if(aptStatus!=null)
							{
								return "APTITUDE";
							}
						}
					}
				}
			}
		}
		return "TRUE";
	}
	
	public Status getPersonalStatus(Resume resume)
	{
		if(resume==null) return null;
		Status result=null;
		List<String> messages=new ArrayList<String>();
		String firstName="";
		String lastName="";
		if(resume.getTemplateIdCountry() == 216) // Thai
		{
			if(resume.getIdLanguage() == 38) // Thai
			{
				firstName=resume.getFirstNameThai();
				lastName=resume.getLastNameThai();
			}
			else // English = 11
			{
				firstName=resume.getFirstName();
				lastName=resume.getLastName();
			}
			
			if(resume.getSalutation().equals(""))
			{
				messages.add("GLOBAL_SALUTATION");
			}
			if(firstName.equals(""))
			{
				messages.add("GLOBAL_FIRST_NAME");
			}
			if(lastName.equals(""))
			{
				messages.add("GLOBAL_LAST_NAME");
			}
			if(resume.getBirthDate()==null || resume.getBirthDate().equals(""))
			{
				messages.add("GLOBAL_BIRTHDATE");
			}
			if(resume.getIdResume()>0 && resume.getCitizenship().equals(""))
			{
				messages.add("GLOBAL_CITIZENSHIP");
			}
			if(resume.getIdResume()>0 && resume.getOwnCarStatus().equals(""))
			{
				messages.add("GLOBAL_OWNCAR");
			}
		}
		else // 231  US
		{
			firstName=resume.getFirstName();
			lastName=resume.getLastName();

			if(firstName.equals(""))
			{
				messages.add("GLOBAL_FIRST_NAME");
			}

			if(lastName.equals(""))
			{
				messages.add("GLOBAL_LAST_NAME");
			}
		}
		if(resume.getIdResume()>0 && resume.getHomeAddress().equals(""))
		{
			messages.add("GLOBAL_HOME_ADDRESS");
		}
		if(resume.getIdResume()>0 && resume.getIdCountry() == 0)
		{
			messages.add("GLOBAL_COUNTRY");
		}
		if(resume.getIdResume()>0 && resume.getPostal().equals("") && resume.getIdCountry()!=151)
		{
			messages.add("GLOBAL_POSTAL");
		}
		if(resume.getPrimaryPhone().equals(""))
		{
			messages.add("GLOBAL_PRIMARY_PHONE");
		}
		if(messages.size()>0)
		{
			result=new Status();
			result.setId(0);
			result.setMessages(messages);
		}
		return result;
	}
	
	public Status getCareerObjectiveStatus(Resume resume)
	{
		if(resume==null) return null;
		Status result=null;
		List<String> messages=null;
		int count = countCareerObjective(resume);
		if(count==0)
		{
			result=new Status();
			messages=new ArrayList<String>();
			messages.add("CAREEROBJECTIVE");
			result.setId(0);
			result.setMessages(messages);
		}
		return result;
	}
	
	public int countCareerObjective(Resume resume)
	{
		if(resume==null) return 0;
		int result=0;
		String SQL="SELECT COUNT(ID_CAREEROBJECTIVE) AS TOTAL FROM INTER_CAREER_OBJECTIVE   WHERE ID_JSK=? AND ID_RESUME=? AND ( ID_CAREEROBJECTIVE <> 0) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
	
	public Status getTargetJobStatus(Resume resume)
	{
		if(resume==null) return null;
		Status result=null;
		List<String> messages=new ArrayList<String>();
		int countTargetJob = countTargetJob(resume);
		int countIndustry = countIndustry(resume);
		TargetJob tarExt = new TargetJobManager().get(resume.getIdJsk(), resume.getIdResume());//INTER_TARGETJOB_EXTENSION
		if(countTargetJob == 0)
		{
			messages.add("TARGETJOB_TARGET_JOBFIELD");
		}
		if(countIndustry == 0)
		{
			messages.add("TARGETJOB_INDUSTRY_WANTED");
		}
		if(tarExt==null) 
		{
			messages.add("TARGETJOB_AVAILABLE");
		}
		else if((tarExt.getStartJob() == null && tarExt.getStartJobNotice() < 1)  )
		{
			messages.add("TARGETJOB_AVAILABLE");
		}
		
		if(messages.size()>0)
		{
			result=new Status();
			result.setId(0);
			result.setMessages(messages);
		}
		return result;
	}
	
	public int countTargetJob(Resume resume)
	{
		int result=0;
		String SQL="SELECT COUNT(ID_JOBFIELD) AS TOTAL FROM INTER_TARGETJOB   WHERE ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
	
	public int countIndustry(Resume resume)
	{
		if(resume==null) return 0;
		int result=0;
		String SQL="SELECT COUNT(ID_INDUSTRY) AS TOTAL FROM INTER_INDUSTRY   WHERE ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
	

	
	public Status getEducationStatus(Education education)
	{
		Status result=null;
		List<String> messages=new ArrayList<String>();
		if(education.getIdCountry() == 0)
		{
			messages.add("EDUCATION_COUNTRY");
		}

		if(education.getIdSchool() == 0)
		{
			messages.add("EDUCATION_SCHOOL");
		}

		if(education.getIdDegree() == 0)
		{
			messages.add("EDUCATION_DEGREE");
		}

		if(education.getIdDegree() != 1) // 1 is high school
		{
			if(education.getIdFacMajor() == 0)
			{
				messages.add("EDUCATION_FACULTY");
			}

			if(education.getIdMajor() == 0)
			{
				messages.add("EDUCATION_MAJOR");
			}
		}
		
		if(messages.size()>0)
		{
			result=new Status();
			result.setId(education.getId());
			result.setMessages(messages);
		}
		return result;
	}
	
	
	public CompleteStatus getEducationCompleteStatus(Resume resume)
	{
		if(resume==null) return null;
		CompleteStatus result=null;
		List<Status> statusList=null;
		List<Education> eduList=new EducationManager().getAll(resume.getIdJsk(), resume.getIdResume());
		if(resume.getIdCountry()==216 && !isCompleteBachelorDegree(resume))
		{
			if(result==null) 
			{
				result=new CompleteStatus();
				result.setSection("EDUCATION");
			}
			result.setMessage("EDUCATION_MISS_BACHELOR");
		}
		
		if(eduList.size()>0)
		{
			for(int i=0; i<eduList.size();i++)
			{
				Status status=getEducationStatus(eduList.get(i));
				if(status!=null)
				{
					if(statusList==null)
					{
						statusList=new ArrayList<Status>();
					}
					statusList.add(status);
				}
			}
			
			if(statusList!=null)
			{
				if(statusList.size()>0)
				{
					if(result==null) 
					{
						result=new CompleteStatus();
						result.setSection("EDUCATION");
						result.setStatusList(statusList);
					}
				}
			}
		}
		else
		{
			if(result==null) 
			{
				result=new CompleteStatus();
				result.setSection("EDUCATION");
			}
			result.setMessage("EDUCATION_NOT_EXIST");
		}
		return result;
	}
	
	
	public boolean isCompleteBachelorDegree(Resume resume)
	{
		if(resume==null) return false;
		boolean result=false;
		int chkUpper=0,chkBach=0;
		List<Education> edu=new EducationManager().getAll(resume.getIdJsk(), resume.getIdResume());
		if(edu.size()>0)
		{
			for (int i = 0; i < edu.size(); i++)
			{
				if (edu.get(i).getIdDegree() == 7 || edu.get(i).getIdDegree() ==3)
				{
					chkUpper = 1;
				}
				else if (edu.get(i).getIdDegree() == 2)
				{
					chkBach = 1;
				}
			}
		}
		if (chkUpper ==1 && chkBach == 1)
		{
			result=true;
		}
		else
		{
			if(chkUpper == 1)
			{
				result = false;
			}
			else
			{
				result = true;
			}
		}
		return result;
	}
	
	public List<String> checkCompleteWorkingExperience(WorkExperience workList, Resume resume)
	{
		List<String> messages=new ArrayList<String>();
		int countIndustry=0;
		if(workList.getCompanyName().equals(""))
		{
			messages.add("WORKEXP_COMNAME");
		}
		if(workList.getIdCountry() == 0)
		{
			messages.add("GLOBAL_COUNTRY");
		}
		countIndustry=getExpIndustryCount(resume,workList.getId());
		if(countIndustry == 0)
		{
			messages.add("WORKEXP_INDUSTRY");
		}
		if(workList.getPositionLast().equals(""))
		{
			messages.add("WORKEXP_LASTEST_POS");
		}
		if(workList.getWorkJobField() == 0)
		{
			messages.add("WORKEXP_JOBFIELD");
		}
		if(workList.getWorkSubField() == 0)
		{
			messages.add("WORKEXP_EQUIVALENT");
		}
		if(workList.getWorkStart() == null)
		{
			messages.add("WORKEXP_START_DATE");
		}
		if(workList.getWorkEnd() == null)
		{
			messages.add("WORKEXP_END_DATE");
		}
		return messages;
	}
	
	public CompleteStatus getWorkingExperienceStatus(Resume resume)
	{
		if(resume==null) return null;
		CompleteStatus result=null;
		List<Status> statusList=new ArrayList<Status>();
		if(resume.getIdResume()==0)
		{
			List<String> messages=new ArrayList<String>();;
			String expStatus=Util.getStr(resume.getExpStatus());
			if(!expStatus.equals("FALSE"))
			{
				int expCompany=resume.getExpCompany();
				if(expCompany>0)
				{
					WorkExperience workLast=new WorkExperienceManager().getLatestExperience(resume.getIdJsk(), resume.getIdResume());
					if(workLast!= null)
					{
						messages=checkCompleteWorkingExperience(workLast, resume);
						if(messages.size()>0)
						{
							Status status=new Status();
							status.setId(workLast.getId());
							status.setMessages(messages);
							statusList.add(status);
						}
					}
					else 
					{
						messages.add("WORK_LAST");
					}
				}
				else 
				{
					messages.add("EXP_COMPANY");
				}
				if(messages.size()>0)
				{
					Status status=new Status();
					status.setId(1);
					status.setMessages(messages);
					statusList.add(status);
				}
			}
		}
		else// other resume 
		{
			List<WorkExperience> workList= new WorkExperienceManager().getAllFulltimes(resume.getIdJsk(), resume.getIdResume());
			if (workList != null)
			{
				for (int co=0;co<workList.size();co++)
				{
					List<String> messages=checkCompleteWorkingExperience(workList.get(co), resume);
					if(messages.size()>0)
					{
						Status status=new Status();
						status.setId(workList.get(co).getId());
						status.setMessages(messages);
						statusList.add(status);
					}
				}
			}	
		}
		if(statusList.size()>0)
		{
			result=new CompleteStatus();
			result.setSection("WORKING_EXPERIENCE");
			result.setStatusList(statusList);
		}
		return result;
	}
	
	public int getExpIndustryCount(Resume resume,int idWork)
	{
		if(resume==null) return 0;
		int result=0;
		String SQL="SELECT COUNT(ID_WORK) AS TOTAL FROM INTER_EXPERIENCE_INDUSTRY WHERE ID_JSK=? AND ID_RESUME=? AND ID_WORK=?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
			db.setInt(3, idWork);
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
	
	public CompleteStatus getStrengthStatus(Resume resume)
	{
		if(resume==null) return null;
		CompleteStatus result=null;
		List<Strength> strength=new StrengthManager().getAll(resume.getIdJsk(), resume.getIdResume());
		if(strength.size()>0)
		{
			int countRank=0;
			for(int c=0;c<strength.size();c++)
			{
				if(Util.getInt(strength.get(c).getStrengthOrder())>0 && Util.getInt(strength.get(c).getStrengthOrder())!=9)//not rank
				{
					countRank++;
				}
			}
			if(strength.size()>5)
			{
				if(countRank<5)
				{
					result=new CompleteStatus();
					result.setSection("STRENGTH_ORDER");
				}
			}
			else
			{
				if(strength.size()!=countRank)
				{
					result=new CompleteStatus();
					result.setSection("STRENGTH_ORDER");
				}
			}
		}
		else
		{
			result=new CompleteStatus();
			result.setSection("STRENGTH");
		}
		return result;
	}
	
	public CompleteStatus getAptitudeStatus(Resume resume)
	{
		if(resume==null) return null;
		CompleteStatus result=null;
		int countAptitude = getHobbyCountByCountry(resume);
		if(countAptitude == 0)
		{
			result=new CompleteStatus();
			result.setSection("APTITIDE_INCOMPLETE");
		}
		else
		{

			int countAptitudeRanked =  getHobbyCountByCountryRanked(resume);
			if(countAptitudeRanked == 0)
			{
				result=new CompleteStatus();
				result.setSection("APTITIDE_RANK_INCOMPLETE");
			}

		}

		return result;
	}
	public int getHobbyCountByCountry(Resume resume)
	{
		if(resume==null) return 0;
		int result=0;
		String SQL="SELECT COUNT(ID_HOBBY) AS TOTAL FROM INTER_HOBBY  WHERE (ID_JSK=?) AND (ID_RESUME=?) " ;
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
	
	public int getHobbyCountByCountryRanked(Resume resume)
	{
		if(resume==null) return 0;
		int result=0;
		String SQL=	"SELECT COUNT(ID_HOBBY) AS TOTAL FROM INTER_HOBBY WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (HOBBY_ORDER > 0)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
	
	public CompleteStatus getSkillComputerStatus(Resume resume)
	{
		if(resume==null) return null;
		CompleteStatus result=null;
		int comCnt=countComputerSkill(resume);
		if(comCnt==0)
		{
			result=new CompleteStatus();
			result.setSection("SKILLS_COMPUTER");
		}
		return result;
	}

	public CompleteStatus getSkillLanguageStatus(Resume resume)
	{
		if(resume==null) return null;
		CompleteStatus result=null;
		int langCnt=countLanguageSkill(resume);
		if(langCnt==0)
		{
			result=new CompleteStatus();
			result.setSection("SKILLS_LANGUAGE");
		}
		return result;
	}
	
	
	public int countComputerSkill(Resume resume)
	{
		if(resume==null) return 0;
		int rs=0;
		int result=countComputerSkillOther(resume);
		String SQL="SELECT COUNT(ID_COMPUTER) AS TOTAL FROM INTER_SKILL_COMPUTER   WHERE ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
			db.executeQuery();
			if(db.next())
			{
				rs=db.getInt("TOTAL");
			}
			result=result+rs;
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
	
	public int countComputerSkillOther(Resume resume)
	{
		if(resume==null) return 0;
		int result=0;
		String SQL="SELECT COUNT(*) AS TOTAL FROM INTER_SKILL_OTHER WHERE (ID_JSK=?) AND (ID_RESUME=?) ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
	
	public int countLanguageSkill(Resume resume)
	{
		if(resume==null) return 0;
		int result=0;
		String SQL="SELECT COUNT(ID_SKILL_LANGUAGE) AS TOTAL FROM INTER_SKILL_LANGUAGE   WHERE ID_JSK=? AND ID_RESUME=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, resume.getIdJsk());
			db.setInt(2, resume.getIdResume());
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
}
