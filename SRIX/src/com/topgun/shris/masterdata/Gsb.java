package com.topgun.shris.masterdata;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;

public class Gsb 
{
	int row;
	String content;
	String desc;
	int region;
	int idProvince;
	String province;
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public int getIdProvince() {
		return idProvince;
	}
	public void setIdProvince(int idProvince) {
		this.idProvince = idProvince;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public static List<Gsb> getAllContent()
	{
		List<Gsb> result=new ArrayList<Gsb>();
		String SQL="SELECT * FROM GSB_CHOICE ORDER BY ID";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.executeQuery();
			while(db.next())
			{
				Gsb ans=new Gsb();
				ans.setRow(db.getInt("ID"));
				ans.setContent(db.getString("CONTENT"));
				ans.setDesc(db.getString("DESCRIPTION"));
				result.add(ans);
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
	
	public static List<Gsb> getProvince(int idRegion)
	{
		List<Gsb> result=new ArrayList<Gsb>();
		String SQL="SELECT * FROM GSB_PROVINCE WHERE ID_REGION =? ORDER BY PROVINCE_NAME";
		DBManager db=null;
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idRegion);
			db.executeQuery();
			while(db.next())
			{
				Gsb ans=new Gsb();
				ans.setRegion(db.getInt("ID_REGION"));
				ans.setIdProvince(db.getInt("ID_PROVINCE"));
				ans.setProvince(db.getString("PROVINCE_NAME"));
				result.add(ans);
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
