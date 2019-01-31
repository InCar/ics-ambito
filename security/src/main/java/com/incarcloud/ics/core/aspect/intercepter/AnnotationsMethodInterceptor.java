package com.incarcloud.ics.core.aspect.intercepter;

import com.incarcloud.ics.core.aspect.MethodInvocation;
import com.incarcloud.ics.core.utils.CollectionUtils;


import java.util.Collection;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public abstract class AnnotationsMethodInterceptor implements MethodInterceptor {

    public List<SecurityAnnotationMethodInterceptor> interceptors;

    public AnnotationsMethodInterceptor() {
        this.interceptors = CollectionUtils.asUnmodifiableList(
                new AuthenticatedAnnotationMethodInterceptor(),
                new PrivilegeAnnotationMethodInterceptor(),
                new RoleAnnotationMethodInterceptor()
        );
    }

    public List<SecurityAnnotationMethodInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<SecurityAnnotationMethodInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        assertAuthAccess(methodInvocation);
        return methodInvocation.proceed();
    }

    protected void assertAuthAccess(MethodInvocation methodInvocation) throws SecurityException {
        Collection<SecurityAnnotationMethodInterceptor> interceptors = getInterceptors();
        if(CollectionUtils.isNotEmpty(interceptors)){
            for(SecurityAnnotationMethodInterceptor interceptor : interceptors){
                if(interceptor.supports(methodInvocation)){
                    interceptor.assertAuthAccess(methodInvocation);
                }
            }
        }
    }
}
