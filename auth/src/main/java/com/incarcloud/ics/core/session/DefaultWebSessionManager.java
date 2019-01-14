package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.cookie.Cookie;
import com.incarcloud.ics.core.cookie.SimpleCookie;
import com.incarcloud.ics.core.exception.AuthorizationException;
import com.incarcloud.ics.core.exception.UnknownSessionException;
import com.incarcloud.ics.core.servlet.AmbitoHttpServletRequest;
import com.incarcloud.ics.core.utils.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import static com.incarcloud.ics.core.servlet.AmbitoHttpServletRequest.REFERENCED_SESSION_ID_SOURCE;
import static com.incarcloud.ics.core.servlet.AmbitoHttpServletRequest.REFERENCED_SESSION_IS_NEW;


public class DefaultWebSessionManager extends AbstractValidateSessionManager implements WebSessionManager{

    protected SessionDAO sessionDao;
    private SessionFactory sessionFactory;
    private Cookie sessionIdCookie;
    private boolean isDeleteInvalidSessions;
    public DefaultWebSessionManager() {
        this.sessionIdCookie = new SimpleCookie(AmbitoHttpSession.DEFAULT_SESSION_ID_NAME);
        this.sessionDao = new MemorySessionDAO();
        this.sessionFactory = new SimpleSessionFactory();
        this.isDeleteInvalidSessions = true;
    }


    public Cookie getSessionIdCookie() {
        return sessionIdCookie;
    }

    public boolean isDeleteInvalidSessions() {
        return isDeleteInvalidSessions;
    }

    public void setDeleteInvalidSessions(boolean deleteInvalidSessions) {
        isDeleteInvalidSessions = deleteInvalidSessions;
    }

    public void setSessionIdCookie(Cookie sessionIdCookie) {
        this.sessionIdCookie = sessionIdCookie;
    }

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    public void setSessionDao(SessionDAO sessionDao) {
        this.sessionDao = sessionDao;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean isServletContainerSessions() {
        return false;
    }

    protected void onStop(Session session, SessionKey key) {
        super.onStop(session, key);
        HttpServletRequest request = WebUtils.getHttpRequest(key);
        HttpServletResponse response = WebUtils.getHttpResponse(key);
        this.removeSessionIdCookie(request, response);
    }

    protected void afterStopped(Session session) {
        if (this.isDeleteInvalidSessions()) {
            this.delete(session);
        }
    }

    protected void delete(Session session) {
        this.sessionDao.delete(session);
    }

    private void removeSessionIdCookie(HttpServletRequest request, HttpServletResponse response) {
        this.getSessionIdCookie().removeFrom(request, response);
    }

    protected void onStart(Session session, SessionContext context){
        super.onStart(session, context);

        HttpServletRequest request = WebUtils.getHttpRequest(context);
        HttpServletResponse response = WebUtils.getHttpResponse(context);

        Serializable sessionId = session.getId();
        this.storeSessionId(sessionId, request, response);
        request.removeAttribute(REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
    }

    private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response) {
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        } else {
            Cookie template = this.getSessionIdCookie();
            Cookie cookie = new SimpleCookie(template);
            String idString = currentId.toString();
            cookie.setValue(idString);
            cookie.saveTo(request, response);
        }
    }

    @Override
    protected Session createSession(SessionContext context) throws AuthorizationException {
        return doCreateSession(context);
    }


    protected Session doCreateSession(SessionContext context) {
        Session s = newSessionInstance(context);
        create(s);
        return s;
    }

    protected void create(Session session) {
        sessionDao.create(session);
    }

    protected Session newSessionInstance(SessionContext context) {
        return getSessionFactory().createSession(context);
    }

    @Override
    protected Session doGetSession(SessionKey key) {
        Serializable sessionid = getSessionId(key);
        if(sessionid == null){
            return null;
        }
        Session session = sessionDao.readSession(sessionid);
        if(session == null){
            String msg = "Could not find session with ID [" + sessionid + "]";
            throw new UnknownSessionException(msg);
        }
        return session;
    }


