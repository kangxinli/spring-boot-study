package com.sample.app.common.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sample.app.common.shiro.SessionFilter;
import com.sample.app.common.shiro.ShiroSecurityRealm;


@Configuration
public class ShiroConfiguration {
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		
		// Shiro的核心安全接口
		shiroFilter.setSecurityManager(securityManager);
		
		// 添加自己的过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("sessionFilter", new SessionFilter());
        shiroFilter.setFilters(filterMap);

		Map<String, String> chains = new HashMap<>();
		chains.put("/assets/**", "anon");
		chains.put("/plugin/**", "anon");
		chains.put("/js/**", "anon");
		chains.put("/css/**", "anon");
		chains.put("/img/**", "anon");
		chains.put("/login/*", "anon");
		// 访问401和404页面不通过我们的Filter
		chains.put("/401", "anon");
		// 所有请求通过我们自己的Filter
		chains.put("/**", "sessionFilter");
        
		shiroFilter.setFilterChainDefinitionMap(chains);
		return shiroFilter;
	}
	
	/**
	 * 权限管理器
	 * 
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(ShiroSecurityRealm realm
//			, RedisTemplate<byte[], byte[]> template
			) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		
		realm.setCachingEnabled(true);
		realm.setAuthenticationCachingEnabled(false); //禁用认证缓存
		realm.setAuthorizationCachingEnabled(true);
		// 数据库认证的实现
		manager.setRealm(realm);
		// session 管理器
		manager.setSessionManager(sessionManager());
		// 缓存管理器
		// manager.setCacheManager(redisCacheManager(template));
		return manager;
	}
	
	/**
	 * DefaultWebSessionManager
	 * 
	 * @return
	 */
	@Bean
	public ServletContainerSessionManager sessionManager() {
		ServletContainerSessionManager sessionManager = new ServletContainerSessionManager();
		return sessionManager;
	}

	/**
	@Bean(name = "shrioRedisCacheManager")
	@DependsOn(value = "redisTemplate")
	public ShrioRedisCacheManager redisCacheManager(RedisTemplate<byte[],byte[]> template) {
		ShrioRedisCacheManager cacheManager = new ShrioRedisCacheManager(template);
		cacheManager.createCache("shiro_redis:");
		return cacheManager;
	}
	*/

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
