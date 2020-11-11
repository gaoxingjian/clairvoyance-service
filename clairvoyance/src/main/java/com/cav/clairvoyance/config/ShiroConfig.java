package com.cav.clairvoyance.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;


import com.cav.clairvoyance.shiro.CustomAccessControlFilter;
import com.cav.clairvoyance.shiro.CustomHashedCredentialsMatcher;
import com.cav.clairvoyance.shiro.CustomRealm;
import com.cav.clairvoyance.shiro.RedisCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * TODO:类文件简单描述
 * @Author: 小霍
 * @CreateDate: 2019/9/7 13:31
 * @UpdateUser: 小霍
 * @UpdateDate: 2019/9/7 13:31
 * @Version: 0.0.1
 */
@Configuration
public class ShiroConfig {

    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher() {
        return new CustomHashedCredentialsMatcher();
    }

    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        customRealm.setCacheManager(redisCacheManager());
        return customRealm;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }


    @Bean
    public SecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器限制并发人数,参考博客：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("token", new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/user/token", "anon");
        filterChainDefinitionMap.put("/user", "token,authc");
        filterChainDefinitionMap.put("/index/**", "anon");
        filterChainDefinitionMap.put("*.html", "anon");
        //放开swagger-ui地址
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

        filterChainDefinitionMap.put("/bootstrap/**", "anon");
        filterChainDefinitionMap.put("/bower_components/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/less/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        //filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/**", "anon");
        //filterChainDefinitionMap.put("/**", "token,authc");
        //filterChainDefinitionMap.put("/batch", "token,authc");
        //filterChainDefinitionMap.put("/batch", "anon");
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @throws
     * @Author: 小霍
     * @CreateDate: 2019/9/19 10:50
     * @UpdateUser:
     * @UpdateDate: 2019/9/19 10:50
     * @Version: 0.0.1
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
