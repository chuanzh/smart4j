/**
 * 
 */
package org.smart4j.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;

/**
 * @author Simon Shen 2016-6-23
 */
public final class ControllerHelper {
	/**
	 * 用于存放请求和处理的映射 Map
	 */
	private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

	static {
		// 获得所有controller
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if (CollectionUtil.isNotEmpty(controllerClassSet)) {
			for (Class<?> controllerClass : controllerClassSet) {
				// 获取contrller的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if (ArrayUtil.isNotEmpty(methods)) {
					for (Method method : methods) {
						// 判断是否有action的注解
						if (method.isAnnotationPresent(Action.class)) {
							// 从Action中获得url映射规则
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							// 验证URL规则
							if (mapping.matches("\\w+:/\\w*")) {
								String[] array = mapping.split(":");
								if (ArrayUtil.isNotEmpty(array)
										&& array.length == 2) {
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(
											requestMethod, requestPath);
									Handler handler = new Handler(
											controllerClass, method);
									ACTION_MAP.put(request, handler);
								}
							}

						}

					}
				}

			}
		}
	}

	/**
	 * 获取Handler
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
