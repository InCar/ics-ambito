package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/25
 */
public abstract class AccessControllerFilter extends PathMatcherFilter {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }

    protected abstract boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception;


    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return onAccessDenied(request, response);
    }

    protected abstract boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception;

    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return isAccessAllowed(request, response, mappedValue) || onAccessDenied(request, response, mappedValue);
    }
}
