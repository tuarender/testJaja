<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Language"%>
<%@page import="com.topgun.resume.WorkExperienceManager"%>
<%@page import="com.topgun.util.PropertiesManager"%>
<%@page import="com.topgun.resume.JobseekerManager"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.WorkExperience"%>
<%@page import="com.topgun.resume.EducationManager"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.util.EncryptionLink"%>
<%@page import="com.topgun.util.DecryptionLink"%>
<%@page import="com.topgun.resume.Education"%>
<%@page import="com.topgun.resume.Jobseeker"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.util.EncryptionLink"%>
<%@page import="com.topgun.util.Encoder"%>
<%@page import="com.topgun.util.DecryptionLink"%>
<%@page import="java.util.List"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<%
	String enc= Util.getStr(request.getParameter("enc"));
	List<Education>eduList=null;
	List<Education>eduList2=null;
	List<WorkExperience> workList2= null;
	List<WorkExperience> workList=null;
	PropertiesManager proMgr=new PropertiesManager();
	String idSession=Encoder.getEncode(session.getId());
	int idLanguage=38;
	Resume resume = new Resume();
	Resume resume2 = new Resume();
	String atms="";
	String ln="";
	String ct="";
	String ln2="";
	String ct2="";
	int idEmp = 0;
	int idJsk=0;
	int idPos= 0;
	int idResume1= 0;
	int idResume2= 0;
	int firstEdit = Util.getInt(request.getParameter("firstEdit"), 0);
	request.setAttribute("firstEdit", firstEdit);
	String myResumeLanguage = "";
	String languageAll = "";
	int languageIndex = 0;
	List<Language> languageRequireList = null ;
	String locale="";
	if(!enc.equals(""))
	{
	    DecryptionLink decLink = new DecryptionLink();
	    decLink = DecryptionLink.getDecode(enc);
	    idEmp = Util.getInt(decLink.getIdEmp());
		idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
		idPos= Util.getInt(decLink.getIdPos());
		idResume1= Util.getInt(decLink.getIdResume());
		idResume2= Util.getInt(decLink.getIdResume2());
		atms= Util.getStr(decLink.getAtms());
		locale=Util.getStr(request.getParameter("locale"));
		 
		 //personal
		 Jobseeker jobseeker=new JobseekerManager().get(idJsk);
		 if(jobseeker!=null)
		 {
		 	//------------- resume1 -------------
		 	resume=new ResumeManager().get(idJsk, idResume1);
		 	//experience
		 	if(resume != null)
		 	{
		 		ln=MasterDataManager.getLanguage(resume.getIdLanguage()).getAbbreviation();
				ct=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage()).getAbbreviation();
				eduList = new EducationManager().getAll(idJsk, idResume1);
		 		workList=new WorkExperienceManager().getAll(resume.getIdJsk(), resume.getIdResume());
		 		myResumeLanguage = new MasterDataManager().getLanguageName(resume.getIdLanguage(),resume.getIdLanguage());
		 		request.setAttribute("workList",workList);
		 		request.setAttribute("eduList", eduList);
		 		if(idEmp > 0)
				 {
				 	languageRequireList = new EmployerManager().getRequireLanguage(idEmp);
				 	String prefix = "";
				 	String postfix = "";
				 	String and = "";
				 	if(resume.getIdLanguage()==38)
				 	{
				 		prefix = "ภาษา";
				 		and = "และ";
				 	}else if(resume.getIdLanguage()==11)
				 	{
				 		postfix = "Version";
				 		and = "and";
				 	}
				 	if(resume.getIdLanguage()==38)
				 	{
				 		languageAll+="ภาษาอังกฤษ";
				 	}
				 	for(int i=0;i<languageRequireList.size();i++)
				 	{
				 		Language langaugeRequire =  languageRequireList.get(i);
				 		if(resume.getIdLanguage()==38)
					 	{
					 		languageAll+=" "+and+" ";
					 	}
				 		languageAll+=prefix+new MasterDataManager().getLanguageName(langaugeRequire.getId(),resume.getIdLanguage());
				 		if(i==(languageRequireList.size()-1))
				 		{
				 			languageAll+=" "+postfix+" ";
				 		}
				 	}
				 }
		 		//resume language
		 		idLanguage = resume.getIdLanguage();
		 		locale = resume.getLocale();
		 	}
		 	else
		 	{
		 		locale="th_TH";
		 	}
		 	
		 	request.setAttribute("resume",resume);
		 	request.setAttribute("ln",ln);
			request.setAttribute("ct",ct);
			
			//------------- resume2 -------------
			resume2=new ResumeManager().get(idJsk, idResume2);
		 	//experience
		 	if(resume2 != null)
		 	{
		 		ln2=MasterDataManager.getLanguage(resume2.getIdLanguage()).getAbbreviation();
				ct2=MasterDataManager.getCountry(resume2.getTemplateIdCountry(), resume2.getIdLanguage()).getAbbreviation();
				eduList2 = new EducationManager().getAll(idJsk, idResume2);
				
		 		workList2=new WorkExperienceManager().getAll(resume2.getIdJsk(), resume2.getIdResume());
		 		request.setAttribute("workList2",workList2);
		 		request.setAttribute("eduList2", eduList2);
		 	}
			request.setAttribute("resume2",resume2);
		 	request.setAttribute("ln2",ln2);
			request.setAttribute("ct2",ct2);
			request.setAttribute("enc", enc);
		 }
		 request.setAttribute("idResume1", idResume1);
		 request.setAttribute("idResume2", idResume2);
		 request.setAttribute("idSession", idSession);
		 request.setAttribute("languageRequireList",languageRequireList);
		 request.setAttribute("myResumeLanguage", myResumeLanguage);
		 request.setAttribute("languageAll",languageAll);
	 }
	 else
	 {
	 	RequestDispatcher requestDispatcher; 
		requestDispatcher = request.getRequestDispatcher("home.jsp");
		requestDispatcher.forward(request, response);
	 }
