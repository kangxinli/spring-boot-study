package com.sample.crypto.generator;

/**
 * 对于那些需要设置key的
 * 
 * @see Crypto
 */
public interface KeyCrypto extends Crypto {
	/**
	 * 设置key,并返回自己
	 * 
	 * @param key key
	 * @return this
	 */
	KeyCrypto setKey(String key);
}