package com.doservlet.test;
/*
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.doservlet.framework.helper.DatabaseHelper;
import com.doservlet.plugin.security.SmartSecurity;

public class AppSecurity implements SmartSecurity {

	public String getPassword(String username) {
		List<User> list = DatabaseHelper.queryEntityList(User.class,
				"select password from user where username=?", username);
		if (list == null || list.size() != 1) {
			throw new RuntimeException("user not found");
		}
		return list.get(0).getPassword();
	}

	public Set<String> getRoleNameSet(String username) {
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.add("admin");
		return hashSet;
	}

	public Set<String> getPermissionNameSet(String roleName) {
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.add("delete");
		hashSet.add("update");
		hashSet.add("insert");
		return hashSet;
	}

}
*/