%>
<c:choose>
	<c:when test="${not empty resume}">
		<fmt:setLocale value="${resume.locale}"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="${sessionScope.SESSION_LOCALE}"/>
	</c:otherwise>
</c:choose>

<script>
	 $(document).ready(function () 
	 {
	 	var container = $('div.errorContainer');
	 	$('#saveButton').click(function()
	 	{
	 		$('#formFullFill').submit();
	 	});
	 	$('#formFullFill').validate(
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
  				firstName:
  				{
  					required:true
  				},
  				lastName:
  				{
  					required:true
  				}  	
			},			  
			messages: 
			{
				firstName:
				{
					required:'<fmt:message key="REFERENCES_NAME_REQUIRED"/>'
				},
				lastName:
				{
					required:'<fmt:message key="LASTNAME_REQUIRED"/>'
				}			
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
	        		url: '/ResumeFullFillServ?service=update',
	       			data: $('#formFullFill').serialize(),
	       			async:false,
	       			success: function(data)
	       			{
	       				var obj = jQuery.parseJSON(data);
	       				if(obj.success==1)
	       				{
	       					$('#modalChild .modal-body  #modalContent').text("บันทึกสำเร็จแล้ว")
	       					$('#modalChild').modal('show');
	       				}
	       				else
	       				{
	       					$('#modalChild .modal-body  #modalContent').text("<fmt:message key='CANCEL_ERROR'/>")
	       					$('#modalChild').modal('show');
	       				}
	       			}
	      		});
	      		return false;
			}
		});
		
		//validate school name
		$("[name^=school_]").each(function () {
		    $(this).rules("add", {
		        required: true,
		        messages:
		        {
		        	required:'<fmt:message key="EDUCATION_REQUIRED"/>'
		        }
		    });
		});
		
		//validate faculty name
		$("[name^=faculty_]").each(function () {
		    $(this).rules("add", {
		        required: true,
		        messages:
		        {
		        	required:'<fmt:message key="EDU_FACULTY_REQUIRED"/>'
		        }
		    });
		});
		
		//validate major name
		$("[name^=major_]").each(function () {
		    $(this).rules("add", {
		        required: true,
		        messages:
		        {
		        	required:'<fmt:message key="EDU_MAJOR_REQUIRED"/>'
		        }
		    });
		});
		
		//validate company name
		$("[name^=company_]").each(function () {
		    $(this).rules("add", {
		        required: true,
		        messages:
		        {
		        	required:'<fmt:message key="APPLY_REQUIRED_COMNAME"/>'
		        }
		    });
		});
		
		//validate position name
		$("[name^=position_]").each(function () {
		    $(this).rules("add", {
		        required: true,
		        messages:
		        {
		        	required:'<fmt:message key="APPLY_REQUIRED_POSITION"/>'
		        }
		    });
		});
	 });
