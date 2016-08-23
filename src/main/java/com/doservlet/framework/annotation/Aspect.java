package com.doservlet.framework.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 切面注解，该注解只能用于类
 * 
 * @author 陈小默
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
	/**
	 * 参数为注解
	 * 
	 * @return
	 */
	Class<? extends Annotation> value();
}
