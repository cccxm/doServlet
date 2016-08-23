package com.doservlet.plugin.security;

import java.util.Set;

/**
 * 
 * @author 陈小默
 *
 */
public interface SmartSecurity {
	/**
	 * 获取密码
	 * 
	 * @param username
	 *            用户名
	 * @return 密码
	 */
	String getPassword(String username);

	/**
	 * 根据用户名获取角色
	 * 
	 * @param username
	 *            用户名
	 * @return 角色名
	 */
	Set<String> getRoleNameSet(String username);

	/**
	 * 根据角色名获取权限名
	 * 
	 * @param roleName
	 *            角色名
	 * @return 权限名
	 */
	Set<String> getPermissionNameSet(String roleName);
}
