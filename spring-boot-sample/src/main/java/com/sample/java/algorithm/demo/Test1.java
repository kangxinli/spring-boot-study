package com.sample.java.algorithm.demo;

/**
 * 在一个字符串中找到第一个只出现一次的字符。
 * 
 * 如输入abaccdeff，则输出b。
 * @author user
 *
 */
public class Test1 {

	public static void main(String[] args) {
		test("abaccdeff");
		System.out.println("aaccdeff".indexOf("b"));
	}
	
	public static void test(String str) {
		String result = "";
		for (int i=0; i < str.length(); i++) {
			String temp = str.substring(0, i) + str.substring(i+1);
			int index = temp.indexOf(str.charAt(i));
			if (index == -1) {
				result = String.valueOf(str.charAt(i));
				break;
			}
		}
		System.out.println(result);
	}

}
