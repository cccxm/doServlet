package com.cccxm.english.view;

import com.cccxm.english.contract.UserContract;
import com.cccxm.english.contract.UserContract.IUserPresenter;
import com.cccxm.english.model.UserModel;
import com.cccxm.english.present.UserPresenter;
import com.doservlet.framework.annotation.Action;
import com.doservlet.framework.annotation.Controller;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.HttpResponse;
import com.doservlet.framework.bean.Param;

@Controller
public class UserView implements UserContract.IUserView {
	@Action("get:/register")
	public Data register(Param param) {
		IUserPresenter presenter = new UserPresenter(new UserModel(), this);
		return presenter.register(param);
	}

	@Action("get:/login")
	public Data login(Param param) {
		IUserPresenter presenter = new UserPresenter(new UserModel(), this);
		return presenter.login(param);
	}

	public Data error(String message) {
		HttpResponse<String> res = new HttpResponse<String>();
		res.setSuccess(false);
		res.setMessage(message);
		return new Data(res);
	}

	public Data sessionId(String sessionId) {
		HttpResponse<String> res = new HttpResponse<String>();
		res.setSuccess(true);
		res.setMessage(sessionId);
		return new Data(res);
	}
}
