package com.cccxm.english.present;

import javax.servlet.http.HttpSession;

import com.cccxm.english.bean.UserBean;
import com.cccxm.english.contract.UserContract;
import com.cccxm.english.contract.UserContract.IUserModel;
import com.cccxm.english.contract.UserContract.IUserView;
import com.cccxm.english.security.TokenUtils;
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
		String username = null;
		String password = null;
		try {
			username = param.getString("username");
			password = param.getString("password");
		} catch (Exception e) {
			return view.error("参数错误");
		}
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
					return view.success(createUser(param, username, password));
				} else {
					return view.error("用户名或密码错误");
				}
			}
		}
	}

	public Data login(Param param) {
		String username = null;
		String password = null;
		try {
			username = param.getString("username");
			password = param.getString("password");
		} catch (Exception e) {
			view.error("参数错误");
		}
		if (!Regex.isPhone(username) || !Regex.password(password)) {
			return view.error("用户名或密码格式错误");
		} else {
			boolean flag = model.login(username, password);
			if (flag) {
				return view.success(createUser(param, username, password));
			} else {
				return view.error("用户名或密码错误");
			}
		}
	}

	public String saveUser(Param param, String username, String password) {
		HttpSession session = param.getRequest().getSession(false);
		if (session != null)
			session.invalidate();
		session = param.getRequest().getSession(true);
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		return TokenUtils.buildToken(session);
	}

	private UserBean createUser(Param param, String username, String password) {
		UserBean bean = new UserBean();
		bean.setUsername(username);
		bean.setSid(param.getRequest().getSession(false).getId());
		bean.setToken(saveUser(param, username, password));
		return bean;
	}
}
