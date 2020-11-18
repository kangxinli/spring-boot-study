package com.sample.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.crypto.generator.Crypto;
import com.sample.crypto.request.DecryptRequest;
import com.sample.crypto.request.EncryptResponse;

@RestController
@RequestMapping("/test")
@DecryptRequest
@EncryptResponse
public class TestController {
    @Autowired
    @Qualifier("rrCrypto")
    private Crypto crypto;
 
    @DecryptRequest(true)
    @EncryptResponse(true)
    @RequestMapping(value = "/enc" , method = RequestMethod.POST)
    public String enc(@RequestBody String body){
    	System.out.println(body);
        return crypto.encrypt(body);
    }
}
