package com.topgun.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import javax.naming.Context;
import javax.sql.DataSource;

public class DBManager
{	
	private PreparedStatement stmn;	
	private Connection conn;
	private ResultSet dataset;
	private String SQL="";
	private String autoIncrementColumn = "";
	private long startTime=0;
	private ResultSetMetaData metaData=null;
	
	public DBManager()
	{
		Context context=null;
		DataSource ds=null;
		try
		{
			context=DBContext.getContext();
			ds = (DataSource) context.lookup("resumeJNDI");
			this.conn = ds.getConnection();
			this.startTime=new java.util.Date().getTime();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public DBManager(String JndiName)
	{
		Context context=null;
		DataSource ds=null;
		try
		{
			context=DBContext.getContext();
			ds = (DataSource) context.lookup("interJNDI");
			this.conn = ds.getConnection();
			this.startTime=new java.util.Date().getTime();
		}  
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public DBManager(boolean inter66)
	{
		Context context=null;
		DataSource ds=null;
		try
		{
			context=DBContext.getContext();
			ds = (DataSource) context.lookup("inter66JNDI");
			this.conn = ds.getConnection();
			this.startTime=new java.util.Date().getTime();
		}  
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public int countColumn()
	{
		int result=0;
		try
		{
			if(this.dataset!=null)
			{
				this.metaData = this.dataset.getMetaData();
				result=metaData.getColumnCount();
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public String getColumnName(int index)
	{
		String result="";
		try
		{
			if(this.dataset!=null)
			{
				this.metaData = this.dataset.getMetaData();
				result=metaData.getColumnName(index);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;		
	}
	
	public int getColumnTypes(int index)
	{
		int result=0;
		try
		{
			if(this.dataset!=null)
			{
				this.metaData = this.dataset.getMetaData();
				result=metaData.getColumnType(index);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;				
	}
	
	public void createPreparedStatement(String sql)  throws Exception 
	{				
		this.stmn = this.conn.prepareStatement(sql);
		this.SQL=sql;
	}
	
	public void createPreparedStatement(String sql, String autoIncrementColumn)  throws Exception 
	{				
		this.stmn = this.conn.prepareStatement(sql, new String[]{autoIncrementColumn});
		this.autoIncrementColumn = autoIncrementColumn;
		this.SQL=sql;
	}
	
	public int getGeneratedId()
	{
		int result = -1;
		try
		{
			ResultSet rs = this.stmn.getGeneratedKeys();
			rs.next();
			result = rs.getInt(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public void setInt(int index,int value) throws Exception 
	{
		this.stmn.setInt(index, value);
	}
	
	public void setString(int index,String value) throws Exception
	{
		this.stmn.setString(index,value);			
	}

	public void setDate(int index, Date value) throws Exception
	{		
		this.stmn.setDate(index,value);			
	}

	public void setTime(int index, Time value) throws Exception
	{		
		this.stmn.setTime(index,value);			
	}

	public void setFloat(int index, float value) throws Exception
	{		
		this.stmn.setFloat(index,value);			
	}
	
	public void setDouble(int index, double value) throws Exception
	{		
		this.stmn.setDouble(index,value);			
	}
			
	public void setLong(int index, long value) throws Exception
	{		
		this.stmn.setLong(index,value);			
	}

	public void setTimestamp(int index, Timestamp value) throws Exception
	{		
		this.stmn.setTimestamp(index,value);				
	}	
		
	public String getString(String field) throws Exception
	{
		return this.dataset.getString(field);
	}

	public int getInt(String field) throws Exception
	{
		return this.dataset.getInt(field);
	}
	
	public float getFloat(String field) throws Exception
	{
		return this.dataset.getFloat(field);
	}	
	
	public long getLong(String field) throws Exception
	{
		return this.dataset.getLong(field);
	}	

	public double getDouble(String field) throws Exception
	{
		return this.dataset.getDouble(field);
	}	

	public Date getDate(String field) throws Exception
	{
		return this.dataset.getDate(field);
	}	

	public Time getTime(String field) throws Exception
	{
		return this.dataset.getTime(field);
	}	
	
	public Timestamp getTimestamp(String field) throws Exception
	{
		return this.dataset.getTimestamp(field);
	}	
	
	public void executeQuery() throws Exception 
	{				
		this.dataset = this.stmn.executeQuery();			
	}
	
	public int executeUpdate() throws Exception
	{
		return this.stmn.executeUpdate();
	}

	public void first() throws Exception
	{
		this.dataset.beforeFirst();
	}
	
	public boolean next() throws Exception
	{
		return this.dataset.next();
	}
	
	public void close() 
	{
		try 
		{				
			if(this.dataset!=null)
			{
				this.dataset.close();
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			this.dataset=null;
		}
		
		try
		{
			if(this.stmn!=null)
			{
				this.stmn.close();	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.stmn=null;
		}
		
		try
		{
			if(this.conn!=null)
			{
				this.conn.close();		
			}
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			this.conn=null;
		}
		long usage=new java.util.Date().getTime()-this.startTime;
		if(usage>5000)
		{
			System.out.println(this.SQL);
			System.out.println("Usage: "+usage);
		}		
	}
}