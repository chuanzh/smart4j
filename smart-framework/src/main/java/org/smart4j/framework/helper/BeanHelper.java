/**
 * 
 */
package org.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.util.ReflectionUtil;

/**
 * @author Simon Shen 2016-6-23
 */
public final class BeanHelper {

	/**
	 * 定义Bean映射 Map
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for (Class<?> beanclass : beanClassSet) {
			Object obj = ReflectionUtil.newInstance(beanclass);
			BEAN_MAP.put(beanclass, obj);
		}
	}

	/**
	 * 获取beanMap
	 * @return
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}
	/**
	 * 根据class 获取 bean实例
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls){
		
		if(!BEAN_MAP.containsKey(cls)){
			throw new RuntimeException("Can not get bean by class:"+cls);
		}
		return (T) BEAN_MAP.get(cls);
	}
	 
}
