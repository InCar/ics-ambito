package com.incarcloud.ics.core.aspect.advisor;

import com.incarcloud.ics.core.aspect.anno.RequiresAccessible;
import com.incarcloud.ics.core.aspect.anno.RequiresAuthenticated;
import com.incarcloud.ics.core.aspect.anno.RequiresPrivileges;
import com.incarcloud.ics.core.aspect.anno.RequiresRoles;
import com.incarcloud.ics.core.aspect.intercepter.AopAnnotationsMethodInterceptor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class SecurityAnnotationMethodAdvisor extends StaticMethodMatcherPointcutAdvisor {

    @SuppressWarnings("unchecked")
    private static final Class<? extends Annotation>[] AUTHZ_ANNOTATION_CLASSES =
            new Class[] {
                    RequiresAuthenticated.class, RequiresRoles.class,
                    RequiresPrivileges.class, RequiresAccessible.class
            };

    public SecurityAnnotationMethodAdvisor() {
        setAdvice(new AopAnnotationsMethodInterceptor());
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Method m = method;

        if ( isAuthzAnnotationPresent(m) ) {
            return true;
        }

        //The 'method' parameter could be from an interface that doesn't have the annotation.
        //Check to see if the implementation has it.
        if ( targetClass != null) {
            try {
                m = targetClass.getMethod(m.getName(), m.getParameterTypes());
                return isAuthzAnnotationPresent(m) || isAuthzAnnotationPresent(targetClass);
            } catch (NoSuchMethodException ignored) {
                //default return value is false.  If we can't find the method, then obviously
                //there is no annotation, so just use the default return value.
            }
        }
        return false;
    }

    private boolean isAuthzAnnotationPresent(Class<?> targetClazz) {
        for( Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES ) {
            Annotation a = AnnotationUtils.findAnnotation(targetClazz, annClass);
            if ( a != null ) {
                return true;
            }
        }
        return false;
    }

    private boolean isAuthzAnnotationPresent(Method method) {
        for( Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES ) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if ( a != null ) {
                return true;
            }
        }
        return false;
    }
}
