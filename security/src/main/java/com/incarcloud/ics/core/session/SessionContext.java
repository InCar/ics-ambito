package com.incarcloud.ics.core.session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Map;

public interface SessionContext extends Map<String,Object> {

    String getHost();

    void setHost(String host);

    Serializable getSessionId();

    void setSessionId(Serializable sessionId);

    ServletRequest getServletRequest();

    void setServletRequest(ServletRequest request);

    ServletResponse getServletResponse();

    void setServletResponse(ServletResponse response);
}
