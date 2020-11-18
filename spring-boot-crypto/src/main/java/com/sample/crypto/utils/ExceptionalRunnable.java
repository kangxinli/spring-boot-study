package com.sample.crypto.utils;

/**
 * 可以抛出异常的runnable,{@link Runnable}
 */
@FunctionalInterface
public interface ExceptionalRunnable {
	/**
	 * 执行的代码
	 * 
	 * @throws Exception 可以抛出异常
	 */
	void run() throws Exception;
}
