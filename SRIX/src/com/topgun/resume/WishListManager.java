package com.topgun.resume;

import com.topgun.util.DBManager;

public class WishListManager {
	public int deleteWishList(int idJsk,int idEmp,int idPosition)
	{
		int result = 0 ;
		DBManager db = null;
		String sql = "";
		try{
			db = new DBManager();
			sql = "DELETE FROM INTER_WISHLIST WHERE ID_JSK = ? AND ID_EMP = ? AND ID_POSITION = ?";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setInt(2, idEmp);
			db.setInt(3, idPosition);
			result = db.executeUpdate();
			
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
