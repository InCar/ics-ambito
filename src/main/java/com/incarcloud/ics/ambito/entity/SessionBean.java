package com.incarcloud.ics.ambito.entity;

import com.incarcloud.ics.ambito.jdbc.Table;

import java.io.Serializable;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/15
 */
@Table(name = "t_sys_session")
public class SessionBean implements Serializable {

    private static final long serialVersionUID = -8543325972370230532L;
    private String sessionId;
    private byte[] session;

    public SessionBean() {
    }

    public SessionBean(String sessionId, byte[] session) {
        this.sessionId = sessionId;
        this.session = session;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public byte[] getSession() {
        return session;
    }

    public void setSession(byte[] session) {
        this.session = session;
    }

}
