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

public class AppSecurityFilter implements Filter {
	private static final Logger l = LoggerFactory
			.getLogger(AppSecurityFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest iRequest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) iRequest;
			HttpSession session = request.getSession(false);
			if (session == null) {
				request.getRequestDispatcher("/").forward(iRequest, response);
			} else {
				String token = request.getParameter("token");
				if (token == null) {
					request.getRequestDispatcher("/").forward(iRequest,
							response);
				} else if (TokenUtils.autch(session, token)) {
					chain.doFilter(request, response);
				} else {
					request.getRequestDispatcher("/").forward(iRequest,
							response);
				}
			}
		} catch (Exception e) {
			l.error(e.getMessage());
		}
	}

	public void destroy() {
	}

}
