package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class MatchResumeAndJob {
	
	public static List<PositionRequired> getPositionRequired(int idEmp,int idPosition)
	{
		List<PositionRequired> list = new ArrayList<PositionRequired>();
		DBManager db = null;
		String sql="";
		int [] idWorktype=null;
		String [] idWorktypeString;
		String ageMinStr="";
		String ageMaxStr="";
		try
		{
			db = new DBManager();
			sql="select sex,degree,exp_less,exp_most,salary_less,salary_most,work_type,show_infield,unitsalary,age_min,age_max from position "+
			" where id_emp=? and id_position=? ";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			if(db.next())
			{
				PositionRequired pos = new PositionRequired();
				pos.setIdEmp(idEmp);
				pos.setIdPosition(idPosition);
				pos.setSex(db.getInt("sex"));   // basic 1 **
				pos.setDegree(db.getInt("degree")); // basic 2 **
				pos.setExpLess(db.getInt("exp_less"));  //basic 4 **
				pos.setExpMost(db.getInt("exp_most"));   //basic 4 **
				pos.setSalaryLess(db.getInt("salary_less")); // advance 1 **
				pos.setSalaryMost(db.getInt("salary_most")); // advance 1 **
				
				if(db.getString("work_type") != null && db.getString("work_type").indexOf("0") != -1) // have 0 is all type
				{
					idWorktypeString=null;
				}
				else 
				{
					idWorktypeString=(db.getString("work_type")!=null)?db.getString("work_type").split(","):null;
				}
				if(idWorktypeString!=null)
				{
					idWorktype =new int[idWorktypeString.length];
					for(int c=0;c<idWorktypeString.length;c++)
					{
						idWorktype[c]=Integer.parseInt(idWorktypeString[c]);
					}
				}
				pos.setWorkType(idWorktype);  //advance 2 **
				pos.setShowInfield(db.getInt("show_infield"));  //basic 4 **
				
				//basic 3 fac-major **
				 if(getPositionMajorAllFac(idEmp,idPosition) == false)
				 {
					 List<FacMajor> facMajorList =  getPositionMajor(idEmp,idPosition);
					 pos.setFacMajor(facMajorList);
				 }
				 //basic 4 experience in field **
				 List<WorkEqui> expList =  getPositionWorkEqui(idEmp,idPosition);
				 pos.setWorkEqui(expList);
				 //basic 5 industry
				 List<JobIndustry> industryList=getIndustry(idEmp,idPosition);
				 pos.setJobIndustry(industryList);
				 //advance 3 **
				 List<JobLocation> locList =  getPositionLocation(idEmp,idPosition);
				 pos.setJobLocation(locList);
				 pos.setUnitSalary(db.getInt("unitsalary"));  //advance 1 **
				 
				 ageMinStr=(db.getString("age_min")!=null)?db.getString("age_min"):"-1";  // basic 1 **
		         ageMaxStr=(db.getString("age_max")!=null)?db.getString("age_max"):"-1";  // basic 1 **
		         pos.setAgeMin(Integer.parseInt(ageMinStr));
		         pos.setAgeMax(Integer.parseInt(ageMaxStr));
				 list.add(pos);
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		
		
		return list;
	}
	
	public static boolean getPositionMajorAllFac(int idEmp,int idPosition)
	{
		
		boolean result=false;
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select id_faculty ,id_major  from position_major where id_emp=? and id_position=? "+
			" and id_faculty = 0 ";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			if(db.next())
			{
				result=true;
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return result;
		
	}
	
	public static List<FacMajor> getPositionMajor(int idEmp,int idPosition)
	{
		
		List<FacMajor> list = new ArrayList<FacMajor>();
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select id_faculty ,id_major  from position_major where id_emp=? and id_position=? "+
			" and id_faculty <> 0 and id_faculty <> -1 and id_faculty <> 33  ";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			while(db.next())
			{
				FacMajor fac = new FacMajor();
				fac.setFaculty(db.getInt("id_faculty"));
				fac.setMajor(db.getInt("id_major"));
				list.add(fac);
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return list;
		
	}
	
	public static List<JobIndustry> getIndustry(int idEmp,int idPosition)
	{
		List<JobIndustry> list = new ArrayList<JobIndustry>();
		DBManager db = null;
		String sql="";
		try
		{
			db = new DBManager();
			sql=" select id,id_emp,id_position,id_industry from position_industry where id_emp=? and id_position=?";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			while(db.next())
			{
				JobIndustry bean = new JobIndustry();
				bean.setId(db.getInt("ID"));
				bean.setIdEmp(db.getInt("ID_EMP"));
				bean.setIdIndustry(db.getInt("ID_INDUSTRY"));
				bean.setIdPosition(db.getInt("ID_POSITION"));
				list.add(bean);
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return list;
	}
	
	public static List<JobLocation> getPositionLocation(int idEmp,int idPosition)
	{
		
		List<JobLocation> list = new ArrayList<JobLocation>();
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select  id_country ,id_state,id_city   from position_location where id_emp=? and id_position=?";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			while(db.next())
			{
				JobLocation loc = new JobLocation();
				loc.setIdCountry(db.getInt("id_country"));
				loc.setIdState(db.getInt("id_state"));
				loc.setIdCity(db.getInt("id_city"));
				list.add(loc);
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return list;
		
	}
	
	
	public static List<WorkEqui> getPositionWorkEqui(int idEmp,int idPosition)
	{
		
		List<WorkEqui> list = new ArrayList<WorkEqui>();
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select  workjob_field ,worksub_field  from position_workequi where id_emp=? and id_position=?";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			while(db.next())
			{
				WorkEqui work =  new WorkEqui();
				work.setJobField(db.getInt("workjob_field"));
				work.setSubField(db.getInt("worksub_field"));
				list.add(work);
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return list;
		
	}
	
	public static boolean compareJobAndResume(int idJsk,int idResume,List <PositionRequired> posList )
	{
		
		boolean result = true;
		DBManager db = null;
		String sql="";
		//System.out.println(idJsk);
		int checkTotalBeforeBreak=0;
		try
		{
			db = new DBManager();
			if(posList !=null && posList.size()>0)
			{
				PositionRequired pos = (PositionRequired) posList.get(0);
				String[] personalData = getJskPersonalData(idJsk,idResume);
				/*********************************** BASIC 4 PRIORITY 1***************************************/
				// exp_less, exp_most, expList(workjob_field,worksub_field ) , (show_infield not concern now) 
				int exp_less=pos.getExpLess();
				int exp_most=pos.getExpMost();
				String sqlExp="";
				
				if(pos.getExpMost() < pos.getExpLess())
				{
					//exp_less=pos.getExpMost();
					//exp_most=pos.getExpLess();
					exp_less=pos.getExpLess();
					exp_most=exp_less;
				}
			/*	if(exp_less != exp_most) // range
				{
					sqlExp=" exp_infield >= "+exp_less+" and exp_infield <="+exp_most;
				}
				else  // >= X
				{
					sqlExp=" exp_infield >= "+exp_less;
				}*/
				if( pos.getWorkEqui() !=null && pos.getWorkEqui().size() > 0)
				{
					int sumInfield = getSumInfield(idJsk,idResume,pos.getWorkEqui());
					
					/***********************************BASIC 1.1 PRIORITY 6***************************************/
					if(exp_less != 0 ) // concern experience
					{
						
						if(sumInfield==0)
						{
							 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"9");  // no experience
							 return false;
						}
						else 
						{
							// check industry
						    if(pos.getJobIndustry().size()!=0)
						    {
								if(matchIndustry(idJsk,idResume, pos.getJobIndustry()) == false)
								{
									insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"2");
									return false;
								}
						    }
							
							// compare exp job and exp jsk
							 if(sumInfield > exp_most && (exp_most!=exp_less) )
							{
								 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"6");  // over experience
								 return false;
							}
							else 
							{
								if(exp_less != exp_most) // range
								{
									if(sumInfield >= exp_less && sumInfield <=  exp_most)
									{
										//pass
									}
									
									else  // check total before break
									{
										 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"3");  // lower experience
									     return false;
									}
								}
								else  // >= X
								{
									if(Integer.parseInt(personalData[1]) >= exp_less)
									{
										//pass
									}
									else // check total before break
									{
										 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"3");  // lower experience
									     return false;
									}
								}
								

							}
						}
					
						
					}
					
				}
				
				/*********************************** BASIC 2 PRIORITY 2***************************************/
				String level_1 = "1";  // match 1
				String level_2= "2,6,8,9,10,12,14"; // match 2
				String level_3="3,6,8,9,10,11,12,13,14"; // match 3
				String level_4="-1,4,9,10,12,15"; // match 4 ,15
				String level_5="-1,5,10,12"; // match 5
				String level_7="7,8,9,10,11,12,13,14"; // match 7
				String level_14="9,10,12,14"; // match 14
	           
				String degreeSql="";
				
				if(exp_less == 0 && exp_most > 0) // concern experience 0 - X years
				{
					String reason="7";
					boolean resultMatchDegree=true;
					boolean resultMatchFacMajor=true;
					if(pos.getDegree() != 0) // 0 is null means not set
					{
						switch(pos.getDegree())
						{
						case -1:  degreeSql="inter_education.id_degree=4 or  inter_education.id_degree=5 or  inter_education.id_degree=15"; break;
						case 1:  degreeSql="inter_education.id_degree=1"; break;
						case 2:  degreeSql="inter_education.id_degree=2"; break;
						case 3:  degreeSql="inter_education.id_degree=3"; break;
						case 4:  degreeSql="inter_education.id_degree=4 or inter_education.id_degree=15"; break;
						case 5:  degreeSql="inter_education.id_degree=5"; break;
						case 6:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3"; break;
						case 7:  degreeSql="inter_education.id_degree=7"; break;
						case 8:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=7"; break;
						case 9:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=4 or inter_education.id_degree=7 or inter_education.id_degree=14 or inter_education.id_degree=15"; break;
						case 10:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=4 or inter_education.id_degree=7 or inter_education.id_degree=14 or inter_education.id_degree=15 or inter_education.id_degree=5"; break;
						case 11:  degreeSql="inter_education.id_degree=3 or inter_education.id_degree=7"; break;
						case 12:   degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=4 or inter_education.id_degree=7 or inter_education.id_degree=14 or inter_education.id_degree=15 or inter_education.id_degree=5"; break;
						case 13:  degreeSql="inter_education.id_degree=3 or inter_education.id_degree=7"; break;
						case 14:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=7 or inter_education.id_degree=14"; break;
						case 15:  degreeSql="inter_education.id_degree=4 or inter_education.id_degree=15";
						}
						
						if(!degreeSql.equals(""))
						{
							
							resultMatchDegree = isMatchDegree(idJsk,idResume,degreeSql);
						}
					}
					/*********************************** BASIC 3 fac-major PRIORITY 3***************************************/
					if(pos.getFacMajor()!=null && pos.getFacMajor().size() > 0)
					{
						
					   resultMatchFacMajor = matchFacMajor(idJsk,idResume, pos.getFacMajor());
						
					}
					if(resultMatchDegree == false || resultMatchFacMajor == false) // for match exp next step
					{
						//check experience
						if( pos.getWorkEqui() !=null && pos.getWorkEqui().size() > 0)
						{
							//boolean resultMatchExp = getSumInfield(idJsk,idResume,pos.getWorkEqui(),sqlExp);
							int sumInfield = getSumInfield(idJsk,idResume,pos.getWorkEqui());
										if(sumInfield > exp_less && sumInfield <=  exp_most)
										{
											//pass
										}
										
										else  // check total before break
										{
											 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"7");  // lower experience
										     return false;
										}
						}
						else 
						{
							insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"7");  // lower experience
						    return false;
						}
					}
				
				}
				else // not concern experience
				{
					
					if(pos.getDegree() != 0) // 0 is null means not set
					{
						switch(pos.getDegree())
						{
						case -1:  degreeSql="inter_education.id_degree=4 or  inter_education.id_degree=5 or  inter_education.id_degree=15"; break;
						case 1:  degreeSql="inter_education.id_degree=1"; break;
						case 2:  degreeSql="inter_education.id_degree=2"; break;
						case 3:  degreeSql="inter_education.id_degree=3"; break;
						case 4:  degreeSql="inter_education.id_degree=4 or inter_education.id_degree=15"; break;
						case 5:  degreeSql="inter_education.id_degree=5"; break;
						case 6:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3"; break;
						case 7:  degreeSql="inter_education.id_degree=7"; break;
						case 8:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=7"; break;
						case 9:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=4 or inter_education.id_degree=7 or inter_education.id_degree=14 or inter_education.id_degree=15"; break;
						case 10:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=4 or inter_education.id_degree=7 or inter_education.id_degree=14 or inter_education.id_degree=15 or inter_education.id_degree=5"; break;
						case 11:  degreeSql="inter_education.id_degree=3 or inter_education.id_degree=7"; break;
						case 12:   degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=4 or inter_education.id_degree=7 or inter_education.id_degree=14 or inter_education.id_degree=15 or inter_education.id_degree=5"; break;
						case 13:  degreeSql="inter_education.id_degree=3 or inter_education.id_degree=7"; break;
						case 14:  degreeSql="inter_education.id_degree=2 or inter_education.id_degree=3 or inter_education.id_degree=7 or inter_education.id_degree=14"; break;
						case 15:  degreeSql="inter_education.id_degree=4 or inter_education.id_degree=15";
						}
						
						if(!degreeSql.equals(""))
						{
							String reason="1";
							boolean resultMatchDegree = isMatchDegree(idJsk,idResume,degreeSql);
							if(resultMatchDegree==false)
							{
								if(exp_less == 0 ) // not concern experience
								{
									reason="7";
								}
								insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,reason);  // match experience and not match edu
								return false;
							}
						}
					}
					/*********************************** BASIC 3 fac-major PRIORITY 3***************************************/
					if(pos.getFacMajor()!=null && pos.getFacMajor().size() > 0)
					{
						String reason="1"; 
						if(matchFacMajor(idJsk,idResume, pos.getFacMajor()) == false)
						{
							if(exp_less == 0 ) // not concern experience
							{
								reason="7";
							}
							insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,reason);  // match experience and not match edu
							return false;
						}
					}
				}
				
				
				
				/*********************************** BASIC 1 PRIORITY 4***************************************/
				if(pos.getSex()==1 || pos.getSex()==2 ) // 1 is MR, 2 is MRS or MISS
				{
					String reason="2"; 
					 if(exp_less == 0 ) // not concern experience
					 {
						 reason="8";
					 }
					 if(personalData !=null)
					 {
						 if(pos.getSex()== 1 &&  personalData[0].toLowerCase().trim().equals("mr"))
						 {
							 result =true;
						 }
						 else if(pos.getSex()== 2 &&  ( personalData[0].toLowerCase().trim().equals("mrs") || personalData[0].toLowerCase().trim().equals("miss") ))
						 {
							 result =true;
						 }
						 else  // not match
						 {
							 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,reason);  // match experience and not match gender
							 return false;
						 }
					 }
					 
				}
				/***********************************BASIC 1.1 PRIORITY 5***************************************/
				/** age_min = -1 , age_max = -1 is not specified
				 *  age_min = 0  , age_max = X  is less than X
				 *  age_min = X  , age_max = 66 is more than X
				 *  age_min = X  , age_max = Y  is age between X and Y
				 */
				String reason="2"; 
				if(exp_less == 0 ) // not concern experience
				{
					reason="8";
				}
			    if(pos.getAgeMin() == 0 && pos.getAgeMax() > 0) // is less than X
				{
			    	if(!(Integer.parseInt(personalData[2]) < pos.getAgeMax())) 
			    	{
			    		 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,reason);  //  not match age
						 return false;
			    	}
				}
				else if(pos.getAgeMin() > 0 && pos.getAgeMax() == 66 ) // is more than X
				{
					if(!(Integer.parseInt(personalData[2]) > pos.getAgeMin()))
					{
						insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,reason);  //  not match age
						return false;
					}
				}
				else if(pos.getAgeMin() > 0 && pos.getAgeMax() > 0 ) // age between X and Y
				{
					if(!((Integer.parseInt(personalData[2]) >= pos.getAgeMin()) && (Integer.parseInt(personalData[2]) <= pos.getAgeMax())) )
					{
						insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,reason);  //  not match age
						return false;
					}
				}
			    
				/*********************************** insert into RESUME_MATCH_JOB(BASIC MATCH) (match jsk)***************************************/
				 insResumeMatchJob(idJsk,idResume,pos.getIdEmp(),pos.getIdPosition(),0,"");  // 0 is BASIC
			}// close position
			
		
			
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		
		
		return result;
	}
	

	//match sex,exp
	public static String[]  getJskPersonalData(int idJsk,int idResume)
	{
		String[] result = null;
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select salutation,exp_year, trunc((months_between(sysdate, birthdate))/12) age from inter_resume where id_jsk = ? and id_resume = ? ";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result = new String[3];
				result[0]=(db.getString("salutation")!=null)?db.getString("salutation"):"";
				result[1]=(db.getString("exp_year")!=null)?db.getString("exp_year"):"0";
				result[2]=(db.getString("age")!=null)?db.getString("age"):"0";
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{ 
			db.close();
		}
		
		return result;
		
	}
	
	public static boolean isMatchDegree(int idJsk,int idResume,String sqlPlus)
	{
		boolean result=false;
		DBManager db = null;
		String sql="";
		try
		{
			db = new DBManager();
			sql="select inter_education.id_degree from  inter_education "+
			"where inter_education.delete_status='FALSE' and id_jsk = ?  and id_resume = ? and ("+sqlPlus+")";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return result;
	}
	public static boolean matchFacMajor(int idJsk,int idResume, List<FacMajor> facMajorList ) 
	{
		boolean result = false;
		DBManager db = null;
		String sql="";
		try
		{
			db = new DBManager();
			sql="select  id_jsk from inter_education where delete_status='FALSE' and id_jsk =? and id_resume=? and ( ";
			for(int c=0;c<facMajorList.size();c++)
			{
				FacMajor fm = (FacMajor) facMajorList.get(c);
				if(c>0){sql+=" or "; }
				sql+=" ( ";
				sql+=" id_fac_major = "+fm.getFaculty();
				if(fm.getMajor()!=0)
				{
					sql+=" and id_major ="+fm.getMajor();
				}
				sql+=" ) ";
			}
		
			sql+=") ";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
			}
			
			
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		
		return result;
	}
	public static boolean matchIndustry(int idJsk,int idResume, List<JobIndustry> jobIndustryList ) 
	{
		boolean result = false;
		DBManager db = null;
		String sql="";
		try
		{
			db = new DBManager();
			sql="select  id_jsk from inter_experience_industry where id_jsk =? and id_resume=? and id_industry in(";
			for(int c=0;c<jobIndustryList.size();c++)
			{
				if(c>0)
				{
					sql+=",";
				}
				sql+=jobIndustryList.get(c).getIdIndustry();
			}
			sql+=") ";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return result;
	}
    
	/*public static boolean getSumInfield(int idJsk,int idResume,List<WorkEqui> expList,String sqlExp)
	{
	   boolean result = false;
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="  select exp_infield from "+
			"( "+
			   "select (sum( exp_y) +sum( exp_m)/12)  as exp_infield "+
			   "from inter_work_experience "+
			   "where delete_status='FALSE' and id_jsk = "+idJsk+" and id_resume="+idResume+"  and  ";
			sql+=" ( ";
			for(int c=0;c<expList.size();c++)
			{
				WorkEqui work = (WorkEqui)expList.get(c);
				if(c>0){sql+=" or";}
				sql+=" ( ";
				          sql+="workjob_field = "+work.getJobField() +" and worksub_field="+work.getSubField();
				sql+=" ) ";
			}
			sql+=" ) ";
          sql+=" ) ";
			sql+=" where  "+sqlExp;
			db.createPreparedStatement(sql);
			db.executeQuery();
			if(db.next())
			{
				result=true;
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
	   return result;
	}*/
	
	public static int getSumInfield(int idJsk,int idResume,List<WorkEqui> expList)
	{
	   int result = 0;
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
		
			  sql= "select (sum( exp_y) +sum( exp_m)/12)  as exp_infield "+
			   "from inter_work_experience "+
			   "where delete_status='FALSE' "+
			   "AND (WORK_JOBTYPE <> 2   AND WORK_JOBTYPE <> 4 AND  WORK_JOBTYPE <> 7) " +
			   "and id_jsk = "+idJsk+" and id_resume="+idResume+"  and  ";
			sql+=" ( ";
			for(int c=0;c<expList.size();c++)
			{
				WorkEqui work = (WorkEqui)expList.get(c);
				if(c>0){sql+=" or";}
				sql+=" ( ";
				          sql+="workjob_field = "+work.getJobField() +" and worksub_field="+work.getSubField();
				sql+=" ) ";
			}
			sql+=" ) ";
			db.createPreparedStatement(sql);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("exp_infield");
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
	   return result;
	}
	
	public static int insResumeMatchJob(int idJsk,int idResume,int idEmp,int idPosition,int levelMatch,String reason) // 0 is basic , 1 is advanced
	{
		int result=0;
		DBManager db = null;
		String sql="";
		String tableName="resume_match_job";
		//String tableName="resume_match_job2";
		if(levelMatch==1)
		{
			 tableName="resume_match_job_closed";
		}
		try
		{
			db = new DBManager();
			sql="insert into "+tableName+" (id_jsk,id_resume,id_emp,id_position,sentdate,not_match_remark) values(?,?,?,?,SYSDATE,?)";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idEmp);
			db.setInt(4, idPosition);
			db.setString(5, reason);
			result=db.executeUpdate();
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		
		return result;
	}
	
	public static int[] getJskExpectedSalary(int idJsk,int idResume)
	{
		int[] result=null;
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select min_expected_salary, max_expected_salary, expected_salary_per  from  inter_targetjob_extension where id_jsk = ? and id_resume=?";
		    db.createPreparedStatement(sql);
		    db.setInt(1, idJsk);
		    db.setInt(2, idResume);
		    db.executeQuery();
		    if(db.next())
		    {
		    	result = new int[3];
		    	result[0]=db.getInt("min_expected_salary");
		    	result[1]=db.getInt("max_expected_salary");
		    	result[2]=db.getInt("expected_salary_per");
		    }
		
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		
		return result;
	}
	
	public static boolean matchJskJobType(int idJsk,int idResume,int [] jobType)
	{
		boolean result =false;
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select jobtype from inter_jobtype where id_jsk =? and id_resume=? and  ( ";
			for(int c=0;c<jobType.length;c++)
			{
				if(c>0){sql+=" or ";}
				sql+=" jobtype ="+jobType[c];
			}
			sql+=" ) ";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
	
	public static boolean matchJskLocation(int idJsk,int idResume,List<JobLocation> locList)
	{
		boolean result = false;
		DBManager db = null;
		String sql="";
		
		try
		{
			db = new DBManager();
			sql="select id_country,id_state,id_city from inter_location where id_jsk=? and id_resume = ? and ( ";
			   if(locList !=null)
			   {
				   for(int c=0;c<locList.size();c++)
				   {
					   JobLocation loc = (JobLocation) locList.get(c);
					   if(c>0){sql+=" or ";}
					   sql+=" ( ";
					   sql+=" id_country="+loc.getIdCountry();
					   if(loc.getIdState() != 0 &&  loc.getIdState() != -1) // emp require
					   {
						   sql+=" and (id_state="+loc.getIdState()+" or id_state = 0)";  // jsk all
					   }
					/*   if(loc.getIdCity() != 0) // 0 is all , -1 is other 
					   {
						   sql+=" and (id_city="+loc.getIdCity()+" or id_city = 0)";  // jsk all
					   }*/
					   sql+=" ) ";
				   }
			   }
			sql+=") ";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=true;
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		
		return result;
	}
	
	public static boolean isSAF(int idResume)
	{
		boolean result = false;
		if(idResume == 0)
		{
			result=true;
		}
		
		return result;
	}
	
	public static boolean isJobRequireExp(int idEmp,int idPosition)
	{
		DBManager db = null;
		String sql="";
		boolean result=true;
		int exp_less=0;
		int exp_most=0;
		
		try
		{
			db = new DBManager();
			sql="select exp_less,exp_most from position "+
			" where id_emp=? and id_position=? ";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			if(db.next())
			{
				exp_less=db.getInt("exp_less"); 
				exp_most=db.getInt("exp_most"); 
				if(exp_less==0)
				{
					result=false;
				}
				
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			db.close();
		}
		return result;
	}
}
