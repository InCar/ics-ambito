package com.incarcloud.ics.core.aspect.intercepter;

import com.incarcloud.ics.core.aspect.MethodInvocation;
import com.incarcloud.ics.core.utils.CollectionUtils;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public class AopAnnotationsMethodInterceptor extends AnnotationsMethodInterceptor implements MethodInterceptor {

    private Logger log = Logger.getLogger(AopAnnotationsMethodInterceptor.class.getName());

    private MethodInvocation proxy(org.aopalliance.intercept.MethodInvocation aopMethodInvocation){

        return new MethodInvocation() {
            @Override
            public Object proceed() throws Throwable {
                return aopMethodInvocation.proceed();
            }

            @Override
            public Method getMethod() {
                return aopMethodInvocation.getMethod();
            }

            @Override
            public Object[] getArguments() {
                return aopMethodInvocation.getArguments();
            }

            @Override
            public Object getThis() {
                return aopMethodInvocation.getThis();
            }
        };
    }

    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation methodInvocation) throws Throwable {
        MethodInvocation proxy = proxy(methodInvocation);
        return super.invoke(proxy);
    }



}
