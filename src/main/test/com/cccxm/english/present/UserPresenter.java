package com.cccxm.english.present;

import javax.servlet.http.HttpSession;

import com.cccxm.english.contract.UserContract;
import com.cccxm.english.contract.UserContract.IUserModel;
import com.cccxm.english.contract.UserContract.IUserView;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.util.Regex;

public class UserPresenter implements UserContract.IUserPresenter {
	private IUserModel model;
	private IUserView view;

	public UserPresenter(IUserModel model, IUserView view) {
		this.model = model;
		this.view = view;
	}

	public Data register(Param param) {
		String username = param.getString("username");
		String password = param.getString("password");
		if (!Regex.isPhone(username)) {
			return view.error("用户名必须是正确的手机号码");
		} else if (!Regex.password(password)) {
			return view.error("密码必须是8-16位数字与字母的组合");
		} else {
			int i = model.register(username, password);
			if (i <= 0) {
				return view.error("该号码已经注册");
			} else {
				model.setRole(i, 1);
				boolean success = model.login(username, password);
				if (success) {
					return view.sessionId(saveUser(param, username, password));
				} else {
					return view.error("用户名或密码错误");
				}
			}
		}
	}

	public Data login(Param param) {
		String username = param.getString("username");
		String password = param.getString("password");
		if (!Regex.isPhone(username) || !Regex.password(password)) {
			return view.error("用户名或密码格式错误");
		} else {
			boolean flag = model.login(username, password);
			if (flag) {
				return view.sessionId(saveUser(param, username, password));
			} else {
				return view.error("用户名或密码错误");
			}
		}
	}

	public String saveUser(Param param, String username, String password) {
		HttpSession session = param.getRequest().getSession(true);
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		return session.getId();
	}

}
