/**
 * 
 */
package org.smart4j.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 请求封装类
 * 
 * @author Simon Shen 2016-6-23
 * 
 */
public class Request {

	/**
	 * 请求方法
	 */
	private String requesstMethod;

	/**
	 * 请求路劲
	 */
	private String requestPath;

	public Request(String requestMethod, String requestPath) {
		this.requesstMethod = requestMethod;
		this.requestPath = requestPath;
	}

	public String getRequesstMethod() {
		return requesstMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
