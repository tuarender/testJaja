package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.topgun.resume.*;

import com.topgun.util.Util;

/**
 * Servlet implementation class SyncResume
 */
public class SyncResumeServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//section
		//1=Personal Data
		//2=Target Job
		//3=Education
		//4=Experience
		//5=Strength
		//6=Aptitude
		
		int idResumeSrc=Util.getInt(request.getParameter("idResumeSrc"));
		int section=Util.getInt(request.getParameter("section"));
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		int targetResume=Util.getInt(request.getParameter("targetResume"));
		String targetIdResume[] = request.getParameterValues("targetIdResume");
		int success=0;
		System.out.println("idResumeSrc > "+idResumeSrc);
		System.out.println("section > "+section);
		System.out.println("idJsk > "+idJsk);
		System.out.println("targetResume > "+targetResume);
		
		ResumeManager resumeMgr=new ResumeManager();
		Resume resumeSrc=resumeMgr.get(idJsk, idResumeSrc);
		
		//update all resume
		if(targetResume==1)
		{
			List<Resume> resumeList=new ResumeManager().getAllResumeByIdLanguage(idJsk,resumeSrc.getIdLanguage(),idResumeSrc);
			for(int i=0;i<resumeList.size();i++)
			{
				System.out.println("resumeDst > "+resumeList.get(i).getIdResume());
				resumeList.get(i).setIdResume(getFinalDestIdResume(idJsk, resumeList.get(i).getIdResume()));
				if(section==1)
				{
					Resume resumeDst=resumeMgr.get(idJsk, resumeList.get(i).getIdResume());
					success = copyResumePersonal(resumeSrc,resumeDst);
				}else
				{
					success = copyResume(section,idJsk,idResumeSrc,resumeList.get(i).getIdResume(),resumeList.get(i).getIdLanguage());
				}
			}
		}
		
		
		//update some resume
		if(targetResume==3)
		{
			if(targetIdResume!=null)
			{
				for(int i=0;i<targetIdResume.length;i++)
				{
					int idDst = Integer.parseInt(targetIdResume[i]);
					idDst=getFinalDestIdResume(idJsk,idDst);
					System.out.println("resumeDst > "+idDst);
					if(section==1)
					{
						Resume resumeDst=resumeMgr.get(idJsk, idDst);
						success = copyResumePersonal(resumeSrc,resumeDst);
					}else
					{
						success = copyResume(section,idJsk,idResumeSrc,idDst,resumeSrc.getIdLanguage());
					}
				}
			}
		}
		
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        out.println("1");
	}
	
	private int getFinalDestIdResume(int idJsk, int idResume)
	{
		int result=idResume;
		ResumeManager rsmMgr=new ResumeManager();
		if(idResume>0)
		{
			int cnt=new TrackManager().countAll(idJsk, idResume);
			if(cnt>0)
			{
				Resume resume=rsmMgr.get(idJsk, idResume);
				result=rsmMgr.copyResume(idJsk, idResume, resume.getResumeName(), resume.getApplyIdCountry(), resume.getIdLanguage(), resume.getTemplateIdCountry(), resume.getResumePrivacy());
				if(result>0)
				{
					rsmMgr.delete(idJsk,resume.getIdResume());
				}
			}
		}	
		return result;
	}
	
	private int copyResumePersonal(Resume resumeSrc,Resume resumeDst)
	{
		int result=-1;
		ResumeManager resumeMgr=new ResumeManager();
		System.out.println("Personal Data >");
		result=resumeMgr.copyPersonalData(resumeSrc, resumeDst);
		
		return result;
	}
	
	private int copyResume(int section,int idJsk,int idResumeSrc,int idDst,int idLanguage)
	{
		int result=-1;
		ResumeManager resumeMgr=new ResumeManager();
			
		if(section==2)
		{
			System.out.println("Target Job > "+section);
			result=resumeMgr.copyTargetjob(idJsk, idResumeSrc, idDst, idLanguage);
		}
		else if(section==3)
		{
			System.out.println("Education > "+section);
			result=resumeMgr.copyEducation(idJsk, idResumeSrc, idDst, idLanguage);
		}
		else if(section==4)
		{
			System.out.println("Experience > "+section);
			result=resumeMgr.copyWorkExperience(idJsk, idResumeSrc, idDst, idLanguage);
		}
		else if(section==5)
		{
			System.out.println("Strength > "+section);
			result=resumeMgr.copyStrength(idJsk, idResumeSrc, idDst, idLanguage);
		}
		else if(section==6)
		{
			System.out.println("Aptitude > "+section);
			result=resumeMgr.copyAptitude(idJsk, idResumeSrc, idDst, idLanguage);
		}
		
		if(result>=0)
		{
			System.out.println("copy Success");
		}
		
		return result;
	}
}
