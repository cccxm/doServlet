package com.doservlet.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.doservlet.framework.annotation.Aspect;
import com.doservlet.framework.annotation.Service;
import com.doservlet.framework.proxy.AspectProxy;
import com.doservlet.framework.proxy.Proxy;
import com.doservlet.framework.proxy.ProxyManager;
import com.doservlet.framework.proxy.TransactionProxy;

/**
 * 方法拦截助手
 * 
 * @author 陈小默
 *
 */
public class AopHelper {
	static {
		try {
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap
					.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	public static Set<Class<?>> createTargetClassSet(Aspect aspect)
			throws Exception {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		Class<? extends Annotation> annotation = aspect.value();
		if (annotation != null && !annotation.equals(Aspect.class)) {
			classes.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return classes;
	}

	public static Map<Class<?>, Set<Class<?>>> createProxyMap()
			throws Exception {
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
		addAspectProxy(proxyMap);
		addTransactionProxy(proxyMap);
		return proxyMap;
	}

	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap)
			throws Exception {
		Set<Class<?>> proxyClassSet = ClassHelper
				.getClassSetBySuper(AspectProxy.class);
		for (Class<?> proxyClass : proxyClassSet) {
			if (proxyClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}

	private static void addTransactionProxy(
			Map<Class<?>, Set<Class<?>>> proxyMap) {
		Set<Class<?>> serviceClassSet = ClassHelper
				.getClassSetByAnnotation(Service.class);
		proxyMap.put(TransactionProxy.class, serviceClassSet);
	}

	private static Map<Class<?>, List<Proxy>> createTargetMap(
			Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap
				.entrySet()) {
			Class<?> proxyClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for (Class<?> targetClass : targetClassSet) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(proxy);
				} else {
					List<Proxy> proxyList = new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}

}
