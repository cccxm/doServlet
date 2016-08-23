package com.doservlet.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.doservlet.framework.util.CodecUtil;

public class Md5CredentialsMatcher implements CredentialsMatcher {

	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		// 获取原文密码
		String submitted = String.valueOf(((UsernamePasswordToken) token)
				.getPassword());
		// 加密密码
		String encrypted = String.valueOf(info.getCredentials());
		return CodecUtil.md5(submitted).equals(encrypted);
	}

}
