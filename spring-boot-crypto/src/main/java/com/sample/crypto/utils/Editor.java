package com.sample.crypto.utils;

/**
 * 编辑器接口， 1.常用于对于集合中的元素做统一编辑
 * 
 * <pre>
 * 1、如果返回值为<code>null</code>，表示此值被抛弃
 * 2、对对象做修改
 * </pre>
 * 
 * <br>
 * 2.对Model或者Record的列进行编辑
 * 
 * @param <T> 被编辑对象类型
 */
@FunctionalInterface
public interface Editor<T> {
	/**
	 * 修改过滤后的结果
	 * 
	 * @param t 被过滤的对象
	 * @return 修改后的对象，如果被过滤返回<code>null</code>
	 */
	public T edit(T t);
}
