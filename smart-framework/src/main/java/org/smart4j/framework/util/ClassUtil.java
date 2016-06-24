package org.smart4j.framework.util;

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
 * @author Simon Shen 2016-6-23
 */
public final class ClassUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClassUtil.class);

	/**
	 * 获类加载器
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
		} catch (ClassNotFoundException e) {
			LOGGER.error("load class failure", e);
			throw new RuntimeException(e);
		}
		return cls;
	}

	/**
	 * 获取指定包下所有类
	 * 
	 * @param packgeName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packgeName) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(
					packgeName.replace(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol();
					if (protocol.equals("file")) {
						String packagePath = url.getPath().replaceAll("%20",
								" ");
						addClass(classSet, packagePath, packgeName);
					} else if (protocol.equals("jar")) {
						//jar专用
						JarURLConnection jarURLConnection = (JarURLConnection) url
								.openConnection();
						if (jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if (jarFile != null) {
								Enumeration<JarEntry> jarEntries = jarFile
										.entries();
								while (jarEntries.hasMoreElements()) {
									JarEntry jarEntry = jarEntries
											.nextElement();
									String jarEntryName = jarEntry.getName();
									if (jarEntryName.endsWith(".class")) {
										String className = jarEntryName
												.substring(
														0,
														jarEntryName
																.lastIndexOf("."))
												.replaceAll("/", ".");
										doAddClass(classSet, className);
									}
								}
							}
						}

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("get class set failure",e);
		}
		return classSet;
	}

	/**
	 * 添加类加载
	 * 
	 * @param classSet
	 * @param packagePath
	 * @param packageName
	 */
	private static void addClass(Set<Class<?>> classSet, String packagePath,
			String packageName) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			/**
			 * 过滤文件
			 */
			@Override
			public boolean accept(File file) {

				return (file.isFile() && file.getName().endsWith(".class"))
						|| file.isDirectory();
			}

		});

		// 遍历文件
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0,
						fileName.lastIndexOf("."));
				if (StringUtil.isNotEmpty(packageName)) {
					className = packageName + "." + className;
				}
				doAddClass(classSet, className);
			} else {
				String subPackagePath = fileName;
				if (StringUtil.isNotEmpty(packagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}
				String subPackageName = fileName;
				if (StringUtil.isNotEmpty(packageName)) {
					subPackageName = packageName + "." + subPackageName;
				}
				addClass(classSet, subPackagePath, subPackageName);
			}
		}
	}

	/**
	 * 向集合中添加类
	 * 
	 * @param classSet
	 * @param ClassName
	 */
	private static void doAddClass(Set<Class<?>> classSet, String ClassName) {
		Class<?> cls = loadClass(ClassName, false);
		classSet.add(cls);
	}
}
