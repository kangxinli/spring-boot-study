package com.sample.java.jvm.demo;


/**
 * 逃逸分析
 * 
 * 	关闭逃逸分析
 * 		运行参数：-Xmx4G -Xms4G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * 		其中-XX:-DoEscapeAnalysis表示关闭逃逸分析
 *
 *	开启逃逸分析
 * 		运行参数：-Xmx4G -Xms4G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * 		其中-XX:+DoEscapeAnalysis表示开启逃逸分析
 */
public class EscapeAnalysisTest {

	public static void main(String[] args) {

	    long a1 = System.currentTimeMillis();

	    for (int i = 0; i < 1000000; i++) {

	        alloc();

	    }

	    // 查看执行时间

	    long a2 = System.currentTimeMillis();

	    System.out.println("cost " + (a2 - a1) + " ms");

	    // 为了方便查看堆内存中对象个数，线程sleep

	    try {

	        Thread.sleep(100000);

	    } catch (InterruptedException e1) {

	        e1.printStackTrace();

	    }

	}

	private static void alloc() {

	    new User();

	}

	static class User {

	}

}
