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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class SendResumeThread extends Thread
{
	private Track track=null;
	
	public SendResumeThread(Track track)
	{
		this.track=track;
	}		
	
	public void run()
	{
		String projectDir="/oracle/bea/user_projects/domains/mydomain/applications/SRIX";
		//String projectDir="/Users/boriwat/Documents/JTGProject/SRIX/SRIX";
		//String projectDir="/Volumes/DataStorage/EclipseProject/SRIX/SRIX";
		String srLogoPath=projectDir+"/images/superresume_logo.gif";
		String safLogoPath=projectDir+"/images/superresume_logo.gif";
		
		String photoPath="";
		String logoPath="";
		String lg_cid="";
		String encode="";
		int isAttach=0;

		if(track!=null)
		{
			if(track.getSent()==1)
			{
				sendAutoReply();
			}
			else
			{
				logoPath=new EmployerManager().getLogoPath(track.getIdEmp());
				if(new File(logoPath).exists())
				{
					lg_cid=track.getIdEmp()+"_"+new java.util.Date().getTime();
				}
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
				
		//if((countryJob==216) && (track.getIdPosition()<100000)){
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
		//}
				
		
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
			Resume resume=new ResumeManager().getIncludeDeleted(track.getIdJsk(),track.getIdResume());
			int idParent=track.getIdResume();
			int viewType=0;
			String resumeListLink="";
			List<Resume> rsChild= new ResumeManager().getSubResume(resume.getIdJsk(), resume.getIdResume());
			if(resume.getIdParent()==0)
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String language=MasterDataManager.getLanguageName(resume.getIdLanguage(),11)!=""?MasterDataManager.getLanguageName(resume.getIdLanguage(),11):"Thai";
				
				String encChild = "";
				String keyChild = "";
				if(resume.getIdLanguage()==38){viewType=1;}
				resumeListLink+="<ol>";
				resumeListLink+="<li>"+resume.getResumeName()+" (Original "+language+")&nbsp;&nbsp;<a href='http://www.superresume.com/ViewResume?viewType="+viewType+"&Key="+Key+"&Enc="+Enc+"&idLang=&applyDate="+Encryption.encode(""+dateFormat.format(date))+"'>View</a></li>";
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
							if((track.getIdEmp()>0)&&(track.getIdPosition()>0))
							{
								Enc=Encryption.getEncoding(track.getIdEmp(),track.getIdPosition(),track.getIdJsk(),rsChild.get(i).getIdResume());
								Key=Encryption.getKey(track.getIdEmp(),track.getIdPosition(),track.getIdJsk(),rsChild.get(i).getIdResume());
							}
							else
							{
								Enc=Encryption.getEncoding(0,0,track.getIdJsk(),rsChild.get(i).getIdResume());
								Key=Encryption.getKey(0,0,track.getIdJsk(),rsChild.get(i).getIdResume());
							}
						}
					}
				}
				resumeListLink+="</ol>";
			}
			
			String templateFile = projectDir+File.separator+"templates"+File.separator;
			String templateCountry="TH";
			Country country=MasterDataManager.getCountry(resume.getTemplateIdCountry(), resume.getIdLanguage());
			if(country!=null)
			{
				templateCountry=country.getAbbreviation();
			}
			if(resume.getIdResume()==0 || resume.getIdParent() == -1)
			{
				templateFile+="SAF_"+templateCountry+".html";
			}
			else
			{				
				templateFile+="TH_A.html";
				/*if(track.getIdEmp()<0)
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
				}*/
			}
			ViewResumeManager viewMgr=new ViewResumeManager();
			//StringBuilder buffer=viewMgr.viewResume(resume, templateFile, track.getPositionOther(), 1,false);
			StringBuilder buffer = null;
			if(resume.getIdParent() != 0)
			{
				//is child resume.
				buffer = viewMgr.viewResumeChild(resume,templateFile, track.getPositionOther(),1, false, viewType);
			}
			else
			{
				buffer = viewMgr.viewResume(resume,templateFile, track.getPositionOther(),1, false, viewType);
			}
			if(buffer!=null)
			{
				String attachPath=projectDir+"/Attachments/"+(Math.round(track.getIdJsk()/2000)+1)+File.separator+track.getIdJsk();
				String ph_cid="ph_"+new java.util.Date().getTime();
				String sr_cid="sr_"+new java.util.Date().getTime();
				String sf_cid="sf_"+new java.util.Date().getTime();
				
				viewMgr.replaceAll(buffer,"superresume_logo.gif", "cid:"+sr_cid);
				viewMgr.replaceAll(buffer,"saf_logo.gif", "cid:"+sf_cid);
				if(track.getIdEmp()>0)
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

				}else{
					viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_LOGO#","<!--");
					viewMgr.replaceAll(buffer,"#END_COMPANY_LOGO#", "-->");
					viewMgr.replaceAll(buffer,"#BEGIN_COMPANY_NAME#","");
					viewMgr.replaceAll(buffer,"#COMPANY_NAME#",track.getEmpOther());
					viewMgr.replaceAll(buffer,"#END_COMPANY_NAME#", "");
				}
					
				Attachment photo=new AttachmentManager().getPhoto(track.getIdJsk());
				if(photo!=null)
				{
					photoPath=attachPath+File.separator+photo.getIdFile()+"_"+photo.getFileType()+Util.getFileExtension(photo.getFileFormat());
					if(new File(photoPath).exists())
					{
						viewMgr.replaceAll(buffer,"#PHOTOGRAPH#", "<img src=\"cid:"+ph_cid+"\" width=\"86\">");
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
				String firstName = Util.getStr(resume.getFirstName()).trim();
				String lastName = Util.getStr(resume.getLastName()).trim();
				String firstNameThai = Util.getStr(resume.getFirstNameThai()).trim();
				String lastNameThai = Util.getStr(resume.getLastNameThai()).trim();
				String jskFullName = "";
				
				
				if(resume.getIdLanguage()==38)
				{
					if(firstNameThai.length() > 0)
					{
						jskFullName = firstNameThai+" "+lastNameThai;
					}
					else
					{
						jskFullName = firstName+" "+lastName;
					}
				}
				else
				{
					if(firstName.length() > 0)
					{
						jskFullName = firstName+" "+lastName;
					}
					else
					{
						jskFullName = firstNameThai+" "+lastNameThai;
					}
				}
				
				subject = "Super Resume from "+jskFullName+" for "+track.getPositionOther();
				
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
					
					mailMgr.setSender("no-reply@jobtopgun.com", "JOBTOPGUN.COM");
					
					mailMgr.setReplyTo(jsk.getUsername());
					mailMgr.setSubject(subject);	

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
											
											attachment+="<td  class='f11' align='right'><a href=\"http://www.superresume.com/DownloadServ?time="+times+"&view=true&idFile="+attach.getIdFile()+"&Enc="+Enc+"&Key="+Key+"\" target=\"_blank\">View</a><span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
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
							attachment+="</table><br/><br/>";
							if(isAttach!=1)
							{
								content=content.replaceAll("<!--#ATTACHMENTS#-->", attachment);
							}
						}
					}	
					if(resumeListLink!="")
					{
						resumeListLink="<br/><b>For printing, please click \"View\" on the links below:</b>"+resumeListLink+"<hr/>";
					}
					mailMgr.addHTML(resumeListLink+content);
						
					if(SendResumeUtil.isUsePdfAttachFile(track))
					{
						ResumePDFConvert resumePDFConvert = new ResumePDFConvert(track.getIdJsk(), track.getIdResume());
						byte[] resumePdfByte = resumePDFConvert.getResumePdfByte();
						if(resumePdfByte != null)
						{
							mailMgr.addAttachmentFromByte(resumePdfByte, "superresume.pdf");
						}
					}
					else
					{
						mailMgr.addAttachmentFromByte(content, "superresume.html");
					}
					
					/*
					OutputStreamWriter output=null;
					FileOutputStream fos=null;
					String rsPath=projectDir+"/Attachments";
					if(!(new File(rsPath).exists())){
						new File(rsPath).mkdir();
					}
					if(new File(rsPath).exists()){	
						rsPath=rsPath+File.separator+(Math.round(track.getIdJsk()/2000)+1);
						if(!(new File(rsPath).exists())){
							new File(rsPath).mkdir();
						}
						rsPath=rsPath+File.separator+track.getIdJsk();
						if(!(new File(rsPath).exists())){
							new File(rsPath).mkdir();
						}
						if(new File(rsPath).exists())
						{
							String rsFile=rsPath+File.separator+"superresume.html";
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
						}
					}
					
					*/
					
					
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
					
					if(track.getRecipient() != null)
					{
						String[] recepientArr = track.getRecipient().split(",");
						//mailMgr.setRecepient("anuwat@topgunthailand.com,anuwat.palasak@gmail.com");
						if(recepientArr != null)
						{
							for (String recepient : recepientArr) {
								mailMgr.setRecepient(recepient);
								mailMgr.send();
							}
						}
						
								
					}
					
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
			Resume rs=new ResumeManager().get(track.getIdJsk(), track.getIdResume());
			String firstName=Util.getStr(rs.getFirstName());
			if(firstName.equals(""))
			{
				firstName=rs.getFirstNameThai();
			}
			String subject="Your Super Resume has been sent to "+employer +" for "+position;
			
			String message="<html><body><table border=0 width=650><tr><td>สวัสดีค่ะ <b>คุณ"+firstName+",</b>";
			message+="<br><br>Super Resume ของคุณได้ถูกส่งไปสมัครงานที่<b>"+employer;
			message+="</b> ในตำแหน่ง <b>"+position+"</b> เมื่อวันที่ <b>"+Util.DateToStr(new java.util.Date(), "d/M/yyyy")+"</b>  เรียบร้อยแล้วค่ะ <br>";
			message+="สมัครงานตำแหน่งงานอื่นเพิ่มเติม กรุณาไปที่<a href='http://www.jobtopgun.com' target='_blank'>www.jobtopgun.com</a><br><br>";
			message+="This Auto-Reply is to confirm that your Super Resume has been successfully sent to <b>"+employer;
			message+="</b> for the position of <b>"+position+"</b> on <b>"+Util.DateToStr(new java.util.Date(), "d/M/yyyy")+"</b><br><br>";
			message+="Search for more jobs, go to <a href='http://www.jobtopgun.com' target='_blank'>www.jobtopgun.com</a><br><br>";
			message+="<b>ขอให้คุณโชคดีในการสมัครงานค่ะ</b><br>Good luck<br><br>";
			message+="<b>JOBTOPGUN.com และ Superresume.com</b></td></tr></table></body></html>";
			
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
