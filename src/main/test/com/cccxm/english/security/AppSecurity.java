package com.cccxm.english.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cccxm.english.bean.Permission;
import com.cccxm.english.bean.Role;
import com.cccxm.english.bean.User;
import com.doservlet.framework.helper.DatabaseHelper;
import com.doservlet.framework.util.PropsUtil;
import com.doservlet.plugin.security.SmartSecurity;

public class AppSecurity implements SmartSecurity {
	private PropsUtil properties = new PropsUtil("smart.properties");

	public String getPassword(String username) {
		List<User> list = DatabaseHelper.queryEntityList(User.class,
				properties.getString("smart.plugin.security.jdbc.authc_query"),
				username);
		if (list == null || list.size() != 1) {
			throw new RuntimeException("user not found");
		}
		return list.get(0).getPassword();
	}

	public Set<String> getRoleNameSet(String username) {
		List<Role> list = DatabaseHelper.queryEntityList(Role.class,
				properties.getString("smart.plugin.security.jdbc.roles_query"),
				username);
		if (list == null || list.size() == 0) {
			throw new RuntimeException("user not found");
		}
		HashSet<String> hashSet = new HashSet<String>();
		for (Role r : list) {
			hashSet.add(r.getRole_name());
		}
		return hashSet;
	}

	public Set<String> getPermissionNameSet(String roleName) {
		List<Permission> list = DatabaseHelper
				.queryEntityList(
						Permission.class,
						properties
								.getString("smart.plugin.security.jdbc.permission_query"),
						roleName);
		if (list == null || list.size() == 0) {
			throw new RuntimeException("user not found");
		}
		HashSet<String> hashSet = new HashSet<String>();
		for (Permission p : list) {
			hashSet.add(p.getPermission_name());
		}
		return hashSet;
	}

}
