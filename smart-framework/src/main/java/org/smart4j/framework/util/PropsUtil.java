package org.smart4j.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 属性文件工具类
 * @author Simon Shen
 * 2016-6-23
 */
public class PropsUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PropsUtil.class);

	/***
	 * 加载属性文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties lodProps(String fileName) {
		Properties props = null;
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName);
			if (is == null) {
				throw new FileNotFoundException(fileName + "file is not found");
			}
			props = new Properties();
			props.load(is);
		} catch (IOException e) {
			LOGGER.error("load properties file failure", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error("close properties file  input stream failure",
							e);
				}
			}
		}
		return props;
	}
	/**
	 * 获取字符型属性 默认空
	 * @param props
	 * @param key
	 * @return
	 */
	public  static String getString(Properties props,String key){
		return getString(props,key,"");
	}
	/**
	 * 获取字符型属性 默认可指定
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public  static String getString(Properties props,String key,String defaultValue){
		String value=defaultValue;
        if(props.containsKey(key)){
        	value=props.getProperty(key);
        }
		return value;
	}
	/**
	 * 数值型属性
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Properties props,String key){
		return getInt(props,key,0);
	}
	public static int getInt(Properties props,String key,int defaultValue){
		int value=defaultValue;
        if(props.containsKey(key)){
        	value=Integer
        			.parseInt(props.getProperty(key));
        }
		return value;
	}
}
