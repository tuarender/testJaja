package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Reference;
import com.topgun.resume.ReferenceManager;
import com.topgun.shris.masterdata.CertSubFieldLanguage;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class ReferenceServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Reference> referenceList = new ArrayList<Reference>();
        ArrayList<String[]> referenceListDetail = new ArrayList<String[]>();
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"),216);
		//int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"),38);
		//String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		int idLanguage=Util.getInt(request.getParameter("idLanguage"));
		String locale;
		if(idLanguage==11)
		{
			locale="en_TH";
		}
		else
		{
			locale="th_TH";
		}
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String urlErrorRedirect = "";
		
		String service=Util.getStr(request.getParameter("service"));
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			if(service.equals("getAllReference")){
				referenceList.clear();
				referenceList = (ArrayList<Reference>)new ReferenceManager().getAll(idJsk, idResume);
			}
			if(service.equals("getReferenceList")){
				ArrayList<Reference> referenceData = (ArrayList<Reference>)new ReferenceManager().getAll(idJsk, idResume);
				for(int i=0;i<referenceData.size();i++){
					String refType = "";
					if(referenceData.get(i).getRefType().equals("2")){
						refType = propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_CURRENT");
					}
					else if(referenceData.get(i).getRefType().equals("1")){
						refType = propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_PREVIOUS");
					}
					else if(referenceData.get(i).getRefType().equals("3")){
						refType = propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_PROFESSOR");
					}
					else if(referenceData.get(i).getRefType().equals("4")){
						refType = propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_OTHER");
						if(!referenceData.get(i).getOthRefType().equals("")){
							refType+= "("+referenceData.get(i).getOthRefType()+")";
						}
					}
					else if(referenceData.get(i).getRefType().equals("5")){
						refType = propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_BUSINESS");
					}
					else if(referenceData.get(i).getRefType().equals("6")){
						refType = propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_BUSINESS_COLLEAGUE");
					}
					String data[] = new String[8];
					data[0] = Util.getStr(referenceData.get(i).getId());
					data[1] = Util.getStr(refType);
					data[2] = Util.getStr(referenceData.get(i).getRefCompany());
					data[3] = Util.getStr(referenceData.get(i).getRefTitle());
					data[4] = Util.getStr(referenceData.get(i).getRefTel());
					data[5] = Util.getStr(referenceData.get(i).getRefType());
					data[6] = Util.getStr(referenceData.get(i).getOthRefType());
					data[7] = Util.getStr(referenceData.get(i).getRefName());
					referenceListDetail.add(data);
				}
			}
			else if(service.equals("getEditReference")){
				int idReference = Util.getInt(request.getParameter("idReference"), 0);
				Reference reference = new ReferenceManager().get(idJsk, idResume, idReference);
				if(reference!=null){
					String data[] = new String[7];
					data[0] = Util.getStr(reference.getId());
					data[1] = Util.getStr(reference.getRefCompany());
					data[2] = Util.getStr(reference.getRefTitle());
					data[3] = Util.getStr(reference.getRefTel());
					data[4] = Util.getStr(reference.getRefType());
					data[5] = Util.getStr(reference.getOthRefType());
					data[6] = Util.getStr(reference.getRefName());
					referenceListDetail.add(data);
				}
			}
			else if(service.equals("saveReference")){
				int idReference = Util.getInt(request.getParameter("idReference"), 0);
				String referenceType = Util.getStr(request.getParameter("referenceType"),"");
				String referenceTypeOther = Util.getStr(request.getParameter("referenceTypeOther"));
				String referenceName = Util.getStr(request.getParameter("referenceName"));
				String referenceCompany = Util.getStr(request.getParameter("referenceCompany"));
				String referenceTitle = Util.getStr(request.getParameter("referenceTitle"));
				String referenceTelephone = Util.getStr(request.getParameter("referenceTelephone"));
				if(referenceType.equals("")){
					errors.add(propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_REQUIRED"));
					elements.add("certificateField");
				}
				else if(referenceType.equals("4")&&referenceTypeOther.equals("")){
					errors.add(propMgr.getMessage(locale, "REFERENCES_RELATIONSHIP_OTHER_REQUIRED"));
					elements.add("certificateName");
				}
				else if(referenceTelephone.equals("")){
					errors.add(propMgr.getMessage(locale, "REFERENCES_TELEPHONE_REQUIRED"));
					elements.add("certificateIssue");
				}
				if(errors.size()==0){
					Reference ref = new ReferenceManager().get(idJsk, idResume, idReference);
					if(ref!=null){
						ref.setRefType(referenceType);
						ref.setOthRefType(referenceTypeOther);
						ref.setRefName(referenceName);
						ref.setRefCompany(referenceCompany);
						ref.setRefTitle(referenceTitle);
						ref.setRefTel(referenceTelephone);
						if(new ReferenceManager().update(ref)!=1){
							errors.add(propMgr.getMessage(locale, "REFERENCES_INSERT_ERROR"));
							elements.add("SYSTEM");
						}
					}
					else{
						if(new ReferenceManager().getAll(idJsk, idResume).size()<2){
							ref = new Reference();
							ref.setIdJsk(idJsk);
							ref.setIdResume(idResume);
							ref.setId(new ReferenceManager().getNextId(idJsk, idResume));
							ref.setRefType(referenceType);
							ref.setOthRefType(referenceTypeOther);
							ref.setRefName(referenceName);
							ref.setRefCompany(referenceCompany);
							ref.setRefTitle(referenceTitle);
							ref.setRefTel(referenceTelephone);
							if(new ReferenceManager().add(ref)!=1){
								errors.add(propMgr.getMessage(locale, "REFERENCES_INSERT_ERROR"));
								elements.add("SYSTEM");
							}
						}
						else{
							errors.add(propMgr.getMessage(locale, "REFERENCES_MAX"));
							elements.add("SYSTEM");
						}
					}
				}
			}
			else if(service.equals("deleteReference")){
				int idReference = Util.getInt(request.getParameter("idReference"), 0);
				Reference reference = new ReferenceManager().get(idJsk, idResume, idReference);
				if(reference!=null){
					if(new ReferenceManager().delete(reference)!=1){
						errors.add(propMgr.getMessage(locale, "REFERENCES_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "REFERENCES_NOT_FOUND"));
					elements.add("SYSTEM");
				}
			}
			else{
				errors.add(propMgr.getMessage(locale, "STRENGTH_SERVICE_REQUIRE"));
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
			json.put("referenceList", referenceList);
			json.put("referenceListDetail", referenceListDetail);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nReferenceServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
		
	}

}
