package com.doservlet.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.doservlet.framework.annotation.Action;
import com.doservlet.framework.bean.Handler;
import com.doservlet.framework.bean.Request;

public class ControllerHelper {
	private static final Map<Request, Handler> ACTIO_MAP = new HashMap<Request, Handler>();
	static {
		// 获得所有的Control类
		Set<Class<?>> controllerClasses = ClassHelper.getControllerClasses();
		if (CollectionUtils.isNotEmpty(controllerClasses)) {
			for (Class<?> controllerClass : controllerClasses) {
				// 获取Controller类中定义的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(methods)) {
					for (Method method : methods) {
						if (method.isAnnotationPresent(Action.class)) {
							// 获取url映射规则
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							if (mapping.matches("\\w+:/\\w*")) {
								String[] split = mapping.split(":");
								if (ArrayUtils.isNotEmpty(split)
										&& split.length == 2) {
									// 获得请求方法与请求路径
									String requestMethod = split[0];
									String requestPath = split[1];
									Request request = new Request(
											requestMethod, requestPath);
									Handler handler = new Handler(
											controllerClass, method);
									ACTIO_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取Handler
	 * 
	 * @param requestMethod
	 * @param requestPath
	 * @return
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		return ACTIO_MAP.get(new Request(requestMethod, requestPath));
	}
}
