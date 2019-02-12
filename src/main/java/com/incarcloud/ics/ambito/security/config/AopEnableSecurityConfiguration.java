package com.incarcloud.ics.ambito.security.config;

import com.incarcloud.ics.core.aspect.advisor.SecurityAnnotationMethodAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;

/**
 * @description
 *  该类继承�? {@link DefaultSecurityConfiguration}，用于开启注解方式的权限验证，直接�?�过注解如@RequiresRoles对方法进行拦截并验证权限�?
 *  拦截机制使用了spring的aop，需要开启注解权限验证，继承该配置类即可，配置方式和{@link DefaultSecurityConfiguration}相同
 * @version 1.0
 * @author ThomasChan
 * @date 2019/1/31
 */
public abstract class AopEnableSecurityConfiguration extends DefaultSecurityConfiguration {

    @Bean
    protected SecurityAnnotationMethodAdvisor securityAnnotationMethodAdvisor(){
        return new SecurityAnnotationMethodAdvisor();
    }

    @Bean
    protected DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
}
