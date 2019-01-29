package com.incarcloud.ics.core.session;

public class SimpleSessionFactory implements SessionFactory {


    @Override
    public Session createSession(SessionContext sessionContext) {
        return new SimpleSession(sessionContext.getHost());
    }
}
