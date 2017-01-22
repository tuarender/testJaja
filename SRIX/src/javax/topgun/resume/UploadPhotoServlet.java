package javax.topgun.resume;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javaxt.io.Image;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.topgun.resume.Attachment;
import com.topgun.resume.AttachmentManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public class UploadPhotoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Attachment> attachmentList = null;
        int totalFileSize = 0;
        String urlPhoto = null;
        String service = Util.getStr(request.getParameter("service"),"");
        int idFile = Util.getInt(request.getParameter("idFile"),0);
        PropertiesManager propMgr=new PropertiesManager();
        long startTime=new java.util.Date().getTime();
        
		int idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		String urlErrorRedirect = "";
		if(idJsk<=0){
			errors.add(propMgr.getMessage(locale, "EDU_NOT_LOGGED_IN"));
			elements.add("SYSTEM");
			urlErrorRedirect = "/LogoutServ";
		}
		else{
			if(service.equals("")){
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				String docType="";
				String title=null;
				FileItem file=null;
				String format=null;
				long size=0;
				
				try{
					List  items = upload.parseRequest(request);			
					Iterator itr = items.iterator();
					//--------get data-------------
					while (itr.hasNext()) {
						FileItem item = (FileItem) itr.next();
						if(item.isFormField()){	
							if(item.getFieldName().equals("DocType")){
								docType=item.getString();
							}
							if(item.getFieldName().equals("idFile")){
								idFile=Util.getInt(item.getString(), 0);
							}
							else if(item.getFieldName().equals("fileTitle")){
								title=new String(item.getString().getBytes("ISO8859-1"),"UTF-8");
							}
							else if(item.getFieldName().equals("service")){
								service=item.getString();
							}
							/*
							else if(item.getFieldName().equals("file_upload_name")){
								name = item.getString();
							}*/
						}
						else{
							int chkSize =2*1024*1024;
							if(docType.equals("PHOTOGRAPH")){
								chkSize=1024*1024;
								title = "photo";
							}
							size=item.getSize();
							if(size<chkSize){ 
								boolean existTotal = true;
								if(!docType.equals("PHOTOGRAPH")){
									int allowSize = 5*1024*1024;
									int totalSize = new AttachmentManager().getTotalFileSize(idJsk, docType);
									int existSize = allowSize - totalSize;
									if(existSize<size){
										existTotal = false;
									}
								}
								
								if(existTotal){
									format = item.getContentType();
									if(!format.equals("application/octet-stream")){
										if(Util.isSupportFormat(format, docType)){
											if(item.getFieldName().equals("inputUploadFile")){
												file=item;
											}
										}
										else{
											errors.add(propMgr.getMessage(locale, "PHOTO_TYPE_ERROR"));
											elements.add("SYSTEM");
										}
									}else{
										format = Util.getFileFormat(item.getName());
										if(!format.equals("error")){
											if(item.getFieldName().equals("inputUploadFile")){
												file=item;
											}
										}else{
											errors.add(propMgr.getMessage(locale, "PHOTO_TYPE_ERROR"));
											elements.add("SYSTEM");
										}
									}
								}
								else{
									errors.add(propMgr.getMessage(locale, "PHOTO_SIZE_ERROR"));
									elements.add("SYSTEM");
								}
							}
							else{
								if(docType.equals("PHOTOGRAPH")){
									errors.add(propMgr.getMessage(locale, "PHOTO_SIZE_ERROR"));
									elements.add("SYSTEM");
								}
								else {
									errors.add(propMgr.getMessage(locale, "FILE_SIZE_ERROR"));
									elements.add("SYSTEM");
								}
							}
						}
					}
					
					if(service.equals("savePhoto")){
						//---------------set data----------------
						if((docType!=null)&&(title!=null)&&(file!=null)){	
							try{										
								ServletContext context = request.getSession().getServletContext();
								String path = context.getRealPath("")+File.separator+"Attachments";
								if(!(new File(path).exists())){
									new File(path).mkdir();
								}
								if(new File(path).exists()){	
									path=path+File.separator+(Math.round(idJsk/2000)+1);
									if(!(new File(path).exists())){
										new File(path).mkdir();
									}
									path=path+File.separator+idJsk;
									if(!(new File(path).exists())){
										new File(path).mkdir();
									}
									if(new File(path).exists()){
										int idF = new AttachmentManager().getNextIdFileAttachment(idJsk);
										String fileName=idF+"_"+docType+Util.getFileExtension(format);
										File imageFile = new File(path,fileName);
										file.write(imageFile);
										
										//-------------------------Transform image-----------------
										Image image = new Image(imageFile);
										image.rotate();
										image.saveAs(imageFile);
										
										/*BufferedImage bufferImg = ImageIO.read(imageFile);
										BufferedImage imageTranform = null;
										
										ImageInformation imgInfo = Util.readImageInformation(imageFile);
										AffineTransform transform = Util.getExifTransformation(imgInfo);
										System.out.println(imgInfo.orientation);
										imageTranform = Util.transformImage(bufferImg, transform);
										
										imageTranform.flush();
										bufferImg.flush();
										*/
										//----------------------------------------------------------
										
										Attachment att = new Attachment();
										att.setIdJsk(idJsk);
										att.setIdFile(idF);
										att.setFileTitle(title);
										att.setFileType(docType); 
										att.setFileSize(size);
										att.setFileFormat(format);
										
										if(idFile>0){
											Attachment dataAttachment = new AttachmentManager().get(idJsk, idFile);
											if(dataAttachment!=null){
												if(new AttachmentManager().delete(dataAttachment)!=1){
													errors.add(propMgr.getMessage(locale, "PHOTO_DELETE_ERROR"));
													elements.add("SYSTEM");
												}
											}
										}
										if(errors.size()==0){
											if(new AttachmentManager().add(att)!=1){
												errors.add(propMgr.getMessage(locale, "PHOTO_INSERT_ERROR"));
												elements.add("SYSTEM");
											}
											else{
												urlPhoto = "/"+"Attachments"+"/"+(Math.round(idJsk/2000)+1)+"/"+idJsk+"/"+fileName;
												idFile = idF;
											}
										}
									}
								}
							}
							catch(Exception e){
								e.printStackTrace();
								errors.add(propMgr.getMessage(locale, "PHOTO_INSERT_ERROR"));
								elements.add("SYSTEM");
							}		
						}
					}
					else if(service.equals("addFile")){
						//---------------set data----------------
						if((docType!=null)&&(title!=null)&&(file!=null)){	
							try{										
								ServletContext context = request.getSession().getServletContext();
								String path = context.getRealPath("")+File.separator+"Attachments";
								if(!(new File(path).exists())){
									new File(path).mkdir();
								}
								if(new File(path).exists()){
									path=path+File.separator+(Math.round(idJsk/2000)+1);
									if(!(new File(path).exists())){
										new File(path).mkdir();
									}
									path=path+File.separator+idJsk;
									if(!(new File(path).exists())){
										new File(path).mkdir();
									}
									
									if(new File(path).exists()){
										int idF = new AttachmentManager().getNextIdFileAttachment(idJsk);
										String fileName=idF+"_"+docType+Util.getFileExtension(format);
										file.write(new File(path,fileName));
										Attachment att = new Attachment();
										att.setIdJsk(idJsk);
										att.setIdFile(idF);
										att.setFileTitle(title);
										att.setFileType(docType); 
										att.setFileSize(size);
										att.setFileFormat(format);
										if(new AttachmentManager().add(att)!=1){
											errors.add(propMgr.getMessage(locale, "FILE_INSERT_ERROR"));
											elements.add("SYSTEM");
										}
										else{
											idFile = idF;
										}
									}
								}
							}
							catch(Exception e){
								errors.add(propMgr.getMessage(locale, "FILE_INSERT_ERROR"));
								elements.add("SYSTEM");
							}		
						}
					}
					else if(service.equals("updateTitle")){
						Attachment att = new AttachmentManager().get(idJsk, idFile);
						if(att!=null){
							att.setFileTitle(title);
							if(new AttachmentManager().update(att)!=1){
								errors.add(propMgr.getMessage(locale, "FILE_UPDATE_ERROR"));
								elements.add("SYSTEM");
							}
						}
					}
					else{
						errors.add(propMgr.getMessage(locale, "FILE_SERVICE_REQUIRE"));
						elements.add("SYSTEM");
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else{
				if(service.equals("getPhoto")){
					ArrayList<Attachment> attList = (ArrayList<Attachment>)new AttachmentManager().getAllByFileType(idJsk, "PHOTOGRAPH");
					if(attList.size()>0){
						urlPhoto = "/"+"Attachments"+"/"+(Math.round(idJsk/2000)+1)+"/"+idJsk+"/"+attList.get(0).getIdFile()+"_"+attList.get(0).getFileType()+Util.getFileExtension(attList.get(0).getFileFormat());
						idFile = attList.get(0).getIdFile();
					}
				}
				else if(service.equals("getAllDocument")){
					attachmentList = (ArrayList<Attachment>)new AttachmentManager().getAllByFileType(idJsk, "DOCUMENT");
					totalFileSize = new AttachmentManager().getTotalFileSize(idJsk, "DOCUMENT");
				}
				else if(service.equals("getAllResume")){
					attachmentList = (ArrayList<Attachment>)new AttachmentManager().getAllByFileType(idJsk, "RESUME");
					totalFileSize = new AttachmentManager().getTotalFileSize(idJsk, "RESUME");
				}
				else if(service.equals("viewFile")){
					Attachment att = new AttachmentManager().get(idJsk, idFile);
					if(att!=null){
						urlPhoto = "/"+"Attachments"+"/"+(Math.round(idJsk/2000)+1)+"/"+idJsk+"/"+att.getIdFile()+"_"+att.getFileType()+Util.getFileExtension(att.getFileFormat());
						idFile = att.getIdFile();
					}
				}else if(service.equals("deleteFile")){
					System.out.println("idJsk:"+idJsk+"->idFile:"+idFile);
					Attachment att = new AttachmentManager().get(idJsk, idFile);
					if(new AttachmentManager().delete(att)!=1){
						errors.add(propMgr.getMessage(locale, "FILE_DELETE_ERROR"));
						elements.add("SYSTEM");
					}
				}
				else if(service.equals("deletePhoto")){
					ArrayList<Attachment> attList = (ArrayList<Attachment>)new AttachmentManager().getAllByFileType(idJsk, "PHOTOGRAPH");
					if(attList.size()>0){
						new AttachmentManager().delete(attList.get(0));
					}
				}
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
			json.put("attachmentList", attachmentList);
			json.put("idFile", idFile);
			json.put("urlPhoto", urlPhoto);
			json.put("totalFileSize", totalFileSize);
		}
		out.print(gson.toJson(json));
		out.close();
		
		long usageTime=new java.util.Date().getTime()-startTime;
		if(usageTime>15000)
		{
			System.out.println("\nUploadPhotoServlet => Usage "+usageTime+" msec");
			System.out.println("idJsk=> "+idJsk);
		}
	}
}
