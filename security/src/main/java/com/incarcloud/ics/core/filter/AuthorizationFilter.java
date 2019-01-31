package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.exception.UnAuthorizeException;
import com.incarcloud.ics.core.handler.SimpleFilterExceptionHandlerFactory;
import com.incarcloud.ics.core.utils.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public abstract class AuthorizationFilter extends AccessControllerFilter {
    private String unauthorizedUrl;

    public AuthorizationFilter() {
    }

    public String getUnauthorizedUrl() {
        return this.unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        SimpleFilterExceptionHandlerFactory.getInstance().newHandler(UnAuthorizeException.class)
                .handle(WebUtils.toHttp(request), WebUtils.toHttp(response));
        return false;
    }
}
