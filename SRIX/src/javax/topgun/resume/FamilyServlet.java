package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Family;
import com.topgun.resume.FamilyManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class FamilyServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Family> familyList = new ArrayList<Family>();
        ArrayList<String[]> familyListDetail = new ArrayList<String[]>();
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idLanguage=Util.getInt(request.getParameter("idLanguage"));
		String locale;

		locale=Util.getAvailableLanguage(idLanguage)+"_th";
		
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String urlErrorRedirect = "";
		
		String service=Util.getStr(request.getParameter("service"));
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			if(service.equals("getAllFamily")){
				familyList.clear();
				familyList = (ArrayList<Family>)new FamilyManager().getAll(idJsk, idResume);
			}
			else if(service.equals("getFamilyList")){
				familyListDetail.clear();
				Family familyFather = new FamilyManager().get(idJsk, idResume,1);
				Family familyMother = new FamilyManager().get(idJsk, idResume,2);
				ArrayList<Family> familySibling = (ArrayList<Family>)new FamilyManager().getFamilySibling(idJsk, idResume);
				ArrayList<Family> familyChild = (ArrayList<Family>)new FamilyManager().getFamilyChild(idJsk, idResume);
				int idMarried = new FamilyManager().getIdFamilyMarried(idJsk, idResume);
				Family familyMarried = null;
				if(idMarried>0){
					familyMarried = new FamilyManager().get(idJsk, idResume, idMarried);
				}
				if(familyFather!=null){
					this.setFamilyData(familyListDetail,familyFather,propMgr,locale);
				}
				if(familyMother!=null){
					this.setFamilyData(familyListDetail,familyMother,propMgr,locale);
				}
				if(familyMarried!=null){
					this.setFamilyData(familyListDetail,familyMarried,propMgr,locale);
				}
				for(int i=0;i<familySibling.size();i++){
					this.setFamilyData(familyListDetail,familySibling.get(i),propMgr,locale);
				}
				for(int i=0;i<familyChild.size();i++){
					this.setFamilyData(familyListDetail,familyChild.get(i),propMgr,locale);
				}
			}
			else if(service.equals("getEditFamily")){
				familyListDetail.clear();
				int idFamily = Util.getInt(request.getParameter("idFamily"), 0);
				Family family = new FamilyManager().get(idJsk, idResume, idFamily);
				if(family!=null){
					this.setFamilyData(familyListDetail,family,propMgr,locale);
				}
			}
			else if(service.equals("saveFamily")){
				int idFamily = Util.getInt(request.getParameter("idFamily"), 0);
				String familyType = Util.getStr(request.getParameter("familyType"),"");
				String familyName = Util.getStr(request.getParameter("familyName"),"");
				String familyLastName = Util.getStr(request.getParameter("familyLastName"),"");
				String familyTelephone = Util.getStr(request.getParameter("familyTelephone"),"");
				String spouseStatus = Util.getStr(request.getParameter("spouseStatus"),"");
				String aliveStatus = Util.getStr(request.getParameter("aliveStatus"),"");
				String citizenship = Util.getStr(request.getParameter("citizenship"),"");
				String citizenshipOther = Util.getStr(request.getParameter("citizenshipOther"),"");
				int age = Util.getInt(request.getParameter("age"),0);
				String relationship = Util.getStr(request.getParameter("relationship"),"");
				String workStatus = Util.getStr(request.getParameter("workStatus"),"");
				String company = Util.getStr(request.getParameter("company"),"");
				String position = Util.getStr(request.getParameter("position"),"");
				String eduStatus = Util.getStr(request.getParameter("eduStatus"),"");
				String eduStatusDetail = Util.getStr(request.getParameter("eduStatusDetail"),"");
				if(familyType.equals("")){
					errors.add("Not found type");
					//errors.add(propMgr.getMessage(locale, "REFERENCES_INSERT_ERROR"));
					elements.add("familyType");
				}
				else{
					if(familyType.equals("FATHER")||familyType.equals("MOTHER")){
						if(familyName.equals("")){
							errors.add("Not found name");
							elements.add("familyName");
						}
						if(familyLastName.equals("")){
							errors.add("Not found familyLastName");
							elements.add("familyLastName");
						}
						if(aliveStatus.equals("")){
							errors.add("Not found aliveStatus");
							elements.add("aliveStatus");
						}
						if(citizenship.equals("")){
							errors.add("Not found citizenship");
							elements.add("citizenship");
						}
						if(citizenship.equals("OTHER")&&citizenshipOther.equals("")){
							errors.add("Not found citizenshipOther");
							elements.add("citizenshipOther");
						}
						if(workStatus.equals("")){
							errors.add("Not found workStatus");
							elements.add("workStatus");
						}
						if(workStatus.equals("WORKING")||workStatus.equals("RETIRED")){
							if(company.equals("")){
								errors.add("Not found company");
								elements.add("workStatus");
							}
							if(position.equals("")){
								errors.add("Not found position");
								elements.add("position");
							}
						}
						if(familyTelephone.equals("")){
							errors.add("Not found familyTelephone");
							elements.add("familyTelephone");
						}
					}
					else if(familyType.equals("SPOUSE")){
						if(spouseStatus.equals("")){
							errors.add("Not found spouseStatus");
							elements.add("spouseStatus");
						}
						if(spouseStatus.equals("MARRIED")){
							if(familyName.equals("")){
								errors.add("Not found name");
								elements.add("familyName");
							}
							if(familyLastName.equals("")){
								errors.add("Not found familyLastName");
								elements.add("familyLastName");
							}
							if(citizenship.equals("")){
								errors.add("Not found citizenship");
								elements.add("citizenship");
							}
							if(citizenship.equals("OTHER")&&citizenshipOther.equals("")){
								errors.add("Not found citizenshipOther");
								elements.add("citizenshipOther");
							}
							if(workStatus.equals("")){
								errors.add("Not found workStatus");
								elements.add("workStatus");
							}
							if(workStatus.equals("WORKING")||workStatus.equals("RETIRED")){
								if(company.equals("")){
									errors.add("Not found company");
									elements.add("workStatus");
								}
								if(position.equals("")){
									errors.add("Not found position");
									elements.add("position");
								}
							}
							if(familyTelephone.equals("")){
								errors.add("Not found familyTelephone");
								elements.add("familyTelephone");
							}
						}
					}
					else if(familyType.equals("SIBLING")){
						if(familyName.equals("")){
							errors.add("Not found name");
							elements.add("familyName");
						}
						if(familyLastName.equals("")){
							errors.add("Not found familyLastName");
							elements.add("familyLastName");
						}
						if(age<0&&age>100){
							errors.add("Not found age");
							elements.add("age");
						}
						if(relationship.equals("")){
							errors.add("Not found relationship");
							elements.add("relationship");
						}
						if(eduStatus.equals("")){
							errors.add("Not found eduStatus");
							elements.add("eduStatus");
						}
						if(eduStatus.equals("")){
							errors.add("Not found eduStatus");
							elements.add("eduStatus");
						}
						if(eduStatus.equals("LEARNING")&&eduStatusDetail.equals("")){
							errors.add("Not found eduStatusDetail");
							elements.add("eduStatusDetail");
						}
						if(eduStatus.equals("WORKING")){
							if(company.equals("")){
								errors.add("Not found company");
								elements.add("workStatus");
							}
							if(position.equals("")){
								errors.add("Not found position");
								elements.add("position");
							}
						}
						if(familyTelephone.equals("")){
							errors.add("Not found familyTelephone");
							elements.add("familyTelephone");
						}
					}
					else if(familyType.equals("CHILD")){
						if(familyName.equals("")){
							errors.add("Not found name");
							elements.add("familyName");
						}
						if(familyLastName.equals("")){
							errors.add("Not found familyLastName");
							elements.add("familyLastName");
						}
						if(age<0&&age>100){
							errors.add("Not found age");
							elements.add("age");
						}
						if(eduStatus.equals("")){
							errors.add("Not found eduStatus");
							elements.add("eduStatus");
						}
						if(eduStatus.equals("")){
							errors.add("Not found eduStatus");
							elements.add("eduStatus");
						}
						if(eduStatus.equals("LEARNING")&&eduStatusDetail.equals("")){
							errors.add("Not found eduStatusDetail");
							elements.add("eduStatusDetail");
						}
						if(eduStatus.equals("WORKING")){
							if(company.equals("")){
								errors.add("Not found company");
								elements.add("workStatus");
							}
							if(position.equals("")){
								errors.add("Not found position");
								elements.add("position");
							}
						}
						if(familyTelephone.equals("")){
							errors.add("Not found familyTelephone");
							elements.add("familyTelephone");
						}
					}
					else{
						errors.add("Not found type");
						elements.add("familyType");
					}
				}
				
				if(errors.size()==0){
					if(familyType.equals("FATHER")){
						idFamily = 1;
					}
					else if(familyType.equals("MOTHER")){
						idFamily = 2;
					}
					else if(familyType.equals("SPOUSE")){
						idFamily = new FamilyManager().getIdFamilyMarried(idJsk, idResume);
					}
					Family family = new FamilyManager().get(idJsk, idResume, idFamily);
					if(family!=null){
						family.setFamilyStatus(familyType);
						family.setFirstName(familyName);
						family.setLastName(familyLastName);
						family.setDiedStatus(aliveStatus);
						family.setCitizenshipOther(citizenship);
						if(!citizenshipOther.equals("")){
							family.setCitizenshipOther(citizenshipOther);
						}
						if(eduStatus.equals("WORKING")||workStatus.equals("WORKING")||workStatus.equals("RETIRED")){
							if(workStatus.equals("")){
								workStatus = eduStatus;
							}
						}
						if(eduStatus.equals("LEARNING")){
							workStatus = eduStatus;
							company = eduStatusDetail;
						}
						else if(eduStatus.equals("WORKING")||workStatus.equals("WORKING")||workStatus.equals("RETIRED")){
							if(workStatus.equals("")){
								workStatus = eduStatus;
							}
						}
						family.setWorkStatus(workStatus);
						family.setCompany(company);
						family.setPosition(position);
						family.setTelephone(familyTelephone);
						family.setAge(age);
						family.setMarriedStatus(spouseStatus);
						family.setSiblingRelationship(relationship);
						if(new FamilyManager().update(family)!=1){
							errors.add(propMgr.getMessage(locale, "FAMILY_UPDATE_ERROR"));
							elements.add("SYSTEM");
						}
					}
					else{
						if(idFamily<=0){
							idFamily = new FamilyManager().getNextId(idJsk, idResume);
						}
						family = new Family();
						family.setIdJsk(idJsk);
						family.setIdResume(idResume);
						family.setIdFamily(idFamily);
						family.setFamilyStatus(familyType);
						family.setFirstName(familyName);
						family.setLastName(familyLastName);
						family.setDiedStatus(aliveStatus);
						family.setCitizenshipOther(citizenship);
						if(!citizenshipOther.equals("")){
							family.setCitizenshipOther(citizenshipOther);
						}
						if(eduStatus.equals("WORKING")||workStatus.equals("WORKING")||workStatus.equals("RETIRED")){
							if(workStatus.equals("")){
								workStatus = eduStatus;
							}
							family.setWorkStatus(workStatus);
							family.setCompany(company);
							family.setPosition(position);
						}
						else if(eduStatus.equals("LEARNING")){
							family.setWorkStatus(eduStatus);
							family.setCompany(eduStatusDetail);
						}
						else{
							family.setWorkStatus(workStatus);
						}
						family.setTelephone(familyTelephone);
						family.setAge(age);
						family.setMarriedStatus(spouseStatus);
						family.setSiblingRelationship(relationship);
						if(new FamilyManager().add(family)!=1){
							errors.add(propMgr.getMessage(locale, "FAMILY_INSERT_ERROR"));
							elements.add("SYSTEM");
						}
					}
				}
			}
			else if(service.equals("deleteFamily")){
				int idFamily = Util.getInt(request.getParameter("idFamily"), 0);
				Family family = new FamilyManager().get(idJsk, idResume, idFamily);
				if(family!=null){
					if(new FamilyManager().delete(family)!=1){
						errors.add(propMgr.getMessage(locale, "FAMILY_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "FAMILY_NOT_FOUND"));
					elements.add("SYSTEM");
				}
			}
			else{
				errors.add(propMgr.getMessage(locale, "FAMILY_SERVICE_REQUIRE"));
				elements.add("SYSTEM");
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
			json.put("urlError", urlErrorRedirect);
		}
		else{
			json.put("success",1);
			json.put("familyList", familyList);
			json.put("familyListDetail", familyListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nFamilyServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
	}
	
	private void setFamilyData(ArrayList<String[]> familyListDetail,Family family,PropertiesManager propMgr,String locale){
		String workStatus = "";
		String diedStatus = "";
		String siblingStatus = "";
		String familyStatus = "";
		String mariedStatus = "";
		//--------------------work status---------------------
		if(family.getWorkStatus().equals("WORKING")){
			workStatus = propMgr.getMessage(locale, "FAMILY_WORK_STATUS_WORK");
		}
		else if(family.getWorkStatus().equals("RETIRED")){
			workStatus = propMgr.getMessage(locale, "FAMILY_WORK_STATUS_RETIRE");
		}
		else if(family.getWorkStatus().equals("NONWORKING")){
			workStatus = propMgr.getMessage(locale, "FAMILY_WORK_STATUS_NOT");
		}
		else if(family.getWorkStatus().equals("HOUSEWIFE")){
			workStatus = propMgr.getMessage(locale, "FAMILY_MARRIED_WORK_STATUS_HOME");
		}
		else if(family.getWorkStatus().equals("LEARNING")){
			workStatus = propMgr.getMessage(locale, "FAMILY_SIBLING_STATUS_STUDYING");
		}
		//--------------------deied status---------------------
		if(family.getDiedStatus().equals("ALIVE")){
			diedStatus = propMgr.getMessage(locale, "FAMILY_STATUS_ALIVE");
		}
		else if(family.getDiedStatus().equals("DIED")){
			diedStatus = propMgr.getMessage(locale, "FAMILY_STATUS_DIED");
		}
		//-----------------------sibling status-------------------------
		if(family.getSiblingRelationship().equals("OLDER_SISTER")){
			siblingStatus = propMgr.getMessage(locale, "FAMILY_SIBLING_OLDER_SISTER");
		}
		else if(family.getSiblingRelationship().equals("YOUNGER_SISTER")){
			siblingStatus = propMgr.getMessage(locale, "FAMILY_SIBLING_YOUNGER_SISTER");
		}
		else if(family.getSiblingRelationship().equals("OLDER_BROTHER")){
			siblingStatus = propMgr.getMessage(locale, "FAMILY_SIBLING_OLDER_BROTHER");
		}
		else if(family.getSiblingRelationship().equals("YOUNGER_BROTHER")){
			siblingStatus = propMgr.getMessage(locale, "FAMILY_SIBLING_YOUNGER_BROTHER");
		}
		//-----------------------family status-------------------------
		if(family.getFamilyStatus().equals("FATHER")){
			familyStatus = propMgr.getMessage(locale, "FAMILY_FATHER");
		}
		else if(family.getFamilyStatus().equals("MOTHER")){
			familyStatus = propMgr.getMessage(locale, "FAMILY_MOTHER");
		}
		else if(family.getFamilyStatus().equals("SPOUSE")){
			familyStatus = propMgr.getMessage(locale, "FAMILY_MARRIED_STATUS");
			mariedStatus = propMgr.getMessage(locale, "FAMILY_MARRIED_STATUS_"+family.getMarriedStatus());
		}
		else if(family.getFamilyStatus().equals("SIBLING")){
			familyStatus = propMgr.getMessage(locale, "FAMILY_SIBLING");
		}
		else if(family.getFamilyStatus().equals("CHILD")){
			familyStatus = propMgr.getMessage(locale, "FAMILY_CHILD");
		}
		
		String data[] = new String[19];
		data[0] = ""+family.getIdFamily();
		data[1] = family.getFamilyStatus();
		data[2] = family.getFirstName();
		data[3] = family.getLastName();
		data[4] = diedStatus;
		data[5] = family.getCitizenshipOther().equals("THAI")?propMgr.getMessage(locale, "FAMILY_CITIZENSHIP_THAI"):family.getCitizenshipOther();
		data[6] = family.getWorkStatus();
		data[7] = family.getCompany();
		data[8] = family.getPosition();
		data[9] = family.getTelephone();
		data[10] = family.getMarriedStatus();
		data[11] = family.getSiblingRelationship();
		data[12] = ""+family.getAge();
		data[13] = familyStatus;
		data[14] = mariedStatus;
		data[15] = workStatus;
		data[16] = siblingStatus;
		data[17] = family.getDiedStatus();
		data[18] = family.getCitizenshipOther();
		familyListDetail.add(data);
	}

}
