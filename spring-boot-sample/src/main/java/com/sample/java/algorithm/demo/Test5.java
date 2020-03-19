package com.sample.java.algorithm.demo;

/**
 * 算法：动态规划
 * 
 * 0-1背包问题
 * 
 */
public class Test5 {

	public static void main(String[] args) {
		int[] weight = {3,5,2,6,4}; //物品重量
		int[] value =  {4,4,3,5,3}; //物品价值
		int V = 12; //背包容量
		int N = value.length;
		System.out.println(ZeroOnePack(V, N, weight, value));
	}

	/**
	 * 0-1背包的优化解法 
	 * 思路： 
	 * 只用一个一维数组记录状态，dp[i]表示容量为i的背包所能装入物品的最大价值 用逆序来实现
	 * 
	 * 0-1背包问题
     * @param V 背包容量
     * @param N 物品种类
     * @param weight 物品重量
     * @param value 物品价值
	 */
	public static int ZeroOnePack(int V, int N, int[] weight, int[] value) { // 动态规划
		int[] dp = new int[V + 1];
		for (int i = 0; i < N; i++) {
			for (int j = V; j > weight[i]; j--) {
				// dp[j - weight[i]] 表示除去weight[i]重量后dp[j - weight[i]]存放的最大值，加上value[i]，再与dp[j]比较大小
				dp[j] = Math.max(dp[j - weight[i]] + value[i], dp[j]);
			}
		}
		for (int i : dp)
			System.out.print(i + " ");
		System.out.println();
		return dp[V];
	}

}