</script>
	<div style="height:15pt;"></div>
	<div style="font-size:16pt; color:#715fa8;" align="center">
		<fmt:message key="FULLFILL_DIRECTION">
			<fmt:param><c:out value="${myResumeLanguage}"/></fmt:param>
			<fmt:param><c:out value="${languageAll}"/></fmt:param>
		</fmt:message>
	</div>
	<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
	<div class="col-xs-12 col-sm-12">
		<b class="caption_green">
			1. <fmt:message key="FULLFILL_SUB_ONE"/>
		</b>
		<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
		<div class='col-xs-12 col-sm-12'><font class="suggestion" ><fmt:message key="FULLFILL_SUB_ONE_DIRECTION"/></font></div>
	</div>
	<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
	<div class="errorContainer col-xs-12 col-sm-12">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
	<div class='col-xs-12 col-sm-12' id="formFill">
		<form id="formFullFill" method="post" name="formFullFill">
			<input type="hidden" name="enc" value="<%=enc%>">
			<input type="hidden" name="idJsk" value="<%=idJsk%>">
		    <input type="hidden" name="idResume1" value="<%=idResume1%>">
		    <input type="hidden" name="idResume2" value="<%=idResume2%>">
		    <input type="hidden" name="idEmp" value="<%=idEmp%>">
		    <input type="hidden" name="idPos" value="<%=idPos%>">
		    <input type="hidden" name="atms" value="<%=atms%>">
		    <%
		    	String firstName = "";
		    	String lastName = "";
		    	if(firstEdit!=1)
		    	{
		    		firstName = Util.getStr(resume2.getFirstName(),"");
		    		lastName = Util.getStr(resume2.getLastName(),"");
		    	}else{
		    		firstName = Util.getStr(resume.getFirstName(),"");
		    		lastName = Util.getStr(resume.getLastName(),"");
		    	}
		     %>
			<div class="col-sm-12 caption_bold hidden-xs">
				<div class="col-xs-12 col-sm-4"></div>
				<div class="col-xs-12 col-sm-3"><fmt:message key="FULLFILL_THAI" /></div>
				<div class="col-xs-12 col-sm-5"><fmt:message key="FULLFILL_TO_ENG" /></div>
			</div>
			<div class="col-xs-12 col-sm-12">
				<b class="rememberFont"><ul style="padding-left:11px;"><li><fmt:message key="SECTION_PERSONAL_DATA"/></li></ul></b>
			</div>
			<div  style="padding-left:11px;">
				<div class="col-xs-5 col-sm-4"><b class="caption_bold">1. <fmt:message key="GLOBAL_FIRST_NAME" /></b></div>
				<div class="col-xs-7 col-sm-3">
					<font class="answer">
						<c:out value="${resume.firstNameThai}"/>
					</font>
				</div>
				<div class="visible-xs col-xs-12 col-sm-12"><font class="star">*</font><font class="caption"><fmt:message key="FULLFILL_TO_ENG" /></font></div>
				<div class="col-xs-12 col-sm-5">
					<input type="text" class="form-control" id="firstName" name="firstName" value="<%=Util.getStr(firstName)%>">
				</div>
			</div>
			<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
			<div  style="padding-left:11px;">
				<div class="col-xs-5 col-sm-4"><b class="caption_bold">2. <fmt:message key="GLOBAL_LAST_NAME"/></b></div>
				<div class="col-xs-7 col-sm-3">
					<font class="answer">
						<c:out value="${resume.lastNameThai}"/>
					</font>
				</div>
				<div class="visible-xs col-xs-12 col-sm-12"><font class="star">*</font><font class="caption"><fmt:message key="FULLFILL_TO_ENG" /></font></div>
				<div class="col-xs-12 col-sm-5">
					<input type="text" class="form-control" name="lastName" name="lastName" value="<%=Util.getStr(lastName)%>">
				</div>
			</div>
			<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
			<!-- Education -->
			<c:if test="${not empty eduList}">
				<%
					boolean chkOther = false ; 
			 		for(int i=0; i<eduList.size(); i++)
			 		{
			 			//Education of resume 1 ------------------
			 			Education edu = (Education)eduList.get(i);
			 			String facultyName = "";
						String majorName = "";
						String schoolname="";
						String degreeName="";
			 			if(edu != null)
			 			{
				 			//University
				 			com.topgun.shris.masterdata.School aSchool = MasterDataManager.getSchool(resume.getIdCountry(), edu.getIdState(), edu.getIdSchool(), resume.getIdLanguage());
							schoolname= aSchool!=null?aSchool.getSchoolName():edu.getOtherSchool();
							
							com.topgun.shris.masterdata.Degree aDegree=MasterDataManager.getDegree(edu.getIdDegree(), resume.getIdLanguage());
							degreeName = aDegree!=null?aDegree.getDegreeName():"";
							
							
							//Faculty
							com.topgun.shris.masterdata.Faculty aFaculty = null;
							aFaculty=MasterDataManager.getFacultyExclusive(edu.getIdSchool(), edu.getIdDegree(), edu.getIdFacMajor(), resume.getIdLanguage());
							if(aFaculty==null || eduList.get(i).getIdSchool()==-1)
							{
								aFaculty = MasterDataManager.getFaculty(edu.getIdFacMajor(), resume.getIdLanguage());
							}
							if(eduList.get(i).getIdFacMajor()!=-1){
								facultyName += aFaculty.getFacultyName();
							}
							else{
								facultyName += edu.getOtherFaculty();
							}
							//Major
							com.topgun.shris.masterdata.Major aMajor = null;
							aMajor=MasterDataManager.getMajorExclusive(edu.getIdSchool(), edu.getIdDegree(), edu.getIdFacMajor(), edu.getIdMajor(), resume.getIdLanguage());
							if(aMajor==null){
								aMajor = MasterDataManager.getMajor(edu.getIdFacMajor(), edu.getIdMajor(), resume.getIdLanguage());
							}
							if(aMajor!=null)
							{
								majorName += aMajor.getMajorName();
							}
							else 
							{
								majorName += edu.getOtherMajor();
							}
			 			}
			 			
						//Education of resume 2 ------------------
			 			Education edu2 = new EducationManager().getEucationHashReference(idJsk,idResume2,eduList.get(i).getHashRefer());
			 			String facultyName2 = "";
						String majorName2 = "";
						String schoolname2= "";
			 			
			 			if(edu2 != null)
			 			{
			 				//University
				 			com.topgun.shris.masterdata.School aSchool2 = MasterDataManager.getSchool(resume2.getIdCountry(), edu2.getIdState(), edu2.getIdSchool(), resume2.getIdLanguage());
							schoolname2= aSchool2!=null?aSchool2.getSchoolName():edu2.getOtherSchool();
							
							//Faculty
							com.topgun.shris.masterdata.Faculty aFaculty2 = null;
							aFaculty2=MasterDataManager.getFacultyExclusive(edu2.getIdSchool(), edu2.getIdDegree(), edu2.getIdFacMajor(), resume2.getIdLanguage());
							if(aFaculty2==null || eduList.get(i).getIdSchool()==-1)
							{
								aFaculty2 = MasterDataManager.getFaculty(edu2.getIdFacMajor(), resume2.getIdLanguage());
							}
							if(eduList.get(i).getIdFacMajor()!=-1){
								facultyName2 += aFaculty2.getFacultyName();
							}
							else{
								facultyName2 += edu2.getOtherFaculty();
							}
							//Major
							com.topgun.shris.masterdata.Major aMajor2 = null;
							aMajor2=MasterDataManager.getMajorExclusive(edu2.getIdSchool(), edu2.getIdDegree(), edu2.getIdFacMajor(), edu2.getIdMajor(), resume2.getIdLanguage());
							if(aMajor2==null){
								aMajor2 = MasterDataManager.getMajor(edu2.getIdFacMajor(), edu2.getIdMajor(), resume2.getIdLanguage());
							}
							if(aMajor2!=null)
							{
								majorName2 += aMajor2.getMajorName();
							}
							else 
							{
								majorName2 += edu2.getOtherMajor();
							}
								
			 			}
			 			if(eduList.get(i).getIdSchool() == -1 || eduList.get(i).getIdFacMajor() == -1 || eduList.get(i).getIdMajor() == -1)
			 			{
			 				if(!chkOther)
			 				{
			 					out.println("<div class='col-xs-12 col-sm-12'>");
			 					out.println("<b class='rememberFont'><ul style='padding-left:11px;'><li>"+proMgr.getMessage(locale, "PREVIEW_EDUCATION")+"</li></ul></b>");
			 					out.println("</div>");
			 					chkOther = true ;
			 				}
		 					out.println("<div class='col-xs-12 col-sm-12'><b class='caption_bold'>"+(i+1)+".&nbsp;<u>"+degreeName+" , "+schoolname+"</u></b></div>");
		 					out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
			 			}
			 			if(eduList.get(i).getIdSchool() == -1)
			 			{
				 			out.println("<div style='padding-left:11px;'>");
				 				out.println("<div class='col-xs-4 col-sm-4'><b class='caption_bold'>"+proMgr.getMessage(locale, "FAMILY_EDUCATION")+"</b></div>");
				 				out.println("<div class='col-xs-8 col-sm-3' align='left'><font class='answer'>"+schoolname+"</font></div>");
				 				out.println("<div class='visible-xs col-xs-12 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
				 				out.println("<div class='col-xs-12 col-sm-5'><input type='text' class='form-control' name='school_"+eduList.get(i).getHashRefer()+"'  value='"+Util.getStr(schoolname2)+"'></div>");
				 			out.println("</div>");
				 			out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
				 		}
				 		if(eduList.get(i).getIdFacMajor() == -1)
				 		{
				 			out.println("<div  style='padding-left:11px;'>");
				 				out.println("<div class='col-xs-4 col-sm-4'><b class='caption_bold'>"+proMgr.getMessage(locale, "PREVIEW_EDUCATION_FACULTY")+"</b></div>");
				 				out.println("<div class='col-xs-8 col-sm-3'><font class='answer'>"+facultyName+"</font></div>");
				 				out.println("<div class='visible-xs col-xs-12 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
				 				out.println("<div class='col-xs-12 col-sm-5'><input type='text' class='form-control' name='faculty_"+eduList.get(i).getHashRefer()+"' value='"+Util.getStr(facultyName2)+"'></div>");
				 			out.println("</div>");
				 			out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
				 		}
				 		if(eduList.get(i).getIdMajor() == -1)
				 		{
				 			out.println("<div  style='padding-left:11px;'>");
				 				out.println("<div class='col-xs-4 col-sm-4'><b class='caption_bold'>"+proMgr.getMessage(locale, "EDU_MAJOR")+"</b></div>");
				 				out.println("<div class='col-xs-8 col-sm-3'><font class='answer'>"+majorName+"</font></div>");
				 				out.println("<div class='visible-xs col-xs-12 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
				 				out.println("<div class='col-xs-12 col-sm-5'><input type='text' class='form-control' name='major_"+eduList.get(i).getHashRefer()+"' value='"+Util.getStr(majorName2)+"'></div>");
				 			out.println("</div>");
				 			out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
				 		}
				 		out.println("<div class='col-xs-12 col-sm-12' style='height:1px;'>&nbsp;</div>");
			 		} 
			 	%>
			</c:if>
			<div class='col-xs-12 col-sm-12' style='height:1px;'>&nbsp;</div>
			<!-- Experience -->
		 	<c:if test="${not empty workList}">
		 		<div class="col-xs-12 col-sm-12">
					<ul style="padding-left:11px;"><li><b class="rememberFont"><fmt:message key="SECTION_WORK_EXPERIENCE"/></b></li></ul>
				</div>
				<%
			 		if(workList != null)
			 		{
			 			for(int i=0; i<workList.size(); i++)
			 			{
			 				String comNameEdit = "";
			 				String positionNameEdit = "";
			 				String jobfieldNameEdit = "";
			 				String positionNameStartEdit = "";
			 				String jobfieldNameStartEdit = "";
			 				WorkExperience exp2 = new WorkExperienceManager().getWorkExperienceHashReference(idJsk, idResume2, workList.get(i).getHashRefer());
			 				if(firstEdit!=1)
			 				{
			 					comNameEdit = Util.getStr(exp2.getCompanyName(),"");
			 					jobfieldNameEdit = Util.getStr(exp2.getWorkJobFieldOth(),"");
			 					positionNameEdit = Util.getStr(exp2.getPositionLast(),"");
			 					positionNameStartEdit = Util.getStr(exp2.getPositionStart(),"");
			 					jobfieldNameStartEdit = Util.getStr(exp2.getWorkJobFieldOthStart(),"");
			 				}
			 				out.println("<div class='col-xs-12 col-sm-12'><b class='caption_bold'>"+(i+1)+".&nbsp;<u>"+workList.get(i).getCompanyName()+" , "+workList.get(i).getPositionLast()+"</u></b></div>");
			 				out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
			 				out.println("<div style='padding-left:11px;'>");
		 					out.println("<div class='col-xs-6 col-sm-4'><b class='caption_bold'>"+proMgr.getMessage(locale, "APPLY_COMPANY_NAME")+"</b></div>");
		 					out.println("<div class='col-xs-6 col-sm-3'><font class='answer'>"+workList.get(i).getCompanyName()+"</font></div>");
		 					out.println("<div class='visible-xs col-xs-6 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
		 					out.println("<div class='col-xs-6 col-sm-5'><input type='text' class='form-control' name='company_"+workList.get(i).getHashRefer()+"' value='"+comNameEdit+"'></div>");
			 				out.println("</div>");
			 				out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
			 				if(exp2.getWorkJobField()==-1)
			 				{
				 				out.println("<div style='padding-left:11px;'>");
			 					out.println("<div class='col-xs-4 col-sm-4'><b class='caption_bold'>สายงาน</b></div>");
			 					out.println("<div class='col-xs-8 col-sm-3'><font class='answer'>"+workList.get(i).getWorkJobFieldOth()+"</font></div>");
			 					out.println("<div class='visible-xs col-xs-12 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
			 					out.println("<div class='col-xs-12 col-sm-5'><input type='text' class='form-control' name='jobfield_"+workList.get(i).getHashRefer()+"' value='"+jobfieldNameEdit+"'></div>");
				 				out.println("</div>");
				 				out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
			 				}
			 				if(exp2.getWorkSubField()==-1)
			 				{
				 				out.println("<div style='padding-left:11px;'>");
			 					out.println("<div class='col-xs-4 col-sm-4'><b class='caption_bold'>"+proMgr.getMessage(resume.getLocale(),"REFERENCES_TITLE")+"</b></div>");
			 					out.println("<div class='col-xs-8 col-sm-3'><font class='answer'>"+workList.get(i).getPositionLast()+"</font></div>");
			 					out.println("<div class='visible-xs col-xs-12 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
			 					out.println("<div class='col-xs-12 col-sm-5'><input type='text' class='form-control' name='position_"+workList.get(i).getHashRefer()+"' value='"+positionNameEdit+"'></div>");
				 				out.println("</div>");
				 				out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
			 				}
			 				if(exp2.getWorkJobFieldStart()==-1 && !workList.get(i).getWorkJobFieldOthStart().equals(""))
			 				{
				 				out.println("<div style='padding-left:11px;'>");
			 					out.println("<div class='col-xs-4 col-sm-4'><b class='caption_bold'>สายงานเริ่มต้น</b></div>");
			 					out.println("<div class='col-xs-8 col-sm-3'><font class='answer'>"+workList.get(i).getWorkJobFieldOthStart()+"</font></div>");
			 					out.println("<div class='visible-xs col-xs-12 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
			 					out.println("<div class='col-xs-12 col-sm-5'><input type='text' class='form-control' name='jobfieldStart_"+workList.get(i).getHashRefer()+"' value='"+jobfieldNameStartEdit+"'></div>");
				 				out.println("</div>");
				 				out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
			 				}
			 				if(exp2.getWorkSubFieldStart()==-1 && !workList.get(i).getWorkSubFieldOthStart().equals(""))
			 				{
				 				out.println("<div style='padding-left:11px;'>");
			 					out.println("<div class='col-xs-4 col-sm-4'><b class='caption_bold'>ตำแหน่งเริ่มต้น</b></div>");
			 					out.println("<div class='col-xs-8 col-sm-3'><font class='answer'>"+workList.get(i).getPositionStart()+"</font></div>");
			 					out.println("<div class='visible-xs col-xs-12 col-sm-12'><font class='star'>*</font><font class='caption'>"+proMgr.getMessage(locale, "FULLFILL_TO_ENG")+"</font></div>");
			 					out.println("<div class='col-xs-12 col-sm-5'><input type='text' class='form-control' name='positionStart_"+workList.get(i).getHashRefer()+"' value='"+positionNameStartEdit+"'></div>");
				 				out.println("</div>");
				 				out.println("<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>");
			 				}
			 			}
			 		} 
			 	%>
		 	</c:if>
		</form>
	</div>
	<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>	
	<div id="buttonLayer1" class='col-xs-12 col-sm-12' align="right">
		<a href="#" id="saveButton"><img alt="งาน หางาน" src="../images/<c:out value="${resume.locale}"/>/save2.png" id="editButton"/></a>
	</div>
	<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
	<div class='col-xs-12 col-sm-12'>
		<b class="caption_green">
			2. <fmt:message key="FULLFILL_SUB_TWO"/>
		</b>
		&nbsp;<a href="/view/viewResume.jsp?idResume=<c:out value="${idResume1}" />&jSession=<c:out value="${idSession}"/>" target="_blank" target="_blank"><fmt:message key="GLOBAL_VIEW"/></a>
	</div>
	<c:if test="${not empty languageRequireList}">
		<c:forEach items="${languageRequireList}" var="languageResume" varStatus="idx">
			<c:set var="idLanguage" scope="request" value="${languageResume.id}" />
			<%
				int idChildResume = new ResumeManager().chkSubResumeLanguage(idJsk, idResume1, Util.getInt(request.getAttribute("idLanguage")));
				Language language = new MasterDataManager().getSkillLanguage(Util.getInt(request.getAttribute("idLanguage")),38);
				request.setAttribute("languageName",language.getName());
				request.setAttribute("idChildResume",idChildResume);
			%>
			<c:if test="${not empty languageName && idChildResume > 0}">
				<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
				<div class='col-xs-12 col-sm-12'>
					<b class="caption_green">
						<c:out value="${(idx.index)+3}"/>.
						<fmt:message key="FULLFILL_SUB_LANGUAGE">
							<fmt:param><c:out value="${languageName}"/></fmt:param>
						</fmt:message>
					</b>
				&nbsp;<a href="/view/viewResume.jsp?idResume=<c:out value="${idChildResume}" />&jSession=<c:out value="${idSession}"/>" target="_blank" target="_blank"><fmt:message key="GLOBAL_VIEW"/></a>
				</div>
			</c:if>
		</c:forEach>
	</c:if>
	<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
	<div class='col-xs-12 col-sm-12' style='height:10px;'>&nbsp;</div>
	<!-- Modal confirm editChild -->
	<div class="modal fade" id="modalChild" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-body" align="center">
					<font style="color:#725fa7;font-size:20px;"><span id="modalContent"></span></font>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="window.location.href='/SRIX?view=resumeFullFillEdit&enc=<%=enc%>';"><fmt:message key="GLOBAL_CONFIRM"/></button>
				</div>
			</div>
		</div>
	</div>
