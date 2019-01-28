package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.StringUtils;
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
        Subject subject = this.getSubject(request, response);
        if (subject.getPrincipal() == null) {
            return false;
//            this.saveRequestAndRedirectToLogin(request, response);
        } else {
            String unauthorizedUrl = this.getUnauthorizedUrl();
            if (StringUtils.isNotBlank(unauthorizedUrl)) {
//                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(401);
            }
        }

        return false;
    }
}
