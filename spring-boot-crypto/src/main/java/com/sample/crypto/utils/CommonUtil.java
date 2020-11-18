package com.sample.crypto.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 通用工具
 */
public class CommonUtil {
	/**
	 * 定义一个字符串（A-Z，a-z，0-9）即62位，和长度
	 */
	private CommonUtil() {
	}

	private static final String STR = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM";
	private static final String NUMBER = "1234567890";
	private static final String STR_NUMBER = STR + NUMBER;

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}

	/**
	 * @param length 返回字符串长度
	 * @param range  在哪些字符范围里面随机
	 */
	public static String randomString(int length, String range) {
		// 由Random生成随机数
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		int len = range.length();
		// 长度为几就循环几次
		for (int i = 0; i < length; ++i) {
			// 产生0-STR_LEN的数字
			int number = random.nextInt(len);
			// 将产生的数字通过length次承载到sb中
			sb.append(range.charAt(number));
		}
		// 将承载的字符转换成字符串
		return sb.toString();
	}

	/**
	 * 随机字符串，包含数字和字符
	 * 
	 * @param length 长度
	 */
	public static String randomString(int length) {
		return randomString(length, STR_NUMBER);
	}

	/**
	 * 随机字符串，只包含字符
	 * 
	 * @param length 长度
	 */
	public static String randomChar(int length) {
		return randomString(length, STR);
	}

	/**
	 * 随机字符串，只包含数字
	 * 
	 * @param length 长度
	 */
	public static String randomNumber(int length) {
		return randomString(length, NUMBER);
	}
}