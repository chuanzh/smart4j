/**
 * 
 */
package org.smart4j.framework.bean;

import java.util.Map;

import org.smart4j.framework.util.CastUtil;

/**
 * @author Simon Shen
 * 2016-6-23
 */
public class Param {
		private Map<String,Object> paramMap;
		
		public Param(Map<String,Object> paramMap){
			this.paramMap=paramMap;
		}
		
		/**
		 * 根据参数名获取long型参数
		 * @param name
		 * @return
		 */
		public long getLong(String name){
			return CastUtil.castLong(paramMap.get(name));
		}
		/**
		 * 获取所有字符按键值对
		 * @param name
		 * @return
		 */
		public Map<String,Object> getMap(){
			return paramMap;
		}
		
}
