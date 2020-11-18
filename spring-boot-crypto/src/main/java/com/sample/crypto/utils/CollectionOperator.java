/**
 * @FileName: CollectionOperator.java
 * @Description:
 * @Date 2016年4月19日 上午11:29:28
 * @CopyRight ZTE Corporation
 */
package com.sample.crypto.utils;

import java.util.*;

/**
 * 提供集合的交并差功能。 该类提供的方法任何一个调用之后应该立即使用其结果，而不能先存留
 */
public class CollectionOperator<E> {

	Collection<E> opt1;
	Collection<E> opt2;
	Collection<E> result;

	/**
	 * 用于操作的两个集合
	 */
	public CollectionOperator(Collection<E> opt1, Collection<E> opt2) {
		if (opt1 == null || opt2 == null) {
			throw new RuntimeException("请填写非空集合!");
		}
		this.opt1 = opt1;
		this.opt2 = opt2;
		result = new HashSet<E>();// 这里不能使用ArrayList<>()，否则求并集就可能会有重复的
	}

	/**
	 * 集合交
	 */
	public Collection<E> intersect() {
		result.clear();
		result.addAll(opt1);
		result.retainAll(opt2);
		return result;
	}

	/**
	 * 集合并
	 */
	public Collection<E> union() {
		result.clear();
		result.addAll(opt1);
		result.addAll(opt2);
		return result;
	}

	/**
	 * 集合差
	 */
	public Collection<E> diff() {
		result.clear();
		result.addAll(opt1);
		result.removeAll(opt2);
		return result;
	}

	/**
	 * 集合差，顺序颠倒
	 */
	public Collection<E> diffReverse() {
		result.clear();
		result.addAll(opt2);
		result.removeAll(opt1);
		return result;
	}
}
