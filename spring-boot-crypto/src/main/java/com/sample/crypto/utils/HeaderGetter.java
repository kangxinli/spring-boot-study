package com.sample.crypto.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 因为基于Servlet的和其他的框架例如vertx的获取方式不一样
 */
@FunctionalInterface
public interface HeaderGetter {
	/**
	 * 从请求中获取header
	 * 
	 * @see HttpServletRequest
	 * @param headerKey headerKey
	 * @return header
	 */
	String getHeader(String headerKey);
}