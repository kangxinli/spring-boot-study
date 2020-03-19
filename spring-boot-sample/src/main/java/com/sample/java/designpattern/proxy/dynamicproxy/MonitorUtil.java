package com.sample.java.designpattern.proxy.dynamicproxy;

public class MonitorUtil {
	
	private static ThreadLocal<Long> tl = new ThreadLocal<Long>();
	
	public static void start() {
		tl.set(System.currentTimeMillis());
	}
	
	// 结束时打印耗时
	public static void finish(String methodName) {
		long finishTime = System.currentTimeMillis();
		System.out.println(methodName + "方法耗时" + (finishTime - tl.get()));
	}
}
