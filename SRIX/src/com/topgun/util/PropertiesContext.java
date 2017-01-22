package com.topgun.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesContext 
{
	private static List<String> locales = new ArrayList<String>();
	private static List<Properties> properties = new ArrayList<Properties>();
	
	public static Properties getContext(String propertiesFile,String locale)
	{
		int idx=getIndex(locale);
		if(idx>=0) 
		{
			return properties.get(idx);
		}
		else
		{
			Properties prop=null;
			FileInputStream fin=null;
			try
			{
				fin=new FileInputStream(propertiesFile);
				prop=new Properties();
				prop.load(fin);
				properties.add(prop);
				locales.add(locale);
				fin.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(fin!=null)
				{
					try
					{
						fin.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				fin=null;
			}
			return prop;
		}
	}
	
	private static int getIndex(String locale)
	{
		for(int i=0; i<locales.size(); i++)
		{
			if(locales.get(i).equals(locale))
			{
				return i;
			}
		}
		return -1;
	}
}
