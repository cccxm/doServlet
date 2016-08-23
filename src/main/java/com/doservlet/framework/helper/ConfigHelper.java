package com.doservlet.framework.helper;

import static com.doservlet.framework.ConfigConstant.*;
import com.doservlet.framework.util.PropsUtil;

/**
 * 属性文件帮助类
 * 
 * @author 陈小默
 *
 */
public class ConfigHelper {
	private static final PropsUtil CONFIG_PROPERTIES = new PropsUtil(
			CONFIG_FILE);

	/**
	 * 获取 jdbc 驱动
	 * 
	 * @return
	 */
	public static String getJdbcDriver() {
		return CONFIG_PROPERTIES.getString(JDBC_DRIVER);
	}

	/**
	 * 获得jdbc url
	 * 
	 * @return
	 */
	public static String getJdbcUrl() {
		return CONFIG_PROPERTIES.getString(JDBC_URL);
	}

	/**
	 * 获得 jdbc 用户名
	 * 
	 * @return
	 */
	public static String getJdbcUsername() {
		return CONFIG_PROPERTIES.getString(JDBC_USERNAME);
	}

	/**
	 * 获得 jdbc 密码
	 * 
	 * @return
	 */
	public static String getJdbcPassword() {
		return CONFIG_PROPERTIES.getString(JDBC_PASSWORD);
	}

	/**
	 * 获得基础应用包名
	 * 
	 * @return
	 */
	public static String getAppBasePackage() {
		return CONFIG_PROPERTIES.getString(APP_BASE_PACKAGE);
	}

	/**
	 * 获得基础应用jsp路径
	 * 
	 * @return
	 */
	public static String getAppJspPath() {
		return CONFIG_PROPERTIES.getString(APP_JSP_PATH);
	}

	/**
	 * 获得静态资源路径
	 * 
	 * @return
	 */
	public static String getAppAssetPath() {
		return CONFIG_PROPERTIES.getString(APP_ASSET_PATH);
	}

	/**
	 * 上传文件大小限制
	 * 
	 * @return
	 */
	public static int getAppUploadLimit() {
		return CONFIG_PROPERTIES.getInt(APP_UPLOAD_LIMIT, 10);
	}

	public static String getString(String key) {
		return CONFIG_PROPERTIES.getString(key);
	}

	public static boolean getBoolean(String key) {
		return CONFIG_PROPERTIES.getBoolean(key, false);
	}
}
