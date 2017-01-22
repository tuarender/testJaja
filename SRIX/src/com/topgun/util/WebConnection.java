package com.topgun.util;


import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.google.gson.stream.JsonReader;

public class WebConnection {
	
	enum WebMethod{
		GET,
		POST,
		PUT,
		DELETE
	}
	
	private WebMethod method = WebMethod.POST;
	private String requestCharset = "utf-8";
	private String parameterCharset = "utf-8";
	private String contentType = "application/x-www-form-urlencoded";
	private String url = "";
	private String parameterQuery = "";
	
	private JsonReader reader = null;
	private GZIPInputStream gzis = null;
	private InputStream inputStream = null;
	
	private HttpURLConnection http = null;
	
	private HashMap<String, String> parameterMap = new HashMap<String, String>();
	
	public WebConnection(String url)
	{
		this.url = url;
		initializeConnection();
	}
	
	public WebConnection(String url, String parameterQuery)
	{
		this.url = url;
		this.parameterQuery = parameterQuery;
		initializeConnection();
	}
	
	public WebConnection(String url, String parameterQuery, String requestCharset)
	{
		this.url = url;
		this.parameterQuery = parameterQuery;
		this.requestCharset = requestCharset;
		initializeConnection();
	}
	
	public WebConnection(String url, String parameterQuery, String requestCharset, String contentType)
	{
		this.url = url;
		this.parameterQuery = parameterQuery;
		this.requestCharset = requestCharset;
		this.contentType = contentType;
		initializeConnection();
	}
	
	public JsonReader getJsonReader()
	{
		try
		{
			this.reader = new JsonReader(new InputStreamReader(getInputStream(), this.requestCharset));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this.reader;
	}
	
	public JsonReader getJsonReaderFromGzip()
	{
		try
		{
			this.gzis = new GZIPInputStream(getInputStream());
			this.reader = new JsonReader(new InputStreamReader(gzis, this.requestCharset));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return this.reader;
	}
	
	private InputStream getInputStream() {

		byte[] paramByte = null;
		try {
			this.http.setRequestProperty("charset", this.requestCharset);
			if(this.parameterQuery != null && this.parameterQuery.length() > 0)
			{
				paramByte = this.getParameterQuery().getBytes(this.parameterCharset);
			}
			else
			{
				String param = "";
				String value = "";
				for (String key : this.parameterMap.keySet()) {
					value = this.parameterMap.get(key);
					param += param.length() > 0 ? "&"+key : key;
					param += "="+value;
				};
				paramByte = param.getBytes(this.parameterCharset);
			}
			
			if(paramByte != null)
			{
				this.http.setDoOutput(true);
				this.http.setRequestMethod(this.method.toString());
				this.http.setRequestProperty("Content-Type", this.contentType);
				this.http.setRequestProperty("Content-Length", String.valueOf(paramByte.length) );
				
				DataOutputStream outData = new DataOutputStream(this.http.getOutputStream());
				outData.write(paramByte);
				outData.flush();
				outData.close();
				
			}
			
			this.inputStream = this.http.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.inputStream;
	}

	private void initializeConnection()
	{
		try
		{
			this.http = (HttpURLConnection)new URL(this.url).openConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try
		{
			if(this.inputStream != null)this.inputStream.close();
			if(this.reader != null)	this.reader.close();
			if(this.gzis != null)	this.gzis.close();
			if(this.http != null)	this.http.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.inputStream = null;
			this.reader = null;
			this.gzis = null;
			this.http = null;
			this.parameterMap = null;
		}
	}
	
	public String getQueryString()
	{
		String result = "";
		try
		{
			result = this.http.getURL().getQuery();
			for (Map.Entry<String, String> q : this.parameterMap.entrySet()) {
				if(result.length() > 0)	result += "&";
				result += q.getKey()+"="+q.getValue();
			}
		}
		catch(Exception e){}
		return result;
	}
	
	public void addRequestProperty(String property, String value)
	{
		this.http.setRequestProperty(property, value);
	}
	
	public void setConnectTimeout(int sec)
	{
		this.http.setConnectTimeout(sec * 1000);
	}
	
	public void addParameter(String parameterName, String value)
	{
		this.parameterMap.put(parameterName, value);
	}
	
	public void addParameter(String parameterName, int value)
	{
		this.parameterMap.put(parameterName, String.valueOf(value));
	}
	
	public String getParameterValue(String parameterName)
	{
		return this.parameterMap.get(parameterName);
	}
	
	public void removeParameter(String parameterName)
	{
		this.parameterMap.remove(parameterName);
	}
	
	public void setReadTimeout(int sec)
	{
		this.http.setReadTimeout(sec * 1000);
	}

	public String getParameterQuery() {
		return parameterQuery;
	}

	public void setParameterQuery(String parameterQuery) {
		this.parameterQuery = parameterQuery;
	}
	
	public WebMethod getMethod() {
		return method;
	}

	public void setMethod(WebMethod method) {
		this.method = method;
	}

	public String getRequestCharset() {
		return requestCharset;
	}

	public void setRequestCharset(String requestCharset) {
		this.requestCharset = requestCharset;
	}

	public String getParameterCharset() {
		return parameterCharset;
	}

	public void setParameterCharset(String parameterCharset) {
		this.parameterCharset = parameterCharset;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
