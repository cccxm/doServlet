package com.doservlet.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 属性文件工具类
 * 
 * @author 陈小默
 *
 */
public class PropsUtil {
	private Properties properties;

	/**
	 * @param fileName
	 *            属性文件路径名
	 */
	public PropsUtil(String fileName) {
		properties = loadProps(fileName);
	}

	private Properties loadProps(String fileName) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(PropsUtil.class
					.getResource("/").getPath(), fileName)));
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * 获得字符串属性
	 * 
	 * @param key
	 *            关键字
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public String getString(String key, String defaultValue) {
		return getProperties().getProperty(key, defaultValue);
	}

	/**
	 * 获得字符串属性
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return getProperties().getProperty(key);
	}

	/**
	 * 获得整型属性
	 * 
	 * @param key
	 *            关键字
	 * @param defaultInt
	 *            默认值
	 * @return
	 */
	public int getInt(String key, int defaultInt) {
		return CastUtils.castInt(getProperties().get(key), defaultInt);
	}

	/**
	 * 获得boolean类型数据
	 * 
	 * @param key
	 *            关键字
	 * @param defaultBoolean
	 *            默认值
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultBoolean) {
		return CastUtils.castBoolean(getProperties().get(key), defaultBoolean);
	}
}
