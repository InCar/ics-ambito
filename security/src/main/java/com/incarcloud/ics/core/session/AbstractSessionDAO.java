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
package com.incarcloud.ics.core.session;


import com.incarcloud.ics.core.exception.UnknownSessionException;

import java.io.Serializable;



public abstract class AbstractSessionDAO implements SessionDAO {


    private SessionIdGenerator sessionIdGenerator;


    public AbstractSessionDAO() {
        this.sessionIdGenerator = new JavaUuidSessionIdGenerator();
    }


    public SessionIdGenerator getSessionIdGenerator() {
        return sessionIdGenerator;
    }


    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }


    protected Serializable generateSessionId(Session session) {
        if (this.sessionIdGenerator == null) {
            String msg = "sessionIdGenerator attribute has not been configured.";
            throw new IllegalStateException(msg);
        }
        return this.sessionIdGenerator.generateId(session);
    }


    public Serializable create(Session session) {
        Serializable sessionId = doCreate(session);
        verifySessionId(sessionId);
        return sessionId;
    }


    private void verifySessionId(Serializable sessionId) {
        if (sessionId == null) {
            String msg = "sessionId returned from doCreate implementation is null.  Please verify the implementation.";
            throw new IllegalStateException(msg);
        }
    }


    protected void assignSessionId(Session session, Serializable sessionId) {
        ((SimpleSession) session).setId(sessionId);
    }


    protected abstract Serializable doCreate(Session session);


    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session s = doReadSession(sessionId);
        if (s == null) {
            throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
        }
        return s;
    }


    protected abstract Session doReadSession(Serializable sessionId);

}
