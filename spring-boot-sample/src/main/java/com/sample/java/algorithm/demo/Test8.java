package com.sample.java.algorithm.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 12个高矮不同的人，排成两排，每排必须是从矮到高排列，且第二排比第一排对应的人高。
 *
 */
public class Test8 {
	private static List<Integer[][]> list = new ArrayList<Integer[][]>();
	 
    public static void main(String[] args) {
        Integer[][] array = new Integer[6][2];
        array[0][0] = 1;
        array[5][1] = 12;
 
        putArray(array, 2);
        System.out.println("共" + list.size() + "种结果:\n");
 
        for (Integer[][] a : list) {
            toString(a);
        }
 
    }
 
 
    private static void putArray(Integer[][] array, int i) {
        if (i >= 12) {
            // 检测是否符合要求
            for (int m = 1; m < 6; m++) {
                if (array[m][0] < array[m - 1][0]
                        || array[m][0] > array[m][1]
                        || array[m][1] < array[m - 1][1]) {
                    return;
                }
            }
            list.add(array);
            return;
        }
 
        for (int m = 0; m < 6; m++) {
            if (array[m][0] == null) {
                // 此处可放
                Integer[][] tempArray2 = copy(array); // 复制数组, 避免后期结果影响当前执行
                tempArray2[m][0] = i;
                int temp = i;
                putArray(tempArray2, ++temp);
                break;
            }
        }
 
        for (int n = 0; n < 6; n++) {
            if (array[n][1] == null) {
                // 此处可放
                Integer[][] tempArray3 = copy(array); // 复制数组, 避免后期结果影响当前执行
                tempArray3[n][1] = i;
                int temp2 = i;
                putArray(tempArray3, ++temp2);
                break;
            }
        }
 
    }
 
    private static void toString(Integer[][] a) {
        StringBuffer sb = new StringBuffer();
        for (int n = 0; n < 2; n++) {
            for (int m = 0; m < 6; m++) {
                sb.append(a[m][n] < 10 ? " " + a[m][n] : a[m][n]);
                sb.append(m != 5 ? ", " : "\n");
            }
        }
        System.out.println(sb.toString() + "\n----------------------");
    }
 
    private static Integer[][] copy(Integer[][] array) {
        Integer[][] result = new Integer[array.length][array[0].length];
        for (int m = 0; m < array.length; m++) {
            for (int n = 0; n < array[0].length; n++) {
                result[m][n] = array[m][n];
            }
        }
        return result;
    }
}
