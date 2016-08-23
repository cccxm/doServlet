package com.doservlet.plugin.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import com.doservlet.plugin.security.realm.SmartCustomRealm;
import com.doservlet.plugin.security.realm.SmartJdbcRealm;

public class SmartSecurityFilter extends ShiroFilter {
	@Override
	public void init() throws Exception {
		super.init();
		WebSecurityManager webSecurityManager = super.getSecurityManager();
		// 设置Realm
		setRealms(webSecurityManager);
		setCache(webSecurityManager);
	}

	private void setRealms(WebSecurityManager webSecurityManager) {
		String securityRealms = SecurityConfig.getRealms();
		if (securityRealms != null) {
			String[] securityRealmArray = securityRealms.split(",");
			if (securityRealmArray.length > 0) {
				Set<Realm> realms = new HashSet<Realm>();
				for (String securityRealm : securityRealmArray) {
					if (securityRealm
							.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
						// 添加基于jdbc的realm
						addJdbcRealm(realms);
					} else if (securityRealm
							.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
						addCustomRealm(realms);
					}
				}
				RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
				realmSecurityManager.setRealms(realms);
			}
		}
	}

	private void addJdbcRealm(Set<Realm> realms) {
		SmartJdbcRealm smartJdbcRealm = new SmartJdbcRealm();
		realms.add(smartJdbcRealm);
	}

	private void addCustomRealm(Set<Realm> realms) {
		SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
		SmartCustomRealm smartCustomRealm = new SmartCustomRealm(smartSecurity);
		realms.add(smartCustomRealm);
	}

	private void setCache(WebSecurityManager webSecurityManager) {
		if (SecurityConfig.isCache()) {
			CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
			CacheManager cacheManager = new MemoryConstrainedCacheManager();
			cachingSecurityManager.setCacheManager(cacheManager);
		}
	}
}
