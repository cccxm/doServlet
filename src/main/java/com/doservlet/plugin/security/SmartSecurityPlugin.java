package com.doservlet.plugin.security;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

public class SmartSecurityPlugin implements ServletContainerInitializer {

	public void onStartup(Set<Class<?>> handlesType,
			ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("shiroConfigLocations",
				"classPath:shiro.ini");
		servletContext.addListener(EnvironmentLoaderListener.class);
		FilterRegistration.Dynamic smartSecurityFilter = servletContext
				.addFilter("SmartSecurityFilter", SmartSecurityFilter.class);
		smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}
