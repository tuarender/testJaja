package com.topgun.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.TargetJob;
import com.topgun.resume.TargetJobManager;
import com.topgun.util.DBManager;

public class JobupdateManager {
	public static String getJobfieldJobupdate(int idJsk,int idResume)
	{	
		String jobField="",subField="",reSult="";
		String SQL=" select id_jobfield ,id_subfield from inter_targetjob where  id_jsk=? and id_resume = ? ";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.executeQuery();
				while(db.next())
				{	String jf = db.getString("id_jobfield")!=null?db.getString("id_jobfield"):"";
					String sf = db.getString("id_subfield")!=null?db.getString("id_subfield"):"";
					if(jobField.equals("")){
						jobField=jf;
					}else{
						jobField=jobField+","+jf;	
					}
					if(subField.equals("")){
						subField=sf;
					}else{
						subField=subField+","+sf;	
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
		reSult=jobField+"/"+subField;
		return reSult;
	}
	public static String getLocationJobupdate(int idJsk,int idResume)
	{	
		String city="",state="",reSult="";
		String SQL="select id_city,id_state from inter_location where id_jsk=? and id_resume = ?  order by id_location";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{	String ci=db.getString("id_city")!=null?db.getString("id_city"):"";
				String st=db.getString("id_state")!=null?db.getString("id_state"):""; 
				if(city.equals("")){
					city=ci;
				}else{
					city=city+","+ci;	
				}
				if(state.equals("")){
					state=st;
				}else{
					state=state+","+st;	
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
		reSult=state+"/"+city;
		return reSult;
	}
	public static String getIndustryJobupdate(int idJsk,int idResume)
	{	
		String industry="";
		String SQL=" select id_industry from inter_industry where id_jsk=? and id_resume = ?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{
				String ind=db.getString("id_industry")!=null?db.getString("id_industry"):"";
				if(industry.equals("")){
					industry=ind;
				}else{
					industry=industry+","+ind;	
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
		
		return industry;
	}
	public static String getJobTypeJobupdate(int idJsk,int idResume)
	{	
		String jobtype="";
		String SQL=" select jobtype from inter_jobtype where id_jsk=? and id_resume = ?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next()){
				String jt=db.getString("jobtype")!=null?db.getString("jobtype"):"";
				if(jobtype.equals("")){
					jobtype=jt;
				}else{
					jobtype+=","+jt;	
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
		
		return jobtype;
	}
	public static String getSalaryJobupdate(int idJsk,int idResume)
	{	
		String salary="",Msalary="",reSult="";
		TargetJob tj = new TargetJobManager().get(idJsk, idResume);
		int sal=tj.getMinExpectedSalary();
		int salM = tj.getMaxExpectedSalary();
		int salPer = tj.getExpectedSalaryPer();
				if(salPer==1){salary=String.valueOf(sal/12);}
				 else if(salPer==2){salary=String.valueOf(sal);}
				 else if(salPer==3){salary=String.valueOf(sal*4);}
				 else if(salPer==4){salary=String.valueOf(sal*2);}
				 else if(salPer==5){salary=String.valueOf(sal*30);}
				 else if(salPer==6){salary=String.valueOf(sal*168);}//8 hr * 21 day = 168 hr
				 if(salPer==1){salary=String.valueOf(salM/12);}
				 else if(salPer==2){Msalary=String.valueOf(salM);}
				 else if(salPer==3){Msalary=String.valueOf(salM*4);}
				 else if(salPer==4){Msalary=String.valueOf(salM*2);}
				 else if(salPer==5){Msalary=String.valueOf(salM*30);}
				 else if(salPer==6){Msalary=String.valueOf(salM*168);}
		reSult=salary+","+Msalary;
		return reSult;
	}
	
	
	public static String SqlJobUpdate(int idJsk,int idResume)
	{	
		Resume resume = new ResumeManager().get(idJsk, idResume);
		String sex = resume.getSalutation(); 
		
		String education[]= getLastEducation(idJsk,idResume).split(",");
		String workEnd=getLastWorkFieldYear(idJsk, idResume);
		String idDegree="",idMajor="",idFaculty="",finishYear="";
		if(education !=null){
			for(int i=0; i<education.length;i++)
			{
				if(i==0){idDegree=  education[i];}
				if(i==1){idMajor=  education[i];}
				if(i==2){idFaculty=  education[i];}
				if(i==3){finishYear=  education[i];}
			}
		}
		int iddegree=0;
		if(!idDegree.equals("")){iddegree = Integer.parseInt(idDegree);}
		
		/* Jobfield */
		String jfSf[] = getJobfieldJobupdate(idJsk, idResume).split("/");
		String sql_JfSf="";	
		
		if(jfSf!=null){
			if(jfSf.length>=2){
				String jobfield[] = jfSf[0].trim().split(",");
				String subfield[] = jfSf[1].trim().split(",");
		           	
			   for (int iJF = 0 ; iJF < jobfield.length ;iJF++){
			   	  if (((!(jobfield[iJF].equals("-1"))) && (!(subfield[iJF].equals("-1")))) && ((!(jobfield[iJF].equals(""))) && (!(subfield[iJF].equals(""))))){
			   		  if(sql_JfSf==""){
			   			  sql_JfSf+="(PW.WORKJOB_FIELD="+jobfield[iJF]+" AND PW.WORKSUB_FIELD="+subfield[iJF]+")";
			   		  }else{
			   			  sql_JfSf+=" OR (PW.WORKJOB_FIELD="+jobfield[iJF]+" AND PW.WORKSUB_FIELD="+subfield[iJF]+")"; 	
			   		  }
			   	  }	
			   }
			}
		}
		if(sql_JfSf.equals("")){ 
			if(!idFaculty.equals(0)){
				sql_JfSf+=" (ID_FACULTY="+idFaculty+") ";
				if(!idMajor.equals(0))
					sql_JfSf+=" AND (ID_MAJOR="+idMajor+") ";
			}
		}  
		if(!sql_JfSf.equals("")){ sql_JfSf= "("+sql_JfSf+")";}   
		/* Jobfield */
		
		/* Industry */
		String industry[]= getIndustryJobupdate(idJsk, idResume).split(",");
		String sql_IND="",ALL_INDUSTRY="NO";	
		if (industry!=null){
		  for (int iIND = 0 ; iIND < industry.length ;iIND++){
		   	 if (!(industry[iIND].equals("-1"))  &&(!(industry[iIND].equals("")))){
		   		 if (!(industry[iIND].equals("9999"))){ // chk all
		   			 if(sql_IND.equals("")){
		   				 sql_IND+="(EI.ID_INDUSTRY="+industry[iIND]+")";
		   			 }else{
		   				 sql_IND+=" OR (EI.ID_INDUSTRY="+industry[iIND]+")"; 	
			   	  }
				}else{
					ALL_INDUSTRY="YES";	
				}
		   	 }
		  }
		}
		if(!(sql_IND.equals(""))){ sql_IND= "("+sql_IND+")";}      
		/* Industry */
		/* Location */
		String locCity[] = getLocationJobupdate(idJsk, idResume).split("/");
		String sql_LOC="";	
		if(locCity!=null){
			if(locCity.length>=2){
				String location[] = locCity[0].trim().split(",");
				String city[] = locCity[1].trim().split(",");
				if(location!=null && city!=null){
		           for (int iLOC = 0 ; iLOC < location.length ;iLOC++){
		        	   if(!((location[iLOC].equals("-1"))) && ((!location[iLOC].equals("")))){
		        		   if ((location[iLOC].equals("77"))){
					   		  if(!city[iLOC].equals("0")){
							   	  if(sql_LOC==""){
							   	  	  sql_LOC+="(PL.ID_STATE="+location[iLOC]+" AND PL.ID_CITY="+city[iLOC]+")";
									
							   	  }else{
									  sql_LOC+=" OR (PL.ID_STATE="+location[iLOC]+" AND PL.ID_CITY="+city[iLOC]+")"; 	
									}
					   		  }else if(!city[iLOC].equals("-1")){
						   			if(sql_LOC==""){
						   				sql_LOC+="(PL.ID_STATE='77')";
									}else{
										sql_LOC+=" OR (PL.ID_STATE='77')"; 	
									}  
					   		  }
					   	  }else{
					   		  if(sql_LOC==""){
					   			  sql_LOC+="(PL.ID_STATE="+location[iLOC]+")";
					   		  }else{
					   			  sql_LOC+=" OR (PL.ID_STATE="+location[iLOC]+")"; 	
					   		  }
					   	  }
		        	   }	
		           }
				}
			}
		}
		if(sql_LOC!=""){ sql_LOC= "("+sql_LOC+")";}  
		/* Location */
		
		/* Salary */
		String sl="",sm="";
		TargetJob tj = new TargetJobManager().get(idJsk, idResume);
		int sal=tj.getMinExpectedSalary();
		int salM = tj.getMaxExpectedSalary();
		int salPer = tj.getExpectedSalaryPer();
		String sql_SAL="";
		if(salPer==1){sl=String.valueOf(sal/12);}
		else if(salPer==2){sl=String.valueOf(sal);}
		else if(salPer==3){sl=String.valueOf(sal*4);}
		else if(salPer==4){sl=String.valueOf(sal*2);}
		else if(salPer==5){sl=String.valueOf(sal*20);}
		else if(salPer==6){sl=String.valueOf(sal*168);}//8 hr * 21 day = 168 hr
		
	    if(salPer==1){sm=String.valueOf(salM/12);}
		else if(salPer==2){sm=String.valueOf(salM);}
		else if(salPer==3){sm=String.valueOf(salM*4);}
		else if(salPer==4){sm=String.valueOf(salM*2);}
		else if(salPer==5){sm=String.valueOf(salM*20);}
		else if(salPer==6){sm=String.valueOf(salM*168);}
	   
		if(!(sl.equals(""))){
			sql_SAL+=" ((POSITION.SALARY_LESS <="+sl+" AND POSITION.SALARY_MOST >="+sl+") OR (POSITION.SALARY_LESS <0) OR (POSITION.SALARY_MOST <0))";
			sql_SAL+=" OR (POSITION.SALARY_LESS <="+sl+" AND (POSITION.SALARY_LESS*1.5) >="+sl+")";
		}
		if(!(sm.equals(""))){
			if(sql_SAL.equals("")){ 
				sql_SAL+=" ((POSITION.SALARY_LESS <="+sm+" AND POSITION.SALARY_MOST >="+sm+") OR (POSITION.SALARY_LESS <0) OR (POSITION.SALARY_MOST <0))";	
				sql_SAL+=" OR (POSITION.SALARY_LESS <="+sm+" AND (POSITION.SALARY_LESS*1.5) >="+sm+")";
			}else{
				sql_SAL+=" OR ((POSITION.SALARY_LESS <="+sm+" AND POSITION.SALARY_MOST >="+sm+") OR (POSITION.SALARY_LESS <0) OR (POSITION.SALARY_MOST <0))";
				sql_SAL+=" OR (POSITION.SALARY_LESS <="+sm+" AND (POSITION.SALARY_LESS*1.5) >="+sm+")";
			}
		}	
			
		if(!(sql_SAL.equals(""))){ sql_SAL= "("+sql_SAL+")";}  
		/* Salary */
		
	    /* JobType */
		String jobtype[] = getJobTypeJobupdate(idJsk, idResume).split(",");
		String sql_JT="";
		
		if(jobtype!=null){
			for (int iJT = 0 ; iJT < jobtype.length ;iJT++){
				if(!jobtype[iJT].equals("")){
					if(sql_JT.equals("")){
						sql_JT+="(POSITION.WORK_TYPE like '%"+jobtype[iJT]+"%')";
					}else{
						sql_JT+=" OR (POSITION.WORK_TYPE like '%"+jobtype[iJT]+"%')"; 	
					}
				}
			}
		}
		if(!sql_JT.isEmpty())
			sql_JT = "("+sql_JT+" OR POSITION.WORK_TYPE LIKE '%1%' OR POSITION.WORK_TYPE IS NULL)";
		else
			sql_JT = "(POSITION.WORK_TYPE LIKE '%1%' OR POSITION.WORK_TYPE IS NULL)";
		if(!(sql_JT.equals(""))){ sql_JT= "("+sql_JT+" OR POSITION.WORK_TYPE like '%1%')";}else{sql_JT= " (POSITION.WORK_TYPE like '%1%') ";}   
		
		
		/* Experince */
		
		int exp = resume.getExpYear();
		
		int minExp=0,maxExp=0;
		int newExpYear=getTotalNewExp(exp,finishYear,workEnd);
		switch(newExpYear){
			case 0: minExp=0;maxExp=0; break;
	    	case 1: minExp=1;maxExp=2; break;
	        case 2: minExp=1;maxExp=3; break;
	        case 3: minExp=1;maxExp=5; break;
	        case 4: minExp=2;maxExp=6; break;
	        case 5: minExp=3;maxExp=8; break;
	        case 6: minExp=4;maxExp=9; break;
	        case 7: minExp=5;maxExp=10; break;
	        case 8: minExp=5;maxExp=15; break;		        
	        case 9: minExp=6;maxExp=15; break;
	        case 10: minExp=7;maxExp=25; break;
	        case 11: minExp=8;maxExp=25; break;
	        case 12: minExp=8;maxExp=25; break;
	        case 13: minExp=8;maxExp=25; break;
	        case 14: minExp=8;maxExp=25; break;
	        case 15: minExp=8;maxExp=25; break;
	        default : minExp=8;maxExp=25; break;
		}
		String sql_EXP = "";
		if(newExpYear>0)
		{	
			sql_EXP="(((exp_less<= "+minExp+") AND (exp_most>="+minExp+")) or ((exp_less>="+minExp+") and (exp_less<="+maxExp+")) or exp_less = "+newExpYear+") ";
		}
		else
		{	
			sql_EXP="(exp_less=0) ";
		}
		
		/* End Experince */
		
		
		/********************** 
		* Gender 20110803
		**********************/		
		String sql_Sex = "";
		if((sex.equals("MRS"))||sex.equals("MISS"))  //If Female 
		{
			sql_Sex=" (SEX!='1') "; //Exclude Male
		}
		else if(sex.equals("MR")) //If Male
		{
			sql_Sex=" (SEX!='2') "; //Exclude Female
		}
		
		/**********************
		* Degree 20110803
		**********************/	
		String sql_Degree = "";
		if(!idDegree.equals(""))
		{	
			if(iddegree==1)
			{
				sql_Degree="  (DEGREE='1') ";
			}
			else if(iddegree==2)
			{	 	
				sql_Degree="  ((DEGREE='2') OR (DEGREE='6') OR (DEGREE='8')) ";
			}
			else if(iddegree==3)
			{
				sql_Degree=" ((DEGREE='3') OR (DEGREE='6') OR (DEGREE='8') OR (DEGREE='11') OR (DEGREE='13')) ";
			}
			else if(iddegree==4)
			{
				sql_Degree=" ((DEGREE='4') OR (DEGREE='9') OR (DEGREE='10') OR (DEGREE='12') OR (DEGREE='-1')) ";
			}
			else if(iddegree==5)
			{
				sql_Degree=" ((DEGREE='5') OR (DEGREE='9') OR (DEGREE='10') OR (DEGREE='12') OR (DEGREE='-1')) ";
			}
			else if(iddegree==7)
			{
				sql_Degree=" ((DEGREE='7') OR (DEGREE='8') OR (DEGREE='11')) ";
			}
		}
		
		String SQL_UPDATE="";
		
		if(!(sql_JfSf.equals(""))){SQL_UPDATE+=" AND "+sql_JfSf;}  
		if(!(sql_IND.equals(""))){if(ALL_INDUSTRY=="NO"){SQL_UPDATE+=" AND "+sql_IND;}} 
		if(!(sql_LOC.equals(""))){SQL_UPDATE+=" AND "+sql_LOC;}         
        if(!(sql_SAL.equals(""))){SQL_UPDATE+=" AND "+sql_SAL;}  
        if(!(sql_JT.equals(""))){SQL_UPDATE+=" AND "+sql_JT;}
        if(!(sql_Sex.equals(""))){SQL_UPDATE+=" AND "+sql_Sex;}
        if(!(sql_Degree.equals(""))){SQL_UPDATE+=" AND "+sql_Degree;}
        if(!(sql_EXP.equals(""))){SQL_UPDATE+=" AND "+sql_EXP;}
       // System.out.println("idJsk> "+idJsk+"    idResume> "+idResume+"   old= "+exp+"   newExpYear > "+newExpYear+"   SQL_UPDATE > "+SQL_UPDATE);
        return SQL_UPDATE;
	}	
	public static String getJfSfNameJobupdate(int idJsk,int idResume){
		String result="";
		String jfSf[] = getJobfieldJobupdate(idJsk, idResume).split("/");
		String sql_JfSf="";	
		if(jfSf!=null){
		if(jfSf.length>=2){
			String jobfield[] = jfSf[0].trim().split(",");
			String subfield[] = jfSf[1].trim().split(",");
	           	
		   for (int iJF = 0 ; iJF < jobfield.length ;iJF++){
		   	  if (((!(jobfield[iJF].equals("-1"))) && (!(subfield[iJF].equals("-1")))) && ((!(jobfield[iJF].equals(""))) && (!(subfield[iJF].equals(""))))){
			   	  if(sql_JfSf==""){
			   	  	  sql_JfSf= sql_JfSf+"(jf.ID_FIELD="+jobfield[iJF]+" AND sf.ID_SUBFIELD="+subfield[iJF]+")";
					}else{
					  sql_JfSf= sql_JfSf+" OR (jf.ID_FIELD="+jobfield[iJF]+" AND sf.ID_SUBFIELD="+subfield[iJF]+")"; 	
					}
				}
			}
		   
		}
		}
		if(sql_JfSf.equals("")){
			result="-";
		}else{
			String SQL=" select field_name,subfield_name from INTER_JOBFIELD jf, INTER_SUBFIELD  sf where jf.id_field = sf.id_field and ("+sql_JfSf+")";
			DBManager db=null;
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.executeQuery();
				while(db.next()){ 
					String jfResult=db.getString("field_name")!=null?db.getString("field_name"):"";
					String sfResult=db.getString("subfield_name")!=null?db.getString("subfield_name"):"";
				
					if(result.equals("")){
						result=jfResult+" : "+sfResult;
					}else{
						result=result+"<br>"+jfResult+" : "+sfResult;	
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
		}
		
		return result;
		
	}
	public static String getJtNameJobupdate(int idJsk,int idResume){
		String result="";
		String jobtype[] = getJobTypeJobupdate(idJsk, idResume).split(",");
		String jobTypeName="";
		
		if(jobtype!=null){
		  for (int iJT = 0 ; iJT < jobtype.length ;iJT++){
		   	    if(!jobtype[iJT].equals("")){
		   	    	if(jobtype[iJT].equals("1")){
		   	    		jobTypeName = "Full Time";
		   	    	}else if(jobtype[iJT].equals("2")){
		   	    		jobTypeName = "Part Time";
		   	    	}else if(jobtype[iJT].equals("3")){
		   	    		jobTypeName = "Contract/Temp";
		   	    	}else if(jobtype[iJT].equals("4")){
		   	    		jobTypeName = "Freelance";
		   	    	}else if(jobtype[iJT].equals("5")){
		   	    		jobTypeName = "Seasonal";
		   	    	}else if(jobtype[iJT].equals("6")){
		   	    		jobTypeName = "All";
		   	    	}else if(jobtype[iJT].equals("7")){
		   	    		jobTypeName = "Internship";
		   	    	}
			   	  if(result.equals("")){
			   		  result= result+jobTypeName;
					}else{
					  result= result+", "+jobTypeName; 	
					}
		   	    }
			}
		}
		if(result.equals("")){result="-";}
		return result;
	}
	public static String getIndustryNameJobupdate(int idJsk,int idResume)
	{	
		String industry="";
		String SQL="  select i.INDUSTRY_NAME from inter_industry ii,INTER_INDUSTRY_LANGUAGE i  where  i.id_industry = ii.id_industry and i.ID_LANGUAGE ='11' and id_jsk=? and id_resume = ?";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{
				String ind=db.getString("INDUSTRY_NAME")!=null?db.getString("INDUSTRY_NAME"):"";
				if(industry.equals("")){
					industry=ind;
				}else{
					industry=industry+",  "+ind;	
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
		
		return industry;
	}
	public static String getSalaryNameJobupdate(int idJsk,int idResume)
	{
		String result="";	
		String sl="",sm="";
		TargetJob tj = new TargetJobManager().get(idJsk, idResume);
		int sal=tj.getMinExpectedSalary();
		int salM = tj.getMaxExpectedSalary();
		int salPer = tj.getExpectedSalaryPer();
		int salCurrency = tj.getSalaryCurrency();
		String currencyName = salCurrency!=0?JobManager.getCurrency(salCurrency):"Thai Baht";
		if(!(sal==-1)){// no insert sallary
			if(salPer==1){sl=String.valueOf(sal/12);}
			else if(salPer==2){sl=String.valueOf(sal);}
			else if(salPer==3){sl=String.valueOf(sal*4);}
			else if(salPer==4){sl=String.valueOf(sal*2);}
			else if(salPer==5){sl=String.valueOf(sal*30);}
			else if(salPer==6){sl=String.valueOf(sal*168);}//8 hr * 21 day = 168 hr
		    if(salPer==1){sm=String.valueOf(salM/12);}
			else if(salPer==2){sm=String.valueOf(salM);}
			else if(salPer==3){sm=String.valueOf(salM*4);}
			else if(salPer==4){sm=String.valueOf(salM*2);}
			else if(salPer==5){sm=String.valueOf(salM*30);}
			else if(salPer==6){sm=String.valueOf(salM*168);}
		}
		if((sl.equals("")&&sm.equals(""))||(sl.equals("0")&&sm.equals("0"))){
		result="-";	
		}else{
		result= sl+" - "+ sm + " "+currencyName+" per Month.";
		}
		return result;
   }
	public static String getLocationNameJobupdate(int idJsk,int idResume)
	{	
		String reSult="";
		String SQL=	"SELECT ID_STATE,ID_CITY FROM INTER_LOCATION WHERE ID_JSK = ? AND  ID_RESUME = ? AND ID_COUNTRY='216' ORDER BY ID_STATE";
		DBManager db=null;
		String stateName="";
		try
		{	
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.executeQuery();
			while(db.next())
			{	
				String SQL2 ="SELECT STATE_NAME from inter_state  WHERE ID_COUNTRY='216' AND ID_STATE=?";
				DBManager db2=null;
				try
				{
					db2=new DBManager();
					db2.createPreparedStatement(SQL2);
					db2.setInt(1, db.getInt("ID_STATE"));
					db2.executeQuery();
					if(db2.next())
					{
						if(stateName.equals("")){
						stateName = db2.getString("STATE_NAME");
						}else{
						stateName = stateName+" , "+db2.getString("STATE_NAME");	
						}
					}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						db2.close();
					}
					if(
						(!db.getString("ID_CITY").equals(""))
						&&(db.getString("ID_CITY") != null) 
						&&!(db.getString("ID_CITY").equals("0"))
						&&(db.getString("ID_STATE").equals("77"))
						){
					String SQL3 ="SELECT CITY_NAME from INTER_CITY  WHERE ID_COUNTRY='216' AND ID_STATE='77' AND ID_CITY=?";
					DBManager db3=null;
					
					try
					{
						db3=new DBManager();
						db3.createPreparedStatement(SQL3);
						db3.setInt(1, db.getInt("ID_CITY"));
						db3.executeQuery();
						if(db3.next())
						{
							
							stateName = stateName+" : "+db3.getString("CITY_NAME");
						
						}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							db3.close();
						}
					}else if(db.getString("ID_CITY").equals("0")){
						stateName = stateName+" : All District";
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
		reSult=stateName;
		if(reSult.equals("")){reSult="-";}
		return reSult;
	}
	
	public static String getLastEducation(int idJsk,int idResume)
	{
		String result="",idDeg="",idMaj="",idFac="",finishYear="";
		String SQL=" SELECT e.id_degree,e.id_major,e.id_fac_major,to_char(finish_date,'YYYY') as finishYear FROM  INTER_EDUCATION e LEFT JOIN INTER_DEGREE d ON e.id_degree = d.id_degree "+ 
				   " WHERE id_jsk = ? AND id_resume= ? AND degree_order IN "+
				   "(SELECT MIN(degree_order) FROM  INTER_EDUCATION e LEFT JOIN	INTER_DEGREE d ON e.id_degree = d.id_degree "+
		           " WHERE id_jsk = ? AND id_resume=? AND delete_status='FALSE')";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idJsk);
			db.setInt(4, idResume);
			db.executeQuery();
			if(db.next())
			{	
				idDeg=db.getString("id_degree")!=null?db.getString("id_degree"):"";
				idMaj =db.getString("id_major")!=null?db.getString("id_major"):"";
				idFac = db.getString("id_fac_major")!=null?db.getString("id_fac_major"):"";
				finishYear = db.getString("finishYear")!=null?db.getString("finishYear"):"";
				result = idDeg+","+idMaj+","+idFac+","+finishYear;
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
	
	public static int getTotalNewExp(int totalExp, String finishDate, String finishWork )
	{
		int expTotal=totalExp;
		int thisYear= Integer.parseInt(nowDateFormat("yyyy"));
		int finishEduYear=finishDate!=null &&finishDate!=""?Integer.parseInt(finishDate):0;//end date of education
		int finishWorkYear=finishWork!=null && finishWork!=""?Integer.parseInt(finishWork):0;//end date of exp
		if(expTotal==0)
		{
			//ถ้าเกิน 10 ปี จะปรับเป็น 10 ปี
			if(finishEduYear<=thisYear)
			{
				if(finishEduYear<=thisYear-10)
				{	expTotal=10;}
				else
				{	expTotal=thisYear-finishEduYear;}
			}
		}else 
		{
			if(finishWorkYear<=thisYear)
			{
				if(finishWorkYear<=thisYear-10)
				{	expTotal=10;}
				else
				{	expTotal=expTotal+(thisYear-finishWorkYear);}
			}
		}
		return expTotal;
	}
	
	 public static String nowDateFormat (String strFormat) 
	 {
		 String sDate = "";
		try 
		{
			Date today = new Date();
			SimpleDateFormat dataformat = new SimpleDateFormat(strFormat,Locale.US); //Create format Date
			sDate = dataformat.format( today ); //Convert Date to String (format(Date))
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	    return sDate;
	}
	 
	public static String getLastWorkFieldYear(int idJsk,int idResume)
	{
		String result="";
		String SQL=	"SELECT id,workjob_field,worksub_field,salary_last,salary_per,id_currency, TO_CHAR(work_end,'YYYY') AS workEnd FROM INTER_WORK_EXPERIENCE "+
					"WHERE ID_JSK=? and ID_RESUME=? AND DELETE_STATUS = 'FALSE' AND "+
					"WORK_END = (SELECT MAX(WORK_END) FROM INTER_WORK_EXPERIENCE WHERE ID_JSK=? AND ID_RESUME=? AND DELETE_STATUS = 'FALSE' ) ORDER BY ID DESC";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idJsk);
			db.setInt(4, idResume);
			db.executeQuery();
			if(db.next())
			{
				result=db.getString("workEnd")!=null?db.getString("workEnd"):"";
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
