package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.Strength;
import com.topgun.resume.StrengthManager;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class StrengthServlet  extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<String[]> strengthData = new ArrayList<String[]>();
        PropertiesManager propMgr=new PropertiesManager();
        
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		int idCountry=Util.getInt(request.getSession().getAttribute("SESSION_ID_COUNTRY"),216);
		int idLanguage=Util.getInt(request.getParameter("SESSION_ID_LANGUAGE"),38);
		String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		int idResume=Util.getInt(request.getParameter("idResume"),0);
		String urlErrorRedirect = "";
		
		String service=Util.getStr(request.getParameter("service"));
		if(idJsk<=0)
		{
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else
		{
			if(service.equals("addStrength"))
			{
				new StrengthManager().deleteAllStrengthOther(idJsk, idResume);
				String [] strength =  request.getParameterValues("strength");
				if(strength!=null)
				{
					String otherStrength=Util.getStr(request.getParameter("otherStrength"));
					if(!otherStrength.equals(""))
					{
						Strength st=new Strength();
						st.setIdJsk(idJsk);
						st.setIdResume(idResume);
						st.setIdStrength(-1);
						st.setOthStrength(otherStrength);
						st.setReason("");
						st.setStrengthReason("");
						st.setStrengthOrder(9);
						new StrengthManager().add(st);
					}
					String idStrength="";
					for (int i = 0; i < strength.length ; i++)
					{
						if(new StrengthManager().checkStrength(idJsk, idResume,  Util.getInt(strength[i]))==false)
						{
							Strength st=new Strength();
							st.setIdJsk(idJsk);
							st.setIdResume(idResume);
							st.setIdStrength(Util.getInt(strength[i]));
							st.setOthStrength("");
							st.setReason("");
							st.setStrengthReason("");
							st.setStrengthOrder(9);
							new StrengthManager().add(st);
						}
						if(i==0 || idStrength=="")
						{	idStrength=strength[i];}
						else
						{	idStrength+=","+strength[i];}
					}
					if(idStrength!="")
					{
						new StrengthManager().deleteNotChoose(idJsk, idResume, idStrength);
					}
					else
					{
						errors.add(propMgr.getMessage(locale, "STRENGTH_REQUIRE"));
						elements.add("SYSTEM");
					}
					/*if(strength.length==5)
					{
						new StrengthManager().updateAllRank(idJsk, idResume);
					}*/
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "STRENGTH_MAX_REQUIRE"));
					elements.add("SYSTEM");
				}
			}
			else if(service.equals("updateDetail"))
			{
				int idStrength = Util.getInt(request.getParameter("idStrength"));
				String detail = Util.getStr(request.getParameter("detail"));
				Strength strength = new StrengthManager().get(idJsk, idResume, idStrength);
				if(strength!=null)
				{
					strength.setStrengthReason(detail);
					if(new StrengthManager().update(strength)!=1)
					{
						errors.add(propMgr.getMessage(locale, "STRENGTH_UPDATE_ERROR"));
						elements.add("SYSTEM");
						
					}
					else
					{
						elements.add("SYSTEM");
					}
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "STRENGTH_CANT_FOUND")+" "+propMgr.getMessage(locale, "GLOBAL_OR")+" "+propMgr.getMessage(locale, "STRENGTH_ORDER_DETAIL"));
					elements.add("SYSTEM");
				}
			}
			else if(service.equals("getAllStrength"))
			{
				ArrayList<Strength> strengths = (ArrayList<Strength>)new StrengthManager().getAll(idJsk, idResume);
				for(int i=0;i<strengths.size();i++)
				{
					String data[] = new String[4];
					data[0] = ""+strengths.get(i).getIdStrength();
					if(strengths.get(i).getIdStrength()<0)
					{
						data[1] = strengths.get(i).getOthStrength();
					}
					else
					{
						data[1] = MasterDataManager.getStrength(strengths.get(i).getIdStrength(), idLanguage).getStrengthName();
					}
					data[2] = ""+strengths.get(i).getStrengthOrder();
					data[3] = ""+strengths.get(i).getStrengthReason();
					strengthData.add(data);
				}
			}
			else if(service.equals("getAllStrengthChoose"))
			{
				ArrayList<Strength> strengths = (ArrayList<Strength>)new StrengthManager().getAllChoose(idJsk, idResume);
				for(int i=0;i<strengths.size();i++)
				{
					String data[] = new String[4];
					data[0] = ""+strengths.get(i).getIdStrength();
					if(strengths.get(i).getIdStrength()<0)
					{
						data[1] = strengths.get(i).getOthStrength();
					}
					else
					{
						data[1] = MasterDataManager.getStrength(strengths.get(i).getIdStrength(), idLanguage).getStrengthName();
					}
					data[2] = ""+strengths.get(i).getStrengthOrder();
					data[3] = ""+strengths.get(i).getStrengthReason();
					strengthData.add(data);
				}
			}
			else if(service.equals("delete"))
			{
				int idStrength = Util.getInt(request.getParameter("idStrength"));
				Strength strength = new StrengthManager().get(idJsk, idResume, idStrength);
				if(strength!=null)
				{
					if(new StrengthManager().delete(strength)!=1)
					{
						errors.add(propMgr.getMessage(locale, "STRENGTH_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "STRENGTH_SERVICE_REQUIRE"));
					elements.add("SYSTEM");
				}
			}
			else if(service.equals("updateChoose"))
			{
				String [] stList =  request.getParameterValues("strength");
				if(stList!=null)
				{
					if(stList.length==5)
					{
						new StrengthManager().updateAllNotRank(idJsk, idResume);
						for (int i = 0; i < stList.length ; i++)
						{
							Strength strength = new StrengthManager().get(idJsk, idResume, Util.getInt(stList[i]));
							if(strength!=null)
							{
								strength.setStrengthOrder(9);
								if(new StrengthManager().update(strength)!=1)
								{
									errors.add(propMgr.getMessage(locale, "STRENGTH_DELETE_ERROR"));
									elements.add("SYSTEM");
								}
							}
							else
							{
								errors.add(propMgr.getMessage(locale, "STRENGTH_SERVICE_REQUIRE"));
								elements.add("SYSTEM");
							}
						}
					}
					else
					{
						errors.add(propMgr.getMessage(locale, "STRENGTH_REQUIRE"));
						elements.add("SYSTEM");
					}
					
				}
				else
				{
					errors.add(propMgr.getMessage(locale, "STRENGTH_SERVICE_REQUIRE"));
					elements.add("SYSTEM");
				}
			}
			else if(service.equals("orderStrength"))
			{
				new StrengthManager().clearOrderStrength(idJsk,idResume);//Clear old order
				for(int i=0;i<5;i++)
				{
					int idStrength = Util.getInt(request.getParameter("strength["+i+"][id]"));
					int idOrder = Util.getInt(request.getParameter("strength["+i+"][idOrder]"));
					Strength strength = new StrengthManager().get(idJsk, idResume, idStrength);
					if(strength!=null)
					{
						strength.setStrengthOrder(idOrder);
						if(new StrengthManager().update(strength)!=1)
						{
							errors.add(propMgr.getMessage(locale, "STRENGTH_UPDATE_ERROR"));
							elements.add("SYSTEM");
						}
					}
					
					
				}
			}
			else if(service.equals("orderDetail"))
			{
				int countStrength = Util.getInt(request.getParameter("countStrength"));
				for(int i=0; i<countStrength; i++)
				{
					int idStrength = Util.getInt(request.getParameter("strength["+i+"][id]"));
					int idOrder = Util.getInt(request.getParameter("strength["+i+"][idOrder]"));
					Strength strength = new StrengthManager().get(idJsk, idResume, idStrength);
					if(strength!=null)
					{
						if(new StrengthManager().update(idJsk, idResume, idStrength, idOrder)!=1)
						{
							errors.add(propMgr.getMessage(locale, "STRENGTH_UPDATE_ERROR"));
							elements.add("SYSTEM");
						}
					}
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
			json.put("strengths", strengthData);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		if(usageTime>500){
			System.out.println("\nStrengthServlet => Usage "+usageTime+" msec");
			System.out.println("idJsk=> "+idJsk);
		}
		
	}

}
