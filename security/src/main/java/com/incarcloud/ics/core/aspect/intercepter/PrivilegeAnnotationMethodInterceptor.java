package com.incarcloud.ics.core.aspect.intercepter;

import com.incarcloud.ics.core.aspect.handler.PrivilegeAnnotationHandler;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public class PrivilegeAnnotationMethodInterceptor extends SecurityAnnotationMethodInterceptor {
    public PrivilegeAnnotationMethodInterceptor() {
        super(new PrivilegeAnnotationHandler());
    }
}
