package com.topgun.util;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.topgun.resume.Attachment;
import com.topgun.resume.AttachmentManager;

public class Util
{
    public static boolean isAvailableCountry(int idCountry)
	{
		if(idCountry==216) return true;
		if(idCountry==174) return true;
		if(idCountry==197) return true;
		if(idCountry==231) return true;
		if(idCountry==110) return true;
		if(idCountry==81) return true;
		if(idCountry==45) return true;
		if(idCountry==213) return true;
		if(idCountry==151) return true;
		return false;	
	}	
	
	public static boolean isAvailableCountry(String country)
	{
		country=getStr(country);
		if(country.equals(""))	return false;
		if(country.equals("US")) return true;
		if(country.equals("SG")) return true;
		if(country.equals("PH")) return true;
		if(country.equals("DE")) return true;
		if(country.equals("JP")) return true;
		if(country.equals("TH")) return true;
		if(country.equals("CN")) return true;
		if(country.equals("TW")) return true;
		if(country.equals("MM")) return true;
		return false;	
	}		
	
	public static boolean isAvailableLanguage(String language)
	{
		language=getStr(language);
		if(language.equals(""))	return false;
		if(language.equals("en")) return true;
		if(language.equals("th")) return true;
		if(language.equals("de")) return true;
		if(language.equals("jp")) return true;
		if(language.equals("zh")) return true;
		if(language.equals("zt")) return true;
		return false;	
	}	
	
	public static boolean isAvailableLanguage(int idLanguage)
	{
		if(idLanguage==11) return true;
		if(idLanguage==23) return true;
		if(idLanguage==38) return true;
		if(idLanguage==16) return true;
		if(idLanguage==5) return true;
		if(idLanguage==6) return true;
		return false;	
	}	
	
	public static String getAvailableLanguage(int idLanguage)
	{
		if(idLanguage==11) return "en";
		if(idLanguage==23) return "ja";
		if(idLanguage==38) return "th";
		if(idLanguage==16) return "de";
		if(idLanguage==5) return "zh";
		if(idLanguage==6) return "zt";
		return "";
	}
	
	public static int getAvailableIdLanguage(String language)
	{
		int result=11;
		language=getStr(language);
		if(language.equals(""))
		{
			result = 11;
		}
		else if(language.equals("en"))
		{
			result = 11;
		}
		else if(language.equals("ja"))
		{
			result = 23;
		}
		else if(language.equals("th"))
		{
			result = 38;
		}
		else if(language.equals("de"))
		{
			result = 16;
		}
		else if(language.equals("zh"))
		{
			result = 5;
		}
		else if(language.equals("zt"))
		{
			result = 6;
		}
		return result;
	}
	
	public static String getAvailableCountry(int idCountry)
	{
		if(idCountry==231) return "US";
		if(idCountry==216) return "TH";
		if(idCountry==110) return "JP";
		if(idCountry==197) return "SG";
		if(idCountry==81) return "DE";
		if(idCountry==174) return "PH";
		if(idCountry==45) return "CN";
		if(idCountry==213) return "TW";
		if(idCountry==151) return "MM";
		return "";
	}
	
	public static int getAvailableIdCountry(String country)
	{
		int result=216;
		country=getStr(country);
		if(country.equals(""))
		{
			result = 216;
		}
		else if(country.equals("US"))
		{
			result = 231;
		}
		else if(country.equals("JP"))
		{
			result = 110;
		}
		else if(country.equals("SG"))
		{
			result = 197;
		}
		else if(country.equals("TH"))
		{
			result = 216;
		}
		else if(country.equals("DE"))
		{
			result = 81;
		}	
		else if(country.equals("PH"))
		{
			result = 174;
		}	
		else if(country.equals("CN"))
		{
			result = 45;
		}				
		else if(country.equals("TW"))
		{
			result = 213;
		}				
		else if(country.equals("MM"))
		{
			result = 151;
		}
		return result;
	}
	
