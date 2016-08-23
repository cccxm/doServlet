package com.doservlet.framework.util;

/**
 * 类型转换工具类
 * 
 * @author 陈小默
 *
 */
public class CastUtils {
	/**
	 * 将对象转换为字符串后输出，对象不能为空
	 * 
	 * @param object
	 * @return
	 */
	public static String castString(Object object) {
		if (object == null)
			throw new RuntimeException("Object is null");
		return object.toString();
	}

	/**
	 * 类型转换为字符串
	 * 
	 * @param object
	 * @param defaultString
	 * @return
	 */
	public static String castString(Object object, String defaultString) {
		return object == null ? defaultString : castString(object);
	}

	public static double castDouble(Object object) {
		return Double.valueOf(castString(object));
	}

	public static double castDouble(Object object, double defaultDouble) {
		try {
			return castDouble(object);
		} catch (Exception e) {
			return defaultDouble;
		}
	}

	public static long castLong(Object object) {
		return Long.valueOf(castString(object));
	}

	public static long castLong(Object object, long defaultLong) {
		try {
			return castLong(object);
		} catch (Exception e) {
			return defaultLong;
		}
	}

	public static int castInt(Object object) {
		return Integer.valueOf(castString(object));
	}

	public static int castInt(Object object, int defaultInt) {
		try {
			return castInt(object);
		} catch (Exception e) {
			return defaultInt;
		}
	}

	public static boolean castBoolean(Object object) {
		return Boolean.valueOf(castString(object));
	}

	public static boolean castBoolean(Object object, boolean defaultBoolean) {
		try {
			return castBoolean(object);
		} catch (Exception e) {
			return defaultBoolean;
		}
	}
}
