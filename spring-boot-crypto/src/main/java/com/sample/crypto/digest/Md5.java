package com.sample.crypto.digest;

import java.security.MessageDigest;

import com.sample.crypto.generator.Crypto;
import com.sample.crypto.generator.CryptoException;

/**
 * MD5摘要
 */
public class Md5 extends AbstractDigestCrypto implements Crypto {
	@Override
	public byte[] encrypt(byte[] src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return md5.digest(src);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}
}
