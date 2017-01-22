<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.topgun.resume.*"%>
<%@ page import="com.topgun.util.*"%>
<% 
	DBManager db=null;
	try
	{
		db=new DBManager();
		//String sql=" SELECT ID_JSK, ID_RESUME ,CREATE_DATE FROM INTER_RESUME WHERE TO_CHAR(CREATE_DATE,'YYYYMMDD')='20141216' AND ID_RESUME=0 ORDER BY CREATE_DATE DESC";
		String sql="SELECT ID_JSK, ID_RESUME ,TIMESTAMP FROM INTER_RESUME WHERE COMPLETE_STATUS IN('WORKEXP_QUESTION','WORKEXP_INTERN', 'WORKEXP_FULL_TIME','WORKEXP_PART_TIME'  ) ORDER BY TIMESTAMP DESC";
		db.createPreparedStatement(sql);
		db.executeQuery();
		while(db.next())
		{
			Resume resume=new ResumeManager().get(db.getInt("ID_JSK"), 0);
			String status=new ResumeStatusManager().getRegisterStatus(resume);
			System.out.println("ID_JSK="+db.getInt("ID_JSK")+" STATUS="+status);
			resume.setCompleteStatus(status);
			new ResumeManager().update(resume);
		}
		System.out.println("END");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		db.close();
	}
%>