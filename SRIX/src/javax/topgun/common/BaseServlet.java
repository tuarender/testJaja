package javax.topgun.common;

import java.io.IOException;

import static javax.topgun.common.Constants.SESSION_LOCALE;
import static javax.topgun.common.Constants.LOCALE_THAI;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.topgun.common.action.ResetPasswordServEnum;

import org.apache.commons.lang.WordUtils;

import javax.topgun.common.action.HttpMethodEnum;

import com.topgun.util.Util;

import java.lang.reflect.Method;
import java.util.HashSet;

public abstract class BaseServlet extends HttpServlet implements Servlet {

    /**
     * 
     */
    private static final long serialVersionUID = -8410349271952693346L;
    public static String ENUM_CLASS = "Enum";
    public static String GET_ENUMS = "getEnums";

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String locale;
    private HttpSession session;
    private String action;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processServlet(HttpMethodEnum.GET, request, response);
    };

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processServlet(HttpMethodEnum.POST, request, response);
    };

    protected void processServlet(HttpMethodEnum httpMethod, HttpServletRequest request, HttpServletResponse response) {
	prepareServlet(request, response);

	if (!isValidAction()) {
	    processAction(httpMethod);
	} else {
	    System.out.println("Not valid action");
	}
    }

    private void processAction(HttpMethodEnum httpMethod) {
	try {
	    Method method = this.getClass().getMethod(action + WordUtils.capitalizeFully(httpMethod.name()));
	    method.invoke(this);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private boolean isValidAction() {
	try {
	    return getActionEnum().getEnums().contains(action);
	} catch (Exception e) {
	    return false;
	}
    }

    private BaseEnum getActionEnum() throws Exception {
	Class<?> clazz = Class.forName(this.getClass().getName() + ENUM_CLASS);
	return (BaseEnum) clazz.newInstance();
    }

    private void prepareServlet(HttpServletRequest request, HttpServletResponse response) {
	this.request = request;
	this.response = response;
	session = request.getSession();
	action = Util.getStr(request.getParameter("action"));
	setLocale();
    }

    private void setLocale() {
	locale = null != session.getAttribute(SESSION_LOCALE) ? (String) session.getAttribute(SESSION_LOCALE)
		: LOCALE_THAI;
    }

    protected String getLocale() {
	return locale;
    }
}
