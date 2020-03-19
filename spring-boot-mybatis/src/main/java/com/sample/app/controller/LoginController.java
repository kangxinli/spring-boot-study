package com.sample.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.common.constant.ReturnCode;
import com.sample.app.common.controller.BaseController;
import com.sample.app.common.domain.ResponseWrapper;
import com.sample.app.service.LoginService;

/**
 * 登录
 * @author user
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录
	 *
	 * http://localhost:8080/login/checkLogin?username=admin&password=admin
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkLogin")
	public ResponseWrapper checkLoginShiroLogin(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) throws Exception {
		ResponseWrapper wrapper = new ResponseWrapper();
    	try {
    		wrapper = loginService.checkLogin(username, password);
    	} catch (Exception e) {
    		logger.error("登录失败： "  + "_" + e.getMessage(), e);
    		wrapper = ResponseWrapper.markCustom(false, ReturnCode.FAILED.getCode(), ReturnCode.FAILED.getMsg(), null);
    	}
    	return wrapper;
	}
	
}
