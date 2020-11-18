package com.sample.crypto.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import com.sample.crypto.generator.Crypto;
import com.sample.crypto.utils.IoUtil;

public class DecryptHttpInputMessage implements HttpInputMessage {
    private HttpInputMessage inputMessage;
    private String charset;
    private Crypto crypto;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String charset , Crypto crypto) {
        this.inputMessage = inputMessage;
        this.charset = charset;
        this.crypto = crypto;
    }

    @Override
    public InputStream getBody() throws IOException {
        String content = IoUtil.read(inputMessage.getBody() , charset);

        String decryptBody = crypto.decrypt(content, charset);

        return new ByteArrayInputStream(decryptBody.getBytes(charset));
    }

    @Override
    public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
    }
}