<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<table style="border-collapse:collapse; width:100%;margin:50px 0px">
<tr>
	<td class="content" align="center">
		<span class="section_header">Terms of Uses</span>
		<c:choose>
			<c:when test="${sessionScope.SESSION_LOCALE eq 'th_TH'}">
				<c:import url="/view/termTh.jsp" charEncoding="UTF-8"></c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/view/termEn.jsp" charEncoding="UTF-8"></c:import>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</table>
