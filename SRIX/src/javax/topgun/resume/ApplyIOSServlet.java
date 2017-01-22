package javax.topgun.resume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.Employer;
import com.topgun.resume.EmployerManager;
import com.topgun.resume.Position;
import com.topgun.resume.PositionManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.SendResumeManager;
import com.topgun.resume.SendResumeThread;
import com.topgun.resume.TrackManager;
import com.topgun.resume.MatchResumeAndJob;
import com.topgun.resume.PositionRequired;
import com.topgun.resume.Track;
import com.topgun.util.Util;

public class ApplyIOSServlet extends HttpServlet 
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
        
		int idJsk=Util.getInt(request.getParameter("idJsk"));
		int idResume=Util.getInt(request.getParameter("idResume"));
		int idEmp=Util.getInt(request.getParameter("idEmp"));
		int idPos=Util.getInt(request.getParameter("idPosition"));
		String atms=Util.getStr(request.getParameter("atms"));
		String company="";
		String position="";
		String email="";
		boolean acceptMail=true;
		String source="JTG App";
		if(idJsk<0)
		{
			out.print("1");
		}
		else if(idResume<0)
		{
			out.print("2");
		}
		else if(idEmp<0)
		{
			out.print("3");
		}
		else if(idPos<0)
		{
			out.print("4");
		}
		else 
		{
			Resume resume=new ResumeManager().get(idJsk, idResume);
			if(resume==null)
			{
				out.print("6");
			}
			else
			{
				if(idEmp>0 && idPos>0)
				{
					Position pos=new PositionManager().getPosition(idEmp, idPos);
					Employer com=new EmployerManager().get(idEmp);
					company=com.getNameEng();
					email=pos.getHrEmail();
					position=pos.getPositionName();
					acceptMail=new EmployerManager().isAcceptMail(idEmp);
				}				
				Timestamp curTime = new Timestamp(new java.util.Date().getTime());
				Timestamp lastTime = new SendResumeManager().getLastTimeApply(idJsk,idResume,idEmp,idPos,email,position);
				//Other Company check 30 days
				if(Util.getDayInterval(lastTime,curTime)<=30)
				{
					out.print("7");
				}
				else
				{
					String referer=Util.getStr(request.getParameter("referer"));
					String ip=Util.getStr(request.getRemoteAddr());
					if(ip.equals("203.146.208.152"))
					{
						ip=Util.getStr(request.getHeader("X-Forwarded-For"));
						if(ip.equals(""))
						{
							ip=request.getHeader("Proxy-Client-IP");
						}
					}
					Track track=new Track();
					track.setIdJsk(idJsk);
					track.setIdResume(idResume);
					track.setIdEmp(idEmp);
					track.setIdPosition(idPos);
					track.setAttachments(atms);
					track.setEmpOther(company);
					track.setIp(ip);
					track.setPositionOther(position);
					track.setRecipient(email);
					track.setSource(source);
					track.setReferer(referer);
					track.setSent(0);
					if(acceptMail==true)
					{
						track.setSent(0);
					}
					else
					{
						track.setSent(1);
					}
					int result =new TrackManager().add(track);
					//int result=1;
					if((idEmp>0)&&(idPos>0) && result==1) // match resume and job for stat
					{
						 try
						 {
						   List<PositionRequired> posList =  MatchResumeAndJob.getPositionRequired(idEmp,idPos);
						   MatchResumeAndJob.compareJobAndResume(idJsk,idResume, posList );
						 }
						 catch(Exception e)
						 {
							e.printStackTrace();
						 }
					}
					if(result==1)
					{
						new TrackManager().addTrackAll(idJsk, idResume, idEmp, company, idPos, position,"");
						int idTrack=new TrackManager().getTrackId(idJsk, idResume);
						track.setIdTrack(Util.getInt(idTrack));
					}
					new ResumeManager().updateLastApply(idJsk, idResume);
					new SendResumeThread(track).start();
					out.print("8");
				}			
			}
		}
	}
}
