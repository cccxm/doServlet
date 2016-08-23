package com.doservlet.plugin.security;

import com.doservlet.framework.helper.ConfigHelper;
import com.doservlet.framework.util.ReflectionUtils;

public final class SecurityConfig {

	public static String getRealms() {
		return ConfigHelper.getString(SecurityConstant.REALMS);
	}

	public static SmartSecurity getSmartSecurity() {
		String className = ConfigHelper
				.getString(SecurityConstant.SMART_SECURITY);
		return (SmartSecurity) ReflectionUtils.newInstance(className);
	}

	public static String getJdbcAuthcQuery() {
		return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
	}

	public static String getJdbcRolesQuery() {
		return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
	}

	public static String getJdbcPermissionQuery() {
		return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
	}

	public static boolean isCache() {
		return ConfigHelper.getBoolean(SecurityConstant.CACHE);
	}
}
