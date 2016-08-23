package com.doservlet.framework.bean;

import java.lang.reflect.Method;

public class Handler {
	/**
	 * 封装Action信息
	 */
	private Class<?> controllerClass;
	/**
	 * Action方法
	 */
	private Method actionMethod;

	public Handler(Class<?> controllerClass, Method actionMethod) {
		super();
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public Method getActionMethod() {
		return actionMethod;
	}

}
