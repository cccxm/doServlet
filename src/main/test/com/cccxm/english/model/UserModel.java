package com.cccxm.english.model;

import java.util.List;

import com.cccxm.english.bean.User;
import com.cccxm.english.contract.UserContract;
import com.doservlet.framework.helper.DatabaseHelper;
import com.doservlet.framework.util.CodecUtil;

public class UserModel implements UserContract.IUserModel {

	public int register(String username, String password) {
		return DatabaseHelper.insert(
				"insert user(username,password) value(?,?)", username,
				CodecUtil.md5(password));
	}

	public List<User> login(String username, String password) {
		return DatabaseHelper.queryEntityList(User.class,
				"select * from user where username=? and password=?", username,
				CodecUtil.md5(password));
		// return SecurityHelper.login(username, password);
	}

	public void setRole(int user_id, int role_id) {
		DatabaseHelper.insert("insert user_role(user_id,role_id) value(?,?)",
				user_id, role_id);
	}

}
