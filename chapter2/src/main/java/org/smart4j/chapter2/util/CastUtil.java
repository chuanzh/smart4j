package org.smart4j.chapter2.util;

/**
 * 类型转换
 * 
 * @author shenz
 * 
 */
public final class CastUtil {

	/**
	 * 转string
	 * 
	 * @param obj
	 * @return
	 */
	public static String castString(Object obj) {
		return CastUtil.castString(obj, "");
	}

	/**
	 * 转string 指定那个默认值
	 * 
	 * @param obj
	 * @return
	 */
	public static String castString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	/**
	 * 转double
	 * 
	 * @param obj
	 * @return
	 */
	public static double castDouble(Object obj) {
		return castDouble(obj, 0);
	}

	/**
	 * 转double 指定那个默认值
	 * 
	 * @param obj
	 * @return
	 */
	public static double castDouble(Object obj, double defaultValue) {
		double doubleValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					doubleValue = Double.parseDouble(strValue);
				} catch (NumberFormatException e) {
					doubleValue = defaultValue;
				}
			}
		}
		return doubleValue;
	}

	/**
	 * 转long
	 * 
	 * @param obj
	 * @return
	 */
	public static long castLong(Object obj) {
		return castLong(obj, 0);
	}

	/**
	 * 转long 指定那个默认值
	 * 
	 * @param obj
	 * @return
	 */
	public static long castLong(Object obj, long defaultValue) {
		long longValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					longValue = Long.parseLong(strValue);
				} catch (NumberFormatException e) {
					longValue = defaultValue;
				}
			}
		}
		return longValue;
	}

	/**
	 * 转int
	 * 
	 * @param obj
	 * @return
	 */
	public static int castInt(Object obj) {
		return castInt(obj, 0);
	}

	/**
	 * 转int 指定那个默认值
	 * 
	 * @param obj
	 * @return
	 */
	public static int castInt(Object obj, int defaultValue) {
		int intValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					intValue = Integer.parseInt(strValue);
				} catch (NumberFormatException e) {
					intValue = defaultValue;
				}
			}
		}
		return intValue;
	}

	/**
	 * 转boolean
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean castBoolean(Object obj) {
		return castBoolean(obj, false);
	}

	/**
	 * 转boolean 指定那个默认值
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean castBoolean(Object obj, boolean defaultValue) {
		boolean booleanValue = defaultValue;
		if (obj != null) {
			booleanValue = Boolean.parseBoolean(castString(obj));
		}
		return booleanValue;
	}
}
