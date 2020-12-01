package com.sample.multi.ds.mybatisplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sample.multi.ds.mybatisplus.entity.User;
import com.sample.multi.ds.mybatisplus.service.IUser2Service;
import com.sample.multi.ds.mybatisplus.service.IUserService;

@RestController
public class UserController {
    private final IUserService userService;
    private final IUser2Service user2Service;

    @Autowired
    public UserController(IUserService userService, IUser2Service user2Service) {
        this.userService = userService;
        this.user2Service = user2Service;
    }
    
    @GetMapping("/test")
    public void test() {
    	System.out.println("总数1 : " + userService.count());
    	System.out.println("总数2 : " + user2Service.count());
    }

    /**
     * 查询列表
     */
    @GetMapping("/")
    public List<User> list() {
        return userService.list();
    }

    /**
     * 查询单条记录
     */
    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * 清除数据
     */
    @GetMapping("/clean")
    public String clean() {
        userService.remove(null);
        return "success";
    }
}
