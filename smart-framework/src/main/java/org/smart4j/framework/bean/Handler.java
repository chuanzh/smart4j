/**
 * 
 */
package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 * @author Simon Shen
 * 2016-6-23
 */
public class Handler {

	/**
	 * controller类
	 */
	private Class<?> controllerClass;
	
	/**
	 * Action 方法
	 */
	private Method actionMethod;
	
	
	public Handler(Class<?> controllerClass,Method actionMethod){
		this.controllerClass=controllerClass;
		this.actionMethod=actionMethod;
	}


	public Class<?> getControllerClass() {
		return controllerClass;
	}


	public Method getActionMethod() {
		return actionMethod;
	}
	
}
