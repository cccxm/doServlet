package com.doservlet.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public class ProxyChain {
	private final Class<?> targetClass;
	private final Object targetObject;
	private final Method targetMethod;
	private final MethodProxy methodProxy;
	private final Object[] methodParams;

	private List<Proxy> proxyList = new ArrayList<Proxy>();
	private int proxyIndex = 0;

	public ProxyChain(Class<?> targetClass, Object targetObject,
			Method targetMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		super();
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

	public Object doProxyChain() throws Throwable {
		Object result;
		if (proxyIndex < proxyList.size()) {
			result = proxyList.get(proxyIndex++).doProxy(this);
		} else {
			result = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return result;
	}
}
