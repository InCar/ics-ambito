package com.incarcloud.ics.ambito.security.config;

import com.incarcloud.ics.core.aspect.advisor.SecurityAnnotationMethodAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;

/**
 * @description
 *  该类继承自 {@link DefaultSecurityConfiguration}，用于开启注解方式的权限验证，
 *  直接通过注解如{@link com.incarcloud.ics.core.aspect.anno.RequiresRoles}
 *  {@link com.incarcloud.ics.core.aspect.anno.RequiresPrivileges}等，对方法进行拦截并验证权限，
 *  拦截机制使用了spring的aop，需要开启注解权限验证，继承该配置类即可,
 *  配置方式和{@link DefaultSecurityConfiguration}相同
 * @version 1.0
 * @author ThomasChan
 * @date 2019/1/31
 */
public abstract class AopEnabledSecurityConfiguration extends DefaultSecurityConfiguration {

    @Bean
    protected SecurityAnnotationMethodAdvisor securityAnnotationMethodAdvisor(){
        return new SecurityAnnotationMethodAdvisor();
    }



    @Bean
    protected DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //true表示基于类的代理，依赖于cglib库，false表示基于接口进行代理，
        //依赖于jdk的动态代理，此处需要开启
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
