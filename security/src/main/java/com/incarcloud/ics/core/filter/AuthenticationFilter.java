package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.exception.UnauthenticatedException;
import com.incarcloud.ics.core.handler.SimpleFilterExceptionHandlerFactory;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/25
 */
public class AuthenticationFilter extends  AccessControllerFilter{

    public AuthenticationFilter() {
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = this.getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception{
        SimpleFilterExceptionHandlerFactory
                .getInstance()
                .newHandler(UnauthenticatedException.class)
                .handle(WebUtils.toHttp(request), WebUtils.toHttp(response));
        return false;
    }
}
