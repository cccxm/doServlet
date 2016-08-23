package com.doservlet.framework.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流操作工具类
 * 
 * @author 陈小默
 *
 */
public class StreamUtil {
	private static final Logger l = LoggerFactory.getLogger(StreamUtil.class);

	/**
	 * 从输入流中获取字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String getString(InputStream is) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			l.error("get string failure", e);
		}
		return builder.toString();
	}

	public static void copyStream(InputStream in, OutputStream out) {
		
	}
}
