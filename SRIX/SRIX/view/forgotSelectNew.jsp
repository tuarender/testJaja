<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%@page import="com.topgun.util.Util"%>
<%@ page import="java.util.List"%>
<%@page import= "com.topgun.resume.Forgot" %>
<%@page import= "com.topgun.resume.ForgotManager" %>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="java.util.ArrayList"%>
<%	
	String primaryPhone = Util.getStr(request.getAttribute("primaryPhone"),"");	
	List<Forgot> reports = new ForgotManager().getAllAccount(primaryPhone);
%>
<c:choose>
	<c:when test="${(not empty email) || (not empty mobile)}">
		<div>
			<h3 class="section_header" align="center">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">ตั้งรหัสผ่านใหม่<br></c:when>
					<c:otherwise>Reset Password<br></c:otherwise>
				</c:choose>
			</h3>
		</div>
		<c:choose>
			<c:when test="${not empty resume}">
				<fmt:setLocale value="${resume.locale}"/>
			</c:when>
			<c:otherwise>
				<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
			</c:otherwise>
		</c:choose>
		
		<form id="forgotNewFrm" method="post" action="/ForgotPasswordNewServ?step=1">
			<input type="hidden" name="service" value="chooseMethodSendEmailAndOTP" />	
			<div align="center" class="caption">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						<td class="caption">กรุณาเลือกช่องทางที่คุณต้องการตั้งรหัสผ่านใหม่<br></td>
					</c:when>
					<c:otherwise>
						<td class="caption">Please select your new password setting.<br></td>
					</c:otherwise>
				</c:choose>
				<br>
			</div>
			
			<c:if test="${flag == 1}">	
					<c:if test="${not empty email}">
						<c:forEach items="${email}" var="jsk">
							<c:choose>
								<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
									<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>" checked>
									<font class="caption">ส่งลิงค์การตั้งรหัสผ่านไปที่อีเมล<b class="suggestion"><c:out value="${jsk.username}"/></b><br/>เพื่อให้คุณตั้งรหัสผ่านใหม่<br/></font>
								</c:when>
								<c:otherwise>
									<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>">
									<font class="caption">Send reset password link to <b class="suggestion"><c:out value="${jsk.username}"/></b> and you can reset your new password here </font><br />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
			</c:if>
			
			<c:if test="${flag == 2}">
					<c:if test="${not empty email}">
						<c:forEach items="${email}" var="jsk">
							<c:choose>
								<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
									<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>" checked>
									<font class="caption">ส่งลิงค์การตั้งรหัสผ่านไปที่อีเมล<b class="suggestion"><c:out value="${jsk.username}"/></b><br/>เพื่อให้คุณตั้งรหัสผ่านใหม่<br/></font>
								</c:when>
								<c:otherwise>
									<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>">
									<font class="caption">Send reset password link to <b class="suggestion"><c:out value="${jsk.username}"/></b> and you can reset your new password here </font><br />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
			</c:if>
			
			<c:if test="${flag eq 3}">
				<c:forEach items="${email}" var="jsk">
						<c:choose>
							<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
								<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>" checked>
								<font class="caption">ส่งลิงค์การตั้งรหัสผ่านไปที่อีเมล<b class="suggestion"><c:out value="${jsk.username}"/></b><br/>เพื่อให้คุณตั้งรหัสผ่านใหม่<br/></font>
							</c:when>
							<c:otherwise>
								<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>">
								<font class="caption">Send reset password link to <b class="suggestion"><c:out value="${jsk.username}"/></b> and you can reset your new password here </font><br />
							</c:otherwise>
						</c:choose>
				</c:forEach>	
				<br>
				<c:if test="${not empty reports}">
					<c:forEach items="${mobile}">
							<c:choose>
								<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
									<input type="radio" name="chooseMethod" value="2_<%=Util.getStr(reports.get(0).getId_Jsk())%>">
									<font class="caption">ส่งรหัส OTP ไปที่เบอร์โทรศัพท์มือถือ<b class="suggestion"><c:out value="${mobile}"/></b><br/>เพื่อให้คุณกลับมาตั้งรหัสผ่านใหม่ได้ทันทีที่นี่<br/></font>
								</c:when>
								<c:otherwise>
									<input type="radio" name="chooseMethod" value="2_<%=Util.getStr(reports.get(0).getId_Jsk())%>">
									<font class="caption">Send OTP code to mobile number <b class="suggestion"><c:out value="${mobile}"/></b>. After receiving code, you can reset your new password here.</font><br />
								</c:otherwise>
							</c:choose>
					</c:forEach>
				</c:if>
			</c:if>
			
			<c:if test="${flag eq 4}">
				<c:forEach items="${email}" var="jsk">
						<c:choose>
							<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
								<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>" checked>
								<font class="caption">ส่งลิงค์การตั้งรหัสผ่านไปที่อีเมล<b class="suggestion"><c:out value="${jsk.username}"/></b><br/>เพื่อให้คุณตั้งรหัสผ่านใหม่<br/></font>
							</c:when>
							<c:otherwise>
								<input type="radio" name="chooseMethod" value="<c:out value='${jsk.idJsk}'/>">
								<font class="caption">Send reset password link to <b class="suggestion"><c:out value="${jsk.username}"/></b> and you can reset your new password here </font><br />
							</c:otherwise>
						</c:choose>
				</c:forEach>	
				<br>
				<c:if test="${not empty reports}">
					<c:forEach items="${mobile}">
							<c:choose>
								<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
									<input type="radio" name="chooseMethod" value="2_<%=Util.getStr(reports.get(0).getId_Jsk())%>">
									<font class="caption">ส่งรหัส OTP ไปที่เบอร์โทรศัพท์มือถือ<b class="suggestion"><c:out value="${mobile}"/></b><br/>เพื่อให้คุณกลับมาตั้งรหัสผ่านใหม่ได้ทันทีที่นี่<br/></font>
								</c:when>
								<c:otherwise>
									<input type="radio" name="chooseMethod" value="2_<%=Util.getStr(reports.get(0).getId_Jsk())%>">
									<font class="caption">Send OTP code to mobile number <b class="suggestion"><c:out value="${mobile}"/></b>. After receiving code, you can reset your new password here.</font><br />
								</c:otherwise>
							</c:choose>
					</c:forEach>
				</c:if>
			</c:if>
			<br>
			<div align="center">
				<button type="submit" class="btn btn-lg btn-success"><fmt:message key="CANCEL_JM_SUBMIT" /></button>
			</div>
		</form>
	</c:when>
	<c:otherwise>
		<br>
		<div class="text-center">
			<span class="caption">
				<c:choose>
					<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
						<c:set var="input">
							<c:if test="${emailInput ne ''}">
								<c:out value="${emailInput}"/>
							</c:if>
							<c:if test="${(emailInput ne '') && (primaryPhoneInput ne '')}"> หรือ </c:if>
							<c:if test="${primaryPhoneInput ne ''}">
								<c:out value="${primaryPhoneInput}"/>
							</c:if>
						</c:set>
						ขออภัย ไม่พบ <c:out value="${input}"/> ในระบบ กรุณาลองอีกครั้ง
					</c:when>
					<c:otherwise>
						<c:set var="input">
							<c:if test="${emailInput ne ''}">
								<c:out value="${emailInput}"/>
							</c:if>
							<c:if test="${(emailInput ne '') && (primaryPhoneInput ne '')}"> or </c:if>
							<c:if test="${primaryPhoneInput ne ''}">
								<c:out value="${primaryPhoneInput}"/>
							</c:if>
						</c:set>
						Sorry, we could not find <c:out value="${input}"/> in our records. Please try again.
					</c:otherwise>
				</c:choose>
			</span>
			<br>
		</div>
	</c:otherwise>
</c:choose>
