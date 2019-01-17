package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.InvalidSessionException;

import java.util.Collection;
import java.util.Date;

public interface NativeSessionManager extends SessionManager{

    Date getStartTimestamp(SessionKey sessionKey);

    Date getLastAccessTime(SessionKey sessionKey);

    long getTimeout(SessionKey sessionKey) throws InvalidSessionException;

    void setTimeout(SessionKey sessionKey, long timeOut) throws InvalidSessionException;

    void touch(SessionKey sessionKey) throws InvalidSessionException;

    String getHost(SessionKey sessionKey);

    boolean isValid(SessionKey sessionKey);

    void checkValid(SessionKey key) throws InvalidSessionException;

    void stop(SessionKey sessionKey) throws InvalidSessionException;

    Collection<Object> getAttributeKeys(SessionKey sessionKey);

    Object getAttribute(SessionKey var1, Object sessionKey) throws InvalidSessionException;

    void setAttribute(SessionKey sessionKey, Object key, Object value) throws InvalidSessionException;

    Object removeAttribute(SessionKey sessionKey, Object key) throws InvalidSessionException;

}
