package com.incarcloud.ics.core.subject;

import com.incarcloud.ics.core.authc.AuthenticateInfo;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.security.SecurityManager;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.session.Session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Map;

public interface SubjectContext  extends Map<String, Object> {

    String getHost();

    void setHost(String host);

    Session getSession();

    void setSession(Session session);

    Serializable getSessionId();

    void setSessionId(Serializable sessionId);

    boolean isAuthenticated();

    void setIsAuthenticated(Boolean isAuthenticated);

    SecurityManager getSecurityManager();

    void setSecurityManager(SecurityManager securityManager);

    Principal getPrincipal();

    void setPrincipal(Principal principal);

    Subject getSubject();

    void setSubject(Subject subject);

    AuthenticateToken getAuthenticateToken();

    void setAuthenticateToken(AuthenticateToken authenticateToken);

    AuthenticateInfo getAuthenticateInfo();

    void setAuthenticateInfo(AuthenticateInfo accountInfo);

    void setServletRequest(ServletRequest servletRequest);

    void setServletResponse(ServletResponse servletResponse);

    ServletRequest getServletRequest();

    ServletResponse getServletResponse();

    SecurityManager resolveSecurityManager();

    Session resolveSession();

    String resolveHost();

    Principal resolvePrincipals();

    boolean resolveAuthenticated();

    ServletRequest resoleveServletRequest();

    ServletResponse resoleveServletResponse();

}
