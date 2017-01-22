package com.topgun.resume;

import com.topgun.util.DBManager;


public class CurrencyManager 
{
	public int getIdCurrencyByIdCountry(int idCountry)
	{
		int result=0;
		String SQL="SELECT ID_CURRENCY FROM INTER_COUNTRY_CURRENCY WHERE (ID_COUNTRY = ?)";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idCountry);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("ID_CURRENCY");
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
