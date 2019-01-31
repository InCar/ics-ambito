package com.incarcloud.ics.core.aspect.intercepter;

import com.incarcloud.ics.core.aspect.handler.RoleAnnotationHandler;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public class RoleAnnotationMethodInterceptor extends SecurityAnnotationMethodInterceptor {

    public RoleAnnotationMethodInterceptor() {
        super(new RoleAnnotationHandler());
    }
}
