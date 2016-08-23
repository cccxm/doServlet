package com.doservlet.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 切面代理
 * 
 * @author 陈小默
 *
 */
public class AspectProxy implements Proxy {
	private static final Logger l = LoggerFactory.getLogger(AspectProxy.class);

	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();

		begin();
		try {
			if (intercept(cls, method, params)) {
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				after(cls, method, params, result);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			l.error("proxy failure", e);
			err(cls, method, params, e);
			throw e;
		}
		return result;
	}

	public boolean intercept(Class<?> cls, Method method, Object[] params)
			throws Throwable {
		return true;
	}

	public void before(Class<?> cls, Method method, Object[] params)
			throws Throwable {

	}

	public void after(Class<?> cls, Method method, Object[] params,
			Object result) {

	}

	public void err(Class<?> cls, Method method, Object[] params, Throwable e) {

	}

	public void end() {

	}

	public void begin() {

	}
}
