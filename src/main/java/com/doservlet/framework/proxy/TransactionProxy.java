package com.doservlet.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doservlet.framework.annotation.Transaction;
import com.doservlet.framework.helper.Tran;

public class TransactionProxy implements Proxy {
	private static final Logger l = LoggerFactory
			.getLogger(TransactionProxy.class);

	private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return false;
		}
	};

	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;

		boolean flag = FLAG_HOLDER.get();
		Method method = proxyChain.getTargetMethod();
		if (!flag && method.isAnnotationPresent(Transaction.class)) {
			FLAG_HOLDER.set(true);
			try {
				Tran.startTransaction();
				result = proxyChain.doProxyChain();
				Tran.commit();
				Tran.close();
			} catch (Exception e) {
				Tran.rollback();
				l.error("rollback transaction", e);
				throw e;
			} finally {
				FLAG_HOLDER.remove();
			}
		} else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}

}
