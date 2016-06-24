/**
 * 
 */
package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

/**
 * 依赖注入助手类
 * 
 * @author Simon Shen 2016-6-23
 */
public final class IocHelper {

	static {
		// 获取所有bean类 与bean实例之间的关系
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		if (CollectionUtil.isNotEmpty(beanMap)) {
			for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				// 获取bean中的所有成员变量
				Field[] beanFields = beanClass.getDeclaredFields();
				if (ArrayUtil.isNotEmpty(beanFields)) {
					for (Field beanField : beanFields) {
						//属性上是否依赖注入的注解
						if(beanField.isAnnotationPresent(Inject.class)){
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance =beanMap.get(beanFieldClass);
							if(beanFieldInstance != null){
								//通过反射获取beanfield的值
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
