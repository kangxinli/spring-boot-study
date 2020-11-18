package com.sample.crypto.asymmetric;

/**
 * 非对称算法类型<br>
 * 
 *
 */
public enum AsymmetricAlgorithm {
	/**
	 * RSA
	 */
	RSA("RSA"), @Deprecated
	DSA("DSA");

	private String value;

	private AsymmetricAlgorithm(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
