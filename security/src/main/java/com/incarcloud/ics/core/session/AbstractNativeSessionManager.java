package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.UnAuthorizeException;
import com.incarcloud.ics.core.exception.InvalidSessionException;
import com.incarcloud.ics.core.exception.SessionException;
import com.incarcloud.ics.core.exception.UnknownSessionException;

import java.util.Collection;
import java.util.Date;


public abstract class AbstractNativeSessionManager extends AbstractSessionManager implements NativeSessionManager {

    @Override
    public Date getStartTimestamp(SessionKey sessionKey) {
        Session session = lookupRequiredSession(sessionKey);
        return session.getStartTimestamp();
    }

    @Override
    public Date getLastAccessTime(SessionKey sessionKey) {
        return lookupRequiredSession(sessionKey).getLastAccessTime();
    }

    @Override
    public long getTimeout(SessionKey sessionKey) throws InvalidSessionException {
        return lookupRequiredSession(sessionKey).getTimeout();
    }

    @Override
    public void setTimeout(SessionKey sessionKey, long timeOut) throws InvalidSessionException {
        Session session = lookupRequiredSession(sessionKey);
        session.setTimeout(timeOut);
        onChange(session);
    }

    @Override
    public void touch(SessionKey sessionKey) throws InvalidSessionException {
        Session session = lookupRequiredSession(sessionKey);
        session.touch();
        onChange(session);
    }

    @Override
    public String getHost(SessionKey sessionKey) {
        return lookupRequiredSession(sessionKey).getHost();
    }

    @Override
    public boolean isValid(SessionKey sessionKey) {
        try {
            checkValid(sessionKey);
            return true;
        }catch (InvalidSessionException e){
            return false;
        }
    }

    @Override
    public void checkValid(SessionKey key) throws InvalidSessionException {
        lookupRequiredSession(key);
    }

    @Override
    public void stop(SessionKey sessionKey) throws InvalidSessionException {
        Session session = lookupRequiredSession(sessionKey);
        try {
            session.stop();
            onStop(session, sessionKey);
//            notifyStop(session);
        } finally {
            afterStopped(session);
        }
    }

    protected void afterStopped(Session session) {
    }

    protected void onStop(Session session, SessionKey sessionKey){
        onStop(session);
    }

    protected void onStop(Session session){
        onChange(session);
    }

    protected void onChange(Session s) {
    }


    @Override
    public Collection<Object> getAttributeKeys(SessionKey sessionKey) {
        return lookupRequiredSession(sessionKey).getAttributeKeys();
    }

    @Override
    public Object getAttribute(SessionKey sessionKey, Object key) throws InvalidSessionException {
        return lookupRequiredSession(sessionKey).getAttribute(key);
    }

    @Override
    public void setAttribute(SessionKey sessionKey, Object key, Object value) throws InvalidSessionException {
        if(value == null){
            this.removeAttribute(sessionKey, key);
        }else {
            Session session = lookupRequiredSession(sessionKey);
            session.setAttribute(key, value);
            onChange(session);
        }

    }

    @Override
    public Object removeAttribute(SessionKey sessionKey, Object key) throws InvalidSessionException {
        Session session = lookupRequiredSession(sessionKey);
        Object o = session.removeAttribute(key);
        onChange(session);
        return o;
    }

    @Override
    public Session start(SessionContext sessionContext) {
        Session session = createSession(sessionContext);
        this.applyGlobalSessionTimeout(session);
        this.onStart(session, sessionContext);
        //Don't expose the EIS-tier Session object to the client-tier:
        return createExposedSession(session, sessionContext);
    }

    protected void onStart(Session session, SessionContext sessionContext){
    }

    protected Session createExposedSession(Session session, SessionContext context) {
        return new DelegatingSession(this, new WebSessionKey(session.getId()));
    }

    protected Session createExposedSession(Session session, SessionKey key) {
        return new DelegatingSession(this, new WebSessionKey(session.getId()));
    }

    protected abstract Session createSession(SessionContext context) throws UnAuthorizeException;

    protected void applyGlobalSessionTimeout(Session session) {
        session.setTimeout(getGlobalSessionTimeout());
        onChange(session);
    }

    @Override
    public Session getSession(SessionKey key) throws SessionException {
        Session session = lookupSession(key);
        return session != null ? createExposedSession(session, key) : null;
    }

    private Session lookupSession(SessionKey key) throws SessionException {
        if (key == null) {
            throw new NullPointerException("SessionKey argument cannot be null.");
        }
        return doGetSession(key);
    }

    private Session lookupRequiredSession(SessionKey key) throws SessionException {
        Session session = lookupSession(key);
        if (session == null) {
            String msg = "Unable to locate required Session instance based on SessionKey [" + key + "].";
            throw new UnknownSessionException(msg);
        }
        return session;
    }

    protected abstract Session doGetSession(SessionKey key);
}
