package com.clown.framework.utils;

import java.util.Map;
/**
 * 字符串
 * @author colin.tan
 *
 */
public class StringUtil {
	public static boolean isNE(String input) {
		return null == input || 0 == input.length()
				|| 0 == input.replaceAll("\\s", "").length();
	}

	public static String killNull(String str) {
		String returnStr = null;
		if (str == null || "null".equals(str.toLowerCase())) {
			returnStr = "";
		} else {
			returnStr = str;
		}
		return returnStr;
	}

	/**
	 * 日期格式2016-03-23 转为2016年03月23日
	 * 
	 * @param timefir
	 *            时间字符串对象
	 * @return 字符串
	 */
	public static String timeFirs(String timefir) {
		String[] timefirs = new String[3];
		// String timefir = "2009-9-28";
		timefirs = timefir.split("-");
		String mydate1 = timefirs[0] + "年" + timefirs[1] + "月" + timefirs[2]
				+ "日";
		return mydate1;
	}

	/**
	 * 去空格
	 */
	public static String trim(String str) {
		String returnStr = null;
		returnStr = killNull(str);
		returnStr = returnStr.trim();
		return returnStr;
	}

	/**
	 * 字符串拆分
	 * 
	 * @param sourceStr
	 *            拆分对象
	 * @param spiString
	 *            拆分字符
	 * @return 字符串
	 */
	public static String StringSplit(String sourceStr, String spiString) {
		String str = "";
		String[] sourceStrArray = sourceStr.split(spiString);
		for (int i = 0; i < sourceStrArray.length; i++) {
			str = sourceStrArray[i];
		}
		return str;
	}

	/**
	 * 字符串截取
	 * 
	 * @param sub
	 *            截取对象
	 * @param startsub
	 *            截取开始下标
	 * @param endsub
	 *            截取最后下标
	 * @return 字符串
	 */
	public static String sub(String sub, int startsub, int endsub) {
		return sub.substring(startsub, endsub);
	}

	/**
	 * 将ISO-8859-1编码转换为 UTF-8
	 * 
	 * @param str
	 *            目标字符串
	 * @return String
	 */
	public static String ISOtoUTF8(String str) {
		if (str == null) {
			return " ";
		}
		try {
			byte[] bytesStr = str.getBytes("ISO-8859-1");
			return new String(bytesStr, "UTF-8");
		} catch (Exception ex) {
			return str;
		}
	}

	/**
	 * 断是否为数字整数
	 * 
	 * @param number
	 * @return boolean:true or false
	 */
	public static boolean isInt(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException sqo) {
			return false;
		}
	}

	/**
	 * 获取Map中的属性
	 * 
	 * @see 由于Map.toString()打印出来的参数值对,是横着一排的...参数多的时候,不便于查看各参数值
	 * @see 故此仿照commons-lang.jar中的ReflectionToStringBuilder.toString()编写了本方法
	 * @return String key11=value11 \n key22=value22 \n key33=value33 \n......
	 */
	public static String getStringFromMap(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		sb.append(map.getClass().getName()).append("@").append(map.hashCode())
				.append("[");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append("\n").append(entry.getKey()).append("=")
					.append(entry.getValue());
		}
		return sb.append("\n]").toString();
	}

}
