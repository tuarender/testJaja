package com.topgun.search;

import java.util.ArrayList;
import java.util.List;

import com.topgun.util.DBManager;


public class WishListManager
{
	public static boolean isExist(WishList wishList)
	{
		boolean result=false;
		DBManager db=null;
		String sql="SELECT ID_EMP FROM INTER_WISHLIST WHERE ID_EMP=? AND ID_POSITION=? AND ID_JSK=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,wishList.getIdEmp());
			db.setInt(2,wishList.getIdPosition());
			db.setInt(3,wishList.getIdJsk());
			db.executeQuery();
			if(db.next())
			{
				result=true;
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
	
	public static int addWishList(WishList wishList)
	{
		int result=0;
		if(isExist(wishList))	return 1;
		DBManager db=null;
		String sql="INSERT INTO INTER_WISHLIST(ID_EMP, ID_POSITION, ID_JSK,READ) VALUES(?,?,?,0)";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,wishList.getIdEmp());
			db.setInt(2,wishList.getIdPosition());
			db.setInt(3,wishList.getIdJsk());
			result=db.executeUpdate();
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

	public static int deleteWishList(WishList wishList)
	{
		int result=1;
		DBManager db=null;
		String sql="DELETE FROM INTER_WISHLIST WHERE ID_EMP=? AND ID_POSITION=? AND ID_JSK=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,wishList.getIdEmp());
			db.setInt(2,wishList.getIdPosition());
			db.setInt(3,wishList.getIdJsk());
			db.executeUpdate();
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
	
	public static int countWithList(int idJsk)
	{
		int result=0;
		DBManager db=null;
		String sql="SELECT COUNT(*) AS TOTAL "
					+" FROM INTER_WISHLIST A "
					+" INNER JOIN POSITION B "
					+" ON A.ID_POSITION = B.ID_POSITION "
					+" AND A.ID_EMP = B.ID_EMP "
					+" AND TO_DATE(B.POST_DATE, 'YYYYMMDD') <= SYSDATE "
					+" AND TO_DATE(B.E_D2, 'YYYYMMDD') >= SYSDATE "
					+" AND B.FLAG = 1 "
					+" AND A.ID_JSK = ?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	
	
	public static int countWithListNotification(int idJsk)
	{
		int result=0;
		DBManager db=null;
		String sql="SELECT COUNT(*) AS TOTAL "
				+" FROM INTER_WISHLIST A "
				+" INNER JOIN POSITION B "
				+" ON A.ID_POSITION = B.ID_POSITION "
				+" AND A.ID_EMP = B.ID_EMP "
				+" AND TO_DATE(B.POST_DATE, 'YYYYMMDD') <= SYSDATE "
				+" AND TO_DATE(B.E_D2, 'YYYYMMDD') >= SYSDATE "
				+" AND B.FLAG = 1 "
				+" AND A.ID_JSK = ? AND READ = 0";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			db.executeQuery();
			if(db.next())
			{
				result=db.getInt("TOTAL");
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
	
	public static int setReadAllWithList(int idJsk)
	{
		int result=0;
		DBManager db=null;
		String sql="UPDATE INTER_WISHLIST SET READ=1 WHERE ID_JSK=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,idJsk);
			result=db.executeUpdate();
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
	
	public static int setReadWithList(WishList wishList)
	{
		int result=0;
		DBManager db=null;
		String sql="UPDATE INTER_WISHLIST SET READ=1 WHERE ID_JSK=? AND ID_EMP=? AND ID_JSK=?";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1,wishList.getIdJsk());
			db.setInt(2,wishList.getIdEmp());
			db.setInt(3,wishList.getIdPosition());
			result=db.executeUpdate();
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
	
	public static List<WishList> getAllWishList(int idJsk)
	{
		List<WishList> result = new ArrayList<WishList>();
		String sql = "";
		DBManager db= null ;
		try{
			db = new DBManager();
			sql = "SELECT * FROM INTER_WISH WHERE ID_JSK = ?";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			while(db.next())
			{
				WishList wl = new WishList();
				wl.setIdJsk(db.getInt("ID_JSK"));
				wl.setIdEmp(db.getInt("ID_EMP"));
				wl.setIdPosition(db.getInt("ID_POSITION"));
				wl.setRead(db.getInt("READ"));
				wl.setTimettamp(db.getDate("TIMESTAMP"));
				result.add(wl);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			db.close();
		}
		return result ;
	}
}
