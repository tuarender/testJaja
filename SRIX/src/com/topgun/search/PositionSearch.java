package com.topgun.search;

import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class PositionSearch {
	public static String getJobFieldJson(int idEmp, int idPosition)
	{
		String result="{}";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement("SELECT DISTINCT(WORKJOB_FIELD) AS JOB_FIELD FROM POSITION_WORKEQUI WHERE ID_EMP=? AND ID_POSITION=?");
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			while(db.next())
			{
				if(result.equals("{}"))
				{
					result=Util.getStr(db.getInt("JOB_FIELD"))+":{"+getSubGroupJson(idEmp,idPosition,db.getInt("JOB_FIELD"))+"}";
				}
				else
				{
					result+=","+Util.getStr(db.getInt("JOB_FIELD"))+":{"+getSubGroupJson(idEmp,idPosition,db.getInt("JOB_FIELD"))+"}";
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
		if(!result.equals("{}"))
		{
			result="{"+result+"}";
		}
		return result;
	}
	
	public static String getSubGroupJson(int idEmp, int idPosition, int idJobField)
	{
		String result="0:{}";
		DBManager db=null;
		try
		{
			String sql="SELECT "
					+ "	DISTINCT(ID_GROUP_SUBFIELD) AS GROUP_FIELD "
					+ "FROM "
					+ "	POSITION_WORKEQUI A, INTER_GROUP_SUBFIELD B "
					+ "WHERE "
					+ "	A.ID_EMP=? AND A.ID_POSITION=? AND "
					+ "	A.WORKJOB_FIELD=B.ID_FIELD AND B.ID_FIELD=?";
			
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idJobField);
			db.executeQuery();
			while(db.next())
			{
				if(result.equals("0:{}"))
				{
					result=Util.getStr(db.getInt("GROUP_FIELD"))+":{}";
				}
				else
				{
					result+=","+Util.getStr(db.getInt("GROUP_FIELD"))+":{}";;
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
	
	public static int[] getSalary(int idEmp, int idPosition)
	{
		int [] result=new int[2];
		result[0]=0;
		result[1]=0;
		DBManager db=null;
		try
		{
			String sql="SELECT SALARY_LESS, SALARY_MOST FROM POSITION WHERE ID_EMP=? AND ID_POSITION=?";
			
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.executeQuery();
			if(db.next())
			{
				result[0]=db.getInt("SALARY_LESS");
				result[1]=db.getInt("SALARY_MOST");
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
