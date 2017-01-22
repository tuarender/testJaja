package javax.topgun.resume;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topgun.resume.CancelService;
import com.topgun.resume.CancelServiceManager;
import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.util.Encoder;
import com.topgun.util.Util;

public class CancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		String username = Util.getStr(request.getSession().getAttribute("SESSION_USERNAME"));
		String service = Util.getStr(request.getParameter("cancelService"));
		if(username.equals("")){
			Jobseeker jsk = new JobseekerManager().get(idJsk);
			if(jsk!=null){
				username = Util.getStr(jsk.getUsername());
			}
		}
		if(service.equals("")){
			service = Util.getStr(request.getParameter("cancelService")).toLowerCase();
		}
		String type = Util.getStr((request.getParameter("type")));
		int reasonType = Util.getInt(request.getParameter("reasonType"));
		int howLong = Util.getInt(request.getParameter("howLong"));
		int changeMail = Util.getInt(request.getParameter("changeMail"));
		int howOften = Util.getInt(request.getParameter("howOften"));
		String juDay = Util.getStr(request.getParameter("juDay"));
		int notMatch = Util.getInt(request.getParameter("notMatch"));
		String otherReason = Util.getStr(request.getParameter("otherReason"));
		String tel = Util.getStr(request.getParameter("tel"));
		String target="/LogoutServ";
		CancelService cs = (CancelService)(request.getSession().getAttribute("SESSION_CANCEL_SERVICE")==null?new CancelService():request.getSession().getAttribute("SESSION_CANCEL_SERVICE"));
		if(idJsk>0){
			if(reasonType>0){
				boolean jmStatus = false;
				String juStatus = "NO";
				cs.setIdJsk(idJsk);
				cs.setEmail(username);
				cs.setService(service);
				cs.setReasonType(reasonType);
				cs.setDisable("FALSE");
				cs.setDisableTimeStamp("");
				if(reasonType==1||reasonType==3||reasonType==6){
					cs.setHowLong(howLong);
					if(reasonType==6){
						cs.setOtherReason(otherReason);
						cs.setTel(tel);
					}
				}
				else if(reasonType==2){
					cs.setNotMatch(notMatch);
					if(notMatch==2){
						cs.setHowLong(howLong);
						jmStatus = false;
					}
					else{
						cs.setHowLong(0);
						jmStatus = true;
					}
				}
				else if(reasonType==4){
					cs.setHowOften(howOften);
					if(howOften==4){
						cs.setHowLong(howLong);
						jmStatus = false;
					}
					else if(howOften==1&&service.equals("jobupdate")){
						cs.setHowLong(0);
						juStatus = juDay;
					}
					else{
						cs.setHowLong(0);
						jmStatus = true;
					}
				}
				else if(reasonType==5){
					cs.setChangeMail(changeMail);
					cs.setHowLong(howLong);
				}
				
				int resultAdd = 0;
				if(CancelServiceManager.checkExist(cs,service)){
					resultAdd =	CancelServiceManager.update(cs);
				}
				else{
					resultAdd =	CancelServiceManager.add(cs);
				}
				if(resultAdd>0){
					new JobseekerManager().updateLastLogin(cs.getIdJsk());
					if(service.equals("jobmatch")){
						if(!jmStatus)
							new JobseekerManager().updateJobmatchStatus(cs.getIdJsk(),"FALSE");
						else
							new JobseekerManager().updateJobmatchStatus(cs.getIdJsk(),"TRUE");
					}
					else if(service.equals("jobupdate")){
						new JobseekerManager().setJobUpdateStatus(cs.getIdJsk(),juStatus);
					}
					target = "/SRIX?view=cancelResult&success=1";
				}
				else{
					target = "/SRIX?view=cancelResult&error=1";
				}
			}
			else{
				if(type.equals("receive")){
					String juStatus = juDay;
					cs.setIdJsk(idJsk);
					cs.setEmail(username);
					cs.setService(service);
					cs.setDisable("TRUE");
					int resultAdd = 0;
					if(CancelServiceManager.checkExist(cs,service)){
						resultAdd =	CancelServiceManager.update(cs);
					}
					else{
						resultAdd =	CancelServiceManager.add(cs);
					}
					System.out.println("ResultAdd"+resultAdd);
					System.out.println("Service"+service);
					if(resultAdd>0){
						new JobseekerManager().updateLastLogin(cs.getIdJsk());
						if(service.equals("jobmatch")){
							new JobseekerManager().updateJobmatchStatus(cs.getIdJsk(),"TRUE");
						}
						else if(service.equals("jobupdate")){
							new JobseekerManager().setJobUpdateStatus(cs.getIdJsk(),juStatus);
						}
						target = "/SRIX?view=cancelResult&success=1";
					}
					else{
						target = "/SRIX?view=cancelResult&error=1";
					}
				}else if(type.equals("change")){
					String juStatus = juDay;
					int frequency = Util.getInt(request.getParameter("frequency")); 
					cs.setIdJsk(idJsk);
					cs.setEmail(username);
					cs.setService(service);
					if(frequency==4&&service.equals("jobmatch")){
						cs.setDisable("TRUE");
					}
					else if((frequency==4||frequency==1)&&service.equals("jobupdate")){
						cs.setDisable("TRUE");
					}
					else{
						cs.setReasonType(4);
						cs.setHowOften(frequency);
					}
					int resultAdd = 0;
					if(CancelServiceManager.checkExist(cs,service)){
						resultAdd =	CancelServiceManager.update(cs);
					}
					else{
						resultAdd =	CancelServiceManager.add(cs);
					}
					if(resultAdd>0){
						new JobseekerManager().updateLastLogin(cs.getIdJsk());
						if(frequency==4&&service.equals("jobmatch")){
							new JobseekerManager().updateJobmatchStatus(cs.getIdJsk(),"TRUE");
						}
						else if((frequency==4||frequency==1)&&service.equals("jobupdate")){
							new JobseekerManager().setJobUpdateStatus(cs.getIdJsk(), frequency==4?"ALL":juStatus);
						}
						target = "/SRIX?view=cancelResult&success=1";
					}
					else{
						target = "/SRIX?view=cancelResult&error=1";
					}
				}
				else{
					target = "/SRIX?view=cancelResult&error=1";
				}
			}
		}
		response.sendRedirect(response.encodeRedirectURL(target));
	}
	
	//post change password
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idJsk=Util.StrToInt(""+request.getSession().getAttribute("SESSION_ID_JSK"));
		String service = Util.getStr(Util.getStr(request.getParameter("cancelService")),request.getParameter("cancelService"));
		String newUsername = request.getParameter("newUsername").trim().toLowerCase();
		String confirmUsername = request.getParameter("confirmUsername").trim().toLowerCase();
		String username = Util.getStr(request.getSession().getAttribute("SESSION_USERNAME"));
		String password = request.getParameter("password");
		String curEncode=Encoder.getEncode(password);
		String oldEncode="";
		int idError = 0;
		String target="/LogoutServ";
		
		Jobseeker jsk = new JobseekerManager().get(idJsk);
		if(jsk!=null){
			
			oldEncode=Encoder.getEncode(jsk.getPassword());	
			if(username.equals("")){
				username = jsk.getUsername();
			}
			else if (newUsername.equals("")){
				idError = 250;  // check fill new_username 
			}
			else if (confirmUsername.equals("")){
				idError = 251;  // check fill confirm_username
			}
			else if (password.equals("")){
				idError = 252;  // check fill password
			}
			else if (!Util.isEmail(newUsername) || !Util.isEmail(confirmUsername)){
				idError = 101;  // invalid email
			}
			else if (!newUsername.equals(confirmUsername)){
				idError = 253;  // new username not equal confirm username
			}
			else if (new JobseekerManager().isExist(newUsername)){
				idError = 254;  // new username already exist
			}
			else if (!curEncode.equals(oldEncode)){
				idError = 255;  // password not correct
			}
			if (idError == 0&&idJsk>0){
				if(jsk!=null){
					jsk.setUsername(newUsername);
					if (new JobseekerManager().update(jsk) == 0){
						idError = 209;
					}
					else{
						CancelService cs = new CancelService();
						cs.setIdJsk(idJsk);
						cs.setEmail(username);
						cs.setNewEmail(newUsername);
						cs.setService(service);
						int resultAdd = 0;
						if(CancelServiceManager.checkExist(cs,service)){
							resultAdd =	CancelServiceManager.update(cs);
						}
						else{
							resultAdd =	CancelServiceManager.add(cs);
						}
						
						if(resultAdd>0){
							new JobseekerManager().updateLastLogin(cs.getIdJsk());
						}
					}
				}
				if(idError == 0){
					target = "/SRIX?view=cancelResult&success=1";
				}
				else{
					target = "/SRIX?view=cancelResult&error=1";
				}
			}
			else if(idError>0&&idJsk>0){
				target = "/SRIX?view=cancelService&ErrorId="+idError;
			}
		}
		response.sendRedirect(response.encodeRedirectURL(target));
	}

}
