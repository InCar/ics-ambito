package com.incarcloud.ics.core.session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;


public class WebSessionKey implements SessionKey, ServletPairSource {

    private Serializable sessionId;
    private ServletResponse servletResponse;
    private ServletRequest servletRequest;

    public WebSessionKey() {
    }

    public WebSessionKey(Serializable sessionId) {
        this(sessionId, null, null);
    }

    public WebSessionKey(Serializable sessionId, ServletRequest servletRequest, ServletResponse servletResponse) {
        this.sessionId = sessionId;
        this.servletResponse = servletResponse;
        this.servletRequest = servletRequest;
    }

    public void setSessionId(Serializable sessionId) {
        this.sessionId = sessionId;
    }

    public void setServletResponse(ServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    public void setServletRequest(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @Override
    public Serializable getSessionId() {
        return sessionId;
    }

    @Override
    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    @Override
    public ServletResponse getServletResponse() {
        return servletResponse;
    }
}
