<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.*"%>
<%@page import="com.topgun.services.*"%>
<%	
	String param = Util.getStr(request.getParameter("param")); 
	String[] idDecode=null;
	String[] paramDecode =null;
	int idJsk=0;
	if(!param.equals("") && param!=null && !param.equals("null"))
	{
		paramDecode = Base64Coder.decodeString(param).split("#");
		idDecode=null;
		if(paramDecode!=null){
			if(paramDecode.length>0)
			{
				idDecode = paramDecode[0].split("@");
			}
		}
		if(idDecode!=null)
		{
			if(idDecode.length>0)
			{
				 idJsk = Integer.parseInt(idDecode[1]);
			}
		}
	}
	Jobseeker jsk=new JobseekerManager().get(idJsk);
	request.setAttribute("jsk",jsk);
	request.setAttribute("encode",Encryption.getEncoding(0,0,idJsk,0));
	request.setAttribute("key",Encryption.getKey(0,0,idJsk,0));
%>
	<script type="text/javascript" src="http://job.jobinthailand.com/bt/bt.js"></script>
	<style type="text/css">
	<!--
	.style6 {color: #666666}
	.style18 {color: #999999; font-weight: bold; }
	.style20 {font-family: "MS Sans Serif"; font-size: 12; }
	.style28 {font-size: 14px; }
	div.errorContainerCancel 
	{
		color: #D63301;
	}
	-->
	</style>
</head>
<script type="text/javascript">
	$(document).ready(function()
	{
		<c:if test="${jsk.mailOtherStatus == 'TRUE'}">
			$('input[name="newschk"][value="1"]').prop('checked',true);
		</c:if>	
		<c:if test="${jsk.mailOtherStatus == 'FALSE'}">
			$('input[name="newschk"][value="0"]').prop('checked',true);
		</c:if>	
		<c:if test="${jsk.mailOtherStatus == ''}">
			$('input[name="newschk"]').prop('checked',false);
		</c:if>	
		var container = $('div.errorContainerCancel');
		$('#cancelFrm').validate(
		{
			errorContainerCancel: container,
			errorLabelContainer: $("#cancelFrm div.errorContainerCancel"),
			submitHandler:function(form)
			{
				form.submit();
		 		return false ;
			}
		});
	});
</script>
<br>
<div class="row">
	<div class="style20 style28 alignCenter"><strong><u>รับ / ยกเลิก E-mail</u></strong></div>
	<div class="style20 style18 alignCenter"><strong><u>Receiving  / Stop receiving E-mail</u></strong></div><br>
	<div class="style20 style28">ยินดีต้อนรับเข้าสู่ระบบตั้งค่าสถานะอีเมลจาก Jobtopgun กรุณาเลือกประเภทข่าวสารที่คุณต้องการรับข้อมูลหรือยกเลิกการรับข้อมูลตามรายชื่อด้านล่างค่ะ</div>    <br>       
	<div class="style20 style6">Welcome to Jobtopgun Email preferences setting system. Please check any of the boxes below to subscribe or unsubscribe to our electronic communication. </div>   
	<br>
	<form id="cancelFrm" method="post"   class="form-horizontal" action="/JobMatchServ">
	<input type="hidden" name="cancel" value="true">
	<input type="hidden" name="page" value="cancel">
	<input type="hidden" name="param" value="<%=param%>">
	  <table class="table table-bordered table-fixed">
	    <thead>
	      <tr>
	        <th rowspan="2" class="alignCenter" style="width:60%;vertical-align: middle;">Email</th>
	        <th colspan="2" class="alignCenter">ตั้งค่า<br> Preferences</th>
	      </tr>
	      <tr>
	        <th  class="alignCenter"style="width:20%;" >รับ<br>Subscribe</th>
	        <th  class="alignCenter" >ยกเลิก<br>Unsubscribe</th>
	      </tr>
	    </thead>
	    <tbody>
	      <tr>
	        <td>
	        	<div class="style28"><strong>Job Match Standard ที่ส่งให้คุณทุกวัน</strong></div>
	        	<br />                                
				<div class="style28">บริการส่งตำแหน่งที่ตรงกับคุณสมบัติที่มีให้คุณผ่านทาง E-mail</div>   
				<div class="style6">Serve you with positions must suitable to your qualification.</div> 	
				<br />   	
	        </td>
	        <td colspan="2">
				<div class="style20 style28 alignCenter">
					รับ/ยกเลิกอีเมล Job Match	<br/>
					<a href="http://www.superresume.com/SRIX?view=cancelService&locale=th_TH&cancelService=jobmatch&encode=<c:out value='${encode}'/>&key=<c:out value='${key}'/>">คลิกที่นี่</a>
				</div><br><br>
				<div class="style20 style28 alignCenter">
					Unsubscribe/Subscribe Job Match	<br/>
					<a href="http://www.superresume.com/SRIX?view=cancelService&locale=en_TH&cancelService=jobmatch&encode=<c:out value='${encode}'/>&key=<c:out value='${key}'/>">Click here</a>
				</div>
			</td>
	      </tr>
	      <tr>
	        <td>
	        	<div class="style28"><strong>Job Update</strong></div>
	        	<br />                                
				<div class="style28">บริการส่งตำแหน่งที่ตรงกับเงื่อนไขของคุณ</div>   
				<div class="style6">Serve you with jobs matching your interest.</div> 	
				<br />   	
	        </td>
	        <td colspan="2">
				<div class="style20 style28 alignCenter">
					รับ/ยกเลิกอีเมล Job Update<br/>
					<a href="http://www.superresume.com/SRIX?view=cancelService&locale=th_TH&cancelService=jobupdate&encode=<c:out value='${encode}'/>&key=<c:out value='${key}'/>">คลิกที่นี่</a>
				</div><br><br>
				<div class="style20 style28 alignCenter">
					Unsubscribe/Subscribe Job Update	<br/>
					<a href="http://www.superresume.com/SRIX?view=cancelService&locale=en_TH&cancelService=jobupdate&encode=<c:out value='${encode}'/>&key=<c:out value='${key}'/>">Click here</a>
				</div>
			</td>
	      </tr>
	      <tr>
	        <td> 
	        	<div class="style28"><strong>Jobtopgun’s Newsletter</strong></div>
	        	<br />                                
				<div class="style28">ข่าวสาร กิจกรรมร่วมสนุกและสิทธิประโยชน์ต่างๆ จาก Jobtopgun</div>   
				<div class="style6">Updates on campaigns and privileges from Jobtopgun</div> 	
				<br />   	
	        </td>
	        <td class="alignCenter" style="vertical-align: middle;">
				<input class="required" type="radio" name="newschk" title="กรุณาตั้งค่า Jobtopgun’s Newsletter " id="newschkyes" value="1" />	
			</td>
			<td  class="alignCenter" style="vertical-align: middle;">
				<input class="required" type="radio" name="newschk" title="Please preferences Jobtopgun’s Newsletter " id="newschkno" value="0"/>
			</td>
	      </tr>
	      <tr>
			<td  colspan="3">
				<div class="row errorContainerCancel alignCenter ">
					<ul style="list-style-type:none;"></ul><br><br>
				</div>
				<div class="row alignCenter">
					<button type="submit" class="btn btn-success ">Submit</button>
				</div>
 			</td>
	      </tr>
	    </tbody>
	  </table>
	  </form>
</div>
<br>
<br><br><br><br><br>
