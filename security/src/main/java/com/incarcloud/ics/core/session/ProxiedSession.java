package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.InvalidSessionException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


public class ProxiedSession implements Session {

    protected final Session session;

    public ProxiedSession(Session session) {
        this.session = session;
    }

    @Override
    public Serializable getId() {
        return session.getId();
    }

    @Override
    public Date getStartTimestamp() {
        return session.getStartTimestamp();
    }

    @Override
    public Date getLastAccessTime() {
        return session.getLastAccessTime();
    }

    @Override
    public long getTimeout() {
        return session.getTimeout();
    }

    @Override
    public void setTimeout(long timeout) {
        session.setTimeout(timeout);
    }

    @Override
    public String getHost() {
        return session.getHost();
    }

    @Override
    public void setHost(String host) {
        session.setHost(host);
    }

    @Override
    public void touch() throws InvalidSessionException {
        session.touch();
    }

    @Override
    public void stop() {
        session.stop();
    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return session.getAttributeKeys();
    }

    @Override
    public Object getAttribute(Object key) throws InvalidSessionException {
        return session.getAttribute(key);
    }

    @Override
    public void setAttribute(Object key, Object value) throws InvalidSessionException {
        session.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key) throws InvalidSessionException {
        return session.removeAttribute(key);
    }

}
