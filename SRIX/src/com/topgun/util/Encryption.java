package com.topgun.util;

import java.util.Random; 

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class Encryption 
{
	public static int idEmp=-1;
	public static int idPosition=-1;
	public static int idJsk=-1;
	public static int idResume=-1;
	public static boolean isValid=false;
	
	private static final String encoder[]={"Mu8Bo","Kt7Zb","Nv9Ej","Js6Xw","Dz5Af","Op0Fk","Ra3In","Qx2Hm","Pq1Gl","Ch4Ye"};
	private static final String spliter="LcSdTrUgVyWi";	
	private static final String keyenc[]={"F3iUo","6GaRp","ncW5D","HO2by","AjmY4","h7BrT","0eStK","X9Mvf","g1lPJ","V8Lsz"};
	private static final String kspliter="NdCkEuQxZwIq";

	public static String getEncoding(int idEmp, int idPosition, int idJsk, int idResume)
	{
		String result="";
		String E1=encoding(""+(idEmp+idResume));
		String E2=encoding(""+(idEmp+idJsk));
		String E3=encoding(""+(idEmp+idPosition));
		String E4=encoding(""+idEmp);
		result=E1+getSpliter()+E2+getSpliter()+E3+getSpliter()+E4;
		return result;		
	}
	
	public static String getKey(int idEmp, int idPosition, int idJsk, int idResume)
	{
		String result="";
		String E1=encodeKey(""+(idEmp+idResume));
		String E2=encodeKey(""+(idEmp+idJsk));
		String E3=encodeKey(""+(idEmp+idPosition));
		String E4=encodeKey(""+idEmp);
		result=E1+getKeySpliter()+E2+getKeySpliter()+E3+getKeySpliter()+E4;
		return result;		
	}

	public static void getDecoding(String encode,String key)
	{
		int decode[]=new int[4];
		int keychk[]=new int[4];
		try
		{
			decode=decoding(encode);
			keychk=decodeKey(key);
			if(	(decode[0]==keychk[0])&&(decode[1]==keychk[1])&&(decode[2]==keychk[2])&&(decode[3]==keychk[3]) &&
				(decode[0]!=-1)&&(decode[1]!=-1)&&(decode[2]!=-1)&&(decode[3]!=-1))
			{			
				//check and set -> ID_RESUME
				if((decode[0]==keychk[0])&&(decode[0]!=-1))
				{
					idResume=decode[0];
				}
				
				//check and set -> ID_JSK
				if((decode[1]==keychk[1])&&(decode[1]!=-1))
				{
					idJsk=decode[1];
				}
				
				//check and set -> ID_Position
				if((decode[2]==keychk[2])&&(decode[2]!=-1))
				{
					idPosition=decode[2];
				}
				
				//check and set -> ID_EMP
				if((decode[3]==keychk[3])&&(decode[3]!=-1))
				{
					idEmp=decode[3];
				}
				isValid=true;
			}
			else
			{
				idEmp=-1;
				idPosition=-1;
				idJsk=-1;
				idResume=-1;
				isValid=false;				
			}
		}
		catch(Exception e)
		{
			idEmp=-1;
			idPosition=-1;
			idJsk=-1;
			idResume=-1;
			isValid=false;
			e.printStackTrace();
		}
	}
	
	private static String encoding(String bits)
	{
		String result="";
		if(bits!=null)
		{
			if(bits.length()>0)
			{
				Random rand=new Random();
				for(int i=0; i<bits.length(); i++)
				{
					int idx=Integer.parseInt(""+bits.charAt(i));
					result+=encoder[idx].charAt(Math.abs(rand.nextInt())%5);
				}
			}
		}
		
		return result;
	}

	private static String getSpliter()
	{
		String result="";
		Random rand=new Random();
		int length=Math.abs(rand.nextInt())%8;
		while(length<4)
		{
			length=Math.abs(rand.nextInt())%8;
		}
		
		for(int i=0; i<length; i++)
		{
			result+=spliter.charAt(Math.abs(rand.nextInt())%12);
		}		
		
		return result;
	}	

	private static String encodeKey(String bits)
	{
		String result="";
		if(bits!=null)
		{
			if(bits.length()>0)
			{
				Random rand=new Random();
				for(int i=0; i<bits.length(); i++)
				{
					int idx=Integer.parseInt(""+bits.charAt(i));
					result+=keyenc[idx].charAt(Math.abs(rand.nextInt())%5);
				}
			}
		}
		return result;
	}
	
	private static String getKeySpliter()
	{
		String result="";
		Random rand=new Random();
		int length=Math.abs(rand.nextInt())%8;
		while(length<4)
		{
			length=Math.abs(rand.nextInt())%8;
		}
		
		for(int i=0; i<length; i++)
		{
			result+=kspliter.charAt(Math.abs(rand.nextInt())%12);
		}		
		
		return result;
	}	
	
	private static int[] decoding(String encode)	throws Exception
	{
		int result[]=new int[4];
		result[0]=-1;
		result[1]=-1;
		result[2]=-1;
		result[3]=-1;
		
		if(encode!=null)
		{
			encode=encode.trim();
			if(!encode.equals(""))
			{
				int idx=0;
				boolean found=true;
				String buf="";
				for(int i=0; i<encode.length(); i++)
				{
					int encoderIndex=decoding(encode.charAt(i));
					int spliterIndex=spliter.indexOf(encode.charAt(i));
					if((encoderIndex!=-1)||(spliterIndex!=-1))
					{
						if(encoderIndex!=-1)
						{
							buf+=encoderIndex;
							found=true;
						}
						else
						{
							if(found==true)
							{
								try
								{
									result[idx]=Integer.parseInt(buf);
								}
								catch(Exception e)
								{
									result[idx]=-1;
									break;
								}
								buf="";
								idx++;
							}
							found=false;							
						}
					}
					else
					{
						break;
					}
				}
				
				if(!buf.equals(""))
				{
					try
					{
						result[idx]=Integer.parseInt(buf);
					}
					catch(Exception e)
					{
						result[idx]=-1;
					}
				}
			}
		}
		
		if((result[0]!=-1)&&(result[1]!=-1)&&(result[2]!=-1)&&(result[3]!=-1))
		{
			result[2]=result[2]-result[3];
			result[1]=result[1]-result[3];
			result[0]=result[0]-result[3];
		}
		else
		{
			result[0]=-1;
			result[1]=-1;
			result[2]=-1;			
			result[3]=-1;			
		}
		
		return result;
	}

	
	private static int[] decodeKey(String encode) throws Exception
	{
		int result[]=new int[4];
		result[0]=-1;
		result[1]=-1;
		result[2]=-1;
		result[3]=-1;
		
		if(encode!=null)
		{
			encode=encode.trim();
			if(!encode.equals(""))
			{
				int idx=0;
				boolean found=true;
				String buf="";
				for(int i=0; i<encode.length(); i++)
				{
					int encoderIndex=keyDecoding(encode.charAt(i));
					int spliterIndex=kspliter.indexOf(encode.charAt(i));
					if((encoderIndex!=-1)&&(spliterIndex!=-1))
					{
						System.out.println(encode.charAt(i));
					}
					if((encoderIndex!=-1)||(spliterIndex!=-1))
					{
						if(encoderIndex!=-1)
						{
							buf+=encoderIndex;
							found=true;
						}
						else
						{
							if(found==true)
							{
								try
								{
									result[idx]=Integer.parseInt(buf);
								}
								catch(Exception e)
								{
									result[idx]=-1;
									break;
								}
								buf="";
								idx++;
							}
							found=false;							
						}
					}
					else
					{
						break;
					}
				}
				
				if(!buf.equals(""))
				{
					try
					{
						result[idx]=Integer.parseInt(buf);
					}
					catch(Exception e)
					{
						result[idx]=-1;
					}
				}
			}
		}
		
		if((result[0]!=-1)&&(result[1]!=-1)&&(result[2]!=-1)&&(result[3]!=-1))
		{
			result[2]=result[2]-result[3];
			result[1]=result[1]-result[3];
			result[0]=result[0]-result[3];
		}
		else
		{
			result[0]=-1;
			result[1]=-1;
			result[2]=-1;			
			result[3]=-1;			
		}
		
		return result;
	}

	private static int decoding(char c)
	{
		for(int i=0; i<10; i++)
		{
			if(encoder[i].indexOf(c)!=-1)
			{
				return i;
			}
		}
		return -1;
	}

	private static int keyDecoding(char c)
	{
		for(int i=0; i<10; i++)
		{
			if(keyenc[i].indexOf(c)!=-1)
			{
				return i;
			}
		}
		return -1;
	}	
	
	public static String decode(String key) 
	{
	    return StringUtils.newStringUtf8(Base64.decodeBase64(key));
	}
	public static String encode(String key) 
	{
	    return Base64.encodeBase64String(StringUtils.getBytesUtf8(key));
	}
	
}