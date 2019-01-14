package com.incarcloud.ics.core.session;


import com.incarcloud.ics.core.exception.SessionException;


public interface SessionManager {
    Session start(SessionContext sessionContext);

    Session getSession(SessionKey key) throws SessionException;



}
