package com.topgun.resume;

import com.topgun.util.DBManager;

public class CancelServiceManager {
	
	public static int add(CancelService cs){
		int result = 0;
		DBManager db=null;
		String sql;
		try{
			db=new DBManager();
			sql="INSERT INTO CANCEL_SERVICE(ID_JSK, EMAIL, SERVICE, REASON_TYPE, HOW_LONG, HOW_OFTEN, NOT_MATCH, " +
					" CHANGE_MAIL, OTHER_REASON, TEL, NEW_EMAIL, NEXT_SEND) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,ADD_MONTHS(SYSDATE, ?))";
			db.createPreparedStatement(sql);
			db.setInt(1, cs.getIdJsk());
			db.setString(2, cs.getEmail());
			db.setString(3, cs.getService());
			db.setInt(4, cs.getReasonType());
			db.setInt(5, cs.getHowLong());
			db.setInt(6, cs.getHowOften());
			db.setInt(7, cs.getNotMatch());
			db.setInt(8, cs.getChangeMail());
			db.setString(9, cs.getOtherReason());
			db.setString(10, cs.getTel());
			db.setString(11, cs.getNewEmail());
			db.setInt(12, getAddMonth(cs));
			result = db.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
	
	public static int update(CancelService cs){
		int result = 0;
		DBManager db=null;
		String sql;
		String sqlDisableTimeStamp = ",DISABLE_TIMESTAMP='' ";
		if(cs.getDisable().equalsIgnoreCase("TRUE")||cs.getDisable().equalsIgnoreCase("FALSE"))
		{
			if(cs.getDisable().equalsIgnoreCase("TRUE")){
				sqlDisableTimeStamp = ",DISABLE_TIMESTAMP=SYSDATE ";
			}
		}
		else{
			cs.setDisable("FALSE");
		}
		try{
			db=new DBManager();
			sql="UPDATE CANCEL_SERVICE SET EMAIL=?, REASON_TYPE=?, HOW_LONG=?, HOW_OFTEN=?, NOT_MATCH=?, CHANGE_MAIL=?, " +
					"OTHER_REASON=?, TEL=?, NEW_EMAIL=?, NEXT_SEND=ADD_MONTHS(SYSDATE, ?), DISABLE=UPPER(?) " +sqlDisableTimeStamp+
					"WHERE ID_JSK=? AND SERVICE = ?";
			db.createPreparedStatement(sql);
			db.setString(1, cs.getEmail());
			db.setInt(2, cs.getReasonType());
			db.setInt(3, cs.getHowLong());
			db.setInt(4, cs.getHowOften());
			db.setInt(5, cs.getNotMatch());
			db.setInt(6, cs.getChangeMail());
			db.setString(7, cs.getOtherReason());
			db.setString(8, cs.getTel());
			db.setString(9, cs.getNewEmail());
			db.setInt(10, getAddMonth(cs));
			db.setString(11, cs.getDisable());
			db.setInt(12, cs.getIdJsk());
			db.setString(13, cs.getService());
			result = db.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}

	
	public static boolean checkExist(CancelService cs,String service){
		boolean result = false;
		DBManager db=null;
		String sql;
		try{
			db=new DBManager();
			sql = "SELECT ID_JSK FROM CANCEL_SERVICE WHERE ID_JSK = ? AND SERVICE = ?";
			db.createPreparedStatement(sql);
			db.setInt(1, cs.getIdJsk());
			db.setString(2, service);
			db.executeQuery();
			if(db.next()){
				result = true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
	
	public static CancelService getByIdJsk(int idJsk){
		CancelService result = null;
		DBManager db=null;
		String sql;
		try{
			db=new DBManager();
			sql = 	"SELECT ID_JSK, EMAIL, SERVICE, REASON_TYPE, " +
				  	" HOW_LONG, HOW_OFTEN, NOT_MATCH, CHANGE_MAIL, " +
				  	" OTHER_REASON, TEL, NEW_EMAIL, TO_CHAR(TIMESTAMP,'DD/MM/YYYY') AS TIMESTAMP, TO_CHAR(NEXT_SEND,'DD/MM/YYYY') AS NEXT_SEND, " +
				  	" DISABLE, TO_CHAR(DISABLE_TIMESTAMP,'DD/MM/YYYY') AS DISABLE_TIMESTAMP FROM CANCEL_SERVICE WHERE ID_JSK = ?";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next()){
				result = new CancelService();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setEmail(db.getString("EMAIL"));
				result.setService(db.getString("SERVICE"));
				result.setReasonType(db.getInt("REASON_TYPE"));
				result.setHowLong(db.getInt("HOW_LONG"));
				result.setHowOften(db.getInt("HOW_OFTEN"));
				result.setNotMatch(db.getInt("NOT_MATCH"));
				result.setChangeMail(db.getInt("CHANGE_MAIL"));
				result.setOtherReason(db.getString("OTHER_REASON"));
				result.setTel(db.getString("TEL"));
				result.setNewEmail(db.getString("NEW_EMAIL"));
				result.setTimeStamp(db.getString("TIMESTAMP"));
				result.setNextSend(db.getString("NEXT_SEND"));
				result.setDisable(db.getString("DISABLE"));
				result.setDisableTimeStamp(db.getString("DISABLE_TIMESTAMP"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
	
	public static CancelService getCancelByIdJsk(int idJsk){
		CancelService result = null;
		DBManager db=null;
		String sql;
		try{
			db=new DBManager();
			sql = 	"SELECT ID_JSK, EMAIL, SERVICE, REASON_TYPE, " +
				  	" HOW_LONG, HOW_OFTEN, NOT_MATCH, CHANGE_MAIL, " +
				  	" OTHER_REASON, TEL, NEW_EMAIL, TO_CHAR(TIMESTAMP,'DD/MM/YYYY') AS TIMESTAMP, TO_CHAR(NEXT_SEND,'DD/MM/YYYY') AS NEXT_SEND, " +
				  	" DISABLE, TO_CHAR(DISABLE_TIMESTAMP,'DD/MM/YYYY') AS DISABLE_TIMESTAMP FROM CANCEL_SERVICE WHERE ID_JSK = ? AND DISABLE <> 'TRUE' AND (NEXT_SEND >= SYSDATE OR HOW_LONG IN (0,5,6))";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.executeQuery();
			if(db.next()){
				result = new CancelService();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setEmail(db.getString("EMAIL"));
				result.setService(db.getString("SERVICE"));
				result.setReasonType(db.getInt("REASON_TYPE"));
				result.setHowLong(db.getInt("HOW_LONG"));
				result.setHowOften(db.getInt("HOW_OFTEN"));
				result.setNotMatch(db.getInt("NOT_MATCH"));
				result.setChangeMail(db.getInt("CHANGE_MAIL"));
				result.setOtherReason(db.getString("OTHER_REASON"));
				result.setTel(db.getString("TEL"));
				result.setNewEmail(db.getString("NEW_EMAIL"));
				result.setTimeStamp(db.getString("TIMESTAMP"));
				result.setNextSend(db.getString("NEXT_SEND"));
				result.setDisable(db.getString("DISABLE"));
				result.setDisableTimeStamp(db.getString("DISABLE_TIMESTAMP"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
	
	public static CancelService getCancelByIdJsk(int idJsk,String service){
		CancelService result = null;
		DBManager db=null;
		String sql;
		try{
			db=new DBManager();
			sql = 	"SELECT ID_JSK, EMAIL, SERVICE, REASON_TYPE, " +
				  	" HOW_LONG, HOW_OFTEN, NOT_MATCH, CHANGE_MAIL, " +
				  	" OTHER_REASON, TEL, NEW_EMAIL, TO_CHAR(TIMESTAMP,'DD/MM/YYYY') AS TIMESTAMP, TO_CHAR(NEXT_SEND,'DD/MM/YYYY') AS NEXT_SEND, " +
				  	" DISABLE, TO_CHAR(DISABLE_TIMESTAMP,'DD/MM/YYYY') AS DISABLE_TIMESTAMP FROM CANCEL_SERVICE WHERE ID_JSK = ? AND SERVICE = ? AND DISABLE <> 'TRUE' AND (NEXT_SEND >= SYSDATE OR HOW_LONG IN (0,5,6))";
			db.createPreparedStatement(sql);
			db.setInt(1, idJsk);
			db.setString(2, service);
			db.executeQuery();
			if(db.next()){
				result = new CancelService();
				result.setIdJsk(db.getInt("ID_JSK"));
				result.setEmail(db.getString("EMAIL"));
				result.setService(db.getString("SERVICE"));
				result.setReasonType(db.getInt("REASON_TYPE"));
				result.setHowLong(db.getInt("HOW_LONG"));
				result.setHowOften(db.getInt("HOW_OFTEN"));
				result.setNotMatch(db.getInt("NOT_MATCH"));
				result.setChangeMail(db.getInt("CHANGE_MAIL"));
				result.setOtherReason(db.getString("OTHER_REASON"));
				result.setTel(db.getString("TEL"));
				result.setNewEmail(db.getString("NEW_EMAIL"));
				result.setTimeStamp(db.getString("TIMESTAMP"));
				result.setNextSend(db.getString("NEXT_SEND"));
				result.setDisable(db.getString("DISABLE"));
				result.setDisableTimeStamp(db.getString("DISABLE_TIMESTAMP"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}

	
	public static int getAddMonth(CancelService cs){
		int result = 0;
		if(cs.getHowLong()==1){
			result = 3;
		}
		else if(cs.getHowLong()==2){
			result = 6;
		}
		else if(cs.getHowLong()==3){
			result = 12;
		}
		else if(cs.getHowLong()==4){
			result = 24;
		}
		return result;
	}
	
	public static int cancelNonMember(String email){
		int result = -1;
		DBManager db=null;
		String sql;
		try{
			db=new DBManager(true);
			sql = "UPDATE EMAIL_NONMEMBER SET CANCEL_STATUS='TRUE',CANCEL_TIMESTAMP=SYSDATE WHERE EMAIL=?";
			db.createPreparedStatement(sql);
			db.setString(1, email);
			result=db.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
		return result;
	}
}
