package com.doservlet.plugin.security.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.doservlet.plugin.security.SecurityConstant;
import com.doservlet.plugin.security.SmartSecurity;
import com.doservlet.plugin.security.password.Md5CredentialsMatcher;

public class SmartCustomRealm extends AuthorizingRealm {
	private final SmartSecurity smartSecurity;

	public SmartCustomRealm(SmartSecurity smartSecurity) {
		this.smartSecurity = smartSecurity;
		super.setName(SecurityConstant.REALMS_CUSTOM);
		super.setCredentialsMatcher(new Md5CredentialsMatcher());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null) {
			throw new RuntimeException("parameter principals is null");
		}
		// 获取用户名
		String username = (String) super.getAvailablePrincipal(principals);
		// 获取角色用户名集合
		Set<String> roleNameSet = smartSecurity.getRoleNameSet(username);
		// 获取对应的权限
		Set<String> permissionNameSet = new HashSet<String>();
		if (roleNameSet != null && roleNameSet.size() != 0) {
			for (String roleName : roleNameSet) {
				permissionNameSet.addAll(smartSecurity
						.getPermissionNameSet(roleName));
			}
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleNameSet);
		authorizationInfo.setStringPermissions(permissionNameSet);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if (token == null) {
			throw new RuntimeException("parameter token is null");
		}
		// 获取用户名
		String username = ((UsernamePasswordToken) token).getUsername();
		// 获取密码
		String password = smartSecurity.getPassword(username);
		// 存放用户名和密码
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
		authenticationInfo.setPrincipals(new SimplePrincipalCollection(
				username, super.getName()));
		authenticationInfo.setCredentials(password);
		return authenticationInfo;
	}
}
