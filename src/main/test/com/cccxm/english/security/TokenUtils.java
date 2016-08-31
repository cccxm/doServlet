package com.cccxm.english.security;

import javax.servlet.http.HttpSession;

import com.doservlet.framework.util.CodecUtil;
import com.doservlet.framework.util.RandomUtils;
import com.doservlet.framework.util.Regex;

public class TokenUtils {
	public static String buildToken(HttpSession session) {
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		String salt = RandomUtils.getRandomString(12);
		String str = username + salt + password;
		String token = CodecUtil.md5(str);
		session.setAttribute("salt", salt);
		return token;
	}

	public static boolean autch(HttpSession session, String token) {
		if (session == null) {
			return false;
		} else {
			String username = (String) session.getAttribute("username");
			String password = (String) session.getAttribute("password");
			String salt = (String) session.getAttribute("salt");
			if (Regex.allIsEmpty(username, password, salt)) {
				return false;
			} else {
				String str = username + salt + password;
				return token.equals(CodecUtil.md5(str));
			}
		}
	}
}
