package com.incarcloud.ics.core.aspect.intercepter;

import com.incarcloud.ics.core.aspect.handler.AuthenticatedAnnotationHandler;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public class AuthenticatedAnnotationMethodInterceptor extends SecurityAnnotationMethodInterceptor {

    public AuthenticatedAnnotationMethodInterceptor() {
        super(new AuthenticatedAnnotationHandler());
    }
}
