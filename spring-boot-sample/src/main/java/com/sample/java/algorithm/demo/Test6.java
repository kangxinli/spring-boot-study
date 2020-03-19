package com.sample.java.algorithm.demo;

/**
 * 输入一个正数n,输出所有和为n的连续正数序列
 * 
 * 举例：输入15,由于1+2+3+4+5=4+5+6=7+8=15, 所以输出3个连续序列 1,2,3,4,5; 4,5,6; 7,8。
 *
 */
public class Test6 {

	public static void main(String[] args) {
		test(16);
	}

	public static void test(int n) {
		if (n <= 0) {
			return;
		}
		int first = 1;
		int last = 2;
		int end = (n + 1) / 2;
		int sum = 1;
		while (first <= end) // 序列必定有两个及以上
			if (sum == n) {
				for (int i = first; i < last; i++) {
					System.out.print(i + " ");
				}
				System.out.println();
				sum -= first;
				first++;
			} else if (sum < n) {
				sum += last;
				last++;
			} else if (sum > n) {
				sum -= first;
				first++;
			}
	}

	static boolean binaryIs2Power(int num) {
		if (num < 2)
			return false;

		int temp = 1;
		while (num > temp) {
			temp <<= 1;
		}

		return temp == num ? true : false;
	}

}
