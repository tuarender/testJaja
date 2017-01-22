<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.text.*"%>
<%@page import="com.topgun.*"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.resume.*"%>
<%@page import="com.topgun.shris.masterdata.*"%>
<b>Version:</b> 31.08.53.10.51.55<br>
<%
	int totalSent=0;
	int jsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	String ip=request.getRemoteAddr();
	try
	{
		if(ip!=null)
		{
			if(ip.equals("203.146.208.152"))
			{
				ip=request.getHeader("Proxy-Client-IP");
				
				if(ip==null)
				{
					ip=request.getHeader("X-Forwarded-For");
				}
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	/*
	if(!((ip.equals("203.146.115.190") || ip.equals("203.146.115.175") || ip.equals("203.146.194.194")) && (jsk>0)))
	{
		response.sendRedirect("/");
	}
	*/
	boolean acceptMail=true;
		
	int idEmp=Util.getInt(request.getParameter("idEmp"));
	int idPos=Util.getInt(request.getParameter("idPos"));
	String startDate=Util.getStr(request.getParameter("startDate"));
	String endDate=Util.getStr(request.getParameter("endDate"));
	String email=Util.getStr(request.getParameter("email"));
	
	
	if(idEmp>0 && !startDate.equals("") && !endDate.equals("") && !email.equals(""))
	{
		
			String sql="SELECT ID_JSK,ID_RESUME,ID_EMP,ID_POSITION,EMP_OTHER,POSITION_OTHER,RECIPIENT,ATTACHMENTS FROM INTER_TRACK ";
			  sql+="WHERE ID_EMP=? AND TO_CHAR(SENTDATE,'YYYYMMDD')>=TO_CHAR(TO_DATE('"+startDate+"','DD/MM/YYYY'),'YYYYMMDD') AND TO_CHAR(SENTDATE,'YYYYMMDD')<=TO_CHAR(TO_DATE('"+endDate+"','DD/MM/YYYY'),'YYYYMMDD') ";
			  if(idPos>0)
			  {
			  	sql+=" AND ID_POSITION="+idPos+" ";
			  }
			  sql+="ORDER BY ID_JSK,ID_RESUME ";
			DBManager db=null;			
			try
			{
				db=new DBManager();
				db.createPreparedStatement(sql);
				db.setInt(1,idEmp);
				db.executeQuery();
				while(db.next())
				{
					int idJsk=db.getInt("ID_JSK");
					int idResume=db.getInt("ID_RESUME");					
					int idPosition = db.getInt("ID_POSITION");
					Track track=new TrackManager().get(idJsk, idResume, idEmp, idPosition);
					track.setSent(0);
					track.setEmpOther(email);
					new ResentResumeThread(track).start();	
					totalSent++;
				}
			}
			catch(Exception e)
			{
				out.println("error:"+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				db.close();
			}
	}
%>
<font color="#0000ff"><b>Re-send Resume <%=totalSent%> Successfully</b></font>
