package com.topgun.util;

public class Decryption 
{
	private int idEmp=-1;
	private int idPosition=-1;
	private int idJsk=-1;
	private int idResume=-1;
	private boolean valid=false;
	
	private static final String encoder[]={"Mu8Bo","Kt7Zb","Nv9Ej","Js6Xw","Dz5Af","Op0Fk","Ra3In","Qx2Hm","Pq1Gl","Ch4Ye"};
	private static final String spliter="LcSdTrUgVyWi";	
	private static final String keyenc[]={"F3iUo","6GaRp","ncW5D","HO2by","AjmY4","h7BrT","0eStK","X9Mvf","g1lPJ","V8Lsz"};
	private static final String kspliter="NdCkEuQxZwIq";

	public Decryption(String encode,String key)
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
				valid=true;
			}
			else
			{
				idEmp=-1;
				idPosition=-1;
				idJsk=-1;
				idResume=-1;
				valid=false;				
			}
		}
		catch(Exception e)
		{
			idEmp=-1;
			idPosition=-1;
			idJsk=-1;
			idResume=-1;
			valid=false;
			e.printStackTrace();
		}
	}
		
	public int getIdEmp() 
	{
		return idEmp;
	}


	public int getIdPosition() 
	{
		return idPosition;
	}



	public int getIdJsk() 
	{
		return idJsk;
	}


	public int getIdResume() 
	{
		return idResume;
	}

	private int[] decoding(String encode)	throws Exception
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

	
	private int[] decodeKey(String encode) throws Exception
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

	private int decoding(char c)
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

	private int keyDecoding(char c)
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

	public boolean isValid() {
		return valid;
	}	

}
