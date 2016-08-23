package com.doservlet.test;
/*
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.FileParam;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.bean.View;
import com.doservlet.framework.helper.Tran;
import com.doservlet.framework.helper.UploadHelper;

//@Controller
public class TestController {
	private static final Logger l = LoggerFactory
			.getLogger(TestController.class);

	// @Action("get:/show")
	public View show(Param param) {
		return new View("show.jsp").addModel("id", param.getString("id"));
	}

	// @Action("get:/get")
	public Data get(Param param) {
		return new Data(param);
	}

	// @Action("get:/db")
	// @Transaction
	public Data db(Param param) {
		List<TestBean> entityList = Tran.queryEntityList(TestBean.class,
				"select * from test where id=?", param.get("id"));
		return new Data(entityList);
	}

	// @Action("get:/logger")
	public View logger(Param param) {
		try {
			throw new Exception("测试");
		} catch (Throwable e) {
			l.error("错误测试", e);
		}
		return new View("show.jsp").addModel("id", param.getString("id"));
	}

	// @Action("get:/open")
	public View open(Param param) {
		return new View("index.jsp").setModel(param.getFieldMap());
	}

	// @Action("get:/login")
	public View login(Param param) {
		return new View("login.jsp").setModel(param.getFieldMap());
	}

	// @Action("post:/customer_create")
	public View customer_create(Param param) {
		FileParam file = param.getFile("photo");
		UploadHelper.uploadFile("D://", file);
		return new View("show.jsp").addModel("id", 111);// TODO
	}

}
*/