	public static int getInteger(Object obj)
	{
		int result=-1;
		if(obj!=null)
		{
			try
			{
				String str=obj.toString();
				if(str!=null)
				{
					str=str.replace(",", "");
					result=Integer.parseInt(str);
				}
				
			}
			catch(Exception e)
			{
				;
			}
		}
		return result;
	}
	
	public static int getInteger(Object obj, int defaultValue)
	{
		int result=defaultValue;
		if(obj!=null)
		{
			try
			{
				String str=obj.toString();
				if(str!=null)
				{
					str=str.replace(",", "");
					result=Integer.parseInt(str);
				}
				
			}
			catch(Exception e)
			{
				;
			}
		}
		return result;
	}	

	public static int getInt(Object obj)
	{
		return getInteger(obj);
	}
	
	public static int getInt(Object obj, int defaultValue)
	{
		return getInteger(obj,defaultValue);
	}
	
	public static String getStr(Object obj)
	{
		return obj!=null?obj.toString():"";
	}
	
	
	public static String getStr(Object obj,String defaultValue)
	{
		return obj!=null?obj.toString():defaultValue;
	}	
	
	public static boolean getBoolean(int value)
	{
		return (value>0)?true:false;    	
	}
	
	public static int getInt(String value)
	{
		return getInteger(value);    	
	}
	public static Long getLong(Object obj)
	{
		Long result=-1l;
		if(obj!=null)
		{
			try
			{
				String str=obj.toString();
				if(str!=null)
				{
					str=str.replace(",", "");
					result=Long.parseLong(str);
				}
				
			}
			catch(Exception e)
			{
				;
			}
		}
		return result;
	}
	public static float getFloat(String value)
	{
		float result=0;
		try
		{      		
			result = Float.parseFloat(value);
		}
		catch(Exception e)
		{
			;
		}
		return result;    	
	}
	
