package com.doservlet.framework.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 宙斯线程
 * 
 * @author 陈小默
 *
 */
public class Zeus {
	private static Map<String, Runnable> pool = new HashMap<String, Runnable>();
	private static String threadName = Zeus.class.getName();
	private static HashSet<String> unregist = new HashSet<String>();
	private static Runnable runnable = new Runnable() {

		public void run() {
			while (true) {
				Iterator<Entry<String, Runnable>> it = pool.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, Runnable> next = it.next();
					ThreadControl.startThread(next.getKey(), next.getValue());
					if (unregist.contains(next.getKey())) {
						unregist.remove(next.getKey());
						ThreadControl.killThread(next.getKey());
						it.remove();
					}
				}
			}
		}
	};
	static {
		zeus();
	}

	/**
	 * 启动宙斯模式
	 */
	public static void zeus() {
		ThreadControl.startThread(threadName, runnable);
	}

	/**
	 * 注册为宙斯线程
	 * 
	 * @param name
	 * @param runnable
	 */
	public static void regist(String name, final Runnable runnable) {
		pool.put(name, new Runnable() {
			
			public void run() {
				runnable.run();
				zeus();
				
			}
		});
		zeus();
	}

	public static void unregist(String name) {
		unregist.add(name);
	}
}
