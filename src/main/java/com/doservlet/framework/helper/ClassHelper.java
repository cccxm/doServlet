package com.doservlet.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.doservlet.framework.annotation.Controller;
import com.doservlet.framework.annotation.Service;
import com.doservlet.framework.util.ClassUtils;

/**
 * 类操作助手
 * 
 * @author 陈小默
 *
 */
public final class ClassHelper {
	private static final Set<Class<?>> CLASS_SET;
	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtils.getClassSet(basePackage);
	}

	/**
	 * 获取应用下所有的类
	 * 
	 * @return
	 */
	public static Set<Class<?>> getClasses() {
		return CLASS_SET;
	}

	/**
	 * 获得所有的Service类
	 */
	public static Set<Class<?>> getServiceClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		for (Class<?> cls : getClasses()) {
			if (cls.isAnnotationPresent(Service.class)) {
				set.add(cls);
			}
		}
		return set;
	}

	/**
	 * 获得所有的Controller类
	 */
	public static Set<Class<?>> getControllerClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		for (Class<?> cls : getClasses()) {
			if (cls.isAnnotationPresent(Controller.class)) {
				set.add(cls);
			}
		}
		return set;
	}

	/**
	 * 获得所有的Bean类 包括Service和Controller
	 */
	public static Set<Class<?>> getBeanClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.addAll(getServiceClasses());
		set.addAll(getControllerClasses());
		return set;
	}

	/**
	 * 获取应用包下某父类的所有子类
	 * 
	 * @param superClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
				classes.add(cls);
			}
		}
		return classes;
	}

	/**
	 * 获取应用包名下带有注解的所有类
	 * 
	 * @param annotationClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation(
			Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(annotationClass)) {
				classes.add(cls);
			}
		}
		return classes;
	}
}
