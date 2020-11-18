package com.sample.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.sample.crypto.asymmetric.AsymmetricAlgorithm;
import com.sample.crypto.asymmetric.RSA;
import com.sample.crypto.digest.DigestAlgorithm;
import com.sample.crypto.digest.Digester;
import com.sample.crypto.digest.HMac;
import com.sample.crypto.digest.HmacAlgorithm;
import com.sample.crypto.digest.Md5;
import com.sample.crypto.generator.CompositeCrypto;
import com.sample.crypto.generator.Crypto;
import com.sample.crypto.generator.CryptoException;
import com.sample.crypto.generator.KeyGenerator;
import com.sample.crypto.symmetric.AES;
import com.sample.crypto.symmetric.AesCrypto;
import com.sample.crypto.symmetric.Base64;
import com.sample.crypto.symmetric.Base64Crypto;
import com.sample.crypto.symmetric.Base64OptCrypto;
import com.sample.crypto.symmetric.DES;
import com.sample.crypto.symmetric.DESAS;
import com.sample.crypto.symmetric.DesCrypto;
import com.sample.crypto.symmetric.UrlCrypto;
import com.sample.crypto.utils.IoUtil;
import com.sample.crypto.utils.RadixUtil;

public class CryptoTest {
	@Test
	public void testMd5() throws Exception {
		String src = "abc";
		Assert.assertEquals("900150983CD24FB0D6963F7D28E17F72", new Md5().encrypt(src));
	}

	@Test
	public void testDes() {
		String src = "abc";
		// key must be 8 bytes long
		Crypto crypto = new DesCrypto("xxssyyyy");
		final String xx = crypto.encrypt(src);
		Assert.assertEquals("6A0EC53A7B87BF1A", xx);
		Assert.assertEquals(src, crypto.decrypt(xx));

		final String yy = crypto.encrypt(src, "UTF-8");
		Assert.assertEquals("6A0EC53A7B87BF1A", yy);
		Assert.assertEquals(src, crypto.decrypt(yy));

	}

	@Test
	public void testBase64() throws Exception {
		String src = "abc";
		Crypto crypto = new Base64Crypto();
		final String xx = crypto.encrypt(src).trim();
		Assert.assertEquals("YWJj", xx);
		Assert.assertEquals(src, crypto.decrypt(xx));

		String yy = Base64.encode(src);

		Assert.assertEquals("YWJj", yy);
		Assert.assertEquals(src, crypto.decrypt(yy));
		Assert.assertEquals(src, Base64.decodeStr(yy));

		crypto = new Base64OptCrypto(false, true);
		String encrypt = crypto.encrypt(src);
		Assert.assertEquals("YWJj", encrypt);
		Assert.assertEquals(src, crypto.decrypt(encrypt));
	}

	@Test
	public void testUrl() {
		String src = "abc";
		Crypto crypto = new UrlCrypto();
		final String xx = crypto.encrypt(src);
		Assert.assertEquals("abc", xx);
		Assert.assertEquals(src, crypto.decrypt(xx));

	}

	@Test
	public void testAes() {
		String src = "abc";
		Crypto crypto = new AesCrypto("123456");
		final String xx = crypto.encrypt(src);
		Assert.assertEquals("MMfuoKJ+QjoPxBgY3aNBBg==", xx);
		Assert.assertEquals(src, crypto.decrypt(xx));

	}

	@Test
	public void testDESAS() {
		String src = "abc";
		Crypto crypto = new DESAS("xxssyyyy");
		final String xx = crypto.encrypt(src);
		Assert.assertEquals("ceaab832691e3e0a", xx);
		Assert.assertEquals(src, crypto.decrypt(xx));

	}

	@Test
	public void testComposite() throws Exception {
		String src = "abc";
		Crypto base64Crypto = new Base64Crypto();
		Crypto desCrypto = new DesCrypto("xxssyyyy");

		CompositeCrypto compositeCrypto = new CompositeCrypto();
		// 这个就能很好地表达先进行什么加密再进行什么加密，自动逆序解密
		compositeCrypto.add(base64Crypto).add(desCrypto).add(base64Crypto);

		final String xx = compositeCrypto.encrypt(src);
		Assert.assertEquals("MzEzRkY2Qzg0NEYxQzhENA==", xx);
		Assert.assertEquals(src, compositeCrypto.decrypt(xx));

	}

