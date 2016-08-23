package com.doservlet.plugin.security.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;

import com.doservlet.framework.helper.DatabaseHelper;
import com.doservlet.plugin.security.SecurityConfig;
import com.doservlet.plugin.security.password.Md5CredentialsMatcher;

public class SmartJdbcRealm extends JdbcRealm {
	public SmartJdbcRealm() {
		super.setDataSource(DatabaseHelper.getDataSource());
		super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
		super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
		super.setPermissionsQuery(SecurityConfig.getJdbcPermissionQuery());
		super.setPermissionsLookupEnabled(true);
		super.setCredentialsMatcher(new Md5CredentialsMatcher());
	}
}
