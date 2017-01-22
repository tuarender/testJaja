package com.topgun.resume;

import com.topgun.util.DBManager;

public class PositionManager
{	
	public Position getPosition(int idCompany, int idPosition)
	{
		Position result=null;
		int found =0;
		String SQL="SELECT POSITION_NAME FROM POSITION WHERE (ID_EMP=?) AND (ID_POSITION = ?) ";
		DBManager db=null;
		DBManager db1=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idCompany);
			db.setInt(2, idPosition);
			db.executeQuery();
			if(db.next())
			{
				result=new Position();
				result.setPositionName(db.getString("POSITION_NAME"));
				SQL="SELECT EMAIL FROM EMPLOYER_EMAIL WHERE (ID_EMP=?) AND (ID_P=?)";
				DBManager dbx=null;
				try
				{
					dbx=new DBManager();
					dbx.createPreparedStatement(SQL);
					dbx.setInt(1,idCompany);
					dbx.setInt(2,idPosition);
					dbx.executeQuery();
					if(dbx.next())
					{
						result.setHrEmail(dbx.getString("EMAIL"));
					}
					else
					{
						DBManager dx=null;
						SQL="SELECT EMAIL FROM EMPLOYER WHERE (ID_EMP=?)";
						try
						{
							dx=new DBManager();
							dx.createPreparedStatement(SQL);
							dx.setInt(1, idCompany);
							dx.executeQuery();
							if(dx.next())
							{
								result.setHrEmail(dx.getString("EMAIL"));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							dx.close();
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					dbx.close();
				}
				found = 1;
			}
			if(found == 0)
			{
				try
				{
					SQL="SELECT POSITION_NAME FROM POSITION_SUP WHERE (ID_EMP=?) AND (ID_POSITION = ?) ";
					db1=new DBManager();
					db1.createPreparedStatement(SQL);
					db1.setInt(1,idCompany);
					db1.setInt(2,idPosition);
					db1.executeQuery();
					if(db1.next())
					{
						result=new Position();
						result.setPositionName(db1.getString("POSITION_NAME"));
						DBManager dx=null;
						SQL="SELECT EMAIL FROM CALL WHERE (ID=?)";
						try
						{
							dx=new DBManager();
							dx.createPreparedStatement(SQL);
							dx.setInt(1, idCompany);
							dx.executeQuery();
							if(dx.next())
							{
								result.setHrEmail(dx.getString("EMAIL"));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							dx.close();
						}
							
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						db1.close();
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
	
	public boolean isBachelorUp(int idCompany, int idPosition)
	{
		boolean result=false;
		DBManager db=null;
		DBManager db1=null;
		String SQL="";
		int degree=0;
		int[] degreeList = new int[7];
		degreeList[0]=2;
		degreeList[1]=3;
		degreeList[2]=6;
		degreeList[3]=7;
		degreeList[4]=8;
		degreeList[5]=11;
		degreeList[6]=13;
		int found =0 ;
		try
		{
			db=new DBManager();
			SQL="select degree from position where id_emp = ? and id_position = ? ";
			db.createPreparedStatement(SQL);
			db.setInt(1, idCompany);
			db.setInt(2, idPosition);
			db.executeQuery();
			if(db.next())
			{
				degree=db.getInt("degree");
				found=1;
			}
			if(found == 0)
			{
				db1=new DBManager();
				SQL="select degree from position where id_emp = ? and id_position "+
				"=(select id_position_ref  from position_sup where id_emp = ? and id_position = ? )";
				db1.createPreparedStatement(SQL);
				db1.setInt(1, idCompany);
				db1.setInt(2, idCompany);
				db1.setInt(3, idPosition);
				db1.executeQuery();
				if(db1.next())
				{
					degree=db1.getInt("degree");
				}
			}
			
			if(degree!=0)
			{
				for(int c=0;c<degreeList.length;c++)
				{
					if(degree==degreeList[c])
					{
						result=true;
						break;
					}
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
	
	public boolean isOnline(int idEmp,int idPosition)
	{
		boolean result=false;
		String sql=	"SELECT " +
					"	POSITION.ID_POSITION,EMPLOYER.ID_EMP " +
					"FROM " +
					"	POSITION, EMPLOYER " +
					"WHERE " +
					"	(POST_DATE <= TO_CHAR(SYSDATE,'YYYYMMDD')) AND " +
					"	(E_D2 >= TO_CHAR(SYSDATE,'YYYYMMDD')) AND " +
					"	(EMPLOYER.FLAG='1') AND (POSITION.FLAG='1') AND " +
					"	(POSITION.ID_EMP = EMPLOYER.ID_EMP) AND " +
					"	(POSITION.ID_EMP=?) AND (POSITION.ID_POSITION=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idEmp);
			db.setInt(2, idPosition);
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
	
	public boolean isNationWideJob(int idEmp,int idPosition)
	{
		boolean result=false;
		String sql=	"SELECT * FROM INTER_NATIONWIDE_JOBS WHERE ID_EMP=? AND ID_POSITION=? ";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idEmp);
			db.setInt(2, idPosition);
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
	
	public  boolean isOnlinePosition(int idEmp,int idPosition)
	{
		boolean result=false;
		String sql=	"SELECT " +
					"	POSITION.ID_POSITION,EMPLOYER.ID_EMP " +
					"FROM " +
					"	POSITION, EMPLOYER " +
					"WHERE " +
					"	(POST_DATE <= TO_CHAR(SYSDATE,'YYYYMMDD')) AND " +
					"	(E_D2 >= TO_CHAR(SYSDATE,'YYYYMMDD')) AND " +
					"	(EMPLOYER.FLAG='1') AND (POSITION.FLAG='1') AND " +
					"	(POSITION.ID_EMP = EMPLOYER.ID_EMP) AND " +
					"	(POSITION.ID_EMP=?) AND (POSITION.ID_POSITION=?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idEmp);
			db.setInt(2, idPosition);
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
	
	public boolean chkPositionJobField(int idEmp,int idPosition,int idJobField)
	{
		DBManager db = null ;
		String sql = "";
		boolean result = false ;
		try
		{
			sql = "SELECT * FROM POSITION_WORKEQUI WHERE ID_EMP = ? AND ID_POSITION = ? AND WORKJOB_FIELD = ?";
			db = new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idJobField);
			db.executeQuery();
			if(db.next())
			{
				result = true ;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			db.close();
		}
		return result ;
	}
}
