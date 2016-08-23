package com.doservlet.framework.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码解码工具类
 * 
 * @author 陈小默
 *
 */
public class CodecUtil {
	private final static Logger l = LoggerFactory.getLogger(CodecUtil.class);

	/**
	 * 编码
	 * 
	 * @param source
	 * @return
	 */
	public static String encodeURL(String source) {
		String target;
		try {
			target = URLEncoder.encode(source, "utf-8");
		} catch (Exception e) {
			l.error("encode url failure", e);
			return null;
		}
		return target;
	}

	/**
	 * 解码
	 * 
	 * @param source
	 * @return
	 */
	public static String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source, "utf-8");
		} catch (Exception e) {
			l.error("decode url failure", e);
			return null;
		}
		return target;
	}

	public static String md5(String source) {
		return DigestUtils.md5Hex(source);
	}
}
