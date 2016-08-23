package com.doservlet.framework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 多线程控制管理器
 * 
 * @author 陈小默
 * @create 2015.10.12
 */
public class ThreadControl {
	private static Map<String, MThread> threadMap = new HashMap<String, MThread>();
	private static ThreadCallback callback;

	/**
	 * 获得一个线程,线程不存在返回空
	 * 
	 * @param threadName
	 *            线程名
	 * @return
	 */
	public static Thread getThread(String threadName) {
		return threadMap.get(threadName);
	}

	/**
	 * 获得一个线程,如果当前线程正在运行则返回当前线程,否则返回新线程对象
	 * 
	 * @param threadName
	 *            线程名
	 * @param runnable
	 * @return
	 */
	public static Thread getThread(String threadName, Runnable runnable) {
		MThread mThread = threadMap.get(threadName);
		if (mThread == null) {
			mThread = new MThread(threadName, runnable);
			threadMap.put(threadName, mThread);
		}
		return mThread;
	}

	/**
	 * 安全启动一个线程
	 * 
	 * @param threadName
	 * @param runnable
	 */
	public synchronized static void startThread(String threadName,
			Runnable runnable) {
		if (getThread(threadName) == null) {
			getThread(threadName, runnable).start();
		}
	}

	/**
	 * 获得某一线程的存活状态
	 * 
	 * @param threadName
	 * @return
	 */
	public static boolean isAlive(String threadName) {
		MThread mThread = threadMap.get(threadName);
		if (mThread != null)
			return !mThread.getKill();
		return false;
	}

	/**
	 * 大概率杀死一个线程
	 * 
	 * @param threadName
	 * @return
	 */
	public static boolean killThread(String threadName) {
		MThread mThread = threadMap.get(threadName);
		if (mThread == null) {
			return false;
		}
		mThread.setKill(true);
		mThread.interrupt();
		return true;
	}

	/**
	 * 杀死大部分线程
	 */
	public static void killAllThread() {
		Iterator<Entry<String, MThread>> iterator = threadMap.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, MThread> entry = iterator.next();
			entry.getValue().keep_kill(true);
			killThread(entry.getKey());
			iterator.remove();
			sendCount();
		}
	}

	private static void sendCount() {
		if (callback != null)
			callback.threadCount(getCount());
	}

	/**
	 * 监听线程数量
	 * 
	 * @param callback
	 */
	public static void setThreadCountListener(ThreadCallback callback) {
		ThreadControl.callback = callback;
	}

	/**
	 * 得到当前线程总数
	 * 
	 * @return
	 */
	public static int getCount() {
		return threadMap.size();
	}

	public static class MThread extends Thread {
		private boolean kill = false;
		private boolean keep_kill = false;

		/**
		 * 线程是否被杀死
		 * 
		 * @return
		 */
		protected boolean getKill() {
			return kill;
		}

		/**
		 * 正在被团灭
		 * 
		 * @param keep_kill
		 */
		protected void keep_kill(boolean keep_kill) {
			this.keep_kill = keep_kill;
		}

		protected void setKill(boolean kill) {
			this.kill = kill;
		}

		public MThread(String threadName) {
			this.setName(threadName);
		}

		public MThread(String threadName, Runnable runnable) {
			super(runnable);
			this.setName(threadName);
		}

		@Override
		public void run() {
			super.run();
			if (!keep_kill) {
				threadMap.remove(getName());
				sendCount();
			}
		}
	}

	public static interface ThreadCallback {
		void threadCount(int count);
	}
}
