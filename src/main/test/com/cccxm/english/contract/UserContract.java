package com.cccxm.english.contract;

import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.Param;

public interface UserContract {
	public static interface IUserView {
		Data register(Param param);

		Data login(Param param);

		Data error(String message);

		Data sessionId(String token);
	}

	public static interface IUserPresenter {
		Data register(Param param);

		Data login(Param param);

		String saveUser(Param param, String username, String password);
	}

	public static interface IUserModel {
		int register(String username, String password);

		void setRole(int user_id, int role_id);

		boolean login(String username, String password);
	}

}
