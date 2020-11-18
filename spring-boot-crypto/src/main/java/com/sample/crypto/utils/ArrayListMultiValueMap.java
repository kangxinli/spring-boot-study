package com.sample.crypto.utils;

import java.io.Serializable;
import java.util.*;

/**
 * Simple implementation of {@link MultiValueMap} that wraps a {@link HashMap},
 * storing multiple values in a {@link ArrayList}.
 *
 * <p>
 * This Map implementation is generally not thread-safe. It is primarily
 * designed for data structures exposed from request objects, for use in a
 * single thread only.
 *
 */
public class ArrayListMultiValueMap<K, V> extends AbstractMultiValueMap<K, V>
		implements MultiValueMap<K, V>, Serializable, Cloneable {

	private static final long serialVersionUID = 3801124242820219131L;

	private final Map<K, List<V>> targetMap;

	/**
	 * Create a new LinkedMultiValueMap that wraps a {@link HashMap}.
	 */
	public ArrayListMultiValueMap() {
		this.targetMap = new HashMap<>();
	}

	/**
	 * Create a new LinkedMultiValueMap that wraps a {@link HashMap} with the given
	 * initial capacity.
	 * 
	 * @param initialCapacity the initial capacity
	 */
	public ArrayListMultiValueMap(int initialCapacity) {
		this.targetMap = new HashMap<>(initialCapacity);
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
	public ArrayListMultiValueMap(Map<K, List<V>> otherMap) {
		this.targetMap = new HashMap<>(otherMap);
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
			vList = new ArrayList<>(1);
			this.targetMap.put(key, vList);
		}

		vList.add(value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void add(K key, V value, V... values) {
		List<V> vList = this.targetMap.get(key);

		if (null == vList) {
			vList = new ArrayList<>(1);
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
			vList = new ArrayList<>(1);
			this.targetMap.put(key, vList);
		}
		// 添加value到第一个位置
		vList.add(0, value);
	}

	@Override
	public Map<K, V> toSingleValueMap() {
		Map<K, List<V>> targetMap = getMap();
		Map<K, V> singleValueMap = new HashMap<>(targetMap.size());
		for (Entry<K, List<V>> entry : targetMap.entrySet()) {
			singleValueMap.put(entry.getKey(), entry.getValue().get(0));
		}
		return singleValueMap;
	}

	@Override
	public Map<K, List<V>> getMap() {
		return this.targetMap;
	}

	public MultiValueMap<K, V> deepCopy() {
		ArrayListMultiValueMap<K, V> copy = new ArrayListMultiValueMap<>(this.targetMap.size());
		for (Entry<K, List<V>> entry : this.targetMap.entrySet()) {
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
	 * @see ArrayListMultiValueMap#ArrayListMultiValueMap() (Map)
	 * @see #deepCopy()
	 */
	@Override
	public ArrayListMultiValueMap<K, V> clone() {
		return new ArrayListMultiValueMap<>(this);
	}

	/**
	 * 从普通map转换而来
	 */
	public static <K1, V1> MultiValueMap<K1, V1> fromMap(Map<K1, V1> map) {
		if (null == map) {
			return null;
		}
		MultiValueMap<K1, V1> mapList = new ArrayListMultiValueMap<>(map.size());
		map.forEach(mapList::add);
		return mapList;
	}
}
