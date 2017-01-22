<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.UpdateLogsManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<%
	int idJskUpd=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idResumeUpd=new ResumeManager().getIdResumeLastUpDate(idJskUpd);
	Resume safUpd=null;
	int remindUpdate=0;
	String idSession=Encoder.getEncode(session.getId());
	UpdateLogsManager ulm=new UpdateLogsManager();
	if(idResumeUpd>-1)
	{
		int count=ulm.countUpdate(idJskUpd);
		if(count<5)
		{
			int datediff=ulm.getDiffDateLastRemind(idJskUpd);
			if(datediff!=0)
			{
				remindUpdate=1;//alert
				ulm.add(idJskUpd);
			}
		}
		else
		{
			int datediff=ulm.getDiffDateLastRemind(idJskUpd);
			if(datediff>=180)
			{
				remindUpdate=1;//alert
				ulm.add(idJskUpd);
			}
		}
	}
	if(remindUpdate==1)
	{
		safUpd=new ResumeManager().get(idJskUpd, 0);
		if(safUpd!=null)
		{
			request.setAttribute("safUpd",safUpd);
		}
	}
	request.setAttribute("safUpd",safUpd);
	request.setAttribute("remindUpdate",remindUpdate);
	request.setAttribute("idResumeUpd",idResumeUpd);
 %>
<script>
	var remindUpdate=<c:out value='${remindUpdate}'/>;
	$( document ).ready( function() 
	{
		if(remindUpdate==1)
		{
			$('#myModal').modal('show');
		}
	 	$('#updateMe').click(function(){
	 		if(remindUpdate==1)
	 		{
	 			window.location.href = "/SRIX?view=resumeInfo&idResume=<c:out value='${idResumeUpd}'/>&jSession=<c:out value='${idSession}'/>";
	 			return false;
	 		}
	 	});
	 	$('#remindMe').click(function(){
	 		if(remindUpdate==1)
	 		{
	 			$('#myModal').modal('hide');
	 			return false;
	 			
	 		}
	 	});
	 	
	});
</script>
<script src="/js/html5shiv.js"></script>
<script src="/js/respond.min.js"></script>
<style type="text/css"> 
	.modal
	{
		font-family: 'SukhumvitSet-Text'; 
		font-size: 16pt;
	} 
	/*large - table*/
	@media (min-width: 752px) 
	{
		.update{
			color: #10599F;
			padding:auto !important;
			margin:auto !important;
			text-align: center !important;
			text-decoration: none!important;
		}
		.remind {
			color: #58585B;
			padding:auto !important;
			margin:auto !important;
			text-align: center !important;
			text-decoration: none!important;
		}
		.img-responsive { max-width:50%;  height: auto;}
		.modal-dialog {
	      width: 460px;
	      padding:auto  !important;
	      margin:auto !important;
	      margin:0;
	      text-decoration: none!important;
	    }
	    .modal-content {
	      height: 100%;
	      border-radius: 2;
	      padding:auto !important;
	      overflow:auto; 
	      text-decoration: none!important;
	   	}
	}
	/*Medium - table*/
	@media (min-width: 651px) and (max-width: 751px) 
	{
		body
		{
			font-size: 14pt;
		}
		.update{
			color: #10599F;
			padding:auto !important;
			margin:auto !important;
			text-align: center !important;
			text-decoration: none!important;
		}
		.remind{
			color: #58585B;
			padding:auto !important;
			margin:auto !important;
			text-align: center !important;
			text-decoration: none!important;
		}
		.img-responsive {  max-width:50%; height: auto;}
	}
	/*small - table*/
	@media (max-width: 650px)
	{
		body
		{
			font-size: 10pt;
		}
		.update{
			color: #10599F;
			padding:auto !important;
			margin:auto !important;
			text-align: center !important;
			text-decoration: none!important;
		}
		.remind{
			color: #58585B;
			padding:auto !important;
			margin:auto !important;
			text-align: center !important;
			text-decoration: none !important;
		} 
		.img-responsive { max-width:50%; height: auto;}
	}
</style>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="simplemodal-overlay" style="opacity: 0.5; height: 3821px; width: 3371px; position: fixed; left: 0px; top: 0px; z-index: 1001;"></div>
	<div class="modal-dialog" style="z-index: 1002;">
	    <div class="modal-content" style="background-color: #F0F0F0;">
	      <div class="modal-body" >
	      		<div class="row">
					<div class="col-xs-12">
						<img src="../images/JTG_SR.png" class="img-responsive center-block" />
					</div>
				</div> <br/>
				<div class="row">
					<div class="col-xs-12 col-lg-12">
						<c:choose>
							<c:when test="${sessionScope.SESSION_LANGUAGE eq 'th' and safUpd.idLanguage eq 38}">
								<fmt:message key="ALERT_WELCOME"/><c:out value="${safUpd.firstNameThai}"/>
							</c:when>
							<c:otherwise>
								<fmt:message key="ALERT_WELCOME"/><c:out value="${safUpd.firstName}"/>
							</c:otherwise>
						</c:choose>
					</div>
				</div> 
				<div class="row">
					<div class="col-xs-12 col-lg-12"><fmt:message key="ALERT_CONTENT1"/></div>
				</div> 
				<div class="row" style="height:5px;"></div>
				<div class="row">
					<div class="col-xs-12 col-lg-12"><fmt:message key="ALERT_CONTENT2"/></div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-lg-12"><fmt:message key="ALERT_CONTENT3"/></div>
				</div>
	      </div>
	      <div class="modal-footer" style="background-color: #F0F0F0;">
	        	<div class="row">
					<div class="col-xs-6" style="border-right: 1px solid #e5e5e5;"><a href="javascript:void(0);"  class="update" id="updateMe"><fmt:message key="ALERT_UPDATE"/></a></div>
					<div class="col-xs-6">&nbsp;<a href="javascript:void(0);"  class="remind"  id="remindMe"><fmt:message key="ALERT_REMIND"/></a></div>
				</div> 
	      </div>
	    </div>
	</div>
</div>
