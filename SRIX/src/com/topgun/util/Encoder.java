package com.topgun.util;
import java.security.MessageDigest;

public class Encoder
{
	//convert the byte to hex format method 1
	public static String getHexString(byte [] bytes)
	{
		String result="";
		if(bytes.length>0 && bytes!=null)
		{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i<bytes.length; i++) 
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			result=sb.toString();
		} 
        return result;
	}

	//convert the byte to hex format method 2
	/*
	public static String getHexString2(byte [] bytes)
	{
		String result="";
		if(bytes.length>0 && bytes!=null)
		{
	        StringBuffer sb = new StringBuffer();
	    	for (int i=0;i<bytes.length;i++) 
	    	{
	    		String hex=Integer.toHexString(0xff & bytes[i]);
	   	     	if(hex.length()==1) sb.append('0');
	   	     	sb.append(hex);
	    	}
	    	result=sb.toString();
		} 
        return result;
	}
	*/

	
	//get SHA-1 Encryption
	public static String getSHA1Encode(String password)
	{
		String result="";
		if((password!=null) && (!password.trim().equals("")))
		{
			try
			{
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(password.getBytes());
				byte bytes[] = md.digest();
				result=getHexString(bytes);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	//get SHA-256 Encryption
	public static String getSHA256Encode(String password)
	{
		String result="";
		if((password!=null) && (!password.trim().equals("")))
		{
			try
			{
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(password.getBytes());
				byte bytes[] = md.digest();
				result=getHexString(bytes);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//get MD5 Encryption
	public static String getMD5Encode(String password)
	{
		String result="";
		if((password!=null) && (!password.trim().equals("")))
		{
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				byte bytes[] = md.digest();
				result=getHexString(bytes);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String getEncode(String password)
	{
		return getSHA256Encode(getSHA1Encode(getMD5Encode(password)));
	}
}