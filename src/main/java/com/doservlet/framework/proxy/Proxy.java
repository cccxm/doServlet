package com.doservlet.framework.proxy;

/**
 * 代理接口
 * 
 * @author 陈小默
 *
 */
public interface Proxy {
	/**
	 * 
	 * 执行链式代理
	 * 
	 * @param proxyChain
	 * @return
	 * @throws Throwable
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
}
