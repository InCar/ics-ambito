package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.InvalidSessionException;
import com.incarcloud.ics.core.utils.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

public class HttpServletSession implements Session{

    private static final String HOST_SESSION_KEY = HttpServletSession.class.getName() + ".HOST_SESSION_KEY";
    private static final String TOUCH_OBJECT_SESSION_KEY = HttpServletSession.class.getName() + ".TOUCH_OBJECT_SESSION_KEY";

    private HttpSession httpSession;

    public HttpServletSession(HttpSession httpSession, String host) {
        this.httpSession = httpSession;
        if(StringUtils.isNotBlank(host)){
            setHost(host);
        }
    }

    @Override
    public Serializable getId() {
        return httpSession.getId();
    }

    @Override
    public Date getStartTimestamp() {
        return new Date(httpSession.getCreationTime());
    }

    @Override
    public Date getLastAccessTime() {
        return new Date(httpSession.getLastAccessedTime());
    }

    @Override
    public long getTimeout() throws InvalidSessionException {
        return httpSession.getMaxInactiveInterval() * 1000L;
    }

    @Override
    public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException{
        try {
            int timeout = Long.valueOf(maxIdleTimeInMillis / 1000).intValue();
            httpSession.setMaxInactiveInterval(timeout);
        } catch (Exception e) {
            throw new InvalidSessionException(e);
        }
    }

    @Override
    public String getHost() {
        return (String) getAttribute(HOST_SESSION_KEY);
    }

    @Override
    public void setHost(String host) {
        setAttribute(HOST_SESSION_KEY, host);
    }

    @Override
    public void touch() throws InvalidSessionException {
        //just manipulate the session to update the access time:
        try {
            httpSession.setAttribute(TOUCH_OBJECT_SESSION_KEY, TOUCH_OBJECT_SESSION_KEY);
            httpSession.removeAttribute(TOUCH_OBJECT_SESSION_KEY);
        } catch (Exception e) {
            throw new InvalidSessionException(e);
        }
    }


    @Override
    public void stop() {
        try {
            httpSession.invalidate();
        } catch (Exception e) {
            throw new InvalidSessionException(e);
        }
    }

    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        try {
            Enumeration namesEnum = httpSession.getAttributeNames();
            Collection<Object> keys = null;
            if (namesEnum != null) {
                keys = new ArrayList<Object>();
                while (namesEnum.hasMoreElements()) {
                    keys.add(namesEnum.nextElement());
                }
            }
            return keys;
        } catch (Exception e) {
            throw new InvalidSessionException(e);
        }
    }

    public Object getAttribute(Object key) throws InvalidSessionException {
        try {
            return httpSession.getAttribute(assertString(key));
        } catch (Exception e) {
            throw new InvalidSessionException(e);
        }
    }

    public void setAttribute(Object key, Object value) throws InvalidSessionException {
        try {
            httpSession.setAttribute(assertString(key), value);
        } catch (Exception e) {
            throw new InvalidSessionException(e);
        }
    }

    public Object removeAttribute(Object key) throws InvalidSessionException {
        try {
            String sKey = assertString(key);
            Object removed = httpSession.getAttribute(sKey);
            httpSession.removeAttribute(sKey);
            return removed;
        } catch (Exception e) {
            throw new InvalidSessionException(e);
        }
    }

    private static String assertString(Object key) {
        if (!(key instanceof String)) {
            String msg = "HttpSession based implementations of the Session interface requires attribute keys " +
                    "to be String objects.  The HttpSession class does not support anything other than String keys.";
            throw new IllegalArgumentException(msg);
        }
        return (String) key;
    }

}
