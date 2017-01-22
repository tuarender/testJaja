package com.topgun.resume;

import com.topgun.shris.masterdata.Country;
import com.topgun.shris.masterdata.MasterDataManager;
import com.topgun.util.DBManager;
import com.topgun.util.Encryption;
import com.topgun.util.MailManager;
import com.topgun.util.Util;
import com.topgun.resume.AttachmentManager;
import com.topgun.resume.Attachment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.text.DecimalFormat;


public class SendResumeThread2 extends Thread
{
	private Track track=null;
	
	public SendResumeThread2(Track track)
	{
		this.track=track;
	}		
	
	public void run()
	{
		String projectDir="/oracle/bea/user_projects/domains/mydomain/applications/superresume9";
		String srLogoPath=projectDir+"/images/superresume_logo.gif";
		String safLogoPath=projectDir+"/images/saf_logo.gif";
		
		String photoPath="";
		String logoPath="";
		String lg_cid="";
		String encode="";
		int isAttach=0;

		if(track!=null)
		{
			logoPath=new EmployerManager().getLogoPath(track.getIdEmp());
			if(new File(logoPath).exists())
			{
				lg_cid=track.getIdEmp()+"_"+new java.util.Date().getTime();
			}			
		}
		
		//----------------------This part for marketing report----------------------------------------------
		int countryJob=216;
		if(track.getIdEmp()>0 && track.getIdPosition()>0)
		{
			DBManager dd=null;
			try
			{
				dd=new DBManager();
				dd.createPreparedStatement("SELECT JOB_IN_COUNTRY FROM POSITION WHERE ID_EMP=? AND ID_POSITION=?");
				dd.setInt(1, track.getIdEmp());
				dd.setInt(2, track.getIdPosition());
				dd.executeQuery();
				if(dd.next())
				{
					countryJob=dd.getInt("JOB_IN_COUNTRY");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				dd.close();									
			}
		}
				
		if((countryJob==216) && (track.getIdPosition()<100000))
		{
			String SQLZ="INSERT INTO INTER_TRACK_CURRENT_MONTH(SENTDATE,ID_JSK,ID_RESUME,ID_EMP,ID_POSITION,ID_TRACK) VALUES(SYSDATE,?,?,?,?,?)";
			DBManager dbm=null;
			try
			{
				dbm=new DBManager();
				dbm.createPreparedStatement(SQLZ);
				dbm.setInt(1, track.getIdJsk());
				dbm.setInt(2, track.getIdResume());
				dbm.setInt(3, track.getIdEmp());
				dbm.setInt(4, track.getIdPosition());
				dbm.setInt(5, track.getIdTrack());
				dbm.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				dbm.close();									
			}
		}
				
		Resume resume=null;	
		if(track.getSent()!=1)
		{
			//------------------check encoding-----------------
			DBManager dr=null;
			try
			{
				dr=new DBManager();
				dr.createPreparedStatement("SELECT ENCODE,ATTACHMENT FROM INTER_EMPLOYER_MAIL WHERE ID_EMP=?");
				dr.setInt(1, track.getIdEmp());
				dr.executeQuery();
				if(dr.next())
				{
					encode=Util.getStr(dr.getString("ENCODE"));
					isAttach=Util.getInt(dr.getInt("ATTACHMENT"));
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				dr.close();
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
			
			resume=new ResumeManager().getIncludeDeleted(track.getIdJsk(),track.getIdResume());
			if(resume!=null)
			{
				System.out.println(resume.getIdJsk()+"=>"+resume.getIdResume());
				String templateFile = projectDir+File.separator+"templates"+File.separator;
				String templateCountry="TH";
				Country country=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage());
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
					if(track.getIdEmp()<0)
					{
						Miscellaneous mis= new MiscellaneousManager().get(track.getIdJsk(),track.getIdResume());
						if(mis!=null)
						{
							templateFile+=mis.getResumeTemplate();
						}
						else
						{
							templateFile+=templateCountry+"_A.html";
						}
					}
					else
					{
						String template=new EmployerManager().getResumeTemplate(track.getIdEmp());
						if(!template.equals(""))
						{
							templateFile+=templateCountry+"_"+template.toUpperCase()+".html";
						}
						else
						{
							templateFile+=templateCountry+"_A.html";
						}
					}
				}
				ViewResumeManager viewMgr=new ViewResumeManager();
				StringBuilder buffer=viewMgr.viewResume(resume, templateFile, track.getPositionOther(), 1,false);
				if(buffer!=null)
				{
					String attachPath=projectDir+"/Attachments/"+(Math.round(track.getIdJsk()/2000)+1)+File.separator+track.getIdJsk();
					String ph_cid="ph_"+new java.util.Date().getTime();
					String sr_cid="sr_"+new java.util.Date().getTime();
					String sf_cid="sf_"+new java.util.Date().getTime();
					
					viewMgr.replaceAll(buffer,"superresume_logo.gif", "cid:"+sr_cid);
					viewMgr.replaceAll(buffer,"saf_logo.gif", "cid:"+sf_cid);
					if((track.getIdEmp()>0) && (new File(logoPath).exists()))
					{
						viewMgr.replaceAll(buffer,"#COMPANY_LOGO#","<img src=\"cid:"+lg_cid+"\">");
					}
					else
					{
						viewMgr.replaceAll(buffer,"#COMPANY_LOGO#", track.getEmpOther());
					}
						
					Attachment photo=new AttachmentManager().getPhoto(track.getIdJsk());
					if(photo!=null)
					{
						photoPath=attachPath+File.separator+photo.getIdFile()+"_"+photo.getFileType()+Util.getFileExtension(photo.getFileFormat());
						if(new File(photoPath).exists())
						{
							viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "<img src=\"cid:"+ph_cid+"\" width=\"120\">");
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
									
					if(resume.getIdLanguage() == 38)
					{
						viewMgr.replaceAll(buffer,"#SEND_DATE#",Util.getThaiDate());
					}
					else 
					{
						viewMgr.replaceAll(buffer,"#SEND_DATE#",Util.nowDateFormat("MMMM dd, yyyy"));
					}								
								
					String content=buffer.toString();
					if((track.getIdEmp()>0) && (track.getIdPosition()>0))
					{
						content=content+"<br><center><b><a href=\"http://www.superresume.com/ViewResume?Enc="+Enc+"&Key="+Key+"\" target=\"_blank\"><u><FONT COLOR=\"#FF0000\">***If you cannot read this Super Resume, Please click here***</FONT></u></a></b></center>";
						content+="<img src=\"http://www.jobtopgun.com/captcha/ImageView?Enc="+Enc+"&Key="+Key+"\">";
					}
					
					String subject="";
					
					if(Util.getStr(track.getSource()).equals("SuperResume"))
					{
						subject="From Super Resume for "+track.getPositionOther();
					}
					else if(Util.getStr(track.getSource()).equals("Jobtopgun"))
					{
						subject="From Jobtopgun for "+track.getPositionOther();
					}
					Jobseeker jsk=new JobseekerManager().get(track.getIdJsk());
					
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
						
						mailMgr.setSender(jsk.getUsername());
						mailMgr.setMailReturn("supreturn@jobinthailand.com");
						mailMgr.setRecepient(track.getRecipient());
						mailMgr.setSubject(subject);	
						String rejectInformation="idEmp:"+track.getIdEmp()+"#idPosition:"+track.getIdPosition()+"#idJsk:"+track.getIdJsk()+"#idResume:"+track.getIdResume()+"#emailRecipient:"+track.getRecipient()+"#emailSender:"+jsk.getUsername();
						rejectInformation= "\n<!--\n[rejectInformation]"+rejectInformation+"[/rejectInformation]\n -->";
						content=content+rejectInformation;
	
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
						mailMgr.addHTML(content);
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
							/*File f=new File(rsFile);
							if(f.exists())
							{
								f.delete();
							}*/
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
	
						
						String SQLX="UPDATE INTER_TRACK SET SENT=1 WHERE (ID=?) AND (ID_JSK=?) AND (ID_RESUME=?) AND (ID_EMP=?) AND (ID_POSITION=?) ";
						DBManager dbx=null;
						try
						{
							dbx=new DBManager();
							dbx.createPreparedStatement(SQLX);
							dbx.setInt(1, track.getIdTrack());
							dbx.setInt(2, track.getIdJsk());
							dbx.setInt(3, track.getIdResume());
							dbx.setInt(4, track.getIdEmp());
							dbx.setInt(5, track.getIdPosition());
							dbx.executeUpdate();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							dbx.close();									
						}
						sendAutoReply();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				sendAutoReply();
			}
			
		}
	}	
	
	public void sendAutoReply()
	{
		String position=track.getPositionOther();
		String employer=track.getEmpOther();
			
		if((!position.equals(""))&&(!employer.equals("")))
		{
			String rsType="Super Resume";
			if(track.getIdResume()==0)
			{
				rsType="SAF";
			}
			Resume rs=new ResumeManager().get(track.getIdJsk(), track.getIdResume());
			if(rs!=null)
			{
				String firstName=Util.getStr(rs.getFirstName());
				if(firstName.equals(""))
				{
					firstName=rs.getFirstNameThai();
				}
				String subject="Your "+rsType+" has been sent to "+employer +" for "+position;
				
				String message="<html><body><table border=0 width=650><tr><td>Dear <b>"+firstName+"</b><br><br>This Auto-Reply is to confirm that your <b>"+rsType+"</b> has been successfully sent to <b>"+employer;
				message+="</b> for the position of <b>"+position+"</b> on <b>"+Util.DateToStr(new java.util.Date(), "d/M/yyyy")+"</b>.<br><br>";
				message+="Basically, HR of the company will contact you within a few days to a month depending on pre-screening process.";
				message+="<br><br>Sincerely yours,<br><a href='http://www.jobtopgun.com' target='_blank'>Jobtopgun</a></td></tr></table></body></html>";
				
				Jobseeker jsk=new JobseekerManager().get(track.getIdJsk());
				if(jsk!=null)
				{
					MailManager mailMgr=null;
					try
					{
						mailMgr=new MailManager();
						mailMgr.setRecepient(jsk.getUsername());
						mailMgr.setSender("autoreply@superresume.com");
						mailMgr.setSubject(subject);
						mailMgr.addHTML(message);
						mailMgr.send();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
}
