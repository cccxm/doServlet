package com.doservlet.test;
/*
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doservlet.framework.annotation.Action;
import com.doservlet.framework.annotation.Controller;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.bean.View;
import com.doservlet.plugin.security.SecurityHelper;

@Controller
public class SystemController {
	private static final Logger l = LoggerFactory
			.getLogger(SystemController.class);

	@Action("get:/")
	public View index() {
		l.info("index");
		return new View("index.jsp");
	}

	@Action("get:/login")
	public View login() {

		return new View("login.jsp");
	}

	@Action("post:/login")
	public Object login(Param param) {
		String username = param.getString("username");
		String password = param.getString("password");
		try {
			SecurityHelper.login(username, password);
		} catch (Exception e) {
			l.error("login failure", e);
			return new View("/login");
		}
		return new View("index.jsp");
	}

	@Action("get:/logout")
	public View logout() {
		SecurityHelper.logout();
		return new View("/");
	}

	@Action("get:/playfo")
	public View playfo(Param param) {
		int number = param.getInt("number");
		File directory = new File(
				"F:\\视频\\佛经讲义\\佛法修学概要 净土教观学苑 净界法师 2013年4月\\合并");
		File[] list = directory.listFiles();
		if (number < list.length) {
			return new View("player.jsp").addModel("filePath",
					list[number].getAbsolutePath());
		} else {
			return new View("/");
		}
	}

	@Action("get:/play")
	public View play(Param param) {
		int number = param.getInt("number");
		File directory = new File("F:\\文件\\其它\\插件");
		File[] list = directory.listFiles();
		if (number < list.length) {
			return new View("player.jsp").addModel("filePath",
					list[number].getAbsolutePath());
		} else {
			return new View("/");
		}
	}
}
*/