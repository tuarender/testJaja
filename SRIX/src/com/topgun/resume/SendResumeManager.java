package com.topgun.resume;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.topgun.util.DBManager;
import com.topgun.util.PropertiesManager;

public class SendResumeManager 
{
	private PropertiesManager propMgr=new PropertiesManager();
	public String getRequiredFieldforEachEmp(int idJsk,int idResume, int idEmp) // only thailand
	{
		String incompleteResumeStr="";
		String incInner="";
		String sql="";
		DBManager db=null;
		String[] tableName = new String[4];
		String[] fieldName = new String[2];
		Resume resume=new ResumeManager().get(idJsk, idResume);
		int found=0;
		int foundMarried=0;
		try
		{
		  if(resume.getTemplateIdCountry()==216 && idResume > 0) // thailand and is super resume
		  {
			List<String[]> listTable=getRequiredTable(idEmp);
			if(listTable!=null && listTable.size()>0)
			{
				for(int countT=0;countT<listTable.size();countT++)
				{
					incInner="";
					tableName= (String[])listTable.get(countT);  // tableName [table name, where, desc]
					List<String[]> listField=getRequiredFieldForEachTable(idEmp,tableName[0]);
					sql="";
					if(listField!=null && listField.size() > 0)
					{
						sql="select ";
						for(int countF=0;countF<listField.size();countF++)
						{
							if(countF > 0 )
							{
								sql+=", ";
							}
							fieldName=(String[])listField.get(countF); //fieldName [field name, desc]
							sql+=fieldName[0];
						}
						sql+=" from "+tableName[0]+" where id_jsk="+idJsk+" and id_resume="+idResume;
						if(tableName[1]!=null)
						{
							sql+=" and "+tableName[1];
						}
					}
					found=0;
					db=new DBManager();
					db.createPreparedStatement(sql);
					db.executeQuery();
					while(db.next())  // have record and check each field
					{
						for(int countF=0;countF<listField.size();countF++)
						{
							fieldName=(String[])listField.get(countF);
							if(tableName[0].equals("inter_additional_personal"))
							{
								if(fieldName[0].equals("military_status"))
								{
									if(db.getString(fieldName[0])==null && resume.getSalutation().equals("MR"))
									{
										incInner+="<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- "+propMgr.getMessage(resume.getLocale(),fieldName[1]);
									}
							    }
								else //other field
								{
									if(db.getString(fieldName[0])==null)
									{
										incInner+="<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- "+propMgr.getMessage(resume.getLocale(),fieldName[1]);
									}
								}
							}
							else if(tableName[0].equals("inter_family"))
							{
								if(fieldName[0].equals("married_status"))
								{
									if(db.getString(fieldName[0])!=null)
									{
										foundMarried++;
									}
								}
							}
							else // other table
							{
								if(db.getString(fieldName[0])==null)
								{
									incInner+="<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- "+propMgr.getMessage(resume.getLocale(),fieldName[1]);
								}
							}
						}
						if(!incInner.equals(""))
						{
							incInner="<br><a href=\"/Resume/ResumePortal?Service="+tableName[3]+"&ResumeId="+resume.getIdResume()+"&Sequence=0\"><b><u>"+propMgr.getMessage(resume.getLocale(),tableName[2])+"</u></b></a>"+incInner;
						}

						found=1;
					}
					if(found ==0) // no record
					{
						incInner+="<br><a href=\"/Resume/ResumePortal?Service="+tableName[3]+"&ResumeId="+resume.getIdResume()+"&Sequence=0\"><b><u>"+propMgr.getMessage(resume.getLocale(),tableName[2])+"</u></b></a>";
					}
					else
					{
						if(foundMarried==0 &&  tableName[0].equals("inter_family"))
						{
							incInner="<br><a href=\"/Resume/ResumePortal?Service="+tableName[3]+"&ResumeId="+resume.getIdResume()+"&Sequence=0\"><b><u>"+propMgr.getMessage(resume.getLocale(),tableName[2])+"</u></b></a><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- "+propMgr.getMessage(resume.getLocale(),fieldName[1]);
						}
					}
					if(db!=null)
					{
						db.close();
					}

					incompleteResumeStr+=incInner;
				} // loop table
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
		return incompleteResumeStr;
	}
	
	public List<String[]> getRequiredTable(int idEmp)
	{
		List<String[]> list = new ArrayList<String[]>();
		String sql="";
		DBManager db=null;
		try
		{
			sql="select table_name,condition_where,part_name,service   "+
			"from  shris_required_resume, shris_resume_part, shris_resume_topic "+
			"where shris_required_resume.id_emp= ? and shris_required_resume.id_part = shris_resume_topic.id_part and shris_resume_topic.id_part = shris_resume_part.id_part "+
			"and shris_required_resume.id_topic=shris_resume_topic.id_topic "+
			"group by table_name, condition_where,part_name,service  ";

			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.executeQuery();
			while(db.next())
			{
				String[] tableName=new String[4];
				tableName[0]=db.getString("table_name");
				tableName[1]=db.getString("condition_where");
				tableName[2]=db.getString("part_name");
				tableName[3]=db.getString("service");
				list.add(tableName);
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
		return list;
	}
	
	public List<String[]> getRequiredFieldForEachTable(int idEmp,String tableName)
	{
		List<String[]> list = new ArrayList<String[]>();
		String sql="";
		DBManager db=null;
		try
		{
			sql="select  field_name,topic_name "+
			"from  shris_required_resume, shris_resume_part, shris_resume_topic "+
			"where shris_required_resume.id_emp= ?  and shris_required_resume.id_part = shris_resume_topic.id_part and shris_resume_topic.id_part = shris_resume_part.id_part "+
			"and shris_required_resume.id_topic=shris_resume_topic.id_topic "+
			"and table_name=? "+
			"group by  field_name,topic_name  ";

			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setString(2, tableName);
			db.executeQuery();
			while(db.next())
			{
				String[] field_name=new String[2];
				field_name[0]=db.getString("field_name");
				field_name[1]=db.getString("topic_name");
				list.add(field_name);
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
		return list;
	}
	
	public Timestamp getLastTimeApply(int idJsk,int idResume,int idEmp, int idPosition, String email,String position)
	{
		Timestamp result=new Timestamp(0);
		position=position!=null?position.trim().toLowerCase():"";
		String SQL="SELECT SENTDATE FROM INTER_TRACK WHERE (ID_JSK=?) AND (ID_RESUME=?) ";
		if((idEmp!=-1)&&(idPosition!=-1))
		{
			SQL+="AND (ID_EMP=?) ";


			 List<Integer> posList = findPositionfromPosRef(idEmp,idPosition); // id_position_ref
				if(posList.size()>0)
				{
					SQL+=" and ( ";
					for(int cPos=0;cPos<posList.size();cPos++)
					{
						if(cPos > 0)
						{
							SQL+=" or ";
						}
						SQL+=" ID_POSITION = "+posList.get(cPos);
					}
					SQL+=" ) ";
				}
		}
		else
		{
			SQL+="AND (RECIPIENT=?) AND (LOWER(POSITION_OTHER)=?)";
		}
		SQL+=" ORDER BY SENTDATE DESC";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			if((idEmp!=-1)&&(idPosition!=-1))
			{
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.setInt(3, idEmp);
			}
			else
			{
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.setString(3, email);
				db.setString(4, position);
			}
			db.executeQuery();
			if(db.next())
			{
				result=db.getTimestamp("SENTDATE");
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
	
	public List<Integer> findPositionfromPosRef(int idEmp,int idPosition)
	{
	    SortedSet set1 = new TreeSet();
		List<Integer> list = new ArrayList<Integer>();
		DBManager db=null;
		DBManager db2=null;
	    String sql="";
	    String sql2="";
		try
		{
			db=new DBManager();
			sql="select id_position,id_position_ref from position_sup where id_emp=? and (id_position_ref=? or id_position = ?)";
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idPosition);
			db.executeQuery();
			while(db.next())
			{
				set1.add(db.getInt("id_position"));
				set1.add(db.getInt("id_position_ref"));
			}

			db2=new DBManager();
			sql2="select id_position from position where id_emp=? and id_position = ?";
			db2.createPreparedStatement(sql2);
			db2.setInt(1, idEmp);
			db2.setInt(2, idPosition);
			db2.executeQuery();
			if(db2.next())
			{
				set1.add(db2.getInt("id_position"));
			}

			list.addAll(set1);

		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{

		   if(db !=null)
		   {
			   try 
			   {
				   db.close();
			   } 
			   catch (Exception e) 
			   {
				   e.printStackTrace();
			   }
		   }

		   if(db2 !=null)
		   {
			   try 
			   {
				   db2.close();
			   } 
			   catch (Exception e) 
			   {
				   e.printStackTrace();
			   }
		   }
		}
		return list;
	}
	public int saveInquiry(int idQuestion, int idAnswer,int idEmp)
	{
		int result=0;
		DBManager db=null;
		int score=0;
		String SQL=	"SELECT SCORE FROM POLL_COMPANY WHERE ID_EMP=? AND ID_QUESTION =? AND ID_ANSWER=? ";

		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idQuestion);
			db.setInt(3, idAnswer);
			db.executeQuery();
			if(db.next())
			{
				score=db.getInt("SCORE");
			}
			score++;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		try
		{
			SQL="UPDATE POLL_COMPANY SET SCORE=? WHERE ID_EMP=? AND ID_QUESTION =? AND ID_ANSWER=? ";
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, score);
			db.setInt(2, idEmp);
			db.setInt(3, idQuestion);
			db.setInt(4, idAnswer);
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

		return result;
	}
	
	public int saveInquiryEmp2775AndPos37(String ans1,String ans2,String ans3,String ans4,int idEmp,int idJsk,int idResume,int idPosition,String cert)
	{
		int result=0;
		DBManager db=null;
		DBManager dby=null;
		String SQL="DELETE FROM ANSWER_COMPANY  WHERE ID_EMP=? AND ID_JSK=? AND ID_POSITION=? ";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idJsk);
			db.setInt(3, idPosition);
			db.executeUpdate();

				try
				{
					SQL="INSERT INTO ANSWER_COMPANY (ANSWER1,ANSWER2,LOCATION,STATE,ID_EMP,ID_JSK,ID_RESUME,ID_POSITION,CERTIFICATE) VALUES(?,?,?,?,?,?,?,?,?) ";
					dby=new DBManager();
					dby.createPreparedStatement(SQL);
					dby.setString(1, ans1);
					dby.setString(2, ans2);
					dby.setString(3, ans3);
					dby.setString(4, ans4);
					dby.setInt(5, idEmp);
					dby.setInt(6, idJsk);
					dby.setInt(7, idResume);
					dby.setInt(8, idPosition);
					dby.setString(9, cert);
					result = dby.executeUpdate();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					dby.close();
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
	
	public int saveInquiryEmp2775Cert(int idEmp,int idJsk,int idResume,int idPosition,String cert)
	{
		int result=0;
		DBManager dby=null;
		String SQL="";
		try
		{
			SQL="INSERT INTO ANSWER_COMPANY (ID_EMP,ID_JSK,ID_RESUME,ID_POSITION,CERTIFICATE) VALUES(?,?,?,?,?) ";
			dby=new DBManager();
			dby.createPreparedStatement(SQL);

			dby.setInt(1, idEmp);
			dby.setInt(2, idJsk);
			dby.setInt(3, idResume);
			dby.setInt(4, idPosition);
			dby.setString(5, cert);
			result = dby.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dby.close();
		}
		return result;
	}
	
	public boolean chkExistsIDCARD(String idCard)
	{
		boolean result=false;
		DBManager db=null;

		String SQL="select id_card from answer_baac where  id_card = ? ";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setString(1, idCard);
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
	public int saveInquiryEmp17113AndPos14(int idEmp,int idJsk,int idResume,int idPosition,String idCard,String phoneNo,int location,int idProvince1,int idProvince2,int idFaculty,int idMajor,String otherMajor)
	{
		int result=0;
		DBManager db=null;
		String SQL="";
		try
		{	SQL="INSERT INTO ANSWER_BAAC (ID_EMP,ID_JSK,ID_RESUME,ID_POSITION,ID_CARD,PHONE_NO,LOCATION,ID_PROVINCE1,ID_PROVINCE2,ID_FACULTY,ID_MAJOR,OTHER_MAJOR) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idEmp);
			db.setInt(2, idJsk);
			db.setInt(3, idResume);
			db.setInt(4, idPosition);
			db.setString(5, idCard);
			db.setString(6, phoneNo);
			db.setInt(7, location);
			db.setInt(8, idProvince1);
			db.setInt(9, idProvince2);
			db.setInt(10, idFaculty);
			db.setInt(11, idMajor);
			db.setString(12, otherMajor);
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
		return result;
	}
	public Boolean canApplyJob(int idJsk,int idResume,int idEmp, int idPosition, String email,String position)
	{
		Boolean result=true;//can apply
		position=position!=null?position.trim().toLowerCase():"";
		String SQL="SELECT  FLOOR(SYSDATE-SENTDATE) AS TOTAL_DAY FROM INTER_TRACK WHERE (ID_JSK=?) AND (ID_RESUME=?) ";
		if((idEmp!=-1)&&(idPosition!=-1))
		{
			SQL+=" AND (ID_EMP=?) ";
			 List<Integer> posList = findPositionfromPosRef(idEmp,idPosition); // id_position_ref
				if(posList.size()>0)
				{
					SQL+=" and ( ";
					for(int cPos=0;cPos<posList.size();cPos++)
					{
						if(cPos > 0)
						{
							SQL+=" or ";
						}
						SQL+=" ID_POSITION = "+posList.get(cPos);
					}
					SQL+=" ) ";
				}
		}
		else
		{
			SQL+="AND (RECIPIENT=?) AND (LOWER(POSITION_OTHER)=?)";
		}
		SQL+=" ORDER BY TOTAL_DAY DESC";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			if((idEmp!=-1)&&(idPosition!=-1))
			{
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.setInt(3, idEmp);
			}
			else
			{
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.setString(3, email);
				db.setString(4, position);
			}
			db.executeQuery();
			if(db.next())
			{
				if(db.getInt("TOTAL_DAY")>30)
				{
					result=true;
				}
				else
				{
					result=false;
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
}
