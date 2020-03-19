package com.sample.app.common.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.alibaba.fastjson.JSONObject;
import com.sample.app.common.constant.ReturnCode;
import com.sample.app.entity.User;


/**
 * session 过滤器
 *
 */
public class SessionFilter extends AuthorizationFilter {
	// 是否允许访问
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute("currentUser");
		if (user == null) {// 未登陆，返回false
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		
		System.out.println("未登陆，跳转到登陆页面");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", ReturnCode.UNAUTHORIZED.getCode());
		jsonObject.put("msg", ReturnCode.UNAUTHORIZED.getMsg());
		PrintWriter out = null;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json");
			out = response.getWriter();
			out.println(jsonObject);
		} catch (Exception e) {
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
		return false;
	}
}
