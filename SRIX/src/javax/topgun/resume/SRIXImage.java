package javax.topgun.resume;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import com.topgun.util.Util;


import com.topgun.resume.JobseekerManager;

public class SRIXImage extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		int idJsk=-1;
		try
		{
		
			String referer=Util.getStr(request.getHeader("Referer"));
			request.getSession().removeAttribute("SESSION_ID_JSK");
	
			
			//if(referer.indexOf("localhost")>=0 || referer.indexOf("127.0.0.1")>=0)
			//{
				idJsk=Util.getInt(request.getParameter("idJsk"));
				
				String jSession=Util.getStr(request.getParameter("jSession"));
				if(idJsk>=0 && !jSession.equals(""))					
				{

					if(new JobseekerManager().isLogin(idJsk, jSession))
					{
						request.getSession().setAttribute("SESSION_ID_JSK", idJsk);
					}
				}
			//}
			int w = 1;
			int h = 1;
			BufferedImage buffer = new BufferedImage(w, h, 1);
			Graphics2D g = buffer.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(new Font("Verdana", 1, 20));
			g.setColor(new Color(0xffffff));
			g.fillRect(0, 0, w, h);
			response.setContentType("image/png");
			OutputStream os = response.getOutputStream();
			ImageIO.write(buffer, "png", os);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


}
