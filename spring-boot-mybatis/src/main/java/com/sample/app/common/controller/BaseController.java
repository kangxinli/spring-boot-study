package com.sample.app.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.sample.app.entity.User;

public class BaseController {
    @Autowired
    private HttpServletRequest request;

    //获取当前登录用户
    public  User getCurrentUser() {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        return currentUser;
    }
}
