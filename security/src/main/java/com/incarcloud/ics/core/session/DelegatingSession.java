package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.InvalidSessionException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


public class DelegatingSession implements Session{
    private final SessionKey key;

    //cached fields to avoid a server-side method call if out-of-process:
    private Date startTimestamp = null;
    private String host = null;

    private final transient NativeSessionManager sessionManager;


    public DelegatingSession(NativeSessionManager sessionManager, SessionKey key) {
        if (sessionManager == null) {
            throw new IllegalArgumentException("sessionManager argument cannot be null.");
        }
        if (key == null) {
            throw new IllegalArgumentException("sessionKey argument cannot be null.");
        }
        if (key.getSessionId() == null) {
            String msg = "The " + DelegatingSession.class.getName() + " implementation requires that the " +
                    "SessionKey argument returns a non-null sessionId to support the " +
                    "Session.getId() invocations.";
            throw new IllegalArgumentException(msg);
        }
        this.sessionManager = sessionManager;
        this.key = key;
    }

    public Serializable getId() {
        return key.getSessionId();
    }

    public Date getStartTimestamp() {
        if (startTimestamp == null) {
            startTimestamp = sessionManager.getStartTimestamp(key);
        }
        return startTimestamp;
    }

    public Date getLastAccessTime() {
        //can't cache - only business pojo knows the accurate time:
        return sessionManager.getLastAccessTime(key);
    }

    public long getTimeout() throws InvalidSessionException {
        return sessionManager.getTimeout(key);
    }

    public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {
        sessionManager.setTimeout(key, maxIdleTimeInMillis);
    }

    public String getHost() {
        if (host == null) {
            host = sessionManager.getHost(key);
        }
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }


    public void touch() throws InvalidSessionException {
        sessionManager.touch(key);
    }


    public void stop() throws InvalidSessionException {
        sessionManager.stop(key);
    }

    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return sessionManager.getAttributeKeys(key);
    }


    public Object getAttribute(Object attributeKey) throws InvalidSessionException {
        return sessionManager.getAttribute(this.key, attributeKey);
    }


    public void setAttribute(Object attributeKey, Object value) throws InvalidSessionException {
        if (value == null) {
            removeAttribute(attributeKey);
        } else {
            sessionManager.setAttribute(this.key, attributeKey, value);
        }
    }

    public Object removeAttribute(Object attributeKey) throws InvalidSessionException {
        return sessionManager.removeAttribute(this.key, attributeKey);
    }
}
