<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/pd4ml" prefix="pd4ml" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<pd4ml:transform screenWidth="668"
	fileName="superresume.pdf"
	pageFormat="A4"
	inline="true"
	pageOrientation="portrait"
	encoding="default"
	pageInsets="50,50,50,50,points">
<%-- <pd4ml:usettf from="file:/oracle/bea/user_projects/domains/mydomain/applications/SRIX/pd4ml/fonts"/> --%>
<pd4ml:usettf from="http://superresume.com/pd4ml/fonts"/>
	<c:import url="/view/viewResume.jsp?pdf=1" charEncoding="UTF-8"/>
</pd4ml:transform>