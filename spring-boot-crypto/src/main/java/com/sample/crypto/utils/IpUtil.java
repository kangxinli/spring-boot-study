package com.sample.crypto.utils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值 获取代理之后的客户端的真实IP，支持多级代理
	 * 1.有X-Forwarded-For的分两种： 1.1有逗号分隔的，第一个才是真实IP 1.2没有逗号分隔的，就是他 2.有X-Real-IP的就是他
	 * 3....
	 *
	 *
	 * X-Forwarded-For ：这是一个 Squid 开发的字段，只有在通过了HTTP代理或者负载均衡服务器时才会添加该项。
	 * 格式为X-Forwarded-For:client1,proxy1,proxy2，一般情况下，第一个ip为客户端真实ip，后面的为经过的代理服务器ip。现在大部分的代理都会加上这个请求头。
	 * X-Real-IP ：nginx代理一般会加上此请求头。 Proxy-Client-IP/WL- Proxy-Client-IP
	 * ：这个一般是经过apache http服务器的请求才会有，用apache
	 * http做代理时一般会加上Proxy-Client-IP请求头，而WL-Proxy-Client-IP是他的weblogic插件加上的头。
	 * HTTP_CLIENT_IP ：有些代理服务器会加上此请求头。
	 */
	private static final String UN_KNOWN = "unknown";
	private static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
	private static final String[] PROXY_IP_HEADERS = { "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };

	/**
	 * 获取客户端真实IP
	 * 
	 * @param headerGetter        提供获取请求中header的方法
	 * @param remoteAddressGetter 提供获取请求中远程地址的方法，可以为空，就忽略
	 * @return 客户端IP
	 */
	public static String getClientIp(HeaderGetter headerGetter, RemoteAddressGetter remoteAddressGetter) {
		String ip = headerGetter.getHeader(X_FORWARDED_FOR);
		if (StrUtil.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.contains(StrUtil.COMMA)) {
				ip = ip.split(StrUtil.COMMA)[0];
			}
		}

		for (String proxyIpHeader : PROXY_IP_HEADERS) {
			if (StrUtil.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
				ip = headerGetter.getHeader(proxyIpHeader);
			}
		}

		if (StrUtil.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
			if (null != remoteAddressGetter) {
				ip = remoteAddressGetter.getRemoteAddress();
			}
		}
		return ip;
	}

	@FunctionalInterface
	public interface RemoteAddressGetter {
		/**
		 * 获取远程地址
		 * 
		 * @see HttpServletRequest
		 */
		String getRemoteAddress();
	}
}
