package org.lf.admin.api.baseapi.config;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class SysConfig {
	private static Properties pro = new Properties();

	static {
		loadConfig();
	}

	private static void loadConfig() {
		try {
			InputStream stream = SysConfig.class.getResourceAsStream("/sysconfig.properties");
			pro.load(stream);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String storeConfig(){
		try {
			FileOutputStream out = new FileOutputStream("sysconfig.properties");
			pro.store(out,"System Config File");
			return "OK";
		}
		catch(Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 查询属性的值
	 * 
	 * @param proName
	 *            属性名称
	 * @return
	 */
	public static String getProperty(String proName) {
		return pro.getProperty(proName);
	}

	/**
	 * 安全查询属性的值
	 * 
	 * @param proName
	 *            属性名称
	 * @param defaultValue
	 *            如果没有找到该属性则返回的值
	 * @return
	 */
	public static String getProperty(String proName, String defaultValue) {
		String value = pro.getProperty(proName);
		if (value == null) {
			return defaultValue;
		}
		else {
			return pro.getProperty(proName);
		}
	}

	public static void setProperty(String key, String newValue) {
		pro.setProperty(key, newValue);
	}

}
