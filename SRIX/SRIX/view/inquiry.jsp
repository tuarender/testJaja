<%@page import="com.topgun.resume.AdditionalManager"%>
<%@page import="com.topgun.resume.Additional"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.shris.masterdata.Gsb"%>
<%@page import="com.topgun.shris.masterdata.PollAnswer"%>
<%@page import="java.util.List"%>
<%@page import="com.topgun.shris.masterdata.PollQuestion"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	int idLanguage = Util.getInteger(session.getAttribute("SESSION_ID_LANGUAGE"));
	int idCountry = Util.getInteger(session.getAttribute("SESSION_ID_COUNTRY"));
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int idEmp=Util.getInt(request.getParameter("idEmp"));
	int idPos=Util.getInt(request.getParameter("idPos"));
	int idQuestion = MasterDataManager.getIdQuestionInquiry(idEmp);
	PollQuestion questionInq =MasterDataManager.getQuestion(idQuestion, idLanguage);
	List<PollAnswer> answerInqs=MasterDataManager.getAnswerInquiry(idEmp, idQuestion,idLanguage);
	List<Gsb> gsbDatas=Gsb.getAllContent();
	
	String companyName="";
	String positionName="";
	if(idEmp!=-1 && idPos!=-1 )
	{
		Employer emp=new EmployerManager().get(idEmp);
		if(emp!=null)
		{
			companyName=emp.getNameEng();
			if(idLanguage==38)
			{
				companyName=emp.getNameThai();
			}
			Position pos=new PositionManager().getPosition(emp.getId(), idPos);
			if(pos!=null)
			{
				positionName=pos.getPositionName();
			}
		}
		if(companyName!="" && positionName!="")
		{
			request.setAttribute("companyName",companyName);
			request.setAttribute("positionName",positionName);
		}
	}
	
	Resume saf=new ResumeManager().get(idJsk, 0);
	if(saf!=null)
	{
		if(Util.getStr(saf.getCompleteStatus()).equals("TRUE"))
		{
			List<Resume> resumes=new ResumeManager().getAll(idJsk,false,true);
			List<Attachment> ownResumes = new AttachmentManager().getAllByFileType(idJsk,"RESUME");
			List<Attachment> docs = new AttachmentManager().getAllByFileType(idJsk,"DOCUMENT");
			
			request.setAttribute("saf",saf);
			request.setAttribute("resumes",resumes);
			request.setAttribute("docs",docs);
			request.setAttribute("ownResumes",ownResumes);
		}
	}
	request.setAttribute("idEmp", idEmp);
	request.setAttribute("idPos", idPos);
	request.setAttribute("questionInq", questionInq);		
	request.setAttribute("answerInqs", answerInqs);
	request.setAttribute("gsbDatas", gsbDatas);
	request.setAttribute("ansSize", answerInqs.size());
 %>
 <script>
	$(document).ready(function()
	{ 
		var container = $('div.errorContainer');
		$('#inquiryFrm').validate(
		{
			errorContainer: container,
			errorLabelContainer: $("ol", container),
			wrapper: 'li',
			focusInvalid: false,
  			invalidHandler: function(form, validator) 
  			{
  				$('html, body').animate({scrollTop: '0px'}, 300);      
  			},
  			highlight: function(element) 
  			{
            	$(element).closest('.form-group').addClass('has-error');
        	},
        	unhighlight: function(element) 
        	{
            	$(element).closest('.form-group').removeClass('has-error');
        	},
        	errorPlacement: function(error, element) 
        	{
            	if(element.parent('.form-group').length) 
            	{
                	error.insertAfter(element.parent());
            	} 
            	else 
            	{
                	error.insertAfter(element);
            	}
        	},
  			rules:
  			{
  				email:
  				{
  					required:true,
  					email:true
  				}
			},			  
			messages: 
			{
				email:
				{
					required:'<fmt:message key="APPLY_REQUIRED_EMAIL_TO"/>',
					email:'<fmt:message key="USERNAME_EMAIL"/>'
				}
			},
			submitHandler:function(form)
			{
         		form.submit();
			}
		});
		$('input[name="certificate"]').click(function() 
		{
			var aName=$(this).attr('ref');
			if($(this).is(':checked'))
			{
				$('input[name="'+aName+'"]').addClass( "required" );
				$('input[name="'+aName+'"]').focus();
			}
			else//false
			{
				$('input[name="'+aName+'"]').val("");
				$('input[name="'+aName+'"]').removeClass();
			}
		});
		$('input[name="location"]').click(function() 
		{
			var id=$(this).attr("ref");
			$('p[name="locationDiv"]').hide();
			$( "#locationDiv_"+id).show();
			$('select[name="state"]').removeClass();
			$("#state_"+id).addClass( "required" );
		});
	});
