package com.doservlet.framework.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletHelper {
	private static final Logger l = LoggerFactory
			.getLogger(ServletHelper.class);
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<ServletHelper>();
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ServletHelper(HttpServletRequest request,
			HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	public static void init(HttpServletRequest request,
			HttpServletResponse response) {
		SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
	}

	public static void destory() {
		SERVLET_HELPER_HOLDER.remove();
	}

	public static HttpServletRequest getRequest() {
		return SERVLET_HELPER_HOLDER.get().request;
	}

	public static HttpServletResponse getResponse() {
		return SERVLET_HELPER_HOLDER.get().response;
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletContext getsServletContext() {
		return getRequest().getServletContext();
	}

	public static void setRequestAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	public static Object getRequestAttribute(String key) {
		return getRequest().getAttribute(key);
	}

	public static void removeRequestAttribute(String key) {
		getRequest().removeAttribute(key);
	}

	/**
	 * 重定向
	 * 
	 * @param location
	 */
	public static void sendRedirect(String location) {
		try {
			getResponse()
					.sendRedirect(getRequest().getContextPath() + location);
		} catch (Exception e) {
			l.error("redirect failure", e);
		}
	}

	public static void setSessionAttribute(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(String key) {
		return getSession().getAttribute(key);
	}

	public static void removeSessionAttribute(String key) {
		getSession().removeAttribute(key);
	}

	public static void invalidateSession() {
		getSession().invalidate();
	}
}
