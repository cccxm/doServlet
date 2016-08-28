package com.cccxm.english.model;

import com.cccxm.english.contract.UserContract;
import com.doservlet.framework.helper.DatabaseHelper;
import com.doservlet.framework.util.CodecUtil;
import com.doservlet.plugin.security.SecurityHelper;

public class UserModel implements UserContract.IUserModel {

	public int register(String username, String password) {
		return DatabaseHelper.insert(
				"insert user(username,password) value(?,?)", username,
				CodecUtil.md5(password));
	}

	public boolean login(String username, String password) {
		try {
			SecurityHelper.login(username, password);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void setRole(int user_id, int role_id) {
		DatabaseHelper.insert("insert user_role(user_id,role_id) value(?,?)",
				user_id, role_id);
	}

}
