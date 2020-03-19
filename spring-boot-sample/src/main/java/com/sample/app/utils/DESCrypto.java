package com.sample.app.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESCrypto {
	
	final static String keyString = "edream12";
	
	//final SecretKeySpec key = new SecretKeySpec("edream".getBytes(StandardCharsets.UTF_8),"AES");
	//final IvParameterSpec iv = new IvParameterSpec("edream".getBytes(StandardCharsets.UTF_8));
	
	//Properties properties = new Properties();
	//properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.OPENSSL.getClassName());
	//Creates a CryptoCipher instance with the transformation and properties.
	//final String transform = "AES/CBC/PKCS5Padding";

	public static String getDecryptString(String data){
		String result = decrypt(data,keyString);
		return result;
	}
	
	public static String getEncryptString(String data){
		String result = encrypt(data,keyString);
		return result;
	}
	
    private static byte[] encryptProcess(String message, String key) throws Exception { 
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); 
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8")); 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec); 
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8")); 
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv); 
        return cipher.doFinal(message.getBytes("UTF-8")); 
    }
    
    private static String decryptProcess(String message,String key) throws Exception { 
            byte[] bytesrc =convertHexString(message); 
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); 
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8")); 
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec); 
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8")); 
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv); 
            byte[] retByte = cipher.doFinal(bytesrc); 
            return new String(retByte); 
    }
    
    private static byte[] convertHexString(String ss) throws Exception { 
        byte digest[] = new byte[ss.length() / 2]; 
        for(int i = 0; i < digest.length; i++) 
        { 
        String byteString = ss.substring(2 * i, 2 * i + 2); 
        int byteValue = Integer.parseInt(byteString, 16); 
        digest[i] = (byte)byteValue; 
        } 
        return digest; 
    }
    
    private static String toHexString(byte b[]) throws Exception { 
        StringBuffer hexString = new StringBuffer(); 
        for (int i = 0; i < b.length; i++) { 
            String plainText = Integer.toHexString(0xff & b[i]); 
            if (plainText.length() < 2) 
                plainText = "0" + plainText; 
            hexString.append(plainText); 
        } 
           
        return hexString.toString(); 
    }
    
    public static String encrypt(String message,String key){
        String enStr = null;
        try {
             String orignStr=java.net.URLEncoder.encode(message, "utf-8"); 
             enStr=toHexString(encryptProcess(orignStr, key)); 
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return enStr;
    }
    
    public static String decrypt(String message,String key){
        String decStr = null;
        try {
            decStr = java.net.URLDecoder.decode(decryptProcess(message,key), "utf-8") ;
        }catch (Exception e) {
        	e.printStackTrace();
        }
        return decStr;
    }
    
    public static void main(String[] args) {
		String data = "root";
		System.out.println(getEncryptString(data));
	}
}
