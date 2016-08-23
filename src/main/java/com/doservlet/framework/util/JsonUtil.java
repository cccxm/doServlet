package com.doservlet.framework.util;

import com.google.gson.Gson;

/**
 * json工具类
 * 
 * @author 陈小默
 *
 */
public class JsonUtil {

	private static final Gson GSON = new Gson();

	/**
	 * 将对象转换为Json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		return GSON.toJson(object);
	}

	/**
	 * 将字符串解析为对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> type) {
		return GSON.fromJson(json, type);
	}
}
