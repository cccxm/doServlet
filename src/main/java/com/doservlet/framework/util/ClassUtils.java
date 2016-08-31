package com.doservlet.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类操作工具
 * 
 * @author 陈小默
 *
 */
public final class ClassUtils {
	private static final Logger l = LoggerFactory.getLogger(ClassUtils.class);

	/**
	 * 获取类加载器
	 * 
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 加载类
	 * 
	 * @param className
	 * @param isInitialized
	 * @return
	 */
	public static Class<?> loadClass(String className, boolean isInitialized) {
		Class<?> cls;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cls;
	}

	/**
	 * 加载类
	 * 
	 * @param className
	 * @return
	 */
	public static Class<?> loadClass(String className) {
		return loadClass(className, false);
	}

	/**
	 * 获取指定包名下的所有类
	 * 
	 * @param packageName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(
					packageName.replaceAll("[.]", "/"));
			URL url;
			String protocol, packagePath;
			while (urls.hasMoreElements()) {
				url = urls.nextElement();
				if (url != null) {
					protocol = url.getProtocol();
					if (protocol.equals("file")) {
						packagePath = url.getPath().replaceAll("%20", " ");
						addClass(classes, packagePath, packageName);
					} else if (protocol.equals("jar")) {
						JarURLConnection jarURLConnection = (JarURLConnection) url
								.openConnection();
						if (jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if (jarFile != null) {
								Enumeration<JarEntry> entries = jarFile
										.entries();
								while (entries.hasMoreElements()) {
									JarEntry jarEntry = entries.nextElement();
									String jarEntryName = jarEntry.getName();
									if (jarEntryName.endsWith(".class")) {
										if (jarEntryName.contains("$"))
											continue;
										String className = jarEntryName
												.substring(
														0,
														jarEntryName
																.lastIndexOf("."))
												.replaceAll("/", ".");
										try {
											doAddClass(classes, className);
										} catch (Throwable e) {
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			l.error("Throwable", e);
			throw new RuntimeException(e);
		}
		return classes;
	}

	private static void addClass(Set<Class<?>> classes, String packagePath,
			String packageName) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {

			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class"))
						|| file.isDirectory();
			}
		});
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0,
						fileName.lastIndexOf("."));
				if (!Regex.isBlank(packageName))
					className = packageName + "." + className;
				doAddClass(classes, className);
			} else {
				String subPackagePath = fileName;
				if (!Regex.isBlank(packagePath))
					subPackagePath = packagePath + "/" + subPackagePath;
				String subPackageName = fileName;
				if (!Regex.isBlank(packageName))
					subPackageName = packageName + "." + subPackageName;
				addClass(classes, subPackagePath, subPackageName);
			}
		}
	}

	private static void doAddClass(Set<Class<?>> classes, String className) {
		Class<?> cls = loadClass(className);
		classes.add(cls);
	}
}
