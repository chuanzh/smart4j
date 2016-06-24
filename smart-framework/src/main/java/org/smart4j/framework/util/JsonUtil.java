/**
 * 
 */
package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Simon Shen
 * 2016-6-23
 */
public final class JsonUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JsonUtil.class);
	
	private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();
	/**
	 * pojo to json
	 * @param obj
	 * @return
	 */
	public static <T> String toJson(T obj){
		String json;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.error("Pojo To Json  failure", e);
			throw new RuntimeException(e);
		}
		return json;
	}
	/**
	 * pojo to json
	 * @param obj
	 * @return
	 */
	public static <T> T fromJson(String json,Class<T> type){
		T pojo;
		try {
			pojo = OBJECT_MAPPER.readValue(json,type);
		} catch (Exception e) {
			LOGGER.error("Json To Pojo failure", e);
			throw new RuntimeException(e);
		}
		return pojo;
	}
}
