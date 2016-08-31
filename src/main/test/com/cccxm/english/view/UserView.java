package com.cccxm.english.view;

import javax.servlet.http.HttpSession;

import com.cccxm.english.bean.UserBean;
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
	@Action("post:/register")
	public Data register(Param param) {
		IUserPresenter presenter = new UserPresenter(new UserModel(), this);
		return presenter.register(param);
	}

	@Action("post:/login")
	public Data login(Param param) {
		IUserPresenter presenter = new UserPresenter(new UserModel(), this);
		return presenter.login(param);
	}

	@Action("get:/login")
	public Data loginGet(Param param) {
		IUserPresenter presenter = new UserPresenter(new UserModel(), this);
		return presenter.login(param);
	}

	@Action("get:/")
	public Data noLogin(Param param) {
		HttpSession session = param.getRequest().getSession(false);
		if(session==null){
			return error("logout");
		}else{
			return error("login");
		}
	}

	public Data error(String message) {
		HttpResponse<String> res = new HttpResponse<String>();
		res.setSuccess(false);
		res.setMessage(message);
		return new Data(res);
	}

	public Data success(UserBean bean) {
		HttpResponse<UserBean> res = new HttpResponse<UserBean>();
		res.setSuccess(true);
		res.setData(bean);
		return new Data(res);
	}
}
