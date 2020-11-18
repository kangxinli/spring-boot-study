package com.sample.crypto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sample.crypto.generator.Crypto;
import com.sample.crypto.symmetric.AesCrypto;

/**
 * Request-Response加解密体系的加解密方式
 */
@Configuration
public class RRCryptoConfig {
    /**
     * 加密解密方式使用一样的
     */
    @Bean("rrCrypto")
    public Crypto rrCrypto(){
        return new AesCrypto("lee");
    }

    public static void main(String[] args) {
        Crypto crypto = new AesCrypto("lee");
        System.out.println(crypto.encrypt("lee"));
        System.out.println(crypto.decrypt("ZqRHBnlJ/NNV01zjeloIbw=="));
    }
}