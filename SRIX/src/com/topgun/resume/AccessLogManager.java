package com.topgun.resume;

import javax.servlet.http.HttpServletRequest;

import com.topgun.util.DBManager;
import com.topgun.util.Util;

public class AccessLogManager {
	
	public static void addLog(HttpServletRequest request)
	{
		//P'Ton comment 28/11/2016
		/*String refer= Util.getStr(request.getSession().getAttribute("SESSION_REFER"));
		String referURL= Util.getStr(request.getHeader("Referer"));
		if(!refer.equals(""))
		{
			refer=Util.getStr(request.getParameter("refer"));
		}
		else
		{
			if(referURL.toLowerCase().indexOf("adword") != -1 && referURL.toLowerCase().indexOf("facebook") == -1)
			{
				refer= "adword";
			}
		}
		String urlReq= Util.getStr(request.getRequestURI())+"?"+Util.getStr(request.getQueryString());
		String agent=Util.getStr(request.getHeader("User-Agent"));
		String sql="INSERT INTO INTER_ACCESS_LOGS (SESSION_ID, HOST, REFER, REFERAL_URL, REQUEST_URL, AGENT, DOMAIN) "
				 + "VALUES(?, ?, ?, ?, ?, ?, ?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, Util.getStr(request.getSession().getId()));
			db.setString(2, Util.getStr(request.getServerName()));
			db.setString(3, refer);
			db.setString(4, Util.getStr(request.getHeader("Referer")));
			db.setString(5,urlReq.trim());
			db.setString(6, agent);
			db.setString(7, "www.superresume.com");
			db.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}*/
	}
}
