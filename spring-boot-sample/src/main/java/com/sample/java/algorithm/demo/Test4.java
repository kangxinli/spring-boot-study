package com.sample.java.algorithm.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 算法：动态规划
 * 
 * 输入两个整数 n 和 m，从数列1，2，3.......n 中随意取几个数, 使其和等于 m,要求将其中所有的可能组合列出来.
 * 
 * 解法见尾部
 *
 */
public class Test4 {
	
	public static void main(String[] args) {
		int n = 7;
		int m = 8;
		List<Integer> list = new ArrayList<Integer>();
		
		// 1.首先判断，如果n>m，则n中大于m的数不可能参与组合，此时置n = m;
		int up = n > m ? m : n;
		
		printList(m, up, list);
		
	}
	
	/**
     * 
     * @param m
     *            要求满足的和sum
     * @param n
     *            取数的范围scope
     * @param list
     *            每一种满足条件的组合
     */
	public static void printList(int m, int n, List<Integer> list) {
//		System.out.println("m=" + m + "/n=" + n + "/list:" + list);
		
		// m=0时，即背包刚好装满，打印并退出
		if (m == 0) {
			System.out.println(list);
			return;
		}
		
		// 根据题意，可知m和n必须为正整数，所以m和n为负数，或n=0时，直接退出
		if (n <= 0 || m < 0) {
			return;
		}
		
		// 拿到上一次计算的结果list
        List<Integer> list1 = new ArrayList<Integer>(list);
        
        // n没有加入
        printList(m, n - 1, list);
        
        // n加入
        list1.add(n);
        printList(m - n, n - 1, list1);
	}
}
/*
Answer解法

这道题就是一道典型的动态规划问题了，思路和背包问题差不多，m就相当于背包能容纳的重量了，就是从右往左校验，通过m，以及m-n进行下一次计算。

逻辑分析

    首先判断，如果n>m，则n中大于m的数不可能参与组合，此时置n = m;

    将最大数n加入且n == m,则满足条件，直接输出；

    将n分两种情况求解

    （1）n没有加入，取n = n-1; m = m;递归下去
    （2）n加入，取n = n-1, m = m-n,递归下去

当前是 ： printList( m , n )

递归下一层是 ： printList( m , n-1 ) 和 printList( m-n , n-1 )

printList(m,n)=printList(m,n−1)+printList(m−n,n−1)；

终止条件：n<=0，以及m<0(m<0说明在上一次递归调用是减的n，结果减多了，所以为负)，m==0时候说明正好找到，打印
*/
