package com.doservlet.framework.helper;

import java.util.HashMap;
import java.util.Map;

import com.doservlet.framework.util.ReflectionUtils;

public class BeanHelper {
	private static final Map<Class<?>, Object> BEAN_MAP;
	static {
		BEAN_MAP = new HashMap<Class<?>, Object>();
		for (Class<?> beanClass : ClassHelper.getBeanClasses()) {
			BEAN_MAP.put(beanClass, ReflectionUtils.newInstance(beanClass));
		}
	}

	/**
	 * 获取bean影射
	 * 
	 * @return
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 获取bean实例
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) {
		if (!BEAN_MAP.containsKey(cls))
			throw new RuntimeException("can not get bean");
		return (T) BEAN_MAP.get(cls);
	}

	/**
	 * 设置bean实例
	 * 
	 * @param cls
	 * @param obj
	 */
	public static void setBean(Class<?> cls, Object obj) {
		BEAN_MAP.put(cls, obj);
	}
}
