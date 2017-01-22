package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Certificate;
import com.topgun.resume.CertificateManager;
import com.topgun.shris.masterdata.CertSubFieldLanguage;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class CertificateServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Certificate> certificateList = new ArrayList<Certificate>();
        ArrayList<String[]> certificateListDetail = new ArrayList<String[]>();
        ArrayList<CertSubFieldLanguage> certSubfield = new ArrayList<CertSubFieldLanguage>();
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"),216);
		//int idLanguage=Util.getInt(request.getSession().getAttribute("SESSION_ID_LANGUAGE"),38);
		int idLanguage=Util.getInt(request.getParameter("idLanguage"));
		//String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
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
		String localeArray[] = locale.split("_");
		
		String service=Util.getStr(request.getParameter("service"));
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			certificateListDetail.clear();
			if(service.equals("getAllCertificate")){
				certificateList.clear();
				certificateList = (ArrayList<Certificate>)new CertificateManager().getAll(idJsk, idResume);
			}
			if(service.equals("getCertificateList")){
				ArrayList<Certificate> CertificateData = (ArrayList<Certificate>)new CertificateManager().getAll(idJsk, idResume);
				for(int i=0;i<CertificateData.size();i++){
					String data[] = new String[10];
					data[0] = Util.getStr(CertificateData.get(i).getIdCertificate());
					if(CertificateData.get(i).getIdCertField()>0){
						data[1] = MasterDataManager.getCertFieldLanguage(CertificateData.get(i).getIdCertField(), idLanguage).getCertFieldName();
						if(CertificateData.get(i).getIdCertSubfield()!=-1){
							data[2] = MasterDataManager.getCertSubFieldLanguage(CertificateData.get(i).getIdCertField(), idLanguage, CertificateData.get(i).getIdCertSubfield()).getCertSubfieldName();
						}
					}
					data[3] = CertificateData.get(i).getCerName();
					data[4] = Util.DateToStr(CertificateData.get(i).getCerDate(), "MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					data[5] = CertificateData.get(i).getInstitution();
					data[6] = CertificateData.get(i).getDetail();
					data[7] = ""+CertificateData.get(i).getIdCertField();
					data[8] = ""+CertificateData.get(i).getIdCertSubfield();
					data[9] = Util.DateToStr(CertificateData.get(i).getCerDate(), "yyyy-MM");
					certificateListDetail.add(data);
				}
			}
			else if(service.equals("getEditCertificate")){	
				int idCertificate = Util.getInt(request.getParameter("idCertificate"), 0);
				Certificate certificate = new CertificateManager().get(idJsk, idResume, idCertificate);
				if(certificate!=null){
					String data[] = new String[10];
					data[0] = Util.getStr(certificate.getIdCertificate());
					if(certificate.getIdCertField()!=-1){
						data[1] = MasterDataManager.getCertFieldLanguage(certificate.getIdCertField(), idLanguage).getCertFieldName();
						if(certificate.getIdCertSubfield()!=-1){
							data[2] = MasterDataManager.getCertSubFieldLanguage(certificate.getIdCertField(), idLanguage, certificate.getIdCertSubfield()).getCertSubfieldName();
						}
					}
					data[3] = certificate.getCerName();
					if(certificate.getCerDate()!=null)
					{
						data[4] = Util.DateToStr(certificate.getCerDate(), "MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					}
					//data[4] = Util.DateToStr(certificate.getCerDate(), "MMM yyyy",new Locale(localeArray[0],localeArray[1]));
					data[5] = certificate.getInstitution();
					data[6] = certificate.getDetail();
					data[7] = ""+certificate.getIdCertField();
					data[8] = ""+certificate.getIdCertSubfield();
					if(certificate.getCerDate()!=null)
					{
						data[9] = Util.DateToStr(certificate.getCerDate(), "yyyy-MM");
					}
					//data[9] = Util.DateToStr(certificate.getCerDate(), "yyyy-MM");
					certificateListDetail.add(data);
				}
			}
			else if(service.equals("getCertificateSubField")){
				int idCertField = Util.getInt(request.getParameter("idCertificateField"), -1);
				certSubfield = MasterDataManager.getAllCertSubFieldLanguage(idCertField, idLanguage);
			}
			//need change
			else if(service.equals("saveCertificate")){
				int idCertificate = Util.getInt(request.getParameter("idCertificate"), 0);
				int certField = Util.getInt(request.getParameter("certificateField"), -1);
				int certSubField = Util.getInt(request.getParameter("certificateSubField"), -1);
				String certNameOther = Util.getStr(request.getParameter("certificateName"));
				//String certDate = Util.getStr(request.getParameter("certificateDate"));
				String certIssue = Util.getStr(request.getParameter("certificateIssue"));
				String certDetail = Util.getStr(request.getParameter("certificateDetail"));
				int startDay=1;
				int startMonth=Util.getInt(request.getParameter("startMonth"));
				int startYear=Util.getInt(request.getParameter("startYear"));      	
				Date dateStart=Util.getSQLDate(startDay, startMonth, startYear);
				if(certField==0){
					errors.add(propMgr.getMessage(locale, "CERT_FIELD_REQUIRED"));
					elements.add("certificateField");
				}
				else if(certSubField==0){
					errors.add(propMgr.getMessage(locale, "CERT_NAME_REQUIRED"));
					elements.add("certificateSubField");
				}
				else if(certField==-1&&certNameOther.equals("")){
					errors.add(propMgr.getMessage(locale, "CERT_NAME_OTHER_REQUIRED"));
					elements.add("certificateName");
				}
				else if(dateStart.equals("")){
					errors.add(propMgr.getMessage(locale, "CERT_DATE_REQUIRED"));
					elements.add("certificateDate");
				}
				else if(certIssue.equals("")){
					errors.add(propMgr.getMessage(locale, "CERT_ISSUE_REQUIRE"));
					elements.add("certificateIssue");
				}
				/*
				else if(certDate.equals("")){
					errors.add(propMgr.getMessage(locale, "CERT_DATE_REQUIRED"));
					elements.add("certificateDate");
				}*/
				
				if(errors.size()==0){
					Certificate cert = new Certificate();
					cert.setIdJsk(idJsk);
					cert.setIdResume(idResume);
					cert.setIdCertField(certField);
					cert.setCerName(certNameOther);
					cert.setIdCertSubfield(certSubField);
					cert.setInstitution(certIssue);
					//cert.setCerDate(Util.getSQLDate(certDate, "yyyy-MM"));
					if(dateStart!=null)
		        	{
						cert.setCerDate(dateStart);
		        	} 
					cert.setDetail(certDetail);
					if(idCertificate>0){
						cert.setId(idCertificate);
						System.out.println("update");
						if(new CertificateManager().update(cert)!=1){
							errors.add(propMgr.getMessage(locale, "CERT_INSERT_ERROR"));
							elements.add("SYSTEM");
						}
					}
					else{
						System.out.println("add");
						idCertificate = new CertificateManager().getNextId(idJsk, idResume);
						cert.setIdCertificate(idCertificate);
						if(new CertificateManager().add(cert)!=1){
							errors.add(propMgr.getMessage(locale, "CERT_INSERT_ERROR"));
							elements.add("SYSTEM");
						}
					}
				}
			}
			else if(service.equals("deleteCertificate")){
				int idCertificate = Util.getInt(request.getParameter("idCertificate"), 0);
				Certificate certificate = new CertificateManager().get(idJsk, idResume, idCertificate);
				if(certificate!=null){
					if(new CertificateManager().delete(certificate)!=1){
						errors.add(propMgr.getMessage(locale, "CERT_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else{
					errors.add(propMgr.getMessage(locale, "CERT_NOT_FOUND"));
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
			json.put("certificateList", certificateList);
			json.put("certificateListDetail", certificateListDetail);
			json.put("certificateSubField", certSubfield);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		System.out.println("\nCertificateServlet => Usage "+usageTime+" msec");
		System.out.println("idJsk=> "+idJsk);
		
	}
}
