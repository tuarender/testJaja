<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%@page import="com.topgun.util.Util"%>
<%@ page import="java.util.List"%>
<%@page import= "com.topgun.resume.Forgot" %>
<%@page import= "com.topgun.resume.ForgotManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<%
	int idJsk= Util.getInt(request.getAttribute("idJSK"));
	String telephone= Util.getStr(request.getAttribute("mobile"));
	int sizeNo=0;
	int totalSMS=0;
	if(idJsk != -1)
	{
		String username= Util.getStr(ForgotManager.getUsernameByIdJsk(idJsk));
		request.setAttribute("username", username);
		String mobile= Util.getStr(ForgotManager.getMobileByIdJsk(idJsk));
		sizeNo= mobile.length();
		if(sizeNo==10)
		{
			String newMobile= "*******"+ mobile.substring(7, 10);
			request.setAttribute("mobile", newMobile);
		}
		totalSMS= Util.getInt(ForgotManager.tatalSMS(mobile));
	}    
	else
	{
		sizeNo= telephone.length();
		if(sizeNo ==10)
		{
			String mobile= "*******"+ telephone.substring(7, 10);
			request.setAttribute("mobile", mobile);
		}
		totalSMS= Util.getInt(ForgotManager.tatalSMS(telephone));
	}
	request.setAttribute("idJsk", idJsk);
	request.setAttribute("size", sizeNo);
	request.setAttribute("sms", totalSMS);
	request.setAttribute("telephone", telephone);
%>
<div>
	<h3 class="section_header" align="center">
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">ตั้งรหัสผ่านใหม่</c:when>
			<c:otherwise>Reset Password</c:otherwise>
		</c:choose>
	</h3>
		
</div>
<form id="forgotFrm" method="post" action="/ForgotPasswordServ?step=1">
	<input type="hidden" name="locale" value="<c:out value="${sessionScope.SESSION_LOCALE}"/>">
	<div align="center" class="caption">
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				<td class="caption">กรุณาเลือกช่องทางที่คุณต้องการตั้งรหัสผ่านใหม่</td>
			</c:when>
			<c:otherwise>
				<td class="caption">Please select your new password setting.</td>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="row col-lg-3 col-md-3 col-sm-3 col-xs-3"></div>
		<div class="row col-lg-9 col-md-9 col-sm-9 col-xs-9">
			<c:if test="${idJsk ne '-1'}">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH' and size==10 and sms<=5}">
						<input type="radio" name="result" value="1_<c:out value="${idJsk}"/>" checked>
						<font class="caption">ส่งรหัส OTP ไปที่เบอร์โทรศัพท์มือถือ <b class="suggestion"><c:out value="${mobile}"/></b> เพื่อให้คุณกลับมาตั้งรหัสผ่านใหม่ได้ทันทีที่นี่ <br /></font>
					</c:when>
					<c:otherwise>
						<c:if test="${size==10 and sms<3}">
							<input type="radio" name="result" value="1_<c:out value="${idJsk}"/>">
							<font class="caption">Send OTP code to mobile number <b class="suggestion"><c:out value="${mobile}"/></b>. After receiving code, you can reset your new password here. </font> <br />
						</c:if>
					</c:otherwise>
				</c:choose><br />
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						<input type="radio" name="result" value="2_<c:out value="${idJsk}"/>" checked>
						<font class="caption">ส่งลิงค์การตั้งรหัสผ่านไปที่อีเมล <b class="suggestion"><c:out value="${username}"/></b> เพื่อให้คุณตั้งรหัสผ่านใหม่ </font><br />
					</c:when>
					<c:otherwise>
						<input type="radio" name="result" value="2_<c:out value="${idJsk}"/>">
						<font class="caption">Send reset password link to <b class="suggestion"><c:out value="${username}"/></b> and you can reset your new password here </font><br />
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${mobile ne '' and idJsk eq -1}">
			<%
				Forgot otp1 = new Forgot();
				List<Forgot> reports=new ForgotManager().getAllAccount(telephone);
				if(reports != null)
				{
					pageContext.setAttribute("balance", reports.size());
				}
				else
				{
					pageContext.setAttribute("balance", 0);
				}
				out.print("balance :"+reports.size());
				int i=0;
			%>
				<c:if test="${balance ne '0'}">
					<c:if test="${balance ==1}">
						<c:choose>
							<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH' and size==10 and sms<=5}">
								<input type="radio" name="result" value="1_<%=Util.getStr(reports.get(0).getId_Jsk())%>" checked>
								<font class="caption">ส่งรหัส OTP ไปที่เบอร์โทรศัพท์มือถือ <b class="suggestion"><c:out value="${mobile}"/></b> เพื่อให้คุณกลับมาตั้งรหัสผ่านใหม่ได้ทันทีที่นี่</font><br />
							</c:when>
							<c:otherwise>
								<c:if test="${size==10 and sms<3}">
									<input type="radio" name="result" value="1_<%=Util.getStr(reports.get(0).getId_Jsk())%>" checked>
									<font class="caption">Send OTP code to mobile number <b class="suggestion"><c:out value="${mobile}"/></b>. After receiving code, you can reset your new password here. </font><br />
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:forEach var="i" begin="1" end="${balance}">
						<% 
							pageContext.setAttribute("idJSK", reports.get(i).getId_Jsk());
							pageContext.setAttribute("username",reports.get(i).getUsername());
						%>
						<tr>
							<c:choose>
								<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
									<input type="radio" name="result" value="2_<%=reports.get(i).getId_Jsk()%>">
									<font class="caption">ส่งลิงค์การตั้งรหัสผ่านไปที่อีเมล <b class="suggestion"><c:out value=" ${username}"/></b> เพื่อให้คุณตั้งรหัสผ่านใหม่</font><br />
								</c:when>
								<c:otherwise>
									<input type="radio" name="result" value="2_<%=reports.get(i).getId_Jsk()%>">
									<font class="caption">Send reset password link to <b class="suggestion"><c:out value=" ${username}"/></b>and you can reset your new password here </font><br />
								</c:otherwise>
							</c:choose>
						<%i++;%>
					</c:forEach>
				</c:if>
		</c:if>
		</div>
	</div>
	<div align="center">
		<button type="submit" class="btn btn-lg btn-success"><fmt:message key="CANCEL_JM_SUBMIT" /></button>
	</div>
</form>
