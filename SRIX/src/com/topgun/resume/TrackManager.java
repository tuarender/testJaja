package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;

import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.shris.masterdata.School;
import com.topgun.shris.masterdata.State;
import com.topgun.util.DBManager;

public class TrackManager 
{
	public Track get(int idJsk,int idResume,int idEmp,int idPosition,int Old)
	{
		Track result=null;
		DBManager db=null;
		String SQL="SELECT IDEMP_T,IDPOSITION_T,COMPANYNAME_T,POSITION_T,DATE_T,IDRESUME_T,FROMFLAG FROM TRACK WHERE (IDJSK_T=?) AND (IDRESUME_T=?) AND (IDEMP_T=?) AND (IDPOSITION_T=?) AND DELFLAG='0'";		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
			db.setInt(3, idEmp);
			db.setInt(4, idPosition);
			db.executeQuery();
			if(db.next())
			{ 
				result=new Track();
				result.setIdJsk(idJsk);
				result.setIdResume(idResume);
				result.setIdEmp(db.getInt("IDEMP_T"));
    			result.setIdPosition(db.getInt("IDPOSITION_T"));
    			result.setEmpOther(db.getString("COMPANYNAME_T"));
    			result.setPositionOther(db.getString("POSITION_T"));
    			result.setSendDate(db.getTimestamp("DATE_T"));
    			result.setAttachments(db.getString("IDRESUME_T"));
    			result.setSource(db.getString("FROMFLAG"));
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
	
	 public int deleteOld(int idJsk,int idResume,int idEmp,int idPosition)
	 {
		 int result = 0;
		 String SQL=	"UPDATE TRACK SET DELFLAG='1' WHERE (IDJSK_T=?) AND (IDRESUME_T=?) AND (IDEMP_T=?) AND (IDPOSITION_T=?)";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, idJsk);
			 db.setInt(2, idResume);
			 db.setInt(3, idEmp);
			 db.setInt(4, idPosition);
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
	 
	 public List<Track> getAll(int idJsk,int idResume)
	 {
		 List<Track> result = new ArrayList<Track>();
			DBManager db=null;
			String SQL="SELECT ID_JSK,ID_RESUME,ID_EMP,ID_POSITION,EMP_OTHER,POSITION_OTHER,RECIPIENT,SENTDATE,IP," +
					"NOTE,ATTACHMENTS,DEFAULT_STATUS,NOTE_UPDATE,TIMESTAMP,SOURCE,ID,SENT,PLACE,REFERER FROM INTER_TRACK WHERE (ID_JSK=?) AND (ID_RESUME=?)";		
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.executeQuery();
				 while(db.next())
				{
					Track tr=new Track();
					tr.setIdJsk(db.getInt("ID_JSK"));
					tr.setIdResume(db.getInt("ID_RESUME"));
					tr.setIdTrack(db.getInt("ID"));
					tr.setIdEmp(db.getInt("ID_EMP"));
	    			tr.setIdPosition(db.getInt("ID_POSITION"));
	    			tr.setEmpOther(db.getString("EMP_OTHER"));
	    			tr.setPositionOther(db.getString("POSITION_OTHER"));
	    			tr.setRecipient(db.getString("RECIPIENT"));
	    			tr.setSendDate(db.getTimestamp("SENTDATE"));
	    			tr.setIp(db.getString("IP"));
	    			tr.setNote(db.getString("NOTE"));
	    			tr.setAttachments(db.getString("ATTACHMENTS"));
	    			tr.setDefaultStatus(db.getString("DEFAULT_STATUS"));
	    			tr.setNoteUpdate(db.getTimestamp("NOTE_UPDATE"));
	    			tr.setTimeStamp(db.getTimestamp("TIMESTAMP"));
	    			tr.setSource(db.getString("SOURCE"));
	    			tr.setIdTrack(db.getInt("ID"));
	    			tr.setSent(db.getInt("SENT"));
	    			tr.setPlace(db.getString("PLACE"));
	    			tr.setReferer(db.getString("REFERER"));
	    			result.add(tr);
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
	 
	 public int countAll(int idJsk,int idResume)
	 {
		 	int  result= 0;
			DBManager db=null;
			String SQL="SELECT COUNT(*) AS TOTAL FROM INTER_TRACK WHERE (ID_JSK=?) AND (ID_RESUME=?)";		
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
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
	 public Track getLastTrackNotsend(int idJsk,int idResume)
	 {
		 	Track result=null;
			DBManager db=null;
			String SQL="SELECT ID_JSK,ID_RESUME,ID_EMP,ID_POSITION,EMP_OTHER,POSITION_OTHER,RECIPIENT,SENTDATE,IP," +
					"NOTE,ATTACHMENTS,DEFAULT_STATUS,NOTE_UPDATE,TIMESTAMP,SOURCE,ID,SENT,PLACE,REFERER FROM INTER_TRACK WHERE (ID_JSK=?) AND (ID_RESUME=?) ORDER BY ID DESC";		
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.executeQuery();
				if(db.next())
				{ 
					result=new Track();
					result.setIdJsk(db.getInt("ID_JSK"));
					result.setIdResume(db.getInt("ID_RESUME"));
					result.setIdTrack(db.getInt("ID"));
					result.setIdEmp(db.getInt("ID_EMP"));
	    			result.setIdPosition(db.getInt("ID_POSITION"));
	    			result.setEmpOther(db.getString("EMP_OTHER"));
	    			result.setPositionOther(db.getString("POSITION_OTHER"));
	    			result.setRecipient(db.getString("RECIPIENT"));
	    			result.setSendDate(db.getTimestamp("SENTDATE"));
	    			result.setIp(db.getString("IP"));
	    			result.setNote(db.getString("NOTE"));
	    			result.setAttachments(db.getString("ATTACHMENTS"));
	    			result.setDefaultStatus(db.getString("DEFAULT_STATUS"));
	    			result.setNoteUpdate(db.getTimestamp("NOTE_UPDATE"));
	    			result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
	    			result.setSource(db.getString("SOURCE"));
	    			result.setIdTrack(db.getInt("ID"));
	    			result.setSent(db.getInt("SENT"));
	    			result.setPlace(db.getString("PLACE"));
	    			result.setReferer(db.getString("REFERER"));
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

	 public Track get(int idJsk,int idResume,int idEmp, int idPosition)
	 {
		 Track result=null;
		 DBManager db=null;
		 String SQL="SELECT "
			 		+ " ID_JSK,ID_RESUME,EMP_OTHER,POSITION_OTHER,RECIPIENT,SENTDATE,IP," 
			 		+ "NOTE,ATTACHMENTS,DEFAULT_STATUS,NOTE_UPDATE,TIMESTAMP,SOURCE,ID,SENT,PLACE,REFERER "
		 		+ " FROM "
		 			+ " INTER_TRACK TR "
		 		+ " LEFT JOIN "
		 			+ " INTER_TRACK_EXTENSION TR_EXT "
		 		+ " ON "
		 			+ " TR.ID = TR_EXT.ID_TRACK "
		 		+ " WHERE (ID_JSK=?) AND (TR.ID_RESUME=? OR TR_EXT.ID_CHILD =?) AND (ID_EMP=?) AND (ID_POSITION=?)";		
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, idJsk);
			 db.setInt(2, idResume);
			 db.setInt(3, idResume);
			 db.setInt(4, idEmp);
			 db.setInt(5, idPosition);
			 db.executeQuery();
			 if(db.next())
			 { 
				 result=new Track();
				 result.setIdJsk(db.getInt("ID_JSK"));
				 result.setIdResume(db.getInt("ID_RESUME"));
				 result.setIdTrack(db.getInt("ID"));
				 result.setEmpOther(db.getString("EMP_OTHER"));
				 result.setPositionOther(db.getString("POSITION_OTHER"));
				 result.setRecipient(db.getString("RECIPIENT"));
				 result.setSendDate(db.getTimestamp("SENTDATE"));
				 result.setIp(db.getString("IP"));
				 result.setNote(db.getString("NOTE"));
				 result.setAttachments(db.getString("ATTACHMENTS"));
				 result.setDefaultStatus(db.getString("DEFAULT_STATUS"));
				 result.setNoteUpdate(db.getTimestamp("NOTE_UPDATE"));
				 result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
				 result.setSource(db.getString("SOURCE"));
				 result.setIdTrack(db.getInt("ID"));
				 result.setSent(db.getInt("SENT"));
				 result.setPlace(db.getString("PLACE"));
				 result.setReferer(db.getString("REFERER"));
			 }
			 return result;
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
	 public Track get(int idJsk,int idResume,int idTrack)
		{
			Track result=null;
			DBManager db=null;
			String SQL="SELECT ID_EMP,ID_POSITION,EMP_OTHER,POSITION_OTHER,RECIPIENT,SENTDATE,IP," +
					"NOTE,ATTACHMENTS,DEFAULT_STATUS,NOTE_UPDATE,TIMESTAMP,SOURCE,ID,SENT,PLACE,REFERER FROM INTER_TRACK WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID=?)";		
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.setInt(3, idTrack);
				db.executeQuery();
				if(db.next())
				{ 
					result=new Track();
					result.setIdJsk(idJsk);
					result.setIdResume(idResume);
					result.setIdTrack(idTrack);
					result.setIdEmp(db.getInt("ID_EMP"));
	    			result.setIdPosition(db.getInt("ID_POSITION"));
	    			result.setEmpOther(db.getString("EMP_OTHER"));
	    			result.setPositionOther(db.getString("POSITION_OTHER"));
	    			result.setRecipient(db.getString("RECIPIENT"));
	    			result.setSendDate(db.getTimestamp("SENTDATE"));
	    			result.setIp(db.getString("IP"));
	    			result.setNote(db.getString("NOTE"));
	    			result.setAttachments(db.getString("ATTACHMENTS"));
	    			result.setDefaultStatus(db.getString("DEFAULT_STATUS"));
	    			result.setNoteUpdate(db.getTimestamp("NOTE_UPDATE"));
	    			result.setTimeStamp(db.getTimestamp("TIMESTAMP"));
	    			result.setSource(db.getString("SOURCE"));
	    			result.setIdTrack(db.getInt("ID"));
	    			result.setSent(db.getInt("SENT"));
	    			result.setPlace(db.getString("PLACE"));
	    			result.setReferer(db.getString("REFERER"));
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
		
	 public List<Track> getAllByidEmp(int idJsk,int idResume,int idEmp)
	 {
			List<Track> result = new ArrayList<Track>();
			DBManager db=null;
			String SQL="SELECT ID_JSK,ID_RESUME,ID_EMP,ID_POSITION,EMP_OTHER,POSITION_OTHER,RECIPIENT,SENTDATE,IP," +
			"NOTE,ATTACHMENTS,DEFAULT_STATUS,NOTE_UPDATE,TIMESTAMP,SOURCE,ID,SENT,PLACE,REFERER FROM INTER_TRACK WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_EMP=?) ORDER BY ID";
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idJsk);
				db.setInt(2, idResume);
				db.setInt(3, idEmp);
			    db.executeQuery();
			    while(db.next())
			    {
			    	Track tr=new Track();
			    	tr.setIdJsk(db.getInt("ID_JSK"));
			    	tr.setIdResume(db.getInt("ID_RESUME"));
			    	tr.setIdEmp(db.getInt("ID_EMP"));
			    	tr.setIdPosition(db.getInt("ID_POSITION"));
			    	tr.setEmpOther(db.getString("EMP_OTHER"));
			    	tr.setPositionOther(db.getString("POSITION_OTHER"));
			    	tr.setRecipient(db.getString("RECIPIENT"));
			    	tr.setSendDate(db.getTimestamp("SENTDATE"));
			    	tr.setIp(db.getString("IP"));
			    	tr.setNote(db.getString("NOTE"));
			    	tr.setAttachments(db.getString("ATTACHMENTS"));
			    	tr.setDefaultStatus(db.getString("DEFAULT_STATUS"));
			    	tr.setNoteUpdate(db.getTimestamp("NOTE_UPDATE"));
			    	tr.setTimeStamp(db.getTimestamp("TIMESTAMP"));
			    	tr.setSource(db.getString("SOURCE"));
			    	tr.setIdTrack(db.getInt("ID"));
			    	tr.setSent(db.getInt("SENT"));
			    	tr.setPlace(db.getString("PLACE"));
			    	tr.setReferer(db.getString("REFERER"));
			    	result.add(tr);
			    }
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				db.close();
			}
			return result;
	 }
		
	 public List<Track> getAll(int idJsk)
	 {
			List<Track> result = new ArrayList<Track>();
			DBManager db=null;
			String SQL="SELECT ID_JSK,ID_RESUME,ID_EMP,ID_POSITION,EMP_OTHER,POSITION_OTHER,RECIPIENT,SENTDATE,IP," +
			"NOTE,ATTACHMENTS,DEFAULT_STATUS,NOTE_UPDATE,TIMESTAMP,SOURCE,ID,SENT,PLACE,REFERER FROM INTER_TRACK WHERE (ID_JSK=?) ORDER BY TIMESTAMP DESC";
			try
			{
				db=new DBManager();
				db.createPreparedStatement(SQL);
				db.setInt(1, idJsk);
			    db.executeQuery();
			    while(db.next())
			    {
			    	Track tr=new Track();
			    	tr.setIdJsk(db.getInt("ID_JSK"));
			    	tr.setIdResume(db.getInt("ID_RESUME"));
			    	tr.setIdEmp(db.getInt("ID_EMP"));
			    	tr.setIdPosition(db.getInt("ID_POSITION"));
			    	tr.setEmpOther(db.getString("EMP_OTHER"));
			    	tr.setPositionOther(db.getString("POSITION_OTHER"));
			    	tr.setRecipient(db.getString("RECIPIENT"));
			    	tr.setSendDate(db.getTimestamp("SENTDATE"));
			    	tr.setIp(db.getString("IP"));
			    	tr.setNote(db.getString("NOTE"));
			    	tr.setAttachments(db.getString("ATTACHMENTS"));
			    	tr.setDefaultStatus(db.getString("DEFAULT_STATUS"));
			    	tr.setNoteUpdate(db.getTimestamp("NOTE_UPDATE"));
			    	tr.setTimeStamp(db.getTimestamp("TIMESTAMP"));
			    	tr.setSource(db.getString("SOURCE"));
			    	tr.setIdTrack(db.getInt("ID"));
			    	tr.setSent(db.getInt("SENT"));
			    	tr.setPlace(db.getString("PLACE"));
			    	tr.setReferer(db.getString("REFERER"));
			    	result.add(tr);
			    }
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				db.close();
			}
			return result;
	 }
	 
	 public int add(Track tr)
	 {
		 int result = 0;
		 String SQL= "INSERT INTO INTER_TRACK " +
		 "("	+"ID,"
		 +"ID_JSK,"
		 +"ID_RESUME,"
		 +"ID_EMP,"
		 +"ID_POSITION,"
		 +"EMP_OTHER,"
		 +"POSITION_OTHER,"
		 +"RECIPIENT,"
		 +"SENTDATE,"
		 +"IP,"
		 +"NOTE,"
		 +"ATTACHMENTS,"
		 +"DEFAULT_STATUS,"
		 +"NOTE_UPDATE,"
		 +"DELETE_STATUS,"
		 +"TIMESTAMP,"
		 +"SOURCE,SENT,PLACE,REFERER,SHORT_REFERER) "
		 +"VALUES(SEQ_INTER_TRACK.NEXTVAL,?,?,?,?,?,?,?,SYSDATE,?,?,?,?,SYSDATE,'FALSE',SYSDATE,?,?,?,?,?)";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, tr.getIdJsk());
			 db.setInt(2, tr.getIdResume());
			 db.setInt(3, tr.getIdEmp());
			 db.setInt(4, tr.getIdPosition());
			 db.setString(5, tr.getEmpOther());
			 db.setString(6, tr.getPositionOther());
			 db.setString(7, tr.getRecipient());
			 db.setString(8, tr.getIp());
			 db.setString(9, tr.getNote());
			 db.setString(10, tr.getAttachments());
			 db.setString(11, tr.getDefaultStatus());
			 db.setString(12, tr.getSource());
			 db.setInt(13, tr.getSent());
			 db.setString(14, tr.getPlace());
			 db.setString(15, tr.getReferer());
			 db.setString(16, tr.getShortReferer());
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
		
	 public int delete(Track tr)
	 {
		 int result = 0;
		 String SQL=	"UPDATE INTER_TRACK SET DELETE_STATUS='TRUE',TIMESTAMP=SYSDATE WHERE (ID=?) and (ID_JSK=?)";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, tr.getIdTrack());
			 db.setInt(2, tr.getIdJsk());
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
	    
	 public int update(Track tr)
	 {
		 int result = 0;
		 String SQL=	"UPDATE INTER_TRACK " +
		 "SET "
		 +"ID_RESUME=?, " 
		 +"ID_EMP=?, " 
		 +"ID_POSITION=?, " 
		 +"EMP_OTHER=?, " 
		 +"POSITION_OTHER=?, " 
		 +"RECIPIENT=?, " 
		 +"SENTDATE=?, " 
		 +"IP=?, "
		 +"ATTACHMENTS=?,"
		 +"NOTE=?, " 
		 +"DEFAULT_STATUS=?, " 
		 +"NOTE_UPDATE=?, " 
		 +"TIMESTAMP=SYSDATE, "
		 +"SOURCE=?, "
		 +"SENT=?, "
		 +"PLACE=?, "
		 +"REFERER=? "
		 +"WHERE (ID=?) AND (ID_JSK=?)";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, tr.getIdResume());
			 db.setInt(2, tr.getIdEmp());
			 db.setInt(3, tr.getIdPosition());
			 db.setString(4, tr.getEmpOther());
			 db.setString(5, tr.getPositionOther());
			 db.setString(6, tr.getRecipient());
			 db.setTimestamp(7, tr.getSendDate());
			 db.setString(8, tr.getIp());
			 db.setString(9, tr.getAttachments());
			 db.setString(10, tr.getNote());
			 db.setString(11, tr.getDefaultStatus());
			 db.setTimestamp(12, tr.getNoteUpdate());
			 db.setString(13, tr.getSource());
			 db.setInt(14, tr.getSent());
			 db.setString(15, tr.getPlace());
			 db.setString(16, tr.getReferer());
			 db.setInt(17, tr.getIdTrack());
			 db.setInt(18, tr.getIdJsk());
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
	 
	public int countTrackByDatePosition(String applyDate,int idEmp,int idPosition)
	{
		int count = 0;
		DBManager db=null;
		String sql="";
		String startDate="";
		String endDate="";
		
		try
		{
			
			 String[] period = getPeriodJob(idEmp,idPosition,applyDate);
			if(period!=null)
			{
				startDate=period[0];
				endDate=period[1];

				
				db=new DBManager();
				sql="select sum(summary+saf) as countTrack from inter_track_transfer where id_emp=? and id_position=?  and "+
				"sentdate  between ? AND ? ";

				db.createPreparedStatement(sql);
			    db.setInt(1, idEmp);
			    db.setInt(2, idPosition);
			    db.setString(3, startDate);
			    db.setString(4, endDate);
			    db.executeQuery();
			 
			    if(db.next())
			    {
			    	count=db.getInt("countTrack");
			    }
				
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			if(db!=null)
			{
				db.close();
			}
		
		}
		
		return count;
	}
	public String[] getPeriodJob(int idEmp,int idPosition,String applyDate)
	{
		   String[] period = null;
			DBManager db=null;
			DBManager db1=null;
			String sql="";
			int found=0;

		 try
		 {
			 db=new DBManager();
			 sql="select  online_date, e_d2 from position where  id_emp = ?  and id_position = ?  and   (  ( ?  between  online_date and e_d2  )  or   (online_date <=? and e_d2 <= ? ) ) ";
			 db.createPreparedStatement(sql);
			 db.setInt(1, idEmp);
			 db.setInt(2, idPosition);
			 db.setString(3, applyDate);
			 db.setString(4, applyDate);
			 db.setString(5, applyDate);
			 db.executeQuery();
			 if(db.next())
			 {
				 period=new String[2];
				 period[0]=db.getString("online_date");
				 period[1]=db.getString("e_d2");
				 found=1;
			 }
			 if(found==0)
			 {
				 db1=new DBManager();
				 sql="select  online_date, expire_date from position_date_b where  id_emp = ? and id_p = ? and  ?  between  online_date and expire_date ";
				 db1.createPreparedStatement(sql);
				 db1.setInt(1, idEmp);
				 db1.setInt(2, idPosition);
				 db1.setString(3, applyDate);
				 db1.executeQuery();
				 if(db1.next())
				 {
					 period=new String[2];
					 period[0]=db1.getString("online_date");
					 period[1]=db1.getString("expire_date");
				 }
			 }
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
		 finally
		 {
			 db.close();
			 if(db1!=null)
			 {
				 db1.close();
			 }
		 }
		return period;
	}
	
	public int chkViewResume(int idEmp,int idPosition,int idJsk,int idResume)
	{
		int result=0;
		try
		{
			result=countViewResumeScreen(idEmp,idPosition,idJsk,idResume)+countViewResumeOpen(idEmp,idPosition,idJsk,idResume);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

			
		return result;
		
	}
	public int countViewResumeScreen(int idEmp,int idPosition,int idJsk,int idResume)
	{
		int result=0;
		DBManager db=null;
		String sql="select id_count , count_click, count_screen from count_view_resume where id_emp=? and id_position=? and id_jsk =? and id_resume = ? and count_screen > 0 ";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idJsk);
			db.setInt(4, idResume);
	        db.executeQuery();
	        if(db.next())
			{ 
	        	result=db.getInt("count_screen");
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.close();
		}
			
		return result;
		
	}
	
	public int countViewResumeOpen(int idEmp,int idPosition,int idJsk,int idResume)
	{
		int result=0;
		DBManager db=null;
		String sql="select count(id_view) as count_open  from inter_view_resume_log where id_emp=? and id_position=? and id_jsk =? and id_resume = ? ";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setInt(1, idEmp);
			db.setInt(2, idPosition);
			db.setInt(3, idJsk);
			db.setInt(4, idResume);
	        db.executeQuery();
	        if(db.next())
			{ 
	        	result=db.getInt("count_open");
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.close();
		}
			
		return result;
		
	}
	
	public  boolean isERecruit(int idEmp)
	{
		boolean result=false;
		String SQL="SELECT COM_NAME,ID,THAI_NAME,FWD_MAIL,SUPER FROM CALL WHERE (ID=?) "; 
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idEmp);
    		db.executeQuery();
    		if(db.next())
    		{
    			if(db.getInt("SUPER") == 1)
    			{
    				result=true;
    			}    			
    		}
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
		return result;
	}
	public int addTrackAll(int idJsk, int idResume,int idEmp,String companyName,int idPosition,String positionName,String idJobFair )
	{
		int result=0;
		boolean  isERecruit=false;
		try
		{
			isERecruit=isERecruit(idEmp);
			if(isERecruit == true)
			{
				if(idResume > 0) // send by SuperResume
				{
					//3.track_idEmp, 4.track_screen
					result = addTrackIdEmp(idJsk, idResume,idEmp, companyName,idPosition, positionName,1);
				}
				else if(idResume == 0)
				{
					//3.track_idEmp
					result = addTrackIdEmp(idJsk, idResume,idEmp, companyName,idPosition, positionName,3);
				}

			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public int addTrackIdEmp(int idJsk, int idResume,int idEmp,String companyName,int idPosition,String positionName,int jobinSite)
	{
		int result = 0;
		String schoolName="";
		String SQL="";
		int haveTable=0;
		// jobinSite = 1 is superresume, 3 is saf
		DBManager dbx=null;
		try
		{
			SQL="SELECT * FROM tab WHERE TNAME = 'TRACK_"+idEmp+"'";
			dbx=new DBManager();
			dbx.createPreparedStatement(SQL);
			dbx.executeQuery();
			if(dbx.next())
			{
				haveTable=1;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbx.close();
		}
		if(haveTable == 1)
		{
			SQL = "INSERT INTO TRACK_"+ idEmp
			+ " (COMPANYNAME_T,IDRESUME_T,IDJSK_T,POSITION_T,DATE_T,FIRSTNAME_T,LASTNAME_T,UNIVERSITY_T,IDEMP_T,IDPOSITION_T,FROM_) "
			+ "VALUES(?,?,?,?,TO_DATE(SYSDATE,'dd/MM/yyyy'),?,?,?,?,?,?)";

			DBManager db = null;
			try
			{
				Resume resume =new ResumeManager().get(idJsk, idResume);
				String firstName=resume.getFirstName();
				String lastName=resume.getLastName();
				if(resume.getIdLanguage()==38)
				{
					firstName=resume.getFirstNameThai();
					lastName=resume.getLastNameThai();
				}
				Education edu =new EducationManager().getHighestEducation(idJsk, idResume);
				if(edu!=null)
				{
					if(edu.getIdSchool()==-1)
					{
						schoolName=edu.getOtherSchool();
					}
					else
					{
						School school=MasterDataManager.getSchool(edu.getIdCountry(),edu.getIdState(),edu.getIdSchool(),resume.getIdLanguage());
						if(school!=null)
						{
							schoolName=school.getSchoolName();
						}
					}
				}
				db = new DBManager();
				db.createPreparedStatement(SQL);
				db.setString(1, companyName);
				db.setInt(2, idResume);
				db.setInt(3, idJsk);
				db.setString(4, positionName);
				db.setString(5, firstName);
				db.setString(6, lastName);
				db.setString(7, schoolName);
				db.setInt(8, idEmp);
				db.setInt(9, idPosition);
				db.setInt(10, jobinSite);
				result = db.executeUpdate();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				db.close();
			}
		}

		return result;
	}
	
    public int getTrackId(int idJsk,int idResume)
    {
    	int result=-1;
    	String SQL="SELECT MAX(ID) AS MAXID FROM INTER_TRACK WHERE ID_JSK=? AND ID_RESUME=?";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.executeQuery();
    		if(db.next())
    		{
    			result=db.getInt("MAXID");
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
    
	 public int addApplyLog(Track tr)
	 {
		 int result = 0;
		 String SQL= "INSERT INTO APPLYALL_LOG " +
		 "(ID_JSK,"
		 +"ID_RESUME,"
		 +"ID_EMP,"
		 +"ID_POSITION,"
		 +"TIMESTAMPS) "
		 +"VALUES(?,?,?,?,SYSDATE)";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, tr.getIdJsk());
			 db.setInt(2, tr.getIdResume());
			 db.setInt(3, tr.getIdEmp());
			 db.setInt(4, tr.getIdPosition());
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
	 
	public String getProvince(int idTrack, int idLanguage)
	{
		String result="";
		DBManager db=null;
		String SQL="SELECT ID_STATE FROM INTER_TRACK_NATIONWIDE WHERE ID_TRACK=?";		
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idTrack);
			db.executeQuery();
			if(db.next())
			{ 
				State state=new MasterDataManager().getState(216, db.getInt("ID_STATE"), idLanguage);
				if(state!=null)
				{
					result=state.getStateName();
				}
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
	
	 public int addTrackExtension(int idTrack, int idParent, int idChild)
	 {
		 int result = 0;
		 String SQL= "INSERT INTO INTER_TRACK_EXTENSION " +
		 "(ID_TRACK,"
		 +"ID_PARENT,"
		 +"ID_CHILD) "
		 +"VALUES(?,?,?)";
		 DBManager db=null;
		 try
		 {
			 db=new DBManager();
			 db.createPreparedStatement(SQL);
			 db.setInt(1, idTrack);
			 db.setInt(2, idParent);
			 db.setInt(3, idChild);
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
	 
	 public int getIdTrackIn2Month(int idJsk ,int idResume ,int idEmp ,int idPosition)
	 {
		 int result = 0 ;
		 DBManager db = null ;
		 String sql = "";
		 try
		 {
			 sql = "SELECT ID FROM INTER_TRACK WHERE ID_JSK = ? AND ID_RESUME = ? AND ID_EMP = ? AND ID_POSITION = ? AND SENTDATE BETWEEN SYSDATE-60 AND SYSDATE ";
			 db = new DBManager();
			 db.createPreparedStatement(sql);
			 db.setInt(1, idJsk);
			 db.setInt(2, idResume);
			 db.setInt(3, idEmp);
			 db.setInt(4, idPosition);
			 db.executeQuery();
			 if(db.next())
			 {
				 result = db.getInt("ID");
			 }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }finally
		 {
			db.close(); 
		 }
		 return result ;
	 }
	 
	 public int getCountTrackResultIn2Month(int idJsk,String notExistsTrack)
	 {
		 int result = 0 ;
		 DBManager db = null ;
		 String sql = "";
		 String notExistsSql = "";
		 if(!notExistsTrack.equals(""))
		 {
			 notExistsSql = " AND ID NOT IN ("+notExistsTrack+")";
		 }
		 try
		 {
			 sql = "SELECT COUNT(1) AS TOTAL FROM INTER_TRACK WHERE ID_JSK = ? AND SENTDATE BETWEEN SYSDATE-60 AND SYSDATE " + notExistsSql ;
			 db = new DBManager() ;
			 db.createPreparedStatement(sql);
			 db.setInt(1, idJsk);
			 db.executeQuery();
			 if(db.next())
			 {
				 result = db.getInt("TOTAL");
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
		 
		 return result ;
	 }
}