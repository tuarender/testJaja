package javax.topgun.common;

import static javax.topgun.constants.Constants.LOCALE_THAI;
import static javax.topgun.constants.Constants.SESSION_LOCALE;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.topgun.constants.BaseEnum;
import javax.topgun.constants.action.HttpMethodEnum;

import org.apache.commons.lang.WordUtils;

import com.topgun.util.PropertiesManager;
import com.topgun.util.Util;

public abstract class BaseServlet extends HttpServlet implements Servlet {

    private static final long serialVersionUID = -8410349271952693346L;
    public static String ENUM_CLASS = "Enum";
    public static String ACTION = "action";
    public static String GET_ENUMS = "getEnums";
    public static String PROPERTIES_PREFIX = "language";

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String locale;
    private HttpSession session;
    private String action;
    private List<ErrorBean> errors = new ArrayList<ErrorBean>();

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
	    clearError();
	    processAction(httpMethod);
	} else {
	    System.out.println("Not valid action");
	}
    }

    protected HttpServletRequest getRequest() {
	return request;
    }

    protected HttpServletResponse getResponse() {
	return response;
    }

    protected void forward(String to) throws ServletException, IOException {
	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(to);
	dispatcher.forward((ServletRequest) request, (ServletResponse) response);
    }

    protected void forwardWithError(String to) throws ServletException, IOException {
	getRequest().setAttribute("errors", errors);
	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(to);
	dispatcher.forward((ServletRequest) request, (ServletResponse) response);
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
	action = Util.getStr(request.getParameter(ACTION));
	setLocale();
    }

    private void setLocale() {
	locale = null != session.getAttribute(SESSION_LOCALE) ? (String) session.getAttribute(SESSION_LOCALE)
		: LOCALE_THAI;
    }

    protected String getPropertiesMessage(String key) {
	PropertiesManager propertiesManager = new PropertiesManager();
	return propertiesManager.getMessage(locale, key);
    }

    protected String getLocale() {
	return locale;
    }

    protected void clearError() {
	errors.clear();
    }

    protected List<ErrorBean> getErrors() {
	return errors;
    }

    protected void addError(ErrorBean error) {
	errors.add(error);
    }
}
