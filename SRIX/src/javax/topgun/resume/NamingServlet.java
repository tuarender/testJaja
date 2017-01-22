package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;
import com.topgun.util.Encoder;

public class NamingServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        PropertiesManager propMgr=new PropertiesManager();
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String urlErrorRedirect = "";
		String service=Util.getStr(request.getParameter("service"));
		String idSession=Encoder.getEncode(request.getSession().getId());
		LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
		int sequence=Util.getInt(request.getParameter("sequence"));
		ResumeManager rsmMgr = new ResumeManager();
		if(idJsk<=0)
		{
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else
		{
			if(service.equals("EDIT"))
			{
				String resumeName=Util.getStr(request.getParameter("resumeName"));
				int idCountry=Util.getInt(request.getParameter("idCountry"));
				String  privacy=Util.getStr(request.getParameter("privacy"));
				if(resumeName!="" && idCountry!=-1  && privacy!="")
				{ 
					Resume resume=new ResumeManager().get(idJsk, idResume);
					if(resume!=null)
					{
						resume.setResumeName(resumeName);
						resume.setIdCountry(idCountry);
						resume.setResumePrivacy(privacy);
						if(new ResumeManager().update(resume)!=1)
						{
							errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
							elements.add("SYSTEM");
							urlErrorRedirect = "/LogoutServ";
						}
						else
						{
							json.put("idResume",idResume);
						}
					}
					else
					{
						errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
						elements.add("SYSTEM");
						urlErrorRedirect = "/LogoutServ";
					}
					
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND"));
					elements.add("SYSTEM");
				}
			}
			if(service.equals("COPY") || service.equals("CREATE"))
			{
				String resumeName=Util.getStr(request.getParameter("resumeName"));
				int idCountry=Util.getInt(request.getParameter("idCountry"));
				int idLanguage=Util.getInt(request.getParameter("idLanguage"));
				String  privacy=Util.getStr(request.getParameter("privacy"));
				
				int source=Util.getInt(request.getParameter("source"));
				if(source==1)
				{
					idResume=new ResumeManager().getLatestCompleteIdResume(idJsk);
				}
				
				int templateIdCountryDst=231;
				if(resumeName!="" && idCountry!=-1 && idLanguage!=-1 && privacy!="")
				{ 
					templateIdCountryDst=idCountry;
					
					int idResumeDst=new ResumeManager().copyResume(idJsk, idResume, resumeName, idCountry, idLanguage, templateIdCountryDst, privacy);
					if(idResumeDst<0)
					{
						errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
						elements.add("SYSTEM");
						urlErrorRedirect = "/LogoutServ";
					}
					else
					{
						json.put("idResumeDst",idResumeDst);
					}
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "GLOBAL_NOT_FOUND"));
					elements.add("SYSTEM");
				}
			}
			if(service.equals("DELETE"))
			{
				if(idJsk>0 && idResume>0)
				{
					if(new ResumeManager().delete(idJsk, idResume)!=1)
					{
						errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
						elements.add("SYSTEM");
						urlErrorRedirect = "/LogoutServ";
					}
					else
					{
						List<Resume> subResumeList = new ResumeManager().getSubResume(idJsk, idResume);
						for(int k=0;k<subResumeList.size();k++)
						{
			    			int idResumeSub = subResumeList.get(k).getIdResume();
			    			new ResumeManager().delete(idJsk, idResumeSub);
						}
						response.sendRedirect("/SRIX?view=home&jSession="+idSession);
					}
				}
			}
			if(service.equals("EVOLUTION"))
			{
				if(idJsk>0 && idResume>0)
				{
					Resume childResume = rsmMgr.get(idJsk, idResume);
					String resumeName=Util.getStr(request.getParameter("resumeName"),childResume.getResumeName());
					int idCountry=Util.getInt(request.getParameter("idCountry"),childResume.getIdCountry());
					int idLanguage=Util.getInt(request.getParameter("idLanguage"),childResume.getIdLanguage());
					int idResumeDel=Util.getInt(request.getParameter("idResumeDel"));
					String  privacy=Util.getStr(request.getParameter("privacy"),childResume.getResumePrivacy());
					int templateIdCountryDst=231;
					if(idCountry>0)
					{
						templateIdCountryDst = idCountry;
					}
					int idResumeDst=new ResumeManager().copyResume(idJsk, childResume.getIdResume(), resumeName, idCountry, idLanguage, templateIdCountryDst, privacy);
					Resume newResume = rsmMgr.get(idJsk, idResumeDst);
					newResume.setIdParent(0);
					if(idResumeDel>0)
					{
						if(new ResumeManager().delete(idJsk, idResumeDel)!=1)
						{
							errors.add(propMgr.getMessage(locale, "EDU_UPDATE_ERROR"));
							elements.add("SYSTEM");
							urlErrorRedirect = "/LogoutServ";
						}
						else
						{
							List<Resume> subResumeList = new ResumeManager().getSubResume(idJsk, idResumeDel);
							for(int k=0;k<subResumeList.size();k++)
							{
				    			int idResumeSub = subResumeList.get(k).getIdResume();
				    			new ResumeManager().delete(idJsk, idResumeSub);
							}
						}
					}
					if(idResumeDst>0 && new ResumeManager().update(newResume)!=1)
					{
						errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
						elements.add("SYSTEM");
						urlErrorRedirect = "/LogoutServ";
					}else{
						json.put("idResumeDst", idResumeDst);
					}
				}else{
					errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
					elements.add("SYSTEM");
					urlErrorRedirect = "/LogoutServ";
				}
			}
			
			if(service.equals("GENERATEPARENT"))
			{
				String resumeName=Util.getStr(request.getParameter("resumeName"));
				int idCountry=Util.getInt(request.getParameter("idCountry"));
				String  privacy=Util.getStr(request.getParameter("privacy"));
				int idResumeDel=Util.getInt(request.getParameter("idResumeDel"));
				int templateIdCountryDst=231;
				int idLanguage=11;
				int idResumeDst = -1;
				if(idCountry>0)
				{
					templateIdCountryDst = idCountry;
				}
				Resume resume=new ResumeManager().get(idJsk, idResume);
				if(resume!=null)
				{
					idLanguage = resume.getIdLanguage();
					if(resumeName!="" && idCountry!=-1  && privacy!="")
					{ 
						idResumeDst = new ResumeManager().copyResume(idJsk, idResume, resumeName, idCountry, idLanguage, templateIdCountryDst, privacy);
						if(idResumeDel!=-1)
						{
							rsmMgr.delete(idJsk,idResumeDel);
						}
					}
				}
				if(idResumeDst!=-1)
				{
					json.put("idResumeDst", idResumeDst);
				}else{
					errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
					elements.add("SYSTEM");
					urlErrorRedirect = "/LogoutServ";
				}
			}
			if(service.equals("REGISTERFINISH"))
			{
				idResume=new ResumeManager().getLatestCompleteIdResume(idJsk);
				Resume resume=new ResumeManager().get(idJsk, 0);
				int idResumeDst=new ResumeManager().copyResume(idJsk, idResume, "My Super Resume", resume.getApplyIdCountry(), resume.getIdLanguage(), resume.getTemplateIdCountry(), resume.getResumePrivacy());
				response.sendRedirect("/SRIX?view=strength&idResume="+idResumeDst+"&sequence="+sequence);
			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
		
		if(errors.size()>0){
			json.put("success",0);
			json.put("errors", errors);
			json.put("elements", elements);
			json.put("urlError", urlErrorRedirect);
		}
		else{
			json.put("success",1);
		}
		out.print(gson.toJson(json));
		out.close();
		
	}

}
