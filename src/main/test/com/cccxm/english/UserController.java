package com.cccxm.english;

import com.doservlet.framework.annotation.Action;
import com.doservlet.framework.annotation.Controller;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.HttpResponse;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.helper.DatabaseHelper;
import com.doservlet.framework.util.Regex;
import com.doservlet.plugin.security.SecurityHelper;

@Controller
public class UserController {
	@Action("get:/register")
	public Data register(Param param) {
		String username = param.getString("username");
		String password = param.getString("password");
		if (!Regex.isPhone(username)) {
			HttpResponse<String> res = new HttpResponse<String>();
			res.setSuccess(false);
			res.setMessage("用户名必须是正确的手机号码");
			return new Data(res);
		} else if (!Regex.password(password)) {
			HttpResponse<String> res = new HttpResponse<String>();
			res.setSuccess(false);
			res.setMessage("密码必须是8-16位数字与字母的组合");
			return new Data(res);
		} else {
			int i = DatabaseHelper.insert(
					"insert user(username,password) value(?,?)", username,
					password);
			if (i == -1) {
				HttpResponse<String> res = new HttpResponse<String>();
				res.setSuccess(false);
				res.setMessage("该号码已经注册");
				return new Data(res);
			} else {
				SecurityHelper.login(username, password);
				return null;
			}
		}
	}
}
