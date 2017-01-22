<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>
<c:set var="curYear" value="2014"></c:set>
<form id="myForm" method="post" class="form-horizontal">
	<h1>Responsive Image ccc</h1>
	<img class="img-responsive" src="/images/logo.png"/>
	<br/><br/><br/>
	
	
	<h1>Text Field</h1>
	<div class="form-group">
   	<label for="username">Email address</label>
   	<input type="email" class="form-control" id="username" placeholder="Enter email">
 	</div>
	<div class="form-group">
   	<label for="password">Password</label>
   	<input type="password" class="form-control" id="password" placeholder="Password">
 	</div>
 	<br/><br/><br/>
 	
 	<h1>TextArea</h1>
	<div class="form-group">
   	<label for="description">Email address</label>
   		<textarea class="form-control" id="description" rows="3"></textarea>
 	</div>
 	<br/><br/><br/>


	<div class="form-group radio inline">
		<div class="row">
			<div style="padding:0px; margin:0px; width:40px;">
				<input type="radio" name="noticeStatus" id="noticeStatus1" value="1" class="radio">
			</div>
		
			<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="padding:1px;">
				<select name="startDay" id="startDay" class="form-control startNotice required" title="<fmt:message key="GLOBAL_START_DAY_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_DAY"/></option>
					<c:forEach var="i" begin="1" end="31">
						<c:choose>
							<c:when test="${startDay eq i}">
								<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5"  style="padding:1px;">
				<select  name="startMonth" id="startMonth" class="form-control startNotice required" title="<fmt:message key="GLOBAL_START_MONTH_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_MONTH"/></option>
					<c:forEach var="i" begin="1" end="12">
						<fmt:parseDate value='${i}' var='parsedDate' pattern='M' />
						<c:choose>
							<c:when test="${startMonth eq i}">
								<option value="<c:out value='${i}'/>" selected><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${i}'/>"><fmt:formatDate value="${parsedDate}" pattern="MMMM" /></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" style="padding:1px;">
				<select  name="startYear" id="startYear" class="form-control startNotice required" title="<fmt:message key="GLOBAL_START_YEAR_REQUIRED"/>">
					<option value=""><fmt:message key="GLOBAL_YEAR"/></option>
					<c:forEach var="i" begin="0" end="120">
						<c:choose>
							<c:when test="${resume.locale eq 'th_TH'}">
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${startYear eq (curYear-i+543)}">selected</c:if>>
									<c:out value="${curYear-i+543}"/>
								</option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value='${curYear-i}'/>" <c:if test="${startYear eq (curYear-i)}">selected</c:if>>
								<c:out value="${curYear-i}"/>
								</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<br/><br/><br/>
	
	
	<h1>File input</h1>	
	<div class="form-group">
    	<label for="exampleInputFile">File input</label>
    	<input type="file" id="exampleInputFile">
    	<p class="help-block">Example block-level help text here.</p>
  	</div>
  	<br/><br/><br/>
  	
  	
  	
  	
  	<h1>Check Box</h1>
  	<div class="checkbox">
    	<label><input type="checkbox"> Check me out</label>
  	</div>
  	<div class="checkbox">
    	<label><input type="checkbox"> Check me out</label>
  	</div>
  	<div class="checkbox">
    	<label><input type="checkbox"> Check me out</label>
  	</div>
  	<div class="checkbox">
    	<label><input type="checkbox"> Check me out</label>
  	</div>
  	<br/><br/><br/>
  	
  	
  	
  	
  	<h1>Select</h1>
	<select class="form-control">
  		<option>1</option>
  		<option>2</option>
  		<option>3</option>
  		<option>4</option>
  		<option>5</option>
	</select>
	<br/><br/><br/>
	<h1>images button</h1>
  	<p ><button type="button" class="btn btn-primary btn-sm" id='jfLink'><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="GLOBAL_ADD"/><fmt:message key="GLOBAL_JOBFIELD"/></button></p>
  	<br/><br/><br/>
  <h1>Button</h1>
  <button type="submit" class="btn btn-lg btn-success">Submit</button>	
  <button type="reset" class="btn btn-lg btn-success">Cancel</button>	
  <br/><br/><br/>
 
  <a href="test.jsp" class="button">Hello</a><br/>
  <a href="test.jsp" class="button_long">Hello World!!</a>
   <br/><br/><br/>
    <br/><br/><br/>
  
</form>