	public static Date getDate(String dateStr, String format)
	{
		Date result=null;
		try
		{
			DateFormat formatter = new SimpleDateFormat(format);
			result=formatter.parse(dateStr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static java.sql.Date getDate(int day, int month, int year)
	{
		java.sql.Date result=null;
		if(day>0 && month>0 && year>0)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month-1);
			cal.set(Calendar.DAY_OF_MONTH, day);
			result=new java.sql.Date(cal.getTimeInMillis());
			//System.out.println("result="+result.toString());
		}
		return result;				
	}

	public static java.sql.Date getSQLDate(String dateStr, String format)
	{
		java.sql.Date result=null;
		dateStr=getStr(dateStr);
		format=getStr(format);
		if(dateStr.equals("")) return null;
		if(format.equals("")) format="yyyy-MM-dd";
		try
		{
			DateFormat formatter = new SimpleDateFormat(format);
			result=new java.sql.Date(formatter.parse(dateStr).getTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}	
	
	public static java.sql.Date getCurrentSQLDate()
	{
		java.sql.Date result=null;
		try
		{
			java.util.Calendar cal = java.util.Calendar.getInstance();
			result = new java.sql.Date(cal.getTime().getTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
    public static boolean isEmail(String email) {
	boolean result = true;
	try {
	    InternetAddress addr = new InternetAddress(email);
	    addr.validate();
	} catch (AddressException ex) {
	    result = false;
	}
	return result;
    }
	
    /**
     * Is phone number contains only numeric and length more than or equals 1จ
     * 
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber) {
	return StringUtils.isNumeric(phoneNumber) && phoneNumber.length() >= 10;
    }
	
    public static boolean IntToBoolean(int value)
    {
    	return (value>0)?true:false;    	
    }
    
    public static int StrToInt(String value)
    {
    	int result=-1;
    	try
    	{
    		result=Integer.parseInt(value);
    	}
    	catch(Exception e)
    	{
    		;
    	}
    	return result;    	
    }
    public static float StrToFloat(String value)
    {
    	float result=0;
    	try
    	{      		
    	     result = Float.parseFloat(value);
    	}
    	catch(Exception e)
    	{
    		;
    	}
    	return result;    	
    }
    
    public static int getDuration(String format,String startDate,String endDate) 
    {
    	int month = 0; 
    	try 
    	{
    		 SimpleDateFormat sdf = new SimpleDateFormat(format);
    		 Date date = sdf.parse(startDate);
    		 Date date2 = sdf.parse(endDate);
    		 if (getYear(date) == getYear(date2)) // not over 1 year 
    		 { 
    			 month = Math.abs(getMonth(date2)-getMonth(date))+1;
    		 }
    		 else if ((getYear(date2)-getYear(date)) == 1) // 1 year
    		 {
    			 month = ((12 - getMonth(date))+1) + getMonth(date2) ;
    		 }
    		 else 
    		 {
    			 month = ((12 - getMonth(date))+1) + getMonth(date2);
    			 month = month + ((Math.abs((getYear(date2)-1)-(getYear(date)+1))+1)*12);
    		 }
    		 if (month == 0) 
    		 {
    			 month = 1;
    		 }
    	}    	
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    	return month;
    }
    
    public static int getMonthInterval(java.sql.Date startDate,java.sql.Date endDate) 
    {
    	int month = 0; 
    	try 
    	{
    		 if (getYear(startDate) == getYear(endDate)) // not over 1 year 
    		 { 
    			 month = Math.abs(getMonth(endDate)-getMonth(startDate))+1;
    		 }
    		 else if ((getYear(endDate)-getYear(startDate)) == 1) // 1 year
    		 {
    			 month = ((12 - getMonth(startDate))+1) + getMonth(endDate) ;
    		 }
    		 else 
    		 {
    			 month = ((12 - getMonth(startDate))+1) + getMonth(endDate);
    			 month = month + ((Math.abs((getYear(endDate)-1)-(getYear(startDate)+1))+1)*12);
    		 }
    		 if (month == 0) 
    		 {
    			 month = 1;
    		 }
    	}    	
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    	return month;
    }
        
    
    public static int getDayInterval(Timestamp startDate,Timestamp endDate) 
    {
    	return Math.round((endDate.getTime() - startDate.getTime())/86400000);
	}
    
    public static String DateToStr (Date date,String format) 
    {
        String sDate = "";
        try 
        {
        	SimpleDateFormat dataformat = new SimpleDateFormat(format,Locale.US); //Create format Date
        	sDate = dataformat.format( date ); //Convert Date to String (format(Date))
        }
        catch (Exception e) 
        {
 			e.printStackTrace();
		}
        return sDate;
    }
    
    public static java.util.Date getDateFromStr(String str)
    {
    	java.util.Date result=null;
    	if(str!=null)
    	{
    		try
    		{
    			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    			if(str.indexOf('/')!=-1)
    			{
    				df = new SimpleDateFormat("dd/MM/yyyy"); 
    			}
    			else if(str.indexOf('-')!=-1)
    			{
    				df = new SimpleDateFormat("yyyy-MM-dd"); 
    			}
    			result=df.parse(str);
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	return result;
    }
    
    public static String DateToStr(Date date,String format,Locale locale) 
    {
        String sDate = "";
        try 
        {
        	SimpleDateFormat dataformat = new SimpleDateFormat(format,locale); //Create format Date
        	sDate = dataformat.format( date ); //Convert Date to String (format(Date))
        }
        catch (Exception e) 
        {
 			e.printStackTrace();
		}
        return sDate;
    }
    
    public static int getCurrentYear()
    {
    	int result=0;
    	Calendar calendar = GregorianCalendar.getInstance(java.util.Locale.US);
    	result=calendar.get(Calendar.YEAR);
    	return result;
    }

	public static boolean isSupportFormat(String format,String attachmentType)
	{	
		if(attachmentType.equals("DOCUMENT"))
		{	
			if(format.equals("text/plain"))	return true;		
			if(format.equals("application/msword"))	return true;
			if(format.equals("image/pjpeg")) return true;
			if(format.equals("image/jpg")) return true;
			if(format.equals("image/jpeg")) return true;
			if(format.equals("image/bmp")) return true;
			if(format.equals("image/gif"))	return true;
			if(format.equals("image/png")) return true;
			if(format.equals("text/plain"))	return true;		
			if(format.equals("application/msword"))		return true;
			if(format.equals("application/vnd.ms-excel")) return true;			
			if(format.equals("application/pdf")) return true;			
			if(format.equals("application/vnd.ms-powerpoint"))	return true;
			if(format.equals("application/x-zip-compressed"))	return true;
			if(format.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))return true;
			if(format.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation"))return true;
			if(format.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))return true;
		}
		else if(attachmentType.equals("RESUME"))
		{
			if(format.equals("text/plain"))	return true;	
			if(format.equals("application/pdf")) return true;
			if(format.equals("application/msword"))	return true;
			if(format.equals("application/vnd.ms-excel")) return true;
			if(format.equals("application/vnd.ms-powerpoint"))	return true;
			if(format.equals("application/x-zip-compressed"))	return true;
			if(format.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))return true;
			if(format.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation"))return true;
			if(format.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))return true;
		}
		else if(attachmentType.equals("PHOTOGRAPH"))
		{
			if(format.equals("image/pjpeg")) return true;
			if(format.equals("image/jpg")) return true;
			if(format.equals("image/jpeg")) return true;
			if(format.equals("image/bmp")) return true;
			if(format.equals("image/gif"))	return true;
			if(format.equals("image/png")) return true;
		}
		return false;	 	
	}
	
	public static String getFileExtension(String format)
	{
		if(format.equals("text/plain"))
			return ".txt";		
		if(format.equals("image/png"))
			return ".png";
		if(format.equals("image/bmp"))
			return ".bmp";		
		if(format.equals("image/jpg"))
			return ".jpg";
		if(format.equals("image/pjpeg"))
			return ".jpg";
		if(format.equals("image/jpeg"))
			return ".jpg";
		if(format.equals("image/gif"))
			return ".gif";
		if(format.equals("application/msword"))
			return ".doc";
		if(format.equals("application/vnd.ms-excel"))
			return ".xls";			
		if(format.equals("application/pdf"))
			return ".pdf";			
		if(format.equals("application/vnd.ms-powerpoint"))
			return ".ppt";
		if(format.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
			return ".xlsx";
		if(format.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
			return ".docx";
		if(format.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation"))
			return ".pptx";
		return ".unknown";
	}
	
	public static int getDay(java.sql.Date date)
	{
		return StrToInt(DateToStr(date, "d"));
	}

	public static int getMonth(java.sql.Date date)
	{
		return StrToInt(DateToStr(date, "M"));
	}
	
	public static int getYear(java.sql.Date date)
	{
		return StrToInt(DateToStr(date, "yyyy"));
	}
	
	
	public static int getDay(java.util.Date date)
	{
		return StrToInt(DateToStr(date, "d"));
	}

	public static int getMonth(java.util.Date date)
	{
		return StrToInt(DateToStr(date, "M"));
	}
	
	public static int getYear(java.util.Date date)
	{
		return StrToInt(DateToStr(date, "yyyy"));
	}
		
	public static String getMonthToString(java.sql.Date date)
	{
		return DateToStr(date, "MMM");
	}
    
	public static int checkCaptcha(String sessionId,String captcha)
    {
    	int result=0;
    	String SQX=	"SELECT * FROM INTER_CAPTCHA WHERE ID_SESSION=? AND CAPTCHA=?";
    	DBManager dx=null;
    	try
    	{
			dx=new DBManager();
			dx.createPreparedStatement(SQX);
			dx.setString(1, sessionId);
			dx.setString(2, captcha);
			dx.executeQuery();
			if(dx.next())
			{
				result=1;	
				String SQZ="DELETE FROM INTER_CAPTCHA WHERE ID_SESSION=?";
				DBManager dz=null;
				try
				{
					dz=new DBManager();
					dz.createPreparedStatement(SQZ);
					dz.setString(1, sessionId);
					dz.executeUpdate();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					dz.close();
				}   								
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
    	return result;
    }
   
	public static boolean isEnglishString(String str)
	{
		return Pattern.matches("^[a-z0-9-_ ]*$", str);
	}
	
    // check for a is subset of b
 	public static boolean isSubset(int [] a, int [] b)
 	{
 		boolean result=false;
 		if(a.length<b.length)	return false;
 		for(int i=0; i<b.length;  i++)
 		{
 			for(int j=0; j<a.length; j++)
 			{
 				result=false;
 				if(b[i]==a[j])
 				{
 					result=true;
 					break;
 				}
 			}
 			if(result==false)
 			{
 				break;
 			}
 		}
 		return result;		
 	}
 	
 	//return result array of  a - b, see example below
 	//{1,2,3,4}-{1,2,3}= {4}
 	//{1,2}-{1,2,3}=empty set
 	public static int[] arrayMinus(int [] a, int [] b)
 	{
 		int result[]=null;
 		if(a.length==0)	return null;
 		if(b.length==0) return a;
 		int cnt=0;
 		String buf="";
 		for(int i=0; i<a.length;  i++)
 		{
 			int found=0;
 			for(int j=0; j<b.length; j++)
 			{
 				if(a[i]==b[j])
 				{
 					found=1;
 					break;
 				}
 			}
 			if(found==0)
 			{
 				buf+=" "+a[i];
 				cnt++;
 			}
 		}	
 		buf=buf.trim();
 		if(!buf.equals(""))
 		{
 			String buffer[]=buf.split(" ");
 			if(buffer.length>0)
 			{
 				result=new int[cnt];
 				int idx=0;
 				for(int i=0; i<buffer.length; i++)
 				{
 					if(getInt(buffer[i])!=-1)
 					{
 						result[idx]=getInt(buffer[i]);
 						idx++;
 					}
 				}
 			}
 		}
 		return result;
 	}
 	
    public static java.sql.Date getDateFormat(String day,String month,String year) 
    {
    	java.sql.Date result = null;
    	if(day==null) day="";
    	if(month==null) month="";
    	if(year==null) year="";
    	
    	if(day.equals("")|| month.equals("")||year.equals(""))
    	{
    		return null;
    	}
    	try 
    	{
    		
    		String date = year+"-"+month+"-"+day;
    		result = java.sql.Date.valueOf(date);
    	}
    	catch (Exception e) 
    	{
 			e.printStackTrace();
		}
    	return result;
    }
    
    //year is english year such as 1997 not buddhist year
    public static java.sql.Date getSQLDate(int day, int month, int year) 
    {
    	java.sql.Date result = null;
    	
    	if(day<=0|| month<=0|| year<=0)
    	{
    		return null;
    	}
    	
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day);
    	result=new java.sql.Date(cal.getTime().getTime());
    	return result;
    }
    
    public static boolean isFileExist(Attachment att)
    {
    	boolean result=false;
    	String path = Util.class.getClassLoader().getResource("/").toString();
    	String rebuiltPath = "/";
    	String[] splitPath = path.split("/");
    	for(int i=1;i<splitPath.length-2;i++){
    		rebuiltPath+=splitPath[i]+"/";
    	}
    	rebuiltPath+="Attachments";
    	rebuiltPath=rebuiltPath+File.separator+(Math.round(att.getIdJsk()/2000)+1)+File.separator+att.getIdJsk();
		Attachment atm=new AttachmentManager().get(att.getIdJsk(),att.getIdFile());
		String files=rebuiltPath+File.separator+atm.getIdFile()+"_"+atm.getFileType()+Util.getFileExtension(atm.getFileFormat());
		if(new File(files).exists()){
			result=true;
		}
		return result;    	
    }
    
    public static String getLocaleDateFormatFull(Date date,String strFormat,String language,String country) 
    {
 	   DateFormat df;
 	   String sDate = "";
 	   int formatDate=0;
 	   strFormat=strFormat.toUpperCase();
 	   if(strFormat.equals("FULL"))
 	   {
 		   formatDate=DateFormat.FULL;
 	   }
 	   else if(strFormat.equals("LONG"))
 	   {
 		   formatDate=DateFormat.LONG;
 	   }
 	   else // long
 	   {
 		   formatDate=DateFormat.MEDIUM;
 	   }
        try 
        {
     	    df  = DateFormat.getDateInstance(formatDate, new Locale(language, country)); 
     	    sDate=df.format(date);
        }
        catch (Exception e) 
        {
 			e.printStackTrace();
 		}
        return sDate;
    }
    
    public static String getLocaleDate(Date date,String strFormat,String language,String country) 
    {
        String sDate = "";
        try 
        {
        	SimpleDateFormat dataformat = new SimpleDateFormat(strFormat,new Locale(language,country)); //Create format Date
        	sDate = dataformat.format(date); //Convert Date to String (format(Date))
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
		}
        return sDate;
    }
    
	public static String getFileFormat(String filename)
	{
		String[] subfilename = filename.split("[.]");
		String surname = "";
		if(subfilename!=null)
		{
			if(subfilename.length>0)
			{
				surname=subfilename[subfilename.length-1];
				if(surname.equals("txt"))
					return "text/plain";
				if(surname.equals("png"))
					return "image/png";
				if(surname.equals("bmp"))
					return "image/bmp";		
				if(surname.equals("jpg"))
					return "image/jpg";
				if(surname.equals("jpg"))
					return "image/pjpeg";
				if(surname.equals("jpg"))
					return "image/jpeg";
				if(surname.equals("gif"))
					return "image/gif";
				if(surname.equals("doc"))
					return "application/msword";
				if(surname.equals("xls"))
					return "application/vnd.ms-excel";			
				if(surname.equals("pdf"))
					return "application/pdf";			
				if(surname.equals("ppt"))
					return "application/vnd.ms-powerpoint";
				if(surname.equals("xlsx"))
					return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
				if(surname.equals("docx"))
					return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
				if(surname.equals("pptx"))
					return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
			}
		}
		return "error";
	}
	
	public static String getThaiDate() 
    {
        String sDate = "";
        try 
        {
        	Date today = new Date();
        	SimpleDateFormat dataformat = new SimpleDateFormat("d MMMM yyyy",new Locale("th","TH")); //Create format Date
        	sDate = dataformat.format(today); //Convert Date to String (format(Date))
        }
        catch (Exception e) 
        {
 			e.printStackTrace();
		}
        return sDate;
    }
	
	public static String nowDateFormat (String strFormat) 
    {
        String sDate = "";
        try 
        {
        	Date today = new Date();
        	SimpleDateFormat dataformat = new SimpleDateFormat(strFormat,Locale.US); //Create format Date
        	sDate = dataformat.format( today ); //Convert Date to String (format(Date))
        }
        catch (Exception e) 
        {
 			e.printStackTrace();
		}
        return sDate;
    }

	public static Date toDate(String str)
	{
		Date result=null;
		try
		{
			result = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean isDigit(String str)
	{
		boolean result=true;
		str=str!=null?str.trim():"";
		for(int i=0; i<str.length(); i++)
		{
			int ascii=(int)str.charAt(i);
			if((ascii<48)||(ascii>57))
			{
				result=false;
				break;
			}
		}
		return result;		
	}	
	
	public static String getYMDToDate(String date,String language)
	{
		String result=date!=null?date.trim():"";
		if((result.length()==8)&&(isDigit(result)))
		{
			if(language.equals("en"))
			{
				result=result.substring(6,8)+"/"+result.substring(4,6)+"/"+result.substring(0,4);
			}
			else
			{
				result=result.substring(6,8)+"/"+result.substring(4,6)+"/"+(Integer.parseInt(result.substring(0,4))+543);
			}
		}
		if(result.trim().equals(""))
		{
			result=getCurrentDate("dd/MM/yyyy",language);
		}
		return result;
	}	
	
    public static String getCurrentDate(String format,String language) 
    {
        String result = "";
        SimpleDateFormat dataformat=null;
        try 
        {
        	Date today = new Date();
        	if(language.equals("en"))
        	{
        		dataformat = new SimpleDateFormat(format,Locale.US);        		
        	}
        	else
        	{
        		dataformat = new SimpleDateFormat(format,new Locale("th","TH"));
        	}
        	result = dataformat.format(today);
        }
        catch (Exception e) 
        {
 			e.printStackTrace();
		}
        return result;
    }	
    
    
    public static int inArray(String key, String[] array)
    {
    	int result=-1;
    	if(array!=null)
    	{
    		for(int i=0; i<array.length; i++)
    		{
    			if(key.toUpperCase().equals(array[i].toUpperCase()))
    			{
    				result=i;
    				break;
    			}
    		}
    	}
    	return result;
    }
    
    public static  String getHashString()
	{
		return Encoder.getSHA1Encode(Util.getStr(new java.util.Date().getTime()));
	}
    
    public static int getAge(final Date birthdate) {
        return getAge(Calendar.getInstance().getTime(), birthdate);
      }

  public static int getAge(final Date current, final Date birthdate) {

    if (birthdate == null) {
      return 0;
    }
    if (current == null) {
      return getAge(birthdate);
    } else {
      final Calendar calend = new GregorianCalendar();
      calend.set(Calendar.HOUR_OF_DAY, 0);
      calend.set(Calendar.MINUTE, 0);
      calend.set(Calendar.SECOND, 0);
      calend.set(Calendar.MILLISECOND, 0);

      calend.setTimeInMillis(current.getTime() - birthdate.getTime());

      float result = 0;
      result = calend.get(Calendar.YEAR) - 1970;
      result += (float) calend.get(Calendar.MONTH) / (float) 12;
      return (int)result;
    }

  }
  public static String getFormatNumber(double number, String format)
  {
  	String result = "";
  	try
  	{
  		result = new DecimalFormat(format).format(number);
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
  	return result;
  }
  	public static int convertCurrency(int total, int idCurrency)
	{
		int result = total;
		String SQL = "SELECT BAHT FROM RMS_CURRENCY WHERE ID_CURRENCY=?";
		DBManager db = null;
		try
		{
			db = new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idCurrency);
			db.executeQuery();
			if (db.next())
			{
				result = (total * db.getInt("BAHT")) + 0;
				if (result <= 0)
				{
					result = total;
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result;
	}
  	
  	public static int convertSalaryPerMonth(int total, int idCurrency, int salaryPer)
	{
		int result = convertCurrency(total, idCurrency);
		
		if(result!=-1)
		{	//no insert sallary
			if(salaryPer==1)	result= (int)(result / 12f);
			else if(salaryPer==3)	result=result * 4;
			else if(salaryPer==4)	result=result * 2;
			else if(salaryPer==5)	result=result * 30;
			else if(salaryPer==6)	result=result * 168;//8 hr * 21 day = 168 hr
		}
		
		return result;
	}
  	
  	public static String base64(String text)
	{
		String result="";
		if(text!=null)
		{
			result=Base64.encodeBase64String(text.getBytes());
		}
		return result;
	}
  	
  	public static String encodeJskSuperSearch(int idJsk, int idResume) 
	{
		String result="";
		String tempJ="";
		for(int i=0;  i<(8-String.valueOf(idJsk).length()); i++)
		{
			tempJ="0"+tempJ;
		}
		tempJ+=idJsk;
		String tmpR="R";
		for(int i=0;  i<(3-String.valueOf(idResume).length()); i++)
		{
			tmpR+="0";
		}
		tmpR+=idResume;
		result="JTG"+tempJ+tmpR;
		return result;
	}
}
