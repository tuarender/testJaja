package com.topgun.resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendResumeUtil {
	
	/*
	 * Sender email!!!
	 * 
	 * supreturn@jobinthailand.com
	 * no-reply@jobtopgun.com
	 * mailreturn@jobinthailand.com
	 * 
	 */
	
	/*
	 * Sender email send to jobseeker
	 * 
	 * autoreply@superresume.com
	 * 
	 */
	
	
	private static HashMap<Integer, Boolean> idEmpException = new HashMap<Integer, Boolean>(){{
		put(6149, true);
		put(2359, true);
		put(15962, true);
		put(19246, true);
		put(18390, true);
		put(22043, true);
		put(3327, true);
		put(18702, true);
		put(5142, true);	//Panasonic
		put(23613, true);	//สุพรคลีนิค
		put(3849, true);	//CCL Label
		put(837, true);	//Sunflower Group
		put(19156, true);	//Bangkok University;
		put(24703, true);	////Anatolian
		put(1663, true);	//BEC-TERO Entertainment Public Company Limited.
		put(6110, true);	//Essilor Distribution Thailand
		put(19138, true);	//Vevo Systems Co., Ltd 
		put(5604, true);	//Thong Thai Textile Co.,Ltd.
		put(5855 ,true);	//Hyundai Motor (Thailand) Co.,Ltd.
		put(2944, true);	//Thai Edible Oil Co.,Ltd.
	}};
	
	private static List<String> exceptionDomain = new ArrayList<String>(){{
		add("@bu.ac.th");	//Bangkok University
		add("@anextour.com");	//Anatolian
		add("@essilor.co.th");	//Essilor Distribution Thailand
		add("hr.vevosystems@gmail.com");	//Vevo Systems Co., Ltd 
		add("@tttinter.com");	//Thong Thai Textile Co.,Ltd.
		add("@hyundai-motor.co.th"); //Hyundai Motor (Thailand) Co.,Ltd.
		add("@yahoo");
		add("@kingriceoilgroup.com");	//Thai Edible Oil Co.,Ltd.
		add("@weberthai.com");	//Saint-Gobain Weber Co., Ltd.
		add("@saint-gobain.com");	//Saint-Gobain Weber Co., Ltd.
	}};
	
	private static HashMap<Integer, Boolean> idEmpExceptionPdf = new HashMap<Integer, Boolean>(){{
		put(15823, true);	//Toyota Tsusho (Thailand) Co., Ltd.
		put(6149, true);
		put(1830, true);	//Sumitomo Mitsui Banking
		put(17204, true);	//Kkudos
		put(4382, true);	//Sansiri
		put(255, true);	//Advanced Info Service PLC. (Advanced Wireless Network Co., Ltd.)
		put(2626, true);	//Saint-Gobain Weber Co., Ltd.
		put(5263, true);	//V-SERVE GROUP/V-SERVE LOGISTICS CO.,LTD.
		put(2359, true);	//Prominent Fluid Controls (Thailand) Co.,Ltd.
	}};
	
	private static List<String> exceptionDomainPdf = new ArrayList<String>(){{
		add("@tttc.co.th");	//Toyota Tsusho (Thailand) Co., Ltd.
		add("ekanant@topgunthailand.com");
		add("@th.smbc.co.jp");	//Sumitomo Mitsui Banking
		add("@kkudos.com");	//Kkudos
		add("@sansiri.com");	//Sansiri
		add("@ais.co.th");	//Advanced Info Service PLC. (Advanced Wireless Network Co., Ltd.)
		add("@v-servegroup.com");	//V-SERVE GROUP/V-SERVE LOGISTICS CO.,LTD.
		add("@prominent.co.th");	//Prominent Fluid Controls (Thailand) Co.,Ltd.
	}};
	
	public static boolean isUseNewSender(int idEmp)
	{
		return idEmpException != null && idEmpException.containsKey(idEmp) ? true : false;
	}
	
	public static boolean isUseNewSender(Track track)
	{
		boolean result = false;
		if(track == null)
		{
			result = true;
		}
		else
		{
			if( idEmpException != null && idEmpException.containsKey(track.getIdEmp()))
			{
				result = true;
			}
			else
			{
				for (int i = 0; i < exceptionDomain.size(); i++) {
					if(track.getRecipient().indexOf(exceptionDomain.get(i)) > -1)
					{
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public static boolean isUsePdfAttachFile(int idEmp)
	{
		return idEmpExceptionPdf != null && idEmpExceptionPdf.containsKey(idEmp) ? true : false;
	}
	
	public static boolean isUsePdfAttachFile(Track track)
	{
		boolean result = false;
		if(track == null)
		{
			result = true;
		}
		else
		{
			if( idEmpExceptionPdf != null && idEmpExceptionPdf.containsKey(track.getIdEmp()))
			{
				result = true;
			}
			else
			{
				for (int i = 0; i < exceptionDomainPdf.size(); i++) {
					if(track.getRecipient().indexOf(exceptionDomainPdf.get(i)) > -1)
					{
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
}
