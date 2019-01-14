/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.incarcloud.ics.core.servlet;


import com.incarcloud.ics.core.role.SimpleRole;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.SecurityUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * A {@code AmbitoHttpServletRequest} wrps the Servlet container's original {@code ServletRequest} instance, but ensures
 * that all {@link HttpServletRequest} invocations that require Shiro's support ({@link #getRemoteUser getRemoteUser},
 * {@link #getSession getSession}, etc) can be executed first by Shiro as necessary before allowing the underlying
 * Servlet container instance's method to be invoked.
 *
 * @since 0.2
 */
public class AmbitoHttpServletRequest extends HttpServletRequestWrapper {

    //TODO - complete JavaDoc

    //The following 7 constants support the Shiro's implementation of the Servlet Specification
    public static final String COOKIE_SESSION_ID_SOURCE = "cookie";
    public static final String URL_SESSION_ID_SOURCE = "url";
    public static final String REFERENCED_SESSION_ID = AmbitoHttpServletRequest.class.getName() + "_REQUESTED_SESSION_ID";
    public static final String REFERENCED_SESSION_ID_IS_VALID = AmbitoHttpServletRequest.class.getName() + "_REQUESTED_SESSION_ID_VALID";
    public static final String REFERENCED_SESSION_IS_NEW = AmbitoHttpServletRequest.class.getName() + "_REFERENCED_SESSION_IS_NEW";
    public static final String REFERENCED_SESSION_ID_SOURCE = AmbitoHttpServletRequest.class.getName() + "REFERENCED_SESSION_ID_SOURCE";
    public static final String IDENTITY_REMOVED_KEY = AmbitoHttpServletRequest.class.getName() + "_IDENTITY_REMOVED_KEY";
    public static final String SESSION_ID_URL_REWRITING_ENABLED = AmbitoHttpServletRequest.class.getName() + "_SESSION_ID_URL_REWRITING_ENABLED";

    protected ServletContext servletContext = null;

    protected HttpSession session = null;
    protected boolean httpSessions = true;

    public AmbitoHttpServletRequest(HttpServletRequest wrapped, ServletContext servletContext, boolean httpSessions) {
        super(wrapped);
        this.servletContext = servletContext;
        this.httpSessions = httpSessions;
    }

    public boolean isHttpSessions() {
        return httpSessions;
    }

    public String getRemoteUser() {
        String remoteUser;
        Object scPrincipal = getSubjectPrincipal();
        if (scPrincipal != null) {
            if (scPrincipal instanceof String) {
                return (String) scPrincipal;
            } else if (scPrincipal instanceof Principal) {
                remoteUser = ((Principal) scPrincipal).getName();
            } else {
                remoteUser = scPrincipal.toString();
            }
        } else {
            remoteUser = super.getRemoteUser();
        }
        return remoteUser;
    }

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected Object getSubjectPrincipal() {
        Object userPrincipal = null;
        Subject subject = getSubject();
        if (subject != null) {
            userPrincipal = subject.getPrincipal();
        }
        return userPrincipal;
    }

    public boolean isUserInRole(String s) {
        Subject subject = getSubject();
        boolean inRole = (subject != null && subject.hasRole(new SimpleRole(s)));
        if (!inRole) {
            inRole = super.isUserInRole(s);
        }
        return inRole;
    }

    public Principal getUserPrincipal() {
        Principal userPrincipal;
        Object scPrincipal = getSubjectPrincipal();
        if (scPrincipal != null) {
            if (scPrincipal instanceof Principal) {
                userPrincipal = (Principal) scPrincipal;
            } else {
                userPrincipal = new ObjectPrincipal(scPrincipal);
            }
        } else {
            userPrincipal = super.getUserPrincipal();
        }
        return userPrincipal;
    }

    public String getRequestedSessionId() {
        String requestedSessionId = null;
        if (isHttpSessions()) {
            requestedSessionId = super.getRequestedSessionId();
        } else {
            Object sessionId = getAttribute(REFERENCED_SESSION_ID);
            if (sessionId != null) {
                requestedSessionId = sessionId.toString();
            }
        }

        return requestedSessionId;
    }

    public HttpSession getSession(boolean create) {

        HttpSession httpSession = super.getSession(false);
        if (httpSession == null && create) {
            httpSession = super.getSession(create);
        }
        return httpSession;
    }

    public HttpSession getSession() {
        return getSession(true);
    }

    public boolean isRequestedSessionIdValid() {
        if (isHttpSessions()) {
            return super.isRequestedSessionIdValid();
        } else {
            Boolean value = (Boolean) getAttribute(REFERENCED_SESSION_ID_IS_VALID);
            return (value != null && value.equals(Boolean.TRUE));
        }
    }

    public boolean isRequestedSessionIdFromCookie() {
        if (isHttpSessions()) {
            return super.isRequestedSessionIdFromCookie();
        } else {
            String value = (String) getAttribute(REFERENCED_SESSION_ID_SOURCE);
            return value != null && value.equals(COOKIE_SESSION_ID_SOURCE);
        }
    }

    public boolean isRequestedSessionIdFromURL() {
        if (isHttpSessions()) {
            return super.isRequestedSessionIdFromURL();
        } else {
            String value = (String) getAttribute(REFERENCED_SESSION_ID_SOURCE);
            return value != null && value.equals(URL_SESSION_ID_SOURCE);
        }
    }

    public boolean isRequestedSessionIdFromUrl() {
        return isRequestedSessionIdFromURL();
    }

    private class ObjectPrincipal implements java.security.Principal {
        private Object object = null;

        public ObjectPrincipal(Object object) {
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public String getName() {
            return getObject().toString();
        }

        public int hashCode() {
            return object.hashCode();
        }

        public boolean equals(Object o) {
            if (o instanceof ObjectPrincipal) {
                ObjectPrincipal op = (ObjectPrincipal) o;
                return getObject().equals(op.getObject());
            }
            return false;
        }

        public String toString() {
            return object.toString();
        }
    }
}
