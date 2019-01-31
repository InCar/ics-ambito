package com.incarcloud.ics.core.aspect.intercepter;


import com.incarcloud.ics.core.aspect.MethodInvocation;
import com.incarcloud.ics.core.aspect.handler.AnnotationHandler;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.Asserts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public abstract class SecurityAnnotationMethodInterceptor implements MethodInterceptor {

    protected AnnotationHandler handler;

    public SecurityAnnotationMethodInterceptor(AnnotationHandler handler) {
        Asserts.assertNotNull(handler, "handler");
        this.handler = handler;
    }

    public AnnotationHandler getHandler() {
        return handler;
    }

    public void setHandler(AnnotationHandler handler) {
        this.handler = handler;
    }


    protected Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    protected Annotation resolveAnnotation(MethodInvocation mi){
        return doResolveAnnotation(mi,getHandler().getAnnotationClass());
    }

    protected Annotation doResolveAnnotation(MethodInvocation mi, Class<? extends Annotation> clazz) {
        if (mi == null) {
            throw new IllegalArgumentException("method argument cannot be null");
        } else {
            Method m = mi.getMethod();
            if (m == null) {
                String msg = MethodInvocation.class.getName() + " parameter incorrectly constructed.  getMethod() returned null";
                throw new IllegalArgumentException(msg);
            } else {
                Annotation annotation = m.getAnnotation(clazz);
                if (annotation == null) {
                    Object miThis = mi.getThis();
                    annotation = miThis != null ? miThis.getClass().getAnnotation(clazz) : null;
                }

                return annotation;
            }
        }
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        assertAuthAccess(methodInvocation);
        return methodInvocation.proceed();
    }


    protected void assertAuthAccess(MethodInvocation methodInvocation) throws SecurityException{
        Annotation annotation = resolveAnnotation(methodInvocation);
        getHandler().assertAuthMatch(annotation);
    }

    protected boolean supports(MethodInvocation methodInvocation){
        return resolveAnnotation(methodInvocation) != null;
    }
}
