package com.cccxm.english.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doservlet.plugin.security.SecurityHelper;

public class AppSecurityFilter implements Filter {
	private static final Logger l = LoggerFactory
			.getLogger(AppSecurityFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest iRequest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(iRequest, response);
//		try {
//			HttpServletRequest request = (HttpServletRequest) iRequest;
//			HttpSession session = request.getSession(false);
//			if (session == null) {
//				request.getRequestDispatcher("/").forward(iRequest, response);
//			} else {
//				String username = (String) session.getAttribute("username");
//				String password = (String) session.getAttribute("password");
//				if (SecurityHelper.login(username, password)) {
//					chain.doFilter(request, response);
//				} else {
//					request.getRequestDispatcher("/").forward(iRequest,
//							response);
//				}
//			}
//		} catch (Exception e) {
//			l.error(e.getMessage());
//		} 
	}

	public void destroy() {
	}

}
