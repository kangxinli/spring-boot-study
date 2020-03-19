package com.sample.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取配置文件
 */
public class ReadConfigUtil{
	
	private static final Logger logger = Logger.getLogger(ReadConfigUtil.class);
	private static String UPLOADCONFIGFILE = "/config.properties";
	/**
	 * 文件配置
	 */
	private static Properties uploadConfig = new Properties();
	
	static {
		InputStream is = ReadConfigUtil.class.getResourceAsStream(UPLOADCONFIGFILE);
		try {
			uploadConfig.load(is);
		} catch (IOException e) {
			logger.error("读取文件上传配置失败 :" + UPLOADCONFIGFILE);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("关闭文件上传配置读取！");
			}
		}
	}
	
	/**
	 * 根据key值 获取配置文件信息
	 * @param key：static.path
	 * @return
	 */
	public static String getValue(String key){
		return uploadConfig.getProperty(key);
	}
}
