package com.doservlet.framework.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Poseidon {
	private static ExecutorService service = Executors.newFixedThreadPool(200);

	public static void add(Runnable runnable) {
		service.execute(runnable);
	}
}
