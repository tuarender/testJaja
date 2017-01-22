package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.HobbyManager;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Education;
import com.topgun.resume.EducationManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.WorkExperienceManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class EducationServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
        EducationManager eduMgr=new EducationManager();
        ResumeManager rsmMgr=new ResumeManager();
        JobseekerManager jskMgr=new JobseekerManager();
        long startTime=new java.util.Date().getTime();
		
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String locale=Util.getStr(request.getParameter("locale"));
		if(locale.equals("")){
			locale=Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"));
		}
		if(locale.equals("")){
			locale="th_TH";
		}
		//idJsk = 6953367;//by pass idJsk
		String urlErrorRedirect = "";
		String degreeError = "";
		
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			Resume resume=rsmMgr.get(idJsk, idResume);
			if(resume==null){
				errors.add(propMgr.getMessage(locale, "EDU_NOT_FOUND_RESUME"));
				elements.add("SYSTEM");
				urlErrorRedirect = "/LogoutServ";
			}
			else{
				
				String service=Util.getStr(request.getParameter("service"));
				if(service.equals("addEducation")){
					int idEducation = Util.getInt(request.getParameter("idEducation"));
					int country = Util.getInt(request.getParameter("country"));
					int state = Util.getInt(request.getParameter("state"),0);
					int school = Util.getInt(request.getParameter("idSchool"));
					int degree = Util.getInt(request.getParameter("degree")); 
					int faculty = Util.getInt(request.getParameter("idFaculty"),0);
					int facultyMinor = Util.getInt(request.getParameter("facultyMinor"),0);//no use in srix
					int major = Util.getInt(request.getParameter("idMajor"),0);
					int minor = Util.getInt(request.getParameter("minor"),0);
					//Long idStudent = Util.getLong(request.getParameter("idStudent"));
					String idStudent = Util.getStr(request.getParameter("idStudent"));
					
					String otherSchool = Util.getStr(request.getParameter("school"));
					String otherFaculty = Util.getStr(request.getParameter("faculty"));
					String otherMajor = Util.getStr(request.getParameter("major"));
					String minorOther = Util.getStr(request.getParameter("minorOther"));//no use in srix
					String grade =  Util.getStr(request.getParameter("grade"));
					String unit =  Util.getStr(request.getParameter("unit"));
					String unitOther =  Util.getStr(request.getParameter("unitOther"));
					
					String startMonth = Util.getStr(request.getParameter("startMonth"));
					String startYear = Util.getStr(request.getParameter("startYear"));
					String finishMonth = Util.getStr(request.getParameter("finishMonth"));
					String finishYear = Util.getStr(request.getParameter("finishYear"));
					String award = Util.getStr(request.getParameter("award"));
					
					//--------------------------if high school-------------------------
					if(degree==1){
						faculty = 0;
						major = 0;
						otherFaculty="";
						otherMajor="";
					}
					
					//--------------------------validate data--------------------------
					if (country == 0){
						errors.add(propMgr.getMessage(locale, "EDU_COUNTRY_REQUIRED"));
						elements.add("country");
					}
					else if (school == 0||otherSchool.equals("")){
						errors.add(propMgr.getMessage(locale, "EDU_UNIVERSITY_REQUIRED"));
						elements.add("school");	
					}
					else if (degree == 0){
						errors.add(propMgr.getMessage(locale, "EDU_DEGREE_REQUIRED"));
						elements.add("degree");	
					}
					else if (((faculty == -1&&otherFaculty.equals(""))||faculty==0) && degree != 1){
						errors.add(propMgr.getMessage(locale, "EDU_FACULTY_REQUIRED"));
						elements.add("faculty");	
					}
					else if (((major == -1&&otherMajor.equals(""))||major==0) && degree!= 1 ){
						errors.add(propMgr.getMessage(locale, "EDU_MAJOR_REQUIRED"));
						elements.add("major");	
					}
					else if (unit.equals("")){
						errors.add(propMgr.getMessage(locale, "EDU_UNIT_REQUIRED"));
						elements.add("unit");	
					}
					/*
					else if (startMonth.equals("")){
						errors.add(propMgr.getMessage(locale, "EDU_START_MONTH_REQUIRED"));
						elements.add("startDate");	
					}
					else if (startYear.equals("")){
						errors.add(propMgr.getMessage(locale, "EDU_START_YEAR_REQUIRED"));
						elements.add("startDate");	
					}
					else if (finishMonth.equals("")){
						errors.add(propMgr.getMessage(locale, "EDU_END_MONTH_REQUIRED"));
						elements.add("startDate");	
					}	*/	
					
					else if (finishYear.equals("")){
						errors.add(propMgr.getMessage(locale, "EDU_END_YEAR_REQUIRED"));
						elements.add("finishYear");	
					}	
					else if ((country == 231||country == 110||country == 213||country == 81) && state == 0){
						errors.add(propMgr.getMessage(locale, "EDU_STATE_REQUIRED"));
						elements.add("state");
					}
					else if (unit.equals("/4") && (Double.parseDouble(grade) <0||Double.parseDouble(grade)>4)){
						errors.add(propMgr.getMessage(locale, "EDU_GRADE_REQUIRED_4"));
						elements.add("grade");
					}
					else if (unit.equals("/5")&& (Double.parseDouble(grade) <0||Double.parseDouble(grade)>5)){
						errors.add(propMgr.getMessage(locale, "EDU_GRADE_REQUIRED_5"));
						elements.add("grade");
					}	
					else if (unit.equals("/7")&& (Double.parseDouble(grade) <0||Double.parseDouble(grade)>7)){
						errors.add(propMgr.getMessage(locale, "EDU_GRADE_REQUIRED_7"));
						elements.add("grade");	
					}	
					else if (unit.equals("%")&& (Double.parseDouble(grade) <0||Double.parseDouble(grade)>100)){
						errors.add(propMgr.getMessage(locale, "EDU_GRADE_REQUIRED_100"));
						elements.add("grade");	
					}
					else if(unit.equals("-1")&&unitOther.trim().equals("")){
						errors.add(propMgr.getMessage(locale, "EDU_UNIT_REQUIRED"));
						elements.add("unitOther");
						
					}else if(Integer.parseInt(finishYear)>=2014)
					{
						if(idStudent.equals(""))
						{
							errors.add(propMgr.getMessage(locale, "STUDENT_ID_REQUIRED"));
							elements.add("idStudent");	
						}
					}

					if(errors.size()==0){
						boolean isEduExist = true;
						
						Date startEdu=null;
						
						if(startMonth.equals("")&&startYear.equals("")){
							startEdu = Util.getSQLDate(3,5,1000);
						}
						else if(startMonth.equals("")&&!startYear.equals("")){
							startEdu = Util.getSQLDate(2,5,Util.getInt(startYear));
						}
						else if(!startMonth.equals("")&&!startYear.equals("")){
							startEdu = Util.getSQLDate(1,Util.getInt(startMonth),Util.getInt(startYear));
						}
						
						
						Date finishEdu = null;
						if(finishMonth.equals("")&&finishYear.equals("")){
							finishEdu = Util.getSQLDate(3,5,4000);
						}
						else if(finishMonth.equals("")&&!finishYear.equals("")){
							finishEdu = Util.getSQLDate(2,5,Util.getInt(finishYear));
						}
						else if(!finishMonth.equals("")&&!finishYear.equals("")){
							finishEdu = Util.getSQLDate(1,Util.getInt(finishMonth),Util.getInt(finishYear));
						}
						
						Education edu = eduMgr.get(idJsk, idResume, idEducation);
						if(edu==null){
							edu = new Education();
							edu.setIdJsk(idJsk);
							edu.setIdResume(idResume);
							edu.setId(idEducation);
							isEduExist = false;
						}
						edu.setIdCountry(country);
						edu.setIdState(state);
						edu.setIdSchool(school);
						if (school == -1){
							edu.setOtherSchool(otherSchool);
						}
						else{
							edu.setOtherSchool("");
						}
						edu.setIdDegree(degree);
						edu.setIdFacMajor(faculty);
						if (faculty == -1){
							edu.setOtherFaculty(otherFaculty);
						}
						else{
							edu.setOtherFaculty("");
						}
						edu.setIdMajor(major);
						edu.setIdMinor(minor);
						edu.setIdFacMinor(facultyMinor);
						if (major == -1){
							edu.setOtherMajor(otherMajor);
						}
						else{
							edu.setOtherMajor("");
						}			
						if (minor== -1){
							edu.setOtherMinor(minorOther);
						}
						else{
							edu.setOtherMinor("");
						}
						
						//if finishEdu before startEdu.....SWAP IT!!!.....
						if(finishEdu.before(startEdu)){
							Date eduTemp = startEdu;
							startEdu = finishEdu;
							finishEdu = eduTemp;
						}
						
						
						edu.setStartDate(startEdu);			
						edu.setFinishDate(finishEdu);
						edu.setIdStudent(idStudent);
						edu.setAward(award);
						edu.setGpa(Util.StrToFloat(grade));			
						edu.setUnit(unit);
						if(unit.equals("-1")){
							edu.setOtherUnit(unitOther);
						}
						else{
							edu.setOtherUnit("");
						}
						String hashRefer=Util.getStr(edu.getHashRefer());
						if(hashRefer.equals("")) //update parent hash refer
						{
							hashRefer=Util.getHashString();
							edu.setHashRefer(hashRefer);
						}
						if (isEduExist){
							if (eduMgr.update(edu)!=1){
								errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
								elements.add("SYSTEM");	
							}
						}			 
						else{
							if(eduMgr.chkExistEducation(edu)==0)
							{
								if(eduMgr.add(edu)==1){  
									Jobseeker jsk = jskMgr.get(idJsk);
									if(jsk.getJobMatchStatus().equals("")){
										jskMgr.updateJobmatchStatusTrue(idJsk);
									}
								}
								else{				  
									errors.add(propMgr.getMessage(locale, "EDU_INSERT_ERROR"));
									elements.add("SYSTEM");	
								}
							}
						}
						edu.setIdResume(idResume);
						edu.setIdRefer(0);
						if(errors.size()==0){
							if(resume.getTemplateIdCountry() == 216){
								if(!eduMgr.chkBachelorDegree(idJsk, 0)){
									List<Education> eduList = eduMgr.getAll(idJsk, idResume);
									if(eduList!=null){//check all education
										/*
										for (int i = eduList.size()-1; i >= 0&&degreeError.equals(""); i--){
											if (eduList.get(i).getIdDegree() == 7){
												errors.add(propMgr.getMessage(locale, "EDU_DEGREE_MASTER_REQUIRED"));
												degreeError = "degreeMaster";
												elements.add("degree");
											}
											else if(eduList.get(i).getIdDegree() == 3){
												errors.add(propMgr.getMessage(locale, "EDU_DEGREE_BECHELOR_REQUIRED"));
												degreeError = "degreeBechelor";
												elements.add("degree");
											}
										}
										*/
										if(eduMgr.chkDoctorDegree(idJsk, idResume))
										{
											errors.add(propMgr.getMessage(locale, "EDU_DEGREE_MASTER_REQUIRED"));
											degreeError = "degreeMaster";
											elements.add("degree");
										}
										if(eduMgr.chkMasterDegree(idJsk, idResume))
										{
											errors.add(propMgr.getMessage(locale, "EDU_DEGREE_BECHELOR_REQUIRED"));
											degreeError = "degreeBechelor";
											elements.add("degree");
										}
									}
									else{
										errors.add(propMgr.getMessage(locale, "EDU_VALIDATE_BECHELOR_DEGREE_ERROR"));
										degreeError = "true";
										elements.add("SYSTEM");	
									}
								}
							}
							else{
								if(eduMgr.chkBachelorDegree(idJsk, 0)){
									Resume resumeData = rsmMgr.get(idJsk, idResume);
									if(resumeData!=null){
										resumeData.setCompleteStatus("EDUCATION");
										if(rsmMgr.update(resumeData)!=1){
											errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
											elements.add("SYSTEM");
										}
									}
									else{
										errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
										elements.add("SYSTEM");	
									}
								}
							}
						}
					}
					if(idResume==0)
					{
						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
					}
					else
					{
						rsmMgr.updateStatus(idJsk,idResume, new ResumeStatusManager().getResumeStatus(rsmMgr.get(idJsk,idResume)));
					}
					rsmMgr.updateTimestamp(idJsk,idResume);
				}
				else if(service.equals("delete")){
					int idEducation = Util.getInt(request.getParameter("idEducation"),1);
					Education edu = eduMgr.get(idJsk, idResume, idEducation);
					if(eduMgr.delete(idJsk, idResume, idEducation)!=1){
						errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
						elements.add("SYSTEM");	
					}
					
					
					if(idResume==0)
					{
						rsmMgr.updateStatus(idJsk,0, new ResumeStatusManager().getRegisterStatus(rsmMgr.get(idJsk,0)));
					}
					else
					{
						rsmMgr.updateStatus(idJsk,idResume, new ResumeStatusManager().getResumeStatus(rsmMgr.get(idJsk,idResume)));
					}
					rsmMgr.updateTimestamp(idJsk,idResume);
				}
				else if(service.equals("checkHightestEducation")){
					if(resume.getTemplateIdCountry() == 216){
						if(!eduMgr.chkBachelorDegree(idJsk, idResume)){
							List<Education> eduList = eduMgr.getAll(idJsk, idResume);
							if(eduList!=null){//check all education
								//Greater then to lower then
								for (int i = eduList.size()-1; i >= 0&&degreeError.equals(""); i--){
									if (eduList.get(i).getIdDegree() == 7){
										errors.add(propMgr.getMessage(locale, "EDU_DEGREE_MASTER_REQUIRED"));
										degreeError = "degreeMaster";
										elements.add("degree");
									}
									else if(eduList.get(i).getIdDegree() == 3){
										errors.add(propMgr.getMessage(locale, "EDU_DEGREE_BECHELOR_REQUIRED"));
										degreeError = "degreeBechelor";
										elements.add("degree");
									}
								}
							}
						
						}
					}
					else{
						if(eduMgr.chkBachelorDegree(idJsk, idResume)){
							Resume resumeData = rsmMgr.get(idJsk, idResume);
							if(resumeData!=null){
								resumeData.setCompleteStatus("EDUCATION");
								if(rsmMgr.update(resumeData)!=1){
									errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
									elements.add("SYSTEM");
								}
							}
							else{
								errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
								elements.add("SYSTEM");	
							}
						}
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "EDU_SERVICE_REQUIRE"));
					elements.add("SYSTEM");
				}
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
		LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
		if(errors.size()>0){
			json.put("success",0);
			json.put("errors", errors);
			json.put("elements", elements);
			json.put("degreeError", degreeError);
			json.put("urlError", urlErrorRedirect);
		}
		else{
			json.put("success",1);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		if(usageTime>500){
			System.out.println("\nEducationServlet => Usage "+usageTime+" msec");
			System.out.println("idJsk=> "+idJsk);
		}
	}

}
