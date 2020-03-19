package com.sample.java.algorithm.demo;

/**
 * 找出数组中两个只出现一次的数字
 * 
 * 题目：一个整型数组里除了两个数字之外，其他的数字都出现了两次。
 *
 */
public class Test7 {

	public static void main(String[] args) {
		int[] a = {5,3,4,3,4};
		FindNumsAppearOnce(a);
	}

	public static void FindNumsAppearOnce(int data[]) {
		if (data.length < 2)
			return;
		int resultExclusiveOR = 0;
		for (int i = 0; i < data.length; ++i)
			resultExclusiveOR ^= data[i];
		System.out.println(resultExclusiveOR);
	}

}
