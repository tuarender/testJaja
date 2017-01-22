package com.topgun.util;

import javax.naming.Context;
import javax.naming.InitialContext;

public class DBContext 
{
	private static Context ctx=null;
	
	public static Context getContext()
	{
		try
		{
			if(ctx==null)
			{
				ctx = new InitialContext();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ctx;
	}
}
