package com.incarcloud.ics.core.session;


public interface SessionFactory {
    Session createSession(SessionContext sessionContext);
}
