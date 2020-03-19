package com.sample.java.algorithm;

import java.util.Calendar;

/**
 * 数独
 * 
 * 回溯法
 * 
 * @author user
 *
 */
public class ShuDu {

	private int matrix[][];
	private long timeAfter = 0;
	private long timeBefore = 0;

	public ShuDu(int m[][]) {
		matrix = new int[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				matrix[i][j] = m[i][j];
		this.timeBefore = Calendar.getInstance().getTimeInMillis();
	}

	public void backTrack(int i, int j) throws Exception {
		// 回收系统内存资源
		// System.gc();
		if (i == 8 && j >= 9) {
			this.timeAfter = Calendar.getInstance().getTimeInMillis();
			// 成功输出矩阵
			this.showMatrix();
			return;
		}

		//已经到了列末尾了，还没到行尾，就换行
		if (j == 9) {
			j = 0;
			i++;
		}

		//如果i行j列是空格，那么才进入给空格填值的逻辑
		if (matrix[i][j] == 0) {
			// 数字为零
			for (int k = 1; k <= 9; k++) {
				//判断给i行j列放1-9中的任意一个数是否能满足规则
				if (bound(i, j, k)) {
					matrix[i][j] = k;
					// 符合条件，查找下一个方格
					backTrack(i, j + 1);
					//初始化该空格
					matrix[i][j] = 0;
				}
			}
		} else {
			// 数字不为零，直接查找下一个
			backTrack(i, j + 1);
		}
	}

	/**
	 * 判断要填入的数字和同行同列以及同一九宫格内数字是否重复
	 */
	private boolean bound(int i, int j, int k) {
		int m = i / 3;
		int n = j / 3;
		for (int p = 0; p < 9; p++) {
			if (k == matrix[i][p]) {
				return false;
			}
			if (k == matrix[p][j]) {
				return false;
			}
			if (k == matrix[3 * m + p / 3][3 * n + p % 3]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 打印解题时间
	 * 
	 * @return
	 */
	public long printTime() {
		return this.timeAfter - this.timeBefore;
	}

	/**
	 * 打印矩阵
	 */
	public void showMatrix() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("解题时间： " + printTime() + "毫秒");
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		int matrix[][] = { { 3, 0, 6, 0, 5, 7, 0, 0, 0 }, { 7, 9, 0, 0, 2, 4, 0, 0, 0 }, { 0, 5, 0, 6, 0, 0, 9, 7, 4 },
				{ 8, 0, 1, 0, 0, 9, 0, 0, 0 }, { 0, 2, 0, 3, 0, 8, 0, 0, 7 }, { 4, 0, 0, 0, 6, 0, 5, 0, 0 },
				{ 0, 0, 4, 0, 3, 6, 0, 5, 0 }, { 2, 0, 3, 7, 0, 5, 0, 0, 1 }, { 0, 0, 7, 4, 1, 0, 6, 0, 0 } };

		int ma1[][] = { { 0, 3, 0, 0, 0, 5, 0, 6, 0 }, { 0, 1, 0, 0, 0, 3, 0, 8, 0 }, { 0, 4, 0, 0, 0, 0, 0, 0, 7 },
				{ 0, 0, 7, 0, 2, 4, 0, 0, 0 }, { 5, 0, 0, 0, 9, 0, 0, 0, 0 }, { 0, 8, 0, 3, 0, 0, 5, 0, 0 },
				{ 0, 0, 0, 8, 0, 0, 0, 0, 0 }, { 0, 0, 9, 0, 0, 0, 0, 7, 3 }, { 0, 5, 0, 9, 0, 0, 0, 0, 2 } };

		int ma2[][] = { { 0, 0, 0, 0, 8, 4, 0, 0, 0 }, // 8
				{ 0, 0, 0, 2, 0, 3, 0, 8, 0 }, { 8, 3, 0, 9, 0, 0, 0, 5, 0 }, { 0, 5, 3, 0, 9, 0, 7, 0, 0 },
				{ 0, 0, 0, 6, 3, 7, 0, 4, 5 }, // 7
				{ 0, 7, 0, 5, 0, 0, 0, 0, 0 }, { 0, 0, 6, 8, 0, 0, 0, 0, 0 }, { 3, 0, 0, 0, 2, 9, 0, 0, 0 },
				{ 2, 0, 9, 3, 0, 0, 0, 0, 1 } };// 3

		// 号称世界上最难数独
		int[][] sudoku = { { 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 6, 0, 0, 0, 0, 0 }, { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
				{ 0, 5, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 4, 5, 7, 0, 0 }, { 0, 0, 0, 1, 0, 6, 0, 3, 0 },
				{ 0, 0, 1, 0, 0, 0, 0, 6, 8 }, { 0, 0, 8, 5, 0, 0, 0, 1, 0 }, { 0, 9, 0, 0, 0, 0, 4, 0, 0 } };

		int[][] test = { { 5, 0, 8, 0, 9, 0, 0, 0, 7 }, { 1, 0, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 3, 0, 6 },
				{ 0, 0, 4, 0, 6, 0, 0, 0, 0 }, { 0, 7, 0, 5, 0, 0, 0, 8, 1 }, { 0, 0, 0, 0, 0, 3, 2, 0, 0 },
				{ 0, 0, 0, 9, 0, 0, 0, 2, 0 }, { 9, 0, 3, 0, 5, 0, 0, 0, 0 }, { 0, 8, 0, 0, 7, 0, 0, 6, 0 } };

		ShuDu m1 = new ShuDu(matrix);
		m1.backTrack(0, 0);
		
		ShuDu m2 = new ShuDu(ma1);
		m2.backTrack(0, 0);
		
		ShuDu m3 = new ShuDu(ma2);
		m3.backTrack(0, 0);
		
		ShuDu m4 = new ShuDu(sudoku);
		m4.backTrack(0, 0);
		
		ShuDu m5 = new ShuDu(test);
		m5.backTrack(0, 0);
	}

}
