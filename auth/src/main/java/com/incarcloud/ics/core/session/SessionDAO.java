package com.incarcloud.ics.core.session;


import com.incarcloud.ics.core.exception.UnknownSessionException;

import java.io.Serializable;
import java.util.Collection;


public interface SessionDAO {

    Serializable create(Session session);

    Session readSession(Serializable sessionId) throws UnknownSessionException;

    void update(Session session) throws UnknownSessionException;

    void delete(Session session);

    Collection<Session> getActiveSessions();
}
