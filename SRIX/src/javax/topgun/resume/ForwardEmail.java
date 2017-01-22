package javax.topgun.resume;

import com.topgun.shris.masterdata.Country;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.DBManager;
import com.topgun.util.Encryption;
import com.topgun.util.MailManager;
import com.topgun.util.Util;
import com.topgun.resume.AttachmentManager;
import com.topgun.resume.Attachment;
import com.topgun.resume.EmployerManager;
import com.topgun.resume.ForwardEmailManager;
import com.topgun.resume.FwdBean;
import com.topgun.resume.Miscellaneous;
import com.topgun.resume.MiscellaneousManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.resume.Track;
import com.topgun.resume.TrackManager;
import com.topgun.resume.ViewResumeManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ForwardEmail extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    try{
    	
	 	int idJsk=Util.getInt(request.getParameter("idJsk"));
		int idResume=Util.getInt(request.getParameter("idResume"));
		int idEmp=Util.getInt(request.getParameter("idEmp"));
		int idPosition=Util.getInt(request.getParameter("idPos"));
		int jtgFwdId=Util.getInt(request.getParameter("jtgFwdId"));
		int idLang=Util.getInt(request.getParameter("idLang"));
		int isSearch=Util.getInt(request.getParameter("isSearch"));
		String projectDir="/oracle/bea/user_projects/domains/mydomain/applications/SRIX";
		//String projectDir="/Users/Pluem/Project/SRIX/SRIX";
		//String projectDir="/Users/Micchii/Documents/JTGProject/SRIX/SRIX";
		String srLogoPath=projectDir+"/images/superresume_logo.gif";
		String safLogoPath=projectDir+"/images/superresume_logo.gif";
		String photoPath="";
		String logoPath="";
		String lg_cid="";
		String encode="";
		int isAttach=0;
		
		Track track=new TrackManager().get(idJsk, idResume, idEmp,idPosition);
		if(track!=null && isSearch==-1)
		{
			track.setIdEmp(idEmp);
			track.setIdPosition(idPosition);
			ForwardEmailManager fem=new ForwardEmailManager();
			//-----------FORWARD-----------------------
			FwdBean fb=fem.get(jtgFwdId);
			if(track!=null && fb!=null)
			{
			
				logoPath=new EmployerManager().getLogoPath(track.getIdEmp());
				if(new File(logoPath).exists())
				{
					lg_cid=track.getIdEmp()+"_"+new java.util.Date().getTime();
				}
				//------------------check encoding-----------------
				String[] rsEncoding=fem.getEncode(track.getIdEmp());
				if(rsEncoding!=null)
				{
					encode=Util.getStr(rsEncoding[0]);
					isAttach=Util.getInt(rsEncoding[1]);
				}
				//---------------end check encode-----------------
				String Enc="";
				String Key="";
				if((track.getIdEmp()>0)&&(track.getIdPosition()>0))
				{
					Enc=Encryption.getEncoding(track.getIdEmp(),track.getIdPosition(),track.getIdJsk(),track.getIdResume());
					Key=Encryption.getKey(track.getIdEmp(),track.getIdPosition(),track.getIdJsk(),track.getIdResume());
				}
				else
				{
					Enc=Encryption.getEncoding(0,0,track.getIdJsk(),track.getIdResume());
					Key=Encryption.getKey(0,0,track.getIdJsk(),track.getIdResume());
				}
			
				Resume resume=new ResumeManager().getIncludeDeleted(track.getIdJsk(),track.getIdResume());
				int idParent=track.getIdResume();
				String resumeListLink="";
				List<Resume> rsChild= new ResumeManager().getSubResume(resume.getIdJsk(), resume.getIdResume());
				if(resume.getIdParent()==0 && rsChild.size()>0)
				{
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					String language=MasterDataManager.getLanguageName(resume.getIdLanguage(),11)!=""?MasterDataManager.getLanguageName(resume.getIdLanguage(),11):"Thai";
					int viewType=0;
					String encChild = "";
					String keyChild = "";
					if(resume.getIdLanguage()==38){viewType=1;}
					resumeListLink+="<br><ol>";
					resumeListLink+="<li>"+resume.getResumeName()+"("+language+")&nbsp;&nbsp;<a href='http://www.superresume.com/ViewResume?viewType="+viewType+"&Key="+Key+"&Enc="+Enc+"&idLang=&applyDate="+Encryption.encode(""+dateFormat.format(date))+"'>View</a></li>";
					if(rsChild.size()>0)
					{
						for(int i=0;i<rsChild.size();i++)
						{
							if((track.getIdEmp()>0)&&(track.getIdPosition()>0))
							{
								encChild=Encryption.getEncoding(track.getIdEmp(),track.getIdPosition(),track.getIdJsk(),rsChild.get(i).getIdResume());
								keyChild=Encryption.getKey(track.getIdEmp(),track.getIdPosition(),track.getIdJsk(),rsChild.get(i).getIdResume());
							}
							else
							{
								encChild=Encryption.getEncoding(0,0,track.getIdJsk(),rsChild.get(i).getIdResume());
								keyChild=Encryption.getKey(0,0,track.getIdJsk(),rsChild.get(i).getIdResume());
							}
							language=MasterDataManager.getLanguageName(rsChild.get(i).getIdLanguage(),11)!=""?MasterDataManager.getLanguageName(rsChild.get(i).getIdLanguage(),11):"Thai";
							viewType=0;
							if(resume.getIdLanguage()==38){viewType=1;}
							if(new EmployerManager().chkRequireLanguage(track.getIdEmp(), rsChild.get(i).getIdLanguage()) || rsChild.get(i).getIdLanguage()==11)
							{
								resumeListLink+="<li>"+rsChild.get(i).getResumeName()+"&nbsp;&nbsp;<a href='http://www.superresume.com/ViewResume?viewType="+viewType+"&Key="+keyChild+"&Enc="+encChild+"&idLang=&applyDate="+Encryption.encode(""+dateFormat.format(date))+"'>View</a></li>";
							}
							if(rsChild.get(i).getIdLanguage()==11 && new EmployerManager().chkFirstViewEngLangauge(track.getIdEmp()))
							{
								resume=new ResumeManager().getIncludeDeleted(track.getIdJsk(),rsChild.get(i).getIdResume());
								idParent=resume.getIdResume();
							}
						}
					}
					resumeListLink+="</ol>";
				}
				String templateFile = projectDir+File.separator+"templates"+File.separator;
				String templateCountry="TH";
				Country country=MasterDataManager.getCountry(resume.getTemplateIdCountry(),idLang);
				if(country!=null)
				{
					templateCountry=country.getAbbreviation();
				}
				if(resume.getIdResume()==0)
				{
					templateFile+="SAF_"+templateCountry+".html";
				}
				else
				{
					templateFile+="TH_A.html";
					
				}
				ViewResumeManager viewMgr=new ViewResumeManager();
				boolean isTranslate=false;
				
				StringBuilder buffer=viewMgr.viewResume(resume, templateFile, track.getPositionOther(), 1,isTranslate);
				if(buffer!=null)
				{
					String attachPath=projectDir+"/Attachments/"+(Math.round(track.getIdJsk()/2000)+1)+File.separator+track.getIdJsk();
					String ph_cid="ph_"+new java.util.Date().getTime();
					String sr_cid="sr_"+new java.util.Date().getTime();
					String sf_cid="sf_"+new java.util.Date().getTime();
					
					viewMgr.replaceAll(buffer,"superresume_logo.gif", "cid:"+sr_cid);
					viewMgr.replaceAll(buffer,"saf_logo.gif", "cid:"+sf_cid);
					if((track.getIdEmp()>0))
					{
						if((new File(logoPath).exists()))
						{
							viewMgr.replaceAll(buffer,"#COMPANY_LOGO#","<img src=\"cid:"+lg_cid+"\">");
							//viewMgr.replaceAll(buffer,"#COMPANY_LOGO#","<img src='http://www.jobtopgun.com/content/filejobtopgun/logo_com_job/j1700.gif'>");
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_LOGO#","");
							viewMgr.replaceAll(buffer,"#END_COMPANY_LOGO#", "");
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_NAME#","<!--");
							viewMgr.replaceAll(buffer,"#END_COMPANY_NAME#", "-->");
							viewMgr.replaceAll(buffer,"#COMPANY_NAME#","");
						}
						else
						{
							viewMgr.replaceAll(buffer,"#COMPANY_LOGO#","");
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_LOGO#","<!--");
							viewMgr.replaceAll(buffer,"#END_COMPANY_LOGO#", "-->");
							viewMgr.replaceAll(buffer,"#COMPANY_NAME#",track.getEmpOther());
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_NAME#","");
							viewMgr.replaceAll(buffer,"#END_COMPANY_NAME#", "");
						}
					}
					else
					{
						viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_LOGO#","<!--");
						viewMgr.replaceAll(buffer,"#END_COMPANY_LOGO#", "-->");
						viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_NAME#","<!--");
						viewMgr.replaceAll(buffer,"#END_COMPANY_NAME#", "-->");
					}
						
					Attachment photo=new AttachmentManager().getPhoto(track.getIdJsk());
					if(photo!=null)
					{
						photoPath=attachPath+File.separator+photo.getIdFile()+"_"+photo.getFileType()+Util.getFileExtension(photo.getFileFormat());
						if(new File(photoPath).exists())
						{
							viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "<img src=\"cid:"+ph_cid+"\" style='max-width:84px;' width='84'>");
						}
						else
						{
							viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "");
						}
					}
					else
					{
						viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "");
					}		
									
					if(idLang== 38)
					{
						viewMgr.replaceAll(buffer,"#SEND_DATE#",Util.getThaiDate());
					}
					else 
					{
						viewMgr.replaceAll(buffer,"#SEND_DATE#",Util.nowDateFormat("MMMM dd, yyyy"));
					}								
								
					String content=fb.getMsg()+buffer.toString();
					content=content+"<br><center><b><a href=\"http://www.superresume.com/ViewResume?Enc="+Enc+"&Key="+Key+"&idLang="+idLang+"\" target=\"_blank\"><u><FONT COLOR=\"#FF0000\">***If you cannot read this Super Resume, Please click here***</FONT></u></a></b></center>";
					MailManager mailMgr=null;
					try
					{
						if(encode!=null && !encode.equals(""))
						{
							mailMgr=new MailManager(encode);
						}
						else
						{
							mailMgr=new MailManager();
						}
						
						mailMgr.setSender(fb.getSender(),fb.getSend_name());
						mailMgr.setMailReturn("supreturn@jobinthailand.com");
						mailMgr.setRecepient(fb.getRecipient());
						mailMgr.setSubject(fb.getSubject());	
						mailMgr.setCc(fb.getCc());
						String attachment="";							
						if(!track.getAttachments().equals(""))
						{
							String atm[]=track.getAttachments().split(",");
							if(atm.length>0)
							{
								attachment="<table border='0' cellpadding='3' cellspacing='0'>";
								attachment+="<tr><td colspan='4' class='f11'><b>Attached File(s)</b></td></tr>";
								for(int i=0; i<atm.length; i++)
								{
									if(Util.getInt(atm[i])>0)
									{
										long times=new java.util.Date().getTime();
										Attachment attach = new AttachmentManager().get(track.getIdJsk(), Util.getInt(atm[i]));
										if(isAttach==1)
										{
											String filePath=attach.getIdFile()+"_"+attach.getFileType()+Util.getFileExtension(attach.getFileFormat());
											String fileName=attach.getFileTitle()+Util.getFileExtension(attach.getFileFormat());
											if(new File(attachPath+File.separator+filePath).exists())
											{
												mailMgr.addAttachment(attachPath+File.separator+filePath, fileName);
											}
										}
										else
										{
											NumberFormat formatter = new DecimalFormat("#0.00");
											String fileSize=formatter.format(attach.getFileSize()/1024.0)+" KB";
											attachment+="<tr>";
											attachment+="<td align='right' class='f11'>"+(i+1)+".</td>";
											attachment+="<td class='f11'><a href=\"http://www.superresume.com/DownloadServ?time="+times+"&idFile="+attach.getIdFile()+"&Enc="+Enc+"&Key="+Key+"\" target=\"_blank\">"+attach.getFileTitle()+"</a></td>";
											attachment+="<td  class='f11' align='right'>"+(fileSize)+".</td>";
											if(attach.getFileFormat()!=null && attach.getFileFormat().indexOf("image")!=-1)
											{
												
												attachment+="<td  class='f11' align='right'><a href=\"http://www.superresume.com/DownloadServ?time="+times+"&view=true&idFile="+attach.getIdFile()+"&Enc="+Enc+"&Key="+Key+"\" target=\"_blank\">View</a>&nbsp;&nbsp;&nbsp;&nbsp;";
												attachment+="<a href=\"http://www.superresume.com/DownloadServ?idFile="+attach.getIdFile()+"&Enc="+Enc+"&Key="+Key+"\" target=\"_blank\">Download</a></td>";
											}
											else
											{
												attachment+="<td  class='f11' align='right'><a href=\"http://www.superresume.com/DownloadServ?time="+times+"&idFile="+attach.getIdFile()+"&Enc="+Enc+"&Key="+Key+"\" target=\"_blank\">Download</a></td>";
											}
											attachment+="</tr>";
										}
									}
								}
								attachment+="</table><br><br>";
								if(isAttach!=1)
								{
									content=content.replaceAll("<!--#ATTACHMENTS#-->", attachment);
								}
							}
						}	
						if(resumeListLink!="")
						{resumeListLink=resumeListLink+"<hr>";}
						mailMgr.addHTML(resumeListLink+content);
					
						OutputStreamWriter output=null;
						FileOutputStream fos=null;
						String rsFile=projectDir+"/Attachments/"+(Math.round(track.getIdJsk()/2000)+1)+File.separator+track.getIdJsk()+File.separator+"superresume.html";
						try
						{
							fos = new FileOutputStream (rsFile);
							output = new OutputStreamWriter(fos, "UTF-8"); 
							output.write(content);
							output.close();
							File f=new File(rsFile);
							if(f.exists())
							{
								mailMgr.addAttachment(rsFile, "superresume.html");
							}
						}
						catch(Exception e)
						{
						}
						finally
						{
							if(output != null)
							{
								try
								{
									output.close();
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}
						
						if(photo!=null)
						{
							if(new File(photoPath).exists())
							{
								mailMgr.addImage(photoPath,true,ph_cid);
							}
						}
						
						if(resume.getIdResume()>0)
						{
							mailMgr.addImage(srLogoPath,true,sr_cid);
						}
						else
						{
							mailMgr.addImage(safLogoPath,true,sf_cid);
						}
						if(new File(logoPath).exists())
						{
							mailMgr.addImage(logoPath,true,lg_cid);
						}
						
						//mailMgr.send();
				
						int check=fem.insFwdRec(fb.getJtg_fwd_id(), idJsk, idResume, idEmp, idPosition);
						//check=0;
						if(check==1)
						{
							PrintWriter out = response.getWriter();
							out.println( "<SCRIPT LANGUAGE=\"JavaScript\">" );
							out.println("alert(\"Successfully sent.\");");
							out.println( "location.href='"+fb.getTopage()+"' ");
							out.println( "</SCRIPT>" );
							
					        out.close();
						}
						else
						{
							PrintWriter out = response.getWriter();
							out.println("ERROR");
					         out.close();
						}
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				//error not have in fwd 
			}
		}
		else
		{
			ForwardEmailManager fem=new ForwardEmailManager();
			//-----------FORWARD-----------------------
			FwdBean fb=fem.get(jtgFwdId);
			if(fb!=null)
			{
			
				logoPath=new EmployerManager().getLogoPath(idEmp);
				if(new File(logoPath).exists())
				{
					lg_cid=idEmp+"_"+new java.util.Date().getTime();
				}
				//------------------check encoding-----------------
				String[] rsEncoding=fem.getEncode(idEmp);
				if(rsEncoding!=null)
				{
					encode=Util.getStr(rsEncoding[0]);
					isAttach=Util.getInt(rsEncoding[1]);
				}
				//---------------end check encode-----------------
				String Enc=Encryption.getEncoding(0,0,idJsk,idResume);
				String Key=Encryption.getKey(0,0,idJsk,idResume);
			
				Resume resume=new ResumeManager().getIncludeDeleted(idJsk,idResume);
				String templateFile = projectDir+File.separator+"templates"+File.separator;
				String templateCountry="TH";
				Country country=MasterDataManager.getCountry(resume.getTemplateIdCountry(),idLang);
				if(country!=null)
				{
					templateCountry=country.getAbbreviation();
				}
				if(resume.getIdResume()==0)
				{
					templateFile+="SAF_"+templateCountry+".html";
				}
				else
				{
					templateFile+="TH_A.html";
					
				}
				ViewResumeManager viewMgr=new ViewResumeManager();
				boolean isTranslate=false;
				
				StringBuilder buffer=viewMgr.viewResume(resume, templateFile, "", 1,isTranslate);
				if(buffer!=null)
				{
					String attachPath=projectDir+"/Attachments/"+(Math.round(idJsk/2000)+1)+File.separator+idJsk;
					String ph_cid="ph_"+new java.util.Date().getTime();
					String sr_cid="sr_"+new java.util.Date().getTime();
					String sf_cid="sf_"+new java.util.Date().getTime();
					
					viewMgr.replaceAll(buffer,"superresume_logo.gif", "cid:"+sr_cid);
					viewMgr.replaceAll(buffer,"saf_logo.gif", "cid:"+sf_cid);
					if((idEmp>0))
					{
						if((new File(logoPath).exists()))
						{
							viewMgr.replaceAll(buffer,"#COMPANY_LOGO#","<img src=\"cid:"+lg_cid+"\">");
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_LOGO#","");
							viewMgr.replaceAll(buffer,"#END_COMPANY_LOGO#", "");
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_NAME#","<!--");
							viewMgr.replaceAll(buffer,"#END_COMPANY_NAME#", "-->");
							viewMgr.replaceAll(buffer,"#COMPANY_NAME#","");
						}
						else
						{
							viewMgr.replaceAll(buffer,"#COMPANY_LOGO#","");
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_LOGO#","<!--");
							viewMgr.replaceAll(buffer,"#END_COMPANY_LOGO#", "-->");
							viewMgr.replaceAll(buffer,"#COMPANY_NAME#","");
							viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_NAME#","");
							viewMgr.replaceAll(buffer,"#END_COMPANY_NAME#", "");
						}
					}
					else
					{
						viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_LOGO#","<!--");
						viewMgr.replaceAll(buffer,"#END_COMPANY_LOGO#", "-->");
						viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_NAME#","<!--");
						viewMgr.replaceAll(buffer,"#END_COMPANY_NAME#", "-->");
					}
						
					Attachment photo=new AttachmentManager().getPhoto(idJsk);
					if(photo!=null)
					{
						photoPath=attachPath+File.separator+photo.getIdFile()+"_"+photo.getFileType()+Util.getFileExtension(photo.getFileFormat());
						if(new File(photoPath).exists())
						{
							viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "<img src=\"cid:"+ph_cid+"\" style='max-width:84px;' width='84'>");
						}
						else
						{
							viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "");
						}
					}
					else
					{
						viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "");
					}		
									
					if(idLang== 38)
					{
						viewMgr.replaceAll(buffer,"#SEND_DATE#",Util.getThaiDate());
					}
					else 
					{
						viewMgr.replaceAll(buffer,"#SEND_DATE#",Util.nowDateFormat("MMMM dd, yyyy"));
					}								
								
					String content=fb.getMsg()+buffer.toString();
					content=content+"<br><center><b><a href=\"http://www.superresume.com/ViewResume?Enc="+Enc+"&Key="+Key+"&idLang="+idLang+"\" target=\"_blank\"><u><FONT COLOR=\"#FF0000\">***If you cannot read this Super Resume, Please click here***</FONT></u></a></b></center>";
					MailManager mailMgr=null;
					try
					{
						if(encode!=null && !encode.equals(""))
						{
							mailMgr=new MailManager(encode);
						}
						else
						{
							mailMgr=new MailManager();
						}
						
						if(Util.isEmail(fb.getSender()))
						{
							mailMgr.setReplyTo(fb.getSender());
						}
						else
						{
							mailMgr.setReplyTo(fb.getSend_name());
						}
						
						mailMgr.setSender("no-reply@jobtopgun.com", fb.getSender().isEmpty() ? fb.getSend_name() : fb.getSender());
						mailMgr.setRecepient(fb.getRecipient());
						mailMgr.setSubject(fb.getSubject());	
						mailMgr.setCc(fb.getCc());
						mailMgr.addHTML(content);
					
						OutputStreamWriter output=null;
						FileOutputStream fos=null;
						String rsFile=projectDir+"/Attachments/"+(Math.round(idJsk/2000)+1)+File.separator+idJsk+File.separator+"superresume.html";
						try
						{
							fos = new FileOutputStream (rsFile);
							output = new OutputStreamWriter(fos, "UTF-8"); 
							output.write(content);
							output.close();
							File f=new File(rsFile);
							if(f.exists())
							{
								mailMgr.addAttachment(rsFile, "superresume.html");
							}
						}
						catch(Exception e)
						{
						}
						finally
						{
							if(output != null)
							{
								try
								{
									output.close();
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}
						
						if(photo!=null)
						{
							if(new File(photoPath).exists())
							{
								mailMgr.addImage(photoPath,true,ph_cid);
							}
						}
						
						if(resume.getIdResume()>0)
						{
							mailMgr.addImage(srLogoPath,true,sr_cid);
						}
						else
						{
							mailMgr.addImage(safLogoPath,true,sf_cid);
						}
						if(new File(logoPath).exists())
						{
							mailMgr.addImage(logoPath,true,lg_cid);
						}
						mailMgr.send();
						int check=fem.insFwdRec(fb.getJtg_fwd_id(), idJsk, idResume, idEmp, idPosition);
						if(check==1)
						{
							PrintWriter out = response.getWriter();
							out.println( "<SCRIPT LANGUAGE=\"JavaScript\">" );
							out.println("alert(\"Successfully sent.\");");
							out.println( "location.href='"+fb.getTopage()+"' ");
							out.println( "</SCRIPT>" );
							
					        out.close();
						}
						else
						{
							PrintWriter out = response.getWriter();
							out.println("ERROR");
					        out.close();
						}
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				//error not have in fwd 
			}
		}
    }catch(Exception ex)
    {ex.printStackTrace();}
}
	
	 

}
