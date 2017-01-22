<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.topgun.util.*"%>
<%
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
	if(!((ip.equals("203.146.115.190") || ip.equals("203.146.115.175")) && (jsk>0)))
	{
		response.sendRedirect("/");
	}
	*/
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>SuperResume.com</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="/js/jquery.js"></script>
		<script type="text/javascript" src="/js/jquery.ui.datepicker-th.js"></script> 	
		<script type="text/javascript" src="/js/jquery-ui-1.8.23.custom.min.js"></script> 	
		<script type="text/javascript" src="/js/jquery.validate.js"></script>
		
	<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js"></script> 
	<link type="text/css" rel="stylesheet" href="css/jquery-ui.css" media="screen"/>	
	
			
		<script language="javascript">
			$(document).ready(function() 
			{
				$('#startDate').datepicker(
				{				
					changeMonth: true,
					changeYear: true,
					dateFormat: 'dd/mm/yy',
					showAnim: 'show',
					yearRange: '-5:-0',
					firstDay:1
				});
	 
	 
				$('#endDate').datepicker(
				{				
					changeMonth: true,
					changeYear: true,
					dateFormat: 'dd/mm/yy',
					showAnim: 'show',
					yearRange: '-5:-0',
					firstDay:1
				});				
				
				$("#form1").validate({
					success: function(label){label.text("ok!").addClass("success");}
				});				
			}); 		
		</script> 	
		<style type="text/css">
			body,td,input
			{
				font-family:Tahoma;
				font-size:10pt;
				margin:0px;
			}		
		
			label.error 
			{
				background:url("/images/unchecked.gif") no-repeat 2px 0px;
				padding-left: 18px;
				color:#ff0000;
				font-style:italic;
			}
			
			label.success 
			{
				background:url("/images/checked.gif") no-repeat 2px 0px;
				padding-left: 18px;
				color:#0000ff;
				font-style:italic;
			}
			label.error { display: none; }			
		</style>
	</head>
<body>
<br><br><br>
<center><h1>Re-Send Resume</h1></center>
<form action="resentResume.jsp" method="post" id="form1">
<table border="0" cellpadding="5" cellspacing="1" bgcolor="#eeeeee" width="600" align="center">
<tr>
	<td bgcolor="#ffffff" align="right" width="200" valign="top">รหัสบริษัท (ID_EMP) :</td>
	<td bgcolor="#ffffff" width="400"><input type="text" name="idEmp" required="true" number="true" size="10"></td>
</tr>
<tr>
	<td bgcolor="#ffffff" align="right" width="200" valign="top">รหัสตำแหน่ง (ID_POSITION) :</td>
	<td bgcolor="#ffffff" width="400"><input type="text" name="idPos" number="true" size="10"><br><font color="#ff0000"><i>รหัสตำแหน่ง ถ้าไม่ระบุ ระบบจะส่งให้ทุกตำแหน่งในบริษัทนั้น</i></font></td>
</tr>
<tr>
	<td bgcolor="#ffffff" align="right" width="200" valign="top">Email ผู้รับ :</td>
	<td bgcolor="#ffffff" width="400"><input type="text" name="email" required="true" size="50"><br><font color="#ff0000"><i>ถ้ามีหลาย E-mail ให้คั่นด้วย , (comma)</i></font></td>
</tr>
<tr>
	<td bgcolor="#ffffff" align="right">ตั้งแต่วันที่ :</td>
	<td bgcolor="#ffffff"><input type="text" readonly="readonly" name="startDate" id="startDate" size="10" required="true"></td>
</tr>	
<tr>
	<td bgcolor="#ffffff" align="right">ถึงวันที่ :</td>
	<td bgcolor="#ffffff"><input type="text" readonly="readonly" name="endDate" id="endDate" size="10" required="true"></td>
</tr>	
<tr>
	<td bgcolor="#ffffff"></td>
	<td bgcolor="#ffffff">
		<input type="submit" value="Submit">
		<input type="reset" value="Cancel">
	</td>
</tr>		
</table>
</form>
<br>
<hr size="1" width="100%">
</body>
</html>
