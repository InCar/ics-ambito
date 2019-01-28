package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/25
 */
public class AuthenticationFilter extends  AccessControllerFilter{
    public static final String DEFAULT_SUCCESS_URL = "/";
    private String successUrl = "/";

    public AuthenticationFilter() {
    }

    public String getSuccessUrl() {
        return this.successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = this.getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception{
        writeUnauthenticatedInfo(response);
        return false;
    }

    private void writeUnauthenticatedInfo(ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write("{\"result\":false,\"message\":\"Unauthenticated!\",\"code\":\"401\"}");
        writer.flush();
        writer.close();
    }
}
