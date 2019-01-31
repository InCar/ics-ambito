package com.incarcloud.ics.ambito.config;

import com.incarcloud.ics.ambito.security.DatabaseSessionDAO;
import com.incarcloud.ics.ambito.security.JdbcRealm;
import com.incarcloud.ics.core.aspect.advisor.SecurityAnnotationMethodAdvisor;
import com.incarcloud.ics.core.filter.AbstractAmbitoFilter;
import com.incarcloud.ics.core.filter.FilterFactoryBean;
import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.security.DefaultSecurityManager;
import com.incarcloud.ics.core.security.SecurityManager;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.session.DefaultWebSessionManager;
import com.incarcloud.ics.core.session.SessionDAO;
import com.incarcloud.ics.core.session.SessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
@Configuration
public class SecurityBeanConfiguration {

    public SecurityBeanConfiguration() {
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager(realm());
        securityManager.setSessionManager(sessionManager());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public Realm realm(){
        return new JdbcRealm();
    }

    @Bean
    public FilterFactoryBean filterFactoryBean(){
        FilterFactoryBean filterFactoryBean = new FilterFactoryBean();
        Map<String,String> filterChainDefinitions = new LinkedHashMap<>();
        filterChainDefinitions.put("/ics/user/login", "anon");
        filterChainDefinitions.put("/ics/**", "authc,roles[abc]");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        filterFactoryBean.setSecurityManager(securityManager());
        return filterFactoryBean;
    }

    @Bean
    public AbstractAmbitoFilter filter(){
        return (AbstractAmbitoFilter) filterFactoryBean().getObject();
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDao(sessionDAO());
        return defaultWebSessionManager;
    }

    @Bean
    public SessionDAO sessionDAO(){
        return new DatabaseSessionDAO();
    }

    @Bean
    public SecurityAnnotationMethodAdvisor securityAnnotationMethodAdvisor(){
        return new SecurityAnnotationMethodAdvisor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

}

