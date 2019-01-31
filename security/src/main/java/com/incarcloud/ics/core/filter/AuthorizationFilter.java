package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.exception.UnAuthorizeException;
import com.incarcloud.ics.core.handler.HttpSecurityExceptionHandler;
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
        HttpSecurityExceptionHandler.getInstance()
                .handle(WebUtils.toHttp(response), new UnAuthorizeException());
        return false;
    }
}