	/**
	 * 测试整数的二进制表示
	 */
	@Test
	public void testBinary() {
		int a = -6;
		for (int i = 0; i < 32; i++) {
			int t = (a & 0x80000000 >>> i) >>> (31 - i);
			System.out.print(t);
		}
	}

	@Test
	public void testAesHutool() throws Exception {
		String src = "abc";
		Crypto crypto = new AES();
		String xx = crypto.encrypt(src);
		System.out.println(xx);// 因为key是随机的，所以每次不一样
		Assert.assertEquals(src, crypto.decrypt(xx));

		crypto = new AES(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 9, 9, 9, 9 });
		String yy = crypto.encrypt(src);
		Assert.assertEquals("B508FECBB7815B3FEA5FF07C76E9CF36", yy);
		Assert.assertEquals(src, crypto.decrypt(yy));
	}

	@Test
	public void testDesHutool() throws Exception {
		String src = "abc";
		Crypto crypto = new DES();
		String xx = crypto.encrypt(src);
		System.out.println(xx);
		Assert.assertEquals(src, crypto.decrypt(xx));

		crypto = new DES(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 9, 9, 9, 9 });
		String yy = crypto.encrypt(src);
		Assert.assertEquals("2A4A100F650BD672", yy);
		Assert.assertEquals(src, crypto.decrypt(yy));
	}

	@Test
	public void testRsa() throws Exception {
		String src = "abc";
		// 应该是产生之后就保存下来
		KeyPair keyPair = KeyGenerator.generateKeyPair(AsymmetricAlgorithm.RSA.getValue());
		Crypto crypto = new RSA(keyPair.getPrivate(), keyPair.getPublic());
		String encrypt = crypto.encrypt(src);
		System.out.println(encrypt);
		System.out.println(crypto.decrypt(encrypt));
	}

	@Test(expected = CryptoException.class)
	public void testDigester() throws Exception {
		String src = "abc";
		Crypto crypto = new Digester(DigestAlgorithm.SHA256);
		String xx = crypto.encrypt(src);
		// System.out.println(xx);
		Assert.assertEquals("BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD", xx);
		System.out.println(crypto.decrypt(xx));
	}

	@Test(expected = CryptoException.class)
	public void testHmac() throws Exception {
		String src = "abc";
		Crypto crypto = new HMac(HmacAlgorithm.HmacSHA256, new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		String xx = crypto.encrypt(src);
		// System.out.println(xx);
		Assert.assertEquals("446D1715583CF1C30DFFFBEC0DF4FF1F9D39D493211AB4C97ED6F3F0EB579B47", xx);
		System.out.println(crypto.decrypt(xx));
	}

	@Test
	@Ignore
	public void testStream() throws Exception {
		Crypto crypto = new AES(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 });
		FileInputStream in = new FileInputStream("C:\\Users\\dell\\Desktop\\1.txt");// "abc" -> 1.txt
		FileOutputStream out = new FileOutputStream("C:\\Users\\dell\\Desktop\\2.bat");
		crypto.encrypt(in, out);

		String encrypt = crypto.encrypt("abc");
		Assert.assertEquals("E4B1ED894580A0784BE84812EA68314A", encrypt);
		FileInputStream outin = new FileInputStream("C:\\Users\\dell\\Desktop\\2.bat");
		String hex = RadixUtil.toHex(IoUtil.stream2Bytes(outin));
		Assert.assertEquals(encrypt, hex);

		in.close();
		out.close();
		outin.close();
	}

	/**
	 * 测试整数的二进制表示
	 */
	@Test
	public void testHex() throws Exception {
		byte[] bytes = "abc".getBytes("UTF-8");
		Assert.assertEquals("616263", RadixUtil.toHex(bytes));
		Assert.assertEquals("616263", RadixUtil.toHexLower(bytes));
	}
}
