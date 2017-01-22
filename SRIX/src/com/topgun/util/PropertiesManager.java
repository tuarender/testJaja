package com.topgun.util;

import java.util.Properties;

public class PropertiesManager 
{
	public  String getMessage(String locale, String key)
	{
		String result="";

		String propFile=this.getClass().getClassLoader().getResource("com/topgun/properties/language_"+locale+".properties").toString();
		propFile=propFile.substring(5);
		Properties prop=PropertiesContext.getContext(propFile, locale);
		if(prop!=null)
		{
			result=Util.getStr(prop.getProperty(key));
		}
		return !result.equals("")?result:key;
	}
}
