package com.doservlet.framework.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 通用正则表达式工具类
 * 
 * @author 陈小默
 * @version 1.0
 * 
 */
public class Regex {

	/**
	 * 当前字符串是否为手机号
	 * 
	 * @param str
	 * @return
	 * @since 1.0
	 */
	public static boolean isPhone(String str) {
		if (str == null)
			return false;
		return Pattern.compile("1[0-9]{10}").matcher(str).matches();
	}

	/**
	 * 验证密码格式
	 * 
	 * @param password
	 * @return
	 */
	public static boolean password(String password) {
		return Pattern.compile("[\\w]{6,18}").matcher(password).matches();
	}

	/**
	 * 替换字符串中的符号
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String replaceAll(String str, String regex, String replacement) {
		if (isEmpty(str))
			return null;
		return str.trim().replaceAll(regex, replacement);
	}

	/**
	 * 替换查询参数字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String buildQueryString(String str) {
		return "%" + replaceAll(str, "[ ]+", "%") + "%";
	}

	/**
	 * 是否包含数字
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isContainNumber(String string) {
		return Pattern.compile("[\\w]*[0-9]+[\\w]*").matcher(string).matches();
	}

	/**
	 * 是否全为数字
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isAllNumber(String string) {
		return Pattern.compile("[0-9]{1,}").matcher(string).matches();
	}

	/**
	 * 是否不含非法字符
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isIllegalChar(String string) {
		return Pattern.compile("[0-9a-zA-Z\\-_\u4e00-\u9fa5]{1,}")
				.matcher(string).matches();
	}

	/**
	 * 判断字符串是否为空对象
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return str == null;
	}

	/**
	 * 判断字符串是否为空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return isNull(str) ? true : str.length() == 0;
	}

	/**
	 * 判断字符串是否为空白字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return isEmpty(str) ? true : str.trim().length() == 0;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 判断长度
	 * 
	 * @param string
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean length(String string, int start, int end) {
		return string.length() >= start && string.length() <= end;
	}

	/**
	 * 截取区间
	 * 
	 * @param string
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> subString(String string, String start,
			String end, int length) {
		List<Integer> index_start = getIndex(string, start);
		List<Integer> index_end = getIndex(string, end);
		if (index_start.size() == 0 || index_end.size() == 0)
			return null;
		List<String> strings = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		int i = 0, j = 0;
		for (; i < index_start.size() && j < index_end.size(); i++) {
			for (j = 0; j < index_end.size(); j++) {
				if (index_end.get(j) <= index_start.get(i))
					continue;
				if (index_end.get(j) - length > index_start.get(i)) {
					if (i < index_start.size() - 1
							&& index_end.get(j) > index_start.get(i + 1))
						break;
					set.add(string.substring(index_start.get(i) + 1,
							index_end.get(j)));
					break;
				}
				break;
			}
		}
		strings.addAll(set);
		return strings;
	}

	/**
	 * 截取regex之间的字符串
	 * 
	 * @param string
	 * @return
	 */
	public static List<String> subString(String string, String regex) {
		List<Integer> index = getIndex(string, regex);
		if (index.size() < 2)
			return null;
		List<String> result = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < index.size(); i++) {
			if (i == index.size() - 1)
				break;
			set.add(string.substring(index.get(i++) + regex.length(),
					index.get(i)));
		}
		result.addAll(set);
		return result;
	}

	/**
	 * 获得字符串中字符串出现的所有位置
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static List<Integer> getIndex(String string, String regex) {
		List<Integer> indexList = new ArrayList<Integer>();
		int length = regex.length() - 1;
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (i == charArray.length - 1)
				break;
			int indexOf = new String(charArray, i, charArray.length - i)
					.indexOf(regex);
			if (indexOf == -1)
				break;
			indexList.add(indexOf + i);
			i += (indexOf + length);
		}
		return indexList;
	}
}
