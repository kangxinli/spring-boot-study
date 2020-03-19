package com.sample.app.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sample.app.common.constant.ReturnCode;
import com.sample.app.common.domain.ResponseWrapper;
import com.sample.app.common.utils.MD5Utils;

@Service
public class LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	/**
	 * 登录校验
	 * @param userName 用户名
	 * @param password 密码
	 * @param companyCode 登录代码
	 * @return
	 */
	public ResponseWrapper checkLogin(String userName, String password) {
		
		try {
			// 验证用户名密码
			UsernamePasswordToken token = null;
			if (userName != null && !userName.equals("") && password != null && !password.equals("")) {
				// shiro验证登录
				Subject subject = SecurityUtils.getSubject();
				token = new UsernamePasswordToken(userName, MD5Utils.MD5(password));
				// token = new DefaultUsernamepasswordToken(userName, MD5Utils.MD5(password), companyCode);
				subject.login(token);
				if (subject.isAuthenticated()) {// 验证成功
					// Session session = subject.getSession();
					// User user = (User) session.getAttribute("currentUser");

					return ResponseWrapper.markSuccess(null);
				} 
			}
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			logger.error("login failed. UnknownAccountException : " + "_" + e.getMessage(), e);
		} catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			logger.error("login failed. IncorrectCredentialsException : " + "_" + e.getMessage(), e);
		} catch (ExcessiveAttemptsException e) {
			e.printStackTrace();
			logger.error("login failed. ExcessiveAttemptsException : " + "_" + e.getMessage(), e);
		}
		// 用户名/密码错误
		return ResponseWrapper.markCustom(false, ReturnCode.LOGIN_FAIL.getCode(), "用户名或密码错误", null);
	}
}
