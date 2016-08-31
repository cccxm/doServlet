package com.doservlet.plugin.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecurityHelper {
	private static final Logger l = LoggerFactory
			.getLogger(SecurityHelper.class);

	public static boolean login(String username, String password) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null) {
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					password, true);
			try {
				currentUser.login(token);
				return true;
			} catch (Exception e) {
				l.error("login failure", e);
				return false;
			}
		}
		return true;
	}

	public static void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null) {
			currentUser.logout();
		}
	}
}
