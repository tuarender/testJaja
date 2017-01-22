package com.topgun.resume;

import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class ForwardEmailManager {
	public FwdBean get(int jtgFwdId)
	{
		FwdBean result=null;
		String sql="";
		DBManager dr=null;;
		try
		{
			sql="SELECT * FROM JTG_FWD WHERE JTG_FWD_ID=?";
			dr=new DBManager();
			dr.createPreparedStatement(sql);
			dr.setInt(1,jtgFwdId);
			dr.executeQuery();
			if(dr.next())
			{
				result=new FwdBean();
				result.setJtg_fwd_id(dr.getInt("JTG_FWD_ID"));
				result.setSender(dr.getString("SENDER"));
				result.setRecipient(dr.getString("RECIPIENT"));
				result.setCc(dr.getString("CC"));
				result.setSubject(dr.getString("SUBJECT"));
				result.setMsg(dr.getString("MSG"));
				result.setSend_name(dr.getString("SENDER_NAME"));
				result.setTopage(dr.getString("PAGES"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dr.close();
		}
		return result;
	}
	public String[] getEncode(int idEmp)
	{
		String[] result=null;
		String sql="";
		DBManager dr=null;;
		try
		{
			sql="SELECT ENCODE,ATTACHMENT FROM INTER_EMPLOYER_MAIL WHERE ID_EMP=?";
			dr=new DBManager();
			dr.createPreparedStatement(sql);
			dr.setInt(1,idEmp);
			dr.executeQuery();
			if(dr.next())
			{
				result = new String[2];
				result[0]=Util.getStr(dr.getString("ENCODE"));
				result[1]=Util.getStr(dr.getString("ATTACHMENT"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dr.close();
		}
		return result;
	}
	public static int insFwdRec(int fwdId, int idJsk, int idResume, int idEmp, int idPos)
	{
		int result=0;
		String sql="INSERT INTO JTG_FWD_RECORD(JTG_FWD_ID, ID_JSK, ID_RESUME, ID_EMP, ID_POSITION) VALUES(?,?,?,?,?)";
		DBManager db=null;
		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,fwdId);
			db.setInt(2,idJsk);
			db.setInt(3,idResume);
			db.setInt(4,idEmp);
			db.setInt(5,idPos);
			result=db.executeUpdate();
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
}
