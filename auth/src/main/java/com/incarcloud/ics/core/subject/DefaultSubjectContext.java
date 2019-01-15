package com.incarcloud.ics.core.subject;

import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.security.SecurityManager;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.exception.UnavailableSecurityManagerException;
import com.incarcloud.ics.core.session.Session;
import com.incarcloud.ics.core.utils.MapContext;
import com.incarcloud.ics.core.security.SecurityUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultSubjectContext extends MapContext implements SubjectContext {
    private Logger logger = Logger.getLogger(DefaultSubjectContext.class.getName());
    private static final long serialVersionUID = 4228303924423812100L;
    private static final String SESSION = DefaultSubjectContext.class.getName() + ".SESSION";
    private static final String IS_AUTHENTICATED = DefaultSubjectContext.class.getName() + ".IS_AUTHENTICATED";
    private static final String SECURITY_MANAGER = DefaultSubjectContext.class.getName() + ".SECURITY_MANAGER";
    private static final String PRINCIPAL = DefaultSubjectContext.class.getName() + ".PRINCIPAL";
    private static final String SUBJECT = DefaultSubjectContext.class.getName() + ".SUBJECT";
    private static final String AUTHENTICATE_TOKEN = DefaultSubjectContext.class.getName() + ".AUTHENTICATE_TOKEN";
    private static final String ACCOUNT_INFO = DefaultSubjectContext.class.getName() + ".ACCOUNT_INFO";
    private static final String SESSION_ID = DefaultSubjectContext.class.getName() + ".SESSION_ID";
    private static final String HOST = DefaultSubjectContext.class.getName() + ".HOST";
    private static final String SERVLET_REQUEST = DefaultSubjectContext.class.getName() + ".SERVLET_REQUEST";
    private static final String SERVLET_RESPONSE = DefaultSubjectContext.class.getName() + ".SERVLET_RESPONSE";

    /**
     * 该sessionKey用于保存subject的认证状态
     */
    public static final String AUTHENTICATED_SESSION_KEY = DefaultSubjectContext.class.getName() + "_AUTHENTICATED_SESSION_KEY";

    /**
     * 该sessionKey用于保存subject的身份信息
     */
    public static final String PRINCIPALS_SESSION_KEY = DefaultSubjectContext.class.getName() + "_PRINCIPALS_SESSION_KEY";

    public DefaultSubjectContext() {
    }

    public DefaultSubjectContext(SubjectContext subjectContext) {
        super(subjectContext);
    }

    @Override
    public String getHost() {
        return getTypedValue(HOST, String.class);
    }

    @Override
    public void setHost(String host) {
        super.nullSafePut(HOST, host);
    }

    @Override
    public Session getSession() {
        return getTypedValue(SESSION, Session.class);
    }

    @Override
    public void setSession(Session session) {
        super.nullSafePut(SESSION, session);
    }

    @Override
    public Serializable getSessionId() {
        return getTypedValue(SESSION_ID, Serializable.class);
    }

    @Override
    public void setSessionId(Serializable sessionId) {
        super.nullSafePut(SESSION_ID, sessionId);
    }

    @Override
    public boolean isAuthenticated() {
        Boolean isAuthenticated = getTypedValue(IS_AUTHENTICATED, Boolean.class);
        return  isAuthenticated != null && isAuthenticated;
    }

    @Override
    public void setIsAuthenticated(Boolean isAuthenticated) {
        super.nullSafePut(IS_AUTHENTICATED, isAuthenticated);
    }

    @Override
    public SecurityManager getSecurityManager() {
        return getTypedValue(SECURITY_MANAGER, SecurityManager.class);
    }

    @Override
    public void setSecurityManager(SecurityManager securityManager) {
        super.nullSafePut(SECURITY_MANAGER, securityManager);
    }

    @Override
    public Principal getPrincipal() {
        return getTypedValue(PRINCIPAL, Principal.class);
    }

    @Override
    public void setPrincipal(Principal principal) {
        super.nullSafePut(PRINCIPAL, principal);
    }

    @Override
    public Subject getSubject() {
        return getTypedValue(SUBJECT, Subject.class);
    }

    @Override
    public void setSubject(Subject subject) {
        super.nullSafePut(SUBJECT, subject);
    }

    @Override
    public AuthenticateToken getAuthenticateToken() {
        return getTypedValue(AUTHENTICATE_TOKEN, AuthenticateToken.class);
    }

    @Override
    public void setAuthenticateToken(AuthenticateToken authenticateToken) {
        super.nullSafePut(AUTHENTICATE_TOKEN, authenticateToken);
    }

    @Override
    public Account getAccountInfo() {
        return getTypedValue(ACCOUNT_INFO, Account.class);
    }

    @Override
    public void setAccountInfo(Account accountInfo) {
        super.nullSafePut(ACCOUNT_INFO, accountInfo);
    }

    @Override
    public void setServletRequest(ServletRequest servletRequest) {
        super.nullSafePut(SERVLET_REQUEST, servletRequest);
    }

    @Override
    public void setServletResponse(ServletResponse servletResponse) {
        super.nullSafePut(SERVLET_RESPONSE, servletResponse);
    }

    @Override
    public ServletRequest getServletRequest() {
        return getTypedValue(SERVLET_REQUEST, ServletRequest.class);
    }

    @Override
    public ServletResponse getServletResponse() {
        return getTypedValue(SERVLET_RESPONSE, ServletResponse.class);
    }

    public SecurityManager resolveSecurityManager() {
        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            try {
                securityManager = SecurityUtils.getSecurityManager();
            } catch (UnavailableSecurityManagerException e) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("No SecurityManager available via SecurityUtils.  Heuristics exhausted. " );
                }
            }
        }
        return securityManager;
    }

    @Override
    public Session resolveSession() {
        Session session = getSession();
        if(session == null){
            Subject existingSubject = getSubject();
            if(existingSubject != null){
                session = existingSubject.getSession(false);
            }
        }
        return session;
    }

    public Principal resolvePrincipals() {
        Principal principals = getPrincipal();
        if (principals == null) {
            //check to see if they were just authenticated:
            Account info = getAccountInfo();
            if (info != null) {
                principals = info.getPrincipal();
            }
        }

        if (principals == null) {
            Subject subject = getSubject();
            if (subject != null) {
                principals = subject.getPrincipal();
            }
        }

        if (principals == null) {
            //try the session:
            Session session = resolveSession();
            if (session != null) {
                principals = (Principal) session.getAttribute(PRINCIPALS_SESSION_KEY);
            }
        }

        return principals;
    }

    @Override
    public boolean resolveAuthenticated() {
        Boolean authc = getTypedValue(IS_AUTHENTICATED, Boolean.class);
        if (authc == null) {
            //see if there is an AuthenticationInfo object.  If so, the very presence of one indicates a successful
            //authentication attempt:
            Account info = getAccountInfo();
            authc = info != null;
        }
        if (!authc) {
            //fall back to a session check:
            Session session = resolveSession();
            if (session != null) {
                Boolean sessionAuthc = (Boolean) session.getAttribute(AUTHENTICATED_SESSION_KEY);
                authc = sessionAuthc != null && sessionAuthc;
            }
        }

        return authc;
    }

    @Override
    public ServletRequest resoleveServletRequest() {
        ServletRequest request = getServletRequest();
        if(request == null){
            Subject existing = this.getSubject();
            if(existing != null){
                request = existing.getServletRequest();
            }
        }
        return request;
    }

    @Override
    public ServletResponse resoleveServletResponse() {
        ServletResponse response = getServletResponse();
        if(response == null){
            Subject existing = this.getSubject();
            if(existing != null){
                response = existing.getServletResponse();
            }
        }
        return response;
    }

    @Override
    public String resolveHost() {
        String host = getHost();
        if (host == null) {
            ServletRequest request = resoleveServletRequest();
            if (request != null) {
                host = request.getRemoteHost();
            }
        }
        return host;
    }

}
