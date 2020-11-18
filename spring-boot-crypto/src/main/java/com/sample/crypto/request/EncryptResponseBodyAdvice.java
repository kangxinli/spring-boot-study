package com.sample.crypto.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.sample.crypto.generator.Crypto;


/**
 * 请求响应处理类<br>
 * 
 * 对加了@Encrypt的方法的数据进行加密操作
 * 
 *
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Value("${spring.crypto.request.decrypt.charset:UTF-8}")
    private String charset = "UTF-8";

    @Autowired
    @Qualifier("rrCrypto")
    private Crypto crypto;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return NeedCrypto.needEncrypt(returnType);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //TODO 实现具体的加密方法

        return body;
	}

}