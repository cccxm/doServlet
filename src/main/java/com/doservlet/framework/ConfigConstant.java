package com.doservlet.framework;

/**
 * 提供相关配置常量
 * 
 * @author 陈小默
 *
 */
public interface ConfigConstant {
	String CONFIG_FILE = "smart.properties";

	String JDBC_DRIVER = "smart.framework.jdbc.driver";
	String JDBC_URL = "smart.framework.jdbc.url";
	String JDBC_USERNAME = "smart.framework.jdbc.username";
	String JDBC_PASSWORD = "smart.framework.jdbc.password";

	String APP_BASE_PACKAGE = "smart.framework.app.base_package";
	String APP_JSP_PATH = "smart.framework.app.jsp_path";
	String APP_ASSET_PATH = "smart.framework.app.asset_path";

	String APP_UPLOAD_LIMIT = "smart.framework.app.upload_limit";

	String PLUGIN_SECURITY_SSO = "smart.plugin.security.sso";
	String PLUGIN_SECURITY_REALMS = "smart.plugin.security.realms";
	String PLUGIN_SECURITY_CUSTOM_CLASS = "smart.plugin.security.custom.class";
}
