/**
 * 
 */
package org.smart4j.framework.bean;

/**
 * @author Simon Shen
 * 2016-6-23
 */
public class Data {

	/**
	 * 模型对象
	 */
	private Object model;
	
	public Data(Object model){
		this.model=model;
	}

	public Object getModel() {
		return model;
	}
}
