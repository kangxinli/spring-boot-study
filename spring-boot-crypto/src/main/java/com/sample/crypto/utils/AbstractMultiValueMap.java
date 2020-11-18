package com.sample.crypto.utils;

import java.util.*;

/**
 * 一些基本操作
 * 
 * @since 1.8.2.3
 */
public abstract class AbstractMultiValueMap<K, V> implements MultiValueMap<K, V> {
	@Override
	public V getFirst(K key) {
		List<V> values = getMap().get(key);
		return (values != null ? values.get(0) : null);
	}

	@Override
	public V getLast(K key) {
		List<V> values = getMap().get(key);
		return (values != null ? values.get(values.size() - 1) : null);
	}

	/**
	 * 覆盖性的
	 * 
	 * @param key   the key
	 * @param value the value to set
	 */
	@Override
	public void set(K key, V value) {
		List<V> values = new ArrayList<>(1);
		values.add(value);
		getMap().put(key, values);
	}

	@Override
	public void setAll(Map<K, V> values) {
		for (Entry<K, V> entry : values.entrySet()) {
			set(entry.getKey(), entry.getValue());
		}
	}

	// Map implementation

	@Override
	public int size() {
		return getMap().size();
	}

	@Override
	public boolean isEmpty() {
		return getMap().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return getMap().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return getMap().containsValue(value);
	}

	@Override
	public List<V> get(Object key) {
		return getMap().get(key);
	}

	@Override
	public List<V> put(K key, List<V> value) {
		return getMap().put(key, value);
	}

	@Override
	public List<V> remove(Object key) {
		return getMap().remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends List<V>> map) {
		getMap().putAll(map);
	}

	@Override
	public void clear() {
		getMap().clear();
	}

	@Override
	public Set<K> keySet() {
		return getMap().keySet();
	}

	@Override
	public Collection<List<V>> values() {
		return getMap().values();
	}

	@Override
	public Set<Entry<K, List<V>>> entrySet() {
		return getMap().entrySet();
	}

	@Override
	public boolean equals(Object obj) {
		return getMap().equals(obj);
	}

	@Override
	public int hashCode() {
		return getMap().hashCode();
	}

	@Override
	public String toString() {
		return getMap().toString();
	}
}
