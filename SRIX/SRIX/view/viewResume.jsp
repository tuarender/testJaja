<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.text.DateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="com.topgun.resume.PositionManager"%>
<%@page import="com.topgun.util.Encryption"%>
<%@page import="com.topgun.resume.Position"%>
<%@page import="com.topgun.resume.EmployerManager"%>
<%@page import="com.topgun.resume.Employer"%>
<%@page import="com.topgun.resume.ResumeManager"%>
<%@page import="com.topgun.resume.ViewResumeManager"%>
<%@page import="com.topgun.resume.AttachmentManager"%>
<%@page import="com.topgun.resume.Attachment"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Track"%>
<%@page import="com.topgun.resume.TrackManager"%>
<%@page import="com.topgun.util.Decryption"%>
<%@page import="com.topgun.resume.CompanyManager"%>
<%@page import="com.topgun.resume.Company"%>
<%@page import="com.topgun.resume.MiscellaneousManager"%>
<%@page import="com.topgun.resume.Miscellaneous"%>
<%@page import="com.topgun.shris.masterdata.*"%>
<%
	AttachmentManager atmMgr=new AttachmentManager();
	int idResume=Util.getInt(request.getParameter("idResume"));
	int viewType=Util.getInt(request.getParameter("viewType"),0);//1=show templateC
	int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
	int pdf = Util.getInt(request.getParameter("pdf"));
	int idEmp=-1;
	int idPosition=-1;
	Employer emp=null;
	Position pos=null;
	String attachmentsStr="";
	Track track=null;
	String key="";
	String enc="";
	String applyDate="";
	boolean havelogo = false ;
	
	if(pdf == 1)
	{
		key=Util.getStr(request.getParameter("Key"));
		enc=Util.getStr(request.getParameter("Enc"));
		Decryption decryption = new Decryption(enc, key);
		if(decryption.isValid())
		{
			idJsk = decryption.getIdJsk();
			idResume = decryption.getIdResume();
		}
	}
	
	if(idResume==-1 || idJsk==-1 )
	{
		key=Util.getStr(request.getParameter("Key"));
		enc=Util.getStr(request.getParameter("Enc"));
		applyDate= Util.getStr(request.getParameter("applyDate"));
		if((!enc.equals(""))&&(!key.equals("")))
		{
			Decryption decryption=new Decryption(enc, key);
			if(decryption.isValid())
			{
				idJsk=decryption.getIdJsk();
				idResume=decryption.getIdResume();
				idEmp=decryption.getIdEmp();
				idPosition=decryption.getIdPosition();
		
				
				emp=new EmployerManager().get(idEmp);
				pos=new PositionManager().getPosition(idEmp,idPosition);
				track=new TrackManager().get(idJsk,idResume,idEmp,idPosition);
				String attachPath = "/oracle/bea/user_projects/domains/mydomain/applications/superresume_inter/Attachments";
				attachPath=attachPath+File.separator+(Math.round(idJsk/2000)+1)+File.separator+idJsk;
				String attachments="";
				if(track!=null)
				{
					attachments=Util.getStr(track.getAttachments());
				}
				
				int showHeader=0;
				if(!attachments.equals(""))
				{
					if(attachments.indexOf("-1")!=-1)
					{
						
						List<Attachment> atmList = atmMgr.getAllByFileType(idJsk,"DOCUMENT");
						List<Attachment> safList = atmMgr.getAllByFileType(idJsk,"RESUME");	
						
						if((safList!=null)||(atmList!=null))
						{
							
							if(safList!=null)
							{
								if(safList.size()>0)
								{
									for(int i=0; i<safList.size(); i++)
									{
										String filePath=safList.get(i).getIdFile()+"_"+safList.get(i).getFileType()+Util.getFileExtension(safList.get(i).getFileFormat());
										if(new File(attachPath+File.separator+filePath).exists())
										{
											if(showHeader==0)
											{
												attachmentsStr+="<br/><center><h1>Super Attached Files</h1>";						
												attachmentsStr+="<ul>";		
												showHeader=1;
											}
											if(safList.get(i).getFileFormat().equals("image/bmp")||safList.get(i).getFileFormat().equals("image/pjpeg")||safList.get(i).getFileFormat().equals("image/gif"))
											{
												attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+safList.get(i).getIdFile()+"&view=true\" target=\"_blank\"><u>"+safList.get(i).getFileTitle()+"</u></a></li>";
											}
											else
											{
												attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+safList.get(i).getIdFile()+"\"><u>"+safList.get(i).getFileTitle()+"</u></a></li>";
											}
										}
									}
								}
							}
							
							
							if(atmList!=null)
							{								
								if(atmList.size()>0)
								{
									for(int i=0; i<atmList.size(); i++)
									{
										String filePath=atmList.get(i).getIdFile()+"_"+atmList.get(i).getFileType()+Util.getFileExtension(atmList.get(i).getFileFormat());
										if(new File(attachPath+File.separator+filePath).exists())
										{
											if(showHeader==0)
											{
												attachmentsStr+="<br/><center><h1>Super Attached Files</h1>";						
												attachmentsStr+="<ul>";		
												showHeader=1;
											}
											
											if(atmList.get(i).getFileFormat().equals("image/bmp")||atmList.get(i).getFileFormat().equals("image/pjpeg")||atmList.get(i).getFileFormat().equals("image/gif"))
											{
												attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+atmList.get(i).getIdFile()+"&view=true\" target=\"_blank\"><u>"+safList.get(i).getFileTitle()+"</u></a></li>";
											}
											else
											{
												attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+atmList.get(i).getIdFile()+"\"><u>"+atmList.get(i).getFileTitle()+"</u></a></li>";
											}
										}
									}
								}
							}
							
							attachmentsStr+="</ul></center><br/><br/><br/>";	
							
						}
					}
					
					else if(attachments.indexOf(",")!=-1)
					{	
						StringTokenizer token = new StringTokenizer(attachments,",");
						while(token.hasMoreTokens())
						{
							String buf = token.nextToken().trim();
							int idFile=Util.getInt(buf);
							if(idFile!=-1)
							{
								Attachment saf=atmMgr.get(idJsk, idFile);
								String filePath=saf.getIdFile()+"_"+saf.getFileType()+Util.getFileExtension(saf.getFileFormat());
								if(new File(attachPath+File.separator+filePath).exists())
								{					
									if(showHeader==0)
									{
										attachmentsStr+="<br/><center><h1>Super Attached Files</h1>";						
										attachmentsStr+="<ul>";		
										showHeader=1;
									}
									
									if(saf.getFileFormat().equals("image/bmp")||saf.getFileFormat().equals("image/pjpeg")||saf.getFileFormat().equals("image/gif"))
									{
										attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+saf.getIdFile()+"&view=true\" target=\"_blank\"><u>"+saf.getFileTitle()+"</u></a></li>";
									}
									else
									{
										attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+saf.getIdFile()+"\"><u>"+saf.getFileTitle()+"</u></a></li>";
									}
								}
							}
						}
						
						attachmentsStr+="</ul></center><br/><br/><br/>";
					}
					else
					{
						int idFile=Util.getInt(attachments);
						if(idFile!=-1)
						{
							Attachment saf=atmMgr.get(idJsk, idFile);
							String filePath=saf.getIdFile()+"_"+saf.getFileType()+Util.getFileExtension(saf.getFileFormat());
							if(new File(attachPath+File.separator+filePath).exists())
							{			
								if(showHeader==0)
								{
									attachmentsStr+="<br/><center><h1>Super Attached Files</h1>";						
									attachmentsStr+="<ul>";		
									showHeader=1;
								}
								
								if(saf.getFileFormat().equals("image/bmp")||saf.getFileFormat().equals("image/pjpeg")||saf.getFileFormat().equals("image/gif"))
								{
									attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+saf.getIdFile()+"&view=true\" target=\"_blank\"><u>"+saf.getFileTitle()+"</u></a></li>";
								}
								else
								{
									attachmentsStr+="<li><a href=\"/DownloadServ?time="+new java.util.Date().getTime()+"&idJsk="+idJsk+"&idFile="+saf.getIdFile()+"\"><u>"+saf.getFileTitle()+"</u></a></li>";
								}
							}
						}
						attachmentsStr+="</ul></center><br/><br/><br/>";							
					}
				}
			}
		}
	}
	
	
	
	Resume resume=new ResumeManager().getIncludeDeleted(idJsk,idResume);
	if(resume!=null)
	{
		String template=request.getRealPath("")+"/templates/TH_A.html";
		if(idEmp>0)
		{
			Company company=new CompanyManager().get(idEmp);
			if(company!=null)
			{
				template=request.getRealPath("")+"/templates/TH_"+company.getResumeTemplate()+".html";
			}
			template=request.getRealPath("")+"/templates/TH_A.html";
		}
		else
		{
			//Miscellaneous mis= new MiscellaneousManager().get(idJsk, idResume);
			//if(mis !=null)
			//{
				//template=request.getRealPath("")+"/templates/"+mis.getResumeTemplate();
			//}
			template=request.getRealPath("")+"/templates/TH_A.html";
		}
		if(idResume==0 || resume.getIdParent()==-1)
		{
			template=request.getRealPath("")+"/templates/SAF_TH.html";
		}
		
		if(idJsk==1673238)
		{
		}
		String curDate=Util.getCurrentDate("d MMMM yyyy", "th");
		if(resume.getIdLanguage()!=38)
		{
			curDate=Util.getCurrentDate("d MMMM yyyy", "en");
		}
		if(track!=null)
		{
			curDate=Util.DateToStr(track.getSendDate(),"d MMMM yyyy");
		}
		if(applyDate != "")
		{
			 String applyDateDec= Encryption.decode(applyDate);
			 DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
			 Date day;
			 day= df.parse(applyDateDec);
			 curDate=Util.DateToStr(day,"d MMMM yyyy");
		}
		String photo="";
		Attachment att=atmMgr.getPhoto(idJsk);
		if(att!=null)
		{
			photo = "<img width=\"86\" src=\"/"+"Attachments"+"/"+(Math.round(idJsk/2000)+1)+"/"+idJsk+"/"+att.getIdFile()+"_"+att.getFileType()+Util.getFileExtension(att.getFileFormat())+"\">";
		}
		//photo = "<img width=\"86\" src='../images/kamol.jpg'>";
		String positionName=pos!=null?Util.getStr(pos.getPositionName()):"";
		if(pos!=null)
		{
			boolean isNW=new PositionManager().isNationWideJob(idEmp, idPosition);
			if(isNW==true && track!=null)
			{
				String stateName=new TrackManager().getProvince(track.getIdTrack(), resume.getIdLanguage());
				if(!stateName.equals(""))
				{
					positionName+="  ("+stateName+")";
				}
			}
		}
		String comLogo="";
		int idLang=Util.getInt(request.getParameter("idLang"))!=-1?Util.getInt(request.getParameter("idLang")):resume.getIdLanguage();
		if(emp!=null)
		{
			havelogo=new ViewResumeManager().checkLogoPath(emp.getId());
			if(havelogo==true)
			{
				comLogo="<img style='max-width: 200px; max-height: 110px' src='http://www.jobtopgun.com/content/filejobtopgun/logo/sup_lg"+emp.getId()+".gif'>";
			}
			else
			{
				comLogo=emp.getNameThai()!=null?emp.getNameThai():emp.getNameEng();
				if(idLang==11)
				{
					comLogo=emp.getNameEng();
				}
			}
		}
		
		if(idEmp>0)
		{
			String resumeType = "";
			if(resume.getIdParent()==0)
			{
				resumeType = "Original";

			}else
			{
				resumeType = "Translated";
			}
			resumeType+=" "+new MasterDataManager().getLanguageName(resume.getIdLanguage(),11);
			out.println("<div width='100%' align='right' style='font-size:14px;padding-right:10px;padding-top:10px;'>"+resumeType+"</div>");
		}
		out.println("<div id='non-printable' width='100%' align='right' style='font-size:14px;padding-right:10px;padding-top:10px;'><button onclick='window.print()'>Print</button></div>");
		//havelogo=true ;
		//comLogo="<img height='110' src='http://www.jobtopgun.com/content/filejobtopgun/logo/sup_lg5.gif'>";
		ViewResumeManager viewMgr=new ViewResumeManager();
		//in case employer mini check idLang
		
		if(pdf == 1)	viewMgr.setResumeFont("Taeyhom");	//Similar to tahoma and more compatible to pd4ml pdf display
		
		boolean isTranslate=false;
		if(idLang!= resume.getIdLanguage())
		{
			String loc= new ResumeManager().getLocale(idLang, resume.getIdCountry());
			loc= loc!=""?loc:resume.getLocale();
			resume.setLocale(loc);
			resume.setIdLanguage(idLang);
			isTranslate=true;
		}
		if(viewType==1)
		{
			List<Resume> rs=new ResumeManager().getSubResume(idJsk, idResume);
			if(rs.size()>0)
			{
				viewType=1;
			}
			else
			{
				viewType=0;
			}
		}
		String buffer = null;
		//System.out.printf("ID_JSK = %d, ID_RESUME = %d, ID Parent = %d\n",resume.getIdJsk(),resume.getIdParent(), resume.getIdParent());
		if(resume.getIdParent() != 0)
		{
			//is child resume.
			buffer = viewMgr.viewResumeChild(resume,template,positionName,1,isTranslate,viewType).toString();
		}
		else
		{
			buffer = viewMgr.viewResume(resume,template,positionName,1,isTranslate,viewType).toString();
		}
		buffer=buffer.replaceAll("#COMPANY_LOGO#",comLogo);
		buffer=buffer.replaceAll("#SEND_DATE#",curDate);
		buffer=buffer.replaceAll("superresume_logo.gif","http://www.superresume.com/images/superresume_logo.png");
		buffer=buffer.replaceAll("saf_logo.gif","http://www.superresume.com/images/superresume_logo.png");
		buffer=buffer.replaceAll("#PHOTOGRAPH#",photo);
		if(idEmp!=-1 && idPosition!=-1)
		{
			emp=new EmployerManager().get(idEmp);
			pos=new PositionManager().getPosition(idEmp, idPosition);
			if(emp!=null)
			{
				if(havelogo)
				{
					buffer=buffer.replaceAll("#COMPANY_LOGO#",comLogo);
					buffer=buffer.replaceAll("#BEGIN_COMPANY_LOGO#","");
					buffer=buffer.replaceAll("#END_COMPANY_LOGO#","");
					buffer=buffer.replaceAll("#BEGIN_COMPANY_NAME#","<!--");
					buffer=buffer.replaceAll("#END_COMPANY_NAME#","-->");
				}else
				{
					buffer=buffer.replaceAll("#COMPANY_NAME#",comLogo);
					buffer=buffer.replaceAll("#BEGIN_COMPANY_NAME#","");
					buffer=buffer.replaceAll("#END_COMPANY_NAME#","");
					buffer=buffer.replaceAll("#BEGIN_COMPANY_LOGO#","<!--");
					buffer=buffer.replaceAll("#END_COMPANY_LOGO#","-->");
				}
				
			}else{
				
				buffer=buffer.replaceAll("#BEGIN_COMPANY_LOGO#","<!--");
				buffer=buffer.replaceAll("#END_COMPANY_LOGO#","-->");
				buffer=buffer.replaceAll("#BEGIN_COMPANY_NAME#","<!--");
				buffer=buffer.replaceAll("#END_COMPANY_NAME#","-->");
			}
			if(new EmployerManager().chkNowPkgSuperERecruit(idEmp)== true)
			{
				String referer=Util.getStr(request.getHeader("referer"));
				if(referer.equals("") || (referer.indexOf("http://203.146.250.66/RMS/")==-1))
				{
				    if(idEmp==5)
					{
				    	out.println("<div id='non-printable'><center><input type=\"button\" value=\"Prediction\" onclick=\"window.open('http://www.topgunthailand.com/jtg/research/hiddenScore.php?idJsk="+idJsk+"&idResume="+idResume+"&pname=','_blank')\" />&nbsp;&nbsp;&nbsp;");						
						out.println("<input type=\"button\" value=\"Analyze Super Resume\" onclick=\"window.open('http://www.topgunthailand.com/jtg/research/prediction.php?Enc="+enc+"&Key="+key+"','_blank')\" /></center></div>");			
					}
					else 
					{
						out.println("<div id='non-printable'><center><input type=\"button\" value=\"Prediction\" onclick=\"window.open('http://www.topgunthailand.com/jtg/research/hiddenScore.php?idJsk="+idJsk+"&idResume="+idResume+"&pname=','_blank')\" /></center></div>");						
					}
				}
			}
		}
		else{
			buffer=buffer.replaceAll("#BEGIN_COMPANY_LOGO#","<!--");
			buffer=buffer.replaceAll("#END_COMPANY_LOGO#","-->");
			buffer=buffer.replaceAll("#BEGIN_COMPANY_NAME#","<!--");
			buffer=buffer.replaceAll("#END_COMPANY_NAME#","-->");
		}
		out.println(buffer);	
		out.println(attachmentsStr);
	}
%>
