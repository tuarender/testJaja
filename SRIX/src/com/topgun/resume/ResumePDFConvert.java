package com.topgun.resume;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.zefer.pd4ml.PD4ML;

import com.topgun.util.Encryption;



public class ResumePDFConvert {

	private int idJsk;
	private int idResume;
	private int htmlWidth = 668;
	private String superresumeUrl = "http://superresume.com";
	private final String PROGRAM_PATH = "/Volumes/DataStorage/EclipseProject/SRIX/SRIX/";
	private final String FONT_PATH = PROGRAM_PATH+"/pd4ml/fonts/";
	private final String OUTPUT_PATH = PROGRAM_PATH+"/resume_pdf/";
	private String outputFilePath;
	private String viewResumeUrl = "";
	private PD4ML pd4ml;
	
	public ResumePDFConvert(int idJsk, int idResume)
	{
		this.setIdJsk(idJsk);
		this.setIdResume(idResume);
		this.pd4ml = new PD4ML();	
	}
	
	public byte[] getResumePdfByte()
	{
		byte[] result = null;
		try
		{
			String enc = Encryption.getEncoding(0, 0, this.idJsk, this.idResume);
			String key = Encryption.getKey(0, 0, this.idJsk, this.idResume);
			URL url = new URL(this.superresumeUrl+"/view/viewResumePdf.jsp?idResume="+idResume+"&Enc="+enc+"&Key="+key);
			InputStream is = url.openStream();
			ArrayList<Integer>	byteList = new ArrayList<Integer>();
			int i = 0;
			while((i = is.read()) != -1)
			{
				byteList.add(i);
			}

			result = new byte[byteList.size()];
			
			for(int j = 0 ; j < byteList.size() ; j++)
			{
				result[j] = (byte)(byteList.get(j) & 0xff);
			}
			
			is.close();
			is = null;
			byteList = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	public void convertResume()
	{
		try
		{
			this.viewResumeUrl = this.superresumeUrl+"/view/viewResume.jsp?idResume="+idResume+"&jSession="+req.getSession().getId();
			this.outputFilePath = OUTPUT_PATH+"/"+idJsk+"_"+idResume+".pdf";
	
			File outputFolder = new File(OUTPUT_PATH);
			if(!outputFolder.exists())	outputFolder.mkdirs();
			FileOutputStream fs = new FileOutputStream(this.outputFilePath);
			pd4ml.setHtmlWidth(this.htmlWidth);
			
			pd4ml.setCookie("JSESSIONID", req.getSession().getId());
			pd4ml.useTTF(FONT_PATH, true);
			pd4ml.setPageSize(PD4Constants.A4);
			pd4ml.overrideDocumentEncoding("UTF-8");
			pd4ml.render(this.viewResumeUrl, fs);
			fs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	*/
	
	public int removePdfFile()
	{
		int result = 0;
		try
		{
			new File(this.outputFilePath).delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public int getIdJsk() {
		return idJsk;
	}

	public void setIdJsk(int idJsk) {
		this.idJsk = idJsk;
	}

	public int getIdResume() {
		return idResume;
	}

	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public int getHtmlWidth() {
		return htmlWidth;
	}

	public void setHtmlWidth(int htmlWidth) {
		this.htmlWidth = htmlWidth;
	}
}
