package com.topgun.resume;

import com.topgun.util.DBManager;

public class LoginManager 
{
	public Login getLogin(int idJsk, String session)
	{
		Login result=null;
		String sql=	" SELECT * FROM INTER_LOGIN WHERE ID_JSK=? AND ID_SESSION=? AND TO_CHAR(TIMESTAME,'YYYYMMDD')=TO_CHAR(SYSDATE,'YYYYMMDD')";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.setString(2, session);
			db.executeQuery();
			if(db.next())
			{
				result=new Login();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setIdSession(db.getString("ID_SESSION"));
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
