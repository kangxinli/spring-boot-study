package com.sample.app.common.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sample.app.dao.UserMapper;
import com.sample.app.entity.User;

@Configuration
public class ShiroSecurityRealm extends AuthorizingRealm {
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 获取授权/提供账户信息返回认证信息（登录验证）
	 * 
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		
		// 1.从主体传过来的认证信息中，获得用户名
		String username = (String) authcToken.getPrincipal();
		// 2.通过用户名到数据库中获取凭证
		User example = new User();
		example.setUserName(username);
		
		List<User> list = userMapper.select(example); 
		if (list.size() == 0) {
			return null;
		}
		User user = (User) list.get(0);
		if (user != null) {
			getMenuTreeByUserId(user);// 获取权限
			return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 用于获取认证成功后的角色、权限等信息
	 * 
	 * @param principalCollection
	 * @return
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		// 用户信息
		User user = (User) session.getAttribute("currentUser");
		// 根据用户获取该用户角色和所有权限
		if (user != null) {
			// 权限
			return info;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取树形菜单
	 * 
	 * @param id
	 *            当前菜单id
	 * @param rootMenu
	 *            所有 有权限的菜单数据
	 * @return
	 */
	public void getMenuTreeByUserId(User currentUser) {
		// 将信息放在session中
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		session.setAttribute("currentUser", currentUser);// 当前用户
	}

}
