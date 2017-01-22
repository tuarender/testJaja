package javax.topgun.ajax;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.util.Util;

public class JSONServ extends HttpServlet 
{
	private static String convertToUTF8(String s) 
	{
		String result="";
		try
		{
			result=URLEncoder.encode(s, "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String[] jsonURL = {"http://202.142.222.244/JTGServices","http://202.142.222.245/JTGServices"};
		int idx=(int)(new java.util.Date().getTime()%jsonURL.length);
		String urlService = "";
		
		// get Parameter //
		String action = Util.getStr(request.getParameter("action"));
		int idCountry = Util.getInt(request.getParameter("idCountry"));
		int idState = Util.getInt(request.getParameter("idState"));
		int idSchool = Util.getInt(request.getParameter("idSchool"));
		int idDegree = Util.getInt(request.getParameter("idDegree"));
		int idLanguage = Util.getInt(request.getParameter("idLanguage"));
		int idFaculty = Util.getInt(request.getParameter("idFaculty"));
		String keyword = convertToUTF8(Util.getStr(request.getParameter("keyword")));
		if(action.equals("genUniversityByKeyword"))
		{
			if(!keyword.equals(""))
			{
				urlService = jsonURL[idx]+"/iServ.php?action=genUniversityByKeyword&idCountry="+idCountry+"&idState="+idState+"&idLanguage="+idLanguage+"&keyword="+keyword;
			}
		}
		else if(action.equals("getFaculty"))
		{
			if(idDegree>=0 && idLanguage>0)
			{
				urlService = jsonURL[idx]+"/iServ.php?action=getFaculty&idSchool="+idSchool+"&idDegree="+idDegree+"&idLanguage="+idLanguage;
			}
		}
		else if(action.equals("getFacultySR"))
		{
			if(idDegree>=0 && idLanguage>0)
			{
				urlService = jsonURL[idx]+"/iServ.php?action=getFacultySR&idDegree="+idDegree+"&idLanguage="+idLanguage;
			}
		}
		else if(action.equals("getFacultyExclusive"))
		{
			if(idSchool!=0 &&idDegree>=0 && idLanguage>0)
			{
				urlService = jsonURL[idx]+"/iServ.php?action=getFacultyExclusive&idSchool="+idSchool+"&idDegree="+idDegree+"&idLanguage="+idLanguage;
			}
		}
		else if(action.equals("getMajor"))
		{
			if(idDegree>=0 && idLanguage>0 && idFaculty>0)
			{
				urlService = jsonURL[idx]+"/iServ.php?action=getMajor&idSchool="+idSchool+"&idDegree="+idDegree+"&idLanguage="+idLanguage+"&idFaculty="+idFaculty;
			}
		}
		else if(action.equals("getMajorSR"))
		{
			if(idLanguage>0 && idFaculty>0)
			{
				urlService = jsonURL[idx]+"/iServ.php?action=getMajorSR&idSchool="+idSchool+"&idDegree="+idDegree+"&idLanguage="+idLanguage+"&idFaculty="+idFaculty;
			}
		}
		else if(action.equals("getMajorExclusive"))
		{
			if(idSchool!=0 && idDegree>0 && idLanguage>0 && idFaculty>0)
			{
				urlService = jsonURL[idx]+"/iServ.php?action=getMajorExclusive&idSchool="+idSchool+"&idDegree="+idDegree+"&idLanguage="+idLanguage+"&idFaculty="+idFaculty;
			}
		}
		
		if(!urlService.equals(""))
		{
			URL url = new URL(urlService);
			InputStreamReader in=null;
			try
			{
				GZIPInputStream gzis = new GZIPInputStream(url.openStream());
				in = new InputStreamReader(gzis, "UTF-8");
				int ch=0;
				while((ch=in.read())!=-1)
				{
					out.print((char) ch);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(in!=null)
				{
					in.close();
				}
			}
		}
	}

}
