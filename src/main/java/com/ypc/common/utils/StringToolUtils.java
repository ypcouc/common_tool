package com.ypc.common.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class StringToolUtils extends StringUtils {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str != null && str.length() == 0));
	}

	public static String getUUID() {
		UUID randomUUID = UUID.randomUUID();
		String uuid = randomUUID.toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	/**
	 * String字符串不足位数向左补零
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForString(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
	
	/**
	 * String字符串超长从后截取
	 * @param str
	 * @param
	 * @return
	 */
	public static String cutForString(String str, int length) {
		int strLen = str.length();
		if (strLen > length) {
			str = str.substring(strLen-length, strLen);
		}
		return str;
	}
	
	/**
	 * 条码超长截取不足补0
	 * @param str
	 * @param length
	 * @return
	 */
	public static String barCodeCutAndAdd(String str,int length){
		if(StringUtils.isEmpty(str)){
			str = "";
		}
		if(str.length()>length){
			str = cutForString(str, length);
		}else if(str.length()<length){
			str = addZeroForString(str, length);
		}
		return str;
	}

}
