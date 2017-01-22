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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.topgun.resume.Attachment;
import com.topgun.resume.AttachmentManager;
import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

import javaxt.io.Image;


public class AppsUploadServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String idJsk = Base64.getDecoder().decode(Util.getStr(request.getParameter("idJsk"))).toString();
		Gson gson = new Gson();
        ArrayList<String> errors = new ArrayList<String>();
        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<Attachment> attachmentList = null;
        int totalFileSize = 0;
        String urlPhoto = null;
        String service = Util.getStr(request.getParameter("service"),"");
        int idFile = Util.getInt(request.getParameter("idFile"),0);
        int idJsk = Util.getInt(request.getParameter("idJsk"),0);
        PropertiesManager propMgr=new PropertiesManager();
        long startTime=new java.util.Date().getTime();
        
		String locale=Util.getStr(request.getParameter("locale"),Util.getStr(request.getSession().getAttribute("SESSION_LOCALE"),"th_TH"));
		String urlErrorRedirect = "";
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		String docType="";
		String title=null;
		FileItem file=null;
		String format=null;
		long size=0;
		String os = "None";
		boolean chkPhoto = false ;
		
		try{
			List  items = upload.parseRequest(request);			
			Iterator itr = items.iterator();
			//--------get data-------------
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if(item.getFieldName().equals("docType")){
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
				else if(item.getFieldName().equals("idJsk"))
				{
					idJsk = Util.getInt(item.getString(),0) ;
				}
				else if(item.getFieldName().equals("photo"))
				{
					file=item;
				}
				else if(item.getFieldName().equals("os"))
				{
					os = Util.getStr(item.getString(),"None");
				}
			}
			System.out.println("Service : "+service);
			System.out.println("docType : "+docType);
			System.out.println("os : "+os);
			System.out.println("idJsk : "+idJsk);
			if(file!=null)
			{
				System.out.println("file Not null");
				int chkSize =2*1024*1024;
				if(docType.equals("PHOTOGRAPH")){
					chkSize=1024*1024;
					title = "photo";
				}
				size=file.getSize();
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
						format = file.getContentType();
						System.out.println("format file : "+format);
						if(!format.equals("application/octet-stream")){
							if(Util.isSupportFormat(format, docType)){
								chkPhoto = true ;
							}
							else{
								errors.add(propMgr.getMessage(locale, "PHOTO_TYPE_ERROR"));
								elements.add("SYSTEM");
							}
						}else{
							format = Util.getFileFormat(file.getName());
							if(!format.equals("error")){
								chkPhoto = true ;
								
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
			if(service.equals("savePhoto")){
				//---------------set data----------------
				if((docType!=null) && (title!=null) && (file!=null) && idJsk>0 && chkPhoto){	
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
										urlPhoto = "http://www.superresume.com/"+"Attachments"+"/"+(Math.round(idJsk/2000)+1)+"/"+idJsk+"/"+fileName;
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
