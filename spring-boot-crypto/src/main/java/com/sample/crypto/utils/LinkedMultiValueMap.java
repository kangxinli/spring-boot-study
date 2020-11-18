package com.sample.crypto.utils;

import java.io.Serializable;
import java.util.*;

/**
 * Simple implementation of {@link MultiValueMap} that wraps a
 * {@link LinkedHashMap}, storing multiple values in a {@link LinkedList}.
 *
 * <p>
 * This Map implementation is generally not thread-safe. It is primarily
 * designed for data structures exposed from request objects, for use in a
 * single thread only.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 3.0
 */
public class LinkedMultiValueMap<K, V> extends AbstractMultiValueMap<K, V>
		implements MultiValueMap<K, V>, Serializable, Cloneable {

	private static final long serialVersionUID = 3801124242820219131L;

	private final Map<K, List<V>> targetMap;

	/**
	 * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap}.
	 */
	public LinkedMultiValueMap() {
		this.targetMap = new LinkedHashMap<>();
	}

	/**
	 * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap} with the
	 * given initial capacity.
	 * 
	 * @param initialCapacity the initial capacity
	 */
	public LinkedMultiValueMap(int initialCapacity) {
		this.targetMap = new LinkedHashMap<>(initialCapacity);
	}

	/**
	 * Copy constructor: Create a new LinkedMultiValueMap with the same mappings as
	 * the specified Map. Note that this will be a shallow copy; its value-holding
	 * List entries will get reused and therefore cannot get modified independently.
	 * 
	 * @param otherMap the Map whose mappings are to be placed in this Map
	 * @see #clone()
	 * @see #deepCopy()
	 */
	public LinkedMultiValueMap(Map<K, List<V>> otherMap) {
		this.targetMap = new LinkedHashMap<>(otherMap);
	}

	// MultiValueMap implementation

	/**
	 * //无论values存在不（不存在就新建一个），都加进去
	 * 
	 * @param key   the key
	 * @param value the value to be added
	 * @see AbstractMultiValueMap#put(Object, List)
	 */
	@Override
	public void add(K key, V value) {
		List<V> vList = this.targetMap.get(key);

		if (null == vList) {
			vList = new LinkedList<>();
			this.targetMap.put(key, vList);
		}
		vList.add(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(K key, V value, V... values) {
		List<V> vList = this.targetMap.get(key);

		if (null == vList) {
			vList = new LinkedList<>();
			this.targetMap.put(key, vList);
		}
		// 添加value
		vList.add(value);
		// 添加values
		if (null != values && values.length > 0) {
			vList.addAll(Arrays.asList(values));
		}
	}

	@Override
	public void addFirst(K key, V value) {
		List<V> vList = this.targetMap.get(key);

		if (null == vList) {
			vList = new LinkedList<>();
			this.targetMap.put(key, vList);
		}
		// 添加value到第一个位置
		vList.add(0, value);
	}

	@Override
	public Map<K, V> toSingleValueMap() {
		LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<>(this.targetMap.size());
		for (Entry<K, List<V>> entry : this.targetMap.entrySet()) {
			singleValueMap.put(entry.getKey(), entry.getValue().get(0));
		}
		return singleValueMap;
	}

	@Override
	public Map<K, List<V>> getMap() {
		return this.targetMap;
	}

	/**
	 * Create a deep copy of this Map.
	 * 
	 * @return a copy of this Map, including a copy of each value-holding List entry
	 * @since 4.2
	 * @see #clone()
	 */
	public MultiValueMap<K, V> deepCopy() {
		LinkedMultiValueMap<K, V> copy = new LinkedMultiValueMap<>(this.targetMap.size());
		for (Map.Entry<K, List<V>> entry : this.targetMap.entrySet()) {
			copy.put(entry.getKey(), new LinkedList<>(entry.getValue()));
		}
		return copy;
	}

	/**
	 * Create a regular copy of this Map.
	 * 
	 * @return a shallow copy of this Map, reusing this Map's value-holding List
	 *         entries
	 * @since 4.2
	 * @see LinkedMultiValueMap#LinkedMultiValueMap(Map)
	 * @see #deepCopy()
	 */
	@Override
	public LinkedMultiValueMap<K, V> clone() {
		return new LinkedMultiValueMap<>(this);
	}

	/**
	 * 从普通map转换而来
	 */
	public static <K1, V1> MultiValueMap<K1, V1> fromMap(Map<K1, V1> map) {
		if (null == map) {
			return null;
		}
		MultiValueMap<K1, V1> mapList = new LinkedMultiValueMap<>(map.size());
		map.forEach(mapList::add);
		return mapList;
	}
}
