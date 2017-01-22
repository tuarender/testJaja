package javax.topgun.competency;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import javax.topgun.competency.CheckUpHobby;
import javax.topgun.competency.CheckUpHobbyManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.Util;

public class CheckUpAptitudeServlet extends HttpServlet 
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
		
        int idUser=Util.getInt(request.getSession().getAttribute("SESSION_ID_USER"));
        String idAptitude="";
		if(idUser>0)
		{
			String [] aptitude =  request.getParameterValues("aptitude");
			if(aptitude!=null)
			{
				for (int i = 0; i < aptitude.length ; i++)
				{
					if(Util.getInt((aptitude[i]))>0)
					{
						com.topgun.shris.masterdata.Hobby result=MasterDataManager.getHobby(Util.getInt(aptitude[i]), 11);
						if(result!=null)
						{
							if(new CheckUpHobbyManager().checkAptitude(idUser, Util.getInt(aptitude[i]))==false)
							{
								int idGroup=result.getIdGroup();
								CheckUpHobby hob=new CheckUpHobby();
								hob.setIdUser(idUser);
								hob.setIdHobby(Util.getInt(aptitude[i])); 
								hob.setIdGroup(idGroup);
								hob.setSkill(0);
								new CheckUpHobbyManager().add(hob);
							}
							if(i==0)
							{	idAptitude=aptitude[i];}
							else
							{	idAptitude+=","+aptitude[i];}
						}
					}
				}
				new CheckUpHobbyManager().deleteNotChoose(idUser,idAptitude);
			}
			List<CheckUpHobby> countAptitude=new CheckUpHobbyManager().getAll(idUser);
			LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
			json.put("success",1);
			json.put("countAptitude",countAptitude.size());
			out.print(gson.toJson(json));
		}
		else //error
		{
			List<CheckUpHobby> countAptitude=new CheckUpHobbyManager().getAll(idUser);
			LinkedHashMap<String,Object> json = new LinkedHashMap<String,Object>();
			json.put("success",0);
			json.put("countAptitude",countAptitude.size());
			out.print(gson.toJson(json));
		}
	}

}
