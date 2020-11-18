package com.sample.crypto.utils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;

public class CollectionUtil {
	private CollectionUtil() {
	}

	/**
	 * 判断一个集合是否为空
	 * 
	 * @param c 待判断集合
	 * @return true if null or 0==size()
	 */
	public static <E> boolean isEmpty(Collection<E> c) {
		return null == c || 0 == c.size();
	}

	/**
	 * 判断一个集合是否非空
	 * 
	 * @param c 待判断集合
	 * @return true if not null && size()>0
	 */
	public static <E> boolean isNotEmpty(Collection<E> c) {
		return !isEmpty(c);
	}

	/**
	 * 检查某个值是否在集合中
	 */
	public static <T> boolean targetInCollection(T target, Collection<T> collection) {
		if (isEmpty(collection)) {
			return false;
		}

		/**
		 * 如果是类似ArrayList这种可以随机访问的，使用这种方式提高效率
		 * 
		 * @see List
		 * @see RandomAccess
		 */
		if (collection instanceof List && collection instanceof RandomAccess) {
			int size = collection.size();
			List<T> list = (List<T>) collection;
			for (int i = 0; i < size; i++) {
				if (Objects.equals(list.get(i), target)) {
					return true;
				}
			}
			return false;
		}

		// 普通的集合遍历
		for (T s : collection) {
			if (Objects.equals(s, target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 合并两个集合
	 */
	public static <E, T extends Collection<E>> T merge(T t1, T t2) {
		if (null == t1) {
			return t2;
		}
		if (null == t2) {
			return t1;
		}

		t1.addAll(t2);
		return t1;
	}
}
