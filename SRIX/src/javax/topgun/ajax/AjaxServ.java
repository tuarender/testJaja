package javax.topgun.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.topgun.shris.masterdata.*;
import com.topgun.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxServ extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private final String[] servicesData = {
			"CareerObjective","City","Computer",
			"ComputerGroup","Country","Currency",
			"Degree","Faculty","Gender",
			"Hobby","HobbyGroup","Industry",
			"JobField","Language","LanguageLevel",
			"Major","Proficiency","Region",
			"Salary","SalaryPer","School",
			"SkillLevel","State","Strength",
			"SubField","TypeOfJob", "RelateSubFieldPosition"};

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String service = Util.getStr(request.getParameter("service")!=null?request.getParameter("service"):"0");
		int idCareerObjective = Util.getInt(request.getParameter("idCareerObjective")!=null?request.getParameter("idCareerObjective"):"0");
		int idCountry = Util.getInt(request.getParameter("idCountry")!=null?request.getParameter("idCountry"):"0");
		int idState = Util.getInt(request.getParameter("idState")!=null?request.getParameter("idState"):"0");
		int idCity = Util.getInt(request.getParameter("idCity")!=null?request.getParameter("idCity"):"0");
		int idLanguage = Util.getInt(request.getParameter("idLanguage")!=null?request.getParameter("idLanguage"):"0");
		int idComputer = Util.getInt(request.getParameter("idComputer")!=null?request.getParameter("idComputer"):"0");
		int idComputerGroup = Util.getInt(request.getParameter("idComputerGroup")!=null?request.getParameter("idComputerGroup"):"0");
		int idHobby = Util.getInt(request.getParameter("idHobby")!=null?request.getParameter("idHobby"):"0");
		int idHobbyGroup = Util.getInt(request.getParameter("idHobbyGroup")!=null?request.getParameter("idHobbyGroup"):"0");
		int idFaculty = Util.getInt(request.getParameter("idFaculty")!=null?request.getParameter("idFaculty"):"0");
		int idRegion = Util.getInt(request.getParameter("idRegion")!=null?request.getParameter("idRegion"):"0");
		int idSalaryType = Util.getInt(request.getParameter("idSalaryType")!=null?request.getParameter("idSalaryType"):"0");
		int idPer = Util.getInt(request.getParameter("idPer")!=null?request.getParameter("idPer"):"0");
		int idSchool = Util.getInt(request.getParameter("idSchool")!=null?request.getParameter("idSchool"):"0"); 
		int idLevelSkill = Util.getInt(request.getParameter("idLevelSkill")!=null?request.getParameter("idLevelSkill"):"0"); 
		int idStrength = Util.getInt(request.getParameter("idStrength")!=null?request.getParameter("idStrength"):"0");
		int idJobField = Util.getInt(request.getParameter("idJobField")!=null?request.getParameter("idJobField"):"0");
		int idSubField = Util.getInt(request.getParameter("idSubField")!=null?request.getParameter("idSubField"):"0");
		int idCurrency = Util.getInt(request.getParameter("idCurrency")!=null?request.getParameter("idCurrency"):"0");
		int idDegree = Util.getInt(request.getParameter("idDegree")!=null?request.getParameter("idDegree"):"0");
		int idGender = Util.getInt(request.getParameter("idGender")!=null?request.getParameter("idGender"):"0");
		int idIndustry = Util.getInt(request.getParameter("idIndustry")!=null?request.getParameter("idIndustry"):"0");
		int idMajor = Util.getInt(request.getParameter("idMajor")!=null?request.getParameter("idMajor"):"0");
		int idLevelLanguage = Util.getInt(request.getParameter("idLevelLanguage")!=null?request.getParameter("idLevelLanguage"):"0");
		int idProficiency = Util.getInt(request.getParameter("idProficiency")!=null?request.getParameter("idProficiency"):"0");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        try{
        	//check request data by self
        	/*String host = request.getRemoteHost();
        	//------------------------for dev-----------------------
        	if(host.equals("127.0.0.1")){
        		host = "localhost";
        	}
        	//------------------------------------------------------
        	String header = request.getHeader("referer");
        	System.out.println("Header"+header);
        	System.out.println("Host"+host);
        	System.out.println(service);
        	System.out.println(idCountry);*/
        	//if(header.indexOf(host)>-1){
        		if(Arrays.asList(servicesData).contains(service)){
        			Gson gson = new Gson();
        			String json = "";
        			if(service.equals("CareerObjective")){
        				if(idLanguage>0){
        					if(idCareerObjective>0){
        						CareerObjective careerObjective = MasterDataManager.getCareerObjective(idCareerObjective, idLanguage);
        						json = gson.toJson(careerObjective);
        					}
        					else{
        						ArrayList<CareerObjective> careerObjectives = (ArrayList<CareerObjective>)MasterDataManager.getAllCareerObjective(idLanguage);
        						json = gson.toJson(careerObjectives);
        					}
        				}
                    }
        			else if(service.equals("City")){
        				if(idCountry>0&&idState>0){
        					if(idCity>0){
        						City city = MasterDataManager.getCity(idCountry, idState, idCity, idLanguage);
        						json = gson.toJson(city);
        					}
        					else{
        						ArrayList<City> cities = (ArrayList<City>)MasterDataManager.getAllCity(idCountry, idState, idLanguage);
        						json = gson.toJson(cities);
        					}
        				}
                    }
        			else if(service.equals("Computer")){
        				if(idComputerGroup>0){
        					if(idComputer>0){
        						Computer computer = MasterDataManager.getComputer(idComputerGroup, idComputer);
        						json = gson.toJson(computer);
        					}
        					else{
        						ArrayList<Computer> computers = (ArrayList<Computer>)MasterDataManager.getAllComputer(idComputerGroup);
        						json = gson.toJson(computers);
        					}
        				}
                    }
        			else if(service.equals("ComputerGroup")){
        				if(idLanguage>0){
        					if(idComputerGroup>0){
        						ComputerGroup computerGroup = MasterDataManager.getComputerGroup(idComputerGroup, idLanguage);
        						json = gson.toJson(computerGroup);
        					}
        					else{
        						ArrayList<ComputerGroup> computerGroup = (ArrayList<ComputerGroup>)MasterDataManager.getAllComputerGroup(idLanguage);
        						json = gson.toJson(computerGroup);
        					}
        				}
                    }
        			else if(service.equals("Country")){
        				if(idLanguage>0){
        					if(idCountry>0){
        						Country country = MasterDataManager.getCountry(idCountry, idLanguage);
        						json = gson.toJson(country);
        					}
        					else{
        						ArrayList<Country> countries = (ArrayList<Country>)MasterDataManager.getAllCountry(idLanguage);
        						json = gson.toJson(countries);
        					}
        				}
                    }
        			else if(service.equals("Currency")){
        				if(idCurrency>0){
        					Currency currency = MasterDataManager.getCurrency(idCurrency);
        					json = gson.toJson(currency);
        				}
        				else{
        					ArrayList<Currency> currencies = (ArrayList<Currency>)MasterDataManager.getAllCurrency();
        					json = gson.toJson(currencies);
        				}
        			}
        			else if(service.equals("Degree")){
        				if(idLanguage>0){
        					if(idDegree>0){
            					Degree degree = MasterDataManager.getDegree(idDegree, idLanguage);
            					json = gson.toJson(degree);
            				}
        					else{
        						ArrayList<Degree> degrees = (ArrayList<Degree>)MasterDataManager.getAllDegree(idLanguage);
            					json = gson.toJson(degrees);
        					}
        				}
                    }
        			else if(service.equals("Faculty")){
        				if(idLanguage>0){
        					if(idFaculty>0){
        						Faculty faculty = MasterDataManager.getFaculty(idFaculty, idLanguage);
        						json = gson.toJson(faculty);
        					}
        					else{
        						ArrayList<Faculty> faculties = (ArrayList<Faculty>)MasterDataManager.getAllFaculty(idLanguage);
        						json = gson.toJson(faculties);
        					}
        				}
                    }
        			else if(service.equals("Gender")){
        				if(idLanguage>0){
        					if(idGender>0){
        						Gender gender = MasterDataManager.getGender(idGender,idLanguage);
        						json = gson.toJson(gender);
        					}
        					else{
        						ArrayList<Gender> genders = (ArrayList<Gender>)MasterDataManager.getAllGender(idLanguage);
        						json = gson.toJson(genders);
        					}
        				}
                    }
        			else if(service.equals("Hobby")){
        				if(idHobbyGroup>0){
        					if(idHobby>0){
        						Hobby hobby = MasterDataManager.getHobby(idHobby, idLanguage);
        						json = gson.toJson(hobby);
        					}
        					else if (idCountry>0){
		    					ArrayList<Hobby> hobbys = (ArrayList<Hobby>)MasterDataManager.getAllHobbyByCountry(idCountry, idHobbyGroup, idLanguage);
		    					json = gson.toJson(hobbys);
        					}
        					else{
		    					ArrayList<Hobby> hobbys = (ArrayList<Hobby>)MasterDataManager.getAllHobby(idHobbyGroup, idLanguage);
		    					json = gson.toJson(hobbys);
        					}
        				}
                    }
        			else if(service.equals("HobbyGroup")){
        				if(idLanguage>0){
        					if(idHobbyGroup>0){
        						HobbyGroup hobbyGroup = MasterDataManager.getHobbyGroup(idHobbyGroup, idLanguage);
        						json = gson.toJson(hobbyGroup);
        					}
        					else{
        						ArrayList<HobbyGroup> hobbyGroups = (ArrayList<HobbyGroup>)MasterDataManager.getAllHobbyGroup(idLanguage);
        						json = gson.toJson(hobbyGroups);
        					}
        				}
                    }
        			else if(service.equals("Industry")){
        				if(idLanguage>0){
        					if(idIndustry>0){
        						Industry industry = MasterDataManager.getIndustry(idIndustry, idLanguage);
        						json = gson.toJson(industry);
        					}
        					else{
        						ArrayList<Industry> industries = (ArrayList<Industry>)MasterDataManager.getAllIndustry(idLanguage);
        						json = gson.toJson(industries);
        					}
        				}
                    }
        			else if(service.equals("JobField")){
        				if(idLanguage>0){
        					if(idJobField>0){
        						JobField jobField = MasterDataManager.getJobField(idJobField, idLanguage);
        						json = gson.toJson(jobField);
        					}
        					else{
        						ArrayList<JobField> jobFields = (ArrayList<JobField>)MasterDataManager.getAllJobField(idLanguage);
        						json = gson.toJson(jobFields);
        					}
        				}
                    }
        			else if(service.equals("Language")){
        				if(idLanguage>0){
        					Language language = MasterDataManager.getLanguage(idLanguage);
        					json = gson.toJson(language);
        				}
        				else{
        					ArrayList<Language> languages = (ArrayList<Language>)MasterDataManager.getAllLanguage();
    						json = gson.toJson(languages);
        				}
        			}
        			else if(service.equals("LanguageLevel")){
        				if(idLanguage>0){
        					if(idLevelLanguage>0){
        						LanguageLevel languageLevel= MasterDataManager.getLanguageLevel(idLevelLanguage, idLanguage);
        						json = gson.toJson(languageLevel);
        					}
        					else{
        						ArrayList<LanguageLevel> languageLevels = (ArrayList<LanguageLevel>)MasterDataManager.getAllLanguageLevel(idLanguage);
        						json = gson.toJson(languageLevels);
        					}
        				}
                    }
        			else if(service.equals("Major")){
        				if(idFaculty>0&&idLanguage>0){
        					if(idMajor>0){
        						Major major = MasterDataManager.getMajor(idFaculty, idMajor, idLanguage);
        						json = gson.toJson(major);
        					}
        					else{
        						ArrayList<Major> major = (ArrayList<Major>)MasterDataManager.getAllMajor(idFaculty, idLanguage);
        						json = gson.toJson(major);
        					}
        				}
                    }
        			else if(service.equals("Proficiency")){
        				if(idLanguage>0){
        					if(idProficiency>0){
        						Proficiency proficiency = MasterDataManager.getProficiency(idLanguage, idProficiency);
        						json = gson.toJson(proficiency);
        					}
        					else{
        						ArrayList<Proficiency> proficiencies = (ArrayList<Proficiency>)MasterDataManager.getAllProficiency(idLanguage);
        						json = gson.toJson(proficiencies);
        					}
        				}
                    }
        			else if(service.equals("Region")){
        				if(idLanguage>0&&idRegion>0){
        					Region region = MasterDataManager.getRegion(idRegion, idLanguage);
        					json = gson.toJson(region);
        				}
                    }
        			else if(service.equals("Salary")){
        				if(idLanguage>0){
        					if(idSalaryType>0){
        						Salary salary = MasterDataManager.getSalaryType(idSalaryType, idLanguage);
        						json = gson.toJson(salary);
        					}
        					else{
        						ArrayList<Salary> salaries = (ArrayList<Salary>)MasterDataManager.getAllSalaryType(idLanguage);
        						json = gson.toJson(salaries);
        					}
        				}
        				else{
        					ArrayList<Integer> salaries = (ArrayList<Integer>)MasterDataManager.getAllSalary();
    						json = gson.toJson(salaries);
        				}
                    }
        			else if(service.equals("SalaryPer")){
        				if(idLanguage>0){
        					if(idLanguage>0&&idPer>0){
        						SalaryPer salaryPer = MasterDataManager.getSalaryPer(idPer, idLanguage);
        						json = gson.toJson(salaryPer);
        					}
        					else{
        						ArrayList<SalaryPer> salaryPers = (ArrayList<SalaryPer>)MasterDataManager.getAllSalaryPer(idLanguage);
        						json = gson.toJson(salaryPers);
        					}
        				}
                    }
        			else if(service.equals("School")){
        				if(idCountry>0){
        					if(idLanguage>0){
        						if(idState>0){
        							if(idSchool>0){
        								School school = MasterDataManager.getSchool(idCountry, idState, idSchool, idLanguage);
        								json = gson.toJson(school);
        							}
        							else{
        								ArrayList<School> schools = (ArrayList<School>)MasterDataManager.getAllSchool(idCountry, idState, idLanguage);
        								json = gson.toJson(schools);
        							}
        						}
        						else{
        							ArrayList<School> schools = (ArrayList<School>)MasterDataManager.getAllSchoolByIdCountryIdLanguage(idCountry, idLanguage);
        							json = gson.toJson(schools);
        						}
        					}
        					else{
        						ArrayList<School> schools = (ArrayList<School>)MasterDataManager.getAllSchoolByIdCountry(idCountry);
        						json = gson.toJson(schools);
        					}
        					
        				}
                    }
        			else if(service.equals("SkillLevel")){
        				if(idLanguage>0){
        					if(idLevelSkill>0){
        						SkillLevel skillLevel = MasterDataManager.getSkillLevel(idLevelSkill, idLanguage);
        						json = gson.toJson(skillLevel);
        					}
        					else{
        						ArrayList<SkillLevel> skillLevels = (ArrayList<SkillLevel>)MasterDataManager.getAllSkillLevel(idLanguage);
        						json = gson.toJson(skillLevels);
        					}
        				}
        			}
        			else if(service.equals("State")){
        				System.out.println("Language:"+idLanguage+"->idCountry:"+idCountry);
        				if(idLanguage>0&&idCountry>0){
        					if(idState>0){
        						State state = MasterDataManager.getState(idCountry, idState, idLanguage);
        						json = gson.toJson(state);
        					}
        					else{
        						ArrayList<State> states = (ArrayList<State>)MasterDataManager.getAllState(idCountry, idLanguage);
        						json = gson.toJson(states);
        					}
        				}
        			}
        			else if(service.equals("Strength")){
        				if(idLanguage>0){
        					if(idStrength>0){
        						Strength strength = MasterDataManager.getStrength(idStrength, idLanguage);
        						json = gson.toJson(strength);
        					}
        					else{
        						ArrayList<Strength> strengths = (ArrayList<Strength>)MasterDataManager.getAllStrength(idLanguage);
        						json = gson.toJson(strengths);
        					}
        				}
        			}
        			else if(service.equals("SubField")){
        				if(idLanguage>0&&idJobField>0){
        					if(idSubField>0){
        						SubField subField = MasterDataManager.getSubField(idJobField, idSubField, idLanguage);
        						json = gson.toJson(subField);
        					}
        					else{
        						ArrayList<SubField> subFields = (ArrayList<SubField>)MasterDataManager.getAllSubField(idJobField, idLanguage);
        						json = gson.toJson(subFields);
        					}
        				}
        			}
        			else if(service.equals("RelateSubFieldPosition")){
        				ArrayList<SubField> subFields = (ArrayList<SubField>)MasterDataManager.getAllSubField(idJobField, idLanguage);
						json = gson.toJson(subFields);
        			}
        			
        			out.println(json);
            	}
        	//}
        }catch(Exception e){
        	e.printStackTrace();
        }
		out.flush();
		out.close();
	}
}
