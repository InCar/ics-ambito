package com.incarcloud.ics.core.security;

import com.incarcloud.ics.core.access.Accessor;
import com.incarcloud.ics.core.access.DefaultAccessor;
import com.incarcloud.ics.core.authc.*;
import com.incarcloud.ics.core.authz.Authorizer;
import com.incarcloud.ics.core.authz.DefaultAuthorizer;
import com.incarcloud.ics.core.cache.CacheManager;
import com.incarcloud.ics.core.cache.LocalCacheManager;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.exception.InvalidSessionException;
import com.incarcloud.ics.core.exception.SessionException;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.realm.CacheRealm;
import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.servlet.AmbitoHttpServletRequest;
import com.incarcloud.ics.core.session.*;
import com.incarcloud.ics.core.subject.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class DefaultSecurityManager implements SecurityManager {

    private Logger logger = Logger.getLogger(DefaultSecurityManager.class.getName());
    private Authorizer authorizer;
    private Authenticator authenticator;
    private Accessor accessor;
    private SessionManager sessionManager;
    private List<Realm> realms;
    private CacheManager cacheManager;
    private SubjectDAO subjectDao;
    private SubjectFactory subjectFactory;

    public DefaultSecurityManager() {
        this.cacheManager = new LocalCacheManager();
        this.sessionManager = new DefaultWebSessionManager();
        this.subjectDao = new DefaultSubjectDAO();
        this.subjectFactory = new DefaultSubjectFactory();
    }

    public DefaultSecurityManager(Realm realm) {
        this.cacheManager = new LocalCacheManager();
        List<Realm> realms = new ArrayList<>();
        realms.add(realm);
        this.setRealms(realms);
        this.sessionManager = new DefaultWebSessionManager();
        this.subjectDao = new DefaultSubjectDAO();
        this.subjectFactory = new DefaultSubjectFactory();
        this.authorizer = new DefaultAuthorizer(realm);
        this.accessor = new DefaultAccessor(realm);
        this.authenticator = new DefaultAuthenticator(realm);
    }

    @Override
    public Subject login(AuthenticateToken authenticateToken, Subject subject) throws AuthenticationException {
        AuthenticateInfo authenticate = authenticator.authenticate(authenticateToken);
        return createSubject(authenticate, authenticateToken, subject);
    }

    @Override
    public void logout(Subject subject) {
        beforeLogout(subject);
        try {
            Principal principal = subject.getPrincipal();
            if(principal != null){
                List<Realm> realms = getRealms();
                for(Realm realm : realms){
                    if(realm instanceof LogoutAware){
                        ((LogoutAware)realm).onLogout(principal);
                    }
                }
            }
            deleteSubject(subject);
        }catch (Exception e){
            if (logger.isLoggable(Level.FINE)) {
                java.lang.String msg = "Unable to cleanly unbind Subject.  Ignoring (logging out).";
                logger.fine(msg);
            }
        }finally {
            try {
                stopSession(subject);
            }catch (Exception e){
                if (logger.isLoggable(Level.FINE)) {
                    java.lang.String msg = "Unable to cleanly stop Session for Subject [" + subject.getPrincipal() + "] " +
                            "Ignoring (logging out).";
                    logger.fine(msg);
                }
            }

        }
    }


    private void beforeLogout(Subject subject) {
        ServletRequest request = subject.getServletRequest();
        if (request != null) {
            request.setAttribute(AmbitoHttpServletRequest.IDENTITY_REMOVED_KEY, Boolean.TRUE);
        }
    }

    private void deleteSubject(Subject subject) {
        Session session = subject.getSession(false);
        if (session != null) {
            session.removeAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            session.removeAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        }
    }

    protected void stopSession(Subject subject) {
        Session s = subject.getSession(false);
        if (s != null) {
            s.stop();
        }
    }


    /**
     * 构造登录成功的subject
     * @param account
     * @param authenticateToken
     * @param existing
     * @return
     */
    public Subject createSubject(AuthenticateInfo account, AuthenticateToken authenticateToken, Subject existing) {
        SubjectContext context = new DefaultSubjectContext();
        context.setAuthenticateToken(authenticateToken);
        context.setAuthenticateInfo(account);
        context.setIsAuthenticated(true);
        if(existing != null){
            context.setSubject(existing);
            context.setServletRequest(existing.getServletRequest());
            context.setServletResponse(existing.getServletResponse());
        }
        return createSubject(context);
    }

    @Override
    public Subject createSubject(SubjectContext subjectContext){
        SubjectContext context = new DefaultSubjectContext(subjectContext);

        //ensure that the context has a SecurityManager instance, and if not, add one:
        context = ensureSecurityManager(context);

        //Resolve an associated Session (usually based on a referenced session ID), and place it in the context before
        //sending to the SubjectFactory.  The SubjectFactory should not need to know how to acquire sessions as the
        //process is often environment specific - better to shield the SF from these details:
        context = resolveSession(context);

        //Similarly, the SubjectFactory should not require any concept of RememberMe - translate that here first
        //if possible before handing off to the SubjectFactory:
        context = resolvePrincipals(context);

        Subject subject = doCreateSubject(context);

        //save this subject for future reference if necessary:
        //(this is needed here in case rememberMe principals were resolved and they need to be stored in the
        //session, so we don't constantly rehydrate the rememberMe PrincipalCollection on every operation).
        //Added in 1.2:
        save(subject);

        return subject;
    }

    @Override
    public boolean isHttpSessionMode() {
        SessionManager sessionManager = this.getSessionManager();
        return sessionManager.isServletContainerSessions();
    }

    private void save(Subject subject) {
        subjectDao.save(subject);
    }

    private Subject doCreateSubject(SubjectContext context) {
        return this.getSubjectFactory().createSubject(context);
    }

    protected SubjectContext resolvePrincipals(SubjectContext context) {
        Principal principals = context.resolvePrincipals();
        if (principals == null) {
            logger.fine("No identity (Principal) found in the context");
        }else {
            context.setPrincipal(principals);
        }
        return context;
    }

    private SubjectContext resolveSession(SubjectContext context) {
        if (context.resolveSession() != null) {
            logger.fine("Context already contains a session.  Returning.");
            return context;
        }
        try {
            Session session = resolveContextSession(context);
            if (session != null) {
                context.setSession(session);
            }
        } catch (SessionException e) {
            logger.fine("Resolved SubjectContext context session is invalid.  Ignoring and creating an anonymous " +
                    "(session-less) Subject instance.");
        }
        return context;
    }

    protected Session resolveContextSession(SubjectContext context) throws InvalidSessionException {
        SessionKey key = getSessionKey(context);
        if (key != null) {
            return getSession(key);
        }
        return null;
    }

    protected SessionKey getSessionKey(SubjectContext context) {
        Serializable sessionId = context.getSessionId();
        ServletRequest request = context.getServletRequest();
        ServletResponse response = context.getServletResponse();
        return new WebSessionKey(sessionId, request, response);
    }

    public Session getSession(SessionKey key) throws SessionException {
        return this.sessionManager.getSession(key);
    }

    @Override
    public boolean isServletContainerSessions() {
        return this.sessionManager.isServletContainerSessions();
    }

    protected SubjectContext ensureSecurityManager(SubjectContext context) {
        if (context.resolveSecurityManager() != null) {
            logger.fine("Context already contains a SecurityManager instance.  Returning.");
            return context;
        }
        logger.fine("No SecurityManager found in context.  Adding self reference.");
        context.setSecurityManager(this);
        return context;
    }

    @Override
    public AuthenticateInfo authenticate(AuthenticateToken authenticateToken) {
        return authenticator.authenticate(authenticateToken);
    }

    @Override
    public boolean isPermitted(Principal principal, Privilege privilege) {
        return authorizer.isPermitted(principal, privilege);
    }

    @Override
    public boolean isPermittedAllObjectPrivileges(Principal principal, Collection<Privilege> privileges) {
        return authorizer.isPermittedAllObjectPrivileges(principal, privileges);
    }

    @Override
    public boolean isPermittedAllStringPrivileges(Principal principal, Collection<String> privileges) {
        return authorizer.isPermittedAllStringPrivileges(principal, privileges);
    }

    @Override
    public boolean hasRole(Principal principal, String role) {
        return authorizer.hasRole(principal, role);
    }

    @Override
    public boolean hasAllRoles(Principal principal, Collection<String> roleList) {
        return authorizer.hasAllRoles(principal, roleList);
    }

    @Override
    public Session start(SessionContext sessionContext) {
        return sessionManager.start(sessionContext);
    }

    public Authorizer getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(Authorizer authorizer) {
        this.authorizer = authorizer;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }


    @Override
    public boolean isAccessibleForData(Principal principal, Serializable id, Class<?> clzz) {
        return accessor.isAccessibleForData(principal, id, clzz);
    }

    @Override
    public Collection<String> getFilterCodes(Principal principal, Class<?> aClass) {
        return accessor.getFilterCodes(principal, aClass);
    }

    public List<Realm> getRealms() {
        return realms;
    }

    public void setRealms(List<Realm> realms) {
        this.realms = realms;
        afterRealmSet();
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    protected void afterRealmSet(){
        applyCacheManagerToRealms();
    }

    protected void applyCacheManagerToRealms() {
        CacheManager cacheManager = getCacheManager();
        List<Realm> realms = getRealms();
        if(cacheManager != null && realms != null) {
            for (Realm realm : realms) {
                if (realm instanceof CacheRealm && ((CacheRealm) realm).isCacheEnabled()) {
                    ((CacheRealm) realm).setCacheManager(this.getCacheManager());
                }
            }
        }
    }

    public SubjectFactory getSubjectFactory() {
        return subjectFactory;
    }

    public Accessor getAccessor() {
        return accessor;
    }

    public void setAccessor(Accessor accessor) {
        this.accessor = accessor;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public SubjectDAO getSubjectDao() {
        return subjectDao;
    }

    public void setSubjectDao(SubjectDAO subjectDao) {
        this.subjectDao = subjectDao;
    }

    public void setSubjectFactory(SubjectFactory subjectFactory) {
        this.subjectFactory = subjectFactory;
    }
}
