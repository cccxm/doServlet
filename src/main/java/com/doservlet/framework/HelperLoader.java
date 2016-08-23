package com.doservlet.framework;

import com.doservlet.framework.helper.AopHelper;
import com.doservlet.framework.helper.BeanHelper;
import com.doservlet.framework.helper.ClassHelper;
import com.doservlet.framework.helper.ControllerHelper;
import com.doservlet.framework.helper.IocHelper;
import com.doservlet.framework.util.ClassUtils;

public class HelperLoader {
	public static void init() {
		for (Class<?> cls : new Class<?>[] { ClassHelper.class,
				BeanHelper.class, AopHelper.class, IocHelper.class,
				ControllerHelper.class }) {
			ClassUtils.loadClass(cls.getName(), true);
		}
	}
}
