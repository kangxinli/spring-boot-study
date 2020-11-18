package com.sample.crypto.utils;

import java.util.Map;

public class MapUtil {
	private MapUtil() {
	}

	/**
	 * 判断一个map是否为空
	 * 
	 * @param map map
	 * @return true if null or isEmpty
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return null == map || map.isEmpty();
	}

	/**
	 * 判断一个map是够非空
	 * 
	 * @param map map
	 * @return true if not null and !isEmpty
	 */
	public static <K, V> boolean notEmpty(Map<K, V> map) {
		return !isEmpty(map);
	}

	/**
	 * 合并两个Map，如果一个为{@code null}就返回另外一个
	 * 
	 * @param first  第一个
	 * @param second 第二个
	 * @param <K>    K
	 * @param <V>    V
	 * @return 合并后的Map
	 */
	public static <K, V> MultiValueMap<K, V> mergeMap(MultiValueMap<K, V> first, MultiValueMap<K, V> second) {
		if (null == first) {
			return second;
		}
		if (null == second) {
			return first;
		}
		second.forEachKeyValue(first::add);

		return first;
	}

	/**
	 * 合并两个Map，如果一个为{@code null}就返回另外一个
	 * 
	 * @param first     第一个
	 * @param second    第二个
	 * @param holdFirst 同样的key的时候first覆盖second的 if true
	 * @param <K>       K
	 * @param <V>       V
	 * @return 合并后的Map
	 */
	public static <K, V> Map<K, V> mergeMap(Map<K, V> first, Map<K, V> second, boolean holdFirst) {
		if (null == first) {
			return second;
		}
		if (null == second) {
			return first;
		}
		// 保留第一个：同样的key的时候first覆盖second的
		if (holdFirst) {
			first.forEach(second::put);
			return second;
		} else {
			second.forEach(first::put);
			return first;
		}
	}

	/**
	 * https://www.cnblogs.com/liumz0323/p/12145393.html
	 * 
	 * @param cap 事先知道的map会装这么多个值
	 * @return map的初始size
	 */
	public static int bestMapSize(int cap) {
		// 因为默认因子就是0.75
		return cap * 4 / 3 + 1;
	}

}
