package com.topgun.resume;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.topgun.resume.ForgotPasswordServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.topgun.util.DBManager;
import com.topgun.util.MailManager;
import com.topgun.util.Util;

public class OTPManager 
{
	public static List<OTP> getAllAccount(String mobile)
	{
		String eMail;
		List<OTP> result=null;
		int position=0;
		String neweMail=null;
		DBManager db=null;
		String sql=	"SELECT " +
					"	A.ID_JSK, A.USERNAME, B.PRIMARY_PHONE " +
					"FROM " +
					"	INTER_JOBSEEKER A, INTER_RESUME B " +
					"WHERE " +
					"	B.PRIMARY_PHONE=? AND " +
					"	A.ID_JSK = B.ID_JSK " +
					"	B.ID_RESUME=0 AND DELETE_STATUS<>'TRUE'" +
					"ORDER BY " +
					"	A.ID_JSK";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(sql);
			db.setString(1, mobile);
			db.executeQuery();
			while(db.next())
			{
				if(result==null)
				{
					result=new ArrayList<OTP>();
				}
				OTP otp=new OTP();
				eMail= db.getString("USERNAME");
				position= eMail.indexOf("@");
				neweMail= '*'+eMail.substring(1, (position-3))+"***"+eMail.substring((position));
				otp.setIdJsk(db.getInt("ID_JSK"));
				otp.setUsername(neweMail);
				result.add(otp);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return result;
	}
	public int getOTP(int idJsk, String telephone) {
        int result = 0;
        if (this.addOTP(idJsk, telephone) == 1) {
            result = this.readOTP(idJsk, telephone);
        }
        return result;
    }

    public int addOTP(int idJsk, String telephone) {
        int result;
        result = 0;
        String email = this.getEmailByIdJsk(idJsk);
        DBManager db = null;
        String sql = "INSERT INTO INTER_OTP(ID_JSK,MOBILE, CODE, USERNAME) VALUES(?,?,SEQ_INTER_OTP.NEXTVAL,?)";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setInt(1, idJsk);
            db.setString(2, telephone);
            db.setString(3, email);
            result = db.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }

    public int readOTP(int idJsk, String telephone) {
        int result;
        result = 0;
        DBManager db = null;
        String sql = "SELECT MOBILE, CODE, TIMESTAMP, USERNAME FROM INTER_OTP WHERE ID_JSK=? AND MOBILE=? ORDER BY TIMESTAMP DESC";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setInt(1, idJsk);
            db.setString(2, telephone);
            db.executeQuery();
            if (db.next()) {
                result = db.getInt("CODE");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
   
        return result;
    }
    public String getEmailByIdJsk(int idJsk) {
        String result;
        result = null;
        DBManager db = null;
        String sql = "SELECT USERNAME FROM INTER_JOBSEEKER WHERE ID_JSK=?";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setInt(1, idJsk);
            db.executeQuery();
            if (db.next()) {
                result = db.getString("USERNAME");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }

    public int getIdJskByOTP(String telephone, int otp) {
        int result;
        result = 0;
        DBManager db = null;
        String sql = "SELECT ID_JSK FROM INTER_OTP WHERE MOBILE=? AND CODE=? ORDER BY TIMESTAMP DESC";
        try {
            db = new DBManager();
            db.createPreparedStatement(sql);
            db.setString(1, telephone);
            db.setInt(2, otp);
            db.executeQuery();
            if (db.next()) {
                result = db.getInt("ID_JSK");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        finally {
            db.close();
        }
        return result;
    }
}
