package com.ypc.common.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 属性文件工具类
 * @author Administrator
 *
 */
public class PropertiesUtils {
	
	/**
	 * 获取属性文件
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String getPropertyValue(String key) throws IOException {
		Properties properties=(Properties)SpringContextsUtil.getBean("jhConfigs");
		return properties.getProperty(key);
	}
	
	/**
	 * 获取当前应用的访问地址
	 * @return
	 * @throws IOException
	 */
	public static String getSystemLink() throws IOException{
		return getPropertyValue("systemLink"); 
	}
}
