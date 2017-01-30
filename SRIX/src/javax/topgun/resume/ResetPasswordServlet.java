package javax.topgun.resume;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.topgun.common.BaseServlet;
import javax.topgun.common.ErrorBean;
import javax.topgun.constants.Constants;
import javax.topgun.constants.enumulation.PhoneTypeEnum;

import com.topgun.resume.Jobseeker;
import com.topgun.resume.JobseekerManager;
import com.topgun.resume.Resume;
import com.topgun.resume.ResumeManager;
import com.topgun.util.Util;

public class ResetPasswordServlet extends BaseServlet {

    private static final long serialVersionUID = 4847704180635384082L;
    private static final String RETRIEVE_USER_PAGE = "/SRIX?view=forgotPassword";
    private JobseekerManager jskManager;
    private ResumeManager resumeManager;

    public ResetPasswordServlet() {
	jskManager = new JobseekerManager();
	resumeManager = new ResumeManager();
    }
    
    /**
     * retrieve user for reset password
     */
    public void retrieveUserPost() throws ServletException, IOException {
	String searchIdentity = Util.getStr(getRequest().getParameter("searchIdentity"));

	if (isValidRetrieveUser(searchIdentity)) {
	    retrieveUser(searchIdentity);
	} else {
	    addError(new ErrorBean(getPropertiesMessage("FORGOT_PASSWORD_INVALID_INPUT")));
	    forwardWithError(RETRIEVE_USER_PAGE);
	}
    }

    private void retrieveUser(String searchIdentity) throws ServletException, IOException {
	if (Util.isEmail(searchIdentity)) {
	    retrieveUserByEmail(searchIdentity);
	} else if (Util.isPhoneNumber(searchIdentity)) {
	    retrieveUserByPhone(searchIdentity);
	} else {
	    retrieveUserByIdentity(searchIdentity);
	}
    }

    private void retrieveUserByEmail(String email) throws ServletException, IOException {
	Jobseeker jobseeker = jskManager.get(email);
	if (null!=jobseeker) {
	    Resume resume = resumeManager.get(jobseeker.getIdJsk(), 0);
	    resume.getPrimaryPhoneType();
	} else {
	    addError(new ErrorBean(getPropertiesMessage("FORGOT_PASSWORD_INVALID_INPUT")));
	    forwardWithError(RETRIEVE_USER_PAGE);
	}
    }

    private void retrieveUserByPhone(String phone) {

    }

    private void retrieveUserByIdentity(String phone) {

    }

    private boolean isValidRetrieveUser(String searchIdentity) {
	return !searchIdentity.isEmpty() && searchIdentity.length() > Constants.RESET_PASSWORD_MIN_SEARCH_LENGTH;
    }
    
    private String getMobilePhone(int idJsk){
	String mobilePhone = null;
	Resume resume = resumeManager.get(idJsk, 0);
	//TODO
	resume.getPrimaryPhoneType();
	return mobilePhone;
    }
}
