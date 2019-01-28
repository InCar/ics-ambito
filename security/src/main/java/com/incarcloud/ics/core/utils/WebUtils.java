package com.incarcloud.ics.core.utils;


import com.incarcloud.ics.core.session.ServletPairSource;
import com.incarcloud.ics.core.session.SessionContext;
import com.incarcloud.ics.core.session.SessionKey;
import com.incarcloud.ics.core.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {

    public static ServletRequest getRequest(SessionKey sessionKey){
        ServletRequest servletRequest = null;
        if(sessionKey instanceof ServletPairSource){
            servletRequest = ((ServletPairSource)sessionKey).getServletRequest();
        }
        return servletRequest;
    }

    public static ServletResponse getResponse(SessionKey sessionKey){
        ServletResponse servletResponse = null;
        if(sessionKey instanceof ServletPairSource){
            servletResponse = ((ServletPairSource)sessionKey).getServletResponse();
        }
        return servletResponse;
    }

    public static HttpServletRequest getHttpRequest(SessionContext sessionContext) {
        if(sessionContext != null){
            ServletRequest servletRequest = sessionContext.getServletRequest();
            if(servletRequest instanceof HttpServletRequest){
                return (HttpServletRequest) servletRequest;
            }
        }
        return null;
    }

    public static HttpServletRequest getHttpRequest(Subject subject) {
        if(subject != null){
            ServletRequest servletRequest = subject.getServletRequest();
            if(servletRequest instanceof HttpServletRequest){
                return (HttpServletRequest) servletRequest;
            }
        }
        return null;
    }

    public static HttpServletResponse getHttpResponse(Subject subject) {
        if(subject != null){
            ServletResponse servletResponse = subject.getServletResponse();
            if(servletResponse instanceof HttpServletResponse){
                return (HttpServletResponse) servletResponse;
            }
        }
        return null;
    }


    public static HttpServletResponse getHttpResponse(SessionContext sessionContext) {
        if(sessionContext != null){
            ServletResponse servletResponse = sessionContext.getServletResponse();
            if(servletResponse instanceof HttpServletResponse){
                return (HttpServletResponse) servletResponse;
            }
        }
        return null;
    }


    public static HttpServletRequest getHttpRequest(SessionKey sessionKey) {
        ServletRequest request = getRequest(sessionKey);
        if(request instanceof HttpServletRequest){
            return (HttpServletRequest) request;
        }
        return null;
    }

    public static HttpServletResponse getHttpResponse(SessionKey sessionKey) {
        ServletResponse response = getResponse(sessionKey);
        if(response instanceof HttpServletResponse){
            return (HttpServletResponse) response;
        }
        return null;
    }


    public static HttpServletResponse toHttp(ServletResponse response) {
        if(response instanceof HttpServletResponse){
            return (HttpServletResponse)response;
        }
        return null;
    }
}
