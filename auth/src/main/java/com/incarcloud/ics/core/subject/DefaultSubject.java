package com.incarcloud.ics.core.subject;

import com.incarcloud.ics.core.Principle.Principal;
import com.incarcloud.ics.core.ambito.SecurityManager;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.exception.InvalidSessionException;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.role.Role;
import com.incarcloud.ics.core.session.DefaultSessionContext;
import com.incarcloud.ics.core.session.ProxiedSession;
import com.incarcloud.ics.core.session.Session;
import com.incarcloud.ics.core.session.SessionContext;
import com.incarcloud.ics.core.utils.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collection;
import java.util.logging.Logger;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class DefaultSubject implements Subject {

    private Logger logger = Logger.getLogger(DefaultSubject.class.getName());

    protected SecurityManager securityManager;
    protected Session session;
    protected boolean isAuthenticated;
    protected Principal principal;
    protected String host;
    private final ServletRequest servletRequest;
    private final ServletResponse servletResponse;

    public DefaultSubject(SecurityManager securityManager) {
        this(securityManager, null, null, false, null, null, null);
    }

    public DefaultSubject(SecurityManager securityManager, ServletRequest servletRequest, ServletResponse servletResponse) {
        this(securityManager, null, null, false, null, servletRequest, servletResponse);
    }

    public DefaultSubject(SecurityManager securityManager, Session session,String host, boolean isAuthenticated, Principal principal, ServletRequest servletRequest, ServletResponse servletResponse) {
        this.securityManager = securityManager;
        this.session = session;
        this.isAuthenticated = isAuthenticated;
        this.principal = principal;
        this.host = host;
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    public ServletResponse getServletResponse() {
        return servletResponse;
    }

    @Override
    public boolean isPermitted(Privilege privilege) {
        return securityManager.isPermitted(principal, privilege);
    }

    @Override
    public boolean isPermittedAll(Collection<Privilege> privileges) {
        return securityManager.isPermittedAll(principal, privileges);
    }

    @Override
    public boolean hasRole(Role role) {
        return securityManager.hasRole(principal, role);
    }

    @Override
    public boolean hasAllRoles(Collection<Role> roles) {
        return securityManager.hasAllRoles(principal, roles);
    }

    @Override
    public boolean isAccessiableForData(String dataId) {
        return securityManager.isAccessibleForData(principal, dataId);
    }

    @Override
    public Collection<String> getAccessiableOrg() {
        return securityManager.assessibleOrgCodes(principal);
    }

    @Override
    public void login(AuthenticateToken authenticateToken) {
        Subject subject = securityManager.login(authenticateToken, this);
        Principal principal = subject.getPrincipal();
        if(principal == null){
            throw new IllegalArgumentException("Principal return after login is null");
        }
        this.principal = principal;
        this.isAuthenticated = true;
        Session session = subject.getSession(false);
        if(session != null){
            this.session = new StoppingAwareSession(session, this);
        }else {
            this.session = null;
        }
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public Session getSession() {
        return getSession(true);
    }

    @Override
    public Session getSession(boolean create) {
        if(this.session == null && create){
            SessionContext sessionContext = createSessionContext();
            Session start = this.securityManager.start(sessionContext);
            this.session = new StoppingAwareSession(start, this);
        }
        return this.session;
    }

    private SessionContext createSessionContext() {
        SessionContext sessionContext = new DefaultSessionContext(this.getServletRequest(), this.getServletResponse());
        if (StringUtils.isNotBlank(sessionContext.getHost())) {
            sessionContext.setHost(sessionContext.getHost());
        }
        return sessionContext;
    }

    @Override
    public void logout() {
        try {
            securityManager.logout(this);
        }finally {
            this.session = null;
            this.isAuthenticated = false;
            this.principal = null;
        }

    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    private void sessionStop(){
        this.session = null;
    }

    private class StoppingAwareSession extends ProxiedSession {

        private final DefaultSubject owner;

        private StoppingAwareSession(Session target, DefaultSubject owningSubject) {
            super(target);
            owner = owningSubject;
        }

        public void stop() throws InvalidSessionException {
            super.stop();
            owner.sessionStop();
        }
    }
}