    @Override
    protected Collection<Session> getActiveSessions() {
        Collection<Session> activeSessions = sessionDao.getActiveSessions();
        return activeSessions == null ? Collections.emptyList() : activeSessions;
    }

    protected Session createExposedSession(Session session, SessionContext context) {
        ServletRequest request = WebUtils.getHttpRequest(context);
        ServletResponse response = WebUtils.getHttpResponse(context);
        SessionKey key = new WebSessionKey(session.getId(), request, response);
        return new DelegatingSession(this, key);
    }

    protected Session createExposedSession(Session session, SessionKey key) {
        ServletRequest request = WebUtils.getRequest(key);
        ServletResponse response = WebUtils.getResponse(key);
        SessionKey sessionKey = new WebSessionKey(session.getId(), request, response);
        return new DelegatingSession(this, sessionKey);
    }

    public Serializable getSessionId(SessionKey key) {
        Serializable id = key.getSessionId();
        if(id == null){
            ServletRequest request = WebUtils.getRequest(key);
            ServletResponse response = WebUtils.getResponse(key);
            id = getSessionId(request, response);
        }
        return id;
    }

    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return getReferencedSessionId(request, response);
    }

    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {

        String id = getSessionIdCookieValue(request, response);
        if (id != null) {
            request.setAttribute(REFERENCED_SESSION_ID_SOURCE,
                    AmbitoHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
        } else {
            //not in a cookie, or cookie is disabled - try the request URI as a fallback (i.e. due to URL rewriting):

            //try the URI path segment parameters first:
            id = getUriPathSegmentParamValue(request, AmbitoHttpSession.DEFAULT_SESSION_ID_NAME);

            if (id == null) {
                //not a URI path segment parameter, try the query parameters:
                String name = getSessionIdName();
                id = request.getParameter(name);
                if (id == null) {
                    //try lowercase:
                    id = request.getParameter(name.toLowerCase());
                }
            }
            if (id != null) {
                request.setAttribute(REFERENCED_SESSION_ID_SOURCE,
                        AmbitoHttpServletRequest.URL_SESSION_ID_SOURCE);
            }
        }
        if (id != null) {
            request.setAttribute(AmbitoHttpServletRequest.REFERENCED_SESSION_ID, id);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(AmbitoHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }

        // always set rewrite flag - SHIRO-361
        request.setAttribute(AmbitoHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, true);

        return id;
    }


    //since 1.2.1
    private String getSessionIdName() {
        String name = this.sessionIdCookie != null ? this.sessionIdCookie.getName() : null;
        if (name == null) {
            name = AmbitoHttpSession.DEFAULT_SESSION_ID_NAME;
        }
        return name;
    }

    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {
//        if (!isSessionIdCookieEnabled()) {
//            log.debug("Session ID cookie is disabled - session id will not be acquired from a request cookie.");
//            return null;
//        }
        if (!(request instanceof HttpServletRequest)) {
//            log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
    }


    //SHIRO-351
    //also see http://cdivilly.wordpress.com/2011/04/22/java-servlets-uri-parameters/
    //since 1.2.2
    private String getUriPathSegmentParamValue(ServletRequest servletRequest, String paramName) {

        if (!(servletRequest instanceof HttpServletRequest)) {
            return null;
        }
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        if (uri == null) {
            return null;
        }

        int queryStartIndex = uri.indexOf('?');
        if (queryStartIndex >= 0) { //get rid of the query string
            uri = uri.substring(0, queryStartIndex);
        }

        int index = uri.indexOf(';'); //now check for path segment parameters:
        if (index < 0) {
            //no path segment params - return:
            return null;
        }

        //there are path segment params, let's get the last one that may exist:

        final String TOKEN = paramName + "=";

        uri = uri.substring(index+1); //uri now contains only the path segment params

        //we only care about the last JSESSIONID param:
        index = uri.lastIndexOf(TOKEN);
        if (index < 0) {
            //no segment param:
            return null;
        }

        uri = uri.substring(index + TOKEN.length());

        index = uri.indexOf(';'); //strip off any remaining segment params:
        if(index >= 0) {
            uri = uri.substring(0, index);
        }

        return uri; //what remains is the value
    }

}
