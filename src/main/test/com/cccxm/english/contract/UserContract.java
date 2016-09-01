package com.cccxm.english.contract;

import java.util.List;

import com.cccxm.english.bean.User;
import com.cccxm.english.bean.UserBean;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.Param;

public interface UserContract {
	public static interface IUserView {
		Data register(Param param);

		Data login(Param param);

		Data error(String message);

		Data success(UserBean bean);
	}

	public static interface IUserPresenter {
		Data register(Param param);

		Data login(Param param);

		String saveUser(Param param, String username, String password);
	}

	public static interface IUserModel {
		int register(String username, String password);

		void setRole(int user_id, int role_id);

		List<User> login(String username, String password);
	}

}
