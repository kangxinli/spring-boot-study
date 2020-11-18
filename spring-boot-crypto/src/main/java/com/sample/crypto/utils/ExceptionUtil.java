package com.sample.crypto.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 */
public class ExceptionUtil {
	private ExceptionUtil() {
	}

	/**
	 * 获取异常的堆栈信息。logback自动支持，使用logger.error(msg,Throwable e)
	 * 
	 * @param e 异常
	 * @return 异常堆栈
	 */
	public static String getExceptionStack(Exception e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			// 将出错的栈信息输出到printWriter中
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			IoUtil.close(sw);
			IoUtil.close(pw);
		}
		return sw.toString();
	}

	/**
	 * 一直往上找，找到根原因
	 */
	public static Throwable getRootThrowable(Throwable throwable) {
		Throwable t = throwable;
		Throwable temp = throwable;
		while (null != t) {
			// 记录最后不为空的异常
			temp = t;
			t = t.getCause();
		}
		return temp;
	}
}