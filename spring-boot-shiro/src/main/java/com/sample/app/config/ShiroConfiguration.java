package com.sample.app.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import com.sample.app.shiro.JWTFilter;
import com.sample.app.shiro.MyRealm;
import com.sample.app.shiro.ShrioRedisCacheManager;


@Configuration
public class ShiroConfiguration {
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		
		// Shiro的核心安全接口
		shiroFilter.setSecurityManager(securityManager);
		
		shiroFilter.setLoginUrl("/login");			// 登录页面配置
		shiroFilter.setUnauthorizedUrl("/401");// 没有权限默认跳转的页面
		

        /**
         * anon---------------org.apache.shiro.web.filter.authc.AnonymousFilter 没有参数，表示可以匿名使用。
         * authc--------------org.apache.shiro.web.filter.authc.FormAuthenticationFilter 表示需要认证(登录)才能使用，没有参数
         * authcBasic---------org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter 没有参数表示httpBasic认证
         * logout-------------org.apache.shiro.web.filter.authc.LogoutFilter
         * noSessionCreation--org.apache.shiro.web.filter.session.NoSessionCreationFilter
         * perms--------------org.apache.shiro.web.filter.authz.PermissionAuthorizationFilter 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
         * port---------------org.apache.shiro.web.filter.authz.PortFilter port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
         * rest---------------org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter 根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
         * roles--------------org.apache.shiro.web.filter.authz.RolesAuthorizationFilter 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
         * ssl----------------org.apache.shiro.web.filter.authz.SslFilter 没有参数，表示安全的url请求，协议为https
         * user---------------org.apache.shiro.web.filter.authz.UserFilter 没有参数表示必须存在用户，当登入操作时不做检查
         */
 
        /**
         * 通常可将这些过滤器分为两组
         * anon,authc,authcBasic,user是第一组认证过滤器
         * perms,port,rest,roles,ssl是第二组授权过滤器
         * 注意user和authc不同：当应用开启了rememberMe时,用户下次访问时可以是一个user,但绝不会是authc,因为authc是需要重新认证的
         * user表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe 说白了,以前的一个用户登录时开启了rememberMe,然后他关闭浏览器,下次再访问时他就是一个user,而不会authc
         *
         *
         * 举几个例子
         *  /admin=authc,roles[admin]      表示用户必需已通过认证,并拥有admin角色才可以正常发起'/admin'请求
         *  /edit=authc,perms[admin:edit]  表示用户必需已通过认证,并拥有admin:edit权限才可以正常发起'/edit'请求
         *  /home=user     表示用户不一定需要已经通过认证,只需要曾经被Shiro记住过登录状态就可以正常发起'/home'请求
         */
 
 
        /**
         * 各默认过滤器常用如下(注意URL Pattern里用到的是两颗星,这样才能实现任意层次的全匹配)
         * /admins/**=anon             无参,表示可匿名使用,可以理解为匿名用户或游客
         *  /admins/user/**=authc       无参,表示需认证才能使用
         *  /admins/user/**=authcBasic  无参,表示httpBasic认证
         *  /admins/user/**=ssl         无参,表示安全的URL请求,协议为https
         *  /admins/user/**=perms[user:add:*]  参数可写多个,多参时必须加上引号,且参数之间用逗号分割,如/admins/user/**=perms["user:add:*,user:modify:*"]。当有多个参数时必须每个参数都通过才算通过,相当于isPermitedAll()方法
         *  /admins/user/**=port[8081] 当请求的URL端口不是8081时,跳转到schemal://serverName:8081?queryString。其中schmal是协议http或https等,serverName是你访问的Host,8081是Port端口,queryString是你访问的URL里的?后面的参数
         *  /admins/user/**=rest[user] 根据请求的方法,相当于/admins/user/**=perms[user:method],其中method为post,get,delete等
         *  /admins/user/**=roles[admin]  参数可写多个,多个时必须加上引号,且参数之间用逗号分割,如：/admins/user/**=roles["admin,guest"]。当有多个参数时必须每个参数都通过才算通过,相当于hasAllRoles()方法
         *
         */

		// 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTFilter());
        shiroFilter.setFilters(filterMap);

		Map<String, String> chains = new HashMap<>();
		chains.put("/druid/**", "anon");
		chains.put("/login", "anon");
		chains.put("/401", "anon");
		chains.put("/logout", "logout");
		chains.put("/base/**", "anon");
		chains.put("/css/**", "anon");
		chains.put("/layer/**", "anon");
		// 所有请求通过我们自己的JWT Filter
		chains.put("/**", "jwt");
        // 访问401和404页面不通过我们的Filter
		chains.put("/401", "anon");
		shiroFilter.setFilterChainDefinitionMap(chains);
		return shiroFilter;
	}
	
	/**
	 * 权限管理器
	 * 
	 * @return
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(MyRealm realm, RedisTemplate<byte[], byte[]> template) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		
		realm.setCachingEnabled(true);
		realm.setAuthenticationCachingEnabled(false); //禁用认证缓存
		realm.setAuthorizationCachingEnabled(true);
		// 数据库认证的实现
		manager.setRealm(realm);
		// session 管理器
		manager.setSessionManager(sessionManager());
		// 缓存管理器
		manager.setCacheManager(redisCacheManager(template));
		return manager;
	}
	
	/**
	 * DefaultWebSessionManager
	 * 
	 * @return
	 */
	@Bean(name = "sessionManager")
	public ServletContainerSessionManager sessionManager() {
		ServletContainerSessionManager sessionManager = new ServletContainerSessionManager();
		return sessionManager;
	}

	@Bean(name = "shrioRedisCacheManager")
	@DependsOn(value = "redisTemplate")
	public ShrioRedisCacheManager redisCacheManager(RedisTemplate<byte[],byte[]> template) {
		ShrioRedisCacheManager cacheManager = new ShrioRedisCacheManager(template);
		cacheManager.createCache("shiro_redis:");
		return cacheManager;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
