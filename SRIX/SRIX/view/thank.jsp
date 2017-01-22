<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	String pageChk = request.getParameter("pageChk")!=null?request.getParameter("pageChk"):""; 
%>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.style1 {
	font-family: Tahoma;
	font-size: 12px;
}
.style6 {color: #666666}
.style5 {font-family: Tahoma; font-size: 14px; }
.style16 {
	font-family: "MS Sans Serif";
	font-size: 14px;
}
.style17 {font-family: "MS Sans Serif"}
-->
</style>
<table style="border-collapse:collapse; width:100%;">
	<tr>
		<td class="content" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td><div align="center">
			      <table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td background="images/bg.gif"><div align="center">
			            <table border="0" align="center" cellpadding="0" cellspacing="0">
			              <tr>
			                <td><div align="center">
			                  <p align="center"><strong><span class="style5"><span class="style17">ระบบได้ดำเนินการเรียบร้อยแล้วค่ะ</span></span></strong><span class="style16"><br />
			                          <strong><span class="style6">Your status has been updated.</span> </strong><br />
			                        </span></p>
			                 <%if(pageChk.equals("jmju")){ %> <p align="left" class="style16">หากคุณต้องการรับบริการ <strong> Job  Update</strong> ก่อนระยะเวลาที่คุณกำหนดไว้ คุณสามารถทำได้โดย<br />
			                        <span class="style6">If you would like to resubscribe to receive  Job Update earlier than the period that you have specified, you can.</span></p>
			                  <p align="left" class="style16">• Login เข้าสู่ระบบที่ www.jobtopgun.com  กรอก Username และ Password ของคุณ <a href="JM01.jsp" target="_blank">ตัวอย่าง</a> <br />
			                      <span class="style6">Log in to <a href="http://www.jobtopgun.com/">www.jobtopgun.com</a> by  entering your username and password. <a href="JM01.jsp" target="_blank">Example</a>  <br />
			                      <br /></span><br />
			                    • เมื่อเข้าสู่หน้าแรก ให้ Click  เข้าไปที่ Receive / Stop receiving e-mail <a href="JMU02.html" target="_blank">ตัวอย่าง</a><br />
			                      <span class="style6">In the main page, click Receive / Stop receiving e-mail. <a href="JMU02_en.html" target="_blank">Example</a></span> </p>
			                  <p align="left" class="style16"><br />
			                    • หน้าจอจะปรากฎตามภาพด้านล่าง  ให้คุณเปลี่ยนสถานะ  Job Update เป็นรับ เพื่อรับบริการ  Job Update อีกครั้ง และกดปุ่ม Submit ค่ะ  <a href="JMU03.html" target="_blank">ตัวอย่าง</a><br />
			                    <span class="style6">Change the Job Update status to “Subscribe” to start receiving Job Update again, then click submit.<a href="JMU03.html" target="_blank"> Example</a></span></p>
			                  <%} %>
			                  <p align="left" class="style1">&nbsp;</p>
			                  <p align="left" class="style1"><br />
			                  </p>
			                 
			                </div>
			                    </td>
			              </tr>
			            </table>
			          </div></td>
			        </tr>
			      </table>
			    </div></td>
			  </tr>
			</table>
		</td>
		<td class="faq"></td>
	</tr>
</table>
