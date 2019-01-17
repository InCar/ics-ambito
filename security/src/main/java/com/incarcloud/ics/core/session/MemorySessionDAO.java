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
import com.incarcloud.ics.core.utils.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

/**
 * 该类是基于内存的SessionDAO实现，直接会话保存在ConcurrentMap中，对于拥有大量会话的应用不建议使用，可能会造成OutOfMemory异常，
 * 如果应用的会话数量很多，建议实现AbstractSessionDAO，使用更有扩展性的存储方式，比如保存到enhance, redis，mysql等
 */
public class MemorySessionDAO extends AbstractSessionDAO {

    private static final Logger log = Logger.getLogger(MemorySessionDAO.class.getName());

    private ConcurrentMap<Serializable, Session> sessions;

    public MemorySessionDAO() {
        this.sessions = new ConcurrentHashMap<Serializable, Session>();
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        storeSession(sessionId, session);
        return sessionId;
    }

    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        }
        return sessions.putIfAbsent(id, session);
    }

    protected Session doReadSession(Serializable sessionId) {
        return sessions.get(sessionId);
    }


    public void update(Session session) throws UnknownSessionException {
        storeSession(session.getId(), session);
    }

    public void delete(Session session) {
        if (session == null) {
            throw new NullPointerException("session argument cannot be null.");
        }
        Serializable id = session.getId();
        if (id != null) {
            sessions.remove(id);
        }
    }

    public Collection<Session> getActiveSessions() {
        Collection<Session> values = sessions.values();
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableCollection(values);
        }
    }

}
