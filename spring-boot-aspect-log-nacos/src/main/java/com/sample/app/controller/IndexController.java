package com.sample.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.service.LoginService;

@RestController
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    @Autowired
    LoginService loginService;
 
    @GetMapping("index")
    public Object index() {
        logger.info("进入了index方法");
        logger.info("开始执行业务逻辑");
        logger.info("index方法结束");
 
        return "success";
    }
 
    @PostMapping("login")
    public Object login() {
        logger.info("进入了login方法");
 
        loginService.login();
 
        logger.info("登录成功");
 
        return "success";
    }
}
