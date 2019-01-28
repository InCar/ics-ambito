package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.utils.MapContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/8
 */
public class DefaultSessionContext extends MapContext implements SessionContext {

    private static final long serialVersionUID = -5117896880337338522L;
    private static final String HOST = DefaultSessionContext.class.getName() + ".HOST";
    private static final String SESSION_ID = DefaultSessionContext.class.getName() + ".SESSION_ID";
    private static final String SERVLET_REQUEST = DefaultSessionContext.class.getName() + ".SERVLET_REQUEST";
    private static final String SERVLET_RESPONSE = DefaultSessionContext.class.getName() + ".SERVLET_RESPONSE";

    public DefaultSessionContext(ServletRequest servletRequest, ServletResponse servletResponse) {
        this.setServletRequest(servletRequest);
        this.setServletResponse(servletResponse);
    }

    public DefaultSessionContext(SessionContext map) {
        super(map);
    }


    @Override
    public String getHost() {
        return getTypedValue(HOST, String.class);
    }

    public void setHost(String host) {
        super.nullSafePut(HOST, host);
    }

    @Override
    public Serializable getSessionId() {
        return getTypedValue(SESSION_ID, Serializable.class);
    }

    @Override
    public void setSessionId(Serializable sessionId) {
        super.nullSafePut(HOST, sessionId);
    }

    public void setServletRequest(ServletRequest request) {
        if (request != null) {
            put(SERVLET_REQUEST, request);
        }
    }

    public ServletRequest getServletRequest() {
        return getTypedValue(SERVLET_REQUEST, ServletRequest.class);
    }

    public void setServletResponse(ServletResponse response) {
        if (response != null) {
            put(SERVLET_RESPONSE, response);
        }
    }

    public ServletResponse getServletResponse() {
        return getTypedValue(SERVLET_RESPONSE, ServletResponse.class);
    }

}
