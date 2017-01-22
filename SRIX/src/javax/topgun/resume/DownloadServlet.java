package javax.topgun.resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.topgun.resume.Attachment;
import com.topgun.resume.AttachmentManager;
import com.topgun.util.Decryption;
import com.topgun.util.Util;

public class DownloadServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		int idJsk=Util.getInt(request.getParameter("idJsk"));
		int idFile=Util.getInt(request.getParameter("idFile"));
		if(idJsk<0)
		{
			//----------------Use for download attachment from email---------------------
			String enc=request.getParameter("Enc")!=null?request.getParameter("Enc"):"";
			String key=request.getParameter("Key")!=null?request.getParameter("Key"):"";		
			if((!enc.equals("")) && (!key.equals(""))) 
			{
				Decryption decryption=new Decryption(enc, key);
				idJsk=decryption.getIdJsk();
			}
		}
		if(idJsk<0)
		{
			idJsk=Util.getInt(request.getSession().getAttribute("SESSION_ID_JSK"));
		}
		
		if(idJsk<0)
		{
			response.sendRedirect("/LogoutServ");
			return;
		}	
		if(idJsk>0)
		{			
			try
			{
				ServletContext context = request.getSession().getServletContext();
				String path = context.getRealPath("")+File.separator+"Attachments";		
				String localFileName=path+File.separator+(Math.round(idJsk/2000)+1)+File.separator+idJsk;
				Attachment att = new AttachmentManager().get(idJsk,idFile);
				if(att!=null)
				{
					String fileName = idFile+"_"+att.getFileType()+Util.getFileExtension(att.getFileFormat());
					localFileName = localFileName+File.separator+fileName;
						
					if((!fileName.equals(""))&&(idFile!=-1)&&(!localFileName.equals(""))&&(new File(localFileName).exists()))
					{
						String view=request.getParameter("view")!=null?request.getParameter("view"):"";
						if(!view.equals("true"))
						{
							response.setContentType("application/octet-stream; charset=UTF-8"); 
							response.setHeader("Content-disposition", "attachment;filename="+fileName);
							response.setContentLength((int)new File(localFileName).length());
						}
						else
						{
							response.setContentType(att.getFileFormat());
						}
					    BufferedOutputStream output = null;
					    BufferedInputStream input = null;
		
					    if(response.getOutputStream()!=null)
					    {
					    	output = new BufferedOutputStream(response.getOutputStream());
					    	input = new BufferedInputStream(new FileInputStream(localFileName));
					
					    	byte[] buffer = new byte[4096];
					    	int size=-1;
					    	while((size=input.read(buffer,0,4096))!=-1)
					    	{
					    		output.write(buffer, 0, size);
					    	}		    		
					    }
					    input.close();
					    output.flush();
					    output.close();
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