</script>
<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
<h3 style="text-align:center;"><fmt:message key="SECTION_SENDING_PAGE"/></h3>
<p/>
<div class="form-group">
	<div class="errorContainer">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
</div>
<c:choose>
	<c:when test="${not empty saf}">
		<form  method="post" id="inquiryFrm" name="inquiryFrm" action="/ApplyServ" class="form-horizontal">
			<input type="hidden" name="cmd" id="cmd" value='inquiry'>
			<input type="hidden" name="idEmp" id="idEmp" value='<c:out value="${idEmp}"/>'>
			<input type="hidden" name="idPos" id="idPos" value='<c:out value="${idPos}"/>'>	
			<div class="form-group">
				<label for="company"><font color="red">*</font><fmt:message key="APPLY_COMPANY_NAME"/></label>
				<c:choose>
					<c:when test='${not empty companyName && idEmp!=-1 && idPos!=-1}'>
						<p>&nbsp;&nbsp;<c:out value="${companyName }"/></p>
					</c:when>
					<c:otherwise>
						<input type="text" value="" maxlength="120"  class="form-control required"  name="company"  title="<fmt:message key="APPLY_REQUIRED_COMNAME"/>">
					</c:otherwise>
				</c:choose>
			</div>
			<div class="form-group">
				<label for="company"><font color="red">*</font><fmt:message key="APPLY_POSITION"/></label>
				<c:choose>
					<c:when test='${not empty positionName && idEmp!=-1 && idPos!=-1}'>
						<p>&nbsp;&nbsp;<c:out value="${positionName}" escapeXml="false"/></p>
					</c:when>
					<c:otherwise>
						<input type="text" value="" maxlength="100" class="form-control required"   name="position" title="<fmt:message key="APPLY_REQUIRED_POSITION"/>"> 
					</c:otherwise>
				</c:choose>
			</div>
			<c:choose>
				<c:when test="${idEmp==-1 && idPos ==-1}">
					<div class="form-group">
						<label for="email"><font color="red">*</font><fmt:message key="APPLY_EMAIL_TO"/></label>
						<input type="text" value="" maxlength="100" class="form-control required"  name="email"  title="<fmt:message key="APPLY_REQUIRED_EMAIL_TO"/>">
					</div>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${not empty resumes}">
						<div class="form-group"><label><fmt:message key="SECTION_SENT_DOCUMENT"/></label></div>
						<p><img src="/images/sr.gif"></p>
						<c:forEach items="${resumes}" var="resume">
							<div class="form-group">
								<input type="radio" name="idResume" value="<c:out value="${resume.idResume}"/>" class="required" title="<fmt:message key="APPLY_REQUIRED_SUPERRESUME"/>">
								<c:out value="${resume.resumeName}"/>
							</div>
						</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="form-group"><label><fmt:message key="SECTION_SENT_DOCUMENT"/></label></div>
					<p><img src="/images/saf.gif"></p>
					<div class="form-group"><input type="radio" name="idResume" value="<c:out value="${saf.idResume}"/>" checked><label>SAF Cover</label></div>
				</c:otherwise>
			</c:choose>	
			<c:if test="${not empty ownResumes}">
				<p/>
				<div class="form-group"><label><fmt:message key="HOME_YOUR_OWN_RESUME"/></label></div>
				<c:forEach items="${ownResumes}" var="saf">
					<div class="form-group">
						<input type="radio" name="saf" value="<c:out value="${saf.idFile}"/>">
						<c:out value="${saf.fileTitle}"/>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${not empty docs}">
				<p/>
				<div class="form-group"><label><fmt:message key="HOME_OTHER_DOCUMENT"/></label></div>
				<c:forEach items="${docs}" var="doc">
					<div class="form-group">
						<input type="checkbox" name="doc" value="<c:out value="${doc.idFile}"/>">
						<c:out value="${doc.fileTitle}"/>
					</div>
				</c:forEach>
			</c:if>
			<p/>
			<h3 class="form-group" style="text-align:center;"><fmt:message key="INQUIRY_SUGGESTION"/></h3>
			<h5 class="form-group" style="text-align:justify;"><fmt:message key="INQUIRY_SUGGESTION_NOWADAYS"/></h5>
			<ol class="form-group">
				<li><fmt:message key="INQUIRY_SUGGESTION1"/></li>
				<li><fmt:message key="INQUIRY_SUGGESTION2"/></li>
				<li><fmt:message key="INQUIRY_SUGGESTION3"/></li>
			</ol>
			<c:if test="${ansSize>0}">
				<div class="form-group">
					<label for="answer"><fmt:message key="INQUIRY_MEDIA_EFFECTIVENESS"/></label>
					<label for="answer"><c:out value="${questionInq.question}"/></label>
				</div>
				<c:forEach items="${answerInqs}" var="answerInq">
					<div class="row"> 
						<div class="form-group"> 
							<input type="radio" value="<c:out value="${answerInq.idAnswer}"/>" name="answer" class="required" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_MEDIA_EFFECTIVENESS"/>" >&nbsp;&nbsp;
							<c:out value="${answerInq.answer}"/>
						</div>
					</div>
				</c:forEach>
			</c:if>
			<c:choose>
 				<c:when test="${idEmp eq 2775 && (idPos eq 37 || idPos eq 63 || idPos eq 101)}">
 					<label class="form-group">สำหรับผู้ที่สมัครตำแหน่ง <c:out value="${positionName}" escapeXml="false"/> ให้เลือกหน่วยงานที่ประสงค์จะไปปฏิบัติงานได้ 1 หน่วยงาน</label>
 					<%int i=0; %>
					<c:forEach items="${gsbDatas}" var="gsbData">
						<c:out value="${gsbData.row}"/>&nbsp;&nbsp;<input type="radio" value='<c:out value="${gsbData.row}"/>@<c:out value="${gsbData.content}"/>' ref='<c:out value="${gsbData.row}"/>'  name="location" class="required" title="<fmt:message key="INQUIRY_AGENCY"/>" >
						<b class="caption"><c:out value="${gsbData.content}"/></b>
						<p style="display:none" id="locationDiv_<c:out value="${gsbData.row}"/>" name="locationDiv">
						<%
							List<Gsb> aProvinces=Gsb.getProvince(gsbDatas.get(i).getRow());
							request.setAttribute("aProvinces", aProvinces);
							i++;
						%>
							<select name='state_<c:out value="${gsbData.row}"/>'  id="state_<c:out value="${gsbData.row}"/>" title="<fmt:message key="INQUIRY_STATE"/>">	
								<option value=""><fmt:message key="GLOBAL_SELECT"/></option>					
								<c:forEach var="aProvince" items="${aProvinces}">
									<option value="<c:out value="${aProvince.idProvince}"/>@<c:out value="${aProvince.province}"/>"><c:out value="${aProvince.province}"/></option>
								</c:forEach>
							</select>
						</p>
						<p class="answer"><c:out value="${gsbData.desc}" escapeXml="false"/><br><br></p>
					</c:forEach>
					<label class="form-group"><fmt:message key="INQUIRY_CERT_ASK"/></label>
					<div class="form-group"><input type="checkbox" name="certificate" value="CPA (Certified Public Accountant)" ref="capNo" >&nbsp;<fmt:message key="INQUIRY_CPA"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="capNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_CPA"/>"></div>
					<div class="form-group"><input type="checkbox" name="certificate" value="Single License" ref="slNo"  >&nbsp;<fmt:message key="INQUIRY_SING"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="slNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_SING"/>"></div>
					<div class="form-group"><input type="checkbox" name="certificate" value="CISA (Certified Investment and Securities Analyst)"  ref="cisaNo"  >&nbsp;<fmt:message key="INQUIRY_CISA"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="cisaNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_CISA"/>"></div>
					<div class="form-group"><input type="checkbox" name="certificate" value="CFP (Certified Financial Planning) " ref="cfpNo"  >&nbsp;<fmt:message key="INQUIRY_CFP"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="cfpNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_CFP"/>"></div>
					<div class="form-group"><input type="checkbox" name="certificate" value="SAP License "  ref="sapNo"  >&nbsp;<fmt:message key="INQUIRY_SAP"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="sapNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_SAP"/>"></div>
					<div class="form-group"><input type="checkbox" name="certificate" value="ตั๋วทนาย" ref="lawyerNo"  >&nbsp;<fmt:message key="INQUIRY_LAW"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="lawyerNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_LAW"/>"></div>
					<div class="form-group"><input type="checkbox" name="certificate" value="ใบประกอบวิชาชีพวิศวกรรม "  ref="engNo">&nbsp;<fmt:message key="INQUIRY_ENGINEAR"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="engNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_ENGINEAR"/>"></div>
					<div class="form-group"><input type="checkbox" name="certificate" value="CIA"  ref="ciaNo">&nbsp;<fmt:message key="INQUIRY_CIA"/></div>
					<div class="form-group"><input type="text" class="form-control"  value="" name="ciaNo" title="<fmt:message key="GLOBAL_REQUIRE"/> <fmt:message key="INQUIRY_CIA"/>"></div>
 				</c:when>
 			</c:choose>
			<c:if test="${idEmp eq 17113 }">
				<!-- ธ.ก.ส --->
				<%
					String idCard="";
				String phoneNumber="";
				Resume resume=new ResumeManager().get(idJsk, 0);
				if(resume!=null)
				{
					phoneNumber=resume.getPrimaryPhone();
				}
					Additional additional = new AdditionalManager().get(idJsk, 0);
					if(additional!=null)
					{
						idCard=additional.getIdCard();
					}
					request.setAttribute("idCard", idCard);
					request.setAttribute("phoneNumber", phoneNumber);
				%>
				<h5>ท่านอยู่ในขั้นตอนการส่งใบสมัครไปที่ ธ.ก.ส. ก่อนที่การส่งใบสมัครของท่านจะเสร็จสมบูรณ์ ท่านจะต้องตอบคำถามให้ครบทุกข้อ เสร็จแล้วให้ท่านกดปุ่มส่งเรซูเม่ด้านล่างอีกครั้ง และหากคุณพบข้อความ "Resume ของคุณได้ถูกส่งไปยังบริษัทเรียบร้อยแล้ว " จึงจะถือว่าเสร็จสิ้นสมบูรณ์</h5>
				<h5><font color="#FF0000"><u>กรุณาตอบคำถามก่อนส่งใบสมัครไปที่ ธ.ก.ส.ให้ครบถ้วน</u></font></h5>
				<label for="id_card">เลขที่บัตรประจำตัวประชาชน : </label>
				<input type="text"  name="id_card" id="id_card" maxlength="13" value='<c:out value="${idCard}"/>' class="form-control required" title="<fmt:message key="ADDITIONAL_ID_CARD_REQUIRED"/>" >
				<label for="id_card">เบอร์โทรศัพท์มือถือ :</label>
				<input type="text"  name="phone_number" maxlength="20" value="<c:out value="${phoneNumber}"/>" class="form-control required" title="<fmt:message key="FAMILY_TELEPHONE_REQUIRED"/>"> 
			</c:if>
			<div style="text-align:center"><button type="submit" class="btn btn-large btn-primary"><fmt:message key="GLOBAL_SAVE"/></button></div>
		</form>
	</c:when>
	<c:otherwise>
		<h3 style="text-align:center;font-color:red;" class="form-group">Resume not found</h3>
	</c:otherwise>
</c:choose>
		<br><br><br>
