package com.doservlet.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.doservlet.framework.annotation.Inject;
import com.doservlet.framework.util.ReflectionUtils;

public final class IocHelper {
	static {
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		if (MapUtils.isNotEmpty(beanMap)) {
			for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				Field[] fields = beanClass.getDeclaredFields();
				if (ArrayUtils.isNotEmpty(fields)) {
					for (Field field : fields) {
						if (field.isAnnotationPresent(Inject.class)) {
							// 包含Inject注解
							Class<?> beanFieldClass = field.getType();
							Object beanFieldInstance = beanMap
									.get(beanFieldClass);
							if (beanFieldInstance != null) {
								ReflectionUtils.set(field, beanInstance,
										beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
