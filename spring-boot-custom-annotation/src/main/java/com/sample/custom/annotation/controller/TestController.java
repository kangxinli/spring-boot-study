package com.sample.custom.annotation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.custom.annotation.annotation.History;
import com.sample.custom.annotation.entity.HistoryBo;

@RestController
public class TestController {
	
	/**
     * aop测试
     * @param bo
     * @param request
     * @param response
     */
    @PostMapping("/aopTest")
    @History
    public HistoryBo aopTest(@RequestBody HistoryBo bo, HttpServletRequest request, HttpServletResponse response){

        System.out.println("程序进入 aopTest");

        return bo;
    }

}
