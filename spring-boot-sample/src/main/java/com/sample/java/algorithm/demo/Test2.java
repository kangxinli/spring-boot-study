package com.sample.java.algorithm.demo;

/**
 * 求子数组和的最大值
 * 
 * 例如输入的数组为1, -2, 3, 10, -4, 7, 2, -5，和最大的子数组为3, 10, -4, 7, 2，因此输出为该子数组的和18。
 * @author user
 *
 */
public class Test2 {

	public static void main(String[] args) {
		int[] arr = {1, -2, 3, 10, -4, 7, 2, -5};
		test(arr);
	}
	
	public static void test(int[] arr) {
		// sum 为子数组的和
		int sum = 0;
		
		// max 为子数组的最大值
		int max = 0;
		
		// start 为最大子数组的起始位置
		int start = 0;
		
		// end 为最大子数组的结束位置
		int end = 0;
		
		for (int i=0; i < arr.length; i++) {
			sum += arr[i];
			
			if (sum < 0) {
				sum = 0;
				start = i + 1;
			}
			
			if (sum > max) {
				max = sum;
				end = i;
			}
		}
		
		System.out.println("Max : " + max);
		System.out.println("start-end : " + start + "-" + end);
	}

}
