package com.sample.crypto.generator;

/**
 * 加密解密有任何问题抛出该异常
 */
public class CryptoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CryptoException(String message) {
		super(message);
	}

	public CryptoException(Exception e) {
		super(e);
	}
}
