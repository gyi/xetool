package com.xe.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.core.general.loader.DynamicClassLoader;
import com.core.general.loader.PackageScanner;

/**
 * 
 * @Create time : 2014-11-26
 * @author : zhaowei
 * @Description : TODO
 */
public class ClassManager {

	private static String path = "com.base.def";
	/**
	 * <类名，类全路径>
	 */
	private Map<String, Class<?>> clazMap = new HashMap<>();

	private static ClassManager manager;

	private ClassManager() {
		load();
	}

	public static ClassManager instance() {
		if (manager == null) {
			manager = new ClassManager();
		}
		return manager;
	}

	/**
	 * 加载配置类
	 */
	private void load() {
		Set<Class<?>> classSet = PackageScanner.scanPackages(path, true, new DynamicClassLoader());
		for (Class<?> claz : classSet) {
			String simpleName = claz.getSimpleName();
			clazMap.put(simpleName, claz);
		}
	}

	/**
	 * 获得配置类型
	 * 
	 * @param simpleName
	 *            类简单类名
	 * @return
	 */
	public Class<?> get(String simpleName) {
		return clazMap.get(simpleName);
	}
}
