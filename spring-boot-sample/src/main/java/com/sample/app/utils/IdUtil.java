package com.sample.app.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 */
public abstract class IdUtil {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成,中间有-分割
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return random.nextLong();
	}
	
	/**
	 * 时间戳
	 * @return
	 */
	public static Long timeStamp(){
		return DateUtil.now().getTime();
	}

	/**
	 * 生成时间戳+随机数字
	 * @param length 时间戳后面几位数字
	 * @return
	 */
	public static Long randMark(int length){
		Long time = timeStamp();
		String mark = time.toString();
		Random r=new Random();
		for (int i = 0; i < length; i++) {
			mark+=r.nextInt(9);
		}
		return Long.parseLong(mark);
	}
	
	public static void main(String[] args){
		for (int i = 0; i < 10; i++) {
			System.out.println("309ca3f3-293c-48f0-a549-b9aa8eab8d7c".length());
		}
	}
}