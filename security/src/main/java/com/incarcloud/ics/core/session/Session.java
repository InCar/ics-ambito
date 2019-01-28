package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.InvalidSessionException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


public interface Session {
    Serializable getId();
    Date getStartTimestamp();
    Date getLastAccessTime();
    long getTimeout();
    void setTimeout(long timeout);
    String getHost();
    void setHost(String host);
    void touch() throws InvalidSessionException;
    void stop();
    Collection<Object> getAttributeKeys() throws InvalidSessionException;
    Object getAttribute(Object key) throws InvalidSessionException;
    void setAttribute(Object key, Object value) throws InvalidSessionException;
    Object removeAttribute(Object key) throws InvalidSessionException;

}
