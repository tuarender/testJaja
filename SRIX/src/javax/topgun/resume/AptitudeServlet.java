package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Hobby;
import com.topgun.resume.HobbyManager;
import com.topgun.resume.PetManager;
import com.topgun.resume.ReadingManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.ResumeStatusManager;
import com.topgun.resume.StrengthManager;
import com.topgun.resume.TravelManager;
import com.topgun.shris.masterdata.HobbyGroup;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.Util;

public class AptitudeServlet extends HttpServlet 
{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
        Gson gson = new Gson();
		
        int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
        int idResume=Util.getInt(request.getParameter("idResume"));
		ResumeManager rsmMgr=new ResumeManager();
		if(idJsk>0  && idResume>=0)
		{
			new HobbyManager().deleteAllHbOther(idJsk, idResume);
			List<HobbyGroup> hobbyGroups = MasterDataManager.getAllHobbyGroup(11);
			if(hobbyGroups.size()>0)
			{
				for (int i = 1; i <= hobbyGroups.size() ; i++)
				{
					String otherHobby1=Util.getStr(request.getParameter("othHobby_1"+i));
					String otherHobby2=Util.getStr(request.getParameter("othHobby_2"+i));
					if(!otherHobby1.equals(""))
					{
						Hobby hob=new Hobby();
						hob.setIdJsk(idJsk);
						hob.setIdResume(idResume);
						hob.setIdHobby(-1);
						hob.setOthHobby(otherHobby1);
						hob.setIdGroup(i);
						hob.setSkill(0);
						new HobbyManager().add(hob);
					}
					if(!otherHobby2.equals(""))
					{
						Hobby hob=new Hobby();
						hob.setIdJsk(idJsk);
						hob.setIdResume(idResume);
						hob.setIdHobby(-2);
						hob.setOthHobby(otherHobby2);
						hob.setIdGroup(i);
						hob.setSkill(0);
						new HobbyManager().add(hob);
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
			String [] aptitude =  request.getParameterValues("aptitude");
			if(aptitude!=null)
			{
				String idAptitude="";
				for (int i = 0; i < aptitude.length ; i++)
				{
					//if(Util.getInt((aptitude[i]))>0)
					{
						com.topgun.shris.masterdata.Hobby result=MasterDataManager.getHobby(Util.getInt(aptitude[i]), 11);
						if(result!=null)
						{
							int idGroup=result.getIdGroup();
							if(new HobbyManager().checkHobby(idJsk, idResume, Util.getInt(aptitude[i]), idGroup)==false)
							{
								Hobby hob=new Hobby();
								hob.setIdJsk(idJsk);
								hob.setIdResume(idResume);
								hob.setIdHobby(Util.getInt(aptitude[i])); 
								hob.setIdGroup(idGroup);
								hob.setSkill(0);
								new HobbyManager().add(hob);
							}
							if(i==0 || idAptitude=="")
							{	idAptitude=aptitude[i];}
							else
							{	idAptitude+=","+aptitude[i];}
						}
					}
				}
				
				if(idAptitude!="")
				{
					new HobbyManager().deleteNotChoose(idJsk, idResume, idAptitude);
					// check Travel, Reading, Pet
					if(idAptitude.indexOf("43")==-1)
					{
						TravelManager.deleteAllTravel(idJsk, idResume);
					}
					if(idAptitude.indexOf("45")==-1)
					{
						new ReadingManager().deleteAllBook(idJsk, idResume);
					}
					if(idAptitude.indexOf("56")==-1)
					{
						new PetManager().deleteAllPet(idJsk, idResume);
					}
				}
				else
				{
					new HobbyManager().deleteNotChoose(idJsk, idResume);
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
			List<Hobby> countAptitude=new HobbyManager().getAll(idJsk, idResume);
			LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
			json.put("success",1);
			json.put("countAptitude",countAptitude.size());
			out.print(gson.toJson(json));
		}
		else //error
		{
			List<Hobby> countAptitude=new HobbyManager().getAll(idJsk, idResume);
			LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
			json.put("success",0);
			json.put("countAptitude",countAptitude.size());
			out.print(gson.toJson(json));
		}
	}

}
